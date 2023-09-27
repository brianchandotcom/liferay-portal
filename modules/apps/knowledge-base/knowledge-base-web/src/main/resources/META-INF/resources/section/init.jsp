<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
request.setAttribute("init.jsp-enableKBArticleAssetLinks", kbSectionPortletInstanceConfiguration.enableKBArticleAssetLinks());
request.setAttribute("init.jsp-enableKBArticleDescription", kbSectionPortletInstanceConfiguration.enableKBArticleDescription());
request.setAttribute("init.jsp-enableKBArticleHistory", kbSectionPortletInstanceConfiguration.enableKBArticleHistory());
request.setAttribute("init.jsp-enableKBArticlePrint", kbSectionPortletInstanceConfiguration.enableKBArticlePrint());
request.setAttribute("init.jsp-enableKBArticleRatings", kbSectionPortletInstanceConfiguration.enableKBArticleRatings());
request.setAttribute("init.jsp-enableKBArticleSubscriptions", kbSectionPortletInstanceConfiguration.enableKBArticleSubscriptions());
request.setAttribute("init.jsp-enableKBArticleViewCountIncrement", kbSectionPortletInstanceConfiguration.enableKBArticleViewCountIncrement());
request.setAttribute("init.jsp-showKBArticleAssetEntries", kbSectionPortletInstanceConfiguration.showKBArticleAssetEntries());
request.setAttribute("init.jsp-showKBArticleAttachments", kbSectionPortletInstanceConfiguration.showKBArticleAttachments());
request.setAttribute("init.jsp-socialBookmarksDisplayStyle", kbSectionPortletInstanceConfiguration.socialBookmarksDisplayStyle());
request.setAttribute("init.jsp-socialBookmarksTypes", kbSectionPortletInstanceConfiguration.socialBookmarksTypes());
%>