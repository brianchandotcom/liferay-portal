/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.expando;

import com.liferay.expando.kernel.model.ExpandoColumn;
import com.liferay.expando.kernel.model.ExpandoColumnConstants;
import com.liferay.expando.kernel.model.ExpandoTableConstants;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;

/**
 * @author Shuyang Zhou
 */
public class ExpandoBridgeUtil {

	public static final String NUMBER_SUFFIX = "_Number";

	public static String encodeFieldName(ExpandoColumn expandoColumn) {
		StringBundler sb = new StringBundler(7);

		sb.append(_FIELD_NAMESPACE);
		sb.append(StringPool.DOUBLE_UNDERLINE);

		if (getIndexType(expandoColumn) ==
				ExpandoColumnConstants.INDEX_TYPE_KEYWORD) {

			sb.append("keyword__");
		}

		sb.append(
			StringUtil.toLowerCase(ExpandoTableConstants.DEFAULT_TABLE_NAME));
		sb.append(StringPool.DOUBLE_UNDERLINE);
		sb.append(expandoColumn.getName());
		sb.append(getNumericSuffix(expandoColumn.getType()));

		return sb.toString();
	}

	public static int getIndexType(ExpandoColumn expandoColumn) {
		UnicodeProperties unicodeProperties =
			expandoColumn.getTypeSettingsProperties();

		return GetterUtil.getInteger(
			unicodeProperties.getProperty(ExpandoColumnConstants.INDEX_TYPE));
	}

	public static String getNumericSuffix(int columnType) {
		if ((columnType == ExpandoColumnConstants.DOUBLE) ||
			(columnType == ExpandoColumnConstants.DOUBLE_ARRAY)) {

			return "_double";
		}
		else if ((columnType == ExpandoColumnConstants.FLOAT) ||
				 (columnType == ExpandoColumnConstants.FLOAT_ARRAY)) {

			return "_float";
		}
		else if ((columnType == ExpandoColumnConstants.INTEGER) ||
				 (columnType == ExpandoColumnConstants.INTEGER_ARRAY)) {

			return "_integer";
		}
		else if ((columnType == ExpandoColumnConstants.LONG) ||
				 (columnType == ExpandoColumnConstants.LONG_ARRAY)) {

			return "_long";
		}
		else if ((columnType == ExpandoColumnConstants.SHORT) ||
				 (columnType == ExpandoColumnConstants.SHORT_ARRAY)) {

			return "_short";
		}

		return StringPool.BLANK;
	}

	private static final String _FIELD_NAMESPACE = "expando";

}