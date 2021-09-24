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

package com.liferay.commerce.product.internal.layout.admin.util;

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.service.AssetCategoryService;
import com.liferay.asset.kernel.service.AssetVocabularyService;
import com.liferay.commerce.product.constants.CPPortletKeys;
import com.liferay.commerce.product.url.CPFriendlyURL;
import com.liferay.friendly.url.model.FriendlyURLEntry;
import com.liferay.friendly.url.service.FriendlyURLEntryLocalService;
import com.liferay.layout.admin.kernel.model.LayoutTypePortletConstants;
import com.liferay.layout.admin.kernel.util.Sitemap;
import com.liferay.layout.admin.kernel.util.SitemapURLProvider;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.xml.Element;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alec Sloan
 */
@Component(
	enabled = false, immediate = true, service = SitemapURLProvider.class
)
public class AssetCategorySitemapURLProvider implements SitemapURLProvider {

	@Override
	public String getClassName() {
		return AssetCategory.class.getName();
	}

	@Override
	public void visitLayout(
			Element element, String layoutUuid, LayoutSet layoutSet,
			ThemeDisplay themeDisplay)
		throws PortalException {

		long plid = _portal.getPlidFromPortletId(
			layoutSet.getGroupId(), layoutSet.isPrivateLayout(),
			CPPortletKeys.CP_CATEGORY_CONTENT_WEB);

		Layout layout = _layoutLocalService.fetchLayout(plid);

		if (layout == null) {
			return;
		}

		if (layoutUuid.equals(layout.getUuid())) {
			Group group = layoutSet.getGroup();

			Company company = _companyLocalService.getCompany(
				group.getCompanyId());

			List<AssetVocabulary> assetVocabularies =
				_assetVocabularyService.getGroupVocabularies(
					company.getGroupId(),
					group.getName(themeDisplay.getLocale()), QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null);

			if (assetVocabularies.size() == 1) {
				AssetVocabulary assetVocabulary = assetVocabularies.get(0);

				List<AssetCategory> assetCategories =
					_assetCategoryService.getVocabularyRootCategories(
						assetVocabulary.getGroupId(),
						assetVocabulary.getVocabularyId(), QueryUtil.ALL_POS,
						QueryUtil.ALL_POS, null);

				for (AssetCategory assetCategory : assetCategories) {
					visitLayout(
						element, layout, assetCategory.getCategoryId(),
						themeDisplay);
				}
			}
		}
	}

	@Override
	public void visitLayoutSet(
			Element element, LayoutSet layoutSet, ThemeDisplay themeDisplay)
		throws PortalException {
	}

	protected void visitLayout(
			Element element, Layout layout, long assetCategoryId,
			ThemeDisplay themeDisplay)
		throws PortalException {

		if (layout.isSystem()) {
			return;
		}

		UnicodeProperties typeSettingsUnicodeProperties =
			layout.getTypeSettingsProperties();

		if (!GetterUtil.getBoolean(
				typeSettingsUnicodeProperties.getProperty(
					LayoutTypePortletConstants.SITEMAP_INCLUDE),
				true)) {

			return;
		}

		String currentSiteURL = _portal.getGroupFriendlyURL(
			layout.getLayoutSet(), themeDisplay, false, false);

		String urlSeparator = _cpFriendlyURL.getAssetCategoryURLSeparator(
			themeDisplay.getCompanyId());

		FriendlyURLEntry friendlyURLEntry =
			_friendlyURLEntryLocalService.getMainFriendlyURLEntry(
				_portal.getClassNameId(AssetCategory.class), assetCategoryId);

		String categoryFriendlyURL =
			currentSiteURL + urlSeparator +
				friendlyURLEntry.getUrlTitle(themeDisplay.getLanguageId());

		_sitemap.addURLElement(
			element, categoryFriendlyURL, typeSettingsUnicodeProperties,
			layout.getModifiedDate(), categoryFriendlyURL, null);
	}

	@Reference
	private AssetCategoryService _assetCategoryService;

	@Reference
	private AssetVocabularyService _assetVocabularyService;

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private CPFriendlyURL _cpFriendlyURL;

	@Reference
	private FriendlyURLEntryLocalService _friendlyURLEntryLocalService;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private Portal _portal;

	@Reference
	private Sitemap _sitemap;

}