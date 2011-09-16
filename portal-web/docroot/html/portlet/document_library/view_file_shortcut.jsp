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
String strutsAction = ParamUtil.getString(request, "struts_action");

String tabs2 = ParamUtil.getString(request, "tabs2", "version-history");

String redirect = ParamUtil.getString(request, "redirect");

DLFileShortcut fileShortcut = (DLFileShortcut)request.getAttribute(WebKeys.DOCUMENT_LIBRARY_FILE_SHORTCUT);

fileShortcut = fileShortcut.toEscapedModel();

long fileShortcutId = fileShortcut.getFileShortcutId();

Group toGroup = null;

Folder toFolder = null;

long toFileEntryId = fileShortcut.getToFileEntryId();

FileEntry toFileEntry = null;

if (toFileEntryId > 0) {
	try {
		toFileEntry = DLAppLocalServiceUtil.getFileEntry(toFileEntryId);

		toFolder = toFileEntry.getFolder();

		toGroup = GroupLocalServiceUtil.getGroup(toFileEntry.getRepositoryId());
	}
	catch (Exception e) {
	}
}

toFileEntry = toFileEntry.toEscapedModel();

String extension = toFileEntry.getExtension();

String[] conversions = new String[0];

if (PrefsPropsUtil.getBoolean(PropsKeys.OPENOFFICE_SERVER_ENABLED, PropsValues.OPENOFFICE_SERVER_ENABLED)) {
	conversions = (String[])DocumentConversionUtil.getConversions(extension);
}

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("struts_action", strutsAction);
portletURL.setParameter("tabs2", tabs2);
portletURL.setParameter("redirect", redirect);
portletURL.setParameter("fileShortcutId", String.valueOf(fileShortcutId));

request.setAttribute("view_file_shortcut.jsp-fileShortcut", fileShortcut);
%>

<liferay-util:include page="/html/portlet/document_library/top_links.jsp" />

<%
Folder folder = fileShortcut.getFolder();

String parentFolderName = LanguageUtil.get(pageContext, "documents-home");

if (Validator.isNotNull(folder.getName())) {
	parentFolderName = folder.getName();
}
%>

<portlet:renderURL var="backURL">
	<portlet:param name="struts_action" value="/document_library/view" />
	<portlet:param name="folderId" value="<%= String.valueOf(folder.getFolderId()) %>" />
</portlet:renderURL>

<liferay-ui:header
	backLabel="<%= parentFolderName %>"
	backURL="<%= backURL.toString() %>"
	title='<%= LanguageUtil.format(pageContext, "shortcut-to-x", toFileEntry.getTitle()) %>'
/>

<aui:layout>
	<aui:column columnWidth="<%= 75 %>" cssClass="file-entry-column file-entry-column-first" first="<%= true %>">

		<%
		String versionText = LanguageUtil.format(pageContext, "version-x", toFileEntry.getVersion());

		if (Validator.isNull(toFileEntry.getVersion())) {
			versionText = LanguageUtil.get(pageContext, "not-approved");
		}
		%>

		<div class="file-entry-categories">
			<liferay-ui:asset-categories-summary
				className="<%= DLFileEntryConstants.getClassName() %>"
				classPK="<%= toFileEntry.getFileEntryId() %>"
			/>
		</div>

		<div class="file-entry-tags">
			<liferay-ui:asset-tags-summary
				className="<%= DLFileEntryConstants.getClassName() %>"
				classPK="<%= toFileEntry.getFileEntryId() %>"
				message="tags"
			/>
		</div>

		<div class="file-entry-description">
			<%= toFileEntry.getDescription() %>
		</div>

		<liferay-ui:custom-attributes-available className="<%= DLFileEntryConstants.getClassName() %>">
			<liferay-ui:custom-attribute-list
				className="<%= DLFileEntryConstants.getClassName() %>"
				classPK="<%= (toFileEntry != null) ? toFileEntry.getFileEntryId() : 0 %>"
				editable="<%= false %>"
				label="<%= true %>"
			/>
		</liferay-ui:custom-attributes-available>

		<div class="file-entry-author">
			<%= LanguageUtil.format(pageContext, "last-updated-by-x", HtmlUtil.escape(PortalUtil.getUserName(toFileEntry.getUserId(), toFileEntry.getUserName()))) %>
		</div>

		<div class="file-entry-date">
			<%= dateFormatDateTime.format(toFileEntry.getModifiedDate()) %>
		</div>

		<c:if test="<%= PropsValues.DL_FILE_ENTRY_READ_COUNT_ENABLED %>">
			<div class="file-entry-downloads">
				<%= toFileEntry.getReadCount() %> <liferay-ui:message key="downloads" />
			</div>
		</c:if>

		<div class="file-entry-ratings">
			<liferay-ui:ratings
				className="<%= DLFileEntryConstants.getClassName() %>"
				classPK="<%= toFileEntry.getFileEntryId() %>"
			/>
		</div>

		<div class="file-entry-field">
			<label><liferay-ui:message key="url" /></label>

			<liferay-ui:input-resource
				url='<%= themeDisplay.getPortalURL() + themeDisplay.getPathContext() + "/documents/" + fileShortcutId %>'
			/>
		</div>

		<div class="file-entry-field">
			<label><liferay-ui:message key="site" /></label>

			<%= HtmlUtil.escape(toGroup.getDescriptiveName()) %>
		</div>
	</aui:column>

	<aui:column columnWidth="<%= 25 %>" cssClass="detail-column detail-column-last" last="<%= true %>">
		<img alt="" class="shortcut-icon" src="<%= themeDisplay.getPathThemeImages() %>/file_system/large/overlay_link.png">

		<c:if test="<%= toFileEntry.isCheckedOut() %>">
			<img alt="" class="locked-icon" src="<%= themeDisplay.getPathThemeImages() %>/file_system/large/overlay_lock.png">
		</c:if>

		<div class="file-entry-download">
			<liferay-ui:icon
				cssClass="file-entry-avatar"
				image='<%= "../file_system/large/" + DLUtil.getGenericName(extension) %>'
				message="download"
				url='<%= themeDisplay.getPortalURL() + themeDisplay.getPathContext() + "/documents/" + fileShortcutId %>'
			/>

			<div class="file-entry-name">
				<a href="<%= themeDisplay.getPortalURL() + themeDisplay.getPathContext() + "/documents/" + fileShortcutId %>">
					<%= toFileEntry.getTitle() %>
				</a>
			</div>

			<c:if test="<%= conversions.length > 0 %>">
				<div class="file-entry-field file-entry-conversions">
					<label><liferay-ui:message key="other-available-formats" /></label>

					<%
					for (int i = 0; i < conversions.length; i++) {
						String conversion = conversions[i];
					%>

						<liferay-ui:icon
							image='<%= "../file_system/small/" + conversion %>'
							label="<%= true %>"
							message="<%= conversion.toUpperCase() %>"
							url='<%= themeDisplay.getPortalURL() + themeDisplay.getPathContext() + "/documents/" + themeDisplay.getScopeGroupId() + StringPool.SLASH + toFolder.getFolderId() + StringPool.SLASH + HttpUtil.encodeURL(HtmlUtil.unescape(toFileEntry.getTitle())) + "?targetExtension=" + conversion %>'
						/>

					<%
					}
					%>

				</div>
			</c:if>
		</div>

		<liferay-util:include page="/html/portlet/document_library/file_entry_action.jsp" />
	</aui:column>
