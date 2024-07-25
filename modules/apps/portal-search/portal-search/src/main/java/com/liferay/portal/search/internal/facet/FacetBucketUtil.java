/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.facet;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.facet.Facet;
import com.liferay.portal.kernel.search.facet.RangeFacet;
import com.liferay.portal.kernel.search.facet.config.FacetConfiguration;
import com.liferay.portal.kernel.search.facet.util.RangeParserUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.facet.nested.NestedFacet;

/**
 * @author Bryan Engler
 * @author André de Oliveira
 */
public class FacetBucketUtil {

	public static boolean isFieldInBucket(
		Field field, String term, Facet facet) {

		if (facet instanceof NestedFacet) {
			return _isNestedValueInBucket(field, term, (NestedFacet)facet);
		}

		if (facet instanceof RangeFacet) {
			return _isRangeValueInBucket(field, term);
		}

		if (ArrayUtil.contains(field.getValues(), term, false)) {
			return true;
		}

		return false;
	}

	private static boolean _containsFieldNameFieldValue(
		String fieldName, String fieldValue, String[] parts) {

		for (String pair : parts) {
			if (pair.equals(fieldName + "=" + fieldValue)) {
				return true;
			}
		}

		return false;
	}

	private static boolean _isNestedValueInBucket(
		Field field, String term, NestedFacet nestedFacet) {

		FacetConfiguration facetConfiguration =
			nestedFacet.getFacetConfiguration();

		String fieldName = _removePath(
			nestedFacet.getPath(), facetConfiguration.getFieldName());

		String filterFieldName = _removePath(
			nestedFacet.getPath(), nestedFacet.getFilterField());
		String filterFieldValue = nestedFacet.getFilterValue();

		for (String fieldValue : field.getValues()) {
			fieldValue = _removeCurlyBraces(fieldValue);

			String[] parts = fieldValue.split(StringPool.COMMA_AND_SPACE);

			if (_containsFieldNameFieldValue(
					filterFieldName, filterFieldValue, parts) &&
				_containsFieldNameFieldValue(fieldName, term, parts)) {

				return true;
			}
		}

		return false;
	}

	private static boolean _isRangeValueInBucket(Field field, String term) {
		String[] range = RangeParserUtil.parserRange(term);

		String lower = range[0];
		String upper = range[1];

		String value = field.getValue();

		if (Validator.isNotNull(lower) && (lower.compareTo(value) <= 0) &&
			Validator.isNotNull(upper) && (value.compareTo(upper) <= 0)) {

			return true;
		}

		return false;
	}

	private static String _removeCurlyBraces(String value) {
		return value.substring(1, value.length() - 1);
	}

	private static String _removePath(String path, String string) {
		return StringUtil.removeSubstring(string, path + StringPool.PERIOD);
	}

}