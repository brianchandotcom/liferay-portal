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
ObjectDefinitionsActionsDisplayContext objectDefinitionsActionsDisplayContext = (ObjectDefinitionsActionsDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);
%>

<liferay-portlet:resourceURL copyCurrentRenderParameters="<%= false %>" id="/object_definitions/get_object_definitions_relations" varImpl="objectDefinitionsRelationshipsURL">
	<portlet:param name="objectDefinitionId" value="<%= String.valueOf(objectDefinitionsActionsDisplayContext.getObjectDefinitionId()) %>" />
</liferay-portlet:resourceURL>

<react:component
	module="js/components/ObjectAction/AddObjectAction"
	props='<%=
		HashMapBuilder.<String, Object>put(
			"apiURL", objectDefinitionsActionsDisplayContext.getAPIURL()
		).put(
			"objectActionCodeEditorElements", objectDefinitionsActionsDisplayContext.getObjectActionCodeEditorElements()
		).put(
			"objectActionExecutors", objectDefinitionsActionsDisplayContext.getObjectActionExecutorsJSONArray()
		).put(
			"objectActionTriggers", objectDefinitionsActionsDisplayContext.getObjectActionTriggersJSONArray()
		).put(
			"objectDefinitionsRelationshipsURL", objectDefinitionsActionsDisplayContext.getObjectDefinitionsRelationshipsURL()
		).put(
			"validateExpressionURL", objectDefinitionsActionsDisplayContext.getValidateExpressionURL()
		).build()
	%>'
/>