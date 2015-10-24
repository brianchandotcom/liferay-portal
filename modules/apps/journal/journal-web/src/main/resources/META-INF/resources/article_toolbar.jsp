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
String redirect = ParamUtil.getString(request, "redirect");

String referringPortletResource = ParamUtil.getString(request, "referringPortletResource");

JournalArticle article = ActionUtil.getArticle(request);
%>

<div class="article-toolbar toolbar" id="<portlet:namespace />articleToolbar">
	<div class="btn-group">
		<aui:button data-title='<%= LanguageUtil.get(request, "in-order-to-preview-your-changes,-the-web-content-is-saved-as-a-draft") %>' disabled="<%= true %>" icon="icon-search" name="basicPreviewButton" value="basic-preview" />

		<c:if test="<%= JournalArticlePermission.contains(permissionChecker, article, ActionKeys.PERMISSIONS) %>">
			<aui:button disabled="<%= true %>" icon="icon-lock" name="articlePermissionsButton" value="permissions" />
		</c:if>

		<portlet:renderURL var="viewHistoryURL">
			<portlet:param name="mvcPath" value="/view_article_history.jsp" />
			<portlet:param name="redirect" value="<%= redirect %>" />
			<portlet:param name="referringPortletResource" value="<%= referringPortletResource %>" />
			<portlet:param name="groupId" value="<%= String.valueOf(article.getGroupId()) %>" />
			<portlet:param name="articleId" value="<%= article.getArticleId() %>" />
		</portlet:renderURL>

		<aui:button href="<%= viewHistoryURL %>" icon="icon-time" name="articleHistoryButton" value="view-history" />
	</div>
</div>