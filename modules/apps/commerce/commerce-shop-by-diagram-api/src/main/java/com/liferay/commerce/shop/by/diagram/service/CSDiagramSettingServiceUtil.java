/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.shop.by.diagram.service;

import com.liferay.commerce.shop.by.diagram.model.CSDiagramSetting;
import com.liferay.portal.kernel.exception.PortalException;

/**
 * Provides the remote service utility for CSDiagramSetting. This utility wraps
 * <code>com.liferay.commerce.shop.by.diagram.service.impl.CSDiagramSettingServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Alessio Antonio Rendina
 * @see CSDiagramSettingService
 * @generated
 */
public class CSDiagramSettingServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.commerce.shop.by.diagram.service.impl.CSDiagramSettingServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static CSDiagramSetting addCSDiagramSetting(
			long cpDefinitionId, long cpAttachmentFileEntryId, String color,
			double radius, String type)
		throws PortalException {

		return getService().addCSDiagramSetting(
			cpDefinitionId, cpAttachmentFileEntryId, color, radius, type);
	}

	public static CSDiagramSetting fetchCSDiagramSettingByCPDefinitionId(
			long cpDefinitionId)
		throws PortalException {

		return getService().fetchCSDiagramSettingByCPDefinitionId(
			cpDefinitionId);
	}

	public static CSDiagramSetting getCSDiagramSetting(long csDiagramSettingId)
		throws PortalException {

		return getService().getCSDiagramSetting(csDiagramSettingId);
	}

	public static CSDiagramSetting getCSDiagramSettingByCPDefinitionId(
			long cpDefinitionId)
		throws PortalException {

		return getService().getCSDiagramSettingByCPDefinitionId(cpDefinitionId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static CSDiagramSetting updateCSDiagramSetting(
			long csDiagramSettingId, long cpAttachmentFileEntryId, String color,
			double radius, String type)
		throws PortalException {

		return getService().updateCSDiagramSetting(
			csDiagramSettingId, cpAttachmentFileEntryId, color, radius, type);
	}

	public static CSDiagramSettingService getService() {
		return _service;
	}

	public static void setService(CSDiagramSettingService service) {
		_service = service;
	}

	private static volatile CSDiagramSettingService _service;

}