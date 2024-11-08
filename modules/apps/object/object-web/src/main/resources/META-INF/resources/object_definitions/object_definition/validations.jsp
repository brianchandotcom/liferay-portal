<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
ObjectDefinition objectDefinition = (ObjectDefinition)request.getAttribute(ObjectWebKeys.OBJECT_DEFINITION);

ObjectDefinitionsValidationsDisplayContext objectDefinitionsValidationsDisplayContext = (ObjectDefinitionsValidationsDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(
	ParamUtil.getString(
		request, "backURL",
		URLBuilder.create(
			String.valueOf(renderResponse.createRenderURL())
		).setParameter(
			"objectFolderName", objectDefinitionsValidationsDisplayContext.getObjectFolderName()
		).build()));

renderResponse.setTitle(objectDefinition.getLabel(locale, true));
%>

<div>
	<react:component
		module="{Validations} from object-web"
		props='<%=
			HashMapBuilder.<String, Object>put(
				"allowScriptContentToBeExecutedOrIncluded", objectDefinitionsValidationsDisplayContext.isAllowScriptContentToBeExecutedOrIncluded()
			).put(
				"apiURL", objectDefinitionsValidationsDisplayContext.getAPIURL()
			).put(
				"backURL", portletDisplay.getURLBack()
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
				"objectValidationRuleEngines", objectDefinitionsValidationsDisplayContext.getObjectValidationRuleEngines()
			).put(
				"style", "fluid"
			).put(
				"url", objectDefinitionsValidationsDisplayContext.getEditObjectValidationURL()
			).build()
		%>'
	/>
</div>

<div>
	<react:component
		module="{ModalSelectObjectFields} from object-web"
	/>
</div>

<div>
	<react:component
		module="{ModalDeletionNotAllowed} from object-web"
	/>
</div>