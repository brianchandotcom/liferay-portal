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

import java.util.Objects;

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

		if (absolutePath.contains("/upgrade/") ||
			absolutePath.contains("/verify/")) {

			return;
		}

		DetailAST forEachClauseDetailAST = detailAST.findFirstToken(
			TokenTypes.FOR_EACH_CLAUSE);

		if (forEachClauseDetailAST == null) {
			return;
		}

		DetailAST methodCallDetailAST = _getIteratorMethodCallDetailAST(
			forEachClauseDetailAST);

		if (methodCallDetailAST != null) {
			DetailAST firstChildDetailAST = methodCallDetailAST.getFirstChild();

			if (firstChildDetailAST.getType() == TokenTypes.DOT) {
				firstChildDetailAST = firstChildDetailAST.getFirstChild();

				if ((firstChildDetailAST.getType() == TokenTypes.IDENT) &&
					Objects.equals(
						firstChildDetailAST.getText(), "PortalInstances")) {

					return;
				}
			}
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

	private DetailAST _getIteratorMethodCallDetailAST(
		DetailAST forEachClauseDetailAST) {

		DetailAST lastChildDetailAST = forEachClauseDetailAST.getLastChild();

		if (lastChildDetailAST.getType() != TokenTypes.EXPR) {
			return null;
		}

		DetailAST firstChildDetailAST = lastChildDetailAST.getFirstChild();

		if (firstChildDetailAST.getType() == TokenTypes.METHOD_CALL) {
			return firstChildDetailAST;
		}

		if (firstChildDetailAST.getType() != TokenTypes.IDENT) {
			return null;
		}

		String variableName = firstChildDetailAST.getText();

		DetailAST typeDetailAST = getVariableTypeDetailAST(
			firstChildDetailAST, variableName, false);

		if (typeDetailAST == null) {
			return null;
		}

		DetailAST parentDetailAST = typeDetailAST.getParent();

		if (parentDetailAST.getType() != TokenTypes.VARIABLE_DEF) {
			return null;
		}

		DetailAST assignDetailAST = parentDetailAST.findFirstToken(
			TokenTypes.ASSIGN);

		if (assignDetailAST == null) {
			return null;
		}

		firstChildDetailAST = assignDetailAST.getFirstChild();

		if (firstChildDetailAST.getType() != TokenTypes.EXPR) {
			return null;
		}

		firstChildDetailAST = firstChildDetailAST.getFirstChild();

		if (firstChildDetailAST.getType() == TokenTypes.METHOD_CALL) {
			return firstChildDetailAST;
		}

		return null;
	}

	private static final String _MSG_USE_COMPANY_LOCAL_SERVICE =
		"company.local.service.use";

}