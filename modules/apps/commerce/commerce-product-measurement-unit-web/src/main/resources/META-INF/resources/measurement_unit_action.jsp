<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
			<portlet:param name="mvcRenderCommandName" value="/cp_measurement_unit/edit_cp_measurement_unit" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="cpMeasurementUnitId" value="<%= String.valueOf(cpMeasurementUnit.getCPMeasurementUnitId()) %>" />
			<portlet:param name="type" value="<%= String.valueOf(cpMeasurementUnit.getType()) %>" />
		</portlet:renderURL>

		<liferay-ui:icon
			message="edit"
			url="<%= editURL %>"
		/>

		<portlet:actionURL name="/cp_measurement_unit/edit_cp_measurement_unit" var="setPrimaryURL">
			<portlet:param name="<%= Constants.CMD %>" value="setPrimary" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="cpMeasurementUnitId" value="<%= String.valueOf(cpMeasurementUnit.getCPMeasurementUnitId()) %>" />
			<portlet:param name="primary" value="<%= String.valueOf(!cpMeasurementUnit.getPrimary()) %>" />
		</portlet:actionURL>

		<liferay-ui:icon
			message='<%= cpMeasurementUnit.getPrimary() ? "unset-as-primary" : "set-as-primary" %>'
			url="<%= setPrimaryURL %>"
		/>

		<portlet:actionURL name="/cp_measurement_unit/edit_cp_measurement_unit" var="deleteURL">
			<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.DELETE %>" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="cpMeasurementUnitId" value="<%= String.valueOf(cpMeasurementUnit.getCPMeasurementUnitId()) %>" />
		</portlet:actionURL>

		<liferay-ui:icon-delete
			url="<%= deleteURL %>"
		/>
	</c:if>
</liferay-ui:icon-menu>