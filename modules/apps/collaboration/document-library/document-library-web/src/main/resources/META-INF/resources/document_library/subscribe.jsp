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
Folder folder = (Folder)request.getAttribute("info_panel.jsp-folder");

long folderId = DLFolderConstants.DEFAULT_PARENT_FOLDER_ID;

if (folder != null) {
	folderId = folder.getFolderId();
}

long fileEntryTypeId = ParamUtil.getLong(request, "fileEntryTypeId", -1);

boolean emailFileEntryAnyEventEnabled = dlGroupServiceSettings.isEmailFileEntryAddedEnabled() || dlGroupServiceSettings.isEmailFileEntryUpdatedEnabled();
%>

<c:if test="<%= DLFolderPermission.contains(permissionChecker, scopeGroupId, folderId, ActionKeys.SUBSCRIBE) && ((folder == null) || folder.isSupportsSubscribing()) && emailFileEntryAnyEventEnabled %>">

	<%
	boolean subscribed = false;
	boolean unsubscribable = true;

	if (fileEntryTypeId == DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_ALL) {
		subscribed = DLUtil.isSubscribedToFolder(themeDisplay.getCompanyId(), scopeGroupId, user.getUserId(), folderId);

		if (subscribed) {
			if (!DLUtil.isSubscribedToFolder(themeDisplay.getCompanyId(), scopeGroupId, user.getUserId(), folderId, false)) {
				unsubscribable = false;
			}
		}
	}
	else {
		subscribed = DLUtil.isSubscribedToFileEntryType(themeDisplay.getCompanyId(), scopeGroupId, user.getUserId(), fileEntryTypeId);
	}
	%>

	<c:choose>
		<c:when test="<%= subscribed %>">
			<c:choose>
				<c:when test="<%= unsubscribable %>">
					<portlet:actionURL name='<%= (fileEntryTypeId == DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_ALL) ? "/document_library/edit_folder" : "/document_library/edit_file_entry_type" %>' var="unsubscribeURL">
						<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.UNSUBSCRIBE %>" />
						<portlet:param name="redirect" value="<%= currentURL %>" />

						<c:choose>
							<c:when test="<%= fileEntryTypeId == DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_ALL %>">
								<portlet:param name="folderId" value="<%= String.valueOf(folderId) %>" />
							</c:when>
							<c:otherwise>
								<portlet:param name="fileEntryTypeId" value="<%= String.valueOf(fileEntryTypeId) %>" />
							</c:otherwise>
						</c:choose>
					</portlet:actionURL>

					<liferay-ui:icon
						icon="star"
						linkCssClass="icon-monospaced"
						markupView="lexicon"
						message="unsubscribe"
						url="<%= unsubscribeURL %>"
					/>
				</c:when>
				<c:otherwise>
					<liferay-ui:icon
						icon="star"
						linkCssClass="icon-monospaced"
						markupView="lexicon"
						message="subscribed-to-a-parent-folder"
					/>
				</c:otherwise>
			</c:choose>
		</c:when>
		<c:otherwise>
			<portlet:actionURL name='<%= (fileEntryTypeId == DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_ALL) ? "/document_library/edit_folder" : "/document_library/edit_file_entry_type" %>' var="subscribeURL">
				<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.SUBSCRIBE %>" />
				<portlet:param name="redirect" value="<%= currentURL %>" />

				<c:choose>
					<c:when test="<%= fileEntryTypeId == DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_ALL %>">
						<portlet:param name="folderId" value="<%= String.valueOf(folderId) %>" />
					</c:when>
					<c:otherwise>
						<portlet:param name="fileEntryTypeId" value="<%= String.valueOf(fileEntryTypeId) %>" />
					</c:otherwise>
				</c:choose>
			</portlet:actionURL>

			<liferay-ui:icon
				icon="star-o"
				linkCssClass="icon-monospaced"
				markupView="lexicon"
				message="subscribe"
				url="<%= subscribeURL %>"
			/>
		</c:otherwise>
	</c:choose>
</c:if>