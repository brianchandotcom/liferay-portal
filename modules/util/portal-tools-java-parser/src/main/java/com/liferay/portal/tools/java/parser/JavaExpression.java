/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.java.parser;

/**
 * @author Hugo Huijser
 */
public interface JavaExpression extends JavaTerm {

	public JavaExpression getChainedJavaExpression();

	public boolean hasSurroundingParentheses();

	public void setChainedJavaExpression(JavaExpression chainedJavaExpression);

	public void setHasSurroundingParentheses(boolean hasSurroundingParentheses);

	public void setSurroundingParentheses();

}