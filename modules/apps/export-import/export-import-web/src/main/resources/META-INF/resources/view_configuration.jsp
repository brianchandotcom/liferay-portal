<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
long exportImportConfigurationId = (Long)request.getAttribute(ExportImportWebKeys.EXPORT_IMPORT_CONFIGURATION_ID);

ExportImportConfiguration exportImportConfiguration = ExportImportConfigurationLocalServiceUtil.getExportImportConfiguration(exportImportConfigurationId);
%>

<clay:sheet-section>
	<h2 class="sheet-title">
		<liferay-ui:message key="template-type" />
	</h2>

	<liferay-ui:message key="<%= ExportImportConfigurationConstants.getTypeLabel(exportImportConfiguration.getType()) %>" />
</clay:sheet-section>

<clay:sheet-section>
	<h2 class="sheet-title">
		<liferay-ui:message key="created-by" />
	</h2>

	<liferay-ui:message key="<%= exportImportConfiguration.getUserName() %>" />
</clay:sheet-section>

<clay:sheet-section>
	<h2 class="sheet-title">
		<liferay-ui:message key="description" />
	</h2>

	<liferay-ui:message key="<%= HtmlUtil.escape(exportImportConfiguration.getDescription()) %>" />
</clay:sheet-section>