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
 * This interface represents the context surrounding per-request scope check
 * security procedure.
 *
 * @author Carlos Sierra Andrés
 * @review
 */
public interface ScopeContext {

	/**
	 * Reset state of the context
	 */
	public void clear();

	/**
	 * Sets access token string into the context to be used during security
	 * check
	 * @param accessToken
	 */
	public void setAccessToken(String accessToken);

	/**
	 * Sets application name into the context to be used during security check
	 * @param applicationName
	 * @review
	 */
	public void setApplicationName(String applicationName);

	/**
	 * Sets OSGi bundle into the context to be used during security check
	 * @param bundle
	 * @review
	 */
	public void setBundle(Bundle bundle);

	/**
	 * Sets request companyId into the context to be used during security check
	 * @param companyId
	 */
	public void setCompanyId(long companyId);

}