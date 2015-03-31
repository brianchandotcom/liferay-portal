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
import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;

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
public class LanguageUtil {

	public static String format(
		HttpServletRequest request, String pattern, LanguageWrapper argument) {

		return getLanguage().format(request, pattern, argument);
	}

	public static String format(
		HttpServletRequest request, String pattern, LanguageWrapper argument,
		boolean translateArguments) {

		return getLanguage().format(
			request, pattern, argument, translateArguments);
	}

	public static String format(
		HttpServletRequest request, String pattern,
		LanguageWrapper[] arguments) {

		return getLanguage().format(request, pattern, arguments);
	}

	public static String format(
		HttpServletRequest request, String pattern, LanguageWrapper[] arguments,
		boolean translateArguments) {

		return getLanguage().format(
			request, pattern, arguments, translateArguments);
	}

	public static String format(
		HttpServletRequest request, String pattern, Object argument) {

		return getLanguage().format(request, pattern, argument);
	}

	public static String format(
		HttpServletRequest request, String pattern, Object argument,
		boolean translateArguments) {

		return getLanguage().format(
			request, pattern, argument, translateArguments);
	}

	public static String format(
		HttpServletRequest request, String pattern, Object[] arguments) {

		return getLanguage().format(request, pattern, arguments);
	}

	public static String format(
		HttpServletRequest request, String pattern, Object[] arguments,
		boolean translateArguments) {

		return getLanguage().format(
			request, pattern, arguments, translateArguments);
	}

	public static String format(
		Locale locale, String pattern, List<Object> arguments) {

		return getLanguage().format(locale, pattern, arguments);
	}

	public static String format(
		Locale locale, String pattern, Object argument) {

		return getLanguage().format(locale, pattern, argument);
	}

	public static String format(
		Locale locale, String pattern, Object argument,
		boolean translateArguments) {

		return getLanguage().format(
			locale, pattern, argument, translateArguments);
	}

	public static String format(
		Locale locale, String pattern, Object[] arguments) {

		return getLanguage().format(locale, pattern, arguments);
	}

	public static String format(
		Locale locale, String pattern, Object[] arguments,
		boolean translateArguments) {

		return getLanguage().format(
			locale, pattern, arguments, translateArguments);
	}

	public static String format(
		ResourceBundle resourceBundle, String pattern, Object argument) {

		return getLanguage().format(resourceBundle, pattern, argument);
	}

	public static String format(
		ResourceBundle resourceBundle, String pattern, Object argument,
		boolean translateArguments) {

		return getLanguage().format(
			resourceBundle, pattern, argument, translateArguments);
	}

	public static String format(
		ResourceBundle resourceBundle, String pattern, Object[] arguments) {

		return getLanguage().format(resourceBundle, pattern, arguments);
	}

	public static String format(
		ResourceBundle resourceBundle, String pattern, Object[] arguments,
		boolean translateArguments) {

		return getLanguage().format(
			resourceBundle, pattern, arguments, translateArguments);
	}

	public static String get(HttpServletRequest request, String key) {
		return getLanguage().get(request, key);
	}

	public static String get(
		HttpServletRequest request, String key, String defaultValue) {

		return getLanguage().get(request, key, defaultValue);
	}

	public static String get(Locale locale, String key) {
		return getLanguage().get(locale, key);
	}

	public static String get(Locale locale, String key, String defaultValue) {
		return getLanguage().get(locale, key, defaultValue);
	}

	public static String get(ResourceBundle resourceBundle, String key) {
		return getLanguage().get(resourceBundle, key);
	}

	public static String get(
		ResourceBundle resourceBundle, String key, String defaultValue) {

		return getLanguage().get(resourceBundle, key, defaultValue);
	}

	public static Locale[] getAvailableLocales() {
		return getLanguage().getAvailableLocales();
	}

	public static Locale[] getAvailableLocales(long groupId) {
		return getLanguage().getAvailableLocales(groupId);
	}

	public static String getBCP47LanguageId(HttpServletRequest request) {
		return getLanguage().getBCP47LanguageId(request);
	}

