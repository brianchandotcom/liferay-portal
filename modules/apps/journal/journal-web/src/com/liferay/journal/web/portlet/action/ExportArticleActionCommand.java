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

package com.liferay.journal.web.portlet.action;

import com.liferay.journal.web.constants.JournalPortletKeys;
import com.liferay.journal.web.util.ExportArticleUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.ActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseActionCommand;
import com.liferay.portal.util.PortalUtil;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import org.osgi.service.component.annotations.Component;

/**
 * @author Bruno Farache
 * @author Eduardo Garcia
 */
@Component(
	immediate = true,
	property = {
		"mvc.command.name=exportArticle",
		"javax.portlet.name=" + JournalPortletKeys.JOURNAL
	},
	service = ActionCommand.class
)
public class ExportArticleActionCommand extends BaseActionCommand {

	@Override
	protected void doProcessCommand(
			PortletRequest portletRequest, PortletResponse portletResponse)
		throws Exception {

		try {
			ExportArticleUtil.sendFile(portletRequest, portletResponse);
		}
		catch (Exception e) {
			PortalUtil.sendError(
				e, (ActionRequest)portletRequest,
				(ActionResponse)portletResponse);
		}
	}

}