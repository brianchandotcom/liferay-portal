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

package com.liferay.asset.publisher.web.internal.portlet.action;

import com.liferay.asset.publisher.constants.AssetPublisherPortletKeys;
import com.liferay.asset.publisher.constants.AssetPublisherWebKeys;
import com.liferay.asset.publisher.web.internal.helper.AssetPublisherWebHelper;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(
	property = {
		"javax.portlet.name=" + AssetPublisherPortletKeys.ASSET_PUBLISHER,
		"javax.portlet.name=" + AssetPublisherPortletKeys.HIGHEST_RATED_ASSETS,
		"javax.portlet.name=" + AssetPublisherPortletKeys.MOST_VIEWED_ASSETS,
		"javax.portlet.name=" + AssetPublisherPortletKeys.RECENT_CONTENT,
		"javax.portlet.name=" + AssetPublisherPortletKeys.RELATED_ASSETS,
		"mvc.command.name=/asset_publisher/select_structure_field"
	},
	service = MVCRenderCommand.class
)
public class SelectStructureFieldMVCRenderCommand implements MVCRenderCommand {

	@Override
	public String render(
		RenderRequest renderRequest, RenderResponse renderResponse) {

		renderRequest.setAttribute(
			AssetPublisherWebKeys.ASSET_PUBLISHER_WEB_HELPER,
			_assetPublisherWebHelper);

		return "/select_structure_field.jsp";
	}

	@Reference
	private AssetPublisherWebHelper _assetPublisherWebHelper;

}