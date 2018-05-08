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

package com.liferay.apio.architect.language;

import java.util.Locale;
import java.util.stream.Stream;

/**
 * Represents information about the requested languages.
 *
 * @author Alejandro Hernández
 */
public interface Language {

	/**
	 * Returns the {@code Stream} of the request's preferred {@code Locale}, in
	 * decreasing order.
	 *
	 * <p>
	 * The stream values start with the first locale added on the {@code
	 * Accept-Language} header and continue with the rest of the header.
	 * </p>
	 *
	 * <p>
	 * If the request doesn't have an {@code Accept-Language} header, this
	 * method returns an {@code Stream} containing the default locale for the
	 * server.
	 * </p>
	 *
	 * @return the {@code Stream} of the request's preferred {@code Locale}, if
	 *         the {@code Accept-Language} header is present; otherwise returns
	 *         the {@code Stream} containing the server's default locale
	 */
	public default Stream<Locale> getLocales() {
		return Stream.of(getPreferredLocale());
	}

	/**
	 * Returns the first {@code Locale} added on the {@code Accept-Language}
	 * header. If the request doesn't have an {@code Accept-Language} header,
	 * this method returns the server's default locale.
	 *
	 * @return the request's first {@code Locale}, if the {@code
	 *         Accept-Language} header is present; otherwise returns the {@code
	 *         Enumeration} containing the default locale for the server
	 */
	public Locale getPreferredLocale();

}