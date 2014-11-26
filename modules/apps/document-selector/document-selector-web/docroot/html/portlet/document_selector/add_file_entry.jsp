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

<%@ include file="/html/portlet/document_selector/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

String uploadProgressId = "dlFileEntryUploadProgress";

long repositoryId = scopeGroupId;

long folderId = ParamUtil.getLong(request, "folderId");

if (folderId != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
	try {
		Folder folder = DLAppServiceUtil.getFolder(folderId);

		repositoryId = folder.getRepositoryId();
	}
	catch (NoSuchFolderException nsfe) {
		folderId = DLFolderConstants.DEFAULT_PARENT_FOLDER_ID;
	}
}

long fileEntryTypeId = ParamUtil.getLong(request, "fileEntryTypeId", -1);

DLFileEntryType dlFileEntryType = null;

if (fileEntryTypeId > 0) {
	dlFileEntryType = DLFileEntryTypeLocalServiceUtil.fetchDLFileEntryType(fileEntryTypeId);
}

String[] mimeTypes = DocumentSelectorUtil.getMimeTypes(request);
%>

<liferay-ui:header
	backURL="<%= redirect %>"
	localizeTitle="<%= true %>"
	title='<%= LanguageUtil.get(request, "new-document") %>'
/>

<portlet:actionURL var="editFileEntryURL">
	<portlet:param name="struts_action" value="/document_selector/add_file_entry" />
	<portlet:param name="uploadExceptionRedirect" value="<%= currentURL %>" />
	<portlet:param name="type" value="<%= DocumentSelectorUtil.getType(request) %>" />
</portlet:actionURL>

