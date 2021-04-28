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

import com.liferay.commerce.shop.by.diagram.model.CPDefinitionDiagramSettings;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.List;

/**
 * Provides the remote service utility for CPDefinitionDiagramSettings. This utility wraps
 * <code>com.liferay.commerce.shop.by.diagram.service.impl.CPDefinitionDiagramSettingsServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Alessio Antonio Rendina
 * @see CPDefinitionDiagramSettingsService
 * @generated
 */
public class CPDefinitionDiagramSettingsServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.commerce.shop.by.diagram.service.impl.CPDefinitionDiagramSettingsServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static CPDefinitionDiagramSettings addCPDefinitionDiagramSettings(
			long userId, long cpDefinitionId, long assetCategoryId, String name)
		throws PortalException {

		return getService().addCPDefinitionDiagramSettings(
			userId, cpDefinitionId, assetCategoryId, name);
	}

	public static CPDefinitionDiagramSettings
			fetchCPDefinitionDiagramSettingsByCPDefinitionId(
				long cpDefinitionId)
		throws PortalException {

		return getService().fetchCPDefinitionDiagramSettingsByCPDefinitionId(
			cpDefinitionId);
	}

	public static CPDefinitionDiagramSettings getCPDefinitionDiagramSettings(
			long cpDefinitionDiagramSettingsId)
		throws PortalException {

		return getService().getCPDefinitionDiagramSettings(
			cpDefinitionDiagramSettingsId);
	}

	public static CPDefinitionDiagramSettings
			getCPDefinitionDiagramSettingsByCPDefinitionId(long cpDefinitionId)
		throws PortalException {

		return getService().getCPDefinitionDiagramSettingsByCPDefinitionId(
			cpDefinitionId);
	}

	public static List<CPDefinitionDiagramSettings>
		getCPDefinitionDiagramSettingsList(
			long assetCategoryId, int start, int end) {

		return getService().getCPDefinitionDiagramSettingsList(
			assetCategoryId, start, end);
	}

	public static int getCPDefinitionDiagramSettingsListCount(
		long assetCategoryId) {

		return getService().getCPDefinitionDiagramSettingsListCount(
			assetCategoryId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static CPDefinitionDiagramSettings updateCPDefinitionDiagramSettings(
			long cpDefinitionDiagramSettingsId, long assetCategoryId,
			String name)
		throws PortalException {

		return getService().updateCPDefinitionDiagramSettings(
			cpDefinitionDiagramSettingsId, assetCategoryId, name);
	}

	public static CPDefinitionDiagramSettingsService getService() {
		return _service;
	}

	private static volatile CPDefinitionDiagramSettingsService _service;

}