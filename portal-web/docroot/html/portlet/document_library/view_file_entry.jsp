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
String tabs2 = ParamUtil.getString(request, "tabs2", "version-history");

String redirect = ParamUtil.getString(request, "redirect");

String referringPortletResource = ParamUtil.getString(request, "referringPortletResource");

String uploadProgressId = "dlFileEntryUploadProgress";

FileEntry fileEntry = (FileEntry)request.getAttribute(WebKeys.DOCUMENT_LIBRARY_FILE_ENTRY);

long fileEntryId = fileEntry.getFileEntryId();
long folderId = fileEntry.getFolderId();
String extension = fileEntry.getExtension();
String title = fileEntry.getTitle();

Folder folder = fileEntry.getFolder();
FileVersion fileVersion = (FileVersion)request.getAttribute(WebKeys.DOCUMENT_LIBRARY_FILE_VERSION);

boolean versionSpecific = false;

if (fileVersion != null) {
	versionSpecific = true;
}
else if ((user.getUserId() == fileEntry.getUserId()) || permissionChecker.isCompanyAdmin() || permissionChecker.isGroupAdmin(scopeGroupId) || DLFileEntryPermission.contains(permissionChecker, fileEntry, ActionKeys.UPDATE)) {
	fileVersion = fileEntry.getLatestFileVersion();
}
else {
	fileVersion = fileEntry.getFileVersion();
}

long fileVersionId = fileVersion.getFileVersionId();

long fileEntryTypeId = 0;

if (fileVersion.getModel() instanceof DLFileVersion) {
	DLFileVersion dlFileVersion = (DLFileVersion)fileVersion.getModel();

	fileEntryTypeId = dlFileVersion.getFileEntryTypeId();
}

Lock lock = fileEntry.getLock();

String[] conversions = new String[0];

if (PrefsPropsUtil.getBoolean(PropsKeys.OPENOFFICE_SERVER_ENABLED, PropsValues.OPENOFFICE_SERVER_ENABLED)) {
	conversions = (String[])DocumentConversionUtil.getConversions(extension);
}

long assetClassPK = 0;

if (!fileVersion.isApproved() && (fileVersion.getVersion() != DLFileEntryConstants.VERSION_DEFAULT)) {
	assetClassPK = fileVersion.getFileVersionId();
	title = fileVersion.getTitle();
	extension = fileVersion.getExtension();
}
else {
	assetClassPK = fileEntry.getFileEntryId();
}

String fileUrl = themeDisplay.getPortalURL() + themeDisplay.getPathContext() + "/documents/" + themeDisplay.getScopeGroupId() + StringPool.SLASH + folderId + StringPool.SLASH + HttpUtil.encodeURL(fileEntry.getTitle());
String webDavUrl = StringPool.BLANK;

if (portletDisplay.isWebDAVEnabled()) {
	StringBuilder sb = new StringBuilder();

	if (folderId != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
		Folder curFolder = DLAppLocalServiceUtil.getFolder(folderId);

		while (true) {
			sb.insert(0, HttpUtil.encodeURL(curFolder.getName(), true));
			sb.insert(0, StringPool.SLASH);

			if (curFolder.getParentFolderId() == DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
				break;
			}
			else {
				curFolder = DLAppLocalServiceUtil.getFolder(curFolder.getParentFolderId());
			}
		}
	}

	sb.append(StringPool.SLASH);
	sb.append(HttpUtil.encodeURL(fileEntry.getTitle(), true));

	Group group = themeDisplay.getScopeGroup();

	webDavUrl = themeDisplay.getPortalURL() + "/tunnel-web/secure/webdav" + group.getFriendlyURL() + "/document_library" + sb.toString();
}

boolean hasAudio = AudioProcessor.hasAudio(fileVersion);
boolean hasImages = ImageProcessor.hasImages(fileVersion);
boolean hasPDFImages = PDFProcessor.hasImages(fileVersion);
boolean hasVideo = VideoProcessor.hasVideo(fileVersion);

User userDisplay = UserLocalServiceUtil.getUserById(fileEntry.getUserId());

AssetEntry layoutAssetEntry = AssetEntryLocalServiceUtil.fetchEntry(DLFileEntryConstants.getClassName(), assetClassPK);

request.setAttribute(WebKeys.LAYOUT_ASSET_ENTRY, layoutAssetEntry);

