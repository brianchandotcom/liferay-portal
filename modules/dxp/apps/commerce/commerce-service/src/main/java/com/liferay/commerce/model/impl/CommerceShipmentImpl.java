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

package com.liferay.commerce.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.model.CommerceAddress;
import com.liferay.commerce.model.CommerceShippingMethod;
import com.liferay.commerce.service.CommerceAddressLocalServiceUtil;
import com.liferay.commerce.service.CommerceShippingMethodLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.RoleConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.OrganizationLocalServiceUtil;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;

/**
 * @author Alessio Antonio Rendina
 */
@ProviderType
public class CommerceShipmentImpl extends CommerceShipmentBaseImpl {

	public CommerceShipmentImpl() {
	}

	@Override
	public CommerceAddress fetchCommerceAddress() {
		return CommerceAddressLocalServiceUtil.fetchCommerceAddress(
			getCommerceAddressId());
	}

	@Override
	public CommerceShippingMethod fetchCommerceShippingMethod() {
		return
			CommerceShippingMethodLocalServiceUtil.fetchCommerceShippingMethod(
				getCommerceShippingMethodId());
	}

	@Override
	public CommerceShippingMethod getCommerceShippingMethod()
		throws PortalException {

		long commerceShippingMethodId = getCommerceShippingMethodId();

		if (commerceShippingMethodId > 0) {
			return CommerceShippingMethodLocalServiceUtil.
				getCommerceShippingMethod(commerceShippingMethodId);
		}

		return null;
	}

	@Override
	public long getCustomerId() {
		long customerId = getShipmentOrganizationId();

		if (customerId <= 0) {
			customerId = getShipmentUserId();
		}

		return customerId;
	}

	@Override
	public String getCustomerName() throws PortalException {
		Organization organization = getShipmentOrganization();

		if (organization != null) {
			return organization.getName();
		}

		User orderUser = getShipmentUser();

		if (orderUser.isDefaultUser()) {
			return RoleConstants.GUEST;
		}

		return orderUser.getFullName();
	}

	@Override
	public Organization getShipmentOrganization() throws PortalException {
		long orderOrganizationId = getShipmentOrganizationId();

		if (orderOrganizationId <= 0) {
			return null;
		}

		return OrganizationLocalServiceUtil.getOrganization(
			orderOrganizationId);
	}

	@Override
	public User getShipmentUser() throws PortalException {
		long orderUserId = getShipmentUserId();

		if (orderUserId <= 0) {
			return null;
		}

		return UserLocalServiceUtil.getUser(orderUserId);
	}

}