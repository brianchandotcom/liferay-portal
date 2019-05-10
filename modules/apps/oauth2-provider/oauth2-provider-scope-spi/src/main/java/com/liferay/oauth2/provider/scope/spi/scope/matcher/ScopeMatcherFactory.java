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

package com.liferay.oauth2.provider.scope.spi.scope.matcher;

import org.osgi.annotation.versioning.ProviderType;

/**
 * Factory that creates {@link ScopeMatcher} for a given input. This allow for
 * components to switch matching strategies using configuration.
 *
 * @author Carlos Sierra Andrés
 * @review
 */
@ProviderType
public interface ScopeMatcherFactory {

	/**
	 * Creates a {@link ScopeMatcher} for the given input.
	 *
	 * @param  input the input the matcher will match against.
	 * @return the ScopeMatcher that will match against the input.
	 * @review
	 */
	public ScopeMatcher create(String input);

}