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

package com.liferay.trash.web.internal.portlet.action;

import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.trash.kernel.model.TrashEntry;
import com.liferay.trash.web.internal.constants.TrashPortletKeys;
import com.liferay.trash.web.internal.constants.TrashWebKeys;

import java.util.List;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;

/**
 * Provides an implementation of <code>MVCResourceCommand</code> (in
 * <code>com.liferay.portal.kernel</code>) to allow Recycle Bin entries selected
 * in the Recycle Bin portlet to render an information panel.
 *
 * @author Jürgen Kappler
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + TrashPortletKeys.TRASH,
		"mvc.command.name=/trash/info_panel"
	},
	service = MVCResourceCommand.class
)
public class InfoPanelMVCResourceCommand extends BaseMVCResourceCommand {

	@Override
	protected void doServeResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		List<TrashEntry> trashEntries = ActionUtil.getTrashEntries(
			resourceRequest);

		resourceRequest.setAttribute(TrashWebKeys.TRASH_ENTRIES, trashEntries);

		include(resourceRequest, resourceResponse, "/info_panel.jsp");
	}

}