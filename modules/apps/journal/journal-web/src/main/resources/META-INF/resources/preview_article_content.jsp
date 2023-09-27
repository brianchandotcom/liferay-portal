<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
JournalArticle article = journalDisplayContext.getArticle();
%>

<liferay-portlet:renderURL plid="<%= JournalUtil.getPreviewPlid(article, themeDisplay) %>" varImpl="previewArticleContentURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
	<portlet:param name="mvcPath" value="/preview_article_content.jsp" />
	<portlet:param name="groupId" value="<%= String.valueOf(article.getGroupId()) %>" />
	<portlet:param name="articleId" value="<%= article.getArticleId() %>" />
	<portlet:param name="version" value="<%= String.valueOf(article.getVersion()) %>" />
</liferay-portlet:renderURL>

<clay:container-fluid>
	<clay:row>
		<clay:col>
			<liferay-journal:journal-article-display
				articleDisplay="<%= journalDisplayContext.getArticleDisplay() %>"
				paginationURL="<%= previewArticleContentURL %>"
				showTitle='<%= GetterUtil.getBoolean(renderRequest.getParameter("showTitle")) %>'
			/>
		</clay:col>
	</clay:row>
</clay:container-fluid>

<liferay-util:include page="/html/common/themes/bottom.jsp" />