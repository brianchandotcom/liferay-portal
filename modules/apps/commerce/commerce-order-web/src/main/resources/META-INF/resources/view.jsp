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
CommerceOrderListDisplayContext commerceOrderListDisplayContext = (CommerceOrderListDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

request.setAttribute("view.jsp-portletURL", commerceOrderListDisplayContext.getPortletURL());
%>

<div id="<portlet:namespace />orderDefinitionsContainer">
	<aui:form action="<%= commerceOrderListDisplayContext.getPortletURL() %>" method="post" name="fm">
		<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.DELETE %>" />
		<aui:input name="redirect" type="hidden" value="<%= String.valueOf(commerceOrderListDisplayContext.getPortletURL()) %>" />
		<aui:input name="deleteCPDefinitionIds" type="hidden" />

		<frontend-data-set:headless-display
			apiURL="/o/headless-commerce-admin-order/v1.0/orders?nestedFields=account,channel"
			bulkActionDropdownItems="<%= commerceOrderListDisplayContext.getBulkActionDropdownItems() %>"
			fdsActionDropdownItems="<%= commerceOrderListDisplayContext.getFDSActionDropdownItems() %>"
			fdsSortItemList="<%= commerceOrderListDisplayContext.getFDSSortItemList() %>"
			formName="fm"
			id="<%= CommerceOrderFDSNames.ALL_ORDERS %>"
			selectedItemsKey="id"
			selectionType="multiple"
			style="fluid"
		/>
	</aui:form>
</div>