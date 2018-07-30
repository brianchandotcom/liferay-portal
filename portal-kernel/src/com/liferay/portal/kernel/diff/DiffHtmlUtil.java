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

package com.liferay.portal.kernel.diff;

import java.io.Reader;

/**
 * <p>
 * This class can compare two different versions of HTML code. It detects
 * changes to an entire HTML page such as removal or addition of characters or
 * images.
 * </p>
 *
 * @author Julio Camarero
 */
public class DiffHtmlUtil {

	public static String diff(Reader source, Reader target) throws Exception {
		return getDiffHtml().diff(source, target);
	}

	public static DiffHtml getDiffHtml() {
		return _diffHtml;
	}

	public static String replaceStyles(String html) {
		return getDiffHtml().replaceStyles(html);
	}

	public void setDiffHtml(DiffHtml diffHtml) {
		_diffHtml = diffHtml;
	}

	private static DiffHtml _diffHtml;

}