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
NPMResolver npmResolver = (NPMResolver)request.getAttribute("NPMResolver");

String cpOptionsEditorRequire = npmResolver.resolveModuleName("commerce-product-options-web/CPOptionsEditor.es") + " as CPOptionsEditor";
%>

<%
NavigationItem navigationItem = new NavigationItem();

navigationItem.setActive(true);
navigationItem.setHref(currentURL);
navigationItem.setLabel(LanguageUtil.get(request, "option-templates"));
%>

<clay:navigation-bar
	inverted="<%= false %>"
	navigationItems="<%= Collections.singletonList(navigationItem) %>"
/>

<div class="container-fluid-1280" id="<portlet:namespace />CPOptionsEditor">

</div>

<portlet:resourceURL id="cpOptions" var="cpOptionsURL">
</portlet:resourceURL>

<liferay-portlet:renderURL var="cpOptionURL" windowState="<%= LiferayWindowState.EXCLUSIVE.toString() %>">
	<portlet:param name="mvcRenderCommandName" value="cpOption" />
</liferay-portlet:renderURL>

<portlet:resourceURL id="cpOptionValues" var="cpOptionValuesURL">
</portlet:resourceURL>

<liferay-portlet:renderURL var="cpOptionValueURL" windowState="<%= LiferayWindowState.EXCLUSIVE.toString() %>">
	<portlet:param name="mvcRenderCommandName" value="editProductOptionValue" />
</liferay-portlet:renderURL>

<aui:script require="<%= cpOptionsEditorRequire %>">
	var cpOptionsEditor = new CPOptionsEditor.default(
		{
			hasEditPermission: <%= PortalPermissionUtil.contains(permissionChecker, CPActionKeys.ADD_COMMERCE_PRODUCT_OPTION) %>,
			namespace: '<portlet:namespace />',
			optionURL: '<%= cpOptionURL %>',
			optionValueURL: '<%= cpOptionValueURL %>',
			optionValuesURL: '<%= cpOptionValuesURL %>',
			optionsURL: '<%= cpOptionsURL %>',
			pathThemeImages: '<%= themeDisplay.getPathThemeImages() %>',
			successMessage:
				'<liferay-ui:message key="your-request-completed-successfully" />',
		},
		'#<portlet:namespace />CPOptionsEditor'
	);
</aui:script>