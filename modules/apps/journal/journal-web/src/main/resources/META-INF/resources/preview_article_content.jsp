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
JournalArticleDisplay articleDisplay = journalDisplayContext.getArticleDisplay();
%>

<c:if test='<%= Boolean.parseBoolean(renderRequest.getParameter("showTitle")) %>'>
	<clay:container-fluid
		cssClass="mt-3"
	>
		<clay:row>
			<clay:col>
				<h3 class="m-0"><%= articleDisplay.getTitle() %></h3>
			</clay:col>
		</clay:row>
	</clay:container-fluid>

	<hr class="mb-4 separator" />
</c:if>

<clay:container-fluid>
	<clay:row>
		<clay:col>
			<%= articleDisplay.getContent() %>
		</clay:col>
	</clay:row>
</clay:container-fluid>

<c:if test="<%= articleDisplay.isPaginate() %>">

	<%
	JournalArticle article = journalDisplayContext.getArticle();
	%>

	<liferay-portlet:renderURL plid="<%= JournalUtil.getPreviewPlid(article, themeDisplay) %>" varImpl="previewArticleContentURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
		<portlet:param name="mvcPath" value="/preview_article_content.jsp" />
		<portlet:param name="groupId" value="<%= String.valueOf(article.getGroupId()) %>" />
		<portlet:param name="articleId" value="<%= article.getArticleId() %>" />
		<portlet:param name="version" value="<%= String.valueOf(article.getVersion()) %>" />
	</liferay-portlet:renderURL>

	<liferay-ui:page-iterator
		cur="<%= articleDisplay.getCurrentPage() %>"
		curParam="page"
		delta="<%= 1 %>"
		id="articleDisplayPages"
		maxPages="<%= 25 %>"
		portletURL="<%= previewArticleContentURL %>"
		total="<%= articleDisplay.getNumberOfPages() %>"
		type="article"
	/>

	<br />
</c:if>

<liferay-util:include page="/html/common/themes/bottom.jsp" />