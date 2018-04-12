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
import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.user.associated.data.aggregator.UADAggregator;
import com.liferay.user.associated.data.constants.UserAssociatedDataPortletKeys;
import com.liferay.user.associated.data.display.UADEntityDisplay;
import com.liferay.user.associated.data.web.internal.constants.UADWebKeys;
import com.liferay.user.associated.data.web.internal.display.UADEntity;
import com.liferay.user.associated.data.web.internal.display.ViewUADEntitiesDisplay;
import com.liferay.user.associated.data.web.internal.registry.UADRegistry;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
		"mvc.command.name=/view_uad_entities"
	},
	service = MVCRenderCommand.class
)
public class ViewUADEntitiesMVCRenderCommand implements MVCRenderCommand {

	@Override
	public String render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortletException {

		try {
			User selectedUser = _portal.getSelectedUser(renderRequest);

			String applicationName = ParamUtil.getString(
				renderRequest, "applicationName");
			String uadRegistryKey = ParamUtil.getString(
				renderRequest, "uadRegistryKey");

			ViewUADEntitiesDisplay viewUADEntitiesDisplay =
				new ViewUADEntitiesDisplay();

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

			viewUADEntitiesDisplay.setApplicationName(applicationName);
			viewUADEntitiesDisplay.setNavigationItems(
				_getNavigationItems(
					applicationName, uadRegistryKey, currentURL,
					liferayPortletResponse));

			UADEntityDisplay uadEntityDisplay =
				_uadRegistry.getUADEntityDisplay(uadRegistryKey);

			viewUADEntitiesDisplay.setSearchContainer(
				_getSearchContainer(
					renderRequest, currentURL, uadRegistryKey, uadEntityDisplay,
					selectedUser.getUserId(), liferayPortletResponse));
			viewUADEntitiesDisplay.setTypeName(uadEntityDisplay.getTypeName());

			viewUADEntitiesDisplay.setUADRegistryKey(uadRegistryKey);

			renderRequest.setAttribute(
				UADWebKeys.INFO_PANEL_UAD_ENTITY_DISPLAY, uadEntityDisplay);
			renderRequest.setAttribute(
				UADWebKeys.VIEW_UAD_ENTITIES_DISPLAY, viewUADEntitiesDisplay);
		}
		catch (Exception pe) {
			throw new PortletException(pe);
		}

		return "/view_uad_entities.jsp";
	}

	private <T> UADEntity<T> _constructUADEntity(
			T entity, UADAggregator<T> uadAggregator,
			UADEntityDisplay<T> uadEntityDisplay,
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse)
		throws Exception {

		UADEntity<T> uadEntity = new UADEntity(
			entity, uadAggregator.getPrimaryKey(entity),
			uadEntityDisplay.getEditURL(
				entity, liferayPortletRequest, liferayPortletResponse));

		Map<String, Object> nonanonymizableFieldValues =
			uadEntityDisplay.getNonanonymizableFieldValues(entity);

		for (String displayFieldName :
				uadEntityDisplay.getDisplayFieldNames()) {

			Object nonanonymizableFieldValue = nonanonymizableFieldValues.get(
				displayFieldName);

			if (nonanonymizableFieldValue != null) {
				uadEntity.addColumnEntry(
					displayFieldName, nonanonymizableFieldValue);
			}
		}

		return uadEntity;
	}

	private List<NavigationItem> _getNavigationItems(
			String applicationName, String uadRegistryKey,
			PortletURL currentURL,
			LiferayPortletResponse liferayPortletResponse)
		throws PortletException {

		NavigationItemList navigationItemList = new NavigationItemList();

		for (UADEntityDisplay uadEntityDisplay :
				_uadRegistry.getUADEntityDisplays()) {

			if (!applicationName.equals(
					uadEntityDisplay.getApplicationName())) {

				continue;
			}

			PortletURL tabPortletURL = PortletURLUtil.clone(
				currentURL, liferayPortletResponse);

			navigationItemList.add(
				navigationItem -> {
					navigationItem.setActive(
						uadRegistryKey.equals(uadEntityDisplay.getKey()));
					navigationItem.setHref(
						tabPortletURL, "uadRegistryKey",
						uadEntityDisplay.getKey());
					navigationItem.setLabel(uadEntityDisplay.getTypeName());
				});
		}

		return navigationItemList;
	}

	private SearchContainer<UADEntity> _getSearchContainer(
			RenderRequest renderRequest, PortletURL currentURL,
			String uadRegistryKey, UADEntityDisplay uadEntityDisplay,
			long selectedUserId, LiferayPortletResponse liferayPortletResponse)
		throws Exception {

		LiferayPortletRequest liferayPortletRequest =
			_portal.getLiferayPortletRequest(
				(PortletRequest)renderRequest.getAttribute(
					JavaConstants.JAVAX_PORTLET_REQUEST));

		SearchContainer<UADEntity> searchContainer = new SearchContainer<>(
			liferayPortletRequest, currentURL, null, null);

		UADAggregator uadAggregator = _uadRegistry.getUADAggregator(
			uadRegistryKey);

		List<Object> entities = uadAggregator.getRange(
			selectedUserId, searchContainer.getStart(),
			searchContainer.getEnd());

		List<UADEntity> uadEntities = new ArrayList<>();

		for (Object entity : entities) {
			uadEntities.add(
				_constructUADEntity(
					entity, uadAggregator, uadEntityDisplay,
					liferayPortletRequest, liferayPortletResponse));
		}

		searchContainer.setResults(uadEntities);
		searchContainer.setRowChecker(
			new EmptyOnClickRowChecker(liferayPortletResponse));
		searchContainer.setTotal((int)uadAggregator.count(selectedUserId));

		return searchContainer;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ViewUADEntitiesMVCRenderCommand.class);

	@Reference
	private Portal _portal;

	@Reference
	private UADRegistry _uadRegistry;

}