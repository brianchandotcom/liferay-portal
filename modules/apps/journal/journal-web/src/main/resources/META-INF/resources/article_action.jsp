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

ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

JournalArticle article = null;

if (row != null) {
	article = (JournalArticle)row.getObject();
}
else {
	article = (JournalArticle)request.getAttribute("view_entries.jsp-article");
}
%>

<liferay-ui:icon-menu direction="left-side" icon="<%= StringPool.BLANK %>" markupView="lexicon" message="<%= StringPool.BLANK %>" showWhenSingleIcon="<%= true %>">
	<c:if test="<%= JournalArticlePermission.contains(permissionChecker, article, ActionKeys.UPDATE) %>">
		<portlet:renderURL var="editURL">
			<portlet:param name="mvcPath" value="/edit_article.jsp" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="referringPortletResource" value="<%= referringPortletResource %>" />
			<portlet:param name="groupId" value="<%= String.valueOf(article.getGroupId()) %>" />
			<portlet:param name="folderId" value="<%= String.valueOf(article.getFolderId()) %>" />
			<portlet:param name="articleId" value="<%= article.getArticleId() %>" />
			<portlet:param name="version" value="<%= String.valueOf(article.getVersion()) %>" />
		</portlet:renderURL>

		<liferay-ui:icon
			message="edit"
			url="<%= editURL %>"
		/>
	</c:if>

	<c:if test="<%= JournalArticlePermission.contains(permissionChecker, article, ActionKeys.UPDATE) %>">
		<portlet:renderURL var="moveURL">
			<portlet:param name="mvcPath" value="/move_entries.jsp" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="referringPortletResource" value="<%= referringPortletResource %>" />
			<portlet:param name="rowIdsJournalArticle" value="<%= article.getArticleId() %>" />
		</portlet:renderURL>

		<liferay-ui:icon
			message="move"
			url="<%= moveURL %>"
		/>
	</c:if>

	<c:if test="<%= JournalArticlePermission.contains(permissionChecker, article, ActionKeys.PERMISSIONS) %>">
		<liferay-security:permissionsURL
			modelResource="<%= JournalArticle.class.getName() %>"
			modelResourceDescription="<%= HtmlUtil.escape(article.getTitle(locale)) %>"
			resourcePrimKey="<%= String.valueOf(article.getResourcePrimKey()) %>"
			var="permissionsURL"
			windowState="<%= LiferayWindowState.POP_UP.toString() %>"
		/>

		<liferay-ui:icon
			message="permissions"
			method="get"
			url="<%= permissionsURL %>"
			useDialog="<%= true %>"
		/>
	</c:if>

	<c:if test="<%= JournalArticlePermission.contains(permissionChecker, article, ActionKeys.VIEW) %>">
		<liferay-portlet:renderURL plid="<%= JournalUtil.getPreviewPlid(article, themeDisplay) %>" var="previewArticleContentURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
			<portlet:param name="mvcPath" value="/preview_article_content.jsp" />
			<portlet:param name="groupId" value="<%= String.valueOf(article.getGroupId()) %>" />
			<portlet:param name="articleId" value="<%= article.getArticleId() %>" />
			<portlet:param name="version" value="<%= String.valueOf(article.getVersion()) %>" />
		</liferay-portlet:renderURL>

		<%
		String taglibOnClick = "Liferay.fire('previewArticle', {title: '" + HtmlUtil.escapeJS(article.getTitle(locale)) + "', uri: '" + HtmlUtil.escapeJS(previewArticleContentURL.toString()) + "'});";
		%>

		<liferay-ui:icon
			message="preview"
			onClick="<%= taglibOnClick %>"
			url="javascript:;"
		/>

		<c:if test="<%= JournalArticlePermission.contains(permissionChecker, article, ActionKeys.UPDATE) %>">
			<portlet:renderURL var="viewHistoryURL">
				<portlet:param name="mvcPath" value="/view_article_history.jsp" />
				<portlet:param name="redirect" value="<%= redirect %>" />
				<portlet:param name="backURL" value="<%= currentURL %>" />
				<portlet:param name="referringPortletResource" value="<%= referringPortletResource %>" />
				<portlet:param name="articleId" value="<%= article.getArticleId() %>" />
			</portlet:renderURL>

			<liferay-ui:icon
				message="view-history"
				url="<%= viewHistoryURL.toString() %>"
			/>
		</c:if>

		<c:if test="<%= JournalFolderPermission.contains(permissionChecker, scopeGroupId, article.getFolderId(), ActionKeys.ADD_ARTICLE) %>">
			<portlet:renderURL var="copyURL">
				<portlet:param name="mvcPath" value="/copy_article.jsp" />
				<portlet:param name="redirect" value="<%= currentURL %>" />
				<portlet:param name="groupId" value="<%= String.valueOf(article.getGroupId()) %>" />
				<portlet:param name="oldArticleId" value="<%= article.getArticleId() %>" />
				<portlet:param name="version" value="<%= String.valueOf(article.getVersion()) %>" />
			</portlet:renderURL>

			<liferay-ui:icon
				message="copy"
				url="<%= copyURL.toString() %>"
			/>
		</c:if>
	</c:if>

	<c:if test="<%= JournalArticlePermission.contains(permissionChecker, article, ActionKeys.EXPIRE) && article.hasApprovedVersion() %>">
		<portlet:actionURL name="expireArticles" var="expireURL">
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="groupId" value="<%= String.valueOf(article.getGroupId()) %>" />
			<portlet:param name="articleId" value="<%= article.getArticleId() %>" />
		</portlet:actionURL>

		<liferay-ui:icon
			message="expire"
			url="<%= expireURL %>"
		/>
	</c:if>

	<c:if test="<%= JournalArticlePermission.contains(permissionChecker, article, ActionKeys.DELETE) %>">
		<portlet:actionURL name='<%= TrashUtil.isTrashEnabled(scopeGroupId) ? "moveToTrash" : "deleteArticles" %>' var="deleteURL">
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="groupId" value="<%= String.valueOf(article.getGroupId()) %>" />
			<portlet:param name="articleId" value="<%= article.getArticleId() %>" />
		</portlet:actionURL>

		<liferay-ui:icon-delete
			trash="<%= TrashUtil.isTrashEnabled(scopeGroupId) %>"
			url="<%= deleteURL %>"
		/>
	</c:if>
</liferay-ui:icon-menu>