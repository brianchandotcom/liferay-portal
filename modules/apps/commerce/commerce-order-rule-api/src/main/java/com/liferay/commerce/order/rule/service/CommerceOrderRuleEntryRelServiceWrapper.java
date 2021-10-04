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

package com.liferay.commerce.order.rule.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CommerceOrderRuleEntryRelService}.
 *
 * @author Luca Pellizzon
 * @see CommerceOrderRuleEntryRelService
 * @generated
 */
public class CommerceOrderRuleEntryRelServiceWrapper
	implements CommerceOrderRuleEntryRelService,
			   ServiceWrapper<CommerceOrderRuleEntryRelService> {

	public CommerceOrderRuleEntryRelServiceWrapper(
		CommerceOrderRuleEntryRelService commerceOrderRuleEntryRelService) {

		_commerceOrderRuleEntryRelService = commerceOrderRuleEntryRelService;
	}

	@Override
	public com.liferay.commerce.order.rule.model.CommerceOrderRuleEntryRel
			addCommerceOrderRuleEntryRel(
				String className, long classPK, long commerceOrderRuleEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceOrderRuleEntryRelService.addCommerceOrderRuleEntryRel(
			className, classPK, commerceOrderRuleEntryId);
	}

	@Override
	public void deleteCommerceOrderRuleEntryRel(
			long commerceOrderRuleEntryRelId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_commerceOrderRuleEntryRelService.deleteCommerceOrderRuleEntryRel(
			commerceOrderRuleEntryRelId);
	}

	@Override
	public void deleteCommerceOrderRuleEntryRelsByCommerceOrderRuleEntryId(
			long commerceOrderRuleEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_commerceOrderRuleEntryRelService.
			deleteCommerceOrderRuleEntryRelsByCommerceOrderRuleEntryId(
				commerceOrderRuleEntryId);
	}

	@Override
	public com.liferay.commerce.order.rule.model.CommerceOrderRuleEntryRel
			fetchCommerceOrderRuleEntryRel(
				String className, long classPK, long commerceOrderRuleEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceOrderRuleEntryRelService.fetchCommerceOrderRuleEntryRel(
			className, classPK, commerceOrderRuleEntryId);
	}

	@Override
	public java.util.List
		<com.liferay.commerce.order.rule.model.CommerceOrderRuleEntryRel>
				getCommerceOrderRuleEntryAccountEntryRels(
					long commerceOrderRuleEntryId, String keywords, int start,
					int end)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceOrderRuleEntryRelService.
			getCommerceOrderRuleEntryAccountEntryRels(
				commerceOrderRuleEntryId, keywords, start, end);
	}

	@Override
	public int getCommerceOrderRuleEntryAccountEntryRelsCount(
			long commerceOrderRuleEntryId, String keywords)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceOrderRuleEntryRelService.
			getCommerceOrderRuleEntryAccountEntryRelsCount(
				commerceOrderRuleEntryId, keywords);
	}

	@Override
	public java.util.List
		<com.liferay.commerce.order.rule.model.CommerceOrderRuleEntryRel>
				getCommerceOrderRuleEntryAccountGroupRels(
					long commerceOrderRuleEntryId, String keywords, int start,
					int end)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceOrderRuleEntryRelService.
			getCommerceOrderRuleEntryAccountGroupRels(
				commerceOrderRuleEntryId, keywords, start, end);
	}

	@Override
	public int getCommerceOrderRuleEntryAccountGroupRelsCount(
			long commerceOrderRuleEntryId, String keywords)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceOrderRuleEntryRelService.
			getCommerceOrderRuleEntryAccountGroupRelsCount(
				commerceOrderRuleEntryId, keywords);
	}

	@Override
	public java.util.List
		<com.liferay.commerce.order.rule.model.CommerceOrderRuleEntryRel>
				getCommerceOrderRuleEntryCommerceChannelRels(
					long commerceOrderRuleEntryId, String keywords, int start,
					int end)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceOrderRuleEntryRelService.
			getCommerceOrderRuleEntryCommerceChannelRels(
				commerceOrderRuleEntryId, keywords, start, end);
	}

	@Override
	public int getCommerceOrderRuleEntryCommerceChannelRelsCount(
			long commerceOrderRuleEntryId, String keywords)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceOrderRuleEntryRelService.
			getCommerceOrderRuleEntryCommerceChannelRelsCount(
				commerceOrderRuleEntryId, keywords);
	}

	@Override
	public java.util.List
		<com.liferay.commerce.order.rule.model.CommerceOrderRuleEntryRel>
				getCommerceOrderRuleEntryCommerceOrderTypeRels(
					long commerceOrderRuleEntryId, String keywords, int start,
					int end)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceOrderRuleEntryRelService.
			getCommerceOrderRuleEntryCommerceOrderTypeRels(
				commerceOrderRuleEntryId, keywords, start, end);
	}

	@Override
	public int getCommerceOrderRuleEntryCommerceOrderTypeRelsCount(
			long commerceOrderRuleEntryId, String keywords)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceOrderRuleEntryRelService.
			getCommerceOrderRuleEntryCommerceOrderTypeRelsCount(
				commerceOrderRuleEntryId, keywords);
	}

	@Override
	public com.liferay.commerce.order.rule.model.CommerceOrderRuleEntryRel
			getCommerceOrderRuleEntryRel(long commerceOrderRuleEntryRelId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceOrderRuleEntryRelService.getCommerceOrderRuleEntryRel(
			commerceOrderRuleEntryRelId);
	}

	@Override
	public java.util.List
		<com.liferay.commerce.order.rule.model.CommerceOrderRuleEntryRel>
				getCommerceOrderRuleEntryRels(long commerceOrderRuleEntryId)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceOrderRuleEntryRelService.getCommerceOrderRuleEntryRels(
			commerceOrderRuleEntryId);
	}

	@Override
	public java.util.List
		<com.liferay.commerce.order.rule.model.CommerceOrderRuleEntryRel>
				getCommerceOrderRuleEntryRels(
					long commerceOrderRuleEntryId, int start, int end,
					com.liferay.portal.kernel.util.OrderByComparator
						<com.liferay.commerce.order.rule.model.
							CommerceOrderRuleEntryRel> orderByComparator)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceOrderRuleEntryRelService.getCommerceOrderRuleEntryRels(
			commerceOrderRuleEntryId, start, end, orderByComparator);
	}

	@Override
	public int getCommerceOrderRuleEntryRelsCount(long commerceOrderRuleEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceOrderRuleEntryRelService.
			getCommerceOrderRuleEntryRelsCount(commerceOrderRuleEntryId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _commerceOrderRuleEntryRelService.getOSGiServiceIdentifier();
	}

	@Override
	public CommerceOrderRuleEntryRelService getWrappedService() {
		return _commerceOrderRuleEntryRelService;
	}

	@Override
	public void setWrappedService(
		CommerceOrderRuleEntryRelService commerceOrderRuleEntryRelService) {

		_commerceOrderRuleEntryRelService = commerceOrderRuleEntryRelService;
	}

	private CommerceOrderRuleEntryRelService _commerceOrderRuleEntryRelService;

}