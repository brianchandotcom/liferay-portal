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

package com.liferay.commerce.user.segment.web.internal.servlet.taglib.ui;

import com.liferay.commerce.user.segment.constants.CommerceUserSegmentScreenNavigationConstants;
import com.liferay.commerce.user.segment.model.CommerceUserSegmentEntry;
import com.liferay.frontend.taglib.servlet.taglib.ScreenNavigationEntry;
import com.liferay.frontend.taglib.servlet.taglib.util.JSPRenderer;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;

import java.io.IOException;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	property = "screen.navigation.entry.order:Integer=30",
	service = ScreenNavigationEntry.class
)
public class CommerceUserSegmentEntryPreviewScreenNavigationEntry
	implements ScreenNavigationEntry<CommerceUserSegmentEntry> {

	@Override
	public String getCategoryKey() {
		return CommerceUserSegmentScreenNavigationConstants.
			CATEGORY_KEY_COMMERCE_USER_SEGMENT_DETAIL;
	}

	@Override
	public String getEntryKey() {
		return CommerceUserSegmentScreenNavigationConstants.
			ENTRY_KEY_COMMERCE_USER_SEGMENT_ENTRY_PREVIEW;
	}

	@Override
	public String getLabel(Locale locale) {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());

		return LanguageUtil.get(
			resourceBundle,
			CommerceUserSegmentScreenNavigationConstants.
				ENTRY_KEY_COMMERCE_USER_SEGMENT_ENTRY_PREVIEW);
	}

	@Override
	public String getScreenNavigationKey() {
		return CommerceUserSegmentScreenNavigationConstants.
			SCREEN_NAVIGATION_KEY_COMMERCE_USER_SEGMENT_ENTRY_GENERAL;
	}

	@Override
	public void render(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException {

		_jspRenderer.renderJSP(
			_servletContext, httpServletRequest, httpServletResponse,
			"/user_segment_entry/preview.jsp");
	}

	@Reference
	private JSPRenderer _jspRenderer;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.commerce.user.segment.web)"
	)
	private ServletContext _servletContext;

}