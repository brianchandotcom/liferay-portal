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

package com.liferay.journal.web.internal.display.context;

import com.liferay.frontend.taglib.clay.servlet.taglib.display.context.SearchContainerManagementToolbarDisplayContext;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;

import java.util.Objects;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class JournalArticleItemSelectorViewManagementToolbarDisplayContext
	extends SearchContainerManagementToolbarDisplayContext {

	public JournalArticleItemSelectorViewManagementToolbarDisplayContext(
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse,
			HttpServletRequest httpServletRequest,
			JournalArticleItemSelectorViewDisplayContext
				journalArticleItemSelectorViewDisplayContext)
		throws Exception {

		super(
			liferayPortletRequest, liferayPortletResponse, httpServletRequest,
			journalArticleItemSelectorViewDisplayContext.getSearchContainer());

		_journalArticleItemSelectorViewDisplayContext =
			journalArticleItemSelectorViewDisplayContext;
	}

	@Override
	public String getClearResultsURL() {
		PortletURL clearResultsURL = getPortletURL();

		clearResultsURL.setParameter("keywords", StringPool.BLANK);

		return clearResultsURL.toString();
	}

	@Override
	public String getSearchActionURL() {
		PortletURL searchActionURL = getPortletURL();

		return searchActionURL.toString();
	}

	@Override
	public String getSortingOrder() {
		if (Objects.equals(getOrderByCol(), "relevance")) {
			return null;
		}

		return super.getSortingOrder();
	}

	@Override
	public Boolean isSelectable() {
		return false;
	}

	@Override
	protected String getDefaultDisplayStyle() {
		return "descriptive";
	}

	@Override
	protected String[] getDisplayViews() {
		return new String[] {"list", "descriptive", "icon"};
	}

	@Override
	protected String[] getOrderByKeys() {
		if (_journalArticleItemSelectorViewDisplayContext.isSearch()) {
			return new String[] {"relevance", "modified-date", "title"};
		}

		return new String[] {"modified-date", "title"};
	}

	private final JournalArticleItemSelectorViewDisplayContext
		_journalArticleItemSelectorViewDisplayContext;

}