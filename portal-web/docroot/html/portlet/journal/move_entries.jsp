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

<%@ include file="/html/portlet/journal/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

long newFolderId = ParamUtil.getLong(request, "newFolderId");

List<JournalFolder> folders = (List<JournalFolder>)request.getAttribute(WebKeys.JOURNAL_FOLDERS);

List<JournalFolder> invalidMoveFolders = new ArrayList<JournalFolder>();
List<JournalFolder> validMoveFolders = new ArrayList<JournalFolder>();

for (JournalFolder curFolder : folders) {
	boolean hasUpdatePermission = JournalFolderPermission.contains(permissionChecker, curFolder, ActionKeys.UPDATE);

	if (hasUpdatePermission) {
		validMoveFolders.add(curFolder);
	}
	else {
		invalidMoveFolders.add(curFolder);
	}
}

JournalArticle article = (JournalArticle)request.getAttribute(WebKeys.JOURNAL_ARTICLE);

List<JournalArticle> articles = null;

if (article != null) {
	articles = new ArrayList<JournalArticle>();

	articles.add(article);
}
else {
	articles = (List<JournalArticle>)request.getAttribute(WebKeys.JOURNAL_ARTICLES);
}

List<JournalArticle> validMoveArticles = new ArrayList<JournalArticle>();
List<JournalArticle> invalidMoveArticles = new ArrayList<JournalArticle>();

for (JournalArticle curArticle : articles) {
	boolean hasUpdatePermission = JournalArticlePermission.contains(permissionChecker, curArticle, ActionKeys.UPDATE);

	if (hasUpdatePermission) {
		validMoveArticles.add(curArticle);
	}
	else {
		invalidMoveArticles.add(curArticle);
	}
}
%>

<portlet:actionURL var="moveArticleURL">
	<portlet:param name="struts_action" value="/journal/move_entry" />
</portlet:actionURL>

