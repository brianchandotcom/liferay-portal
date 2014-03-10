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

<%@ include file="/html/portlet/wiki/init.jsp" %>

<%
String diffHtmlResults = (String)request.getAttribute(WebKeys.DIFF_HTML_RESULTS);
long nodeId = (Long)request.getAttribute(WebKeys.WIKI_NODE_ID);
String title = (String)request.getAttribute(WebKeys.TITLE);
double sourceVersion = (Double)request.getAttribute(WebKeys.SOURCE_VERSION);
double targetVersion = (Double)request.getAttribute(WebKeys.TARGET_VERSION);

double previousVersion = 0;
double nextVersion = 0;

List<WikiPage> allPages = WikiPageLocalServiceUtil.getPages(nodeId, title, QueryUtil.ALL_POS, QueryUtil.ALL_POS, new PageVersionComparator());
List<WikiPage> intermediatePages = new ArrayList<WikiPage>();

for (WikiPage wikiPage : allPages) {
	if ((wikiPage.getVersion() < sourceVersion) && (wikiPage.getVersion() > previousVersion)) {
		previousVersion = wikiPage.getVersion();
	}

	if ((wikiPage.getVersion() > targetVersion) && ((wikiPage.getVersion() < nextVersion) || (nextVersion == 0))) {
		nextVersion = wikiPage.getVersion();
	}

	if ((wikiPage.getVersion() > sourceVersion) && (wikiPage.getVersion() <= targetVersion)) {
		intermediatePages.add(wikiPage);
	}
}

List<Tuple> versionsInfo = new ArrayList<Tuple>();

for (WikiPage wikiPage : intermediatePages) {
	String description = StringPool.BLANK;

	if (intermediatePages.size() == 1) {
		if (Validator.isNotNull(wikiPage.getSummary())) {
			description = StringPool.COLON + StringPool.SPACE + HtmlUtil.escape(wikiPage.getSummary());
		}

		if (wikiPage.isMinorEdit()) {
			description += StringPool.OPEN_PARENTHESIS + LanguageUtil.get(pageContext, "minor-edit") + StringPool.CLOSE_PARENTHESIS;
		}
	}
	else {
		description = StringPool.OPEN_PARENTHESIS + String.valueOf(wikiPage.getVersion()) + StringPool.CLOSE_PARENTHESIS;
	}

	Tuple versionInfo = new Tuple(wikiPage.getUserName(), description);

	versionsInfo.add(versionInfo);
}
%>

<liferay-portlet:renderURL varImpl="iteratorURL">
	<portlet:param name="struts_action" value="/wiki/compare_versions" />
	<portlet:param name="nodeId" value="<%= String.valueOf(nodeId) %>" />
	<portlet:param name="title" value="<%= title %>" />
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