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

<%@ include file="/wiki/init.jsp" %>

<%
WikiPage wikiPage = (WikiPage)request.getAttribute(WikiWebKeys.WIKI_PAGE);

boolean copyPageAttachments = ParamUtil.getBoolean(request, "copyPageAttachments", true);

List<FileEntry> attachmentsFileEntries = null;

if (wikiPage != null) {
	attachmentsFileEntries = wikiPage.getAttachmentsFileEntries();
}

long templateNodeId = ParamUtil.getLong(request, "templateNodeId");
String templateTitle = ParamUtil.getString(request, "templateTitle");

WikiPage templatePage = null;

if ((templateNodeId > 0) && Validator.isNotNull(templateTitle)) {
	try {
		templatePage = WikiPageServiceUtil.getPage(templateNodeId, templateTitle);
	}
	catch (Exception e) {
	}
}
%>

<c:if test="<%= (templatePage != null) && (templatePage.getAttachmentsFileEntriesCount() > 0) %>">

	<%
	attachmentsFileEntries = templatePage.getAttachmentsFileEntries();
	%>

	<aui:input name="copyPageAttachments" type="checkbox" value="<%= copyPageAttachments %>" />
</c:if>

<c:if test="<%= attachmentsFileEntries != null %>">

	<%
	for (int i = 0; i < attachmentsFileEntries.size(); i++) {
		FileEntry attachmentsFileEntry = attachmentsFileEntries.get(i);
	%>

		<aui:a href="<%= (templatePage != null) && (templatePage.getAttachmentsFileEntriesCount() > 0) ? PortletFileRepositoryUtil.getDownloadPortletFileEntryURL(themeDisplay, attachmentsFileEntry, StringPool.BLANK) : null %>"><%= attachmentsFileEntry.getTitle() %></aui:a> (<%= TextFormatter.formatStorageSize(attachmentsFileEntry.getSize(), locale) %>)<%= (i < (attachmentsFileEntries.size() - 1)) ? ", " : "" %>

	<%
	}
	%>

</c:if>