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

ObjectDefinitionsViewsDisplayContext objectDefinitionsViewsDisplayContext = (ObjectDefinitionsViewsDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(backURL);

renderResponse.setTitle(objectDefinition.getLabel(locale, true));
%>

<div>
	<react:component
		module="js/components/ObjectView/Views"
		props='<%=
			HashMapBuilder.<String, Object>put(
				"apiURL", objectDefinitionsViewsDisplayContext.getAPIURL()
			).put(
				"creationMenu", objectDefinitionsViewsDisplayContext.getCreationMenu()
			).put(
				"formName", "fm"
			).put(
				"id", ObjectDefinitionsFDSNames.OBJECT_VIEWS
			).put(
				"items", objectDefinitionsViewsDisplayContext.getFDSActionDropdownItems()
			).put(
				"objectDefinitionExternalReferenceCode", objectDefinition.getExternalReferenceCode()
			).put(
				"style", "fluid"
			).put(
				"url", objectDefinitionsViewsDisplayContext.getEditObjectViewsURL()
			).build()
		%>'
	/>
</div>

<div id="<portlet:namespace />AddObjectView">
	<react:component
		module="js/components/ModalAddObjectCustomView"
		props='<%=
			HashMapBuilder.<String, Object>put(
				"apiURL", objectDefinitionsViewsDisplayContext.getAPIURL()
			).build()
		%>'
	/>
</div>

<div>
	<react:component
		module="js/components/ModalAddColumns"
	/>
</div>