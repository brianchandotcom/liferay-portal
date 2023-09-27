/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.wiki.engine.creole.internal.antlrwiki.translator.internal;

import com.liferay.wiki.engine.creole.internal.parser.ast.HeadingNode;

/**
 * @author Miguel Pastor
 */
public class UnformattedHeadingTextVisitor extends BaseUnformattedTextVisitor {

	public String getUnformattedText(HeadingNode headingNode) {
		traverse(headingNode.getChildASTNodes());

		return getText();
	}

}