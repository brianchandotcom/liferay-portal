<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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