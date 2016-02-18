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
FileEntry fileEntry = (FileEntry)request.getAttribute(WebKeys.DOCUMENT_LIBRARY_FILE_ENTRY);

FileVersion fileVersion = (FileVersion)request.getAttribute(WebKeys.DOCUMENT_LIBRARY_FILE_VERSION);

if (fileVersion == null) {
	if ((user.getUserId() == fileEntry.getUserId()) || permissionChecker.isContentReviewer(user.getCompanyId(), scopeGroupId) || DLFileEntryPermission.contains(permissionChecker, fileEntry, ActionKeys.UPDATE)) {
		fileVersion = fileEntry.getLatestFileVersion();
	}
	else {
		fileVersion = fileEntry.getFileVersion();
	}
}

DLViewFileVersionDisplayContext dlViewFileVersionDisplayContext = dlDisplayContextProvider.getDLViewFileVersionDisplayContext(request, response, fileVersion);
%>

<liferay-util:dynamic-include key="com.liferay.document.library.web#/document_library/file_entry_discussion.jsp#pre" />

<liferay-ui:panel collapsible="<%= true %>" cssClass="lfr-document-library-comments" extended="<%= true %>" markupView="lexicon" persistState="<%= true %>" title="<%= dlViewFileVersionDisplayContext.getDiscussionLabel(locale) %>">
	<liferay-ui:discussion
		className="<%= dlViewFileVersionDisplayContext.getDiscussionClassName() %>"
		classPK="<%= dlViewFileVersionDisplayContext.getDiscussionClassPK() %>"
		formName="fm2"
		ratingsEnabled="<%= dlPortletInstanceSettings.isEnableCommentRatings() %>"
		redirect="<%= currentURL %>"
		userId="<%= fileEntry.getUserId() %>"
	/>
</liferay-ui:panel>

<liferay-util:dynamic-include key="com.liferay.document.library.web#/document_library/file_entry_discussion.jsp#post" />