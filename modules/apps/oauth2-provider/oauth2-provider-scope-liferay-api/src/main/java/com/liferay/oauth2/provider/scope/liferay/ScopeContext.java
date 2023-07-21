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
 * Represents a context for the per-request scope check security procedure.
 *
 * @author Carlos Sierra Andrés
 */
public interface ScopeContext {

	/**
	 * Resets the state context's state.
	 */
	public void clear();

	public String getAccessToken();

	public String getApplicationName();

	public String getBundleSymbolicName();

	public Long getCompanyId();

	/**
	 * Inserts the access token string into the context.
	 *
	 * @param accessToken the access token
	 */
	public void setAccessToken(String accessToken);

	/**
	 * Inserts the application name into the context.
	 *
	 * @param applicationName the application name
	 */
	public void setApplicationName(String applicationName);

	/**
	 * Inserts the OSGi bundle into the context.
	 *
	 * @param bundle the OSGi bundle
	 */
	public void setBundle(Bundle bundle);

	/**
	 * Inserts the portal instance's ID into the context.
	 *
	 * @param companyId the portal instance's ID
	 */
	public void setCompanyId(long companyId);

}