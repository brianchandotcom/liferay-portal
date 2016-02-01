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

<liferay-util:dynamic-include key="com.liferay.document.library.web#/document_library/view_file_entry.jsp#pre" />

<%
String tabs2 = ParamUtil.getString(request, "tabs2", "version-history");

String redirect = ParamUtil.getString(request, "redirect");

String uploadProgressId = "dlFileEntryUploadProgress";

FileEntry fileEntry = (FileEntry)request.getAttribute(WebKeys.DOCUMENT_LIBRARY_FILE_ENTRY);

long fileEntryId = fileEntry.getFileEntryId();

long folderId = fileEntry.getFolderId();

if (Validator.isNull(redirect)) {
	PortletURL portletURL = renderResponse.createRenderURL();

	portletURL.setParameter("mvcRenderCommandName", (folderId == DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) ? "/document_library/view" : "/document_library/view_folder");
	portletURL.setParameter("folderId", String.valueOf(folderId));

	redirect = portletURL.toString();
}

Folder folder = fileEntry.getFolder();
FileVersion fileVersion = (FileVersion)request.getAttribute(WebKeys.DOCUMENT_LIBRARY_FILE_VERSION);

boolean versionSpecific = false;

if (fileVersion != null) {
	versionSpecific = true;
}
else if ((user.getUserId() == fileEntry.getUserId()) || permissionChecker.isContentReviewer(user.getCompanyId(), scopeGroupId) || DLFileEntryPermission.contains(permissionChecker, fileEntry, ActionKeys.UPDATE)) {
	fileVersion = fileEntry.getLatestFileVersion();
}
else {
	fileVersion = fileEntry.getFileVersion();
}

long fileVersionId = fileVersion.getFileVersionId();

com.liferay.portal.kernel.lock.Lock lock = fileEntry.getLock();

String[] conversions = new String[0];

if (PropsValues.DL_FILE_ENTRY_CONVERSIONS_ENABLED && PrefsPropsUtil.getBoolean(PropsKeys.OPENOFFICE_SERVER_ENABLED, PropsValues.OPENOFFICE_SERVER_ENABLED)) {
	conversions = (String[])DocumentConversionUtil.getConversions(fileVersion.getExtension());
}

long assetClassPK = 0;

if (!fileVersion.isApproved() && !fileVersion.getVersion().equals(DLFileEntryConstants.VERSION_DEFAULT) && !fileEntry.isInTrash()) {
	assetClassPK = fileVersion.getFileVersionId();
}
else {
	assetClassPK = fileEntry.getFileEntryId();
}

String webDavURL = StringPool.BLANK;

if (portletDisplay.isWebDAVEnabled()) {
	webDavURL = DLUtil.getWebDavURL(themeDisplay, folder, fileEntry);
}

AssetEntry layoutAssetEntry = AssetEntryLocalServiceUtil.fetchEntry(DLFileEntryConstants.getClassName(), assetClassPK);

request.setAttribute(WebKeys.LAYOUT_ASSET_ENTRY, layoutAssetEntry);

DLPortletInstanceSettingsHelper dlPortletInstanceSettingsHelper = new DLPortletInstanceSettingsHelper(dlRequestHelper);
final DLViewFileVersionDisplayContext dlViewFileVersionDisplayContext = dlDisplayContextProvider.getDLViewFileVersionDisplayContext(request, response, fileVersion);

boolean portletTitleBasedNavigation = GetterUtil.getBoolean(portletConfig.getInitParameter("portlet-title-based-navigation"));

if (portletTitleBasedNavigation) {
	portletDisplay.setShowBackIcon(true);
	portletDisplay.setURLBack(redirect);

	renderResponse.setTitle(fileVersion.getTitle());
}
%>

