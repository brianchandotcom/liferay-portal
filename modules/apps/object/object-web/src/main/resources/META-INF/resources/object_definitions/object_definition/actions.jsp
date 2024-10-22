<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
ObjectDefinition objectDefinition = (ObjectDefinition)request.getAttribute(ObjectWebKeys.OBJECT_DEFINITION);

ObjectDefinitionsActionsDisplayContext objectDefinitionsActionsDisplayContext = (ObjectDefinitionsActionsDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(
	ParamUtil.getString(
		request, "backURL",
		URLBuilder.create(
			String.valueOf(renderResponse.createRenderURL())
		).setParameter(
			"objectFolderName", objectDefinitionsActionsDisplayContext.getObjectFolderName()
		).build()));

renderResponse.setTitle(objectDefinition.getLabel(locale, true));
%>

<div>
	<react:component
		module="{Actions} from object-web"
		props='<%=
			HashMapBuilder.<String, Object>put(
				"apiURL", objectDefinitionsActionsDisplayContext.getAPIURL()
			).put(
				"backURL", portletDisplay.getURLBack()
			).put(
				"creationMenu", objectDefinitionsActionsDisplayContext.getCreationMenu()
			).put(
				"formName", "fm"
			).put(
				"id", ObjectDefinitionsFDSNames.OBJECT_ACTIONS
			).put(
				"items", objectDefinitionsActionsDisplayContext.getFDSActionDropdownItems()
			).put(
				"objectDefinitionExternalReferenceCode", objectDefinition.getExternalReferenceCode()
			).put(
				"style", "fluid"
			).put(
				"url", objectDefinitionsActionsDisplayContext.getEditObjectActionURL()
			).build()
		%>'
	/>
</div>

<div>
	<react:component
		module="{ExpressionBuilderModal} from object-web"
		props='<%=
			HashMapBuilder.<String, Object>put(
				"sidebarElements", objectDefinitionsActionsDisplayContext.getObjectActionCodeEditorElements()
			).build()
		%>'
	/>
</div>

<div>
	<react:component
		module="{ModalSelectObjectFields} from object-web"
	/>
</div>