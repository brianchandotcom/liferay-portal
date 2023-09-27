/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.analytics.settings.rest.internal.util.comparator;

import com.liferay.portal.kernel.bean.BeanPropertiesUtil;
import com.liferay.portal.kernel.util.CollatorUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.text.Collator;

import java.util.Locale;

/**
 * @author Thiago Buarque
 */
public class IgnoreCaseOrderByComparator<T> extends OrderByComparator<T> {

	public IgnoreCaseOrderByComparator(Object[] columns, Locale locale) {
		if ((columns.length == 0) || ((columns.length % 2) != 0)) {
			throw new IllegalArgumentException(
				"Columns length is not an even number");
		}

		_columns = columns;
		_locale = locale;
	}

	@Override
	public int compare(T object1, T object2) {
		for (int i = 0; i < _columns.length; i += 2) {
			String columnName = String.valueOf(_columns[i]);

			Object columnValue1 = BeanPropertiesUtil.getObjectSilent(
				object1, columnName);
			Object columnValue2 = BeanPropertiesUtil.getObjectSilent(
				object2, columnName);

			int value;

			if ((columnValue1 instanceof String) &&
				(columnValue2 instanceof String)) {

				String columnValue1String = (String)columnValue1;
				String columnValue2String = (String)columnValue2;

				if (Validator.isXml(columnValue1String)) {
					columnValue1String = LocalizationUtil.getLocalization(
						columnValue1String, _locale.getLanguage());
					columnValue2String = LocalizationUtil.getLocalization(
						columnValue2String, _locale.getLanguage());

					Collator collator = CollatorUtil.getInstance(_locale);

					value = collator.compare(
						StringUtil.toLowerCase(columnValue1String),
						StringUtil.toLowerCase(columnValue2String));
				}
				else {
					value = columnValue1String.compareToIgnoreCase(
						columnValue2String);
				}
			}
			else {
				Comparable<Object> columnValueComparable1 =
					(Comparable<Object>)columnValue1;
				Comparable<Object> columnValueComparable2 =
					(Comparable<Object>)columnValue2;

				value = columnValueComparable1.compareTo(
					columnValueComparable2);
			}

			if (value == 0) {
				continue;
			}

			boolean columnAscending = Boolean.valueOf(
				String.valueOf(_columns[i + 1]));

			if (columnAscending) {
				return value;
			}

			return -value;
		}

		return 0;
	}

	private final Object[] _columns;
	private final Locale _locale;

}