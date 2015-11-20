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

package com.liferay.dynamic.data.mapping.util;

import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.TempFileEntryUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.documentlibrary.service.DLAppLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLAppServiceUtil;
import com.liferay.portlet.documentlibrary.util.DLUtil;

import java.util.Locale;

/**
 * @author Marcellus Tavares
 */
public class DocumentLibraryDDMFormFieldValueTransformer
	implements DDMFormFieldValueTransformer {

	@Override
	public String getFieldType() {
		return "ddm-documentlibrary";
	}

	@Override
	public void transform(DDMFormFieldValue ddmFormFieldValue)
		throws PortalException {

		Value value = ddmFormFieldValue.getValue();

		for (Locale locale : value.getAvailableLocales()) {
			FileEntry tempFileEntry = fetchTempFileEntry(
				value.getString(locale));

			if (tempFileEntry == null) {
				continue;
			}

			FileEntry fileEntry = addFileEntry(tempFileEntry);

			value.addString(locale, toJSON(fileEntry));
		}
	}

	protected FileEntry addFileEntry(FileEntry tempFileEntry)
		throws PortalException {

		String originalTempFileName = TempFileEntryUtil.getOriginalTempFileName(
			tempFileEntry.getFileName());

		String fileName = DLUtil.getUniqueFileName(
			tempFileEntry.getGroupId(), tempFileEntry.getFolderId(),
			originalTempFileName);

		return DLAppServiceUtil.addFileEntry(
			tempFileEntry.getGroupId(), 0, fileName,
			tempFileEntry.getMimeType(), fileName, StringPool.BLANK,
			StringPool.BLANK, tempFileEntry.getContentStream(),
			tempFileEntry.getSize(), new ServiceContext());
	}

	protected FileEntry fetchTempFileEntry(String value)
		throws PortalException {

		if (Validator.isNull(value)) {
			return null;
		}

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(value);

		boolean tempFile = jsonObject.getBoolean("tempFile");

		if (tempFile == false) {
			return null;
		}

		return DLAppLocalServiceUtil.getFileEntryByUuidAndGroupId(
			jsonObject.getString("uuid"), jsonObject.getLong("groupId"));
	}

	protected String toJSON(FileEntry fileEntry) {
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		jsonObject.put("groupId", fileEntry.getGroupId());
		jsonObject.put("title", fileEntry.getTitle());
		jsonObject.put("uuid", fileEntry.getUuid());

		return jsonObject.toString();
	}

}