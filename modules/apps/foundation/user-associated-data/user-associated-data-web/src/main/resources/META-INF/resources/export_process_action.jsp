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

		Map<String, Object> data = new HashMap<String, Object>();

		data.put("senna-off", "true");

		StringBundler sb = new StringBundler(5);

		sb.append(LanguageUtil.get(request, "download"));
		sb.append(StringPool.SPACE);
		sb.append(StringPool.OPEN_PARENTHESIS);
		sb.append(TextFormatter.formatStorageSize(fileEntry.getSize(), locale));
		sb.append(StringPool.CLOSE_PARENTHESIS);
		%>

		<liferay-ui:icon
			data="<%= data %>"
			label="<%= true %>"
			markupView="lexicon"
			message="<%= sb.toString() %>"
			method="get"
			url="<%= PortletFileRepositoryUtil.getDownloadPortletFileEntryURL(themeDisplay, fileEntry, StringPool.BLANK) %>"
		/>
	</c:if>

	<liferay-portlet:actionURL name="deleteBackgroundTask" portletName="<%= PortletKeys.EXPORT_IMPORT %>" var="deleteBackgroundTaskURL">
		<portlet:param name="redirect" value="<%= currentURL %>" />
		<portlet:param name="backgroundTaskId" value="<%= String.valueOf(backgroundTask.getBackgroundTaskId()) %>" />
	</liferay-portlet:actionURL>

	<%
	Date completionDate = backgroundTask.getCompletionDate();
	%>

	<liferay-ui:icon-delete
		label="<%= true %>"
		message='<%= ((completionDate != null) && completionDate.before(new Date())) ? "delete" : "cancel" %>'
		url="<%= deleteBackgroundTaskURL %>"
	/>
</liferay-ui:icon-menu>