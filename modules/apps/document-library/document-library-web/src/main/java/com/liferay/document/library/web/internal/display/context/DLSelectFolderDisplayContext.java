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

import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.document.library.constants.DLPortletKeys;
import com.liferay.document.library.kernel.model.DLFolder;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppServiceUtil;
import com.liferay.document.library.web.internal.security.permission.resource.DLFolderPermission;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.bean.BeanParamUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Adolfo Pérez
 */
public class DLSelectFolderDisplayContext {

	public DLSelectFolderDisplayContext(
		Folder folder, HttpServletRequest httpServletRequest,
		LiferayPortletResponse liferayPortletResponse) {

		_folder = folder;
		_httpServletRequest = httpServletRequest;
		_liferayPortletResponse = liferayPortletResponse;

		_themeDisplay = (ThemeDisplay)httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public PortletURL getAddFolderPortletURL() {
		return PortletURLBuilder.createRenderURL(
			_liferayPortletResponse
		).setMVCRenderCommandName(
			"/document_library/edit_folder"
		).setRedirect(
			PortalUtil.getCurrentURL(_httpServletRequest)
		).setParameter(
			"ignoreRootFolder", true
		).setParameter(
			"parentFolderId", getFolderId()
		).setParameter(
			"repositoryId", getRepositoryId()
		).buildRenderURL();
	}

	public Folder getFolder() {
		return _folder;
	}

	public int getFolderFileEntriesCount(Folder folder) {
		try {
			return DLAppServiceUtil.getFoldersFileEntriesCount(
				folder.getRepositoryId(),
				Collections.singletonList(folder.getFolderId()),
				WorkflowConstants.STATUS_APPROVED);
		}
		catch (PortalException portalException) {
			_log.error(portalException, portalException);

			return 0;
		}
	}

	public int getFolderFoldersCount(Folder folder) {
		try {
			return DLAppServiceUtil.getFoldersCount(
				folder.getRepositoryId(), folder.getFolderId());
		}
		catch (PortalException portalException) {
			_log.error(portalException, portalException);

			return 0;
		}
	}

	public long getFolderId() {
		return BeanParamUtil.getLong(
			_folder, _httpServletRequest, "folderId",
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID);
	}

	public String getFolderName() {
		if (_folder == null) {
			return LanguageUtil.get(_httpServletRequest, "home");
		}

		return _folder.getName();
	}

	public List<Folder> getFolders(int start, int end) throws PortalException {
		return DLAppServiceUtil.getFolders(
			getRepositoryId(), getFolderId(), _isMountFolderVisible(), start,
			end);
	}

	public int getFoldersCount() throws PortalException {
		return DLAppServiceUtil.getFoldersCount(
			getRepositoryId(), getFolderId(), _isMountFolderVisible());
	}

	public String getIconCssClass(Folder folder) throws PortalException {
		AssetRendererFactory<?> assetRendererFactory =
			AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(
				DLFolder.class.getName());

		AssetRenderer<?> assetRenderer = assetRendererFactory.getAssetRenderer(
			folder.getFolderId());

		return assetRenderer.getIconCssClass();
	}

	public PortletURL getIteratorPortletURL() throws PortalException {
		return _getFolderPortletURL(getFolderId());
	}

	public long getRepositoryId() {
		if (_folder != null) {
			return _folder.getRepositoryId();
		}

		return _themeDisplay.getScopeGroupId();
	}

	public PortletURL getRowPortletURL(Folder folder) throws PortalException {
		return _getFolderPortletURL(folder.getFolderId());
	}

	public long getSelectedFolderId() {
		return ParamUtil.getLong(
			_httpServletRequest, "selectedFolderId", getFolderId());
	}

	public Map<String, Object> getSelectorButtonData() {
		return getSelectorButtonData(_folder);
	}

	public Map<String, Object> getSelectorButtonData(Folder folder) {
		return HashMapBuilder.<String, Object>put(
			"folderid", getFolderId()
		).put(
			"folderissupportsmetadata",
			() -> {
				if (folder != null) {
					return folder.isSupportsMetadata();
				}

				return true;
			}
		).put(
			"folderissupportssocial",
			() -> {
				if (folder != null) {
					return folder.isSupportsSocial();
				}

				return true;
			}
		).put(
			"foldername", getFolderName()
		).build();
	}

	public boolean hasAddFolderPermission() throws PortalException {
		if (_isAddFolderButtonVisible() &&
			DLFolderPermission.contains(
				_themeDisplay.getPermissionChecker(), getRepositoryId(),
				getFolderId(), ActionKeys.ADD_FOLDER)) {

			return true;
		}

		return false;
	}

	public boolean isSelectButtonDisabled() {
		return isSelectButtonDisabled(getFolderId());
	}

	public boolean isSelectButtonDisabled(long folderId) {
		if (folderId == getSelectedFolderId()) {
			return true;
		}

		return false;
	}

	private PortletURL _getFolderPortletURL(long folderId) {
		return PortletURLBuilder.createRenderURL(
			_liferayPortletResponse
		).setMVCRenderCommandName(
			"/document_library/select_folder"
		).setParameter(
			"folderId", folderId
		).setParameter(
			"ignoreRootFolder", true
		).setParameter(
			"selectedFolderId", getSelectedFolderId()
		).setParameter(
			"showMountFolder", _isMountFolderVisible()
		).buildRenderURL();
	}

	private boolean _isAddFolderButtonVisible() {
		PortletDisplay portletDisplay = _themeDisplay.getPortletDisplay();

		String portletName = portletDisplay.getPortletName();

		if (portletName.equals(DLPortletKeys.DOCUMENT_LIBRARY) ||
			portletName.equals(DLPortletKeys.DOCUMENT_LIBRARY_ADMIN)) {

			return true;
		}

		return false;
	}

	private boolean _isMountFolderVisible() {
		return ParamUtil.getBoolean(
			_httpServletRequest, "showMountFolder", true);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DLSelectFolderDisplayContext.class);

	private final Folder _folder;
	private final HttpServletRequest _httpServletRequest;
	private final LiferayPortletResponse _liferayPortletResponse;
	private final ThemeDisplay _themeDisplay;

}