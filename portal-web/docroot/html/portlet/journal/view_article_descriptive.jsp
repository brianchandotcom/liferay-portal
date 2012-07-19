<%--
/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/journal/init.jsp" %>

<%
JournalArticle article = (JournalArticle)request.getAttribute("view_entries.jsp-article");

PortletURL tempRowURL = (PortletURL)request.getAttribute("view_entries.jsp-tempRowURL");

boolean showCheckBox = JournalArticlePermission.contains(permissionChecker, article, ActionKeys.DELETE) || JournalArticlePermission.contains(permissionChecker, article, ActionKeys.UPDATE);
%>

<div class="article-display-style display-descriptive <%= showCheckBox ? "selectable" : StringPool.BLANK %>" data-draggable="<%= showCheckBox ? Boolean.TRUE.toString() : Boolean.FALSE.toString() %>" data-title="<%= StringUtil.shorten(article.getTitle(locale), 60) %>">
	<a class="article-link" data-folder="<%= Boolean.FALSE.toString() %>" href="<%= tempRowURL.toString() %>" title="<%= HtmlUtil.escapeAttribute(HtmlUtil.unescape(article.getTitle(locale)) + " - " + HtmlUtil.unescape(article.getDescription())) %>">
		<div class="article-thumbnail" style="height: 136px; width: 136px;">
			<img alt="" border="no" src="<%= themeDisplay.getPathThemeImages() + "/file_system/large/default.png" %>" style="max-height: 128px; max-width: 128px;" />
		</div>

		<span class="article-title">
			<%= article.getTitle(locale) %>

			<c:if test="<%= article.isDraft() || article.isPending() %>">

				<%
				String statusLabel = WorkflowConstants.toLabel(article.getStatus());
				%>

				<span class="workflow-status-<%= statusLabel %>">
					(<liferay-ui:message key="<%= statusLabel %>" />)
				</span>
			</c:if>
		</span>

		<span class="article-description"><%= article.getDescription() %></span>
	</a>

	<%
	request.removeAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);
	%>

	<liferay-util:include page="/html/portlet/journal/article_action.jsp" />

	<c:if test="<%= showCheckBox %>">

		<%
		String rowCheckerName = JournalArticle.class.getSimpleName();
		String rowCheckerId = article.getArticleId();
		%>

		<aui:input cssClass="overlay article-selector" label="" name="<%= RowChecker.ROW_IDS + rowCheckerName %>" type="checkbox" value="<%= rowCheckerId %>" />
	</c:if>
</div>