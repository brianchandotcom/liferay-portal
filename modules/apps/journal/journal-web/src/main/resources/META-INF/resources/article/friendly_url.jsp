<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
JournalArticle article = journalDisplayContext.getArticle();

JournalEditArticleDisplayContext journalEditArticleDisplayContext = new JournalEditArticleDisplayContext(request, liferayPortletResponse, article);
%>

<c:if test="<%= Validator.isNotNull(journalEditArticleDisplayContext.getFriendlyURLDuplicatedWarningMessage()) %>">
	<clay:alert
		dismissible="<%= true %>"
		displayType="warning"
		message="<%= journalEditArticleDisplayContext.getFriendlyURLDuplicatedWarningMessage() %>"
	/>
</c:if>

<p class="text-secondary"><liferay-ui:message key="changing-the-friendly-url-will-affect-all-web-content-article-versions-even-when-saving-it-as-a-draft" /></p>

<p class="text-secondary"><liferay-ui:message key="the-friendly-url-may-be-modified-to-ensure-uniqueness" /></p>

<liferay-friendly-url:input
	className="<%= JournalArticle.class.getName() %>"
	classPK="<%= (article == null) || (article.getPrimaryKey() == 0) ? 0 : article.getResourcePrimKey() %>"
	inputAddon="<%= journalEditArticleDisplayContext.getFriendlyURLBase() %>"
	name="friendlyURL"
	showHistory="<%= false %>"
	showLabel="<%= false %>"
/>