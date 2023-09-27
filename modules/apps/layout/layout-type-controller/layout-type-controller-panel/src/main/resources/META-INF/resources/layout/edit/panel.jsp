<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/layout/init.jsp" %>

<%
String description = StringPool.BLANK;

if (selLayout != null) {
	UnicodeProperties typeSettingsUnicodeProperties = selLayout.getTypeSettingsProperties();

	description = typeSettingsUnicodeProperties.getProperty("panelLayoutDescription", StringPool.BLANK);
}
%>

<aui:input cssClass="layout-description" id="descriptionPanel" label="description" name="TypeSettingsProperties--panelLayoutDescription--" type="textarea" value="<%= description %>" wrap="soft" />

<clay:alert
	message="select-the-applications-that-are-available-in-the-panel"
/>

<%
WidgetsTreeDisplayContext widgetsTreeDisplayContext = new WidgetsTreeDisplayContext(request, layoutTypePortlet, user);
%>

<div>
	<react:component
		module="js/WidgetsTree"
		props="<%= widgetsTreeDisplayContext.getData() %>"
	/>
</div>