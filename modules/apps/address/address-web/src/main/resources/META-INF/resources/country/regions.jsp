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
SearchContainer<Region> regionSearchContainer = RegionSearchContainerFactory.create(liferayPortletRequest, liferayPortletResponse);
%>

<clay:management-toolbar
	managementToolbarDisplayContext="<%= new RegionsManagementToolbarDisplayContext(request, liferayPortletRequest, liferayPortletResponse, regionSearchContainer) %>"
	propsTransformer="js/RegionsManagementToolbarPropsTransformer"
/>

<clay:container-fluid>
	<aui:form method="post" name="fm">
		<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
		<aui:input name="regionIds" type="hidden" />

		<liferay-ui:search-container
			searchContainer="<%= regionSearchContainer %>"
		>
			<liferay-ui:search-container-row
				className="com.liferay.portal.kernel.model.Region"
				keyProperty="regionId"
				modelVar="region"
			>
				<portlet:renderURL var="rowURL">
					<portlet:param name="mvcRenderCommandName" value="/address/edit_region" />
					<portlet:param name="redirect" value="<%= currentURL %>" />
					<portlet:param name="backURL" value="<%= currentURL %>" />
					<portlet:param name="countryId" value="<%= String.valueOf(region.getCountryId()) %>" />
					<portlet:param name="regionId" value="<%= String.valueOf(region.getRegionId()) %>" />
				</portlet:renderURL>

				<%
				if (!PortalPermissionUtil.contains(permissionChecker, ActionKeys.MANAGE_COUNTRIES)) {
					rowURL = null;
				}
				%>

				<liferay-ui:search-container-column-text
					cssClass="font-weight-bold important table-cell-expand-smallest"
					href="<%= rowURL %>"
					name="name"
					value="<%= HtmlUtil.escape(region.getTitle(LocaleUtil.toLanguageId(locale))) %>"
				/>

				<liferay-ui:search-container-column-text
					name="region-code"
					property="regionCode"
				/>

				<liferay-ui:search-container-column-text
					name="active"
				>
					<liferay-ui:icon
						cssClass='<%= region.isActive() ? "text-success" : "text-danger" %>'
						icon='<%= region.isActive() ? "check" : "times" %>'
						markupView="lexicon"
					/>
				</liferay-ui:search-container-column-text>

				<liferay-ui:search-container-column-text
					name="priority"
					property="position"
				/>

				<liferay-ui:search-container-column-jsp
					cssClass="table-column-text-end"
					path="/region_action.jsp"
				/>
			</liferay-ui:search-container-row>

			<liferay-ui:search-iterator
				markupView="lexicon"
			/>
		</liferay-ui:search-container>
	</aui:form>
</clay:container-fluid>