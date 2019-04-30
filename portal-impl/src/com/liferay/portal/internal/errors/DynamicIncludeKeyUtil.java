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
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.servlet.taglib.DynamicIncludeUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author Shuyang Zhou
 */
public class DynamicIncludeKeyUtil {

	public static String getDynamicIncludeKey(String accept) {
		Comparator<String> mediaRangesComparator =
			Comparator.<String>comparingDouble(
				mediaRange -> {
					String weightString = _extractWeight(mediaRange);

					if (weightString != null) {
						return GetterUtil.getDouble(
							weightString.substring(3), 1);
					}

					return 1;
				}
			).reversed(
			).thenComparing(
				mediaRange -> {
					int pos = mediaRange.indexOf(CharPool.SEMICOLON);

					if (pos > 0) {
						mediaRange = mediaRange.substring(0, pos);
					}

					pos = mediaRange.indexOf(CharPool.SLASH);

					if (pos > 0) {
						mediaRange = mediaRange.substring(0, pos);
					}

					return mediaRange.trim();
				}
			).thenComparing(
				mediaRange -> {
					int pos = mediaRange.indexOf(CharPool.SEMICOLON);

					if (pos > 0) {
						mediaRange = mediaRange.substring(0, pos);
					}

					pos = mediaRange.indexOf(CharPool.SLASH);

					if (pos > 0) {
						mediaRange = mediaRange.substring(pos);
					}

					return mediaRange.trim();
				}
			).thenComparing(
				mediaRange -> {
					mediaRange = _removeWeight(mediaRange);

					int pos = mediaRange.indexOf(CharPool.SEMICOLON);

					if (pos > 0) {
						mediaRange = mediaRange.substring(pos);
					}

					return mediaRange.trim();
				}
			);

		Set<String> mediaRangesSet = new TreeSet<>(mediaRangesComparator);

		if (Validator.isBlank(accept)) {
			return null;
		}
		else if (!accept.contains(StringPool.COMMA)) {
			mediaRangesSet.add(accept);
		}
		else {
			String[] mediaRanges = accept.split(StringPool.COMMA);

			for (int i = 0; i < mediaRanges.length; i++) {
				String mediaRange = mediaRanges[i];

				if (_extractWeight(mediaRange) == null) {
					mediaRange += ";q=" + (mediaRanges.length - i);
				}

				mediaRangesSet.add(mediaRange);
			}
		}

		String dynamicIncludeKeyPrefix = "/errors/code.jsp#";

		for (String mediaType : mediaRangesSet) {
			mediaType = _removeWeight(mediaType);

			String dynamicIncludeKey =
				dynamicIncludeKeyPrefix + mediaType.trim();

			if (DynamicIncludeUtil.hasDynamicInclude(dynamicIncludeKey)) {
				return dynamicIncludeKey;
			}
		}

		return null;
	}

	private static String _extractWeight(String mediaRange) {
		int weightPos = mediaRange.indexOf(";q=");

		if (weightPos < 0) {
			weightPos = mediaRange.indexOf(";Q=");
		}

		if (weightPos < 0) {
			return null;
		}

		int endPos = mediaRange.indexOf(CharPool.SEMICOLON, weightPos + 1);

		if (endPos < 0) {
			endPos = mediaRange.length();
		}

		return mediaRange.substring(weightPos, endPos);
	}

	private static String _removeWeight(String mediaRange) {
		String weightString = _extractWeight(mediaRange);

		if (weightString != null) {
			return StringUtil.removeSubstring(mediaRange, weightString);
		}

		return mediaRange;
	}

}