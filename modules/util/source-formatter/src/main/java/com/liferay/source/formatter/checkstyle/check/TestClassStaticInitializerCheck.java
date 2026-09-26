/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.checkstyle.check;

import com.liferay.portal.kernel.util.ArrayUtil;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Adolfo Pérez
 */
public class TestClassStaticInitializerCheck extends BaseCheck {

	@Override
	public int[] getDefaultTokens() {
		return new int[] {TokenTypes.STATIC_INIT, TokenTypes.VARIABLE_DEF};
	}

	@Override
	protected void doVisitToken(DetailAST detailAST) {
		DetailAST parentDetailAST = detailAST.getParent();

		if (parentDetailAST.getType() != TokenTypes.OBJBLOCK) {
			return;
		}

		List<String> importNames = getImportNames(detailAST);

		if (!importNames.contains(
				"com.liferay.arquillian.extension.junit.bridge.junit." +
					"Arquillian") &&
			!importNames.contains("org.jboss.arquillian.junit.Arquillian")) {

			return;
		}

		if (detailAST.getType() == TokenTypes.VARIABLE_DEF) {
			DetailAST modifiersDetailAST = detailAST.findFirstToken(
				TokenTypes.MODIFIERS);

			if (!modifiersDetailAST.branchContains(TokenTypes.LITERAL_STATIC)) {
				return;
			}
		}

		List<DetailAST> methodCallDetailASTs = getAllChildTokens(
			detailAST, true, TokenTypes.METHOD_CALL);

		for (DetailAST methodCallDetailAST : methodCallDetailASTs) {
			DetailAST enclosingDetailAST = getParentWithTokenType(
				methodCallDetailAST, TokenTypes.LAMBDA, TokenTypes.OBJBLOCK);

			if (enclosingDetailAST != parentDetailAST) {
				continue;
			}

			String className = getClassOrVariableName(methodCallDetailAST);

			if (ArrayUtil.contains(_FORBIDDEN_CLASS_NAMES, className)) {
				log(methodCallDetailAST, _MSG_INCORRECT_CLASS, className);

				continue;
			}

			String methodCallName =
				className + "." + getMethodName(methodCallDetailAST);

			if (_isForbiddenMethodCallName(methodCallName)) {
				log(
					methodCallDetailAST, _MSG_INCORRECT_METHOD_CALL,
					methodCallName);

				continue;
			}

			if (!ArrayUtil.contains(
					_RANDOM_STRING_METHOD_CALL_NAMES, methodCallName)) {

				continue;
			}

			for (DetailAST identDetailAST :
					getAllChildTokens(
						methodCallDetailAST.findFirstToken(TokenTypes.ELIST),
						true, TokenTypes.IDENT)) {

				String randomizerBumperName = identDetailAST.getText();

				if (ArrayUtil.contains(
						_FORBIDDEN_RANDOMIZER_BUMPER_NAMES,
						randomizerBumperName)) {

					log(
						methodCallDetailAST, _MSG_INCORRECT_RANDOMIZER_BUMPER,
						methodCallName, randomizerBumperName);
				}
			}
		}
	}

	private boolean _isForbiddenMethodCallName(String methodCallName) {
		if (ArrayUtil.contains(_FORBIDDEN_METHOD_CALL_NAMES, methodCallName)) {
			return true;
		}

		for (Pattern forbiddenMethodCallNamePattern :
				_FORBIDDEN_METHOD_CALL_NAME_PATTERNS) {

			Matcher matcher = forbiddenMethodCallNamePattern.matcher(
				methodCallName);

			if (matcher.matches()) {
				return true;
			}
		}

		return false;
	}

	private static final String[] _FORBIDDEN_CLASS_NAMES = {
		"ServiceContextTestUtil", "TestPropsValues"
	};

	private static final Pattern[] _FORBIDDEN_METHOD_CALL_NAME_PATTERNS = {
		Pattern.compile("\\w+ServiceUtil\\.\\w+"),
		Pattern.compile("\\w+TestUtil\\.(add|delete|update)\\w*")
	};

	private static final String[] _FORBIDDEN_METHOD_CALL_NAMES = {
		"DLTestUtil.randomTextFileBytes", "RandomTestUtil.nextDouble",
		"RandomTestUtil.nextInt", "RandomTestUtil.nextLong"
	};

	private static final String[] _FORBIDDEN_RANDOMIZER_BUMPER_NAMES = {
		"BBCodeRandomizerBumper", "LayoutFriendlyURLRandomizerBumper",
		"SiteFriendlyURLKeywordRandomizerBumper", "TikaRandomizerBumper"
	};

	private static final String _MSG_INCORRECT_CLASS = "class.incorrect";

	private static final String _MSG_INCORRECT_METHOD_CALL =
		"method.call.incorrect";

	private static final String _MSG_INCORRECT_RANDOMIZER_BUMPER =
		"randomizer.bumper.incorrect";

	private static final String[] _RANDOM_STRING_METHOD_CALL_NAMES = {
		"RandomTestUtil.randomString", "RandomTestUtil.randomStrings"
	};

}