<aui:form action="<%= editFileEntryURL %>" cssClass="lfr-dynamic-form" enctype="multipart/form-data" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "saveFileEntry();" %>'>
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.ADD %>" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="uploadProgressId" type="hidden" value="<%= uploadProgressId %>" />
	<aui:input name="repositoryId" type="hidden" value="<%= repositoryId %>" />
	<aui:input name="folderId" type="hidden" value="<%= folderId %>" />

	<liferay-ui:error exception="<%= AntivirusScannerException.class %>">

		<%
		AntivirusScannerException ase = (AntivirusScannerException)errorException;
		%>

		<liferay-ui:message key="<%= ase.getMessageKey() %>" />
	</liferay-ui:error>

	<liferay-ui:error exception="<%= DuplicateFileException.class %>" message="please-enter-a-unique-document-name" />
	<liferay-ui:error exception="<%= DuplicateFolderNameException.class %>" message="please-enter-a-unique-document-name" />

	<liferay-ui:error exception="<%= LiferayFileItemException.class %>">
		<liferay-ui:message arguments="<%= TextFormatter.formatStorageSize(LiferayFileItem.THRESHOLD_SIZE, locale) %>" key="please-enter-valid-content-with-valid-content-size-no-larger-than-x" translateArguments="<%= false %>" />
	</liferay-ui:error>

	<liferay-ui:error exception="<%= FileExtensionException.class %>">
		<liferay-ui:message key="document-names-must-end-with-one-of-the-following-extensions" /> <%= StringUtil.merge(PrefsPropsUtil.getStringArray(PropsKeys.DL_FILE_EXTENSIONS, StringPool.COMMA), StringPool.COMMA_AND_SPACE) %>.
	</liferay-ui:error>

	<liferay-ui:error exception="<%= FileMimeTypeException.class %>">
		<liferay-ui:message key="media-files-must-be-one-of-the-following-formats" /> <%= StringUtil.merge(mimeTypes, StringPool.COMMA_AND_SPACE) %>.
	</liferay-ui:error>

	<liferay-ui:error exception="<%= FileNameException.class %>" message="please-enter-a-file-with-a-valid-file-name" />
	<liferay-ui:error exception="<%= NoSuchFolderException.class %>" message="please-enter-a-valid-folder" />

	<liferay-ui:error exception="<%= SourceFileNameException.class %>">
		<liferay-ui:message key="the-source-file-does-not-have-the-same-extension-as-the-original-file" />
	</liferay-ui:error>

	<%
	long fileMaxSize = PrefsPropsUtil.getLong(PropsKeys.DL_FILE_MAX_SIZE);

	if (fileMaxSize == 0) {
		fileMaxSize = PrefsPropsUtil.getLong(PropsKeys.UPLOAD_SERVLET_REQUEST_IMPL_MAX_SIZE);
	}
	%>

	<liferay-ui:error exception="<%= FileSizeException.class %>">
		<liferay-ui:message arguments="<%= TextFormatter.formatStorageSize(fileMaxSize, locale) %>" key="please-enter-a-file-with-a-valid-file-size-no-larger-than-x" translateArguments="<%= false %>" />
	</liferay-ui:error>

	<liferay-ui:asset-categories-error />

	<liferay-ui:asset-tags-error />

	<aui:fieldset>
		<aui:field-wrapper>
			<c:if test="<%= fileMaxSize != 0 %>">
				<div class="alert alert-info">
					<%= LanguageUtil.format(request, "upload-documents-no-larger-than-x", TextFormatter.formatStorageSize(fileMaxSize, locale), false) %>
				</div>
			</c:if>
		</aui:field-wrapper>

		<%
		String folderName = StringPool.BLANK;

		if (folderId > 0) {
			Folder folder = DLAppLocalServiceUtil.getFolder(folderId);

			folder = folder.toEscapedModel();

			folderId = folder.getFolderId();
			folderName = folder.getName();
		}
		else {
			folderName = LanguageUtil.get(request, "home");
		}
		%>

		<div class="form-group">
			<aui:input label="folder" name="folderName" type="resource" value="<%= folderName %>" />
		</div>

		<aui:input autoFocus="<%= windowState.equals(WindowState.MAXIMIZED) || windowState.equals(LiferayWindowState.POP_UP) %>" name="file" onChange='<%= renderResponse.getNamespace() + "validateTitle();" %>' type="file">
			<aui:validator name="acceptFiles">
				'<%= StringUtil.merge(PrefsPropsUtil.getStringArray(PropsKeys.DL_FILE_EXTENSIONS, StringPool.COMMA)) %>'
			</aui:validator>
		</aui:input>

		<aui:input name="title">
			<aui:validator errorMessage="you-must-specify-a-file-or-a-title" name="custom">
				function(val, fieldNode, ruleValue) {
					return ((val != '') || A.one('#<portlet:namespace />file').val() != '');
				}
			</aui:validator>
		</aui:input>

		<aui:input name="description" />

		<aui:input name="fileEntryTypeId" type="hidden" value="<%= fileEntryTypeId %>" />

		<aui:input name="defaultLanguageId" type="hidden" value="<%= themeDisplay.getLanguageId() %>" />

		<%
		if (fileEntryTypeId > 0) {
			try {
				List<DDMStructure> ddmStructures = dlFileEntryType.getDDMStructures();

				for (DDMStructure ddmStructure : ddmStructures) {
					Fields fields = null;

					try {
						DLFileEntryMetadata fileEntryMetadata = DLFileEntryMetadataLocalServiceUtil.getFileEntryMetadata(ddmStructure.getStructureId(), 0);

						fields = StorageEngineUtil.getFields(fileEntryMetadata.getDDMStorageId());
					}
					catch (Exception e) {
					}
		%>

					<liferay-ddm:html
						classNameId="<%= PortalUtil.getClassNameId(DDMStructure.class) %>"
						classPK="<%= ddmStructure.getPrimaryKey() %>"
						fields="<%= fields %>"
						fieldsNamespace="<%= String.valueOf(ddmStructure.getPrimaryKey()) %>"
						requestedLocale="<%= locale %>"
					/>

		<%
				}
			}
			catch (Exception e) {
				_log.error(e, e);
			}
		}
		%>

		<liferay-ui:custom-attributes-available className="<%= DLFileEntryConstants.getClassName() %>">
			<liferay-ui:custom-attribute-list
				className="<%= DLFileEntryConstants.getClassName() %>"
				classPK="<%= 0 %>"
				editable="<%= true %>"
				label="<%= true %>"
			/>
		</liferay-ui:custom-attributes-available>

		<liferay-ui:panel defaultState="closed" extended="<%= false %>" id="dlFileEntryCategorizationPanel" persistState="<%= true %>" title="categorization">
			<aui:fieldset>
				<aui:input classPK="<%= 0 %>" classTypePK="<%= fileEntryTypeId %>" model="<%= DLFileEntry.class %>" name="categories" type="assetCategories" />

				<aui:input classPK="<%= 0 %>" model="<%= DLFileEntry.class %>" name="tags" type="assetTags" />
			</aui:fieldset>
		</liferay-ui:panel>

		<liferay-ui:panel defaultState="closed" extended="<%= false %>" id="dlFileEntryAssetLinksPanel" persistState="<%= true %>" title="related-assets">
			<aui:fieldset>
				<liferay-ui:input-asset-links
					className="<%= DLFileEntry.class.getName() %>"
					classPK="<%= 0 %>"
				/>
			</aui:fieldset>
		</liferay-ui:panel>

		<aui:field-wrapper label="permissions">
			<liferay-ui:input-permissions
				modelName="<%= DLFileEntryConstants.getClassName() %>"
			/>
		</aui:field-wrapper>

		<aui:button-row>
			<aui:button name="publishButton" type="submit" value='<%= DLUtil.hasWorkflowDefinitionLink(themeDisplay.getCompanyId(), scopeGroupId, folderId, fileEntryTypeId) ? "submit-for-publication" : "publish" %>' />

			<aui:button href="<%= redirect %>" type="cancel" />
		</aui:button-row>
	</aui:fieldset>
</aui:form>

<liferay-ui:upload-progress
	id="<%= uploadProgressId %>"
	message="uploading"
	redirect="<%= redirect %>"
/>

<aui:script>
	function <portlet:namespace />getSuggestionsContent() {
		return document.<portlet:namespace />fm.<portlet:namespace />title.value + ' ' + document.<portlet:namespace />fm.<portlet:namespace />description.value;
	}

	function <portlet:namespace />saveFileEntry() {
		<%= HtmlUtil.escape(uploadProgressId) %>.startProgress();

		submitForm(document.<portlet:namespace />fm);
	}

	function <portlet:namespace />validateTitle() {
		Liferay.Form.get('<portlet:namespace />fm').formValidator.validateField('<portlet:namespace />title');
	}
</aui:script>

<%
DLUtil.addPortletBreadcrumbEntries(folderId, request, renderResponse);

PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, "add-file-entry"), currentURL);
%>

<%!
private static Log _log = LogFactoryUtil.getLog("portal-web.docroot.html.portlet.document_selector.add_file_entry_jsp");
%>