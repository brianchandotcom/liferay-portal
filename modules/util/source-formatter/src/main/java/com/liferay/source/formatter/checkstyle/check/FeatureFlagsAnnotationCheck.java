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

import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.NaturalOrderStringComparator;
import com.liferay.portal.kernel.util.StringUtil;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FullIdent;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.AnnotationUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Qi Zhang
 */
public class FeatureFlagsAnnotationCheck extends BaseCheck {

	@Override
	public int[] getDefaultTokens() {
		return new int[] {TokenTypes.CLASS_DEF};
	}

	@Override
	protected void doVisitToken(DetailAST detailAST) {
		List<DetailAST> methodDefDetailASTs = getAllChildTokens(
			detailAST, true, TokenTypes.METHOD_DEF);

		for (DetailAST methodDefDetailAST : methodDefDetailASTs) {
			String methodName = getName(methodDefDetailAST);

			DetailAST sListDetailAST = methodDefDetailAST.findFirstToken(
				TokenTypes.SLIST);

			if (sListDetailAST == null) {
				return;
			}

			List<String> featureFlags = new ArrayList<>();

			DetailAST childDetailAST = sListDetailAST.getFirstChild();

			while (childDetailAST != null) {
				List<DetailAST> eListDetailASTs = _getEListDetailASTs(
					childDetailAST);

				if (ListUtil.isEmpty(eListDetailASTs)) {
					childDetailAST = childDetailAST.getNextSibling();

					continue;
				}

				for (DetailAST eListDetailAST : eListDetailASTs) {
					List<String> properties = _getProperties(eListDetailAST);

					if (ListUtil.isEmpty(properties)) {
						continue;
					}

					String value = properties.get(1);

					if (StringUtil.equals(value, "false")) {
						continue;
					}

					featureFlags.add("\"" + properties.get(0) + "\"");
				}

				childDetailAST = childDetailAST.getNextSibling();
			}

			if (ListUtil.isEmpty(featureFlags)) {
				continue;
			}

			featureFlags.sort(new NaturalOrderStringComparator());

			String annotationValue = StringUtil.merge(featureFlags, ", ");

			if (featureFlags.size() > 1) {
				annotationValue = "{" + annotationValue + "}";
			}

			if ((StringUtil.equals(methodName, "setUp") &&
				 AnnotationUtil.containsAnnotation(
					 methodDefDetailAST, "Before")) ||
				(StringUtil.equals(methodName, "setUpClass") &&
				 AnnotationUtil.containsAnnotation(
					 methodDefDetailAST, "BeforeClass"))) {

				log(
					detailAST, _MSG_USE_FEATURE_FLAGS, annotationValue,
					"class");
			}
			else {
				log(
					methodDefDetailAST, _MSG_USE_FEATURE_FLAGS, annotationValue,
					"method");
			}
		}
	}

	private List<DetailAST> _getEListDetailASTs(DetailAST detailAST) {
		DetailAST childDetailAST = detailAST;
		boolean inAddProperties = false;

		while (true) {
			if (childDetailAST.getType() != TokenTypes.EXPR) {
				return Collections.emptyList();
			}

			childDetailAST = childDetailAST.getFirstChild();

			if (childDetailAST.getType() != TokenTypes.METHOD_CALL) {
				return Collections.emptyList();
			}

			DetailAST dotDetailAST = childDetailAST.getFirstChild();

			if (dotDetailAST.getType() != TokenTypes.DOT) {
				return Collections.emptyList();
			}

			if (inAddProperties) {
				break;
			}

			FullIdent fullIdent = FullIdent.createFullIdent(dotDetailAST);

			String fullIdentText = fullIdent.getText();

			if (!StringUtil.equals(fullIdentText, "PropsUtil.addProperties")) {
				return Collections.emptyList();
			}

			inAddProperties = true;

			DetailAST eListDetailAST = childDetailAST.findFirstToken(
				TokenTypes.ELIST);

			if (eListDetailAST.getChildCount() > 1) {
				return Collections.emptyList();
			}

			childDetailAST = eListDetailAST.getFirstChild();
		}

		DetailAST methodCallDetailAST = childDetailAST;
		List<DetailAST> eListDetailASTs = new ArrayList<>();

		while (true) {
			childDetailAST = childDetailAST.findFirstToken(TokenTypes.DOT);

			if (childDetailAST == null) {
				return Collections.emptyList();
			}

			DetailAST firstChildDetailAST = childDetailAST.getFirstChild();

			if (firstChildDetailAST.getType() == TokenTypes.METHOD_CALL) {
				DetailAST lastChildDetailAST = childDetailAST.getLastChild();

				String lastChildDetailASTText = lastChildDetailAST.getText();

				if (lastChildDetailASTText.equals("setProperty")) {
					eListDetailASTs.add(
						methodCallDetailAST.findFirstToken(TokenTypes.ELIST));
				}

				childDetailAST = firstChildDetailAST;
				methodCallDetailAST = firstChildDetailAST;
			}
			else if (firstChildDetailAST.getType() == TokenTypes.IDENT) {
				String firstChildDetailASTText = firstChildDetailAST.getText();

				if (firstChildDetailASTText.equals(
						"UnicodePropertiesBuilder")) {

					eListDetailASTs.add(
						methodCallDetailAST.findFirstToken(TokenTypes.ELIST));

					break;
				}

				return Collections.emptyList();
			}
			else {
				return Collections.emptyList();
			}
		}

		return eListDetailASTs;
	}

	private List<String> _getProperties(DetailAST eListDetailAST) {
		DetailAST eListChildDetailAST = eListDetailAST.getFirstChild();

		List<String> properties = new ArrayList<>();

		while (eListChildDetailAST != null) {
			if (eListChildDetailAST.getType() == TokenTypes.COMMA) {
				eListChildDetailAST = eListChildDetailAST.getNextSibling();

				continue;
			}

			if (eListChildDetailAST.getType() != TokenTypes.EXPR) {
				return Collections.emptyList();
			}

			DetailAST firstChildDetailAST = eListChildDetailAST.getFirstChild();

			if (firstChildDetailAST.getType() != TokenTypes.STRING_LITERAL) {
				return Collections.emptyList();
			}

			properties.add(StringUtil.unquote(firstChildDetailAST.getText()));

			eListChildDetailAST = eListChildDetailAST.getNextSibling();
		}

		if (properties.size() != 2) {
			return Collections.emptyList();
		}

		String property = properties.get(0);

		Matcher matcher = _featureFlagPattern.matcher(property);

		if (matcher.find()) {
			properties.set(0, matcher.group(1));

			return properties;
		}

		return Collections.emptyList();
	}

	private static final String _MSG_USE_FEATURE_FLAGS = "use.feature.flags";

	private static final Pattern _featureFlagPattern = Pattern.compile(
		"feature\\.flag\\.([\\w-]+)");

}