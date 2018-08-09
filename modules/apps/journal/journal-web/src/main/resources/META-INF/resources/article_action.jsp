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
	article = (JournalArticle)request.getAttribute("info_panel.jsp-entry");
}
%>

<liferay-ui:icon-menu
	direction="left-side"
	icon="<%= StringPool.BLANK %>"
	markupView="lexicon"
	message="<%= StringPool.BLANK %>"
	showWhenSingleIcon="<%= true %>"
>
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

		<c:if test="<%= JournalArticlePermission.contains(permissionChecker, article, ActionKeys.SUBSCRIBE) %>">
			<c:choose>
				<c:when test="<%= JournalUtil.isSubscribedToArticle(article.getCompanyId(), scopeGroupId, themeDisplay.getUserId(), article.getResourcePrimKey()) %>">
					<portlet:actionURL name="unsubscribeArticle" var="subscribeArticleURL">
						<portlet:param name="redirect" value="<%= currentURL %>" />
						<portlet:param name="articleId" value="<%= String.valueOf(article.getResourcePrimKey()) %>" />
					</portlet:actionURL>

					<liferay-ui:icon
						message="unsubscribe"
						url="<%= subscribeArticleURL.toString() %>"
					/>
				</c:when>
				<c:otherwise>
					<portlet:actionURL name="subscribeArticle " var="subscribeArticleURL">
						<portlet:param name="redirect" value="<%= currentURL %>" />
						<portlet:param name="articleId" value="<%= String.valueOf(article.getResourcePrimKey()) %>" />
					</portlet:actionURL>

					<liferay-ui:icon
						message="subscribe"
						url="<%= subscribeArticleURL.toString() %>"
					/>
				</c:otherwise>
			</c:choose>
		</c:if>

		<c:if test="<%= JournalFolderPermission.contains(permissionChecker, scopeGroupId, article.getFolderId(), ActionKeys.ADD_ARTICLE) %>">
			<c:choose>
				<c:when test="<%= journalWebConfiguration.journalArticleForceAutogenerateId() %>">
					<portlet:actionURL name="copyArticle" var="copyArticleURL">
						<portlet:param name="redirect" value="<%= currentURL %>" />
						<portlet:param name="groupId" value="<%= String.valueOf(article.getGroupId()) %>" />
						<portlet:param name="oldArticleId" value="<%= article.getArticleId() %>" />
						<portlet:param name="version" value="<%= String.valueOf(article.getVersion()) %>" />
						<portlet:param name="autoArticleId" value="<%= Boolean.TRUE.toString() %>" />
					</portlet:actionURL>

					<liferay-ui:icon
						message="copy"
						url="<%= copyArticleURL.toString() %>"
					/>
				</c:when>
				<c:otherwise>
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
				</c:otherwise>
			</c:choose>
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
		<portlet:actionURL name='<%= trashHelper.isTrashEnabled(scopeGroupId) ? "moveToTrash" : "deleteArticles" %>' var="deleteURL">
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="groupId" value="<%= String.valueOf(article.getGroupId()) %>" />
			<portlet:param name="articleId" value="<%= article.getArticleId() %>" />
		</portlet:actionURL>

		<liferay-ui:icon-delete
			trash="<%= trashHelper.isTrashEnabled(scopeGroupId) %>"
			url="<%= deleteURL %>"
		/>
	</c:if>

	<%
	Group group = themeDisplay.getScopeGroup();
	%>

	<c:if test="<%= journalDisplayContext.isShowPublishArticleAction(article) && !group.isLayout() %>">
		<portlet:actionURL name="/journal/publish_article" var="publishArticleURL">
			<portlet:param name="backURL" value="<%= currentURL %>" />
			<portlet:param name="groupId" value="<%= String.valueOf(article.getGroupId()) %>" />
			<portlet:param name="articleId" value="<%= article.getArticleId() %>" />
		</portlet:actionURL>

		<liferay-ui:icon-delete
			confirmation="are-you-sure-you-want-to-publish-the-selected-web-content"
			message="publish-to-live"
			url="<%= publishArticleURL %>"
		/>
	</c:if>
</liferay-ui:icon-menu>