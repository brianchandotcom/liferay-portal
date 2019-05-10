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

import java.util.function.Function;

import org.osgi.annotation.versioning.ProviderType;

/**
 * Interface to create {@link PrefixHandler} using a given prefix. This allows
 * components to switch prefixing strategies using configuration, such as using
 * different characters <i>'_'</i> or <i>'.'</i>, thus keeping the prefixing
 * strategy consistent across components.
 *
 * @author Carlos Sierra Andrés
 * @review
 */
@ProviderType
public interface PrefixHandlerFactory {

	/**
	 * This method allows to create a {@link PrefixHandler} using the properties
	 *
	 * @param  propertyAccessorFunction to configure the {@link PrefixHandler}
	 *         from
	 * @return the {@link PrefixHandler} initialized from the given properties
	 * @review
	 */
	public PrefixHandler create(
		Function<String, Object> propertyAccessorFunction);

}