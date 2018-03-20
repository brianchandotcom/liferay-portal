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

package com.liferay.message.boards.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.message.boards.model.MBThread;

import com.liferay.portal.kernel.dao.orm.QueryDefinition;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.jsonwebservice.JSONWebService;
import com.liferay.portal.kernel.lock.Lock;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.security.access.control.AccessControlled;
import com.liferay.portal.kernel.service.BaseService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.spring.osgi.OSGiBeanProperties;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Provides the remote service interface for MBThread. Methods of this
 * service are expected to have security checks based on the propagated JAAS
 * credentials because this service can be accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see MBThreadServiceUtil
 * @see com.liferay.message.boards.service.base.MBThreadServiceBaseImpl
 * @see com.liferay.message.boards.service.impl.MBThreadServiceImpl
 * @generated
 */
@AccessControlled
@JSONWebService
@OSGiBeanProperties(property =  {
	"json.web.service.context.name=mb", "json.web.service.context.path=MBThread"}, service = MBThreadService.class)
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface MBThreadService extends BaseService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link MBThreadServiceUtil} to access the message boards thread remote service. Add custom service methods to {@link com.liferay.message.boards.service.impl.MBThreadServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public void deleteThread(long threadId) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<MBThread> getGroupThreads(long groupId, long userId,
		Date modifiedDate, boolean includeAnonymous, int status, int start,
		int end) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<MBThread> getGroupThreads(long groupId, long userId,
		Date modifiedDate, int status, int start, int end)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<MBThread> getGroupThreads(long groupId, long userId,
		int status, boolean subscribed, boolean includeAnonymous, int start,
		int end) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<MBThread> getGroupThreads(long groupId, long userId,
		int status, boolean subscribed, int start, int end)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<MBThread> getGroupThreads(long groupId, long userId,
		int status, int start, int end) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getGroupThreadsCount(long groupId, long userId,
		Date modifiedDate, boolean includeAnonymous, int status);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getGroupThreadsCount(long groupId, long userId,
		Date modifiedDate, int status);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getGroupThreadsCount(long groupId, long userId, int status);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getGroupThreadsCount(long groupId, long userId, int status,
		boolean subscribed);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getGroupThreadsCount(long groupId, long userId, int status,
		boolean subscribed, boolean includeAnonymous);

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public java.lang.String getOSGiServiceIdentifier();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<MBThread> getThreads(long groupId, long categoryId, int status,
		int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<MBThread> getThreads(long groupId, long categoryId,
		QueryDefinition<MBThread> queryDefinition) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getThreadsCount(long groupId, long categoryId, int status);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getThreadsCount(long groupId, long categoryId,
		QueryDefinition<MBThread> queryDefinition) throws PortalException;

	public Lock lockThread(long threadId) throws PortalException;

	public MBThread moveThread(long categoryId, long threadId)
		throws PortalException;

	public MBThread moveThreadFromTrash(long categoryId, long threadId)
		throws PortalException;

	public MBThread moveThreadToTrash(long threadId) throws PortalException;

	public void restoreThreadFromTrash(long threadId) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Hits search(long groupId, long creatorUserId, int status, int start,
		int end) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Hits search(long groupId, long creatorUserId, long startDate,
		long endDate, int status, int start, int end) throws PortalException;

	public MBThread splitThread(long messageId, java.lang.String subject,
		ServiceContext serviceContext) throws PortalException;

	public void unlockThread(long threadId) throws PortalException;
}