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
import com.liferay.portal.kernel.security.access.control.AccessControlled;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;

/**
 * Provides the remote service interface for Team. Methods of this
 * service are expected to have security checks based on the propagated JAAS
 * credentials because this service can be accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see TeamServiceUtil
 * @see com.liferay.portal.service.base.TeamServiceBaseImpl
 * @see com.liferay.portal.service.impl.TeamServiceImpl
 * @generated
 */
@AccessControlled
@JSONWebService
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface TeamService extends BaseService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link TeamServiceUtil} to access the team remote service. Add custom service methods to {@link com.liferay.portal.service.impl.TeamServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */

	/**
	* @throws PortalException
	* @deprecated As of 7.0.0, replaced by {@link
	#addTeam(long,long,String,String,ServiceContext)}
	*/
	@java.lang.Deprecated
	public com.liferay.portal.model.Team addTeam(long groupId,
		java.lang.String name, java.lang.String description)
		throws PortalException;

	public com.liferay.portal.model.Team addTeam(long groupId,
		java.lang.String name, java.lang.String description,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws PortalException;

	public void deleteTeam(long teamId) throws PortalException;

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public java.lang.String getBeanIdentifier();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portal.model.Team> getGroupTeams(
		long groupId) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portal.model.Team getTeam(long groupId,
		java.lang.String name) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portal.model.Team getTeam(long teamId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portal.model.Team> getUserTeams(
		long userId) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portal.model.Team> getUserTeams(
		long userId, long groupId) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public boolean hasUserTeam(long userId, long teamId)
		throws PortalException;

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public void setBeanIdentifier(java.lang.String beanIdentifier);

	public com.liferay.portal.model.Team updateTeam(long teamId,
		java.lang.String name, java.lang.String description)
		throws PortalException;
}