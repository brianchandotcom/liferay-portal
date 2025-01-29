<%--
/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
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
			<portlet:actionURL name="/document_library/subscribe_file_entry" var="subscribeURL">
				<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.SUBSCRIBE %>" />
				<portlet:param name="redirect" value="<%= currentURL %>" />
				<portlet:param name="fileEntryId" value="<%= String.valueOf(fileEntry.getFileEntryId()) %>" />
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