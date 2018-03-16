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

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the remote service utility for MBThread. This utility wraps
 * {@link com.liferay.message.boards.service.impl.MBThreadServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on a remote server. Methods of this service are expected to have security
 * checks based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see MBThreadService
 * @see com.liferay.message.boards.service.base.MBThreadServiceBaseImpl
 * @see com.liferay.message.boards.service.impl.MBThreadServiceImpl
 * @generated
 */
@ProviderType
public class MBThreadServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.message.boards.service.impl.MBThreadServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static void deleteThread(long threadId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().deleteThread(threadId);
	}

	public static java.util.List<com.liferay.message.boards.model.MBThread> getGroupThreads(
		long groupId, long userId, java.util.Date modifiedDate,
		boolean includeAnonymous, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getGroupThreads(groupId, userId, modifiedDate,
			includeAnonymous, status, start, end);
	}

	public static java.util.List<com.liferay.message.boards.model.MBThread> getGroupThreads(
		long groupId, long userId, java.util.Date modifiedDate, int status,
		int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getGroupThreads(groupId, userId, modifiedDate, status,
			start, end);
	}

	public static java.util.List<com.liferay.message.boards.model.MBThread> getGroupThreads(
		long groupId, long userId, int status, boolean subscribed,
		boolean includeAnonymous, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getGroupThreads(groupId, userId, status, subscribed,
			includeAnonymous, start, end);
	}

	public static java.util.List<com.liferay.message.boards.model.MBThread> getGroupThreads(
		long groupId, long userId, int status, boolean subscribed, int start,
		int end) throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getGroupThreads(groupId, userId, status, subscribed, start,
			end);
	}

	public static java.util.List<com.liferay.message.boards.model.MBThread> getGroupThreads(
		long groupId, long userId, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getGroupThreads(groupId, userId, status, start, end);
	}

	public static int getGroupThreadsCount(long groupId, long userId,
		java.util.Date modifiedDate, boolean includeAnonymous, int status) {
		return getService()
				   .getGroupThreadsCount(groupId, userId, modifiedDate,
			includeAnonymous, status);
	}

	public static int getGroupThreadsCount(long groupId, long userId,
		java.util.Date modifiedDate, int status) {
		return getService()
				   .getGroupThreadsCount(groupId, userId, modifiedDate, status);
	}

	public static int getGroupThreadsCount(long groupId, long userId, int status) {
		return getService().getGroupThreadsCount(groupId, userId, status);
	}

	public static int getGroupThreadsCount(long groupId, long userId,
		int status, boolean subscribed) {
		return getService()
				   .getGroupThreadsCount(groupId, userId, status, subscribed);
	}

	public static int getGroupThreadsCount(long groupId, long userId,
		int status, boolean subscribed, boolean includeAnonymous) {
		return getService()
				   .getGroupThreadsCount(groupId, userId, status, subscribed,
			includeAnonymous);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static java.lang.String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static java.util.List<com.liferay.message.boards.model.MBThread> getThreads(
		long groupId, long categoryId, int status, int start, int end) {
		return getService().getThreads(groupId, categoryId, status, start, end);
	}

	public static java.util.List<com.liferay.message.boards.model.MBThread> getThreads(
		long groupId, long categoryId,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<com.liferay.message.boards.model.MBThread> queryDefinition)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getThreads(groupId, categoryId, queryDefinition);
	}

	public static int getThreadsCount(long groupId, long categoryId, int status) {
		return getService().getThreadsCount(groupId, categoryId, status);
	}

	public static int getThreadsCount(long groupId, long categoryId,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<com.liferay.message.boards.model.MBThread> queryDefinition)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getThreadsCount(groupId, categoryId, queryDefinition);
	}

	public static com.liferay.portal.kernel.lock.Lock lockThread(long threadId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().lockThread(threadId);
	}

	public static com.liferay.message.boards.model.MBThread moveThread(
		long categoryId, long threadId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().moveThread(categoryId, threadId);
	}

	public static com.liferay.message.boards.model.MBThread moveThreadFromTrash(
		long categoryId, long threadId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().moveThreadFromTrash(categoryId, threadId);
	}

	public static com.liferay.message.boards.model.MBThread moveThreadToTrash(
		long threadId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().moveThreadToTrash(threadId);
	}

	public static void restoreThreadFromTrash(long threadId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().restoreThreadFromTrash(threadId);
	}

	public static com.liferay.portal.kernel.search.Hits search(long groupId,
		long creatorUserId, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().search(groupId, creatorUserId, status, start, end);
	}

	public static com.liferay.portal.kernel.search.Hits search(long groupId,
		long creatorUserId, long startDate, long endDate, int status,
		int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .search(groupId, creatorUserId, startDate, endDate, status,
			start, end);
	}

	public static com.liferay.message.boards.model.MBThread splitThread(
		long messageId, java.lang.String subject,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().splitThread(messageId, subject, serviceContext);
	}

	public static void unlockThread(long threadId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().unlockThread(threadId);
	}

	public static MBThreadService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<MBThreadService, MBThreadService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(MBThreadService.class);

		ServiceTracker<MBThreadService, MBThreadService> serviceTracker = new ServiceTracker<MBThreadService, MBThreadService>(bundle.getBundleContext(),
				MBThreadService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}