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

<%@ include file="/document_library/init.jsp" %>

<%
String mvcRenderCommandName = ParamUtil.getString(request, "mvcRenderCommandName");

String tabs2 = ParamUtil.getString(request, "tabs2", "version-history");

String redirect = ParamUtil.getString(request, "redirect");

FileShortcut fileShortcut = (FileShortcut)request.getAttribute(WebKeys.DOCUMENT_LIBRARY_FILE_SHORTCUT);

long fileShortcutId = BeanParamUtil.getLong(fileShortcut, request, "fileShortcutId");

long toGroupId = ParamUtil.getLong(request, "toGroupId");

Group toGroup = null;

long repositoryId = BeanParamUtil.getLong(fileShortcut, request, "repositoryId");
long folderId = BeanParamUtil.getLong(fileShortcut, request, "folderId");

long toFileEntryId = BeanParamUtil.getLong(fileShortcut, request, "toFileEntryId");

FileEntry toFileEntry = null;

if (toFileEntryId > 0) {
	try {
		toFileEntry = DLAppLocalServiceUtil.getFileEntry(toFileEntryId);

		toFileEntry = toFileEntry.toEscapedModel();

		toGroupId = toFileEntry.getRepositoryId();

		toGroup = GroupLocalServiceUtil.getGroup(toGroupId);

		toGroup = toGroup.toEscapedModel();
	}
	catch (Exception e) {
	}
}

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("mvcRenderCommandName", mvcRenderCommandName);
portletURL.setParameter("tabs2", tabs2);
portletURL.setParameter("redirect", redirect);
portletURL.setParameter("fileShortcutId", String.valueOf(fileShortcutId));

String headerTitle = (fileShortcut != null) ? LanguageUtil.format(request, "shortcut-to-x", fileShortcut.getToTitle(), false) : LanguageUtil.get(request, "new-file-shortcut");

boolean portletTitleBasedNavigation = GetterUtil.getBoolean(portletConfig.getInitParameter("portlet-title-based-navigation"));

if (portletTitleBasedNavigation) {
	portletDisplay.setShowBackIcon(true);
	portletDisplay.setURLBack(redirect);

	renderResponse.setTitle(headerTitle);
}
%>

<div <%= portletTitleBasedNavigation ? "class=\"container-fluid-1280\"" : StringPool.BLANK %>>
	<portlet:actionURL name="/document_library/edit_file_shortcut" var="editFileShortcutURL">
		<portlet:param name="mvcRenderCommandName" value="/document_library/edit_file_shortcut" />
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

		<c:if test="<%= !portletTitleBasedNavigation %>">
			<liferay-ui:header
				backURL="<%= redirect %>"
				localizeTitle="<%= false %>"
				title="<%= headerTitle %>"
			/>
		</c:if>

		<liferay-ui:error exception="<%= FileShortcutPermissionException.class %>" message="you-do-not-have-permission-to-create-a-shortcut-to-the-selected-document" />
		<liferay-ui:error exception="<%= NoSuchFileEntryException.class %>" message="the-document-could-not-be-found" />

		<aui:fieldset-group markupView="lexicon">
			<aui:fieldset>
				<div class="alert alert-info">
					<liferay-ui:message key="you-can-create-a-shortcut-to-any-document-that-you-have-read-access-for" />
				</div>

				<%
				String toGroupName = StringPool.BLANK;

				if (toGroup != null) {
					toGroupName = HtmlUtil.escape(toGroup.getDescriptiveName(locale));
				}
				%>

				<div class="form-group">
					<aui:input label="site" name="toGroupName" type="resource" value="<%= toGroupName %>" />

					<aui:button name="selectGroupButton" value="select" />
				</div>

				<%
				String toFileEntryTitle = BeanPropertiesUtil.getString(toFileEntry, "title");
				%>

				<div class="form-group">
					<aui:input label="document" name="toFileEntryTitle" type="resource" value="<%= toFileEntryTitle %>" />

					<aui:button disabled="<%= (toGroup == null) %>" name="selectToFileEntryButton" value="select" />
				</div>
			</aui:fieldset>

			<c:if test="<%= fileShortcut == null %>">
				<aui:fieldset collapsed="<%= true %>" collapsible="<%= true %>" label="permissions">
					<liferay-ui:input-permissions
						modelName="<%= DLFileShortcutConstants.getClassName() %>"
					/>
				</aui:fieldset>
			</c:if>
		</aui:fieldset-group>

		<aui:button-row>
			<aui:button cssClass="btn-lg" type="submit" />

			<aui:button cssClass="btn-lg" href="<%= redirect %>" type="cancel" />
		</aui:button-row>
	</aui:form>
</div>

<aui:script sandbox="<%= true %>">
	$('#<portlet:namespace />selectGroupButton').on(
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

					<portlet:renderURL var="selectGroupURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
						<portlet:param name="mvcPath" value="/document_library/select_group.jsp" />
					</portlet:renderURL>

					uri: '<%= selectGroupURL.toString() %>'
				},
				function(event) {
					if (document.<portlet:namespace />fm.<portlet:namespace />toGroupId.value != event.groupid) {
						<portlet:namespace />selectFileEntry('', '');
					}

					document.<portlet:namespace />fm.<portlet:namespace />toGroupId.value = event.groupid;
					document.<portlet:namespace />fm.<portlet:namespace />toFileEntryId.value = 0;

					document.getElementById('<portlet:namespace />toGroupName').value = _.escape(event.groupdescriptivename);

					Liferay.Util.toggleDisabled('#<portlet:namespace />selectToFileEntryButton', false);
				}
			);
		}
	);

	$('#<portlet:namespace />selectToFileEntryButton').on(
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

					<portlet:renderURL var="selectFileEntryURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
						<portlet:param name="mvcRenderCommandName" value="/document_library/select_file_entry" />
					</portlet:renderURL>

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
		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = '<%= (fileShortcut == null) ? Constants.ADD : Constants.UPDATE %>';

		submitForm(document.<portlet:namespace />fm);
	}

	function <portlet:namespace />selectFileEntry(fileEntryId, title) {
		document.<portlet:namespace />fm.<portlet:namespace />toFileEntryId.value = fileEntryId;

		document.getElementById('<portlet:namespace />toFileEntryTitle').value = title;
	}
</aui:script>

<%
if (fileShortcut != null) {
	DLBreadcrumbUtil.addPortletBreadcrumbEntries(fileShortcut, request, renderResponse);

	PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, "edit"), currentURL);
}
else {
	DLBreadcrumbUtil.addPortletBreadcrumbEntries(folderId, request, renderResponse);

	PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, "add-file-shortcut"), currentURL);
}
%>