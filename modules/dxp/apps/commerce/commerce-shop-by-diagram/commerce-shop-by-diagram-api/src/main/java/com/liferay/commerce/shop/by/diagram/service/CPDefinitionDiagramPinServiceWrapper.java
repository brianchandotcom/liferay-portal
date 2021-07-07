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

package com.liferay.commerce.shop.by.diagram.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CPDefinitionDiagramPinService}.
 *
 * @author Andrea Sbarra
 * @see CPDefinitionDiagramPinService
 * @generated
 */
public class CPDefinitionDiagramPinServiceWrapper
	implements CPDefinitionDiagramPinService,
			   ServiceWrapper<CPDefinitionDiagramPinService> {

	public CPDefinitionDiagramPinServiceWrapper(
		CPDefinitionDiagramPinService cpDefinitionDiagramPinService) {

		_cpDefinitionDiagramPinService = cpDefinitionDiagramPinService;
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _cpDefinitionDiagramPinService.getOSGiServiceIdentifier();
	}

	@Override
	public CPDefinitionDiagramPinService getWrappedService() {
		return _cpDefinitionDiagramPinService;
	}

	@Override
	public void setWrappedService(
		CPDefinitionDiagramPinService cpDefinitionDiagramPinService) {

		_cpDefinitionDiagramPinService = cpDefinitionDiagramPinService;
	}

	private CPDefinitionDiagramPinService _cpDefinitionDiagramPinService;

}