<%--
/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/document_library_display/init.jsp" %>

<%
String topLink = ParamUtil.getString(request, "topLink", "home");

String redirect = ParamUtil.getString(request, "redirect");

Folder folder = (Folder)request.getAttribute(WebKeys.DOCUMENT_LIBRARY_FOLDER);

long defaultFolderId = GetterUtil.getLong(preferences.getValue("rootFolderId", StringPool.BLANK), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID);

long folderId = BeanParamUtil.getLong(folder, request, "folderId", defaultFolderId);

if ((folder == null) && (defaultFolderId != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID)) {
	try {
		folder = DLAppLocalServiceUtil.getFolder(folderId);
	}
	catch (NoSuchFolderException nsfe) {
		folderId = DLFolderConstants.DEFAULT_PARENT_FOLDER_ID;
	}
}

long repositoryId = scopeGroupId;

if (folder != null) {
	repositoryId = folder.getRepositoryId();
}

int status = WorkflowConstants.STATUS_APPROVED;

if (permissionChecker.isCompanyAdmin() || permissionChecker.isGroupAdmin(scopeGroupId)) {
	status = WorkflowConstants.STATUS_ANY;
}

int foldersCount = DLAppServiceUtil.getFoldersCount(repositoryId, folderId);
int fileEntriesCount = DLAppServiceUtil.getFileEntriesAndFileShortcutsCount(repositoryId, folderId, status);

long assetCategoryId = ParamUtil.getLong(request, "categoryId");
String assetTagName = ParamUtil.getString(request, "tag");

String assetCategoryTitle = null;
String assetVocabularyTitle = null;

if (assetCategoryId != 0) {
	AssetCategory assetCategory = AssetCategoryLocalServiceUtil.getAssetCategory(assetCategoryId);

	assetCategory = assetCategory.toEscapedModel();

	assetCategoryTitle = assetCategory.getTitle(locale);

	AssetVocabulary assetVocabulary = AssetVocabularyLocalServiceUtil.getAssetVocabulary(assetCategory.getVocabularyId());

	assetVocabulary = assetVocabulary.toEscapedModel();

	assetVocabularyTitle = assetVocabulary.getTitle(locale);
}

boolean useAssetEntryQuery = (assetCategoryId > 0) || Validator.isNotNull(assetTagName);

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("struts_action", "/document_library_display/view");
portletURL.setParameter("topLink", topLink);
portletURL.setParameter("folderId", String.valueOf(folderId));

request.setAttribute("view.jsp-folder", folder);

request.setAttribute("view.jsp-defaultFolderId", String.valueOf(defaultFolderId));

request.setAttribute("view.jsp-folderId", String.valueOf(folderId));

request.setAttribute("view.jsp-repositoryId", String.valueOf(repositoryId));

request.setAttribute("view.jsp-viewFolder", Boolean.TRUE.toString());

request.setAttribute("view.jsp-useAssetEntryQuery", String.valueOf(useAssetEntryQuery));
%>

<liferay-util:include page="/html/portlet/document_library/top_links.jsp" />

