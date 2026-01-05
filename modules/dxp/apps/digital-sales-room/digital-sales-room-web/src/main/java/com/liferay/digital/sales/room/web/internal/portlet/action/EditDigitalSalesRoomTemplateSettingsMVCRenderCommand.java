/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.digital.sales.room.web.internal.portlet.action;

import com.liferay.digital.sales.room.web.internal.constants.DigitalSalesRoomPortletKeys;
import com.liferay.digital.sales.room.web.internal.display.context.EditDigitalSalesRoomTemplateSettingsDisplayContext;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.service.GroupService;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;

import jakarta.portlet.PortletException;
import jakarta.portlet.RenderRequest;
import jakarta.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Stefano Motta
 */
@Component(
	property = {
		"jakarta.portlet.name=" + DigitalSalesRoomPortletKeys.DIGITAL_SALES_ROOM_MANAGEMENT,
		"mvc.command.name=/digital_sales_room/edit_digital_sales_room_template_settings"
	},
	service = MVCRenderCommand.class
)
public class EditDigitalSalesRoomTemplateSettingsMVCRenderCommand
	implements MVCRenderCommand {

	@Override
	public String render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortletException {

		try {
			Group group = _groupService.getGroup(
				ParamUtil.getLong(renderRequest, "digitalSalesRoomTemplateId"));

			renderRequest.setAttribute(
				WebKeys.PORTLET_DISPLAY_CONTEXT,
				new EditDigitalSalesRoomTemplateSettingsDisplayContext(
					group.getGroupId(),
					_portal.getHttpServletRequest(renderRequest)));

			return "/template/edit_settings.jsp";
		}
		catch (PortalException portalException) {
			SessionErrors.add(renderRequest, portalException.getClass());

			throw new PortletException(portalException);
		}
	}

	@Reference
	private GroupService _groupService;

	@Reference
	private Portal _portal;

}