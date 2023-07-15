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

package com.liferay.user.groups.admin.item.selector.web.internal;

import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.item.selector.ItemSelectorViewDescriptor;
import com.liferay.item.selector.TableItemView;
import com.liferay.item.selector.criteria.UUIDItemSelectorReturnType;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.model.UserGroup;

/**
 * @author Eudaldo Alonso
 */
public class UserGroupSelectorViewDescriptor
	implements ItemSelectorViewDescriptor<UserGroup> {

	public UserGroupSelectorViewDescriptor(
		boolean multipleSelection, SearchContainer<UserGroup> searchContainer) {

		_multipleSelection = multipleSelection;
		_searchContainer = searchContainer;
	}

	@Override
	public ItemDescriptor getItemDescriptor(UserGroup userGroup) {
		return new UserGroupItemDescriptor(_multipleSelection, userGroup);
	}

	@Override
	public ItemSelectorReturnType getItemSelectorReturnType() {
		return new UUIDItemSelectorReturnType();
	}

	@Override
	public String[] getOrderByKeys() {
		return new String[] {"name", "description"};
	}

	public SearchContainer<UserGroup> getSearchContainer() {
		return _searchContainer;
	}

	@Override
	public TableItemView getTableItemView(UserGroup userGroup) {
		return new UserGroupTableItemView(userGroup);
	}

	@Override
	public boolean isMultipleSelection() {
		return _multipleSelection;
	}

	@Override
	public boolean isShowSearch() {
		return true;
	}

	private final boolean _multipleSelection;
	private final SearchContainer<UserGroup> _searchContainer;

}