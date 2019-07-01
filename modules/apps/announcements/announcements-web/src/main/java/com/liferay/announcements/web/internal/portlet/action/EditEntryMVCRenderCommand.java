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

package com.liferay.announcements.web.internal.portlet.action;

import com.liferay.announcements.constants.AnnouncementsPortletKeys;
import com.liferay.announcements.kernel.exception.NoSuchEntryException;
import com.liferay.announcements.kernel.model.AnnouncementsEntry;
import com.liferay.announcements.web.internal.constants.AnnouncementsWebKeys;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.servlet.SessionErrors;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;

/**
 * @author Adolfo Pérez
 */
@Component(
	property = {
		"javax.portlet.name=" + AnnouncementsPortletKeys.ALERTS,
		"javax.portlet.name=" + AnnouncementsPortletKeys.ANNOUNCEMENTS,
		"javax.portlet.name=" + AnnouncementsPortletKeys.ANNOUNCEMENTS_ADMIN,
		"mvc.command.name=/announcements/edit_entry"
	},
	service = MVCRenderCommand.class
)
public class EditEntryMVCRenderCommand implements MVCRenderCommand {

	@Override
	public String render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortletException {

		try {
			AnnouncementsEntry entry = ActionUtil.getEntry(renderRequest);

			renderRequest.setAttribute(
				AnnouncementsWebKeys.ANNOUNCEMENTS_ENTRY, entry);
		}
		catch (NoSuchEntryException | PrincipalException e) {
			SessionErrors.add(renderRequest, e.getClass());

			return "/announcements/error.jsp";
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new PortletException(e);
		}

		return "/announcements/edit_entry.jsp";
	}

}