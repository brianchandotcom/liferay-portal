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

package com.liferay.commerce.bom.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CommerceBOMDefinitionService}.
 *
 * @author Luca Pellizzon
 * @see CommerceBOMDefinitionService
 * @generated
 */
public class CommerceBOMDefinitionServiceWrapper
	implements CommerceBOMDefinitionService,
			   ServiceWrapper<CommerceBOMDefinitionService> {

	public CommerceBOMDefinitionServiceWrapper(
		CommerceBOMDefinitionService commerceBOMDefinitionService) {

		_commerceBOMDefinitionService = commerceBOMDefinitionService;
	}

	@Override
	public com.liferay.commerce.bom.model.CommerceBOMDefinition
			addCommerceBOMDefinition(
				long userId, long commerceBOMFolderId,
				long cpAttachmentFileEntryId, String name, String friendlyUrl)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceBOMDefinitionService.addCommerceBOMDefinition(
			userId, commerceBOMFolderId, cpAttachmentFileEntryId, name,
			friendlyUrl);
	}

	@Override
	public void deleteCommerceBOMDefinition(long commerceBOMDefinitionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_commerceBOMDefinitionService.deleteCommerceBOMDefinition(
			commerceBOMDefinitionId);
	}

	@Override
	public com.liferay.commerce.bom.model.CommerceBOMDefinition
			getCommerceBOMDefinition(long commerceBOMDefinitionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceBOMDefinitionService.getCommerceBOMDefinition(
			commerceBOMDefinitionId);
	}

	@Override
	public java.util.List<com.liferay.commerce.bom.model.CommerceBOMDefinition>
		getCommerceBOMDefinitions(
			long commerceBOMFolderId, int start, int end) {

		return _commerceBOMDefinitionService.getCommerceBOMDefinitions(
			commerceBOMFolderId, start, end);
	}

	@Override
	public int getCommerceBOMDefinitionsCount(long commerceBOMFolderId) {
		return _commerceBOMDefinitionService.getCommerceBOMDefinitionsCount(
			commerceBOMFolderId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _commerceBOMDefinitionService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.commerce.bom.model.CommerceBOMDefinition
			updateCommerceBOMDefinition(
				long commerceBOMDefinitionId, long cpAttachmentFileEntryId,
				String name)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceBOMDefinitionService.updateCommerceBOMDefinition(
			commerceBOMDefinitionId, cpAttachmentFileEntryId, name);
	}

	@Override
	public CommerceBOMDefinitionService getWrappedService() {
		return _commerceBOMDefinitionService;
	}

	@Override
	public void setWrappedService(
		CommerceBOMDefinitionService commerceBOMDefinitionService) {

		_commerceBOMDefinitionService = commerceBOMDefinitionService;
	}

	private CommerceBOMDefinitionService _commerceBOMDefinitionService;

}