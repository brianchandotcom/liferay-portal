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
			<portlet:param name="mvcRenderCommandName" value="editCommerceCurrency" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="commerceCurrencyId" value="<%= String.valueOf(commerceCurrency.getCommerceCurrencyId()) %>" />
		</portlet:renderURL>

		<liferay-ui:icon
			message="edit"
			url="<%= editURL %>"
		/>

		<c:if test="<%= !commerceCurrency.isPrimary() %>">
			<portlet:actionURL name="editCommerceCurrency" var="setPrimaryURL">
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

		<portlet:actionURL name="editCommerceCurrency" var="setActiveURL">
			<portlet:param name="<%= Constants.CMD %>" value="setActive" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="commerceCurrencyId" value="<%= String.valueOf(commerceCurrency.getCommerceCurrencyId()) %>" />
			<portlet:param name="active" value="<%= String.valueOf(!commerceCurrency.isActive()) %>" />
		</portlet:actionURL>

		<liferay-ui:icon
			message='<%= commerceCurrency.isActive() ? "deactivate" : "activate" %>'
			url="<%= setActiveURL %>"
		/>

		<portlet:actionURL name="editCommerceCurrency" var="deleteURL">
			<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.DELETE %>" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="commerceCurrencyId" value="<%= String.valueOf(commerceCurrency.getCommerceCurrencyId()) %>" />
		</portlet:actionURL>

		<liferay-ui:icon-delete
			url="<%= deleteURL %>"
		/>
	</c:if>
</liferay-ui:icon-menu>