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

<%@ include file="/admin/init.jsp" %>

<%
EditClientExtensionEntryDisplayContext<ThemeCSSCET> editClientExtensionEntryDisplayContext = (EditClientExtensionEntryDisplayContext)renderRequest.getAttribute(ClientExtensionAdminWebKeys.EDIT_CLIENT_EXTENSION_ENTRY_DISPLAY_CONTEXT);

ThemeCSSCET themeCSSCET = editClientExtensionEntryDisplayContext.getCET();
%>

<aui:field-wrapper cssClass="form-group">
	<aui:input label="main-css-url" name="mainURL" required="<%= true %>" type="text" value="<%= themeCSSCET.getMainURL() %>" />

	<div class="form-text">
		<liferay-ui:message key="this-css-replaces-the-main-css-of-the-system" />
	</div>
</aui:field-wrapper>

<aui:field-wrapper cssClass="form-group">
	<aui:input label="clay-css-url" name="clayURL" required="<%= true %>" type="text" value="<%= themeCSSCET.getClayURL() %>" />

	<div class="form-text">
		<liferay-ui:message key="this-css-replaces-the-clay-css-of-the-system" />
	</div>
</aui:field-wrapper>