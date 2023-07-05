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