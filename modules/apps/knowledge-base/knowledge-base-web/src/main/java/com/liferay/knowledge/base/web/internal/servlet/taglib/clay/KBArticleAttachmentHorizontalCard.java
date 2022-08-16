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

package com.liferay.knowledge.base.web.internal.servlet.taglib.clay;

import com.liferay.frontend.taglib.clay.servlet.taglib.HorizontalCard;
import com.liferay.knowledge.base.web.internal.display.context.DLMimeTypeDisplayContextUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepositoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class KBArticleAttachmentHorizontalCard implements HorizontalCard {

	public KBArticleAttachmentHorizontalCard(
			FileEntry fileEntry, HttpServletRequest httpServletRequest)
		throws PortalException {

		_fileEntry = fileEntry;

		_fileVersion = fileEntry.getFileVersion();
		_themeDisplay = (ThemeDisplay)httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	@Override
	public String getCssClass() {
		return DLMimeTypeDisplayContextUtil.getCssClassFileMimeType(
			_fileVersion);
	}

	@Override
	public String getHref() {
		return PortletFileRepositoryUtil.getDownloadPortletFileEntryURL(
			_themeDisplay, _fileEntry,
			"status=" + WorkflowConstants.STATUS_APPROVED);
	}

	@Override
	public String getIcon() {
		return DLMimeTypeDisplayContextUtil.getIconFileMimeType(_fileVersion);
	}

	@Override
	public String getTitle() {
		return _fileEntry.getTitle();
	}

	@Override
	public boolean isSelectable() {
		return false;
	}

	private final FileEntry _fileEntry;
	private final FileVersion _fileVersion;
	private final ThemeDisplay _themeDisplay;

}