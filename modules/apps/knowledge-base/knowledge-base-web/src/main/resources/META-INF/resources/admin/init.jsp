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
long kbFolderClassNameId = PortalUtil.getClassNameId(KBFolderConstants.getClassName());

long resourceClassNameId = kbFolderClassNameId;

if (resourceClassNameId == 0) {
	resourceClassNameId = kbFolderClassNameId;
}

long resourcePrimKey = 0;

String kbArticlesOrderByCol = "priority";
String kbArticlesOrderByType = "desc";

boolean enableKBArticleDescription = true;
boolean enableKBArticleRatings = true;
boolean showKBArticleAssetEntries = true;
boolean showKBArticleAttachments = true;
boolean enableKBArticleAssetLinks = true;
boolean enableKBArticleViewCountIncrement = true;
boolean enableKBArticleSubscriptions = true;
boolean enableKBArticleHistory = true;
boolean enableKBArticlePrint = true;
boolean enableSocialBookmarks = false;
String socialBookmarksDisplayStyle = "menu";
String socialBookmarksDisplayPosition = "bottom";
String socialBookmarksTypes = PropsUtil.get(PropsKeys.SOCIAL_BOOKMARK_TYPES);

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

boolean enableRSS = kbGroupServiceConfiguration.enableRSS();
int rssDelta = kbGroupServiceConfiguration.rssDelta();
String rssDisplayStyle = kbGroupServiceConfiguration.rssDisplayStyle();
String rssFeedType = kbGroupServiceConfiguration.rssFeedType();
%>