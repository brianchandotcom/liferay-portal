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

package com.liferay.content.dashboard.blogs.internal.item.action;

import com.liferay.blogs.model.BlogsEntry;
import com.liferay.content.dashboard.item.action.ContentDashboardItemAction;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.Portal;

import java.util.Locale;

import javax.portlet.PortletResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Cristina González
 */
public class SharingCollaboratorsBlogsEntryContentDashboardItemAction
	implements ContentDashboardItemAction {

	public SharingCollaboratorsBlogsEntryContentDashboardItemAction(
		BlogsEntry blogsEntry, HttpServletRequest httpServletRequest,
		Language language, Portal portal) {

		_blogsEntry = blogsEntry;
		_httpServletRequest = httpServletRequest;
		_language = language;
		_portal = portal;
	}

	@Override
	public String getIcon() {
		return "share";
	}

	@Override
	public String getLabel(Locale locale) {
		return _language.get(locale, "share");
	}

	@Override
	public String getName() {
		return "share";
	}

	@Override
	public Type getType() {
		return Type.SHARING_COLLABORATORS;
	}

	@Override
	public String getURL() {
		try {
			PortletResponse portletResponse =
				(PortletResponse)_httpServletRequest.getAttribute(
					JavaConstants.JAVAX_PORTLET_RESPONSE);

			return PortletURLBuilder.createRenderURL(
				_portal.getLiferayPortletResponse(portletResponse),
				"com_liferay_content_dashboard_web_portlet_" +
					"ContentDashboardAdminPortlet"
			).setMVCPath(
				"/sharing_collaborators.jsp"
			).setParameter(
				"className", BlogsEntry.class.getName()
			).setParameter(
				"classPK", _blogsEntry.getEntryId()
			).setWindowState(
				LiferayWindowState.EXCLUSIVE
			).buildString();
		}
		catch (Exception exception) {
			_log.error(exception);

			return StringPool.BLANK;
		}
	}

	@Override
	public String getURL(Locale locale) {
		return getURL();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SharingCollaboratorsBlogsEntryContentDashboardItemAction.class);

	private final BlogsEntry _blogsEntry;
	private final HttpServletRequest _httpServletRequest;
	private final Language _language;
	private final Portal _portal;

}