request.setAttribute("view_file_entry.jsp-fileEntry", fileEntry);
%>

<portlet:actionURL var="editFileEntry">
	<portlet:param name="struts_action" value="/document_library/edit_file_entry" />
	<portlet:param name="redirect" value="<%= currentURL %>" />
	<portlet:param name="fileEntryId" value="<%= String.valueOf(fileEntry.getFileEntryId()) %>" />
</portlet:actionURL>

<aui:form action="<%= editFileEntry %>" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" />
</aui:form>

<c:if test="<%= folder != null %>">

	<%
	String versionText = null;

	if (Validator.isNotNull(fileVersion.getVersion())) {
		versionText = LanguageUtil.format(pageContext, "version-x", fileVersion.getVersion());
	}
	else {
		versionText = LanguageUtil.get(pageContext, "not-approved");
	}
	%>

	<liferay-ui:header
		backURL="<%= redirect %>"
		localizeTitle="<%= false %>"
		title="<%= fileEntry.getTitle() %>"
	/>
</c:if>

<div class="view">
	<aui:layout>
		<aui:column columnWidth="<%= 65 %>" cssClass="lfr-asset-column-details" first="<%= true %>">
			<div class="lfr-header-row">
				<div class="lfr-header-row-content">
					<aui:button-row cssClass="edit-toolbar" id='<%= renderResponse.getNamespace() + "fileEntryToolbar" %>' />
				</div>
			</div>

			<c:if test="<%= fileEntry.isCheckedOut() && DLFileEntryPermission.contains(permissionChecker, fileEntry, ActionKeys.UPDATE) %>">
				<c:choose>
					<c:when test="<%= fileEntry.hasLock() %>">
						<div class="portlet-msg-lock portlet-msg-success">
							<c:choose>
								<c:when test="<%= lock.isNeverExpires() %>">
									<liferay-ui:message key="you-now-have-an-indefinite-lock-on-this-document" />
								</c:when>
								<c:otherwise>

									<%
									String lockExpirationTime = LanguageUtil.getTimeDescription(pageContext, DLFileEntryConstants.LOCK_EXPIRATION_TIME).toLowerCase();
									%>

									<%= LanguageUtil.format(pageContext, "you-now-have-a-lock-on-this-document", lockExpirationTime, false) %>
								</c:otherwise>
							</c:choose>
						</div>
					</c:when>
					<c:otherwise>
						<div class="portlet-msg-error">
							<%= LanguageUtil.format(pageContext, "you-cannot-modify-this-document-because-it-was-locked-by-x-on-x", new Object[] {HtmlUtil.escape(PortalUtil.getUserName(lock.getUserId(), String.valueOf(lock.getUserId()))), dateFormatDateTime.format(lock.getCreateDate())}, false) %>
						</div>
					</c:otherwise>
				</c:choose>
			</c:if>

			<div class="body-row">
				<div class="document-info">
					<h2 class="document-title">
						<c:choose>
							<c:when test="<%= versionSpecific %>">
								<%= fileVersion.getTitle() %>

								(<liferay-ui:message key="version" /> <%= fileVersion.getVersion() %>)
							</c:when>
							<c:otherwise>
								<%= title %>
							</c:otherwise>
						</c:choose>
					</h2>

					<span class="document-thumbnail">

						<%
						String thumbnailSrc = themeDisplay.getPathThemeImages() + "/file_system/large/" + DLUtil.getGenericName(extension) + ".png";

						String thumbnailQueryString = null;

						if (hasImages) {
							thumbnailQueryString = "&imageThumbnail=1";
						}
						else if (hasPDFImages) {
							thumbnailQueryString = "&documentThumbnail=1";
						}
						else if (hasVideo) {
							thumbnailQueryString = "&videoThumbnail=1";
						}

						if (Validator.isNotNull(thumbnailQueryString)) {
							thumbnailSrc = _getPreviewURL(fileEntry, fileVersion.getVersion(), themeDisplay, thumbnailQueryString);
						}

						if (layoutAssetEntry != null) {
							AssetEntry incrementAssetEntry = AssetEntryServiceUtil.incrementViewCounter(layoutAssetEntry.getClassName(), assetClassPK);

							if (incrementAssetEntry != null) {
								layoutAssetEntry = incrementAssetEntry;
							}
						}
						%>

						<img alt="" border="no" class="thumbnail" src="<%= thumbnailSrc %>" />
					</span>

					<span class="user-date">
						<liferay-ui:icon image="../document_library/add_document" label="<%= true %>" message='<%= LanguageUtil.format(pageContext, "uploaded-by-x-x", new Object[] {userDisplay.getDisplayURL(themeDisplay), HtmlUtil.escape(fileEntry.getUserName()), fileEntry.getCreateDate().toString()}) %>' />
					</span>

					<c:if test="<%= fileEntry.isSupportsSocial() %>">
						<span class="lfr-asset-ratings">
							<liferay-ui:ratings
								className="<%= DLFileEntryConstants.getClassName() %>"
								classPK="<%= fileEntryId %>"
							/>
						</span>
					</c:if>

					<c:if test="<%= enableRelatedAssets %>">
						<div class="entry-links">
							<liferay-ui:asset-links
								className="<%= DLFileEntryConstants.getClassName() %>"
								classPK="<%= assetClassPK %>"
							/>
						</div>
					</c:if>

					<span class="document-description">
						<%= HtmlUtil.escape(fileVersion.getDescription()) %>
					</span>

					<c:if test="<%= fileEntry.isSupportsSocial() %>">
						<div class="lfr-asset-categories">
							<liferay-ui:asset-categories-summary
								className="<%= DLFileEntryConstants.getClassName() %>"
								classPK="<%= assetClassPK %>"
							/>
						</div>

						<div class="lfr-asset-tags">
							<liferay-ui:asset-tags-summary
								className="<%= DLFileEntryConstants.getClassName() %>"
								classPK="<%= assetClassPK %>"
								message="tags"
							/>
						</div>
					</c:if>
				</div>

				<aui:model-context bean="<%= fileVersion %>" model="<%= DLFileVersion.class %>" />

				<c:if test="<%= PropsValues.DL_FILE_ENTRY_PREVIEW_ENABLED %>">
					<div>

						<%
						boolean supportedAudio = AudioProcessor.isSupportedAudio(fileVersion.getMimeType());
						boolean supportedVideo = VideoProcessor.isSupportedVideo(fileVersion.getMimeType());

						int previewFileCount = 0;
						String previewFileURL = null;
						String videoThumbnailURL = null;

						String previewQueryString = null;

						if (hasAudio) {
							previewQueryString = "&audioPreview=1";
						}
						else if (hasImages) {
							previewQueryString = "&imagePreview=1";
						}
						else if (hasPDFImages) {
							previewFileCount = PDFProcessor.getPreviewFileCount(fileVersion);

							previewQueryString = "&previewFileIndex=";
						}
						else if (hasVideo) {
							previewQueryString = "&videoPreview=1";

							videoThumbnailURL = _getPreviewURL(fileEntry, fileVersion.getVersion(), themeDisplay, "&videoThumbnail=1");
						}

						if (Validator.isNotNull(previewQueryString)) {
							previewFileURL = _getPreviewURL(fileEntry, fileVersion.getVersion(), themeDisplay, previewQueryString);

							if (!hasPDFImages) {
								previewFileCount = 1;
							}
						}

						request.setAttribute("view_file_entry.jsp-supportedAudio", String.valueOf(supportedAudio));
						request.setAttribute("view_file_entry.jsp-supportedVideo", String.valueOf(supportedVideo));

						request.setAttribute("view_file_entry.jsp-previewFileURL", previewFileURL);
						request.setAttribute("view_file_entry.jsp-videoThumbnailURL", videoThumbnailURL);
						%>

						<c:choose>
							<c:when test="<%= previewFileCount == 0 %>">
								<div class="portlet-msg-info">
									<liferay-ui:message key="generating-preview-will-take-a-few-minutes" />
								</div>
							</c:when>
							<c:otherwise>
								<c:choose>
									<c:when test ="<%= !hasImages && !supportedAudio && !supportedVideo %>">
										<div class="lfr-preview-file" id="<portlet:namespace />previewFile">
											<div class="lfr-preview-file-content" id="<portlet:namespace />previewFileContent">
												<div class="lfr-preview-file-image-current-column">
													<div class="lfr-preview-file-image-container">
														<img class="lfr-preview-file-image-current" id="<portlet:namespace />previewFileImage" src="<%= previewFileURL + "1" %>" />
													</div>
													<span class="lfr-preview-file-actions aui-helper-hidden" id="<portlet:namespace />previewFileActions">
														<span class="lfr-preview-file-toolbar" id="<portlet:namespace />previewToolbar"></span>

														<span class="lfr-preview-file-info">
															<span class="lfr-preview-file-index" id="<portlet:namespace />previewFileIndex">1</span> of <span class="lfr-preview-file-count"><%= previewFileCount %></span>
														</span>
													</span>
												</div>

												<div class="lfr-preview-file-images" id="<portlet:namespace />previewImagesContent">
													<div class="lfr-preview-file-images-content"></div>
												</div>
											</div>
										</div>

										<aui:script use="aui-base,liferay-preview">
											new Liferay.Preview(
												{
													actionContent: '#<portlet:namespace />previewFileActions',
													baseImageURL: '<%= previewFileURL %>',
													boundingBox: '#<portlet:namespace />previewFile',
													contentBox: '#<portlet:namespace />previewFileContent',
													currentPreviewImage: '#<portlet:namespace />previewFileImage',
													imageListContent: '#<portlet:namespace />previewImagesContent',
													maxIndex: <%= previewFileCount %>,
													previewFileIndexNode: '#<portlet:namespace />previewFileIndex',
													toolbar: '#<portlet:namespace />previewToolbar'
												}
											).render();
										</aui:script>
									</c:when>
									<c:when test="<%= hasImages %>">
										<div class="lfr-preview-file lfr-preview-image" id="<portlet:namespace />previewFile">
											<div class="lfr-preview-file-content lfr-preview-image-content" id="<portlet:namespace />previewFileContent">
												<img src="<%= previewFileURL %>" />
											</div>
										</div>
									</c:when>
									<c:when test="<%= supportedAudio || supportedVideo %>">
										<div class="lfr-preview-file lfr-preview-video" id="<portlet:namespace />previewFile">
											<div class="lfr-preview-file-content lfr-preview-video-content" id="<portlet:namespace />previewFileContent"></div>
										</div>

										<liferay-util:include page="/html/portlet/document_library/player.jsp" />
									</c:when>
								</c:choose>
							</c:otherwise>
						</c:choose>
					</div>
				</c:if>

				<c:if test="<%= PropsValues.DL_FILE_ENTRY_COMMENTS_ENABLED %>">
					<liferay-ui:panel cssClass="lfr-document-library-comments" collapsible="<%= true %>" extended="<%= true %>" persistState="<%= true %>" title="comments">
						<portlet:actionURL var="discussionURL">
							<portlet:param name="struts_action" value="/document_library/edit_file_entry_discussion" />
						</portlet:actionURL>

						<liferay-ui:discussion
							className="<%= DLFileEntryConstants.getClassName() %>"
							classPK="<%= fileEntryId %>"
							formAction="<%= discussionURL %>"
							formName="fm2"
							ratingsEnabled="<%= enableCommentRatings %>"
							redirect="<%= currentURL %>"
							subject="<%= title %>"
							userId="<%= fileEntry.getUserId() %>"
						/>
					</liferay-ui:panel>
				</c:if>
			</div>
		</aui:column>

		<aui:column columnWidth="<%= 35 %>" cssClass="lfr-asset-column-details context-pane" last="<%= true %>">
			<div class="lfr-header-row">
				<div class="lfr-header-row-content"></div>
			</div>

			<div class="body-row asset-details">
				<div class="asset-details-content">
					<h3 class="version <%= fileEntry.isCheckedOut() ? "document-locked" : StringPool.BLANK %>">
						<liferay-ui:message key="version" /> <%= fileVersion.getVersion() %>
					</h3>

					<div class="lfr-asset-icon lfr-asset-author">
						<liferay-ui:message arguments="<%= HtmlUtil.escape(fileVersion.getUserName()) %>" key="last-updated-by-x" />
					</div>

					<div class="lfr-asset-icon lfr-asset-date">
						<%= dateFormatDateTime.format(fileVersion.getModifiedDate()) %>
					</div>

					<div class="lfr-asset-summary">
						<aui:workflow-status model="<%= DLFileEntry.class %>" status="<%= fileVersion.getStatus() %>" />
					</div>

					<c:if test="<%= Validator.isNotNull(fileVersion.getDescription()) %>">
						<blockquote class="lfr-asset-description">
							<%= HtmlUtil.escape(fileVersion.getDescription()) %>
						</blockquote>
					</c:if>

					<span class="download-document">
						<c:if test="<%= DLFileEntryPermission.contains(permissionChecker, fileEntry, ActionKeys.VIEW) %>">
							<liferay-ui:icon
								image="download"
								label="<%= true %>"
								message='<%= LanguageUtil.get(pageContext, "download") + " (" + TextFormatter.formatKB(fileVersion.getSize(), locale) + "k)" %>'
								url='<%= themeDisplay.getPortalURL() + themeDisplay.getPathContext() + "/documents/" + themeDisplay.getScopeGroupId() + StringPool.SLASH + fileEntry.getFolderId() + StringPool.SLASH + HttpUtil.encodeURL(fileEntry.getTitle()) + "?version=" + fileVersion.getVersion() %>'
							/>
						</c:if>
					</span>

					<span class="conversions">

						<%
						for (int i = 0; i < conversions.length; i++) {
							String conversion = conversions[i];
						%>

							<liferay-ui:icon
								image='<%= "../file_system/small/" + conversion %>'
								label="<%= true %>"
								message="<%= conversion.toUpperCase() %>"
								url='<%= themeDisplay.getPortalURL() + themeDisplay.getPathContext() + "/documents/" + themeDisplay.getScopeGroupId() + StringPool.SLASH + fileEntry.getFolderId() + StringPool.SLASH + HttpUtil.encodeURL(fileEntry.getTitle()) + "?version=" + fileVersion.getVersion() + "&targetExtension=" + conversion %>'
							/>

						<%
						}
						%>

					</span>

					<span class="webdav-url">
						<c:choose>
							<c:when test="<%= portletDisplay.isWebDAVEnabled() && fileEntry.isSupportsSocial() %>">
								<liferay-ui:message key="get-url-or-webdav-url" />
							</c:when>

							<c:otherwise>
								<liferay-ui:message key="get-url" />
							</c:otherwise>
						</c:choose>
					</span>

					<div class="lfr-asset-field url-file-container aui-helper-hidden">
						<label><liferay-ui:message key="url" /></label>

						<liferay-ui:input-resource
							url='<%= themeDisplay.getPortalURL() + themeDisplay.getPathContext() + "/documents/" + themeDisplay.getScopeGroupId() + StringPool.SLASH + fileEntry.getUuid() %>'
						/>
					</div>

					<c:if test="<%= portletDisplay.isWebDAVEnabled() && fileEntry.isSupportsSocial() %>">
						<div class="lfr-asset-field webdav-url-file-container aui-helper-hidden">

							<%
							String webDavHelpMessage = null;

							if (BrowserSnifferUtil.isWindows(request)) {
								webDavHelpMessage = LanguageUtil.format(pageContext, "webdav-windows-help", new Object[] {"http://www.microsoft.com/downloads/details.aspx?FamilyId=17C36612-632E-4C04-9382-987622ED1D64", "http://www.liferay.com/web/guest/community/wiki/-/wiki/Main/WebDAV"});
							}
							else {
								webDavHelpMessage = LanguageUtil.format(pageContext, "webdav-help", "http://www.liferay.com/web/guest/community/wiki/-/wiki/Main/WebDAV");
							}
							%>

							<aui:field-wrapper helpMessage="<%= webDavHelpMessage %>" label="webdav-url">
								<liferay-ui:input-resource url="<%= webDavUrl %>" />
							</aui:field-wrapper>
						</div>
					</c:if>
				</div>

				<%
				request.removeAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);
				%>

				<div class="lfr-asset-panels">
					<liferay-ui:panel-container extended="<%= false %>" id="documentLibraryAssetPanelContainer" persistState="<%= true %>">

						<%
						if (fileEntryTypeId > 0) {
							try {
								DLFileEntryType fileEntryType = DLFileEntryTypeServiceUtil.getFileEntryType(fileEntryTypeId);

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

									<liferay-ui:panel collapsible="<%= true %>" cssClass="metadata" extended="<%= true %>" id="documentLibraryMetadataPanel" persistState="<%= true %>" title="<%= ddmStructure.getName(LocaleUtil.getDefault()) %>">

										<%= DDMXSDUtil.getHTML(pageContext, ddmStructure.getXsd(), fields, String.valueOf(ddmStructure.getPrimaryKey()), true, locale) %>

									</liferay-ui:panel>

						<%
								}
							}
							catch (Exception e) {
							}
						}
						%>

						<liferay-ui:custom-attributes-available className="<%= DLFileEntryConstants.getClassName() %>" classPK="<%= fileVersionId %>" editable="<%= false %>">
							<liferay-ui:panel collapsible="<%= true %>" cssClass="custom-fields" id="documentLibraryCustomAttributesPanel" persistState="<%= true %>" title="custom-fields">
								<liferay-ui:custom-attribute-list
									className="<%= DLFileEntryConstants.getClassName() %>"
									classPK="<%= fileVersionId %>"
									editable="<%= false %>"
									label="<%= true %>"
								/>
							</liferay-ui:panel>
						</liferay-ui:custom-attributes-available>

						<%
						try {
							List<DDMStructure> ddmStructures = DDMStructureLocalServiceUtil.getClassStructures(PortalUtil.getClassNameId(DLFileEntry.class));

							for (DDMStructure ddmStructure : ddmStructures) {
								Fields fields = null;

								try {
									DLFileEntryMetadata fileEntryMetadata = DLFileEntryMetadataLocalServiceUtil.getFileEntryMetadata(ddmStructure.getStructureId(), fileVersionId);

									fields = StorageEngineUtil.getFields(fileEntryMetadata.getDDMStorageId());
								}
								catch (Exception e) {
								}

								if (fields != null) {
									String name = "metadata." + ddmStructure.getName(LocaleUtil.getDefault(), true);
						%>

									<liferay-ui:panel collapsible="<%= true %>" cssClass="lfr-asset-metadata" id="documentLibraryAssetMetadataPanel" persistState="<%= true %>" title="<%= name %>">

										<%= DDMXSDUtil.getHTML(pageContext, ddmStructure.getXsd(), fields, String.valueOf(ddmStructure.getPrimaryKey()), true, locale) %>

									</liferay-ui:panel>

						<%
								}
							}
						}
						catch (Exception e) {
						}
						%>

						<liferay-ui:panel collapsible="<%= true %>" cssClass="version-history" id="documentLibraryVersionHistoryPanel" persistState="<%= true %>" title="version-history">

							<%
							boolean comparableFileEntry = DocumentConversionUtil.isComparableVersion(extension);
							boolean showNonApprovedDocuments = false;

							if ((user.getUserId() == fileEntry.getUserId()) || permissionChecker.isCompanyAdmin() || permissionChecker.isGroupAdmin(scopeGroupId)) {
								showNonApprovedDocuments = true;
							}

							SearchContainer searchContainer = new SearchContainer();

							List<String> headerNames = new ArrayList<String>();

							headerNames.add("version");
							headerNames.add("date");
							headerNames.add("size");

							if (showNonApprovedDocuments) {
								headerNames.add("status");
							}

							headerNames.add(StringPool.BLANK);

							searchContainer.setHeaderNames(headerNames);

							PortletURL viewFileEntryURL = renderResponse.createRenderURL();

							viewFileEntryURL.setParameter("struts_action", "/document_library/view_file_entry");
							viewFileEntryURL.setParameter("redirect", currentURL);
							viewFileEntryURL.setParameter("fileEntryId", String.valueOf(fileEntry.getFileEntryId()));

							searchContainer.setIteratorURL(viewFileEntryURL);

							if (comparableFileEntry) {
								RowChecker rowChecker = new RowChecker(renderResponse);

								rowChecker.setAllRowIds(null);

								searchContainer.setRowChecker(rowChecker);
							}

							int status = WorkflowConstants.STATUS_APPROVED;

							if (showNonApprovedDocuments) {
								status = WorkflowConstants.STATUS_ANY;
							}

							List results = fileEntry.getFileVersions(status);
							List resultRows = searchContainer.getResultRows();

							for (int i = 0; i < results.size(); i++) {
								FileVersion curFileVersion = (FileVersion)results.get(i);

								ResultRow row = new ResultRow(new Object[] {fileEntry, curFileVersion, results.size(), conversions, fileEntry.isCheckedOut(), fileEntry.hasLock()}, String.valueOf(curFileVersion.getVersion()), i);

								// Statistics

								row.addText(String.valueOf(curFileVersion.getVersion()));
								row.addText(dateFormatDateTime.format(curFileVersion.getCreateDate()));
								row.addText(TextFormatter.formatKB(curFileVersion.getSize(), locale) + "k");

								// Status

								if (showNonApprovedDocuments) {
									row.addText(LanguageUtil.get(pageContext, WorkflowConstants.toLabel(curFileVersion.getStatus())));
								}

								// Action

								row.addJSP("right", SearchEntry.DEFAULT_VALIGN, "/html/portlet/document_library/file_entry_history_action.jsp");

								// Add result row

								resultRows.add(row);
							}

							if (comparableFileEntry && !results.isEmpty()) {
								FileVersion curFileVersion = (FileVersion)results.get(0);
							%>

								<portlet:actionURL var="compareVersionsURL">
									<portlet:param name="struts_action" value="/document_library/compare_versions" />
								</portlet:actionURL>

								<aui:form action="<%= compareVersionsURL %>" method="post" name="fm1" onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "compare();" %>'>
									<aui:input name="backURL" type="hidden" value="<%= currentURL %>" />
									<aui:input name="fileEntryId" type="hidden" value="<%= fileEntryId %>" />
									<aui:input name="sourceVersion" type="hidden" value="<%= curFileVersion.getVersion() %>" />
									<aui:input name="targetVersion" type="hidden" value="<%= fileEntry.getVersion() %>" />

									<aui:button-row>
										<aui:button type="submit" value="compare-versions" />
									</aui:button-row>
								</aui:form>

							<%
							}
							%>

							<liferay-ui:search-iterator searchContainer="<%= searchContainer %>" paginate="<%= false %>" />
						</liferay-ui:panel>
					</liferay-ui:panel-container>
				</div>
			</div>
		</aui:column>
	</aui:layout>
