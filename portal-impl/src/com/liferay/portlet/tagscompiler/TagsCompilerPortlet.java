/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.tagscompiler;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.LiferayPortlet;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.RenderParametersPool;
import com.liferay.portlet.tagscompiler.util.TagsCompilerSessionUtil;

import java.util.Collection;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Brian Wing Shun Chan
 */
public class TagsCompilerPortlet extends LiferayPortlet {

	@Override
	public void render(
		RenderRequest renderRequest, RenderResponse renderResponse) {

		// Compile entries

		String entriesFromURL = ParamUtil.getString(renderRequest, "entries");
		String[] entriesFromURLArray = StringUtil.split(entriesFromURL);

		if (_log.isDebugEnabled()) {
			_log.debug("Entries from friendly URL " + entriesFromURL);
		}

		Collection<String> entriesFromSession =
			TagsCompilerSessionUtil.getEntries(renderRequest);

		String[] entries =
			new String[entriesFromURLArray.length + entriesFromSession.size()];

		System.arraycopy(
			entriesFromURLArray, 0, entries, 0, entriesFromURLArray.length);

		int index = entriesFromURLArray.length;

		for (String entry : entriesFromSession) {
			entries[index++] = entry;
		}

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Entries from session " +
					StringUtil.merge(entriesFromSession.toArray()));
		}

		renderRequest.setAttribute(WebKeys.TAGS_COMPILER_ENTRIES, entries);

		// Clear render parameters cache

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			renderRequest);

		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		RenderParametersPool.clear(
			request, themeDisplay.getPlid(), PortletKeys.TAGS_COMPILER);
	}

	private static Log _log = LogFactoryUtil.getLog(TagsCompilerPortlet.class);

}