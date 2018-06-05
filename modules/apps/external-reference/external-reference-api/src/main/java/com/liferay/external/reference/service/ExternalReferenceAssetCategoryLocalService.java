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

package com.liferay.external.reference.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.asset.kernel.model.AssetCategory;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.service.BaseLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Transactional;

import java.util.Locale;
import java.util.Map;

/**
 * Provides the local service interface for ExternalReferenceAssetCategory. Methods of this
 * service will not have security checks based on the propagated JAAS
 * credentials because this service can only be accessed from within the same
 * VM.
 *
 * @author Brian Wing Shun Chan
 * @see ExternalReferenceAssetCategoryLocalServiceUtil
 * @see com.liferay.external.reference.service.base.ExternalReferenceAssetCategoryLocalServiceBaseImpl
 * @see com.liferay.external.reference.service.impl.ExternalReferenceAssetCategoryLocalServiceImpl
 * @generated
 */
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface ExternalReferenceAssetCategoryLocalService
	extends BaseLocalService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link ExternalReferenceAssetCategoryLocalServiceUtil} to access the external reference asset category local service. Add custom service methods to {@link com.liferay.external.reference.service.impl.ExternalReferenceAssetCategoryLocalServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public String getOSGiServiceIdentifier();

	public AssetCategory upsertCategory(long userId, long groupId,
		long parentCategoryId, Map<Locale, String> titleMap,
		Map<Locale, String> descriptionMap, long vocabularyId,
		String[] categoryProperties, String externalReferenceCode,
		ServiceContext serviceContext) throws PortalException;
}