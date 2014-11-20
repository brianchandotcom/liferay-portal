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

package com.liferay.journal.content.web.portlet.asset;

import com.liferay.journal.content.web.util.JournalContentPortletKeys;
import com.liferay.portal.model.Layout;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.asset.AssetRendererFactoryRegistryUtil;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.provider.DisplayPortletProvider;
import com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil;
import com.liferay.portlet.journal.asset.JournalArticleAssetRenderer;
import com.liferay.portlet.journal.asset.JournalArticleAssetRendererFactory;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.service.JournalContentSearchLocalServiceUtil;

import javax.portlet.PortletPreferences;

import org.osgi.service.component.annotations.Component;

/**
 * @author Eudaldo Alonso
*/
@Component(
	immediate = true,
	property = {
		"model.class.name=com.liferay.portlet.journal.model.JournalArticle"
	},
	service = DisplayPortletProvider.class
)
public class JournalContentDisplayPortletProvider
	implements DisplayPortletProvider {

	@Override
	public String getPortletId() {
		return JournalContentPortletKeys.JOURNAL_CONTENT;
	}

	@Override
	public void setPortletPreferences(
			PortletPreferences portletPreferences, String portletId,
			String className, long classPK, ThemeDisplay themeDisplay)
		throws Exception {

		AssetEntry assetEntry = AssetEntryLocalServiceUtil.getEntry(
			className, classPK);

		JournalArticleAssetRendererFactory articleAssetRendererFactory =
			(JournalArticleAssetRendererFactory)
				AssetRendererFactoryRegistryUtil.
					getAssetRendererFactoryByClassName(
						JournalArticle.class.getName());

		JournalArticleAssetRenderer articleAssetRenderer =
			(JournalArticleAssetRenderer)articleAssetRendererFactory.
				getAssetRenderer(assetEntry.getClassPK());

		JournalArticle article = articleAssetRenderer.getArticle();

		portletPreferences.setValue("articleId", article.getArticleId());
		portletPreferences.setValue(
			"groupId", String.valueOf(article.getGroupId()));

		Layout layout = themeDisplay.getLayout();

		JournalContentSearchLocalServiceUtil.updateContentSearch(
			layout.getGroupId(), layout.isPrivateLayout(), layout.getLayoutId(),
			portletId, article.getArticleId(), true);
	}

}