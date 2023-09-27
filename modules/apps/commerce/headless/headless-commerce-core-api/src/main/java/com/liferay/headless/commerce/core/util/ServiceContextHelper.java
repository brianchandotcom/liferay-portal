/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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