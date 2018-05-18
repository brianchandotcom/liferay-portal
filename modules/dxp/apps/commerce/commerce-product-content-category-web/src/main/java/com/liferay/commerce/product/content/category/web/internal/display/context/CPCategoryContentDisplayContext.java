/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.product.content.category.web.internal.display.context;

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.service.AssetCategoryService;
import com.liferay.commerce.product.content.category.web.internal.configuration.CPCategoryContentPortletInstanceConfiguration;
import com.liferay.commerce.product.model.CPAttachmentFileEntry;
import com.liferay.commerce.product.model.CPAttachmentFileEntryConstants;
import com.liferay.commerce.product.service.CPAttachmentFileEntryService;
import com.liferay.document.library.kernel.util.DLUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.theme.PortletDisplay;
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
			CPAttachmentFileEntryService cpAttachmentFileEntryService,
			Portal portal)
		throws ConfigurationException {

		_httpServletRequest = httpServletRequest;
		_assetCategoryService = assetCategoryService;
		_cpAttachmentFileEntryService = cpAttachmentFileEntryService;
		_portal = portal;

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		_cpCategoryContentPortletInstanceConfiguration =
			portletDisplay.getPortletInstanceConfiguration(
				CPCategoryContentPortletInstanceConfiguration.class);
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

	public String getDefaultImageSrc(ThemeDisplay themeDisplay)
		throws Exception {

		AssetCategory assetCategory = getAssetCategory();

		if (assetCategory == null) {
			return null;
		}

		CPAttachmentFileEntry cpAttachmentFileEntry = null;

		long classNameId = _portal.getClassNameId(AssetCategory.class);

		List<CPAttachmentFileEntry> cpAttachmentFileEntries =
			_cpAttachmentFileEntryService.getCPAttachmentFileEntries(
				classNameId, assetCategory.getCategoryId(),
				CPAttachmentFileEntryConstants.TYPE_IMAGE,
				WorkflowConstants.STATUS_APPROVED, 0, 1);

		if (cpAttachmentFileEntries.isEmpty()) {
			return null;
		}

		cpAttachmentFileEntry = cpAttachmentFileEntries.get(0);

		if (cpAttachmentFileEntry == null) {
			return null;
		}

		FileEntry fileEntry = cpAttachmentFileEntry.getFileEntry();

		if (fileEntry == null) {
			return null;
		}

		return DLUtil.getDownloadURL(
			fileEntry, fileEntry.getFileVersion(), themeDisplay,
			StringPool.BLANK);
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
		return
			_cpCategoryContentPortletInstanceConfiguration.useAssetCategory();
	}

	private AssetCategory _assetCategory;
	private final AssetCategoryService _assetCategoryService;
	private final CPAttachmentFileEntryService _cpAttachmentFileEntryService;
	private final CPCategoryContentPortletInstanceConfiguration
		_cpCategoryContentPortletInstanceConfiguration;
	private long _displayStyleGroupId;
	private final HttpServletRequest _httpServletRequest;
	private final Portal _portal;

}