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

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.source.formatter.checkstyle.util.DetailASTUtil;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FullIdent;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

			if (detailAST.getType() != TokenTypes.METHOD_DEF) {
				continue;
			}

			DetailAST classDefAST = DetailASTUtil.getParentWithTokenType(
				detailAST, TokenTypes.CLASS_DEF);

			if (classDefAST == null) {
				continue;
			}

			_checkUnbind(classDefAST, detailAST, annotationAST, policyName);
			_checkVolitileReferenceVariable(classDefAST, detailAST);
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

	private void _checkUnbind(
		DetailAST classDefAST, DetailAST detailAST, DetailAST annotationAST,
		String policyName) {

		DetailAST identAST = detailAST.findFirstToken(TokenTypes.IDENT);

		String methodName = identAST.getText();

		String defaultUnbindMethodName = _getDefaultUnbindMethodName(
			methodName);

		String unbindName = _getAnnotationMemberValue(
			annotationAST, "unbind", null);

		if (unbindName == null) {
			if (!_containsMethod(classDefAST, defaultUnbindMethodName)) {
				if (policyName.endsWith(_POLICY_DYNAMIC)) {
					log(
						annotationAST.getLineNo(),
						_MSG_MISSING_DYNAMIC_POLICY_UNBIND);
				}
				else {
					log(
						annotationAST.getLineNo(),
						_MSG_MISSING_STATIC_POLICY_UNBIND, _NO_UNBIND);
				}
			}
		}
		else if (unbindName.equals("\"" + defaultUnbindMethodName + "\"")) {
			log(annotationAST.getLineNo(), _MSG_REDUNDANT_DEFAULT_UNBIND);
		}
		else if (unbindName.equals(_NO_UNBIND) &&
				 policyName.endsWith(_POLICY_DYNAMIC)) {

			log(annotationAST.getLineNo(), _MSG_MISSING_DYNAMIC_POLICY_UNBIND);
		}
	}

	private void _checkVolitileReferenceVariable(
		DetailAST classDefAST, DetailAST methodDefAST) {

		String methodBody = _getMethodBody(methodDefAST);

		Matcher matcher = _referenceMethodContentPattern.matcher(
			StringUtil.trim(methodBody));

		if (!matcher.find()) {
			return;
		}

		String variableName = matcher.group(1);

		List<DetailAST> variableDefASTList = DetailASTUtil.getAllChildTokens(
			classDefAST, true, TokenTypes.VARIABLE_DEF);

		for (DetailAST variableDefAST : variableDefASTList) {
			DetailAST identAST = variableDefAST.findFirstToken(
				TokenTypes.IDENT);

			if (!variableName.equals(identAST.getText())) {
				continue;
			}

			DetailAST modifiersAST = variableDefAST.findFirstToken(
				TokenTypes.MODIFIERS);

			if (modifiersAST.branchContains(TokenTypes.LITERAL_PRIVATE) &&
				modifiersAST.branchContains(TokenTypes.LITERAL_VOLATILE)) {

				log(
					variableDefAST.getLineNo(), _MSG_MISSING_VOLATILE,
					variableName);

				return;
			}
		}
	}

	private boolean _containsMethod(DetailAST classDefAST, String methodName) {
		List<DetailAST> methodDefASTList = DetailASTUtil.getAllChildTokens(
			classDefAST, true, TokenTypes.METHOD_DEF);

		for (DetailAST methodDefAST : methodDefASTList) {
			DetailAST identAST = methodDefAST.findFirstToken(TokenTypes.IDENT);

			if (methodName.equals(identAST.getText())) {
				return true;
			}
		}

		return false;
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

	private String _getDefaultUnbindMethodName(String methodName) {
		if (methodName.startsWith("add")) {
			return StringUtil.replaceFirst(methodName, "add", "remove");
		}

		return "un" + methodName;
	}

	private String _getMethodBody(DetailAST methodDefAST) {
		DetailAST slistAST = methodDefAST.findFirstToken(TokenTypes.SLIST);

		int start = DetailASTUtil.getStartLine(slistAST);
		int end = DetailASTUtil.getEndLine(slistAST);

		StringBundler sb = new StringBundler((end - start - 1) * 2);

		for (int i = start + 1; i < end; i++) {
			sb.append(getLine(i - 1));
			sb.append("\n");
		}

		return sb.toString();
	}

	private static final String _MSG_INCORRECT_GREEDY_POLICY_OPTION =
		"greedy.policy.option.incorrect";

	private static final String _MSG_MISSING_DYNAMIC_POLICY_UNBIND =
		"unbind.dynamic.policy.missing";

	private static final String _MSG_MISSING_STATIC_POLICY_UNBIND =
		"unbind.static.policy.missing";

	private static final String _MSG_MISSING_VOLATILE = "volatile.missing";

	private static final String _MSG_REDUNDANT_DEFAULT_UNBIND =
		"default.unbind.redundant";

	private static final String _NO_UNBIND = "\"-\"";

	private static final String _POLICY_DYNAMIC = "DYNAMIC";

	private static final String _POLICY_OPTION_GREEDY = "GREEDY";

	private static final String _POLICY_OPTION_RELUCTANT = "RELUCTANT";

	private static final String _POLICY_STATIC = "STATIC";

	private final Pattern _referenceMethodContentPattern = Pattern.compile(
		"^(\\w+) =\\s+\\w+;$");

}