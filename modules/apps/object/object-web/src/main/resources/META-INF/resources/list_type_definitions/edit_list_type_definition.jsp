<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<% ListTypeDefinition listTypeDefinition = (ListTypeDefinition)request.getAttribute(ObjectWebKeys.LIST_TYPE_DEFINITION);
	ViewListTypeEntriesDisplayContext viewListTypeEntriesDisplayContext = (ViewListTypeEntriesDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);
%>

<react:component
	module="js/components/ListTypeDefinition/EditListTypeDefinition"
	props='<%=
		HashMapBuilder.<String, Object>put(
			"listTypeDefinitionId", listTypeDefinition.getListTypeDefinitionId()
		).put(
			"readOnly", !viewListTypeEntriesDisplayContext.hasUpdateListTypeDefinitionPermission()
		).build()
	%>'
/>