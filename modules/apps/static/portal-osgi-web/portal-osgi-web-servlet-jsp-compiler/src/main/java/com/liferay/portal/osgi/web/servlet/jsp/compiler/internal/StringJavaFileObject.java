/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.osgi.web.servlet.jsp.compiler.internal;

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