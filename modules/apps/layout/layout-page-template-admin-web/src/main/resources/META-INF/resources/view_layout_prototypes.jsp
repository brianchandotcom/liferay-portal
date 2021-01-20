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
LayoutPrototypeDisplayContext layoutPrototypeDisplayContext = new LayoutPrototypeDisplayContext(request, renderRequest, renderResponse);
%>

<clay:navigation-bar
	inverted="<%= true %>"
	navigationItems="<%= layoutPageTemplatesAdminDisplayContext.getNavigationItems() %>"
/>

<%
LayoutPrototypeManagementToolbarDisplayContext layoutPrototypeManagementToolbarDisplayContext = new LayoutPrototypeManagementToolbarDisplayContext(request, liferayPortletRequest, liferayPortletResponse, layoutPrototypeDisplayContext);
%>

<clay:management-toolbar-v2
	displayContext="<%= layoutPrototypeManagementToolbarDisplayContext %>"
/>

<portlet:actionURL name="/layout_page_template_admin/delete_layout_prototype" var="deleteLayoutPrototypesURL">
	<portlet:param name="redirect" value="<%= currentURL %>" />
</portlet:actionURL>

<aui:form action="<%= deleteLayoutPrototypesURL %>" cssClass="container-fluid container-fluid-max-xl" name="fm">
	<liferay-ui:error embed="<%= false %>" exception="<%= RequiredLayoutPrototypeException.class %>" message="you-cannot-delete-page-templates-that-are-used-by-a-page" />

	<liferay-ui:search-container
		searchContainer="<%= layoutPrototypeDisplayContext.getSearchContainer() %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.layout.page.template.model.LayoutPageTemplateEntry"
			escapedModel="<%= true %>"
			keyProperty="layoutPrototypeId"
			modelVar="layoutPageTemplateEntry"
		>

			<%
			LayoutPrototype layoutPrototype = LayoutPrototypeServiceUtil.getLayoutPrototype(layoutPageTemplateEntry.getLayoutPrototypeId());

			row.setData(
				HashMapBuilder.<String, Object>put(
					"actions", layoutPrototypeManagementToolbarDisplayContext.getAvailableActions(layoutPrototype)
				).build());
			%>

			<liferay-ui:search-container-column-text>
				<clay:vertical-card
					propsTransformer="js/propsTransformers/LayoutPrototypeDropdownPropsTransformer"
					verticalCard="<%= new LayoutPrototypeVerticalCard(layoutPrototype, renderRequest, renderResponse, searchContainer.getRowChecker()) %>"
				/>
			</liferay-ui:search-container-column-text>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			displayStyle="icon"
			markupView="lexicon"
		/>
	</liferay-ui:search-container>
</aui:form>

<liferay-frontend:component
	componentId="<%= layoutPrototypeManagementToolbarDisplayContext.getDefaultEventHandler() %>"
	module="js/LayoutPrototypeManagementToolbarDefaultEventHandler.es"
/>