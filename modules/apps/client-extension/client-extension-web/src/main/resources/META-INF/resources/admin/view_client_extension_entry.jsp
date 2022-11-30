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
ViewClientExtensionEntryDisplayContext viewClientExtensionEntryDisplayContext = (ViewClientExtensionEntryDisplayContext)renderRequest.getAttribute(ClientExtensionAdminWebKeys.VIEW_CLIENT_EXTENSION_ENTRY_DISPLAY_CONTEXT);

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(viewClientExtensionEntryDisplayContext.getRedirect());

renderResponse.setTitle(viewClientExtensionEntryDisplayContext.getTitle());
%>

<liferay-frontend:edit-form>
	<liferay-frontend:edit-form-body>
		<aui:field-wrapper label="name" name="name">
			<liferay-ui:input-localized
				autoFocus="<%= windowState.equals(WindowState.MAXIMIZED) %>"
				disabled="<%= true %>"
				name="name"
				xml="<%= viewClientExtensionEntryDisplayContext.getName() %>"
			/>
		</aui:field-wrapper>

		<aui:input disabled="<%= true %>" label="description" name="description" type="textarea" value="<%= viewClientExtensionEntryDisplayContext.getDescription() %>" />

		<aui:input disabled="<%= true %>" label="source-code-url" name="sourceCodeURL" type="text" value="<%= viewClientExtensionEntryDisplayContext.getSourceCodeURL() %>" />

		<aui:input disabled="<%= true %>" label="type" name="typeLabel" type="text" value="<%= viewClientExtensionEntryDisplayContext.getTypeLabel() %>" />

		<liferay-util:include page="<%= viewClientExtensionEntryDisplayContext.getViewJSP() %>" servletContext="<%= application %>" />

		<c:if test="<%= viewClientExtensionEntryDisplayContext.isPropertiesVisible() %>">
			<aui:input disabled="<%= true %>" label="properties" name="properties" type="textarea" value="<%= viewClientExtensionEntryDisplayContext.getProperties() %>" />
		</c:if>
	</liferay-frontend:edit-form-body>
</liferay-frontend:edit-form>