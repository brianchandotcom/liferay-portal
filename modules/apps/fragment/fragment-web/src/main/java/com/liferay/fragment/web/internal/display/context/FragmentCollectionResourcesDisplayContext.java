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

package com.liferay.fragment.web.internal.display.context;

import com.liferay.fragment.model.FragmentCollection;
import com.liferay.fragment.service.FragmentCollectionLocalServiceUtil;
import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepositoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.List;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class FragmentCollectionResourcesDisplayContext {

	public FragmentCollectionResourcesDisplayContext(
		RenderRequest renderRequest, RenderResponse renderResponse,
		HttpServletRequest httpServletRequest,
		FragmentDisplayContext fragmentDisplayContext) {

		_renderRequest = renderRequest;
		_renderResponse = renderResponse;
		_httpServletRequest = httpServletRequest;
		_fragmentDisplayContext = fragmentDisplayContext;

		_themeDisplay = (ThemeDisplay)_httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public SearchContainer getSearchContainer() throws PortalException {
		if (_searchContainer != null) {
			return _searchContainer;
		}

		PortletURL portletURL = _renderResponse.createRenderURL();

		portletURL.setParameter("mvcRenderCommandName", "/fragment/view");
		portletURL.setParameter("tabs1", "resources");
		portletURL.setParameter(
			"redirect", _fragmentDisplayContext.getRedirect());
		portletURL.setParameter(
			"fragmentCollectionId",
			String.valueOf(_fragmentDisplayContext.getFragmentCollectionId()));

		SearchContainer searchContainer = new SearchContainer(
			_renderRequest, portletURL, null, "there-are-no-resources");

		searchContainer.setRowChecker(
			new EmptyOnClickRowChecker(_renderResponse));

		int fileEntriesCount =
			PortletFileRepositoryUtil.getPortletFileEntriesCount(
				_themeDisplay.getScopeGroupId(), _getFolderId());

		searchContainer.setTotal(fileEntriesCount);

		List<FileEntry> fileEntries =
			PortletFileRepositoryUtil.getPortletFileEntries(
				_themeDisplay.getScopeGroupId(), _getFolderId(),
				WorkflowConstants.STATUS_ANY, searchContainer.getStart(),
				searchContainer.getEnd(),
				searchContainer.getOrderByComparator());

		searchContainer.setResults(fileEntries);

		_searchContainer = searchContainer;

		return _searchContainer;
	}

	private long _getFolderId() throws PortalException {
		if (_folderId != null) {
			return _folderId;
		}

		FragmentCollection fragmentCollection =
			FragmentCollectionLocalServiceUtil.fetchFragmentCollection(
				_fragmentDisplayContext.getFragmentCollectionId());

		_folderId = fragmentCollection.getResourcesFolderId();

		return _folderId;
	}

	private Long _folderId;
	private final FragmentDisplayContext _fragmentDisplayContext;
	private final HttpServletRequest _httpServletRequest;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;
	private SearchContainer _searchContainer;
	private final ThemeDisplay _themeDisplay;

}