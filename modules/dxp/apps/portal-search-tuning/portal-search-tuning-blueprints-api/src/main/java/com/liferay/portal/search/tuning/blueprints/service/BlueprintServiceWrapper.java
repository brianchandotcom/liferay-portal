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

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link BlueprintService}.
 *
 * @author Brian Wing Shun Chan
 * @see BlueprintService
 * @generated
 */
public class BlueprintServiceWrapper
	implements BlueprintService, ServiceWrapper<BlueprintService> {

	public BlueprintServiceWrapper(BlueprintService blueprintService) {
		_blueprintService = blueprintService;
	}

	@Override
	public com.liferay.portal.search.tuning.blueprints.model.Blueprint
			addCompanyBlueprint(
				java.util.Map<java.util.Locale, String> titleMap,
				java.util.Map<java.util.Locale, String> descriptionMap,
				String configuration, String selectedElements, int type,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _blueprintService.addCompanyBlueprint(
			titleMap, descriptionMap, configuration, selectedElements, type,
			serviceContext);
	}

	@Override
	public com.liferay.portal.search.tuning.blueprints.model.Blueprint
			addGroupBlueprint(
				java.util.Map<java.util.Locale, String> titleMap,
				java.util.Map<java.util.Locale, String> descriptionMap,
				String configuration, String selectedElements, int type,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _blueprintService.addGroupBlueprint(
			titleMap, descriptionMap, configuration, selectedElements, type,
			serviceContext);
	}

	@Override
	public com.liferay.portal.search.tuning.blueprints.model.Blueprint
			deleteBlueprint(long blueprintId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _blueprintService.deleteBlueprint(blueprintId);
	}

	@Override
	public com.liferay.portal.search.tuning.blueprints.model.Blueprint
			getBlueprint(long blueprintId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _blueprintService.getBlueprint(blueprintId);
	}

	@Override
	public java.util.List
		<com.liferay.portal.search.tuning.blueprints.model.Blueprint>
			getGroupBlueprints(long companyId, int type, int start, int end) {

		return _blueprintService.getGroupBlueprints(
			companyId, type, start, end);
	}

	@Override
	public java.util.List
		<com.liferay.portal.search.tuning.blueprints.model.Blueprint>
			getGroupBlueprints(
				long companyId, int status, int type, int start, int end) {

		return _blueprintService.getGroupBlueprints(
			companyId, status, type, start, end);
	}

	@Override
	public java.util.List
		<com.liferay.portal.search.tuning.blueprints.model.Blueprint>
			getGroupBlueprints(
				long companyId, int status, int type, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.portal.search.tuning.blueprints.model.
						Blueprint> orderByComparator) {

		return _blueprintService.getGroupBlueprints(
			companyId, status, type, start, end, orderByComparator);
	}

	@Override
	public java.util.List
		<com.liferay.portal.search.tuning.blueprints.model.Blueprint>
			getGroupBlueprints(
				long companyId, int type, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.portal.search.tuning.blueprints.model.
						Blueprint> orderByComparator) {

		return _blueprintService.getGroupBlueprints(
			companyId, type, start, end, orderByComparator);
	}

	@Override
	public int getGroupBlueprintsCount(long companyId, int type) {
		return _blueprintService.getGroupBlueprintsCount(companyId, type);
	}

	@Override
	public int getGroupBlueprintsCount(long companyId, int status, int type) {
		return _blueprintService.getGroupBlueprintsCount(
			companyId, status, type);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _blueprintService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.search.tuning.blueprints.model.Blueprint
			updateBlueprint(
				long blueprintId,
				java.util.Map<java.util.Locale, String> titleMap,
				java.util.Map<java.util.Locale, String> descriptionMap,
				String configuration, String selectedElements,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _blueprintService.updateBlueprint(
			blueprintId, titleMap, descriptionMap, configuration,
			selectedElements, serviceContext);
	}

	@Override
	public BlueprintService getWrappedService() {
		return _blueprintService;
	}

	@Override
	public void setWrappedService(BlueprintService blueprintService) {
		_blueprintService = blueprintService;
	}

	private BlueprintService _blueprintService;

}