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

package com.liferay.portal.search.tuning.blueprints.service;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.search.tuning.blueprints.model.Blueprint;

import java.util.List;
import java.util.Map;

/**
 * Provides the remote service utility for Blueprint. This utility wraps
 * <code>com.liferay.portal.search.tuning.blueprints.service.impl.BlueprintServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see BlueprintService
 * @generated
 */
public class BlueprintServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.portal.search.tuning.blueprints.service.impl.BlueprintServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static Blueprint addCompanyBlueprint(
			Map<java.util.Locale, String> titleMap,
			Map<java.util.Locale, String> descriptionMap, String configuration,
			String selectedElements,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().addCompanyBlueprint(
			titleMap, descriptionMap, configuration, selectedElements,
			serviceContext);
	}

	public static Blueprint addGroupBlueprint(
			Map<java.util.Locale, String> titleMap,
			Map<java.util.Locale, String> descriptionMap, String configuration,
			String selectedElements,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().addGroupBlueprint(
			titleMap, descriptionMap, configuration, selectedElements,
			serviceContext);
	}

	public static Blueprint deleteBlueprint(long blueprintId)
		throws PortalException {

		return getService().deleteBlueprint(blueprintId);
	}

	public static Blueprint getBlueprint(long blueprintId)
		throws PortalException {

		return getService().getBlueprint(blueprintId);
	}

	public static List<Blueprint> getGroupBlueprints(
		long groupId, int start, int end) {

		return getService().getGroupBlueprints(groupId, start, end);
	}

	public static List<Blueprint> getGroupBlueprints(
		long groupId, int status, int start, int end) {

		return getService().getGroupBlueprints(groupId, status, start, end);
	}

	public static List<Blueprint> getGroupBlueprints(
		long groupId, int status, int start, int end,
		OrderByComparator<Blueprint> orderByComparator) {

		return getService().getGroupBlueprints(
			groupId, status, start, end, orderByComparator);
	}

	public static List<Blueprint> getGroupBlueprints(
		long groupId, int start, int end,
		OrderByComparator<Blueprint> orderByComparator) {

		return getService().getGroupBlueprints(
			groupId, start, end, orderByComparator);
	}

	public static int getGroupBlueprintsCount(long groupId) {
		return getService().getGroupBlueprintsCount(groupId);
	}

	public static int getGroupBlueprintsCount(long groupId, int status) {
		return getService().getGroupBlueprintsCount(groupId, status);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static Blueprint updateBlueprint(
			long blueprintId, Map<java.util.Locale, String> titleMap,
			Map<java.util.Locale, String> descriptionMap, String configuration,
			String selectedElements,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().updateBlueprint(
			blueprintId, titleMap, descriptionMap, configuration,
			selectedElements, serviceContext);
	}

	public static BlueprintService getService() {
		return _service;
	}

	private static volatile BlueprintService _service;

}