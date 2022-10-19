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

package com.liferay.asset.publisher.web.internal.item.selector;

import com.liferay.asset.publisher.web.internal.display.context.ItemSelectorViewDisplayContext;
import com.liferay.info.list.provider.item.selector.criterion.InfoListProviderItemSelectorReturnType;
import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.item.selector.ItemSelectorViewDescriptor;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class SitesItemSelectorViewDescriptor
	implements ItemSelectorViewDescriptor<Group> {

	public SitesItemSelectorViewDescriptor(
		HttpServletRequest httpServletRequest,
		ItemSelectorViewDisplayContext itemSelectorViewDisplayContext) {

		_httpServletRequest = httpServletRequest;
		_itemSelectorViewDisplayContext = itemSelectorViewDisplayContext;
	}

	@Override
	public ItemDescriptor getItemDescriptor(Group group) {
		return new SitesItemDescriptor(group, _httpServletRequest);
	}

	@Override
	public ItemSelectorReturnType getItemSelectorReturnType() {
		return new InfoListProviderItemSelectorReturnType();
	}

	@Override
	public String[] getOrderByKeys() {
		return new String[] {"name", "type"};
	}

	public SearchContainer<Group> getSearchContainer() {
		try {
			return _itemSelectorViewDisplayContext.getGroupSearch();
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}
		}

		return null;
	}

	@Override
	public boolean isShowSearch() {
		return _itemSelectorViewDisplayContext.isShowSearch();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SitesItemSelectorViewDescriptor.class);

	private final HttpServletRequest _httpServletRequest;
	private final ItemSelectorViewDisplayContext
		_itemSelectorViewDisplayContext;

}