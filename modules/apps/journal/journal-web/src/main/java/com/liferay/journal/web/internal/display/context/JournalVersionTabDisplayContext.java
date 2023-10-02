/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.journal.web.internal.display.context;

import com.liferay.asset.display.page.portlet.AssetDisplayPageFriendlyURLProvider;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.web.internal.servlet.taglib.util.JournalArticleActionDropdownItemsProvider;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.trash.TrashHelper;

import java.util.List;

/**
 * @author Clara Izquierdo
 */
public class JournalVersionTabDisplayContext {

	public JournalVersionTabDisplayContext(
		AssetDisplayPageFriendlyURLProvider assetDisplayPageFriendlyURLProvider,
		JournalArticle article, LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse,
		TrashHelper trashHelper) {

		_assetDisplayPageFriendlyURLProvider =
			assetDisplayPageFriendlyURLProvider;
		_article = article;
		_liferayPortletRequest = liferayPortletRequest;
		_liferayPortletResponse = liferayPortletResponse;
		_trashHelper = trashHelper;
	}

	public List<DropdownItem> getArticleHistoryActionDropdownItems(
		JournalArticle article) {

		JournalArticleActionDropdownItemsProvider
			articleActionDropdownItemsProvider =
				new JournalArticleActionDropdownItemsProvider(
					article, _liferayPortletRequest, _liferayPortletResponse,
					_assetDisplayPageFriendlyURLProvider, _trashHelper);

		return articleActionDropdownItemsProvider.
			getArticleVersionTabActionDropdownItems();
	}

	public String getViewMoreURL() {
		return PortletURLBuilder.createRenderURL(
			_liferayPortletResponse
		).setMVCPath(
			"/view_article_history.jsp"
		).setBackURL(
			PortletURLBuilder.createRenderURL(
				_liferayPortletResponse
			).buildString()
		).setParameter(
			"articleId", _article.getArticleId()
		).buildString();
	}

	private final JournalArticle _article;
	private final AssetDisplayPageFriendlyURLProvider
		_assetDisplayPageFriendlyURLProvider;
	private final LiferayPortletRequest _liferayPortletRequest;
	private final LiferayPortletResponse _liferayPortletResponse;
	private final TrashHelper _trashHelper;

}