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
String backURL = ParamUtil.getString(request, "backURL", String.valueOf(renderResponse.createRenderURL()));

ObjectDefinition objectDefinition = (ObjectDefinition)request.getAttribute(ObjectWebKeys.OBJECT_DEFINITION);

ObjectDefinitionsFieldsDisplayContext objectDefinitionsFieldsDisplayContext = (ObjectDefinitionsFieldsDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(backURL);

renderResponse.setTitle(objectDefinition.getLabel(locale, true));
%>

<frontend-data-set:headless-display
	apiURL="<%= objectDefinitionsFieldsDisplayContext.getAPIURL() %>"
	creationMenu="<%= objectDefinitionsFieldsDisplayContext.getCreationMenu(objectDefinition) %>"
	fdsActionDropdownItems="<%= objectDefinitionsFieldsDisplayContext.getFDSActionDropdownItems() %>"
	formName="fm"
	id="<%= ObjectDefinitionsFDSNames.OBJECT_FIELDS %>"
	propsTransformer="js/components/FDSPropsTransformer/ObjectFieldsFDSPropsTransformer"
	style="fluid"
/>

<div id="<portlet:namespace />AddObjectField">
	<react:component
		module="js/components/ObjectField/AddObjectField"
		props='<%=
			HashMapBuilder.<String, Object>put(
				"apiURL", objectDefinitionsFieldsDisplayContext.getAPIURL()
			).put(
				"forbiddenChars", PropsUtil.getArray(PropsKeys.DL_CHAR_BLACKLIST)
			).put(
				"forbiddenLastChars", objectDefinitionsFieldsDisplayContext.getForbiddenLastCharacters()
			).put(
				"forbiddenNames", PropsUtil.getArray(PropsKeys.DL_NAME_BLACKLIST)
			).put(
				"objectDefinitionId", objectDefinition.getObjectDefinitionId()
			).put(
				"objectFieldTypes", objectDefinitionsFieldsDisplayContext.getObjectFieldBusinessTypeMaps(false, locale)
			).put(
				"objectName", objectDefinition.getShortName()
			).build()
		%>'
	/>
</div>

<div>
	<react:component
		module="js/components/ExpressionBuilderModal"
		props='<%=
			HashMapBuilder.<String, Object>put(
				"sidebarElements", objectDefinitionsFieldsDisplayContext.getObjectFieldCodeEditorElements()
			).build()
		%>'
	/>
</div>