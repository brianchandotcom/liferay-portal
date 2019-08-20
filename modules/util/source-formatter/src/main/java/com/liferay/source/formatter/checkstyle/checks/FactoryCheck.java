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

import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.source.formatter.checkstyle.util.DetailASTUtil;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FullIdent;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import java.util.List;

/**
 * @author Hugo Huijser
 */
public class FactoryCheck extends BaseCheck {

	@Override
	public int[] getDefaultTokens() {
		return new int[] {TokenTypes.LITERAL_NEW};
	}

	@Override
	protected void doVisitToken(DetailAST detailAST) {
		for (String enforceFactoryClassNames :
				getAttributeValues(_ENFORCE_FACTORY_CLASS_NAMES_KEY)) {

			_checkEnforceFactory(
				detailAST,
				StringUtil.split(enforceFactoryClassNames, CharPool.COLON));
		}
	}

	private void _checkEnforceFactory(
		DetailAST detailAST, String... classNames) {

		if (classNames.length != 2) {
			return;
		}

		DetailAST firstChildDetailAST = detailAST.getFirstChild();

		if (firstChildDetailAST == null) {
			return;
		}

		String className = classNames[0];

		if (firstChildDetailAST.getType() == TokenTypes.DOT) {
			FullIdent fullIdent = FullIdent.createFullIdent(
				firstChildDetailAST);

			if (className.equals(fullIdent.getText())) {
				log(detailAST, _MSG_USE_FACTORY, classNames[1], className);
			}
		}
		else if ((firstChildDetailAST.getType() == TokenTypes.IDENT) &&
				 className.endsWith("." + firstChildDetailAST.getText())) {

			List<String> importNames = DetailASTUtil.getImportNames(detailAST);

			if (importNames.contains(className)) {
				log(detailAST, _MSG_USE_FACTORY, classNames[1], className);
			}
		}
	}

	private static final String _ENFORCE_FACTORY_CLASS_NAMES_KEY =
		"enforceFactoryClassNames";

	private static final String _MSG_USE_FACTORY = "factory.use";

}