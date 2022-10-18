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

package com.liferay.fragment.entry.processor.editable;

import com.liferay.fragment.constants.FragmentEntryLinkConstants;
import com.liferay.fragment.entry.processor.editable.mapper.EditableElementMapper;
import com.liferay.fragment.entry.processor.editable.parser.EditableElementParser;
import com.liferay.fragment.entry.processor.helper.FragmentEntryProcessorHelper;
import com.liferay.fragment.entry.processor.util.EditableFragmentEntryProcessorUtil;
import com.liferay.fragment.exception.FragmentEntryContentException;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.processor.FragmentEntryProcessor;
import com.liferay.fragment.processor.FragmentEntryProcessorContext;
import com.liferay.fragment.processor.PortletRegistry;
import com.liferay.info.constants.InfoDisplayWebKeys;
import com.liferay.info.item.InfoItemFieldValues;
import com.liferay.info.item.provider.InfoItemFieldValuesProvider;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pavel Savinov
 */
@Component(
	immediate = true, property = "fragment.entry.processor.priority:Integer=2",
	service = FragmentEntryProcessor.class
)
public class EditableFragmentEntryProcessor implements FragmentEntryProcessor {

	@Override
	public JSONArray getAvailableTagsJSONArray() {
		JSONArray jsonArray = _jsonFactory.createJSONArray();

		for (String key : _editableElementParserServiceTrackerMap.keySet()) {
			StringBundler sb = new StringBundler(
				2 + (5 * _REQUIRED_ATTRIBUTE_NAMES.length));

			sb.append("<lfr-editable");

			for (String attributeName : _REQUIRED_ATTRIBUTE_NAMES) {
				sb.append(StringPool.SPACE);
				sb.append(attributeName);
				sb.append("=\"");

				String value = StringPool.BLANK;

				if (attributeName.equals("type")) {
					value = key;
				}

				sb.append(value);
				sb.append("\"");
			}

			sb.append("></lfr-editable>");

			jsonArray.put(
				JSONUtil.put(
					"content", sb.toString()
				).put(
					"name", "lfr-editable:" + key
				));
		}

		return jsonArray;
	}

	@Override
	public JSONArray getDataAttributesJSONArray() {
		JSONArray jsonArray = JSONUtil.put("lfr-editable-id");

		for (String key : _editableElementParserServiceTrackerMap.keySet()) {
			jsonArray.put("lfr-editable-type:" + key);
		}

		return jsonArray;
	}

	@Override
	public JSONObject getDefaultEditableValuesJSONObject(
		String html, String configuration) {

		return _getDefaultEditableValuesJSONObject(html);
	}

