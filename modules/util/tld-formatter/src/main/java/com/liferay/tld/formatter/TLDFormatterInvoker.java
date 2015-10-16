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

package com.liferay.tld.formatter;

import java.io.File;

/**
 * @author Andrea Di Giorgi
 */
public class TLDFormatterInvoker {

	public static TLDFormatter invoke(
			File baseDir, TLDFormatterArgs tldFormatterArgs)
		throws Exception {

		return new TLDFormatter(
			_getAbsolutePath(baseDir, tldFormatterArgs.getBaseDirName()),
			tldFormatterArgs.isPlugin());
	}

	private static String _getAbsolutePath(File baseDir, String fileName) {
		File file = new File(baseDir, fileName);

		return file.getAbsolutePath();
	}

}