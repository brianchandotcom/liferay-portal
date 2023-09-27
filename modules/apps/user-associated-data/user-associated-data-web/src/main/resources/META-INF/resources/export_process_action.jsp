<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

BackgroundTask backgroundTask = (BackgroundTask)row.getObject();
%>

<liferay-ui:icon-menu
	direction="left-side"
	markupView="lexicon"
	showWhenSingleIcon="<%= true %>"
>
	<c:if test="<%= backgroundTask.isCompleted() && (backgroundTask.getAttachmentsFileEntriesCount() > 0) %>">

		<%
		FileEntry fileEntry = UADExportProcessUtil.getFileEntry(backgroundTask);

		StringBundler sb = new StringBundler(5);

		sb.append(LanguageUtil.get(request, "download"));
		sb.append(StringPool.SPACE);
		sb.append(StringPool.OPEN_PARENTHESIS);
		sb.append(LanguageUtil.formatStorageSize(fileEntry.getSize(), locale));
		sb.append(StringPool.CLOSE_PARENTHESIS);
		%>

		<liferay-ui:icon
			data='<%= Collections.singletonMap("senna-off", "true") %>'
			label="<%= true %>"
			markupView="lexicon"
			message="<%= sb.toString() %>"
			method="get"
			url="<%= PortletFileRepositoryUtil.getDownloadPortletFileEntryURL(themeDisplay, fileEntry, StringPool.BLANK) %>"
		/>
	</c:if>

	<portlet:actionURL name="/user_associated_data/delete_uad_export_background_task" var="deleteBackgroundTaskURL">
		<portlet:param name="redirect" value="<%= currentURL %>" />
		<portlet:param name="backgroundTaskId" value="<%= String.valueOf(backgroundTask.getBackgroundTaskId()) %>" />
	</portlet:actionURL>

	<%
	Date completionDate = backgroundTask.getCompletionDate();
	%>

	<liferay-ui:icon-delete
		confirmation='<%= ((completionDate != null) && completionDate.before(new Date())) ? "are-you-sure-you-want-to-delete-this" : "are-you-sure-you-want-to-cancel" %>'
		label="<%= true %>"
		message='<%= ((completionDate != null) && completionDate.before(new Date())) ? "delete" : "cancel" %>'
		url="<%= deleteBackgroundTaskURL.toString() %>"
	/>
</liferay-ui:icon-menu>