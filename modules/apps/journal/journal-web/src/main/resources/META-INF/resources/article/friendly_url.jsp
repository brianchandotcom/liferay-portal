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
JournalArticle article = journalDisplayContext.getArticle();

JournalEditArticleDisplayContext journalEditArticleDisplayContext = new JournalEditArticleDisplayContext(request, liferayPortletResponse, article);
%>

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