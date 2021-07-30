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

	@Override
	public com.liferay.commerce.shop.by.diagram.model.CPDefinitionDiagramEntry
			addCPDefinitionDiagramEntry(
				long userId, long cpDefinitionId, String cpInstanceUuid,
				long cProductId, boolean diagram, int number, int quantity,
				String sku,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cpDefinitionDiagramEntryService.addCPDefinitionDiagramEntry(
			userId, cpDefinitionId, cpInstanceUuid, cProductId, diagram, number,
			quantity, sku, serviceContext);
	}

	@Override
	public void deleteCPDefinitionDiagramEntry(long cpDefinitionDiagramEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_cpDefinitionDiagramEntryService.deleteCPDefinitionDiagramEntry(
			cpDefinitionDiagramEntryId);
	}

	@Override
	public com.liferay.commerce.shop.by.diagram.model.CPDefinitionDiagramEntry
			fetchCPDefinitionDiagramEntry(long cpDefinitionId, int number)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cpDefinitionDiagramEntryService.fetchCPDefinitionDiagramEntry(
			cpDefinitionId, number);
	}

	@Override
	public java.util.List
		<com.liferay.commerce.shop.by.diagram.model.CPDefinitionDiagramEntry>
				getCPDefinitionDiagramEntries(
					long cpDefinitionId, int start, int end)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _cpDefinitionDiagramEntryService.getCPDefinitionDiagramEntries(
			cpDefinitionId, start, end);
	}

	@Override
	public int getCPDefinitionDiagramEntriesCount(long cpDefinitionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cpDefinitionDiagramEntryService.
			getCPDefinitionDiagramEntriesCount(cpDefinitionId);
	}

	@Override
	public com.liferay.commerce.shop.by.diagram.model.CPDefinitionDiagramEntry
			getCPDefinitionDiagramEntry(long cpDefinitionDiagramEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cpDefinitionDiagramEntryService.getCPDefinitionDiagramEntry(
			cpDefinitionDiagramEntryId);
	}

	@Override
	public com.liferay.commerce.shop.by.diagram.model.CPDefinitionDiagramEntry
			getCPDefinitionDiagramEntry(long cpDefinitionId, int number)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cpDefinitionDiagramEntryService.getCPDefinitionDiagramEntry(
			cpDefinitionId, number);
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
	public com.liferay.commerce.shop.by.diagram.model.CPDefinitionDiagramEntry
			updateCPDefinitionDiagramEntry(
				long cpDefinitionDiagramEntryId, String cpInstanceUuid,
				long cProductId, boolean diagram, int number, int quantity,
				String sku,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cpDefinitionDiagramEntryService.updateCPDefinitionDiagramEntry(
			cpDefinitionDiagramEntryId, cpInstanceUuid, cProductId, diagram,
			number, quantity, sku, serviceContext);
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