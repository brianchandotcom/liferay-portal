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

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.ResourceBundleLoader;
import com.liferay.portal.kernel.util.Validator;

import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.function.Supplier;

import javax.portlet.PortletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Brian Wing Shun Chan
 */
public class LanguageUtil {

	public static String format(
		HttpServletRequest httpServletRequest, String pattern,
		LanguageWrapper argument) {

		return getLanguage().format(httpServletRequest, pattern, argument);
	}

	public static String format(
		HttpServletRequest httpServletRequest, String pattern,
		LanguageWrapper argument, boolean translateArguments) {

		return getLanguage().format(
			httpServletRequest, pattern, argument, translateArguments);
	}

	public static String format(
		HttpServletRequest httpServletRequest, String pattern,
		LanguageWrapper[] arguments) {

		return getLanguage().format(httpServletRequest, pattern, arguments);
	}

	public static String format(
		HttpServletRequest httpServletRequest, String pattern,
		LanguageWrapper[] arguments, boolean translateArguments) {

		return getLanguage().format(
			httpServletRequest, pattern, arguments, translateArguments);
	}

	public static String format(
		HttpServletRequest httpServletRequest, String pattern,
		Object argument) {

		return getLanguage().format(httpServletRequest, pattern, argument);
	}

	public static String format(
		HttpServletRequest httpServletRequest, String pattern, Object argument,
		boolean translateArguments) {

		return getLanguage().format(
			httpServletRequest, pattern, argument, translateArguments);
	}

	public static String format(
		HttpServletRequest httpServletRequest, String pattern,
		Object[] arguments) {

		return getLanguage().format(httpServletRequest, pattern, arguments);
	}

	public static String format(
		HttpServletRequest httpServletRequest, String pattern,
		Object[] arguments, boolean translateArguments) {

		return getLanguage().format(
			httpServletRequest, pattern, arguments, translateArguments);
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

	public static String formatStorageSize(double size, Locale locale) {
		return getLanguage().formatStorageSize(size, locale);
	}

	public static String get(
		HttpServletRequest httpServletRequest, ResourceBundle resourceBundle,
		String key) {

		return getLanguage().get(httpServletRequest, resourceBundle, key);
	}

	public static String get(
		HttpServletRequest httpServletRequest, ResourceBundle resourceBundle,
		String key, String defaultValue) {

		return getLanguage().get(
			httpServletRequest, resourceBundle, key, defaultValue);
	}

	public static String get(
		HttpServletRequest httpServletRequest, String key) {

		return getLanguage().get(httpServletRequest, key);
	}

	public static String get(
		HttpServletRequest httpServletRequest, String key,
		String defaultValue) {

		return getLanguage().get(httpServletRequest, key, defaultValue);
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

	public static Set<Locale> getAvailableLocales() {
		return getLanguage().getAvailableLocales();
	}

	public static Set<Locale> getAvailableLocales(long groupId) {
		return getLanguage().getAvailableLocales(groupId);
	}

	public static String getBCP47LanguageId(
		HttpServletRequest httpServletRequest) {

		return getLanguage().getBCP47LanguageId(httpServletRequest);
	}

	public static String getBCP47LanguageId(Locale locale) {
		return getLanguage().getBCP47LanguageId(locale);
	}

	public static String getBCP47LanguageId(PortletRequest portletRequest) {
		return getLanguage().getBCP47LanguageId(portletRequest);
	}

	public static Set<Locale> getCompanyAvailableLocales(long companyId) {
		return getLanguage().getCompanyAvailableLocales(companyId);
	}

	public static Language getLanguage() {
		return _language;
	}

	public static String getLanguageId(HttpServletRequest httpServletRequest) {
		return getLanguage().getLanguageId(httpServletRequest);
	}

	public static String getLanguageId(Locale locale) {
		return getLanguage().getLanguageId(locale);
	}

	public static String getLanguageId(PortletRequest portletRequest) {
		return getLanguage().getLanguageId(portletRequest);
	}

	public static long getLastModified() {
		return getLanguage().getLastModified();
	}

	public static Locale getLocale(long groupId, String languageCode) {
		return getLanguage().getLocale(groupId, languageCode);
	}

	public static Locale getLocale(String languageCode) {
		return getLanguage().getLocale(languageCode);
	}

	public static ResourceBundleLoader getPortalResourceBundleLoader() {
		return getLanguage().getPortalResourceBundleLoader();
	}

	public static Set<Locale> getSupportedLocales() {
		return getLanguage().getSupportedLocales();
	}

	public static String getTimeDescription(
		HttpServletRequest httpServletRequest, long milliseconds) {

		return getLanguage().getTimeDescription(
			httpServletRequest, milliseconds);
	}

	public static String getTimeDescription(
		HttpServletRequest httpServletRequest, long milliseconds,
		boolean approximate) {

		return getLanguage().getTimeDescription(
			httpServletRequest, milliseconds, approximate);
	}

	public static String getTimeDescription(
		HttpServletRequest httpServletRequest, Long milliseconds) {

		return getLanguage().getTimeDescription(
			httpServletRequest, milliseconds);
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

	public static boolean isSameLanguage(Locale locale1, Locale locale2) {
		return getLanguage().isSameLanguage(locale1, locale2);
	}

	public static boolean isValidLanguageKey(Locale locale, String key) {
		String value = getLanguage().get(locale, key, StringPool.BLANK);

		return Validator.isNotNull(value);
	}

	public static String process(
		Supplier<ResourceBundle> resourceBundleSupplier, Locale locale,
		String content) {

		return getLanguage().process(resourceBundleSupplier, locale, content);
	}

	public static void resetAvailableGroupLocales(long groupId) {
		getLanguage().resetAvailableGroupLocales(groupId);
	}

	public static void resetAvailableLocales(long companyId) {
		getLanguage().resetAvailableLocales(companyId);
	}

	public static void updateCookie(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse, Locale locale) {

		getLanguage().updateCookie(
			httpServletRequest, httpServletResponse, locale);
	}

	public void setLanguage(Language language) {
		_language = language;
	}

	private static Language _language;

}