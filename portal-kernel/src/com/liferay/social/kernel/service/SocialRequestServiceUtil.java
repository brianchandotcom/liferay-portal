/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.social.kernel.service;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.social.kernel.model.SocialRequest;

/**
 * Provides the remote service utility for SocialRequest. This utility wraps
 * <code>com.liferay.portlet.social.service.impl.SocialRequestServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see SocialRequestService
 * @generated
 */
public class SocialRequestServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.portlet.social.service.impl.SocialRequestServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static SocialRequest updateRequest(
			long requestId, int status,
			com.liferay.portal.kernel.theme.ThemeDisplay themeDisplay)
		throws PortalException {

		return getService().updateRequest(requestId, status, themeDisplay);
	}

	public static SocialRequestService getService() {
		return _service;
	}

	public static void setService(SocialRequestService service) {
		_service = service;
	}

	private static volatile SocialRequestService _service;

}