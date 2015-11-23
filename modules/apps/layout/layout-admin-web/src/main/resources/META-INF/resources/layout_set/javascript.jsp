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
LayoutSet selLayoutSet = layoutsAdminDisplayContext.getSelLayoutSet();

UnicodeProperties layoutSetTypeSettings = selLayoutSet.getSettingsProperties();
%>

<liferay-ui:error-marker key="errorSection" value="javascript" />

<aui:fieldset>
	<aui:input cssClass="lfr-textarea-container" label="paste-javascript-code-that-is-executed-at-the-bottom-of-every-page" name="TypeSettingsProperties--javascript--" style="height: 300px; width: 300px" type="textarea" value='<%= layoutSetTypeSettings.getProperty("javascript") %>' wrap="soft" />
</aui:fieldset>