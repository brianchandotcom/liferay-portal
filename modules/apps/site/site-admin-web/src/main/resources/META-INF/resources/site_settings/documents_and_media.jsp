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
Group siteGroup = (Group)request.getAttribute("site.group");

UnicodeProperties groupTypeSettings = (UnicodeProperties)request.getAttribute("site.groupTypeSettings");
%>

<aui:field-wrapper cssClass="form-group">
	<aui:input inlineLabel="right" label="enable-directory-indexing" labelCssClass="simple-toggle-switch" name="TypeSettingsProperties--directoryIndexingEnabled--" type="toggle-switch" value='<%= PropertiesParamUtil.getBoolean(groupTypeSettings, request, "directoryIndexingEnabled") %>' />

	<span class="form-text"><liferay-ui:message arguments='<%= new Object[] {HtmlUtil.escape(siteGroup.getDescriptiveName(themeDisplay.getLocale())), themeDisplay.getPortalURL() + "/documents" + siteGroup.getFriendlyURL()} %>' key="directory-indexing-help" translateArguments="<%= false %>" /></span>
</aui:field-wrapper>