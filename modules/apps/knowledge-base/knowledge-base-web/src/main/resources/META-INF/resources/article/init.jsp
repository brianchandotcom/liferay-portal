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
KBArticlePortletInstanceConfiguration kbArticlePortletInstanceConfiguration = portletDisplay.getPortletInstanceConfiguration(KBArticlePortletInstanceConfiguration.class);

String portletResource = ParamUtil.getString(request, "portletResource");

long kbFolderClassNameId = PortalUtil.getClassNameId(KBFolderConstants.getClassName());

long resourceClassNameId = kbArticlePortletInstanceConfiguration.resourceClassNameId();

if (resourceClassNameId == 0) {
	resourceClassNameId = kbFolderClassNameId;
}

request.setAttribute("init.jsp-resourceClassNameId", resourceClassNameId);

request.setAttribute("init.jsp-resourcePrimKey", kbArticlePortletInstanceConfiguration.resourcePrimKey());

request.setAttribute("init.jsp-enableKBArticleDescription", kbArticlePortletInstanceConfiguration.enableKBArticleDescription());
request.setAttribute("init.jsp-enableKBArticleRatings", kbArticlePortletInstanceConfiguration.enableKBArticleRatings());
request.setAttribute("init.jsp-showKBArticleAssetEntries", kbArticlePortletInstanceConfiguration.showKBArticleAssetEntries());
request.setAttribute("init.jsp-showKBArticleAttachments", kbArticlePortletInstanceConfiguration.showKBArticleAttachments());
request.setAttribute("init.jsp-enableKBArticleAssetLinks", kbArticlePortletInstanceConfiguration.enableKBArticleAssetLinks());
request.setAttribute("init.jsp-enableKBArticleViewCountIncrement", kbArticlePortletInstanceConfiguration.enableKBArticleViewCountIncrement());
request.setAttribute("init.jsp-enableKBArticleSubscriptions", kbArticlePortletInstanceConfiguration.enableKBArticleSubscriptions());
request.setAttribute("init.jsp-enableKBArticleHistory", kbArticlePortletInstanceConfiguration.enableKBArticleHistory());
request.setAttribute("init.jsp-enableKBArticlePrint", kbArticlePortletInstanceConfiguration.enableKBArticlePrint());
request.setAttribute("init.jsp-enableSocialBookmarks", kbArticlePortletInstanceConfiguration.enableSocialBookmarks());
request.setAttribute("init.jsp-socialBookmarksDisplayStyle", kbArticlePortletInstanceConfiguration.socialBookmarksDisplayStyle());
request.setAttribute("init.jsp-socialBookmarksDisplayPosition", kbArticlePortletInstanceConfiguration.socialBookmarksDisplayPosition());
request.setAttribute("init.jsp-socialBookmarksTypes", kbArticlePortletInstanceConfiguration.socialBookmarksTypes());
%>