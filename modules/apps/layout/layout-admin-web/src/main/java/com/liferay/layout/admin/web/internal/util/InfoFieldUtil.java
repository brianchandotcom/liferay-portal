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

package com.liferay.layout.admin.web.internal.util;

import com.liferay.fragment.entry.processor.util.EditableFragmentEntryProcessorUtil;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.service.FragmentEntryLinkLocalServiceUtil;
import com.liferay.info.field.InfoField;
import com.liferay.info.field.type.TextInfoFieldType;
import com.liferay.info.localized.InfoLocalizedValue;
import com.liferay.petra.function.UnsafeSupplier;
import com.liferay.petra.function.UnsafeTriConsumer;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Layout;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Adolfo Pérez
 */
public class InfoFieldUtil {

	public static <E extends Throwable> void forEachInfoField(
			Layout layout,
			UnsafeTriConsumer
				<String, InfoField<TextInfoFieldType>,
				 UnsafeSupplier<JSONObject, JSONException>, E> consumer)
		throws E {

		if (!layout.isTypeContent()) {
			return;
		}

		List<FragmentEntryLink> fragmentEntryLinks =
			FragmentEntryLinkLocalServiceUtil.getFragmentEntryLinksByPlid(
				layout.getGroupId(), layout.getPlid());

		for (FragmentEntryLink fragmentEntryLink : fragmentEntryLinks) {
			Map<String, String> editableTypes =
				EditableFragmentEntryProcessorUtil.getEditableTypes(
					_getHtml(fragmentEntryLink));

			for (Map.Entry<String, String> entry : editableTypes.entrySet()) {
				String type = entry.getValue();

				if (_isTextFieldType(type)) {
					String name = entry.getKey();

					consumer.accept(
						name,
						_getInfoField(
							fragmentEntryLink.getFragmentEntryLinkId(), name,
							type),
						() -> JSONFactoryUtil.createJSONObject(
							fragmentEntryLink.getEditableValues()));
				}
			}
		}
	}

	private static String _getHtml(FragmentEntryLink fragmentEntryLink) {
		Matcher matcher = _pattern.matcher(fragmentEntryLink.getHtml());

		return matcher.replaceAll("element");
	}

	private static InfoField<TextInfoFieldType> _getInfoField(
		long fragmentEntryLinkId, String name, String type) {

		return InfoField.builder(
		).infoFieldType(
			TextInfoFieldType.INSTANCE
		).name(
			fragmentEntryLinkId + StringPool.COLON + name
		).labelInfoLocalizedValue(
			InfoLocalizedValue.singleValue(name)
		).localizable(
			true
		).attribute(
			TextInfoFieldType.HTML, _isHtml(type)
		).build();
	}

	private static boolean _isHtml(String type) {
		if (type.equals("rich-text")) {
			return true;
		}

		return false;
	}

	private static boolean _isTextFieldType(String type) {
		if (type.equals("link") || type.equals("rich-text") ||
			type.equals("text")) {

			return true;
		}

		return false;
	}

	private static final Pattern _pattern = Pattern.compile("\\$\\{[^}]*\\}");

}