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

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * @author Hugo Huijser
 */
public class CompanyIterationCheck extends BaseCheck {

	@Override
	public int[] getDefaultTokens() {
		return new int[] {TokenTypes.LITERAL_FOR};
	}

	@Override
	protected void doVisitToken(DetailAST detailAST) {
		String absolutePath = getAbsolutePath();

		if (absolutePath.contains("com/liferay/portal/") &&
			absolutePath.contains("/upgrade/")) {

			return;
		}

		DetailAST forEachClauseDetailAST = detailAST.findFirstToken(
			TokenTypes.FOR_EACH_CLAUSE);

		if (forEachClauseDetailAST == null) {
			return;
		}

		DetailAST variableDefinitionDetailAST =
			forEachClauseDetailAST.findFirstToken(TokenTypes.VARIABLE_DEF);

		String typeName = getTypeName(
			variableDefinitionDetailAST.findFirstToken(TokenTypes.TYPE), true);

		DetailAST nameDetailAST = variableDefinitionDetailAST.findFirstToken(
			TokenTypes.IDENT);

		String variableName = nameDetailAST.getText();

		if (typeName.equals("Company")) {
			log(
				detailAST, _MSG_USE_COMPANY_LOCAL_SERVICE, "forEachCompany",
				typeName + " " + variableName);
		}
		else if ((typeName.equals("Long") || typeName.equals("long")) &&
				 variableName.equals("companyId")) {

			log(
				detailAST, _MSG_USE_COMPANY_LOCAL_SERVICE, "forEachCompanyId",
				typeName + " " + variableName);
		}
	}

	private static final String _MSG_USE_COMPANY_LOCAL_SERVICE =
		"company.local.service.use";

}