</div>

<aui:script>
	Liferay.provide(
		window,
		'<portlet:namespace />compare',
		function() {
			var A = AUI();

			var rowIds = A.all('input[name=<portlet:namespace />rowIds]:checked');
			var sourceVersion = A.one('input[name="<portlet:namespace />sourceVersion"]');
			var targetVersion = A.one('input[name="<portlet:namespace />targetVersion"]');

			var rowIdsSize = rowIds.size();

			if (rowIdsSize == 1) {
				if (sourceVersion) {
					sourceVersion.val(rowIds.item(0).val());
				}
			}
			else if (rowIdsSize == 2) {
				if (sourceVersion) {
					sourceVersion.val(rowIds.item(1).val());
				}

				if (targetVersion) {
					targetVersion.val(rowIds.item(0).val());
				}
			}

			submitForm(document.<portlet:namespace />fm1);
		},
		['aui-base', 'selector-css3']
	);

	Liferay.provide(
		window,
		'<portlet:namespace />initRowsChecked',
		function() {
			var A = AUI();

			var rowIds = A.all('input[name=<portlet:namespace />rowIds]');

			rowIds.each(
				function(item, index, collection) {
					if (index >= 2) {
						item.set('checked', false);
					}
				}
			);
		},
		['aui-base']
	);

	Liferay.provide(
		window,
		'<portlet:namespace />updateRowsChecked',
		function(element) {
			var A = AUI();

			var rowsChecked = A.all('input[name=<portlet:namespace />rowIds]:checked');

			if (rowsChecked.size() > 2) {
				var index = 2;

				if (rowsChecked.item(2).compareTo(element)) {
					index = 1;
				}

				rowsChecked.item(index).set('checked', false);
			}
		},
		['aui-base', 'selector-css3']
	);
