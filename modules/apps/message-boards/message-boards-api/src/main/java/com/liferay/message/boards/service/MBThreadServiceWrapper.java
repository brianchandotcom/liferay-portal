/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.message.boards.service;

import com.liferay.message.boards.model.MBThread;
import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link MBThreadService}.
 *
 * @author Brian Wing Shun Chan
 * @see MBThreadService
 * @generated
 */
public class MBThreadServiceWrapper
	implements MBThreadService, ServiceWrapper<MBThreadService> {

	public MBThreadServiceWrapper() {
		this(null);
	}

	public MBThreadServiceWrapper(MBThreadService mbThreadService) {
		_mbThreadService = mbThreadService;
	}

	@Override
	public void deleteThread(long threadId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_mbThreadService.deleteThread(threadId);
	}

	@Override
	public java.util.List<MBThread> getGroupThreads(
			long groupId, long userId, java.util.Date modifiedDate,
			boolean includeAnonymous, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _mbThreadService.getGroupThreads(
			groupId, userId, modifiedDate, includeAnonymous, status, start,
			end);
	}

	@Override
	public java.util.List<MBThread> getGroupThreads(
			long groupId, long userId, java.util.Date modifiedDate, int status,
			int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _mbThreadService.getGroupThreads(
			groupId, userId, modifiedDate, status, start, end);
	}

	@Override
	public java.util.List<MBThread> getGroupThreads(
			long groupId, long userId, int status, boolean subscribed,
			boolean includeAnonymous, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _mbThreadService.getGroupThreads(
			groupId, userId, status, subscribed, includeAnonymous, start, end);
	}

	@Override
	public java.util.List<MBThread> getGroupThreads(
			long groupId, long userId, int status, boolean subscribed,
			int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _mbThreadService.getGroupThreads(
			groupId, userId, status, subscribed, start, end);
	}

	@Override
	public java.util.List<MBThread> getGroupThreads(
			long groupId, long userId, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _mbThreadService.getGroupThreads(
			groupId, userId, status, start, end);
	}

	@Override
	public int getGroupThreadsCount(
		long groupId, long userId, java.util.Date modifiedDate,
		boolean includeAnonymous, int status) {

		return _mbThreadService.getGroupThreadsCount(
			groupId, userId, modifiedDate, includeAnonymous, status);
	}

	@Override
	public int getGroupThreadsCount(
		long groupId, long userId, java.util.Date modifiedDate, int status) {

		return _mbThreadService.getGroupThreadsCount(
			groupId, userId, modifiedDate, status);
	}

	@Override
	public int getGroupThreadsCount(long groupId, long userId, int status) {
		return _mbThreadService.getGroupThreadsCount(groupId, userId, status);
	}

	@Override
	public int getGroupThreadsCount(
		long groupId, long userId, int status, boolean subscribed) {

		return _mbThreadService.getGroupThreadsCount(
			groupId, userId, status, subscribed);
	}

	@Override
	public int getGroupThreadsCount(
		long groupId, long userId, int status, boolean subscribed,
		boolean includeAnonymous) {

		return _mbThreadService.getGroupThreadsCount(
			groupId, userId, status, subscribed, includeAnonymous);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _mbThreadService.getOSGiServiceIdentifier();
	}

	@Override
	public java.util.List<MBThread> getThreads(
		long groupId, long categoryId, int status, int start, int end) {

		return _mbThreadService.getThreads(
			groupId, categoryId, status, start, end);
	}

	@Override
	public java.util.List<MBThread> getThreads(
			long groupId, long categoryId,
			com.liferay.portal.kernel.dao.orm.QueryDefinition<MBThread>
				queryDefinition)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _mbThreadService.getThreads(
			groupId, categoryId, queryDefinition);
	}

	@Override
	public int getThreadsCount(long groupId, long categoryId, int status) {
		return _mbThreadService.getThreadsCount(groupId, categoryId, status);
	}

	@Override
	public int getThreadsCount(
			long groupId, long categoryId,
			com.liferay.portal.kernel.dao.orm.QueryDefinition<MBThread>
				queryDefinition)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _mbThreadService.getThreadsCount(
			groupId, categoryId, queryDefinition);
	}

	@Override
	public com.liferay.portal.kernel.lock.Lock lockThread(long threadId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _mbThreadService.lockThread(threadId);
	}

	@Override
	public MBThread moveThread(long categoryId, long threadId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _mbThreadService.moveThread(categoryId, threadId);
	}

	@Override
	public MBThread moveThreadFromTrash(long categoryId, long threadId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _mbThreadService.moveThreadFromTrash(categoryId, threadId);
	}

	@Override
	public MBThread moveThreadToTrash(long threadId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _mbThreadService.moveThreadToTrash(threadId);
	}

	@Override
	public void restoreThreadFromTrash(long threadId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_mbThreadService.restoreThreadFromTrash(threadId);
	}

	@Override
	public com.liferay.portal.kernel.search.Hits search(
			long groupId, long creatorUserId, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _mbThreadService.search(
			groupId, creatorUserId, status, start, end);
	}

	@Override
	public com.liferay.portal.kernel.search.Hits search(
			long groupId, long creatorUserId, long startDate, long endDate,
			int status, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _mbThreadService.search(
			groupId, creatorUserId, startDate, endDate, status, start, end);
	}

	@Override
	public MBThread splitThread(
			long messageId, String subject,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _mbThreadService.splitThread(messageId, subject, serviceContext);
	}

	@Override
	public void unlockThread(long threadId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_mbThreadService.unlockThread(threadId);
	}

	@Override
	public MBThreadService getWrappedService() {
		return _mbThreadService;
	}

	@Override
	public void setWrappedService(MBThreadService mbThreadService) {
		_mbThreadService = mbThreadService;
	}

	private MBThreadService _mbThreadService;

}