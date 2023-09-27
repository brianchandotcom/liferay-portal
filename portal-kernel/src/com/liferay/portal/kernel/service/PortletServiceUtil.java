/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.service;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Portlet;

/**
 * Provides the remote service utility for Portlet. This utility wraps
 * <code>com.liferay.portal.service.impl.PortletServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see PortletService
 * @generated
 */
public class PortletServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.portal.service.impl.PortletServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.portal.kernel.json.JSONArray getWARPortlets() {
		return getService().getWARPortlets();
	}

	public static boolean hasPortlet(long companyId, String portletId) {
		return getService().hasPortlet(companyId, portletId);
	}

	public static Portlet updatePortlet(
			long companyId, String portletId, String roles, boolean active)
		throws PortalException {

		return getService().updatePortlet(companyId, portletId, roles, active);
	}

	public static PortletService getService() {
		return _service;
	}

	public static void setService(PortletService service) {
		_service = service;
	}

	private static volatile PortletService _service;

}