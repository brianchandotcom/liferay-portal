/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.commerce.shop.by.diagram.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CSDiagramEntryService}.
 *
 * @author Alessio Antonio Rendina
 * @see CSDiagramEntryService
 * @generated
 */
public class CSDiagramEntryServiceWrapper
	implements CSDiagramEntryService, ServiceWrapper<CSDiagramEntryService> {

	public CSDiagramEntryServiceWrapper() {
		this(null);
	}

	public CSDiagramEntryServiceWrapper(
		CSDiagramEntryService csDiagramEntryService) {

		_csDiagramEntryService = csDiagramEntryService;
	}

	@Override
	public com.liferay.commerce.shop.by.diagram.model.CSDiagramEntry
			addCSDiagramEntry(
				long cpDefinitionId, long cpInstanceId, long cProductId,
				boolean diagram, int quantity, String sequence, String sku,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _csDiagramEntryService.addCSDiagramEntry(
			cpDefinitionId, cpInstanceId, cProductId, diagram, quantity,
			sequence, sku, serviceContext);
	}

	@Override
	public void deleteCSDiagramEntries(long cpDefinitionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_csDiagramEntryService.deleteCSDiagramEntries(cpDefinitionId);
	}

	@Override
	public void deleteCSDiagramEntry(
			com.liferay.commerce.shop.by.diagram.model.CSDiagramEntry
				csDiagramEntry)
		throws com.liferay.portal.kernel.exception.PortalException {

		_csDiagramEntryService.deleteCSDiagramEntry(csDiagramEntry);
	}

	@Override
	public com.liferay.commerce.shop.by.diagram.model.CSDiagramEntry
			fetchCSDiagramEntry(long cpDefinitionId, String sequence)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _csDiagramEntryService.fetchCSDiagramEntry(
			cpDefinitionId, sequence);
	}

	@Override
	public java.util.List
		<com.liferay.commerce.shop.by.diagram.model.CSDiagramEntry>
				getCSDiagramEntries(long cpDefinitionId, int start, int end)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _csDiagramEntryService.getCSDiagramEntries(
			cpDefinitionId, start, end);
	}

	@Override
	public int getCSDiagramEntriesCount(long cpDefinitionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _csDiagramEntryService.getCSDiagramEntriesCount(cpDefinitionId);
	}

	@Override
	public com.liferay.commerce.shop.by.diagram.model.CSDiagramEntry
			getCSDiagramEntry(long csDiagramEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _csDiagramEntryService.getCSDiagramEntry(csDiagramEntryId);
	}

	@Override
	public com.liferay.commerce.shop.by.diagram.model.CSDiagramEntry
			getCSDiagramEntry(long cpDefinitionId, String sequence)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _csDiagramEntryService.getCSDiagramEntry(
			cpDefinitionId, sequence);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _csDiagramEntryService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.commerce.shop.by.diagram.model.CSDiagramEntry
			updateCSDiagramEntry(
				long csDiagramEntryId, long cpInstanceId, long cProductId,
				boolean diagram, int quantity, String sequence, String sku,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _csDiagramEntryService.updateCSDiagramEntry(
			csDiagramEntryId, cpInstanceId, cProductId, diagram, quantity,
			sequence, sku, serviceContext);
	}

	@Override
	public CSDiagramEntryService getWrappedService() {
		return _csDiagramEntryService;
	}

	@Override
	public void setWrappedService(CSDiagramEntryService csDiagramEntryService) {
		_csDiagramEntryService = csDiagramEntryService;
	}

	private CSDiagramEntryService _csDiagramEntryService;

}