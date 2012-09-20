<%--
/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/layouts_admin/init.jsp" %>

<%
LayoutSet selLayoutSet = ((LayoutSet)request.getAttribute("edit_pages.jsp-selLayoutSet"));

UnicodeProperties layoutSetTypeSettings = selLayoutSet.getSettingsProperties();
%>

<liferay-ui:error-marker key="errorSection" value="javascript" />

<h3><liferay-ui:message key="javascript" /></h3>

<aui:fieldset>
	<aui:input cssClass="lfr-textarea-container" label="paste-javascript-code-that-will-be-executed-at-the-bottom-of-every-page" name="TypeSettingsProperties--javascript--" style="width: 300px; height: 300px" type="textarea" value='<%= layoutSetTypeSettings.getProperty("javascript") %>' wrap="soft" />
</aui:fieldset>