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

package com.liferay.commerce.headless.product.apio.internal.util;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistry;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.UserService;

import javax.ws.rs.NotFoundException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Zoltán Takács
 */
@Component(immediate = true, service = ProductIndexerHelper.class)
public class ProductIndexerHelper {

	public <T extends BaseModel> Indexer<T> getIndexer(Class<T> clazz) {
		Indexer<T> indexer = _indexerRegistry.getIndexer(clazz.getName());

		if (indexer == null) {
			throw new NotFoundException(
				"Unable to get indexer for " + clazz.getName());
		}

		return indexer;
	}

	public ServiceContext getServiceContext() throws PortalException {
		return getServiceContext(0, new long[0]);
	}

	/**
	 * Compose the ServiceContext object which needed for operation on Product
	 * related resources
	 *
	 * @param  groupId
	 * @param  assetCategoryIds
	 * @return ServiceContext
	 * @throws PortalException
	 * @see    BaseCPDemoDataCreatorHelper
	 */
	public ServiceContext getServiceContext(
			long groupId, long[] assetCategoryIds)
		throws PortalException {

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		if (serviceContext == null) {
			serviceContext = new ServiceContext();
		}

		User user = _userService.getUserById(PrincipalThreadLocal.getUserId());

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);
		serviceContext.setAssetCategoryIds(assetCategoryIds);
		serviceContext.setCompanyId(user.getCompanyId());
		serviceContext.setScopeGroupId(groupId);
		serviceContext.setTimeZone(user.getTimeZone());
		serviceContext.setUserId(user.getUserId());

		return serviceContext;
	}

	@Reference
	private IndexerRegistry _indexerRegistry;

	@Reference
	private UserService _userService;

}