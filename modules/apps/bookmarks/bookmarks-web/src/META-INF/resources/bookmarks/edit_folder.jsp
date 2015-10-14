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

<%@ include file="/bookmarks/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

BookmarksFolder folder = (BookmarksFolder)request.getAttribute(BookmarksWebKeys.BOOKMARKS_FOLDER);

long folderId = BeanParamUtil.getLong(folder, request, "folderId");

long parentFolderId = BeanParamUtil.getLong(folder, request, "parentFolderId", BookmarksFolderConstants.DEFAULT_PARENT_FOLDER_ID);

boolean mergeWithParentFolderDisabled = ParamUtil.getBoolean(request, "mergeWithParentFolderDisabled");

if (folder != null) {
	BookmarksUtil.addPortletBreadcrumbEntries(folderId, request, renderResponse);

	if (!layout.isTypeControlPanel()) {
		PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, "edit"), currentURL);
	}
}
else {
	if (parentFolderId > 0) {
		BookmarksUtil.addPortletBreadcrumbEntries(parentFolderId, request, renderResponse);

		if (!layout.isTypeControlPanel()) {
			PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, "add-subfolder"), currentURL);
		}
	}
	else if (!layout.isTypeControlPanel()) {
		PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, "add-folder"), currentURL);
	}
}
%>

<c:if test="<%= !windowState.equals(LiferayWindowState.POP_UP) %>">
	<liferay-util:include page="/bookmarks/top_links.jsp" servletContext="<%= application %>" />
</c:if>

<portlet:actionURL name="/bookmarks/edit_folder" var="editFolderURL" />

<aui:form action="<%= editFolderURL %>" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "saveFolder();" %>'>
	<aui:input name="<%= Constants.CMD %>" type="hidden" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="folderId" type="hidden" value="<%= folderId %>" />
	<aui:input name="parentFolderId" type="hidden" value="<%= parentFolderId %>" />

	<liferay-ui:header
		backURL="<%= redirect %>"
		localizeTitle="<%= (folder == null) %>"
		title='<%= (folder == null) ? ((parentFolderId > 0) ? "add-subfolder" : "add-folder") : LanguageUtil.format(request, "edit-x", folder.getName(), false) %>'
	/>

	<liferay-ui:error exception="<%= FolderNameException.class %>">
		<p>
			<liferay-ui:message arguments="<%= new String[] {BookmarksFolderConstants.NAME_LABEL, BookmarksFolderConstants.NAME_GENERAL_RESTRICTIONS, BookmarksFolderConstants.NAME_RESERVED_WORDS} %>" key="the-x-cannot-be-x-or-a-reserved-word-such-as-x" />
		</p>

		<p>
			<liferay-ui:message arguments="<%= new String[] {BookmarksFolderConstants.NAME_LABEL, BookmarksFolderConstants.NAME_INVALID_CHARACTERS} %>" key="the-x-cannot-contain-the-following-invalid-characters-x" />
		</p>
	</liferay-ui:error>

	<aui:model-context bean="<%= folder %>" model="<%= BookmarksFolder.class %>" />

	<aui:fieldset>
		<c:if test="<%= folder != null %>">

			<%
			String parentFolderName = LanguageUtil.get(request, "home");

			try {
				BookmarksFolder parentFolder = BookmarksFolderServiceUtil.getFolder(parentFolderId);

				parentFolderName = parentFolder.getName();
			}
			catch (NoSuchFolderException nsfe) {
			}
			%>

			<div class="form-group">
				<aui:input label="parent-folder" name="parentFolderName" type="resource" value="<%= parentFolderName %>" />

				<aui:button name="selectFolderButton" value="select" />

				<aui:script>
					AUI.$('#<portlet:namespace />selectFolderButton').on(
						'click',
						function(event) {
							Liferay.Util.selectEntity(
								{
									dialog: {
										constrain: true,
										modal: true,
										width: 680
									},
									id: '<portlet:namespace />selectFolder',
									title: '<liferay-ui:message arguments="folder" key="select-x" />',
									uri: '<liferay-portlet:renderURL windowState="<%= LiferayWindowState.POP_UP.toString() %>"><portlet:param name="mvcRenderCommandName" value="/bookmarks/select_folder" /></liferay-portlet:renderURL>'
								},
								function(event) {
									var folderData = {
										idString: 'parentFolderId',
										idValue: event.folderid,
										nameString: 'parentFolderName',
										nameValue: event.name
									};

									Liferay.Util.selectFolder(folderData, '<portlet:namespace />');
								}
							);
						}
					);
				</aui:script>

				<%
				String taglibRemoveFolder = "Liferay.Util.removeEntitySelection('parentFolderId', 'parentFolderName', this, '" + renderResponse.getNamespace() + "');";
				%>

				<aui:button disabled="<%= (parentFolderId <= 0) %>" name="removeFolderButton" onClick="<%= taglibRemoveFolder %>" value="remove" />
			</div>

			<aui:input disabled="<%= mergeWithParentFolderDisabled %>" label="merge-with-parent-folder" name="mergeWithParentFolder" type="checkbox" />
		</c:if>

		<aui:input autoFocus="<%= windowState.equals(WindowState.MAXIMIZED) %>" name="name" />

		<aui:input name="description" />

		<liferay-ui:custom-attributes-available className="<%= BookmarksFolder.class.getName() %>">
			<liferay-ui:custom-attribute-list
				className="<%= BookmarksFolder.class.getName() %>"
				classPK="<%= (folder != null) ? folder.getFolderId() : 0 %>"
				editable="<%= true %>"
				label="<%= true %>"
			/>
		</liferay-ui:custom-attributes-available>

		<c:if test="<%= folder == null %>">
			<aui:field-wrapper label="permissions">
				<liferay-ui:input-permissions
					modelName="<%= BookmarksFolder.class.getName() %>"
				/>
			</aui:field-wrapper>
		</c:if>
	</aui:fieldset>

	<aui:button-row>
		<aui:button type="submit" />

		<aui:button href="<%= redirect %>" type="cancel" />
	</aui:button-row>
</aui:form>

<aui:script>
	function <portlet:namespace />saveFolder() {
		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = '<%= (folder == null) ? Constants.ADD : Constants.UPDATE %>';

		submitForm(document.<portlet:namespace />fm);
	}
</aui:script>