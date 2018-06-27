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

package com.liferay.commerce.test.util;

import com.liferay.commerce.context.CommerceContext;
import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.price.list.model.CommercePriceList;
import com.liferay.commerce.price.list.service.CommercePriceListLocalServiceUtil;
import com.liferay.commerce.product.model.CPRule;
import com.liferay.commerce.user.segment.service.CommerceUserSegmentEntryLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.User;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * @author Luca Pellizzon
 */
public class TestCommerceContext implements CommerceContext {

	public TestCommerceContext(
		CommerceCurrency commerceCurrency, User contextUser,
		Organization organization, CommerceOrder commerceOrder) {

		_commerceCurrency = commerceCurrency;
		_contextUser = contextUser;
		_organization = organization;
		_commerceOrder = commerceOrder;
	}

	@Override
	public CommerceCurrency getCommerceCurrency() {
		return _commerceCurrency;
	}

	@Override
	public CommerceOrder getCommerceOrder() {
		return _commerceOrder;
	}

	@Override
	public Optional<CommercePriceList> getCommercePriceList()
		throws PortalException {

		if (_organization == null) {
			return Optional.empty();
		}
		else {
			return CommercePriceListLocalServiceUtil.getCommercePriceList(
				_organization.getGroupId(), getCommerceUserSegmentEntryIds());
		}
	}

	@Override
	public long[] getCommerceUserSegmentEntryIds() throws PortalException {
		if ((_organization == null) || (_contextUser == null)) {
			return new long[0];
		}
		else {
			return CommerceUserSegmentEntryLocalServiceUtil.
				getCommerceUserSegmentEntryIds(
					_organization.getGroupId(),
					_organization.getOrganizationId(),
					_contextUser.getUserId());
		}
	}

	@Override
	public List<CPRule> getCPRules() {
		return Collections.emptyList();
	}

	@Override
	public String getCuponCode() {
		return null;
	}

	@Override
	public Organization getOrganization() {
		return _organization;
	}

	@Override
	public long getUserId() {
		return _contextUser.getUserId();
	}

	private final CommerceCurrency _commerceCurrency;
	private final CommerceOrder _commerceOrder;
	private final User _contextUser;
	private final Organization _organization;

}