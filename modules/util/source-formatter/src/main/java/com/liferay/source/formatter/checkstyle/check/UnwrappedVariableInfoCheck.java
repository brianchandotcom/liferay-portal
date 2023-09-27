/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.checkstyle.check;

import com.liferay.portal.kernel.util.StringUtil;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * @author Hugo Huijser
 */
public class UnwrappedVariableInfoCheck extends BaseCheck {

	@Override
	public int[] getDefaultTokens() {
		return new int[] {TokenTypes.VARIABLE_DEF};
	}

	@Override
	protected void doVisitToken(DetailAST detailAST) {
		String absolutePath = getAbsolutePath();

		if (!absolutePath.endsWith("Tei.java")) {
			return;
		}

		String line = getLine(detailAST.getLineNo() - 1);

		if (!line.contains("private static final VariableInfo[]")) {
			return;
		}

		DetailAST parentDetailAST = detailAST.getParent();

		while (true) {
			if (parentDetailAST == null) {
				return;
			}

			if (parentDetailAST.getType() == TokenTypes.CLASS_DEF) {
				if (StringUtil.equals(getName(parentDetailAST), "Concealer")) {
					return;
				}

				break;
			}

			parentDetailAST = parentDetailAST.getParent();
		}

		log(detailAST, _MSG_UNWRAPPED_VARIABLE_INFO, getName(detailAST));
	}

	private static final String _MSG_UNWRAPPED_VARIABLE_INFO =
		"variable.info.unwrapped";

}