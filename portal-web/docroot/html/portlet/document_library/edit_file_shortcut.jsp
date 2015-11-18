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
String strutsAction = ParamUtil.getString(request, "struts_action");

String tabs2 = ParamUtil.getString(request, "tabs2", "version-history");

String redirect = ParamUtil.getString(request, "redirect");

DLFileShortcut fileShortcut = (DLFileShortcut)request.getAttribute(WebKeys.DOCUMENT_LIBRARY_FILE_SHORTCUT);

long fileShortcutId = BeanParamUtil.getLong(fileShortcut, request, "fileShortcutId");

long toGroupId = ParamUtil.getLong(request, "toGroupId");

Group toGroup = null;

long repositoryId = BeanParamUtil.getLong(fileShortcut, request, "repositoryId");
long folderId = BeanParamUtil.getLong(fileShortcut, request, "folderId");

Folder toFolder = null;

long toFileEntryId = BeanParamUtil.getLong(fileShortcut, request, "toFileEntryId");

FileEntry toFileEntry = null;

if (toFileEntryId > 0) {
	try {
		toFileEntry = DLAppLocalServiceUtil.getFileEntry(toFileEntryId);

		toFileEntry = toFileEntry.toEscapedModel();

		toFolder = toFileEntry.getFolder();

		toGroupId = toFolder.getRepositoryId();

		toGroup = GroupLocalServiceUtil.getGroup(toGroupId);

		toGroup = toGroup.toEscapedModel();
	}
	catch (Exception e) {
	}
}

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("struts_action", strutsAction);
portletURL.setParameter("tabs2", tabs2);
portletURL.setParameter("redirect", redirect);
portletURL.setParameter("fileShortcutId", String.valueOf(fileShortcutId));
%>

<liferay-util:include page="/html/portlet/document_library/top_links.jsp" />

<portlet:actionURL var="editFileShortcutURL">
	<portlet:param name="struts_action" value="/document_library/edit_file_shortcut" />
</portlet:actionURL>

<aui:form action="<%= editFileShortcutURL %>" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "saveFileShortcut();" %>'>
	<aui:input name="<%= Constants.CMD %>" type="hidden" />
	<aui:input name="tabs2" type="hidden" value="<%= tabs2 %>" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="fileShortcutId" type="hidden" value="<%= fileShortcutId %>" />
	<aui:input name="repositoryId" type="hidden" value="<%= repositoryId %>" />
	<aui:input name="folderId" type="hidden" value="<%= folderId %>" />
	<aui:input name="toGroupId" type="hidden" value="<%= toGroupId %>" />
	<aui:input name="toFileEntryId" type="hidden" value="<%= toFileEntryId %>" />

	<liferay-ui:header
		backURL="<%= redirect %>"
		title='<%= (fileShortcut != null)? LanguageUtil.format(pageContext, "shortcut-to-x", fileShortcut.getToTitle()) : "new-file-shortcut" %>'
	/>

	<liferay-ui:error exception="<%= FileShortcutPermissionException.class %>" message="you-do-not-have-permission-to-create-a-shortcut-to-the-selected-document" />
	<liferay-ui:error exception="<%= NoSuchFileEntryException.class %>" message="the-document-could-not-be-found" />

	<aui:fieldset>
		<div class="alert alert-info">
			<liferay-ui:message key="you-can-create-a-shortcut-to-any-document-that-you-have-read-access-for" />
		</div>

		<%
		String toGroupName = StringPool.BLANK;

		if (toGroup != null) {
			toGroupName = HtmlUtil.escape(toGroup.getDescriptiveName());
		}
		%>

		<div class="control-group">
			<aui:input label="site" name="toGroupName" type="resource" value="<%= toGroupName %>" />

			<aui:button name="selectGroupButton" value="select" />
		</div>

		<%
		String toFileEntryTitle = BeanPropertiesUtil.getString(toFileEntry, "title");
		%>

		<div class="control-group">
			<aui:input label="document" name="toFileEntryTitle" type="resource" url="<%= toFileEntryTitle %>" />

			<aui:button disabled="<%= (toGroup == null) %>" name="selectToFileEntryButton" value="select" />
		</div>

		<c:if test="<%= fileShortcut == null %>">
			<aui:field-wrapper label="permissions">
				<liferay-ui:input-permissions
					modelName="<%= DLFileShortcut.class.getName() %>"
				/>
			</aui:field-wrapper>
		</c:if>

		<aui:button-row>
			<aui:button type="submit" />

			<aui:button href="<%= redirect %>" type="cancel" />
		</aui:button-row>
	</aui:fieldset>
