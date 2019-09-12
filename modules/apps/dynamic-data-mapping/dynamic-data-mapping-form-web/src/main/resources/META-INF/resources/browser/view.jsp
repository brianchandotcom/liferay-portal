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

<%@ include file="/browser/init.jsp" %>

<clay:navigation-bar
	inverted="<%= true %>"
	navigationItems="<%= ddmFormBrowserDisplayContext.getNavigationItems() %>"
/>

<clay:management-toolbar
	clearResultsURL="<%= ddmFormBrowserDisplayContext.getClearResultsURL() %>"
	disabled="<%= ddmFormBrowserDisplayContext.isDisabledManagementBar() %>"
	filterDropdownItems="<%= ddmFormBrowserDisplayContext.getFilterItemsDropdownItems() %>"
	itemsTotal="<%= ddmFormBrowserDisplayContext.getTotalItems() %>"
	namespace="<%= renderResponse.getNamespace() %>"
	searchActionURL="<%= ddmFormBrowserDisplayContext.getSearchActionURL() %>"
	searchContainerId="<%= ddmFormBrowserDisplayContext.getSearchContainerId() %>"
	searchFormName="searchFm"
	sortingOrder="<%= ddmFormBrowserDisplayContext.getOrderByType() %>"
	sortingURL="<%= ddmFormBrowserDisplayContext.getSortingURL() %>"
/>

<div class="container-fluid-1280" id="<portlet:namespace />formContainer">
	<aui:form action="<%= String.valueOf(ddmFormBrowserDisplayContext.getPortletURL()) %>" method="post" name="selectDDMFormFm">
		<liferay-ui:search-container
			id="<%= ddmFormBrowserDisplayContext.getSearchContainerId() %>"
			searchContainer="<%= ddmFormBrowserDisplayContext.getFormInstanceSearch() %>"
		>
			<liferay-ui:search-container-row
				className="com.liferay.dynamic.data.mapping.model.DDMFormInstance"
				cssClass="entry-display-style"
				keyProperty="formInstanceId"
				modelVar="formInstance"
			>

				<%
				Map<String, Object> data = new HashMap<>();

				data.put("forminstanceid", formInstance.getFormInstanceId());
				data.put("forminstancename", formInstance.getName(locale));
				%>

				<liferay-ui:search-container-column-text
					cssClass="content-column title-column"
					name="name"
					truncate="<%= true %>"
				>
					<aui:a cssClass="selector-button" data="<%= data %>" href="javascript:;">
						<%= HtmlUtil.escape(formInstance.getName(locale)) %>
					</aui:a>
				</liferay-ui:search-container-column-text>

				<liferay-ui:search-container-column-text
					cssClass="content-column"
					name="description"
					truncate="<%= true %>"
					value="<%= HtmlUtil.escape(formInstance.getDescription(locale)) %>"
				/>

				<liferay-ui:search-container-column-date
					cssClass="modified-date-column text-column"
					name="modified-date"
					value="<%= formInstance.getModifiedDate() %>"
				/>
			</liferay-ui:search-container-row>

			<liferay-ui:search-iterator
				displayStyle="<%= ddmFormBrowserDisplayContext.getDisplayStyle() %>"
				markupView="lexicon"
			/>
		</liferay-ui:search-container>
	</aui:form>
</div>

<aui:script>
	Liferay.Util.selectEntityHandler(
		'#<portlet:namespace />selectDDMFormFm',
		'<%= HtmlUtil.escapeJS(ddmFormBrowserDisplayContext.getEventName()) %>'
	);
</aui:script>