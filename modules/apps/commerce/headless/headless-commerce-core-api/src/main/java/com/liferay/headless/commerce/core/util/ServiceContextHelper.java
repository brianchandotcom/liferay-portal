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

package com.liferay.headless.commerce.core.util;

import aQute.bnd.annotation.ConsumerType;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;

/**
 * @author Alessio Antonio Rendina
 */
@ConsumerType
public interface ServiceContextHelper {

	public ServiceContext getServiceContext() throws PortalException;

	public ServiceContext getServiceContext(long groupId)
		throws PortalException;

	public ServiceContext getServiceContext(
			long groupId, long[] assetCategoryIds, User user)
		throws PortalException;

	public ServiceContext getServiceContext(
			long groupId, long[] assetCategoryIds, User user,
			boolean generateUuid)
		throws PortalException;

	public ServiceContext getServiceContext(User user) throws PortalException;

}