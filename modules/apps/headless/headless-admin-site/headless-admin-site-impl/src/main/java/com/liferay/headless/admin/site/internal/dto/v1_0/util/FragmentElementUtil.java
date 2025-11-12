/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.internal.dto.v1_0.util;

import com.liferay.fragment.entry.processor.constants.FragmentEntryProcessorConstants;
import com.liferay.fragment.entry.processor.util.EditableFragmentEntryProcessorUtil;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.headless.admin.site.dto.v1_0.FragmentElement;
import com.liferay.headless.admin.site.dto.v1_0.FragmentInlineValue;
import com.liferay.headless.admin.site.dto.v1_0.TextFragmentElementValue;
import com.liferay.headless.admin.site.dto.v1_0.TextFragmentValue;
import com.liferay.headless.admin.site.dto.v1_0.TextInlineFragmentValue;
import com.liferay.headless.admin.site.dto.v1_0.TextMappedFragmentValue;
import com.liferay.info.item.InfoItemServiceRegistry;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author Rubén Pulido
 */
public class FragmentElementUtil {

	public static FragmentElement[] getFragmentElements(
		long companyId, FragmentEntryLink fragmentEntryLink,
		InfoItemServiceRegistry infoItemServiceRegistry, long scopeGroupId) {

		JSONObject editableValuesJSONObject =
			fragmentEntryLink.getEditableValuesJSONObject();

		if (editableValuesJSONObject == null) {
			return null;
		}

		List<FragmentElement> fragmentElements = new ArrayList<>();

		JSONObject editableFragmentEntryProcessorJSONObject =
			editableValuesJSONObject.getJSONObject(
				FragmentEntryProcessorConstants.
					KEY_EDITABLE_FRAGMENT_ENTRY_PROCESSOR);

		if (editableFragmentEntryProcessorJSONObject == null) {
			return fragmentElements.toArray(new FragmentElement[0]);
		}

		fragmentElements.addAll(
			_getTextFragmentElements(
				companyId,
				EditableFragmentEntryProcessorUtil.getEditableTypes(
					fragmentEntryLink.getHtml()),
				infoItemServiceRegistry,
				editableFragmentEntryProcessorJSONObject, scopeGroupId));

		return fragmentElements.toArray(new FragmentElement[0]);
	}

	private static List<FragmentElement> _getTextFragmentElements(
		long companyId, Map<String, String> editableTypes,
		final InfoItemServiceRegistry infoItemServiceRegistry,
		JSONObject jsonObject, long scopeGroupId) {

		return TransformUtil.transform(
			jsonObject.keySet(),
			textId -> new FragmentElement() {
				{
					setFragmentElementValue(
						() -> {
							String type = editableTypes.getOrDefault(
								textId, "text");

							if (!Objects.equals(type, "text")) {
								return null;
							}

							return FragmentElementUtil.
								_toTextFragmentElementValue(
									companyId, infoItemServiceRegistry,
									jsonObject.getJSONObject(textId),
									scopeGroupId);
						});
					setId(() -> textId);
				}
			});
	}

	private static TextFragmentElementValue _toTextFragmentElementValue(
		long companyId, InfoItemServiceRegistry infoItemServiceRegistry,
		JSONObject jsonObject, long scopeGroupId) {

		if (jsonObject == null) {
			return null;
		}

		TextFragmentElementValue textFragmentElementValue =
			new TextFragmentElementValue();

		textFragmentElementValue.setTextFragmentValue(
			() -> _toTextFragmentValue(
				companyId, infoItemServiceRegistry, jsonObject, scopeGroupId));

		return textFragmentElementValue;
	}

	private static TextFragmentValue _toTextFragmentValue(
		long companyId, InfoItemServiceRegistry infoItemServiceRegistry,
		JSONObject jsonObject, long scopeGroupId) {

		if (jsonObject == null) {
			return null;
		}

		boolean mappedValue = FragmentMappingUtil.isMappedValue(jsonObject);

		if (mappedValue) {
			TextMappedFragmentValue textMappedFragmentValue =
				new TextMappedFragmentValue();

			textMappedFragmentValue.setFragmentMappedValue(
				() -> FragmentMappingUtil.toFragmentMappedValue(
					companyId, infoItemServiceRegistry, jsonObject,
					scopeGroupId));

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

		return textInlineFragmentValue;
	}

}