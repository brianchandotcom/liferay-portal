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

package com.liferay.portlet.journal.search;

import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.PortalPreferences;
import com.liferay.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.util.JournalUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletConfig;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

/**
 * @author Brian Wing Shun Chan
 */
public class ArticleSearch extends SearchContainer<JournalArticle> {

	static List<String> headerNames = new ArrayList<String>();
	static Map<String, String> orderableHeaders = new HashMap<String, String>();

	static {
		headerNames.add("id");
		headerNames.add("title");
		headerNames.add("status");
		//headerNames.add("version");
		headerNames.add("modified-date");
		headerNames.add("display-date");
		headerNames.add("author");
		headerNames.add(StringPool.BLANK);

		orderableHeaders.put("id", "id");
		orderableHeaders.put("title", "title");
		//orderableHeaders.put("version", "version");
		orderableHeaders.put("modified-date", "modified-date");
		orderableHeaders.put("display-date", "display-date");
	}

	public ArticleSearch(
		PortletRequest portletRequest, int cur, int delta,
		PortletURL iteratorURL) {

		super(
			portletRequest, new ArticleDisplayTerms(portletRequest),
			new ArticleSearchTerms(portletRequest), DEFAULT_CUR_PARAM, cur,
			delta, iteratorURL, headerNames, null);

		PortletConfig portletConfig =
			(PortletConfig)portletRequest.getAttribute(
				JavaConstants.JAVAX_PORTLET_CONFIG);

		ArticleDisplayTerms displayTerms =
			(ArticleDisplayTerms)getDisplayTerms();
		ArticleSearchTerms searchTerms = (ArticleSearchTerms)getSearchTerms();

		String portletName = portletConfig.getPortletName();

		if (!portletName.equals(PortletKeys.JOURNAL)) {
			displayTerms.setStatus("approved");
			searchTerms.setStatus("approved");
		}

		iteratorURL.setParameter(
			ArticleDisplayTerms.ARTICLE_ID, displayTerms.getArticleId());
		iteratorURL.setParameter(
			ArticleDisplayTerms.CONTENT, displayTerms.getContent());
		iteratorURL.setParameter(
			ArticleDisplayTerms.DESCRIPTION, displayTerms.getDescription());
		iteratorURL.setParameter(
			ArticleDisplayTerms.FOLDER_ID,
			String.valueOf(displayTerms.getFolderId()));
		iteratorURL.setParameter(
			ArticleDisplayTerms.GROUP_ID,
			String.valueOf(displayTerms.getGroupId()));
		iteratorURL.setParameter(
			ArticleDisplayTerms.NAVIGATION, displayTerms.getNavigation());
		iteratorURL.setParameter(
			ArticleDisplayTerms.STATUS, displayTerms.getStatus());
		iteratorURL.setParameter(
			ArticleDisplayTerms.STRUCTURE_ID, displayTerms.getStructureId());
		iteratorURL.setParameter(
			ArticleDisplayTerms.TEMPLATE_ID, displayTerms.getTemplateId());
		iteratorURL.setParameter(
			ArticleDisplayTerms.TITLE, displayTerms.getTitle());
		iteratorURL.setParameter(
			ArticleDisplayTerms.TYPE, displayTerms.getType());
		iteratorURL.setParameter(
			ArticleDisplayTerms.VERSION,
			String.valueOf(displayTerms.getVersion()));

		try {
			PortalPreferences preferences =
				PortletPreferencesFactoryUtil.getPortalPreferences(
					portletRequest);

			String orderByCol = ParamUtil.getString(
				portletRequest, "orderByCol");
			String orderByType = ParamUtil.getString(
				portletRequest, "orderByType");

			if (Validator.isNotNull(orderByCol) &&
				Validator.isNotNull(orderByType)) {

				preferences.setValue(
					PortletKeys.JOURNAL, "articles-order-by-col", orderByCol);
				preferences.setValue(
					PortletKeys.JOURNAL, "articles-order-by-type", orderByType);
			}
			else {
				orderByCol = preferences.getValue(
					PortletKeys.JOURNAL, "articles-order-by-col", "id");
				orderByType = preferences.getValue(
					PortletKeys.JOURNAL, "articles-order-by-type", "asc");
			}

			OrderByComparator orderByComparator =
				JournalUtil.getArticleOrderByComparator(
					orderByCol, orderByType);

			setOrderableHeaders(orderableHeaders);
			setOrderByCol(orderByCol);
			setOrderByType(orderByType);
			setOrderByComparator(orderByComparator);
		}
		catch (Exception e) {
			_log.error(e);
		}
	}

	public ArticleSearch(
		PortletRequest portletRequest, PortletURL iteratorURL) {

		this(portletRequest, 0, DEFAULT_DELTA, iteratorURL);
	}

	private static Log _log = LogFactoryUtil.getLog(ArticleSearch.class);

}