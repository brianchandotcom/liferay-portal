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

import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecord;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.headless.form.dto.v1_0.FieldValue;
import com.liferay.headless.form.dto.v1_0.FormRecord;
import com.liferay.portal.kernel.exception.PortalException;
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
			DDMFormInstanceRecord ddmFormInstanceRecord, Locale locale,
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

}