	@Override
	public String processFragmentEntryLinkHTML(
			FragmentEntryLink fragmentEntryLink, String html,
			FragmentEntryProcessorContext fragmentEntryProcessorContext)
		throws PortalException {

		JSONObject jsonObject = _jsonFactory.createJSONObject(
			fragmentEntryLink.getEditableValues());

		if (jsonObject.length() == 0) {
			Class<?> clazz = getClass();

			jsonObject.put(
				clazz.getName(),
				getDefaultEditableValuesJSONObject(
					html, fragmentEntryLink.getConfiguration()));
		}

		Document document = _getDocument(html);

		Map<Long, InfoItemFieldValues> infoDisplaysFieldValues =
			new HashMap<>();

		for (Element element :
				document.select("lfr-editable,*[data-lfr-editable-id]")) {

			EditableElementParser editableElementParser =
				_getEditableElementParser(element);

			if (editableElementParser == null) {
				continue;
			}

			String id = EditableFragmentEntryProcessorUtil.getElementId(
				element);

			Class<?> clazz = getClass();

			JSONObject editableValuesJSONObject = jsonObject.getJSONObject(
				clazz.getName());

			if ((editableValuesJSONObject == null) ||
				!editableValuesJSONObject.has(id)) {

				continue;
			}

			JSONObject editableValueJSONObject =
				editableValuesJSONObject.getJSONObject(id);

			String value = StringPool.BLANK;

			JSONObject mappedValueConfigJSONObject =
				_jsonFactory.createJSONObject();

			if (_fragmentEntryProcessorHelper.isAssetDisplayPage(
					fragmentEntryProcessorContext.getMode()) &&
				editableValueJSONObject.has("mappedField")) {

				String mappedField = editableValueJSONObject.getString(
					"mappedField");

				if (Validator.isNotNull(mappedField)) {
					HttpServletRequest httpServletRequest =
						fragmentEntryProcessorContext.getHttpServletRequest();

					Object infoItem = httpServletRequest.getAttribute(
						InfoDisplayWebKeys.INFO_ITEM);

					InfoItemFieldValuesProvider<Object>
						infoItemFieldValuesProvider =
							(InfoItemFieldValuesProvider)
								httpServletRequest.getAttribute(
									InfoDisplayWebKeys.
										INFO_ITEM_FIELD_VALUES_PROVIDER);

					Object fieldValue =
						_fragmentEntryProcessorHelper.
							getMappedInfoItemFieldValue(
								mappedField, infoItemFieldValuesProvider,
								fragmentEntryProcessorContext.getLocale(),
								infoItem);

					if (fieldValue != null) {
						mappedValueConfigJSONObject =
							editableElementParser.
								getFieldTemplateConfigJSONObject(
									mappedField,
									fragmentEntryProcessorContext.getLocale(),
									fieldValue);

						value = editableElementParser.parseFieldValue(
							fieldValue);
					}
					else {
						value = editableValueJSONObject.getString(
							"defaultValue");
					}
				}
			}
			else if (_fragmentEntryProcessorHelper.isMapped(
						editableValueJSONObject)) {

				Object fieldValue =
					_fragmentEntryProcessorHelper.getMappedInfoItemFieldValue(
						editableValueJSONObject, infoDisplaysFieldValues,
						fragmentEntryProcessorContext.getLocale(),
						fragmentEntryProcessorContext.getMode(),
						fragmentEntryProcessorContext.getPreviewClassPK(),
						fragmentEntryProcessorContext.getPreviewVersion());

				if (fieldValue != null) {
					String fieldId = editableValueJSONObject.getString(
						"fieldId");

					mappedValueConfigJSONObject =
						editableElementParser.getFieldTemplateConfigJSONObject(
							fieldId, fragmentEntryProcessorContext.getLocale(),
							fieldValue);

					value = editableElementParser.parseFieldValue(fieldValue);
				}
				else {
					value = editableValueJSONObject.getString("defaultValue");
				}
			}
			else if (_fragmentEntryProcessorHelper.isMappedCollection(
						editableValueJSONObject)) {

				Object fieldValue =
					_fragmentEntryProcessorHelper.getMappedCollectionValue(
						fragmentEntryProcessorContext.
							getContextInfoItemReferenceOptional(),
						editableValueJSONObject,
						fragmentEntryProcessorContext.getLocale());

				if (fieldValue != null) {
					String fieldId = editableValueJSONObject.getString(
						"collectionFieldId");

					mappedValueConfigJSONObject =
						editableElementParser.getFieldTemplateConfigJSONObject(
							fieldId, fragmentEntryProcessorContext.getLocale(),
							fieldValue);

					value = editableElementParser.parseFieldValue(fieldValue);
				}
				else {
					value = editableValueJSONObject.getString("defaultValue");
				}
			}
			else {
				value = _fragmentEntryProcessorHelper.getEditableValue(
					editableValueJSONObject,
					fragmentEntryProcessorContext.getLocale());
			}

			JSONObject configJSONObject = JSONUtil.merge(
				editableValueJSONObject.getJSONObject("config"),
				mappedValueConfigJSONObject);

			JSONObject localizedJSONObject = configJSONObject.getJSONObject(
				LocaleUtil.toLanguageId(
					fragmentEntryProcessorContext.getLocale()));

			String mapperType = configJSONObject.getString(
				"mapperType", element.attr("type"));

			if ((localizedJSONObject != null) &&
				(localizedJSONObject.length() > 0)) {

				configJSONObject = localizedJSONObject;
			}

			editableElementParser.replace(element, value, configJSONObject);

			if (!Objects.equals(
					fragmentEntryProcessorContext.getMode(),
					FragmentEntryLinkConstants.EDIT)) {

				if (Validator.isNull(mapperType)) {
					mapperType = element.attr("data-lfr-editable-type");
				}

				EditableElementMapper editableElementMapper =
					_editableElementMapperServiceTrackerMap.getService(
						mapperType);

				if (editableElementMapper != null) {
					editableElementMapper.map(
						element, configJSONObject,
						fragmentEntryProcessorContext);
				}
			}
		}

		if (Objects.equals(
				fragmentEntryProcessorContext.getMode(),
				FragmentEntryLinkConstants.ASSET_DISPLAY_PAGE) ||
			Objects.equals(
				fragmentEntryProcessorContext.getMode(),
				FragmentEntryLinkConstants.VIEW)) {

			for (Element element : document.select("lfr-editable")) {
				element.removeAttr("id");
				element.removeAttr("type");

				String tagName = element.attr("view-tag-name");

				if (!Objects.equals(tagName, "span")) {
					tagName = "div";
				}

				element.tagName(tagName);

				element.removeAttr("view-tag-name");
			}
		}

		Element bodyElement = document.body();

		if (!infoDisplaysFieldValues.containsKey(
				fragmentEntryProcessorContext.getPreviewClassPK())) {

			return bodyElement.html();
		}

		Element previewElement = new Element("div");

		previewElement.attr("style", "border: 1px solid #0B5FFF");

		bodyElement = previewElement.html(bodyElement.html());

		return bodyElement.outerHtml();
	}

