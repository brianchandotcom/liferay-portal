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

package com.liferay.document.library.web.internal.asset;

import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.model.BaseJSPAssetRenderer;
import com.liferay.asset.kernel.model.DDMFormValuesReader;
import com.liferay.document.library.constants.DLPortletKeys;
import com.liferay.document.library.kernel.document.conversion.DocumentConversionUtil;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFileEntryConstants;
import com.liferay.document.library.kernel.service.DLFileEntryLocalService;
import com.liferay.document.library.kernel.service.DLFileEntryLocalServiceUtil;
import com.liferay.document.library.kernel.util.DLUtil;
import com.liferay.document.library.web.internal.security.permission.resource.DLFileEntryPermission;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.trash.TrashRenderer;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.documentlibrary.asset.DLFileEntryDDMFormValuesReader;
import com.liferay.trash.TrashHelper;

import java.util.Date;
import java.util.Locale;

import javax.portlet.ActionRequest;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletURL;
import javax.portlet.WindowState;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Julio Camarero
 * @author Juan Fernández
 * @author Sergio González
 * @author Zsolt Berentey
 */
public class DLFileEntryAssetRenderer
	extends BaseJSPAssetRenderer<FileEntry> implements TrashRenderer {

	/**
	 * @deprecated As of Judson (7.1.x)
	 */
	@Deprecated
	public DLFileEntryAssetRenderer(
		FileEntry fileEntry, FileVersion fileVersion) {

		this(fileEntry, fileVersion, DLFileEntryLocalServiceUtil.getService());
	}

	/**
	 * @deprecated As of Judson (7.1.x)
	 */
	@Deprecated
	public DLFileEntryAssetRenderer(
		FileEntry fileEntry, FileVersion fileVersion,
		DLFileEntryLocalService dlFileEntryLocalService) {

		this(fileEntry, fileVersion, dlFileEntryLocalService, null);
	}

	public DLFileEntryAssetRenderer(
		FileEntry fileEntry, FileVersion fileVersion,
		DLFileEntryLocalService dlFileEntryLocalService,
		TrashHelper trashHelper) {

		_fileEntry = fileEntry;
		_fileVersion = fileVersion;
		_dlFileEntryLocalService = dlFileEntryLocalService;
		_trashHelper = trashHelper;
	}

	@Override
	public FileEntry getAssetObject() {
		return _fileEntry;
	}

	@Override
	public String getClassName() {
		return DLFileEntry.class.getName();
	}

	@Override
	public long getClassPK() {
		String version = _fileVersion.getVersion();

		if (!_fileVersion.isApproved() && _fileVersion.isDraft() &&
			!_fileVersion.isPending() &&
			!version.equals(DLFileEntryConstants.VERSION_DEFAULT)) {

			return _fileVersion.getFileVersionId();
		}

		return _fileEntry.getFileEntryId();
	}

	@Override
	public DDMFormValuesReader getDDMFormValuesReader() {
		return new DLFileEntryDDMFormValuesReader(_fileEntry, _fileVersion);
	}

	@Override
	public String getDiscussionPath() {
		if (PropsValues.DL_FILE_ENTRY_COMMENTS_ENABLED) {
			return "edit_file_entry_discussion";
		}

		return null;
	}

	/**
	 * @deprecated As of Judson (7.1.x), with no direct replacement
	 */
	@Deprecated
	@Override
	public Date getDisplayDate() {
		return _fileEntry.getModifiedDate();
	}

	@Override
	public long getGroupId() {
		return _fileEntry.getGroupId();
	}

	@Override
	public String getIconCssClass() {
		return _fileEntry.getIconCssClass();
	}

	@Override
	public String getJspPath(HttpServletRequest request, String template) {
		if (template.equals(TEMPLATE_ABSTRACT) ||
			template.equals(TEMPLATE_FULL_CONTENT)) {

			return "/document_library/asset/file_entry_" + template + ".jsp";
		}

		return null;
	}

	@Override
	public String getNewName(String oldName, String token) {
		String extension = FileUtil.getExtension(oldName);

		if (Validator.isNull(extension)) {
			return super.getNewName(oldName, token);
		}

		StringBundler sb = new StringBundler(5);

		int index = oldName.lastIndexOf(CharPool.PERIOD);

		sb.append(oldName.substring(0, index));

		sb.append(StringPool.SPACE);
		sb.append(token);
		sb.append(StringPool.PERIOD);
		sb.append(extension);

		return sb.toString();
	}

	@Override
	public String getPortletId() {
		AssetRendererFactory<FileEntry> assetRendererFactory =
			getAssetRendererFactory();

		return assetRendererFactory.getPortletId();
	}

	@Override
	public int getStatus() {
		return _fileVersion.getStatus();
	}

	@Override
	public String getSummary(
		PortletRequest portletRequest, PortletResponse portletResponse) {

		return _fileEntry.getDescription();
	}

	@Override
	public String[] getSupportedConversions() {
		return DocumentConversionUtil.getConversions(_fileEntry.getExtension());
	}

	@Override
	public String getThumbnailPath(PortletRequest portletRequest)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String thumbnailSrc = DLUtil.getThumbnailSrc(_fileEntry, themeDisplay);

		if (Validator.isNotNull(thumbnailSrc)) {
			return thumbnailSrc;
		}

		return super.getThumbnailPath(portletRequest);
	}

	@Override
	public String getTitle(Locale locale) {
		String title = null;

		if (getAssetRendererType() == AssetRendererFactory.TYPE_LATEST) {
			title = _fileVersion.getTitle();
		}
		else {
			title = _fileEntry.getTitle();
		}

		if (_trashHelper == null) {
			return title;
		}

		return _trashHelper.getOriginalTitle(title);
	}

	@Override
	public String getType() {
		return DLFileEntryAssetRendererFactory.TYPE;
	}

	@Override
	public String getURLDownload(ThemeDisplay themeDisplay) {
		return DLUtil.getDownloadURL(
			_fileEntry, _fileVersion, themeDisplay, StringPool.BLANK);
	}

	@Override
	public PortletURL getURLEdit(
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse)
		throws Exception {

		Group group = GroupLocalServiceUtil.fetchGroup(_fileEntry.getGroupId());

		if (group.isCompany()) {
			ThemeDisplay themeDisplay =
				(ThemeDisplay)liferayPortletRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			group = themeDisplay.getScopeGroup();
		}

		PortletURL portletURL = PortalUtil.getControlPanelPortletURL(
			liferayPortletRequest, group, DLPortletKeys.DOCUMENT_LIBRARY, 0, 0,
			PortletRequest.RENDER_PHASE);

		portletURL.setParameter(
			"mvcRenderCommandName", "/document_library/edit_file_entry");
		portletURL.setParameter(
			"fileEntryId", String.valueOf(_fileEntry.getFileEntryId()));

		return portletURL;
	}

	@Override
	public PortletURL getURLExport(
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse)
		throws Exception {

		PortletURL portletURL = PortalUtil.getControlPanelPortletURL(
			liferayPortletRequest, DLPortletKeys.DOCUMENT_LIBRARY,
			PortletRequest.ACTION_PHASE);

		portletURL.setParameter(
			ActionRequest.ACTION_NAME, "/document_library/get_file");
		portletURL.setParameter(
			"groupId", String.valueOf(_fileEntry.getRepositoryId()));
		portletURL.setParameter(
			"folderId", String.valueOf(_fileEntry.getFolderId()));
		portletURL.setParameter("title", String.valueOf(_fileEntry.getTitle()));

		return portletURL;
	}

	@Override
	public String getURLImagePreview(PortletRequest portletRequest)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		return DLUtil.getImagePreviewURL(
			_fileEntry, _fileVersion, themeDisplay);
	}

	@Override
	public String getURLView(
			LiferayPortletResponse liferayPortletResponse,
			WindowState windowState)
		throws Exception {

		AssetRendererFactory<FileEntry> assetRendererFactory =
			getAssetRendererFactory();

		PortletURL portletURL = assetRendererFactory.getURLView(
			liferayPortletResponse, windowState);

		portletURL.setParameter(
			"mvcRenderCommandName", "/document_library/view_file_entry");
		portletURL.setParameter(
			"fileEntryId", String.valueOf(_fileEntry.getFileEntryId()));
		portletURL.setWindowState(windowState);

		return portletURL.toString();
	}

	@Override
	public String getURLViewInContext(
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse,
		String noSuchEntryRedirect) {

		return getURLViewInContext(
			liferayPortletRequest, noSuchEntryRedirect,
			"/document_library/find_file_entry", "fileEntryId",
			_fileEntry.getFileEntryId());
	}

	@Override
	public long getUserId() {
		return _fileEntry.getUserId();
	}

	@Override
	public String getUserName() {
		return _fileEntry.getUserName();
	}

	@Override
	public String getUuid() {
		return _fileEntry.getUuid();
	}

	public boolean hasDeletePermission(PermissionChecker permissionChecker)
		throws PortalException {

		return DLFileEntryPermission.contains(
			permissionChecker, _fileEntry.getFileEntryId(), ActionKeys.DELETE);
	}

	@Override
	public boolean hasEditPermission(PermissionChecker permissionChecker)
		throws PortalException {

		return DLFileEntryPermission.contains(
			permissionChecker, _fileEntry.getFileEntryId(), ActionKeys.UPDATE);
	}

	@Override
	public boolean hasViewPermission(PermissionChecker permissionChecker)
		throws PortalException {

		return DLFileEntryPermission.contains(
			permissionChecker, _fileEntry.getFileEntryId(), ActionKeys.VIEW);
	}

	@Override
	public boolean include(
			HttpServletRequest request, HttpServletResponse response,
			String template)
		throws Exception {

		request.setAttribute(WebKeys.DOCUMENT_LIBRARY_FILE_ENTRY, _fileEntry);

		String version = ParamUtil.getString(request, "version");

		if ((getAssetRendererType() == AssetRendererFactory.TYPE_LATEST) ||
			Validator.isNotNull(version)) {

			if ((_fileEntry != null) && Validator.isNotNull(version)) {
				_fileVersion = _fileEntry.getFileVersion(version);
			}

			request.setAttribute(
				WebKeys.DOCUMENT_LIBRARY_FILE_VERSION, _fileVersion);
		}
		else {
			request.setAttribute(
				WebKeys.DOCUMENT_LIBRARY_FILE_VERSION,
				_fileEntry.getFileVersion());
		}

		return super.include(request, response, template);
	}

	@Override
	public boolean isCategorizable(long groupId) {
		long classPK = getClassPK();

		DLFileEntry dlFileEntry = _dlFileEntryLocalService.fetchDLFileEntry(
			classPK);

		if ((dlFileEntry == null) ||
			(dlFileEntry.getRepositoryId() != groupId)) {

			return false;
		}

		return super.isCategorizable(groupId);
	}

	@Override
	public boolean isConvertible() {
		return true;
	}

	@Override
	public boolean isPrintable() {
		return false;
	}

	private final DLFileEntryLocalService _dlFileEntryLocalService;
	private final FileEntry _fileEntry;
	private FileVersion _fileVersion;
	private final TrashHelper _trashHelper;

}