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

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link SXPBlueprintService}.
 *
 * @author Brian Wing Shun Chan
 * @see SXPBlueprintService
 * @generated
 */
public class SXPBlueprintServiceWrapper
	implements ServiceWrapper<SXPBlueprintService>, SXPBlueprintService {

	public SXPBlueprintServiceWrapper() {
		this(null);
	}

	public SXPBlueprintServiceWrapper(SXPBlueprintService sxpBlueprintService) {
		_sxpBlueprintService = sxpBlueprintService;
	}

	@Override
	public com.liferay.search.experiences.model.SXPBlueprint addSXPBlueprint(
			String configurationJSON,
			java.util.Map<java.util.Locale, String> descriptionMap,
			String elementInstancesJSON, String externalReferenceCode,
			String schemaVersion,
			com.liferay.portal.kernel.service.ServiceContext serviceContext,
			java.util.Map<java.util.Locale, String> titleMap)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _sxpBlueprintService.addSXPBlueprint(
			configurationJSON, descriptionMap, elementInstancesJSON,
			externalReferenceCode, schemaVersion, serviceContext, titleMap);
	}

	@Override
	public com.liferay.search.experiences.model.SXPBlueprint deleteSXPBlueprint(
			long sxpBlueprintId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _sxpBlueprintService.deleteSXPBlueprint(sxpBlueprintId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _sxpBlueprintService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.search.experiences.model.SXPBlueprint getSXPBlueprint(
			long sxpBlueprintId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _sxpBlueprintService.getSXPBlueprint(sxpBlueprintId);
	}

	@Override
	public com.liferay.search.experiences.model.SXPBlueprint
			getSXPBlueprintByExternalReferenceCode(
				long companyId, String externalReferenceCode)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _sxpBlueprintService.getSXPBlueprintByExternalReferenceCode(
			companyId, externalReferenceCode);
	}

	@Override
	public com.liferay.search.experiences.model.SXPBlueprint updateSXPBlueprint(
			String configurationJSON,
			java.util.Map<java.util.Locale, String> descriptionMap,
			String elementInstancesJSON, String schemaVersion,
			com.liferay.portal.kernel.service.ServiceContext serviceContext,
			long sxpBlueprintId,
			java.util.Map<java.util.Locale, String> titleMap)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _sxpBlueprintService.updateSXPBlueprint(
			configurationJSON, descriptionMap, elementInstancesJSON,
			schemaVersion, serviceContext, sxpBlueprintId, titleMap);
	}

	@Override
	public SXPBlueprintService getWrappedService() {
		return _sxpBlueprintService;
	}

	@Override
	public void setWrappedService(SXPBlueprintService sxpBlueprintService) {
		_sxpBlueprintService = sxpBlueprintService;
	}

	private SXPBlueprintService _sxpBlueprintService;

}