<%--
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
--%>

<%@ include file="/init.jsp" %>

<%
KBSearchPortletInstanceConfiguration kbSearchPortletInstanceConfiguration = portletDisplay.getPortletInstanceConfiguration(KBSearchPortletInstanceConfiguration.class);

request.setAttribute("init.jsp-enableKBArticleDescription", kbSearchPortletInstanceConfiguration.enableKBArticleDescription());
request.setAttribute("init.jsp-enableKBArticleRatings", kbSearchPortletInstanceConfiguration.enableKBArticleRatings());
request.setAttribute("init.jsp-showKBArticleAssetEntries", kbSearchPortletInstanceConfiguration.showKBArticleAssetEntries());
request.setAttribute("init.jsp-showKBArticleAttachments", kbSearchPortletInstanceConfiguration.showKBArticleAttachments());
request.setAttribute("init.jsp-enableKBArticleAssetLinks", kbSearchPortletInstanceConfiguration.enableKBArticleAssetLinks());
request.setAttribute("init.jsp-enableKBArticleViewCountIncrement", kbSearchPortletInstanceConfiguration.enableKBArticleViewCountIncrement());
request.setAttribute("init.jsp-enableKBArticleSubscriptions", kbSearchPortletInstanceConfiguration.enableKBArticleSubscriptions());
request.setAttribute("init.jsp-enableKBArticleHistory", kbSearchPortletInstanceConfiguration.enableKBArticleHistory());
request.setAttribute("init.jsp-enableKBArticlePrint", kbSearchPortletInstanceConfiguration.enableKBArticlePrint());
request.setAttribute("init.jsp-enableSocialBookmarks", kbSearchPortletInstanceConfiguration.enableSocialBookmarks());
request.setAttribute("init.jsp-socialBookmarksDisplayStyle", kbSearchPortletInstanceConfiguration.socialBookmarksDisplayStyle());
request.setAttribute("init.jsp-socialBookmarksDisplayPosition", kbSearchPortletInstanceConfiguration.socialBookmarksDisplayPosition());
request.setAttribute("init.jsp-socialBookmarksTypes", kbSearchPortletInstanceConfiguration.socialBookmarksTypes());
%>