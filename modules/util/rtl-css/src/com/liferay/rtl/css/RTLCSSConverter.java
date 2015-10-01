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

package com.liferay.rtl.css;

import com.helger.commons.charset.CCharset;
import com.helger.css.ECSSVersion;
import com.helger.css.decl.CSSDeclaration;
import com.helger.css.decl.CSSExpression;
import com.helger.css.decl.CSSExpressionMemberTermSimple;
import com.helger.css.decl.CSSExpressionMemberTermURI;
import com.helger.css.decl.CSSMediaRule;
import com.helger.css.decl.CSSStyleRule;
import com.helger.css.decl.CSSUnknownRule;
import com.helger.css.decl.CascadingStyleSheet;
import com.helger.css.decl.ICSSExpressionMember;
import com.helger.css.decl.ICSSTopLevelRule;
import com.helger.css.reader.CSSReader;
import com.helger.css.reader.errorhandler.DoNothingCSSParseErrorHandler;
import com.helger.css.writer.CSSWriterSettings;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author David Truong
 */
public class RTLCSSConverter {

	public RTLCSSConverter() {
		this(true);
	}

	public RTLCSSConverter(boolean compress) {
		_cssWriterSettings = new CSSWriterSettings(ECSSVersion.CSS30, compress);

		_cssWriterSettings.setOptimizedOutput(compress);
		_cssWriterSettings.setRemoveUnnecessaryCode(Boolean.TRUE);
	}

	public String process(String css) {
		css = processNoFlip(css);

		CascadingStyleSheet cascadingStyleSheet = CSSReader.readFromString(
			css, CCharset.CHARSET_UTF_8_OBJ, ECSSVersion.CSS30,
			new DoNothingCSSParseErrorHandler());

		return processRules(cascadingStyleSheet.getAllRules());
	}

	protected void convertBackground(CSSStyleRule cssStyleRule, String property) {
		reverseImage(cssStyleRule, property);

		convertBackgroundPosition(cssStyleRule, property);
	}

	protected void convertBackgroundPosition(
		CSSStyleRule cssStyleRule, String property) {

		CSSDeclaration cssDeclaration =
			cssStyleRule.getDeclarationOfPropertyNameCaseInsensitive(property);

		if (cssDeclaration == null) {
			return;
		}

		CSSExpression cssExpression = cssDeclaration.getExpression();

		List<CSSExpressionMemberTermSimple> cssExpressionMemberTerms =
			cssExpression.getAllSimpleMembers();

		for (CSSExpressionMemberTermSimple cssExpressionMemberTerm :
				cssExpressionMemberTerms) {

			String value = cssExpressionMemberTerm.getValue();

			if (value.contains("right")) {
				cssExpressionMemberTerm.setValue("left");
			}
			else if (value.contains("left")) {
				cssExpressionMemberTerm.setValue("right");
			}
		}

		if (cssExpressionMemberTerms.size() == 1) {
			CSSExpressionMemberTermSimple cssExpressionMemberTerm =
				cssExpressionMemberTerms.get(0);

			String value = cssExpressionMemberTerm.getValue();

			Matcher matcher = _percentOrLengthPattern.matcher(value);

			if (matcher.matches()) {
				cssExpression.addTermSimple(value);

				cssExpressionMemberTerm.setValue("right");
			}
		}
		else if (cssExpressionMemberTerms.size() == 2) {
			CSSExpressionMemberTermSimple cssExpressionMemberTerm =
				cssExpressionMemberTerms.get(0);

			String value = cssExpressionMemberTerm.getValue();

			Matcher matcher = _percentPattern.matcher(value);

			if (matcher.matches()) {
				int delta = Integer.valueOf(value.replaceAll("[^\\d]", ""), 10);

				value = (100 - delta) + "%";

				cssExpressionMemberTerm.setValue(value);
			}
		}
	}

	protected void convertShorthand(CSSStyleRule cssStyleRule, String property) {
		CSSDeclaration cssDeclaration =
			cssStyleRule.getDeclarationOfPropertyNameCaseInsensitive(property);

		if (cssDeclaration == null) {
			return;
		}

		CSSExpression cssExpression = cssDeclaration.getExpression();

		List<CSSExpressionMemberTermSimple> cssExpressionMemberTerms =
			cssExpression.getAllSimpleMembers();

		if (cssExpressionMemberTerms.size() == 4) {
			CSSExpressionMemberTermSimple cssExpressionMemberTerm2 =
				cssExpressionMemberTerms.get(1);

			String value = cssExpressionMemberTerm2.getValue();

			CSSExpressionMemberTermSimple cssExpressionMemberTerm4 =
				cssExpressionMemberTerms.get(3);

			cssExpressionMemberTerm2.setValue(
				cssExpressionMemberTerm4.getValue());

			cssExpressionMemberTerm4.setValue(value);
		}
	}

