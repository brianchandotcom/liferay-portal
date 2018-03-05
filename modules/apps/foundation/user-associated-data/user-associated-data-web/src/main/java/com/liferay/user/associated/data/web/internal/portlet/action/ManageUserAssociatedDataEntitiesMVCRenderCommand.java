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

import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItemList;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.user.associated.data.aggregator.UADEntityAggregator;
import com.liferay.user.associated.data.constants.UserAssociatedDataPortletKeys;
import com.liferay.user.associated.data.display.UADEntityDisplay;
import com.liferay.user.associated.data.entity.UADEntity;
import com.liferay.user.associated.data.web.internal.constants.UserAssociatedDataWebKeys;
import com.liferay.user.associated.data.web.internal.display.ManageUserAssociatedDataEntitiesDisplay;
import com.liferay.user.associated.data.web.internal.registry.UADRegistry;

import java.util.List;

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
		String uadEntitySetName = ParamUtil.getString(
			renderRequest, "uadEntitySetName");
		String uadRegistryKey = ParamUtil.getString(
			renderRequest, "uadRegistryKey");

		UADEntityAggregator uadEntityAggregator =
			_uadRegistry.getUADEntityAggregator(uadRegistryKey);

		PortletRequest portletRequest =
			(PortletRequest)renderRequest.getAttribute(
				JavaConstants.JAVAX_PORTLET_REQUEST);
		LiferayPortletResponse liferayPortletResponse =
			_portal.getLiferayPortletResponse(
				(PortletResponse)renderRequest.getAttribute(
					JavaConstants.JAVAX_PORTLET_RESPONSE));

		PortletURL currentURL = PortletURLUtil.getCurrent(
			_portal.getLiferayPortletRequest(portletRequest),
			liferayPortletResponse);

		ManageUserAssociatedDataEntitiesDisplay
			manageUserAssociatedDataEntitiesDisplay =
				new ManageUserAssociatedDataEntitiesDisplay();

		manageUserAssociatedDataEntitiesDisplay.setNavigationItems(
			_getNaviagationItems(
				uadEntitySetName, uadRegistryKey, currentURL,
				liferayPortletResponse));

		manageUserAssociatedDataEntitiesDisplay.setSearchContainer(
			_getSearchContainer(
				portletRequest, currentURL, uadEntityAggregator, selUserId));

		manageUserAssociatedDataEntitiesDisplay.setUADEntityDisplay(
			_uadRegistry.getUADEntityDisplay(uadRegistryKey));
		manageUserAssociatedDataEntitiesDisplay.setUADEntitySetName(
			uadEntitySetName);
		manageUserAssociatedDataEntitiesDisplay.setUADRegistryKey(
			uadRegistryKey);

		renderRequest.setAttribute(
			UserAssociatedDataWebKeys.
				MANAGE_USER_ASSOCIATED_DATA_ENTITIES_DISPLAY,
			manageUserAssociatedDataEntitiesDisplay);

		return "/manage_user_associated_data_entities.jsp";
	}

	private List<NavigationItem> _getNaviagationItems(
			String uadEntitySetName, String uadRegistryKey,
			PortletURL currentURL,
			LiferayPortletResponse liferayPortletResponse)
		throws PortletException {

		NavigationItemList navigationItemList = new NavigationItemList();

		for (String uadEntityAggregatorKey :
				_uadRegistry.getUADEntityAggregatorKeySet()) {

			UADEntityAggregator curUADEntityAggregator =
				_uadRegistry.getUADEntityAggregator(uadEntityAggregatorKey);

			if (!uadEntitySetName.equals(
					curUADEntityAggregator.getUADEntitySetName())) {

				continue;
			}

			UADEntityDisplay uadEntityDisplay =
				_uadRegistry.getUADEntityDisplay(uadEntityAggregatorKey);

			PortletURL tabPortletURL = PortletURLUtil.clone(
				currentURL, liferayPortletResponse);

			navigationItemList.add(
				navigationItem -> {
					navigationItem.setActive(
						uadEntityAggregatorKey.equals(uadRegistryKey));
					navigationItem.setHref(
						tabPortletURL, "uadRegistryKey",
						uadEntityAggregatorKey);
					navigationItem.setLabel(
						uadEntityDisplay.getUADEntityTypeName());
				});
		}

		return navigationItemList;
	}

	private SearchContainer<UADEntity> _getSearchContainer(
		PortletRequest portletRequest, PortletURL currentURL,
		UADEntityAggregator uadEntityAggregator, long selUserId) {

		SearchContainer<UADEntity> searchContainer = new SearchContainer<>(
			portletRequest, currentURL, null, null);

		searchContainer.setResults(
			uadEntityAggregator.getUADEntities(
				selUserId, searchContainer.getStart(),
				searchContainer.getEnd()));

		searchContainer.setTotal(uadEntityAggregator.count(selUserId));

		return searchContainer;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ManageUserAssociatedDataEntitiesMVCRenderCommand.class);

	@Reference
	private Portal _portal;

	@Reference
	private UADRegistry _uadRegistry;

}