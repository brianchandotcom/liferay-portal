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

package com.liferay.document.library.web.internal.servlet.taglib.clay;

import com.liferay.document.library.web.internal.display.context.DLViewDisplayContext;
import com.liferay.frontend.taglib.clay.servlet.taglib.VerticalCard;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.WebKeys;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class FileEntryTemplateVerticalCard implements VerticalCard {

	public FileEntryTemplateVerticalCard(
		DLViewDisplayContext dlViewDisplayContext,
		HttpServletRequest httpServletRequest) {

		_dlViewDisplayContext = dlViewDisplayContext;

		_themeDisplay = (ThemeDisplay)httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	@Override
	public String getCssClass() {
		return "card-type-asset display-icon entry-display-style file-card " +
			"form-check form-check-card";
	}

	@Override
	public String getHref() {
		try {
			return _dlViewDisplayContext.getUploadURL();
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}
		}

		return StringPool.BLANK;
	}

	@Override
	public String getIcon() {
		return "documents-and-media";
	}

	@Override
	public String getStickerCssClass() {
		return "file-icon-color-0 sticker-bottom-left sticker-document";
	}

	@Override
	public String getStickerIcon() {
		return "document-default";
	}

	@Override
	public String getSubtitle() {
		User user = _themeDisplay.getUser();

		return LanguageUtil.format(
			_themeDisplay.getLocale(), "right-now-by-x",
			HtmlUtil.escape(user.getFullName()));
	}

	@Override
	public String getTitle() {
		return "{title}";
	}

	@Override
	public boolean isSelectable() {
		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FileEntryTemplateVerticalCard.class);

	private final DLViewDisplayContext _dlViewDisplayContext;
	private final ThemeDisplay _themeDisplay;

}