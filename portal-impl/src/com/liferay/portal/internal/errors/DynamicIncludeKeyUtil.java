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

		List<Map.Entry<String, Double>> mediaRanges = new ArrayList<>(
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

			mediaRanges.add(
				new AbstractMap.SimpleImmutableEntry<>(
					mediaRangeString, weight));
		}

		mediaRanges.sort(_comparator);

		for (Map.Entry<String, Double> mediaRange : mediaRanges) {
			String dynamicIncludeKey =
				_DYNAMIC_INCLUDE_KEY_PREFIX + mediaRange.getKey();

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