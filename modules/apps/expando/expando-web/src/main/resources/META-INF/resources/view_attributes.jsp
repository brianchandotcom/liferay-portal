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
String redirect = ParamUtil.getString(request, "redirect");

String modelResource = ParamUtil.getString(request, "modelResource");
String modelResourceName = ResourceActionsUtil.getModelResource(request, modelResource);

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("mvcPath", "/view_attributes.jsp");
portletURL.setParameter("redirect", redirect);
portletURL.setParameter("modelResource", modelResource);
%>

<liferay-ui:header
	backURL="<%= redirect %>"
	localizeTitle="<%= false %>"
	title="<%= modelResourceName %>"
/>

<%
ExpandoBridge expandoBridge = ExpandoBridgeFactoryUtil.getExpandoBridge(company.getCompanyId(), modelResource);

List<String> attributeNames = Collections.list(expandoBridge.getAttributeNames());
%>

<liferay-ui:search-container
	emptyResultsMessage='<%= LanguageUtil.format(request, "no-custom-fields-are-defined-for-x", modelResourceName, false) %>'
	iteratorURL="<%= portletURL %>"
>
	<liferay-ui:search-container-results
		results="<%= attributeNames %>"
		total="<%= attributeNames.size() %>"
	/>

	<liferay-ui:search-container-row
		className="java.lang.String"
		modelVar="name"
		stringKey="<%= true %>"
	>

		<%
		int type = expandoBridge.getAttributeType(name);

		ExpandoColumn expandoColumn = ExpandoColumnLocalServiceUtil.getDefaultTableColumn(company.getCompanyId(), modelResource, name);

		UnicodeProperties typeSettings = expandoColumn.getTypeSettingsProperties();
		%>

		<portlet:renderURL var="rowURL">
			<portlet:param name="mvcPath" value="/edit_expando.jsp" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="columnId" value="<%= String.valueOf(expandoColumn.getColumnId()) %>" />
			<portlet:param name="modelResource" value="<%= modelResource %>" />
		</portlet:renderURL>

		<liferay-ui:search-container-row-parameter
			name="expandoColumn"
			value="<%= expandoColumn %>"
		/>

		<liferay-ui:search-container-row-parameter
			name="modelResource"
			value="<%= modelResource %>"
		/>

		<%@ include file="/attribute_columns.jspf" %>
	</liferay-ui:search-container-row>

	<c:if test="<%= PortletPermissionUtil.contains(permissionChecker, ExpandoPortletKeys.EXPANDO, ActionKeys.ADD_EXPANDO) %>">
		<portlet:renderURL var="addExpandoURL">
			<portlet:param name="mvcPath" value="/edit_expando.jsp" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="modelResource" value="<%= modelResource %>" />
		</portlet:renderURL>

		<aui:button-row>
			<aui:button href="<%= addExpandoURL %>" value="add-custom-field" />
		</aui:button-row>

		<br />
	</c:if>

	<liferay-ui:search-iterator paginate="<%= false %>" />
</liferay-ui:search-container>

<%
PortalUtil.addPortletBreadcrumbEntry(request, modelResourceName, portletURL.toString());
%>