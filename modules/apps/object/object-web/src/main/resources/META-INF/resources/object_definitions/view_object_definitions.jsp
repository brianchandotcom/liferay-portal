<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
ViewObjectDefinitionsDisplayContext viewObjectDefinitionsDisplayContext = (ViewObjectDefinitionsDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);
%>

<liferay-portlet:resourceURL copyCurrentRenderParameters="<%= false %>" var="baseResourceURL" />

<div>
	<react:component
		module="js/components/ViewObjectDefinitions/ViewObjectDefinitions"
		props='<%=
			HashMapBuilder.<String, Object>put(
				"apiURL", viewObjectDefinitionsDisplayContext.getAPIURL()
			).put(
				"baseResourceURL", String.valueOf(baseResourceURL)
			).put(
				"creationMenu", viewObjectDefinitionsDisplayContext.getCreationMenu()
			).put(
				"id", ObjectDefinitionsFDSNames.OBJECT_DEFINITIONS
			).put(
				"items", viewObjectDefinitionsDisplayContext.getFDSActionDropdownItems()
			).put(
				"modelBuilderURL", viewObjectDefinitionsDisplayContext.getModelBuilderURL()
			).put(
				"objectFolderPermissionsURL", viewObjectDefinitionsDisplayContext.getPermissionsURL(ObjectFolder.class.getName())
			).put(
				"storages", viewObjectDefinitionsDisplayContext.getStoragesJSONArray()
			).put(
				"url", viewObjectDefinitionsDisplayContext.getEditObjectDefinitionURL()
			).build()
		%>'
	/>
</div>