/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.java.parser;

/**
 * @author Hugo Huijser
 */
public class JavaSynchronizedStatement extends BaseJavaTerm {

	public void setSynchronizedJavaExpression(
		JavaExpression synchronizedJavaExpression) {

		_synchronizedJavaExpression = synchronizedJavaExpression;
	}

	@Override
	public String toString(
		String indent, String prefix, String suffix, int maxLineLength) {

		return _synchronizedJavaExpression.toString(
			indent, prefix + "synchronized (", ")" + suffix, maxLineLength);
	}

	private JavaExpression _synchronizedJavaExpression;

}