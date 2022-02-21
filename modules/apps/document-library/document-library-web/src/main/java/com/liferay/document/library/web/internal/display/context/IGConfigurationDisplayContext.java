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

import com.liferay.document.library.constants.DLPortletKeys;
import com.liferay.document.library.kernel.exception.NoSuchFolderException;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.document.library.web.internal.display.context.helper.DLPortletInstanceSettingsHelper;
import com.liferay.document.library.web.internal.display.context.helper.IGRequestHelper;
import com.liferay.document.library.web.internal.settings.DLPortletInstanceSettings;
import com.liferay.document.library.web.internal.util.DLFolderUtil;
import com.liferay.item.selector.ItemSelector;
import com.liferay.item.selector.criteria.FolderItemSelectorReturnType;
import com.liferay.item.selector.criteria.folder.criterion.FolderItemSelectorCriterion;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.Repository;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactoryUtil;
import com.liferay.portal.kernel.repository.capabilities.TrashCapability;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.PortletPreferencesLocalService;
import com.liferay.portal.kernel.service.RepositoryLocalService;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.KeyValuePair;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.trash.TrashHelper;

import java.util.List;
import java.util.Objects;

import javax.portlet.PortletPreferences;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Adolfo Pérez
 */
public class IGConfigurationDisplayContext {