<c:choose>
	<c:when test="<%= useAssetEntryQuery %>">
		<c:choose>
			<c:when test="<%= Validator.isNotNull(assetCategoryTitle) && Validator.isNotNull(assetTagName) %>">
				<h1 class="entry-title">
					<liferay-ui:message arguments="<%= new String[] {assetVocabularyTitle, assetCategoryTitle, assetTagName} %>" key="documents-with-x-x-and-tag-x" />
				</h1>
			</c:when>
			<c:otherwise>
				<c:if test="<%= Validator.isNotNull(assetCategoryTitle) %>">
					<h1 class="entry-title">
						<liferay-ui:message arguments="<%= new String[] {assetVocabularyTitle, assetCategoryTitle} %>" key="documents-with-x-x" />
					</h1>
				</c:if>

				<c:if test="<%= Validator.isNotNull(assetTagName) %>">
					<h1 class="entry-title">
						<liferay-ui:message arguments="<%= assetTagName %>" key="documents-with-tag-x" />
					</h1>
				</c:if>
			</c:otherwise>
		</c:choose>

		<%@ include file="/html/portlet/document_library_display/view_file_entries.jspf" %>

		<%
		PortalUtil.addPageKeywords(assetTagName, request);
		PortalUtil.addPageKeywords(assetCategoryTitle, request);
		%>

	</c:when>
	<c:when test='<%= topLink.equals("home") %>'>
		<aui:layout>
			<c:if test="<%= (folder != null) %>">
				<liferay-ui:header
					backURL="<%= redirect %>"
					localizeTitle="<%= false %>"
					title="<%= folder.getName() %>"
				/>
			</c:if>

			<aui:column columnWidth="<%= showFolderMenu ? 75 : 100 %>" cssClass="lfr-asset-column lfr-asset-column-details" first="<%= true %>">
				<liferay-ui:panel-container extended="<%= false %>" id="documentLibraryDisplayInfoPanelContainer" persistState="<%= true %>">
					<c:if test="<%= folder != null %>">
						<c:if test="<%= Validator.isNotNull(folder.getDescription()) %>">
							<div class="lfr-asset-description">
								<%= HtmlUtil.escape(folder.getDescription()) %>
							</div>
						</c:if>

						<div class="lfr-asset-metadata">
							<div class="lfr-asset-icon lfr-asset-date">
								<%= LanguageUtil.format(pageContext, "last-updated-x", dateFormatDateTime.format(folder.getModifiedDate())) %>
							</div>

							<div class="lfr-asset-icon lfr-asset-subfolders">
								<%= foldersCount %> <liferay-ui:message key='<%= (foldersCount == 1) ? "subfolder" : "subfolders" %>' />
							</div>

							<div class="lfr-asset-icon lfr-asset-items last">
								<%= fileEntriesCount %> <liferay-ui:message key='<%= (fileEntriesCount == 1) ? "document" : "documents" %>' />
							</div>
						</div>

						<liferay-ui:custom-attributes-available className="<%= DLFolderConstants.getClassName() %>">
							<liferay-ui:custom-attribute-list
								className="<%= DLFolderConstants.getClassName() %>"
								classPK="<%= (folder != null) ? folder.getFolderId() : 0 %>"
								editable="<%= false %>"
								label="<%= true %>"
							/>
						</liferay-ui:custom-attributes-available>
					</c:if>

					<c:if test="<%= foldersCount > 0 %>">
						<liferay-ui:panel collapsible="<%= true %>" cssClass="view-folders" extended="<%= true %>" id="documentLibraryDisplayFoldersListingPanel" persistState="<%= true %>" title='<%= (folder != null) ? "subfolders" : "folders" %>'>
							<liferay-ui:search-container
								curParam="cur1"
								delta="<%= foldersPerPage %>"
								deltaConfigurable="<%= false %>"
								headerNames="<%= StringUtil.merge(folderColumns) %>"
								iteratorURL="<%= portletURL %>"
							>
								<liferay-ui:search-container-results
									results="<%= DLAppServiceUtil.getFolders(repositoryId, folderId, searchContainer.getStart(), searchContainer.getEnd()) %>"
									total="<%= foldersCount %>"
								/>

								<liferay-ui:search-container-row
									className="com.liferay.portal.kernel.repository.model.Folder"
									escapedModel="<%= true %>"
									keyProperty="folderId"
									modelVar="curFolder"
									rowVar="row"
								>
									<liferay-portlet:renderURL varImpl="rowURL">
										<portlet:param name="struts_action" value="/document_library_display/view" />
										<portlet:param name="redirect" value="<%= currentURL %>" />
										<portlet:param name="folderId" value="<%= String.valueOf(curFolder.getFolderId()) %>" />
									</liferay-portlet:renderURL>

									<%@ include file="/html/portlet/document_library_display/folder_columns.jspf" %>
								</liferay-ui:search-container-row>

								<liferay-ui:search-iterator />
							</liferay-ui:search-container>
						</liferay-ui:panel>
					</c:if>

					<liferay-ui:panel collapsible="<%= true %>" extended="<%= true %>" id="documentLibraryDisplayDocumentsListingPanel" persistState="<%= true %>" title="documents">
						<%@ include file="/html/portlet/document_library_display/view_file_entries.jspf" %>
					</liferay-ui:panel>
				</liferay-ui:panel-container>
			</aui:column>

			<c:if test="<%= showFolderMenu %>">
				<aui:column columnWidth="<%= 25 %>" cssClass="lfr-asset-column lfr-asset-column-actions" last="<%= true %>">
					<div class="lfr-asset-summary">
						<liferay-ui:icon
							cssClass="lfr-asset-avatar"
							image='<%= "../file_system/large/" + (((foldersCount + fileEntriesCount) > 0) ? "folder_full_document" : "folder_empty") %>'
							message=""
						/>

						<div class="lfr-asset-name">
							<h4><%= (folder != null) ? folder.getName() : LanguageUtil.get(pageContext, "home") %></h4>
						</div>
					</div>

					<%
					request.removeAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);
					%>

					<liferay-util:include page="/html/portlet/document_library/folder_action.jsp" />
				</aui:column>
			</c:if>
		</aui:layout>

		<%
		if (folder != null) {
			DLUtil.addPortletBreadcrumbEntries(folder, request, renderResponse);

			PortalUtil.setPageSubtitle(folder.getName(), request);
			PortalUtil.setPageDescription(folder.getDescription(), request);
		}
		%>

	</c:when>
	<c:when test='<%= topLink.equals("mine") || topLink.equals("recent") %>'>
		<aui:layout>
			<liferay-ui:header
				backURL="<%= redirect %>"
				title="<%= topLink %>"
			/>

			<liferay-ui:search-container
				delta="<%= fileEntriesPerPage %>"
				deltaConfigurable="<%= false %>"
				emptyResultsMessage="there-are-no-documents"
				iteratorURL="<%= portletURL %>"
			>

				<%
				long groupFileEntriesUserId = 0;

				if (topLink.equals("mine") && themeDisplay.isSignedIn()) {
					groupFileEntriesUserId = user.getUserId();
				}
				%>

				<liferay-ui:search-container-results
					results="<%= DLAppServiceUtil.getGroupFileEntries(repositoryId, groupFileEntriesUserId, defaultFolderId, searchContainer.getStart(), searchContainer.getEnd()) %>"
					total="<%= DLAppServiceUtil.getGroupFileEntriesCount(repositoryId, groupFileEntriesUserId, defaultFolderId) %>"
				/>

				<liferay-ui:search-container-row
					className="com.liferay.portal.kernel.repository.model.FileEntry"
					escapedModel="<%= true %>"
					keyProperty="fileEntryId"
					modelVar="fileEntry"
				>

					<%
					DLFileShortcut fileShortcut = null;

					String rowHREF = null;

					if (DLFileEntryPermission.contains(permissionChecker, fileEntry, ActionKeys.VIEW)) {
						PortletURL viewFileEntryURL = renderResponse.createRenderURL();

						viewFileEntryURL.setParameter("struts_action", "/document_library_display/view_file_entry");
						viewFileEntryURL.setParameter("redirect", currentURL);
						viewFileEntryURL.setParameter("fileEntryId", String.valueOf(fileEntry.getFileEntryId()));

						rowHREF = viewFileEntryURL.toString();
					}
					%>

					<%@ include file="/html/portlet/document_library_display/file_entry_columns.jspf" %>
				</liferay-ui:search-container-row>

				<liferay-ui:search-iterator />
			</liferay-ui:search-container>
		</aui:layout>

		<%
		PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(pageContext, topLink), currentURL);

		PortalUtil.setPageSubtitle(LanguageUtil.get(pageContext, topLink), request);
		%>

	</c:when>
</c:choose>

<%!
private static Log _log = LogFactoryUtil.getLog("portal-web.docroot.html.portlet.document_library.view_jsp");
%>