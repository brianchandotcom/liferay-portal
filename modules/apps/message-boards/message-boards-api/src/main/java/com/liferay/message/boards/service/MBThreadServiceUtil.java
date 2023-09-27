/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.message.boards.service;

import com.liferay.message.boards.model.MBThread;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.List;

/**
 * Provides the remote service utility for MBThread. This utility wraps
 * <code>com.liferay.message.boards.service.impl.MBThreadServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see MBThreadService
 * @generated
 */
public class MBThreadServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.message.boards.service.impl.MBThreadServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static void deleteThread(long threadId) throws PortalException {
		getService().deleteThread(threadId);
	}

	public static List<MBThread> getGroupThreads(
			long groupId, long userId, java.util.Date modifiedDate,
			boolean includeAnonymous, int status, int start, int end)
		throws PortalException {

		return getService().getGroupThreads(
			groupId, userId, modifiedDate, includeAnonymous, status, start,
			end);
	}

	public static List<MBThread> getGroupThreads(
			long groupId, long userId, java.util.Date modifiedDate, int status,
			int start, int end)
		throws PortalException {

		return getService().getGroupThreads(
			groupId, userId, modifiedDate, status, start, end);
	}

	public static List<MBThread> getGroupThreads(
			long groupId, long userId, int status, boolean subscribed,
			boolean includeAnonymous, int start, int end)
		throws PortalException {

		return getService().getGroupThreads(
			groupId, userId, status, subscribed, includeAnonymous, start, end);
	}

	public static List<MBThread> getGroupThreads(
			long groupId, long userId, int status, boolean subscribed,
			int start, int end)
		throws PortalException {

		return getService().getGroupThreads(
			groupId, userId, status, subscribed, start, end);
	}

	public static List<MBThread> getGroupThreads(
			long groupId, long userId, int status, int start, int end)
		throws PortalException {

		return getService().getGroupThreads(
			groupId, userId, status, start, end);
	}

	public static int getGroupThreadsCount(
		long groupId, long userId, java.util.Date modifiedDate,
		boolean includeAnonymous, int status) {

		return getService().getGroupThreadsCount(
			groupId, userId, modifiedDate, includeAnonymous, status);
	}

	public static int getGroupThreadsCount(
		long groupId, long userId, java.util.Date modifiedDate, int status) {

		return getService().getGroupThreadsCount(
			groupId, userId, modifiedDate, status);
	}

	public static int getGroupThreadsCount(
		long groupId, long userId, int status) {

		return getService().getGroupThreadsCount(groupId, userId, status);
	}

	public static int getGroupThreadsCount(
		long groupId, long userId, int status, boolean subscribed) {

		return getService().getGroupThreadsCount(
			groupId, userId, status, subscribed);
	}

	public static int getGroupThreadsCount(
		long groupId, long userId, int status, boolean subscribed,
		boolean includeAnonymous) {

		return getService().getGroupThreadsCount(
			groupId, userId, status, subscribed, includeAnonymous);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static List<MBThread> getThreads(
		long groupId, long categoryId, int status, int start, int end) {

		return getService().getThreads(groupId, categoryId, status, start, end);
	}

	public static List<MBThread> getThreads(
			long groupId, long categoryId,
			com.liferay.portal.kernel.dao.orm.QueryDefinition<MBThread>
				queryDefinition)
		throws PortalException {

		return getService().getThreads(groupId, categoryId, queryDefinition);
	}

	public static int getThreadsCount(
		long groupId, long categoryId, int status) {

		return getService().getThreadsCount(groupId, categoryId, status);
	}

	public static int getThreadsCount(
			long groupId, long categoryId,
			com.liferay.portal.kernel.dao.orm.QueryDefinition<MBThread>
				queryDefinition)
		throws PortalException {

		return getService().getThreadsCount(
			groupId, categoryId, queryDefinition);
	}

	public static com.liferay.portal.kernel.lock.Lock lockThread(long threadId)
		throws PortalException {

		return getService().lockThread(threadId);
	}

	public static MBThread moveThread(long categoryId, long threadId)
		throws PortalException {

		return getService().moveThread(categoryId, threadId);
	}

	public static MBThread moveThreadFromTrash(long categoryId, long threadId)
		throws PortalException {

		return getService().moveThreadFromTrash(categoryId, threadId);
	}

	public static MBThread moveThreadToTrash(long threadId)
		throws PortalException {

		return getService().moveThreadToTrash(threadId);
	}

	public static void restoreThreadFromTrash(long threadId)
		throws PortalException {

		getService().restoreThreadFromTrash(threadId);
	}

	public static com.liferay.portal.kernel.search.Hits search(
			long groupId, long creatorUserId, int status, int start, int end)
		throws PortalException {

		return getService().search(groupId, creatorUserId, status, start, end);
	}

	public static com.liferay.portal.kernel.search.Hits search(
			long groupId, long creatorUserId, long startDate, long endDate,
			int status, int start, int end)
		throws PortalException {

		return getService().search(
			groupId, creatorUserId, startDate, endDate, status, start, end);
	}

	public static MBThread splitThread(
			long messageId, String subject,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().splitThread(messageId, subject, serviceContext);
	}

	public static void unlockThread(long threadId) throws PortalException {
		getService().unlockThread(threadId);
	}

	public static MBThreadService getService() {
		return _service;
	}

	public static void setService(MBThreadService service) {
		_service = service;
	}

	private static volatile MBThreadService _service;

}