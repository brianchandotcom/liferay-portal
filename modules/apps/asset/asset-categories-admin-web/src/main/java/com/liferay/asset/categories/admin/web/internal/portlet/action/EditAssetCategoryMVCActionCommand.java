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

package com.liferay.asset.categories.admin.web.internal.portlet.action;

import com.liferay.asset.categories.admin.web.constants.AssetCategoriesAdminPortletKeys;
import com.liferay.asset.category.property.model.AssetCategoryProperty;
import com.liferay.asset.category.property.service.AssetCategoryPropertyLocalService;
import com.liferay.asset.display.page.portlet.AssetDisplayPageEntryFormProcessor;
import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetCategoryConstants;
import com.liferay.asset.kernel.service.AssetCategoryService;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.MultiSessionMessages;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Diego Hu
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + AssetCategoriesAdminPortletKeys.ASSET_CATEGORIES_ADMIN,
		"mvc.command.name=/asset_categories_admin/edit_asset_category"
	},
	service = MVCActionCommand.class
)
public class EditAssetCategoryMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long categoryId = ParamUtil.getLong(actionRequest, "categoryId");

		long parentCategoryId = ParamUtil.getLong(
			actionRequest, "parentCategoryId");
		Map<Locale, String> titleMap = LocalizationUtil.getLocalizationMap(
			actionRequest, "title");
		Map<Locale, String> descriptionMap =
			LocalizationUtil.getLocalizationMap(actionRequest, "description");
		long vocabularyId = ParamUtil.getLong(actionRequest, "vocabularyId");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			AssetCategory.class.getName(), actionRequest);

		hideDefaultSuccessMessage(actionRequest);

		MultiSessionMessages.add(
			actionRequest, actionResponse.getNamespace() + "requestProcessed");

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		AssetCategory category = null;

		if (categoryId <= 0) {

			// Add category

			long groupId = ParamUtil.getLong(actionRequest, "groupId");

			category = _assetCategoryService.addCategory(
				groupId, parentCategoryId, titleMap, descriptionMap,
				vocabularyId, null, serviceContext);

			MultiSessionMessages.add(
				actionRequest, "categoryAdded",
				LanguageUtil.format(
					_portal.getHttpServletRequest(actionRequest),
					"x-was-created-successfully",
					new Object[] {
						HtmlUtil.escape(titleMap.get(themeDisplay.getLocale()))
					}));
		}
		else {

			// Update category

			String[] categoryPropertiesArray = _getCategoryProperties(
				_assetCategoryPropertyLocalService.getCategoryProperties(
					categoryId));

			category = _assetCategoryService.updateCategory(
				categoryId, parentCategoryId, titleMap, descriptionMap,
				vocabularyId, categoryPropertiesArray, serviceContext);

			MultiSessionMessages.add(
				actionRequest, "categoryUpdated",
				LanguageUtil.format(
					_portal.getHttpServletRequest(actionRequest),
					"x-was-updated-successfully",
					new Object[] {
						HtmlUtil.escape(titleMap.get(themeDisplay.getLocale()))
					}));
		}

		// Asset display page

		_assetDisplayPageEntryFormProcessor.process(
			AssetCategory.class.getName(), category.getCategoryId(),
			actionRequest);

		sendRedirect(actionRequest, actionResponse);
	}

	private String[] _getCategoryProperties(
		List<AssetCategoryProperty> categoryProperties) {

		String[] categoryPropertiesArray =
			new String[categoryProperties.size()];

		for (int i = 0; i < categoryProperties.size(); i++) {
			AssetCategoryProperty categoryProperty = categoryProperties.get(i);

			categoryPropertiesArray[i] =
				categoryProperty.getKey() +
					AssetCategoryConstants.PROPERTY_KEY_VALUE_SEPARATOR +
						categoryProperty.getValue();
		}

		return categoryPropertiesArray;
	}

	@Reference
	private AssetCategoryPropertyLocalService
		_assetCategoryPropertyLocalService;

	@Reference
	private AssetCategoryService _assetCategoryService;

	@Reference
	private AssetDisplayPageEntryFormProcessor
		_assetDisplayPageEntryFormProcessor;

	@Reference
	private Portal _portal;

}