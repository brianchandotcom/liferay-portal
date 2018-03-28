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

package com.liferay.dynamic.data.mapping.form.analytics.internal.model.listener;

import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTypeServicesTracker;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldValueAccessor;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecordVersion;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.ModelListener;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Inácio Nery
 */
@Component(immediate = true, service = ModelListener.class)
public class DDMFormInstanceRecordVersionModelListener
	extends DDMFormBaseModelListener<DDMFormInstanceRecordVersion> {

	@Override
	public void onAfterCreate(
			DDMFormInstanceRecordVersion ddmFormInstanceRecordVersion)
		throws ModelListenerException {

		try {
			sendAnalytics(
				ddmFormInstanceRecordVersion.getCompanyId(), "formSubmitted",
				createEventProperties(ddmFormInstanceRecordVersion));

			checkEmptyFields(ddmFormInstanceRecordVersion);
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug(e, e);
			}
		}
	}

	protected void checkEmptyFields(
			DDMFormInstanceRecordVersion ddmFormInstanceRecordVersion)
		throws Exception {

		DDMFormValues ddmFormValues =
			ddmFormInstanceRecordVersion.getDDMFormValues();

		Set<String> fieldNames = new HashSet<>();

		for (DDMFormFieldValue ddmFormFieldValue :
				ddmFormValues.getDDMFormFieldValues()) {

			if (isEmpty(
					ddmFormFieldValue.getDDMFormField(), ddmFormFieldValue)) {

				String fieldName = ddmFormFieldValue.getName();

				if (fieldNames.contains(fieldName)) {
					continue;
				}

				fieldNames.add(fieldName);

				sendEmptyFieldEvent(ddmFormInstanceRecordVersion, fieldName);
			}
		}
	}

	protected Map<String, String> createEventProperties(
			DDMFormInstanceRecordVersion ddmFormInstanceRecordVersion)
		throws PortalException {

		Map<String, String> properties = new HashMap<>();

		properties.put(
			"formId",
			String.valueOf(ddmFormInstanceRecordVersion.getFormInstanceId()));
		properties.put(
			"recordId",
			String.valueOf(
				ddmFormInstanceRecordVersion.getFormInstanceRecordId()));
		properties.put(
			"version",
			String.valueOf(ddmFormInstanceRecordVersion.getVersion()));

		return properties;
	}

	protected boolean isEmpty(
		DDMFormField ddmFormField, DDMFormFieldValue ddmFormFieldValue) {

		Value value = ddmFormFieldValue.getValue();

		if (value == null) {
			return true;
		}

		DDMFormFieldValueAccessor<?> ddmFormFieldValueAccessor =
			_ddmFormFieldTypeServicesTracker.getDDMFormFieldValueAccessor(
				ddmFormField.getType());

		for (Locale availableLocale : value.getAvailableLocales()) {
			if (ddmFormFieldValueAccessor.isEmpty(
					ddmFormFieldValue, availableLocale)) {

				return true;
			}
		}

		return false;
	}

	protected void sendEmptyFieldEvent(
			DDMFormInstanceRecordVersion ddmFormInstanceRecordVersion,
			String fieldName)
		throws Exception {

		Map<String, String> eventProperties = createEventProperties(
			ddmFormInstanceRecordVersion);

		eventProperties.put("fieldName", fieldName);

		sendAnalytics(
			ddmFormInstanceRecordVersion.getCompanyId(), "fieldBlanked",
			eventProperties);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DDMFormInstanceRecordVersionModelListener.class);

	@Reference
	private DDMFormFieldTypeServicesTracker _ddmFormFieldTypeServicesTracker;

}