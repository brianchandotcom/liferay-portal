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

package com.liferay.portal.language;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.language.LanguageWrapper;
import com.liferay.portal.kernel.language.UnicodeLanguage;
import com.liferay.portal.kernel.security.pacl.DoPrivileged;
import com.liferay.portal.kernel.util.UnicodeFormatter;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;

/**
 * You'll find documentation for the behaviour of many of the methods in this
 * class by looking up the equivalent descriptions in {@link LanguageImpl}  
 * 
 * @author Brian Wing Shun Chan
 */
@DoPrivileged
public class UnicodeLanguageImpl implements UnicodeLanguage {

	/**
	 * @see {@link LanguageImpl.format(HttpServletRequest, String, LanguageWrapper)}
	 */
	@Override
	public String format(
		HttpServletRequest request, String pattern, LanguageWrapper argument) {

		return UnicodeFormatter.toString(
			LanguageUtil.format(request, pattern, argument));
	}

	/**
	 * @see {@link LanguageImpl.format(HttpServletRequest, String, LanguageWrapper, boolean)}
	 */
	@Override
	public String format(
		HttpServletRequest request, String pattern, LanguageWrapper argument,
		boolean translateArguments) {

		return UnicodeFormatter.toString(
			LanguageUtil.format(
				request, pattern, argument, translateArguments));
	}

	/**
	 * @see {@link LanguageImpl.format(HttpServletRequest, String, LanguageWrapper[])}
	 */
	@Override
	public String format(
		HttpServletRequest request, String pattern,
		LanguageWrapper[] arguments) {

		return UnicodeFormatter.toString(
			LanguageUtil.format(request, pattern, arguments));
	}

	/**
	 * @see {@link LanguageImpl.format(HttpServletRequest, String, LanguageWrapper[], boolean)}
	 */
	@Override
	public String format(
		HttpServletRequest request, String pattern, LanguageWrapper[] arguments,
		boolean translateArguments) {

		return UnicodeFormatter.toString(
			LanguageUtil.format(
				request, pattern, arguments, translateArguments));
	}

	/**
	 * @see {@link LanguageImpl.format(HttpServletRequest, String, Object)}
	 */
	@Override
	public String format(
		HttpServletRequest request, String pattern, Object argument) {

		return UnicodeFormatter.toString(
			LanguageUtil.format(request, pattern, argument));
	}

	/**
	 * @see {@link LanguageImpl.format(HttpServletRequest, String, Object, boolean)}
	 */
	@Override
	public String format(
		HttpServletRequest request, String pattern, Object argument,
		boolean translateArguments) {

		return UnicodeFormatter.toString(
			LanguageUtil.format(
				request, pattern, argument, translateArguments));
	}

	/**
	 * @see {@link LanguageImpl.format(HttpServletRequest, String, Object[])}
	 */
	@Override
	public String format(
		HttpServletRequest request, String pattern, Object[] arguments) {

		return UnicodeFormatter.toString(
			LanguageUtil.format(request, pattern, arguments));
	}

	/**
	 * @see {@link LanguageImpl.format(HttpServletRequest, String, Object[], boolean)}
	 */
	@Override
	public String format(
		HttpServletRequest request, String pattern, Object[] arguments,
		boolean translateArguments) {

		return UnicodeFormatter.toString(
			LanguageUtil.format(
				request, pattern, arguments, translateArguments));
	}

	/**
	 * @see {@link LanguageImpl.format(Locale, String, Object)}
	 */
	@Override
	public String format(Locale locale, String pattern, Object argument) {
		return UnicodeFormatter.toString(
			LanguageUtil.format(locale, pattern, argument));
	}

	/**
	 * @see {@link LanguageImpl.format(Locale, String, Object, boolean)}
	 */
	@Override
	public String format(
		Locale locale, String pattern, Object argument,
		boolean translateArguments) {

		return UnicodeFormatter.toString(
			LanguageUtil.format(locale, pattern, argument, translateArguments));
	}

	/**
	 * @see {@link LanguageImpl.format(Locale, String, Object[])}
	 */
	@Override
	public String format(Locale locale, String pattern, Object[] arguments) {
		return UnicodeFormatter.toString(
			LanguageUtil.format(locale, pattern, arguments));
	}

