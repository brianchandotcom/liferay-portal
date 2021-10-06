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

import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.util.DLUtil;
import com.liferay.dynamic.data.mapping.constants.DDMPortletKeys;
import com.liferay.dynamic.data.mapping.form.item.selector.criterion.DDMUserPersonalFolderItemSelectorCriterion;
import com.liferay.dynamic.data.mapping.form.web.internal.item.selector.DDMUserPersonalFolderItemSelectorView;
import com.liferay.item.selector.ItemSelectorReturnTypeResolver;
import com.liferay.item.selector.ItemSelectorReturnTypeResolverHandler;
import com.liferay.item.selector.taglib.servlet.taglib.util.RepositoryEntryBrowserTagUtil;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.dao.search.SearchPaginationUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.PortalPreferences;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepositoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.RepositoryEntry;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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

		_httpServletRequest = httpServletRequest;
		_itemSelectedEventName = itemSelectedEventName;
		_itemSelectorReturnTypeResolverHandler =
			itemSelectorReturnTypeResolverHandler;
		_ddmUserPersonalFolderItemSelectorCriterion =
			ddmUserPersonalFolderItemSelectorCriterion;
		_ddmUserPersonalFolderItemSelectorView =
			ddmUserPersonalFolderItemSelectorView;
		_portletURL = portletURL;
		_search = search;

		_portalPreferences = PortletPreferencesFactoryUtil.getPortalPreferences(
			_httpServletRequest);
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

	public List<RepositoryEntry> getPortletFileEntries()
		throws PortalException {

		long folderId =
			_ddmUserPersonalFolderItemSelectorCriterion.getFolderId();

		if (folderId == DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			return new ArrayList<>();
		}

		int cur = ParamUtil.getInteger(
			_httpServletRequest, SearchContainer.DEFAULT_CUR_PARAM,
			SearchContainer.DEFAULT_CUR);
		int delta = ParamUtil.getInteger(
			_httpServletRequest, SearchContainer.DEFAULT_DELTA_PARAM,
			SearchContainer.DEFAULT_DELTA);

		int[] startAndEnd = SearchPaginationUtil.calculateStartAndEnd(
			cur, delta);

		return new ArrayList<>(
			PortletFileRepositoryUtil.getPortletFileEntries(
				_ddmUserPersonalFolderItemSelectorCriterion.getGroupId(),
				folderId, WorkflowConstants.STATUS_APPROVED, startAndEnd[0],
				startAndEnd[1], _getOrderByComparator()));
	}

	public int getPortletFileEntriesCount() throws PortalException {
		long folderId =
			_ddmUserPersonalFolderItemSelectorCriterion.getFolderId();

		if (folderId == DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			return 0;
		}

		return PortletFileRepositoryUtil.getPortletFileEntriesCount(
			_ddmUserPersonalFolderItemSelectorCriterion.getGroupId(), folderId,
			WorkflowConstants.STATUS_APPROVED);
	}

	public PortletURL getPortletURL() {
		return _portletURL;
	}

	public String getTitle(Locale locale) {
		return _ddmUserPersonalFolderItemSelectorView.getTitle(locale);
	}

	public PortletURL getUploadURL(
		LiferayPortletResponse liferayPortletResponse) {

		return PortletURLBuilder.createActionURL(
			liferayPortletResponse, DDMPortletKeys.DYNAMIC_DATA_MAPPING_FORM
		).setActionName(
			"/dynamic_data_mapping_form/upload_ddm_user_personal_folder"
		).setParameter(
			"folderId",
			_ddmUserPersonalFolderItemSelectorCriterion.getFolderId()
		).setParameter(
			"repositoryId",
			_ddmUserPersonalFolderItemSelectorCriterion.getRepositoryId()
		).buildPortletURL();
	}

	public boolean isSearch() {
		return _search;
	}

	private OrderByComparator<FileEntry> _getOrderByComparator() {
		return DLUtil.getRepositoryModelOrderByComparator(
			RepositoryEntryBrowserTagUtil.getOrderByCol(
				_httpServletRequest, _portalPreferences),
			RepositoryEntryBrowserTagUtil.getOrderByType(
				_httpServletRequest, _portalPreferences));
	}

	private final DDMUserPersonalFolderItemSelectorCriterion
		_ddmUserPersonalFolderItemSelectorCriterion;
	private final DDMUserPersonalFolderItemSelectorView
		_ddmUserPersonalFolderItemSelectorView;
	private final HttpServletRequest _httpServletRequest;
	private final String _itemSelectedEventName;
	private final ItemSelectorReturnTypeResolverHandler
		_itemSelectorReturnTypeResolverHandler;
	private final PortalPreferences _portalPreferences;
	private final PortletURL _portletURL;
	private final boolean _search;

}