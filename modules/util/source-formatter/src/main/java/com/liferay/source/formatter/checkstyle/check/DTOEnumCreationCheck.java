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

import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.util.StringUtil;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FullIdent;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import java.util.regex.Pattern;

/**
 * @author Alan Huang
 */
public class DTOEnumCreationCheck extends BaseCheck {

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

		FullIdent fullIdent = FullIdent.createFullIdent(firstChildDetailAST);

		String fullyQualifiedName = fullIdent.getText();

		if (StringUtil.count(fullyQualifiedName, CharPool.PERIOD) != 2) {
			return;
		}

		String fullyQualifiedTypeName = getFullyQualifiedTypeName(
			fullyQualifiedName, firstChildDetailAST, true);

		if (Pattern.matches(
				"com\\.liferay(\\.\\w+)+\\.v\\d+_\\d+(\\.\\w+){2}\\.valueOf",
				fullyQualifiedTypeName)) {

			log(detailAST, _MSG_USE_CREATE);
		}
	}

	private static final String _MSG_USE_CREATE = "create.use";

}