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
PortalPreferences portalPreferences = PortletPreferencesFactoryUtil.getPortalPreferences(liferayPortletRequest);

String portletResource = ParamUtil.getString(request, "portletResource");

KBDisplayPortletInstanceConfiguration kbDisplayPortletInstanceConfiguration = portletDisplay.getPortletInstanceConfiguration(KBDisplayPortletInstanceConfiguration.class);

long kbFolderClassNameId = PortalUtil.getClassNameId(KBFolderConstants.getClassName());

long resourceClassNameId = kbDisplayPortletInstanceConfiguration.resourceClassNameId();

if (resourceClassNameId == 0) {
	resourceClassNameId = kbFolderClassNameId;
}

request.setAttribute("init.jsp-resourceClassNameId", resourceClassNameId);

request.setAttribute("init.jsp-resourcePrimKey", kbDisplayPortletInstanceConfiguration.resourcePrimKey());
request.setAttribute("init.jsp-enableKBArticleDescription", kbDisplayPortletInstanceConfiguration.enableKBArticleDescription());
request.setAttribute("init.jsp-enableKBArticleRatings", kbDisplayPortletInstanceConfiguration.enableKBArticleRatings());
request.setAttribute("init.jsp-showKBArticleAssetEntries", kbDisplayPortletInstanceConfiguration.showKBArticleAssetEntries());
request.setAttribute("init.jsp-showKBArticleAttachments", kbDisplayPortletInstanceConfiguration.showKBArticleAttachments());
request.setAttribute("init.jsp-enableKBArticleAssetLinks", kbDisplayPortletInstanceConfiguration.enableKBArticleAssetLinks());
request.setAttribute("init.jsp-enableKBArticleViewCountIncrement", kbDisplayPortletInstanceConfiguration.enableKBArticleViewCountIncrement());
request.setAttribute("init.jsp-enableKBArticleSubscriptions", kbDisplayPortletInstanceConfiguration.enableKBArticleSubscriptions());
request.setAttribute("init.jsp-enableKBArticleHistory", kbDisplayPortletInstanceConfiguration.enableKBArticleHistory());
request.setAttribute("init.jsp-enableKBArticlePrint", kbDisplayPortletInstanceConfiguration.enableKBArticlePrint());
request.setAttribute("init.jsp-enableSocialBookmarks", kbDisplayPortletInstanceConfiguration.enableSocialBookmarks());
request.setAttribute("init.jsp-socialBookmarksDisplayStyle", kbDisplayPortletInstanceConfiguration.socialBookmarksDisplayStyle());
request.setAttribute("init.jsp-socialBookmarksDisplayPosition", kbDisplayPortletInstanceConfiguration.socialBookmarksDisplayPosition());
request.setAttribute("init.jsp-socialBookmarksTypes", kbDisplayPortletInstanceConfiguration.socialBookmarksTypes());
%>