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

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.user.associated.data.aggregator.UADEntityAggregator;
import com.liferay.user.associated.data.constants.UserAssociatedDataPortletKeys;
import com.liferay.user.associated.data.web.internal.constants.UserAssociatedDataWebKeys;
import com.liferay.user.associated.data.web.internal.registry.UADRegistry;
import com.liferay.user.associated.data.web.internal.util.UADEntitySetComposite;
import com.liferay.user.associated.data.web.internal.util.UADEntityTypeComposite;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletException;
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
		"mvc.command.name=/user_associated_data/manage_user_associated_data_entity_sets"
	},
	service = MVCRenderCommand.class
)
public class ManageUserAssociatedDataEntitySetsMVCRenderCommand
	implements MVCRenderCommand {

	@Override
	public String render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortletException {

		long selUserId = ParamUtil.getLong(renderRequest, "selUserId");

		if (selUserId > 0) {
			Map<String, List<UADEntityTypeComposite>>
				uadEntityTypeCompositesMap = new HashMap<>();

			for (String key : _uadRegistry.getUADEntityAggregatorKeySet()) {
				UADEntityAggregator uadAggregator =
					_uadRegistry.getUADEntityAggregator(key);

				List<UADEntityTypeComposite> uadEntityTypeComposites =
					uadEntityTypeCompositesMap.getOrDefault(
						uadAggregator.getUADEntitySetName(),
						new ArrayList<UADEntityTypeComposite>());

				UADEntityTypeComposite uadEntityTypeComposite =
					new UADEntityTypeComposite(
						selUserId, key, _uadRegistry.getUADEntityDisplay(key),
						uadAggregator.getUADEntities(selUserId));

				uadEntityTypeComposites.add(uadEntityTypeComposite);

				uadEntityTypeCompositesMap.put(
					uadAggregator.getUADEntitySetName(),
					uadEntityTypeComposites);
			}

			List<UADEntitySetComposite> uadEntitySetComposites =
				new ArrayList<>();

			for (Map.Entry<String, List<UADEntityTypeComposite>> entry :
					uadEntityTypeCompositesMap.entrySet()) {

				String uadEntitySetName = entry.getKey();
				List<UADEntityTypeComposite> uadEntityTypeComposites =
					entry.getValue();

				UADEntitySetComposite uadEntitySetComposite =
					new UADEntitySetComposite(
						selUserId, uadEntitySetName, uadEntityTypeComposites);

				uadEntitySetComposites.add(uadEntitySetComposite);
			}

			renderRequest.setAttribute(
				UserAssociatedDataWebKeys.UAD_ENTITY_SET_COMPOSITES,
				uadEntitySetComposites);
		}

		return "/manage_user_associated_data_entity_sets.jsp";
	}

	@Reference
	private UADRegistry _uadRegistry;

}