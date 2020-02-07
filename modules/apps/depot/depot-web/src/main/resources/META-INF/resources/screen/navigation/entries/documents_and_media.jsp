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
DepotEntry depotEntry = (DepotEntry)request.getAttribute(DepotAdminWebKeys.DEPOT_ENTRY);

Group group = depotEntry.getGroup();

UnicodeProperties typeSettingsProperties = group.getTypeSettingsProperties();

boolean directoryIndexingEnabled = PropertiesParamUtil.getBoolean(typeSettingsProperties, request, "directoryIndexingEnabled");
%>

<liferay-frontend:fieldset
	collapsible="true"
	cssClass="panel-group-flush"
	label='<%= LanguageUtil.get(request, "documents-and-media") %>'
>
	<aui:input helpMessage='<%= LanguageUtil.format(request, "directory-indexing-help", new Object[] {HtmlUtil.escape(group.getDescriptiveName(themeDisplay.getLocale())), themeDisplay.getPortalURL() + "/documents" + group.getFriendlyURL()}, false) %>' label="enable-directory-indexing" name="TypeSettingsProperties--directoryIndexingEnabled--" type="toggle-switch" value="<%= directoryIndexingEnabled %>" />
</liferay-frontend:fieldset>