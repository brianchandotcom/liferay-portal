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

ObjectDefinitionsFieldsDisplayContext objectDefinitionsFieldsDisplayContext = (ObjectDefinitionsFieldsDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(backURL);

renderResponse.setTitle(objectDefinition.getLabel(locale, true));
%>

<liferay-portlet:resourceURL copyCurrentRenderParameters="<%= false %>" var="baseResourceURL" />

<div>
	<react:component
		module="js/components/ObjectField/Fields"
		props='<%=
			HashMapBuilder.<String, Object>put(
				"apiURL", objectDefinitionsFieldsDisplayContext.getAPIURL()
			).put(
				"baseResourceURL", String.valueOf(baseResourceURL)
			).put(
				"creationMenu", objectDefinitionsFieldsDisplayContext.getCreationMenu(objectDefinition)
			).put(
				"formName", "fm"
			).put(
				"id", ObjectDefinitionsFDSNames.OBJECT_FIELDS
			).put(
				"items", objectDefinitionsFieldsDisplayContext.getFDSActionDropdownItems()
			).put(
				"objectDefinitionExternalReferenceCode", objectDefinition.getExternalReferenceCode()
			).put(
				"objectFieldTypes", objectDefinitionsFieldsDisplayContext.getObjectFieldBusinessTypeMaps(false, locale)
			).put(
				"style", "fluid"
			).put(
				"url", objectDefinitionsFieldsDisplayContext.getEditObjectFieldURL()
			).build()
		%>'
	/>
</div>

<div>
	<react:component
		module="js/components/ExpressionBuilderModal"
		props='<%=
			HashMapBuilder.<String, Object>put(
				"sidebarElements", objectDefinitionsFieldsDisplayContext.getObjectFieldCodeEditorElements(ObjectFieldConstants.BUSINESS_TYPE_FORMULA)
			).build()
		%>'
	/>
</div>