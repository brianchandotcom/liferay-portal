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

package com.liferay.oauth2.provider.scope.spi.application.descriptor;

import java.util.Locale;

import org.osgi.annotation.versioning.ProviderType;

/**
 * Represents the localization information for OAuth2 applications.
 *
 * @author Carlos Sierra Andrés
 * @review
 */
@ProviderType
public interface ApplicationDescriptor {

	/**
	 * Localize an application for a given locale.
	 *
	 * @param  locale the locale requested for the description.
	 * @return a description for the applicationName in the requested locale.
	 * @review
	 */
	public String describeApplication(Locale locale);

}