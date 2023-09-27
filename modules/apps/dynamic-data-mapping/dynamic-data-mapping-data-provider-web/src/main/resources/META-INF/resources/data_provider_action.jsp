<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

DDMDataProviderInstance ddmDataProviderInstance = (DDMDataProviderInstance)row.getObject();
%>

<liferay-ui:icon-menu
	direction="left-side"
	icon="<%= StringPool.BLANK %>"
	markupView="lexicon"
	message="<%= StringPool.BLANK %>"
	showWhenSingleIcon="<%= true %>"
>
	<c:if test="<%= ddmDataProviderDisplayContext.isShowEditDataProviderIcon(ddmDataProviderInstance) %>">
		<portlet:renderURL var="editURL">
			<portlet:param name="mvcPath" value="/edit_data_provider.jsp" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="dataProviderInstanceId" value="<%= String.valueOf(ddmDataProviderInstance.getDataProviderInstanceId()) %>" />
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
		<portlet:actionURL name="/dynamic_data_mapping_data_provider/delete_data_provider" var="deleteURL">
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="refererPortletName" value="<%= DDMPortletKeys.DYNAMIC_DATA_MAPPING_FORM_ADMIN %>" />
			<portlet:param name="dataProviderInstanceId" value="<%= String.valueOf(ddmDataProviderInstance.getDataProviderInstanceId()) %>" />
		</portlet:actionURL>

		<liferay-ui:icon-delete
			label="<%= true %>"
			url="<%= deleteURL %>"
		/>
	</c:if>
</liferay-ui:icon-menu>