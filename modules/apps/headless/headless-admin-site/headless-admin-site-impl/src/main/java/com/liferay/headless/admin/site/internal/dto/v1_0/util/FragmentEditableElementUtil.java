/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.internal.dto.v1_0.util;

import com.liferay.fragment.entry.processor.constants.FragmentEntryProcessorConstants;
import com.liferay.fragment.entry.processor.util.EditableFragmentEntryProcessorUtil;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.headless.admin.site.dto.v1_0.FragmentEditableElement;
import com.liferay.headless.admin.site.dto.v1_0.FragmentEditableElementValue;
import com.liferay.headless.admin.site.dto.v1_0.FragmentEditableElementValueFragmentLink;
import com.liferay.headless.admin.site.dto.v1_0.FragmentInlineValue;
import com.liferay.headless.admin.site.dto.v1_0.FragmentLink;
import com.liferay.headless.admin.site.dto.v1_0.FragmentMappedValue;
import com.liferay.headless.admin.site.dto.v1_0.HTMLFragmentEditableElementValue;
import com.liferay.headless.admin.site.dto.v1_0.HTMLFragmentValue;
import com.liferay.headless.admin.site.dto.v1_0.HTMLInlineFragmentValue;
import com.liferay.headless.admin.site.dto.v1_0.HTMLMappedFragmentValue;
import com.liferay.headless.admin.site.dto.v1_0.TextFragmentEditableElementValue;
import com.liferay.headless.admin.site.dto.v1_0.TextFragmentValue;
import com.liferay.headless.admin.site.dto.v1_0.TextInlineFragmentValue;
import com.liferay.headless.admin.site.dto.v1_0.TextMappedFragmentValue;
import com.liferay.info.item.InfoItemServiceRegistry;
import com.liferay.petra.function.UnsafeSupplier;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author Rubén Pulido
 */
public class FragmentEditableElementUtil {

	public static JSONObject getEditableFragmentEntryProcessorJSONObject(
			long companyId, FragmentEditableElement[] fragmentEditableElements,
			InfoItemServiceRegistry infoItemServiceRegistry, long scopeGroupId)
		throws Exception {

		JSONObject editableFragmentEntryProcessorJSONObject =
			_getEditableFragmentEntryProcessorJSONObject(
				companyId, fragmentEditableElements, infoItemServiceRegistry,
				scopeGroupId);

		if (editableFragmentEntryProcessorJSONObject.length() > 0) {
			return editableFragmentEntryProcessorJSONObject;
		}

		return null;
	}

	public static FragmentEditableElement[] getFragmentEditableElements(
		long companyId, FragmentEntryLink fragmentEntryLink,
		InfoItemServiceRegistry infoItemServiceRegistry, long scopeGroupId) {

		JSONObject editableValuesJSONObject =
			fragmentEntryLink.getEditableValuesJSONObject();

		if (editableValuesJSONObject == null) {
			return null;
		}

		List<FragmentEditableElement> fragmentEditableElements =
			new ArrayList<>();

		JSONObject editableFragmentEntryProcessorJSONObject =
			editableValuesJSONObject.getJSONObject(
				FragmentEntryProcessorConstants.
					KEY_EDITABLE_FRAGMENT_ENTRY_PROCESSOR);

		if (editableFragmentEntryProcessorJSONObject == null) {
			return fragmentEditableElements.toArray(
				new FragmentEditableElement[0]);
		}

		fragmentEditableElements.addAll(
			_getFragmentEditableElements(
				companyId,
				EditableFragmentEntryProcessorUtil.getEditableTypes(
					fragmentEntryLink.getHtml()),
				infoItemServiceRegistry,
				editableFragmentEntryProcessorJSONObject, scopeGroupId));

		return fragmentEditableElements.toArray(new FragmentEditableElement[0]);
	}

