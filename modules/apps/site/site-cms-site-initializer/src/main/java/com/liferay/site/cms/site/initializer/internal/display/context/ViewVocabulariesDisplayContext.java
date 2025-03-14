/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cms.site.initializer.internal.display.context;

import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.PortalUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Noor Najjar
 */
public class ViewVocabulariesDisplayContext {

	public ViewVocabulariesDisplayContext(ThemeDisplay themeDisplay) {
		_themeDisplay = themeDisplay;
	}

	public List<AssetRendererFactory<?>> getAvailableAssetRendererFactories() {
		return ListUtil.filter(
			AssetRendererFactoryRegistryUtil.getAssetRendererFactories(
				_themeDisplay.getCompanyId()),
			AssetRendererFactory::isCategorizable);
	}

	public List<Map<String, String>> getClassNameIdOptions() {
		List<Map<String, String>> selectOptions = new ArrayList<>();

		List<AssetRendererFactory<?>> availableAssetRendererFactories =
			getAvailableAssetRendererFactories();

		for (AssetRendererFactory<?> availableAssetRendererFactory :
				availableAssetRendererFactories) {

			selectOptions.add(
				HashMapBuilder.put(
					"icon", availableAssetRendererFactory.getIconCssClass()
				).put(
					"label",
					ResourceActionsUtil.getModelResource(
						_themeDisplay.getLocale(),
						availableAssetRendererFactory.getClassName())
				).put(
					"restricted", Boolean.FALSE.toString()
				).put(
					"value",
					String.valueOf(
						availableAssetRendererFactory.getClassNameId())
				).build());
		}

		return selectOptions;
	}

	public Map<String, Object> getReactData() throws PortalException {
		return HashMapBuilder.<String, Object>put(
			"addVocabularyURL",
			PortalUtil.getLayoutFullURL(
				LayoutLocalServiceUtil.getLayoutByFriendlyURL(
					_themeDisplay.getScopeGroupId(), false,
					"/categorization/new_vocabulary"),
				_themeDisplay)
		).put(
			"assetTypes", getClassNameIdOptions()
		).put(
			"siteId", _themeDisplay.getScopeGroupId()
		).put(
			"tagsURL",
			PortalUtil.getLayoutFullURL(
				LayoutLocalServiceUtil.getLayoutByFriendlyURL(
					_themeDisplay.getScopeGroupId(), false,
					"/categorization/view_tags"),
				_themeDisplay)
		).put(
			"vocabulariesURL",
			PortalUtil.getLayoutFullURL(
				LayoutLocalServiceUtil.getLayoutByFriendlyURL(
					_themeDisplay.getScopeGroupId(), false,
					"/categorization/view_vocabularies"),
				_themeDisplay)
		).build();
	}

	private final ThemeDisplay _themeDisplay;

}