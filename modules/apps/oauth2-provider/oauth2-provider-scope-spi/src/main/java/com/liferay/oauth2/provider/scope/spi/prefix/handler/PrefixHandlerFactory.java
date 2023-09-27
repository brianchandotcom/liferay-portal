/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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