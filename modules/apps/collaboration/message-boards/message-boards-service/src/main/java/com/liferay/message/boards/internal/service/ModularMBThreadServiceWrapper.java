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

package com.liferay.message.boards.internal.service;

import com.liferay.message.boards.kernel.model.MBThread;
import com.liferay.message.boards.kernel.service.MBThreadService;
import com.liferay.message.boards.kernel.service.MBThreadServiceWrapper;
import com.liferay.petra.model.adapter.util.ModelAdapterUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.lock.Lock;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceWrapper;

import java.util.Date;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Sergio González
 */
@Component(immediate = true, service = ServiceWrapper.class)
public class ModularMBThreadServiceWrapper extends MBThreadServiceWrapper {

	public ModularMBThreadServiceWrapper() {
		super(null);
	}

	public ModularMBThreadServiceWrapper(MBThreadService mbThreadService) {
		super(mbThreadService);
	}

	@Override
	public void deleteThread(long threadId) throws PortalException {
		_mbThreadService.deleteThread(threadId);
	}

	@Override
	public List<MBThread> getGroupThreads(
			long groupId, long userId, Date modifiedDate, int status, int start,
			int end)
		throws PortalException {

		return ModelAdapterUtil.adapt(
			MBThread.class,
			_mbThreadService.getGroupThreads(
				groupId, userId, modifiedDate, status, start, end));
	}

	@Override
	public List<MBThread> getGroupThreads(
			long groupId, long userId, int status, boolean subscribed,
			boolean includeAnonymous, int start, int end)
		throws PortalException {

		return ModelAdapterUtil.adapt(
			MBThread.class,
			_mbThreadService.getGroupThreads(
				groupId, userId, status, subscribed, includeAnonymous, start,
				end));
	}

	@Override
	public List<MBThread> getGroupThreads(
			long groupId, long userId, int status, boolean subscribed,
			int start, int end)
		throws PortalException {

		return ModelAdapterUtil.adapt(
			MBThread.class,
			_mbThreadService.getGroupThreads(
				groupId, userId, status, subscribed, start, end));
	}

	@Override
	public List<MBThread> getGroupThreads(
			long groupId, long userId, int status, int start, int end)
		throws PortalException {

		return ModelAdapterUtil.adapt(
			MBThread.class,
			_mbThreadService.getGroupThreads(
				groupId, userId, status, start, end));
	}

	@Override
	public int getGroupThreadsCount(
		long groupId, long userId, Date modifiedDate, int status) {

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

	@Override
	public String getOSGiServiceIdentifier() {
		return _mbThreadService.getOSGiServiceIdentifier();
	}

	@Override
	public List<MBThread> getThreads(
		long groupId, long categoryId, int status, int start, int end) {

		return ModelAdapterUtil.adapt(
			MBThread.class,
			_mbThreadService.getThreads(
				groupId, categoryId, status, start, end));
	}

	@Override
	public int getThreadsCount(long groupId, long categoryId, int status) {
		return _mbThreadService.getThreadsCount(groupId, categoryId, status);
	}

	@Override
	public MBThreadService getWrappedService() {
		return super.getWrappedService();
	}

	@Override
	public Lock lockThread(long threadId) throws PortalException {
		return _mbThreadService.lockThread(threadId);
	}

	@Override
	public MBThread moveThread(long categoryId, long threadId)
		throws PortalException {

		return ModelAdapterUtil.adapt(
			MBThread.class, _mbThreadService.moveThread(categoryId, threadId));
	}

	@Override
	public MBThread moveThreadFromTrash(long categoryId, long threadId)
		throws PortalException {

		return ModelAdapterUtil.adapt(
			MBThread.class,
			_mbThreadService.moveThreadFromTrash(categoryId, threadId));
	}

	@Override
	public MBThread moveThreadToTrash(long threadId) throws PortalException {
		return ModelAdapterUtil.adapt(
			MBThread.class, _mbThreadService.moveThreadToTrash(threadId));
	}

	@Override
	public void restoreThreadFromTrash(long threadId) throws PortalException {
		_mbThreadService.restoreThreadFromTrash(threadId);
	}

	@Override
	public Hits search(
			long groupId, long creatorUserId, int status, int start, int end)
		throws PortalException {

		return _mbThreadService.search(
			groupId, creatorUserId, status, start, end);
	}

	@Override
	public Hits search(
			long groupId, long creatorUserId, long startDate, long endDate,
			int status, int start, int end)
		throws PortalException {

		return _mbThreadService.search(
			groupId, creatorUserId, startDate, endDate, status, start, end);
	}

	@Override
	public void setWrappedService(MBThreadService mbThreadService) {
		super.setWrappedService(mbThreadService);
	}

	@Override
	public MBThread splitThread(
			long messageId, String subject, ServiceContext serviceContext)
		throws PortalException {

		return ModelAdapterUtil.adapt(
			MBThread.class,
			_mbThreadService.splitThread(messageId, subject, serviceContext));
	}

	@Override
	public void unlockThread(long threadId) throws PortalException {
		_mbThreadService.unlockThread(threadId);
	}

	@Reference
	private com.liferay.message.boards.service.MBThreadService _mbThreadService;

}