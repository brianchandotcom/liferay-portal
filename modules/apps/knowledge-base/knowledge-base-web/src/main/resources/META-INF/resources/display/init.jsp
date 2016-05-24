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

long resourcePrimKey = kbDisplayPortletInstanceConfiguration.resourcePrimKey();

request.setAttribute("init.jsp-resourcePrimKey", resourcePrimKey);

boolean enableKBArticleDescription = kbDisplayPortletInstanceConfiguration.enableKBArticleDescription();
boolean enableKBArticleRatings = kbDisplayPortletInstanceConfiguration.enableKBArticleRatings();
boolean showKBArticleAssetEntries = kbDisplayPortletInstanceConfiguration.showKBArticleAssetEntries();
boolean showKBArticleAttachments = kbDisplayPortletInstanceConfiguration.showKBArticleAttachments();
boolean enableKBArticleAssetLinks = kbDisplayPortletInstanceConfiguration.enableKBArticleAssetLinks();
boolean enableKBArticleViewCountIncrement = kbDisplayPortletInstanceConfiguration.enableKBArticleViewCountIncrement();
boolean enableKBArticleSubscriptions = kbDisplayPortletInstanceConfiguration.enableKBArticleSubscriptions();
boolean enableKBArticleHistory = kbDisplayPortletInstanceConfiguration.enableKBArticleHistory();
boolean enableKBArticlePrint = kbDisplayPortletInstanceConfiguration.enableKBArticlePrint();
boolean enableSocialBookmarks = kbDisplayPortletInstanceConfiguration.enableSocialBookmarks();
String socialBookmarksDisplayStyle = kbDisplayPortletInstanceConfiguration.socialBookmarksDisplayStyle();
String socialBookmarksDisplayPosition = kbDisplayPortletInstanceConfiguration.socialBookmarksDisplayPosition();
String socialBookmarksTypes = kbDisplayPortletInstanceConfiguration.socialBookmarksTypes();

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

String contentRootPrefix = kbDisplayPortletInstanceConfiguration.contentRootPrefix();
%>