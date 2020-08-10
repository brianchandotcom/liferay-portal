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
 * Provides a wrapper for {@link CommerceBOMFolderApplicationRelService}.
 *
 * @author Luca Pellizzon
 * @see CommerceBOMFolderApplicationRelService
 * @generated
 */
public class CommerceBOMFolderApplicationRelServiceWrapper
	implements CommerceBOMFolderApplicationRelService,
			   ServiceWrapper<CommerceBOMFolderApplicationRelService> {

	public CommerceBOMFolderApplicationRelServiceWrapper(
		CommerceBOMFolderApplicationRelService
			commerceBOMFolderApplicationRelService) {

		_commerceBOMFolderApplicationRelService =
			commerceBOMFolderApplicationRelService;
	}

	@Override
	public com.liferay.commerce.bom.model.CommerceBOMFolderApplicationRel
			addCommerceBOMFolderApplicationRel(
				long userId, long commerceBOMFolderId,
				long commerceApplicationModelId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceBOMFolderApplicationRelService.
			addCommerceBOMFolderApplicationRel(
				userId, commerceBOMFolderId, commerceApplicationModelId);
	}

	@Override
	public void deleteCommerceBOMFolderApplicationRel(
			long commerceBOMFolderApplicationRelId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_commerceBOMFolderApplicationRelService.
			deleteCommerceBOMFolderApplicationRel(
				commerceBOMFolderApplicationRelId);
	}

	@Override
	public java.util.List
		<com.liferay.commerce.bom.model.CommerceBOMFolderApplicationRel>
				getCommerceBOMFolderApplicationRelsByCAMId(
					long commerceApplicationModelId, int start, int end)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceBOMFolderApplicationRelService.
			getCommerceBOMFolderApplicationRelsByCAMId(
				commerceApplicationModelId, start, end);
	}

	@Override
	public java.util.List
		<com.liferay.commerce.bom.model.CommerceBOMFolderApplicationRel>
				getCommerceBOMFolderApplicationRelsByCommerceBOMFolderId(
					long commerceBOMFolderId, int start, int end)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceBOMFolderApplicationRelService.
			getCommerceBOMFolderApplicationRelsByCommerceBOMFolderId(
				commerceBOMFolderId, start, end);
	}

	@Override
	public int getCommerceBOMFolderApplicationRelsCountByCAMId(
			long commerceApplicationModelId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceBOMFolderApplicationRelService.
			getCommerceBOMFolderApplicationRelsCountByCAMId(
				commerceApplicationModelId);
	}

	@Override
	public int getCommerceBOMFolderApplicationRelsCountByCommerceBOMFolderId(
			long commerceBOMFolderId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceBOMFolderApplicationRelService.
			getCommerceBOMFolderApplicationRelsCountByCommerceBOMFolderId(
				commerceBOMFolderId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _commerceBOMFolderApplicationRelService.
			getOSGiServiceIdentifier();
	}

	@Override
	public CommerceBOMFolderApplicationRelService getWrappedService() {
		return _commerceBOMFolderApplicationRelService;
	}

	@Override
	public void setWrappedService(
		CommerceBOMFolderApplicationRelService
			commerceBOMFolderApplicationRelService) {

		_commerceBOMFolderApplicationRelService =
			commerceBOMFolderApplicationRelService;
	}

	private CommerceBOMFolderApplicationRelService
		_commerceBOMFolderApplicationRelService;

}