	public IGConfigurationDisplayContext(
		DLAppLocalService dlAppLocalService, ItemSelector itemSelector,
		HttpServletRequest httpServletRequest,
		PortletPreferencesLocalService portletPreferencesLocalService,
		RepositoryLocalService repositoryLocalService,
		TrashHelper trashHelper) {

		_dlAppLocalService = dlAppLocalService;
		_itemSelector = itemSelector;
		_httpServletRequest = httpServletRequest;
		_portletPreferencesLocalService = portletPreferencesLocalService;
		_repositoryLocalService = repositoryLocalService;
		_trashHelper = trashHelper;

		IGRequestHelper igRequestHelper = new IGRequestHelper(
			_httpServletRequest);

		_dlPortletInstanceSettingsHelper = new DLPortletInstanceSettingsHelper(
			igRequestHelper);
		_igRequestHelper = igRequestHelper;

		_renderRequest = (RenderRequest)httpServletRequest.getAttribute(
			JavaConstants.JAVAX_PORTLET_REQUEST);
		_themeDisplay = (ThemeDisplay)httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public List<KeyValuePair> getAvailableMimeTypes() {
		return _dlPortletInstanceSettingsHelper.getAvailableMimeTypes();
	}

	public List<KeyValuePair> getCurrentMimeTypes() {
		return _dlPortletInstanceSettingsHelper.getCurrentMimeTypes();
	}

	public String getDisplayStyle() {
		PortletPreferences portletPreferences = _getPortletPreferences();

		return portletPreferences.getValue("displayStyle", StringPool.BLANK);
	}

	public long getDisplayStyleGroupId() {
		PortletPreferences portletPreferences = _getPortletPreferences();

		return GetterUtil.getLong(
			portletPreferences.getValue("displayStyleGroupId", null),
			_themeDisplay.getScopeGroupId());
	}

	public String getItemSelectedEventName() {
		PortletDisplay portletDisplay = _themeDisplay.getPortletDisplay();

		return portletDisplay.getNamespace() + "folderSelected";
	}

	public long getRootFolderId() throws PortalException {
		_initFolder();

		return _folderId;
	}

	public String getRootFolderName() throws PortalException {
		_initFolder();

		if (Objects.equals(_folderName, StringPool.BLANK)) {
			_folderName = _getFolderName();
		}

		return _folderName;
	}

	public long getSelectedRepositoryId() throws PortalException {
		_initRepository();

		return _selectedRepositoryId;
	}

	public PortletURL getSelectFolderURL() throws PortalException {
		FolderItemSelectorCriterion folderItemSelectorCriterion =
			new FolderItemSelectorCriterion();

		folderItemSelectorCriterion.setDesiredItemSelectorReturnTypes(
			new FolderItemSelectorReturnType());

		folderItemSelectorCriterion.setFolderId(getRootFolderId());
		folderItemSelectorCriterion.setIgnoreRootFolder(true);
		folderItemSelectorCriterion.setRepositoryId(getSelectedRepositoryId());
		folderItemSelectorCriterion.setSelectedFolderId(getRootFolderId());
		folderItemSelectorCriterion.setSelectedRepositoryId(
			getSelectedRepositoryId());
		folderItemSelectorCriterion.setShowGroupSelector(true);

		long groupId = getSelectedRepositoryId();

		Repository repository = _repositoryLocalService.fetchRepository(
			getSelectedRepositoryId());

		if (repository != null) {
			groupId = repository.getGroupId();
		}

		return _itemSelector.getItemSelectorURL(
			RequestBackedPortletURLFactoryUtil.create(_httpServletRequest),
			GroupLocalServiceUtil.getGroup(
				GetterUtil.getLong(groupId, _themeDisplay.getScopeGroupId())),
			_themeDisplay.getScopeGroupId(), getItemSelectedEventName(),
			folderItemSelectorCriterion);
	}

	public boolean isRootFolderInTrash() throws PortalException {
		_initFolder();

		return _folderInTrash;
	}

	public boolean isRootFolderNotFound() throws PortalException {
		_initFolder();

		return _folderNotFound;
	}

	public boolean isShowActions() {
		return _dlPortletInstanceSettingsHelper.isShowActions();
	}

	private Folder _getFolder() {
		try {
			return _dlAppLocalService.getFolder(_folderId);
		}
		catch (Exception exception) {
			_folderNotFound = true;

			return null;
		}
	}

	private String _getFolderName() {
		if ((_folderId == null) ||
			(_folderId == DLFolderConstants.DEFAULT_PARENT_FOLDER_ID)) {

			return LanguageUtil.get(_httpServletRequest, "home");
		}

		Folder folder = _getFolder();

		if (folder == null) {
			return StringPool.BLANK;
		}

		if (_folderInTrash) {
			return _trashHelper.getOriginalTitle(_folder.getName());
		}

		return folder.getName();
	}

	private PortletPreferences _getPortletPreferences() {
		if (_portletPreferences != null) {
			return _portletPreferences;
		}

		Layout layout = _themeDisplay.getLayout();

		if (layout.isTypeControlPanel()) {
			_portletPreferences =
				_portletPreferencesLocalService.getPreferences(
					_themeDisplay.getCompanyId(),
					_themeDisplay.getScopeGroupId(),
					PortletKeys.PREFS_OWNER_TYPE_GROUP, 0,
					DLPortletKeys.DOCUMENT_LIBRARY, null);
		}
		else {
			_portletPreferences = _renderRequest.getPreferences();
		}

		return _portletPreferences;
	}

	private void _initFolder() throws PortalException {
		if (_folderId != null) {
			return;
		}

		_folderId = DLFolderConstants.DEFAULT_PARENT_FOLDER_ID;
		_folderInTrash = false;
		_folderName = StringPool.BLANK;
		_folderNotFound = false;

		DLPortletInstanceSettings dlPortletInstanceSettings =
			_igRequestHelper.getDLPortletInstanceSettings();

		_folderId = dlPortletInstanceSettings.getRootFolderId();

		if (_folderId == DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			return;
		}

		Folder folder = _getFolder();

		if (folder == null) {
			return;
		}

		_folder = folder;
		_folderName = folder.getName();

		if (_folder.isRepositoryCapabilityProvided(TrashCapability.class)) {
			TrashCapability trashCapability = _folder.getRepositoryCapability(
				TrashCapability.class);

			_folderInTrash = trashCapability.isInTrash(_folder);

			if (_folderInTrash) {
				_folderName = _trashHelper.getOriginalTitle(_folder.getName());
			}
		}

		try {
			DLFolderUtil.validateDepotFolder(
				_folderId, _folder.getGroupId(),
				_themeDisplay.getScopeGroupId());
		}
		catch (NoSuchFolderException noSuchFolderException) {
			if (_log.isWarnEnabled()) {
				_log.warn(noSuchFolderException);
			}

			_folderNotFound = true;
		}
	}

	private void _initRepository() throws PortalException {
		if (_selectedRepositoryId != 0) {
			return;
		}

		DLPortletInstanceSettings dlPortletInstanceSettings =
			_igRequestHelper.getDLPortletInstanceSettings();

		_selectedRepositoryId =
			dlPortletInstanceSettings.getSelectedRepositoryId();

		if (_selectedRepositoryId != 0) {
			return;
		}

		_initFolder();

		if ((_folder == null) && (_folderId != null) &&
			(_folderId != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID)) {

			_folder = _getFolder();
		}

		if (_folder != null) {
			_selectedRepositoryId = _folder.getRepositoryId();
		}
		else {
			_selectedRepositoryId = ParamUtil.getLong(
				_httpServletRequest, "repositoryId",
				_themeDisplay.getScopeGroupId());
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		IGConfigurationDisplayContext.class);

	private final DLAppLocalService _dlAppLocalService;
	private final DLPortletInstanceSettingsHelper
		_dlPortletInstanceSettingsHelper;
	private Folder _folder;
	private Long _folderId;
	private boolean _folderInTrash;
	private String _folderName;
	private boolean _folderNotFound;
	private final HttpServletRequest _httpServletRequest;
	private final IGRequestHelper _igRequestHelper;
	private final ItemSelector _itemSelector;
	private PortletPreferences _portletPreferences;
	private final PortletPreferencesLocalService
		_portletPreferencesLocalService;
	private final RenderRequest _renderRequest;
	private final RepositoryLocalService _repositoryLocalService;
	private long _selectedRepositoryId;
	private final ThemeDisplay _themeDisplay;
	private final TrashHelper _trashHelper;

}