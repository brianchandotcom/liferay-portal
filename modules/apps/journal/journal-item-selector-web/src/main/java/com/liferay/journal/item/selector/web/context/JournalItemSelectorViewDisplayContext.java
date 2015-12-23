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

package com.liferay.journal.item.selector.web.context;

import com.liferay.journal.constants.JournalPortletKeys;
import com.liferay.journal.item.selector.criterion.JournalItemSelectorCriterion;
import com.liferay.journal.item.selector.web.JournalItemSelectorView;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portlet.PortletURLUtil;

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
		String itemSelectedEventName, boolean search, PortletURL portletURL) {

		_journalItemSelectorCriterion = journalItemSelectorCriterion;
		_journalItemSelectorView = journalItemSelectorView;
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
	private final JournalItemSelectorCriterion _journalItemSelectorCriterion;
	private final JournalItemSelectorView _journalItemSelectorView;
	private final PortletURL _portletURL;
	private final boolean _search;

}