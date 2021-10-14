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

package com.liferay.screens.service.impl;

import com.liferay.dynamic.data.lists.model.DDLRecord;
import com.liferay.dynamic.data.lists.model.DDLRecordSet;
import com.liferay.dynamic.data.lists.service.DDLRecordLocalService;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.storage.constants.FieldConstants;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.screens.service.base.ScreensDDLRecordServiceBaseImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author José Manuel Navarro
 */
@Component(
	property = {
		"json.web.service.context.name=screens",
		"json.web.service.context.path=ScreensDDLRecord"
	},
	service = AopService.class
)
public class ScreensDDLRecordServiceImpl
	extends ScreensDDLRecordServiceBaseImpl {

	@Override
	public JSONObject getDDLRecord(long ddlRecordId, Locale locale)
		throws PortalException {

		DDLRecord ddlRecord = _ddlRecordLocalService.getRecord(ddlRecordId);

		_ddlRecordSetModelResourcePermission.check(
			getPermissionChecker(), ddlRecord.getRecordSetId(),
			ActionKeys.VIEW);

		DDMFormValues ddmFormValues = ddlRecord.getDDMFormValues();

		Set<Locale> availableLocales = ddmFormValues.getAvailableLocales();

		if ((locale == null) || !availableLocales.contains(locale)) {
			locale = ddmFormValues.getDefaultLocale();
		}

		return getDDLRecordJSONObject(ddlRecord, locale);
	}

	@Override
	public JSONArray getDDLRecords(
			long ddlRecordSetId, Locale locale, int start, int end,
			OrderByComparator<DDLRecord> orderByComparator)
		throws PortalException {

		_ddlRecordSetModelResourcePermission.check(
			getPermissionChecker(), ddlRecordSetId, ActionKeys.VIEW);

		List<DDLRecord> ddlRecords = _ddlRecordLocalService.getRecords(
			ddlRecordSetId, start, end, orderByComparator);

		return getDDLRecordsJSONArray(ddlRecords, locale);
	}

	@Override
	public JSONArray getDDLRecords(
			long ddlRecordSetId, long userId, Locale locale, int start, int end,
			OrderByComparator<DDLRecord> orderByComparator)
		throws PortalException {

		_ddlRecordSetModelResourcePermission.check(
			getPermissionChecker(), ddlRecordSetId, ActionKeys.VIEW);

		List<DDLRecord> ddlRecords = _ddlRecordLocalService.getRecords(
			ddlRecordSetId, userId, start, end, orderByComparator);

		return getDDLRecordsJSONArray(ddlRecords, locale);
	}

	@Override
	public int getDDLRecordsCount(long ddlRecordSetId) throws PortalException {
		_ddlRecordSetModelResourcePermission.check(
			getPermissionChecker(), ddlRecordSetId, ActionKeys.VIEW);

		return _ddlRecordLocalService.getRecordsCount(ddlRecordSetId);
	}

	@Override
	public int getDDLRecordsCount(long ddlRecordSetId, long userId)
		throws PortalException {

		_ddlRecordSetModelResourcePermission.check(
			getPermissionChecker(), ddlRecordSetId, ActionKeys.VIEW);

		return _ddlRecordLocalService.getRecordsCount(ddlRecordSetId, userId);
	}

	protected JSONObject getDDLRecordJSONObject(
			DDLRecord ddlRecord, Locale locale)
		throws PortalException {

		Map<String, Object> ddlRecordMap = new HashMap<>();

		DDMFormValues ddmFormValues = ddlRecord.getDDMFormValues();

		Set<Locale> availableLocales = ddmFormValues.getAvailableLocales();

		if ((locale == null) || !availableLocales.contains(locale)) {
			locale = ddmFormValues.getDefaultLocale();
		}

		for (DDMFormFieldValue ddmFormFieldValue :
				ddmFormValues.getDDMFormFieldValues()) {

			Object fieldValue = getFieldValue(ddmFormFieldValue, locale);

			if (fieldValue != null) {
				ddlRecordMap.put(ddmFormFieldValue.getName(), fieldValue);
			}
			else {
				for (Locale availableLocale : availableLocales) {
					fieldValue = getFieldValue(
						ddmFormFieldValue, availableLocale);

					if (fieldValue != null) {
						ddlRecordMap.put(
							ddmFormFieldValue.getName(), fieldValue);

						break;
					}
				}
			}
		}

		return JSONUtil.put(
			"modelAttributes",
			JSONFactoryUtil.createJSONObject(
				JSONFactoryUtil.looseSerialize(ddlRecord.getModelAttributes()))
		).put(
			"modelValues",
			JSONFactoryUtil.createJSONObject(
				JSONFactoryUtil.looseSerialize(ddlRecordMap))
		);
	}

	protected JSONArray getDDLRecordsJSONArray(
			List<DDLRecord> ddlRecords, Locale locale)
		throws PortalException {

		JSONArray ddlRecordsJSONArray = JSONFactoryUtil.createJSONArray();

		for (DDLRecord ddlRecord : ddlRecords) {
			JSONObject ddlRecordJSONObject = getDDLRecordJSONObject(
				ddlRecord, locale);

			ddlRecordsJSONArray.put(ddlRecordJSONObject);
		}

		return ddlRecordsJSONArray;
	}

	protected Object getFieldValue(
			DDMFormFieldValue ddmFormFieldValue, Locale locale)
		throws PortalException {

		Value value = ddmFormFieldValue.getValue();

		String fieldValueString = value.getString(locale);

		if ((fieldValueString == null) || fieldValueString.isEmpty()) {
			return null;
		}

		String dataType = ddmFormFieldValue.getType();

		if (dataType.equals(FieldConstants.BOOLEAN)) {
			return Boolean.valueOf(fieldValueString);
		}
		else if (dataType.equals(FieldConstants.DATE)) {
			return fieldValueString;
		}
		else if (dataType.equals(FieldConstants.DOCUMENT_LIBRARY)) {
			return JSONFactoryUtil.looseSerialize(
				JSONFactoryUtil.looseDeserialize(fieldValueString));
		}
		else if (dataType.equals(FieldConstants.FLOAT) ||
				 dataType.equals(FieldConstants.NUMBER)) {

			if (Validator.isNull(fieldValueString)) {
				return null;
			}

			return Float.valueOf(fieldValueString);
		}
		else if (dataType.equals(FieldConstants.INTEGER)) {
			if (Validator.isNull(fieldValueString)) {
				return null;
			}

			return Integer.valueOf(fieldValueString);
		}
		else if (dataType.equals(FieldConstants.LONG)) {
			if (Validator.isNull(fieldValueString)) {
				return null;
			}

			return Long.valueOf(fieldValueString);
		}
		else if (dataType.equals(FieldConstants.SHORT)) {
			if (Validator.isNull(fieldValueString)) {
				return null;
			}

			return Short.valueOf(fieldValueString);
		}

		return fieldValueString;
	}

	@Reference
	private DDLRecordLocalService _ddlRecordLocalService;

	@Reference(
		target = "(model.class.name=com.liferay.dynamic.data.lists.model.DDLRecordSet)"
	)
	private ModelResourcePermission<DDLRecordSet>
		_ddlRecordSetModelResourcePermission;

}