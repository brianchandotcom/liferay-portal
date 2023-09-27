<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

SamlSpIdpConnection samlSpIdpConnection = (SamlSpIdpConnection)row.getObject();
%>

<liferay-ui:icon-menu
	icon="<%= StringPool.BLANK %>"
	markupView="lexicon"
	message="<%= StringPool.BLANK %>"
>
	<portlet:renderURL var="editURL">
		<portlet:param name="mvcRenderCommandName" value="/admin/edit_identity_provider_connection" />
		<portlet:param name="redirect" value="<%= currentURL %>" />
		<portlet:param name="samlSpIdpConnectionId" value="<%= String.valueOf(samlSpIdpConnection.getSamlSpIdpConnectionId()) %>" />
	</portlet:renderURL>

	<liferay-ui:icon
		iconCssClass="icon-edit"
		message="edit"
		url="<%= editURL %>"
	/>

	<portlet:actionURL name="/admin/delete_saml_sp_idp_connection" var="deleteURL">
		<portlet:param name="redirect" value="<%= currentURL %>" />
		<portlet:param name="samlSpIdpConnectionId" value="<%= String.valueOf(samlSpIdpConnection.getSamlSpIdpConnectionId()) %>" />
	</portlet:actionURL>

	<liferay-ui:icon-delete
		url="<%= deleteURL %>"
	/>
</liferay-ui:icon-menu>