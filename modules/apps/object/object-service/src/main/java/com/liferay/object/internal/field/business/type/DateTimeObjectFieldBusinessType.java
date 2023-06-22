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

package com.liferay.object.internal.field.business.type;

import com.liferay.dynamic.data.mapping.form.field.type.constants.DDMFormFieldTypeConstants;
import com.liferay.object.constants.ObjectFieldConstants;
import com.liferay.object.constants.ObjectFieldSettingConstants;
import com.liferay.object.field.business.type.ObjectFieldBusinessType;
import com.liferay.object.field.setting.util.ObjectFieldSettingUtil;
import com.liferay.object.model.ObjectField;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.vulcan.extension.PropertyDefinition;

import java.sql.Timestamp;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import java.util.Collections;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Murilo Stodolni
 */
@Component(
	property = "object.field.business.type.key=" + ObjectFieldConstants.BUSINESS_TYPE_DATE_TIME,
	service = ObjectFieldBusinessType.class
)
public class DateTimeObjectFieldBusinessType
	implements ObjectFieldBusinessType {

	@Override
	public Set<String> getAllowedObjectFieldSettingsNames() {
		return Collections.singleton(
			ObjectFieldSettingConstants.NAME_TIME_STORAGE);
	}

	@Override
	public String getDBType() {
		return ObjectFieldConstants.DB_TYPE_DATE_TIME;
	}

	@Override
	public String getDDMFormFieldTypeName() {
		return DDMFormFieldTypeConstants.DATE_TIME;
	}

	@Override
	public String getDescription(Locale locale) {
		return _language.get(locale, "add-date-and-time-values");
	}

	@Override
	public Object getDisplayContextValue(
			ObjectField objectField, long userId, Map<String, Object> values)
		throws PortalException {

		String value = MapUtil.getString(values, objectField.getName());

		if (Validator.isNull(value)) {
			return StringPool.BLANK;
		}

		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(
			"yyyy-MM-dd HH:mm");

		return dateTimeFormatter.format(
			_getLocalDateTime(
				StringPool.UTC,
				ObjectFieldSettingUtil.getTimeZoneId(
					objectField.getObjectFieldSettings(),
					_userLocalService.getUser(userId)),
				value));
	}

	@Override
	public String getLabel(Locale locale) {
		return _language.get(locale, "date-and-time");
	}

	@Override
	public String getName() {
		return ObjectFieldConstants.BUSINESS_TYPE_DATE_TIME;
	}

	@Override
	public PropertyDefinition.PropertyType getPropertyType() {
		return PropertyDefinition.PropertyType.DATE_TIME;
	}

	@Override
	public Set<String> getRequiredObjectFieldSettingsNames(
		ObjectField objectField) {

		return Collections.singleton(
			ObjectFieldSettingConstants.NAME_TIME_STORAGE);
	}

	@Override
	public Set<String> getUnmodifiableObjectFieldSettingsNames() {
		return Collections.singleton(
			ObjectFieldSettingConstants.NAME_TIME_STORAGE);
	}

	@Override
	public Object getValue(
			ObjectField objectField, long userId, Map<String, Object> values)
		throws PortalException {

		String value = MapUtil.getString(values, objectField.getName());

		if (Validator.isNull(value)) {
			return null;
		}

		return Timestamp.valueOf(
			_getLocalDateTime(
				ObjectFieldSettingUtil.getTimeZoneId(
					objectField.getObjectFieldSettings(),
					_userLocalService.getUser(userId)),
				StringPool.UTC, value));
	}

	private String _getDateTimePattern(String value) {
		if (value.length() == 16) {
			return "yyyy-MM-dd HH:mm";
		}
		else if (value.length() == 20) {
			return "yyyy-MM-dd'T'HH:mm:ss'Z'";
		}
		else if (value.length() == 21) {
			return "yyyy-MM-dd HH:mm:ss.S";
		}
		else if ((value.length() == 23) && (value.charAt(10) == 'T')) {
			return "yyyy-MM-dd'T'HH:mm:ss.SSS";
		}
		else if ((value.length() == 24) && (value.charAt(10) == 'T')) {
			return "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
		}
		else if ((value.length() == 28) && (value.charAt(23) == '+')) {
			return "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
		}

		return DateUtil.ISO_8601_PATTERN;
	}

	private LocalDateTime _getLocalDateTime(
		String sourceTimeZoneId, String targetTimeZoneId, String value) {

		LocalDateTime localDateTime = LocalDateTime.parse(
			value, DateTimeFormatter.ofPattern(_getDateTimePattern(value)));

		if (Validator.isNull(sourceTimeZoneId) ||
			Validator.isNull(targetTimeZoneId)) {

			return localDateTime;
		}

		ZonedDateTime zonedDateTime = ZonedDateTime.of(
			localDateTime, ZoneId.of(sourceTimeZoneId));

		return LocalDateTime.ofInstant(
			zonedDateTime.toInstant(), ZoneId.of(targetTimeZoneId));
	}

	@Reference
	private Language _language;

	@Reference
	private UserLocalService _userLocalService;

}