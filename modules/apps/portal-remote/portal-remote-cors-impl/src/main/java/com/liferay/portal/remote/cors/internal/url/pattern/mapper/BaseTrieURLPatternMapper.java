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

package com.liferay.portal.remote.cors.internal;

import com.liferay.portal.kernel.util.Validator;

/**
 * @author Carlos Sierra Andrés
 * @author Arthur Chan
 */
public abstract class BaseTrieURLToCORSSupportMapper
	extends BaseURLToCORSSupportMapper {

	@Override
	public CORSSupport get(String urlPath) {
		try {
			CORSSupport corsSupport = getWildcardCORSSupport(urlPath);

			if (corsSupport != null) {
				return corsSupport;
			}

			return getExtensionCORSSupport(urlPath);
		}
		catch (IndexOutOfBoundsException indexOutOfBoundsException) {
			throw new IllegalArgumentException(
				"URL path contains invalid characters",
				indexOutOfBoundsException);
		}
	}

	protected abstract CORSSupport getExtensionCORSSupport(String urlPath);

	protected abstract CORSSupport getWildcardCORSSupport(String urlPath);

	@Override
	protected void put(CORSSupport corsSupport, String urlPattern)
		throws IllegalArgumentException {

		if (corsSupport == null) {
			throw new IllegalArgumentException("CORS support is null");
		}

		if (Validator.isBlank(urlPattern)) {
			throw new IllegalArgumentException("URL pattern is blank");
		}

		try {
			if (isWildcardURLPattern(urlPattern)) {
				put(corsSupport, urlPattern, true);

				return;
			}

			if (isExtensionURLPattern(urlPattern)) {
				put(corsSupport, urlPattern, false);

				return;
			}

			put(corsSupport, urlPattern, true);
		}
		catch (IndexOutOfBoundsException indexOutOfBoundsException) {
			throw new IllegalArgumentException(
				"URL pattern contains invalid characters",
				indexOutOfBoundsException);
		}
	}

	protected abstract void put(
		CORSSupport corsSupport, String urlPattern, boolean wildcard);

	protected static final byte ASCII_CHARACTER_RANGE = 96;

	protected static final byte ASCII_PRINTABLE_OFFSET = 32;

}