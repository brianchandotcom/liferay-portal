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
InformationTemplatesTemplateDisplayContext informationTemplatesTemplateDisplayContext = new InformationTemplatesTemplateDisplayContext(liferayPortletRequest, liferayPortletResponse);

InformationTemplatesManagementToolbarDisplayContext informationTemplatesManagementToolbarDisplayContext = new InformationTemplatesManagementToolbarDisplayContext(request, informationTemplatesTemplateDisplayContext, liferayPortletRequest, liferayPortletResponse);
%>

<clay:navigation-bar
	inverted="<%= true %>"
	navigationItems="<%= informationTemplatesTemplateDisplayContext.getNavigationItems() %>"
/>

<clay:management-toolbar
	additionalProps="<%= informationTemplatesManagementToolbarDisplayContext.getAdditionalProps() %>"
	managementToolbarDisplayContext="<%= informationTemplatesManagementToolbarDisplayContext %>"
	propsTransformer="js/InformationTemplatesManagementToolbarPropsTransformer"
/>

<portlet:actionURL name="/template/delete_ddm_template" var="deleteDDMTemplateURL">
	<portlet:param name="redirect" value="<%= currentURL %>" />
</portlet:actionURL>

<clay:container-fluid>
	<aui:form action="<%= deleteDDMTemplateURL %>" name="fm">
		<liferay-ui:search-container
			id="<%= informationTemplatesManagementToolbarDisplayContext.getSearchContainerId() %>"
			searchContainer="<%= informationTemplatesTemplateDisplayContext.getTemplateSearchContainer() %>"
		>
			<liferay-ui:search-container-row
				className="com.liferay.dynamic.data.mapping.model.DDMTemplate"
				keyProperty="templateId"
				modelVar="ddmTemplate"
			>

				<%
				row.setData(
					HashMapBuilder.<String, Object>put(
						"actions", informationTemplatesManagementToolbarDisplayContext.getAvailableActions(ddmTemplate)
					).build());
				%>

				<liferay-ui:search-container-column-text
					cssClass="table-cell-expand table-title"
					href="<%= informationTemplatesTemplateDisplayContext.getDDMTemplateEditURL(ddmTemplate) %>"
					name="name"
					value="<%= HtmlUtil.escape(ddmTemplate.getName(locale)) %>"
				/>

				<liferay-ui:search-container-column-text
					cssClass="table-cell-expand"
					name="item-type"
					value="<%= HtmlUtil.escape(informationTemplatesTemplateDisplayContext.getTemplateTypeLabel(ddmTemplate.getClassNameId())) %>"
				/>

				<liferay-ui:search-container-column-text
					cssClass="table-cell-expand"
					name="item-subtype"
					value="<%= HtmlUtil.escape(informationTemplatesTemplateDisplayContext.getTemplateSubtypeLabel(ddmTemplate.getClassNameId(), ddmTemplate.getClassPK())) %>"
				/>

				<liferay-ui:search-container-column-text>
					<clay:dropdown-actions
						dropdownItems="<%= informationTemplatesTemplateDisplayContext.getDDMTemplateActionDropdownItems(ddmTemplate) %>"
						propsTransformer="js/InformationTemplatesDropdownPropsTransformer"
					/>
				</liferay-ui:search-container-column-text>
			</liferay-ui:search-container-row>

			<liferay-ui:search-iterator
				markupView="lexicon"
			/>
		</liferay-ui:search-container>
	</aui:form>
</clay:container-fluid>