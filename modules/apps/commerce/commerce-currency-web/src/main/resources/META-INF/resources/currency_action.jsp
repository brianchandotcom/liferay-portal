<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
CommerceCurrenciesDisplayContext commerceCurrenciesDisplayContext = (CommerceCurrenciesDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

CommerceCurrency commerceCurrency = (CommerceCurrency)row.getObject();
%>

<liferay-ui:icon-menu
	direction="left-side"
	icon="<%= StringPool.BLANK %>"
	markupView="lexicon"
	message="<%= StringPool.BLANK %>"
	showWhenSingleIcon="<%= true %>"
>
	<c:if test="<%= commerceCurrenciesDisplayContext.hasManageCommerceCurrencyPermission() %>">
		<portlet:renderURL var="editURL">
			<portlet:param name="mvcRenderCommandName" value="/commerce_currency/edit_commerce_currency" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="commerceCurrencyId" value="<%= String.valueOf(commerceCurrency.getCommerceCurrencyId()) %>" />
		</portlet:renderURL>

		<liferay-ui:icon
			message="edit"
			url="<%= editURL %>"
		/>

		<c:if test="<%= !commerceCurrency.isPrimary() %>">
			<portlet:actionURL name="/commerce_currency/edit_commerce_currency" var="setPrimaryURL">
				<portlet:param name="<%= Constants.CMD %>" value="setPrimary" />
				<portlet:param name="redirect" value="<%= currentURL %>" />
				<portlet:param name="commerceCurrencyId" value="<%= String.valueOf(commerceCurrency.getCommerceCurrencyId()) %>" />
				<portlet:param name="primary" value="<%= String.valueOf(!commerceCurrency.getPrimary()) %>" />
			</portlet:actionURL>

			<liferay-ui:icon
				message='<%= commerceCurrency.getPrimary() ? "unset-as-primary" : "set-as-primary" %>'
				url="<%= setPrimaryURL %>"
			/>
		</c:if>

		<portlet:actionURL name="/commerce_currency/edit_commerce_currency" var="setActiveURL">
			<portlet:param name="<%= Constants.CMD %>" value="setActive" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="commerceCurrencyId" value="<%= String.valueOf(commerceCurrency.getCommerceCurrencyId()) %>" />
			<portlet:param name="active" value="<%= String.valueOf(!commerceCurrency.isActive()) %>" />
		</portlet:actionURL>

		<liferay-ui:icon
			message='<%= commerceCurrency.isActive() ? "deactivate" : "activate" %>'
			url="<%= setActiveURL %>"
		/>

		<portlet:actionURL name="/commerce_currency/edit_commerce_currency" var="deleteURL">
			<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.DELETE %>" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="commerceCurrencyId" value="<%= String.valueOf(commerceCurrency.getCommerceCurrencyId()) %>" />
		</portlet:actionURL>

		<liferay-ui:icon-delete
			url="<%= deleteURL %>"
		/>
	</c:if>
</liferay-ui:icon-menu>