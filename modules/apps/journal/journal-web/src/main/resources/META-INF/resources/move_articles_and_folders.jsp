<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
JournalMoveEntriesDisplayContext journalMoveEntriesDisplayContext = new JournalMoveEntriesDisplayContext(liferayPortletRequest, liferayPortletResponse);
%>

<portlet:actionURL name="/journal/move_articles_and_folders" var="moveArticleURL">
	<portlet:param name="mvcPath" value="/move_articles_and_folders.jsp" />
</portlet:actionURL>

<liferay-frontend:edit-form
	action="<%= moveArticleURL %>"
	name="fm"
>
	<aui:input name="redirect" type="hidden" value="<%= journalMoveEntriesDisplayContext.getRedirect() %>" />
	<aui:input name="newFolderId" type="hidden" value="<%= journalMoveEntriesDisplayContext.getNewFolderId() %>" />

	<liferay-frontend:edit-form-body>
		<liferay-ui:error exception="<%= DuplicateFolderNameException.class %>" message="the-folder-you-selected-already-has-an-entry-with-this-name.-please-select-a-different-folder" />
		<liferay-ui:error exception="<%= InvalidDDMStructureException.class %>" message="the-folder-you-selected-does-not-allow-this-type-of-structure.-please-select-a-different-folder" />

		<liferay-ui:error exception="<%= InvalidFolderException.class %>">

			<%
			InvalidFolderException ife = (InvalidFolderException)errorException;
			%>

			<liferay-ui:message arguments="<%= ife.getMessageArgument(locale) %>" key="<%= ife.getMessageKey() %>" />
		</liferay-ui:error>

		<liferay-ui:error exception="<%= NoSuchFolderException.class %>" message="please-enter-a-valid-folder" />

		<liferay-frontend:fieldset>

			<%
			List<JournalFolder> validMoveFolders = journalMoveEntriesDisplayContext.getValidMoveFolders();
			%>

			<c:if test="<%= !validMoveFolders.isEmpty() %>">
				<h4><liferay-ui:message arguments="<%= validMoveFolders.size() %>" key="x-folders-are-ready-to-be-moved" translateArguments="<%= false %>" /></h4>

				<ul class="list-unstyled">

					<%
					for (JournalFolder folder : validMoveFolders) {
					%>

						<li class="move-folder">
							<span class="folder-title">
								<%= HtmlUtil.escape(folder.getName()) %>
							</span>
						</li>

					<%
					}
					%>

				</ul>
			</c:if>

			<%
			List<JournalFolder> invalidMoveFolders = journalMoveEntriesDisplayContext.getInvalidMoveFolders();
			%>

			<c:if test="<%= !invalidMoveFolders.isEmpty() %>">
				<h4><liferay-ui:message arguments="<%= invalidMoveFolders.size() %>" key="x-folders-cannot-be-moved" translateArguments="<%= false %>" /></h4>

				<ul class="list-unstyled">

					<%
					for (JournalFolder folder : invalidMoveFolders) {
					%>

						<li class="icon-warning-sign move-error move-folder">
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
			</c:if>

			<aui:input name="rowIdsJournalFolder" type="hidden" value="<%= ListUtil.toString(validMoveFolders, JournalFolder.FOLDER_ID_ACCESSOR) %>" />

			<%
			List<JournalArticle> validMoveArticles = journalMoveEntriesDisplayContext.getValidMoveArticles();
			%>

			<c:if test="<%= !validMoveArticles.isEmpty() %>">
				<h4><liferay-ui:message arguments="<%= validMoveArticles.size() %>" key="x-web-content-instances-are-ready-to-be-moved" translateArguments="<%= false %>" /></h4>

				<ul class="list-unstyled">

					<%
					for (JournalArticle validMoveArticle : validMoveArticles) {
					%>

						<li class="move-article">
							<span class="article-title" title="<%= HtmlUtil.escapeAttribute(validMoveArticle.getTitle(locale)) %>">
								<%= HtmlUtil.escape(validMoveArticle.getTitle(locale)) %>
							</span>
						</li>

					<%
					}
					%>

				</ul>
			</c:if>

			<%
			List<JournalArticle> invalidMoveArticles = journalMoveEntriesDisplayContext.getInvalidMoveArticles();
			%>

			<c:if test="<%= !invalidMoveArticles.isEmpty() %>">
				<h4><liferay-ui:message arguments="<%= invalidMoveArticles.size() %>" key="x-web-content-instances-cannot-be-moved" translateArguments="<%= false %>" /></h4>

				<ul class="list-unstyled">

					<%
					for (JournalArticle invalidMoveArticle : invalidMoveArticles) {
					%>

						<li class="icon-warning-sign move-article move-error">
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
			</c:if>

			<aui:input name="rowIdsJournalArticle" type="hidden" value="<%= ListUtil.toString(validMoveArticles, JournalArticle.ARTICLE_ID_ACCESSOR) %>" />

			<aui:input label="new-folder" name="folderName" title="new-folder" type="resource" value="<%= journalMoveEntriesDisplayContext.getNewFolderName() %>" />

			<clay:button
				displayType="secondary"
				id='<%= liferayPortletResponse.getNamespace() + "selectFolderButton" %>'
				label="select"
			/>
		</liferay-frontend:fieldset>
	</liferay-frontend:edit-form-body>

	<liferay-frontend:edit-form-footer>
		<liferay-frontend:edit-form-buttons
			redirect="<%= journalMoveEntriesDisplayContext.getRedirect() %>"
			submitLabel="move"
		/>
	</liferay-frontend:edit-form-footer>
</liferay-frontend:edit-form>

<portlet:renderURL var="selectFolderURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
	<portlet:param name="mvcPath" value="/select_folder.jsp" />
	<portlet:param name="folderId" value="<%= String.valueOf(journalMoveEntriesDisplayContext.getNewFolderId()) %>" />
</portlet:renderURL>

<liferay-frontend:component
	context='<%=
		HashMapBuilder.<String, Object>put(
			"inputName", "newFolderId"
		).put(
			"selectFolderURL", selectFolderURL
		).build()
	%>'
	module="js/SelectFolderButton"
/>