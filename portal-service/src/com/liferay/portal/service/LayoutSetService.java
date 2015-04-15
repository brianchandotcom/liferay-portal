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

package com.liferay.portal.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.jsonwebservice.JSONWebService;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.security.ac.AccessControlled;

/**
 * Provides the remote service interface for LayoutSet. Methods of this
 * service are expected to have security checks based on the propagated JAAS
 * credentials because this service can be accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see LayoutSetServiceUtil
 * @see com.liferay.portal.service.base.LayoutSetServiceBaseImpl
 * @see com.liferay.portal.service.impl.LayoutSetServiceImpl
 * @generated
 */
@AccessControlled
@JSONWebService
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface LayoutSetService extends BaseService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link LayoutSetServiceUtil} to access the layout set remote service. Add custom service methods to {@link com.liferay.portal.service.impl.LayoutSetServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public java.lang.String getBeanIdentifier();

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public void setBeanIdentifier(java.lang.String beanIdentifier);

	/**
	* Updates the state of the layout set prototype link.
	*
	* <p>
	* <strong>Important:</strong> Setting
	* <code>layoutSetPrototypeLinkEnabled</code> to <code>true</code> and
	* <code>layoutSetPrototypeUuid</code> to <code>null</code> when the layout
	* set prototype's current uuid is <code>null</code> will result in an
	* <code>IllegalStateException</code>.
	* </p>
	*
	* @param groupId the primary key of the group
	* @param layoutSetPrototypeLinkEnabled whether the layout set prototype is
	link enabled
	* @param layoutSetPrototypeUuid the uuid of the layout set prototype to
	link with
	* @throws PortalException if a portal exception occurred
	*/
	public void updateLayoutSetPrototypeLinkEnabled(long groupId,
		boolean layoutSetPrototypeLinkEnabled,
		java.lang.String layoutSetPrototypeUuid) throws PortalException;

	public void updateLogo(long groupId, boolean logo, byte[] bytes)
		throws PortalException;

	public void updateLogo(long groupId, boolean logo, java.io.File file)
		throws PortalException;

	public void updateLogo(long groupId, boolean logo,
		java.io.InputStream inputStream) throws PortalException;

	public void updateLogo(long groupId, boolean logo,
		java.io.InputStream inputStream, boolean cleanUpStream)
		throws PortalException;

	public com.liferay.portal.model.LayoutSet updateLookAndFeel(long groupId,
		java.lang.String themeId, java.lang.String colorSchemeId,
		java.lang.String css, boolean wapTheme) throws PortalException;

	public com.liferay.portal.model.LayoutSet updateSettings(long groupId,
		java.lang.String settings) throws PortalException;

	public com.liferay.portal.model.LayoutSet updateVirtualHost(long groupId,
		java.lang.String virtualHost) throws PortalException;
}