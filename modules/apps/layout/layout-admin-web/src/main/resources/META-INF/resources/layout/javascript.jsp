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
Layout selLayout = layoutsAdminDisplayContext.getSelLayout();

UnicodeProperties layoutTypeSettings = null;

if (selLayout != null) {
	layoutTypeSettings = selLayout.getTypeSettingsProperties();
}
%>

<liferay-ui:error-marker key="errorSection" value="javascript" />

<aui:model-context bean="<%= selLayout %>" model="<%= Layout.class %>" />

<aui:input cssClass="lfr-textarea-container" label="paste-javascript-code-that-is-executed-at-the-bottom-of-the-page" name="TypeSettingsProperties--javascript--" style="height: 300px" type="textarea" value='<%= layoutTypeSettings.getProperty("javascript") %>' wrap="soft" />