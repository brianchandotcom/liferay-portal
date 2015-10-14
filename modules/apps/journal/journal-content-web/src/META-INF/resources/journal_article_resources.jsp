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
JournalArticle article = journalContentDisplayContext.getArticle();
AssetRenderer<JournalArticle> assetRenderer = journalContentDisplayContext.getAssetRenderer();
User userDisplay = UserLocalServiceUtil.fetchUserById(assetRenderer.getUserId());
%>

<liferay-util:buffer var="headerHtml">
	<%= HtmlUtil.escapeAttribute(assetRenderer.getTitle(locale)) %>

	<c:if test="<%= article.getGroupId() != themeDisplay.getScopeGroupId() %>">

		<%
		Group articleGroup = GroupLocalServiceUtil.getGroup(article.getGroupId());
		%>

		(<%= articleGroup.getDescriptiveName(locale) %>)
	</c:if>
</liferay-util:buffer>

<liferay-util:buffer var="statusHtml">
	<aui:workflow-status markupView="lexicon" showIcon="<%= false %>" showLabel="<%= false %>" status="<%= article.getStatus() %>" />
</liferay-util:buffer>

<liferay-frontend:card
	cssClass="article-preview-content"
	footer="<%= statusHtml %>"
	imageUrl="<%= HtmlUtil.escapeAttribute(assetRenderer.getThumbnailPath(liferayPortletRequest)) %>"
	smallImageCSSClass="user-icon user-icon-lg"
	smallImageUrl="<%= userDisplay != null ? userDisplay.getPortraitURL(themeDisplay) : UserConstants.getPortraitURL(themeDisplay.getPathImage(), true, 0, null) %>"
	subtitle="<%= assetRenderer.getSummary() %>"
	title="<%= headerHtml %>"
/>