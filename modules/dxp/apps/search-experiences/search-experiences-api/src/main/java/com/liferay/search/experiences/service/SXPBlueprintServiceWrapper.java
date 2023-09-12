/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
			String externalReferenceCode, String configurationJSON,
			java.util.Map<java.util.Locale, String> descriptionMap,
			String elementInstancesJSON, String schemaVersion,
			java.util.Map<java.util.Locale, String> titleMap,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _sxpBlueprintService.addSXPBlueprint(
			externalReferenceCode, configurationJSON, descriptionMap,
			elementInstancesJSON, schemaVersion, titleMap, serviceContext);
	}

	@Override
	public com.liferay.search.experiences.model.SXPBlueprint deleteSXPBlueprint(
			long sxpBlueprintId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _sxpBlueprintService.deleteSXPBlueprint(sxpBlueprintId);
	}

	@Override
	public com.liferay.search.experiences.model.SXPBlueprint fetchSXPBlueprint(
			long sxpBlueprintId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _sxpBlueprintService.fetchSXPBlueprint(sxpBlueprintId);
	}

	@Override
	public com.liferay.search.experiences.model.SXPBlueprint
			fetchSXPBlueprintByExternalReferenceCode(
				String externalReferenceCode, long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _sxpBlueprintService.fetchSXPBlueprintByExternalReferenceCode(
			externalReferenceCode, companyId);
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
			long sxpBlueprintId, String configurationJSON,
			java.util.Map<java.util.Locale, String> descriptionMap,
			String elementInstancesJSON, String schemaVersion,
			java.util.Map<java.util.Locale, String> titleMap,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _sxpBlueprintService.updateSXPBlueprint(
			sxpBlueprintId, configurationJSON, descriptionMap,
			elementInstancesJSON, schemaVersion, titleMap, serviceContext);
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