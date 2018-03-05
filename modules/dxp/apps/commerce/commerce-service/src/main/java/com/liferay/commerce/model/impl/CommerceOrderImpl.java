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

import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.service.CommerceCurrencyLocalServiceUtil;
import com.liferay.commerce.model.CommerceAddress;
import com.liferay.commerce.model.CommerceOrderConstants;
import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.commerce.model.CommercePaymentMethod;
import com.liferay.commerce.model.CommerceShippingMethod;
import com.liferay.commerce.organization.service.CommerceOrganizationLocalServiceUtil;
import com.liferay.commerce.service.CommerceAddressLocalServiceUtil;
import com.liferay.commerce.service.CommerceOrderItemLocalServiceUtil;
import com.liferay.commerce.service.CommercePaymentMethodLocalServiceUtil;
import com.liferay.commerce.service.CommerceShippingMethodLocalServiceUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;

import java.util.List;

/**
 * @author Alessio Antonio Rendina
 * @author Andrea Di Giorgi
 */
@ProviderType
public class CommerceOrderImpl extends CommerceOrderBaseImpl {

	public CommerceOrderImpl() {
	}

	@Override
	public CommerceAddress getBillingAddress() throws PortalException {
		long billingAddressId = getBillingAddressId();

		if (billingAddressId > 0) {
			return CommerceAddressLocalServiceUtil.getCommerceAddress(
				getBillingAddressId());
		}

		return null;
	}

	@Override
	public String getClassName() throws PortalException {
		Group group = GroupLocalServiceUtil.getGroup(getGroupId());

		if (group.isOrganization() &&
			CommerceOrganizationLocalServiceUtil.isB2BOrganization(
				group.getOrganizationId())) {

			return Organization.class.getName();
		}

		return User.class.getName();
	}

	@Override
	public long getClassPK() throws PortalException {
		Group group = GroupLocalServiceUtil.getGroup(getGroupId());

		if (group.isOrganization() &&
			CommerceOrganizationLocalServiceUtil.isB2BOrganization(
				group.getOrganizationId())) {

			return group.getOrganizationId();
		}

		return getOrderUserId();
	}

	@Override
	public CommerceCurrency getCommerceCurrency() throws PortalException {
		return CommerceCurrencyLocalServiceUtil.getCommerceCurrency(
			getCommerceCurrencyId());
	}

	@Override
	public List<CommerceOrderItem> getCommerceOrderItems() {
		return CommerceOrderItemLocalServiceUtil.getCommerceOrderItems(
			getCommerceOrderId(), QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	@Override
	public CommercePaymentMethod getCommercePaymentMethod()
		throws PortalException {

		long commercePaymentMethodId = getCommercePaymentMethodId();

		if (commercePaymentMethodId > 0) {
			return
				CommercePaymentMethodLocalServiceUtil.getCommercePaymentMethod(
					commercePaymentMethodId);
		}

		return null;
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
	public User getOrderUser() throws PortalException {
		long orderUserId = getOrderUserId();

		if (orderUserId <= 0) {
			return null;
		}

		return UserLocalServiceUtil.getUser(orderUserId);
	}

	@Override
	public CommerceAddress getShippingAddress() throws PortalException {
		long shippingAddressId = getShippingAddressId();

		if (shippingAddressId > 0) {
			return CommerceAddressLocalServiceUtil.getCommerceAddress(
				getShippingAddressId());
		}

		return null;
	}

	@Override
	public boolean isB2B() throws PortalException {
		String className = getClassName();

		if (className.equals(Organization.class.getName())) {
			return true;
		}

		return false;
	}

	@Override
	public boolean isGuestOrder() throws PortalException {
		User orderUser = getOrderUser();

		if ((orderUser != null) && orderUser.isDefaultUser()) {
			return true;
		}

		return false;
	}

	@Override
	public boolean isOpen() {
		if (getOrderStatus() == CommerceOrderConstants.ORDER_STATUS_OPEN) {
			return true;
		}

		return false;
	}

}