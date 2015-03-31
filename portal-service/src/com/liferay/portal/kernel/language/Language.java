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

package com.liferay.portal.kernel.language;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.exception.PortalException;

import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.portlet.PortletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Brian Wing Shun Chan
 */
@ProviderType
public interface Language {

	public String format(
		HttpServletRequest request, String pattern, LanguageWrapper argument);

	public String format(
		HttpServletRequest request, String pattern, LanguageWrapper argument,
		boolean translateArguments);

	public String format(
		HttpServletRequest request, String pattern,
		LanguageWrapper[] arguments);

	public String format(
		HttpServletRequest request, String pattern, LanguageWrapper[] arguments,
		boolean translateArguments);

	public String format(
		HttpServletRequest request, String pattern, Object argument);

	public String format(
		HttpServletRequest request, String pattern, Object argument,
		boolean translateArguments);

	public String format(
		HttpServletRequest request, String pattern, Object[] arguments);

	public String format(
		HttpServletRequest request, String pattern, Object[] arguments,
		boolean translateArguments);

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
		ResourceBundle resourceBundle, String pattern, Object argument);

	public String format(
		ResourceBundle resourceBundle, String pattern, Object argument,
		boolean translateArguments);

	public String format(
		ResourceBundle resourceBundle, String pattern, Object[] arguments);

	public String format(
		ResourceBundle resourceBundle, String pattern, Object[] arguments,
		boolean translateArguments);

	public String get(HttpServletRequest request, String key);

	public String get(
		HttpServletRequest request, String key, String defaultValue);

	public String get(Locale locale, String key);

	public String get(Locale locale, String key, String defaultValue);

	public String get(ResourceBundle resourceBundle, String key);

	public String get(
		ResourceBundle resourceBundle, String key, String defaultValue);

	public Locale[] getAvailableLocales();

	public Locale[] getAvailableLocales(long groupId);

	public String getBCP47LanguageId(HttpServletRequest request);

	public String getBCP47LanguageId(Locale locale);

	public String getBCP47LanguageId(PortletRequest portletRequest);

	public String getCharset(Locale locale);

	public String getLanguageId(HttpServletRequest request);

	public String getLanguageId(Locale locale);

	public String getLanguageId(PortletRequest portletRequest);

	public Locale getLocale(long groupId, String languageCode);

	public Locale getLocale(String languageCode);

	public Locale[] getSupportedLocales();

	public String getTimeDescription(
		HttpServletRequest request, long milliseconds);

	public String getTimeDescription(
		HttpServletRequest request, long milliseconds, boolean approximate);

	public String getTimeDescription(
		HttpServletRequest request, Long milliseconds);

	public String getTimeDescription(Locale locale, long milliseconds);

	public String getTimeDescription(
		Locale locale, long milliseconds, boolean approximate);

	public String getTimeDescription(Locale locale, Long milliseconds);

	public void init();

	public boolean isAvailableLanguageCode(String languageCode);

	public boolean isAvailableLocale(Locale locale);

	public boolean isAvailableLocale(long groupId, Locale locale);

	public boolean isAvailableLocale(long groupId, String languageId);

	public boolean isAvailableLocale(String languageId);

	public boolean isBetaLocale(Locale locale);

	public boolean isDuplicateLanguageCode(String languageCode);

	public boolean isInheritLocales(long groupId) throws PortalException;

	public String process(
		ResourceBundle resourceBundle, Locale locale, String content);

	public void resetAvailableGroupLocales(long groupId);

	public void resetAvailableLocales(long companyId);

	public void updateCookie(
		HttpServletRequest request, HttpServletResponse response,
		Locale locale);

}