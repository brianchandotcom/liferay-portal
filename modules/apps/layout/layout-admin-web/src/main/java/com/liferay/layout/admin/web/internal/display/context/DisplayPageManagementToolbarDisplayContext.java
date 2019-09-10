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

package com.liferay.layout.admin.web.internal.display.context;

import com.liferay.asset.kernel.model.ClassType;
import com.liferay.frontend.taglib.clay.servlet.taglib.display.context.SearchContainerManagementToolbarDisplayContext;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemList;
import com.liferay.info.constants.InfoDisplayWebKeys;
import com.liferay.info.display.contributor.InfoDisplayContributor;
import com.liferay.info.display.contributor.InfoDisplayContributorTracker;
import com.liferay.layout.admin.web.internal.security.permission.resource.LayoutPageTemplateEntryPermission;
import com.liferay.layout.admin.web.internal.security.permission.resource.LayoutPageTemplatePermission;
import com.liferay.layout.page.template.constants.LayoutPageTemplateActionKeys;
import com.liferay.layout.page.template.constants.LayoutPageTemplateEntryTypeConstants;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class DisplayPageManagementToolbarDisplayContext
	extends SearchContainerManagementToolbarDisplayContext {

	public DisplayPageManagementToolbarDisplayContext(
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse,
		HttpServletRequest httpServletRequest,
		DisplayPageDisplayContext displayPageDisplayContext) {

		super(
			liferayPortletRequest, liferayPortletResponse, httpServletRequest,
			displayPageDisplayContext.getDisplayPagesSearchContainer());

		_infoDisplayContributorTracker =
			(InfoDisplayContributorTracker)request.getAttribute(
				InfoDisplayWebKeys.INFO_DISPLAY_CONTRIBUTOR_TRACKER);
		_themeDisplay = (ThemeDisplay)httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	@Override
	public List<DropdownItem> getActionDropdownItems() {
		return new DropdownItemList() {
			{
				add(
					dropdownItem -> {
						dropdownItem.putData(
							"action", "deleteSelectedDisplayPages");
						dropdownItem.setIcon("times-circle");
						dropdownItem.setLabel(
							LanguageUtil.get(request, "delete"));
						dropdownItem.setQuickAction(true);
					});
			}
		};
	}

	public String getAvailableActions(
			LayoutPageTemplateEntry layoutPageTemplateEntry)
		throws PortalException {

		if (LayoutPageTemplateEntryPermission.contains(
				_themeDisplay.getPermissionChecker(), layoutPageTemplateEntry,
				ActionKeys.DELETE)) {

			return "deleteSelectedDisplayPages";
		}

		return StringPool.BLANK;
	}

	@Override
	public String getClearResultsURL() {
		PortletURL clearResultsURL = getPortletURL();

		clearResultsURL.setParameter("keywords", StringPool.BLANK);

		return clearResultsURL.toString();
	}

	@Override
	public String getComponentId() {
		return "displayPagesManagementToolbar";
	}

	@Override
	public CreationMenu getCreationMenu() {
		PortletURL addDisplayPageURL = liferayPortletResponse.createActionURL();

		addDisplayPageURL.setParameter(
			ActionRequest.ACTION_NAME, "/layout/add_display_page");
		addDisplayPageURL.setParameter(
			"backURL", _themeDisplay.getURLCurrent());
		addDisplayPageURL.setParameter(
			"type",
			String.valueOf(
				LayoutPageTemplateEntryTypeConstants.TYPE_DISPLAY_PAGE));

		return new CreationMenu() {
			{
				addDropdownItem(
					dropdownItem -> {
						Map<String, Object> dropDownItemData = new HashMap<>();

						dropDownItemData.put("action", "addDisplayPage");
						dropDownItemData.put(
							"addDisplayPageURL", addDisplayPageURL.toString());
						dropDownItemData.put(
							"mappingTypes", _getMappingTypesJSONArray());

						dropdownItem.setData(dropDownItemData);

						dropdownItem.setLabel(LanguageUtil.get(request, "add"));
					});
			}
		};
	}

	@Override
	public String getDefaultEventHandler() {
		return "DISPLAY_PAGE_MANAGEMENT_TOOLBAR_DEFAULT_EVENT_HANDLER";
	}

	@Override
	public String getSearchActionURL() {
		PortletURL searchActionURL = getPortletURL();

		return searchActionURL.toString();
	}

	@Override
	public String getSearchContainerId() {
		return "displayPages";
	}

	@Override
	public Boolean isShowCreationMenu() {
		if (LayoutPageTemplatePermission.contains(
				_themeDisplay.getPermissionChecker(),
				_themeDisplay.getSiteGroupId(),
				LayoutPageTemplateActionKeys.ADD_LAYOUT_PAGE_TEMPLATE_ENTRY)) {

			return true;
		}

		return false;
	}

	@Override
	protected String[] getNavigationKeys() {
		return new String[] {"all"};
	}

	@Override
	protected String[] getOrderByKeys() {
		return new String[] {"create-date", "name"};
	}

	private JSONArray _getMappingSubtypesJSONArray(
		InfoDisplayContributor<?> infoDisplayContributor) {

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		try {
			List<ClassType> classTypes = infoDisplayContributor.getClassTypes(
				_themeDisplay.getScopeGroupId(), _themeDisplay.getLocale());

			for (ClassType classType : classTypes) {
				JSONObject jsonObject = JSONUtil.put(
					"id", String.valueOf(classType.getClassTypeId())
				).put(
					"label", classType.getName()
				);

				jsonArray.put(jsonObject);
			}
		}
		catch (PortalException pe) {
			_log.error("Unable to get mapping subtypes", pe);
		}

		return jsonArray;
	}

	private JSONArray _getMappingTypesJSONArray() {
		JSONArray mappingTypesJSONArray = JSONFactoryUtil.createJSONArray();

		for (InfoDisplayContributor<?> infoDisplayContributor :
				_infoDisplayContributorTracker.getInfoDisplayContributors()) {

			JSONObject jsonObject = JSONUtil.put(
				"id",
				String.valueOf(
					PortalUtil.getClassNameId(
						infoDisplayContributor.getClassName()))
			).put(
				"label",
				infoDisplayContributor.getLabel(_themeDisplay.getLocale())
			).put(
				"subtypes", _getMappingSubtypesJSONArray(infoDisplayContributor)
			);

			mappingTypesJSONArray.put(jsonObject);
		}

		return mappingTypesJSONArray;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DisplayPageManagementToolbarDisplayContext.class);

	private final InfoDisplayContributorTracker _infoDisplayContributorTracker;
	private final ThemeDisplay _themeDisplay;

}