/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CTCollectionTemplateService}.
 *
 * @author Brian Wing Shun Chan
 * @see CTCollectionTemplateService
 * @generated
 */
public class CTCollectionTemplateServiceWrapper
	implements CTCollectionTemplateService,
			   ServiceWrapper<CTCollectionTemplateService> {

	public CTCollectionTemplateServiceWrapper() {
		this(null);
	}

	public CTCollectionTemplateServiceWrapper(
		CTCollectionTemplateService ctCollectionTemplateService) {

		_ctCollectionTemplateService = ctCollectionTemplateService;
	}

	@Override
	public com.liferay.change.tracking.model.CTCollectionTemplate
			addCTCollectionTemplate(
				String name, String description, String json)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _ctCollectionTemplateService.addCTCollectionTemplate(
			name, description, json);
	}

	@Override
	public java.util.List
		<com.liferay.change.tracking.model.CTCollectionTemplate>
			getCTCollectionTemplates(
				String keywords, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.change.tracking.model.CTCollectionTemplate>
						orderByComparator) {

		return _ctCollectionTemplateService.getCTCollectionTemplates(
			keywords, start, end, orderByComparator);
	}

	@Override
	public int getCTCollectionTemplatesCount(String keywords) {
		return _ctCollectionTemplateService.getCTCollectionTemplatesCount(
			keywords);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _ctCollectionTemplateService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.change.tracking.model.CTCollectionTemplate
			updateCTCollectionTemplate(
				long ctCollectionTemplateId, String name, String description,
				String json)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _ctCollectionTemplateService.updateCTCollectionTemplate(
			ctCollectionTemplateId, name, description, json);
	}

	@Override
	public CTCollectionTemplateService getWrappedService() {
		return _ctCollectionTemplateService;
	}

	@Override
	public void setWrappedService(
		CTCollectionTemplateService ctCollectionTemplateService) {

		_ctCollectionTemplateService = ctCollectionTemplateService;
	}

	private CTCollectionTemplateService _ctCollectionTemplateService;

}