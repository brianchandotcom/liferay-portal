<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
KBArticlePortletInstanceConfiguration kbArticlePortletInstanceConfiguration = ConfigurationProviderUtil.getPortletInstanceConfiguration(KBArticlePortletInstanceConfiguration.class, themeDisplay);

long kbFolderClassNameId = PortalUtil.getClassNameId(KBFolderConstants.getClassName());

long resourceClassNameId = kbArticlePortletInstanceConfiguration.resourceClassNameId();

if (resourceClassNameId == 0) {
	resourceClassNameId = kbFolderClassNameId;
}

request.setAttribute("init.jsp-resourceClassNameId", resourceClassNameId);

request.setAttribute("init.jsp-resourcePrimKey", kbArticlePortletInstanceConfiguration.resourcePrimKey());

request.setAttribute("init.jsp-enableKBArticleAssetLinks", kbArticlePortletInstanceConfiguration.enableKBArticleAssetLinks());
request.setAttribute("init.jsp-enableKBArticleDescription", kbArticlePortletInstanceConfiguration.enableKBArticleDescription());
request.setAttribute("init.jsp-enableKBArticleHistory", kbArticlePortletInstanceConfiguration.enableKBArticleHistory());
request.setAttribute("init.jsp-enableKBArticlePrint", kbArticlePortletInstanceConfiguration.enableKBArticlePrint());
request.setAttribute("init.jsp-enableKBArticleRatings", kbArticlePortletInstanceConfiguration.enableKBArticleRatings());
request.setAttribute("init.jsp-enableKBArticleSubscriptions", kbArticlePortletInstanceConfiguration.enableKBArticleSubscriptions());
request.setAttribute("init.jsp-enableKBArticleViewCountIncrement", kbArticlePortletInstanceConfiguration.enableKBArticleViewCountIncrement());
request.setAttribute("init.jsp-showKBArticleAssetEntries", kbArticlePortletInstanceConfiguration.showKBArticleAssetEntries());
request.setAttribute("init.jsp-showKBArticleAttachments", kbArticlePortletInstanceConfiguration.showKBArticleAttachments());
request.setAttribute("init.jsp-socialBookmarksDisplayStyle", kbArticlePortletInstanceConfiguration.socialBookmarksDisplayStyle());
request.setAttribute("init.jsp-socialBookmarksTypes", kbArticlePortletInstanceConfiguration.socialBookmarksTypes());
%>