<%--
/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
String backURL = ParamUtil.getString(request, "backURL", String.valueOf(renderResponse.createRenderURL()));
ObjectDefinitionsDetailsDisplayContext objectDefinitionsDetailsDisplayContext = (ObjectDefinitionsDetailsDisplayContext)request.getAttribute(ObjectWebKeys.OBJECT_DEFINITIONS_DETAILS_DISPLAY_CONTEXT);
ObjectDefinitionsRelationshipsDisplayContext objectDefinitionsRelationshipsDisplayContext = (ObjectDefinitionsRelationshipsDisplayContext)request.getAttribute(ObjectWebKeys.OBJECT_DEFINITIONS_RELATIONSHIP_DISPLAY_CONTEXT);

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(backURL);

renderResponse.setTitle(LanguageUtil.get(request, "object-model-builder"));
%>

<liferay-portlet:resourceURL copyCurrentRenderParameters="<%= false %>" var="baseResourceURL" />

<react:component
	module="js/components/ModelBuilder/index"
	props='<%=
		HashMapBuilder.<String, Object>put(
			"baseResourceURL", String.valueOf(baseResourceURL)
		).put(
			"companyKeyValuePair", objectDefinitionsDetailsDisplayContext.getScopeKeyValuePairs("company")
		).put(
			"deletionTypes", objectDefinitionsRelationshipsDisplayContext.getObjectRelationshipDeletionTypesJSONArray()
		).put(
			"editObjectDefinitionURL", objectDefinitionsDetailsDisplayContext.getEditObjectDefinitionURL()
		).put(
			"objectDefinitionPermissionsURL", objectDefinitionsDetailsDisplayContext.getPermissionsURL(ObjectDefinition.class.getName())
		).put(
			"siteKeyValuePair", objectDefinitionsDetailsDisplayContext.getScopeKeyValuePairs("site")
		).put(
			"storages", objectDefinitionsDetailsDisplayContext.getStoragesJSONArray()
		).put(
			"viewApiURL", "/o/object-admin/v1.0/object-definitions"
		).build()
	%>'
/>