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
boolean showKBArticlesSectionsTitle = kbSectionPortletInstanceConfiguration.showKBArticlesSectionsTitle();
String[] kbArticlesSections = kbSectionPortletInstanceConfiguration.kbArticlesSections();
String kbArticleDisplayStyle = kbSectionPortletInstanceConfiguration.kbArticleDisplayStyle();
String kbArticleWindowState = kbSectionPortletInstanceConfiguration.kbArticleWindowState();
String kbArticlesOrderByCol = kbSectionPortletInstanceConfiguration.kbArticlesOrderByCol();
String kbArticlesOrderByType = kbSectionPortletInstanceConfiguration.kbArticlesOrderByType();
int kbArticlesDelta = kbSectionPortletInstanceConfiguration.kbArticlesDelta();
boolean showKBArticlesPagination = kbSectionPortletInstanceConfiguration.showKBArticlesPagination();

boolean enableKBArticleDescription = kbSectionPortletInstanceConfiguration.enableKBArticleDescription();
boolean enableKBArticleRatings = kbSectionPortletInstanceConfiguration.enableKBArticleRatings();
boolean showKBArticleAssetEntries = kbSectionPortletInstanceConfiguration.showKBArticleAssetEntries();
boolean showKBArticleAttachments = kbSectionPortletInstanceConfiguration.showKBArticleAttachments();
boolean enableKBArticleAssetLinks = kbSectionPortletInstanceConfiguration.enableKBArticleAssetLinks();
boolean enableKBArticleViewCountIncrement = kbSectionPortletInstanceConfiguration.enableKBArticleViewCountIncrement();
boolean enableKBArticleSubscriptions = kbSectionPortletInstanceConfiguration.enableKBArticleSubscriptions();
boolean enableKBArticleHistory = kbSectionPortletInstanceConfiguration.enableKBArticleHistory();
boolean enableKBArticlePrint = kbSectionPortletInstanceConfiguration.enableKBArticlePrint();
boolean enableSocialBookmarks = kbSectionPortletInstanceConfiguration.enableSocialBookmarks();
String socialBookmarksDisplayStyle = kbSectionPortletInstanceConfiguration.socialBookmarksDisplayStyle();
String socialBookmarksDisplayPosition = kbSectionPortletInstanceConfiguration.socialBookmarksDisplayPosition();
String socialBookmarksTypes = kbSectionPortletInstanceConfiguration.socialBookmarksTypes();

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