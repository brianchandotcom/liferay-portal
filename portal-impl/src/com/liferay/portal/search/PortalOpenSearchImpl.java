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

package com.liferay.portal.search;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.BaseOpenSearchImpl;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.search.Summary;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.service.CompanyLocalServiceUtil;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.model.JournalArticleConstants;
import com.liferay.portlet.journal.service.JournalArticleServiceUtil;
import com.liferay.portlet.journal.service.JournalContentSearchLocalServiceUtil;

import java.util.Date;
import java.util.List;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Charles May
 * @author Brian Wing Shun Chan
 */
public class PortalOpenSearchImpl extends BaseOpenSearchImpl {

	public static final String SEARCH_PATH = "/c/search/open_search";

	@Override
	public String search(
			HttpServletRequest request, long groupId, long userId,
			String keywords, int startPage, int itemsPerPage, String format)
		throws SearchException {

		try {
			ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
				WebKeys.THEME_DISPLAY);

			int start = (startPage * itemsPerPage) - itemsPerPage;
			int end = startPage * itemsPerPage;

			Hits results = CompanyLocalServiceUtil.search(
				themeDisplay.getCompanyId(), userId, keywords, start, end);

			String[] queryTerms = results.getQueryTerms();

			int total = results.getLength();

			Object[] values = addSearchResults(
				queryTerms, keywords, startPage, itemsPerPage, total, start,
				"Liferay Portal Search: " + keywords, SEARCH_PATH, format,
				themeDisplay);

			com.liferay.portal.kernel.xml.Document doc =
				(com.liferay.portal.kernel.xml.Document)values[0];
			Element root = (Element)values[1];

			for (int i = 0; i < results.getDocs().length; i++) {
				Document result = results.doc(i);

				String portletId = result.get(Field.PORTLET_ID);

				Portlet portlet = PortletLocalServiceUtil.getPortletById(
					themeDisplay.getCompanyId(), portletId);

				if (portlet == null) {
					continue;
				}

				String portletTitle = PortalUtil.getPortletTitle(
					portletId, themeDisplay.getUser());

				long resultGroupId = GetterUtil.getLong(
					result.get(Field.GROUP_ID));

				long resultScopeGroupId = GetterUtil.getLong(
					result.get(Field.SCOPE_GROUP_ID));

				if (resultScopeGroupId == 0) {
					resultScopeGroupId = themeDisplay.getScopeGroupId();
				}

				String entryClassName = GetterUtil.getString(
					result.get(Field.ENTRY_CLASS_NAME));

				long entryClassPK = GetterUtil.getLong(
					result.get(Field.ENTRY_CLASS_PK));

				String title = StringPool.BLANK;

				PortletURL portletURL = getPortletURL(
					request, portletId, resultScopeGroupId);

				String url = portletURL.toString();

				Date modifiedDate = result.getDate(Field.MODIFIED_DATE);

				String content = StringPool.BLANK;

				Indexer indexer = IndexerRegistryUtil.getIndexer(
					entryClassName);

				if (indexer != null) {
					String snippet = results.snippet(i);

					Summary summary = indexer.getSummary(
						result, snippet, portletURL, null, null);

					title = summary.getTitle();
					url = portletURL.toString();
					content = summary.getContent();

					if (portlet.getPortletId().equals(PortletKeys.JOURNAL)) {
						url = getJournalURL(
							themeDisplay, resultGroupId, result);
					}
				}

				double score = results.score(i);

				addSearchResult(
					root, resultGroupId, resultScopeGroupId, entryClassName,
					entryClassPK,
					portletTitle + " " + CharPool.RAQUO + " " + title, url,
					modifiedDate, content, score, format);
			}

			if (_log.isDebugEnabled()) {
				_log.debug("Return\n" + doc.asXML());
			}

			return doc.asXML();
		}
		catch (Exception e) {
			throw new SearchException(e);
		}
	}

	protected String getJournalURL(
			ThemeDisplay themeDisplay, long groupId, Document result)
		throws Exception {

		String articleId = result.get(Field.ENTRY_CLASS_PK);

		JournalArticle article = JournalArticleServiceUtil.getArticle(
			groupId, articleId);

		if (Validator.isNotNull(article.getLayoutUuid())) {
			String groupFriendlyURL = PortalUtil.getGroupFriendlyURL(
				GroupLocalServiceUtil.getGroup(article.getGroupId()), false,
				themeDisplay);

			return groupFriendlyURL.concat(
				JournalArticleConstants.CANONICAL_URL_SEPARATOR).concat(
					article.getUrlTitle());
		}

		Layout layout = themeDisplay.getLayout();

		List<Long> hitLayoutIds =
			JournalContentSearchLocalServiceUtil.getLayoutIds(
				layout.getGroupId(), layout.isPrivateLayout(), articleId);

		if (!hitLayoutIds.isEmpty()) {
			Long hitLayoutId = hitLayoutIds.get(0);

			Layout hitLayout = LayoutLocalServiceUtil.getLayout(
				layout.getGroupId(), layout.isPrivateLayout(),
				hitLayoutId.longValue());

			return PortalUtil.getLayoutURL(hitLayout, themeDisplay);
		}

		StringBundler sb = new StringBundler(7);

		sb.append(themeDisplay.getPathMain());
		sb.append("/journal/view_article_content?groupId=");
		sb.append(groupId);
		sb.append("&articleId=");
		sb.append(articleId);
		sb.append("&version=");

		String version = result.get("version");

		sb.append(version);

		return sb.toString();
	}

	private static Log _log = LogFactoryUtil.getLog(PortalOpenSearchImpl.class);

}