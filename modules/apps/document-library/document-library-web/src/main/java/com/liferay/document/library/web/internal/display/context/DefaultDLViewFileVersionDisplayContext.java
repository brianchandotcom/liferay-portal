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

import com.liferay.document.library.display.context.DLMimeTypeDisplayContext;
import com.liferay.document.library.display.context.DLViewFileVersionDisplayContext;
import com.liferay.document.library.kernel.model.DLFileEntryConstants;
import com.liferay.document.library.kernel.model.DLFileEntryMetadata;
import com.liferay.document.library.kernel.model.DLFileVersion;
import com.liferay.document.library.kernel.service.DLFileEntryMetadataLocalServiceUtil;
import com.liferay.document.library.kernel.versioning.VersioningStrategy;
import com.liferay.document.library.preview.DLPreviewRenderer;
import com.liferay.document.library.preview.DLPreviewRendererProvider;
import com.liferay.document.library.preview.exception.DLFileEntryPreviewGenerationException;
import com.liferay.document.library.preview.exception.DLPreviewGenerationInProcessException;
import com.liferay.document.library.preview.exception.DLPreviewSizeException;
import com.liferay.document.library.util.DLURLHelper;
import com.liferay.document.library.util.DLURLHelperUtil;
import com.liferay.document.library.web.internal.configuration.FFFriendlyURLEntryFileEntryConfigurationUtil;
import com.liferay.document.library.web.internal.constants.DLWebKeys;
import com.liferay.document.library.web.internal.display.context.helper.DLPortletInstanceSettingsHelper;
import com.liferay.document.library.web.internal.display.context.helper.DLRequestHelper;
import com.liferay.document.library.web.internal.display.context.helper.FileEntryDisplayContextHelper;
import com.liferay.document.library.web.internal.display.context.helper.FileVersionDisplayContextHelper;
import com.liferay.document.library.web.internal.display.context.logic.UIItemsBuilder;
import com.liferay.document.library.web.internal.display.context.util.JSPRenderer;
import com.liferay.document.library.web.internal.helper.DLTrashHelper;
import com.liferay.dynamic.data.mapping.exception.StorageException;
import com.liferay.dynamic.data.mapping.kernel.DDMStructure;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.storage.StorageEngine;
import com.liferay.friendly.url.model.FriendlyURLEntry;
import com.liferay.friendly.url.service.FriendlyURLEntryLocalServiceUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.portlet.constants.FriendlyURLResolverConstants;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileShortcut;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.servlet.taglib.ui.Menu;
import com.liferay.portal.kernel.servlet.taglib.ui.MenuItem;
import com.liferay.portal.kernel.servlet.taglib.ui.ToolbarItem;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Adolfo Pérez
 */
