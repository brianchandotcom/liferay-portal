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

package com.liferay.oauth2.provider.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.oauth2.provider.model.OAuth2Authorization;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.service.BaseLocalService;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

/**
 * Provides the local service interface for OAuth2Authorization. Methods of this
 * service will not have security checks based on the propagated JAAS
 * credentials because this service can only be accessed from within the same
 * VM.
 *
 * @author Brian Wing Shun Chan
 * @see OAuth2AuthorizationLocalServiceUtil
 * @see com.liferay.oauth2.provider.service.base.OAuth2AuthorizationLocalServiceBaseImpl
 * @see com.liferay.oauth2.provider.service.impl.OAuth2AuthorizationLocalServiceImpl
 * @generated
 */
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface OAuth2AuthorizationLocalService extends BaseLocalService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link OAuth2AuthorizationLocalServiceUtil} to access the o auth2 authorization local service. Add custom service methods to {@link com.liferay.oauth2.provider.service.impl.OAuth2AuthorizationLocalServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */

	/**
	* NOTE FOR DEVELOPERS:
	*
	* Never reference this class directly. Always use {@link OAuth2AuthorizationLocalServiceUtil} to access the o auth2 authorization local service.
	*/
	public int countByApplicationId(long companyId, long applicationId);

	public int countByUserId(long companyId, long userId);

	public List<OAuth2Authorization> findByApplicationId(long companyId,
		long applicationId, int start, int end,
		OrderByComparator<OAuth2Authorization> orderByComparator);

	public List<OAuth2Authorization> findByUserId(long companyId, long userId,
		int start, int end,
		OrderByComparator<OAuth2Authorization> orderByComparator);

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public java.lang.String getOSGiServiceIdentifier();

	public boolean revokeAuthorization(long oAuth2AccessTokenId,
		long oAuth2RefreshTokenId) throws PortalException;
}