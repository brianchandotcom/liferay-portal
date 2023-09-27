/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

		if (Pattern.matches(
				"com\\.liferay(\\.\\w+)+\\.v\\d+_\\d+(\\.\\w+){2}\\.valueOf",
				getFullyQualifiedTypeName(
					fullyQualifiedName, firstChildDetailAST, true))) {

			log(detailAST, _MSG_USE_CREATE);
		}
	}

	private static final String _MSG_USE_CREATE = "create.use";

}