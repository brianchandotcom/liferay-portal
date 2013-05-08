<%--
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

<span class="entry-action overlay">
	<liferay-ui:icon-menu direction="down" extended="<%= false %>" icon="<%= StringPool.BLANK %>" message="<%= StringPool.BLANK %>" showWhenSingleIcon="<%= true %>">
		<c:if test="<%= JournalArticlePermission.contains(permissionChecker, article, ActionKeys.UPDATE) %>">
			<portlet:renderURL var="editURL">
				<portlet:param name="struts_action" value="/journal/edit_article" />
				<portlet:param name="redirect" value="<%= currentURL %>" />
				<portlet:param name="backURL" value="<%= currentURL %>" />
				<portlet:param name="referringPortletResource" value="<%= referringPortletResource %>" />
				<portlet:param name="groupId" value="<%= String.valueOf(article.getGroupId()) %>" />
				<portlet:param name="folderId" value="<%= String.valueOf(article.getFolderId()) %>" />
				<portlet:param name="articleId" value="<%= article.getArticleId() %>" />
				<portlet:param name="version" value="<%= String.valueOf(article.getVersion()) %>" />
			</portlet:renderURL>

			<liferay-ui:icon
				image="edit"
				url="<%= editURL %>"
			/>
		</c:if>

		<c:if test="<%= JournalArticlePermission.contains(permissionChecker, article, ActionKeys.UPDATE) %>">
			<portlet:renderURL var="moveURL">
				<portlet:param name="struts_action" value="/journal/move_article" />
				<portlet:param name="redirect" value="<%= currentURL %>" />
				<portlet:param name="backURL" value="<%= currentURL %>" />
				<portlet:param name="referringPortletResource" value="<%= referringPortletResource %>" />
				<portlet:param name="articleId" value="<%= article.getArticleId() %>" />
			</portlet:renderURL>

			<liferay-ui:icon
				image="submit"
				message="move"
				url="<%= moveURL %>"
			/>
		</c:if>
		<c:if test="<%= JournalArticlePermission.contains(permissionChecker, article, ActionKeys.PERMISSIONS) %>">
			<liferay-security:permissionsURL
				modelResource="<%= JournalArticle.class.getName() %>"
				modelResourceDescription="<%= article.getTitle(locale) %>"
				resourcePrimKey="<%= String.valueOf(article.getResourcePrimKey()) %>"
				var="permissionsURL"
			/>

			<liferay-ui:icon
				image="permissions"
				url="<%= permissionsURL %>"
			/>
		</c:if>

		<c:if test="<%= JournalArticlePermission.contains(permissionChecker, article, ActionKeys.VIEW) %>">

			<%
			StringBundler sb = new StringBundler(9);

			sb.append(themeDisplay.getPathMain());
			sb.append("/journal/view_article_content?cmd=");
			sb.append(Constants.VIEW);
			sb.append("&groupId=");
			sb.append(article.getGroupId());
			sb.append("&articleId=");
			sb.append(article.getArticleId());
			sb.append("&version=");
			sb.append(article.getVersion());
			%>

			<liferay-ui:icon
				image="view"
				target="_blank"
				url="<%= sb.toString() %>"
			/>

			<c:if test="<%= JournalPermission.contains(permissionChecker, scopeGroupId, ActionKeys.ADD_ARTICLE) %>">
				<portlet:renderURL var="copyURL">
					<portlet:param name="struts_action" value="/journal/copy_article" />
					<portlet:param name="redirect" value="<%= currentURL %>" />
					<portlet:param name="groupId" value="<%= String.valueOf(article.getGroupId()) %>" />
					<portlet:param name="oldArticleId" value="<%= article.getArticleId() %>" />
					<portlet:param name="version" value="<%= String.valueOf(article.getVersion()) %>" />
				</portlet:renderURL>

				<liferay-ui:icon
					image="copy"
					url="<%= copyURL.toString() %>"
				/>
			</c:if>
		</c:if>

		<c:if test="<%= JournalArticlePermission.contains(permissionChecker, article, ActionKeys.EXPIRE) && (article.getStatus() == WorkflowConstants.STATUS_APPROVED) %>">
			<portlet:actionURL var="expireURL">
				<portlet:param name="struts_action" value="/journal/edit_article" />
				<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.EXPIRE %>" />
				<portlet:param name="redirect" value="<%= currentURL %>" />
				<portlet:param name="groupId" value="<%= String.valueOf(article.getGroupId()) %>" />
				<portlet:param name="articleId" value="<%= article.getArticleId() %>" />
			</portlet:actionURL>

			<liferay-ui:icon image="time" message="expire" url="<%= expireURL %>" />
		</c:if>

		<c:if test="<%= JournalArticlePermission.contains(permissionChecker, article, ActionKeys.DELETE) %>">
			<portlet:actionURL var="deleteURL">
				<portlet:param name="struts_action" value="/journal/edit_article" />
				<portlet:param name="<%= Constants.CMD %>" value="<%= TrashUtil.isTrashEnabled(scopeGroupId) ? Constants.MOVE_TO_TRASH : Constants.DELETE %>" />
				<portlet:param name="redirect" value="<%= currentURL %>" />
				<portlet:param name="groupId" value="<%= String.valueOf(article.getGroupId()) %>" />
				<portlet:param name="articleId" value="<%= article.getArticleId() %>" />
			</portlet:actionURL>

			<liferay-ui:icon-delete trash="<%= TrashUtil.isTrashEnabled(scopeGroupId) %>" url="<%= deleteURL %>" />
		</c:if>
	</liferay-ui:icon-menu>
</span>