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
 * @author Alessio Antonio Rendina
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
				long userId, long cpDefinitionDiagramSettingsId,
				String cpInstanceUuid, long cProductId, int number,
				int quantity, double positionX, double positionY, double radius,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cpDefinitionDiagramEntryService.addCPDefinitionDiagramEntry(
			userId, cpDefinitionDiagramSettingsId, cpInstanceUuid, cProductId,
			number, quantity, positionX, positionY, radius, serviceContext);
	}

	@Override
	public void deleteCPDefinitionDiagramEntry(long cpDefinitionDiagramEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_cpDefinitionDiagramEntryService.deleteCPDefinitionDiagramEntry(
			cpDefinitionDiagramEntryId);
	}

	@Override
	public java.util.List
		<com.liferay.commerce.shop.by.diagram.model.CPDefinitionDiagramEntry>
				getCPDefinitionDiagramEntries(
					long cpDefinitionDiagramSettingsId, int start, int end)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _cpDefinitionDiagramEntryService.getCPDefinitionDiagramEntries(
			cpDefinitionDiagramSettingsId, start, end);
	}

	@Override
	public int getCPDefinitionDiagramEntriesCount(
			long cpDefinitionDiagramSettingsId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cpDefinitionDiagramEntryService.
			getCPDefinitionDiagramEntriesCount(cpDefinitionDiagramSettingsId);
	}

	@Override
	public com.liferay.commerce.shop.by.diagram.model.CPDefinitionDiagramEntry
			getCPDefinitionDiagramEntry(long cpDefinitionDiagramEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cpDefinitionDiagramEntryService.getCPDefinitionDiagramEntry(
			cpDefinitionDiagramEntryId);
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
				long cProductId, int number, int quantity, double positionX,
				double positionY, double radius,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cpDefinitionDiagramEntryService.updateCPDefinitionDiagramEntry(
			cpDefinitionDiagramEntryId, cpInstanceUuid, cProductId, number,
			quantity, positionX, positionY, radius, serviceContext);
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