	private static JSONObject _getEditableFragmentEntryProcessorJSONObject(
		long companyId, FragmentEditableElement[] fragmentEditableElements,
		InfoItemServiceRegistry infoItemServiceRegistry, long scopeGroupId) {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		if (fragmentEditableElements == null) {
			return jsonObject;
		}

		for (FragmentEditableElement fragmentEditableElement :
				fragmentEditableElements) {

			if ((fragmentEditableElement == null) ||
				(fragmentEditableElement.getId() == null)) {

				continue;
			}

			FragmentEditableElementValue fragmentEditableElementValue =
				fragmentEditableElement.getFragmentEditableElementValue();

			if (fragmentEditableElementValue == null) {
				continue;
			}

			if (Objects.equals(
					fragmentEditableElementValue.getType(),
					FragmentEditableElementValue.Type.HTML)) {

				jsonObject.put(
					fragmentEditableElement.getId(),
					() -> _getJSONObject(
						() -> _getHTMLFragmentEditableElementJSONObject(
							companyId,
							(HTMLFragmentEditableElementValue)
								fragmentEditableElementValue,
							infoItemServiceRegistry, scopeGroupId)));

				continue;
			}

			if (Objects.equals(
					fragmentEditableElementValue.getType(),
					FragmentEditableElementValue.Type.TEXT)) {

				jsonObject.put(
					fragmentEditableElement.getId(),
					() -> _getJSONObject(
						() -> _getTextFragmentEditableElementJSONObject(
							companyId, infoItemServiceRegistry, scopeGroupId,
							(TextFragmentEditableElementValue)
								fragmentEditableElementValue)));
			}
		}

		return jsonObject;
	}

	private static FragmentEditableElementValue
		_getFragmentEditableElementValue(
			long companyId, InfoItemServiceRegistry infoItemServiceRegistry,
			JSONObject jsonObject, long scopeGroupId, String type) {

		if (Objects.equals(type, "text")) {
			return _toTextFragmentEditableElementValue(
				companyId, infoItemServiceRegistry, jsonObject, scopeGroupId);
		}

		return null;
	}

	private static JSONObject _getFragmentInlineValueJSONObject(
		FragmentInlineValue fragmentInlineValue) {

		if (fragmentInlineValue == null) {
			return JSONFactoryUtil.createJSONObject();
		}

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		Map<String, String> languageIdMap = LocalizedMapUtil.getLanguageIdMap(
			LocalizedMapUtil.getLocalizedMap(
				fragmentInlineValue.getValue_i18n()));

		for (Map.Entry<String, String> entry : languageIdMap.entrySet()) {
			jsonObject.put(entry.getKey(), entry.getValue());
		}

		return jsonObject;
	}

	private static JSONObject _getFragmentMappedValueJSONObject(
			long companyId, FragmentMappedValue fragmentMappedValue,
			InfoItemServiceRegistry infoItemServiceRegistry, long scopeGroupId)
		throws Exception {

		if (fragmentMappedValue == null) {
			return JSONFactoryUtil.createJSONObject();
		}

		return FragmentMappingUtil.getFragmentMappedValueJSONObject(
			companyId, infoItemServiceRegistry,
			fragmentMappedValue.getMapping(), scopeGroupId);
	}

	private static JSONObject _getHTMLFragmentEditableElementJSONObject(
			long companyId,
			HTMLFragmentEditableElementValue htmlFragmentEditableElementValue,
			InfoItemServiceRegistry infoItemServiceRegistry, long scopeGroupId)
		throws Exception {

		HTMLFragmentValue htmlFragmentValue =
			htmlFragmentEditableElementValue.getHtmlFragmentValue();

		if (htmlFragmentValue == null) {
			return null;
		}

		if (htmlFragmentValue instanceof HTMLInlineFragmentValue) {
			HTMLInlineFragmentValue htmlInlineFragmentValue =
				(HTMLInlineFragmentValue)htmlFragmentValue;

			return _getFragmentInlineValueJSONObject(
				htmlInlineFragmentValue.getFragmentInlineValue());
		}

		if (!(htmlFragmentValue instanceof HTMLMappedFragmentValue)) {
			return null;
		}

		HTMLMappedFragmentValue htmlMappedFragmentValue =
			(HTMLMappedFragmentValue)htmlFragmentValue;

		return _getFragmentMappedValueJSONObject(
			companyId, htmlMappedFragmentValue.getFragmentMappedValue(),
			infoItemServiceRegistry, scopeGroupId);
	}

