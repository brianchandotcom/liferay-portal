<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/asset/init.jsp" %>

<liferay-util:dynamic-include key="com.liferay.journal.web#/asset/full_content.jsp#pre" />

<%
AssetFullContentDisplayContext assetFullContentDisplayContext = new AssetFullContentDisplayContext(request, liferayPortletRequest, liferayPortletResponse);
%>

<liferay-journal:journal-article-display
	articleDisplay="<%= assetFullContentDisplayContext.getJournalArticleDisplay() %>"
	paginationURL="<%= assetFullContentDisplayContext.getPaginationURL() %>"
/>

<liferay-util:dynamic-include key="com.liferay.journal.web#/asset/full_content.jsp#post" />