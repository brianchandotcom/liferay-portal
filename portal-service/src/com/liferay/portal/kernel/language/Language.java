/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.language;

import java.util.List;
import java.util.Locale;

import javax.portlet.PortletConfig;
import javax.portlet.PortletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;

/**
 * @author Brian Wing Shun Chan
 */
public interface Language {

	public String format(Locale locale, String pattern, List<Object> arguments);

	public String format(Locale locale, String pattern, Object argument);

	public String format(
		Locale locale, String pattern, Object argument,
		boolean translateArguments);

	public String format(Locale locale, String pattern, Object[] arguments);

	public String format(
		Locale locale, String pattern, Object[] arguments,
		boolean translateArguments);

	public String format(
		PageContext pageContext, String pattern, LanguageWrapper argument);

	public String format(
		PageContext pageContext, String pattern, LanguageWrapper argument,
		boolean translateArguments);

	public String format(
		PageContext pageContext, String pattern, LanguageWrapper[] arguments);

	public String format(
		PageContext pageContext, String pattern, LanguageWrapper[] arguments,
		boolean translateArguments);

	public String format(
		PageContext pageContext, String pattern, Object argument);

	public String format(
		PageContext pageContext, String pattern, Object argument,
		boolean translateArguments);

	public String format(
		PageContext pageContext, String pattern, Object[] arguments);

	public String format(
		PageContext pageContext, String pattern, Object[] arguments,
		boolean translateArguments);

	public String format(
		PortletConfig portletConfig, Locale locale, String pattern,
		Object argument);

	public String format(
		PortletConfig portletConfig, Locale locale, String pattern,
		Object argument, boolean translateArguments);

	public String format(
		PortletConfig portletConfig, Locale locale, String pattern,
		Object[] arguments);

	public String format(
		PortletConfig portletConfig, Locale locale, String pattern,
		Object[] arguments, boolean translateArguments);

	public String get(Locale locale, String key);

	public String get(Locale locale, String key, String defaultValue);

	public String get(PageContext pageContext, String key);

	public String get(PageContext pageContext, String key, String defaultValue);

	public String get(PortletConfig portletConfig, Locale locale, String key);

	public String get(
		PortletConfig portletConfig, Locale locale, String key,
		String defaultValue);

	public Locale[] getAvailableLocales();

	public String getCharset(Locale locale);

	public String getLanguageId(HttpServletRequest request);

	public String getLanguageId(Locale locale);

	public String getLanguageId(PortletRequest portletRequest);

	public Locale getLocale(String languageCode);

	public Locale[] getSupportedLocales();

	public String getTimeDescription(Locale locale, long milliseconds);

	public String getTimeDescription(
		Locale locale, long milliseconds, boolean approximate);

	public String getTimeDescription(Locale locale, Long milliseconds);

	public String getTimeDescription(
		PageContext pageContext, long milliseconds);

	public String getTimeDescription(
		PageContext pageContext, long milliseconds, boolean approximate);

	public String getTimeDescription(
		PageContext pageContext, Long milliseconds);

	public void init();

	public boolean isAvailableLanguageCode(String languageCode);

	public boolean isAvailableLocale(Locale locale);

	public boolean isAvailableLocale(String languageId);

	public boolean isBetaLocale(Locale locale);

	public boolean isDuplicateLanguageCode(String languageCode);

	public void resetAvailableLocales(long companyId);

	public void updateCookie(
		HttpServletRequest request, HttpServletResponse response,
		Locale locale);

}