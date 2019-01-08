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

package com.liferay.layout.item.selector.criterion;

import com.liferay.item.selector.BaseItemSelectorCriterion;

/**
 * @author Sergio González
 */
public class LayoutItemSelectorCriterion extends BaseItemSelectorCriterion {

	public LayoutItemSelectorCriterion() {
		_showPrivatePages = true;
		_showPublicPages = true;
	}

	public boolean isCheckDisplayPage() {
		return _checkDisplayPage;
	}

	public boolean isEnableCurrentPage() {
		return _enableCurrentPage;
	}

	public boolean isFollowURLOnTitleClick() {
		return _followURLOnTitleClick;
	}

	public boolean isMultiSelection() {
		return _multiSelection;
	}

	public boolean isShowActionsMenu() {
		return _showActionsMenu;
	}

	public boolean isShowHiddenPages() {
		return _showHiddenPages;
	}

	public boolean isShowPrivatePages() {
		return _showPrivatePages;
	}

	public boolean isShowPublicPages() {
		return _showPublicPages;
	}

	public void setCheckDisplayPage(boolean checkDisplayPage) {
		_checkDisplayPage = checkDisplayPage;
	}

	public void setEnableCurrentPage(boolean enableCurrentPage) {
		_enableCurrentPage = enableCurrentPage;
	}

	public void setFollowURLOnTitleClick(boolean followURLOnTitleClick) {
		_followURLOnTitleClick = followURLOnTitleClick;
	}

	public void setMultiSelection(boolean multiSelection) {
		_multiSelection = multiSelection;
	}

	public void setShowActionsMenu(boolean showActionsMenu) {
		_showActionsMenu = showActionsMenu;
	}

	public void setShowHiddenPages(boolean showHiddenPages) {
		_showHiddenPages = showHiddenPages;
	}

	public void setShowPrivatePages(boolean showPrivatePages) {
		_showPrivatePages = showPrivatePages;
	}

	public void setShowPublicPages(boolean showPublicPages) {
		_showPublicPages = showPublicPages;
	}

	private boolean _checkDisplayPage;
	private boolean _enableCurrentPage;
	private boolean _followURLOnTitleClick;
	private boolean _multiSelection;
	private boolean _showActionsMenu;
	private boolean _showHiddenPages;
	private boolean _showPrivatePages;
	private boolean _showPublicPages;

}