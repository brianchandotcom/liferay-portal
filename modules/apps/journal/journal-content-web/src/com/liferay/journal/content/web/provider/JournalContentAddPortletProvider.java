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

package com.liferay.journal.content.web.provider;

import com.liferay.journal.content.web.constants.JournalContentPortletKeys;
import com.liferay.journal.web.asset.JournalArticleAssetRenderer;
import com.liferay.journal.web.asset.JournalArticleAssetRendererFactory;
import com.liferay.portal.kernel.provider.AddPortletProvider;
import com.liferay.portal.model.Layout;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.asset.AssetRendererFactoryRegistryUtil;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.service.AssetEntryLocalService;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.service.JournalContentSearchLocalService;

import javax.portlet.PortletPreferences;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(
	immediate = true,
	property = {
		"model.class.name=com.liferay.portlet.journal.model.JournalArticle"
	},
	service = AddPortletProvider.class
)
public class JournalContentAddPortletProvider implements AddPortletProvider {

	@Override
	public String getPortletId() {
		return JournalContentPortletKeys.JOURNAL_CONTENT;
	}

	@Override
	public void updatePortletPreferences(
			PortletPreferences portletPreferences, String portletId,
			String className, long classPK, ThemeDisplay themeDisplay)
		throws Exception {

		AssetEntry assetEntry = _assetEntryLocalService.getEntry(
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

		_journalContentSearchLocal.updateContentSearch(
			layout.getGroupId(), layout.getLayoutId(), portletId,
			article.getArticleId(), true);
	}

	@Reference
	protected void setAssetEntryLocalService(
		AssetEntryLocalService assetEntryLocalService) {

		_assetEntryLocalService = assetEntryLocalService;
	}

	@Reference
	protected void setJournalContentSearchLocal(
		JournalContentSearchLocalService journalContentSearchLocal) {

		_journalContentSearchLocal = journalContentSearchLocal;
	}

	protected void unsetAssetEntryLocalService(
		AssetEntryLocalService assetEntryLocalService) {

		_assetEntryLocalService = null;
	}

	protected void unsetJournalContentSearchLocal(
		JournalContentSearchLocalService journalContentSearchLocal) {

		_journalContentSearchLocal = null;
	}

	private AssetEntryLocalService _assetEntryLocalService;
	private JournalContentSearchLocalService _journalContentSearchLocal;

}