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

package com.liferay.commerce.product.asset.categories.web.internal.portlet.action;

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.service.AssetCategoryService;
import com.liferay.friendly.url.model.FriendlyURLEntry;
import com.liferay.friendly.url.service.FriendlyURLEntryLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(
	enabled = false, immediate = true,
	property = {
		"javax.portlet.name=com_liferay_asset_categories_admin_web_portlet_AssetCategoriesAdminPortlet",
		"mvc.command.name=/commerce_product_asset_categories/edit_asset_category_friendly_url"
	},
	service = MVCActionCommand.class
)
public class EditAssetCategoryFriendlyURLMVCActionCommand
	extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long categoryId = ParamUtil.getLong(actionRequest, "categoryId");

		AssetCategory assetCategory = _assetCategoryService.getCategory(
			categoryId);

		Map<Locale, String> urlTitleMap = LocalizationUtil.getLocalizationMap(
			actionRequest, "urlTitleMapAsXML");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			AssetCategory.class.getName(), actionRequest);

		// Commerce product friendly URL

		try {
			FriendlyURLEntry friendlyURLEntry =
				_friendlyURLEntryLocalService.getMainFriendlyURLEntry(
					_portal.getClassNameId(AssetCategory.class), categoryId);

			_friendlyURLEntryLocalService.updateFriendlyURLEntry(
				friendlyURLEntry.getFriendlyURLEntryId(),
				friendlyURLEntry.getClassNameId(),
				friendlyURLEntry.getClassPK(),
				friendlyURLEntry.getDefaultLanguageId(),
				_getUniqueUrlTitles(assetCategory, urlTitleMap));
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception, exception);
			}

			Group companyGroup = _groupLocalService.getCompanyGroup(
				assetCategory.getCompanyId());

			_friendlyURLEntryLocalService.addFriendlyURLEntry(
				companyGroup.getGroupId(),
				_portal.getClassNameId(AssetCategory.class), categoryId,
				_getUniqueUrlTitles(assetCategory, urlTitleMap),
				serviceContext);
		}
	}

	private Map<String, String> _getUniqueUrlTitles(
			AssetCategory assetCategory, Map<Locale, String> urlTitleMap)
		throws PortalException {

		Map<String, String> newUrlTitleMap = new HashMap<>();

		Group companyGroup = _groupLocalService.getCompanyGroup(
			assetCategory.getCompanyId());

		long classNameId = _portal.getClassNameId(AssetCategory.class);

		for (Map.Entry<Locale, String> entry : urlTitleMap.entrySet()) {
			Locale locale = entry.getKey();

			String urlTitle = urlTitleMap.get(locale);

			if (Validator.isNotNull(urlTitle) ||
				((urlTitle != null) && urlTitle.equals(StringPool.BLANK))) {

				urlTitle = _friendlyURLEntryLocalService.getUniqueUrlTitle(
					companyGroup.getGroupId(), classNameId,
					assetCategory.getCategoryId(), urlTitle);

				newUrlTitleMap.put(LocaleUtil.toLanguageId(locale), urlTitle);
			}
		}

		return newUrlTitleMap;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		EditAssetCategoryFriendlyURLMVCActionCommand.class);

	@Reference
	private AssetCategoryService _assetCategoryService;

	@Reference
	private FriendlyURLEntryLocalService _friendlyURLEntryLocalService;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private Portal _portal;

}