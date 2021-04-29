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

import com.liferay.document.library.kernel.util.DLUtil;
import com.liferay.item.selector.ItemSelectorReturnTypeResolver;
import com.liferay.item.selector.ItemSelectorReturnTypeResolverHandler;
import com.liferay.item.selector.taglib.servlet.taglib.util.RepositoryEntryBrowserTagUtil;
import com.liferay.journal.constants.JournalPortletKeys;
import com.liferay.journal.item.selector.criterion.JournalItemSelectorCriterion;
import com.liferay.journal.item.selector.web.internal.JournalItemSelectorView;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.PortalPreferences;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.Locale;

import javax.portlet.PortletException;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eduardo García
 */
public class JournalItemSelectorViewDisplayContext {

	public JournalItemSelectorViewDisplayContext(
		HttpServletRequest httpServletRequest, String itemSelectedEventName,
		ItemSelectorReturnTypeResolverHandler
			itemSelectorReturnTypeResolverHandler,
		JournalItemSelectorCriterion journalItemSelectorCriterion,
		JournalItemSelectorView journalItemSelectorView, PortletURL portletURL,
		boolean search) {

		_httpServletRequest = httpServletRequest;
		_itemSelectedEventName = itemSelectedEventName;
		_itemSelectorReturnTypeResolverHandler =
			itemSelectorReturnTypeResolverHandler;
		_journalItemSelectorCriterion = journalItemSelectorCriterion;
		_journalItemSelectorView = journalItemSelectorView;
		_portletURL = portletURL;
		_search = search;

		_portalPreferences = PortletPreferencesFactoryUtil.getPortalPreferences(
			_httpServletRequest);
	}

	public Folder fetchAttachmentsFolder(long userId, long groupId) {
		return null;
	}

	public PortletURL getEditImageURL(
		LiferayPortletResponse liferayPortletResponse) {

		return PortletURLBuilder.createActionURL(
			liferayPortletResponse, JournalPortletKeys.JOURNAL
		).setActionName(
			"/journal/image_editor"
		).setParameter(
			"folderId", _journalItemSelectorCriterion.getFolderId()
		).setParameter(
			"resourcePrimKey",
			_journalItemSelectorCriterion.getResourcePrimKey()
		).build();
	}

	public String getItemSelectedEventName() {
		return _itemSelectedEventName;
	}

	public ItemSelectorReturnTypeResolver<?, ?>
		getItemSelectorReturnTypeResolver() {

		return _itemSelectorReturnTypeResolverHandler.
			getItemSelectorReturnTypeResolver(
				_journalItemSelectorCriterion, _journalItemSelectorView,
				FileEntry.class);
	}

	public JournalArticle getJournalArticle() {
		return JournalArticleLocalServiceUtil.fetchLatestArticle(
			_journalItemSelectorCriterion.getResourcePrimKey());
	}

	public JournalItemSelectorCriterion getJournalItemSelectorCriterion() {
		return _journalItemSelectorCriterion;
	}

	public OrderByComparator<?> getOrderByComparator() {
		return DLUtil.getRepositoryModelOrderByComparator(
			RepositoryEntryBrowserTagUtil.getOrderByCol(
				_httpServletRequest, _portalPreferences),
			RepositoryEntryBrowserTagUtil.getOrderByType(
				_httpServletRequest, _portalPreferences));
	}

	public PortletURL getPortletURL(
			HttpServletRequest httpServletRequest,
			LiferayPortletResponse liferayPortletResponse)
		throws PortletException {

		return PortletURLBuilder.create(
			PortletURLUtil.clone(_portletURL, liferayPortletResponse)
		).setParameter(
			"resourcePrimKey",
			_journalItemSelectorCriterion.getResourcePrimKey()
		).setParameter(
			"selectedTab", getTitle(httpServletRequest.getLocale())
		).build();
	}

	public String getTitle(Locale locale) {
		return _journalItemSelectorView.getTitle(locale);
	}

	public PortletURL getUploadURL(
		LiferayPortletResponse liferayPortletResponse) {

		return PortletURLBuilder.createActionURL(
			liferayPortletResponse, JournalPortletKeys.JOURNAL
		).setActionName(
			"/journal/upload_image"
		).setParameter(
			"folderId", _journalItemSelectorCriterion.getFolderId()
		).setParameter(
			"resourcePrimKey",
			_journalItemSelectorCriterion.getResourcePrimKey()
		).build();
	}

	public boolean isSearch() {
		return _search;
	}

	private final HttpServletRequest _httpServletRequest;
	private final String _itemSelectedEventName;
	private final ItemSelectorReturnTypeResolverHandler
		_itemSelectorReturnTypeResolverHandler;
	private final JournalItemSelectorCriterion _journalItemSelectorCriterion;
	private final JournalItemSelectorView _journalItemSelectorView;
	private final PortalPreferences _portalPreferences;
	private final PortletURL _portletURL;
	private final boolean _search;

}