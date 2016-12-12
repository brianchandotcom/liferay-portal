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
String cmd = ParamUtil.getString(request, Constants.CMD, Constants.EDIT);

String redirect = ParamUtil.getString(request, "redirect");

String uploadProgressId = "dlFileEntryUploadProgress";

FileEntry fileEntry = (FileEntry)request.getAttribute(WebKeys.DOCUMENT_LIBRARY_FILE_ENTRY);

long fileEntryId = BeanParamUtil.getLong(fileEntry, request, "fileEntryId");

long repositoryId = BeanParamUtil.getLong(fileEntry, request, "repositoryId");

if (repositoryId <= 0) {

	// <liferay-ui:asset_add_button /> only passes in groupId

	repositoryId = BeanParamUtil.getLong(fileEntry, request, "groupId");
}

long folderId = BeanParamUtil.getLong(fileEntry, request, "folderId");

Folder folder = null;

if (fileEntry != null) {
	folder = fileEntry.getFolder();
}

if ((folder == null) && (folderId != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID)) {
	try {
		folder = DLAppServiceUtil.getFolder(folderId);
	}
	catch (NoSuchFolderException nsfe) {
		folderId = DLFolderConstants.DEFAULT_PARENT_FOLDER_ID;
	}
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

DLFileEntryType dlFileEntryType = null;

if (fileEntryTypeId >= 0) {
	dlFileEntryType = DLFileEntryTypeLocalServiceUtil.getFileEntryType(fileEntryTypeId);
}

boolean majorVersion = ParamUtil.getBoolean(request, "majorVersion");
boolean updateVersionDetails = ParamUtil.getBoolean(request, "updateVersionDetails");

long assetClassPK = 0;

if ((fileVersion != null) && !fileVersion.isApproved() && Validator.isNotNull(fileVersion.getVersion()) && !fileVersion.getVersion().equals(DLFileEntryConstants.VERSION_DEFAULT)) {
	assetClassPK = fileVersion.getFileVersionId();
}
else if (fileEntry != null) {
	assetClassPK = fileEntry.getFileEntryId();
}

boolean approved = false;
boolean checkedOut = false;
boolean hasLock = false;
boolean pending = false;

com.liferay.portal.kernel.lock.Lock lock = null;

if (fileEntry != null) {
	approved = fileVersion.isApproved();
	checkedOut = fileEntry.isCheckedOut();
	hasLock = fileEntry.hasLock();
	lock = fileEntry.getLock();
	pending = fileVersion.isPending();
}

boolean saveAsDraft = false;

if ((checkedOut || pending) && !dlPortletInstanceSettings.isEnableFileEntryDrafts()) {
	saveAsDraft = true;
}

DLEditFileEntryDisplayContext dlEditFileEntryDisplayContext = null;

if (fileEntry == null) {
	dlEditFileEntryDisplayContext = dlDisplayContextProvider.getDLEditFileEntryDisplayContext(request, response, dlFileEntryType);
}
else {
	dlEditFileEntryDisplayContext = dlDisplayContextProvider.getDLEditFileEntryDisplayContext(request, response, fileEntry);
}

String headerTitle = LanguageUtil.get(request, "new-document");

if (fileVersion != null) {
	headerTitle = fileVersion.getTitle();
}
else if ((dlFileEntryType != null) && (fileEntryTypeId != 0)) {
	headerTitle = LanguageUtil.format(request, "new-x", dlFileEntryType.getName(locale), false);
}

boolean portletTitleBasedNavigation = GetterUtil.getBoolean(portletConfig.getInitParameter("portlet-title-based-navigation"));

if (portletTitleBasedNavigation) {
	portletDisplay.setShowBackIcon(true);
	portletDisplay.setURLBack(redirect);

	renderResponse.setTitle(headerTitle);
}
%>

<c:if test="<%= portletTitleBasedNavigation && (fileVersion != null) %>">

	<%
	String version = null;

	if (dlEditFileEntryDisplayContext.isVersionInfoVisible()) {
		version = fileVersion.getVersion();
	}
	%>

	<liferay-frontend:info-bar>
		<aui:workflow-status markupView="lexicon" model="<%= DLFileEntry.class %>" showHelpMessage="<%= false %>" showIcon="<%= false %>" showLabel="<%= false %>" status="<%= fileVersion.getStatus() %>" version="<%= version %>" />
	</liferay-frontend:info-bar>
</c:if>

<div <%= portletTitleBasedNavigation ? "class=\"container-fluid-1280\"" : StringPool.BLANK %>>
	<c:if test="<%= checkedOut %>">
		<c:choose>
			<c:when test="<%= hasLock %>">
				<div class="alert alert-success">
					<c:choose>
						<c:when test="<%= lock.isNeverExpires() %>">
							<liferay-ui:message key="you-now-have-an-indefinite-lock-on-this-document" />
						</c:when>
						<c:otherwise>

							<%
							String lockExpirationTime = StringUtil.toLowerCase(LanguageUtil.getTimeDescription(request, DLFileEntryConstants.LOCK_EXPIRATION_TIME));
							%>

							<liferay-ui:message arguments="<%= StringUtil.toLowerCase(LanguageUtil.getTimeDescription(request, DLFileEntryConstants.LOCK_EXPIRATION_TIME)) %>" key="you-now-have-a-lock-on-this-document" translateArguments="<%= false %>" />
						</c:otherwise>
					</c:choose>
				</div>
			</c:when>
			<c:otherwise>
				<div class="alert alert-danger">
					<liferay-ui:message arguments="<%= new Object[] {HtmlUtil.escape(PortalUtil.getUserName(lock.getUserId(), String.valueOf(lock.getUserId()))), dateFormatDateTime.format(lock.getCreateDate())} %>" key="you-cannot-modify-this-document-because-it-was-checked-out-by-x-on-x" translateArguments="<%= false %>" />
				</div>
			</c:otherwise>
		</c:choose>
	</c:if>

	<c:if test="<%= !portletTitleBasedNavigation && showHeader %>">
		<liferay-ui:header
			backURL="<%= redirect %>"
			localizeTitle="<%= false %>"
			title="<%= headerTitle %>"
		/>
	</c:if>

	<liferay-portlet:actionURL name="/document_library/edit_file_entry" varImpl="editFileEntryURL">
		<liferay-portlet:param name="mvcRenderCommandName" value="/document_library/edit_file_entry" />
	</liferay-portlet:actionURL>

	<aui:form action="<%= editFileEntryURL %>" cssClass="lfr-dynamic-form" enctype="multipart/form-data" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "saveFileEntry(" + saveAsDraft + ");" %>'>
		<aui:input name="<%= Constants.CMD %>" type="hidden" />
		<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
		<aui:input name="uploadProgressId" type="hidden" value="<%= uploadProgressId %>" />
		<aui:input name="repositoryId" type="hidden" value="<%= repositoryId %>" />
		<aui:input name="folderId" type="hidden" value="<%= folderId %>" />
		<aui:input name="fileEntryId" type="hidden" value="<%= fileEntryId %>" />

		<c:if test="<%= (fileEntry != null) && checkedOut %>">
			<aui:input name="majorVersion" type="hidden" />
			<aui:input name="changeLog" type="hidden" />
		</c:if>

		<aui:input name="workflowAction" type="hidden" value="<%= String.valueOf(WorkflowConstants.ACTION_PUBLISH) %>" />

		<div class="lfr-form-content">
			<liferay-ui:error exception="<%= AntivirusScannerException.class %>">

				<%
				AntivirusScannerException ase = (AntivirusScannerException)errorException;
				%>

				<liferay-ui:message key="<%= ase.getMessageKey() %>" />
			</liferay-ui:error>

			<liferay-ui:error exception="<%= DuplicateFileEntryException.class %>" message="please-enter-a-unique-document-name" />
			<liferay-ui:error exception="<%= DuplicateFolderNameException.class %>" message="please-enter-a-unique-document-name" />

			<liferay-ui:error exception="<%= LiferayFileItemException.class %>">
				<liferay-ui:message arguments="<%= TextFormatter.formatStorageSize(LiferayFileItem.THRESHOLD_SIZE, locale) %>" key="please-enter-valid-content-with-valid-content-size-no-larger-than-x" translateArguments="<%= false %>" />
			</liferay-ui:error>

			<liferay-ui:error exception="<%= FileExtensionException.class %>">
				<liferay-ui:message key="document-names-must-end-with-one-of-the-following-extensions" /> <%= StringUtil.merge(PrefsPropsUtil.getStringArray(PropsKeys.DL_FILE_EXTENSIONS, StringPool.COMMA), StringPool.COMMA_AND_SPACE) %>.
			</liferay-ui:error>

			<liferay-ui:error exception="<%= FileMimeTypeException.class %>">
				<liferay-ui:message key="media-files-must-be-one-of-the-following-formats" /> <%= StringUtil.merge(dlPortletInstanceSettings.getMimeTypes(), StringPool.COMMA_AND_SPACE) %>.
			</liferay-ui:error>

			<liferay-ui:error exception="<%= FileNameException.class %>" message="please-enter-a-file-with-a-valid-file-name" />
			<liferay-ui:error exception="<%= NoSuchFolderException.class %>" message="please-enter-a-valid-folder" />

			<liferay-ui:error exception="<%= SourceFileNameException.class %>">
				<liferay-ui:message key="the-source-file-does-not-have-the-same-extension-as-the-original-file" />
			</liferay-ui:error>

			<%
			long fileMaxSize = dlEditFileEntryDisplayContext.getMaximumUploadSize();
			%>

			<liferay-ui:error exception="<%= FileSizeException.class %>">
				<liferay-ui:message arguments="<%= TextFormatter.formatStorageSize(fileMaxSize, locale) %>" key="please-enter-a-file-with-a-valid-file-size-no-larger-than-x" translateArguments="<%= false %>" />
			</liferay-ui:error>

			<liferay-ui:error exception="<%= UploadRequestSizeException.class %>">
				<liferay-ui:message arguments="<%= TextFormatter.formatStorageSize(dlEditFileEntryDisplayContext.getMaximumUploadRequestSize(), locale) %>" key="request-is-larger-than-x-and-could-not-be-processed" translateArguments="<%= false %>" />
			</liferay-ui:error>

			<liferay-ui:asset-categories-error />

			<liferay-ui:asset-tags-error />

			<aui:model-context bean="<%= fileVersion %>" model="<%= DLFileVersion.class %>" />

			<aui:fieldset-group markupView="lexicon">
				<aui:fieldset>
					<c:if test="<%= fileMaxSize != 0 %>">
						<div class="alert alert-info">
							<liferay-ui:message arguments="<%= TextFormatter.formatStorageSize(fileMaxSize, locale) %>" key="upload-documents-no-larger-than-x" translateArguments="<%= false %>" />
						</div>
					</c:if>

					<%
					String folderName = StringPool.BLANK;

					if (folderId > 0) {
						folder = DLAppLocalServiceUtil.getFolder(folderId);

						folder = folder.toEscapedModel();

						folderId = folder.getFolderId();
						folderName = folder.getName();
					}
					else {
						folderName = LanguageUtil.get(request, "home");
					}
					%>

					<div class="form-group">
						<c:if test="<%= dlEditFileEntryDisplayContext.isFolderSelectionVisible() %>">
							<aui:input label="folder" name="folderName" type="resource" value="<%= folderName %>" />

							<aui:button name="selectFolderButton" value="select" />

							<%
							String taglibRemoveFolder = "Liferay.Util.removeEntitySelection('folderId', 'folderName', this, '" + renderResponse.getNamespace() + "');";
							%>

							<aui:button disabled="<%= folderId <= 0 %>" name="removeFolderButton" onClick="<%= taglibRemoveFolder %>" value="remove" />

							<aui:script>
								AUI.$('#<portlet:namespace />selectFolderButton').on(
									'click',
									function(event) {
										Liferay.Util.selectEntity(
											{
												dialog: {
													constrain: true,
													destroyOnHide: true,
													modal: true,
													width: 680
												},
												id: '<portlet:namespace />selectFolder',
												title: '<liferay-ui:message arguments="folder" key="select-x" />',

												<liferay-portlet:renderURL var="selectFolderURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
													<portlet:param name="mvcRenderCommandName" value='<%= "/document_library/select_folder" %>' />
												</liferay-portlet:renderURL>

												uri: '<%= selectFolderURL.toString() %>'
											},
											function(event) {
												var folderData = {
													idString: 'folderId',
													idValue: event.folderid,
													nameString: 'folderName',
													nameValue: event.foldername
												};

												Liferay.Util.selectFolder(folderData, '<portlet:namespace />');
											}
										);
									}
								);
							</aui:script>
						</c:if>
					</div>

					<%@ include file="/document_library/edit_file_entry_picker.jspf" %>

					<aui:input name="title" />

					<c:if test="<%= (folder == null) || folder.isSupportsMetadata() %>">
						<aui:input name="description" />

						<c:if test="<%= (folder == null) || (folder.getModel() instanceof DLFolder) %>">

							<%
							boolean inherited = false;

							if (folder != null) {
								DLFolder dlFolder = (DLFolder)folder.getModel();

								if (dlFolder.getRestrictionType() == DLFolderConstants.RESTRICTION_TYPE_INHERIT) {
									inherited = true;
								}
							}

							List<DLFileEntryType> dlFileEntryTypes = DLFileEntryTypeLocalServiceUtil.getFolderFileEntryTypes(PortalUtil.getCurrentAndAncestorSiteGroupIds(scopeGroupId), folderId, inherited);
							%>

							<c:choose>
								<c:when test="<%= !cmd.equals(Constants.ADD) && (dlFileEntryTypes.size() > 1) %>">
									<aui:select changesContext="<%= true %>" label="document-type" name="fileEntryTypeId" onChange='<%= renderResponse.getNamespace() + "changeFileEntryType();" %>'>

										<%
										for (DLFileEntryType curDLFileEntryType : dlFileEntryTypes) {
											if ((curDLFileEntryType.getFileEntryTypeId() == DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_BASIC_DOCUMENT) || (fileEntryTypeId == curDLFileEntryType.getFileEntryTypeId()) || DLFileEntryTypePermission.contains(permissionChecker, curDLFileEntryType, ActionKeys.VIEW)) {
										%>

											<aui:option label="<%= HtmlUtil.escape(curDLFileEntryType.getName(locale)) %>" selected="<%= (fileEntryTypeId == curDLFileEntryType.getPrimaryKey()) %>" value="<%= curDLFileEntryType.getPrimaryKey() %>" />

										<%
											}
										}
										%>

									</aui:select>
								</c:when>
								<c:otherwise>
									<aui:input name="fileEntryTypeId" type="hidden" value="<%= fileEntryTypeId %>" />
								</c:otherwise>
							</c:choose>

							<aui:input name="defaultLanguageId" type="hidden" value="<%= themeDisplay.getLanguageId() %>" />

							<%
							if (fileEntryTypeId > 0) {
								try {
									List<DDMStructure> ddmStructures = dlFileEntryType.getDDMStructures();

									for (DDMStructure ddmStructure : ddmStructures) {
										com.liferay.dynamic.data.mapping.storage.DDMFormValues ddmFormValues = null;

										try {
											DLFileEntryMetadata fileEntryMetadata = DLFileEntryMetadataLocalServiceUtil.getFileEntryMetadata(ddmStructure.getStructureId(), fileVersionId);

											ddmFormValues = dlEditFileEntryDisplayContext.getDDMFormValues(fileEntryMetadata.getDDMStorageId());
										}
										catch (Exception e) {
										}
							%>

										<c:if test="<%= !dlEditFileEntryDisplayContext.isDDMStructureVisible(ddmStructure) %>">
											<div class="hide">
										</c:if>

										<liferay-ddm:html
											classNameId="<%= PortalUtil.getClassNameId(com.liferay.dynamic.data.mapping.model.DDMStructure.class) %>"
											classPK="<%= ddmStructure.getPrimaryKey() %>"
											ddmFormValues="<%= ddmFormValues %>"
											fieldsNamespace="<%= String.valueOf(ddmStructure.getPrimaryKey()) %>"
											requestedLocale="<%= locale %>"
										/>

										<c:if test="<%= !dlEditFileEntryDisplayContext.isDDMStructureVisible(ddmStructure) %>">
											</div>
										</c:if>

							<%
									}
								}
								catch (Exception e) {
									_log.error(e, e);
								}
							}
							%>

						</c:if>
					</c:if>
				</aui:fieldset>

				<c:if test="<%= (fileEntry != null) && !checkedOut %>">
					<aui:fieldset collapsed="<%= true %>" collapsible="<%= true %>" label="versioning">
						<aui:input
							label="customize-the-version-number-increment-and-describe-my-changes"
							name="updateVersionDetails"
							type="toggle-switch"
							value="<%= updateVersionDetails %>"
						/>

						<div id="<portlet:namespace />versionDetails" style="<%= (updateVersionDetails) ? StringPool.BLANK : "display: none" %>">
							<aui:input checked="<%= majorVersion %>" label="major-version" name="majorVersion" type="radio" value="<%= true %>" />

							<aui:input checked="<%= !majorVersion %>" label="minor-version" name="majorVersion" type="radio" value="<%= false %>" />

							<aui:model-context />

							<aui:input label="version-notes" name="changeLog" type="textarea" />

							<aui:model-context bean="<%= fileVersion %>" model="<%= DLFileVersion.class %>" />
						</div>
					</aui:fieldset>
				</c:if>

				<c:if test="<%= (folder == null) || folder.isSupportsMetadata() %>">
					<liferay-expando:custom-attributes-available className="<%= DLFileEntryConstants.getClassName() %>">
						<aui:fieldset collapsed="<%= true %>" collapsible="<%= true %>" label="custom-fields">
							<liferay-expando:custom-attribute-list
								className="<%= DLFileEntryConstants.getClassName() %>"
								classPK="<%= fileVersionId %>"
								editable="<%= true %>"
								label="<%= true %>"
							/>
						</aui:fieldset>
					</liferay-expando:custom-attributes-available>
				</c:if>

				<c:if test="<%= (folder == null) || folder.isSupportsSocial() %>">
					<aui:fieldset collapsed="<%= true %>" collapsible="<%= true %>" label="categorization">
						<liferay-asset:asset-categories-selector className="<%= DLFileEntry.class.getName() %>" classPK="<%= assetClassPK %>" classTypePK="<%= fileEntryTypeId %>" />

						<liferay-asset:asset-tags-selector className="<%= DLFileEntry.class.getName() %>" classPK="<%= assetClassPK %>" />
					</aui:fieldset>

					<aui:fieldset collapsed="<%= true %>" collapsible="<%= true %>" label="related-assets">
						<liferay-ui:input-asset-links
							className="<%= DLFileEntry.class.getName() %>"
							classPK="<%= assetClassPK %>"
						/>
					</aui:fieldset>
				</c:if>

				<c:if test="<%= fileEntry == null %>">
					<aui:fieldset collapsed="<%= true %>" collapsible="<%= true %>" label="permissions">
						<liferay-ui:input-permissions
							modelName="<%= DLFileEntryConstants.getClassName() %>"
						/>
					</aui:fieldset>
				</c:if>

				<c:if test="<%= pending %>">
					<div class="alert alert-info">
						<liferay-ui:message key="there-is-a-publication-workflow-in-process" />
					</div>
				</c:if>
			</aui:fieldset-group>
		</div>

		<aui:button-row>
			<c:if test="<%= dlEditFileEntryDisplayContext.isSaveButtonVisible() %>">
				<aui:button cssClass="btn-lg" disabled="<%= dlEditFileEntryDisplayContext.isSaveButtonDisabled() %>" name="saveButton" onClick='<%= renderResponse.getNamespace() + "saveFileEntry(true);" %>' value="<%= dlEditFileEntryDisplayContext.getSaveButtonLabel() %>" />
			</c:if>

			<c:if test="<%= dlEditFileEntryDisplayContext.isPublishButtonVisible() %>">
				<aui:button cssClass="btn-lg" disabled="<%= dlEditFileEntryDisplayContext.isPublishButtonDisabled() %>" name="publishButton" type="submit" value="<%= dlEditFileEntryDisplayContext.getPublishButtonLabel() %>" />
			</c:if>

			<c:if test="<%= dlEditFileEntryDisplayContext.isCheckoutDocumentButtonVisible() %>">
				<aui:button cssClass="btn-lg" disabled="<%= dlEditFileEntryDisplayContext.isCheckoutDocumentButtonDisabled() %>" onClick='<%= renderResponse.getNamespace() + "checkOut();" %>' value="checkout[document]" />
			</c:if>

			<c:if test="<%= dlEditFileEntryDisplayContext.isCheckinButtonVisible() %>">
				<aui:button cssClass="btn-lg" disabled="<%= dlEditFileEntryDisplayContext.isCheckinButtonDisabled() %>" onClick='<%= renderResponse.getNamespace() + "checkIn();" %>' value="save-and-checkin" />
			</c:if>

			<c:if test="<%= dlEditFileEntryDisplayContext.isCancelCheckoutDocumentButtonVisible() %>">
				<aui:button cssClass="btn-lg" disabled="<%= dlEditFileEntryDisplayContext.isCancelCheckoutDocumentButtonDisabled() %>" onClick='<%= renderResponse.getNamespace() + "cancelCheckOut();" %>' value="cancel-checkout[document]" />
			</c:if>

			<aui:button cssClass="btn-lg" href="<%= redirect %>" type="cancel" />
		</aui:button-row>
	</aui:form>

	<liferay-ui:upload-progress
		id="<%= uploadProgressId %>"
		message="uploading"
	/>
</div>

<c:if test="<%= (fileEntry != null) && checkedOut %>">

	<%
	request.setAttribute("edit_file_entry.jsp-checkedOut", checkedOut);
	%>

	<liferay-util:include page="/document_library/version_details.jsp" servletContext="<%= application %>" />
</c:if>

<aui:script>
	function <portlet:namespace />changeFileEntryType() {
		var form = AUI.$(document.<portlet:namespace />fm);

		form.fm('<%= Constants.CMD %>').val('<%= Constants.PREVIEW %>');

		submitForm(form);
	}

	function <portlet:namespace />cancelCheckOut() {
		submitForm(document.hrefFm, '<portlet:actionURL name="/document_library/edit_file_entry"><portlet:param name="<%= Constants.CMD %>" value="<%= Constants.CANCEL_CHECKOUT %>" /><portlet:param name="redirect" value="<%= redirect %>" /><portlet:param name="fileEntryId" value="<%= String.valueOf(fileEntryId) %>" /></portlet:actionURL>');
	}

	function <portlet:namespace />checkIn() {
		var form = AUI.$(document.<portlet:namespace />fm);

		form.fm('<%= Constants.CMD %>').val('<%= Constants.UPDATE_AND_CHECKIN %>');

		<portlet:namespace />showVersionDetailsDialog(form);
	}

	function <portlet:namespace />checkOut() {
		submitForm(document.hrefFm, '<portlet:actionURL name="/document_library/edit_file_entry"><portlet:param name="<%= Constants.CMD %>" value="<%= Constants.CHECKOUT %>" /><portlet:param name="redirect" value="<%= redirect %>" /><portlet:param name="fileEntryId" value="<%= String.valueOf(fileEntryId) %>" /></portlet:actionURL>');
	}

	function <portlet:namespace />saveFileEntry(draft) {
		var $ = AUI.$;

		var form = $(document.<portlet:namespace />fm);

		var fileValue = form.fm('file').val();

		if (fileValue) {
			<%= HtmlUtil.escape(uploadProgressId) %>.startProgress();
		}

		form.fm('<%= Constants.CMD %>').val('<%= (fileEntry == null) ? Constants.ADD : Constants.UPDATE %>');

		if (draft) {
			form.fm('workflowAction').val('<%= WorkflowConstants.ACTION_SAVE_DRAFT %>');
		}

		submitForm(form);
	}

	Liferay.provide(
		window,
		'<portlet:namespace />showVersionDetailsDialog',
		function(form) {
			Liferay.Portlet.DocumentLibrary.Checkin.showDialog(
				'<portlet:namespace />versionDetails',
				'<%= UnicodeLanguageUtil.get(request, "describe-your-changes") %>',
				{
					label: '<liferay-ui:message key="save" />',
					callback: function(event) {
						var $ = AUI.$;

						var majorVersionNode = $("input:radio[name='<portlet:namespace />versionDetailsMajorVersion']:checked");

						form.fm('majorVersion').val(majorVersionNode.val());

						var changeLogNode = $('#<portlet:namespace />versionDetailsChangeLog');

						form.fm('changeLog').val(changeLogNode.val());

						submitForm(form);
					}
				},
				'<liferay-ui:message key="cancel" />'
			);
		},
		['document-library-checkin']
	);

	function <portlet:namespace />updateTitle() {
		var title = $('#<portlet:namespace />title').val();

		if (!title) {
			var filePath = $('#<portlet:namespace />file').val();

			var fileName = filePath.replace(/^.*[\\\/]/, '');

			$('#<portlet:namespace />title').val(fileName);
		}

		<portlet:namespace />validateTitle();
	}

	function <portlet:namespace />validateTitle() {
		Liferay.Form.get('<portlet:namespace />fm').formValidator.validateField('<portlet:namespace />title');
	}
</aui:script>

<c:if test="<%= (fileEntry != null) && !checkedOut %>">
	<aui:script use="aui-base">
		$('#<portlet:namespace />updateVersionDetails').on(
			'click',
			function(event) {
				$('#<portlet:namespace />versionDetails').toggle();
			}
		);
	</aui:script>
</c:if>

<%
if (fileEntry != null) {
	DLBreadcrumbUtil.addPortletBreadcrumbEntries(fileEntry, request, renderResponse);

	PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, "edit"), currentURL);
}
else {
	DLBreadcrumbUtil.addPortletBreadcrumbEntries(folderId, request, renderResponse);

	PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, "add-file-entry"), currentURL);
}
%>

<%!
private static Log _log = LogFactoryUtil.getLog("com_liferay_document_library_web.document_library.edit_file_entry_jsp");
%>