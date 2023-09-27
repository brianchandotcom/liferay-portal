<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
String backURL = ParamUtil.getString(request, "backURL", String.valueOf(renderResponse.createRenderURL()));

ObjectDefinition objectDefinition = (ObjectDefinition)request.getAttribute(ObjectWebKeys.OBJECT_DEFINITION);

ObjectDefinitionsRelationshipsDisplayContext objectDefinitionsRelationshipsDisplayContext = (ObjectDefinitionsRelationshipsDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(backURL);

renderResponse.setTitle(objectDefinition.getLabel(locale, true));
%>

<liferay-portlet:resourceURL copyCurrentRenderParameters="<%= false %>" var="baseResourceURL" />

<div>
	<react:component
		module="js/components/ObjectRelationship/Relationships"
		props='<%=
			HashMapBuilder.<String, Object>put(
				"apiURL", objectDefinitionsRelationshipsDisplayContext.getAPIURL()
			).put(
				"baseResourceURL", String.valueOf(baseResourceURL)
			).put(
				"creationMenu", objectDefinitionsRelationshipsDisplayContext.getCreationMenu()
			).put(
				"formName", "fm"
			).put(
				"id", ObjectDefinitionsFDSNames.OBJECT_RELATIONSHIPS
			).put(
				"isApproved", objectDefinition.isApproved()
			).put(
				"items", objectDefinitionsRelationshipsDisplayContext.getFDSActionDropdownItems()
			).put(
				"objectDefinitionExternalReferenceCode", objectDefinition.getExternalReferenceCode()
			).put(
				"parameterRequired", objectDefinitionsRelationshipsDisplayContext.isParameterRequired(objectDefinition)
			).put(
				"style", "fluid"
			).put(
				"url", objectDefinitionsRelationshipsDisplayContext.getEditObjectRelationshipURL()
			).build()
		%>'
	/>
</div>