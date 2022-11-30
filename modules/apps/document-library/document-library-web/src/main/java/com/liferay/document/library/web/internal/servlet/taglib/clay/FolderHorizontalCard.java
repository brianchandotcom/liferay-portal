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

package com.liferay.document.library.web.internal.servlet.taglib.clay;

import com.liferay.document.library.web.internal.display.context.FolderActionDisplayContext;
import com.liferay.document.library.web.internal.display.context.helper.DLPortletInstanceSettingsHelper;
import com.liferay.document.library.web.internal.helper.DLTrashHelper;
import com.liferay.frontend.taglib.clay.servlet.taglib.HorizontalCard;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.portal.kernel.dao.search.RowChecker;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.util.HtmlUtil;

import java.util.List;

import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class FolderHorizontalCard implements HorizontalCard {

	public FolderHorizontalCard(
		DLPortletInstanceSettingsHelper dlPortletInstanceSettingsHelper,
		DLTrashHelper dlTrashHelper, Folder folder,
		HttpServletRequest httpServletRequest, RenderResponse renderResponse,
		RowChecker rowChecker, String viewFolderURL) {

		_dlPortletInstanceSettingsHelper = dlPortletInstanceSettingsHelper;
		_folder = folder;
		_renderResponse = renderResponse;
		_rowChecker = rowChecker;
		_viewFolderURL = viewFolderURL;

		_folderActionDisplayContext = new FolderActionDisplayContext(
			dlTrashHelper, httpServletRequest);
	}

	@Override
	public List<DropdownItem> getActionDropdownItems() {
		if (!_dlPortletInstanceSettingsHelper.isShowActions() ||
			!_folderActionDisplayContext.isShowActions()) {

			return null;
		}

		return _folderActionDisplayContext.getActionDropdownItems();
	}

	@Override
	public String getHref() {
		return _viewFolderURL;
	}

	@Override
	public String getIcon() {
		if (_folder.isMountPoint()) {
			return "repository";
		}

		return "folder";
	}

	@Override
	public String getInputName() {
		if (_rowChecker == null) {
			return null;
		}

		return _renderResponse.getNamespace() + "rowIdsFolder";
	}

	@Override
	public String getInputValue() {
		if (_rowChecker == null) {
			return null;
		}

		return String.valueOf(_folder.getFolderId());
	}

	@Override
	public String getTitle() {
		return HtmlUtil.unescape(_folder.getName());
	}

	@Override
	public boolean isDisabled() {
		if (_rowChecker == null) {
			return false;
		}

		return _rowChecker.isDisabled(_folder);
	}

	@Override
	public boolean isSelectable() {
		if (_rowChecker == null) {
			return false;
		}

		return true;
	}

	@Override
	public boolean isSelected() {
		if (_rowChecker == null) {
			return false;
		}

		return _rowChecker.isChecked(_folder);
	}

	private final DLPortletInstanceSettingsHelper
		_dlPortletInstanceSettingsHelper;
	private final Folder _folder;
	private final FolderActionDisplayContext _folderActionDisplayContext;
	private final RenderResponse _renderResponse;
	private final RowChecker _rowChecker;
	private final String _viewFolderURL;

}