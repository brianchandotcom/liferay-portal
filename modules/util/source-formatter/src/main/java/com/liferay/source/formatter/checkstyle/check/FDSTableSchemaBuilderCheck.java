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

package com.liferay.source.formatter.checkstyle.check;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * @author Alan Huang
 */
public class FDSTableSchemaBuilderCheck extends BaseCheck {

	@Override
	public int[] getDefaultTokens() {
		return new int[] {TokenTypes.METHOD_CALL};
	}

	@Override
	protected void doVisitToken(DetailAST detailAST) {
		DetailAST firstChildDetailAST = detailAST.getFirstChild();

		if (firstChildDetailAST.getType() != TokenTypes.DOT) {
			return;
		}

		firstChildDetailAST = firstChildDetailAST.getFirstChild();

		if (firstChildDetailAST.getType() != TokenTypes.IDENT) {
			return;
		}

		String variableTypeName = getVariableTypeName(
			detailAST, firstChildDetailAST.getText(), false);

		if (!variableTypeName.equals("FDSTableSchemaBuilder")) {
			return;
		}

		String methodName = getMethodName(detailAST);

		if (methodName.equals("addFDSTableSchemaField")) {
			log(detailAST, _MSG_USE_ADD);
		}
	}

	private static final String _MSG_USE_ADD = "add.use";

}