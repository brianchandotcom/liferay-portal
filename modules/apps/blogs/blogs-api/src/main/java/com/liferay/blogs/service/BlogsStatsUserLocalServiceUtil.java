/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.blogs.service;

import com.liferay.portal.kernel.exception.PortalException;

import java.util.List;

/**
 * Provides the local service utility for BlogsStatsUser. This utility wraps
 * <code>com.liferay.blogs.service.impl.BlogsStatsUserLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see BlogsStatsUserLocalService
 * @generated
 */
public class BlogsStatsUserLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.blogs.service.impl.BlogsStatsUserLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static List<com.liferay.blogs.model.BlogsStatsUser>
		getGroupsStatsUsers(long companyId, long groupId, int start, int end) {

		return getService().getGroupsStatsUsers(companyId, groupId, start, end);
	}

	public static List<com.liferay.blogs.model.BlogsStatsUser>
		getGroupStatsUsers(long groupId, int start, int end) {

		return getService().getGroupStatsUsers(groupId, start, end);
	}

	public static List<com.liferay.blogs.model.BlogsStatsUser>
		getOrganizationStatsUsers(long organizationId, int start, int end) {

		return getService().getOrganizationStatsUsers(
			organizationId, start, end);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.blogs.model.BlogsStatsUser getStatsUser(
			long groupId, long userId)
		throws PortalException {

		return getService().getStatsUser(groupId, userId);
	}

	public static BlogsStatsUserLocalService getService() {
		return _service;
	}

	public static void setService(BlogsStatsUserLocalService service) {
		_service = service;
	}

	private static volatile BlogsStatsUserLocalService _service;

}