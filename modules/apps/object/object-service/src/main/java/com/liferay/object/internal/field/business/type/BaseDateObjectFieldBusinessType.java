/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.field.business.type;

import com.liferay.object.constants.ObjectFieldConstants;
import com.liferay.object.constants.ObjectFieldSettingConstants;
import com.liferay.object.field.setting.util.ObjectFieldSettingUtil;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.model.ObjectField;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;

import java.io.Serializable;

import java.sql.Timestamp;

import java.text.SimpleDateFormat;

import java.util.Date;

/**
 * @author Carolina Barbosa
 */
public abstract class BaseDateObjectFieldBusinessType
	extends BaseObjectFieldBusinessType {

	@Override
	public Serializable getDTOValue(
			DTOConverterContext dtoConverterContext,
			ObjectDefinition objectDefinition, ObjectEntry objectEntry,
			ObjectField objectField, Serializable serializable)
		throws Exception {

		if (Validator.isNull(serializable)) {
			return null;
		}

		if (serializable instanceof String) {
			Date date = DateUtil.parseDate(
				"yyyy-MM-dd", (String)serializable,
				LocaleUtil.getSiteDefault());

			serializable = new Timestamp(date.getTime());
		}

		Timestamp timestamp = (Timestamp)serializable;

		if (timestamp == null) {
			return null;
		}

		String pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS";

		if (objectField.compareBusinessType(
				ObjectFieldConstants.BUSINESS_TYPE_DATE) ||
			StringUtil.equals(
				ObjectFieldSettingUtil.getValue(
					ObjectFieldSettingConstants.NAME_TIME_STORAGE, objectField),
				ObjectFieldSettingConstants.VALUE_CONVERT_TO_UTC)) {

			pattern += "'Z'";
		}

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

		return simpleDateFormat.format(timestamp);
	}

}