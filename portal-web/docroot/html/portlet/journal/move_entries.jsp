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
String redirect = ParamUtil.getString(request, "redirect");

long newFolderId = ParamUtil.getLong(request, "newFolderId");

List<JournalFolder> folders = (List<JournalFolder>)request.getAttribute(WebKeys.JOURNAL_FOLDERS);

List<JournalFolder> invalidMoveFolders = new ArrayList<JournalFolder>();
List<JournalFolder> validMoveFolders = new ArrayList<JournalFolder>();

for (JournalFolder curFolder : folders) {
	boolean movePermission = JournalFolderPermission.contains(permissionChecker, curFolder, ActionKeys.UPDATE);

	if (movePermission) {
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
	boolean movePermission = JournalArticlePermission.contains(permissionChecker, curArticle, ActionKeys.UPDATE);

	if (movePermission) {
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

<aui:form action="<%= moveArticleURL %>" enctype="multipart/form-data" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "saveArticle(false);" %>'>
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.MOVE %>" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="newFolderId" type="hidden" value="<%= newFolderId %>" />

	<liferay-ui:header
		backURL="<%= redirect %>"
		title="move-articles"
	/>

	<liferay-ui:error exception="<%= DuplicateFolderNameException.class %>" message="the-folder-you-selected-already-has-an-entry-with-this-name.-please-select-a-different-folder" />
	<liferay-ui:error exception="<%= NoSuchFolderException.class %>" message="please-enter-a-valid-folder" />

	<c:if test="<%= !validMoveFolders.isEmpty() %>">
		<div class="move-list-info">
			<h4><%= LanguageUtil.format(pageContext, "x-folders-ready-to-be-moved", validMoveFolders.size()) %></h4>
		</div>

		<div class="move-list">
			<ul class="unstyled">

				<%
				for (JournalFolder folder : validMoveFolders) {
				%>

					<li class="move-folder">
						<span class="folder-title">
							<%= folder.getName() %>
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
			<h4><%= LanguageUtil.format(pageContext, "x-folders-cannot-be-moved", invalidMoveFolders.size()) %></h4>
		</div>

		<div class="move-list">
			<ul class="unstyled">

				<%
				for (JournalFolder folder : invalidMoveFolders) {
				%>

					<li class="move-folder move-error">
						<span class="folder-title">
							<%= folder.getName() %>
						</span>

						<span class="error-message">
							<%= LanguageUtil.get(pageContext, "you-do-not-have-the-required-permissions") %>
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
			<h4><%= LanguageUtil.format(pageContext, "x-articles-are-ready-to-be-moved", validMoveArticles.size()) %></h4>
		</div>

		<div class="move-list">
			<ul class="unstyled">

				<%
				for (JournalArticle validMoveArticle : validMoveArticles) {
				%>

					<li class="move-article">
						<span class="article-title" title="<%= validMoveArticle.getTitle(locale) %>">
							<%= validMoveArticle.getTitle(locale) %>
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
			<h4><%= LanguageUtil.format(pageContext, "x-articles-cannot-be-moved", invalidMoveArticles.size()) %></h4>
		</div>

		<div class="move-list">
			<ul class="unstyled">

				<%
				for (JournalArticle invalidMoveArticle : invalidMoveArticles) {
				%>

					<li class="move-article move-error">
						<span class="article-title" title="<%= invalidMoveArticle.getTitle() %>">
							<%= invalidMoveArticle.getTitle() %>
						</span>

						<span class="error-message">
							<%= LanguageUtil.get(pageContext, "you-do-not-have-the-required-permissions") %>
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

			folder = folder.toEscapedModel();

			folderName = folder.getName();
		}
		else {
			folderName = LanguageUtil.get(pageContext, "home");
		}
		%>

		<portlet:renderURL var="viewFolderURL">
			<portlet:param name="struts_action" value="/journal/view" />
			<portlet:param name="folderId" value="<%= String.valueOf(newFolderId) %>" />
		</portlet:renderURL>

		<aui:field-wrapper label="new-folder">
			<aui:a href="<%= viewFolderURL %>" id="folderName"><%= folderName %></aui:a>

			<aui:button name="selectFolderButton" value="select" />
		</aui:field-wrapper>

		<aui:button-row>
			<aui:button type="submit" value="move" />

			<aui:button href="<%= redirect %>" type="cancel" />
		</aui:button-row>
	</aui:fieldset>
</aui:form>

<portlet:renderURL var="selectFolderURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
	<portlet:param name="struts_action" value="/journal/select_folder" />
	<portlet:param name="folderId" value="<%= String.valueOf(newFolderId) %>" />
</portlet:renderURL>

<aui:script use="aui-base">
	A.one('#<portlet:namespace />selectFolderButton').on(
		'click',
		function(event) {
			Liferay.Util.selectEntity(
				{
					dialog: {
						align: Liferay.Util.Window.ALIGN_CENTER,
						constrain: true,
						modal: true,
						stack: true,
						width: 680
					},
					id: '<portlet:namespace />selectFolder',
					title: '<%= UnicodeLanguageUtil.format(pageContext, "select-x", "folder") %>',
					uri: '<%= selectFolderURL.toString() %>'
				},
				function(event) {
					var folderData = {
						idString: 'newFolderId',
						idValue: event.folderid,
						nameString: 'folderName',
						nameValue: event.foldername
					};

					Liferay.Util.selectFolder(folderData, '<portlet:renderURL><portlet:param name="struts_action" value="/journal/view" /></portlet:renderURL>', '<portlet:namespace />');
				}
			);
		}
	);
</aui:script>

<aui:script>
	function <portlet:namespace />saveArticle() {
		submitForm(document.<portlet:namespace />fm);
	}
</aui:script>

<%
PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(pageContext, "move-articles"), currentURL);
%>