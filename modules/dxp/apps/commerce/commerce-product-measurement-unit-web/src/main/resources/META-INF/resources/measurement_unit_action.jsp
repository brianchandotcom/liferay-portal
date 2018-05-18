<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */
--%>

<%@ include file="/init.jsp" %>

<%
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
	<c:if test="<%= CPMeasurementUnitPermission.contains(permissionChecker, scopeGroupId, CPActionKeys.MANAGE_COMMERCE_PRODUCT_MEASUREMENT_UNITS) %>">
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