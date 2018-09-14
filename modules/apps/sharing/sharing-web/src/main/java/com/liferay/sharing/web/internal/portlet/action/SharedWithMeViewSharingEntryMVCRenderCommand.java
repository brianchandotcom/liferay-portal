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
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.sharing.interpreter.SharingEntryInterpreter;
import com.liferay.sharing.model.SharingEntry;
import com.liferay.sharing.service.SharingEntryLocalService;
import com.liferay.sharing.web.internal.constants.SharingPortletKeys;
import com.liferay.sharing.web.internal.interpreter.SharingEntryInterpreterTracker;

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
		"mvc.command.name=/shared_with_me/view_sharing_entry"
	},
	service = MVCRenderCommand.class
)
public class SharedWithMeViewSharingEntryMVCRenderCommand
	implements MVCRenderCommand {

	@Override
	public String render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortletException {

		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long sharingEntryId = ParamUtil.getLong(
			renderRequest, "sharingEntryId");

		try {
			SharingEntry sharingEntry =
				_sharingEntryLocalService.getSharingEntry(sharingEntryId);

			if (sharingEntry.getToUserId() != themeDisplay.getUserId()) {
				throw new PrincipalException(
					StringBundler.concat(
						"user id ", themeDisplay.getUserId(),
						" does not have permission to view sharing entry id ",
						sharingEntryId));
			}

			SharingEntryInterpreter<?> sharingEntryInterpreter =
				_sharingEntryInterpreterTracker.getSharingEntryInterpreter(
					sharingEntry.getClassNameId());

			if (sharingEntryInterpreter == null) {
				throw new PortletException(
					"sharing entry interpreter is null for class name id " +
						sharingEntry.getClassNameId());
			}

			renderRequest.setAttribute(
				SharingEntryInterpreter.class.getName(),
				sharingEntryInterpreter);

			return "/shared_with_me/view_sharing_entry.jsp";
		}
		catch (Exception e) {
			throw new PortletException(e);
		}
	}

	@Reference
	private SharingEntryInterpreterTracker _sharingEntryInterpreterTracker;

	@Reference
	private SharingEntryLocalService _sharingEntryLocalService;

}