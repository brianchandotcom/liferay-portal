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

package com.liferay.oauth2.provider.scope.liferay;

import org.osgi.framework.Bundle;

/**
 * Represents the whole information about an application exported scope into
 * OAuth2 provider framework Liferay environment.
 *
 * @author Carlos Sierra Andrés
 * @review
 */
public interface LiferayOAuth2Scope {

	/**
	 * Name of the application that provides the scope.<br />
	 * Usually refers to JAX-RS application name.
	 *
	 * @return non-null application name
	 * @review
	 */
	public String getApplicationName();

	/**
	 * OSGi bundle context from which the application and scope is published.
	 *
	 * @return non-null OSGi bundle
	 * @review
	 */
	public Bundle getBundle();

	/**
	 * Scope name as registered into OAuth2 Provider framework.
	 *
	 * @return non-null scope name
	 */
	public String getScope();

}