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
			String configurationJSON,
			Map<java.util.Locale, String> descriptionMap,
			String elementInstancesJSON, String schemaVersion,
			Map<java.util.Locale, String> titleMap,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().addSXPBlueprint(
			configurationJSON, descriptionMap, elementInstancesJSON,
			schemaVersion, titleMap, serviceContext);
	}

	public static SXPBlueprint deleteSXPBlueprint(long sxpBlueprintId)
		throws PortalException {

		return getService().deleteSXPBlueprint(sxpBlueprintId);
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

	private static volatile SXPBlueprintService _service;

}