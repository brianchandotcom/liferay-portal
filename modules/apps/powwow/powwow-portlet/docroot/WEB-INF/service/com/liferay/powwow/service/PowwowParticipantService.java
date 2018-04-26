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

package com.liferay.powwow.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.jsonwebservice.JSONWebService;
import com.liferay.portal.kernel.security.access.control.AccessControlled;
import com.liferay.portal.kernel.service.BaseService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;

import com.liferay.powwow.model.PowwowParticipant;

import java.util.List;

/**
 * Provides the remote service interface for PowwowParticipant. Methods of this
 * service are expected to have security checks based on the propagated JAAS
 * credentials because this service can be accessed remotely.
 *
 * @author Shinn Lok
 * @see PowwowParticipantServiceUtil
 * @see com.liferay.powwow.service.base.PowwowParticipantServiceBaseImpl
 * @see com.liferay.powwow.service.impl.PowwowParticipantServiceImpl
 * @generated
 */
@AccessControlled
@JSONWebService
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface PowwowParticipantService extends BaseService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link PowwowParticipantServiceUtil} to access the powwow participant remote service. Add custom service methods to {@link com.liferay.powwow.service.impl.PowwowParticipantServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public PowwowParticipant deletePowwowParticipant(
		PowwowParticipant powwowParticipant) throws PortalException;

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public String getOSGiServiceIdentifier();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<PowwowParticipant> getPowwowParticipants(long powwowMeetingId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getPowwowParticipantsCount(long powwowMeetingId)
		throws PortalException;

	public PowwowParticipant updatePowwowParticipant(long powwowParticipantId,
		long powwowMeetingId, String name, long participantUserId,
		String emailAddress, int type, int status, ServiceContext serviceContext)
		throws PortalException;
}