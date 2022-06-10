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

package com.liferay.notification.web.internal.portlet.action;

import com.liferay.notification.constants.NotificationPortletKeys;
import com.liferay.notification.model.NotificationTemplate;
import com.liferay.notification.service.NotificationTemplateLocalService;
import com.liferay.notification.web.internal.constants.NotificationWebKeys;
import com.liferay.notification.web.internal.display.context.ViewNotificationTemplatesDisplayContext;
import com.liferay.portal.kernel.editor.configuration.EditorConfigurationFactory;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Gabriel Albuquerque
 */
@Component(
	property = {
		"javax.portlet.name=" + NotificationPortletKeys.NOTIFICATION_TEMPLATES,
		"mvc.command.name=/notification_templates/edit_notification_template"
	},
	service = MVCRenderCommand.class
)
public class EditNotificationTemplateMVCRenderCommand
	implements MVCRenderCommand {

	@Override
	public String render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortletException {

		try {
			renderRequest.setAttribute(
				NotificationWebKeys.NOTIFICATION_TEMPLATES,
				_notificationTemplateLocalService.getNotificationTemplate(
					ParamUtil.getLong(
						renderRequest, "notificationTemplateId")));
		}
		catch (PortalException portalException) {
			SessionErrors.add(renderRequest, portalException.getClass());
		}

		renderRequest.setAttribute(
			WebKeys.PORTLET_DISPLAY_CONTEXT,
			new ViewNotificationTemplatesDisplayContext(
				_editorConfigurationFactory,
				_portal.getHttpServletRequest(renderRequest),
				_notificationTemplateModelResourcePermission));

		return "/notification_templates/edit_notification_template.jsp";
	}

	@Reference
	private EditorConfigurationFactory _editorConfigurationFactory;

	@Reference
	private NotificationTemplateLocalService _notificationTemplateLocalService;

	@Reference(
		target = "(model.class.name=com.liferay.notification.model.NotificationTemplate)"
	)
	private ModelResourcePermission<NotificationTemplate>
		_notificationTemplateModelResourcePermission;

	@Reference
	private Portal _portal;

}