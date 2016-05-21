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

package com.liferay.sync.oauth.helper;

import com.liferay.portal.kernel.service.ServiceContext;

/**
 * @author Shinn Lok
 */
public interface SyncOAuthHelper {

	public void enableOAuth(long companyId, ServiceContext serviceContext)
		throws Exception;

	public boolean isDeployed();

	public boolean isOAuthApplicationAvailable(long oAuthApplicationId);

}