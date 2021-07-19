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

import com.liferay.info.item.InfoItemClassDetails;
import com.liferay.info.item.InfoItemFormVariation;
import com.liferay.info.item.InfoItemReference;
import com.liferay.info.item.InfoItemServiceTracker;
import com.liferay.info.item.provider.InfoItemDetailsProvider;
import com.liferay.info.item.provider.InfoItemFormVariationsProvider;
import com.liferay.layout.display.page.LayoutDisplayPageObjectProvider;
import com.liferay.layout.display.page.LayoutDisplayPageProvider;
import com.liferay.layout.display.page.LayoutDisplayPageProviderTracker;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.site.navigation.admin.constants.SiteNavigationAdminPortletKeys;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jürgen Kappler
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + SiteNavigationAdminPortletKeys.SITE_NAVIGATION_ADMIN,
		"mvc.command.name=/navigation_menu/get_item_type"
	},
	service = MVCResourceCommand.class
)
public class GetItemTypeMVCResourceCommand extends BaseMVCResourceCommand {

	@Override
	protected void doServeResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)resourceRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long classNameId = ParamUtil.getLong(resourceRequest, "classNameId");
		long classPK = ParamUtil.getLong(resourceRequest, "classPK");
		long classTypeId = ParamUtil.getLong(resourceRequest, "classTypeId");

		try {
			JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

			String itemType = _getItemType(classNameId, themeDisplay);

			if (Validator.isNotNull(itemType)) {
				jsonObject.put("itemType", itemType);
			}

			String itemSubtype = _getItemSubtype(
				classNameId, classPK, classTypeId, themeDisplay);

			if (Validator.isNotNull(itemSubtype)) {
				jsonObject.put("itemSubtype", itemSubtype);
			}

			JSONPortletResponseUtil.writeJSON(
				resourceRequest, resourceResponse, jsonObject);
		}
		catch (Exception exception) {
			_log.error("Unable to get mapping fields", exception);

			JSONPortletResponseUtil.writeJSON(
				resourceRequest, resourceResponse,
				JSONUtil.put(
					"error",
					LanguageUtil.get(
						themeDisplay.getRequest(),
						"an-unexpected-error-occurred")));
		}
	}

	private String _getItemSubtype(
		long classNameId, long classPK, long classTypeId,
		ThemeDisplay themeDisplay) {

		String className = _portal.getClassName(classNameId);

		InfoItemFormVariationsProvider<?> infoItemFormVariationsProvider =
			_infoItemServiceTracker.getFirstInfoItemService(
				InfoItemFormVariationsProvider.class, className);

		if (infoItemFormVariationsProvider == null) {
			return StringPool.BLANK;
		}

		LayoutDisplayPageProvider<?> layoutDisplayPageProvider =
			_layoutDisplayPageProviderTracker.
				getLayoutDisplayPageProviderByClassName(className);

		if (layoutDisplayPageProvider == null) {
			return StringPool.BLANK;
		}

		LayoutDisplayPageObjectProvider<?> layoutDisplayPageObjectProvider =
			layoutDisplayPageProvider.getLayoutDisplayPageObjectProvider(
				new InfoItemReference(className, classPK));

		if (layoutDisplayPageObjectProvider == null) {
			return StringPool.BLANK;
		}

		InfoItemFormVariation infoItemFormVariation =
			infoItemFormVariationsProvider.getInfoItemFormVariation(
				layoutDisplayPageObjectProvider.getGroupId(),
				String.valueOf(classTypeId));

		if (infoItemFormVariation != null) {
			return infoItemFormVariation.getLabel(themeDisplay.getLocale());
		}

		return StringPool.BLANK;
	}

	private String _getItemType(long classNameId, ThemeDisplay themeDisplay) {
		InfoItemDetailsProvider<?> infoItemDetailsProvider =
			_infoItemServiceTracker.getFirstInfoItemService(
				InfoItemDetailsProvider.class,
				_portal.getClassName(classNameId));

		if (infoItemDetailsProvider == null) {
			return StringPool.BLANK;
		}

		InfoItemClassDetails infoItemClassDetails =
			infoItemDetailsProvider.getInfoItemClassDetails();

		if (infoItemClassDetails != null) {
			return infoItemClassDetails.getLabel(themeDisplay.getLocale());
		}

		return StringPool.BLANK;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		GetItemTypeMVCResourceCommand.class);

	@Reference
	private InfoItemServiceTracker _infoItemServiceTracker;

	@Reference
	private LayoutDisplayPageProviderTracker _layoutDisplayPageProviderTracker;

	@Reference
	private Portal _portal;

}