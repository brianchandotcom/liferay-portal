/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.audiences.web.internal.portlet.action;

import com.liferay.audiences.constants.AudiencesPortletKeys;
import com.liferay.audiences.criteria.AudiencesCriteriaProvider;
import com.liferay.audiences.web.internal.display.context.EditAudiencesEntryDisplayContext;
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
		"jakarta.portlet.name=" + AudiencesPortletKeys.AUDIENCES,
		"mvc.command.name=/audiences/edit_audiences_entry"
	},
	service = MVCRenderCommand.class
)
public class EditAudiencesEntryMVCRenderCommand implements MVCRenderCommand {

	@Override
	public String render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortletException {

		renderRequest.setAttribute(
			EditAudiencesEntryDisplayContext.class.getName(),
			new EditAudiencesEntryDisplayContext(
				_audiencesCriteriaProvider,
				_portal.getHttpServletRequest(renderRequest), renderResponse));

		return "/edit_audiences_entry.jsp";
	}

	@Reference
	private AudiencesCriteriaProvider _audiencesCriteriaProvider;

	@Reference
	private Portal _portal;

}