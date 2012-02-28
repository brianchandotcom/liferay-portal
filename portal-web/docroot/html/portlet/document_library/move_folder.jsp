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

<%@ include file="/html/portlet/document_library/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

Folder folder = (Folder)request.getAttribute(WebKeys.DOCUMENT_LIBRARY_FOLDER);

long folderId = BeanParamUtil.getLong(folder, request, "folderId");

long repositoryId = BeanParamUtil.getLong(folder, request, "repositoryId");
long parentFolderId = BeanParamUtil.getLong(folder, request, "parentFolderId", DLFolderConstants.DEFAULT_PARENT_FOLDER_ID);
%>

<liferay-util:include page="/html/portlet/document_library/top_links.jsp" />

<portlet:actionURL var="moveFolderURL">
	<portlet:param name="struts_action" value="/document_library/move_folder" />
</portlet:actionURL>

<aui:form action="<%= moveFolderURL %>" enctype="multipart/form-data" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "saveFolder(false);" %>'>
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.MOVE %>" />
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

			<portlet:renderURL windowState="<%= LiferayWindowState.POP_UP.toString() %>" var="selectFolderURL">
				<portlet:param name="struts_action" value="/document_library/select_folder" />
				<portlet:param name="folderId" value="<%= String.valueOf(parentFolderId) %>" />
			</portlet:renderURL>

			<%
			String taglibOpenFolderWindow = "var folderWindow = window.open('" + selectFolderURL + "','folder', 'directories=no,height=640,location=no,menubar=no,resizable=yes,scrollbars=yes,status=no,toolbar=no,width=680'); void(''); folderWindow.focus();";
			%>

			<aui:button onClick='<%= taglibOpenFolderWindow %>' value="select" />

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

	function <portlet:namespace />selectFolder(parentFolderId, parentFolderName) {
		var folderData = {
			idString: 'parentFolderId',
			idValue: parentFolderId,
			nameString: 'parentFolderName',
			nameValue: parentFolderName
		};

		Liferay.Util.selectFolder(folderData, '<portlet:renderURL><portlet:param name="struts_action" value="/document_library/view" /></portlet:renderURL>', '<portlet:namespace />');
	}

	<c:if test="<%= windowState.equals(WindowState.MAXIMIZED) %>">
		Liferay.Util.focusFormField(document.<portlet:namespace />fm.<portlet:namespace />file);
	</c:if>
</aui:script>

<%
DLUtil.addPortletBreadcrumbEntries(folder, request, renderResponse);

PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(pageContext, "move"), currentURL);
%>