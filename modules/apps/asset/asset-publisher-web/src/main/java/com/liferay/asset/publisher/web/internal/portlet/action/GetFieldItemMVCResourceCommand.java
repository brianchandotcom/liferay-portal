/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.publisher.web.internal.portlet.action;

import com.liferay.asset.publisher.constants.AssetPublisherPortletKeys;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;

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
		"mvc.command.name=/asset_publisher/get_field_item"
	},
	service = MVCResourceCommand.class
)
public class GetFieldItemMVCResourceCommand extends BaseMVCResourceCommand {

	@Override
	protected void doServeResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		include(
			resourceRequest, resourceResponse,
			"/select_structure_field_item.jsp");
	}

}