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
WidgetTemplatesTemplateDisplayContext widgetTemplatesTemplateDisplayContext = new WidgetTemplatesTemplateDisplayContext(liferayPortletRequest, liferayPortletResponse);

WidgetTemplatesManagementToolbarDisplayContext widgetTemplatesManagementToolbarDisplayContext = new WidgetTemplatesManagementToolbarDisplayContext(request, liferayPortletRequest, liferayPortletResponse, widgetTemplatesTemplateDisplayContext);
%>

<clay:navigation-bar
	inverted="<%= true %>"
	navigationItems="<%= widgetTemplatesTemplateDisplayContext.getNavigationItems() %>"
/>

<clay:management-toolbar
	managementToolbarDisplayContext="<%= widgetTemplatesManagementToolbarDisplayContext %>"
	propsTransformer="js/WidgetTemplatesManagementToolbarPropsTransformer"
/>

<portlet:actionURL name="/template/delete_ddm_template" var="deleteDDMTemplateURL">
	<portlet:param name="redirect" value="<%= currentURL %>" />
</portlet:actionURL>

<clay:container-fluid>
	<aui:form action="<%= deleteDDMTemplateURL %>" name="fm">
		<liferay-ui:search-container
			id="<%= widgetTemplatesManagementToolbarDisplayContext.getSearchContainerId() %>"
			searchContainer="<%= widgetTemplatesTemplateDisplayContext.getTemplateSearchContainer() %>"
		>
			<liferay-ui:search-container-row
				className="com.liferay.dynamic.data.mapping.model.DDMTemplate"
				keyProperty="templateId"
				modelVar="ddmTemplate"
			>

				<%
				row.setData(
					HashMapBuilder.<String, Object>put(
						"actions", widgetTemplatesManagementToolbarDisplayContext.getAvailableActions(ddmTemplate)
					).build());
				%>

				<liferay-ui:search-container-column-text
					name="id"
					property="templateId"
				/>

				<liferay-ui:search-container-column-text
					cssClass="table-cell-expand table-title"
					href="<%= widgetTemplatesTemplateDisplayContext.getDDMTemplateEditURL(ddmTemplate) %>"
					name="name"
					value="<%= HtmlUtil.escape(ddmTemplate.getName(locale)) %>"
				/>

				<liferay-ui:search-container-column-text
					cssClass="table-cell-expand"
					name="description"
					value="<%= HtmlUtil.escape(ddmTemplate.getDescription(locale)) %>"
				/>

				<liferay-ui:search-container-column-text
					cssClass="table-cell-expand-smallest"
					name="type"
					value="<%= HtmlUtil.escape(widgetTemplatesTemplateDisplayContext.getTemplateTypeLabel(ddmTemplate.getClassNameId())) %>"
				/>

				<liferay-ui:search-container-column-text
					cssClass="table-cell-expand-smallest"
					name="scope"
					value="<%= HtmlUtil.escape(widgetTemplatesTemplateDisplayContext.getDDMTemplateScope(ddmTemplate)) %>"
				/>

				<liferay-ui:search-container-column-date
					name="modified-date"
					value="<%= ddmTemplate.getModifiedDate() %>"
				/>

				<liferay-ui:search-container-column-text>
					<clay:dropdown-actions
						aria-label='<%= LanguageUtil.get(request, "show-actions") %>'
						dropdownItems="<%= widgetTemplatesTemplateDisplayContext.getDDMTemplateActionDropdownItems(ddmTemplate) %>"
						propsTransformer="js/WidgetTemplatesDropdownPropsTransformer"
					/>
				</liferay-ui:search-container-column-text>
			</liferay-ui:search-container-row>

			<liferay-ui:search-iterator
				markupView="lexicon"
			/>
		</liferay-ui:search-container>
	</aui:form>
</clay:container-fluid>