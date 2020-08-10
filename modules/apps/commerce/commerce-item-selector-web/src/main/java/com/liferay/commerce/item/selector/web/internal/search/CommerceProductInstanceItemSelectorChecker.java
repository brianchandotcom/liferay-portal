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

package com.liferay.commerce.item.selector.web.internal.search;

import com.liferay.commerce.price.list.model.CommercePriceEntry;
import com.liferay.commerce.price.list.model.CommercePriceList;
import com.liferay.commerce.price.list.service.CommercePriceEntryLocalService;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;

import javax.portlet.RenderResponse;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceProductInstanceItemSelectorChecker
	extends EmptyOnClickRowChecker {

	public CommerceProductInstanceItemSelectorChecker(
		RenderResponse renderResponse, CommercePriceList commercePriceList,
		CommercePriceEntryLocalService commercePriceEntryLocalService) {

		super(renderResponse);

		_commercePriceList = commercePriceList;
		_commercePriceEntryLocalService = commercePriceEntryLocalService;
	}

	@Override
	public boolean isChecked(Object obj) {
		if (_commercePriceList == null) {
			return false;
		}

		CPInstance cpInstance = (CPInstance)obj;

		CommercePriceEntry commercePriceEntry =
			_commercePriceEntryLocalService.fetchCommercePriceEntry(
				_commercePriceList.getCommercePriceListId(),
				cpInstance.getCPInstanceUuid());

		if (commercePriceEntry == null) {
			return false;
		}

		return true;
	}

	@Override
	public boolean isDisabled(Object obj) {
		return isChecked(obj);
	}

	private final CommercePriceEntryLocalService
		_commercePriceEntryLocalService;
	private final CommercePriceList _commercePriceList;

}