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
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.source.formatter.checks.util.SourceUtil;
import com.liferay.source.formatter.checkstyle.util.DetailASTUtil;
import com.liferay.source.formatter.util.FileUtil;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FileContents;
import com.puppycrawl.tools.checkstyle.api.FullIdent;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Hugo Huijser
 */
public class ListUtilCheck extends BaseCheck {

	@Override
	public int[] getDefaultTokens() {
		return new int[] {TokenTypes.METHOD_CALL, TokenTypes.VARIABLE_DEF};
	}

	@Override
	protected void doVisitToken(DetailAST detailAST) {
		if (detailAST.getType() == TokenTypes.METHOD_CALL) {
			_checkFromArrayCall(detailAST);

			return;
		}

		if (!Objects.equals(
				DetailASTUtil.getTypeName(detailAST, false), "List") ||
			!_isAssignNewArrayList(detailAST)) {

			return;
		}

		DetailAST nextStatementDetailAST = _getNextStatementDetailAST(
			detailAST);

		if (nextStatementDetailAST == null) {
			return;
		}

		DetailAST nameDetailAST = detailAST.findFirstToken(TokenTypes.IDENT);

		String variableName = nameDetailAST.getText();

		if (!_isAddMethodCall(
				nextStatementDetailAST.getFirstChild(), variableName)) {

			return;
		}

		nextStatementDetailAST = _getNextStatementDetailAST(
			nextStatementDetailAST);

		if ((nextStatementDetailAST == null) ||
			nextStatementDetailAST.branchContains(TokenTypes.LCURLY) ||
			nextStatementDetailAST.branchContains(TokenTypes.SLIST)) {

			return;
		}

		List<DetailAST> identDetailASTList = _getIdentDetailASTList(
			nextStatementDetailAST, variableName);

		if (identDetailASTList.size() != 1) {
			return;
		}

		DetailAST identDetailAST = identDetailASTList.get(0);

		DetailAST parentDetailAST = identDetailAST.getParent();

		if (parentDetailAST.getType() != TokenTypes.EXPR) {
			return;
		}

		while (true) {
			nextStatementDetailAST = _getNextStatementDetailAST(
				nextStatementDetailAST);

			if (nextStatementDetailAST == null) {
				break;
			}

			identDetailASTList = _getIdentDetailASTList(
				nextStatementDetailAST, variableName);

			if (!identDetailASTList.isEmpty()) {
				return;
			}
		}

		FileContents fileContents = getFileContents();

		String fileName = StringUtil.replace(
			fileContents.getFileName(), CharPool.BACK_SLASH, CharPool.SLASH);

		String absolutePath = SourceUtil.getAbsolutePath(fileName);

		if (absolutePath.contains("/modules/")) {
			String buildGradleContent = _getBuildGradleContent(absolutePath);

			if ((buildGradleContent == null) ||
				!buildGradleContent.contains("com.liferay.portal.kernel")) {

				return;
			}
		}

		log(detailAST, _MSG_USE_LIST_UTIL);
	}

	private void _checkFromArrayCall(DetailAST methodCallDetailAST) {
		DetailAST firstChildDetailAST = methodCallDetailAST.getFirstChild();

		if (firstChildDetailAST.getType() != TokenTypes.DOT) {
			return;
		}

		FullIdent fullIdent = FullIdent.createFullIdent(firstChildDetailAST);

		if (!Objects.equals(fullIdent.getText(), "ListUtil.fromArray")) {
			return;
		}

		DetailAST elistDetailAST = methodCallDetailAST.findFirstToken(
			TokenTypes.ELIST);

		List<DetailAST> exprDetailASTList = DetailASTUtil.getAllChildTokens(
			elistDetailAST, false, TokenTypes.EXPR);

		if (exprDetailASTList.size() != 1) {
			return;
		}

		DetailAST exprDetailAST = exprDetailASTList.get(0);

		firstChildDetailAST = exprDetailAST.getFirstChild();

		if (firstChildDetailAST.getType() != TokenTypes.LITERAL_NEW) {
			return;
		}

		DetailAST lastChildDetailAST = firstChildDetailAST.getLastChild();

		if (lastChildDetailAST.getType() == TokenTypes.ARRAY_INIT) {
			log(methodCallDetailAST, _MSG_UNNEEDED_ARRAY);
		}
	}

