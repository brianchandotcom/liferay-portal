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

CommerceWarehouse commerceWarehouse = (CommerceWarehouse)row.getObject();
%>

<liferay-ui:icon-menu
	direction="left-side"
	icon="<%= StringPool.BLANK %>"
	markupView="lexicon"
	message="<%= StringPool.BLANK %>"
	showWhenSingleIcon="<%= true %>"
>
	<c:if test="<%= CommercePermission.contains(permissionChecker, scopeGroupId, CommerceActionKeys.MANAGE_COMMERCE_WAREHOUSES) %>">
		<portlet:renderURL var="editURL">
			<portlet:param name="mvcRenderCommandName" value="editCommerceWarehouse" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="commerceWarehouseId" value="<%= String.valueOf(commerceWarehouse.getCommerceWarehouseId()) %>" />
		</portlet:renderURL>

		<liferay-ui:icon
			message="edit"
			url="<%= editURL %>"
		/>

		<portlet:actionURL name="editCommerceWarehouse" var="geolocateURL">
			<portlet:param name="<%= Constants.CMD %>" value="geolocate" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="commerceWarehouseId" value="<%= String.valueOf(commerceWarehouse.getCommerceWarehouseId()) %>" />
		</portlet:actionURL>

		<liferay-ui:icon
			message="geolocate"
			url="<%= geolocateURL %>"
		/>

		<portlet:actionURL name="editCommerceWarehouse" var="setActiveURL">
			<portlet:param name="<%= Constants.CMD %>" value="setActive" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="commerceWarehouseId" value="<%= String.valueOf(commerceWarehouse.getCommerceWarehouseId()) %>" />
			<portlet:param name="active" value="<%= String.valueOf(!commerceWarehouse.getActive()) %>" />
		</portlet:actionURL>

		<liferay-ui:icon
			message='<%= (commerceWarehouse.getActive()) ? LanguageUtil.get(request, "unset-as-active") : LanguageUtil.get(request, "set-as-active") %>'
			url="<%= setActiveURL %>"
		/>

		<portlet:actionURL name="editCommerceWarehouse" var="deleteURL">
			<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.DELETE %>" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="commerceWarehouseId" value="<%= String.valueOf(commerceWarehouse.getCommerceWarehouseId()) %>" />
		</portlet:actionURL>

		<liferay-ui:icon-delete
			url="<%= deleteURL %>"
		/>
	</c:if>
</liferay-ui:icon-menu>