/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.tools.propertiesdoc;

import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

/**
 * @author James Hinkey
 */
public class PropertiesHtmlFile {

	public PropertiesHtmlFile(String fileName) {
		_fileName = fileName;

		_propertiesFileName = StringUtil.replaceLast(
			_fileName, ".html", StringPool.BLANK);
	}

	public String getFileName() {
		return _fileName;
	}

	public String getPropertiesFileName() {
		return _propertiesFileName;
	}

	private final String _fileName;
	private final String _propertiesFileName;

}