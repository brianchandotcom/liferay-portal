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
		return new int[] {TokenTypes.ANNOTATION};
	}

	@Override
	protected void doVisitToken(DetailAST detailAST) {
		DetailAST identAST = detailAST.findFirstToken(TokenTypes.IDENT);

		if (identAST == null) {
			return;
		}

		String name = identAST.getText();

		if (!name.equals("Reference")) {
			return;
		}

		String policyOptionName = _getAnnotationMemberValue(
			detailAST, "policyOption");

		if ((policyOptionName == null) ||
			!policyOptionName.endsWith("GREEDY")) {

			return;
		}

		String policyName = _getAnnotationMemberValue(detailAST, "policy");

		if ((policyName == null) || !policyName.endsWith("DYNAMIC")) {
			log(detailAST.getLineNo(), _MSG_INCORRECT_GREEDY_POLICY_OPTION);
		}
	}

	private String _getAnnotationMemberValue(
		DetailAST anontationAST, String name) {

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

		return null;
	}

	private static final String _MSG_INCORRECT_GREEDY_POLICY_OPTION =
		"greedy.policy.option.incorrect";

}