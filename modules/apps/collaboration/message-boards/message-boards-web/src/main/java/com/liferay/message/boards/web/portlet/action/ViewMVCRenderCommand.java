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

package com.liferay.message.boards.web.portlet.action;

import com.liferay.message.boards.web.constants.MBPortletKeys;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;

import org.osgi.service.component.annotations.Component;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	property = {
		"javax.portlet.name=" + MBPortletKeys.MESSAGE_BOARDS,
		"mvc.command.name=/", "mvc.command.name=/message_boards/view",
		"mvc.command.name=/message_boards/view_category",
		"mvc.command.name=/message_boards/view_my_posts",
		"mvc.command.name=/message_boards/view_my_subscriptions",
		"mvc.command.name=/message_boards/view_recent_posts"
	},
	service = MVCRenderCommand.class
)
public class ViewMVCRenderCommand extends BaseViewMVCRenderCommand {

	public ViewMVCRenderCommand() {
		super("/message_boards/view.jsp");
	}

}