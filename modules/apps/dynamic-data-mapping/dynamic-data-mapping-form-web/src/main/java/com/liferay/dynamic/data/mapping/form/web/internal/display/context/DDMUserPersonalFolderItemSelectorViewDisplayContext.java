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

package com.liferay.dynamic.data.mapping.form.web.internal.display.context;

import com.liferay.dynamic.data.mapping.constants.DDMPortletKeys;
import com.liferay.dynamic.data.mapping.form.item.selector.criterion.DDMUserPersonalFolderItemSelectorCriterion;
import com.liferay.dynamic.data.mapping.form.web.internal.item.selector.DDMUserPersonalFolderItemSelectorView;
import com.liferay.item.selector.ItemSelectorReturnTypeResolver;
import com.liferay.item.selector.ItemSelectorReturnTypeResolverHandler;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.RepositoryEntry;

import java.util.Collections;
import java.util.List;
import java.util.Locale;

import javax.portlet.ActionRequest;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class DDMUserPersonalFolderItemSelectorViewDisplayContext {

	public DDMUserPersonalFolderItemSelectorViewDisplayContext(
		HttpServletRequest httpServletRequest, String itemSelectedEventName,
		ItemSelectorReturnTypeResolverHandler
			itemSelectorReturnTypeResolverHandler,
		DDMUserPersonalFolderItemSelectorCriterion
			ddmUserPersonalFolderItemSelectorCriterion,
		DDMUserPersonalFolderItemSelectorView
			ddmUserPersonalFolderItemSelectorView,
		PortletURL portletURL, boolean search) {

		_itemSelectedEventName = itemSelectedEventName;
		_itemSelectorReturnTypeResolverHandler =
			itemSelectorReturnTypeResolverHandler;
		_ddmUserPersonalFolderItemSelectorCriterion =
			ddmUserPersonalFolderItemSelectorCriterion;
		_ddmUserPersonalFolderItemSelectorView =
			ddmUserPersonalFolderItemSelectorView;
		_portletURL = portletURL;
		_search = search;
	}

	public String getItemSelectedEventName() {
		return _itemSelectedEventName;
	}

	public ItemSelectorReturnTypeResolver<?, ?>
		getItemSelectorReturnTypeResolver() {

		return _itemSelectorReturnTypeResolverHandler.
			getItemSelectorReturnTypeResolver(
				_ddmUserPersonalFolderItemSelectorCriterion,
				_ddmUserPersonalFolderItemSelectorView, FileEntry.class);
	}

	public List<RepositoryEntry> getPortletFileEntries() {
		return Collections.emptyList();
	}

	public int getPortletFileEntriesCount() {
		return 0;
	}

	public PortletURL getPortletURL() {
		return _portletURL;
	}

	public String getTitle(Locale locale) {
		return _ddmUserPersonalFolderItemSelectorView.getTitle(locale);
	}

	public PortletURL getUploadURL(
		LiferayPortletResponse liferayPortletResponse) {

		PortletURL portletURL = liferayPortletResponse.createActionURL(
			DDMPortletKeys.DYNAMIC_DATA_MAPPING_FORM);

		portletURL.setParameter(
			ActionRequest.ACTION_NAME,
			"/dynamic_data_mapping_form/upload_ddm_user_personal_folder");
		portletURL.setParameter(
			"folderId",
			String.valueOf(
				_ddmUserPersonalFolderItemSelectorCriterion.getFolderId()));

		return portletURL;
	}

	public boolean isSearch() {
		return _search;
	}

	private final DDMUserPersonalFolderItemSelectorCriterion
		_ddmUserPersonalFolderItemSelectorCriterion;
	private final DDMUserPersonalFolderItemSelectorView
		_ddmUserPersonalFolderItemSelectorView;
	private final String _itemSelectedEventName;
	private final ItemSelectorReturnTypeResolverHandler
		_itemSelectorReturnTypeResolverHandler;
	private final PortletURL _portletURL;
	private final boolean _search;

}