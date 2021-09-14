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
ViewListTypeDefinitionsDisplayContext viewListTypeDefinitionsDisplayContext = (ViewListTypeDefinitionsDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);
%>

<clay:headless-data-set-display
	apiURL="<%= viewListTypeDefinitionsDisplayContext.getAPIURL() %>"
	clayDataSetActionDropdownItems="<%= viewListTypeDefinitionsDisplayContext.getClayDataSetActionDropdownItems() %>"
	creationMenu="<%= viewListTypeDefinitionsDisplayContext.getCreationMenu() %>"
	formId="fm"
	id="<%= ListTypeDefinitionsClayDataSetDisplayNames.LIST_TYPE_DEFINITIONS %>"
	itemsPerPage="<%= 20 %>"
	namespace="<%= liferayPortletResponse.getNamespace() %>"
	pageNumber="<%= 1 %>"
	portletURL="<%= liferayPortletResponse.createRenderURL() %>"
	style="fluid"
/>

<div id="<portlet:namespace />addListTypeDefinition">
	<react:component
		module="js/components/ModalAddListTypeDefinition"
		props='<%=
			HashMapBuilder.<String, Object>put(
				"apiURL", viewListTypeDefinitionsDisplayContext.getAPIURL()
			).put(
				"spritemap", themeDisplay.getPathThemeImages() + "/clay/icons.svg"
			).build()
		%>'
	/>
</div>

<script>
	function handleDestroyPortlet() {
		Liferay.detach('destroyPortlet', handleDestroyPortlet);
	}

	Liferay.on('destroyPortlet', handleDestroyPortlet);
</script>