/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.wiki.action;

import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portlet.rss.RSSRenderer;
import com.liferay.portlet.rss.action.DefaultRSSAction;
import com.liferay.portlet.wiki.model.WikiPage;
import com.liferay.portlet.wiki.service.WikiPageServiceUtil;
import com.liferay.portlet.wiki.util.comparator.PageCreateDateComparator;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Jorge Ferrer
 */
public class RSSAction extends DefaultRSSAction {

	@Override
	protected RSSRenderer getRSSRenderer(HttpServletRequest request)
		throws Exception {

		long nodeId = ParamUtil.getLong(request, "nodeId");
		String title = ParamUtil.getString(request, "title");
		int max = ParamUtil.getInteger(
			request, "max", SearchContainer.DEFAULT_DELTA);

		if (nodeId > 0) {
			if (Validator.isNotNull(title)) {
				List<WikiPage> pages = WikiPageServiceUtil. getPages(
					nodeId, title, 0, max, new PageCreateDateComparator(true));

				return new WikiRSSRenderer(request, pages, true);
			}
			else {
				List<WikiPage> pages = WikiPageServiceUtil.getNodePages(
					nodeId, max);
				return new WikiRSSRenderer(request, pages, false);
			}
		}

		throw new UnsupportedOperationException();
	}

}