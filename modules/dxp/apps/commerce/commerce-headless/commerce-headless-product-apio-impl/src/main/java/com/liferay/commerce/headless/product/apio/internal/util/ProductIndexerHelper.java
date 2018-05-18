/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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

	public <T extends BaseModel<T>> Indexer<T> getIndexer(Class<T> clazz) {
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
	 * related resources.
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