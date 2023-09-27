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

ObjectDefinitionsValidationsDisplayContext objectDefinitionsValidationsDisplayContext = (ObjectDefinitionsValidationsDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(backURL);

renderResponse.setTitle(objectDefinition.getLabel(locale, true));
%>

<div>
	<react:component
		module="js/components/ObjectValidation/Validations"
		props='<%=
			HashMapBuilder.<String, Object>put(
				"apiURL", objectDefinitionsValidationsDisplayContext.getAPIURL()
			).put(
				"creationMenu", objectDefinitionsValidationsDisplayContext.getCreationMenu()
			).put(
				"formName", "fm"
			).put(
				"id", ObjectDefinitionsFDSNames.OBJECT_VALIDATIONS
			).put(
				"items", objectDefinitionsValidationsDisplayContext.getFDSActionDropdownItems()
			).put(
				"objectDefinitionExternalReferenceCode", objectDefinition.getExternalReferenceCode()
			).put(
				"style", "fluid"
			).put(
				"url", objectDefinitionsValidationsDisplayContext.getEditObjectValidationURL()
			).build()
		%>'
	/>
</div>

<div id="<portlet:namespace />AddObjectValidation">
	<react:component
		module="js/components/ObjectValidation/AddObjectValidation"
		props='<%=
			HashMapBuilder.<String, Object>put(
				"apiURL", objectDefinitionsValidationsDisplayContext.getAPIURL()
			).put(
				"objectValidationRuleEngines", objectDefinitionsValidationsDisplayContext.getObjectValidationRuleEngines()
			).build()
		%>'
	/>
</div>