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

package com.liferay.asset.kernel.service;

import com.liferay.asset.kernel.model.AssetCategoryProperty;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.jsonwebservice.JSONWebService;
import com.liferay.portal.kernel.security.access.control.AccessControlled;
import com.liferay.portal.kernel.service.BaseService;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;

import java.util.List;

import org.osgi.annotation.versioning.ProviderType;

/**
 * Provides the remote service interface for AssetCategoryProperty. Methods of this
 * service are expected to have security checks based on the propagated JAAS
 * credentials because this service can be accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see AssetCategoryPropertyServiceUtil
 * @deprecated As of Judson (7.1.x), replaced by {@link
 com.liferay.asset.category.property.service.impl.AssetCategoryPropertyServiceImpl}
 * @generated
 */
@AccessControlled
@Deprecated
@JSONWebService
@ProviderType
@Transactional(
	isolation = Isolation.PORTAL,
	rollbackFor = {PortalException.class, SystemException.class}
)
public interface AssetCategoryPropertyService extends BaseService {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link AssetCategoryPropertyServiceUtil} to access the asset category property remote service. Add custom service methods to <code>com.liferay.portlet.asset.service.impl.AssetCategoryPropertyServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public AssetCategoryProperty addCategoryProperty(
			long entryId, String key, String value)
		throws PortalException;

	public void deleteCategoryProperty(long categoryPropertyId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<AssetCategoryProperty> getCategoryProperties(long entryId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<AssetCategoryProperty> getCategoryPropertyValues(
		long companyId, String key);

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public String getOSGiServiceIdentifier();

	public AssetCategoryProperty updateCategoryProperty(
			long userId, long categoryPropertyId, String key, String value)
		throws PortalException;

	public AssetCategoryProperty updateCategoryProperty(
			long categoryPropertyId, String key, String value)
		throws PortalException;

}