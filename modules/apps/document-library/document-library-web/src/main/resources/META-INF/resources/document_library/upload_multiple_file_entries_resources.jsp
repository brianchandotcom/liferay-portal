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
String redirect = ParamUtil.getString(request, "redirect");

FileEntry fileEntry = (FileEntry)request.getAttribute(WebKeys.DOCUMENT_LIBRARY_FILE_ENTRY);

long groupId = BeanParamUtil.getLong(fileEntry, request, "groupId");

long repositoryId = BeanParamUtil.getLong(fileEntry, request, "repositoryId");

if (repositoryId <= 0) {

	// <liferay-ui:asset_add_button /> only passes in groupId

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

	if (dlFolder.getRestrictionType() == DLFolderConstants.RESTRICTION_TYPE_FILE_ENTRY_TYPES_AND_WORKFLOW) {
		inherited = false;
	}
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

DLEditFileEntryDisplayContext dlEditFileEntryDisplayContext = null;

if (fileEntry == null) {
	dlEditFileEntryDisplayContext = dlDisplayContextProvider.getDLEditFileEntryDisplayContext(request, response, fileEntryType);
}
else {
	dlEditFileEntryDisplayContext = dlDisplayContextProvider.getDLEditFileEntryDisplayContext(request, response, fileEntry);
}
%>

<portlet:actionURL name="/document_library/upload_multiple_file_entries" var="uploadMultipleFileEntriesURL" />

<aui:form action="<%= uploadMultipleFileEntriesURL %>" method="post" name="fm2" onSubmit='<%= "event.preventDefault(); " + liferayPortletResponse.getNamespace() + "updateMultipleFiles();" %>'>
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.ADD_MULTIPLE %>" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="portletResource" type="hidden" value='<%= ParamUtil.getString(request, "portletResource") %>' />
	<aui:input name="repositoryId" type="hidden" value="<%= String.valueOf(repositoryId) %>" />
	<aui:input name="folderId" type="hidden" value="<%= String.valueOf(folderId) %>" />

	<div class="alert alert-info hide no-files-selected-info" id="<portlet:namespace />metadataExplanationContainer">
		<liferay-ui:message key="select-documents-from-the-left-to-add-them-to-the-documents-and-media" />
	</div>

	<aui:model-context bean="<%= fileVersion %>" model="<%= DLFileVersion.class %>" />

	<liferay-ui:panel-container
		extended="<%= false %>"
		id="documentLibraryAssetPanelContainer"
		markupView="lexicon"
		persistState="<%= true %>"
	>
		<div class="selected-files-count">
			<liferay-ui:message key="no-files-selected" />
		</div>

		<c:if test="<%= (folder == null) || folder.isSupportsMetadata() %>">
			<aui:input name="description" />

			<c:if test="<%= !fileEntryTypes.isEmpty() %>">
				<liferay-ui:panel
					collapsible="<%= true %>"
					cssClass="document-type"
					markupView="lexicon"
					persistState="<%= true %>"
					title="document-type"
				>
					<aui:input name="fileEntryTypeId" type="hidden" value="<%= (fileEntryTypeId > 0) ? fileEntryTypeId : 0 %>" />

					<aui:input name="defaultLanguageId" type="hidden" value="<%= LocaleUtil.toLanguageId(LocaleUtil.getSiteDefault()) %>" />

					<div class="document-type-selector" id="<portlet:namespace />documentTypeSelector">
						<liferay-ui:icon-menu
							direction="down"
							id="groupSelector"
							message='<%= (fileEntryTypeId > 0) ? HtmlUtil.escape(fileEntryType.getName(locale)) : "basic-document" %>'
							showWhenSingleIcon="<%= true %>"
						>

							<%
							for (DLFileEntryType curFileEntryType : fileEntryTypes) {
							%>

								<liferay-portlet:resourceURL copyCurrentRenderParameters="<%= false %>" id="/document_library/upload_multiple_file_entries" var="viewFileEntryTypeURL">
									<portlet:param name="redirect" value="<%= redirect %>" />
									<portlet:param name="portletResource" value='<%= ParamUtil.getString(request, "portletResource") %>' />
									<portlet:param name="repositoryId" value="<%= String.valueOf(repositoryId) %>" />
									<portlet:param name="folderId" value="<%= String.valueOf(folderId) %>" />
									<portlet:param name="fileEntryTypeId" value="<%= String.valueOf(curFileEntryType.getFileEntryTypeId()) %>" />
								</liferay-portlet:resourceURL>

								<liferay-ui:icon
									cssClass="upload-multiple-document-types"
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

					<c:if test="<%= fileEntryTypeId > 0 %>">

						<%
						try {
							for (DDMStructure ddmStructure : fileEntryType.getDDMStructures()) {
								DDMFormValues ddmFormValues = null;

								try {
									DLFileEntryMetadata fileEntryMetadata = DLFileEntryMetadataLocalServiceUtil.getFileEntryMetadata(ddmStructure.getStructureId(), fileVersionId);

									ddmFormValues = dlEditFileEntryDisplayContext.getDDMFormValues(fileEntryMetadata.getDDMStorageId());
								}
								catch (Exception e) {
								}

								if (groupId <= 0) {
									groupId = ddmStructure.getGroupId();
								}
						%>

								<div class="document-type-fields" data-ddm-fieldset>
									<liferay-data-engine:data-layout-renderer
										containerId='<%= liferayPortletResponse.getNamespace() + "dataEngineLayoutRenderer" + ddmStructure.getStructureId() %>'
										dataDefinitionId="<%= ddmStructure.getStructureId() %>"
										dataRecordValues="<%= DataRecordValuesUtil.getDataRecordValues(ddmFormValues, ddmStructure) %>"
										namespace="<%= liferayPortletResponse.getNamespace() + ddmStructure.getStructureId() + StringPool.UNDERLINE %>"
										persisted="<%= fileEntry != null %>"
										submittable="<%= false %>"
									/>
								</div>

						<%
							}
						}
						catch (Exception e) {
						}
						%>

					</c:if>

					<aui:script position="inline" require="frontend-js-web/liferay/delegate/delegate.es as delegateModule,frontend-js-web/liferay/util/run_scripts_in_element.es as runScriptsInElement">
						var documentTypeMenuList = document.querySelector(
							'#<portlet:namespace />documentTypeSelector .lfr-menu-list'
						);

						if (documentTypeMenuList) {
							var delegate = delegateModule.default;

							delegate(documentTypeMenuList, 'click', 'li a', (event) => {
								event.preventDefault();

								Liferay.Util.fetch(event.delegateTarget.getAttribute('href'))
									.then((response) => {
										return response.text();
									})
									.then((response) => {
										var commonFileMetadataContainer = document.getElementById(
											'<portlet:namespace />commonFileMetadataContainer'
										);

										if (commonFileMetadataContainer) {
											commonFileMetadataContainer.innerHTML = response;

											runScriptsInElement.default(commonFileMetadataContainer);
										}

										var fileNodes = document.querySelectorAll(
											'input[name=<portlet:namespace />selectUploadedFile]'
										);

										var selectedFileNodes = Array.prototype.filter.call(
											fileNodes,
											(fileNode) => {
												return fileNode.checked;
											}
										);

										var selectedFilesCount = selectedFileNodes.length;

										var selectedFilesText = '';

										if (selectedFilesCount > 0) {
											selectedFilesText = selectedFileNodes[0].dataset.title;
										}

										if (selectedFilesCount > 1) {
											if (selectedFilesCount === fileNodes.length) {
												selectedFilesText =
													'<%= UnicodeLanguageUtil.get(request, "all-files-selected") %>';
											}
											else {
												selectedFilesText = Liferay.Util.sub(
													'<%= UnicodeLanguageUtil.get(request, "x-files-selected") %>',
													selectedFilesCount
												);
											}
										}

										var selectedFilesCountElement = document.querySelector(
											'.selected-files-count'
										);

										if (selectedFilesCountElement) {
											selectedFilesCountElement.innerHTML = selectedFilesText;

											selectedFilesCountElement.setAttribute(
												'title',
												selectedFilesText
											);
										}
									});
							});
						}
					</aui:script>
				</liferay-ui:panel>
			</c:if>

			<liferay-expando:custom-attributes-available
				className="<%= DLFileEntryConstants.getClassName() %>"
			>
				<liferay-expando:custom-attribute-list
					className="<%= DLFileEntryConstants.getClassName() %>"
					classPK="<%= fileVersionId %>"
					editable="<%= true %>"
					label="<%= true %>"
				/>
			</liferay-expando:custom-attributes-available>
		</c:if>

		<liferay-ui:panel
			cssClass="display-page-panel"
			defaultState="closed"
			extended="<%= false %>"
			id="dlFileEntryDisplayPagePanel"
			markupView="lexicon"
			persistState="<%= true %>"
			title="display-page"
		>
			<aui:fieldset>
				<liferay-asset:select-asset-display-page
					classNameId="<%= PortalUtil.getClassNameId(FileEntry.class) %>"
					classPK="<%= 0 %>"
					groupId="<%= scopeGroupId %>"
					showPortletLayouts="<%= true %>"
					showViewInContextLink="<%= true %>"
				/>
			</aui:fieldset>
		</liferay-ui:panel>

		<c:if test="<%= (folder == null) || folder.isSupportsSocial() %>">
			<liferay-ui:panel
				cssClass="categorization-panel"
				defaultState="closed"
				extended="<%= false %>"
				id="dlFileEntryCategorizationPanel"
				markupView="lexicon"
				persistState="<%= true %>"
				title="categorization"
			>
				<aui:fieldset>
					<liferay-asset:asset-categories-selector
						className="<%= DLFileEntry.class.getName() %>"
						classPK="<%= assetClassPK %>"
						classTypePK="<%= fileEntryTypeId %>"
						visibilityTypes="<%= AssetVocabularyConstants.VISIBILITY_TYPES %>"
					/>

					<liferay-asset:asset-tags-selector
						className="<%= DLFileEntry.class.getName() %>"
						classPK="<%= assetClassPK %>"
					/>
				</aui:fieldset>
			</liferay-ui:panel>
		</c:if>

		<liferay-ui:panel
			cssClass="expiration-date-panel"
			defaultState="closed"
			extended="<%= false %>"
			id="dlFileEntryExpirationDatePanel"
			markupView="lexicon"
			persistState="<%= true %>"
			title="expiration-date"
		>
			<aui:fieldset>
				<liferay-ui:error exception="<%= FileEntryExpirationDateException.class %>" message="please-enter-a-valid-expiration-date" />
				<liferay-ui:error exception="<%= FileEntryReviewDateException.class %>" message="please-enter-a-valid-review-date" />

				<p class="text-secondary">
					<liferay-ui:message key="including-an-expiration-date-will-allow-your-documents-or-media-to-expire-automatically-and-become-unpublished" />
				</p>

				<aui:input dateTogglerCheckboxLabel="never-expire" disabled="<%= dlEditFileEntryDisplayContext.isNeverExpire() %>" name="expirationDate" wrapperCssClass="expiration-date" />
				<aui:input dateTogglerCheckboxLabel="never-review" disabled="<%= dlEditFileEntryDisplayContext.isNeverReview() %>" name="reviewDate" wrapperCssClass="review-date" />
			</aui:fieldset>
		</liferay-ui:panel>

		<liferay-ui:panel
			cssClass="mb-3"
			defaultState="closed"
			extended="<%= true %>"
			id="dlFileEntryPermissionsPanel"
			markupView="lexicon"
			persistState="<%= true %>"
			title="permissions"
		>
			<liferay-ui:input-permissions
				modelName="<%= DLFileEntryConstants.getClassName() %>"
			/>
		</liferay-ui:panel>
	</liferay-ui:panel-container>

	<span id="<portlet:namespace />selectedFileNameContainer"></span>

	<aui:button type="submit" value="<%= dlEditFileEntryDisplayContext.getPublishButtonLabel() %>" />
</aui:form>