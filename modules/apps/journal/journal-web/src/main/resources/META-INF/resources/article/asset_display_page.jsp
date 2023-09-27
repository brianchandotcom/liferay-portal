<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
String layoutUuid = null;

JournalArticle article = journalDisplayContext.getArticle();

if (article != null) {
	layoutUuid = article.getLayoutUuid();
}

Layout articleLayout = null;

if (Validator.isNotNull(layoutUuid)) {
	articleLayout = LayoutLocalServiceUtil.fetchLayoutByUuidAndGroupId(layoutUuid, article.getGroupId(), false);

	if (articleLayout == null) {
		articleLayout = LayoutLocalServiceUtil.fetchLayoutByUuidAndGroupId(layoutUuid, article.getGroupId(), true);
	}
}

JournalEditArticleDisplayContext journalEditArticleDisplayContext = new JournalEditArticleDisplayContext(request, liferayPortletResponse, article);
%>

<p class="text-secondary"><liferay-ui:message key="changing-the-display-page-template-will-affect-all-web-content-article-versions-even-when-saving-it-as-a-draft" /></p>

<c:if test="<%= Validator.isNotNull(layoutUuid) && (articleLayout == null) %>">
	<clay:alert
		displayType="warning"
		message='<%= LanguageUtil.format(request, "this-article-is-configured-to-use-a-display-page-that-does-not-exist-on-the-current-site", layoutUuid) %>'
	/>
</c:if>

<div>
	<react:component
		data="<%= journalEditArticleDisplayContext.getSelectAssetDisplayPageContext() %>"
		module="js/article/SelectAssetDisplayPage"
	/>
</div>