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

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.AnnotationUtil;

import java.util.List;

/**
 * @author Alan Huang
 */
public class ModifiedMethodCheck extends BaseCheck {

	@Override
	public int[] getDefaultTokens() {
		return new int[] {TokenTypes.CLASS_DEF};
	}

	@Override
	protected void doVisitToken(DetailAST detailAST) {
		List<String> importNames = getImportNames(detailAST);

		if (!importNames.contains(
				"org.osgi.service.component.annotations.Modified")) {

			return;
		}

		DetailAST parentDetailAST = detailAST.getParent();

		if (parentDetailAST != null) {
			return;
		}

		DetailAST objBlockDetailAST = detailAST.findFirstToken(
			TokenTypes.OBJBLOCK);

		List<DetailAST> methodDefinitionDetailASTList = getAllChildTokens(
			objBlockDetailAST, false, TokenTypes.METHOD_DEF);

		for (DetailAST methodDefinitionDetailAST :
				methodDefinitionDetailASTList) {

			if (!StringUtil.equals(
					getName(methodDefinitionDetailAST), "modified")) {

				continue;
			}

			DetailAST modifiersDetailAST =
				methodDefinitionDetailAST.findFirstToken(TokenTypes.MODIFIERS);

			if (!AnnotationUtil.containsAnnotation(
					methodDefinitionDetailAST, "Modified") ||
				!modifiersDetailAST.branchContains(
					TokenTypes.LITERAL_PROTECTED) ||
				!StringUtil.equals(
					getTypeName(methodDefinitionDetailAST, false), "void")) {

				continue;
			}

			DetailAST slistDetailAST = methodDefinitionDetailAST.findFirstToken(
				TokenTypes.SLIST);

			List<DetailAST> exprDetailASTList = getAllChildTokens(
				slistDetailAST, false, TokenTypes.EXPR);

			if (exprDetailASTList.size() != 2) {
				continue;
			}

			DetailAST exprDetailAST = exprDetailASTList.get(0);

			DetailAST firstChildDetailAST = exprDetailAST.getFirstChild();

			if ((firstChildDetailAST.getType() != TokenTypes.METHOD_CALL) ||
				!StringUtil.equals(
					getName(firstChildDetailAST), "deactivate")) {

				continue;
			}

			exprDetailAST = exprDetailASTList.get(1);

			firstChildDetailAST = exprDetailAST.getFirstChild();

			if ((firstChildDetailAST.getType() != TokenTypes.METHOD_CALL) ||
				!StringUtil.equals(getName(firstChildDetailAST), "activate")) {

				continue;
			}

			log(methodDefinitionDetailAST, _MSG_INCORRECT_MODIFIED_METHOD);
		}
	}

	private static final String _MSG_INCORRECT_MODIFIED_METHOD =
		"modified.method.incorrect";

}