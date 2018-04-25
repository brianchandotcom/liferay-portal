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

import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemList;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItemList;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.user.associated.data.aggregator.UADAggregator;
import com.liferay.user.associated.data.constants.UserAssociatedDataPortletKeys;
import com.liferay.user.associated.data.display.UADDisplay;
import com.liferay.user.associated.data.web.internal.constants.UADWebKeys;
import com.liferay.user.associated.data.web.internal.display.UADEntity;
import com.liferay.user.associated.data.web.internal.display.ViewUADEntitiesDisplay;
import com.liferay.user.associated.data.web.internal.registry.UADRegistry;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.portlet.PortletException;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

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

			viewUADEntitiesDisplay.setActionDropdownItems(
				_getActionDropdownItems(renderRequest, renderResponse));
			viewUADEntitiesDisplay.setApplicationName(applicationName);

			LiferayPortletResponse liferayPortletResponse =
				_portal.getLiferayPortletResponse(renderResponse);

			PortletURL currentURL = PortletURLUtil.getCurrent(
				renderRequest, renderResponse);

			viewUADEntitiesDisplay.setNavigationItems(
				_getNavigationItems(
					applicationName, uadRegistryKey, currentURL,
					liferayPortletResponse));

			UADDisplay uadDisplay = _uadRegistry.getUADDisplay(uadRegistryKey);

			viewUADEntitiesDisplay.setSearchContainer(
				_getSearchContainer(
					renderRequest, currentURL, uadRegistryKey, uadDisplay,
					selectedUser.getUserId(), liferayPortletResponse));
			viewUADEntitiesDisplay.setTypeName(
				uadDisplay.getTypeName(
					LocaleThreadLocal.getThemeDisplayLocale()));

			viewUADEntitiesDisplay.setUADRegistryKey(uadRegistryKey);

			renderRequest.setAttribute(
				UADWebKeys.INFO_PANEL_UAD_DISPLAY, uadDisplay);
			renderRequest.setAttribute(
				UADWebKeys.VIEW_UAD_ENTITIES_DISPLAY, viewUADEntitiesDisplay);
		}
		catch (Exception pe) {
			throw new PortletException(pe);
		}

		return "/view_uad_entities.jsp";
	}

	private <T> UADEntity<T> _constructUADEntity(
			T entity, UADAggregator<T> uadAggregator, UADDisplay<T> uadDisplay,
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse)
		throws Exception {

		UADEntity<T> uadEntity = new UADEntity(
			entity, uadAggregator.getPrimaryKey(entity),
			uadDisplay.getEditURL(
				entity, liferayPortletRequest, liferayPortletResponse));

		Map<String, Object> nonanonymizableFieldValues =
			uadDisplay.getNonanonymizableFieldValues(entity);

		for (String displayFieldName : uadDisplay.getDisplayFieldNames()) {
			Object nonanonymizableFieldValue = nonanonymizableFieldValues.get(
				displayFieldName);

			if (nonanonymizableFieldValue != null) {
				uadEntity.addColumnEntry(
					displayFieldName, nonanonymizableFieldValue);
			}
		}

		return uadEntity;
	}

	private DropdownItemList _getActionDropdownItems(
		RenderRequest renderRequest, RenderResponse renderResponse) {

		HttpServletRequest request = _portal.getHttpServletRequest(
			renderRequest);

		return new DropdownItemList() {
			{
				add(
					dropdownItem -> {
						dropdownItem.setHref(
							StringBundler.concat(
								"javascript:", renderResponse.getNamespace(),
								"doAnonymizeMultiple();"));
						dropdownItem.setLabel(
							LanguageUtil.get(request, "anonymize"));
					});
				add(
					dropdownItem -> {
						dropdownItem.setHref(
							StringBundler.concat(
								"javascript:", renderResponse.getNamespace(),
								"doDeleteMultiple();"));
						dropdownItem.setLabel(
							LanguageUtil.get(request, "delete"));
					});
			}

		};
	}

	private List<NavigationItem> _getNavigationItems(
			String applicationName, String uadRegistryKey,
			PortletURL currentURL,
			LiferayPortletResponse liferayPortletResponse)
		throws PortletException {

		NavigationItemList navigationItemList = new NavigationItemList();

		Locale locale = LocaleThreadLocal.getThemeDisplayLocale();
		PortletURL tabPortletURL = PortletURLUtil.clone(
			currentURL, liferayPortletResponse);

		for (UADDisplay uadDisplay : _uadRegistry.getUADDisplays()) {
			if (!applicationName.equals(uadDisplay.getApplicationName())) {
				continue;
			}

			navigationItemList.add(
				navigationItem -> {
					navigationItem.setActive(
						uadRegistryKey.equals(uadDisplay.getKey()));
					navigationItem.setHref(
						tabPortletURL, "uadRegistryKey", uadDisplay.getKey());
					navigationItem.setLabel(uadDisplay.getTypeName(locale));
				});
		}

		return navigationItemList;
	}

	private SearchContainer<UADEntity> _getSearchContainer(
			RenderRequest renderRequest, PortletURL currentURL,
			String uadRegistryKey, UADDisplay uadDisplay, long selectedUserId,
			LiferayPortletResponse liferayPortletResponse)
		throws Exception {

		LiferayPortletRequest liferayPortletRequest =
			_portal.getLiferayPortletRequest(renderRequest);

		SearchContainer<UADEntity> searchContainer = new SearchContainer<>(
			renderRequest, currentURL, null, null);

		UADAggregator uadAggregator = _uadRegistry.getUADAggregator(
			uadRegistryKey);

		List<Object> entities = uadAggregator.getRange(
			selectedUserId, searchContainer.getStart(),
			searchContainer.getEnd());

		List<UADEntity> uadEntities = new ArrayList<>();

		for (Object entity : entities) {
			uadEntities.add(
				_constructUADEntity(
					entity, uadAggregator, uadDisplay, liferayPortletRequest,
					liferayPortletResponse));
		}

		searchContainer.setResults(uadEntities);
		searchContainer.setRowChecker(
			new EmptyOnClickRowChecker(liferayPortletResponse));
		searchContainer.setTotal((int)uadAggregator.count(selectedUserId));

		return searchContainer;
	}

	@Reference
	private Portal _portal;

	@Reference
	private UADRegistry _uadRegistry;

}