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

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderConstants;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.sharing.model.SharingEntry;
import com.liferay.sharing.service.SharingEntryLocalService;
import com.liferay.sharing.web.internal.constants.SharingPortletKeys;
import com.liferay.sharing.web.internal.display.context.SharedWithMeViewDisplayContext;
import com.liferay.sharing.web.interpreter.SharingEntryInterpreter;
import com.liferay.sharing.web.interpreter.SharingEntryInterpreterProvider;
import com.liferay.sharing.web.renderer.SharingEntryViewRenderer;

import java.util.Objects;

import javax.portlet.PortletException;
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
		"mvc.command.name=/", "mvc.command.name=/shared_with_me/view",
		"mvc.command.name=/shared_with_me/view_sharing_entry"
	},
	service = MVCRenderCommand.class
)
public class SharedWithMeViewMVCRenderCommand implements MVCRenderCommand {

	@Override
	public String render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortletException {

		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		if (Objects.equals(
				renderRequest.getParameter("mvcRenderCommandName"),
				"/shared_with_me/view_sharing_entry")) {

			long sharingEntryId = ParamUtil.getLong(
				renderRequest, "sharingEntryId");

			try {
				SharingEntry sharingEntry =
					_sharingEntryLocalService.getSharingEntry(sharingEntryId);

				if (sharingEntry.getToUserId() != themeDisplay.getUserId()) {
					throw new PrincipalException(
						StringBundler.concat(
							"User ", themeDisplay.getUserId(),
							" does not have permission to view sharing entry ",
							sharingEntryId));
				}

				SharingEntryInterpreter<Object> sharingEntryInterpreter =
					_sharingEntryInterpreterProvider.getSharingEntryInterpreter(
						sharingEntry);

				if (sharingEntryInterpreter == null) {
					throw new PortletException(
						"Sharing entry interpreter is null for class name ID " +
							sharingEntry.getClassNameId());
				}

				SharingEntryViewRenderer<Object> sharingEntryViewRenderer =
					sharingEntryInterpreter.getSharingEntryViewRenderer();

				sharingEntryViewRenderer.render(
					sharingEntryInterpreter.getEntry(sharingEntry),
					_portal.getHttpServletRequest(renderRequest),
					_portal.getHttpServletResponse(renderResponse));

				return MVCRenderConstants.MVC_PATH_VALUE_SKIP_DISPATCH;
			}
			catch (Exception e) {
				throw new PortletException(e);
			}
		}

		SharedWithMeViewDisplayContext sharedWithMeViewDisplayContext =
			new SharedWithMeViewDisplayContext(
				themeDisplay, _sharingEntryLocalService,
				_sharingEntryInterpreterProvider::getSharingEntryInterpreter);

		renderRequest.setAttribute(
			SharedWithMeViewDisplayContext.class.getName(),
			sharedWithMeViewDisplayContext);

		return "/shared_with_me/view.jsp";
	}

	@Reference
	private Portal _portal;

	@Reference
	private SharingEntryInterpreterProvider _sharingEntryInterpreterProvider;

	@Reference
	private SharingEntryLocalService _sharingEntryLocalService;

}