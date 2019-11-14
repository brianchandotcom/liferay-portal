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

package com.liferay.headless.form.internal.dto.v1_0.util;

import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.document.library.util.DLURLHelper;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecord;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.headless.form.dto.v1_0.FormDocument;
import com.liferay.headless.form.dto.v1_0.FormFieldValue;
import com.liferay.headless.form.dto.v1_0.FormRecord;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.vulcan.util.TransformUtil;

import java.util.Locale;

/**
 * @author Victor Oliveira
 */
public class FormRecordUtil {

	public static FormRecord toFormRecord(
			DDMFormInstanceRecord ddmFormInstanceRecord,
			DLAppService dlAppService, DLURLHelper dlurlHelper, Locale locale,
			Portal portal, UserLocalService userLocalService)
		throws Exception {

		DDMFormValues ddmFormValues = ddmFormInstanceRecord.getDDMFormValues();

		return new FormRecord() {
			{
				creator = CreatorUtil.toCreator(
					portal,
					userLocalService.getUser(
						ddmFormInstanceRecord.getUserId()));
				dateCreated = ddmFormInstanceRecord.getCreateDate();
				dateModified = ddmFormInstanceRecord.getModifiedDate();
				datePublished = ddmFormInstanceRecord.getLastPublishDate();
				draft =
					ddmFormInstanceRecord.getStatus() ==
						WorkflowConstants.STATUS_DRAFT;
				formFieldValues = TransformUtil.transformToArray(
					ddmFormValues.getDDMFormFieldValues(),
					ddmFormFieldValue -> {
						Value localizedValue = ddmFormFieldValue.getValue();

						if (localizedValue == null) {
							return null;
						}

						return new FormFieldValue() {
							{
								formDocument = _toFormDocument(
									dlAppService, dlurlHelper, locale,
									localizedValue);
								name = ddmFormFieldValue.getName();
								value = localizedValue.getString(locale);
							}
						};
					},
					FormFieldValue.class);
				id = ddmFormInstanceRecord.getFormInstanceRecordId();
			}
		};
	}

	private static FormDocument _toFormDocument(
			DLAppService dlAppService, DLURLHelper dlurlHelper, Locale locale,
			Value localizedValue)
		throws Exception {

		FileEntry fileEntry = null;

		try {
			JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
				localizedValue.getString(locale));

			long fileEntryId = jsonObject.getLong("fileEntryId", 0);

			if (fileEntryId > 0) {
				fileEntry = dlAppService.getFileEntry(fileEntryId);
			}
		}
		catch (JSONException jsone) {
			if (_log.isWarnEnabled()) {
				_log.warn(jsone, jsone);
			}
		}

		if (fileEntry == null) {
			return null;
		}

		return FormDocumentUtil.toFormDocument(dlurlHelper, fileEntry);
	}

	private static final Log _log = LogFactoryUtil.getLog(FormRecordUtil.class);

}