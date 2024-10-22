<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
ObjectDefinition objectDefinition = (ObjectDefinition)request.getAttribute(ObjectWebKeys.OBJECT_DEFINITION);

ObjectDefinitionsStateManagerDisplayContext objectDefinitionsStateManagerDisplayContext = (ObjectDefinitionsStateManagerDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(
	ParamUtil.getString(
		request, "backURL",
		URLBuilder.create(
			String.valueOf(renderResponse.createRenderURL())
		).setParameter(
			"objectFolderName", objectDefinitionsStateManagerDisplayContext.getObjectFolderName()
		).build()));

renderResponse.setTitle(objectDefinition.getLabel(locale, true));
%>

<div>
	<react:component
		module="{StateManager} from object-web"
		props='<%=
			HashMapBuilder.<String, Object>put(
				"apiURL", objectDefinitionsStateManagerDisplayContext.getAPIURL()
			).put(
				"backURL", portletDisplay.getURLBack()
			).put(
				"creationMenu", objectDefinitionsStateManagerDisplayContext.getCreationMenu()
			).put(
				"formName", "fm"
			).put(
				"id", ObjectDefinitionsFDSNames.OBJECT_STATE_MANAGER
			).put(
				"items", objectDefinitionsStateManagerDisplayContext.getFDSActionDropdownItems()
			).put(
				"objectDefinitionExternalReferenceCode", objectDefinition.getExternalReferenceCode()
			).put(
				"style", "fluid"
			).put(
				"url", objectDefinitionsStateManagerDisplayContext.getEditObjectValidationURL()
			).build()
		%>'
	/>
</div>