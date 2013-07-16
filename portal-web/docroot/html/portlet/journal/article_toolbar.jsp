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

	<c:if test="<%= (article != null) %>">
		<portlet:renderURL var="previewArticleContentURL" windowState="<%= LiferayWindowState.EXCLUSIVE.toString() %>">
			<portlet:param name="struts_action" value="/journal/preview_article_content" />
			<portlet:param name="groupId" value="<%= String.valueOf(article.getGroupId()) %>" />
			<portlet:param name="articleId" value="<%= article.getArticleId() %>" />
			<portlet:param name="version" value="<%= String.valueOf(article.getVersion()) %>" />
		</portlet:renderURL>

		var previewArticleContentURL = '<%= previewArticleContentURL %>';

		var form = A.one('#<portlet:namespace />fm1');

		form.on(
			'change',
			function(event) {
				previewArticleContentURL = null;
			}
		);

		<c:if test="<%= classNameId == JournalArticleConstants.CLASSNAME_ID_DEFAULT %>">
			toolbarButtonGroup.push(
				{
					icon: 'icon-search',
					label: '<%= UnicodeLanguageUtil.get(pageContext, "preview") %>',
					on: {
						click: function(event) {
							event.domEvent.preventDefault();

							if (previewArticleContentURL && !(typeof CKEDITOR === 'undefined') && !CKEDITOR.instances.<portlet:namespace />articleContent.checkDirty()) {
								Liferay.fire(
									'previewArticle',
									{
										title: '<%= article.getTitle(locale) %>',
										uri: '<%= previewArticleContentURL.toString() %>'
									}
								);
							}
							else {
								if (!confirm('<%= UnicodeLanguageUtil.get(pageContext, "this-article-has-changes-and-to-preview-the-content-you-have-to-save-the-article-as-draft") %>')) {
									return false;
								}

								form.one('#<portlet:namespace /><%= Constants.CMD %>').val('<%= Constants.PREVIEW %>');

								submitForm(form);
							}
						}
					}
				}
			);
		</c:if>
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
										cssClass: 'portlet-asset-categories-admin-dialog permissions-change'
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