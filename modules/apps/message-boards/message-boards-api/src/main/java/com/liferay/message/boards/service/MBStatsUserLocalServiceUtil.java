/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.message.boards.service;

import com.liferay.portal.kernel.exception.PortalException;

import java.util.List;

/**
 * Provides the local service utility for MBStatsUser. This utility wraps
 * <code>com.liferay.message.boards.service.impl.MBStatsUserLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see MBStatsUserLocalService
 * @generated
 */
public class MBStatsUserLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.message.boards.service.impl.MBStatsUserLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static java.util.Date getLastPostDateByUserId(
		long groupId, long userId) {

		return getService().getLastPostDateByUserId(groupId, userId);
	}

	public static int getMessageCount(long groupId, long userId) {
		return getService().getMessageCount(groupId, userId);
	}

	public static long getMessageCountByGroupId(long groupId)
		throws PortalException {

		return getService().getMessageCountByGroupId(groupId);
	}

	public static long getMessageCountByUserId(long userId) {
		return getService().getMessageCountByUserId(userId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static List<com.liferay.message.boards.model.MBStatsUser>
			getStatsUsersByGroupId(long groupId, int start, int end)
		throws PortalException {

		return getService().getStatsUsersByGroupId(groupId, start, end);
	}

	public static int getStatsUsersByGroupIdCount(long groupId)
		throws PortalException {

		return getService().getStatsUsersByGroupIdCount(groupId);
	}

	public static String[] getUserRank(
			long groupId, String languageId, long userId)
		throws PortalException {

		return getService().getUserRank(groupId, languageId, userId);
	}

	public static MBStatsUserLocalService getService() {
		return _service;
	}

	public static void setService(MBStatsUserLocalService service) {
		_service = service;
	}

	private static volatile MBStatsUserLocalService _service;

}