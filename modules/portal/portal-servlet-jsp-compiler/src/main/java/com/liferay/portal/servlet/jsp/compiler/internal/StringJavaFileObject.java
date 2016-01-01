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

package com.liferay.portal.servlet.jsp.compiler.internal;

import java.net.URI;

/**
 * @author Shuyang Zhou
 */
public class StringJavaFileObject extends BaseJavaFileObject {

	public StringJavaFileObject(String simpleName, String content) {
		super(Kind.SOURCE, simpleName);

		_content = content;
	}

	@Override
	public CharSequence getCharContent(boolean ignoreEncodingErrors) {
		return _content;
	}

	@Override
	public boolean isNameCompatible(String simpleName, Kind kind) {
		if (className.equals(simpleName) && (Kind.SOURCE == kind)) {
			return true;
		}

		return false;
	}

	@Override
	public URI toUri() {
		return URI.create("string:///".concat(className));
	}

	private final String _content;

}