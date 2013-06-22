/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.documentlibrary.asset;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.trash.TrashRenderer;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.asset.model.AssetRendererFactory;
import com.liferay.portlet.asset.model.BaseAssetRenderer;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFileEntryConstants;
import com.liferay.portlet.documentlibrary.service.permission.DLFileEntryPermission;
import com.liferay.portlet.documentlibrary.util.DLUtil;
import com.liferay.portlet.trash.util.TrashUtil;

import java.util.Date;
import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * @author Julio Camarero
 * @author Juan Fernández
 * @author Sergio González
 * @author Zsolt Berentey
 */
public class DLFileEntryAssetRenderer
	extends BaseAssetRenderer implements TrashRenderer {

	public DLFileEntryAssetRenderer(
		FileEntry fileEntry, FileVersion fileVersion, int type) {

		_fileEntry = fileEntry;
		_fileVersion = fileVersion;
		_type = type;
	}

	@Override
	public String getClassName() {
		return DLFileEntry.class.getName();
	}

	@Override
	public long getClassPK() {
		if (!_fileVersion.isApproved() && _fileVersion.isDraft() &&
			!_fileVersion.isPending() &&
			!_fileVersion.getVersion().equals(
				DLFileEntryConstants.VERSION_DEFAULT)) {

			return _fileVersion.getFileVersionId();
		}
		else {
			return _fileEntry.getFileEntryId();
		}
	}

	@Override
	public String getDiscussionPath() {
		if (PropsValues.DL_FILE_ENTRY_COMMENTS_ENABLED) {
			return "edit_file_entry_discussion";
		}
		else {
			return null;
		}
	}

	@Override
	public Date getDisplayDate() {
		return _fileEntry.getModifiedDate();
	}

	@Override
	public long getGroupId() {
		return _fileEntry.getGroupId();
	}

	@Override
	public String getIconPath(ThemeDisplay themeDisplay) {
		return themeDisplay.getPathThemeImages() + "/file_system/small/" +
			_fileEntry.getIcon() + ".png";
	}

	@Override
	public String getNewName(String oldName, String token) {
		String extension = FileUtil.getExtension(oldName);

		if (Validator.isNull(extension)) {
			return super.getNewName(oldName, token);
		}

		StringBundler sb = new StringBundler(5);

		int pos = oldName.lastIndexOf(CharPool.PERIOD);

		sb.append(oldName.substring(0, pos));
		sb.append(StringPool.SPACE);
		sb.append(token);
		sb.append(StringPool.PERIOD);
		sb.append(extension);

		return sb.toString();
	}

	@Override
	public String getPortletId() {
		AssetRendererFactory assetRendererFactory = getAssetRendererFactory();

		return assetRendererFactory.getPortletId();
	}

	@Override
	public String getSummary(Locale locale) {
		return HtmlUtil.stripHtml(_fileEntry.getDescription());
	}

	@Override
	public String getThumbnailPath(PortletRequest portletRequest)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String thumbnailSrc = DLUtil.getThumbnailSrc(
			_fileEntry, null, themeDisplay);

		if (Validator.isNotNull(thumbnailSrc)) {
			return thumbnailSrc;
		}

		return themeDisplay.getPathThemeImages() +
			"/file_system/large/document.png";
	}

	@Override
	public String getTitle(Locale locale) {
		String title = null;

		if (_type == AssetRendererFactory.TYPE_LATEST) {
			title = _fileVersion.getTitle();
		}
		else {
			title = _fileEntry.getTitle();
		}

		return TrashUtil.getOriginalTitle(title);
	}

	@Override
	public String getType() {
		return DLFileEntryAssetRendererFactory.TYPE;
	}

	@Override
	public String getURLDownload(ThemeDisplay themeDisplay) {
		return DLUtil.getPreviewURL(
			_fileEntry, _fileVersion, themeDisplay, StringPool.BLANK);
	}

	@Override
	public PortletURL getURLEdit(
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse)
		throws Exception {

		PortletURL portletURL = liferayPortletResponse.createLiferayPortletURL(
			getControlPanelPlid(liferayPortletRequest),
			PortletKeys.DOCUMENT_LIBRARY, PortletRequest.RENDER_PHASE);

		portletURL.setParameter(
			"struts_action", "/document_library/edit_file_entry");
		portletURL.setParameter(
			"fileEntryId", String.valueOf(_fileEntry.getFileEntryId()));

		return portletURL;
	}

	@Override
	public PortletURL getURLExport(
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse) {

		PortletURL portletURL = liferayPortletResponse.createActionURL();

		portletURL.setParameter("struts_action", "/asset_publisher/get_file");
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
		throws PortalException, SystemException {

		return DLFileEntryPermission.contains(
			permissionChecker, _fileEntry.getFileEntryId(), ActionKeys.DELETE);
	}

	@Override
	public boolean hasEditPermission(PermissionChecker permissionChecker)
		throws PortalException, SystemException {

		return DLFileEntryPermission.contains(
			permissionChecker, _fileEntry.getFileEntryId(), ActionKeys.UPDATE);
	}

	@Override
	public boolean hasViewPermission(PermissionChecker permissionChecker)
		throws PortalException, SystemException {

		return DLFileEntryPermission.contains(
			permissionChecker, _fileEntry.getFileEntryId(), ActionKeys.VIEW);
	}

	@Override
	public boolean isConvertible() {
		return true;
	}

	@Override
	public boolean isPrintable() {
		return false;
	}

	@Override
	public String render(
			RenderRequest renderRequest, RenderResponse renderResponse,
			String template)
		throws Exception {

		if (template.equals(TEMPLATE_ABSTRACT) ||
			template.equals(TEMPLATE_FULL_CONTENT)) {

			renderRequest.setAttribute(
				WebKeys.DOCUMENT_LIBRARY_FILE_ENTRY, _fileEntry);

			String version = ParamUtil.getString(renderRequest, "version");

			if ((_type == AssetRendererFactory.TYPE_LATEST) ||
				Validator.isNotNull(version)) {

				if ((_fileEntry != null) && Validator.isNotNull(version)) {
					_fileVersion = _fileEntry.getFileVersion(version);
				}

				renderRequest.setAttribute(
					WebKeys.DOCUMENT_LIBRARY_FILE_VERSION, _fileVersion);
			}

			return "/html/portlet/document_library/asset/file_entry_" +
				template + ".jsp";
		}
		else {
			return null;
		}
	}

	private FileEntry _fileEntry;
	private FileVersion _fileVersion;
	private int _type;

}