	/**
	 * @see {@link LanguageImpl.format(Locale, String, Object[], boolean)}
	 */
	@Override
	public String format(
		Locale locale, String pattern, Object[] arguments,
		boolean translateArguments) {

		return UnicodeFormatter.toString(
			LanguageUtil.format(
				locale, pattern, arguments, translateArguments));
	}

	/**
	 * @see {@link LanguageImpl.format(ResourceBundle, String, Object)}
	 */
	@Override
	public String format(
		ResourceBundle resourceBundle, String pattern, Object argument) {

		return UnicodeFormatter.toString(
			LanguageUtil.format(resourceBundle, pattern, argument));
	}

	/**
	 * @see {@link LanguageImpl.format(ResourceBundle, String, Object, boolean)}
	 */
	@Override
	public String format(
		ResourceBundle resourceBundle, String pattern, Object argument,
		boolean translateArguments) {

		return UnicodeFormatter.toString(
			LanguageUtil.format(
				resourceBundle, pattern, argument, translateArguments));
	}

	/**
	 * @see {@link LanguageImpl.format(ResourceBundle, String, Object[])}
	 */
	@Override
	public String format(
		ResourceBundle resourceBundle, String pattern, Object[] arguments) {

		return UnicodeFormatter.toString(
			LanguageUtil.format(resourceBundle, pattern, arguments));
	}

	/**
	 * @see {@link LanguageImpl.format(ResourceBundle, String, Object[], boolean)}
	 */
	@Override
	public String format(
		ResourceBundle resourceBundle, String pattern, Object[] arguments,
		boolean translateArguments) {

		return UnicodeFormatter.toString(
			LanguageUtil.format(
				resourceBundle, pattern, arguments, translateArguments));
	}

	/**
	 * @see {@link LanguageImpl.get(HttpServletRequest, String)}
	 */
	@Override
	public String get(HttpServletRequest request, String key) {
		return UnicodeFormatter.toString(LanguageUtil.get(request, key));
	}

	/**
	 * @see {@link LanguageImpl.get(HttpServletRequest, String, String)}
	 */
	@Override
	public String get(
		HttpServletRequest request, String key, String defaultValue) {

		return UnicodeFormatter.toString(
			LanguageUtil.get(request, key, defaultValue));
	}

	/**
	 * @see {@link LanguageImpl.get(Locale, String)}
	 */
	@Override
	public String get(Locale locale, String key) {
		return UnicodeFormatter.toString(LanguageUtil.get(locale, key));
	}

	/**
	 * @see {@link LanguageImpl.get(Locale, String, String)}
	 */
	@Override
	public String get(Locale locale, String key, String defaultValue) {
		return UnicodeFormatter.toString(
			LanguageUtil.get(locale, key, defaultValue));
	}

	/**
	 * @see {@link LanguageImpl.get(ResourceBundle, String)}
	 */
	@Override
	public String get(ResourceBundle resourceBundle, String key) {
		return UnicodeFormatter.toString(LanguageUtil.get(resourceBundle, key));
	}

	/**
	 * @see {@link LanguageImpl.get(ResourceBundle, String, String)}
	 */
	@Override
	public String get(
		ResourceBundle resourceBundle, String key, String defaultValue) {

		return UnicodeFormatter.toString(
			LanguageUtil.get(resourceBundle, key, defaultValue));
	}

	/**
	 * @see {@link LanguageImpl.getTimeDescription(HttpServletRequest, long)
	 */
	@Override
	public String getTimeDescription(
		HttpServletRequest request, long milliseconds) {

		return UnicodeFormatter.toString(
			LanguageUtil.getTimeDescription(request, milliseconds));
	}

	/**
	 * @see {@link LanguageImpl.getTimeDescription(HttpServletRequest, Long)
	 */
	@Override
	public String getTimeDescription(
		HttpServletRequest request, Long milliseconds) {

		return UnicodeFormatter.toString(
			LanguageUtil.getTimeDescription(request, milliseconds));
	}

}