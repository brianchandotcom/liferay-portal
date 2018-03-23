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

package com.liferay.commerce.vat.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CommerceVatNumberService}.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceVatNumberService
 * @generated
 */
@ProviderType
public class CommerceVatNumberServiceWrapper implements CommerceVatNumberService,
	ServiceWrapper<CommerceVatNumberService> {
	public CommerceVatNumberServiceWrapper(
		CommerceVatNumberService commerceVatNumberService) {
		_commerceVatNumberService = commerceVatNumberService;
	}

	@Override
	public com.liferay.commerce.vat.model.CommerceVatNumber addCommerceVatNumber(
		java.lang.String className, long classPK, java.lang.String value,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceVatNumberService.addCommerceVatNumber(className,
			classPK, value, serviceContext);
	}

	@Override
	public void deleteCommerceVatNumber(long commerceVatNumberId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_commerceVatNumberService.deleteCommerceVatNumber(commerceVatNumberId);
	}

	@Override
	public com.liferay.commerce.vat.model.CommerceVatNumber fetchCommerceVatNumber(
		long commerceVatNumberId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceVatNumberService.fetchCommerceVatNumber(commerceVatNumberId);
	}

	@Override
	public com.liferay.commerce.vat.model.CommerceVatNumber fetchCommerceVatNumber(
		long groupId, java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceVatNumberService.fetchCommerceVatNumber(groupId,
			className, classPK);
	}

	@Override
	public java.util.List<com.liferay.commerce.vat.model.CommerceVatNumber> getCommerceVatNumbers(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.vat.model.CommerceVatNumber> orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceVatNumberService.getCommerceVatNumbers(groupId, start,
			end, orderByComparator);
	}

	@Override
	public int getCommerceVatNumbersCount(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceVatNumberService.getCommerceVatNumbersCount(groupId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _commerceVatNumberService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.search.BaseModelSearchResult<com.liferay.commerce.vat.model.CommerceVatNumber> searchCommerceVatNumbers(
		long companyId, long groupId, java.lang.String keywords, int start,
		int end, com.liferay.portal.kernel.search.Sort sort)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceVatNumberService.searchCommerceVatNumbers(companyId,
			groupId, keywords, start, end, sort);
	}

	@Override
	public com.liferay.commerce.vat.model.CommerceVatNumber updateCommerceVatNumber(
		long commerceVatNumberId, java.lang.String value)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceVatNumberService.updateCommerceVatNumber(commerceVatNumberId,
			value);
	}

	@Override
	public CommerceVatNumberService getWrappedService() {
		return _commerceVatNumberService;
	}

	@Override
	public void setWrappedService(
		CommerceVatNumberService commerceVatNumberService) {
		_commerceVatNumberService = commerceVatNumberService;
	}

	private CommerceVatNumberService _commerceVatNumberService;
}