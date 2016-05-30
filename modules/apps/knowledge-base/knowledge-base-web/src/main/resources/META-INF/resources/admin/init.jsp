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
request.setAttribute("init.jsp-enableKBArticleDescription", true);
request.setAttribute("init.jsp-enableKBArticleRatings", true);
request.setAttribute("init.jsp-showKBArticleAssetEntries", true);
request.setAttribute("init.jsp-showKBArticleAttachments", true);
request.setAttribute("init.jsp-enableKBArticleAssetLinks", true);
request.setAttribute("init.jsp-enableKBArticleViewCountIncrement", true);
request.setAttribute("init.jsp-enableKBArticleSubscriptions", true);
request.setAttribute("init.jsp-enableKBArticleHistory", true);
request.setAttribute("init.jsp-enableKBArticlePrint", true);
request.setAttribute("init.jsp-enableSocialBookmarks", false);
request.setAttribute("init.jsp-socialBookmarksDisplayStyle", "menu");
request.setAttribute("init.jsp-socialBookmarksDisplayPosition", "bottom");
request.setAttribute("init.jsp-socialBookmarksTypes", PropsUtil.get(PropsKeys.SOCIAL_BOOKMARK_TYPES));
%>