	@Override
	public void validateFragmentEntryHTML(String html, String configuration)
		throws PortalException {

		Document document = _getDocument(html);

		_validateAttributes(document);

		Elements elements = document.select(
			"lfr-editable,*[data-lfr-editable-id]");

		_validateDuplicatedIds(elements);

		_validateEditableElements(elements);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_editableElementParserServiceTrackerMap =
			ServiceTrackerMapFactory.openSingleValueMap(
				bundleContext, EditableElementParser.class, "type");

		_editableElementMapperServiceTrackerMap =
			ServiceTrackerMapFactory.openSingleValueMap(
				bundleContext, EditableElementMapper.class, "type");
	}

	@Deactivate
	protected void deactivate() {
		_editableElementParserServiceTrackerMap.close();
		_editableElementMapperServiceTrackerMap.close();
	}

	private JSONObject _getDefaultEditableValuesJSONObject(String html) {
		JSONObject defaultEditableValuesJSONObject =
			_jsonFactory.createJSONObject();

		Document document = _getDocument(html);

		for (Element element :
				document.select("lfr-editable,*[data-lfr-editable-id]")) {

			EditableElementParser editableElementParser =
				_getEditableElementParser(element);

			if (editableElementParser == null) {
				continue;
			}

			JSONObject defaultValueJSONObject = JSONUtil.put(
				"config", editableElementParser.getAttributes(element)
			).put(
				"defaultValue", editableElementParser.getValue(element)
			);

			defaultEditableValuesJSONObject.put(
				EditableFragmentEntryProcessorUtil.getElementId(element),
				defaultValueJSONObject);
		}

		return defaultEditableValuesJSONObject;
	}

	private Document _getDocument(String html) {
		Document document = Jsoup.parseBodyFragment(html);

		Document.OutputSettings outputSettings = new Document.OutputSettings();

		outputSettings.prettyPrint(false);

		document.outputSettings(outputSettings);

		return document;
	}

