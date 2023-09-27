/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.page.template.service;

import com.liferay.layout.page.template.model.LayoutPageTemplateStructure;
import com.liferay.portal.kernel.exception.PortalException;

/**
 * Provides the remote service utility for LayoutPageTemplateStructure. This utility wraps
 * <code>com.liferay.layout.page.template.service.impl.LayoutPageTemplateStructureServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see LayoutPageTemplateStructureService
 * @generated
 */
public class LayoutPageTemplateStructureServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.layout.page.template.service.impl.LayoutPageTemplateStructureServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static LayoutPageTemplateStructure
			updateLayoutPageTemplateStructureData(
				long groupId, long plid, long segmentsExperienceId, String data)
		throws PortalException {

		return getService().updateLayoutPageTemplateStructureData(
			groupId, plid, segmentsExperienceId, data);
	}

	public static LayoutPageTemplateStructureService getService() {
		return _service;
	}

	public static void setService(LayoutPageTemplateStructureService service) {
		_service = service;
	}

	private static volatile LayoutPageTemplateStructureService _service;

}