<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
ObjectDefinition objectDefinition = (ObjectDefinition)request.getAttribute(ObjectWebKeys.OBJECT_DEFINITION);
ObjectDefinitionsRelationshipsDisplayContext objectDefinitionsRelationshipsDisplayContext = (ObjectDefinitionsRelationshipsDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);
ObjectRelationship objectRelationship = (ObjectRelationship)request.getAttribute(ObjectWebKeys.OBJECT_RELATIONSHIP);
%>

<liferay-portlet:resourceURL copyCurrentRenderParameters="<%= false %>" var="baseResourceURL" />

<react:component
	module="js/components/ObjectRelationship/EditRelationship"
	props='<%=
		HashMapBuilder.<String, Object>put(
			"baseResourceURL", String.valueOf(baseResourceURL)
		).put(
			"deletionTypes", objectDefinitionsRelationshipsDisplayContext.getObjectRelationshipDeletionTypesJSONArray()
		).put(
			"hasUpdateObjectDefinitionPermission", objectDefinitionsRelationshipsDisplayContext.hasUpdateObjectDefinitionPermission()
		).put(
			"objectDefinitionExternalReferenceCode", objectDefinition.getExternalReferenceCode()
		).put(
			"objectRelationship", objectDefinitionsRelationshipsDisplayContext.getObjectRelationshipJSONObject(objectRelationship)
		).put(
			"parameterRequired", objectDefinitionsRelationshipsDisplayContext.isParameterRequired(objectDefinition)
		).put(
			"restContextPath", objectDefinitionsRelationshipsDisplayContext.getRESTContextPath(objectDefinition)
		).build()
	%>'
/>