	private static JSONObject _getJSONObject(
			UnsafeSupplier<JSONObject, Exception> unsafeSupplier)
		throws Exception {

		JSONObject jsonObject = unsafeSupplier.get();

		if (JSONUtil.isEmpty(jsonObject)) {
			return null;
		}

		return jsonObject;
	}

	private static JSONObject _getTextFragmentEditableElementJSONObject(
			long companyId, InfoItemServiceRegistry infoItemServiceRegistry,
			long scopeGroupId,
			TextFragmentEditableElementValue textFragmentEditableElementValue)
		throws Exception {

		JSONObject jsonObject = JSONUtil.put(
			"config",
			() -> {
				FragmentEditableElementValueFragmentLink
					fragmentEditableElementValueFragmentLink =
						textFragmentEditableElementValue.
							getFragmentEditableElementValueFragmentLink();

				if (fragmentEditableElementValueFragmentLink == null) {
					return null;
				}

				JSONObject configJSONObject = FragmentLinkUtil.toJSONObject(
					companyId,
					fragmentEditableElementValueFragmentLink.getFragmentLink(),
					infoItemServiceRegistry, scopeGroupId);

				if (JSONUtil.isEmpty(configJSONObject)) {
					return null;
				}

				configJSONObject.put("mapperType", "link");

				FragmentEditableElementValueFragmentLink.Prefix prefix =
					fragmentEditableElementValueFragmentLink.getPrefix();

				if ((prefix == null) ||
					Objects.equals(
						prefix,
						FragmentEditableElementValueFragmentLink.Prefix.NONE)) {

					return configJSONObject;
				}

				if (Objects.equals(
						prefix,
						FragmentEditableElementValueFragmentLink.Prefix.
							EMAIL)) {

					return configJSONObject.put("prefix", "mailto:");
				}

				return configJSONObject.put("prefix", "tel:");
			});

		TextFragmentValue textFragmentValue =
			textFragmentEditableElementValue.getTextFragmentValue();

		if (textFragmentValue == null) {
			return jsonObject;
		}

		if (textFragmentValue instanceof TextInlineFragmentValue) {
			TextInlineFragmentValue textInlineFragmentValue =
				(TextInlineFragmentValue)textFragmentValue;

			return JSONUtil.merge(
				_getFragmentInlineValueJSONObject(
					textInlineFragmentValue.getFragmentInlineValue()),
				jsonObject);
		}

		if (!(textFragmentValue instanceof TextMappedFragmentValue)) {
			return jsonObject;
		}

		TextMappedFragmentValue textMappedFragmentValue =
			(TextMappedFragmentValue)textFragmentValue;

		return JSONUtil.merge(
			_getFragmentMappedValueJSONObject(
				companyId, textMappedFragmentValue.getFragmentMappedValue(),
				infoItemServiceRegistry, scopeGroupId),
			jsonObject);
	}

	private static List<FragmentEditableElement> _getFragmentEditableElements(
			long companyId, Map<String, String> editableTypes,
			InfoItemServiceRegistry infoItemServiceRegistry,
			JSONObject jsonObject, long scopeGroupId) {

		return TransformUtil.transform(
			jsonObject.keySet(),
			fieldId -> {
				FragmentEditableElementValue fragmentEditableElementValue =
					_getFragmentEditableElementValue(
						companyId, infoItemServiceRegistry,
						jsonObject.getJSONObject(fieldId), scopeGroupId,
						editableTypes.getOrDefault(fieldId, "text"));

				if (fragmentEditableElementValue == null) {
					return null;
				}

				FragmentEditableElement fragmentEditableElement =
					new FragmentEditableElement();

				fragmentEditableElement.setFragmentEditableElementValue(
					() -> fragmentEditableElementValue);
				fragmentEditableElement.setId(() -> fieldId);

				return fragmentEditableElement;
			});
	}

