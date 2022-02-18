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
CommerceOrderTypeDisplayContext commerceOrderTypeDisplayContext = (CommerceOrderTypeDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

PortletURL portletURL = commerceOrderTypeDisplayContext.getPortletURL();
%>

<div class="pt-4">
	<aui:form action="<%= portletURL %>" cssClass="container-fluid container-fluid-max-xl" method="post" name="fm">
		<aui:input name="<%= Constants.CMD %>" type="hidden" />
		<aui:input name="redirect" type="hidden" value="<%= portletURL.toString() %>" />
		<aui:input name="deleteOrderTypes" type="hidden" />

		<frontend-data-set:headless-display
			apiURL="/o/headless-commerce-admin-order/v1.0/order-types"
			creationMenu="<%= commerceOrderTypeDisplayContext.getCreationMenu() %>"
			fdsActionDropdownItems="<%= commerceOrderTypeDisplayContext.getCommerceOrderTypeFDSActionDropdownItems() %>"
			formName="fm"
			id="<%= CommerceOrderFDSNames.ORDER_TYPES %>"
			itemsPerPage="<%= 10 %>"
			style="stacked"
		/>
	</aui:form>
</div>