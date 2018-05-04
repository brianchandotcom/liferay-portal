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

<%@ include file="/designer/init.jsp" %>

<c:if test="<%= kaleoDesignerDisplayContext.isSaveKaleoDefinitionVersionButtonVisible(permissionChecker, null) %>">
	<liferay-portlet:renderURL portletName="<%= KaleoDesignerPortletKeys.KALEO_DESIGNER %>" var="editKaleoDefinitionVersionURL">
		<portlet:param name="mvcPath" value='<%= "/designer/edit_kaleo_definition_version.jsp" %>' />
		<portlet:param name="redirect" value="<%= currentURL %>" />
		<portlet:param name="clearSessionMessage" value="true" />
	</liferay-portlet:renderURL>

	<liferay-frontend:add-menu
		inline="<%= true %>"
	>
		<liferay-frontend:add-menu-item
			title='<%= LanguageUtil.get(request, "new-workflow") %>'
			url="<%= editKaleoDefinitionVersionURL.toString() %>"
		/>
	</liferay-frontend:add-menu>
</c:if>