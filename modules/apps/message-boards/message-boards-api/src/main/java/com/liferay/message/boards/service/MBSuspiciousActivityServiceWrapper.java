/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
	public MBSuspiciousActivity addOrUpdateMessageSuspiciousActivity(
			long messageId, String reason)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _mbSuspiciousActivityService.
			addOrUpdateMessageSuspiciousActivity(messageId, reason);
	}

	@Override
	public MBSuspiciousActivity addOrUpdateThreadSuspiciousActivity(
			long threadId, String reason)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _mbSuspiciousActivityService.addOrUpdateThreadSuspiciousActivity(
			threadId, reason);
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