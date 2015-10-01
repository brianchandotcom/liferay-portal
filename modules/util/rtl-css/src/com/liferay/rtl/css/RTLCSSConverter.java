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
import com.helger.css.decl.CSSStyleRule;
import com.helger.css.decl.CascadingStyleSheet;
import com.helger.css.decl.ICSSExpressionMember;
import com.helger.css.decl.ICSSTopLevelRule;
import com.helger.css.reader.errorhandler.DoNothingCSSParseErrorHandler;
import com.helger.css.reader.CSSReader;
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

		List<ICSSTopLevelRule> rules =
			cascadingStyleSheet.getAllRules();

		for (ICSSTopLevelRule rule : rules) {
			if (rule instanceof CSSStyleRule) {
				CSSStyleRule cssStyleRule = (CSSStyleRule)rule;

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
					else if (_backgroundPositionStyles.contains(strippedProperty)) {
						convertBGPosition(cssStyleRule, property);
					}
				}
			}
		}

		return _writer.getCSSAsString(cascadingStyleSheet);
	}

	protected void convertBGPosition(
		CSSStyleRule cssStyleRule, String property) {

		CSSDeclaration cssDeclaration =
			cssStyleRule.getDeclarationOfPropertyNameCaseInsensitive(property);

		if (cssDeclaration == null) {
			return;
		}

		CSSExpression cssExpression = cssDeclaration.getExpression();

		List<CSSExpressionMemberTermSimple> cssExpressionMemberTermSimples =
			cssExpression.getAllSimpleMembers();

		for (CSSExpressionMemberTermSimple cssExpressionMemberTermSimple :
				cssExpressionMemberTermSimples) {

			String value = cssExpressionMemberTermSimple.getValue();

			if (value.contains("right")) {
				cssExpressionMemberTermSimple.setValue("left");
			}
			else if (value.contains("left")) {
				cssExpressionMemberTermSimple.setValue("right");
			}
		}

		if (cssExpressionMemberTermSimples.size() == 1) {
			CSSExpressionMemberTermSimple cssExpressionMemberTermSimple =
				cssExpressionMemberTermSimples.get(0);

			String value = cssExpressionMemberTermSimple.getValue();

			Matcher matcher = _percentOrLengthPattern.matcher(value);

			if (matcher.matches()) {
				cssExpression.addTermSimple(value);

				cssExpressionMemberTermSimple.setValue("right");
			}
		}
		else if (cssExpressionMemberTermSimples.size() == 2) {
			CSSExpressionMemberTermSimple cssExpressionMemberTermSimple =
				cssExpressionMemberTermSimples.get(0);

			String value = cssExpressionMemberTermSimple.getValue();

			Matcher matcher = _percentPattern.matcher(value);

			if (matcher.matches()) {
				int delta = Integer.valueOf(value.replaceAll("[^\\d]", ""), 10);

				value = (100 - delta) + "%";

				cssExpressionMemberTermSimple.setValue(value);
			}
		}
	}

	protected void convertShorthand(
		CSSStyleRule cssStyleRule, String property) {

		CSSDeclaration cssDeclaration =
			cssStyleRule.getDeclarationOfPropertyNameCaseInsensitive(property);

		if (cssDeclaration == null) {
			return;
		}

		CSSExpression cssExpression = cssDeclaration.getExpression();

		List<CSSExpressionMemberTermSimple> cssExpressionMemberTermSimples =
			cssExpression.getAllSimpleMembers();

		if (cssExpressionMemberTermSimples.size() == 4) {
			CSSExpressionMemberTermSimple cssExpressionMemberTermSimple2 =
				cssExpressionMemberTermSimples.get(1);

			String value = cssExpressionMemberTermSimple2.getValue();

			CSSExpressionMemberTermSimple cssExpressionMemberTermSimple4 =
				cssExpressionMemberTermSimples.get(3);

			cssExpressionMemberTermSimple2.setValue(
				cssExpressionMemberTermSimple4.getValue());

			cssExpressionMemberTermSimple4.setValue(value);
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

		List<CSSExpressionMemberTermSimple> cssExpressionMemberTermSimples =
			cssExpression.getAllSimpleMembers();

		if (cssExpressionMemberTermSimples.size() == 4) {
			CSSExpressionMemberTermSimple cssExpressionMemberTermSimple1 =
				cssExpressionMemberTermSimples.get(0);

			String value = cssExpressionMemberTermSimple1.getValue();

			CSSExpressionMemberTermSimple cssExpressionMemberTermSimple2 =
				cssExpressionMemberTermSimples.get(1);

			cssExpressionMemberTermSimple1.setValue(
				cssExpressionMemberTermSimple2.getValue());

			cssExpressionMemberTermSimple2.setValue(value);

			CSSExpressionMemberTermSimple cssExpressionMemberTermSimple3 =
				cssExpressionMemberTermSimples.get(2);

			value = cssExpressionMemberTermSimple3.getValue();

			CSSExpressionMemberTermSimple cssExpressionMemberTermSimple4 =
				cssExpressionMemberTermSimples.get(3);

			cssExpressionMemberTermSimple3.setValue(
				cssExpressionMemberTermSimple4.getValue());

			cssExpressionMemberTermSimple4.setValue(value);
		}
		else if (cssExpressionMemberTermSimples.size() == 3) {
			CSSExpressionMemberTermSimple cssExpressionMemberTermSimple1 =
				cssExpressionMemberTermSimples.get(0);

			String value = cssExpressionMemberTermSimple1.getValue();

			CSSExpressionMemberTermSimple cssExpressionMemberTermSimple2 =
				cssExpressionMemberTermSimples.get(1);

			cssExpressionMemberTermSimple1.setValue(
				cssExpressionMemberTermSimple2.getValue());

			cssExpressionMemberTermSimple2.setValue(value);

			CSSExpressionMemberTermSimple cssExpressionMemberTermSimple3 =
				cssExpressionMemberTermSimples.get(2);

			value = cssExpressionMemberTermSimple3.getValue();

			cssExpressionMemberTermSimple3.setValue(
				cssExpressionMemberTermSimple1.getValue());

			cssExpression.addTermSimple(value);
		}
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

		CSSDeclaration replacementCSSDeclaration =
			cssStyleRule.getDeclarationOfPropertyNameCaseInsensitive(
				asterisk + replacementProperty);

		CSSDeclaration cssDeclaration =
			cssStyleRule.getDeclarationOfPropertyNameCaseInsensitive(
				asterisk + property);

		if (cssDeclaration != null) {
			cssDeclaration.setProperty(asterisk + replacementProperty);
		}

		if (replacementCSSDeclaration != null) {
			replacementCSSDeclaration.setProperty(asterisk + property);
		}
	}

	protected void reverseImage(CSSStyleRule cssStyleRule, String property) {
		CSSDeclaration cssDeclaration =
			cssStyleRule.getDeclarationOfPropertyNameCaseInsensitive(property);

		if (cssDeclaration == null) {
			return;
		}

		CSSExpression cssExpression = cssDeclaration.getExpression();

		List<ICSSExpressionMember> icssExpressionMembers =
			cssExpression.getAllMembers();

		for (ICSSExpressionMember icssExpressionMember :
				icssExpressionMembers) {

			if (icssExpressionMember instanceof CSSExpressionMemberTermURI) {
				CSSExpressionMemberTermURI cssExpressionMemberTermURI =
					(CSSExpressionMemberTermURI)icssExpressionMember;

				String uri = cssExpressionMemberTermURI.getURIString();

				if (uri.contains("right")) {
					uri = uri.replaceAll("right", "left");
				}
				else {
					uri = uri.replaceAll("left", "right");
				}

				cssExpressionMemberTermURI.setURIString(uri);
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

		List<CSSExpressionMemberTermSimple> cssExpressionMemberTermSimples =
			cssExpression.getAllSimpleMembers();

		if (cssExpressionMemberTermSimples.size() > 0) {
			CSSExpressionMemberTermSimple cssExpressionMemberTermSimple =
				cssExpressionMemberTermSimples.get(0);

			String value = cssExpressionMemberTermSimple.getValue();

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

			cssExpressionMemberTermSimple.setValue(value);
		}
	}

	protected String stripProperty(String property) {
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