	public static String getBCP47LanguageId(Locale locale) {
		return getLanguage().getBCP47LanguageId(locale);
	}

	public static String getBCP47LanguageId(PortletRequest portletRequest) {
		return getLanguage().getBCP47LanguageId(portletRequest);
	}

	public static String getCharset(Locale locale) {
		return getLanguage().getCharset(locale);
	}

	public static Language getLanguage() {
		PortalRuntimePermission.checkGetBeanProperty(LanguageUtil.class);

		return _language;
	}

	public static String getLanguageId(HttpServletRequest request) {
		return getLanguage().getLanguageId(request);
	}

	public static String getLanguageId(Locale locale) {
		return getLanguage().getLanguageId(locale);
	}

	public static String getLanguageId(PortletRequest portletRequest) {
		return getLanguage().getLanguageId(portletRequest);
	}

	public static Locale getLocale(long groupId, String languageCode) {
		return getLanguage().getLocale(groupId, languageCode);
	}

	public static Locale getLocale(String languageCode) {
		return getLanguage().getLocale(languageCode);
	}

	public static Locale[] getSupportedLocales() {
		return getLanguage().getSupportedLocales();
	}

	public static String getTimeDescription(
		HttpServletRequest request, long milliseconds) {

		return getLanguage().getTimeDescription(request, milliseconds);
	}

	public static String getTimeDescription(
		HttpServletRequest request, long milliseconds, boolean approximate) {

		return getLanguage().getTimeDescription(
			request, milliseconds, approximate);
	}

	public static String getTimeDescription(
		HttpServletRequest request, Long milliseconds) {

		return getLanguage().getTimeDescription(request, milliseconds);
	}

	public static String getTimeDescription(Locale locale, long milliseconds) {
		return getLanguage().getTimeDescription(locale, milliseconds);
	}

	public static String getTimeDescription(
		Locale locale, long milliseconds, boolean approximate) {

		return getLanguage().getTimeDescription(
			locale, milliseconds, approximate);
	}

	public static String getTimeDescription(Locale locale, Long milliseconds) {
		return getLanguage().getTimeDescription(locale, milliseconds);
	}

	public static void init() {
		getLanguage().init();
	}

	public static boolean isAvailableLanguageCode(String languageCode) {
		return getLanguage().isAvailableLanguageCode(languageCode);
	}

	public static boolean isAvailableLocale(Locale locale) {
		return getLanguage().isAvailableLocale(locale);
	}

	public static boolean isAvailableLocale(long groupId, Locale locale) {
		return getLanguage().isAvailableLocale(groupId, locale);
	}

	public static boolean isAvailableLocale(long groupId, String languageId) {
		return getLanguage().isAvailableLocale(groupId, languageId);
	}

	public static boolean isAvailableLocale(String languageId) {
		return getLanguage().isAvailableLocale(languageId);
	}

	public static boolean isBetaLocale(Locale locale) {
		return getLanguage().isBetaLocale(locale);
	}

	public static boolean isDuplicateLanguageCode(String languageCode) {
		return getLanguage().isDuplicateLanguageCode(languageCode);
	}

	public static boolean isInheritLocales(long groupId)
		throws PortalException {

		return getLanguage().isInheritLocales(groupId);
	}

	public static boolean isValidLanguageKey(Locale locale, String key) {
		String value = getLanguage().get(locale, key, StringPool.BLANK);

		return Validator.isNotNull(value);
	}

	public static String process(
		ResourceBundle resourceBundle, Locale locale, String content) {

		return getLanguage().process(resourceBundle, locale, content);
	}

	public static void resetAvailableGroupLocales(long groupId) {
		getLanguage().resetAvailableGroupLocales(groupId);
	}

	public static void resetAvailableLocales(long companyId) {
		getLanguage().resetAvailableLocales(companyId);
	}

	public static void updateCookie(
		HttpServletRequest request, HttpServletResponse response,
		Locale locale) {

		getLanguage().updateCookie(request, response, locale);
	}

	public void setLanguage(Language language) {
		PortalRuntimePermission.checkSetBeanProperty(getClass());

		_language = language;
	}

	private static Language _language;

}