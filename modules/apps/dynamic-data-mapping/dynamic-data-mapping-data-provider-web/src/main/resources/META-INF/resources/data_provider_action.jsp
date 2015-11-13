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

	<c:if test="<%= ddmDataProviderDisplayContext.isShowEditDataProviderIcon(ddmDataProviderInstance) %>">

		<portlet:renderURL var="editURL">
			<portlet:param name="mvcPath" value="/edit_data_provider.jsp" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="dataProviderInstanceId" value="<%= String.valueOf(ddmDataProviderInstance.getDataProviderInstanceId()) %>" />
			<portlet:param name="dataProviderType" value="<%= ddmDataProviderInstance.getType() %>" />
		</portlet:renderURL>

		<liferay-ui:icon
			message="edit"
			url="<%= editURL %>"
		/>

	</c:if>

	<c:if test="<%= ddmDataProviderDisplayContext.isShowPermissionsIcon(ddmDataProviderInstance) %>">
		<liferay-security:permissionsURL
			modelResource="<%= DDMDataProviderInstance.class.getName() %>"
			modelResourceDescription="<%= ddmDataProviderInstance.getName(locale) %>"
			resourcePrimKey="<%= String.valueOf(ddmDataProviderInstance.getDataProviderInstanceId()) %>"
			var="permissionsDataProviderURL"
			windowState="<%= LiferayWindowState.POP_UP.toString() %>"
		/>

		<liferay-ui:icon
			message="permissions"
			method="get"
			url="<%= permissionsDataProviderURL %>"
			useDialog="<%= true %>"
		/>
	</c:if>

	<c:if test="<%= ddmDataProviderDisplayContext.isShowDeleteDataProviderIcon(ddmDataProviderInstance) %>">
		<portlet:actionURL name="deleteDataProvider" var="deleteURL">
			<portlet:param name="dataProviderInstanceId" value="<%= String.valueOf(ddmDataProviderInstance.getDataProviderInstanceId()) %>" />
		</portlet:actionURL>

		<liferay-ui:icon-delete
			label="<%= true %>"
			url="<%= deleteURL %>"
		/>
	</c:if>

</liferay-ui:icon-menu>