	protected void convertShorthandRadius(
		CSSStyleRule cssStyleRule, String property) {

		CSSDeclaration cssDeclaration =
			cssStyleRule.getDeclarationOfPropertyNameCaseInsensitive(property);

		if (cssDeclaration == null) {
			return;
		}

		CSSExpression cssExpression = cssDeclaration.getExpression();

		List<CSSExpressionMemberTermSimple> cssExpressionMemberTerms =
			cssExpression.getAllSimpleMembers();

		if (cssExpressionMemberTerms.size() == 4) {
			CSSExpressionMemberTermSimple cssExpressionMemberTerm1 =
				cssExpressionMemberTerms.get(0);

			String value = cssExpressionMemberTerm1.getValue();

			CSSExpressionMemberTermSimple cssExpressionMemberTerm2 =
				cssExpressionMemberTerms.get(1);

			cssExpressionMemberTerm1.setValue(
				cssExpressionMemberTerm2.getValue());

			cssExpressionMemberTerm2.setValue(value);

			CSSExpressionMemberTermSimple cssExpressionMemberTerm3 =
				cssExpressionMemberTerms.get(2);

			value = cssExpressionMemberTerm3.getValue();

			CSSExpressionMemberTermSimple cssExpressionMemberTerm4 =
				cssExpressionMemberTerms.get(3);

			cssExpressionMemberTerm3.setValue(
				cssExpressionMemberTerm4.getValue());

			cssExpressionMemberTerm4.setValue(value);
		}
		else if (cssExpressionMemberTerms.size() == 3) {
			CSSExpressionMemberTermSimple cssExpressionMemberTerm1 =
				cssExpressionMemberTerms.get(0);

			String value = cssExpressionMemberTerm1.getValue();

			CSSExpressionMemberTermSimple cssExpressionMemberTerm2 =
				cssExpressionMemberTerms.get(1);

			cssExpressionMemberTerm1.setValue(
				cssExpressionMemberTerm2.getValue());

			cssExpressionMemberTerm2.setValue(value);

			CSSExpressionMemberTermSimple cssExpressionMemberTerm3 =
				cssExpressionMemberTerms.get(2);

			value = cssExpressionMemberTerm3.getValue();

			cssExpressionMemberTerm3.setValue(
				cssExpressionMemberTerm1.getValue());

			cssExpression.addTermSimple(value);
		}
	}

	protected String processNoFlip(String css) {
		css = css.replaceAll("/\\*\\s*@noflip\\s*\\*/ *(\\n|$)", "");
		return css.replaceAll("/\\*\\s*@noflip\\s*\\*/", "@noflip ");
	}

	protected void processRule(CSSStyleRule cssStyleRule) {
		for (String property : _replacementStyles.keySet()) {
			replaceStyle(cssStyleRule, property);
		}

		for (CSSDeclaration cssDeclaration :
				cssStyleRule.getAllDeclarations()) {

			String property = cssDeclaration.getProperty();

			String strippedProperty = stripProperty(property);

			if (_shorthandStyles.contains(strippedProperty)) {
				convertShorthand(cssStyleRule, property);
			}
			else if (_shorthandRadiusStyles.contains(strippedProperty)) {
				convertShorthandRadius(cssStyleRule, property);
			}
			else if (_reverseStyles.contains(strippedProperty)) {
				reverseStyle(cssStyleRule, property);
			}
			else if (_reverseImageStyles.contains(strippedProperty)) {
				reverseImage(cssStyleRule, property);
			}
			else if (_backgroundStyles.contains(strippedProperty)) {
				convertBackground(cssStyleRule, property);
			}
			else if (_backgroundPositionStyles.contains(strippedProperty)) {
				convertBackgroundPosition(cssStyleRule, property);
			}
		}
	}

	protected String processRules(List<ICSSTopLevelRule> cssTopLevelRules) {
		StringBuilder sb = new StringBuilder();

		for (ICSSTopLevelRule cssTopLevelRule : cssTopLevelRules) {
			if (cssTopLevelRule instanceof CSSStyleRule) {
				processRule((CSSStyleRule)cssTopLevelRule);
			}
			else if (cssTopLevelRule instanceof CSSMediaRule) {
				CSSMediaRule cssMediaRule = (CSSMediaRule)cssTopLevelRule;

				processRules(cssMediaRule.getAllRules());
			}

			if (cssTopLevelRule instanceof CSSUnknownRule) {
				String css = cssTopLevelRule.getAsCSSString(_cssWriterSettings, 1);

				sb.append(css.replace("@noflip ", ""));
			}
			else {
				sb.append(cssTopLevelRule.getAsCSSString(_cssWriterSettings, 1));
			}
		}

		return sb.toString();
	}

	protected void replaceStyle(CSSStyleRule cssStyleRule, String property) {
		replaceStyle(cssStyleRule, property, false);
		replaceStyle(cssStyleRule, property, true);
	}

