/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.portal.search.tuning.web.internal.display.context;

import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.List;

/**
 * @author Filipe Oshiro
 */
public class SynonymsDisplayContext {

	public List<DropdownItem> getActionDropdownMultipleItems() {
		return _dropdownItems;
	}

	public CreationMenu getCreationMenu() {
		return _creationMenu;
	}

	public SearchContainer<SynonymSetDisplayContext> getSearchContainer() {
		return _searchContainer;
	}

	public boolean isDisabledManagementBar() throws PortalException {
		return _disabledManagementBar;
	}

	public void setCreationMenu(CreationMenu creationMenu) {
		_creationMenu = creationMenu;
	}

	public void setDisabledManagementBar(boolean disabledManagementBar) {
		_disabledManagementBar = disabledManagementBar;
	}

	public void setDropdownItems(List<DropdownItem> dropdownItems) {
		_dropdownItems = dropdownItems;
	}

	public void setSearchContainer(
		SearchContainer<SynonymSetDisplayContext> searchContainer) {

		_searchContainer = searchContainer;
	}

	private CreationMenu _creationMenu;
	private boolean _disabledManagementBar;
	private List<DropdownItem> _dropdownItems;
	private SearchContainer<SynonymSetDisplayContext> _searchContainer;

}