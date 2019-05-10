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

package com.liferay.oauth2.provider.scope.spi.prefix.handler;

import org.osgi.annotation.versioning.ProviderType;

/**
 * Represents a prefix for the input scope. This abstraction allows the
 * framework to adapt the applications to different scope naming strategies.
 *
 * @author Carlos Sierra Andrés
 * @review
 */
@ProviderType
public interface PrefixHandler {

	/**
	 * A {@link PrefixHandler} that keeps the input unchanged.
	 *
	 * @review
	 */
	public static PrefixHandler PASS_THROUGH_PREFIX_HANDLER =
		new PrefixHandler() {

			@Override
			public String addPrefix(String input) {
				return input;
			}

			@Override
			public PrefixHandler append(PrefixHandler prefixHandler) {
				return prefixHandler;
			}

		};

	/**
	 * Adds the prefix to a given input.
	 *
	 * @param  input String to be prefixed.
	 * @return a new String with the prefix.
	 * @review
	 */
	public String addPrefix(String input);

	/**
	 * A new {@link PrefixHandler} taking into account the given {@link
	 * PrefixHandler}
	 *
	 * @param  prefixHandler the prefix handler to append.
	 * @return a new prefix handler combining both prefix handlers.
	 * @review
	 */
	public default PrefixHandler append(PrefixHandler prefixHandler) {
		return string -> addPrefix(prefixHandler.addPrefix(string));
	}

}