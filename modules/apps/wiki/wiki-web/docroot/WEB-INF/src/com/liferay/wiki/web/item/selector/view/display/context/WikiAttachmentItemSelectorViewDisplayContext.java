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

package com.liferay.wiki.web.item.selector.view.display.context;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portlet.PortletURLUtil;
import com.liferay.wiki.item.selector.criterion.WikiAttachmentItemSelectorCriterion;
import com.liferay.wiki.model.WikiPage;
import com.liferay.wiki.service.WikiPageLocalServiceUtil;
import com.liferay.wiki.web.item.selector.view.WikiAttachmentItemSelectorView;

import java.util.Locale;

import javax.portlet.PortletException;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Roberto Díaz
 */
public class WikiAttachmentItemSelectorViewDisplayContext {

	public WikiAttachmentItemSelectorViewDisplayContext(
		WikiAttachmentItemSelectorCriterion wikiAttachmentItemSelectorCriterion,
		WikiAttachmentItemSelectorView
			wikiAttachmentItemSelectorView,
		String itemSelectedEventName, PortletURL portletURL) {

		_wikiAttachmentItemSelectorCriterion =
			wikiAttachmentItemSelectorCriterion;
		_wikiAttachmentItemSelectorView = wikiAttachmentItemSelectorView;
		_itemSelectedEventName = itemSelectedEventName;
		_portletURL = portletURL;
	}

	public String getDisplayStyle(HttpServletRequest request) {
		return ParamUtil.getString(request, "displayStyle");
	}

	public String getItemSelectedEventName() {
		return _itemSelectedEventName;
	}

	public PortletURL getPortletURL(
			HttpServletRequest request,
			LiferayPortletResponse liferayPortletResponse)
		throws PortletException {

		PortletURL portletURL = PortletURLUtil.clone(
			_portletURL, liferayPortletResponse);

		portletURL.setParameter("displayStyle", getDisplayStyle(request));

		return portletURL;
	}

	public String getTitle(Locale locale) {
		return _wikiAttachmentItemSelectorView.getTitle(locale);
	}

	public WikiAttachmentItemSelectorCriterion
		getWikiAttachmentItemSelectorCriterion() {

		return _wikiAttachmentItemSelectorCriterion;
	}

	public WikiPage getWikiPage() throws PortalException {
		return WikiPageLocalServiceUtil.getPage(
			_wikiAttachmentItemSelectorCriterion.getWikiPageResourceId());
	}

	private final String _itemSelectedEventName;
	private final PortletURL _portletURL;
	private final WikiAttachmentItemSelectorCriterion
		_wikiAttachmentItemSelectorCriterion;
	private final WikiAttachmentItemSelectorView
		_wikiAttachmentItemSelectorView;

}