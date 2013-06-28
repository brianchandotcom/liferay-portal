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
String cmd = ParamUtil.getString(request, Constants.CMD);

String redirect = ParamUtil.getString(request, "redirect");

String referringPortletResource = ParamUtil.getString(request, "referringPortletResource");

DLFileShortcut fileShortcut = (DLFileShortcut)request.getAttribute(WebKeys.DOCUMENT_LIBRARY_FILE_SHORTCUT);

long fileShortcutId = BeanParamUtil.getLong(fileShortcut, request, "fileShortcutId");

long folderId = BeanParamUtil.getLong(fileShortcut, request, "folderId");
%>

<c:if test="<%= Validator.isNull(referringPortletResource) %>">
	<liferay-util:include page="/html/portlet/document_library/top_links.jsp" />
</c:if>

<c:if test="<%= cmd.equals(Constants.MOVE_FROM_TRASH) %>">
	<div class="alert alert-block">
		<liferay-ui:message arguments='<%= fileShortcut.getToTitle() + " (" + LanguageUtil.get(pageContext, "shortcut") + ")" %>' key="the-original-folder-does-not-exist-anymore" />
	</div>
</c:if>

<portlet:actionURL var="moveFileShortcutURL">
	<portlet:param name="struts_action" value="/document_library/move_file_shortcut" />
</portlet:actionURL>

<aui:form action="<%= moveFileShortcutURL %>" enctype="multipart/form-data" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "saveFileShortcut(false);" %>'>
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= cmd.equals(Constants.MOVE_FROM_TRASH) ? Constants.MOVE_FROM_TRASH : Constants.MOVE %>" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="fileShortcutId" type="hidden" value="<%= fileShortcutId %>" />
	<aui:input name="newFolderId" type="hidden" value="<%= folderId %>" />

	<liferay-ui:header
		backURL="<%= redirect %>"
		title='<%= LanguageUtil.get(pageContext, "move") + StringPool.SPACE + fileShortcut.getToTitle() + " (" + LanguageUtil.get(pageContext, "shortcut") + ")" %>'
	/>

	<liferay-ui:error exception="<%= NoSuchFolderException.class %>" message="please-enter-a-valid-folder" />

	<aui:model-context bean="<%= fileShortcut %>" model="<%= DLFileShortcut.class %>" />

	<aui:fieldset>

		<%
		String folderName = null;

		if (folderId > 0) {
			Folder folder = DLAppLocalServiceUtil.getFolder(folderId);

			folder = folder.toEscapedModel();

			folderId = folder.getFolderId();
			folderName = folder.getName();
		}
		else {
			folderName = LanguageUtil.get(pageContext, "home");
		}
		%>

		<portlet:renderURL var="viewFolderURL">
			<portlet:param name="struts_action" value="/document_library/view" />
			<portlet:param name="folderId" value="<%= String.valueOf(folderId) %>" />
		</portlet:renderURL>

		<c:if test="<%= !cmd.equals(Constants.MOVE_FROM_TRASH) %>">
			<aui:field-wrapper label="current-folder">
				<liferay-ui:icon
					image="folder"
					label="true"
					message="<%= folderName %>"
					url="<%= viewFolderURL %>"
				/>
			</aui:field-wrapper>
		</c:if>

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
	<portlet:param name="struts_action" value="/document_library/select_folder" />
	<portlet:param name="folderId" value="<%= String.valueOf(folderId) %>" />
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
						idString: 'newFolderId',
						idValue: event.folderid,
						nameString: 'folderName',
						nameValue: event.foldername
					};

					Liferay.Util.selectFolder(folderData, '<portlet:renderURL><portlet:param name="struts_action" value="/document_library/view" /></portlet:renderURL>', '<portlet:namespace />');
				}
			);
		}
	);
</aui:script>

<aui:script>
	function <portlet:namespace />saveFileShortcut() {
		submitForm(document.<portlet:namespace />fm);
	}
</aui:script>

<%
DLUtil.addPortletBreadcrumbEntries(fileShortcut, request, renderResponse);

PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(pageContext, "move"), currentURL);
%>