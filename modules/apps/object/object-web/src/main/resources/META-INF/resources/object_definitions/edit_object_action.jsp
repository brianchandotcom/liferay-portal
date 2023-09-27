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

ObjectAction objectAction = objectDefinitionsActionsDisplayContext.getObjectAction();
%>

<react:component
	module="js/components/ObjectAction/EditObjectAction"
	props='<%=
		HashMapBuilder.<String, Object>put(
			"isApproved", objectDefinition.isApproved()
		).put(
			"objectAction", objectDefinitionsActionsDisplayContext.getObjectActionJSONObject(objectAction)
		).put(
			"objectActionCodeEditorElements", objectDefinitionsActionsDisplayContext.getObjectActionCodeEditorElements()
		).put(
			"objectActionExecutors", objectDefinitionsActionsDisplayContext.getObjectActionExecutorsJSONArray()
		).put(
			"objectActionTriggers", objectDefinitionsActionsDisplayContext.getObjectActionTriggersJSONArray()
		).put(
			"objectDefinitionExternalReferenceCode", objectDefinition.getExternalReferenceCode()
		).put(
			"objectDefinitionsRelationshipsURL", objectDefinitionsActionsDisplayContext.getObjectDefinitionsRelationshipsURL()
		).put(
			"readOnly", !objectDefinitionsActionsDisplayContext.hasUpdateObjectDefinitionPermission()
		).put(
			"systemObject", objectDefinition.isSystem()
		).put(
			"validateExpressionURL", objectDefinitionsActionsDisplayContext.getValidateExpressionURL()
		).build()
	%>'
/>