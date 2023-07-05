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

package com.liferay.document.library.web.internal.display.context;

import com.liferay.document.library.kernel.model.DLFolder;
import com.liferay.document.library.kernel.service.DLFolderLocalServiceUtil;
import com.liferay.item.selector.ItemSelector;
import com.liferay.item.selector.criteria.FolderItemSelectorReturnType;
import com.liferay.item.selector.criteria.folder.criterion.FolderItemSelectorCriterion;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Repository;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactoryUtil;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.RepositoryLocalServiceUtil;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortalUtil;

import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Marco Galluzzi
 */
public class DLCopyFolderDisplayContext {

	public DLCopyFolderDisplayContext(
		HttpServletRequest httpServletRequest,
		LiferayPortletResponse liferayPortletResponse,
		ThemeDisplay themeDisplay) {

		_httpServletRequest = httpServletRequest;
		_liferayPortletResponse = liferayPortletResponse;
		_themeDisplay = themeDisplay;

		_copyDLObjectURL = null;
	}

	public String getCopyDLObjectURL() {
		if (_copyDLObjectURL == null) {
			_copyDLObjectURL = StringBundler.concat(
				PortalUtil.getPortalURL(_httpServletRequest),
				PortalUtil.getPathContext(), Portal.PATH_MODULE,
				"/copy_dl_object");
		}

		return _copyDLObjectURL;
	}

	public String getRedirect() {
		if (_redirect != null) {
			return _redirect;
		}

		_redirect = ParamUtil.getString(_httpServletRequest, "redirect");

		return _redirect;
	}

	public String getSelectFolderURL() throws PortalException {
		ItemSelector itemSelector =
			(ItemSelector)_httpServletRequest.getAttribute(
				ItemSelector.class.getName());

		return String.valueOf(
			itemSelector.getItemSelectorURL(
				RequestBackedPortletURLFactoryUtil.create(_httpServletRequest),
				_getGroup(getSourceRepositoryId()),
				_themeDisplay.getScopeGroupId(), _getItemSelectedEventName(),
				_getFolderItemSelectorCriterion(
					getSourceFolderId(), getSourceRepositoryId())));
	}

	public long getSourceFolderId() {
		if (_sourceFolderId < 0) {
			_sourceFolderId = ParamUtil.getLong(
				_httpServletRequest, "sourceFolderId");
		}

		return _sourceFolderId;
	}

	public String getSourceFolderName() {
		if (_sourceFolderName != null) {
			return _sourceFolderName;
		}

		_sourceFolderName = LanguageUtil.get(_httpServletRequest, "home");

		DLFolder folder = DLFolderLocalServiceUtil.fetchFolder(
			getSourceFolderId());

		if (folder != null) {
			_sourceFolderName = folder.getName();
		}

		return _sourceFolderName;
	}

	public long getSourceRepositoryId() {
		if (_sourceRepositoryId < 0) {
			_sourceRepositoryId = ParamUtil.getLong(
				_httpServletRequest, "sourceRepositoryId");
		}

		return _sourceRepositoryId;
	}

	public void setViewAttributes(
		LiferayPortletResponse liferayPortletResponse) {

		PortletDisplay portletDisplay = _themeDisplay.getPortletDisplay();

		portletDisplay.setShowBackIcon(true);
		portletDisplay.setURLBack(getRedirect());

		if (liferayPortletResponse instanceof RenderResponse) {
			RenderResponse renderResponse =
				(RenderResponse)liferayPortletResponse;

			renderResponse.setTitle(
				LanguageUtil.get(_httpServletRequest, "copy-to"));
		}
	}

	private FolderItemSelectorCriterion _getFolderItemSelectorCriterion(
		long folderId, long repositoryId) {

		FolderItemSelectorCriterion folderItemSelectorCriterion =
			new FolderItemSelectorCriterion();

		folderItemSelectorCriterion.setDesiredItemSelectorReturnTypes(
			new FolderItemSelectorReturnType());
		folderItemSelectorCriterion.setFolderId(folderId);
		folderItemSelectorCriterion.setIgnoreRootFolder(true);
		folderItemSelectorCriterion.setRepositoryId(repositoryId);
		folderItemSelectorCriterion.setSelectedFolderId(folderId);
		folderItemSelectorCriterion.setSelectedRepositoryId(repositoryId);
		folderItemSelectorCriterion.setShowGroupSelector(true);
		folderItemSelectorCriterion.setShowMountFolder(false);

		return folderItemSelectorCriterion;
	}

	private Group _getGroup(long repositoryId) throws PortalException {
		Repository repository = RepositoryLocalServiceUtil.fetchRepository(
			repositoryId);

		if (repository == null) {
			return GroupLocalServiceUtil.getGroup(repositoryId);
		}

		return GroupLocalServiceUtil.getGroup(repository.getGroupId());
	}

	private String _getItemSelectedEventName() {
		PortletDisplay portletDisplay = _themeDisplay.getPortletDisplay();

		return portletDisplay.getNamespace() + "folderSelected";
	}

	private String _copyDLObjectURL;
	private final HttpServletRequest _httpServletRequest;
	private final LiferayPortletResponse _liferayPortletResponse;
	private String _redirect;
	private long _sourceFolderId = -1;
	private String _sourceFolderName;
	private long _sourceRepositoryId = -1;
	private final ThemeDisplay _themeDisplay;

}