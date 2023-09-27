<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
UnicodeProperties groupTypeSettingsUnicodeProperties = (UnicodeProperties)request.getAttribute("site.groupTypeSettings");
Group siteGroup = (Group)request.getAttribute("site.group");
%>

<aui:field-wrapper cssClass="form-group">
	<aui:input inlineLabel="right" label="enable-directory-indexing" labelCssClass="simple-toggle-switch" name="TypeSettingsProperties--directoryIndexingEnabled--" type="toggle-switch" value='<%= PropertiesParamUtil.getBoolean(groupTypeSettingsUnicodeProperties, request, "directoryIndexingEnabled") %>' />

	<p class="small text-secondary"><liferay-ui:message arguments='<%= new Object[] {HtmlUtil.escape(siteGroup.getDescriptiveName(themeDisplay.getLocale())), themeDisplay.getPortalURL() + "/documents" + siteGroup.getFriendlyURL()} %>' key="can-user-with-view-permission-browse-the-site-document-library-files-and-folders" translateArguments="<%= false %>" /></p>
</aui:field-wrapper>