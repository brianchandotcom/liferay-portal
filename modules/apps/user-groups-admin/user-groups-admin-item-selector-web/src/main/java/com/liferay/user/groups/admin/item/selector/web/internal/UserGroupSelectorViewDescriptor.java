/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
	public boolean isShowBreadcrumb() {
		return false;
	}

	@Override
	public boolean isShowSearch() {
		return true;
	}

	private final boolean _multipleSelection;
	private final SearchContainer<UserGroup> _searchContainer;

}