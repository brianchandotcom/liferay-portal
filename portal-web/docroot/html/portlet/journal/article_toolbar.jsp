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
String originalRedirect = ParamUtil.getString(request, "originalRedirect");

JournalArticle article = (JournalArticle)request.getAttribute(WebKeys.JOURNAL_ARTICLE);

long classNameId = BeanParamUtil.getLong(article, request, "classNameId");

String structureId = BeanParamUtil.getString(article, request, "structureId");

String deleteButtonLabel = "delete-this-version";

if ((article != null) && article.isDraft()) {
	deleteButtonLabel = "discard-draft";
}
%>

<div class="article-toolbar" id="<portlet:namespace />articleToolbar"></div>

<aui:script use="aui-toolbar,aui-dialog-iframe,liferay-util-window">
	var permissionPopUp = null;

	var toolbarChildren = [];

	<c:if test="<%= (article != null) && Validator.isNotNull(structureId) && (classNameId == JournalArticleConstants.CLASSNAME_ID_DEFAULT) %>">
		toolbarChildren.push(
			{
				icon: 'preview',
				id: '<portlet:namespace />previewArticleButton',
				label: '<%= UnicodeLanguageUtil.get(pageContext, "preview") %>'
			}
		);
	</c:if>

	<c:if test="<%= (article != null) && Validator.isNotNull(structureId) %>">
		toolbarChildren.push(
			{
				icon: 'download',
				id: '<portlet:namespace />downloadArticleContentButton',
				label: '<%= UnicodeLanguageUtil.get(pageContext, "download") %>'
			}
		);
	</c:if>

	<c:if test="<%= (article != null) && JournalArticlePermission.contains(permissionChecker, article, ActionKeys.PERMISSIONS) %>">
		<liferay-security:permissionsURL windowState="<%= LiferayWindowState.POP_UP.toString() %>"
			modelResource="<%= JournalArticle.class.getName() %>"
			modelResourceDescription="<%= article.getTitle(locale) %>"
			resourcePrimKey="<%= String.valueOf(article.getResourcePrimKey()) %>"
			var="permissionsURL"
		/>

		toolbarChildren.push(
			{
				handler: function(event) {
					if (!permissionPopUp) {
						permissionPopUp = Liferay.Util.openWindow(
							{
								dialog: {
									align: Liferay.Util.Window.ALIGN_CENTER,
									cssClass: 'portlet-asset-categories-admin-dialog permissions-change',
									width: 700
								},
								id: '<portlet:namespace />articlePermissions',
								title: '<%= UnicodeLanguageUtil.get(pageContext, "permissions") %>',
								uri: '<%= permissionsURL %>'
							}
						);
					}
					else {
						permissionPopUp.iframe.node.get('contentWindow.location').reload(true);
					}

					permissionPopUp.show();
					permissionPopUp.centered();

				},
				icon: 'permissions',
				label: '<%= UnicodeLanguageUtil.get(pageContext, "permissions") %>'
			}
		);
	</c:if>

	<c:if test="<%= (article != null) && !article.isExpired() && JournalArticlePermission.contains(permissionChecker, article, ActionKeys.EXPIRE) && !article.isApproved() %>">
		toolbarChildren.push(
			{
				handler: function() {
					<portlet:namespace />expireArticle();
				},
				icon: 'expire',
				label: '<%= UnicodeLanguageUtil.get(pageContext, "expire-this-version") %>'
			}
		);
	</c:if>

	<c:if test="<%= (article != null) && JournalArticlePermission.contains(permissionChecker, article, ActionKeys.DELETE) && !article.isApproved() && !article.isDraft() %>">
		toolbarChildren.push(
			{
				handler: function() {
					<portlet:namespace />deleteArticle();
				},
				icon: 'delete',
				label: '<liferay-ui:message key="<%= deleteButtonLabel %>" />'
			}
		);
	</c:if>

	<c:if test="<%= article != null %>">
		<portlet:renderURL var="viewHistoryURL">
			<portlet:param name="struts_action" value="/journal/view_article_history" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="originalRedirect" value="<%= originalRedirect %>" />
			<portlet:param name="articleId" value="<%= article.getArticleId() %>" />
		</portlet:renderURL>

		toolbarChildren.push(
			{
				handler: function (event) {
					window.location = '<%= viewHistoryURL %>';
				},
				icon: 'history',
				label: '<%= UnicodeLanguageUtil.get(pageContext, "view-history") %>'
			}
		);
	</c:if>

	new A.Toolbar(
		{
			activeState: false,
			boundingBox: '#<portlet:namespace />articleToolbar',
			children: toolbarChildren
		}
	).render();
</aui:script>