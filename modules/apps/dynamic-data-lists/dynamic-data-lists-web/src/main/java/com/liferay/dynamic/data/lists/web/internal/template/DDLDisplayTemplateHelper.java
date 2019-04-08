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

package com.liferay.dynamic.data.lists.web.internal.template;

import com.liferay.document.library.kernel.service.DLAppLocalServiceUtil;
import com.liferay.document.library.util.DLURLHelper;
import com.liferay.dynamic.data.lists.model.DDLRecord;
import com.liferay.dynamic.data.lists.service.DDLRecordLocalServiceUtil;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.render.DDMFormFieldValueRenderer;
import com.liferay.dynamic.data.mapping.render.DDMFormFieldValueRendererRegistryUtil;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.List;
import java.util.Locale;

/**
 * @author Leonardo Barros
 */
public class DDLDisplayTemplateHelper {

	public DDLDisplayTemplateHelper(DLURLHelper dlURLHelper) {
		_dlURLHelper = dlURLHelper;
	}

	public String getDocumentLibraryPreviewURL(
			DDMFormFieldValue recordFieldValue, Locale locale)
		throws PortalException {

		Value value = recordFieldValue.getValue();

		String valueString = value.getString(locale);

		if (Validator.isNull(valueString)) {
			return StringPool.BLANK;
		}

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(valueString);

		String uuid = jsonObject.getString("uuid");
		long groupId = jsonObject.getLong("groupId");

		FileEntry fileEntry =
			DLAppLocalServiceUtil.getFileEntryByUuidAndGroupId(uuid, groupId);

		return _dlURLHelper.getPreviewURL(
			fileEntry, fileEntry.getFileVersion(), null, StringPool.BLANK,
			false, true);
	}

	public String getHTMLContent(
		DDMFormFieldValue recordFieldValue, Locale locale) {

		Value value = recordFieldValue.getValue();

		String valueString = value.getString(locale);

		if (Validator.isNull(valueString)) {
			return StringPool.BLANK;
		}

		return HtmlUtil.escapeJS(valueString);
	}

	public String getLayoutFriendlyURL(
			DDMFormFieldValue recordFieldValue, ThemeDisplay themeDisplay)
		throws PortalException {

		Value value = recordFieldValue.getValue();

		String valueString = value.getString(themeDisplay.getLocale());

		if (Validator.isNull(valueString)) {
			return StringPool.BLANK;
		}

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(valueString);

		long groupId = jsonObject.getLong("groupId");
		boolean privateLayout = jsonObject.getBoolean("privateLayout");
		long layoutId = jsonObject.getLong("layoutId");

		Layout layout = LayoutLocalServiceUtil.getLayout(
			groupId, privateLayout, layoutId);

		return PortalUtil.getLayoutFriendlyURL(layout, themeDisplay);
	}

	public List<DDLRecord> getRecords(long recordSetId) throws PortalException {
		return DDLRecordLocalServiceUtil.getRecords(recordSetId);
	}

	public List<DDLRecord> getRecords(
			long recordSetId, int status, int start, int end,
			OrderByComparator<DDLRecord> orderByComparator)
		throws PortalException {

		return DDLRecordLocalServiceUtil.getRecords(
			recordSetId, status, start, end, orderByComparator);
	}

	public String renderRecordFieldValue(
			DDMFormFieldValue recordFieldValue, Locale locale)
		throws PortalException {

		DDMFormFieldValueRenderer ddmFormFieldValueRenderer =
			DDMFormFieldValueRendererRegistryUtil.getDDMFormFieldValueRenderer(
				recordFieldValue.getType());

		return ddmFormFieldValueRenderer.render(recordFieldValue, locale);
	}

	private final DLURLHelper _dlURLHelper;

}