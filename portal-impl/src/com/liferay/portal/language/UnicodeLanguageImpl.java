/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.language;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.language.LanguageWrapper;
import com.liferay.portal.kernel.language.UnicodeLanguage;
import com.liferay.portal.kernel.security.pacl.DoPrivileged;
import com.liferay.portal.kernel.util.UnicodeFormatter;

import java.util.Locale;

import javax.portlet.PortletConfig;

import javax.servlet.jsp.PageContext;

/**
 * @author Brian Wing Shun Chan
 */
@DoPrivileged
public class UnicodeLanguageImpl implements UnicodeLanguage {

	public String format(Locale locale, String pattern, Object argument) {
		return UnicodeFormatter.toString(
			LanguageUtil.format(locale, pattern, argument));
	}

	public String format(
		Locale locale, String pattern, Object argument,
		boolean translateArguments) {

		return UnicodeFormatter.toString(
			LanguageUtil.format(locale, pattern, argument, translateArguments));
	}

	public String format(Locale locale, String pattern, Object[] arguments) {
		return UnicodeFormatter.toString(
			LanguageUtil.format(locale, pattern, arguments));
	}

	public String format(
		Locale locale, String pattern, Object[] arguments,
		boolean translateArguments) {

		return UnicodeFormatter.toString(
			LanguageUtil.format(
				locale, pattern, arguments, translateArguments));
	}

	public String format(
		PageContext pageContext, String pattern, LanguageWrapper argument) {

		return UnicodeFormatter.toString(
			LanguageUtil.format(pageContext, pattern, argument));
	}

	public String format(
		PageContext pageContext, String pattern, LanguageWrapper argument,
		boolean translateArguments) {

		return UnicodeFormatter.toString(
			LanguageUtil.format(
				pageContext, pattern, argument, translateArguments));
	}

	public String format(
		PageContext pageContext, String pattern, LanguageWrapper[] arguments) {

		return UnicodeFormatter.toString(
			LanguageUtil.format(pageContext, pattern, arguments));
	}

	public String format(
		PageContext pageContext, String pattern, LanguageWrapper[] arguments,
		boolean translateArguments) {

		return UnicodeFormatter.toString(
			LanguageUtil.format(
				pageContext, pattern, arguments, translateArguments));
	}

	public String format(
		PageContext pageContext, String pattern, Object argument) {

		return UnicodeFormatter.toString(
			LanguageUtil.format(pageContext, pattern, argument));
	}

	public String format(
		PageContext pageContext, String pattern, Object argument,
		boolean translateArguments) {

		return UnicodeFormatter.toString(
			LanguageUtil.format(
				pageContext, pattern, argument, translateArguments));
	}

	public String format(
		PageContext pageContext, String pattern, Object[] arguments) {

		return UnicodeFormatter.toString(
			LanguageUtil.format(pageContext, pattern, arguments));
	}

	public String format(
		PageContext pageContext, String pattern, Object[] arguments,
		boolean translateArguments) {

		return UnicodeFormatter.toString(
			LanguageUtil.format(
				pageContext, pattern, arguments, translateArguments));
	}

	public String format(
		PortletConfig portletConfig, Locale locale, String pattern,
		Object argument) {

		return UnicodeFormatter.toString(
			LanguageUtil.format(portletConfig, locale, pattern, argument));
	}

	public String format(
		PortletConfig portletConfig, Locale locale, String pattern,
		Object argument, boolean translateArguments) {

		return UnicodeFormatter.toString(
			LanguageUtil.format(
				portletConfig, locale, pattern, argument, translateArguments));
	}

	public String format(
		PortletConfig portletConfig, Locale locale, String pattern,
		Object[] arguments) {

		return UnicodeFormatter.toString(
			LanguageUtil.format(portletConfig, locale, pattern, arguments));
	}

	public String format(
		PortletConfig portletConfig, Locale locale, String pattern,
		Object[] arguments, boolean translateArguments) {

		return UnicodeFormatter.toString(
			LanguageUtil.format(
				portletConfig, locale, pattern, arguments, translateArguments));
	}

	public String get(Locale locale, String key) {
		return UnicodeFormatter.toString(LanguageUtil.get(locale, key));
	}

	public String get(Locale locale, String key, String defaultValue) {
		return UnicodeFormatter.toString(
			LanguageUtil.get(locale, key, defaultValue));
	}

	public String get(PageContext pageContext, String key) {
		return UnicodeFormatter.toString(LanguageUtil.get(pageContext, key));
	}

	public String get(
		PageContext pageContext, String key, String defaultValue) {

		return UnicodeFormatter.toString(
			LanguageUtil.get(pageContext, key, defaultValue));
	}

	public String get(PortletConfig portletConfig, Locale locale, String key) {
		return UnicodeFormatter.toString(
			LanguageUtil.get(portletConfig, locale, key));
	}

	public String get(
		PortletConfig portletConfig, Locale locale, String key,
		String defaultValue) {

		return UnicodeFormatter.toString(
			LanguageUtil.get(portletConfig, locale, key, defaultValue));
	}

	public String getTimeDescription(
		PageContext pageContext, long milliseconds) {

		return UnicodeFormatter.toString(
			LanguageUtil.getTimeDescription(pageContext, milliseconds));
	}

	public String getTimeDescription(
		PageContext pageContext, Long milliseconds) {

		return UnicodeFormatter.toString(
			LanguageUtil.getTimeDescription(pageContext, milliseconds));
	}

}