/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.notification.term.evaluator.util;

import com.liferay.list.type.model.ListTypeEntry;
import com.liferay.list.type.service.ListTypeEntryLocalServiceUtil;
import com.liferay.object.constants.ObjectFieldConstants;
import com.liferay.object.model.ObjectField;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.sql.Timestamp;

import java.text.SimpleDateFormat;

import java.util.Date;

/**
 * @author Carolina Barbosa
 */
public class ObjectDefinitionNotificationTermEvaluatorUtil {

	public static Object getTermValue(ObjectField objectField, Object value) {
		if (objectField.compareBusinessType(
				ObjectFieldConstants.BUSINESS_TYPE_DATE) &&
			!StringUtil.equals(objectField.getName(), "createDate") &&
			!StringUtil.equals(objectField.getName(), "modifiedDate")) {

			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
				"yyyy-MM-dd");

			if (value instanceof Date) {
				if (value instanceof Timestamp) {
					Timestamp timestamp = (Timestamp)value;

					return simpleDateFormat.format(
						new Date(timestamp.getTime()));
				}

				return simpleDateFormat.format((Date)value);
			}

			Timestamp timestamp = new Timestamp((Long)value);

			return simpleDateFormat.format(new Date(timestamp.getTime()));
		}
		else if (objectField.compareBusinessType(
					ObjectFieldConstants.BUSINESS_TYPE_MULTISELECT_PICKLIST)) {

			return StringUtil.merge(
				TransformUtil.transform(
					StringUtil.split(
						String.valueOf(value), StringPool.COMMA_AND_SPACE),
					listTypeEntryKey -> {
						ListTypeEntry listTypeEntry =
							ListTypeEntryLocalServiceUtil.fetchListTypeEntry(
								objectField.getListTypeDefinitionId(),
								listTypeEntryKey);

						return listTypeEntry.getNameCurrentValue();
					},
					String.class),
				StringPool.COMMA_AND_SPACE);
		}
		else if (objectField.compareBusinessType(
					ObjectFieldConstants.BUSINESS_TYPE_PICKLIST)) {

			ListTypeEntry listTypeEntry =
				ListTypeEntryLocalServiceUtil.fetchListTypeEntry(
					objectField.getListTypeDefinitionId(), (String)value);

			if (listTypeEntry != null) {
				return listTypeEntry.getNameCurrentValue();
			}
		}

		return value;
	}

}