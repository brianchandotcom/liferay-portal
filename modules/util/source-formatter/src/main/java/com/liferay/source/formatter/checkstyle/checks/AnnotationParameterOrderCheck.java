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

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import java.util.List;

/**
 * @author Hugo Huijser
 */
public class AnnotationParameterOrderCheck extends AbstractCheck {

	public static final String MSG_UNSORTED_ANNOTATION_PARAMETER =
		"annotation.unsortedParameter";

	@Override
	public int[] getDefaultTokens() {
		return new int[] {TokenTypes.ANNOTATION};
	}

	@Override
	public void visitToken(DetailAST detailAST) {
		List<DetailAST> annotationMemberValuePairASTList =
			DetailASTUtil.getAllChildTokens(
				detailAST, false, TokenTypes.ANNOTATION_MEMBER_VALUE_PAIR);

		String previousName = null;

		for (DetailAST annotationMemberValuePairAST :
				annotationMemberValuePairASTList) {

			DetailAST nameAST = annotationMemberValuePairAST.findFirstToken(
				TokenTypes.IDENT);

			String name = nameAST.getText();

			if ((previousName != null) &&
				(previousName.compareToIgnoreCase(name) > 0)) {

				log(
					annotationMemberValuePairAST.getLineNo(),
					MSG_UNSORTED_ANNOTATION_PARAMETER, name);
			}

			previousName = name;
		}
	}

}