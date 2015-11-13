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

DDMDataProviderInstance ddmDataProviderInstance = (DDMDataProviderInstance)row.getObject();
%>

<liferay-ui:icon-menu direction="down" icon="<%= StringPool.BLANK %>" markupView="lexicon" message="<%= StringPool.BLANK %>" showExpanded="<%= false %>" showWhenSingleIcon="<%= false %>">

	<portlet:renderURL var="editURL">
		<portlet:param name="mvcPath" value="/edit_data_provider.jsp" />
		<portlet:param name="redirect" value="<%= currentURL %>" />
		<portlet:param name="dataProviderInstanceId" value="<%= String.valueOf(ddmDataProviderInstance.getDataProviderInstanceId()) %>" />
	</portlet:renderURL>

	<liferay-ui:icon
		message="edit"
		url="<%= editURL %>"
	/>

	<portlet:actionURL name="deleteDataProvider" var="deleteURL">
		<portlet:param name="dataProviderInstanceId" value="<%= String.valueOf(ddmDataProviderInstance.getDataProviderInstanceId()) %>" />
	</portlet:actionURL>

	<liferay-ui:icon-delete
		label="<%= true %>"
		url="<%= deleteURL %>"
	/>

</liferay-ui:icon-menu>