/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.taglib.servlet.taglib;

import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppServiceUtil;
import com.liferay.document.library.taglib.internal.display.context.RepositoryBrowserTagDisplayContext;
import com.liferay.document.library.taglib.internal.servlet.ServletContextUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileShortcut;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.ServiceProxyFactory;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.taglib.util.IncludeTag;

import java.util.Collections;
import java.util.Set;

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

	public String getActions() {
		return _actions;
	}

	public long getFolderId() {
		return _folderId;
	}

	public long getRepositoryId() {
		return _repositoryId;
	}

	public void setActions(String actions) {
		_actions = actions;
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

		_actions = StringPool.BLANK;
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
				_getActionsSet(), DLAppServiceUtil.getService(),
				_fileEntryModelResourcePermission,
				_fileShortcutModelResourcePermission,
				_folderModelResourcePermission, _getFolderId(),
				httpServletRequest,
				PortalUtil.getLiferayPortletRequest(portletRequest),
				PortalUtil.getLiferayPortletResponse(portletResponse),
				portletRequest, _getRepositoryId(), getFolderId()));
	}

	private Set<String> _getActionsSet() {
		if (Validator.isBlank(getActions())) {
			return _allActions;
		}

		String actions = StringUtil.trim(getActions());

		Set<String> actionsSet = SetUtil.fromArray(actions.split("\\s*,\\s*"));

		if (actionsSet.contains("none")) {
			return Collections.emptySet();
		}

		if (actionsSet.contains("all")) {
			return _allActions;
		}

		return actionsSet;
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

	private static final Set<String> _allActions = SetUtil.fromArray(
		"add-folder", "delete", "rename", "upload");
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

	private String _actions = StringPool.BLANK;
	private long _folderId = DLFolderConstants.DEFAULT_PARENT_FOLDER_ID;
	private long _repositoryId;

}