</aui:layout>

<div class="file-entry-panels">
	<liferay-ui:panel-container extended="<%= false %>" id="documentLibraryDocumentPanelContainer" persistState="<%= true %>">
		<liferay-ui:panel collapsible="<%= true %>" cssClass="version-history" extended="<%= true %>" id="documentLibraryVersionHistoryPanel" persistState="<%= true %>" title="version-history">

			<%
			boolean showNonApprovedDocuments = false;

			if ((user.getUserId() == fileShortcut.getUserId()) || permissionChecker.isCompanyAdmin() || permissionChecker.isGroupAdmin(scopeGroupId)) {
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

			int status = WorkflowConstants.STATUS_APPROVED;

			if (showNonApprovedDocuments) {
				status = WorkflowConstants.STATUS_ANY;
			}

			List results = toFileEntry.getFileVersions(status);
			List resultRows = searchContainer.getResultRows();

			for (int i = 0; i < results.size(); i++) {
				FileVersion fileVersion = (FileVersion)results.get(i);

				ResultRow row = new ResultRow(new Object[] {toFileEntry, fileVersion, results.size(), conversions, toFileEntry.isCheckedOut(), toFileEntry.hasLock()}, fileVersion.getFileVersionId(), i);

				StringBundler sb = new StringBundler(6);

				sb.append(themeDisplay.getPortalURL());
				sb.append(themeDisplay.getPathContext());
				sb.append("/documents/");
				sb.append(fileShortcutId);
				sb.append("?version=");
				sb.append(String.valueOf(fileVersion.getVersion()));

				String rowHREF = sb.toString();

				// Statistics

				row.addText(String.valueOf(fileVersion.getVersion()), rowHREF);
				row.addText(dateFormatDateTime.format(fileVersion.getCreateDate()), rowHREF);
				row.addText(TextFormatter.formatKB(fileVersion.getSize(), locale) + "k", rowHREF);

				// Status

				if (showNonApprovedDocuments) {
					row.addText(LanguageUtil.get(pageContext, WorkflowConstants.toLabel(fileVersion.getStatus())));
				}

				// Action

				row.addJSP("right", SearchEntry.DEFAULT_VALIGN, "/html/portlet/document_library/file_version_action.jsp");

				// Add result row

				resultRows.add(row);
			}
			%>

			<liferay-ui:search-iterator searchContainer="<%= searchContainer %>" paginate="<%= false %>" />
		</liferay-ui:panel>

		<c:if test="<%= PropsValues.DL_FILE_ENTRY_COMMENTS_ENABLED %>">
			<liferay-ui:panel collapsible="<%= true %>" extended="<%= true %>" id="documentLibraryCommentsPanel" persistState="<%= true %>" title="comments">
				<portlet:actionURL var="discussionURL">
					<portlet:param name="struts_action" value="/document_library/edit_file_entry_discussion" />
				</portlet:actionURL>

				<liferay-ui:discussion
					className="<%= DLFileEntryConstants.getClassName() %>"
					classPK="<%= toFileEntry.getFileEntryId() %>"
					formAction="<%= discussionURL %>"
					formName="fm2"
					ratingsEnabled="<%= enableCommentRatings %>"
					redirect="<%= currentURL %>"
					subject="<%= toFileEntry.getTitle() %>"
					userId="<%= toFileEntry.getUserId() %>"
				/>
			</liferay-ui:panel>
		</c:if>
	</liferay-ui:panel-container>
</div>

<%
DLUtil.addPortletBreadcrumbEntries(fileShortcut, request, renderResponse, true);
%>