/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.shop.by.diagram.service;

import com.liferay.commerce.shop.by.diagram.model.CPDefinitionDiagramSetting;
import com.liferay.portal.kernel.exception.PortalException;

/**
 * Provides the remote service utility for CPDefinitionDiagramSetting. This utility wraps
 * <code>com.liferay.commerce.shop.by.diagram.service.impl.CPDefinitionDiagramSettingServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Andrea Sbarra
 * @see CPDefinitionDiagramSettingService
 * @generated
 */
public class CPDefinitionDiagramSettingServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.commerce.shop.by.diagram.service.impl.CPDefinitionDiagramSettingServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static CPDefinitionDiagramSetting addCPDefinitionDiagramSetting(
			long cpDefinitionId, long cpAttachmentFileEntryId, String color,
			double radius, String type)
		throws PortalException {

		return getService().addCPDefinitionDiagramSetting(
			cpDefinitionId, cpAttachmentFileEntryId, color, radius, type);
	}

	public static CPDefinitionDiagramSetting
			fetchCPDefinitionDiagramSettingByCPDefinitionId(long cpDefinitionId)
		throws PortalException {

		return getService().fetchCPDefinitionDiagramSettingByCPDefinitionId(
			cpDefinitionId);
	}

	public static CPDefinitionDiagramSetting getCPDefinitionDiagramSetting(
			long cpDefinitionDiagramSettingId)
		throws PortalException {

		return getService().getCPDefinitionDiagramSetting(
			cpDefinitionDiagramSettingId);
	}

	public static CPDefinitionDiagramSetting
			getCPDefinitionDiagramSettingByCPDefinitionId(long cpDefinitionId)
		throws PortalException {

		return getService().getCPDefinitionDiagramSettingByCPDefinitionId(
			cpDefinitionId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static CPDefinitionDiagramSetting updateCPDefinitionDiagramSetting(
			long cpDefinitionDiagramSettingId, long cpAttachmentFileEntryId,
			String color, double radius, String type)
		throws PortalException {

		return getService().updateCPDefinitionDiagramSetting(
			cpDefinitionDiagramSettingId, cpAttachmentFileEntryId, color,
			radius, type);
	}

	public static CPDefinitionDiagramSettingService getService() {
		return _service;
	}

	private static volatile CPDefinitionDiagramSettingService _service;

}