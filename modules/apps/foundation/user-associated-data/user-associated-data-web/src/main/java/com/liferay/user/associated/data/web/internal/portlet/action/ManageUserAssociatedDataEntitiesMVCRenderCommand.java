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

package com.liferay.user.associated.data.web.internal.portlet.action;

import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.user.associated.data.aggregator.UADEntityAggregator;
import com.liferay.user.associated.data.constants.UserAssociatedDataPortletKeys;
import com.liferay.user.associated.data.entity.UADEntity;
import com.liferay.user.associated.data.web.internal.constants.UserAssociatedDataWebKeys;
import com.liferay.user.associated.data.web.internal.display.ManageUserAssociatedDataEntitiesDisplay;
import com.liferay.user.associated.data.web.internal.registry.UADRegistry;

import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author William Newbury
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + UserAssociatedDataPortletKeys.USER_ASSOCIATED_DATA,
		"mvc.command.name=/user_associated_data/manage_user_associated_data_entities"
	},
	service = MVCRenderCommand.class
)
public class ManageUserAssociatedDataEntitiesMVCRenderCommand
	implements MVCRenderCommand {

	@Override
	public String render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortletException {

		long selUserId = ParamUtil.getLong(renderRequest, "selUserId");
		String uadRegistryKey = ParamUtil.getString(
			renderRequest, "uadRegistryKey");

		PortletRequest portletRequest =
			(PortletRequest)renderRequest.getAttribute(
				JavaConstants.JAVAX_PORTLET_REQUEST);
		PortletResponse portletResponse =
			(PortletResponse)renderRequest.getAttribute(
				JavaConstants.JAVAX_PORTLET_RESPONSE);

		PortletURL iteratorURL = PortletURLUtil.getCurrent(
			_portal.getLiferayPortletRequest(portletRequest),
			_portal.getLiferayPortletResponse(portletResponse));

		SearchContainer<UADEntity> searchContainer = new SearchContainer<>(
			portletRequest, iteratorURL, null, null);

		UADEntityAggregator uadEntityAggregator =
			_uadRegistry.getUADEntityAggregator(uadRegistryKey);

		searchContainer.setResults(
			uadEntityAggregator.getUADEntities(
				selUserId, searchContainer.getStart(),
				searchContainer.getEnd()));
		searchContainer.setTotal(uadEntityAggregator.count(selUserId));

		renderRequest.setAttribute(
			UserAssociatedDataWebKeys.
				MANAGE_USER_ASSOCIATED_DATA_ENTITIES_DISPLAY,
			new ManageUserAssociatedDataEntitiesDisplay(
				_uadRegistry.getUADEntityDisplay(uadRegistryKey),
				searchContainer));

		return "/manage_user_associated_data_entities.jsp";
	}

	@Reference
	private Portal _portal;

	@Reference
	private UADRegistry _uadRegistry;

}