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

import com.liferay.commerce.account.model.CommerceAccount;
import com.liferay.commerce.account.util.CommerceAccountHelper;
import com.liferay.commerce.product.catalog.CPCatalogEntry;
import com.liferay.commerce.product.catalog.CPQuery;
import com.liferay.commerce.product.constants.CPPortletKeys;
import com.liferay.commerce.product.data.source.CPDataSourceResult;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CProduct;
import com.liferay.commerce.product.service.CPDefinitionLocalService;
import com.liferay.commerce.product.service.CommerceChannelLocalService;
import com.liferay.commerce.product.url.CPFriendlyURL;
import com.liferay.commerce.product.util.CPDefinitionHelper;
import com.liferay.friendly.url.model.FriendlyURLEntry;
import com.liferay.friendly.url.service.FriendlyURLEntryLocalService;
import com.liferay.layout.admin.kernel.model.LayoutTypePortletConstants;
import com.liferay.layout.admin.kernel.util.Sitemap;
import com.liferay.layout.admin.kernel.util.SitemapURLProvider;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.xml.Element;

import java.io.Serializable;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alec Sloan
 */
@Component(
	enabled = false, immediate = true, service = SitemapURLProvider.class
)
public class CPDefinitionSitemapURLProvider implements SitemapURLProvider {

	@Override
	public String getClassName() {
		return CPDefinition.class.getName();
	}

	@Override
	public void visitLayout(
			Element element, String layoutUuid, LayoutSet layoutSet,
			ThemeDisplay themeDisplay)
		throws PortalException {

		long plid = _portal.getPlidFromPortletId(
			layoutSet.getGroupId(), layoutSet.isPrivateLayout(),
			CPPortletKeys.CP_CONTENT_WEB);

		Layout layout = _layoutLocalService.fetchLayout(plid);

		if (layout == null) {
			return;
		}

		if (layoutUuid.equals(layout.getUuid())) {
			long groupId =
				_commerceChannelLocalService.
					getCommerceChannelGroupIdBySiteGroupId(
						layoutSet.getGroupId());

			CommerceAccount commerceAccount =
				_commerceAccountHelper.getCurrentCommerceAccount(
					themeDisplay.getRequest());

			SearchContext searchContext = new SearchContext();

			searchContext.setAttributes(
				HashMapBuilder.<String, Serializable>put(
					Field.STATUS, WorkflowConstants.STATUS_APPROVED
				).put(
					"commerceAccountGroupIds",
					_commerceAccountHelper.getCommerceAccountGroupIds(
						commerceAccount.getCommerceAccountId())
				).put(
					"commerceChannelGroupId", groupId
				).build());

			searchContext.setCompanyId(themeDisplay.getCompanyId());

			CPQuery cpQuery = new CPQuery();

			cpQuery.setOrderByCol1("title");
			cpQuery.setOrderByCol2("modifiedDate");
			cpQuery.setOrderByType1("ASC");
			cpQuery.setOrderByType2("DESC");

			CPDataSourceResult cpDataSourceResult = _cpDefinitionHelper.search(
				groupId, searchContext, cpQuery, -1, -1);

			for (CPCatalogEntry cpCatalogEntry :
					cpDataSourceResult.getCPCatalogEntries()) {

				if (layoutUuid.equals(layout.getUuid())) {
					visitLayout(
						element, layout, cpCatalogEntry.getCPDefinitionId(),
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
			Element element, Layout layout, long cpDefinitionId,
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

		String urlSeparator = _cpFriendlyURL.getProductURLSeparator(
			themeDisplay.getCompanyId());

		CPDefinition cpDefinition = _cpDefinitionLocalService.getCPDefinition(
			cpDefinitionId);

		FriendlyURLEntry friendlyURLEntry =
			_friendlyURLEntryLocalService.getMainFriendlyURLEntry(
				_portal.getClassNameId(CProduct.class),
				cpDefinition.getCProductId());

		String productFriendlyURL =
			currentSiteURL + urlSeparator +
				friendlyURLEntry.getUrlTitle(themeDisplay.getLanguageId());

		_sitemap.addURLElement(
			element, productFriendlyURL, typeSettingsUnicodeProperties,
			layout.getModifiedDate(), productFriendlyURL, null);
	}

	@Reference
	private CommerceAccountHelper _commerceAccountHelper;

	@Reference
	private CommerceChannelLocalService _commerceChannelLocalService;

	@Reference
	private CPDefinitionHelper _cpDefinitionHelper;

	@Reference
	private CPDefinitionLocalService _cpDefinitionLocalService;

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