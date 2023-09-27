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
public class NumberSuffixCheck extends BaseCheck {

	@Override
	public int[] getDefaultTokens() {
		return new int[] {
			TokenTypes.NUM_DOUBLE, TokenTypes.NUM_FLOAT, TokenTypes.NUM_LONG
		};
	}

	@Override
	protected void doVisitToken(DetailAST detailAST) {
		if (detailAST.getType() == TokenTypes.NUM_DOUBLE) {
			_checkType(detailAST, "double", "d");
		}
		else if (detailAST.getType() == TokenTypes.NUM_FLOAT) {
			_checkType(detailAST, "float", "f");
		}
		else if (detailAST.getType() == TokenTypes.NUM_LONG) {
			_checkType(detailAST, "long", "l");
		}
	}

	private void _checkType(DetailAST detailAST, String type, String suffix) {
		String text = detailAST.getText();

		if (text.endsWith(suffix)) {
			log(
				detailAST, _MSG_INCORRECT_SUFFIX,
				StringUtil.toUpperCase(suffix), type);
		}
	}

	private static final String _MSG_INCORRECT_SUFFIX = "suffix.incorrect";

}