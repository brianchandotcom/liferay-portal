/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CommerceOrderTypeRelService}.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceOrderTypeRelService
 * @generated
 */
public class CommerceOrderTypeRelServiceWrapper
	implements CommerceOrderTypeRelService,
			   ServiceWrapper<CommerceOrderTypeRelService> {

	public CommerceOrderTypeRelServiceWrapper() {
		this(null);
	}

	public CommerceOrderTypeRelServiceWrapper(
		CommerceOrderTypeRelService commerceOrderTypeRelService) {

		_commerceOrderTypeRelService = commerceOrderTypeRelService;
	}

	@Override
	public com.liferay.commerce.model.CommerceOrderTypeRel
			addCommerceOrderTypeRel(
				String className, long classPK, long commerceOrderTypeId,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceOrderTypeRelService.addCommerceOrderTypeRel(
			className, classPK, commerceOrderTypeId, serviceContext);
	}

	@Override
	public com.liferay.commerce.model.CommerceOrderTypeRel
			deleteCommerceOrderTypeRel(long commerceOrderTypeRelId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceOrderTypeRelService.deleteCommerceOrderTypeRel(
			commerceOrderTypeRelId);
	}

	@Override
	public void deleteCommerceOrderTypeRels(
			String className, long commerceOrderTypeId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_commerceOrderTypeRelService.deleteCommerceOrderTypeRels(
			className, commerceOrderTypeId);
	}

	@Override
	public java.util.List<com.liferay.commerce.model.CommerceOrderTypeRel>
			getCommerceOrderTypeCommerceChannelRels(
				long commerceOrderTypeId, String keywords, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceOrderTypeRelService.
			getCommerceOrderTypeCommerceChannelRels(
				commerceOrderTypeId, keywords, start, end);
	}

	@Override
	public int getCommerceOrderTypeCommerceChannelRelsCount(
			long commerceOrderTypeId, String keywords)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceOrderTypeRelService.
			getCommerceOrderTypeCommerceChannelRelsCount(
				commerceOrderTypeId, keywords);
	}

	@Override
	public com.liferay.commerce.model.CommerceOrderTypeRel
			getCommerceOrderTypeRel(long commerceOrderTypeRelId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceOrderTypeRelService.getCommerceOrderTypeRel(
			commerceOrderTypeRelId);
	}

	@Override
	public java.util.List<com.liferay.commerce.model.CommerceOrderTypeRel>
			getCommerceOrderTypeRels(
				String className, long classPK, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.commerce.model.CommerceOrderTypeRel>
						orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceOrderTypeRelService.getCommerceOrderTypeRels(
			className, classPK, start, end, orderByComparator);
	}

	@Override
	public int getCommerceOrderTypeRelsCount(String className, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceOrderTypeRelService.getCommerceOrderTypeRelsCount(
			className, classPK);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _commerceOrderTypeRelService.getOSGiServiceIdentifier();
	}

	@Override
	public CommerceOrderTypeRelService getWrappedService() {
		return _commerceOrderTypeRelService;
	}

	@Override
	public void setWrappedService(
		CommerceOrderTypeRelService commerceOrderTypeRelService) {

		_commerceOrderTypeRelService = commerceOrderTypeRelService;
	}

	private CommerceOrderTypeRelService _commerceOrderTypeRelService;

}