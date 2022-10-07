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

package com.liferay.dynamic.data.mapping.form.field.type.internal.document.library;

import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldValueRenderer;
import com.liferay.dynamic.data.mapping.form.field.type.constants.DDMFormFieldTypeConstants;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.util.Validator;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pedro Queiroz
 */
@Component(
	property = "ddm.form.field.type.name=" + DDMFormFieldTypeConstants.DOCUMENT_LIBRARY,
	service = DDMFormFieldValueRenderer.class
)
public class DocumentLibraryDDMFormFieldValueRenderer
	implements DDMFormFieldValueRenderer {

	@Override
	public String render(DDMFormFieldValue ddmFormFieldValue, Locale locale) {
		JSONObject jsonObject =
			documentLibraryDDMFormFieldValueAccessor.getValue(
				ddmFormFieldValue, locale);

		String uuid = jsonObject.getString("uuid");
		long groupId = jsonObject.getLong("groupId");

		if (Validator.isNull(uuid) || (groupId == 0)) {
			return StringPool.BLANK;
		}

		try {
			FileEntry fileEntry = dlAppService.getFileEntryByUuidAndGroupId(
				uuid, groupId);

			return fileEntry.getTitle();
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}

			return _language.format(
				locale, "is-temporarily-unavailable", "content");
		}
	}

	@Reference
	protected DLAppService dlAppService;

	@Reference
	protected DocumentLibraryDDMFormFieldValueAccessor
		documentLibraryDDMFormFieldValueAccessor;

	private static final Log _log = LogFactoryUtil.getLog(
		DocumentLibraryDDMFormFieldValueRenderer.class);

	@Reference
	private Language _language;

}