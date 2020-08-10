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

import com.liferay.commerce.account.model.CommerceAccountGroup;
import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;
import com.liferay.portal.kernel.util.SetUtil;

import java.util.Set;

import javax.portlet.RenderResponse;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceAccountGroupItemSelectorChecker
	extends EmptyOnClickRowChecker {

	public CommerceAccountGroupItemSelectorChecker(
		RenderResponse renderResponse, long[] checkedCommerceAccountGroupIds) {

		super(renderResponse);

		_checkedCommerceAccountGroupIds = SetUtil.fromArray(
			checkedCommerceAccountGroupIds);
	}

	@Override
	public boolean isChecked(Object obj) {
		CommerceAccountGroup commerceAccountGroup = (CommerceAccountGroup)obj;

		return _checkedCommerceAccountGroupIds.contains(
			commerceAccountGroup.getCommerceAccountGroupId());
	}

	@Override
	public boolean isDisabled(Object obj) {
		return isChecked(obj);
	}

	private final Set<Long> _checkedCommerceAccountGroupIds;

}