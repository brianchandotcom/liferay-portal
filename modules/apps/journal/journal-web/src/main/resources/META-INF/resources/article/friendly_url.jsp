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

<p class="mb-2 text-secondary">
	<%= journalEditArticleDisplayContext.getFriendlyURLBase() %>
</p>

<liferay-ui:input-localized
	availableLocales="<%= journalEditArticleDisplayContext.getAvailableLocales() %>"
	defaultLanguageId="<%= journalEditArticleDisplayContext.getDefaultLanguageId() %>"
	maxLength='<%= String.valueOf(ModelHintsUtil.getMaxLength(JournalArticle.class.getName(), "urlTitle")) %>'
	name="friendlyURL"
	xml="<%= (article != null) ? HttpUtil.decodeURL(article.getFriendlyURLsXML()) : StringPool.BLANK %>"
/>