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

import com.liferay.source.formatter.checkstyle.util.DetailASTUtil;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FullIdent;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import java.util.List;

/**
 * @author Hugo Huijser
 */
public class ReferenceAnnotationCheck extends BaseCheck {

	@Override
	public int[] getDefaultTokens() {
		return new int[] {TokenTypes.METHOD_DEF, TokenTypes.VARIABLE_DEF};
	}

	@Override
	protected void doVisitToken(DetailAST detailAST) {
		List<DetailAST> annotationASTList = DetailASTUtil.getAllChildTokens(
			detailAST, true, TokenTypes.ANNOTATION);

		for (DetailAST annotationAST : annotationASTList) {
			DetailAST identAST = annotationAST.findFirstToken(TokenTypes.IDENT);

			if (identAST == null) {
				continue;
			}

			String name = identAST.getText();

			if (!name.equals("Reference")) {
				continue;
			}

			String policyName = _getAnnotationMemberValue(
				annotationAST, "policy", _POLICY_STATIC);

			_checkGreedyOption(annotationAST, policyName);
		}
	}

	private void _checkGreedyOption(
		DetailAST annotationAST, String policyName) {

		String policyOptionName = _getAnnotationMemberValue(
			annotationAST, "policyOption", _POLICY_OPTION_RELUCTANT);

		if (policyOptionName.endsWith(_POLICY_OPTION_GREEDY) &&
			policyName.endsWith(_POLICY_STATIC)) {

			log(annotationAST.getLineNo(), _MSG_INCORRECT_GREEDY_POLICY_OPTION);
		}
	}

	private String _getAnnotationMemberValue(
		DetailAST anontationAST, String name, String defaultValue) {

		List<DetailAST> annotationMemberValuePairASTList =
			DetailASTUtil.getAllChildTokens(
				anontationAST, false, TokenTypes.ANNOTATION_MEMBER_VALUE_PAIR);

		for (DetailAST annotationMemberValuePairAST :
				annotationMemberValuePairASTList) {

			DetailAST identAST = annotationMemberValuePairAST.findFirstToken(
				TokenTypes.IDENT);

			String annotationMemberName = identAST.getText();

			if (!annotationMemberName.equals(name)) {
				continue;
			}

			DetailAST expressionAST =
				annotationMemberValuePairAST.findFirstToken(TokenTypes.EXPR);

			if (expressionAST == null) {
				return null;
			}

			FullIdent expressionIdent = FullIdent.createFullIdentBelow(
				expressionAST);

			return expressionIdent.getText();
		}

		return defaultValue;
	}

	private static final String _MSG_INCORRECT_GREEDY_POLICY_OPTION =
		"greedy.policy.option.incorrect";

	private static final String _POLICY_OPTION_GREEDY = "GREEDY";

	private static final String _POLICY_OPTION_RELUCTANT = "RELUCTANT";

	private static final String _POLICY_STATIC = "STATIC";

}