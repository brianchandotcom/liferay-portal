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

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.AnnotationUtil;

import java.util.List;

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
			absolutePath.contains("/portal-kernel/") ||
			absolutePath.contains("/testIntegration/")) {

			return;
		}

		List<String> importNames = getImportNames(detailAST);

		if (importNames.contains(
				"com.liferay.portal.kernel.test.rule.NewEnvTestRule") ||
			importNames.contains(
				"com.liferay.portal.test.rule.AspectJNewEnvTestRule")) {

			return;
		}

		DetailAST annotationDetailAST = AnnotationUtil.getAnnotation(
			detailAST, "RunWith");

		if ((annotationDetailAST == null) &&
			!importNames.contains(
				"com.liferay.portal.test.rule.LiferayUnitTestRule")) {

			log(detailAST, _MSG_REQUIRE_TEST_RULE);

			return;
		}

		List<DetailAST> literalClassDetailASTList = getAllChildTokens(
			annotationDetailAST, true, TokenTypes.LITERAL_CLASS);

		for (DetailAST literalClassDetailAST : literalClassDetailASTList) {
			DetailAST identDetailAST =
				literalClassDetailAST.getPreviousSibling();

			String className = identDetailAST.getText();

			if (className.equals("MockitoJUnitRunner") ||
				className.equals("PowerMockRunner")) {

				return;
			}
		}

		if (!importNames.contains(
				"com.liferay.portal.test.rule.LiferayUnitTestRule")) {

			log(detailAST, _MSG_REQUIRE_TEST_RULE);
		}
	}

	private static final String _MSG_REQUIRE_TEST_RULE = "test.rule.missing";

}