public class DefaultDLViewFileVersionDisplayContext
	implements DLViewFileVersionDisplayContext {

	public DefaultDLViewFileVersionDisplayContext(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, FileShortcut fileShortcut,
			DLMimeTypeDisplayContext dlMimeTypeDisplayContext,
			ResourceBundle resourceBundle, StorageEngine storageEngine,
			DLTrashHelper dlTrashHelper,
			DLPreviewRendererProvider dlPreviewRendererProvider,
			VersioningStrategy versioningStrategy, DLURLHelper dlURLHelper)
		throws PortalException {

		this(
			httpServletRequest, fileShortcut.getFileVersion(), fileShortcut,
			dlMimeTypeDisplayContext, resourceBundle, storageEngine,
			dlTrashHelper, dlPreviewRendererProvider, versioningStrategy,
			dlURLHelper);
	}

	public DefaultDLViewFileVersionDisplayContext(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse, FileVersion fileVersion,
		DLMimeTypeDisplayContext dlMimeTypeDisplayContext,
		ResourceBundle resourceBundle, StorageEngine storageEngine,
		DLTrashHelper dlTrashHelper,
		DLPreviewRendererProvider dlPreviewRendererProvider,
		VersioningStrategy versioningStrategy, DLURLHelper dlURLHelper) {

		this(
			httpServletRequest, fileVersion, null, dlMimeTypeDisplayContext,
			resourceBundle, storageEngine, dlTrashHelper,
			dlPreviewRendererProvider, versioningStrategy, dlURLHelper);
	}

	@Override
	public String getCssClassFileMimeType() {
		if (_dlMimeTypeDisplayContext == null) {
			return "file-icon-color-0";
		}

		return _dlMimeTypeDisplayContext.getCssClassFileMimeType(
			_fileVersion.getMimeType());
	}

	@Override
	public DDMFormValues getDDMFormValues(DDMStructure ddmStructure)
		throws PortalException {

		DLFileEntryMetadata dlFileEntryMetadata =
			DLFileEntryMetadataLocalServiceUtil.getFileEntryMetadata(
				ddmStructure.getStructureId(), _fileVersion.getFileVersionId());

		return _storageEngine.getDDMFormValues(
			dlFileEntryMetadata.getDDMStorageId());
	}

	@Override
	public DDMFormValues getDDMFormValues(long classPK)
		throws StorageException {

		return _storageEngine.getDDMFormValues(classPK);
	}

	@Override
	public List<DDMStructure> getDDMStructures() throws PortalException {
		if (_ddmStructures != null) {
			return _ddmStructures;
		}

		if (_fileVersionDisplayContextHelper.isDLFileVersion()) {
			DLFileVersion dlFileVersion =
				(DLFileVersion)_fileVersion.getModel();

			_ddmStructures = dlFileVersion.getDDMStructures();
		}
		else {
			_ddmStructures = Collections.emptyList();
		}

		return _ddmStructures;
	}

	@Override
	public int getDDMStructuresCount() throws PortalException {
		List<DDMStructure> ddmStructures = getDDMStructures();

		return ddmStructures.size();
	}

	@Override
	public String getDiscussionClassName() {
		return DLFileEntryConstants.getClassName();
	}

	@Override
	public long getDiscussionClassPK() {
		return _fileVersion.getFileEntryId();
	}

	@Override
	public String getDiscussionLabel(Locale locale) {
		return LanguageUtil.get(_resourceBundle, "comments");
	}

	@Override
	public String getDownloadURL(
			FileEntry fileEntry, FileVersion fileVersion, boolean useVersion)
		throws PortalException {

		String friendlyURL = _getFriendlyURL(fileEntry.getFileEntryId());

		if (!useVersion &&
			FFFriendlyURLEntryFileEntryConfigurationUtil.enabled() &&
			!Validator.isBlank(friendlyURL)) {

			return HttpUtil.addParameter(friendlyURL, "download", true);
		}

		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		return DLURLHelperUtil.getDownloadURL(
			fileEntry, fileVersion, themeDisplay, StringPool.BLANK, false,
			true);
	}

	@Override
	public String getIconFileMimeType() {
		if (_dlMimeTypeDisplayContext == null) {
			return "document-default";
		}

		return _dlMimeTypeDisplayContext.getIconFileMimeType(
			_fileVersion.getMimeType());
	}

	@Override
	public Menu getMenu() throws PortalException {
		Menu menu = new Menu();

		menu.setDirection("left-side");
		menu.setMarkupView("lexicon");
		menu.setMenuItems(_getMenuItems());
		menu.setMessage(LanguageUtil.get(_resourceBundle, "actions"));
		menu.setScroll(false);
		menu.setShowWhenSingleIcon(true);

		return menu;
	}

	@Override
	public List<ToolbarItem> getToolbarItems() throws PortalException {
		List<ToolbarItem> toolbarItems = new ArrayList<>();

		_uiItemsBuilder.addCollectDigitalSignatureToolbarItem(toolbarItems);

		_uiItemsBuilder.addDownloadToolbarItem(toolbarItems);

		_uiItemsBuilder.addEditToolbarItem(toolbarItems);

		_uiItemsBuilder.addCheckoutToolbarItem(toolbarItems);

		_uiItemsBuilder.addCancelCheckoutToolbarItem(toolbarItems);

		_uiItemsBuilder.addCheckinToolbarItem(toolbarItems);

		_uiItemsBuilder.addMoveToolbarItem(toolbarItems);

		_uiItemsBuilder.addPermissionsToolbarItem(toolbarItems);

		_uiItemsBuilder.addMoveToTheRecycleBinToolbarItem(toolbarItems);

		_uiItemsBuilder.addDeleteToolbarItem(toolbarItems);

		return toolbarItems;
	}

	@Override
	public UUID getUuid() {
		return _UUID;
	}

	@Override
	public boolean hasCustomThumbnail() {
		if (_dlPreviewRendererProvider != null) {
			DLPreviewRenderer dlPreviewRenderer =
				_dlPreviewRendererProvider.getThumbnailDLPreviewRenderer(
					_fileVersion);

			if (dlPreviewRenderer != null) {
				return true;
			}
		}

		return false;
	}

	@Override
	public boolean hasPreview() {
		if (_dlPreviewRendererProvider != null) {
			DLPreviewRenderer dlPreviewRenderer =
				_dlPreviewRendererProvider.getPreviewDLPreviewRenderer(
					_fileVersion);

			if (dlPreviewRenderer != null) {
				return true;
			}
		}

		return false;
	}

	@Override
	public boolean isActionsVisible() {
		if (_dlPortletInstanceSettingsHelper.isShowActions()) {
			return true;
		}

		return false;
	}

	@Override
	public boolean isDownloadLinkVisible() throws PortalException {
		return _fileEntryDisplayContextHelper.isDownloadActionAvailable();
	}

	@Override
	public boolean isSharingLinkVisible() {
		return false;
	}

	@Override
	public boolean isVersionInfoVisible() {
		return true;
	}

	@Override
	public void renderCustomThumbnail(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException, ServletException {

		DLPreviewRenderer dlPreviewRenderer = null;

		if (_dlPreviewRendererProvider != null) {
			dlPreviewRenderer =
				_dlPreviewRendererProvider.getThumbnailDLPreviewRenderer(
					_fileVersion);
		}

		_renderPreview(
			httpServletRequest, httpServletResponse, dlPreviewRenderer);
	}

	@Override
	public void renderPreview(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException, ServletException {

		DLPreviewRenderer dlPreviewRenderer = null;

		if (_dlPreviewRendererProvider != null) {
			dlPreviewRenderer =
				_dlPreviewRendererProvider.getPreviewDLPreviewRenderer(
					_fileVersion);
		}

		_renderPreview(
			httpServletRequest, httpServletResponse, dlPreviewRenderer);
	}

	private DefaultDLViewFileVersionDisplayContext(
		HttpServletRequest httpServletRequest, FileVersion fileVersion,
		FileShortcut fileShortcut,
		DLMimeTypeDisplayContext dlMimeTypeDisplayContext,
		ResourceBundle resourceBundle, StorageEngine storageEngine,
		DLTrashHelper dlTrashHelper,
		DLPreviewRendererProvider dlPreviewRendererProvider,
		VersioningStrategy versioningStrategy, DLURLHelper dlURLHelper) {

		try {
			_httpServletRequest = httpServletRequest;
			_fileVersion = fileVersion;
			_dlMimeTypeDisplayContext = dlMimeTypeDisplayContext;
			_resourceBundle = resourceBundle;
			_storageEngine = storageEngine;
			_dlPreviewRendererProvider = dlPreviewRendererProvider;

			DLRequestHelper dlRequestHelper = new DLRequestHelper(
				httpServletRequest);

			_dlPortletInstanceSettingsHelper =
				new DLPortletInstanceSettingsHelper(dlRequestHelper);

			FileEntry fileEntry = _getFileEntry(fileVersion);

			_fileEntryDisplayContextHelper = new FileEntryDisplayContextHelper(
				dlRequestHelper.getPermissionChecker(), fileEntry);

			_fileVersionDisplayContextHelper =
				new FileVersionDisplayContextHelper(fileVersion);

			if (fileShortcut == null) {
				_uiItemsBuilder = new UIItemsBuilder(
					httpServletRequest, fileEntry, fileVersion, _resourceBundle,
					dlTrashHelper, versioningStrategy, dlURLHelper);
			}
			else {
				_uiItemsBuilder = new UIItemsBuilder(
					httpServletRequest, fileShortcut, _resourceBundle,
					dlTrashHelper, versioningStrategy, dlURLHelper);
			}
		}
		catch (PortalException portalException) {
			throw new SystemException(
				"Unable to build DefaultDLViewFileVersionDisplayContext for " +
					fileVersion,
				portalException);
		}
	}

	private FileEntry _getFileEntry(FileVersion fileVersion)
		throws PortalException {

		if (fileVersion != null) {
			return fileVersion.getFileEntry();
		}

		return null;
	}

	private String _getFriendlyURL(long fileEntryId) throws PortalException {
		if (!FFFriendlyURLEntryFileEntryConfigurationUtil.enabled() ||
			(fileEntryId == 0)) {

			return null;
		}

		FriendlyURLEntry friendlyURLEntry =
			FriendlyURLEntryLocalServiceUtil.fetchMainFriendlyURLEntry(
				PortalUtil.getClassNameId(FileEntry.class), fileEntryId);

		if (friendlyURLEntry == null) {
			return null;
		}

		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		StringBundler sb = new StringBundler(6);

		sb.append(themeDisplay.getPortalURL());

		sb.append("/documents");

		String urlSeparatorFileEntry =
			FriendlyURLResolverConstants.URL_SEPARATOR_FILE_ENTRY;

		sb.append(
			urlSeparatorFileEntry.substring(
				0, urlSeparatorFileEntry.length() - 1));

		Group group = themeDisplay.getScopeGroup();

		sb.append(group.getFriendlyURL());

		sb.append(StringPool.SLASH);
		sb.append(friendlyURLEntry.getUrlTitle());

		return sb.toString();
	}

	private List<MenuItem> _getMenuItems() throws PortalException {
		List<MenuItem> menuItems = new ArrayList<>();

		if (isActionsVisible()) {
			_uiItemsBuilder.addDownloadMenuItem(menuItems);

			_uiItemsBuilder.addViewOriginalFileMenuItem(menuItems);

			_uiItemsBuilder.addEditMenuItem(menuItems);

			_uiItemsBuilder.addEditImageItem(menuItems);

			_uiItemsBuilder.addCheckoutMenuItem(menuItems);

			_uiItemsBuilder.addCancelCheckoutMenuItem(menuItems);

			_uiItemsBuilder.addCheckinMenuItem(menuItems);

			_uiItemsBuilder.addCollectDigitalSignatureMenuItem(menuItems);

			_uiItemsBuilder.addMoveMenuItem(menuItems);

			MenuItem menuItem = null;

			if (!menuItems.isEmpty()) {
				menuItem = menuItems.get(menuItems.size() - 1);
			}

			_uiItemsBuilder.addPermissionsMenuItem(menuItems);

			_uiItemsBuilder.addDeleteMenuItem(menuItems);

			_uiItemsBuilder.addPublishMenuItem(menuItems, true);

			if ((menuItem != null) &&
				(menuItem != menuItems.get(menuItems.size() - 1))) {

				menuItem.setSeparator(true);
			}
		}

		return menuItems;
	}

	private void _handleError(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, Exception exception)
		throws IOException, ServletException {

		JSPRenderer jspRenderer = new JSPRenderer(
			"/document_library/view_file_entry_preview_error.jsp");

		jspRenderer.setAttribute(
			WebKeys.DOCUMENT_LIBRARY_FILE_VERSION, _fileVersion);

		if (exception != null) {
			jspRenderer.setAttribute(
				DLWebKeys.DOCUMENT_LIBRARY_PREVIEW_EXCEPTION, exception);
		}

		jspRenderer.render(httpServletRequest, httpServletResponse);
	}

	private void _renderPreview(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse,
			DLPreviewRenderer dlPreviewRenderer)
		throws IOException, ServletException {

		try {
			if (dlPreviewRenderer == null) {
				_handleError(httpServletRequest, httpServletResponse, null);

				return;
			}

			dlPreviewRenderer.render(httpServletRequest, httpServletResponse);
		}
		catch (Exception exception) {
			if (exception instanceof DLFileEntryPreviewGenerationException ||
				exception instanceof DLPreviewGenerationInProcessException ||
				exception instanceof DLPreviewSizeException) {

				if (_log.isWarnEnabled()) {
					_log.warn(exception);
				}
			}
			else {
				_log.error(
					"Unable to render preview for file version: " +
						_fileVersion.getTitle(),
					exception);
			}

			_handleError(httpServletRequest, httpServletResponse, exception);
		}
	}

	private static final UUID _UUID = UUID.fromString(
		"85F6C50E-3893-4E32-9D63-208528A503FA");

	private static final Log _log = LogFactoryUtil.getLog(
		DefaultDLViewFileVersionDisplayContext.class);

	private List<DDMStructure> _ddmStructures;
	private final DLMimeTypeDisplayContext _dlMimeTypeDisplayContext;
	private final DLPortletInstanceSettingsHelper
		_dlPortletInstanceSettingsHelper;
	private DLPreviewRendererProvider _dlPreviewRendererProvider;
	private final FileEntryDisplayContextHelper _fileEntryDisplayContextHelper;
	private final FileVersion _fileVersion;
	private final FileVersionDisplayContextHelper
		_fileVersionDisplayContextHelper;
	private HttpServletRequest _httpServletRequest;
	private final ResourceBundle _resourceBundle;
	private final StorageEngine _storageEngine;
	private final UIItemsBuilder _uiItemsBuilder;

}