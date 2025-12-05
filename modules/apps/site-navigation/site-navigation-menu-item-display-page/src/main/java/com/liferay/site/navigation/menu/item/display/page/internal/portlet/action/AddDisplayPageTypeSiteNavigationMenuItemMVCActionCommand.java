/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.navigation.menu.item.display.page.internal.portlet.action;

import com.liferay.info.item.InfoItemClassDetails;
import com.liferay.info.item.InfoItemServiceRegistry;
import com.liferay.info.item.provider.InfoItemDetailsProvider;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.UnicodePropertiesBuilder;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.site.navigation.admin.constants.SiteNavigationAdminPortletKeys;
import com.liferay.site.navigation.exception.SiteNavigationMenuItemNameException;
import com.liferay.site.navigation.model.SiteNavigationMenuItem;
import com.liferay.site.navigation.service.SiteNavigationMenuItemService;

import jakarta.portlet.ActionRequest;
import jakarta.portlet.ActionResponse;

import java.util.Arrays;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Lourdes Fernández Besada
 */
@Component(
	property = {
		"jakarta.portlet.name=" + SiteNavigationAdminPortletKeys.SITE_NAVIGATION_ADMIN,
		"mvc.command.name=/navigation_menu/add_display_page_type_site_navigation_menu_item"
	},
	service = MVCActionCommand.class
)
public class AddDisplayPageTypeSiteNavigationMenuItemMVCActionCommand
	extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		JSONObject jsonObject = _jsonFactory.createJSONObject();

		long classPK = ParamUtil.getLong(actionRequest, "classPK");
		String externalReferenceCode = ParamUtil.getString(
			actionRequest, "externalReferenceCode");
		long siteNavigationMenuId = ParamUtil.getLong(
			actionRequest, "siteNavigationMenuId");
		String siteNavigationMenuItemType = ParamUtil.getString(
			actionRequest, "siteNavigationMenuItemType");

		if ((classPK > 0) && Validator.isNotNull(externalReferenceCode) &&
			(siteNavigationMenuId > 0) &&
			Validator.isNotNull(siteNavigationMenuItemType)) {

			ThemeDisplay themeDisplay =
				(ThemeDisplay)actionRequest.getAttribute(WebKeys.THEME_DISPLAY);

			ServiceContext serviceContext = ServiceContextFactory.getInstance(
				actionRequest);

			long parentSiteNavigationMenuItemId = ParamUtil.getLong(
				actionRequest, "parentSiteNavigationMenuItemId");

			try {
				SiteNavigationMenuItem siteNavigationMenuItem =
					_siteNavigationMenuItemService.addSiteNavigationMenuItem(
						null, themeDisplay.getScopeGroupId(),
						siteNavigationMenuId, parentSiteNavigationMenuItemId,
						siteNavigationMenuItemType,
						UnicodePropertiesBuilder.create(
							true
						).put(
							"className", siteNavigationMenuItemType
						).put(
							"classPK", String.valueOf(classPK)
						).put(
							"externalReferenceCode", externalReferenceCode
						).put(
							"scopeExternalReferenceCode",
							ParamUtil.getString(
								actionRequest, "scopeExternalReferenceCode")
						).put(
							"title", ParamUtil.getString(actionRequest, "title")
						).put(
							"type", ParamUtil.getString(actionRequest, "type")
						).buildString(),
						serviceContext);

				int order = ParamUtil.getInteger(actionRequest, "order", -1);

				if (order >= 0) {
					_siteNavigationMenuItemService.updateSiteNavigationMenuItem(
						siteNavigationMenuItem.getSiteNavigationMenuItemId(),
						parentSiteNavigationMenuItemId, order);
				}

				InfoItemDetailsProvider<?> infoItemDetailsProvider =
					_infoItemServiceRegistry.getFirstInfoItemService(
						InfoItemDetailsProvider.class,
						siteNavigationMenuItemType);

				InfoItemClassDetails infoItemClassDetails =
					infoItemDetailsProvider.getInfoItemClassDetails();

				SessionMessages.add(
					actionRequest, "siteNavigationMenuItemsAdded",
					_language.format(
						themeDisplay.getLocale(), "x-x-was-added-to-this-menu",
						Arrays.asList(
							1,
							infoItemClassDetails.getLabel(
								themeDisplay.getLocale()))));
			}
			catch (SiteNavigationMenuItemNameException
						siteNavigationMenuItemNameException) {

				if (_log.isDebugEnabled()) {
					_log.debug(siteNavigationMenuItemNameException);
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
						"Unable to add site navigation menu item for class PK ",
						classPK, ", external reference code ",
						externalReferenceCode, " site navigation menu ID ",
						siteNavigationMenuId, " and type ",
						siteNavigationMenuItemType));
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

	private static final Log _log = LogFactoryUtil.getLog(
		AddDisplayPageTypeSiteNavigationMenuItemMVCActionCommand.class);

	@Reference
	private InfoItemServiceRegistry _infoItemServiceRegistry;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private Language _language;

	@Reference
	private Portal _portal;

	@Reference
	private SiteNavigationMenuItemService _siteNavigationMenuItemService;

}