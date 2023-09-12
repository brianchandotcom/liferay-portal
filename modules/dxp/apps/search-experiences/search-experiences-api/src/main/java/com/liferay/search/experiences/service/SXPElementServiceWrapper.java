/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.search.experiences.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link SXPElementService}.
 *
 * @author Brian Wing Shun Chan
 * @see SXPElementService
 * @generated
 */
public class SXPElementServiceWrapper
	implements ServiceWrapper<SXPElementService>, SXPElementService {

	public SXPElementServiceWrapper() {
		this(null);
	}

	public SXPElementServiceWrapper(SXPElementService sxpElementService) {
		_sxpElementService = sxpElementService;
	}

	@Override
	public com.liferay.search.experiences.model.SXPElement addSXPElement(
			String externalReferenceCode,
			java.util.Map<java.util.Locale, String> descriptionMap,
			String elementDefinitionJSON, boolean readOnly,
			String schemaVersion,
			java.util.Map<java.util.Locale, String> titleMap, int type,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _sxpElementService.addSXPElement(
			externalReferenceCode, descriptionMap, elementDefinitionJSON,
			readOnly, schemaVersion, titleMap, type, serviceContext);
	}

	@Override
	public com.liferay.search.experiences.model.SXPElement deleteSXPElement(
			long sxpElementId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _sxpElementService.deleteSXPElement(sxpElementId);
	}

	@Override
	public com.liferay.search.experiences.model.SXPElement fetchSXPElement(
			long sxpElementId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _sxpElementService.fetchSXPElement(sxpElementId);
	}

	@Override
	public com.liferay.search.experiences.model.SXPElement
			fetchSXPElementByExternalReferenceCode(
				String externalReferenceCode, long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _sxpElementService.fetchSXPElementByExternalReferenceCode(
			externalReferenceCode, companyId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _sxpElementService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.search.experiences.model.SXPElement getSXPElement(
			long sxpElementId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _sxpElementService.getSXPElement(sxpElementId);
	}

	@Override
	public com.liferay.search.experiences.model.SXPElement
			getSXPElementByExternalReferenceCode(
				String externalReferenceCode, long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _sxpElementService.getSXPElementByExternalReferenceCode(
			externalReferenceCode, companyId);
	}

	@Override
	public com.liferay.search.experiences.model.SXPElement updateSXPElement(
			long sxpElementId,
			java.util.Map<java.util.Locale, String> descriptionMap,
			String elementDefinitionJSON, String schemaVersion, boolean hidden,
			java.util.Map<java.util.Locale, String> titleMap,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _sxpElementService.updateSXPElement(
			sxpElementId, descriptionMap, elementDefinitionJSON, schemaVersion,
			hidden, titleMap, serviceContext);
	}

	@Override
	public SXPElementService getWrappedService() {
		return _sxpElementService;
	}

	@Override
	public void setWrappedService(SXPElementService sxpElementService) {
		_sxpElementService = sxpElementService;
	}

	private SXPElementService _sxpElementService;

}