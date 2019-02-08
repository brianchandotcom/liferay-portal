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

package com.liferay.portal.vulcan.context;

import java.util.List;
import java.util.Locale;

/**
 * Represents information about the requested languages.
 *
 * @author Alejandro Hernández
 * @review
 */
@FunctionalInterface
public interface AcceptLanguage {

	/**
	 * Returns the {@code List} of the request's preferred {@code Locale}, in
	 * decreasing order.
	 *
	 * <p>
	 * The list starts with the first locale added on the {@code
	 * Accept-Language} header and continue with the rest of the header.
	 * </p>
	 *
	 * <p>
	 * If the request doesn't have an {@code Accept-Language} header, this
	 * method returns a {@code List} containing the default locale for the
	 * current user.
	 * </p>
	 *
	 * @return the {@code List} of the request's preferred {@code Locale}, if
	 *         the {@code Accept-Language} header is present; otherwise returns
	 *         the {@code List} containing the current user's default locale
	 * @review
	 */
	public List<Locale> getLocales();

	/**
	 * Returns the first {@code Locale} added on the {@code Accept-Language}
	 * header. If the request doesn't have an {@code Accept-Language} header,
	 * this method returns the current user's default locale.
	 *
	 * @return the request's first {@code Locale}, if the {@code
	 *         Accept-Language} header is present; otherwise returns the current
	 *         user's default locale
	 * @review
	 */
	public default Locale getPreferredLocale() {
		List<Locale> locales = getLocales();

		return locales.get(0);
	}

}