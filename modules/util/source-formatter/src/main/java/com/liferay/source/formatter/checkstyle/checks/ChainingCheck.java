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

import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.source.formatter.checkstyle.util.DetailASTUtil;

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FileContents;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import java.util.List;

/**
 * @author Hugo Huijser
 */
public class ChainingCheck extends AbstractCheck {

	public static final String MSG_AVOID_CHAINING = "chaining.avoid";

	public static final String MSG_AVOID_CHAINING_MULTIPLE =
		"chaining.avoid.multiple";

	public static final String MSG_AVOID_TOO_MANY_CONCAT =
		"concat.avoid.too.many";

	@Override
	public int[] getDefaultTokens() {
		return new int[] {TokenTypes.CTOR_DEF, TokenTypes.METHOD_DEF};
	}

	public void setChainingAllowedFormat(String chainingAllowedFormat) {
		_chainingAllowedFormat = chainingAllowedFormat;
	}

	@Override
	public void visitToken(DetailAST detailAST) {
		FileContents fileContents = getFileContents();

		String fileName = StringUtil.replace(
			fileContents.getFileName(), CharPool.BACK_SLASH, CharPool.SLASH);

		if (fileName.contains("/test/")) {
			return;
		}

		List<DetailAST> methodCallASTList = DetailASTUtil.getAllChildTokens(
			detailAST, true, TokenTypes.METHOD_CALL);

		for (DetailAST methodCallAST : methodCallASTList) {
			List<DetailAST> childMethodCallASTList =
				DetailASTUtil.getAllChildTokens(
					methodCallAST, true, TokenTypes.METHOD_CALL);

			// Only check the method that is first in the chain

			if (!childMethodCallASTList.isEmpty()) {
				continue;
			}

			String chainedMethodNames = _getChainedMethodNames(methodCallAST);

			if (!chainedMethodNames.contains(StringPool.PERIOD)) {
				continue;
			}

			_checkMethodName(
				chainedMethodNames, "getClass", methodCallAST, detailAST);

			if (StringUtil.count(chainedMethodNames, StringPool.PERIOD) == 1) {
				continue;
			}

			if (chainedMethodNames.contains("concat.concat.concat")) {
				log(methodCallAST.getLineNo(), MSG_AVOID_TOO_MANY_CONCAT);

				continue;
			}

			if (!chainedMethodNames.contains("concat.concat") &&
				!chainedMethodNames.matches(_chainingAllowedFormat)) {

				log(
					methodCallAST.getLineNo(), MSG_AVOID_CHAINING_MULTIPLE,
					DetailASTUtil.getMethodName(methodCallAST));
			}
		}
	}

	private void _checkMethodName(
		String chainedMethodNames, String methodName, DetailAST methodCallAST,
		DetailAST detailAST) {

		if (chainedMethodNames.matches(methodName + "\\..*") &&
			!_isInsideConstructorThisCall(methodCallAST, detailAST)) {

			log(methodCallAST.getLineNo(), MSG_AVOID_CHAINING, methodName);
		}
	}

	private String _getChainedMethodNames(DetailAST methodCallAST) {
		StringBundler sb = new StringBundler();

		sb.append(DetailASTUtil.getMethodName(methodCallAST));

		while (true) {
			DetailAST parentAST = methodCallAST.getParent();

			if (parentAST.getType() != TokenTypes.DOT) {
				return sb.toString();
			}

			methodCallAST = parentAST.getParent();

			if (methodCallAST.getType() != TokenTypes.METHOD_CALL) {
				return sb.toString();
			}

			sb.append(StringPool.PERIOD);
			sb.append(DetailASTUtil.getMethodName(methodCallAST));
		}
	}

	private boolean _isInsideConstructorThisCall(
		DetailAST methodCallAST, DetailAST detailAST) {

		if (detailAST.getType() != TokenTypes.CTOR_DEF) {
			return false;
		}

		DetailAST parentAST = methodCallAST.getParent();

		while (parentAST != null) {
			String parentASTText = parentAST.getText();

			if ((parentAST.getType() == TokenTypes.CTOR_CALL) &&
				parentASTText.equals("this")) {

				return true;
			}

			parentAST = parentAST.getParent();
		}

		return false;
	}

	private String _chainingAllowedFormat;

}