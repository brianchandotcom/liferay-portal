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

JournalArticle article = (JournalArticle)request.getAttribute(WebKeys.JOURNAL_ARTICLE);

long classNameId = BeanParamUtil.getLong(article, request, "classNameId");

String structureId = BeanParamUtil.getString(article, request, "structureId");

String deleteButtonLabel = "delete-this-version";

if ((article != null) && article.isDraft()) {
	deleteButtonLabel = "discard-draft";
}
%>

<div class="article-toolbar" id="<portlet:namespace />articleToolbar"></div>

<aui:script use="aui-toolbar,aui-dialog-iframe-deprecated,liferay-util-window">
	var permissionPopUp = null;

	var toolbarButtonGroup = [];

	<c:if test="<%= (article != null) && Validator.isNotNull(structureId) && (classNameId == JournalArticleConstants.CLASSNAME_ID_DEFAULT) %>">
		toolbarButtonGroup.push(
			{
				icon: 'icon-search',
				id: '<portlet:namespace />previewArticleButton',
				label: '<%= UnicodeLanguageUtil.get(pageContext, "preview") %>'
			}
		);
	</c:if>

	<c:if test="<%= (article != null) && Validator.isNotNull(structureId) %>">
		toolbarButtonGroup.push(
			{
				icon: 'icon-download',
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

		toolbarButtonGroup.push(
			{
				icon: 'icon-lock',
				label: '<%= UnicodeLanguageUtil.get(pageContext, "permissions") %>',
				on: {
					click: function(event) {
						if (!permissionPopUp) {
							permissionPopUp = Liferay.Util.openWindow(
								{
									dialog: {
										cssClass: 'portlet-asset-categories-admin-dialog permissions-change',
										zIndex: Liferay.zIndex.WINDOW + 2
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

						event.domEvent.preventDefault();
					}
				}
			}
		);
	</c:if>

	<c:if test="<%= (article != null) && !article.isExpired() && JournalArticlePermission.contains(permissionChecker, article, ActionKeys.EXPIRE) && !article.isApproved() %>">
		toolbarButtonGroup.push(
			{
				icon: 'icon-calendar',
				label: '<%= UnicodeLanguageUtil.get(pageContext, "expire-this-version") %>',
				on: {
					click: function() {
						<portlet:namespace />expireArticle();

						event.domEvent.preventDefault();
					}
				}
			}
		);
	</c:if>

	<c:if test="<%= (article != null) && JournalArticlePermission.contains(permissionChecker, article, ActionKeys.DELETE) && !article.isApproved() && !article.isDraft() %>">
		toolbarButtonGroup.push(
			{
				icon: 'icon-remove',
				label: '<liferay-ui:message key="<%= deleteButtonLabel %>" />',
				on: {
					click: function() {
						<portlet:namespace />deleteArticle();

						event.domEvent.preventDefault();
					}
				}
			}
		);
	</c:if>

	<c:if test="<%= article != null %>">
		<portlet:renderURL var="viewHistoryURL">
			<portlet:param name="struts_action" value="/journal/view_article_history" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="referringPortletResource" value="<%= referringPortletResource %>" />
			<portlet:param name="articleId" value="<%= article.getArticleId() %>" />
		</portlet:renderURL>

		toolbarButtonGroup.push(
			{
				icon: 'icon-time',
				label: '<%= UnicodeLanguageUtil.get(pageContext, "view-history") %>',
				on: {
					click: function(event) {
						window.location = '<%= viewHistoryURL %>';

						event.domEvent.preventDefault();
					}
				}
			}
		);
	</c:if>

	new A.Toolbar(
		{
			boundingBox: '#<portlet:namespace />articleToolbar',
			children: [toolbarButtonGroup]
		}
	).render();
</aui:script>