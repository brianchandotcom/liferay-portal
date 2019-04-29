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
import com.liferay.headless.form.dto.v1_0.FieldValue;
import com.liferay.headless.form.dto.v1_0.FormRecord;
import com.liferay.portal.kernel.exception.PortalException;
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
import java.util.Objects;

/**
 * @author Victor Oliveira
 */
public class FormRecordUtil {

	public static FormRecord toFormRecord(
			DDMFormInstanceRecord ddmFormInstanceRecord,
			DLAppService dlAppService, DLURLHelper dlurlHelper, Locale locale,
			Portal portal, UserLocalService userLocalService)
		throws PortalException {

		DDMFormValues ddmFormValues = ddmFormInstanceRecord.getDDMFormValues();

		return new FormRecord() {
			{
				creator = CreatorUtil.toCreator(
					portal,
					userLocalService.getUser(
						ddmFormInstanceRecord.getUserId()));
				draft =
					ddmFormInstanceRecord.getStatus() ==
						WorkflowConstants.STATUS_DRAFT;
				dateCreated = ddmFormInstanceRecord.getCreateDate();
				dateModified = ddmFormInstanceRecord.getModifiedDate();
				datePublished = ddmFormInstanceRecord.getLastPublishDate();
				fieldValues = TransformUtil.transformToArray(
					ddmFormValues.getDDMFormFieldValues(),
					ddmFormFieldValue -> {
						Value localizedValue = ddmFormFieldValue.getValue();

						if (localizedValue == null) {
							return null;
						}

						return new FieldValue() {
							{
								FileEntry fileEntry = null;

								try {
									JSONObject jsonObject =
										JSONFactoryUtil.createJSONObject(
											localizedValue.getString(locale));

									Object fileEntryId = jsonObject.opt(
										"fileEntryId");

									if (Objects.nonNull(fileEntryId)) {
										fileEntry = dlAppService.getFileEntry(
											(Long)fileEntryId);
									}
								}
								catch (JSONException jsone) {
									if (_log.isWarnEnabled()) {
										_log.warn(jsone, jsone);
									}
								}

								if (fileEntry != null) {
									document = FormDocumentUtil.toFormDocument(
										dlurlHelper, fileEntry);
								}

								name = ddmFormFieldValue.getName();
								value = localizedValue.getString(locale);
							}
						};
					},
					FieldValue.class);
				id = ddmFormInstanceRecord.getFormInstanceRecordId();
			}
		};
	}

	private static final Log _log = LogFactoryUtil.getLog(FormRecordUtil.class);

}