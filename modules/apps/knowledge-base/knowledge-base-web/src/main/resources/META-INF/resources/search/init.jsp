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

boolean showKBArticleAuthorColumn = kbSearchPortletInstanceConfiguration.showKBArticleAuthorColumn();
boolean showKBArticleCreateDateColumn = kbSearchPortletInstanceConfiguration.showKBArticleCreateDateColumn();
boolean showKBArticleModifiedDateColumn = kbSearchPortletInstanceConfiguration.showKBArticleModifiedDateColumn();
boolean showKBArticleViewsColumn = kbSearchPortletInstanceConfiguration.showKBArticleViewsColumn();

boolean enableKBArticleDescription = kbSearchPortletInstanceConfiguration.enableKBArticleDescription();
boolean enableKBArticleRatings = kbSearchPortletInstanceConfiguration.enableKBArticleRatings();
boolean showKBArticleAssetEntries = kbSearchPortletInstanceConfiguration.showKBArticleAssetEntries();
boolean showKBArticleAttachments = kbSearchPortletInstanceConfiguration.showKBArticleAttachments();
boolean enableKBArticleAssetLinks = kbSearchPortletInstanceConfiguration.enableKBArticleAssetLinks();
boolean enableKBArticleViewCountIncrement = kbSearchPortletInstanceConfiguration.enableKBArticleViewCountIncrement();
boolean enableKBArticleSubscriptions = kbSearchPortletInstanceConfiguration.enableKBArticleSubscriptions();
boolean enableKBArticleHistory = kbSearchPortletInstanceConfiguration.enableKBArticleHistory();
boolean enableKBArticlePrint = kbSearchPortletInstanceConfiguration.enableKBArticlePrint();
boolean enableSocialBookmarks = kbSearchPortletInstanceConfiguration.enableSocialBookmarks();
String socialBookmarksDisplayStyle = kbSearchPortletInstanceConfiguration.socialBookmarksDisplayStyle();
String socialBookmarksDisplayPosition = kbSearchPortletInstanceConfiguration.socialBookmarksDisplayPosition();
String socialBookmarksTypes = kbSearchPortletInstanceConfiguration.socialBookmarksTypes();

request.setAttribute("init.jsp-enableKBArticleDescription", enableKBArticleDescription);
request.setAttribute("init.jsp-enableKBArticleRatings", enableKBArticleRatings);
request.setAttribute("init.jsp-showKBArticleAssetEntries", showKBArticleAssetEntries);
request.setAttribute("init.jsp-showKBArticleAttachments", showKBArticleAttachments);
request.setAttribute("init.jsp-enableKBArticleAssetLinks", enableKBArticleAssetLinks);
request.setAttribute("init.jsp-enableKBArticleViewCountIncrement", enableKBArticleViewCountIncrement);
request.setAttribute("init.jsp-enableKBArticleSubscriptions", enableKBArticleSubscriptions);
request.setAttribute("init.jsp-enableKBArticleHistory", enableKBArticleHistory);
request.setAttribute("init.jsp-enableKBArticlePrint", enableKBArticlePrint);
request.setAttribute("init.jsp-enableSocialBookmarks", enableSocialBookmarks);
request.setAttribute("init.jsp-socialBookmarksDisplayStyle", socialBookmarksDisplayStyle);
request.setAttribute("init.jsp-socialBookmarksDisplayPosition", socialBookmarksDisplayPosition);
request.setAttribute("init.jsp-socialBookmarksTypes", socialBookmarksTypes);
%>