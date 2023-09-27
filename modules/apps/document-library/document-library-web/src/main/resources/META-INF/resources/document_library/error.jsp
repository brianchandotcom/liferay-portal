<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/document_library/init.jsp" %>

<liferay-ui:error-header />

<liferay-ui:error exception="<%= DuplicateLockException.class %>">

	<%
	com.liferay.portal.kernel.lock.Lock lock = (com.liferay.portal.kernel.lock.Lock)errorException;
	%>

	<liferay-ui:message arguments="<%= new Object[] {HtmlUtil.escape(PortalUtil.getUserName(lock.getUserId(), String.valueOf(lock.getUserId()))), dateFormatDateTime.format(lock.getCreateDate())} %>" key="you-cannot-modify-this-document-because-it-was-locked-by-x-on-x" translateArguments="<%= false %>" />
</liferay-ui:error>

<liferay-ui:error exception="<%= InvalidFileVersionException.class %>" message="file-version-is-invalid" />
<liferay-ui:error exception="<%= NoSuchFileEntryException.class %>" message="the-document-could-not-be-found" />
<liferay-ui:error exception="<%= NoSuchFileException.class %>" message="the-document-could-not-be-found" />
<liferay-ui:error exception="<%= NoSuchFileShortcutException.class %>" message="the-document-could-not-be-found" />
<liferay-ui:error exception="<%= NoSuchFolderException.class %>" message="the-folder-could-not-be-found" />
<liferay-ui:error exception="<%= NoSuchRepositoryException.class %>" message="the-repository-could-not-be-found" />
<liferay-ui:error exception="<%= NoSuchStructureException.class %>" message="the-structure-could-not-be-found" />
<liferay-ui:error key="repositoryPingFailed" message="you-cannot-access-the-repository-because-you-are-not-allowed-to-or-it-is-unavailable" />

<liferay-ui:error-principal />