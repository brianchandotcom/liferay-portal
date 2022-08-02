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
String navigation = ParamUtil.getString(request, "navigation", "all");

ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

Region region = (Region)row.getObject();
%>

<liferay-ui:icon-menu
	direction="left-side"
	icon="<%= StringPool.BLANK %>"
	markupView="lexicon"
	message="<%= StringPool.BLANK %>"
	showWhenSingleIcon="<%= true %>"
>
	<c:if test="<%= PortalPermissionUtil.contains(permissionChecker, ActionKeys.MANAGE_COUNTRIES) %>">
		<portlet:renderURL var="editURL">
			<portlet:param name="mvcRenderCommandName" value="/address/edit_region" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="backURL" value="<%= currentURL %>" />
			<portlet:param name="countryId" value="<%= String.valueOf(region.getCountryId()) %>" />
			<portlet:param name="regionId" value="<%= String.valueOf(region.getRegionId()) %>" />
		</portlet:renderURL>

		<liferay-ui:icon
			message="edit"
			url="<%= editURL %>"
		/>

		<c:choose>
			<c:when test="<%= region.isActive() %>">
				<portlet:actionURL name="/address/update_region_status" var="deactivateRegionURL">
					<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.DEACTIVATE %>" />
					<portlet:param name="redirect" value="<%= currentURL %>" />
					<portlet:param name="navigation" value="<%= navigation %>" />
					<portlet:param name="regionId" value="<%= String.valueOf(region.getRegionId()) %>" />
				</portlet:actionURL>

				<liferay-ui:icon-deactivate
					url="<%= deactivateRegionURL %>"
				/>
			</c:when>
			<c:otherwise>
				<portlet:actionURL name="/address/update_region_status" var="activateRegionURL">
					<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.RESTORE %>" />
					<portlet:param name="redirect" value="<%= currentURL %>" />
					<portlet:param name="navigation" value="<%= navigation %>" />
					<portlet:param name="regionId" value="<%= String.valueOf(region.getRegionId()) %>" />
				</portlet:actionURL>

				<liferay-ui:icon
					message="activate"
					url="<%= activateRegionURL %>"
				/>
			</c:otherwise>
		</c:choose>

		<portlet:actionURL name="/address/delete_region" var="deleteRegionURL">
			<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.DELETE %>" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="backURL" value="<%= currentURL %>" />
			<portlet:param name="regionIds" value="<%= String.valueOf(region.getRegionId()) %>" />
		</portlet:actionURL>

		<liferay-ui:icon-delete
			url="<%= deleteRegionURL %>"
		/>
	</c:if>
</liferay-ui:icon-menu>