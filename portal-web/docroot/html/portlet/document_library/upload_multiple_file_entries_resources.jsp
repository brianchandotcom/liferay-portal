<%--
/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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
FileEntry fileEntry = (FileEntry)request.getAttribute(WebKeys.DOCUMENT_LIBRARY_FILE_ENTRY);

long repositoryId = BeanParamUtil.getLong(fileEntry, request, "repositoryId");

if (repositoryId <= 0) {

	// add_asset.jspf only passes in groupId

	repositoryId = BeanParamUtil.getLong(fileEntry, request, "groupId");
}

List<DLFileEntryType> fileEntryTypes = DLFileEntryTypeServiceUtil.getFileEntryTypes(scopeGroupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS);

long folderId = BeanParamUtil.getLong(fileEntry, request, "folderId");

Folder folder = null;

if (folderId > 0) {
	folder = DLAppLocalServiceUtil.getFolder(folderId);
}

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

if (fileEntryTypeId > 0) {
	fileEntryType = DLFileEntryTypeLocalServiceUtil.getFileEntryType(fileEntryTypeId);
}

long assetClassPK = 0;
%>

<aui:input name="repositoryId" type="hidden" value="<%= String.valueOf(repositoryId) %>" />

<aui:input name="folderId" type="hidden" value="<%= String.valueOf(folderId) %>" />

<aui:model-context bean="<%= fileVersion %>" model="<%= DLFileVersion.class %>" />

<liferay-ui:panel-container extended="<%= false %>" id="documentLibraryAssetPanelContainer" persistState="<%= true %>">
	<div class="selected-files-count">
		<liferay-ui:message key="no-files-selected" />
	</div>

	<c:if test="<%= (folder == null) || folder.isSupportsMetadata() %>">
		<aui:input name="description" />

		<c:if test="<%= fileEntryTypes.size() > 1 %>">
			<liferay-ui:panel cssClass="document-type" collapsible="<%= true %>" id="documentTypePanel" persistState="<%= true %>" title="document-type">
				<aui:input name="fileEntryTypeId" type="hidden" value="<%= (fileEntryTypeId > 0) ? fileEntryTypeId : 0 %>" />

				<div class="document-type-selector">
					<liferay-ui:icon-menu align="left" direction="down" id="groupSelector" icon='<%= themeDisplay.getPathThemeImages() + "/common/copy.png" %>' message='<%= (fileEntryTypeId > 0) ? HtmlUtil.escape(fileEntryType.getName()) : "basic-document" %>'>
						<liferay-portlet:resourceURL copyCurrentRenderParameters="<%= false %>" var="viewBasicFileEntryTypeURL">
							<portlet:param name="struts_action" value="/document_library/edit_file_entry" />
							<portlet:param name="repositoryId" value="<%= String.valueOf(repositoryId) %>" />
							<portlet:param name="folderId" value="<%= String.valueOf(folderId) %>" />
							<portlet:param name="fileEntryTypeId" value="0" />
						</liferay-portlet:resourceURL>

						<liferay-ui:icon
							cssClass="upload-multiple-document-types"
							id='<%= "fileEntryType_0" %>'
							image="copy"
							message="basic-document"
							method="get"
							url="<%= viewBasicFileEntryTypeURL %>"
						/>

						<%
						for (DLFileEntryType curFileEntryType : fileEntryTypes) {
						%>

							<liferay-portlet:resourceURL copyCurrentRenderParameters="<%= false %>" var="viewFileEntryTypeURL">
								<portlet:param name="struts_action" value="/document_library/edit_file_entry" />
								<portlet:param name="repositoryId" value="<%= String.valueOf(repositoryId) %>" />
								<portlet:param name="folderId" value="<%= String.valueOf(folderId) %>" />
								<portlet:param name="fileEntryTypeId" value="<%= String.valueOf(curFileEntryType.getFileEntryTypeId()) %>" />
							</liferay-portlet:resourceURL>

							<liferay-ui:icon
								cssClass="upload-multiple-document-types"
								id='<%= "fileEntryType_" + String.valueOf(curFileEntryType.getFileEntryTypeId()) %>'
								image="copy"
								message="<%= curFileEntryType.getName() %>"
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
									<%= DDMXSDUtil.getHTML(pageContext, ddmStructure.getXsd(), fields, String.valueOf(ddmStructure.getPrimaryKey()), locale) %>
								</div>

				<%
						}
					}
					catch (Exception e) {
					}
				}
				%>

				<aui:script use="aui-base">
					var commonFileMetadataContainer = A.one('#<portlet:namespace />commonFileMetadataContainer');

					var groupSelectorMenu = A.one('#<portlet:namespace />groupSelector ul');

					groupSelectorMenu.delegate(
						'click',
						function(event) {
							event.preventDefault();

							var documentTypePanel = A.one('#documentTypePanel');

							documentTypePanel.load(
								event.currentTarget.attr('href') + ' #documentTypePanel',
								{
									where: 'outer'
								}
							);
						},
						'li a'
					);
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
				<aui:input classPK="<%= assetClassPK %>" model="<%= DLFileEntry.class %>" name="categories" type="assetCategories" />

				<aui:input classPK="<%= assetClassPK %>" model="<%= DLFileEntry.class %>" name="tags" type="assetTags" />
			</aui:fieldset>
		</liferay-ui:panel>
	</c:if>
</liferay-ui:panel-container>

<aui:field-wrapper label="permissions">
	<liferay-ui:input-permissions
		modelName="<%= DLFileEntryConstants.getClassName() %>"
	/>
</aui:field-wrapper>

<span id="<portlet:namespace />selectedFileNameContainer"></span>

<aui:button type="submit" />