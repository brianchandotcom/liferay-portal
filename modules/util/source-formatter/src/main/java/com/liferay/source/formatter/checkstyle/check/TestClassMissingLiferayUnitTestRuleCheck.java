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

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FullIdent;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.AnnotationUtil;

import java.util.List;
import java.util.Objects;

/**
 * @author Alan Huang
 */
public class TestClassMissingLiferayUnitTestRuleCheck extends BaseCheck {

	@Override
	public int[] getDefaultTokens() {
		return new int[] {TokenTypes.CLASS_DEF};
	}

	@Override
	protected void doVisitToken(DetailAST detailAST) {
		DetailAST parentDetailAST = detailAST.getParent();

		if (parentDetailAST != null) {
			return;
		}

		String absolutePath = getAbsolutePath();

		if (!absolutePath.contains("/test/") ||
			!absolutePath.endsWith("Test.java") ||
			absolutePath.contains("/testIntegration/")) {

			return;
		}

		List<String> pathNames = getAttributeValues(_PAHT_NAMES_WHITELIST);

		for (String pathName : pathNames) {
			if (!absolutePath.contains(pathName)) {
				continue;
			}

			DetailAST annotationDetailAST = AnnotationUtil.getAnnotation(
				detailAST, "RunWith");

			if (annotationDetailAST != null) {
				List<DetailAST> literalClassDetailASTList = getAllChildTokens(
					annotationDetailAST, true, TokenTypes.LITERAL_CLASS);

				for (DetailAST literalClassDetailAST :
						literalClassDetailASTList) {

					DetailAST identDetailAST =
						literalClassDetailAST.getPreviousSibling();

					String className = identDetailAST.getText();

					if (className.equals("MockitoJUnitRunner") ||
						className.equals("PowerMockRunner")) {

						return;
					}
				}
			}

			DetailAST objBlockDetailAST = detailAST.findFirstToken(
				TokenTypes.OBJBLOCK);

			List<DetailAST> variableDefinitionDetailASTList = getAllChildTokens(
				objBlockDetailAST, false, TokenTypes.VARIABLE_DEF);

			for (DetailAST variableDefinitionDetailAST :
					variableDefinitionDetailASTList) {

				DetailAST typeDetailAST =
					variableDefinitionDetailAST.findFirstToken(TokenTypes.TYPE);

				DetailAST identDetailAST = typeDetailAST.getFirstChild();

				if (identDetailAST.getType() != TokenTypes.IDENT) {
					continue;
				}

				String variableType = identDetailAST.getText();

				if (variableType.equals("LiferayUnitTestRule")) {
					return;
				}

				List<DetailAST> dotDetailASTList = getAllChildTokens(
					variableDefinitionDetailAST, true, TokenTypes.DOT);

				FullIdent fullIdent = null;

				for (DetailAST dotDetailAST : dotDetailASTList) {
					fullIdent = FullIdent.createFullIdent(dotDetailAST);

					if (Objects.equals(
							fullIdent.getText(),
							"LiferayUnitTestRule.INSTANCE")) {

						return;
					}
				}
			}

			List<String> importNames = getImportNames(detailAST);

			if (!importNames.contains(
					"com.liferay.portal.test.rule.LiferayUnitTestRule")) {

				log(detailAST, _MSG_REQUIRE_TEST_RULE);
			}
		}
	}

	private static final String _MSG_REQUIRE_TEST_RULE = "test.rule.missing";

	private static final String _PAHT_NAMES_WHITELIST = "pathNamesWhiltelist";

}