<aui:form action="<%= moveArticleURL %>" enctype="multipart/form-data" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "saveArticle();" %>'>
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.MOVE %>" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="newFolderId" type="hidden" value="<%= newFolderId %>" />

	<liferay-ui:header
		backURL="<%= redirect %>"
		title="move-web-content"
	/>

	<liferay-ui:error exception="<%= DuplicateFolderNameException.class %>" message="the-folder-you-selected-already-has-an-entry-with-this-name.-please-select-a-different-folder" />
	<liferay-ui:error exception="<%= InvalidDDMStructureException.class %>" message="the-folder-you-selected-does-not-allow-this-type-of-structure.-please-select-a-different-folder" />
	<liferay-ui:error exception="<%= NoSuchFolderException.class %>" message="please-enter-a-valid-folder" />

	<c:if test="<%= !validMoveFolders.isEmpty() %>">
		<div class="move-list-info">
			<h4><%= LanguageUtil.format(request, "x-folders-ready-to-be-moved", validMoveFolders.size(), false) %></h4>
		</div>

		<div class="move-list">
			<ul class="list-unstyled">

				<%
				for (JournalFolder folder : validMoveFolders) {
					AssetRendererFactory assetRendererFactory = AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(JournalFolder.class.getName());

					AssetRenderer assetRenderer = assetRendererFactory.getAssetRenderer(folder.getFolderId());
				%>

					<li class="move-folder">
						<i class="<%= assetRendererFactory.getIconCssClass() %>"></i>

						<span class="folder-title">
							<%= HtmlUtil.escape(folder.getName()) %>
						</span>
					</li>

				<%
				}
				%>

			</ul>
		</div>
	</c:if>

	<c:if test="<%= !invalidMoveFolders.isEmpty() %>">
		<div class="move-list-info">
			<h4><%= LanguageUtil.format(request, "x-folders-cannot-be-moved", invalidMoveFolders.size(), false) %></h4>
		</div>

		<div class="move-list">
			<ul class="list-unstyled">

				<%
				for (JournalFolder folder : invalidMoveFolders) {
					AssetRendererFactory assetRendererFactory = AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(JournalFolder.class.getName());

					AssetRenderer assetRenderer = assetRendererFactory.getAssetRenderer(folder.getFolderId());
				%>

					<li class="icon-warning-sign move-error move-folder">
						<i class="<%= assetRenderer.getIconCssClass() %>"></i>

						<span class="folder-title">
							<%= HtmlUtil.escape(folder.getName()) %>
						</span>

						<span class="error-message">
							<%= LanguageUtil.get(request, "you-do-not-have-the-required-permissions") %>
						</span>
					</li>

				<%
				}
				%>

			</ul>
		</div>
	</c:if>

	<aui:input name="folderIds" type="hidden" value="<%= ListUtil.toString(validMoveFolders, JournalFolder.FOLDER_ID_ACCESSOR) %>" />

	<c:if test="<%= !validMoveArticles.isEmpty() %>">
		<div class="move-list-info">
			<h4><%= LanguageUtil.format(request, "x-web-content-instances-are-ready-to-be-moved", validMoveArticles.size(), false) %></h4>
		</div>

		<div class="move-list">
			<ul class="list-unstyled">

				<%
				for (JournalArticle validMoveArticle : validMoveArticles) {
					AssetRendererFactory assetRendererFactory = AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(JournalArticle.class.getName());

					AssetRenderer assetRenderer = assetRendererFactory.getAssetRenderer(JournalArticleAssetRenderer.getClassPK(validMoveArticle));
				%>

					<li class="move-article">
						<i class="<%= assetRenderer.getIconCssClass() %>"></i>

						<span class="article-title" title="<%= HtmlUtil.escapeAttribute(validMoveArticle.getTitle(locale)) %>">
							<%= HtmlUtil.escape(validMoveArticle.getTitle(locale)) %>
						</span>
					</li>

				<%
				}
				%>

			</ul>
		</div>
	</c:if>

	<c:if test="<%= !invalidMoveArticles.isEmpty() %>">
		<div class="move-list-info">
			<h4><%= LanguageUtil.format(request, "x-web-content-instances-cannot-be-moved", invalidMoveArticles.size(), false) %></h4>
		</div>

		<div class="move-list">
			<ul class="list-unstyled">

				<%
				for (JournalArticle invalidMoveArticle : invalidMoveArticles) {
					AssetRendererFactory assetRendererFactory = AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(JournalArticle.class.getName());

					AssetRenderer assetRenderer = assetRendererFactory.getAssetRenderer(JournalArticleAssetRenderer.getClassPK(invalidMoveArticle));
				%>

					<li class="icon-warning-sign move-article move-error">
						<i class="<%= assetRenderer.getIconCssClass() %>"></i>

						<span class="article-title" title="<%= HtmlUtil.escapeAttribute(invalidMoveArticle.getTitle()) %>">
							<%= HtmlUtil.escape(invalidMoveArticle.getTitle()) %>
						</span>

						<span class="error-message">
							<%= LanguageUtil.get(request, "you-do-not-have-the-required-permissions") %>
						</span>
					</li>

				<%
				}
				%>

			</ul>
		</div>
	</c:if>

	<aui:input name="articleIds" type="hidden" value="<%= ListUtil.toString(validMoveArticles, JournalArticle.ARTICLE_ID_ACCESSOR) %>" />

	<aui:fieldset>

		<%
		String folderName = StringPool.BLANK;

		if (newFolderId > 0) {
			JournalFolder folder = JournalFolderLocalServiceUtil.getFolder(newFolderId);

			folderName = folder.getName();
		}
		else {
			folderName = LanguageUtil.get(request, "home");
		}
		%>

		<div class="form-group">
			<aui:input label="new-folder" name="folderName" title="new-folder" type="resource" value="<%= folderName %>" />

			<aui:button name="selectFolderButton" value="select" />
		</div>

		<aui:button-row>
			<aui:button type="submit" value="move" />

			<aui:button href="<%= redirect %>" type="cancel" />
		</aui:button-row>
	</aui:fieldset>
</aui:form>

<aui:script>
	AUI.$('#<portlet:namespace />selectFolderButton').on(
		'click',
		function(event) {
			Liferay.Util.selectEntity(
				{
					dialog: {
						constrain: true,
						destroyOnHide: true,
						modal: true,
						width: 1024
					},
					id: '<portlet:namespace />selectFolder',
					title: '<liferay-ui:message arguments="folder" key="select-x" />',

					<portlet:renderURL var="selectFolderURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
						<portlet:param name="struts_action" value="/journal/select_folder" />
						<portlet:param name="folderId" value="<%= String.valueOf(newFolderId) %>" />
					</portlet:renderURL>

					uri: '<%= selectFolderURL.toString() %>'
				},
				function(event) {
					var folderData = {
						idString: 'newFolderId',
						idValue: event.folderid,
						nameString: 'folderName',
						nameValue: event.foldername
					};

					Liferay.Util.selectFolder(folderData, '<portlet:namespace />');
				}
			);
		}
	);

	function <portlet:namespace />saveArticle() {
		submitForm(document.<portlet:namespace />fm);
	}
</aui:script>

<%
PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, "move-web-content"), currentURL);
%>