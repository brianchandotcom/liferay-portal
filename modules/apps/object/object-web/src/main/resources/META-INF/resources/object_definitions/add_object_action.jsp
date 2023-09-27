<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
ObjectDefinition objectDefinition = (ObjectDefinition)request.getAttribute(ObjectWebKeys.OBJECT_DEFINITION);
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
			"objectDefinitionExternalReferenceCode", objectDefinition.getExternalReferenceCode()
		).put(
			"objectDefinitionId", objectDefinitionsActionsDisplayContext.getObjectDefinitionId()
		).put(
			"objectDefinitionsRelationshipsURL", objectDefinitionsActionsDisplayContext.getObjectDefinitionsRelationshipsURL()
		).put(
			"systemObject", objectDefinition.isSystem()
		).put(
			"validateExpressionURL", objectDefinitionsActionsDisplayContext.getValidateExpressionURL()
		).build()
	%>'
/>