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

<%@ include file="/html/portlet/document_library/init.jsp" %>

<%
String backURL = ParamUtil.getString(request, "backURL");

FileEntry fileEntry = (FileEntry)request.getAttribute(WebKeys.DOCUMENT_LIBRARY_FILE_ENTRY);

long repositoryId = BeanParamUtil.getLong(fileEntry, request, "repositoryId");

if (repositoryId <= 0) {

	// add_asset.jspf only passes in groupId

	repositoryId = BeanParamUtil.getLong(fileEntry, request, "groupId");
}

long folderId = BeanParamUtil.getLong(fileEntry, request, "folderId");

Folder folder = null;

if (folderId > 0) {
	folder = DLAppLocalServiceUtil.getFolder(folderId);
}

boolean inherited = true;

if ((folder != null) && (folder.getModel() instanceof DLFolder)) {
	DLFolder dlFolder = (DLFolder)folder.getModel();

	inherited = !dlFolder.isOverrideFileEntryTypes();
}

List<DLFileEntryType> fileEntryTypes = DLFileEntryTypeServiceUtil.getFolderFileEntryTypes(PortalUtil.getCurrentAndAncestorSiteGroupIds(scopeGroupId), folderId, inherited);

FileVersion fileVersion = null;

long fileVersionId = 0;

long fileEntryTypeId = ParamUtil.getLong(request, "fileEntryTypeId", -1);

if (fileEntry != null) {
	fileVersion = fileEntry.getLatestFileVersion();

	fileVersionId = fileVersion.getFileVersionId();

	if ((fileEntryTypeId == -1) && (fileVersion.getModel() instanceof DLFileVersion)) {
		DLFileVersion dlFileVersion = (DLFileVersion)fileVersion.getModel();

		fileEntryTypeId = dlFileVersion.getFileEntryTypeId();
	}
}

DLFileEntryType fileEntryType = null;

if ((fileEntryTypeId == -1) && !fileEntryTypes.isEmpty()) {
	fileEntryType = fileEntryTypes.get(0);

	fileEntryTypeId = fileEntryType.getFileEntryTypeId();
}

if (fileEntryTypeId > 0) {
	fileEntryType = DLFileEntryTypeLocalServiceUtil.getFileEntryType(fileEntryTypeId);
}

long assetClassPK = 0;

DLFileEntryActionsDisplayContext dlFileEntryActionsDisplayContext = new DLFileEntryActionsDisplayContext(request, dlPortletInstanceSettings, fileEntry, fileVersion);
%>

<portlet:actionURL var="uploadMultipleFileEntriesURL">
	<portlet:param name="struts_action" value="document_library/upload_multiple_file_entries" />
	<portlet:param name="backURL" value="<%= backURL %>" />
</portlet:actionURL>

