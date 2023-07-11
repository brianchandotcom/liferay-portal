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
				"sorting", viewObjectDefinitionsDisplayContext.getFDSSortItemList()
			).put(
				"storages", viewObjectDefinitionsDisplayContext.getStoragesJSONArray()
			).put(
				"url", viewObjectDefinitionsDisplayContext.getEditObjectDefinitionURL()
			).build()
		%>'
	/>
</div>