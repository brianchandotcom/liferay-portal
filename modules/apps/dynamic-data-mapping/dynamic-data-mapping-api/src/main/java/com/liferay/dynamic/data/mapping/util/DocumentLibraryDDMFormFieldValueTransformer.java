/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.util;

import com.liferay.document.library.kernel.service.DLAppLocalServiceUtil;
import com.liferay.document.library.kernel.service.DLAppServiceUtil;
import com.liferay.document.library.kernel.util.DLUtil;
import com.liferay.dynamic.data.mapping.form.field.type.constants.DDMFormFieldTypeConstants;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.Validator;

import java.util.Locale;

/**
 * @author Marcellus Tavares
 */
public class DocumentLibraryDDMFormFieldValueTransformer
	implements DDMFormFieldValueTransformer {

	@Override
	public String getFieldType() {
		return DDMFormFieldTypeConstants.DOCUMENT_LIBRARY;
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

		String fileName = DLUtil.getUniqueFileName(
			tempFileEntry.getGroupId(), tempFileEntry.getFolderId(),
			tempFileEntry.getFileName(), false);

		return DLAppServiceUtil.addFileEntry(
			null, tempFileEntry.getGroupId(), 0, fileName,
			tempFileEntry.getMimeType(), fileName, StringPool.BLANK,
			StringPool.BLANK, StringPool.BLANK,
			tempFileEntry.getContentStream(), tempFileEntry.getSize(), null,
			null, new ServiceContext());
	}

	protected FileEntry fetchTempFileEntry(String value)
		throws PortalException {

		if (Validator.isNull(value)) {
			return null;
		}

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(value);

		boolean tempFile = jsonObject.getBoolean("tempFile");

		if (!tempFile) {
			return null;
		}

		return DLAppLocalServiceUtil.getFileEntryByUuidAndGroupId(
			jsonObject.getString("uuid"), jsonObject.getLong("groupId"));
	}

	protected String toJSON(FileEntry fileEntry) {
		return JSONUtil.put(
			"groupId", fileEntry.getGroupId()
		).put(
			"title", fileEntry.getTitle()
		).put(
			"uuid", fileEntry.getUuid()
		).toString();
	}

}