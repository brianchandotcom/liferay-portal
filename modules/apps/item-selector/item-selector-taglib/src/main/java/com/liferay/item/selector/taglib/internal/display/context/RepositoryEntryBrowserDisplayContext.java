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

package com.liferay.item.selector.taglib.internal.display.context;

import com.liferay.document.library.constants.DLContentTypes;
import com.liferay.document.library.kernel.util.ImageProcessorUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.permission.GroupPermissionUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.util.PropsValues;

import java.util.Locale;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Adolfo Pérez
 */
public class RepositoryEntryBrowserDisplayContext {

	public RepositoryEntryBrowserDisplayContext(
		HttpServletRequest httpServletRequest) {

		_httpServletRequest = httpServletRequest;
	}

	public String getGroupCssIcon(long groupId) throws PortalException {
		Group group = _getGroup(groupId);

		return group.getIconCssClass();
	}

	public String getGroupLabel(long groupId, Locale locale)
		throws PortalException {

		Group group = _getGroup(groupId);

		return group.getDescriptiveName(locale);
	}

	public String getType(FileVersion fileVersion) {
		if (fileVersion == null) {
			return StringPool.BLANK;
		}

		if (ArrayUtil.contains(
				PropsValues.DL_FILE_ENTRY_PREVIEW_AUDIO_MIME_TYPES,
				fileVersion.getMimeType())) {

			return "audio";
		}

		if (ImageProcessorUtil.isSupported(fileVersion.getMimeType())) {
			return "image";
		}

		if (ArrayUtil.contains(
				PropsValues.DL_FILE_ENTRY_PREVIEW_VIDEO_MIME_TYPES,
				fileVersion.getMimeType()) ||
			Objects.equals(
				DLContentTypes.VIDEO_EXTERNAL_SHORTCUT,
				fileVersion.getMimeType())) {

			return "video";
		}

		return StringPool.BLANK;
	}

	public boolean isPreviewable(FileVersion fileVersion) {
		if (fileVersion == null) {
			return false;
		}

		if (ArrayUtil.contains(
				PropsValues.DL_FILE_ENTRY_PREVIEW_VIDEO_MIME_TYPES,
				fileVersion.getMimeType()) ||
			ImageProcessorUtil.isImageSupported(fileVersion.getMimeType()) ||
			Objects.equals(
				DLContentTypes.VIDEO_EXTERNAL_SHORTCUT,
				fileVersion.getMimeType())) {

			return true;
		}

		return false;
	}

	public boolean isSearchEverywhere() {
		if (_searchEverywhere != null) {
			return _searchEverywhere;
		}

		if (Objects.equals(
				ParamUtil.getString(_httpServletRequest, "scope"),
				"everywhere")) {

			_searchEverywhere = true;
		}
		else {
			_searchEverywhere = false;
		}

		return _searchEverywhere;
	}

	private Group _getGroup(long groupId) throws PortalException {
		Group group = GroupLocalServiceUtil.getGroup(groupId);

		if (group.isCompany()) {
			return group;
		}

		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		GroupPermissionUtil.check(
			themeDisplay.getPermissionChecker(), group, ActionKeys.VIEW);

		return group;
	}

	private final HttpServletRequest _httpServletRequest;
	private Boolean _searchEverywhere;

}