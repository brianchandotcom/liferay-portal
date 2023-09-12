/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.search.experiences.service;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.search.experiences.model.SXPBlueprint;

import java.util.Map;

/**
 * Provides the remote service utility for SXPBlueprint. This utility wraps
 * <code>com.liferay.search.experiences.service.impl.SXPBlueprintServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see SXPBlueprintService
 * @generated
 */
public class SXPBlueprintServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.search.experiences.service.impl.SXPBlueprintServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static SXPBlueprint addSXPBlueprint(
			String externalReferenceCode, String configurationJSON,
			Map<java.util.Locale, String> descriptionMap,
			String elementInstancesJSON, String schemaVersion,
			Map<java.util.Locale, String> titleMap,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().addSXPBlueprint(
			externalReferenceCode, configurationJSON, descriptionMap,
			elementInstancesJSON, schemaVersion, titleMap, serviceContext);
	}

	public static SXPBlueprint deleteSXPBlueprint(long sxpBlueprintId)
		throws PortalException {

		return getService().deleteSXPBlueprint(sxpBlueprintId);
	}

	public static SXPBlueprint fetchSXPBlueprint(long sxpBlueprintId)
		throws PortalException {

		return getService().fetchSXPBlueprint(sxpBlueprintId);
	}

	public static SXPBlueprint fetchSXPBlueprintByExternalReferenceCode(
			String externalReferenceCode, long companyId)
		throws PortalException {

		return getService().fetchSXPBlueprintByExternalReferenceCode(
			externalReferenceCode, companyId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static SXPBlueprint getSXPBlueprint(long sxpBlueprintId)
		throws PortalException {

		return getService().getSXPBlueprint(sxpBlueprintId);
	}

	public static SXPBlueprint getSXPBlueprintByExternalReferenceCode(
			long companyId, String externalReferenceCode)
		throws PortalException {

		return getService().getSXPBlueprintByExternalReferenceCode(
			companyId, externalReferenceCode);
	}

	public static SXPBlueprint updateSXPBlueprint(
			long sxpBlueprintId, String configurationJSON,
			Map<java.util.Locale, String> descriptionMap,
			String elementInstancesJSON, String schemaVersion,
			Map<java.util.Locale, String> titleMap,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().updateSXPBlueprint(
			sxpBlueprintId, configurationJSON, descriptionMap,
			elementInstancesJSON, schemaVersion, titleMap, serviceContext);
	}

	public static SXPBlueprintService getService() {
		return _service;
	}

	public static void setService(SXPBlueprintService service) {
		_service = service;
	}

	private static volatile SXPBlueprintService _service;

}