/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.service;

import com.liferay.commerce.product.model.CPSOListTypeDefinitionRel;
import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CPSOListTypeDefinitionRelService}.
 *
 * @author Marco Leo
 * @see CPSOListTypeDefinitionRelService
 * @generated
 */
public class CPSOListTypeDefinitionRelServiceWrapper
	implements CPSOListTypeDefinitionRelService,
			   ServiceWrapper<CPSOListTypeDefinitionRelService> {

	public CPSOListTypeDefinitionRelServiceWrapper() {
		this(null);
	}

	public CPSOListTypeDefinitionRelServiceWrapper(
		CPSOListTypeDefinitionRelService cpsoListTypeDefinitionRelService) {

		_cpsoListTypeDefinitionRelService = cpsoListTypeDefinitionRelService;
	}

	@Override
	public CPSOListTypeDefinitionRel addCPSOListTypeDefinitionRel(
			long cpSpecificationOptionId, long listTypeDefinitionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cpsoListTypeDefinitionRelService.addCPSOListTypeDefinitionRel(
			cpSpecificationOptionId, listTypeDefinitionId);
	}

	@Override
	public void deleteCPSOListTypeDefinitionRel(
			long cpSpecificationOptionId, long listTypeDefinitionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_cpsoListTypeDefinitionRelService.deleteCPSOListTypeDefinitionRel(
			cpSpecificationOptionId, listTypeDefinitionId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _cpsoListTypeDefinitionRelService.getOSGiServiceIdentifier();
	}

	@Override
	public CPSOListTypeDefinitionRelService getWrappedService() {
		return _cpsoListTypeDefinitionRelService;
	}

	@Override
	public void setWrappedService(
		CPSOListTypeDefinitionRelService cpsoListTypeDefinitionRelService) {

		_cpsoListTypeDefinitionRelService = cpsoListTypeDefinitionRelService;
	}

	private CPSOListTypeDefinitionRelService _cpsoListTypeDefinitionRelService;

}