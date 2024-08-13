<%--
/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
CPDefinitionVirtualSettingDisplayContext cpDefinitionVirtualSettingDisplayContext = (CPDefinitionVirtualSettingDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CPDVirtualSettingFileEntry cpdVirtualSettingFileEntry = cpDefinitionVirtualSettingDisplayContext.getCPDVirtualSettingFileEntry();

String className = CPDefinition.class.getName();
long classPK = cpDefinitionVirtualSettingDisplayContext.getCPDefinitionId();

long cpInstanceId = cpDefinitionVirtualSettingDisplayContext.getCPInstanceId();

if (cpInstanceId > 0) {
	className = CPInstance.class.getName();
	classPK = cpInstanceId;
}

long fileEntryId = 0;
long cpdVirtualSettingFileEntryId = 0;

if (cpdVirtualSettingFileEntry != null) {
	fileEntryId = cpdVirtualSettingFileEntry.getFileEntryId();
	cpdVirtualSettingFileEntryId = cpdVirtualSettingFileEntry.getCPDefinitionVirtualSettingFileEntryId();
}

FileEntry fileEntry = cpDefinitionVirtualSettingDisplayContext.getFileEntry(fileEntryId);
%>

<portlet:actionURL name="/cp_definitions/edit_cpd_virtual_setting_file_entry" var="editCPDVirtualSettingFileEntryActionURL" />

<liferay-frontend:side-panel-content
	title='<%= LanguageUtil.get(request, "insert-the-url-or-select-a-file-of-your-virtual-product") %>'
>
	<aui:form action="<%= editCPDVirtualSettingFileEntryActionURL %>" method="post" name="fm">
		<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= (cpdVirtualSettingFileEntry == null) ? Constants.ADD : Constants.UPDATE %>" />
		<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
		<aui:input name="className" type="hidden" value="<%= className %>" />
		<aui:input name="classPK" type="hidden" value="<%= classPK %>" />
		<aui:input name="cpdVirtualSettingFileEntryId" type="hidden" value="<%= cpdVirtualSettingFileEntryId %>" />
		<aui:input name="fileEntryId" type="hidden" value="<%= fileEntryId %>" />

		<aui:model-context bean="<%= cpdVirtualSettingFileEntry %>" model="<%= CPDVirtualSettingFileEntry.class %>" />

		<commerce-ui:panel
			title='<%= LanguageUtil.get(request, "details") %>'
		>
			<aui:button name="selectFile" value="select" />

			<p class="text-default">
				<span class="<%= (fileEntry != null) ? StringPool.BLANK : "hide" %>" id="<portlet:namespace />fileEntryRemove" role="button">
					<clay:button
						aria-label='<%= LanguageUtil.format(locale, "remove-x", "file") %>'
						cssClass="lfr-portal-tooltip"
						displayType="unstyled"
						icon="times"
						title="remove"
					/>
				</span>
				<span id="<portlet:namespace />fileEntryNameInput">
					<c:choose>
						<c:when test="<%= fileEntry != null %>">
							<a href="<%= cpDefinitionVirtualSettingDisplayContext.getDownloadFileEntryURL(fileEntry.getFileEntryId()) %>">
								<%= HtmlUtil.escape(fileEntry.getFileName()) %>
							</a>
						</c:when>
						<c:otherwise>
							<span class="text-muted"><liferay-ui:message key="none" /></span>
						</c:otherwise>
					</c:choose>
				</span>
			</p>

			<aui:input name="url" />
			<aui:input name="version" />
		</commerce-ui:panel>

		<aui:button-row>
			<aui:button cssClass="btn-lg" type="submit" value="save" />

			<aui:button cssClass="btn-lg" type="cancel" />
		</aui:button-row>
	</aui:form>
</liferay-frontend:side-panel-content>

<aui:script sandbox="<%= true %>">
	const fileEntryNameInput = document.getElementById(
		'<portlet:namespace />fileEntryNameInput'
	);

	const fileEntryRemove = document.getElementById(
		'<portlet:namespace />fileEntryRemove'
	);

	const selectFile = document.getElementById('<portlet:namespace />selectFile');

	if (fileEntryNameInput && fileEntryRemove && selectFile) {
		selectFile.addEventListener('click', (event) => {
			event.preventDefault();

			Liferay.Util.openSelectionModal({
				onSelect: (selectedItem) => {
					if (!selectedItem) {
						return;
					}

					const value = JSON.parse(selectedItem.value);

					const fileEntryIdInput = document.getElementById(
						'<portlet:namespace />fileEntryId'
					);

					if (fileEntryIdInput) {
						fileEntryIdInput.value = value.fileEntryId;
					}

					const url = document.getElementById('<portlet:namespace />url');

					if (url) {
						url.setAttribute('disabled', true);
					}

					const message = document.getElementById(
						'lfr-definition-virtual-button-row-message'
					);

					if (message) {
						message.classList.add('hide');
					}

					fileEntryRemove.classList.remove('hide');

					fileEntryNameInput.innerHTML =
						'<a>' + Liferay.Util.escape(value.title) + '</a>';
				},
				selectEventName: 'uploadCPDefinitionVirtualSetting',
				title: '<liferay-ui:message key="select-file" />',
				url: '<%= cpDefinitionVirtualSettingDisplayContext.getFileEntryItemSelectorURL() %>',
			});
		});

		fileEntryRemove.addEventListener('click', (event) => {
			event.preventDefault();

			const fileEntryIdInput = document.getElementById(
				'<portlet:namespace />fileEntryId'
			);

			if (fileEntryIdInput) {
				fileEntryIdInput.value = 0;
			}

			const url = document.getElementById('<portlet:namespace />url');

			if (url) {
				url.removeAttribute('disabled');
			}

			const message = document.getElementById(
				'lfr-definition-virtual-button-row-message'
			);

			if (message) {
				message.classList.remove('hide');
			}

			fileEntryNameInput.innerText = '<liferay-ui:message key="none" />';

			fileEntryRemove.classList.add('hide');
		});
	}
</aui:script>