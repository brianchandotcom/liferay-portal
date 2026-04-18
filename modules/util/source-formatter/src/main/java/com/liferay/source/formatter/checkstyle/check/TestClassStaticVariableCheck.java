/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.checkstyle.check;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Alan Huang
 */
public class TestClassStaticVariableCheck extends BaseCheck {

	@Override
	public int[] getDefaultTokens() {
		return new int[] {TokenTypes.CLASS_DEF};
	}

	@Override
	protected void doVisitToken(DetailAST detailAST) {
		String absolutePath = getAbsolutePath();

		if (!absolutePath.contains("/test/") &&
			!absolutePath.contains("/testIntegration/")) {

			return;
		}

		String className = getName(detailAST);

		if (!className.matches(".*Test(Case)?")) {
			return;
		}

		DetailAST objBlockDetailAST = detailAST.findFirstToken(
			TokenTypes.OBJBLOCK);

		if (objBlockDetailAST == null) {
			return;
		}

		List<DetailAST> staticVariableDefDetailASTs =
			_getStaticVariableDefDetailASTs(objBlockDetailAST);

		if (staticVariableDefDetailASTs.isEmpty()) {
			return;
		}

		List<DetailAST> staticMemberDefDetailASTs =
			_getStaticMemberDefDetailASTs(objBlockDetailAST);

		for (DetailAST staticVariableDefDetailAST :
				staticVariableDefDetailASTs) {

			String variableName = getName(staticVariableDefDetailAST);

			if (_containsVariableName(
					staticMemberDefDetailASTs, variableName)) {

				continue;
			}

			log(
				staticVariableDefDetailAST, _MSG_UNNECESSARY_MODIFIER,
				variableName);
		}
	}

	private boolean _containsVariableName(
		List<DetailAST> detailASTs, String variableName) {

		for (DetailAST detailAST : detailASTs) {
			if (containsVariableName(detailAST, variableName)) {
				return true;
			}
		}

		return false;
	}

	private List<DetailAST> _getStaticMemberDefDetailASTs(DetailAST detailAST) {
		List<DetailAST> staticMemberDefDetailASTs = new ArrayList<>();

		List<DetailAST> childDetailASTs = getAllChildTokens(
			detailAST, false, TokenTypes.CLASS_DEF, TokenTypes.METHOD_DEF,
			TokenTypes.STATIC_INIT);

		for (DetailAST childDetailAST : childDetailASTs) {
			if (childDetailAST.getType() == TokenTypes.STATIC_INIT) {
				staticMemberDefDetailASTs.add(childDetailAST);

				continue;
			}

			DetailAST modifiersDetailAST = childDetailAST.findFirstToken(
				TokenTypes.MODIFIERS);

			if (!modifiersDetailAST.branchContains(TokenTypes.LITERAL_STATIC)) {
				continue;
			}

			staticMemberDefDetailASTs.add(childDetailAST);
		}

		return staticMemberDefDetailASTs;
	}

	private List<DetailAST> _getStaticVariableDefDetailASTs(
		DetailAST detailAST) {

		List<DetailAST> staticVariableDefDetailASTs = new ArrayList<>();

		List<DetailAST> variableDefDetailASTs = getAllChildTokens(
			detailAST, false, TokenTypes.VARIABLE_DEF);

		for (DetailAST variableDefDetailAST : variableDefDetailASTs) {
			DetailAST modifiersDetailAST = variableDefDetailAST.findFirstToken(
				TokenTypes.MODIFIERS);

			if (modifiersDetailAST.branchContains(TokenTypes.FINAL) ||
				!modifiersDetailAST.branchContains(
					TokenTypes.LITERAL_PRIVATE) ||
				!modifiersDetailAST.branchContains(TokenTypes.LITERAL_STATIC)) {

				continue;
			}

			staticVariableDefDetailASTs.add(variableDefDetailAST);
		}

		return staticVariableDefDetailASTs;
	}

	private static final String _MSG_UNNECESSARY_MODIFIER =
		"modifier.unnecessary";

}