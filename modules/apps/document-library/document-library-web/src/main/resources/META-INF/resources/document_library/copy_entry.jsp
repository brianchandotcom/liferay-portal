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
DLCopyEntryDisplayContext dlCopyEntryDisplayContext = new DLCopyEntryDisplayContext(request, liferayPortletResponse, themeDisplay);

dlCopyEntryDisplayContext.setViewAttributes();
%>

<liferay-frontend:edit-form
	action="<%= dlCopyEntryDisplayContext.getActionURL() %>"
	name="fm"
>
	<liferay-frontend:edit-form-body>
		<aui:input name="redirect" type="hidden" value="<%= dlCopyEntryDisplayContext.getRedirect() %>" />
		<aui:input name="fileEntryId" type="hidden" value="<%= dlCopyEntryDisplayContext.getFileEntryId() %>" />
		<aui:input name="fileShortcutId" type="hidden" value="<%= dlCopyEntryDisplayContext.getFileShortcutId() %>" />
		<aui:input name="destinationFolderId" type="hidden" value="-1" />
		<aui:input name="destinationRepositoryId" type="hidden" value="-1" />

		<liferay-ui:error exception="<%= DuplicateFileEntryException.class %>" message="the-folder-you-selected-already-has-an-entry-with-this-name.-please-select-a-different-folder" />

		<liferay-frontend:fieldset>
			<liferay-ui:message key="document-library-copy-folder-help" />

			<br /><br />

			<aui:input label="source-file" name="sourceFileName" type="resource" value="<%= dlCopyEntryDisplayContext.getFileName() %>" />

			<aui:input label="target-folder" name="destinationFolderName" type="resource" value="" />

			<aui:button name="selectFolderButton" value="select" />
		</liferay-frontend:fieldset>

		<aui:script sandbox="<%= true %>">
			var selectFolderButton = document.getElementById(
				'<portlet:namespace />selectFolderButton'
			);

			if (selectFolderButton) {
				selectFolderButton.addEventListener('click', (event) => {
					Liferay.Util.openSelectionModal({
						selectEventName: '<portlet:namespace />folderSelected',
						multiple: false,
						onSelect: function (selectedItem) {
							if (!selectedItem) {
								return;
							}

							var form = document.<portlet:namespace />fm;

							Liferay.Util.setFormValues(form, {
								destinationFolderId: selectedItem.folderid,
								destinationFolderName: selectedItem.foldername,
								destinationRepositoryId: selectedItem.repositoryid,
							});

							Liferay.Util.toggleDisabled(
								'#<portlet:namespace />submitButton',
								false
							);
						},
						title: '<liferay-ui:message arguments="folder" key="select-x" />',
						url:
							'<%= HtmlUtil.escapeJS(dlCopyEntryDisplayContext.getSelectFolderURL()) %>',
					});
				});
			}
		</aui:script>
	</liferay-frontend:edit-form-body>

	<liferay-frontend:edit-form-footer>
		<liferay-frontend:edit-form-buttons
			redirect="<%= dlCopyEntryDisplayContext.getRedirect() %>"
			submitDisabled="<%= true %>"
			submitId="submitButton"
			submitLabel="copy"
		/>
	</liferay-frontend:edit-form-footer>
</liferay-frontend:edit-form>