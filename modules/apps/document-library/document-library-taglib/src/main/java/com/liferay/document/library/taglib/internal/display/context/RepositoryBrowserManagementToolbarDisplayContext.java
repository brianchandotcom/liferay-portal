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

package com.liferay.document.library.taglib.internal.display.context;

import com.liferay.frontend.taglib.clay.servlet.taglib.display.context.SearchContainerManagementToolbarDisplayContext;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenuBuilder;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemListBuilder;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Adolfo Pérez
 */
public class RepositoryBrowserManagementToolbarDisplayContext
	extends SearchContainerManagementToolbarDisplayContext {

	public RepositoryBrowserManagementToolbarDisplayContext(
		long folderId, HttpServletRequest httpServletRequest,
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse, long repositoryId,
		SearchContainer<Object> searchContainer) {

		super(
			httpServletRequest, liferayPortletRequest, liferayPortletResponse,
			searchContainer);

		_folderId = folderId;
		_repositoryId = repositoryId;
	}

	@Override
	public List<DropdownItem> getActionDropdownItems() {
		return DropdownItemListBuilder.add(
			dropdownItem -> {
				dropdownItem.putData("action", "deleteEntries");
				dropdownItem.setIcon("trash");
				dropdownItem.setLabel(
					LanguageUtil.get(httpServletRequest, "delete"));
				dropdownItem.setQuickAction(true);
			}
		).build();
	}

	@Override
	public String getClearResultsURL() {
		return PortletURLBuilder.create(
			getPortletURL()
		).setKeywords(
			StringPool.BLANK
		).buildString();
	}

	@Override
	public CreationMenu getCreationMenu() {
		return CreationMenuBuilder.addDropdownItem(
			dropdownItem -> {
				dropdownItem.putData("action", "uploadFile");
				dropdownItem.setIcon("upload");
				dropdownItem.setLabel(
					LanguageUtil.get(httpServletRequest, "file-upload"));
			}
		).addDropdownItem(
			dropdownItem -> {
				dropdownItem.putData("action", "addFolder");
				dropdownItem.putData(
					"parentFolderId", String.valueOf(_folderId));
				dropdownItem.putData(
					"repositoryId", String.valueOf(_repositoryId));
				dropdownItem.setIcon("folder");
				dropdownItem.setLabel(
					LanguageUtil.get(httpServletRequest, "folder"));
			}
		).build();
	}

	@Override
	public String[] getDisplayViews() {
		return new String[] {"icon"};
	}

	@Override
	public String getSearchActionURL() {
		return String.valueOf(getPortletURL());
	}

	@Override
	public String getSearchContainerId() {
		return "repositoryEntries";
	}

	@Override
	protected String getDefaultDisplayStyle() {
		return "icon";
	}

	private final long _folderId;
	private final long _repositoryId;

}