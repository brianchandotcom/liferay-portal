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

<%@ include file="/ct_display/init.jsp" %>

<%
JournalArticle journalArticle = (JournalArticle)request.getAttribute(WebKeys.JOURNAL_ARTICLE);
JournalArticleDisplay journalArticleDisplay = (JournalArticleDisplay)request.getAttribute(WebKeys.JOURNAL_ARTICLE_DISPLAY);

DDMStructure ddmStructure = journalArticle.getDDMStructure();
%>

<p><b><liferay-ui:message key="structure" /></b>: <%= HtmlUtil.escape(ddmStructure.getName(locale)) %></p>

<p>
	<b><liferay-ui:message key="version" /></b>: <%= journalArticle.getVersion() %>

	<span class="label label-<%= LabelItem.getStyleFromWorkflowStatus(journalArticle.getStatus()) %> ml-2 text-uppercase">
		<liferay-ui:message key="<%= WorkflowConstants.getStatusLabel(journalArticle.getStatus()) %>" />
	</span>
</p>

<p><b><liferay-ui:message key="id" /></b>: <%= journalArticle.getArticleId() %></p>

<p><b><liferay-ui:message key="description" /></b>: <%= HtmlUtil.escape(journalArticle.getDescription(locale)) %></p>

<b><liferay-ui:message key="content" /></b>:

<liferay-journal:journal-article-display
	articleDisplay="<%= journalArticleDisplay %>"
/>