<aui:form action="<%= uploadMultipleFileEntriesURL %>" method="post" name="fm2" onSubmit='<%= "event.preventDefault(); " + liferayPortletResponse.getNamespace() + "updateMultipleFiles();" %>'>
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.ADD_MULTIPLE %>" />
	<aui:input name="repositoryId" type="hidden" value="<%= String.valueOf(repositoryId) %>" />
	<aui:input name="folderId" type="hidden" value="<%= String.valueOf(folderId) %>" />

	<div class="no-files-selected-info alert alert-info hide" id="<portlet:namespace />metadataExplanationContainer">
		<liferay-ui:message key="select-documents-from-the-left-to-add-them-to-the-documents-and-media" />
	</div>

	<aui:model-context bean="<%= fileVersion %>" model="<%= DLFileVersion.class %>" />

	<liferay-ui:panel-container extended="<%= false %>" id="documentLibraryAssetPanelContainer" persistState="<%= true %>">
		<div class="selected-files-count">
			<liferay-ui:message key="no-files-selected" />
		</div>

		<c:if test="<%= (folder == null) || folder.isSupportsMetadata() %>">
			<aui:input name="description" />

			<c:if test="<%= !fileEntryTypes.isEmpty() %>">
				<liferay-ui:panel collapsible="<%= true %>" cssClass="document-type" persistState="<%= true %>" title="document-type">
					<aui:input name="fileEntryTypeId" type="hidden" value="<%= (fileEntryTypeId > 0) ? fileEntryTypeId : 0 %>" />
					<aui:input name="defaultLanguageId" type="hidden" value="<%= themeDisplay.getLanguageId() %>" />

					<div class="document-type-selector">

						<liferay-ui:icon-menu direction="down" icon="../aui/file-alt" id="groupSelector" message='<%= (fileEntryTypeId > 0) ? HtmlUtil.escape(fileEntryType.getName(locale)) : "basic-document" %>' showWhenSingleIcon="<%= true %>">

							<%
							for (DLFileEntryType curFileEntryType : fileEntryTypes) {
							%>

								<liferay-portlet:resourceURL copyCurrentRenderParameters="<%= false %>" var="viewFileEntryTypeURL">
									<portlet:param name="struts_action" value="/document_library/upload_multiple_file_entries" />
									<portlet:param name="repositoryId" value="<%= String.valueOf(repositoryId) %>" />
									<portlet:param name="folderId" value="<%= String.valueOf(folderId) %>" />
									<portlet:param name="fileEntryTypeId" value="<%= String.valueOf(curFileEntryType.getFileEntryTypeId()) %>" />
								</liferay-portlet:resourceURL>

								<liferay-ui:icon
									cssClass="upload-multiple-document-types"
									iconCssClass="icon-file-alt"
									id='<%= "fileEntryType_" + String.valueOf(curFileEntryType.getFileEntryTypeId()) %>'
									message="<%= HtmlUtil.escape(curFileEntryType.getName(locale)) %>"
									method="get"
									url="<%= viewFileEntryTypeURL %>"
								/>

							<%
							}
							%>

						</liferay-ui:icon-menu>
					</div>

					<%
					if (fileEntryTypeId > 0) {
						try {
							List<DDMStructure> ddmStructures = fileEntryType.getDDMStructures();

							for (DDMStructure ddmStructure : ddmStructures) {
								Fields fields = null;

								try {
									DLFileEntryMetadata fileEntryMetadata = DLFileEntryMetadataLocalServiceUtil.getFileEntryMetadata(ddmStructure.getStructureId(), fileVersionId);

									fields = StorageEngineUtil.getFields(fileEntryMetadata.getDDMStorageId());
								}
								catch (Exception e) {
								}
					%>

									<div class="document-type-fields">
										<liferay-ddm:html
											classNameId="<%= PortalUtil.getClassNameId(DDMStructure.class) %>"
											classPK="<%= ddmStructure.getPrimaryKey() %>"
											fields="<%= fields %>"
											fieldsNamespace="<%= String.valueOf(ddmStructure.getPrimaryKey()) %>"
											requestedLocale="<%= locale %>"
										/>
									</div>

					<%
							}
						}
						catch (Exception e) {
						}
					}
					%>

					<aui:script use="aui-base">
						var groupSelectorMenu = A.one('#<portlet:namespace />groupSelector').ancestor().one('.lfr-menu-list');

						if (groupSelectorMenu) {
							groupSelectorMenu.delegate(
								'click',
								function(event) {
									event.preventDefault();

									var documentTypeForm = A.one('#<portlet:namespace />fm2');

									documentTypeForm.load(
										event.currentTarget.attr('href'),
										{
											where: 'outer'
										},
										function() {
											var selectedFilesCountContainer = A.one('.selected-files-count');

											var totalFiles = A.all('input[name=<portlet:namespace />selectUploadedFile]');

											var totalFilesCount = totalFiles.size();

											var selectedFiles = totalFiles.filter(':checked');

											var selectedFilesCount = selectedFiles.size();

											var selectedFilesText = selectedFiles.item(0).attr('data-title');

											if (selectedFilesCount > 1) {
												if (selectedFilesCount == totalFilesCount) {
													selectedFilesText = '<%= UnicodeLanguageUtil.get(pageContext, "all-files-selected") %>';
												}
												else {
													selectedFilesText = A.Lang.sub('<%= UnicodeLanguageUtil.get(pageContext, "x-files-selected") %>', [selectedFilesCount]);
												}
											}

											selectedFilesCountContainer.setContent(selectedFilesText);

											selectedFilesCountContainer.attr('title', selectedFilesText);
										}
									);
								},
								'li a'
							);
						}
					</aui:script>
				</liferay-ui:panel>
			</c:if>

			<liferay-ui:custom-attributes-available className="<%= DLFileEntryConstants.getClassName() %>">
				<liferay-ui:custom-attribute-list
					className="<%= DLFileEntryConstants.getClassName() %>"
					classPK="<%= fileVersionId %>"
					editable="<%= true %>"
					label="<%= true %>"
				/>
			</liferay-ui:custom-attributes-available>
		</c:if>

		<c:if test="<%= (folder == null) || folder.isSupportsSocial() %>">
			<liferay-ui:panel cssClass="categorization-panel" defaultState="closed" extended="<%= false %>" id="dlFileEntryCategorizationPanel" persistState="<%= true %>" title="categorization">
				<aui:fieldset>
					<aui:input classPK="<%= assetClassPK %>" classTypePK="<%= fileEntryTypeId %>" model="<%= DLFileEntry.class %>" name="categories" type="assetCategories" />

					<aui:input classPK="<%= assetClassPK %>" model="<%= DLFileEntry.class %>" name="tags" type="assetTags" />
				</aui:fieldset>
			</liferay-ui:panel>
		</c:if>
	</liferay-ui:panel-container>

	<aui:field-wrapper cssClass="upload-multiple-file-permissions" label="permissions">
		<liferay-ui:input-permissions
			modelName="<%= DLFileEntryConstants.getClassName() %>"
		/>
	</aui:field-wrapper>

	<span id="<portlet:namespace />selectedFileNameContainer"></span>

	<aui:button type="submit" value="<%= dlFileEntryActionsDisplayContext.getPublishButtonLabel() %>" />
</aui:form>

<aui:script>
	<c:if test="<%= (folder == null) || folder.isSupportsSocial() %>">
		function <portlet:namespace />getSuggestionsContent() {
			return document.<portlet:namespace />fm2.<portlet:namespace />description.value;
		}
	</c:if>
</aui:script>