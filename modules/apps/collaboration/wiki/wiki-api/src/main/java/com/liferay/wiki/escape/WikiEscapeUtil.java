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

package com.liferay.wiki.escape;

import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

/**
 * @author Iván Zaera
 */
public class WikiEscapeUtil {

	public static String escapeName(String name) {
		return StringUtil.replace(name, _UNESCAPED_CHARS, _ESCAPED_CHARS);
	}

	public static String unescapeName(String name) {
		return StringUtil.replace(name, _ESCAPED_CHARS, _UNESCAPED_CHARS);
	}

	private static final String[] _ESCAPED_CHARS =
		new String[] {"<PLUS>", "<QUESTION>", "<SLASH>"};

	private static final String[] _UNESCAPED_CHARS =
		new String[] {StringPool.PLUS, StringPool.QUESTION, StringPool.SLASH};

}