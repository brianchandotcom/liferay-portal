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