<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/admin/init.jsp" %>

<%
EditClientExtensionEntryDisplayContext<JSImportMapsEntryCET> editClientExtensionEntryDisplayContext = (EditClientExtensionEntryDisplayContext)renderRequest.getAttribute(ClientExtensionAdminWebKeys.EDIT_CLIENT_EXTENSION_ENTRY_DISPLAY_CONTEXT);

JSImportMapsEntryCET jsImportMapsEntryCET = editClientExtensionEntryDisplayContext.getCET();
%>

<aui:input label="bare-specifier" name="bareSpecifier" required="<%= true %>" type="text" value="<%= jsImportMapsEntryCET.getBareSpecifier() %>" />

<aui:input label="js-url" name="url" required="<%= true %>" type="text" value="<%= jsImportMapsEntryCET.getURL() %>" />