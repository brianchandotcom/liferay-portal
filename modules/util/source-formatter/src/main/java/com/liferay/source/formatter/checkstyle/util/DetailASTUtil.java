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

package com.liferay.source.formatter.checkstyle.util;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Hugo Huijser
 */
public class DetailASTUtil {

	public static List<DetailAST> getAllChildTokens(
		DetailAST detailAST, int tokenType, boolean recursive) {

		return _getAllChildTokens(detailAST, tokenType, recursive, null);
	}

	public static List<DetailAST> getParameterDefs(DetailAST detailAST) {
		List<DetailAST> list = new ArrayList<>();

		if ((detailAST.getType() != TokenTypes.CTOR_DEF) &&
			(detailAST.getType() != TokenTypes.METHOD_DEF)) {

			return list;
		}

		DetailAST parametersAST= detailAST.findFirstToken(
			TokenTypes.PARAMETERS);

		return getAllChildTokens(
			parametersAST, TokenTypes.PARAMETER_DEF, false);
	}

	public static List<String> getParameterNames(DetailAST detailAST) {
		List<String> parameterNames = new ArrayList<>();

		for (DetailAST parameterDefinitionAST : getParameterDefs(detailAST)) {
			DetailAST identAST = parameterDefinitionAST.findFirstToken(
				TokenTypes.IDENT);

			parameterNames.add(identAST.getText());
		}

		return parameterNames;
	}

	private static List<DetailAST> _getAllChildTokens(
		DetailAST detailAST, int tokenType, boolean recursive,
		List<DetailAST> list) {

		if (list == null) {
			list = new ArrayList<>();
		}

		DetailAST childAST = detailAST.getFirstChild();

		while (childAST != null) {
			if (childAST.getType() == tokenType) {
				list.add(childAST);
			}

			if (recursive) {
				list = _getAllChildTokens(childAST, tokenType, recursive, list);
			}

			childAST = childAST.getNextSibling();
		}

		return list;
	}

}