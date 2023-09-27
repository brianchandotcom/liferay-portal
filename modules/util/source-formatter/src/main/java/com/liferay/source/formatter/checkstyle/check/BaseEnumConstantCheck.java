/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.checkstyle.check;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * @author Hugo Huijser
 */
public abstract class BaseEnumConstantCheck extends BaseCheck {

	@Override
	public int[] getDefaultTokens() {
		return new int[] {TokenTypes.ENUM_CONSTANT_DEF};
	}

	protected DetailAST getNextEnumConstantDefinitionDetailAST(
		DetailAST enumConstantDefinitionDetailAST) {

		DetailAST nextSiblingDetailAST =
			enumConstantDefinitionDetailAST.getNextSibling();

		if (nextSiblingDetailAST.getType() != TokenTypes.COMMA) {
			return null;
		}

		nextSiblingDetailAST = nextSiblingDetailAST.getNextSibling();

		if (nextSiblingDetailAST.getType() == TokenTypes.ENUM_CONSTANT_DEF) {
			return nextSiblingDetailAST;
		}

		return null;
	}

}