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

package com.liferay.journal.web.internal.servlet.taglib.util;

import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemList;
import com.liferay.journal.constants.JournalPortletKeys;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalFolder;
import com.liferay.journal.model.JournalFolderConstants;
import com.liferay.journal.web.internal.security.permission.resource.JournalFolderPermission;
import com.liferay.journal.web.internal.security.permission.resource.JournalPermission;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.permission.GroupPermissionUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowEngineManagerUtil;
import com.liferay.portal.kernel.workflow.WorkflowHandler;
import com.liferay.portal.kernel.workflow.WorkflowHandlerRegistryUtil;
import com.liferay.staging.StagingGroupHelper;
import com.liferay.staging.StagingGroupHelperUtil;
import com.liferay.taglib.security.PermissionsURLTag;
import com.liferay.trash.TrashHelper;

import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class JournalFolderActionDropdownItems {

	public JournalFolderActionDropdownItems(
		JournalFolder folder, LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse,
		TrashHelper trashHelper) {

		_folder = folder;
		_liferayPortletRequest = liferayPortletRequest;
		_liferayPortletResponse = liferayPortletResponse;
		_trashHelper = trashHelper;

		_httpServletRequest = PortalUtil.getHttpServletRequest(
			liferayPortletRequest);
		_themeDisplay = (ThemeDisplay)liferayPortletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public List<DropdownItem> getActionDropdownItems() throws Exception {
		return new DropdownItemList() {
			{
				if (JournalFolderPermission.contains(
						_themeDisplay.getPermissionChecker(), _folder,
						ActionKeys.UPDATE)) {

					add(_getEditFolderActionUnsafeConsumer());
				}

				if (JournalFolderPermission.contains(
						_themeDisplay.getPermissionChecker(), _folder,
						ActionKeys.UPDATE)) {

					add(_getMoveFolderActionUnsafeConsumer());
				}

				if (JournalFolderPermission.contains(
						_themeDisplay.getPermissionChecker(), _folder,
						ActionKeys.PERMISSIONS)) {

					add(_getPermissionsFolderActionUnsafeConsumer());
				}

				if (JournalFolderPermission.contains(
						_themeDisplay.getPermissionChecker(), _folder,
						ActionKeys.DELETE)) {

					add(_getDeleteFolderActionUnsafeConsumer());
				}

				Group group = _themeDisplay.getScopeGroup();

				if (_isShowPublishFolderAction() && !group.isLayout()) {
					add(_getPublishToLiveFolderActionUnsafeConsumer());
				}
			}
		};
	}

	public List<DropdownItem> getInfoPanelActionDropdownItems()
		throws Exception {

		if (_folder != null) {
			List<DropdownItem> actionDropdownItems = getActionDropdownItems();

			if (JournalFolderPermission.contains(
					_themeDisplay.getPermissionChecker(), _folder,
					ActionKeys.ADD_FOLDER)) {

				DropdownItem dropdownItem = new DropdownItem();

				dropdownItem.setHref(
					_liferayPortletResponse.createRenderURL(), "mvcPath",
					"/edit_folder.jsp", "redirect", _getRedirect(), "groupId",
					_folder.getGroupId(), "parentFolderId",
					_folder.getFolderId());
				dropdownItem.setLabel(
					LanguageUtil.get(_httpServletRequest, "add-subfolder"));

				actionDropdownItems.add(0, dropdownItem);
			}

			return actionDropdownItems;
		}

		return new DropdownItemList() {
			{
				if (JournalPermission.contains(
						_themeDisplay.getPermissionChecker(),
						_themeDisplay.getScopeGroupId(),
						ActionKeys.ADD_FOLDER)) {

					add(_getAddHomeFolderActionUnsafeConsumer());
				}

				boolean workflowEnabled = false;

				if (WorkflowEngineManagerUtil.isDeployed()) {
					WorkflowHandler<?> workflowHandler =
						WorkflowHandlerRegistryUtil.getWorkflowHandler(
							JournalArticle.class.getName());

					if (workflowHandler != null) {
						workflowEnabled = true;
					}
				}

				if (workflowEnabled &&
					JournalFolderPermission.contains(
						_themeDisplay.getPermissionChecker(),
						_themeDisplay.getScopeGroupId(),
						JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
						ActionKeys.UPDATE)) {

					add(_getEditHomeFolderActionUnsafeConsumer());
				}

				if (JournalPermission.contains(
						_themeDisplay.getPermissionChecker(),
						_themeDisplay.getScopeGroupId(),
						ActionKeys.PERMISSIONS)) {

					add(_getPermissionsHomeFolderActionUnsafeConsumer());
				}

				Group group = _themeDisplay.getScopeGroup();

				if (_isShowPublishFolderAction() && !group.isLayout()) {
					add(_getPublishToLiveFolderActionUnsafeConsumer());
				}
			}
		};
	}

	private UnsafeConsumer<DropdownItem, Exception>
		_getAddHomeFolderActionUnsafeConsumer() {

		return dropdownItem -> {
			dropdownItem.setHref(
				_liferayPortletResponse.createRenderURL(), "mvcPath",
				"/edit_folder.jsp", "redirect", _getRedirect(), "groupId",
				_themeDisplay.getScopeGroupId(), "parentFolderId",
				JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID);
			dropdownItem.setLabel(
				LanguageUtil.get(_httpServletRequest, "add-folder"));
		};
	}

	private UnsafeConsumer<DropdownItem, Exception>
			_getDeleteFolderActionUnsafeConsumer()
		throws PortalException {

		String redirect = _getRedirect();

		long currentFolderId = ParamUtil.getLong(
			_httpServletRequest, "folderId");

		if (currentFolderId == _folder.getFolderId()) {
			PortletURL redirectURL = _liferayPortletResponse.createRenderURL();

			redirectURL.setParameter(
				"groupId", String.valueOf(_folder.getGroupId()));
			redirectURL.setParameter(
				"folderId", String.valueOf(_folder.getParentFolderId()));

			redirect = redirectURL.toString();
		}

		PortletURL deleteURL = _liferayPortletResponse.createActionURL();

		String actionName = "/journal/delete_folder";
		String key = "delete";

		if (_trashHelper.isTrashEnabled(_themeDisplay.getScopeGroupId())) {
			actionName = "/journal/move_folder_to_trash";
			key = "move-to-recycle-bin";
		}

		deleteURL.setParameter(ActionRequest.ACTION_NAME, actionName);

		deleteURL.setParameter("redirect", redirect);
		deleteURL.setParameter("groupId", String.valueOf(_folder.getGroupId()));
		deleteURL.setParameter(
			"folderId", String.valueOf(_folder.getFolderId()));

		String label = LanguageUtil.get(_httpServletRequest, key);

		return dropdownItem -> {
			dropdownItem.putData("action", "delete");
			dropdownItem.putData("deleteURL", deleteURL.toString());
			dropdownItem.setLabel(label);
		};
	}

	private UnsafeConsumer<DropdownItem, Exception>
		_getEditFolderActionUnsafeConsumer() {

		return dropdownItem -> {
			dropdownItem.setHref(
				_liferayPortletResponse.createRenderURL(), "mvcPath",
				"/edit_folder.jsp", "redirect", _getRedirect(), "groupId",
				_folder.getGroupId(), "folderId", _folder.getFolderId());
			dropdownItem.setLabel(
				LanguageUtil.get(_httpServletRequest, "edit"));
		};
	}

	private UnsafeConsumer<DropdownItem, Exception>
		_getEditHomeFolderActionUnsafeConsumer() {

		return dropdownItem -> {
			dropdownItem.setHref(
				_liferayPortletResponse.createRenderURL(), "mvcPath",
				"/edit_folder.jsp", "redirect", _getRedirect(), "groupId",
				_themeDisplay.getScopeGroupId(), "folderId",
				JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID, "rootFolder",
				true);
			dropdownItem.setLabel(
				LanguageUtil.get(_httpServletRequest, "edit"));
		};
	}

	private UnsafeConsumer<DropdownItem, Exception>
		_getMoveFolderActionUnsafeConsumer() {

		return dropdownItem -> {
			dropdownItem.setHref(
				_liferayPortletResponse.createRenderURL(), "mvcPath",
				"/move_entries.jsp", "redirect", _getRedirect(),
				"rowIdsJournalFolder", _folder.getFolderId());
			dropdownItem.setLabel(
				LanguageUtil.get(_httpServletRequest, "move"));
		};
	}

	private UnsafeConsumer<DropdownItem, Exception>
			_getPermissionsFolderActionUnsafeConsumer()
		throws Exception {

		String permissionsURL = PermissionsURLTag.doTag(
			StringPool.BLANK, JournalFolder.class.getName(), _folder.getName(),
			null, String.valueOf(_folder.getPrimaryKey()),
			LiferayWindowState.POP_UP.toString(), null, _httpServletRequest);

		return dropdownItem -> {
			dropdownItem.putData("action", "permissions");
			dropdownItem.putData("permissionsURL", permissionsURL);
			dropdownItem.setLabel(
				LanguageUtil.get(_httpServletRequest, "permissions"));
		};
	}

	private UnsafeConsumer<DropdownItem, Exception>
			_getPermissionsHomeFolderActionUnsafeConsumer()
		throws Exception {

		String permissionsURL = PermissionsURLTag.doTag(
			StringPool.BLANK, "com.liferay.journal",
			_themeDisplay.getScopeGroupName(), null,
			String.valueOf(_themeDisplay.getScopeGroupId()),
			LiferayWindowState.POP_UP.toString(), null, _httpServletRequest);

		return dropdownItem -> {
			dropdownItem.putData("action", "permissions");
			dropdownItem.putData("permissionsURL", permissionsURL);
			dropdownItem.setLabel(
				LanguageUtil.get(_httpServletRequest, "permissions"));
		};
	}

	private UnsafeConsumer<DropdownItem, Exception>
		_getPublishToLiveFolderActionUnsafeConsumer() {

		PortletURL publishFolderURL = _liferayPortletResponse.createActionURL();

		publishFolderURL.setParameter(
			ActionRequest.ACTION_NAME, "/journal/publish_folder");

		publishFolderURL.setParameter("backURL", _getRedirect());
		publishFolderURL.setParameter(
			"folderId", String.valueOf(_folder.getFolderId()));

		return dropdownItem -> {
			dropdownItem.putData("action", "publishToLive");
			dropdownItem.putData(
				"publishFolderURL", publishFolderURL.toString());
			dropdownItem.setLabel(
				LanguageUtil.get(_httpServletRequest, "publish-to-live"));
		};
	}

	private String _getRedirect() {
		if (_redirect != null) {
			return _redirect;
		}

		_redirect = ParamUtil.getString(
			_liferayPortletRequest, "redirect", _themeDisplay.getURLCurrent());

		return _redirect;
	}

	private boolean _isShowPublishAction() {
		PermissionChecker permissionChecker =
			_themeDisplay.getPermissionChecker();

		long scopeGroupId = _themeDisplay.getScopeGroupId();

		StagingGroupHelper stagingGroupHelper =
			StagingGroupHelperUtil.getStagingGroupHelper();

		try {
			if (GroupPermissionUtil.contains(
					permissionChecker, scopeGroupId,
					ActionKeys.EXPORT_IMPORT_PORTLET_INFO) &&
				stagingGroupHelper.isStagingGroup(scopeGroupId) &&
				stagingGroupHelper.isStagedPortlet(
					scopeGroupId, JournalPortletKeys.JOURNAL)) {

				return true;
			}

			return false;
		}
		catch (PortalException pe) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"An exception occured when checking if the publish " +
						"action should be displayed",
					pe);
			}

			return false;
		}
	}

	private boolean _isShowPublishFolderAction() {
		if (_folder == null) {
			return false;
		}

		return _isShowPublishAction();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		JournalFolderActionDropdownItems.class);

	private final JournalFolder _folder;
	private final HttpServletRequest _httpServletRequest;
	private final LiferayPortletRequest _liferayPortletRequest;
	private final LiferayPortletResponse _liferayPortletResponse;
	private String _redirect;
	private final ThemeDisplay _themeDisplay;
	private final TrashHelper _trashHelper;

}