</aui:script>

<aui:script use="aui-base">
	var showURLFile = A.one('.show-url-file');
	var showWebDavFile = A.one('.show-webdav-url-file');

	if (showURLFile) {
		showURLFile.on(
			'click',
			function(event) {
				var URLFileContainer = A.one('.url-file-container');

				URLFileContainer.toggleClass('aui-helper-hidden');
			}
		);
	}

	if (showWebDavFile) {
		showWebDavFile.on(
			'click',
			function(event) {
				var WebDavFileContainer = A.one('.webdav-url-file-container');

				WebDavFileContainer.toggleClass('aui-helper-hidden');
			}
		);
	}

	var buttonRow = A.one('#<portlet:namespace />fileEntryToolbar');

	var fileEntryToolbar = new A.Toolbar(
		{
			activeState: false,
			boundingBox: buttonRow,
			children: [

				<c:if test="<%= DLFileEntryPermission.contains(permissionChecker, fileEntry, ActionKeys.VIEW) %>">

					{
						handler: function(event) {
							location.href = '<%= themeDisplay.getPortalURL() + themeDisplay.getPathContext() + "/documents/" + themeDisplay.getScopeGroupId() + StringPool.SLASH + fileEntry.getFolderId() + StringPool.SLASH + HttpUtil.encodeURL(fileEntry.getTitle()) + "?version=" + fileVersion.getVersion() %>';
						},
						icon: 'download',
						label: '<liferay-ui:message key="download" />'
					},

				</c:if>

				<c:if test="<%= DLFileEntryPermission.contains(permissionChecker, fileEntry, ActionKeys.UPDATE) && (!fileEntry.isCheckedOut() || fileEntry.hasLock()) %>">
					{

						<portlet:renderURL var="editURL">
							<portlet:param name="struts_action" value="/document_library/edit_file_entry" />
							<portlet:param name="redirect" value="<%= currentURL %>" />
							<portlet:param name="backURL" value="<%= currentURL %>" />
							<portlet:param name="fileEntryId" value="<%= String.valueOf(fileEntry.getFileEntryId()) %>" />
						</portlet:renderURL>

						handler: function(event) {
							location.href = '<%= editURL.toString() %>';
						},
						icon: 'edit',
						label: '<liferay-ui:message key="edit" />'
					},
					{

						<portlet:renderURL var="moveURL">
							<portlet:param name="struts_action" value="/document_library/move_file_entry" />
							<portlet:param name="redirect" value="<%= currentURL %>" />
							<portlet:param name="fileEntryId" value="<%= String.valueOf(fileEntry.getFileEntryId()) %>" />
						</portlet:renderURL>

						handler: function(event) {
							location.href = '<%= moveURL.toString() %>';
						},
						icon: 'move',
						label: '<liferay-ui:message key="move" />'
					},

					<c:if test="<%= !fileEntry.isCheckedOut() %>">

						{

							handler: function(event) {
								document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = '<%= Constants.CHECKOUT %>';
								submitForm(document.<portlet:namespace />fm);
							},
							icon: 'lock',
							label: '<liferay-ui:message key="checkout" />'
						},

					</c:if>

					<c:if test="<%= fileEntry.isCheckedOut() && fileEntry.hasLock() %>">

						{

							handler: function(event) {
								document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = '<%= Constants.CANCEL_CHECKOUT %>';
								submitForm(document.<portlet:namespace />fm);
							},
							icon: 'undo',
							label: '<liferay-ui:message key="cancel-checkout" />'
						},

						{

							handler: function(event) {
								document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = '<%= Constants.CHECKIN %>';
								submitForm(document.<portlet:namespace />fm);
							},
							icon: 'unlock',
							label: '<liferay-ui:message key="checkin" />'
						},

					</c:if>
				</c:if>

				<c:if test="<%= DLFileEntryPermission.contains(permissionChecker, fileEntry, ActionKeys.PERMISSIONS) %>">

					{

						<liferay-security:permissionsURL
							modelResource="<%= DLFileEntryConstants.getClassName() %>"
							modelResourceDescription="<%= fileEntry.getTitle() %>"
							resourcePrimKey="<%= String.valueOf(fileEntry.getFileEntryId()) %>"
							var="permissionsURL"
						/>

						handler: function(event) {
							location.href = '<%= permissionsURL.toString() %>';
						},
						icon: 'permissions',
						label: '<liferay-ui:message key="permissions" />'
					}

				</c:if>

			]
		}
	).render();

	buttonRow.setData('fileEntryToolbar', fileEntryToolbar);

	<portlet:namespace />initRowsChecked();

	A.all('input[name=<portlet:namespace />rowIds]').on(
		'click',
		function(event) {
			<portlet:namespace />updateRowsChecked(event.currentTarget);
		}
	);
</aui:script>

<%
DLUtil.addPortletBreadcrumbEntries(fileEntry, request, renderResponse, true);
%>