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
import com.liferay.portal.kernel.exception.PortalException;

import java.util.List;

/**
 * Provides the remote service utility for MBSuspiciousActivity. This utility wraps
 * <code>com.liferay.message.boards.service.impl.MBSuspiciousActivityServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see MBSuspiciousActivityService
 * @generated
 */
public class MBSuspiciousActivityServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.message.boards.service.impl.MBSuspiciousActivityServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static MBSuspiciousActivity addOrUpdateSuspiciousActivityByMessage(
			long messageId, String reason)
		throws PortalException {

		return getService().addOrUpdateSuspiciousActivityByMessage(
			messageId, reason);
	}

	public static MBSuspiciousActivity addOrUpdateSuspiciousActivityByThread(
			String reason, long threadId)
		throws PortalException {

		return getService().addOrUpdateSuspiciousActivityByThread(
			reason, threadId);
	}

	public static MBSuspiciousActivity deleteSuspiciousActivity(
			long suspiciousActivityId)
		throws PortalException {

		return getService().deleteSuspiciousActivity(suspiciousActivityId);
	}

	public static List<MBSuspiciousActivity> getMessageSuspiciousActivities(
		long messageId) {

		return getService().getMessageSuspiciousActivities(messageId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static MBSuspiciousActivity getSuspiciousActivity(
			long suspiciousActivityId)
		throws PortalException {

		return getService().getSuspiciousActivity(suspiciousActivityId);
	}

	public static List<MBSuspiciousActivity> getThreadSuspiciousActivities(
		long threadId) {

		return getService().getThreadSuspiciousActivities(threadId);
	}

	public static MBSuspiciousActivity updateValidated(
			long suspiciousActivityId)
		throws PortalException {

		return getService().updateValidated(suspiciousActivityId);
	}

	public static MBSuspiciousActivityService getService() {
		return _service;
	}

	private static volatile MBSuspiciousActivityService _service;

}