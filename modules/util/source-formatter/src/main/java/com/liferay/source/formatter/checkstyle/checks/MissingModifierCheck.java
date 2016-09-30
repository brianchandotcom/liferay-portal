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

package com.liferay.source.formatter.checkstyle.checks;

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.ScopeUtils;

/**
 * @author Hugo Huijser
 */
public class MissingModifierCheck extends AbstractCheck {

	public static final String MSG_MISSING_MODIFIER = "modifier.missing";

	@Override
	public int[] getDefaultTokens() {
		return new int[] {
			TokenTypes.CLASS_DEF, TokenTypes.CTOR_DEF, TokenTypes.ENUM_DEF,
			TokenTypes.INTERFACE_DEF, TokenTypes.METHOD_DEF,
			TokenTypes.VARIABLE_DEF
		};
	}

	@Override
	public void visitToken(DetailAST detailAST) {
		if (ScopeUtils.isLocalVariableDef(detailAST)) {
			return;
		}

		DetailAST modifiersAST = detailAST.findFirstToken(TokenTypes.MODIFIERS);

		if (modifiersAST.branchContains(TokenTypes.LITERAL_PRIVATE) ||
			modifiersAST.branchContains(TokenTypes.LITERAL_PROTECTED) ||
			modifiersAST.branchContains(TokenTypes.LITERAL_PUBLIC)) {

			return;
		}

		DetailAST nameAST = detailAST.findFirstToken(TokenTypes.IDENT);

		log(detailAST.getLineNo(), MSG_MISSING_MODIFIER, nameAST.getText());
	}

}