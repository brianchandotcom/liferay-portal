<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
CommerceAvailabilityEstimateDisplayContext commerceAvailabilityEstimateDisplayContext = (CommerceAvailabilityEstimateDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

CommerceAvailabilityEstimate commerceAvailabilityEstimate = (CommerceAvailabilityEstimate)row.getObject();

long commerceAvailabilityEstimateId = commerceAvailabilityEstimate.getCommerceAvailabilityEstimateId();
%>

<liferay-ui:icon-menu
	direction="left-side"
	icon="<%= StringPool.BLANK %>"
	markupView="lexicon"
	message="<%= StringPool.BLANK %>"
	showWhenSingleIcon="<%= true %>"
>
	<c:if test="<%= commerceAvailabilityEstimateDisplayContext.hasPermission(commerceAvailabilityEstimateId, ActionKeys.UPDATE) %>">
		<portlet:renderURL var="editURL">
			<portlet:param name="mvcRenderCommandName" value="/commerce_availability_estimate/edit_commerce_availability_estimate" />
			<portlet:param name="commerceAvailabilityEstimateId" value="<%= String.valueOf(commerceAvailabilityEstimateId) %>" />
		</portlet:renderURL>

		<liferay-ui:icon
			message="edit"
			url="<%= editURL %>"
		/>
	</c:if>

	<c:if test="<%= commerceAvailabilityEstimateDisplayContext.hasPermission(commerceAvailabilityEstimateId, ActionKeys.DELETE) %>">
		<portlet:actionURL name="/commerce_availability_estimate/edit_commerce_availability_estimate" var="deleteURL">
			<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.DELETE %>" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="commerceAvailabilityEstimateId" value="<%= String.valueOf(commerceAvailabilityEstimateId) %>" />
		</portlet:actionURL>

		<liferay-ui:icon-delete
			url="<%= deleteURL %>"
		/>
	</c:if>
</liferay-ui:icon-menu>