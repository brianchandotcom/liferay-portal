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

package com.liferay.portal.instances.initializer;

import com.liferay.portal.instances.exception.InitializationException;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Ivica Cardic
 */
@ProviderType
public interface PortalInstanceInitializer {

	public String getKey();

	public void initialize(
			long companyId, HttpServletRequest httpServletRequest,
			Map<String, String> payload)
		throws InitializationException;

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link
	 *             #initialize(long)}
	 */
	@Deprecated
	public void initialize(String webId, String virtualHostname, String mx)
		throws InitializationException;

	public boolean isActive();

}