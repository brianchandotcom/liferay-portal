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
JournalMoveEntriesDisplayContext journalMovesEntriesDisplayContext = new JournalMoveEntriesDisplayContext(liferayPortletRequest, liferayPortletResponse, currentURL);
%>

<portlet:actionURL name="moveEntries" var="moveArticleURL">
	<portlet:param name="mvcPath" value="/move_entries.jsp" />
</portlet:actionURL>

<aui:form action="<%= moveArticleURL %>" cssClass="container-fluid-1280" enctype="multipart/form-data" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "saveArticle();" %>'>
	<aui:input name="redirect" type="hidden" value="<%= journalMovesEntriesDisplayContext.getRedirect() %>" />
	<aui:input name="newFolderId" type="hidden" value="<%= journalMovesEntriesDisplayContext.getNewFolderId() %>" />

	<liferay-ui:error exception="<%= DuplicateFolderNameException.class %>" message="the-folder-you-selected-already-has-an-entry-with-this-name.-please-select-a-different-folder" />
	<liferay-ui:error exception="<%= InvalidDDMStructureException.class %>" message="the-folder-you-selected-does-not-allow-this-type-of-structure.-please-select-a-different-folder" />
	<liferay-ui:error exception="<%= NoSuchFolderException.class %>" message="please-enter-a-valid-folder" />

	<%
	List<JournalFolder> validMoveFolders = journalMovesEntriesDisplayContext.getValidMoveFolders();
	%>

	<c:if test="<%= !validMoveFolders.isEmpty() %>">
		<div class="move-list-info">
			<h4><liferay-ui:message arguments="<%= validMoveFolders.size() %>" key="x-folders-ready-to-be-moved" translateArguments="<%= false %>" /></h4>
		</div>

		<div class="move-list">
			<ul class="list-unstyled">

				<%
				for (JournalFolder folder : validMoveFolders) {
				%>

					<li class="move-folder">
						<i class="<%= journalMovesEntriesDisplayContext.getIconCssClass(folder) %>"></i>

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

	<%
	List<JournalFolder> invalidMoveFolders = journalMovesEntriesDisplayContext.getInvalidMoveFolders();
	%>

	<c:if test="<%= !invalidMoveFolders.isEmpty() %>">
		<div class="move-list-info">
			<h4><liferay-ui:message arguments="<%= invalidMoveFolders.size() %>" key="x-folders-cannot-be-moved" translateArguments="<%= false %>" /></h4>
		</div>

		<div class="move-list">
			<ul class="list-unstyled">

				<%
				for (JournalFolder folder : invalidMoveFolders) {
				%>

					<li class="icon-warning-sign move-error move-folder">
						<i class="<%= journalMovesEntriesDisplayContext.getIconCssClass(folder) %>"></i>

						<span class="folder-title">
							<%= HtmlUtil.escape(folder.getName()) %>
						</span>

						<span class="error-message">
							<liferay-ui:message key="you-do-not-have-the-required-permissions" />
						</span>
					</li>

				<%
				}
				%>

			</ul>
		</div>
	</c:if>

	<aui:input name="rowIdsJournalFolder" type="hidden" value="<%= ListUtil.toString(validMoveFolders, JournalFolder.FOLDER_ID_ACCESSOR) %>" />

	<%
	List<JournalArticle> validMoveArticles = journalMovesEntriesDisplayContext.getValidMoveArticles();
	%>

	<c:if test="<%= !validMoveArticles.isEmpty() %>">
		<div class="move-list-info">
			<h4><liferay-ui:message arguments="<%= validMoveArticles.size() %>" key="x-web-content-instances-are-ready-to-be-moved" translateArguments="<%= false %>" /></h4>
		</div>

		<div class="move-list">
			<ul class="list-unstyled">

				<%
				for (JournalArticle validMoveArticle : validMoveArticles) {
				%>

					<li class="move-article">
						<i class="<%= journalMovesEntriesDisplayContext.getIconCssClass(validMoveArticle) %>"></i>

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

	<%
	List<JournalArticle> invalidMoveArticles = journalMovesEntriesDisplayContext.getInvalidMoveArticles();
	%>

	<c:if test="<%= !invalidMoveArticles.isEmpty() %>">
		<div class="move-list-info">
			<h4><liferay-ui:message arguments="<%= invalidMoveArticles.size() %>" key="x-web-content-instances-cannot-be-moved" translateArguments="<%= false %>" /></h4>
		</div>

		<div class="move-list">
			<ul class="list-unstyled">

				<%
				for (JournalArticle invalidMoveArticle : invalidMoveArticles) {
				%>

					<li class="icon-warning-sign move-article move-error">
						<i class="<%= journalMovesEntriesDisplayContext.getIconCssClass(invalidMoveArticle) %>"></i>

						<span class="article-title" title="<%= HtmlUtil.escapeAttribute(invalidMoveArticle.getTitle()) %>">
							<%= HtmlUtil.escape(invalidMoveArticle.getTitle()) %>
						</span>

						<span class="error-message">
							<liferay-ui:message key="you-do-not-have-the-required-permissions" />
						</span>
					</li>

				<%
				}
				%>

			</ul>
		</div>
	</c:if>

	<aui:input name="rowIdsJournalArticle" type="hidden" value="<%= ListUtil.toString(validMoveArticles, JournalArticle.ARTICLE_ID_ACCESSOR) %>" />

	<aui:fieldset>
		<div class="form-group">
			<aui:input label="new-folder" name="folderName" title="new-folder" type="resource" value="<%= journalMovesEntriesDisplayContext.getNewFolderName() %>" />

			<aui:button name="selectFolderButton" value="select" />
		</div>

		<aui:button-row>
			<aui:button cssClass="btn-lg" type="submit" value="move" />

			<aui:button cssClass="btn-lg" href="<%= journalMovesEntriesDisplayContext.getRedirect() %>" type="cancel" />
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
						<portlet:param name="mvcPath" value="/select_folder.jsp" />
						<portlet:param name="folderId" value="<%= String.valueOf(journalMovesEntriesDisplayContext.getNewFolderId()) %>" />
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