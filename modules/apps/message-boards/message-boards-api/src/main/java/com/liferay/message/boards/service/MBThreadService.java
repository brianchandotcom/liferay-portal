/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.message.boards.service;

import com.liferay.message.boards.model.MBThread;
import com.liferay.portal.kernel.change.tracking.CTAware;
import com.liferay.portal.kernel.dao.orm.QueryDefinition;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.jsonwebservice.JSONWebService;
import com.liferay.portal.kernel.lock.Lock;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.security.access.control.AccessControlled;
import com.liferay.portal.kernel.service.BaseService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;

import java.util.Date;
import java.util.List;

import org.osgi.annotation.versioning.ProviderType;

/**
 * Provides the remote service interface for MBThread. Methods of this
 * service are expected to have security checks based on the propagated JAAS
 * credentials because this service can be accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see MBThreadServiceUtil
 * @generated
 */
@AccessControlled
@CTAware
@JSONWebService
@ProviderType
@Transactional(
	isolation = Isolation.PORTAL,
	rollbackFor = {PortalException.class, SystemException.class}
)
public interface MBThreadService extends BaseService {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add custom service methods to <code>com.liferay.message.boards.service.impl.MBThreadServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface. Consume the message boards thread remote service via injection or a <code>org.osgi.util.tracker.ServiceTracker</code>. Use {@link MBThreadServiceUtil} if injection and service tracking are not available.
	 */
	public void deleteThread(long threadId) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<MBThread> getGroupThreads(
			long groupId, long userId, Date modifiedDate,
			boolean includeAnonymous, int status, int start, int end)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<MBThread> getGroupThreads(
			long groupId, long userId, Date modifiedDate, int status, int start,
			int end)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<MBThread> getGroupThreads(
			long groupId, long userId, int status, boolean subscribed,
			boolean includeAnonymous, int start, int end)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<MBThread> getGroupThreads(
			long groupId, long userId, int status, boolean subscribed,
			int start, int end)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<MBThread> getGroupThreads(
			long groupId, long userId, int status, int start, int end)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getGroupThreadsCount(
		long groupId, long userId, Date modifiedDate, boolean includeAnonymous,
		int status);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getGroupThreadsCount(
		long groupId, long userId, Date modifiedDate, int status);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getGroupThreadsCount(long groupId, long userId, int status);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getGroupThreadsCount(
		long groupId, long userId, int status, boolean subscribed);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getGroupThreadsCount(
		long groupId, long userId, int status, boolean subscribed,
		boolean includeAnonymous);

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public String getOSGiServiceIdentifier();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<MBThread> getThreads(
		long groupId, long categoryId, int status, int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<MBThread> getThreads(
			long groupId, long categoryId,
			QueryDefinition<MBThread> queryDefinition)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getThreadsCount(long groupId, long categoryId, int status);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getThreadsCount(
			long groupId, long categoryId,
			QueryDefinition<MBThread> queryDefinition)
		throws PortalException;

	public Lock lockThread(long threadId) throws PortalException;

	public MBThread moveThread(long categoryId, long threadId)
		throws PortalException;

	public MBThread moveThreadFromTrash(long categoryId, long threadId)
		throws PortalException;

	public MBThread moveThreadToTrash(long threadId) throws PortalException;

	public void restoreThreadFromTrash(long threadId) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Hits search(
			long groupId, long creatorUserId, int status, int start, int end)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Hits search(
			long groupId, long creatorUserId, long startDate, long endDate,
			int status, int start, int end)
		throws PortalException;

	public MBThread splitThread(
			long messageId, String subject, ServiceContext serviceContext)
		throws PortalException;

	public void unlockThread(long threadId) throws PortalException;

}