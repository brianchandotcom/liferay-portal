<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
JournalEditArticleDisplayContext journalEditArticleDisplayContext = new JournalEditArticleDisplayContext(request, liferayPortletResponse, journalDisplayContext.getArticle());
%>

<p class="text-secondary">
	<liferay-ui:message key="preview-this-web-content-through-the-display-page-templates-of-a-specific-site.-this-setting-is-only-for-preview-purposes" />
</p>

<div>
	<span aria-hidden="true" class="loading-animation loading-animation-sm mt-4"></span>

	<react:component
		data="<%= journalEditArticleDisplayContext.getAssetDisplayPagePreviewContext() %>"
		module="js/article/AssetDisplayPagePreview"
	/>
</div>