</aui:form>

<portlet:renderURL var="selectGroupURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
	<portlet:param name="struts_action" value="/document_library/select_group" />
</portlet:renderURL>

<portlet:renderURL var="selectFileEntryURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
	<portlet:param name="struts_action" value="/document_library/select_file_entry" />
</portlet:renderURL>

<aui:script use="aui-base,escape">
	var selectToFileEntryButton = A.one('#<portlet:namespace />selectToFileEntryButton');

	A.one('#<portlet:namespace />selectGroupButton').on(
		'click',
		function(event) {
			Liferay.Util.selectEntity(
				{
					dialog: {
						constrain: true,
						modal: true,
						width: 680
					},
					id: '<portlet:namespace />selectGroup',
					title: '<liferay-ui:message arguments="site" key="select-x" />',
					uri: '<%= selectGroupURL.toString() %>'
				},
				function(event) {
					var A = AUI();

					if (document.<portlet:namespace />fm.<portlet:namespace />toGroupId.value != event.groupid) {
						<portlet:namespace />selectFileEntry("", "");
					}

					document.<portlet:namespace />fm.<portlet:namespace />toGroupId.value = event.groupid;
					document.<portlet:namespace />fm.<portlet:namespace />toFileEntryId.value = 0;

					document.getElementById('<portlet:namespace />toGroupName').value = A.Escape.html(event.groupdescriptivename);

					Liferay.Util.toggleDisabled(selectToFileEntryButton, false);
				}
			);
		}
	);

	selectToFileEntryButton.on(
		'click',
		function(event) {
			Liferay.Util.selectEntity(
				{
					dialog: {
						constrain: true,
						modal: true,
						width: 680
					},
					id: <portlet:namespace />createSelectFileEntryId(),
					title: '<liferay-ui:message arguments="file" key="select-x" />',
					uri: <portlet:namespace />createSelectFileEntryURL('<%= selectFileEntryURL.toString() %>')
				},
				function(event) {
					<portlet:namespace />selectFileEntry(event.entryid, event.entryname);
				}
			);
		}
	);
</aui:script>

<aui:script>
	function <portlet:namespace />createSelectFileEntryId() {
		return '<portlet:namespace />selectFileEntry_' + document.<portlet:namespace />fm.<portlet:namespace />toGroupId.value;
	}

	function <portlet:namespace />createSelectFileEntryURL(url) {
		url += '&<portlet:namespace />groupId=' + document.<portlet:namespace />fm.<portlet:namespace />toGroupId.value;
		url += '&<portlet:namespace />fileEntryId=' + document.<portlet:namespace />fm.<portlet:namespace />toFileEntryId.value;

		return url;
	}

	function <portlet:namespace />saveFileShortcut() {
		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "<%= (fileShortcut == null) ? Constants.ADD : Constants.UPDATE %>";

		submitForm(document.<portlet:namespace />fm);
	}

	function <portlet:namespace />selectFileEntry(fileEntryId, title) {
		document.<portlet:namespace />fm.<portlet:namespace />toFileEntryId.value = fileEntryId;

		document.getElementById('<portlet:namespace />toFileEntryTitle').value = title;
	}
</aui:script>

<%
if (fileShortcut != null) {
	DLUtil.addPortletBreadcrumbEntries(fileShortcut, request, renderResponse);

	PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(pageContext, "edit"), currentURL);
}
else {
	DLUtil.addPortletBreadcrumbEntries(folderId, request, renderResponse);

	PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(pageContext, "add-file-shortcut"), currentURL);
}
%>