<div class="<%= portletTitleBasedNavigation ? "container-fluid-1280" : StringPool.BLANK %> sidenav-container sidenav-right" id="<portlet:namespace />infoPanelId">
	<portlet:actionURL name="/document_library/edit_file_entry" var="editFileEntry" />

	<aui:form action="<%= editFileEntry %>" method="post" name="fm">
		<aui:input name="<%= Constants.CMD %>" type="hidden" />
		<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
		<aui:input name="fileEntryId" type="hidden" value="<%= String.valueOf(fileEntry.getFileEntryId()) %>" />
	</aui:form>

	<c:if test="<%= !portletTitleBasedNavigation && showHeader && (folder != null) %>">
		<liferay-ui:header
			backURL="<%= redirect %>"
			localizeTitle="<%= false %>"
			title="<%= fileVersion.getTitle() %>"
		/>
	</c:if>

	<c:if test="<%= dlPortletInstanceSettingsHelper.isShowActions() && !portletTitleBasedNavigation %>">
		<liferay-ui:app-view-toolbar>
			<aui:button-row cssClass="edit-toolbar" id='<%= renderResponse.getNamespace() + "fileEntryToolbar" %>' />
		</liferay-ui:app-view-toolbar>
	</c:if>

	<div class="sidenav-menu-slider">

		<%
		boolean showCustomFieldsTab = false;
		%>

		<liferay-ui:custom-attributes-available className="<%= DLFileEntryConstants.getClassName() %>" classPK="<%= fileVersionId %>" editable="<%= false %>">

			<%
			showCustomFieldsTab = true;
			%>

		</liferay-ui:custom-attributes-available>

		<div class="sidebar sidebar-default sidenav-menu">

			<%
			String tabsNames = "details";

			if (showCustomFieldsTab) {
				tabsNames += ",custom-fields";
			}

			if (dlViewFileVersionDisplayContext.getDDMStructuresCount() > 0) {
				tabsNames += ",document-type";
			}

			tabsNames += ",metadata";

			if (dlViewFileVersionDisplayContext.isVersionInfoVisible()) {
				tabsNames += ",versions";
			}
			%>

			<liferay-ui:tabs names="<%= tabsNames %>" refresh="<%= false %>" type="dropdown">
				<liferay-ui:section>
					<div class="sidebar-body">
						<c:if test="<%= dlViewFileVersionDisplayContext.isVersionInfoVisible() %>">
							<h3 class="version <%= fileEntry.isCheckedOut() ? "icon-lock" : StringPool.BLANK %>">
								<liferay-ui:message key="version" /> <%= HtmlUtil.escape(fileVersion.getVersion()) %>
							</h3>
						</c:if>

						<div>
							<aui:model-context bean="<%= fileVersion %>" model="<%= DLFileVersion.class %>" />

							<aui:workflow-status model="<%= DLFileEntry.class %>" showIcon="<%= false %>" showLabel="<%= false %>" status="<%= fileVersion.getStatus() %>" />
						</div>

						<div>
							<h5><strong><liferay-ui:message key="created" /></strong></h5>

							<p>

								<liferay-ui:message arguments="<%= new Object[] {HtmlUtil.escape(fileVersion.getUserName()), dateFormatDateTime.format(fileVersion.getCreateDate())} %>" key="by-x-on-x" translateArguments="<%= false %>" />
							</p>
						</div>

						<div>
							<h5><strong><liferay-ui:message key="modified" /></strong></h5>

							<p>
								<liferay-ui:message arguments="<%= new Object[] {HtmlUtil.escape(fileVersion.getStatusByUserName()), dateFormatDateTime.format(fileVersion.getModifiedDate())} %>" key="by-x-on-x" translateArguments="<%= false %>" />
							</p>
						</div>

						<c:if test="<%= Validator.isNotNull(fileVersion.getDescription()) %>">
							<h5><strong><liferay-ui:message key="description" /></strong></h5>

							<p>
								<%= fileEntry.getDescription() %>
							</p>
						</c:if>

						<c:if test="<%= dlViewFileVersionDisplayContext.isDownloadLinkVisible() %>">
							<span class="download-document">
								<liferay-ui:icon
									iconCssClass="icon-download"
									label="<%= true %>"
									message='<%= LanguageUtil.get(resourceBundle, "download") + " (" + TextFormatter.formatStorageSize(fileVersion.getSize(), locale) + ")" %>'
									url="<%= DLUtil.getDownloadURL(fileEntry, fileVersion, themeDisplay, StringPool.BLANK) %>"
								/>
							</span>

							<span class="conversions">

								<%
								for (int i = 0; i < conversions.length; i++) {
									String conversion = conversions[i];
								%>

									<liferay-ui:icon
										iconCssClass="<%= DLUtil.getFileIconCssClass(conversion) %>"
										label="<%= true %>"
										message='<%= LanguageUtil.get(resourceBundle, "download") + " (" + TextFormatter.formatStorageSize(fileVersion.getSize(), locale) + ")" %>'
										method="get"
										url="<%= DLUtil.getDownloadURL(fileEntry, fileVersion, themeDisplay, StringPool.BLANK) %>"
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

							<div class="hide lfr-asset-field url-file-container">
								<aui:input name="url" type="resource" value="<%= DLUtil.getPreviewURL(fileEntry, fileEntry.getFileVersion(), themeDisplay, StringPool.BLANK, false, true) %>" />
							</div>

							<c:if test="<%= portletDisplay.isWebDAVEnabled() && fileEntry.isSupportsSocial() %>">
								<div class="hide lfr-asset-field webdav-url-file-container">

									<%
									String webDavHelpMessage = null;

									if (BrowserSnifferUtil.isWindows(request)) {
										webDavHelpMessage = LanguageUtil.format(resourceBundle, "webdav-windows-help", new Object[] {"http://www.microsoft.com/downloads/details.aspx?FamilyId=17C36612-632E-4C04-9382-987622ED1D64", "http://www.liferay.com/web/guest/community/wiki/-/wiki/Main/WebDAV"}, false);
									}
									else {
										webDavHelpMessage = LanguageUtil.format(resourceBundle, "webdav-help", "http://www.liferay.com/web/guest/community/wiki/-/wiki/Main/WebDAV", false);
									}
									%>

									<aui:input helpMessage="<%= webDavHelpMessage %>" name="webDavURL" type="resource" value="<%= webDavURL %>" />
								</div>
							</c:if>
						</c:if>
					</div>
				</liferay-ui:section>

				<%
				request.removeAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);
				%>

				<c:if test="<%= dlViewFileVersionDisplayContext.getDDMStructuresCount() > 0 %>">
					<liferay-ui:section>

						<%
						try {
							List<DDMStructure> ddmStructures = dlViewFileVersionDisplayContext.getDDMStructures();

							for (DDMStructure ddmStructure : ddmStructures) {
								DDMFormValues ddmFormValues = null;

								try {
									ddmFormValues = dlViewFileVersionDisplayContext.getDDMFormValues(ddmStructure);
								}
								catch (Exception e) {
								}
						%>

								<liferay-ui:panel collapsible="<%= true %>" cssClass="metadata" extended="<%= true %>" id="documentLibraryMetadataPanel" markupView="lexicon" persistState="<%= true %>" title="<%= HtmlUtil.escape(ddmStructure.getName(locale)) %>">

									<liferay-ddm:html
										classNameId="<%= PortalUtil.getClassNameId(com.liferay.dynamic.data.mapping.model.DDMStructure.class) %>"
										classPK="<%= ddmStructure.getPrimaryKey() %>"
										ddmFormValues="<%= DDMBeanTranslatorUtil.translate(ddmFormValues) %>"
										fieldsNamespace="<%= String.valueOf(ddmStructure.getPrimaryKey()) %>"
										readOnly="<%= true %>"
										requestedLocale="<%= locale %>"
										showEmptyFieldLabel="<%= false %>"
									/>
								</liferay-ui:panel>

						<%
							}
						}
						catch (Exception e) {
						}
						%>

					</liferay-ui:section>
				</c:if>

				<c:if test="<%= showCustomFieldsTab %>">
					<liferay-ui:section>
						<liferay-ui:custom-attribute-list
							className="<%= DLFileEntryConstants.getClassName() %>"
							classPK="<%= fileVersionId %>"
							editable="<%= false %>"
							label="<%= true %>"
						/>
					</liferay-ui:section>
				</c:if>

				<liferay-ui:section>

					<%
					try {
						List<DDMStructure> ddmStructures = DDMStructureManagerUtil.getClassStructures(company.getCompanyId(), PortalUtil.getClassNameId(RawMetadataProcessor.class), DDMStructureManager.STRUCTURE_COMPARATOR_STRUCTURE_KEY);

						for (DDMStructure ddmStructure : ddmStructures) {
							com.liferay.dynamic.data.mapping.storage.DDMFormValues ddmFormValues = null;

							try {
								DLFileEntryMetadata fileEntryMetadata = DLFileEntryMetadataLocalServiceUtil.getFileEntryMetadata(ddmStructure.getStructureId(), fileVersionId);

								ddmFormValues = StorageEngineUtil.getDDMFormValues(fileEntryMetadata.getDDMStorageId());

							}
							catch (Exception e) {
							}

							if (ddmFormValues != null) {
								String name = "metadata." + ddmStructure.getName(locale, true);
					%>

								<liferay-ui:panel collapsible="<%= true %>" cssClass="lfr-asset-metadata" id="documentLibraryAssetMetadataPanel" markupView="lexicon" persistState="<%= true %>" title="<%= name %>">

									<liferay-ddm:html
										classNameId="<%= PortalUtil.getClassNameId(com.liferay.dynamic.data.mapping.model.DDMStructure.class) %>"
										classPK="<%= ddmStructure.getPrimaryKey() %>"
										ddmFormValues="<%= ddmFormValues %>"
										fieldsNamespace="<%= String.valueOf(ddmStructure.getPrimaryKey()) %>"
										readOnly="<%= true %>"
										requestedLocale="<%= locale %>"
										showEmptyFieldLabel="<%= false %>"
									/>
								</liferay-ui:panel>

					<%
							}
						}
					}
					catch (Exception e) {
					}
					%>

				</liferay-ui:section>

				<c:if test="<%= dlViewFileVersionDisplayContext.isVersionInfoVisible() %>">
					<liferay-ui:section>
						<div class="sidebar-body">

							<%
							request.setAttribute("info_panel.jsp-fileEntry", fileEntry);
							%>

							<liferay-util:include page="/document_library/file_entry_history.jsp" servletContext="<%= application %>" />
						</div>
					</liferay-ui:section>
				</c:if>
			</liferay-ui:tabs>
		</div>
	</div>

	<div class="sidenav-content">
		<div class="alert alert-danger hide" id="<portlet:namespace />openMSOfficeError"></div>

		<c:if test="<%= (fileEntry.getLock() != null) && DLFileEntryPermission.contains(permissionChecker, fileEntry, ActionKeys.UPDATE) %>">
			<c:choose>
				<c:when test="<%= fileEntry.hasLock() %>">
					<div class="alert alert-success">
						<c:choose>
							<c:when test="<%= lock.isNeverExpires() %>">
								<liferay-ui:message key="you-now-have-an-indefinite-lock-on-this-document" />
							</c:when>
							<c:otherwise>

								<%
								String lockExpirationTime = StringUtil.toLowerCase(LanguageUtil.getTimeDescription(request, DLFileEntryConstants.LOCK_EXPIRATION_TIME));
								%>

								<liferay-ui:message arguments="<%= lockExpirationTime %>" key="you-now-have-a-lock-on-this-document" translateArguments="<%= false %>" />
							</c:otherwise>
						</c:choose>
					</div>
				</c:when>
				<c:otherwise>
					<div class="alert alert-danger">
						<liferay-ui:message arguments="<%= new Object[] {HtmlUtil.escape(PortalUtil.getUserName(lock.getUserId(), String.valueOf(lock.getUserId()))), dateFormatDateTime.format(lock.getCreateDate())} %>" key="you-cannot-modify-this-document-because-it-was-locked-by-x-on-x" translateArguments="<%= false %>" />
					</div>
				</c:otherwise>
			</c:choose>
		</c:if>

		<liferay-util:buffer var="documentTitle">
			<%= fileVersion.getTitle() %>

			<c:if test="<%= versionSpecific %>">
				(<liferay-ui:message key="version" /> <%= fileVersion.getVersion() %>)
			</c:if>
		</liferay-util:buffer>

		<div class="body-row">
			<div class="document-info panel panel-body">
				<h2 title="<%= HtmlUtil.escapeAttribute(documentTitle) %>">
					<%= HtmlUtil.escape(documentTitle) %>
				</h2>

				<span class="document-thumbnail">

					<%
					String thumbnailSrc = DLUtil.getThumbnailSrc(fileEntry, fileVersion, themeDisplay);

					if (layoutAssetEntry != null) {
						AssetEntry incrementAssetEntry = AssetEntryServiceUtil.incrementViewCounter(layoutAssetEntry.getClassName(), fileEntry.getFileEntryId());

						if (incrementAssetEntry != null) {
							layoutAssetEntry = incrementAssetEntry;
						}
					}
					%>

					<img alt="<liferay-ui:message escapeAttribute="<%= true %>" key="thumbnail" />" class="thumbnail" src="<%= thumbnailSrc %>" style="<%= DLUtil.getThumbnailStyle(true, 0, 128, 128) %>" />
				</span>

				<span class="user-date">

					<%
					String displayURL = StringPool.BLANK;

					User userDisplay = UserLocalServiceUtil.fetchUser(fileEntry.getUserId());

					if (userDisplay != null) {
						displayURL = userDisplay.getDisplayURL(themeDisplay);
					}
					%>

					<liferay-ui:icon iconCssClass="icon-plus" label="<%= true %>" message='<%= LanguageUtil.format(resourceBundle, "uploaded-by-x-x", new Object[] {displayURL, HtmlUtil.escape(fileEntry.getUserName()), dateFormatDateTime.format(fileEntry.getCreateDate())}, false) %>' />
				</span>

				<c:if test="<%= dlPortletInstanceSettings.isEnableRatings() && fileEntry.isSupportsSocial() %>">
					<span class="lfr-asset-ratings">
						<liferay-ui:ratings
							className="<%= DLFileEntryConstants.getClassName() %>"
							classPK="<%= fileEntryId %>"
						/>
					</span>
				</c:if>

				<c:if test="<%= (layoutAssetEntry != null) && dlPortletInstanceSettings.isEnableRelatedAssets() && fileEntry.isSupportsSocial() %>">
					<div class="entry-links">
						<liferay-ui:asset-links
							assetEntryId="<%= layoutAssetEntry.getEntryId() %>"
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

			<c:if test="<%= PropsValues.DL_FILE_ENTRY_PREVIEW_ENABLED %>">

				<%
				PortalIncludeUtil.include(
					pageContext,
					new PortalIncludeUtil.HTMLRenderer() {

						@Override
						public void renderHTML(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
							dlViewFileVersionDisplayContext.renderPreview(request, response);
						}

					});
				%>

			</c:if>

			<c:if test="<%= PropsValues.DL_FILE_ENTRY_COMMENTS_ENABLED && showComments %>">
				<liferay-ui:panel collapsible="<%= true %>" cssClass="lfr-document-library-comments" extended="<%= true %>" markupView="lexicon" persistState="<%= true %>" title="comments">
					<liferay-ui:discussion
						className="<%= DLFileEntryConstants.getClassName() %>"
						classPK="<%= fileEntryId %>"
						formName="fm2"
						ratingsEnabled="<%= dlPortletInstanceSettings.isEnableCommentRatings() %>"
						redirect="<%= currentURL %>"
						userId="<%= fileEntry.getUserId() %>"
					/>
				</liferay-ui:panel>
			</c:if>
		</div>
	</div>