	protected void replaceStyle(
		CSSStyleRule cssStyleRule, String property, boolean addAsterisk) {

		String asterisk = "";

		if (addAsterisk) {
			asterisk = "*";
		}

		String replacementProperty = _replacementStyles.get(property);

		CSSDeclaration replacementDeclaration =
			cssStyleRule.getDeclarationOfPropertyNameCaseInsensitive(
				asterisk + replacementProperty);

		CSSDeclaration cssDeclaration =
			cssStyleRule.getDeclarationOfPropertyNameCaseInsensitive(
				asterisk + property);

		if (cssDeclaration != null) {
			cssDeclaration.setProperty(asterisk + replacementProperty);
		}

		if (replacementDeclaration != null) {
			replacementDeclaration.setProperty(asterisk + property);
		}
	}

	protected void reverseImage(CSSStyleRule cssStyleRule, String property) {
		CSSDeclaration cssDeclaration =
			cssStyleRule.getDeclarationOfPropertyNameCaseInsensitive(property);

		if (cssDeclaration == null) {
			return;
		}

		CSSExpression cssExpression = cssDeclaration.getExpression();

		List<ICSSExpressionMember> cssExpressionMembers =
			cssExpression.getAllMembers();

		for (ICSSExpressionMember cssExpressionMember : cssExpressionMembers) {
			if (cssExpressionMember instanceof CSSExpressionMemberTermURI) {
				CSSExpressionMemberTermURI cssExpressionMemberTermURI =
					(CSSExpressionMemberTermURI)cssExpressionMember;

				String uri = cssExpressionMemberTermURI.getURIString();

				int pos = uri.lastIndexOf("/") + 1;

				String fileName = uri.substring(pos);

				if (fileName.contains("right")) {
					fileName = fileName.replaceAll("right", "left");
				}
				else {
					fileName = fileName.replaceAll("left", "right");
				}

				cssExpressionMemberTermURI.setURIString(
					uri.substring(0, pos) + fileName);
			}
		}
	}

	protected void reverseStyle(CSSStyleRule cssStyleRule, String property) {
		CSSDeclaration cssDeclaration =
			cssStyleRule.getDeclarationOfPropertyNameCaseInsensitive(property);

		if (cssDeclaration == null) {
			return;
		}

		CSSExpression cssExpression = cssDeclaration.getExpression();

		List<CSSExpressionMemberTermSimple> cssExpressionMemberTerms =
			cssExpression.getAllSimpleMembers();

		if (cssExpressionMemberTerms.size() > 0) {
			CSSExpressionMemberTermSimple cssExpressionMemberTerm =
				cssExpressionMemberTerms.get(0);

			String value = cssExpressionMemberTerm.getValue();

			if (value.contains("right")) {
				value = value.replace("right", "left");
			}
			else {
				value = value.replace("left", "right");
			}

			if (value.contains("rtl")) {
				value = value.replace("rtl", "ltr");
			}
			else {
				value = value.replace("ltr", "rtl");
			}

			cssExpressionMemberTerm.setValue(value);
		}
	}

	protected String stripProperty(String property) {
		return property.replaceAll("\\**\\b", "");
	}

	private static final List<String> _backgroundPositionStyles = Arrays.asList(
		"background-position");
	private static final List<String> _backgroundStyles = Arrays.asList(
		"background");
	private static final Pattern _percentOrLengthPattern = Pattern.compile(
		"(\\d+)([a-z]{2}|%)");
	private static final Pattern _percentPattern = Pattern.compile("\\d+%");
	private static final Map<String, String> _replacementStyles =
		new HashMap<>();
	private static final List<String> _reverseImageStyles = Arrays.asList(
		"background-image");
	private static final List<String> _reverseStyles = Arrays.asList(
		"clear", "direction", "float", "text-align");
	private static final List<String> _shorthandRadiusStyles = Arrays.asList(
		"-moz-border-radius", "-webkit-border-radius", "border-radius");
	private static final List<String> _shorthandStyles = Arrays.asList(
		"border-color", "border-style", "border-width", "margin", "padding");

	static {
		_replacementStyles.put(
			"-moz-border-radius-bottomright", "-moz-border-radius-bottomleft");
		_replacementStyles.put(
			"-moz-border-radius-topright", "-moz-border-radius-topleft");
		_replacementStyles.put(
			"border-radius-topleft", "border-radius-topright");
		_replacementStyles.put(
			"border-top-right-radius", "border-top-left-radius");
		_replacementStyles.put(
			"-webkit-border-bottom-right-radius",
			"-webkit-border-bottom-left-radius");
		_replacementStyles.put(
			"-webkit-border-top-right-radius",
			"-webkit-border-top-left-radius");
		_replacementStyles.put(
			"border-bottom-right-radius", "border-bottom-left-radius");
		_replacementStyles.put("border-left", "border-right");
		_replacementStyles.put("border-left-color", "border-right-color");
		_replacementStyles.put("border-left-width", "border-right-width");
		_replacementStyles.put(
			"border-radius-bottomleft", "border-radius-bottomright");
		_replacementStyles.put("left", "right");
		_replacementStyles.put("margin-left", "margin-right");
		_replacementStyles.put("padding-left", "padding-right");
	}

	private final CSSWriterSettings _cssWriterSettings;

}