/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.knowledge.base.web.internal.portlet.info.display.contributor.portlet;

import com.liferay.asset.display.page.model.AssetDisplayPageEntry;
import com.liferay.asset.display.page.portlet.BaseAssetDisplayPageFriendlyURLResolver;
import com.liferay.knowledge.base.model.KBArticle;
import com.liferay.layout.display.page.LayoutDisplayPageObjectProvider;
import com.liferay.portal.kernel.portlet.FriendlyURLResolver;
import com.liferay.portal.kernel.portlet.constants.FriendlyURLResolverConstants;

import org.osgi.service.component.annotations.Component;

/**
 * @author Adolfo Pérez
 */
@Component(service = FriendlyURLResolver.class)
public class KBArticleAssetDisplayPageFriendlyURLResolver
	extends BaseAssetDisplayPageFriendlyURLResolver {

	@Override
	public String getURLSeparator() {
		return FriendlyURLResolverConstants.
			URL_SEPARATOR_KNOWLEDGE_BASE_ARTICLE;
	}

	@Override
	protected AssetDisplayPageEntry getAssetDisplayPageEntry(
		long groupId,
		LayoutDisplayPageObjectProvider<?> layoutDisplayPageObjectProvider) {

		KBArticle kbArticle =
			(KBArticle)layoutDisplayPageObjectProvider.getDisplayObject();

		AssetDisplayPageEntry assetDisplayPageEntry =
			assetDisplayPageEntryLocalService.fetchAssetDisplayPageEntry(
				groupId, layoutDisplayPageObjectProvider.getClassNameId(),
				kbArticle.getKbArticleId());

		if (assetDisplayPageEntry != null) {
			return assetDisplayPageEntry;
		}

		return assetDisplayPageEntryLocalService.fetchAssetDisplayPageEntry(
			groupId, layoutDisplayPageObjectProvider.getClassNameId(),
			kbArticle.getResourcePrimKey());
	}

}