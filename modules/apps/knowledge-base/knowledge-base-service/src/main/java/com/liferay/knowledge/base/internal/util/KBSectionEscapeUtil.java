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

package com.liferay.knowledge.base.internal.util;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.StringUtil;

/**
 * @author Preston Crary
 */
public class KBSectionEscapeUtil {

	public static String[] escapeSections(String[] sections) {
		if (ArrayUtil.isEmpty(sections)) {
			return new String[0];
		}

		sections = ArrayUtil.clone(sections);

		for (int i = 0; i < sections.length; i++) {
			sections[i] = StringPool.UNDERLINE.concat(
				sections[i]
			).concat(
				StringPool.UNDERLINE
			);
		}

		return sections;
	}

	public static String[] unescapeSections(String sections) {
		String[] sectionsArray = StringUtil.split(sections);

		for (int i = 0; i < sectionsArray.length; i++) {
			String section = sectionsArray[i];

			if (StringUtil.startsWith(section, StringPool.UNDERLINE) &&
				StringUtil.endsWith(section, StringPool.UNDERLINE)) {

				sectionsArray[i] = section.substring(1, section.length() - 1);
			}
		}

		return sectionsArray;
	}

	private KBSectionEscapeUtil() {
	}

}