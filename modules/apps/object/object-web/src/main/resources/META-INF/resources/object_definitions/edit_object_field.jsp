<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
ObjectDefinition objectDefinition = (ObjectDefinition)request.getAttribute(ObjectWebKeys.OBJECT_DEFINITION);
ObjectDefinitionsFieldsDisplayContext objectDefinitionsFieldsDisplayContext = (ObjectDefinitionsFieldsDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);
ObjectField objectField = (ObjectField)request.getAttribute(ObjectWebKeys.OBJECT_FIELD);
%>

<react:component
	module="js/components/ObjectField/EditObjectField"
	props='<%=
		HashMapBuilder.<String, Object>put(
			"creationLanguageId", objectDefinition.getDefaultLanguageId()
		).put(
			"filterOperators", LocalizedJSONArrayUtil.getFilterOperatorsJSONObject(locale)
		).put(
			"forbiddenChars", PropsUtil.getArray(PropsKeys.DL_CHAR_BLACKLIST)
		).put(
			"forbiddenLastChars", objectDefinitionsFieldsDisplayContext.getForbiddenLastCharacters()
		).put(
			"forbiddenNames", PropsUtil.getArray(PropsKeys.DL_NAME_BLACKLIST)
		).put(
			"isApproved", objectDefinition.isApproved()
		).put(
			"isDefaultStorageType", objectDefinition.isDefaultStorageType()
		).put(
			"learnResources", LearnMessageUtil.getReactDataJSONObject("object-web")
		).put(
			"objectDefinitionExternalReferenceCode", objectDefinition.getExternalReferenceCode()
		).put(
			"objectFieldId", objectField.getObjectFieldId()
		).put(
			"objectFieldTypes", objectDefinitionsFieldsDisplayContext.getObjectFieldBusinessTypeMaps(Validator.isNotNull(objectField.getRelationshipType()), locale)
		).put(
			"objectName", objectDefinition.getShortName()
		).put(
			"objectRelationshipId", objectDefinitionsFieldsDisplayContext.getObjectRelationshipId(objectField)
		).put(
			"readOnly", !objectDefinitionsFieldsDisplayContext.hasUpdateObjectDefinitionPermission()
		).put(
			"readOnlySidebarElements", objectDefinitionsFieldsDisplayContext.getObjectFieldCodeEditorElements()
		).put(
			"sidebarElements", objectDefinitionsFieldsDisplayContext.getObjectFieldCodeEditorElements(objectField.getBusinessType())
		).put(
			"workflowStatusJSONArray", LocalizedJSONArrayUtil.getWorkflowStatusJSONArray(locale)
		).build()
	%>'
/>