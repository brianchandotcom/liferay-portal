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

import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Brian Wing Shun Chan
 */
@ProviderType
public interface UnicodeLanguage {

	public String format(
		HttpServletRequest httpServletRequest, String pattern,
		LanguageWrapper argument);

	public String format(
		HttpServletRequest httpServletRequest, String pattern,
		LanguageWrapper argument, boolean translateArguments);

	public String format(
		HttpServletRequest httpServletRequest, String pattern,
		LanguageWrapper[] arguments);

	public String format(
		HttpServletRequest httpServletRequest, String pattern,
		LanguageWrapper[] arguments, boolean translateArguments);

	public String format(
		HttpServletRequest httpServletRequest, String pattern, Object argument);

	public String format(
		HttpServletRequest httpServletRequest, String pattern, Object argument,
		boolean translateArguments);

	public String format(
		HttpServletRequest httpServletRequest, String pattern,
		Object[] arguments);

	public String format(
		HttpServletRequest httpServletRequest, String pattern,
		Object[] arguments, boolean translateArguments);

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

	public String get(HttpServletRequest httpServletRequest, String key);

	public String get(
		HttpServletRequest httpServletRequest, String key, String defaultValue);

	public String get(Locale locale, String key);

	public String get(Locale locale, String key, String defaultValue);

	public String get(ResourceBundle resourceBundle, String key);

	public String get(
		ResourceBundle resourceBundle, String key, String defaultValue);

	public String getTimeDescription(
		HttpServletRequest httpServletRequest, long milliseconds);

	public String getTimeDescription(
		HttpServletRequest httpServletRequest, Long milliseconds);

}