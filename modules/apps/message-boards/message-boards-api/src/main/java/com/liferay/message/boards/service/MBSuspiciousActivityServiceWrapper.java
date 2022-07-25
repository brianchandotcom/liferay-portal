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

import com.liferay.message.boards.model.MBSuspiciousActivity;
import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link MBSuspiciousActivityService}.
 *
 * @author Brian Wing Shun Chan
 * @see MBSuspiciousActivityService
 * @generated
 */
public class MBSuspiciousActivityServiceWrapper
	implements MBSuspiciousActivityService,
			   ServiceWrapper<MBSuspiciousActivityService> {

	public MBSuspiciousActivityServiceWrapper() {
		this(null);
	}

	public MBSuspiciousActivityServiceWrapper(
		MBSuspiciousActivityService mbSuspiciousActivityService) {

		_mbSuspiciousActivityService = mbSuspiciousActivityService;
	}

	@Override
	public MBSuspiciousActivity addOrUpdateSuspiciousActivityByMessage(
			long messageId, String reason)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _mbSuspiciousActivityService.
			addOrUpdateSuspiciousActivityByMessage(messageId, reason);
	}

	@Override
	public MBSuspiciousActivity addOrUpdateSuspiciousActivityByThread(
			String reason, long threadId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _mbSuspiciousActivityService.
			addOrUpdateSuspiciousActivityByThread(reason, threadId);
	}

	@Override
	public MBSuspiciousActivity deleteSuspiciousActivity(
			long suspiciousActivityId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _mbSuspiciousActivityService.deleteSuspiciousActivity(
			suspiciousActivityId);
	}

	@Override
	public java.util.List<MBSuspiciousActivity> getMessageSuspiciousActivities(
		long messageId) {

		return _mbSuspiciousActivityService.getMessageSuspiciousActivities(
			messageId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _mbSuspiciousActivityService.getOSGiServiceIdentifier();
	}

	@Override
	public MBSuspiciousActivity getSuspiciousActivity(long suspiciousActivityId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _mbSuspiciousActivityService.getSuspiciousActivity(
			suspiciousActivityId);
	}

	@Override
	public java.util.List<MBSuspiciousActivity> getThreadSuspiciousActivities(
		long threadId) {

		return _mbSuspiciousActivityService.getThreadSuspiciousActivities(
			threadId);
	}

	@Override
	public MBSuspiciousActivity updateValidated(long suspiciousActivityId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _mbSuspiciousActivityService.updateValidated(
			suspiciousActivityId);
	}

	@Override
	public MBSuspiciousActivityService getWrappedService() {
		return _mbSuspiciousActivityService;
	}

	@Override
	public void setWrappedService(
		MBSuspiciousActivityService mbSuspiciousActivityService) {

		_mbSuspiciousActivityService = mbSuspiciousActivityService;
	}

	private MBSuspiciousActivityService _mbSuspiciousActivityService;

}