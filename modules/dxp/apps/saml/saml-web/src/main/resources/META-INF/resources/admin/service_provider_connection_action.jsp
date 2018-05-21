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

SamlIdpSpConnection samlIdpSpConnection = (SamlIdpSpConnection)row.getObject();
%>

<liferay-ui:icon-menu
	icon="<%= StringPool.BLANK %>"
	message="<%= StringPool.BLANK %>"
>
	<portlet:renderURL var="editURL">
		<portlet:param name="mvcPath" value="/admin/edit_service_provider_connection.jsp" />
		<portlet:param name="mvcRenderCommandName" value="/admin/edit_service_provider_connection" />
		<portlet:param name="redirect" value="<%= currentURL %>" />
		<portlet:param name="samlIdpSpConnectionId" value="<%= String.valueOf(samlIdpSpConnection.getSamlIdpSpConnectionId()) %>" />
	</portlet:renderURL>

	<liferay-ui:icon
		iconCssClass="icon-edit"
		message="edit"
		url="<%= editURL %>"
	/>

	<portlet:actionURL name="/admin/deleteSamlIdpSpConnection" var="deleteURL">
		<portlet:param name="redirect" value="<%= currentURL %>" />
		<portlet:param name="samlIdpSpConnectionId" value="<%= String.valueOf(samlIdpSpConnection.getSamlIdpSpConnectionId()) %>" />
	</portlet:actionURL>

	<liferay-ui:icon-delete
		url="<%= deleteURL %>"
	/>
</liferay-ui:icon-menu>