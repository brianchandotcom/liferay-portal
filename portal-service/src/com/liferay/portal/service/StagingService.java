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
 * Provides the remote service interface for Staging. Methods of this
 * service are expected to have security checks based on the propagated JAAS
 * credentials because this service can be accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see StagingServiceUtil
 * @see com.liferay.portal.service.base.StagingServiceBaseImpl
 * @see com.liferay.portal.service.impl.StagingServiceImpl
 * @generated
 */
@AccessControlled
@JSONWebService
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface StagingService extends BaseService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link StagingServiceUtil} to access the staging remote service. Add custom service methods to {@link com.liferay.portal.service.impl.StagingServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public void cleanUpStagingRequest(long stagingRequestId)
		throws PortalException;

	public long createStagingRequest(long groupId, java.lang.String checksum)
		throws PortalException;

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public java.lang.String getBeanIdentifier();

	public void publishStagingRequest(long stagingRequestId,
		java.util.Map<java.lang.String, java.lang.String[]> parameterMap)
		throws PortalException;

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public void setBeanIdentifier(java.lang.String beanIdentifier);

	public void updateStagingRequest(long stagingRequestId,
		java.lang.String fileName, byte[] bytes) throws PortalException;

	/**
	* @deprecated As of 7.0.0, replaced by {@link #publishStagingRequest(long,
	boolean, java.util.Map)}
	*/
	@java.lang.Deprecated
	public com.liferay.portal.kernel.lar.MissingReferences validateStagingRequest(
		long stagingRequestId, boolean privateLayout,
		java.util.Map<java.lang.String, java.lang.String[]> parameterMap)
		throws PortalException;
}