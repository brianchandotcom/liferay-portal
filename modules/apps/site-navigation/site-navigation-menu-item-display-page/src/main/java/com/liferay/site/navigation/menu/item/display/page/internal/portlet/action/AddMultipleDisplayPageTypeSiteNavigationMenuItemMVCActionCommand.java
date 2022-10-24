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

package com.liferay.site.navigation.menu.item.display.page.internal.portlet.action;

import com.liferay.info.item.ClassPKInfoItemIdentifier;
import com.liferay.info.item.HierarchicalInfoItemReference;
import com.liferay.info.item.InfoItemReference;
import com.liferay.layout.display.page.LayoutDisplayPageMultiSelectionProvider;
import com.liferay.layout.display.page.LayoutDisplayPageMultiSelectionProviderTracker;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.UnicodePropertiesBuilder;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.site.navigation.admin.constants.SiteNavigationAdminPortletKeys;
import com.liferay.site.navigation.model.SiteNavigationMenuItem;
import com.liferay.site.navigation.service.SiteNavigationMenuItemService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Lourdes Fernández Besada
 */
@Component(
	property = {
		"javax.portlet.name=" + SiteNavigationAdminPortletKeys.SITE_NAVIGATION_ADMIN,
		"mvc.command.name=/navigation_menu/add_multiple_display_page_type_site_navigation_menu_item"
	},
	service = MVCActionCommand.class
)
public class AddMultipleDisplayPageTypeSiteNavigationMenuItemMVCActionCommand
	extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		String siteNavigationMenuItemType = ParamUtil.getString(
			actionRequest, "siteNavigationMenuItemType");
		long siteNavigationMenuId = ParamUtil.getLong(
			actionRequest, "siteNavigationMenuId");

		if (Validator.isNotNull(siteNavigationMenuItemType) &&
			(siteNavigationMenuId > 0)) {

			ServiceContext serviceContext = ServiceContextFactory.getInstance(
				actionRequest);

			ThemeDisplay themeDisplay =
				(ThemeDisplay)actionRequest.getAttribute(WebKeys.THEME_DISPLAY);

			try {
				List<InfoItemReference> infoItemReferences = new ArrayList<>();
				Map<Long, JSONObject> jsonObjects = new HashMap<>();

				JSONArray jsonArray = JSONFactoryUtil.createJSONArray(
					ParamUtil.getString(actionRequest, "items"));

				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject itemJSONObject = jsonArray.getJSONObject(i);

					if ((itemJSONObject != null) &&
						Objects.equals(
							itemJSONObject.getString("className"),
							siteNavigationMenuItemType)) {

						infoItemReferences.add(
							new InfoItemReference(
								itemJSONObject.getString("className"),
								itemJSONObject.getLong("classPK")));

						jsonObjects.put(
							itemJSONObject.getLong("classPK"), itemJSONObject);
					}
				}

				LayoutDisplayPageMultiSelectionProvider<?>
					layoutDisplayPageMultiSelectionProvider =
						_layoutDisplayPageMultiSelectionProviderTracker.
							getLayoutDisplayPageMultiSelectionProvider(
								siteNavigationMenuItemType);

				if (layoutDisplayPageMultiSelectionProvider != null) {
					infoItemReferences =
						layoutDisplayPageMultiSelectionProvider.process(
							infoItemReferences);
				}

				for (InfoItemReference infoItemReference : infoItemReferences) {
					_addSiteNavigationMenuItem(
						themeDisplay.getScopeGroupId(), infoItemReference,
						jsonObjects, 0, serviceContext, siteNavigationMenuId,
						siteNavigationMenuItemType);
				}
			}
			catch (PortalException portalException) {
				if (_log.isDebugEnabled()) {
					_log.debug(portalException);
				}

				jsonObject.put(
					"errorMessage",
					_language.get(
						_portal.getHttpServletRequest(actionRequest),
						"an-unexpected-error-occurred"));
			}
		}
		else {
			if (_log.isDebugEnabled()) {
				_log.debug(
					StringBundler.concat(
						"Unable to add multiple site navigation menu items ",
						"for site navigation menu ID ", siteNavigationMenuId,
						" and type ", siteNavigationMenuItemType));
			}

			jsonObject.put(
				"errorMessage",
				_language.get(
					_portal.getHttpServletRequest(actionRequest),
					"an-unexpected-error-occurred"));
		}

		JSONPortletResponseUtil.writeJSON(
			actionRequest, actionResponse, jsonObject);
	}

	private void _addSiteNavigationMenuItem(
			long groupId, InfoItemReference infoItemReference,
			Map<Long, JSONObject> jsonObjects,
			long parentSiteNavigationMenuItemId, ServiceContext serviceContext,
			long siteNavigationMenuId, String siteNavigationMenuItemType)
		throws PortalException {

		JSONObject jsonObject = jsonObjects.get(_getClassPK(infoItemReference));

		if (jsonObject == null) {
			return;
		}

		SiteNavigationMenuItem siteNavigationMenuItem =
			_siteNavigationMenuItemService.addSiteNavigationMenuItem(
				groupId, siteNavigationMenuId, parentSiteNavigationMenuItemId,
				siteNavigationMenuItemType,
				UnicodePropertiesBuilder.create(
					true
				).put(
					"className", jsonObject.getString("className")
				).put(
					"classNameId", jsonObject.getString("classNameId")
				).put(
					"classPK", jsonObject.getString("classPK")
				).put(
					"classTypeId", jsonObject.getString("classTypeId")
				).put(
					"title", jsonObject.getString("title")
				).put(
					"type", jsonObject.getString("type")
				).buildString(),
				serviceContext);

		if (!(infoItemReference instanceof HierarchicalInfoItemReference)) {
			return;
		}

		HierarchicalInfoItemReference hierarchicalInfoItemReference =
			(HierarchicalInfoItemReference)infoItemReference;

		for (HierarchicalInfoItemReference childHierarchicalInfoItemReference :
				hierarchicalInfoItemReference.
					getChildrenHierarchicalInfoItemReferences()) {

			_addSiteNavigationMenuItem(
				groupId, childHierarchicalInfoItemReference, jsonObjects,
				siteNavigationMenuItem.getSiteNavigationMenuItemId(),
				serviceContext, siteNavigationMenuId,
				siteNavigationMenuItemType);
		}
	}

	private long _getClassPK(InfoItemReference infoItemReference) {
		if (infoItemReference.getInfoItemIdentifier() instanceof
				ClassPKInfoItemIdentifier) {

			ClassPKInfoItemIdentifier classPKInfoItemIdentifier =
				(ClassPKInfoItemIdentifier)
					infoItemReference.getInfoItemIdentifier();

			return classPKInfoItemIdentifier.getClassPK();
		}

		return 0;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AddMultipleDisplayPageTypeSiteNavigationMenuItemMVCActionCommand.class);

	@Reference
	private Language _language;

	@Reference
	private LayoutDisplayPageMultiSelectionProviderTracker
		_layoutDisplayPageMultiSelectionProviderTracker;

	@Reference
	private Portal _portal;

	@Reference
	private SiteNavigationMenuItemService _siteNavigationMenuItemService;

}