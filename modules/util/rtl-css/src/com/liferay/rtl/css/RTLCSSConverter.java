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
import com.helger.css.reader.CSSReader;
import com.helger.css.writer.CSSWriterSettings;

import com.liferay.portal.kernel.util.StringBundler;

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
		_cssWriterSettings = new CSSWriterSettings(ECSSVersion.CSS30, true);
	}

	public RTLCSSConverter(boolean compress) {
		_cssWriterSettings = new CSSWriterSettings(ECSSVersion.CSS30, compress);
	}

	public String process(String css) {
		CascadingStyleSheet cascadingStyleSheet = CSSReader.readFromString(
			css, CCharset.CHARSET_UTF_8_OBJ, ECSSVersion.CSS30);

		List<CSSStyleRule> cssStyleRules =
			cascadingStyleSheet.getAllStyleRules();

		StringBundler sb = new StringBundler(cssStyleRules.size());

		for (CSSStyleRule cssStyleRule : cssStyleRules) {
			for (String property : REPLACEMENT_STYLES.keySet()) {
				replaceStyle(cssStyleRule, property);
			}

			for (CSSDeclaration cssDeclaration :
					cssStyleRule.getAllDeclarations()) {

				String property = cssDeclaration.getProperty();

				String strippedProperty = stripProperty(property);

				if (SHORTHAND_STYLES.contains(strippedProperty)) {
					convertShorthand(cssStyleRule, property);
				}
				else if (SHORTHAND_RADIUS_STYLES.contains(strippedProperty)) {
					convertShorthandRadius(cssStyleRule, property);
				}
				else if (REVERSE_STYLES.contains(strippedProperty)) {
					reverseStyle(cssStyleRule, property);
				}
				else if (REVERSE_IMAGE_STYLES.contains(strippedProperty)) {
					reverseImage(cssStyleRule, property);
				}
				else if (BGPOSITION_STYLES.contains(strippedProperty)) {
					convertBGPosition(cssStyleRule, property);
				}
			}

			sb.append(cssStyleRule.getAsCSSString(_cssWriterSettings, 1));
		}

		return sb.toString();
	}

	protected void convertBGPosition(CSSStyleRule cssStyleRule, String property) {
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

			Matcher matcher = REGEX_PERCENT_OR_LENGTH.matcher(value);

			if (matcher.matches()) {
				cssExpression.addTermSimple(value);

				cssExpressionMemberTermSimple.setValue("right");
			}
		}
		else if (cssExpressionMemberTermSimples.size() == 2) {
			CSSExpressionMemberTermSimple cssExpressionMemberTermSimple =
				cssExpressionMemberTermSimples.get(0);

			String value = cssExpressionMemberTermSimple.getValue();

			Matcher matcher = REGEX_PERCENT.matcher(value);

			if (matcher.matches()) {
				int delta = Integer.valueOf(value.replaceAll("[^\\d]", ""), 10);

				value = (100 - delta) + "%";

				cssExpressionMemberTermSimple.setValue(value);
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

		List<CSSExpressionMemberTermSimple> cssExpressionMemberTermSimples =
			cssExpression.getAllSimpleMembers();

		if (cssExpressionMemberTermSimples.size() == 4) {
			CSSExpressionMemberTermSimple cssExpressionMemberTermSimple2 =
				cssExpressionMemberTermSimples.get(1);
			CSSExpressionMemberTermSimple cssExpressionMemberTermSimple4 =
				cssExpressionMemberTermSimples.get(3);

			String value = cssExpressionMemberTermSimple2.getValue();

			cssExpressionMemberTermSimple2.setValue(
				cssExpressionMemberTermSimple4.getValue());

			cssExpressionMemberTermSimple4.setValue(value);
		}
	}

	protected void convertShorthandRadius(CSSStyleRule style, String property) {
		CSSDeclaration declaration =
			style.getDeclarationOfPropertyNameCaseInsensitive(property);

		if (declaration != null) {
			CSSExpression expression = declaration.getExpression();
			List< CSSExpressionMemberTermSimple> members =
				expression.getAllSimpleMembers();

			if (members.size() == 4) {
				CSSExpressionMemberTermSimple member1 = members.get(0);
				CSSExpressionMemberTermSimple member2 = members.get(1);
				CSSExpressionMemberTermSimple member3 = members.get(2);
				CSSExpressionMemberTermSimple member4 = members.get(3);
				String temp = member1.getValue();
				member1.setValue(member2.getValue());
				member2.setValue(temp);
				temp = member3.getValue();
				member3.setValue(member4.getValue());
				member4.setValue(temp);
			}
			else if (members.size() == 3) {
				CSSExpressionMemberTermSimple member1 = members.get(0);
				CSSExpressionMemberTermSimple member2 = members.get(1);
				CSSExpressionMemberTermSimple member3 = members.get(2);
				String temp = member1.getValue();
				member1.setValue(member2.getValue());
				member2.setValue(temp);
				temp = member3.getValue();
				member3.setValue(member1.getValue());
				expression.addTermSimple(temp);
			}
		}
	}

	protected void replaceStyle(CSSStyleRule style, String property) {
		replaceStyle(style, property, false);
		replaceStyle(style, property, true);
	}

	protected void replaceStyle(
		CSSStyleRule style, String property, boolean addAsterisk) {

		String asterisk = addAsterisk ? "*" : "";

		CSSDeclaration declaration =
				style.getDeclarationOfPropertyNameCaseInsensitive(
					asterisk + property);

		String replacementProperty = REPLACEMENT_STYLES.get(property);

		CSSDeclaration replacementDeclaration =
				style.getDeclarationOfPropertyNameCaseInsensitive(
					asterisk + replacementProperty);

		if (declaration != null) {
			declaration.setProperty(asterisk + replacementProperty);
		}

		if (replacementDeclaration != null) {
			replacementDeclaration.setProperty(asterisk + property);
		}
	}

	protected void reverseImage(CSSStyleRule style, String property) {
		CSSDeclaration declaration =
			style.getDeclarationOfPropertyNameCaseInsensitive(property);

		if (declaration != null) {
			CSSExpression expression = declaration.getExpression();
			List<ICSSExpressionMember> members = expression.getAllMembers();

			for (ICSSExpressionMember member : members) {
				if (member instanceof CSSExpressionMemberTermURI) {
					CSSExpressionMemberTermURI termMember =
						(CSSExpressionMemberTermURI)member;

					String uri = termMember.getURIString();

					if (uri.contains("right")) {
						uri = uri.replaceAll("right", "left");
					}
					else {
						uri = uri.replaceAll("left", "right");
					}

					termMember.setURIString(uri);
				}
			}
		}
	}

	protected void reverseStyle(CSSStyleRule style, String property) {
		CSSDeclaration declaration =
			style.getDeclarationOfPropertyNameCaseInsensitive(property);

		if (declaration != null) {
			CSSExpression expression = declaration.getExpression();
			List< CSSExpressionMemberTermSimple> members =
				expression.getAllSimpleMembers();

			if (members.size() > 0) {
				CSSExpressionMemberTermSimple member = members.get(0);

				String value = member.getValue();

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

				member.setValue(value);
			}
		}
	}

	protected String stripProperty(String property) {
		return property.replaceAll("\\**\\b", "");
	}

	private final CSSWriterSettings _cssWriterSettings;

	private static final List<String> BGPOSITION_STYLES = Arrays.asList(
			"background-position"
	);

	private static final List<String> REVERSE_STYLES = Arrays.asList(
			"text-align", "float", "clear", "direction"
	);
	private static final Map<String, String> REPLACEMENT_STYLES =
		new HashMap<>();

	public static final List<String> SHORTHAND_STYLES = Arrays.asList(
			"padding", "margin", "border-color", "border-width", "border-style"
	);

	public static final List<String> SHORTHAND_RADIUS_STYLES = Arrays.asList(
			"-webkit-border-radius", "-moz-border-radius", "border-radius"
	);

	public static final List<String> REVERSE_IMAGE_STYLES = Arrays.asList(
			"background", "background-image"
	);

	public static final Pattern REGEX_PERCENT = Pattern.compile("\\d+%");

	public static final Pattern REGEX_PERCENT_OR_LENGTH = Pattern.compile(
		"(\\d+)([a-z]{2}|%)");

	static {
		REPLACEMENT_STYLES.put("margin-left", "margin-right");
		REPLACEMENT_STYLES.put("padding-left", "padding-right");
		REPLACEMENT_STYLES.put("border-left", "border-right");
		REPLACEMENT_STYLES.put("border-left-color", "border-right-color");
		REPLACEMENT_STYLES.put("border-left-width", "border-right-width");
		REPLACEMENT_STYLES.put(
			"border-radius-bottomleft", "border-radius-bottomright");
		REPLACEMENT_STYLES.put(
			"border-bottom-right-radius", "border-bottom-left-radius");
		REPLACEMENT_STYLES.put(
				"-webkit-border-bottom-right-radius",
				"-webkit-border-bottom-left-radius");
		REPLACEMENT_STYLES.put(
				"-moz-border-radius-bottomright",
				"-moz-border-radius-bottomleft");
		REPLACEMENT_STYLES.put(
			"border-radius-topleft", "border-radius-topright");
		REPLACEMENT_STYLES.put(
			"border-top-right-radius", "border-top-left-radius");
		REPLACEMENT_STYLES.put(
				"-webkit-border-top-right-radius",
				"-webkit-border-top-left-radius");
		REPLACEMENT_STYLES.put(
			"-moz-border-radius-topright", "-moz-border-radius-topleft");
		REPLACEMENT_STYLES.put("left", "right");
	}
}