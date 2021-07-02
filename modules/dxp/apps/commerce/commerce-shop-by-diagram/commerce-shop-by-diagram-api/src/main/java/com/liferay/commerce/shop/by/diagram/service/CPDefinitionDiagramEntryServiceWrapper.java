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
 * Provides a wrapper for {@link CPDefinitionDiagramEntryService}.
 *
 * @author Andrea Sbarra
 * @see CPDefinitionDiagramEntryService
 * @generated
 */
public class CPDefinitionDiagramEntryServiceWrapper
	implements CPDefinitionDiagramEntryService,
			   ServiceWrapper<CPDefinitionDiagramEntryService> {

	public CPDefinitionDiagramEntryServiceWrapper(
		CPDefinitionDiagramEntryService cpDefinitionDiagramEntryService) {

		_cpDefinitionDiagramEntryService = cpDefinitionDiagramEntryService;
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _cpDefinitionDiagramEntryService.getOSGiServiceIdentifier();
	}

	@Override
	public CPDefinitionDiagramEntryService getWrappedService() {
		return _cpDefinitionDiagramEntryService;
	}

	@Override
	public void setWrappedService(
		CPDefinitionDiagramEntryService cpDefinitionDiagramEntryService) {

		_cpDefinitionDiagramEntryService = cpDefinitionDiagramEntryService;
	}

	private CPDefinitionDiagramEntryService _cpDefinitionDiagramEntryService;

}