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
CommerceCartContentTotalDisplayContext commerceCartContentTotalDisplayContext = (CommerceCartContentTotalDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

Map<String, Object> contextObjects = new HashMap<>();

contextObjects.put("commerceCartContentTotalDisplayContext", commerceCartContentTotalDisplayContext);

SearchContainer<CommerceOrderItem> commerceOrderItemSearchContainer = commerceCartContentTotalDisplayContext.getSearchContainer();
%>

<liferay-ddm:template-renderer
	className="<%= CommerceCartContentTotalPortlet.class.getName() %>"
	contextObjects="<%= contextObjects %>"
	displayStyle="<%= commerceCartContentTotalDisplayContext.getDisplayStyle() %>"
	displayStyleGroupId="<%= commerceCartContentTotalDisplayContext.getDisplayStyleGroupId() %>"
	entries="<%= commerceOrderItemSearchContainer.getResults() %>"
>
	<div class="order-total text-dark">
		<div class="row">
			<div class="col-auto">
				<h3 class="h4"><liferay-ui:message key="total" /></h3>
			</div>

			<div class="col text-right">
				<h3 class="h4"><%= HtmlUtil.escape(commerceCartContentTotalDisplayContext.getCommerceOrderSubtotal()) %></h3>
			</div>
		</div>
	</div>

	<aui:button-row>

		<%
		PortletURL checkoutPortletURL = commerceCartContentTotalDisplayContext.getCheckoutPortletURL();
		%>

		<aui:button cssClass="btn-lg" disabled="<%= !commerceCartContentTotalDisplayContext.isValidCommerceOrder() %>" href="<%= checkoutPortletURL.toString() %>" value="checkout" />
	</aui:button-row>
</liferay-ddm:template-renderer>