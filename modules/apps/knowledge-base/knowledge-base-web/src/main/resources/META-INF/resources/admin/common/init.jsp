<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%@ page import="com.liferay.document.library.configuration.DLConfiguration" %><%@
page import="com.liferay.knowledge.base.web.internal.display.context.EditKBArticleDisplayContext" %><%@
page import="com.liferay.knowledge.base.web.internal.display.context.ViewKBArticleDisplayContext" %><%@
page import="com.liferay.knowledge.base.web.internal.display.context.ViewKBSuggestionDisplayContext" %>

<%
long kbFolderClassNameId = PortalUtil.getClassNameId(KBFolderConstants.getClassName());

long resourceClassNameId = GetterUtil.getLong(request.getAttribute("init.jsp-resourceClassNameId"));

if (resourceClassNameId == 0) {
	resourceClassNameId = kbFolderClassNameId;
}

long resourcePrimKey = GetterUtil.getLong(request.getAttribute("init.jsp-resourcePrimKey"));

boolean enableKBArticleRatings = GetterUtil.getBoolean(request.getAttribute("init.jsp-enableKBArticleRatings"));
boolean showKBArticleAssetEntries = GetterUtil.getBoolean(request.getAttribute("init.jsp-showKBArticleAssetEntries"));
boolean showKBArticleAttachments = GetterUtil.getBoolean(request.getAttribute("init.jsp-showKBArticleAttachments"));
boolean enableKBArticleAssetLinks = GetterUtil.getBoolean(request.getAttribute("init.jsp-enableKBArticleAssetLinks"));
boolean enableKBArticleViewCountIncrement = GetterUtil.getBoolean(request.getAttribute("init.jsp-enableKBArticleViewCountIncrement"));
String socialBookmarksDisplayStyle = GetterUtil.getString(request.getAttribute("init.jsp-socialBookmarksDisplayStyle"));
String socialBookmarksTypes = GetterUtil.getString(request.getAttribute("init.jsp-socialBookmarksTypes"), null);
%>