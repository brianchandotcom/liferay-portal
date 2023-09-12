/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.content.category.web.internal.display.context;

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.service.AssetCategoryService;
import com.liferay.commerce.constants.CommerceWebKeys;
import com.liferay.commerce.context.CommerceContext;
import com.liferay.commerce.media.CommerceMediaResolver;
import com.liferay.commerce.product.constants.CPAttachmentFileEntryConstants;
import com.liferay.commerce.product.content.category.web.internal.configuration.CPCategoryContentPortletInstanceConfiguration;
import com.liferay.commerce.product.model.CPAttachmentFileEntry;
import com.liferay.commerce.product.service.CPAttachmentFileEntryService;
import com.liferay.commerce.util.CommerceUtil;
import com.liferay.portal.configuration.module.configuration.ConfigurationProviderUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Marco Leo
 * @author Alessio Antonio Rendina
 */
public class CPCategoryContentDisplayContext {

	public CPCategoryContentDisplayContext(
			HttpServletRequest httpServletRequest,
			AssetCategoryService assetCategoryService,
			CommerceMediaResolver commerceMediaResolver,
			CPAttachmentFileEntryService cpAttachmentFileEntryService,
			Portal portal)
		throws ConfigurationException {

		_httpServletRequest = httpServletRequest;
		_assetCategoryService = assetCategoryService;
		_commerceMediaResolver = commerceMediaResolver;
		_cpAttachmentFileEntryService = cpAttachmentFileEntryService;
		_portal = portal;

		_cpCategoryContentPortletInstanceConfiguration =
			ConfigurationProviderUtil.getPortletInstanceConfiguration(
				CPCategoryContentPortletInstanceConfiguration.class,
				(ThemeDisplay)httpServletRequest.getAttribute(
					WebKeys.THEME_DISPLAY));
	}

	public AssetCategory getAssetCategory() throws PortalException {
		if (_cpCategoryContentPortletInstanceConfiguration.useAssetCategory()) {
			_assetCategory = _assetCategoryService.fetchCategory(
				_cpCategoryContentPortletInstanceConfiguration.
					assetCategoryId());
		}
		else {
			_assetCategory = (AssetCategory)_httpServletRequest.getAttribute(
				WebKeys.ASSET_CATEGORY);
		}

		return _assetCategory;
	}

	public String getDefaultImageSrc() throws Exception {
		AssetCategory assetCategory = getAssetCategory();

		if (assetCategory == null) {
			return null;
		}

		List<CPAttachmentFileEntry> cpAttachmentFileEntries =
			_cpAttachmentFileEntryService.getCPAttachmentFileEntries(
				_portal.getClassNameId(AssetCategory.class),
				assetCategory.getCategoryId(),
				CPAttachmentFileEntryConstants.TYPE_IMAGE,
				WorkflowConstants.STATUS_APPROVED, 0, 1);

		if (cpAttachmentFileEntries.isEmpty()) {
			return null;
		}

		CPAttachmentFileEntry cpAttachmentFileEntry =
			cpAttachmentFileEntries.get(0);

		if (cpAttachmentFileEntry == null) {
			return null;
		}

		return _commerceMediaResolver.getURL(
			CommerceUtil.getCommerceAccountId(
				(CommerceContext)_httpServletRequest.getAttribute(
					CommerceWebKeys.COMMERCE_CONTEXT)),
			cpAttachmentFileEntry.getCPAttachmentFileEntryId());
	}

	public String getDisplayStyle() {
		return _cpCategoryContentPortletInstanceConfiguration.displayStyle();
	}

	public long getDisplayStyleGroupId() {
		if (_displayStyleGroupId > 0) {
			return _displayStyleGroupId;
		}

		_displayStyleGroupId =
			_cpCategoryContentPortletInstanceConfiguration.
				displayStyleGroupId();

		if (_displayStyleGroupId <= 0) {
			ThemeDisplay themeDisplay =
				(ThemeDisplay)_httpServletRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			_displayStyleGroupId = themeDisplay.getScopeGroupId();
		}

		return _displayStyleGroupId;
	}

	public boolean useAssetCategory() {
		return _cpCategoryContentPortletInstanceConfiguration.
			useAssetCategory();
	}

	private AssetCategory _assetCategory;
	private final AssetCategoryService _assetCategoryService;
	private final CommerceMediaResolver _commerceMediaResolver;
	private final CPAttachmentFileEntryService _cpAttachmentFileEntryService;
	private final CPCategoryContentPortletInstanceConfiguration
		_cpCategoryContentPortletInstanceConfiguration;
	private long _displayStyleGroupId;
	private final HttpServletRequest _httpServletRequest;
	private final Portal _portal;

}