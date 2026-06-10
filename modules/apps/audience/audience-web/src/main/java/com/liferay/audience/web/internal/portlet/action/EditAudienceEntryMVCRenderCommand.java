/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.audience.web.internal.portlet.action;

import com.liferay.audience.constants.AudiencePortletKeys;
import com.liferay.audience.web.internal.display.context.EditAudienceEntryDisplayContext;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.util.Portal;

import jakarta.portlet.PortletException;
import jakarta.portlet.RenderRequest;
import jakarta.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(
	property = {
		"jakarta.portlet.name=" + AudiencePortletKeys.AUDIENCE,
		"mvc.command.name=/audience/edit_audience_entry"
	},
	service = MVCRenderCommand.class
)
public class EditAudienceEntryMVCRenderCommand implements MVCRenderCommand {

	@Override
	public String render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortletException {

		renderRequest.setAttribute(
			EditAudienceEntryDisplayContext.class.getName(),
			new EditAudienceEntryDisplayContext(
				_portal.getHttpServletRequest(renderRequest), renderResponse));

		return "/edit_audience_entry.jsp";
	}

	@Reference
	private Portal _portal;

}