</div>

<c:if test="<%= dlPortletInstanceSettingsHelper.isShowActions() %>">
	<%@ include file="/document_library/version_details.jspf" %>
</c:if>

<aui:script>
	function <portlet:namespace />compare() {
		var rowIds = AUI.$('input[name=<portlet:namespace />rowIds]:checked');
		var sourceFileVersionId = AUI.$('input[name="<portlet:namespace />sourceFileVersionId"]');
		var targetFileVersionId = AUI.$('input[name="<portlet:namespace />targetFileVersionId"]');

		var rowIdsSize = rowIds.length;

		if (rowIdsSize == 1) {
			sourceFileVersionId.val(rowIds.eq(0).val());
		}
		else if (rowIdsSize == 2) {
			sourceFileVersionId.val(rowIds.eq(1).val());

			targetFileVersionId.val(rowIds.eq(0).val());
		}

		submitForm(document.<portlet:namespace />fm1);
	}

	function <portlet:namespace />initRowsChecked() {
		AUI.$('input[name=<portlet:namespace />rowIds]').each(
			function(index, item) {
				if (index >= 2) {
					item = AUI.$(item);

					item.prop('checked', false);
				}
			}
		);
	}

	function <portlet:namespace />updateRowsChecked(element) {
		var rowsChecked = AUI.$('input[name=<portlet:namespace />rowIds]:checked');

		if (rowsChecked.length > 2) {
			var index = 2;

			if (rowsChecked.eq(2).is(element)) {
				index = 1;
			}

			rowsChecked.eq(index).prop('checked', false);
		}
	}
