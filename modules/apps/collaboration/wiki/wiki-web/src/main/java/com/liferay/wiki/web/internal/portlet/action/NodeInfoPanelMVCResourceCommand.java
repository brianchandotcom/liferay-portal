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

package com.liferay.wiki.web.internal.portlet.action;

import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.trash.TrashHelper;
import com.liferay.trash.util.TrashWebKeys;
import com.liferay.wiki.constants.WikiPortletKeys;
import com.liferay.wiki.constants.WikiWebKeys;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + WikiPortletKeys.WIKI_ADMIN,
		"mvc.command.name=/wiki/node_info_panel"
	},
	service = MVCResourceCommand.class
)
public class NodeInfoPanelMVCResourceCommand extends BaseMVCResourceCommand {

	@Override
	public void doServeResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		resourceRequest.setAttribute(TrashWebKeys.TRASH_HELPER, _trashHelper);

		if (ParamUtil.getLong(resourceRequest, "nodeId") != 0) {
			resourceRequest.setAttribute(
				WikiWebKeys.WIKI_NODE, ActionUtil.getNode(resourceRequest));
		}

		resourceRequest.setAttribute(
			WikiWebKeys.WIKI_NODES, ActionUtil.getNodes(resourceRequest));

		include(
			resourceRequest, resourceResponse,
			"/wiki_admin/node_info_panel.jsp");
	}

	@Reference
	private TrashHelper _trashHelper;

}