	private String _getBuildGradleContent(String absolutePath) {
		String buildGradleLocation = absolutePath;

		while (true) {
			int pos = buildGradleLocation.lastIndexOf(StringPool.SLASH);

			if (pos == -1) {
				return null;
			}

			buildGradleLocation = buildGradleLocation.substring(0, pos + 1);

			String buildGradleContent = _buildGradleContentsMap.get(
				buildGradleLocation);

			if (buildGradleContent != null) {
				return buildGradleContent;
			}

			File file = new File(buildGradleLocation + "build.gradle");

			if (file.exists()) {
				try {
					buildGradleContent = FileUtil.read(file);

					_buildGradleContentsMap.put(
						buildGradleLocation, buildGradleContent);

					return buildGradleContent;
				}
				catch (IOException ioe) {
					return null;
				}
			}

			buildGradleLocation = StringUtil.replaceLast(
				buildGradleLocation, CharPool.SLASH, StringPool.BLANK);
		}
	}

	private List<DetailAST> _getIdentDetailASTList(
		DetailAST detailAST, String name) {

		List<DetailAST> identDetailASTList = new ArrayList<>();

		List<DetailAST> childDetailASTList = DetailASTUtil.getAllChildTokens(
			detailAST, true, TokenTypes.IDENT);

		for (DetailAST childDetailAST : childDetailASTList) {
			if (name.equals(childDetailAST.getText())) {
				identDetailASTList.add(childDetailAST);
			}
		}

		return identDetailASTList;
	}

	private DetailAST _getNextStatementDetailAST(DetailAST detailAST) {
		DetailAST nextSiblingDetailAST = detailAST.getNextSibling();

		while (true) {
			if ((nextSiblingDetailAST == null) ||
				(nextSiblingDetailAST.getType() != TokenTypes.SEMI)) {

				return nextSiblingDetailAST;
			}

			nextSiblingDetailAST = nextSiblingDetailAST.getNextSibling();
		}
	}

	private boolean _isAddMethodCall(DetailAST detailAST, String variableName) {
		if ((detailAST == null) ||
			(detailAST.getType() != TokenTypes.METHOD_CALL)) {

			return false;
		}

		DetailAST firstChildDetailAST = detailAST.getFirstChild();

		if (firstChildDetailAST.getType() != TokenTypes.DOT) {
			return false;
		}

		FullIdent fullIdent = FullIdent.createFullIdent(firstChildDetailAST);

		if (!Objects.equals(fullIdent.getText(), variableName + ".add")) {
			return false;
		}

		DetailAST elistDetailAST = detailAST.findFirstToken(TokenTypes.ELIST);

		if (elistDetailAST.getChildCount() != 1) {
			return true;
		}

		firstChildDetailAST = elistDetailAST.getFirstChild();

		if (firstChildDetailAST.getType() == TokenTypes.EXPR) {
			firstChildDetailAST = firstChildDetailAST.getFirstChild();

			if (firstChildDetailAST.getType() == TokenTypes.LITERAL_NULL) {
				return false;
			}
		}

		return true;
	}

	private boolean _isAssignNewArrayList(DetailAST detailAST) {
		DetailAST assignDetailAST = detailAST.findFirstToken(TokenTypes.ASSIGN);

		if (assignDetailAST == null) {
			return false;
		}

		DetailAST firstChildDetailAST = assignDetailAST.getFirstChild();

		if (firstChildDetailAST.getType() != TokenTypes.EXPR) {
			return false;
		}

		firstChildDetailAST = firstChildDetailAST.getFirstChild();

		if (firstChildDetailAST.getType() != TokenTypes.LITERAL_NEW) {
			return false;
		}

		DetailAST identDetailAST = firstChildDetailAST.getFirstChild();

		if ((identDetailAST.getType() != TokenTypes.IDENT) ||
			!Objects.equals(identDetailAST.getText(), "ArrayList")) {

			return false;
		}

		DetailAST elistDetailAST = firstChildDetailAST.findFirstToken(
			TokenTypes.ELIST);

		if (elistDetailAST == null) {
			return false;
		}

		firstChildDetailAST = elistDetailAST.getFirstChild();

		if (firstChildDetailAST == null) {
			return true;
		}

		firstChildDetailAST = firstChildDetailAST.getFirstChild();

		if (firstChildDetailAST.getType() == TokenTypes.NUM_INT) {
			return true;
		}

		return false;
	}

	private static final String _MSG_UNNEEDED_ARRAY = "array.unneeded";

	private static final String _MSG_USE_LIST_UTIL = "list.util.use";

	private final Map<String, String> _buildGradleContentsMap =
		new ConcurrentHashMap<>();

}