<%--
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/expando/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

String modelResource = ParamUtil.getString(request, "modelResource");
String modelResourceName = ResourceActionsUtil.getModelResource(pageContext, modelResource);

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("struts_action", "/expando/view_attributes");
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
	emptyResultsMessage='<%= LanguageUtil.format(pageContext, "no-custom-fields-are-defined-for-x", modelResourceName, false) %>'
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
			<portlet:param name="struts_action" value="/expando/edit_expando" />
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

		<%@ include file="/html/portlet/expando/attribute_columns.jspf" %>
	</liferay-ui:search-container-row>

	<c:if test="<%= PortletPermissionUtil.contains(permissionChecker, PortletKeys.EXPANDO, ActionKeys.ADD_EXPANDO) %>">
		<aui:button-row>
			<aui:button onClick='<%= renderResponse.getNamespace() + "addExpando();" %>' value="add-custom-field" />
		</aui:button-row>

		<br />
	</c:if>

	<liferay-ui:search-iterator paginate="<%= false %>" />
</liferay-ui:search-container>

<aui:script>
	function <portlet:namespace />addExpando() {
		submitForm(document.hrefFm, '<portlet:renderURL><portlet:param name="struts_action" value="/expando/edit_expando" /><portlet:param name="redirect" value="<%= currentURL %>" /><portlet:param name="modelResource" value="<%= modelResource %>" /></portlet:renderURL>');
	}
</aui:script>

<%
PortalUtil.addPortletBreadcrumbEntry(request, modelResourceName, portletURL.toString());
%>