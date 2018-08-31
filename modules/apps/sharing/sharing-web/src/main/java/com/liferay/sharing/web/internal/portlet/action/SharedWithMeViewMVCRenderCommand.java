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

package com.liferay.sharing.web.internal.portlet.action;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.sharing.service.SharingEntryLocalService;
import com.liferay.sharing.web.internal.constants.SharingPortletKeys;
import com.liferay.sharing.web.internal.display.context.SharedWithMeViewDisplayContext;
import com.liferay.sharing.web.internal.interpreter.SharingEntryInterpreterTracker;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Sergio González
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + SharingPortletKeys.SHARED_WITH_ME,
		"mvc.command.name=/", "mvc.command.name=/shared_with_me/view"
	},
	service = MVCRenderCommand.class
)
public class SharedWithMeViewMVCRenderCommand implements MVCRenderCommand {

	@Override
	public String render(
		RenderRequest renderRequest, RenderResponse renderResponse) {

		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		SharedWithMeViewDisplayContext sharedWithMeViewDisplayContext =
			new SharedWithMeViewDisplayContext(
				themeDisplay, _sharingEntryLocalService,
				_sharingEntryInterpreterTracker);

		renderRequest.setAttribute(
			SharedWithMeViewDisplayContext.class.getName(),
			sharedWithMeViewDisplayContext);

		return "/shared_with_me/view.jsp";
	}

	@Reference
	private SharingEntryInterpreterTracker _sharingEntryInterpreterTracker;

	@Reference
	private SharingEntryLocalService _sharingEntryLocalService;

}