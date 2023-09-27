/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.service;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.MembershipRequest;

/**
 * Provides the remote service utility for MembershipRequest. This utility wraps
 * <code>com.liferay.portal.service.impl.MembershipRequestServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see MembershipRequestService
 * @generated
 */
public class MembershipRequestServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.portal.service.impl.MembershipRequestServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static MembershipRequest addMembershipRequest(
			long groupId, String comments, ServiceContext serviceContext)
		throws PortalException {

		return getService().addMembershipRequest(
			groupId, comments, serviceContext);
	}

	public static void deleteMembershipRequests(long groupId, long statusId)
		throws PortalException {

		getService().deleteMembershipRequests(groupId, statusId);
	}

	public static MembershipRequest getMembershipRequest(
			long membershipRequestId)
		throws PortalException {

		return getService().getMembershipRequest(membershipRequestId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static void updateStatus(
			long membershipRequestId, String reviewComments, long statusId,
			ServiceContext serviceContext)
		throws PortalException {

		getService().updateStatus(
			membershipRequestId, reviewComments, statusId, serviceContext);
	}

	public static MembershipRequestService getService() {
		return _service;
	}

	public static void setService(MembershipRequestService service) {
		_service = service;
	}

	private static volatile MembershipRequestService _service;

}