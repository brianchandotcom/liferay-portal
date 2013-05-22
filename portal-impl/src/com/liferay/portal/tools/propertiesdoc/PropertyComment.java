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

/**
 * @author Jesse Rao
 * @author James Hinkey
 * @author Hugo Huijser
 */
public class PropertyComment {

	public PropertyComment(String comment) {
		_comment = comment;

		String[] lines = comment.split(StringPool.NEW_LINE);

		for (String line : lines) {
			if (line.startsWith(PropertiesDocBuilder._INDENT)) {
				_isPreFormatted = true;

				return;
			}
		}
	}

	public String getComment() {
		return _comment;
	}

	public boolean isPreFormatted() {
		return _isPreFormatted;
	}

	private String _comment;
	private boolean _isPreFormatted;

}