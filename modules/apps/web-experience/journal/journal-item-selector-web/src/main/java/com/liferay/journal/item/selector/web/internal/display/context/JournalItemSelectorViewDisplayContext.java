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

package com.liferay.journal.item.selector.web.internal.display.context;

import com.liferay.item.selector.ItemSelectorReturnTypeResolver;
import com.liferay.item.selector.ItemSelectorReturnTypeResolverHandler;
import com.liferay.journal.constants.JournalPortletKeys;
import com.liferay.journal.item.selector.criterion.JournalItemSelectorCriterion;
import com.liferay.journal.item.selector.web.internal.JournalItemSelectorView;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;

import java.util.Locale;

import javax.portlet.ActionRequest;
import javax.portlet.PortletException;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eduardo Garcia
 */
public class JournalItemSelectorViewDisplayContext {

	public JournalItemSelectorViewDisplayContext(
		JournalItemSelectorCriterion journalItemSelectorCriterion,
		JournalItemSelectorView journalItemSelectorView,
		ItemSelectorReturnTypeResolverHandler
			itemSelectorReturnTypeResolverHandler,
		String itemSelectedEventName, boolean search, PortletURL portletURL) {

		_journalItemSelectorCriterion = journalItemSelectorCriterion;
		_journalItemSelectorView = journalItemSelectorView;
		_itemSelectorReturnTypeResolverHandler =
			itemSelectorReturnTypeResolverHandler;
		_itemSelectedEventName = itemSelectedEventName;
		_search = search;
		_portletURL = portletURL;
	}

	public Folder fetchAttachmentsFolder(long userId, long groupId) {
		return null;
	}

	public String getItemSelectedEventName() {
		return _itemSelectedEventName;
	}

	public ItemSelectorReturnTypeResolver getItemSelectorReturnTypeResolver() {
		return _itemSelectorReturnTypeResolverHandler.
			getItemSelectorReturnTypeResolver(
				_journalItemSelectorCriterion, _journalItemSelectorView,
				FileEntry.class);
	}

	public JournalArticle getJournalArticle() throws PortalException {
		return JournalArticleLocalServiceUtil.fetchLatestArticle(
			_journalItemSelectorCriterion.getResourcePrimKey());
	}

	public JournalItemSelectorCriterion getJournalItemSelectorCriterion() {
		return _journalItemSelectorCriterion;
	}

	public PortletURL getPortletURL(
			HttpServletRequest request,
			LiferayPortletResponse liferayPortletResponse)
		throws PortletException {

		PortletURL portletURL = PortletURLUtil.clone(
			_portletURL, liferayPortletResponse);

		portletURL.setParameter(
			"resourcePrimKey",
			String.valueOf(_journalItemSelectorCriterion.getResourcePrimKey()));
		portletURL.setParameter(
			"selectedTab", String.valueOf(getTitle(request.getLocale())));

		return portletURL;
	}

	public String getTitle(Locale locale) {
		return _journalItemSelectorView.getTitle(locale);
	}

	public PortletURL getUploadURL(
		LiferayPortletResponse liferayPortletResponse) {

		PortletURL portletURL = liferayPortletResponse.createActionURL(
			JournalPortletKeys.JOURNAL);

		portletURL.setParameter(
			ActionRequest.ACTION_NAME, "/journal/upload_image");

		return portletURL;
	}

	public boolean isSearch() {
		return _search;
	}

	private final String _itemSelectedEventName;
	private final ItemSelectorReturnTypeResolverHandler
		_itemSelectorReturnTypeResolverHandler;
	private final JournalItemSelectorCriterion _journalItemSelectorCriterion;
	private final JournalItemSelectorView _journalItemSelectorView;
	private final PortletURL _portletURL;
	private final boolean _search;

}