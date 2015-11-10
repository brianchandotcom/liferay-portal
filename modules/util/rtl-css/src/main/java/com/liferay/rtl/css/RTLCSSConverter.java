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
import com.helger.css.decl.CSSExpressionMemberFunction;
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
		CSSWriterSettings cssWriterSettings = new CSSWriterSettings(
			ECSSVersion.CSS30, compress);

		cssWriterSettings.setOptimizedOutput(compress);
		cssWriterSettings.setRemoveUnnecessaryCode(Boolean.TRUE);

		_cssWriter = new CSSWriter(cssWriterSettings);

		_cssWriter.setWriteFooterText(false);
		_cssWriter.setWriteHeaderText(false);
	}

	public String process(String css) {
		CascadingStyleSheet cascadingStyleSheet = CSSReader.readFromString(
			css, CCharset.CHARSET_UTF_8_OBJ, ECSSVersion.CSS30,
			new DoNothingCSSParseErrorHandler());

		processRules(cascadingStyleSheet.getAllRules());

		return _cssWriter.getCSSAsString(cascadingStyleSheet);
	}

	protected void convertBackgroundProperties(CSSDeclaration cssDeclaration) {
		CSSExpression cssExpression = cssDeclaration.getExpression();

		List<ICSSExpressionMember> icssExpressionMembers =
			cssExpression.getAllMembers();

		for (
			ICSSExpressionMember icssExpressionMember : icssExpressionMembers) {

			if (icssExpressionMember instanceof CSSExpressionMemberTermURI) {
				CSSExpressionMemberTermURI cssExpressionMemberTermURI =
					(CSSExpressionMemberTermURI)icssExpressionMember;

				String uri = cssExpressionMemberTermURI.getURIString();

				int index = uri.lastIndexOf("/") + 1;

				String fileName = reverse(uri.substring(index));

				cssExpressionMemberTermURI.setURIString(
					uri.substring(0, index) + fileName);
			}
			else if (icssExpressionMember instanceof
				CSSExpressionMemberTermSimple) {

				CSSExpressionMemberTermSimple cssExpressionMemberTermSimple =
					(CSSExpressionMemberTermSimple)icssExpressionMember;

				cssExpressionMemberTermSimple.setValue(
					reverse(cssExpressionMemberTermSimple.getValue()));
			}
			else if (icssExpressionMember instanceof
				CSSExpressionMemberFunction) {

				CSSExpressionMemberFunction cssExpressionMemberFunction =
					(CSSExpressionMemberFunction)icssExpressionMember;

				reverseValue(cssExpressionMemberFunction.getExpression());
			}
		}

		List<CSSExpressionMemberTermSimple> cssExpressionMemberTermSimples =
			cssExpression.getAllSimpleMembers();

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

	protected void convertShorthandProperties(CSSDeclaration cssDeclaration) {
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

	protected void convertShorthandRadiusProperties(
		CSSDeclaration cssDeclaration) {

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

	protected void processRule(CSSStyleRule cssStyleRule) {
		for (CSSDeclaration cssDeclaration :
			cssStyleRule.getAllDeclarations()) {

			String property = stripAsterick(cssDeclaration.getProperty());

			if (_backgroundProperties.contains(property)) {
				convertBackgroundProperties(cssDeclaration);
			}
			else if (_iconProperties.contains(property)) {
				replaceIcons(cssDeclaration);
			}
			else if (_reverseProperties.contains(property)) {
				reverseProperty(cssDeclaration);
			}
			else if (_reverseValueProperties.contains(property)) {
				reverseValue(cssDeclaration);
			}
			else if (_shorthandRadiusProperties.contains(property)) {
				convertShorthandRadiusProperties(cssDeclaration);
			}
			else if (_shorthandProperties.contains(property)) {
				convertShorthandProperties(cssDeclaration);
			}
		}
	}

	protected void processRules(List<ICSSTopLevelRule> icssTopLevelRules) {
		for (ICSSTopLevelRule icssTopLevelRule : icssTopLevelRules) {
			if (icssTopLevelRule instanceof CSSMediaRule) {
				CSSMediaRule cssMediaRule = (CSSMediaRule)icssTopLevelRule;

				processRules(cssMediaRule.getAllRules());
			}
			else if (icssTopLevelRule instanceof CSSStyleRule) {
				processRule((CSSStyleRule)icssTopLevelRule);
			}
		}
	}

	protected void replaceIcons(CSSDeclaration cssDeclaration) {
		CSSExpression cssExpression = cssDeclaration.getExpression();

		List<CSSExpressionMemberTermSimple> cssExpressionMemberTermSimples =
			cssExpression.getAllSimpleMembers();

		if (cssExpressionMemberTermSimples.size() > 0) {
			CSSExpressionMemberTermSimple cssExpressionMemberTermSimple =
				cssExpressionMemberTermSimples.get(0);

			String replacementValue = _replacementIcons.get(
				cssExpressionMemberTermSimple.getValue());

			if (replacementValue != null) {
				cssExpressionMemberTermSimple.setValue(replacementValue);
			}
		}
	}

	protected String reverse(String s) {
		if (s.contains("right")) {
			s = s.replace("right", "left");
		}
		else if (s.contains("left")) {
			s = s.replace("left", "right");
		}

		return s;
	}

	protected void reverseProperty(CSSDeclaration cssDeclaration) {
		String property = cssDeclaration.getProperty();

		String asterisk = "";

		if (property.startsWith("*")) {
			asterisk = "*";

			property = stripAsterick(property);
		}

		property = reverse(property);

		cssDeclaration.setProperty(asterisk + property);
	}

	protected void reverseValue(CSSDeclaration cssDeclaration) {
		reverseValue(cssDeclaration.getExpression());
	}

	protected void reverseValue(CSSExpression cssExpression) {
		List<CSSExpressionMemberTermSimple> cssExpressionMemberTermSimples =
			cssExpression.getAllSimpleMembers();

		if (cssExpressionMemberTermSimples.size() > 0) {
			CSSExpressionMemberTermSimple cssExpressionMemberTermSimple =
				cssExpressionMemberTermSimples.get(0);

			String value = cssExpressionMemberTermSimple.getValue();

			value = reverse(value);

			if (value.contains("rtl")) {
				value = value.replace("rtl", "ltr");
			}
			else if (value.contains("ltr")) {
				value = value.replace("ltr", "rtl");
			}

			cssExpressionMemberTermSimple.setValue(value);
		}
	}

	protected String stripAsterick(String property) {
		return property.replaceAll("\\**\\b", "");
	}

	private static final List<String> _backgroundProperties = Arrays.asList(
		"background", "background-image", "background-position");
	private static final List<String> _iconProperties = Arrays.asList(
		"content");
	private static final Pattern _percentOrLengthPattern = Pattern.compile(
		"(\\d+)([a-z]{2}|%)");
	private static final Pattern _percentPattern = Pattern.compile("\\d+%");
	private static final Map<String, String> _replacementIcons =
		new HashMap<>();
	private static final List<String> _reverseProperties = Arrays.asList(
		"-moz-border-radius-bottomright", "-moz-border-radius-bottomleft",
		"-moz-border-radius-topright", "-moz-border-radius-topleft",
		"border-radius-topleft", "border-radius-topright",
		"border-top-right-radius", "border-top-left-radius",
		"-webkit-border-bottom-right-radius",
		"-webkit-border-bottom-left-radius", "-webkit-border-top-right-radius",
		"-webkit-border-top-left-radius", "border-bottom-right-radius",
		"border-bottom-left-radius", "border-left", "border-right",
		"border-left-color", "border-right-color", "border-left-width",
		"border-right-width", "border-radius-bottomleft",
		"border-radius-bottomright", "left", "right", "margin-left",
		"margin-right", "padding-left", "padding-right");
	private static final List<String> _reverseValueProperties = Arrays.asList(
		"clear", "direction", "float", "text-align");
	private static final List<String> _shorthandProperties = Arrays.asList(
		"border-color", "border-style", "border-width", "margin", "padding");
	private static final List<String> _shorthandRadiusProperties =
		Arrays.asList(
			"-moz-border-radius", "-webkit-border-radius", "border-radius");

	static {
		_replacementIcons.put("\"\\f060\"", "\"\\f061\"");
		_replacementIcons.put("\"\\f061\"", "\"\\f060\"");
		_replacementIcons.put("\"\\f0a4\"", "\"\\f0a5\"");
		_replacementIcons.put("\"\\f0a5\"", "\"\\f0a4\"");
		_replacementIcons.put("\"\\f0d9\"", "\"\\f0da\"");
		_replacementIcons.put("\"\\f0da\"", "\"\\f0d9\"");
		_replacementIcons.put("\"\\f100\"", "\"\\f101\"");
		_replacementIcons.put("\"\\f101\"", "\"\\f100\"");
		_replacementIcons.put("\"\\f104\"", "\"\\f105\"");
		_replacementIcons.put("\"\\f105\"", "\"\\f104\"");
		_replacementIcons.put("\"\\f137\"", "\"\\f138\"");
		_replacementIcons.put("\"\\f138\"", "\"\\f137\"");
		_replacementIcons.put("\"\\f053\"", "\"\\f054\"");
		_replacementIcons.put("\"\\f054\"", "\"\\f053\"");
		_replacementIcons.put("\"\\f0a8\"", "\"\\f0a9\"");
		_replacementIcons.put("\"\\f0a9\"", "\"\\f0a8\"");
		_replacementIcons.put("\"\\f177\"", "\"\\f178\"");
		_replacementIcons.put("\"\\f178\"", "\"\\f177\"");
	}

	private final CSSWriter _cssWriter;

}