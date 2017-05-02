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

package com.liferay.exportimport.kernel.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.exportimport.kernel.lar.MissingReferences;
import com.liferay.exportimport.kernel.model.ExportImportConfiguration;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.jsonwebservice.JSONWebService;
import com.liferay.portal.kernel.security.access.control.AccessControlled;
import com.liferay.portal.kernel.service.BaseService;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Transactional;

import java.io.Serializable;

import java.util.List;
import java.util.Map;

/**
 * Provides the remote service interface for Staging. Methods of this
 * service are expected to have security checks based on the propagated JAAS
 * credentials because this service can be accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see StagingServiceUtil
 * @see com.liferay.portlet.exportimport.service.base.StagingServiceBaseImpl
 * @see com.liferay.portlet.exportimport.service.impl.StagingServiceImpl
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
	 * Never modify or reference this interface directly. Always use {@link StagingServiceUtil} to access the staging remote service. Add custom service methods to {@link com.liferay.portlet.exportimport.service.impl.StagingServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */

	/**
	* @deprecated As of 7.0.0, with no direct replacement
	*/
	@java.lang.Deprecated
	public MissingReferences publishStagingRequest(long stagingRequestId,
		boolean privateLayout,
		Map<java.lang.String, java.lang.String[]> parameterMap)
		throws PortalException;

	public MissingReferences publishStagingRequest(long stagingRequestId,
		ExportImportConfiguration exportImportConfiguration)
		throws PortalException;

	/**
	* @deprecated As of 7.0.0, replaced by {@link #publishStagingRequest(long,
	boolean, Map)}
	*/
	@java.lang.Deprecated
	public MissingReferences validateStagingRequest(long stagingRequestId,
		boolean privateLayout,
		Map<java.lang.String, java.lang.String[]> parameterMap)
		throws PortalException;

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public java.lang.String getOSGiServiceIdentifier();

	public long createStagingRequest(long groupId, java.lang.String checksum)
		throws PortalException;

	public void cleanUpStagingRequest(long stagingRequestId)
		throws PortalException;

	public void propagateExportImportLifecycleEvent(int code, int processFlag,
		java.lang.String processId, List<Serializable> arguments)
		throws PortalException;

	public void updateStagingRequest(long stagingRequestId,
		java.lang.String fileName, byte[] bytes) throws PortalException;
}