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
CPMeasurementUnitsDisplayContext cpMeasurementUnitsDisplayContext = (CPMeasurementUnitsDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

CPMeasurementUnit cpMeasurementUnit = (CPMeasurementUnit)row.getObject();
%>

<liferay-ui:icon-menu
	direction="left-side"
	icon="<%= StringPool.BLANK %>"
	markupView="lexicon"
	message="<%= StringPool.BLANK %>"
	showWhenSingleIcon="<%= true %>"
>
	<c:if test="<%= cpMeasurementUnitsDisplayContext.hasManageCPMeasurementUnitsPermission() %>">
		<portlet:renderURL var="editURL">
			<portlet:param name="mvcRenderCommandName" value="editCPMeasurementUnit" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="cpMeasurementUnitId" value="<%= String.valueOf(cpMeasurementUnit.getCPMeasurementUnitId()) %>" />
			<portlet:param name="type" value="<%= String.valueOf(cpMeasurementUnit.getType()) %>" />
		</portlet:renderURL>

		<liferay-ui:icon
			message="edit"
			url="<%= editURL %>"
		/>

		<portlet:actionURL name="editCPMeasurementUnit" var="setPrimaryURL">
			<portlet:param name="<%= Constants.CMD %>" value="setPrimary" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="cpMeasurementUnitId" value="<%= String.valueOf(cpMeasurementUnit.getCPMeasurementUnitId()) %>" />
			<portlet:param name="primary" value="<%= String.valueOf(!cpMeasurementUnit.getPrimary()) %>" />
		</portlet:actionURL>

		<liferay-ui:icon
			message='<%= cpMeasurementUnit.getPrimary() ? "unset-as-primary" : "set-as-primary" %>'
			url="<%= setPrimaryURL %>"
		/>

		<portlet:actionURL name="editCPMeasurementUnit" var="deleteURL">
			<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.DELETE %>" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="cpMeasurementUnitId" value="<%= String.valueOf(cpMeasurementUnit.getCPMeasurementUnitId()) %>" />
		</portlet:actionURL>

		<liferay-ui:icon-delete
			url="<%= deleteURL %>"
		/>
	</c:if>
</liferay-ui:icon-menu>