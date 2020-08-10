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

package com.liferay.commerce.application.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CommerceApplicationModelCProductRelService}.
 *
 * @author Luca Pellizzon
 * @see CommerceApplicationModelCProductRelService
 * @generated
 */
public class CommerceApplicationModelCProductRelServiceWrapper
	implements CommerceApplicationModelCProductRelService,
			   ServiceWrapper<CommerceApplicationModelCProductRelService> {

	public CommerceApplicationModelCProductRelServiceWrapper(
		CommerceApplicationModelCProductRelService
			commerceApplicationModelCProductRelService) {

		_commerceApplicationModelCProductRelService =
			commerceApplicationModelCProductRelService;
	}

	@Override
	public
		com.liferay.commerce.application.model.
			CommerceApplicationModelCProductRel
					addCommerceApplicationModelCProductRel(
						long userId, long commerceApplicationModelId,
						long cProductId)
				throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceApplicationModelCProductRelService.
			addCommerceApplicationModelCProductRel(
				userId, commerceApplicationModelId, cProductId);
	}

	@Override
	public void deleteCommerceApplicationModelCProductRel(
			long commerceApplicationModelCProductRelId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_commerceApplicationModelCProductRelService.
			deleteCommerceApplicationModelCProductRel(
				commerceApplicationModelCProductRelId);
	}

	@Override
	public java.util.List
		<com.liferay.commerce.application.model.
			CommerceApplicationModelCProductRel>
					getCommerceApplicationModelCProductRels(
						long commerceApplicationModelId, int start, int end)
				throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceApplicationModelCProductRelService.
			getCommerceApplicationModelCProductRels(
				commerceApplicationModelId, start, end);
	}

	@Override
	public int getCommerceApplicationModelCProductRelsCount(
			long commerceApplicationModelId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceApplicationModelCProductRelService.
			getCommerceApplicationModelCProductRelsCount(
				commerceApplicationModelId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _commerceApplicationModelCProductRelService.
			getOSGiServiceIdentifier();
	}

	@Override
	public CommerceApplicationModelCProductRelService getWrappedService() {
		return _commerceApplicationModelCProductRelService;
	}

	@Override
	public void setWrappedService(
		CommerceApplicationModelCProductRelService
			commerceApplicationModelCProductRelService) {

		_commerceApplicationModelCProductRelService =
			commerceApplicationModelCProductRelService;
	}

	private CommerceApplicationModelCProductRelService
		_commerceApplicationModelCProductRelService;

}