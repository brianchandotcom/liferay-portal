<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
ObjectDefinition objectDefinition = (ObjectDefinition)request.getAttribute(ObjectWebKeys.OBJECT_DEFINITION);

ObjectDefinitionsViewsDisplayContext objectDefinitionsViewsDisplayContext = (ObjectDefinitionsViewsDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(
	ParamUtil.getString(
		request, "backURL",
		URLBuilder.create(
			String.valueOf(renderResponse.createRenderURL())
		).setParameter(
			"objectFolderName", objectDefinitionsViewsDisplayContext.getObjectFolderName()
		).build()));

renderResponse.setTitle(objectDefinition.getLabel(locale, true));
%>

<div>
	<react:component
		module="{Views} from object-web"
		props='<%=
			HashMapBuilder.<String, Object>put(
				"apiURL", objectDefinitionsViewsDisplayContext.getAPIURL()
			).put(
				"backURL", portletDisplay.getURLBack()
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
		module="{ModalAddObjectCustomView} from object-web"
		props='<%=
			HashMapBuilder.<String, Object>put(
				"apiURL", objectDefinitionsViewsDisplayContext.getAPIURL()
			).build()
		%>'
	/>
</div>

<div>
	<react:component
		module="{ModalSelectObjectFields} from object-web"
	/>
</div>