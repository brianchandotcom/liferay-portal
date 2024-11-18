/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.service;

import com.liferay.commerce.product.model.CPSpecificationOptionListTypeDefinitionRel;
import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CPSpecificationOptionListTypeDefinitionRelService}.
 *
 * @author Marco Leo
 * @see CPSpecificationOptionListTypeDefinitionRelService
 * @generated
 */
public class CPSpecificationOptionListTypeDefinitionRelServiceWrapper
	implements CPSpecificationOptionListTypeDefinitionRelService,
			   ServiceWrapper
				   <CPSpecificationOptionListTypeDefinitionRelService> {

	public CPSpecificationOptionListTypeDefinitionRelServiceWrapper() {
		this(null);
	}

	public CPSpecificationOptionListTypeDefinitionRelServiceWrapper(
		CPSpecificationOptionListTypeDefinitionRelService
			cpSpecificationOptionListTypeDefinitionRelService) {

		_cpSpecificationOptionListTypeDefinitionRelService =
			cpSpecificationOptionListTypeDefinitionRelService;
	}

	@Override
	public CPSpecificationOptionListTypeDefinitionRel
			addCPSpecificationOptionListTypeDefinitionRel(
				long cpSpecificationOptionId, long listTypeDefinitionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cpSpecificationOptionListTypeDefinitionRelService.
			addCPSpecificationOptionListTypeDefinitionRel(
				cpSpecificationOptionId, listTypeDefinitionId);
	}

	@Override
	public void deleteCPSpecificationOptionListTypeDefinitionRel(
			long cpSpecificationOptionId, long listTypeDefinitionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_cpSpecificationOptionListTypeDefinitionRelService.
			deleteCPSpecificationOptionListTypeDefinitionRel(
				cpSpecificationOptionId, listTypeDefinitionId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _cpSpecificationOptionListTypeDefinitionRelService.
			getOSGiServiceIdentifier();
	}

	@Override
	public CPSpecificationOptionListTypeDefinitionRelService
		getWrappedService() {

		return _cpSpecificationOptionListTypeDefinitionRelService;
	}

	@Override
	public void setWrappedService(
		CPSpecificationOptionListTypeDefinitionRelService
			cpSpecificationOptionListTypeDefinitionRelService) {

		_cpSpecificationOptionListTypeDefinitionRelService =
			cpSpecificationOptionListTypeDefinitionRelService;
	}

	private CPSpecificationOptionListTypeDefinitionRelService
		_cpSpecificationOptionListTypeDefinitionRelService;

}