	private EditableElementParser _getEditableElementParser(Element element) {
		String type = EditableFragmentEntryProcessorUtil.getElementType(
			element);

		return _editableElementParserServiceTrackerMap.getService(type);
	}

	private boolean _hasNestedWidget(Element element) {
		List<String> portletAliases = _portletRegistry.getPortletAliases();

		for (String portletAlias : portletAliases) {
			Elements tagElements = element.select(
				"> lfr-widget-" + portletAlias);

			if (tagElements.size() > 0) {
				return true;
			}
		}

		return false;
	}

	private void _validateAttribute(Element element, String attributeName)
		throws FragmentEntryContentException {

		if (Validator.isNotNull(element.attr(attributeName))) {
			return;
		}

		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", getClass());

		throw new FragmentEntryContentException(
			_language.format(
				resourceBundle,
				"you-must-define-all-required-attributes-x-for-each-editable-" +
					"element",
				StringUtil.merge(_REQUIRED_ATTRIBUTE_NAMES)));
	}

	private void _validateAttributes(Document document)
		throws FragmentEntryContentException {

		for (Element element : document.getElementsByTag("lfr-editable")) {
			for (String attributeName : _REQUIRED_ATTRIBUTE_NAMES) {
				_validateAttribute(element, attributeName);
			}

			_validateType(element);
		}

		for (Element element :
				document.select(
					"*[data-lfr-editable-id],*[data-lfr-editable-type]")) {

			_validateAttribute(element, "data-lfr-editable-id");
			_validateAttribute(element, "data-lfr-editable-type");

			_validateType(element);
		}
	}

	private void _validateDuplicatedIds(Elements elements)
		throws FragmentEntryContentException {

		Set<String> ids = new HashSet<>();

		for (Element element : elements) {
			if (ids.add(
					EditableFragmentEntryProcessorUtil.getElementId(element))) {

				continue;
			}

			ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
				"content.Language", getClass());

			throw new FragmentEntryContentException(
				_language.get(
					resourceBundle,
					"you-must-define-a-unique-id-for-each-editable-element"));
		}
	}

	private void _validateEditableElements(Elements elements)
		throws FragmentEntryContentException {

		for (Element element : elements) {
			EditableElementParser editableElementParser =
				_getEditableElementParser(element);

			if (editableElementParser == null) {
				continue;
			}

			_validateNestedEditableElements(element);

			editableElementParser.validate(element);
		}
	}

	private void _validateNestedEditableElements(Element element)
		throws FragmentEntryContentException {

		Elements attributeElements = element.getElementsByAttribute(
			"[data-lfr-editable-id]");

		Elements dropZoneElements = element.select("> lfr-drop-zone");

		Elements tagElements = element.select("> lfr-editable");

		if ((attributeElements.size() > 0) || (dropZoneElements.size() > 0) ||
			_hasNestedWidget(element) || (tagElements.size() > 0)) {

			ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
				"content.Language", getClass());

			throw new FragmentEntryContentException(
				_language.get(
					resourceBundle,
					"editable-fields-cannot-include-nested-editables-drop-" +
						"zones-or-widgets-in-it"));
		}
	}

	private void _validateType(Element element)
		throws FragmentEntryContentException {

		EditableElementParser editableElementParser = _getEditableElementParser(
			element);

		if (editableElementParser != null) {
			return;
		}

		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", getClass());

		throw new FragmentEntryContentException(
			_language.get(
				resourceBundle,
				"you-must-define-a-valid-type-for-each-editable-element"));
	}

	private static final String[] _REQUIRED_ATTRIBUTE_NAMES = {"id", "type"};

	private ServiceTrackerMap<String, EditableElementMapper>
		_editableElementMapperServiceTrackerMap;
	private ServiceTrackerMap<String, EditableElementParser>
		_editableElementParserServiceTrackerMap;

	@Reference
	private FragmentEntryProcessorHelper _fragmentEntryProcessorHelper;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private Language _language;

	@Reference
	private PortletRegistry _portletRegistry;

}