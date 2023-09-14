/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.web.internal.display.context;

import com.liferay.document.library.kernel.model.DLFolder;
import com.liferay.document.library.kernel.service.DLFolderLocalServiceUtil;
import com.liferay.item.selector.ItemSelector;
import com.liferay.item.selector.criteria.FolderItemSelectorReturnType;
import com.liferay.item.selector.criteria.folder.criterion.FolderItemSelectorCriterion;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Repository;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactoryUtil;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.RepositoryLocalServiceUtil;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ParamUtil;

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
	}

	public String getActionURL() {
		return PortletURLBuilder.createActionURL(
			_liferayPortletResponse
		).setActionName(
			"/document_library/copy_entries"
		).buildString();
	}

	public long[] getEntryIds() {
		if (ArrayUtil.isEmpty(_entryIds)) {
			_entryIds = ParamUtil.getLongValues(
				_httpServletRequest, "entryIds");
		}

		return _entryIds;
	}

	public String getEntryName() throws PortalException {
		if (_entryName != null) {
			return _entryName;
		}

		long[] entryIds = getEntryIds();

		if (ArrayUtil.isEmpty(entryIds) || (entryIds.length > 1)) {
			_entryName = StringPool.BLANK;
		}
		else {
			DLFolder folder = DLFolderLocalServiceUtil.getFolder(entryIds[0]);

			_entryName = folder.getName();
		}

		return _entryName;
	}

	public String getRedirect() {
		if (_redirect != null) {
			return _redirect;
		}

		_redirect = ParamUtil.getString(_httpServletRequest, "redirect");

		return _redirect;
	}

	public String getSelectionModalURL() throws PortalException {
		ItemSelector itemSelector =
			(ItemSelector)_httpServletRequest.getAttribute(
				ItemSelector.class.getName());

		return String.valueOf(
			itemSelector.getItemSelectorURL(
				RequestBackedPortletURLFactoryUtil.create(_httpServletRequest),
				_getGroup(getSourceRepositoryId()),
				_themeDisplay.getScopeGroupId(), _getItemSelectedEventName(),
				_getFolderItemSelectorCriterion(
					_getSourceFolderId(), getSourceRepositoryId())));
	}

	public long getSourceRepositoryId() {
		if (_sourceRepositoryId != 0) {
			return _sourceRepositoryId;
		}

		_sourceRepositoryId = ParamUtil.getLong(
			_httpServletRequest, "sourceRepositoryId");

		return _sourceRepositoryId;
	}

	public void setViewAttributes() {
		PortletDisplay portletDisplay = _themeDisplay.getPortletDisplay();

		portletDisplay.setShowBackIcon(true);
		portletDisplay.setURLBack(getRedirect());

		if (_liferayPortletResponse instanceof RenderResponse) {
			RenderResponse renderResponse =
				(RenderResponse)_liferayPortletResponse;

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

	private long _getSourceFolderId() {
		if (_sourceFolderId < 0) {
			_sourceFolderId = ParamUtil.getLong(
				_httpServletRequest, "sourceFolderId");
		}

		return _sourceFolderId;
	}

	private long[] _entryIds;
	private String _entryName;
	private final HttpServletRequest _httpServletRequest;
	private final LiferayPortletResponse _liferayPortletResponse;
	private String _redirect;
	private long _sourceFolderId;
	private long _sourceRepositoryId;
	private final ThemeDisplay _themeDisplay;

}