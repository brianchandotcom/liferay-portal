<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
PortalPreferences portalPreferences = PortletPreferencesFactoryUtil.getPortalPreferences(liferayPortletRequest);

String portletResource = ParamUtil.getString(request, "portletResource");

KBDisplayPortletInstanceConfiguration kbDisplayPortletInstanceConfiguration = ConfigurationProviderUtil.getPortletInstanceConfiguration(KBDisplayPortletInstanceConfiguration.class, themeDisplay);

long kbFolderClassNameId = PortalUtil.getClassNameId(KBFolderConstants.getClassName());

long resourceClassNameId = kbDisplayPortletInstanceConfiguration.resourceClassNameId();

if (resourceClassNameId == 0) {
	resourceClassNameId = kbFolderClassNameId;
}

request.setAttribute("init.jsp-resourceClassNameId", resourceClassNameId);

request.setAttribute("init.jsp-enableKBArticleAssetLinks", kbDisplayPortletInstanceConfiguration.enableKBArticleAssetLinks());
request.setAttribute("init.jsp-enableKBArticleDescription", kbDisplayPortletInstanceConfiguration.enableKBArticleDescription());
request.setAttribute("init.jsp-enableKBArticleHistory", kbDisplayPortletInstanceConfiguration.enableKBArticleHistory());
request.setAttribute("init.jsp-enableKBArticlePrint", kbDisplayPortletInstanceConfiguration.enableKBArticlePrint());
request.setAttribute("init.jsp-enableKBArticleRatings", kbDisplayPortletInstanceConfiguration.enableKBArticleRatings());
request.setAttribute("init.jsp-enableKBArticleSubscriptions", kbDisplayPortletInstanceConfiguration.enableKBArticleSubscriptions());
request.setAttribute("init.jsp-enableKBArticleViewCountIncrement", kbDisplayPortletInstanceConfiguration.enableKBArticleViewCountIncrement());
request.setAttribute("init.jsp-resourcePrimKey", kbDisplayPortletInstanceConfiguration.resourcePrimKey());
request.setAttribute("init.jsp-showKBArticleAssetEntries", kbDisplayPortletInstanceConfiguration.showKBArticleAssetEntries());
request.setAttribute("init.jsp-showKBArticleAttachments", kbDisplayPortletInstanceConfiguration.showKBArticleAttachments());
request.setAttribute("init.jsp-socialBookmarksDisplayStyle", kbDisplayPortletInstanceConfiguration.socialBookmarksDisplayStyle());
request.setAttribute("init.jsp-socialBookmarksTypes", kbDisplayPortletInstanceConfiguration.socialBookmarksTypes());
%>