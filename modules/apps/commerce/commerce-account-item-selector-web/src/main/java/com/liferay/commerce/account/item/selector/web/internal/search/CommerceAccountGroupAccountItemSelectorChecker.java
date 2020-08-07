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

package com.liferay.commerce.account.item.selector.web.internal.search;

import com.liferay.commerce.account.model.CommerceAccount;
import com.liferay.commerce.account.model.CommerceAccountGroup;
import com.liferay.commerce.account.model.CommerceAccountGroupCommerceAccountRel;
import com.liferay.commerce.account.service.CommerceAccountGroupCommerceAccountRelLocalService;
import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;

import javax.portlet.RenderResponse;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceAccountGroupAccountItemSelectorChecker
	extends EmptyOnClickRowChecker {

	public CommerceAccountGroupAccountItemSelectorChecker(
		RenderResponse renderResponse,
		CommerceAccountGroup commerceAccountGroup,
		CommerceAccountGroupCommerceAccountRelLocalService
			commerceAccountGroupCommerceAccountRelLocalService) {

		super(renderResponse);

		_commerceAccountGroup = commerceAccountGroup;
		_commerceAccountGroupCommerceAccountRelLocalService =
			commerceAccountGroupCommerceAccountRelLocalService;
	}

	@Override
	public boolean isChecked(Object object) {
		if (_commerceAccountGroup == null) {
			return false;
		}

		CommerceAccount commerceAccount = (CommerceAccount)object;

		CommerceAccountGroupCommerceAccountRel
			commerceAccountGroupCommerceAccountRel =
				_commerceAccountGroupCommerceAccountRelLocalService.
					fetchCommerceAccountGroupCommerceAccountRel(
						_commerceAccountGroup.getCommerceAccountGroupId(),
						commerceAccount.getCommerceAccountId());

		if (commerceAccountGroupCommerceAccountRel == null) {
			return false;
		}

		return true;
	}

	@Override
	public boolean isDisabled(Object object) {
		return isChecked(object);
	}

	private final CommerceAccountGroup _commerceAccountGroup;
	private final CommerceAccountGroupCommerceAccountRelLocalService
		_commerceAccountGroupCommerceAccountRelLocalService;

}