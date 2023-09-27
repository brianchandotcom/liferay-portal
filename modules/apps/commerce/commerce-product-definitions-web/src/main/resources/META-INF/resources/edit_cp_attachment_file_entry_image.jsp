<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
CPAttachmentFileEntriesDisplayContext cpAttachmentFileEntriesDisplayContext = (CPAttachmentFileEntriesDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CPAttachmentFileEntry cpAttachmentFileEntry = cpAttachmentFileEntriesDisplayContext.getCPAttachmentFileEntry();
long cpAttachmentFileEntryId = cpAttachmentFileEntriesDisplayContext.getCPAttachmentFileEntryId();
long cpDefinitionId = cpAttachmentFileEntriesDisplayContext.getCPDefinitionId();

int type = CPAttachmentFileEntryConstants.TYPE_IMAGE;

String title = (cpAttachmentFileEntry == null) ? LanguageUtil.get(request, "add-image") : LanguageUtil.format(request, "id-x", String.valueOf(cpAttachmentFileEntry.getCPAttachmentFileEntryId()), false);
%>

<%@ include file="/edit_cp_attachment_file_entry.jspf" %>