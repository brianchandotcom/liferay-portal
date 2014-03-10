<%--
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
--%>

<%@ include file="/html/portlet/journal/init.jsp" %>

<%
String articleId = ParamUtil.getString(request, "articleId");
long groupId = ParamUtil.getLong(request, "groupId");

String diffHtmlResults = (String)request.getAttribute(WebKeys.DIFF_HTML_RESULTS);
double sourceVersion = (Double)request.getAttribute(WebKeys.SOURCE_VERSION);
double targetVersion = (Double)request.getAttribute(WebKeys.TARGET_VERSION);

double previousVersion = 0;
double nextVersion = 0;

List<JournalArticle> articles = JournalArticleServiceUtil.getArticlesByArticleId(groupId, articleId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, new ArticleVersionComparator());
List<JournalArticle> intermediateArticles = new ArrayList<JournalArticle>();

for (JournalArticle article : articles) {
	if ((article.getVersion() < sourceVersion) && (article.getVersion() > previousVersion)) {
		previousVersion = article.getVersion();
	}

	if ((article.getVersion() > targetVersion) && ((article.getVersion() < nextVersion) || (nextVersion == 0))) {
		nextVersion = article.getVersion();
	}
	if ((article.getVersion() > sourceVersion) && (article.getVersion() <= targetVersion)) {
		intermediateArticles.add(article);
	}
}

List<Tuple> versionsInfo = new ArrayList<Tuple>();

for (JournalArticle article : intermediateArticles) {
	String description = StringPool.BLANK;

	if (intermediateArticles.size() > 1) {
		description = StringPool.OPEN_PARENTHESIS + String.valueOf(article.getVersion()) + StringPool.CLOSE_PARENTHESIS;
	}

	Tuple versionInfo = new Tuple(article.getUserName(), description);

	versionsInfo.add(versionInfo);
}
%>

<liferay-portlet:renderURL varImpl="iteratorURL">
	<portlet:param name="struts_action" value="/journal/compare_versions" />
	<portlet:param name="groupId" value="<%= String.valueOf(groupId) %>" />
	<portlet:param name="articleId" value="<%= articleId %>" />
</liferay-portlet:renderURL>

<liferay-ui:version-comparator
	diffHtmlResults="<%= diffHtmlResults %>"
	iteratorURL="<%= iteratorURL %>"
	nextVersion="<%= nextVersion %>"
	previousVersion="<%= previousVersion %>"
	sourceVersion="<%= sourceVersion %>"
	targetVersion="<%= targetVersion %>"
	versionsInfo="<%= versionsInfo %>"
/>