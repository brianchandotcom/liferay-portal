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
import com.helger.css.decl.CascadingStyleSheet;
import com.helger.css.decl.ICSSExpressionMember;
import com.helger.css.decl.ICSSTopLevelRule;
import com.helger.css.reader.CSSReader;
import com.helger.css.reader.errorhandler.DoNothingCSSParseErrorHandler;
import com.helger.css.writer.CSSWriter;
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
		_settings = new CSSWriterSettings(ECSSVersion.CSS30, compress);

		_settings.setOptimizedOutput(compress);
		_settings.setRemoveUnnecessaryCode(compress);

		_writer = new CSSWriter(_settings);

		_writer.setWriteHeaderText(Boolean.FALSE);
		_writer.setWriteFooterText(Boolean.FALSE);
	}

	public String process(String css) {
		CascadingStyleSheet cascadingStyleSheet = CSSReader.readFromString(
			css, CCharset.CHARSET_UTF_8_OBJ, ECSSVersion.CSS30,
			new DoNothingCSSParseErrorHandler());

		List<ICSSTopLevelRule> rules = cascadingStyleSheet.getAllRules();

		processRules(rules);

		return _writer.getCSSAsString(cascadingStyleSheet);
	}

	private void convertBGPosition(CSSStyleRule styleRule, String property) {
		CSSDeclaration declaration =
			styleRule.getDeclarationOfPropertyNameCaseInsensitive(property);

		if (declaration == null) {
			return;
		}

		CSSExpression expression = declaration.getExpression();

		List<CSSExpressionMemberTermSimple> terms =
			expression.getAllSimpleMembers();

		for (CSSExpressionMemberTermSimple term : terms) {
			String value = term.getValue();

			if (value.contains("right")) {
				term.setValue("left");
			}
			else if (value.contains("left")) {
				term.setValue("right");
			}
		}

		if (terms.size() == 1) {
			CSSExpressionMemberTermSimple term = terms.get(0);

			String value = term.getValue();

			Matcher matcher = _percentOrLengthPattern.matcher(value);

			if (matcher.matches()) {
				expression.addTermSimple(value);

				term.setValue("right");
			}
		}
		else if (terms.size() == 2) {
			CSSExpressionMemberTermSimple term = terms.get(0);

			String value = term.getValue();

			Matcher matcher = _percentPattern.matcher(value);

			if (matcher.matches()) {
				int delta = Integer.valueOf(value.replaceAll("[^\\d]", ""), 10);

				value = (100 - delta) + "%";

				term.setValue(value);
			}
		}
	}

	private void convertShorthand(CSSStyleRule styleRule, String property) {
		CSSDeclaration declaration =
			styleRule.getDeclarationOfPropertyNameCaseInsensitive(property);

		if (declaration == null) {
			return;
		}

		CSSExpression expression = declaration.getExpression();

		List<CSSExpressionMemberTermSimple> terms =
			expression.getAllSimpleMembers();

		if (terms.size() == 4) {
			CSSExpressionMemberTermSimple term2 = terms.get(1);

			String value = term2.getValue();

			CSSExpressionMemberTermSimple term4 = terms.get(3);

			term2.setValue(term4.getValue());

			term4.setValue(value);
		}
	}

	private void convertShorthandRadius(
		CSSStyleRule styleRule, String property) {

		CSSDeclaration declaration =
			styleRule.getDeclarationOfPropertyNameCaseInsensitive(property);

		if (declaration == null) {
			return;
		}

		CSSExpression expression = declaration.getExpression();

		List<CSSExpressionMemberTermSimple> terms =
			expression.getAllSimpleMembers();

		if (terms.size() == 4) {
			CSSExpressionMemberTermSimple term1 = terms.get(0);

			String value = term1.getValue();

			CSSExpressionMemberTermSimple term2 = terms.get(1);

			term1.setValue(term2.getValue());

			term2.setValue(value);

			CSSExpressionMemberTermSimple term3 = terms.get(2);

			value = term3.getValue();

			CSSExpressionMemberTermSimple term4 = terms.get(3);

			term3.setValue(term4.getValue());

			term4.setValue(value);
		}
		else if (terms.size() == 3) {
			CSSExpressionMemberTermSimple term1 = terms.get(0);

			String value = term1.getValue();

			CSSExpressionMemberTermSimple term2 = terms.get(1);

			term1.setValue(term2.getValue());

			term2.setValue(value);

			CSSExpressionMemberTermSimple term3 = terms.get(2);

			value = term3.getValue();

			term3.setValue(term1.getValue());

			expression.addTermSimple(value);
		}
	}

	private void processRule(CSSStyleRule styleRule) {
		for (String property : _replacementStyles.keySet()) {
			replaceStyle(styleRule, property);
		}

		for (CSSDeclaration declaration : styleRule.getAllDeclarations()) {
			String property = declaration.getProperty();

			String strippedProperty = stripProperty(property);

			if (_shorthandStyles.contains(strippedProperty)) {
				convertShorthand(styleRule, property);
			}
			else if (_shorthandRadiusStyles.contains(strippedProperty)) {
				convertShorthandRadius(styleRule, property);
			}
			else if (_reverseStyles.contains(strippedProperty)) {
				reverseStyle(styleRule, property);
			}
			else if (_reverseImageStyles.contains(strippedProperty)) {
				reverseImage(styleRule, property);
			}
			else if (_backgroundPositionStyles.contains(strippedProperty)) {
				convertBGPosition(styleRule, property);
			}
		}
	}

	private void processRules(List<ICSSTopLevelRule> rules) {
		for (ICSSTopLevelRule rule : rules) {
			if (rule instanceof CSSStyleRule) {
				CSSStyleRule styleRule = (CSSStyleRule)rule;

				processRule(styleRule);
			}
			else if (rule instanceof CSSMediaRule) {
				CSSMediaRule mediaRule = (CSSMediaRule)rule;

				processRules(mediaRule.getAllRules());
			}
		}
	}

	private void replaceStyle(CSSStyleRule styleRule, String property) {
		replaceStyle(styleRule, property, false);
		replaceStyle(styleRule, property, true);
	}

	private void replaceStyle(
		CSSStyleRule styleRule, String property, boolean addAsterisk) {

		String asterisk = "";

		if (addAsterisk) {
			asterisk = "*";
		}

		String replacementProperty = _replacementStyles.get(property);

		CSSDeclaration replacementDeclaration =
			styleRule.getDeclarationOfPropertyNameCaseInsensitive(
				asterisk + replacementProperty);

		CSSDeclaration declaration =
			styleRule.getDeclarationOfPropertyNameCaseInsensitive(
				asterisk + property);

		if (declaration != null) {
			declaration.setProperty(asterisk + replacementProperty);
		}

		if (replacementDeclaration != null) {
			replacementDeclaration.setProperty(asterisk + property);
		}
	}

	private void reverseImage(CSSStyleRule styleRule, String property) {
		CSSDeclaration declaration =
			styleRule.getDeclarationOfPropertyNameCaseInsensitive(property);

		if (declaration == null) {
			return;
		}

		CSSExpression expression = declaration.getExpression();

		List<ICSSExpressionMember> members = expression.getAllMembers();

		for (ICSSExpressionMember member : members) {
			if (member instanceof CSSExpressionMemberTermURI) {
				CSSExpressionMemberTermURI termURI =
					(CSSExpressionMemberTermURI)member;

				String uri = termURI.getURIString();

				if (uri.contains("right")) {
					uri = uri.replaceAll("right", "left");
				}
				else {
					uri = uri.replaceAll("left", "right");
				}

				termURI.setURIString(uri);
			}
		}
	}

	private void reverseStyle(CSSStyleRule styleRule, String property) {
		CSSDeclaration declaration =
			styleRule.getDeclarationOfPropertyNameCaseInsensitive(property);

		if (declaration == null) {
			return;
		}

		CSSExpression expression = declaration.getExpression();

		List<CSSExpressionMemberTermSimple> terms =
			expression.getAllSimpleMembers();

		if (terms.size() > 0) {
			CSSExpressionMemberTermSimple term = terms.get(0);

			String value = term.getValue();

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

			term.setValue(value);
		}
	}

	private String stripProperty(String property) {
		return property.replaceAll("\\**\\b", "");
	}

	private static final List<String> _backgroundPositionStyles = Arrays.asList(
		"background-position");
	private static final Pattern _percentOrLengthPattern = Pattern.compile(
		"(\\d+)([a-z]{2}|%)");
	private static final Pattern _percentPattern = Pattern.compile("\\d+%");
	private static final Map<String, String> _replacementStyles =
		new HashMap<>();
	private static final List<String> _reverseImageStyles = Arrays.asList(
		"background", "background-image");
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

	private final CSSWriterSettings _settings;
	private final CSSWriter _writer;

}