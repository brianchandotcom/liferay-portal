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

package com.liferay.document.library.taglib.servlet.taglib;

import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppServiceUtil;
import com.liferay.document.library.taglib.internal.display.context.RepositoryBrowserTagDisplayContext;
import com.liferay.document.library.taglib.internal.servlet.ServletContextUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileShortcut;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.ServiceProxyFactory;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.taglib.util.IncludeTag;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

/**
 * @author Adolfo Pérez
 */
public class RepositoryBrowserTag extends IncludeTag {

	@Override
	public int doStartTag() {
		return EVAL_BODY_INCLUDE;
	}

	public long getFolderId() {
		return _folderId;
	}

	public long getRepositoryId() {
		return _repositoryId;
	}

	public void setFolderId(long folderId) {
		_folderId = folderId;
	}

	@Override
	public void setPageContext(PageContext pageContext) {
		super.setPageContext(pageContext);

		setServletContext(ServletContextUtil.getServletContext());
	}

	public void setRepositoryId(long repositoryId) {
		_repositoryId = repositoryId;
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_folderId = DLFolderConstants.DEFAULT_PARENT_FOLDER_ID;
		_repositoryId = 0;
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	@Override
	protected void setAttributes(HttpServletRequest httpServletRequest) {
		PortletRequest portletRequest =
			(PortletRequest)httpServletRequest.getAttribute(
				JavaConstants.JAVAX_PORTLET_REQUEST);

		PortletResponse portletResponse =
			(PortletResponse)httpServletRequest.getAttribute(
				JavaConstants.JAVAX_PORTLET_RESPONSE);

		httpServletRequest.setAttribute(
			RepositoryBrowserTagDisplayContext.class.getName(),
			new RepositoryBrowserTagDisplayContext(
				DLAppServiceUtil.getService(),
				_fileEntryModelResourcePermission,
				_fileShortcutModelResourcePermission,
				_folderModelResourcePermission, _getFolderId(),
				httpServletRequest,
				PortalUtil.getLiferayPortletRequest(portletRequest),
				PortalUtil.getLiferayPortletResponse(portletResponse),
				portletRequest, _getRepositoryId(), getFolderId()));
	}

	private long _getFolderId() {
		return ParamUtil.getLong(getRequest(), "folderId", _folderId);
	}

	private long _getRepositoryId() {
		if (_repositoryId != 0) {
			return _repositoryId;
		}

		HttpServletRequest httpServletRequest = getRequest();

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		return themeDisplay.getSiteGroupId();
	}

	private static final String _PAGE = "/repository_browser/page.jsp";

	private static volatile ModelResourcePermission<FileEntry>
		_fileEntryModelResourcePermission =
			ServiceProxyFactory.newServiceTrackedInstance(
				ModelResourcePermission.class, RepositoryBrowserTag.class,
				"_fileEntryModelResourcePermission",
				"(model.class.name=" + FileEntry.class.getName() + ")", false);
	private static volatile ModelResourcePermission<FileShortcut>
		_fileShortcutModelResourcePermission =
			ServiceProxyFactory.newServiceTrackedInstance(
				ModelResourcePermission.class, RepositoryBrowserTag.class,
				"_fileShortcutModelResourcePermission",
				"(model.class.name=" + FileShortcut.class.getName() + ")",
				false);
	private static volatile ModelResourcePermission<Folder>
		_folderModelResourcePermission =
			ServiceProxyFactory.newServiceTrackedInstance(
				ModelResourcePermission.class, RepositoryBrowserTag.class,
				"_folderModelResourcePermission",
				"(model.class.name=" + Folder.class.getName() + ")", false);

	private long _folderId = DLFolderConstants.DEFAULT_PARENT_FOLDER_ID;
	private long _repositoryId;

}