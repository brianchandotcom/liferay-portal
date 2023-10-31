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
ObjectDefinitionsFieldsDisplayContext objectDefinitionsFieldsDisplayContext = (ObjectDefinitionsFieldsDisplayContext)request.getAttribute(ObjectWebKeys.OBJECT_DEFINITIONS_FIELD_DISPLAY_CONTEXT);
ObjectDefinitionsRelationshipsDisplayContext objectDefinitionsRelationshipsDisplayContext = (ObjectDefinitionsRelationshipsDisplayContext)request.getAttribute(ObjectWebKeys.OBJECT_DEFINITIONS_RELATIONSHIP_DISPLAY_CONTEXT);

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(backURL);

renderResponse.setTitle(LanguageUtil.get(request, "object-model-builder"));
%>

<liferay-portlet:resourceURL copyCurrentRenderParameters="<%= false %>" var="baseResourceURL" />

<div>
	<react:component
		module="js/components/ModelBuilder/index"
		props='<%=
			HashMapBuilder.<String, Object>put(
				"baseResourceURL", String.valueOf(baseResourceURL)
			).put(
				"companyKeyValuePairs", objectDefinitionsDetailsDisplayContext.getScopeJSONArray("company")
			).put(
				"editObjectDefinitionURL", objectDefinitionsDetailsDisplayContext.getEditObjectDefinitionURL()
			).put(
				"filterOperators", LocalizedJSONArrayUtil.getFilterOperatorsJSONObject(locale)
			).put(
				"forbiddenChars", PropsUtil.getArray(PropsKeys.DL_CHAR_BLACKLIST)
			).put(
				"forbiddenLastChars", objectDefinitionsFieldsDisplayContext.getForbiddenLastCharacters()
			).put(
				"forbiddenNames", PropsUtil.getArray(PropsKeys.DL_NAME_BLACKLIST)
			).put(
				"objectDefinitionPermissionsURL", objectDefinitionsDetailsDisplayContext.getPermissionsURL(ObjectDefinition.class.getName())
			).put(
				"objectDefinitionsStorageTypes", objectDefinitionsDetailsDisplayContext.getStorageTypesJSONArray()
			).put(
				"objectRelationshipDeletionTypes", objectDefinitionsRelationshipsDisplayContext.getObjectRelationshipDeletionTypesJSONArray()
			).put(
				"objectWebLearnResources", LearnMessageUtil.getReactDataJSONObject("object-web")
			).put(
				"siteKeyValuePairs", objectDefinitionsDetailsDisplayContext.getScopeJSONArray("site")
			).put(
				"viewApiURL", "/o/object-admin/v1.0/object-definitions"
			).put(
				"workflowStatusJSONArray", LocalizedJSONArrayUtil.getWorkflowStatusJSONArray(locale)
			).build()
		%>'
	/>
</div>

<div>
	<react:component
		module="js/components/ExpressionBuilderModal"
	/>
</div>