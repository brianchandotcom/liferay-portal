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

import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.poshi.core.util.ListUtil;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FullIdent;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import java.util.List;

/**
 * @author Qi Zhang
 */
public class ToJSONStringMethodCheck extends BaseCheck {

	@Override
	public int[] getDefaultTokens() {
		return new int[] {TokenTypes.METHOD_CALL, TokenTypes.METHOD_DEF};
	}

	@Override
	protected void doVisitToken(DetailAST detailAST) {
		if (StringUtil.equals("toJSONString", getMethodName(detailAST))) {
			List<DetailAST> childDetailASTList = getAllChildTokens(
				detailAST, true, TokenTypes.METHOD_CALL);

			if (!ListUtil.isEmpty(childDetailASTList) &&
				(childDetailASTList.size() == 1)) {

				for (DetailAST tmpDetailAST : childDetailASTList) {
					DetailAST childDetailAST = tmpDetailAST.getFirstChild();

					if ((childDetailAST != null) &&
						(childDetailAST.getType() == TokenTypes.IDENT)) {

						String methodCallName = getMethodName(tmpDetailAST);

						if (_checkMethodReturnType(detailAST, methodCallName)) {
							log(detailAST, _MSG_JSON_TO_STRING_USE);

							return;
						}
					}
				}
			}

			String variableName = getVariableName(detailAST);

			String variableTypeName = getVariableTypeName(
				detailAST, variableName, true);

			if (Validator.isNotNull(variableTypeName)) {
				if (ArrayUtil.contains(
						_VARIABLE_TYPE_NAMES, variableTypeName)) {

					log(detailAST, _MSG_JSON_TO_STRING_USE);
				}
			}
			else {
				_getFirstDetailAST(detailAST, detailAST);
			}
		}
	}

	private boolean _checkMethodReturnType(
		DetailAST detailAST, String callMethodName) {

		if (Validator.isNull(callMethodName)) {
			return false;
		}

		DetailAST parentDetailAST = getParentWithTokenType(
			detailAST, TokenTypes.OBJBLOCK);

		List<DetailAST> childDetailASTList = getAllChildTokens(
			parentDetailAST, true, TokenTypes.METHOD_DEF);

		for (DetailAST tmpDetailAST : childDetailASTList) {
			String methodName = getName(tmpDetailAST);

			if (StringUtil.equals(methodName, callMethodName)) {
				String typeName = getTypeName(tmpDetailAST, true);

				List<String> enforceTypeNames = getAttributeValues(
					_ENFORCE_TYPE_NAMES_KEY);

				if (enforceTypeNames.contains(typeName)) {
					return true;
				}
			}
		}

		return false;
	}

	private void _getFirstDetailAST(
		DetailAST detailAST, DetailAST originDetailAST) {

		if (detailAST.getChildCount() == 0) {
			return;
		}

		DetailAST firstChildDetailAST = detailAST.getFirstChild();

		if (firstChildDetailAST.getType() == TokenTypes.DOT) {
			FullIdent fullIdent = FullIdent.createFullIdent(
				firstChildDetailAST);

			String expr = fullIdent.getText();

			if (StringUtil.equals(expr, "JSONUtil.put") ||
				StringUtil.equals(expr, "JSONUtil.putAll")) {

				log(originDetailAST, _MSG_JSON_TO_STRING_USE);

				return;
			}
		}

		_getFirstDetailAST(firstChildDetailAST, originDetailAST);
	}

	private static final String _ENFORCE_TYPE_NAMES_KEY = "enforceTypeNames";

	private static final String _MSG_JSON_TO_STRING_USE = "json.to.string.use";

	private static final String[] _VARIABLE_TYPE_NAMES = {
		"JSONObject", "JSONArray"
	};

}