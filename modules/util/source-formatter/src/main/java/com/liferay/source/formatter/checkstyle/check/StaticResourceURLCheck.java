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

import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FullIdent;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import java.util.List;

/**
 * @author Qi Zhang
 */
public class StaticResourceURLCheck extends BaseCheck {

	@Override
	public int[] getDefaultTokens() {
		return new int[] {TokenTypes.METHOD_CALL};
	}

	@Override
	protected void doVisitToken(DetailAST detailAST) {
		DetailAST dotDetailAST = detailAST.findFirstToken(TokenTypes.DOT);

		if (dotDetailAST == null) {
			return;
		}

		FullIdent fullIdent = FullIdent.createFullIdent(dotDetailAST);

		if (!StringUtil.equals(
				fullIdent.getText(), "PortalUtil.getStaticResourceURL")) {

			return;
		}

		DetailAST eListDetailAST = detailAST.findFirstToken(TokenTypes.ELIST);

		if ((eListDetailAST == null) || (eListDetailAST.getChildCount() < 2)) {
			return;
		}

		DetailAST firstChildDetailAST = eListDetailAST.getFirstChild();

		int count = 1;

		while (true) {
			if (firstChildDetailAST == null) {
				return;
			}

			if ((count == 2) &&
				(firstChildDetailAST.getType() != TokenTypes.COMMA)) {

				break;
			}

			if (firstChildDetailAST.getType() != TokenTypes.COMMA) {
				count++;
			}

			firstChildDetailAST = firstChildDetailAST.getNextSibling();
		}

		_checkMethod(firstChildDetailAST);
	}

	private void _checkMethod(DetailAST detailAST) {
		if (detailAST.getType() != TokenTypes.EXPR) {
			return;
		}

		List<DetailAST> methodCallDetailASTs = getAllChildTokens(
			detailAST, true, TokenTypes.METHOD_CALL);

		String result = null;
		int lineNo = 0;

		for (DetailAST curDetailAST : methodCallDetailASTs) {
			DetailAST dotDetailAST = curDetailAST.findFirstToken(
				TokenTypes.DOT);

			if (dotDetailAST == null) {
				continue;
			}

			FullIdent fullIdent = FullIdent.createFullIdent(dotDetailAST);

			if (StringUtil.equals(
					"PortalUtil.getPathProxy", fullIdent.getText())) {

				return;
			}

			if (_checkMethodName(curDetailAST)) {
				lineNo = getStartLineNumber(curDetailAST);

				fullIdent = FullIdent.createFullIdent(dotDetailAST);

				result = fullIdent.getText();
			}
		}

		if (Validator.isNotNull(result) && (lineNo > 0)) {
			log(lineNo, _MSG_ADD_PATH_PROXY, result);
		}
	}

	private boolean _checkMethodName(DetailAST detailAST) {
		if (StringUtil.equals("getContextPath", getMethodName(detailAST))) {
			DetailAST dotDetailAST = detailAST.findFirstToken(TokenTypes.DOT);

			if (dotDetailAST == null) {
				return false;
			}

			DetailAST eListDetailAST = detailAST.findFirstToken(
				TokenTypes.ELIST);

			if ((eListDetailAST != null) &&
				(eListDetailAST.getChildCount() > 0)) {

				return false;
			}

			return true;
		}

		return false;
	}

	private static final String _MSG_ADD_PATH_PROXY = "add.path.proxy";

}