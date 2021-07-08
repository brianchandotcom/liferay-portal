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
ObjectDefinitionsAdminDisplayContext objectDefinitionsAdminDisplayContext = (ObjectDefinitionsAdminDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);
%>

<clay:headless-data-set-display
	apiURL="<%= objectDefinitionsAdminDisplayContext.getAPIURL() %>"
	clayDataSetActionDropdownItems="<%= objectDefinitionsAdminDisplayContext.getClayDataSetActionDropdownItems() %>"
	creationMenu="<%= objectDefinitionsAdminDisplayContext.getCreationMenu() %>"
	formId="fm"
	id="/object_definitions/list"
	itemsPerPage="<%= 20 %>"
	namespace="<%= liferayPortletResponse.getNamespace() %>"
	pageNumber="<%= 1 %>"
	portletURL="<%= liferayPortletResponse.createRenderURL() %>"
	style="fluid"
/>

<aui:script require="frontend-js-web/liferay/modal/commands/OpenSimpleInputModal.es as openSimpleInputModal">
	function handleCreateObjectDefinitionClick(event) {
		event.preventDefault();

		//TODO I am forced to create a portlet action to reuse this UI component, while in commerce we created a different approach to avoid it
		//TODO and call directly the headless api, and avoid this code

		openSimpleInputModal.default({
			dialogTitle: '<liferay-ui:message key="name" />',
			formSubmitURL: '<liferay-portlet:actionURL name="/object_definitions_admin/add_object_definition"><portlet:param name="redirect" value="<%= currentURL %>" /></liferay-portlet:actionURL>',
			mainFieldLabel: '<liferay-ui:message key="name" />',
			mainFieldName: 'name',
			mainFieldPlaceholder: '<liferay-ui:message key="name" />',
			namespace: '<%= liferayPortletResponse.getNamespace() %>',
			spritemap: '<%= themeDisplay.getPathThemeImages() %>/clay/icons.svg',
		});
	}

	function handleDestroyPortlet() {
		Liferay.detach('destroyPortlet', handleDestroyPortlet);
		Liferay.detach('eventName', handleCreateObjectDefinitionClick);
	}

	Liferay.on('destroyPortlet', handleDestroyPortlet);
	Liferay.on('eventName', handleCreateObjectDefinitionClick);
</aui:script>