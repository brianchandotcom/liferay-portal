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
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

UADApplicationSummaryDisplay uadApplicationSummaryDisplay = (UADApplicationSummaryDisplay)row.getObject();
%>

<liferay-ui:icon-menu
	direction="left-side"
	disabled="<%= uadApplicationSummaryDisplay.getCount() == 0 %>"
	icon="<%= StringPool.BLANK %>"
	markupView="lexicon"
	message="<%= StringPool.BLANK %>"
	showWhenSingleIcon="<%= true %>"
	triggerCssClass='<%= uadApplicationSummaryDisplay.getCount() <= 0 ? "component-action disabled" : "component-action" %>'
>
	<liferay-ui:icon
		message="view"
		url="<%= uadApplicationSummaryDisplay.getViewURL() %>"
	/>

	<portlet:actionURL name="/anonymize_application_uad_entities" var="anonymizeUADEntitiesURL">
		<portlet:param name="redirect" value="<%= currentURL %>" />
		<portlet:param name="p_u_i_d" value="<%= String.valueOf(selectedUser.getUserId()) %>" />
		<portlet:param name="applicationName" value="<%= uadApplicationSummaryDisplay.getName() %>" />
	</portlet:actionURL>

	<liferay-ui:icon
		message="anonymize"
		url="<%= anonymizeUADEntitiesURL.toString() %>"
	/>

	<portlet:actionURL name="/delete_application_uad_entities" var="deleteUADEntitiesURL">
		<portlet:param name="redirect" value="<%= currentURL %>" />
		<portlet:param name="p_u_i_d" value="<%= String.valueOf(selectedUser.getUserId()) %>" />
		<portlet:param name="applicationName" value="<%= uadApplicationSummaryDisplay.getName() %>" />
	</portlet:actionURL>

	<liferay-ui:icon
		message="delete"
		url="<%= anonymizeUADEntitiesURL.toString() %>"
	/>
</liferay-ui:icon-menu>