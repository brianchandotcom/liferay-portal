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
DLEditFileShortcutDisplayContext dlEditFileShortcutDisplayContext = (DLEditFileShortcutDisplayContext)request.getAttribute(DLWebKeys.DOCUMENT_LIBRARY_EDIT_FILE_SHORTCUT_DISPLAY_CONTEXT);

String redirect = ParamUtil.getString(request, "redirect");

FileShortcut fileShortcut = (FileShortcut)request.getAttribute(WebKeys.DOCUMENT_LIBRARY_FILE_SHORTCUT);

String headerTitle = (fileShortcut != null) ? LanguageUtil.format(request, "shortcut-to-x", fileShortcut.getToTitle(), false) : LanguageUtil.get(request, "new-file-shortcut");

renderResponse.setTitle(headerTitle);
%>

<div class="container-fluid-1280">
	<portlet:actionURL name="/document_library/edit_file_shortcut" var="editFileShortcutURL">
		<portlet:param name="mvcRenderCommandName" value="/document_library/edit_file_shortcut" />
	</portlet:actionURL>

	<aui:form action="<%= editFileShortcutURL %>" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "saveFileShortcut();" %>'>
		<aui:input name="<%= Constants.CMD %>" type="hidden" />
		<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
		<aui:input name="fileShortcutId" type="hidden" value="<%= dlEditFileShortcutDisplayContext.getFileShortcutId() %>" />
		<aui:input name="repositoryId" type="hidden" value="<%= dlEditFileShortcutDisplayContext.getRepositoryId() %>" />
		<aui:input name="folderId" type="hidden" value="<%= dlEditFileShortcutDisplayContext.getFolderId() %>" />
		<aui:input name="toFileEntryId" type="hidden" value="<%= dlEditFileShortcutDisplayContext.getToFileEntryId() %>" />

		<liferay-ui:error exception="<%= FileShortcutPermissionException.class %>" message="you-do-not-have-permission-to-create-a-shortcut-to-the-selected-document" />
		<liferay-ui:error exception="<%= NoSuchFileEntryException.class %>" message="the-document-could-not-be-found" />

		<aui:fieldset-group markupView="lexicon">
			<aui:fieldset>
				<div class="alert alert-info">
					<liferay-ui:message key="you-can-create-a-shortcut-to-any-document-that-you-have-read-access-for" />
				</div>

				<div class="form-group">
					<aui:input label="document" name="toFileEntryTitle" type="resource" value="<%= dlEditFileShortcutDisplayContext.getToFileEntryTitle() %>" />

					<aui:button name="selectToFileEntryButton" value="select" />
				</div>
			</aui:fieldset>

			<c:if test="<%= dlEditFileShortcutDisplayContext.isPermissionConfigurable() %>">
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

<aui:script require="frontend-js-web/liferay/ItemSelectorDialog.es as ItemSelectorDialog">
	var selectToFileEntryButton = document.getElementById(
		'<portlet:namespace />selectToFileEntryButton'
	);

	if (selectToFileEntryButton) {
		var itemSelectorDialog = new ItemSelectorDialog.default({
			buttonAddLabel: '<liferay-ui:message key="done" />',
			eventName: '<portlet:namespace />toFileEntrySelectedItem',
			title: '<liferay-ui:message arguments="file" key="select-x" />',
			url: '<%= dlEditFileShortcutDisplayContext.getItemSelectorURL() %>'
		});

		itemSelectorDialog.on('selectedItemChange', function(event) {
			var selectedItem = event.selectedItem;

			if (selectedItem) {
				var itemValue = JSON.parse(selectedItem.value);

				var toFileEntryId = document.getElementById(
					'<portlet:namespace />toFileEntryId'
				);

				if (toFileEntryId) {
					toFileEntryId.value = itemValue.fileEntryId;
				}

				var toFileEntryTitle = document.getElementById(
					'<portlet:namespace />toFileEntryTitle'
				);

				if (toFileEntryTitle) {
					toFileEntryTitle.value = itemValue.title;
				}
			}
		});

		selectToFileEntryButton.addEventListener('click', function(event) {
			event.preventDefault();
			itemSelectorDialog.open();
		});
	}
</aui:script>

<script>
	var form = document.<portlet:namespace />fm;

	function <portlet:namespace />saveFileShortcut() {
		Liferay.Util.postForm(form, {
			data: {
				<%= Constants.CMD %>:
					'<%= (fileShortcut == null) ? Constants.ADD : Constants.UPDATE %>'
			}
		});
	}
</script>