	private static FragmentEditableElementValueFragmentLink
		_toFragmentEditableElementValueFragmentLink(
			long companyId, InfoItemServiceRegistry infoItemServiceRegistry,
			JSONObject jsonObject, long scopeGroupId) {

		FragmentLink fragmentLink = FragmentLinkUtil.toFragmentLink(
			companyId, infoItemServiceRegistry, jsonObject, scopeGroupId);

		if (fragmentLink == null) {
			return null;
		}

		FragmentEditableElementValueFragmentLink
			fragmentEditableElementValueFragmentLink =
				new FragmentEditableElementValueFragmentLink();

		fragmentEditableElementValueFragmentLink.setFragmentLink(
			() -> fragmentLink);
		fragmentEditableElementValueFragmentLink.setPrefix(
			() -> {
				if (!jsonObject.has("prefix")) {
					return null;
				}

				String prefix = jsonObject.getString("prefix");

				if (Validator.isNull(prefix)) {
					return FragmentEditableElementValueFragmentLink.Prefix.NONE;
				}

				if (prefix.equals("mailto:")) {
					return FragmentEditableElementValueFragmentLink.Prefix.
						EMAIL;
				}

				if (prefix.equals("tel:")) {
					return FragmentEditableElementValueFragmentLink.Prefix.
						PHONE;
				}

				return null;
			});

		return fragmentEditableElementValueFragmentLink;
	}

	private static TextFragmentEditableElementValue
		_toTextFragmentEditableElementValue(
			long companyId, InfoItemServiceRegistry infoItemServiceRegistry,
			JSONObject jsonObject, long scopeGroupId) {

		if (jsonObject == null) {
			return null;
		}

		FragmentEditableElementValueFragmentLink
			fragmentEditableElementValueFragmentLink =
				_toFragmentEditableElementValueFragmentLink(
					companyId, infoItemServiceRegistry,
					jsonObject.getJSONObject("config"), scopeGroupId);

		TextFragmentValue textFragmentValue = _toTextFragmentValue(
			companyId, infoItemServiceRegistry, jsonObject, scopeGroupId);

		if ((fragmentEditableElementValueFragmentLink == null) &&
			(textFragmentValue == null)) {

			return null;
		}

		TextFragmentEditableElementValue textFragmentEditableElementValue =
			new TextFragmentEditableElementValue();

		textFragmentEditableElementValue.
			setFragmentEditableElementValueFragmentLink(
				() -> fragmentEditableElementValueFragmentLink);
		textFragmentEditableElementValue.setTextFragmentValue(
			() -> textFragmentValue);
		textFragmentEditableElementValue.setType(
			() -> FragmentEditableElementValue.Type.TEXT);

		return textFragmentEditableElementValue;
	}

	private static TextFragmentValue _toTextFragmentValue(
		long companyId, InfoItemServiceRegistry infoItemServiceRegistry,
		JSONObject jsonObject, long scopeGroupId) {

		if (jsonObject == null) {
			return null;
		}

		if (FragmentMappingUtil.isMappedValue(jsonObject)) {
			TextMappedFragmentValue textMappedFragmentValue =
				new TextMappedFragmentValue();

			textMappedFragmentValue.setFragmentMappedValue(
				() -> FragmentMappingUtil.toFragmentMappedValue(
					companyId, infoItemServiceRegistry, jsonObject,
					scopeGroupId));
			textMappedFragmentValue.setType(
				() -> TextFragmentValue.Type.MAPPED);

			return textMappedFragmentValue;
		}

		Map<String, String> i18nMap = LocalizedMapUtil.getI18nMap(
			true,
			LocalizedMapUtil.populateLocalizedMap(
				JSONUtil.toStringMap(jsonObject)));

		if (MapUtil.isEmpty(i18nMap)) {
			return null;
		}

		TextInlineFragmentValue textInlineFragmentValue =
			new TextInlineFragmentValue();

		textInlineFragmentValue.setFragmentInlineValue(
			() -> {
				FragmentInlineValue fragmentInlineValue =
					new FragmentInlineValue();

				fragmentInlineValue.setValue_i18n(() -> i18nMap);

				return fragmentInlineValue;
			});
		textInlineFragmentValue.setType(() -> TextFragmentValue.Type.INLINE);

		return textInlineFragmentValue;
	}

}