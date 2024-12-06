<%--
/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/document_library/init.jsp" %>

<%
FileEntry fileEntry = (FileEntry)request.getAttribute("info_panel.jsp-fileEntry");
%>

<c:if test="<%= DLFileEntryPermission.contains(permissionChecker, fileEntry, ActionKeys.SUBSCRIBE) %>">
	<c:choose>
		<c:when test="<%= DLSubscriptionUtil.isSubscribedToFileEntry(themeDisplay.getCompanyId(), scopeGroupId, user.getUserId(), fileEntry.getFileEntryId()) %>">
			<c:choose>
				<c:when test="<%= !DLSubscriptionUtil.isSubscribedToFolder(themeDisplay.getCompanyId(), scopeGroupId, user.getUserId(), fileEntry.getFolderId()) %>">
					<portlet:actionURL name="/document_library/unsubscribe_file_entry" var="unsubscribeURL">
						<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.UNSUBSCRIBE %>" />
						<portlet:param name="redirect" value="<%= currentURL %>" />
						<portlet:param name="fileEntryId" value="<%= String.valueOf(fileEntry.getFileEntryId()) %>" />
					</portlet:actionURL>

					<liferay-ui:icon
						icon="bell-off"
						linkCssClass="icon-monospaced"
						markupView="lexicon"
						message="unsubscribe"
						url="<%= unsubscribeURL %>"
					/>
				</c:when>
				<c:otherwise>
					<liferay-ui:icon
						icon="bell-off"
						linkCssClass="icon-monospaced"
						markupView="lexicon"
						message="subscribed-to-a-parent-folder"
					/>
				</c:otherwise>
			</c:choose>
		</c:when>
		<c:otherwise>
			<portlet:actionURL name="/document_library/subscribe_file_entry" var="subscribeURL">
				<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.SUBSCRIBE %>" />
				<portlet:param name="redirect" value="<%= currentURL %>" />
				<portlet:param name="fileEntryId" value="<%= String.valueOf(fileEntry.getFileEntryId()) %>" />
			</portlet:actionURL>

			<liferay-ui:icon
				icon="bell-on"
				linkCssClass="icon-monospaced"
				markupView="lexicon"
				message="subscribe"
				url="<%= subscribeURL %>"
			/>
		</c:otherwise>
	</c:choose>
</c:if>