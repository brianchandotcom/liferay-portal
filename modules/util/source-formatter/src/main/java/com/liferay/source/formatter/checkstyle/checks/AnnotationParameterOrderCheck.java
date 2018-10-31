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
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import java.util.List;

/**
 * @author Hugo Huijser
 */
public class AnnotationParameterOrderCheck extends BaseCheck {

	@Override
	public int[] getDefaultTokens() {
		return new int[] {TokenTypes.ANNOTATION};
	}

	@Override
	protected void doVisitToken(DetailAST detailAST) {
		List<DetailAST> annotationMemberValuePairDetailASTList =
			DetailASTUtil.getAllChildTokens(
				detailAST, false, TokenTypes.ANNOTATION_MEMBER_VALUE_PAIR);

		String previousName = null;

		for (DetailAST annotationMemberValuePairDetailAST :
				annotationMemberValuePairDetailASTList) {

			DetailAST nameDetailAST =
				annotationMemberValuePairDetailAST.findFirstToken(
					TokenTypes.IDENT);

			String name = nameDetailAST.getText();

			if ((previousName != null) &&
				(previousName.compareToIgnoreCase(name) > 0)) {

				log(
					annotationMemberValuePairDetailAST,
					_MSG_UNSORTED_ANNOTATION_PARAMETER, name);
			}

			previousName = name;
		}
	}

	private static final String _MSG_UNSORTED_ANNOTATION_PARAMETER =
		"annotation.unsortedParameter";

}