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

String redirect = ParamUtil.getString(request, "redirect");

FileShortcut fileShortcut = (FileShortcut)request.getAttribute(WebKeys.DOCUMENT_LIBRARY_FILE_SHORTCUT);

long fileShortcutId = BeanParamUtil.getLong(fileShortcut, request, "fileShortcutId");

long toGroupId = ParamUtil.getLong(request, "toGroupId", themeDisplay.getScopeGroupId());

Group toGroup = GroupServiceUtil.getGroup(toGroupId);

toGroup = toGroup.toEscapedModel();

long repositoryId = BeanParamUtil.getLong(fileShortcut, request, "repositoryId");
long folderId = BeanParamUtil.getLong(fileShortcut, request, "folderId");

long toFileEntryId = BeanParamUtil.getLong(fileShortcut, request, "toFileEntryId");

FileEntry toFileEntry = null;

if (toFileEntryId > 0) {
	try {
		toFileEntry = DLAppServiceUtil.getFileEntry(toFileEntryId);

		toFileEntry = toFileEntry.toEscapedModel();

		toGroupId = toFileEntry.getRepositoryId();

		toGroup = GroupServiceUtil.getGroup(toGroupId);

		toGroup = toGroup.toEscapedModel();
	}
	catch (Exception e) {
	}
}

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("mvcRenderCommandName", mvcRenderCommandName);
portletURL.setParameter("redirect", redirect);
portletURL.setParameter("fileShortcutId", String.valueOf(fileShortcutId));

String headerTitle = (fileShortcut != null) ? LanguageUtil.format(request, "shortcut-to-x", fileShortcut.getToTitle(), false) : LanguageUtil.get(request, "new-file-shortcut");

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(redirect);

renderResponse.setTitle(headerTitle);
%>

<div class="container-fluid-1280">
	<portlet:actionURL name="/document_library/edit_file_shortcut" var="editFileShortcutURL">
		<portlet:param name="mvcRenderCommandName" value="/document_library/edit_file_shortcut" />
	</portlet:actionURL>

	<aui:form action="<%= editFileShortcutURL %>" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "saveFileShortcut();" %>'>
		<aui:input name="<%= Constants.CMD %>" type="hidden" />
		<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
		<aui:input name="fileShortcutId" type="hidden" value="<%= fileShortcutId %>" />
		<aui:input name="repositoryId" type="hidden" value="<%= repositoryId %>" />
		<aui:input name="folderId" type="hidden" value="<%= folderId %>" />
		<aui:input name="toGroupId" type="hidden" value="<%= toGroupId %>" />
		<aui:input name="toFileEntryId" type="hidden" value="<%= toFileEntryId %>" />

		<liferay-ui:error exception="<%= FileShortcutPermissionException.class %>" message="you-do-not-have-permission-to-create-a-shortcut-to-the-selected-document" />
		<liferay-ui:error exception="<%= NoSuchFileEntryException.class %>" message="the-document-could-not-be-found" />

		<aui:fieldset-group markupView="lexicon">
			<aui:fieldset>
				<div class="alert alert-info">
					<liferay-ui:message key="you-can-create-a-shortcut-to-any-document-that-you-have-read-access-for" />
				</div>

				<%
				String toGroupName = toGroup.getDescriptiveName(locale);
				%>

				<div class="form-group">
					<aui:input label="site-or-repository" name="toGroupName" type="resource" value="<%= toGroupName %>" />

					<aui:button name="selectGroupButton" value="select" />
				</div>

				<%
				String toFileEntryTitle = BeanPropertiesUtil.getString(toFileEntry, "title");
				%>

				<div class="form-group">
					<aui:input label="document" name="toFileEntryTitle" type="resource" value="<%= HtmlUtil.unescape(toFileEntryTitle) %>" />

					<aui:button name="selectToFileEntryButton" value="select" />
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
			<aui:button type="submit" />

			<aui:button href="<%= redirect %>" type="cancel" />
		</aui:button-row>
	</aui:form>
</div>

