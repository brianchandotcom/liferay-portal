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
String toolbarItem = ParamUtil.getString(request, "toolbarItem");
%>

<c:if test="<%= SPIDefinitionPermissionUtil.contains(permissionChecker, ActionKeys.ADD_SPI_DEFINITION) %>">
	<aui:nav-bar>
		<aui:nav>
			<portlet:renderURL var="viewSPIDefinitionsURL">
				<portlet:param name="mvcPath" value="/view.jsp" />
			</portlet:renderURL>

			<portlet:renderURL var="addSPIDefinitionURL">
				<portlet:param name="mvcPath" value="/edit_spi_definition.jsp" />
				<portlet:param name="redirect" value="<%= viewSPIDefinitionsURL %>" />
				<portlet:param name="backURL" value="<%= viewSPIDefinitionsURL %>" />
			</portlet:renderURL>

			<aui:nav-item href="<%= addSPIDefinitionURL %>" iconCssClass="icon-plus" label="add-spi" selected='<%= toolbarItem.equals("add") %>' />
		</aui:nav>
	</aui:nav-bar>
</c:if>