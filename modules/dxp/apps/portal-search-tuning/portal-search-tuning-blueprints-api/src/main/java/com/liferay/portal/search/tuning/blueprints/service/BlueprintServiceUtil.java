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

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

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
	public static com.liferay.portal.search.tuning.blueprints.model.Blueprint
			addCompanyBlueprint(
				java.util.Map<java.util.Locale, String> titleMap,
				java.util.Map<java.util.Locale, String> descriptionMap,
				String configuration, String selectedElements, int type,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addCompanyBlueprint(
			titleMap, descriptionMap, configuration, selectedElements, type,
			serviceContext);
	}

	public static com.liferay.portal.search.tuning.blueprints.model.Blueprint
			addGroupBlueprint(
				java.util.Map<java.util.Locale, String> titleMap,
				java.util.Map<java.util.Locale, String> descriptionMap,
				String configuration, String selectedElements, int type,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addGroupBlueprint(
			titleMap, descriptionMap, configuration, selectedElements, type,
			serviceContext);
	}

	public static com.liferay.portal.search.tuning.blueprints.model.Blueprint
			deleteBlueprint(long blueprintId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteBlueprint(blueprintId);
	}

	public static com.liferay.portal.search.tuning.blueprints.model.Blueprint
			getBlueprint(long blueprintId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getBlueprint(blueprintId);
	}

	public static java.util.List
		<com.liferay.portal.search.tuning.blueprints.model.Blueprint>
			getGroupBlueprints(long companyId, int type, int start, int end) {

		return getService().getGroupBlueprints(companyId, type, start, end);
	}

	public static java.util.List
		<com.liferay.portal.search.tuning.blueprints.model.Blueprint>
			getGroupBlueprints(
				long companyId, int status, int type, int start, int end) {

		return getService().getGroupBlueprints(
			companyId, status, type, start, end);
	}

	public static java.util.List
		<com.liferay.portal.search.tuning.blueprints.model.Blueprint>
			getGroupBlueprints(
				long companyId, int status, int type, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.portal.search.tuning.blueprints.model.
						Blueprint> orderByComparator) {

		return getService().getGroupBlueprints(
			companyId, status, type, start, end, orderByComparator);
	}

	public static java.util.List
		<com.liferay.portal.search.tuning.blueprints.model.Blueprint>
			getGroupBlueprints(
				long companyId, int type, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.portal.search.tuning.blueprints.model.
						Blueprint> orderByComparator) {

		return getService().getGroupBlueprints(
			companyId, type, start, end, orderByComparator);
	}

	public static int getGroupBlueprintsCount(long companyId, int type) {
		return getService().getGroupBlueprintsCount(companyId, type);
	}

	public static int getGroupBlueprintsCount(
		long companyId, int status, int type) {

		return getService().getGroupBlueprintsCount(companyId, status, type);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.portal.search.tuning.blueprints.model.Blueprint
			updateBlueprint(
				long blueprintId,
				java.util.Map<java.util.Locale, String> titleMap,
				java.util.Map<java.util.Locale, String> descriptionMap,
				String configuration, String selectedElements,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateBlueprint(
			blueprintId, titleMap, descriptionMap, configuration,
			selectedElements, serviceContext);
	}

	public static BlueprintService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<BlueprintService, BlueprintService>
		_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(BlueprintService.class);

		ServiceTracker<BlueprintService, BlueprintService> serviceTracker =
			new ServiceTracker<BlueprintService, BlueprintService>(
				bundle.getBundleContext(), BlueprintService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}