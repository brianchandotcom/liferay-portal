<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/admin/init.jsp" %>

<%
EditClientExtensionEntryDisplayContext<ThemeSpritemapCET> editClientExtensionEntryDisplayContext = (EditClientExtensionEntryDisplayContext)renderRequest.getAttribute(ClientExtensionAdminWebKeys.EDIT_CLIENT_EXTENSION_ENTRY_DISPLAY_CONTEXT);

ThemeSpritemapCET themeSpritemapCET = editClientExtensionEntryDisplayContext.getCET();
%>

<aui:input label="url" name="url" required="<%= true %>" type="text" value="<%= themeSpritemapCET.getURL() %>" />

<aui:input helpMessage="enable-this-for-resources-that-are-hosted-on-a-different-domain" label="enable-cross-domain-support" name="enableSVG4Everybody" type="checkbox" value="<%= themeSpritemapCET.isEnableSVG4Everybody() %>" />