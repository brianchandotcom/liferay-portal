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
ObjectDefinition objectDefinition = (ObjectDefinition)request.getAttribute(ObjectWebKeys.OBJECT_DEFINITION);
%>

<liferay-frontend:screen-navigation
	context="<%= objectDefinition %>"
	key="<%= ObjectDefinitionsScreenNavigationEntryConstants.SCREEN_NAVIGATION_KEY_OBJECT_DEFINITION %>"
	portletURL='<%=
		PortletURLBuilder.createRenderURL(
			renderResponse
		).setMVCRenderCommandName(
			"/object_definitions/edit_object_definition"
		).setParameter(
			"objectDefinitionId", objectDefinition.getObjectDefinitionId()
		).build()
	%>'
/>