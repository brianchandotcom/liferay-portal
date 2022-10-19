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

package com.liferay.change.tracking.web.internal.portlet.action;

import com.liferay.change.tracking.constants.CTPortletKeys;
import com.liferay.change.tracking.service.CTCollectionService;
import com.liferay.change.tracking.web.internal.constants.CTWebKeys;
import com.liferay.change.tracking.web.internal.display.context.ViewScheduledDisplayContext;
import com.liferay.change.tracking.web.internal.scheduler.PublishScheduler;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.util.Portal;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Samuel Trong Tran
 */
@Component(
	property = {
		"javax.portlet.name=" + CTPortletKeys.PUBLICATIONS,
		"mvc.command.name=/change_tracking/view_scheduled"
	},
	service = MVCRenderCommand.class
)
public class ViewScheduledMVCRenderCommand implements MVCRenderCommand {

	@Override
	public String render(
		RenderRequest renderRequest, RenderResponse renderResponse) {

		ViewScheduledDisplayContext viewScheduledDisplayContext =
			new ViewScheduledDisplayContext(
				_ctCollectionService,
				_portal.getHttpServletRequest(renderRequest), _language,
				_publishScheduler, renderRequest, renderResponse);

		renderRequest.setAttribute(
			CTWebKeys.VIEW_SCHEDULED_DISPLAY_CONTEXT,
			viewScheduledDisplayContext);

		return "/publications/view_scheduled.jsp";
	}

	@Reference
	private CTCollectionService _ctCollectionService;

	@Reference
	private Language _language;

	@Reference
	private Portal _portal;

	@Reference
	private PublishScheduler _publishScheduler;

}