</aui:script>

<c:if test="<%= DLFileEntryPermission.contains(permissionChecker, fileEntry, ActionKeys.VIEW) && DLUtil.isOfficeExtension(fileVersion.getExtension()) && portletDisplay.isWebDAVEnabled() && BrowserSnifferUtil.isIeOnWin32(request) %>">
	<%@ include file="/document_library/action/open_document_js.jspf" %>
</c:if>

<c:if test="<%= dlPortletInstanceSettingsHelper.isShowActions() && !portletTitleBasedNavigation %>">
	<aui:script use="aui-toolbar">
		var buttonRow = A.one('#<portlet:namespace />fileEntryToolbar');

		var fileEntryButtonGroup = [];

		<%
		for (ToolbarItem toolbarItem : dlViewFileVersionDisplayContext.getToolbarItems()) {
		%>

			<liferay-ui:toolbar-item toolbarItem="<%= toolbarItem %>" var="fileEntryButtonGroup" />

		<%
		}
		%>

		var fileEntryToolbar = new A.Toolbar(
			{
				boundingBox: buttonRow,
				children: [fileEntryButtonGroup]
			}
		).render();

		buttonRow.setData('fileEntryToolbar', fileEntryToolbar);
	</aui:script>
</c:if>

<aui:script sandbox="<%= true %>">
	$('.show-url-file').on(
		'click',
		function(event) {
			$('.url-file-container').toggleClass('hide');
		}
	);

	$('.show-webdav-url-file').on(
		'click',
		function(event) {
			$('.webdav-url-file-container').toggleClass('hide');
		}
	);

	<portlet:namespace />initRowsChecked();

	$('input[name=<portlet:namespace />rowIds]').on(
		'click',
		function(event) {
			<portlet:namespace />updateRowsChecked($(event.currentTarget));
		}
	);
</aui:script>

<aui:script>
	$('#<portlet:namespace />infoPanelId').sideNavigation(
		{
			gutter: 15,
			position: 'right',
			type: 'relative',
			typeMobile: 'fixed',
			width: 320
		}
	);
</aui:script>

<%
boolean addPortletBreadcrumbEntries = ParamUtil.getBoolean(request, "addPortletBreadcrumbEntries", true);

if (addPortletBreadcrumbEntries) {
	DLBreadcrumbUtil.addPortletBreadcrumbEntries(fileEntry, request, renderResponse);
}
%>

<liferay-util:dynamic-include key="com.liferay.document.library.web#/document_library/view_file_entry.jsp#post" />