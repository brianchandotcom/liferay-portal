<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
CommerceChannel commerceChannel = commerceOrderContentDisplayContext.fetchCommerceChannel();
%>

<commerce-ui:modal-content
	submitButtonLabel='<%= LanguageUtil.get(request, "add-order") %>'
	title='<%= LanguageUtil.format(locale, "select-x", "order-type") %>'
>
	<portlet:actionURL name="/commerce_open_order_content/edit_commerce_order" var="editCommerceOrderURL" />

	<clay:alert
		dismissible="<%= true %>"
		displayType="info"
		message="commerce-order-type-info"
		title='<%= LanguageUtil.get(request, "info") %>'
	/>

	<aui:form action="<%= editCommerceOrderURL %>" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + liferayPortletResponse.getNamespace() + "addOrder();" %>'>
		<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.ADD %>" />
		<aui:input name="redirect" type="hidden" value="<%= redirect %>" />

		<aui:select label="order-type" name="commerceOrderTypeId">

			<%
			for (CommerceOrderType orderType : commerceOrderContentDisplayContext.getCommerceOrderTypes()) {
			%>

				<aui:option label="<%= orderType.getName(locale) %>" value="<%= orderType.getCommerceOrderTypeId() %>" />

			<%
			}
			%>

		</aui:select>
	</aui:form>
</commerce-ui:modal-content>

<portlet:renderURL var="editCommerceOrderRenderURL">
	<portlet:param name="mvcRenderCommandName" value="/commerce_open_order_content/edit_commerce_order" />
</portlet:renderURL>

<liferay-frontend:component
	context='<%=
		HashMapBuilder.<String, Object>put(
			"accountId", commerceOrderContentDisplayContext.getCommerceAccountId()
		).put(
			"addToCart", ParamUtil.getBoolean(request, "addToCart")
		).put(
			"commerceChannelId", commerceChannel.getCommerceChannelId()
		).put(
			"currencyCode", commerceChannel.getCommerceCurrencyCode()
		).put(
			"editCommerceOrderRenderURL", editCommerceOrderRenderURL.toString()
		).put(
			"namespace", liferayPortletResponse.getNamespace()
		).put(
			"ppState", LiferayWindowState.MAXIMIZED.toString()
		).build()
	%>'
	module="{commerceOrderOrderTypeModal} from commerce-order-content-web"
/>