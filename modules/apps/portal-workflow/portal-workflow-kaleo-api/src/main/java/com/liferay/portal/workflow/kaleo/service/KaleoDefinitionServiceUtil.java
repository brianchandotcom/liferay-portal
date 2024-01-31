/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.service;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.workflow.kaleo.model.KaleoDefinition;

/**
 * Provides the remote service utility for KaleoDefinition. This utility wraps
 * <code>com.liferay.portal.workflow.kaleo.service.impl.KaleoDefinitionServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see KaleoDefinitionService
 * @generated
 */
public class KaleoDefinitionServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.portal.workflow.kaleo.service.impl.KaleoDefinitionServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static KaleoDefinition addKaleoDefinition(
			String name, String title, String description, String content,
			String scope, int version,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().addKaleoDefinition(
			name, title, description, content, scope, version, serviceContext);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static KaleoDefinition updateKaleoDefinition(
			long kaleoDefinitionId, String title, String description,
			String content,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().updateKaleoDefinition(
			kaleoDefinitionId, title, description, content, serviceContext);
	}

	public static KaleoDefinitionService getService() {
		return _service;
	}

	public static void setService(KaleoDefinitionService service) {
		_service = service;
	}

	private static volatile KaleoDefinitionService _service;

}