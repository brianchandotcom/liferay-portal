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

<%@ include file="/html/portlet/document_library/init.jsp" %>

<%
String cmd = ParamUtil.getString(request, Constants.CMD, Constants.MOVE);

String redirect = ParamUtil.getString(request, "redirect");

Folder folder = (Folder)request.getAttribute(WebKeys.DOCUMENT_LIBRARY_FOLDER);

long folderId = BeanParamUtil.getLong(folder, request, "folderId");

long repositoryId = BeanParamUtil.getLong(folder, request, "repositoryId");
long parentFolderId = BeanParamUtil.getLong(folder, request, "parentFolderId", DLFolderConstants.DEFAULT_PARENT_FOLDER_ID);
%>

<liferay-util:include page="/html/portlet/document_library/top_links.jsp" />

<c:if test="<%= cmd.equals(Constants.MOVE_FROM_TRASH) %>">
	<div class="alert alert-block">
		<liferay-ui:message arguments="<%= folder.getName() %>" key="the-original-folder-does-not-exist-anymore" />
	</div>
</c:if>

<portlet:actionURL var="moveFolderURL">
	<portlet:param name="struts_action" value="/document_library/move_folder" />
</portlet:actionURL>

<aui:form action="<%= moveFolderURL %>" enctype="multipart/form-data" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "saveFolder(false);" %>'>
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= cmd %>" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="folderId" type="hidden" value="<%= folderId %>" />
	<aui:input name="parentFolderId" type="hidden" value="<%= parentFolderId %>" />

	<liferay-ui:header
		backURL="<%= redirect %>"
		title='<%= LanguageUtil.get(pageContext, "move") + StringPool.SPACE + folder.getName() %>'
	/>

	<liferay-ui:error exception="<%= DuplicateFileException.class %>" message="the-folder-you-selected-already-has-an-entry-with-this-name.-please-select-a-different-folder" />
	<liferay-ui:error exception="<%= DuplicateFolderNameException.class %>" message="the-folder-you-selected-already-has-an-entry-with-this-name.-please-select-a-different-folder" />
	<liferay-ui:error exception="<%= NoSuchFolderException.class %>" message="please-enter-a-valid-folder" />

	<aui:model-context bean="<%= folder %>" model="<%= DLFolder.class %>" />

	<aui:fieldset>
		<aui:field-wrapper label="parent-folder">

			<%
			String parentFolderName = "";

			try {
				if (parentFolderId != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
					Folder parentFolder = DLAppLocalServiceUtil.getFolder(parentFolderId);

					parentFolderName = parentFolder.getName();
				}
			}
			catch (NoSuchFolderException nsfe) {
			}
			%>

			<portlet:renderURL var="viewFolderURL">
				<portlet:param name="struts_action" value="/document_library/view" />
				<portlet:param name="folderId" value="<%= String.valueOf(parentFolderId) %>" />
			</portlet:renderURL>

			<aui:a href="<%= viewFolderURL %>" id="parentFolderName"><%= parentFolderName %></aui:a>

			<aui:button name="selectFolderButton" value="select" />

			<%
			String taglibRemoveFolder = "Liferay.Util.removeFolderSelection('parentFolderId', 'parentFolderName', '" + renderResponse.getNamespace() + "');";
			%>

			<aui:button name="removeFolderButton" onClick="<%= taglibRemoveFolder %>" value="remove" />
		</aui:field-wrapper>
		<aui:button-row>
			<aui:button type="submit" value="move" />

			<aui:button href="<%= redirect %>" type="cancel" />
		</aui:button-row>
	</aui:fieldset>
</aui:form>

<aui:script>
	function <portlet:namespace />saveFolder() {
		submitForm(document.<portlet:namespace />fm);
	}
</aui:script>

<portlet:renderURL var="selectFolderURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
	<portlet:param name="struts_action" value="/document_library/select_folder" />
	<portlet:param name="folderId" value="<%= String.valueOf(parentFolderId) %>" />
</portlet:renderURL>

<aui:script use="aui-base">
	A.one('#<portlet:namespace />selectFolderButton').on(
		'click',
		function(event) {
			Liferay.Util.selectEntity(
				{
					dialog: {
						constrain: true,
						modal: true,
						zIndex: Liferay.zIndex.WINDOW + 2,
						width: 680
					},
					id: '<portlet:namespace />selectFolder',
					title: '<%= UnicodeLanguageUtil.format(pageContext, "select-x", "folder") %>',
					uri: '<%= selectFolderURL.toString() %>'
				},
				function(event) {
					var folderData = {
						idString: 'parentFolderId',
						idValue: event.folderid,
						nameString: 'parentFolderName',
						nameValue: event.foldername
					};

					Liferay.Util.selectFolder(folderData, '<portlet:renderURL><portlet:param name="struts_action" value="/document_library/view" /></portlet:renderURL>', '<portlet:namespace />');
				}
			);
		}
	);
</aui:script>

<%
DLUtil.addPortletBreadcrumbEntries(folder, request, renderResponse);

PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(pageContext, "move"), currentURL);
%>