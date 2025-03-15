/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.java.parser;

/**
 * @author Alan Huang
 */
public class JavaTextBlock extends BaseJavaExpression {

	public JavaTextBlock(String name) {
		_name = name;
	}

	@Override
	protected String getString(
		String indent, String prefix, String suffix, int maxLineLength,
		boolean forceLineBreak) {

		return "\"\"\"" + _name + "\"\"\"";
	}

	private final String _name;

}