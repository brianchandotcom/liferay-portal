/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.internal.errors;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringUtil;
import com.liferay.portal.kernel.servlet.taglib.DynamicIncludeUtil;
import com.liferay.portal.kernel.util.GetterUtil;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * @author Shuyang Zhou
 */
public class DynamicIncludeKeyUtil {

	public static String getDynamicIncludeKey(String accept) {
		List<String> mediaRangeStrings = StringUtil.split(accept);

		List<Map.Entry<String, Double>> entries = new ArrayList<>(
			mediaRangeStrings.size());

		for (int i = 0; i < mediaRangeStrings.size(); i++) {
			double weight = mediaRangeStrings.size() - i;

			String mediaRangeString = mediaRangeStrings.get(i);

			mediaRangeString = mediaRangeString.trim();

			int index = mediaRangeString.indexOf(CharPool.SEMICOLON);

			if ((index != -1) &&
				(mediaRangeString.charAt(index + 2) == CharPool.EQUAL)) {

				char c = mediaRangeString.charAt(index + 1);

				if ((c == CharPool.LOWER_CASE_Q) ||
					(c == CharPool.UPPER_CASE_Q)) {

					int start = index + 3;

					int end = mediaRangeString.indexOf(
						CharPool.SEMICOLON, start);

					if (end == -1) {
						end = mediaRangeString.length();
					}

					weight = GetterUtil.getDouble(
						mediaRangeString.substring(start, end), 1);
				}

				mediaRangeString = mediaRangeString.substring(0, index);

				mediaRangeString = mediaRangeString.trim();
			}

			entries.add(
				new AbstractMap.SimpleImmutableEntry<>(
					mediaRangeString, weight));
		}

		entries.sort(_comparator);

		for (Map.Entry<String, Double> entry : entries) {
			String dynamicIncludeKey =
				_DYNAMIC_INCLUDE_KEY_PREFIX + entry.getKey();

			if (DynamicIncludeUtil.hasDynamicInclude(dynamicIncludeKey)) {
				return dynamicIncludeKey;
			}
		}

		return null;
	}

	private static final String _DYNAMIC_INCLUDE_KEY_PREFIX =
		"/errors/code.jsp#";

	private static final Comparator<Map.Entry<String, Double>> _comparator =
		(entry1, entry2) -> Double.compare(
			entry2.getValue(), entry1.getValue());

}