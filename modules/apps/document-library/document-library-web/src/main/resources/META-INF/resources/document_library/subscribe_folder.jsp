<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

DLGroupServiceSettings dlGroupServiceSettings = dlRequestHelper.getDLGroupServiceSettings();

boolean emailFileEntryAnyEventEnabled = dlGroupServiceSettings.isEmailFileEntryAddedEnabled() || dlGroupServiceSettings.isEmailFileEntryUpdatedEnabled();
%>

<c:if test="<%= DLFolderPermission.contains(permissionChecker, scopeGroupId, folderId, ActionKeys.SUBSCRIBE) && ((folder == null) || folder.isSupportsSubscribing()) && emailFileEntryAnyEventEnabled %>">

	<%
	boolean subscribed = false;
	boolean unsubscribable = true;

	if (fileEntryTypeId == DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_ALL) {
		subscribed = DLSubscriptionUtil.isSubscribedToFolder(themeDisplay.getCompanyId(), scopeGroupId, user.getUserId(), folderId);

		if (subscribed && (folder != null) && DLSubscriptionUtil.isSubscribedToFolder(themeDisplay.getCompanyId(), scopeGroupId, user.getUserId(), folder.getParentFolderId())) {
			unsubscribable = false;
		}
	}
	else {
		subscribed = DLSubscriptionUtil.isSubscribedToFileEntryType(themeDisplay.getCompanyId(), scopeGroupId, user.getUserId(), fileEntryTypeId);
	}
	%>

	<c:choose>
		<c:when test="<%= subscribed %>">
			<c:choose>
				<c:when test="<%= unsubscribable %>">
					<portlet:actionURL name='<%= (fileEntryTypeId == DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_ALL) ? "/document_library/edit_folder" : "/document_library/unsubscribe_file_entry_type" %>' var="unsubscribeURL">
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

					<clay:link
						aria-label='<%= LanguageUtil.get(request, "unsubscribe") %>'
						borderless="<%= true %>"
						cssClass="lfr-portal-tooltip"
						displayType="secondary"
						href="<%= unsubscribeURL %>"
						icon="bell-off"
						monospaced="<%= true %>"
						small="<%= true %>"
						title='<%= LanguageUtil.get(request, "unsubscribe") %>'
						type="button"
					/>
				</c:when>
				<c:otherwise>
					<clay:button
						borderless="<%= true %>"
						cssClass="lfr-portal-tooltip"
						disabled="<%= true %>"
						displayType="secondary"
						monospaced="<%= true %>"
						small="<%= true %>"
						title='<%= LanguageUtil.get(request, "subscribed-to-a-parent-folder") %>'
					>
						<clay:icon
							aria-label='<%= LanguageUtil.get(request, "subscribed-to-a-parent-folder") %>'
							symbol="bell-off"
							title='<%= LanguageUtil.get(request, "subscribed-to-a-parent-folder") %>'
						/>
					</clay:button>
				</c:otherwise>
			</c:choose>
		</c:when>
		<c:otherwise>
			<portlet:actionURL name='<%= (fileEntryTypeId == DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_ALL) ? "/document_library/edit_folder" : "/document_library/subscribe_file_entry_type" %>' var="subscribeURL">
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

			<clay:link
				aria-label='<%= LanguageUtil.get(request, "subscribe") %>'
				borderless="<%= true %>"
				cssClass="lfr-portal-tooltip"
				displayType="secondary"
				href="<%= subscribeURL %>"
				icon="bell-on"
				monospaced="<%= true %>"
				small="<%= true %>"
				title='<%= LanguageUtil.get(request, "subscribe") %>'
				type="button"
			/>
		</c:otherwise>
	</c:choose>
</c:if>