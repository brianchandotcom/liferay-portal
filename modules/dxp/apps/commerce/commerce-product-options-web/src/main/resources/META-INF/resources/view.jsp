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
renderResponse.setTitle(LanguageUtil.get(request, "catalog"));
%>

<clay:navigation-bar
	inverted="<%= true %>"
	items="<%= CPNavigationItemRegistryUtil.getNavigationItems(renderRequest) %>"
/>

<%
NavigationItem navigationItem = new NavigationItem();

navigationItem.setActive(true);
navigationItem.setHref(currentURL);
navigationItem.setLabel(LanguageUtil.get(request, "option-templates"));
%>

<clay:navigation-bar
	items="<%= Collections.singletonList(navigationItem) %>"
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

<aui:script require="commerce-product-options-web/CPOptionsEditor.es">
	var cpOptionsEditor = new commerceProductOptionsWebCPOptionsEditorEs.default(
		{
			namespace : '<portlet:namespace />',
			optionURL : '<%= cpOptionURL %>',
			optionValueURL : '<%= cpOptionValueURL %>',
			optionValuesURL : '<%= cpOptionValuesURL %>',
			optionsURL : '<%= cpOptionsURL %>',
			pathThemeImages: '<%= themeDisplay.getPathThemeImages() %>'
		},
		'#<portlet:namespace />CPOptionsEditor'
	);
</aui:script>