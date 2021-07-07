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

<clay:navigation-bar
	inverted="<%= true %>"
	navigationItems="<%= templateDisplayContext.getNavigationItems() %>"
/>

<%
TemplateManagementToolbarDisplayContext templateManagementToolbarDisplayContext = new TemplateManagementToolbarDisplayContext(request, liferayPortletRequest, liferayPortletResponse, templateDisplayContext.getTemplateSearchContainer());
%>

<clay:management-toolbar
	managementToolbarDisplayContext="<%= templateManagementToolbarDisplayContext %>"
/>

<portlet:actionURL name="/template/delete_template" var="deleteTemplateURL" />

<aui:form action="<%= deleteTemplateURL %>" cssClass="container-fluid container-fluid-max-xl" method="post" name="fm">
	<clay:container-fluid
		id='<%= liferayPortletResponse.getNamespace() + "templatesContainer" %>'
	>
		<liferay-ui:search-container
			id="<%= templateManagementToolbarDisplayContext.getSearchContainerId() %>"
			searchContainer="<%= templateDisplayContext.getTemplateSearchContainer() %>"
		>
			<liferay-ui:search-container-row
				className="com.liferay.dynamic.data.mapping.model.DDMTemplate"
				keyProperty="templateId"
				modelVar="ddmTemplate"
			>

				<%
				row.setData(
					HashMapBuilder.<String, Object>put(
						"actions", templateManagementToolbarDisplayContext.getAvailableActions(ddmTemplate)
					).build());
				%>

				<liferay-ui:search-container-column-text
					name="id"
					property="templateId"
				/>

				<liferay-ui:search-container-column-text
					cssClass="table-cell-expand"
					name="name"
					value="<%= HtmlUtil.escape(ddmTemplate.getName(locale)) %>"
				/>

				<liferay-ui:search-container-column-text
					cssClass="table-cell-expand"
					name="description"
					value="<%= HtmlUtil.escape(ddmTemplate.getDescription(locale)) %>"
				/>

				<liferay-ui:search-container-column-date
					name="modified-date"
					value="<%= ddmTemplate.getModifiedDate() %>"
				/>

				<liferay-ui:search-container-column-text>
					<clay:dropdown-actions
						dropdownItems="<%= templateDisplayContext.getDDMTemplateActionDropdownItems(ddmTemplate) %>"
					/>
				</liferay-ui:search-container-column-text>
			</liferay-ui:search-container-row>

			<liferay-ui:search-iterator
				markupView="lexicon"
			/>
		</liferay-ui:search-container>
	</clay:container-fluid>
</aui:form>