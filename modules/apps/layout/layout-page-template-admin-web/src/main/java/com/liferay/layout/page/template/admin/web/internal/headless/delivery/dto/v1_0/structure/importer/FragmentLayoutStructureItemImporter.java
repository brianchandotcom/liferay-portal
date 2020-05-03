/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.layout.page.template.admin.web.internal.headless.delivery.dto.v1_0.structure.importer;

import com.liferay.document.library.util.DLURLHelperUtil;
import com.liferay.fragment.contributor.FragmentCollectionContributorTracker;
import com.liferay.fragment.entry.processor.util.EditableFragmentEntryProcessorUtil;
import com.liferay.fragment.model.FragmentCollection;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.processor.FragmentEntryProcessorRegistry;
import com.liferay.fragment.service.FragmentCollectionService;
import com.liferay.fragment.service.FragmentEntryLinkLocalService;
import com.liferay.fragment.service.FragmentEntryLocalService;
import com.liferay.fragment.validator.FragmentEntryValidator;
import com.liferay.headless.delivery.dto.v1_0.PageElement;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.layout.util.structure.LayoutStructureItem;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepository;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jürgen Kappler
 */
@Component(service = LayoutStructureItemImporter.class)
public class FragmentLayoutStructureItemImporter
	extends BaseLayoutStructureItemImporter
	implements LayoutStructureItemImporter {

	@Override
	public LayoutStructureItem addLayoutStructureItem(
			Layout layout, LayoutStructure layoutStructure,
			PageElement pageElement, String parentItemId, int position)
		throws Exception {

		FragmentEntryLink fragmentEntryLink = _addFragmentEntryLink(
			layout, pageElement, position);

		if (fragmentEntryLink == null) {
			return null;
		}

		return layoutStructure.addFragmentLayoutStructureItem(
			fragmentEntryLink.getFragmentEntryLinkId(), parentItemId, position);
	}

	@Override
	public PageElement.Type getPageElementType() {
		return PageElement.Type.FRAGMENT;
	}

	private FragmentEntryLink _addFragmentEntryLink(
			Layout layout, PageElement pageElement, int position)
		throws Exception {

		Map<String, Object> definitionMap = getDefinitionMap(
			pageElement.getDefinition());

		if (definitionMap == null) {
			return null;
		}

		Map<String, Object> fragmentDefinitionMap =
			(Map<String, Object>)definitionMap.get("fragment");

		String fragmentKey = (String)fragmentDefinitionMap.get("key");

		if (Validator.isNull(fragmentKey)) {
			return null;
		}

		FragmentEntry fragmentEntry = _getFragmentEntry(fragmentKey, layout);

		if (fragmentEntry == null) {
			return null;
		}

		long fragmentEntryId = fragmentEntry.getFragmentEntryId();
		String html = fragmentEntry.getHtml();
		String js = fragmentEntry.getJs();
		String css = fragmentEntry.getCss();
		String configuration = fragmentEntry.getConfiguration();

		FragmentCollection fragmentCollection =
			_fragmentCollectionService.fetchFragmentCollection(
				fragmentEntry.getFragmentCollectionId());

		JSONObject defaultEditableValuesJSONObject =
			_fragmentEntryProcessorRegistry.getDefaultEditableValuesJSONObject(
				_replaceResources(fragmentCollection, html), configuration);

		Map<String, String> editableTypes =
			EditableFragmentEntryProcessorUtil.getEditableTypes(html);

		JSONObject fragmentEntryProcessorValuesJSONObject = JSONUtil.put(
			"com.liferay.fragment.entry.processor.background.image." +
				"BackgroundImageFragmentEntryProcessor",
			JSONFactoryUtil.createJSONObject());

		JSONObject editableFragmentEntryProcessorJSONObject =
			_toEditableFragmentEntryProcessorJSONObject(
				editableTypes,
				(List<Object>)definitionMap.get("fragmentFields"));

		if (editableFragmentEntryProcessorJSONObject.length() > 0) {
			fragmentEntryProcessorValuesJSONObject.put(
				"com.liferay.fragment.entry.processor.editable." +
					"EditableFragmentEntryProcessor",
				editableFragmentEntryProcessorJSONObject);
		}

		Map<String, String> configurationTypes = _getConfigurationTypes(
			configuration);

		JSONObject freeMarkerFragmentEntryProcessorJSONObject =
			_toFreeMarkerFragmentEntryProcessorJSONObject(
				configurationTypes,
				(Map<String, Object>)definitionMap.get("fragmentConfig"));

		_fragmentEntryValidator.validateConfigurationValues(
			configuration, fragmentEntryProcessorValuesJSONObject);

		if (freeMarkerFragmentEntryProcessorJSONObject.length() > 0) {
			fragmentEntryProcessorValuesJSONObject.put(
				"com.liferay.fragment.entry.processor.freemarker." +
					"FreeMarkerFragmentEntryProcessor",
				freeMarkerFragmentEntryProcessorJSONObject);
		}

		JSONObject jsonObject = _deepMerge(
			defaultEditableValuesJSONObject,
			fragmentEntryProcessorValuesJSONObject);

		try {
			return _fragmentEntryLinkLocalService.addFragmentEntryLink(
				layout.getUserId(), layout.getGroupId(), 0, fragmentEntryId, 0,
				_portal.getClassNameId(Layout.class.getName()),
				layout.getPlid(), css, html, js, configuration,
				jsonObject.toString(), StringUtil.randomId(), position,
				fragmentKey, ServiceContextThreadLocal.getServiceContext());
		}
		catch (PortalException portalException) {
			if (_log.isWarnEnabled()) {
				_log.warn(portalException, portalException);
			}
		}

		return null;
	}

	private JSONObject _createBaseFragmentFieldJSONObject(
		Map<String, Object> map) {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		if (map == null) {
			return jsonObject;
		}

		Map<String, Object> valueI18nMap = (Map<String, Object>)map.get(
			"value_i18n");

		if (valueI18nMap != null) {
			for (Map.Entry<String, Object> entry : valueI18nMap.entrySet()) {
				jsonObject.put(entry.getKey(), entry.getValue());
			}

			return jsonObject;
		}

		Map<String, Object> defaultValueMap = (Map<String, Object>)map.get(
			"defaultValue");

		if (defaultValueMap != null) {
			jsonObject.put("defaultValue", defaultValueMap.get("value"));
		}

		_processMapping(jsonObject, (Map<String, String>)map.get("mapping"));

		return jsonObject;
	}

	private JSONObject _createFragmentLinkConfigJSONObject(
		Map<String, Object> fragmentLinkMap) {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		if (fragmentLinkMap == null) {
			return jsonObject;
		}

		Map<String, Object> hrefMap = (Map<String, Object>)fragmentLinkMap.get(
			"href");

		if (hrefMap == null) {
			return jsonObject;
		}

		Map<String, Object> defaultValueMap = (Map<String, Object>)hrefMap.get(
			"defaultValue");

		String target = (String)fragmentLinkMap.get("target");

		if (target != null) {
			jsonObject.put(
				"target", "_" + StringUtil.lowerCaseFirstLetter(target));
		}

		Object value = hrefMap.get("value");

		if (value != null) {
			jsonObject.put("href", value);

			return jsonObject;
		}

		if (defaultValueMap != null) {
			value = defaultValueMap.get("value");
		}

		if (value != null) {
			jsonObject.put("href", value);
		}

		_processMapping(
			jsonObject, (Map<String, String>)hrefMap.get("mapping"));

		return jsonObject;
	}

	private JSONObject _createImageConfigJSONObject(
		Map<String, Object> fragmentImageMap) {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		if (fragmentImageMap == null) {
			return jsonObject;
		}

		Map<String, Object> descriptionMap =
			(Map<String, Object>)fragmentImageMap.get("description");

		if (descriptionMap == null) {
			return jsonObject;
		}

		String value = (String)descriptionMap.get("value");

		if (value != null) {
			jsonObject.put("alt", value);
		}

		return jsonObject;
	}

	private JSONObject _deepMerge(
			JSONObject jsonObject1, JSONObject jsonObject2)
		throws JSONException {

		if (jsonObject1 == null) {
			return JSONFactoryUtil.createJSONObject(jsonObject2.toString());
		}

		if (jsonObject2 == null) {
			return JSONFactoryUtil.createJSONObject(jsonObject1.toString());
		}

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			jsonObject1.toString());

		Iterator<String> iterator = jsonObject2.keys();

		while (iterator.hasNext()) {
			String key = iterator.next();

			if (!jsonObject.has(key)) {
				jsonObject.put(key, jsonObject2.get(key));
			}
			else {
				Object value1 = jsonObject1.get(key);
				Object value2 = jsonObject2.get(key);

				if ((value1 instanceof JSONObject) &&
					(value2 instanceof JSONObject)) {

					jsonObject.put(
						key,
						_deepMerge(
							(JSONObject)value1,
							jsonObject2.getJSONObject(key)));
				}
				else {
					jsonObject.put(key, value2);
				}
			}
		}

		return jsonObject;
	}

	private Map<String, String> _getConfigurationTypes(String configuration)
		throws JSONException {

		Map<String, String> configurationTypes = new HashMap<>();

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(configuration);

		JSONArray fieldSetsJSONArray = jsonObject.getJSONArray("fieldSets");

		if (fieldSetsJSONArray == null) {
			return configurationTypes;
		}

		for (int i = 0; i < fieldSetsJSONArray.length(); i++) {
			JSONObject fieldsJSONObject = fieldSetsJSONArray.getJSONObject(i);

			JSONArray fieldsJSONArray = fieldsJSONObject.getJSONArray("fields");

			for (int j = 0; j < fieldsJSONArray.length(); j++) {
				JSONObject fieldJSONObject = fieldsJSONArray.getJSONObject(j);

				configurationTypes.put(
					fieldJSONObject.getString("name"),
					fieldJSONObject.getString("type"));
			}
		}

		return configurationTypes;
	}

	private FragmentEntry _getFragmentEntry(String fragmentKey, Layout layout) {
		FragmentEntry fragmentEntry =
			_fragmentEntryLocalService.fetchFragmentEntry(
				layout.getGroupId(), fragmentKey);

		if (fragmentEntry == null) {
			fragmentEntry =
				_fragmentCollectionContributorTracker.getFragmentEntry(
					fragmentKey);
		}

		return fragmentEntry;
	}

	private void _processMapping(
		JSONObject jsonObject, Map<String, String> map) {

		if (map != null) {
			String fieldKey = map.get("fieldKey");

			if (Validator.isNull(fieldKey)) {
				return;
			}

			String itemKey = map.get("itemKey");

			if (Validator.isNull(itemKey)) {
				jsonObject.put("mappedField", fieldKey);

				return;
			}

			String[] itemKeyParts = itemKey.split(StringPool.POUND);

			if (itemKeyParts.length == 2) {
				jsonObject.put(
					"classNameId", itemKeyParts[0]
				).put(
					"classPK", itemKeyParts[1]
				).put(
					"fieldId", fieldKey
				);
			}
		}
	}

	private String _replaceResources(
			FragmentCollection fragmentCollection, String html)
		throws PortalException {

		if (fragmentCollection == null) {
			return html;
		}

		Matcher matcher = _pattern.matcher(html);

		while (matcher.find()) {
			FileEntry fileEntry = _portletFileRepository.fetchPortletFileEntry(
				fragmentCollection.getGroupId(),
				fragmentCollection.getResourcesFolderId(), matcher.group(1));

			String fileEntryURL = StringPool.BLANK;

			if (fileEntry != null) {
				fileEntryURL = DLURLHelperUtil.getDownloadURL(
					fileEntry, fileEntry.getFileVersion(), null,
					StringPool.BLANK, false, false);
			}

			html = StringUtil.replace(html, matcher.group(), fileEntryURL);
		}

		return html;
	}

	private JSONObject _toEditableFragmentEntryProcessorJSONObject(
		Map<String, String> editableTypes, List<Object> fragmentFields) {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		if (fragmentFields == null) {
			return jsonObject;
		}

		for (Object fragmentField : fragmentFields) {
			JSONObject fragmentFieldJSONObject =
				JSONFactoryUtil.createJSONObject();

			Map<String, Object> fragmentFieldMap =
				(Map<String, Object>)fragmentField;

			String fragmentFieldId = (String)fragmentFieldMap.get("id");

			if (Validator.isNull(fragmentFieldId)) {
				continue;
			}

			Map<String, Object> valueMap =
				(Map<String, Object>)fragmentFieldMap.get("value");

			if (valueMap == null) {
				continue;
			}

			JSONObject editableFieldConfigJSONObject =
				_createFragmentLinkConfigJSONObject(
					(Map<String, Object>)valueMap.get("fragmentLink"));

			JSONObject baseFragmentFieldJSONObject =
				_createBaseFragmentFieldJSONObject(
					(Map<String, Object>)valueMap.get("text"));

			if (Objects.equals(editableTypes.get(fragmentFieldId), "html")) {
				baseFragmentFieldJSONObject =
					_createBaseFragmentFieldJSONObject(
						(Map<String, Object>)valueMap.get("html"));
			}

			if (Objects.equals(editableTypes.get(fragmentFieldId), "image")) {
				Map<String, Object> fragmentImageMap =
					(Map<String, Object>)valueMap.get("fragmentImage");

				baseFragmentFieldJSONObject =
					JSONFactoryUtil.createJSONObject();

				if (fragmentImageMap != null) {
					baseFragmentFieldJSONObject =
						_createBaseFragmentFieldJSONObject(
							(Map<String, Object>)fragmentImageMap.get("url"));
				}

				try {
					editableFieldConfigJSONObject = JSONUtil.merge(
						editableFieldConfigJSONObject,
						_createImageConfigJSONObject(fragmentImageMap));
				}
				catch (JSONException jsonException) {
					if (_log.isWarnEnabled()) {
						_log.warn(jsonException, jsonException);
					}
				}
			}

			if (editableFieldConfigJSONObject.length() > 0) {
				fragmentFieldJSONObject.put(
					"config", editableFieldConfigJSONObject);
			}

			try {
				jsonObject.put(
					fragmentFieldId,
					JSONUtil.merge(
						fragmentFieldJSONObject, baseFragmentFieldJSONObject));
			}
			catch (JSONException jsonException) {
				if (_log.isWarnEnabled()) {
					_log.warn(jsonException, jsonException);
				}
			}
		}

		return jsonObject;
	}

	private JSONObject _toFreeMarkerFragmentEntryProcessorJSONObject(
		Map<String, String> configurationTypes,
		Map<String, Object> fragmentConfigMap) {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		if (fragmentConfigMap == null) {
			return jsonObject;
		}

		for (Map.Entry<String, Object> entry : fragmentConfigMap.entrySet()) {
			if (entry.getValue() instanceof String) {
				String type = configurationTypes.get(entry.getKey());

				if (Objects.equals(type, "colorPalette")) {
					jsonObject.put(
						entry.getKey(),
						JSONUtil.put("color", entry.getValue()));
				}
				else {
					jsonObject.put(entry.getKey(), entry.getValue());
				}
			}
			else if (entry.getValue() instanceof HashMap) {
				Map<String, Object> childFragmentConfigMap =
					(Map<String, Object>)entry.getValue();

				jsonObject.put(
					entry.getKey(),
					_toFreeMarkerFragmentEntryProcessorJSONObject(
						configurationTypes, childFragmentConfigMap));
			}
		}

		return jsonObject;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FragmentLayoutStructureItemImporter.class);

	private static final Pattern _pattern = Pattern.compile(
		"\\[resources:(.+?)\\]");

	@Reference
	private FragmentCollectionContributorTracker
		_fragmentCollectionContributorTracker;

	@Reference
	private FragmentCollectionService _fragmentCollectionService;

	@Reference
	private FragmentEntryLinkLocalService _fragmentEntryLinkLocalService;

	@Reference
	private FragmentEntryLocalService _fragmentEntryLocalService;

	@Reference
	private FragmentEntryProcessorRegistry _fragmentEntryProcessorRegistry;

	@Reference
	private FragmentEntryValidator _fragmentEntryValidator;

	@Reference
	private Portal _portal;

	@Reference
	private PortletFileRepository _portletFileRepository;

}