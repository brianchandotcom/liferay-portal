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
request.setAttribute("init.jsp-enableKBArticleDescription", kbSectionPortletInstanceConfiguration.enableKBArticleDescription());
request.setAttribute("init.jsp-enableKBArticleRatings", kbSectionPortletInstanceConfiguration.enableKBArticleRatings());
request.setAttribute("init.jsp-showKBArticleAssetEntries", kbSectionPortletInstanceConfiguration.showKBArticleAssetEntries());
request.setAttribute("init.jsp-showKBArticleAttachments", kbSectionPortletInstanceConfiguration.showKBArticleAttachments());
request.setAttribute("init.jsp-enableKBArticleAssetLinks", kbSectionPortletInstanceConfiguration.enableKBArticleAssetLinks());
request.setAttribute("init.jsp-enableKBArticleViewCountIncrement", kbSectionPortletInstanceConfiguration.enableKBArticleViewCountIncrement());
request.setAttribute("init.jsp-enableKBArticleSubscriptions", kbSectionPortletInstanceConfiguration.enableKBArticleSubscriptions());
request.setAttribute("init.jsp-enableKBArticleHistory", kbSectionPortletInstanceConfiguration.enableKBArticleHistory());
request.setAttribute("init.jsp-enableKBArticlePrint", kbSectionPortletInstanceConfiguration.enableKBArticlePrint());
request.setAttribute("init.jsp-enableSocialBookmarks", kbSectionPortletInstanceConfiguration.enableSocialBookmarks());
request.setAttribute("init.jsp-socialBookmarksDisplayStyle", kbSectionPortletInstanceConfiguration.socialBookmarksDisplayStyle());
request.setAttribute("init.jsp-socialBookmarksDisplayPosition", kbSectionPortletInstanceConfiguration.socialBookmarksDisplayPosition());
request.setAttribute("init.jsp-socialBookmarksTypes", kbSectionPortletInstanceConfiguration.socialBookmarksTypes());
%>