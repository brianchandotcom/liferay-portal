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

package com.liferay.portlet.expando.model.impl;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.TextFormatter;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portlet.expando.model.ExpandoColumnConstants;
import com.liferay.portlet.expando.model.ExpandoValue;

import java.io.IOException;
import java.io.Serializable;

import java.util.Locale;

/**
 * @author Raymond Augé
 * @author Brian Wing Shun Chan
 */
public class ExpandoColumnImpl extends ExpandoColumnBaseImpl {

	@Override
	public Serializable getDefaultValue() {
		try {
			ExpandoValue value = new ExpandoValueImpl();

			value.setColumnId(getColumnId());
			value.setData(getDefaultData());

			int type = getType();

			if (type == ExpandoColumnConstants.BOOLEAN) {
				return value.getBoolean();
			}
			else if (type == ExpandoColumnConstants.BOOLEAN_ARRAY) {
				return value.getBooleanArray();
			}
			else if (type == ExpandoColumnConstants.DATE) {
				return value.getDate();
			}
			else if (type == ExpandoColumnConstants.DATE_ARRAY) {
				return value.getDateArray();
			}
			else if (type == ExpandoColumnConstants.DOUBLE) {
				return value.getDouble();
			}
			else if (type == ExpandoColumnConstants.DOUBLE_ARRAY) {
				return value.getDoubleArray();
			}
			else if (type == ExpandoColumnConstants.FLOAT) {
				return value.getFloat();
			}
			else if (type == ExpandoColumnConstants.FLOAT_ARRAY) {
				return value.getFloatArray();
			}
			else if (type == ExpandoColumnConstants.INTEGER) {
				return value.getInteger();
			}
			else if (type == ExpandoColumnConstants.INTEGER_ARRAY) {
				return value.getIntegerArray();
			}
			else if (type == ExpandoColumnConstants.LONG) {
				return value.getLong();
			}
			else if (type == ExpandoColumnConstants.LONG_ARRAY) {
				return value.getLongArray();
			}
			else if (type == ExpandoColumnConstants.NUMBER) {
				return value.getNumber();
			}
			else if (type == ExpandoColumnConstants.NUMBER_ARRAY) {
				return value.getNumberArray();
			}
			else if (type == ExpandoColumnConstants.SHORT) {
				return value.getShort();
			}
			else if (type == ExpandoColumnConstants.SHORT_ARRAY) {
				return value.getShortArray();
			}
			else if (type == ExpandoColumnConstants.STRING_ARRAY) {
				return value.getStringArray();
			}
			else if (type == ExpandoColumnConstants.STRING_ARRAY_LOCALIZED) {
				return (Serializable)value.getStringArrayMap();
			}
			else if (type == ExpandoColumnConstants.STRING_LOCALIZED) {
				return (Serializable)value.getStringMap();
			}
			else {
				return value.getString();
			}
		}
		catch (Exception e) {
			return null;
		}
	}

	@Override
	public String getDisplayName(Locale locale) {
		String name = getName();

		String displayName = LanguageUtil.get(locale, name);

		if (name.equals(displayName)) {
			displayName = TextFormatter.format(name, TextFormatter.J);
		}

		return displayName;
	}

	@Override
	public String getTypeSettings() {
		if (_typeSettingsProperties == null) {
			return super.getTypeSettings();
		}
		else {
			return _typeSettingsProperties.toString();
		}
	}

	@Override
	public UnicodeProperties getTypeSettingsProperties() {
		if (_typeSettingsProperties == null) {
			_typeSettingsProperties = new UnicodeProperties(true);

			try {
				_typeSettingsProperties.load(super.getTypeSettings());
			}
			catch (IOException ioe) {
				_log.error(ioe, ioe);
			}
		}

		return _typeSettingsProperties;
	}

	@Override
	public void setTypeSettings(String typeSettings) {
		_typeSettingsProperties = null;

		super.setTypeSettings(typeSettings);
	}

	@Override
	public void setTypeSettingsProperties(
		UnicodeProperties typeSettingsProperties) {

		_typeSettingsProperties = typeSettingsProperties;

		super.setTypeSettings(_typeSettingsProperties.toString());
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ExpandoColumnImpl.class);

	private UnicodeProperties _typeSettingsProperties;

}