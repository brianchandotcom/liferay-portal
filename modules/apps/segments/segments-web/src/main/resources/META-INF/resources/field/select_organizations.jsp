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
SelectOrganizationsDisplayContext selectOrganizationsDisplayContext = (SelectOrganizationsDisplayContext)request.getAttribute(SegmentsWebKeys.SELECT_ORGANIZATIONS_DISPLAY_CONTEXT);
%>

<clay:management-toolbar-v2
	displayContext="<%= (SelectOrganizationsManagementToolbarDisplayContext)request.getAttribute(SegmentsWebKeys.SEGMENTS_SELECT_ORGANIZATION_MANAGEMENT_TOOLBAL_DISPLAY_CONTEXT) %>"
/>

<aui:form cssClass="container-fluid container-fluid-max-xl" name="fm">
	<liferay-ui:search-container
		id="<%= selectOrganizationsDisplayContext.getSearchContainerId() %>"
		searchContainer="<%= selectOrganizationsDisplayContext.getOrganizationSearchContainer() %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.portal.kernel.model.Organization"
			escapedModel="<%= true %>"
			keyProperty="organizationId"
			modelVar="organization"
		>

			<%
			row.setData(
				HashMapBuilder.<String, Object>put(
					"id", organization.getOrganizationId()
				).put(
					"name", organization.getName()
				).build());
			%>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-expand table-title"
				name="name"
				orderable="<%= true %>"
				property="name"
			/>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-expand"
				name="parent-organization"
				value="<%= HtmlUtil.escape(organization.getParentOrganizationName()) %>"
			/>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-expand"
				name="type"
				orderable="<%= true %>"
				value="<%= LanguageUtil.get(request, organization.getType()) %>"
			/>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			displayStyle="<%= selectOrganizationsDisplayContext.getDisplayStyle() %>"
			markupView="lexicon"
		/>
	</liferay-ui:search-container>
</aui:form>

<liferay-util:include page="/field/select_js.jsp" servletContext="<%= application %>">
	<liferay-util:param name="displayStyle" value="<%= selectOrganizationsDisplayContext.getDisplayStyle() %>" />
	<liferay-util:param name="searchContainerId" value="selectSegmentsEntryOrganizations" />
	<liferay-util:param name="selectEventName" value="<%= selectOrganizationsDisplayContext.getEventName() %>" />
</liferay-util:include>