<script>
	var form = document.<portlet:namespace />fm;

	var selectGroupButton = document.getElementById(
		'<portlet:namespace />selectGroupButton'
	);

	if (selectGroupButton) {
		selectGroupButton.addEventListener('click', function(event) {
			Liferay.Util.selectEntity(
				{
					dialog: {
						constrain: true,
						destroyOnHide: true,
						modal: true
					},
					id: '<portlet:namespace />selectGroup',
					title: '<liferay-ui:message arguments="site" key="select-x" />',

					<portlet:renderURL var="selectGroupURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
						<portlet:param name="mvcPath" value="/document_library/select_group.jsp" />
					</portlet:renderURL>

					uri: '<%= selectGroupURL.toString() %>'
				},
				function(event) {
					var toGroupIdElement = Liferay.Util.getFormElement(
						form,
						'toGroupId'
					);

					if (
						toGroupIdElement &&
						toGroupIdElement.value != event.groupid
					) {
						<portlet:namespace />selectFileEntry('', '');
					}

					Liferay.Util.setFormValues(form, {
						toGroupId: event.groupid,
						toFileEntryId: 0
					});

					var toGroupNameElement = document.getElementById(
						'<portlet:namespace />toGroupName'
					);

					if (toGroupNameElement) {
						toGroupNameElement.value = event.groupdescriptivename;
					}

					Liferay.Util.toggleDisabled(
						'#<portlet:namespace />selectToFileEntryButton',
						false
					);
				}
			);
		});
	}

	var selectToFileEntryButton = document.getElementById(
		'<portlet:namespace />selectToFileEntryButton'
	);

	if (selectToFileEntryButton) {
		selectToFileEntryButton.addEventListener('click', function(event) {
			Liferay.Util.selectEntity(
				{
					dialog: {
						constrain: true,
						destroyOnHide: true,
						modal: true
					},
					id: <portlet:namespace />createSelectFileEntryId(),
					title: '<liferay-ui:message arguments="file" key="select-x" />',

					<portlet:renderURL var="selectFileEntryURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
						<portlet:param name="mvcRenderCommandName" value="/document_library/select_file_entry" />
					</portlet:renderURL>

					uri: <portlet:namespace />createSelectFileEntryURL(
						'<%= selectFileEntryURL.toString() %>'
					)
				},
				function(event) {
					<portlet:namespace />selectFileEntry(
						event.entryid,
						event.entryname
					);
				}
			);
		});
	}

	function <portlet:namespace />createSelectFileEntryId() {
		var selectFileEntryId = '';

		var toGroupIdElement = Liferay.Util.getFormElement(form, 'toGroupId');

		if (toGroupIdElement) {
			selectFileEntryId =
				'<portlet:namespace />selectFileEntry_' + toGroupIdElement.value;
		}

		return selectFileEntryId;
	}

	function <portlet:namespace />createSelectFileEntryURL(url) {
		var toGroupIdElement = Liferay.Util.getFormElement(form, 'toGroupId');

		if (toGroupIdElement) {
			url += '&<portlet:namespace />groupId=' + toGroupIdElement.value;
		}

		var scopeGroupId = <%= themeDisplay.getScopeGroupId() %>;

		if (scopeGroupId != toGroupIdElement.value) {
			url +=
				'&<portlet:namespace />folderId=<%= DLFolderConstants.DEFAULT_PARENT_FOLDER_ID %>';
		}

		var toFileEntryIdElement = Liferay.Util.getFormElement(
			form,
			'toFileEntryId'
		);

		if (toFileEntryIdElement) {
			url +=
				'&<portlet:namespace />fileEntryId=' + toFileEntryIdElement.value;
		}

		return url;
	}

	function <portlet:namespace />saveFileShortcut() {
		Liferay.Util.postForm(form, {
			data: {
				<%= Constants.CMD %>:
					'<%= (fileShortcut == null) ? Constants.ADD : Constants.UPDATE %>'
			}
		});
	}

	function <portlet:namespace />selectFileEntry(fileEntryId, title) {
		var toFileEntryIdElement = Liferay.Util.getFormElement(
			form,
			'toFileEntryId'
		);

		if (toFileEntryIdElement) {
			toFileEntryIdElement.value = fileEntryId;
		}

		var toFileEntryTitleElement = Liferay.Util.getFormElement(
			form,
			'toFileEntryTitle'
		);

		if (toFileEntryTitleElement) {
			toFileEntryTitleElement.value = title;
		}
	}
</script>