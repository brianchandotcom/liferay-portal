<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
CommerceTaxMethodsDisplayContext commerceTaxMethodsDisplayContext = (CommerceTaxMethodsDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

CommerceTaxMethod commerceTaxMethod = (CommerceTaxMethod)row.getObject();
%>

<liferay-ui:icon-menu
	direction="left-side"
	icon="<%= StringPool.BLANK %>"
	markupView="lexicon"
	message="<%= StringPool.BLANK %>"
	showWhenSingleIcon="<%= true %>"
>
	<c:if test="<%= commerceTaxMethodsDisplayContext.hasUpdateCommerceChannelPermission() %>">
		<portlet:actionURL name="/commerce_channels/edit_commerce_tax_method" var="editURL">
			<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.EDIT %>" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="commerceTaxMethodId" value="<%= String.valueOf(commerceTaxMethod.getCommerceTaxMethodId()) %>" />
			<portlet:param name="engineKey" value="<%= commerceTaxMethod.getEngineKey() %>" />
		</portlet:actionURL>

		<liferay-ui:icon
			message="edit"
			url="<%= editURL %>"
		/>

		<portlet:actionURL name="/commerce_tax_methods/edit_commerce_tax_method" var="setActiveURL">
			<portlet:param name="<%= Constants.CMD %>" value="setActive" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="commerceTaxMethodId" value="<%= String.valueOf(commerceTaxMethod.getCommerceTaxMethodId()) %>" />
			<portlet:param name="active" value="<%= String.valueOf(!commerceTaxMethod.isActive()) %>" />
			<portlet:param name="engineKey" value="<%= commerceTaxMethod.getEngineKey() %>" />
		</portlet:actionURL>

		<liferay-ui:icon
			message='<%= commerceTaxMethod.isActive() ? "deactivate" : "activate" %>'
			url="<%= setActiveURL %>"
		/>
	</c:if>
</liferay-ui:icon-menu>