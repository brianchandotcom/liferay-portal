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
import com.liferay.change.tracking.service.CTCollectionLocalService;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.util.ParamUtil;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Máté Thurzó
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + CTPortletKeys.PUBLICATIONS,
		"mvc.command.name=/change_tracking/add_ct_collection",
		"mvc.command.name=/change_tracking/edit_ct_collection",
		"mvc.command.name=/change_tracking/undo_ct_collection"
	},
	service = MVCRenderCommand.class
)
public class EditCTCollectionMVCRenderCommand implements MVCRenderCommand {

	@Override
	public String render(
		RenderRequest renderRequest, RenderResponse renderResponse) {

		long ctCollectionId = ParamUtil.getLong(
			renderRequest, "ctCollectionId");

		renderRequest.setAttribute(
			"ctCollection",
			_ctCollectionLocalService.fetchCTCollection(ctCollectionId));

		return "/publications/edit_ct_collection.jsp";
	}

	@Reference
	private CTCollectionLocalService _ctCollectionLocalService;

}