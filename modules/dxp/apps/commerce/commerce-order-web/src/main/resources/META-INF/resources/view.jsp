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

SearchContainer<CommerceOrder> commerceOrderSearchContainer = commerceOrderListDisplayContext.getSearchContainer();
%>

<aui:nav-bar cssClass="collapse-basic-search container-fluid" markupView="lexicon">
	<aui:nav cssClass="navbar-nav">

		<%
		int status = commerceOrderListDisplayContext.getStatus();
		Map<Integer, Long> statusCounts = commerceOrderListDisplayContext.getStatusCounts();

		for (Map.Entry<Integer, Long> entry : statusCounts.entrySet()) {
			int curStatus = entry.getKey();
			long curCount = entry.getValue();

			PortletURL statusURL = renderResponse.createRenderURL();

			statusURL.setParameter("status", String.valueOf(curStatus));
		%>

			<aui:nav-item
				href="<%= statusURL.toString() %>"
				label="<%= commerceOrderListDisplayContext.getStatusLabel(curStatus, curCount) %>"
				localizeLabel="<%= false %>"
				selected="<%= curStatus == status %>"
				title="<%= CommerceOrderConstants.getStatusLabel(curStatus) %>"
			/>

		<%
		}
		%>

	</aui:nav>

	<aui:nav-bar-search>
		<liferay-portlet:renderURL varImpl="searchURL" />

		<aui:form action="<%= searchURL %>" method="get" name="fm">
			<liferay-portlet:renderURLParams varImpl="searchURL" />

			<liferay-ui:search-form
				page="/order_search.jsp"
				servletContext="<%= application %>"
			/>
		</aui:form>
	</aui:nav-bar-search>
</aui:nav-bar>

<liferay-frontend:management-bar
	includeCheckBox="<%= true %>"
	searchContainerId="commerceOrders"
>
	<liferay-frontend:management-bar-filters>
		<liferay-frontend:management-bar-sort
			orderByCol="<%= commerceOrderSearchContainer.getOrderByCol() %>"
			orderByType="<%= commerceOrderSearchContainer.getOrderByType() %>"
			orderColumns="<%= commerceOrderSearchContainer.getOrderableHeaders() %>"
			portletURL="<%= commerceOrderListDisplayContext.getPortletURL() %>"
		/>
	</liferay-frontend:management-bar-filters>

	<liferay-frontend:management-bar-action-buttons>
		<liferay-frontend:management-bar-button href='<%= "javascript:" + renderResponse.getNamespace() + "deleteCommerceOrders();" %>' icon="times" label="delete" />
	</liferay-frontend:management-bar-action-buttons>
</liferay-frontend:management-bar>

<div class="container-fluid-1280">
	<liferay-ui:search-container
		id="commerceOrders"
		searchContainer="<%= commerceOrderSearchContainer %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.commerce.model.CommerceOrder"
			escapedModel="<%= true %>"
			keyProperty="commerceOrderId"
			modelVar="commerceOrder"
		>

			<%
			User orderUser = commerceOrder.getOrderUser();

			PortletURL rowURL = renderResponse.createRenderURL();

			rowURL.setParameter("mvcRenderCommandName", "editCommerceOrder");
			rowURL.setParameter("redirect", currentURL);
			rowURL.setParameter("commerceOrderId", String.valueOf(commerceOrder.getCommerceOrderId()));
			%>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-content"
				href="<%= rowURL %>"
				name="order-date"
			>
				<div class="order-date">
					<%= HtmlUtil.escape(commerceOrderListDisplayContext.getCommerceOrderDate(commerceOrder)) %>
				</div>

				<div class="order-time">
					<%= HtmlUtil.escape(commerceOrderListDisplayContext.getCommerceOrderTime(commerceOrder)) %>
				</div>
			</liferay-ui:search-container-column-text>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-content"
				name="status"
				value="<%= LanguageUtil.get(request, CommerceOrderConstants.getStatusLabel(commerceOrder.getStatus())) %>"
			>

			</liferay-ui:search-container-column-text>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-content"
				name="customer-name"
			>
				<div class="customer-name">
					<%= HtmlUtil.escape(orderUser.getFullName()) %>
				</div>

				<div class="customer-id">
					<%= commerceOrder.getOrderUserId() %>
				</div>
			</liferay-ui:search-container-column-text>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-content"
				name="order-id"
				property="commerceOrderId"
			/>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-content"
				name="order-value"
				value="<%= commerceOrderListDisplayContext.getCommerceOrderValue(commerceOrder) %>"
			/>

			<liferay-ui:search-container-column-jsp
				cssClass="entry-action-column"
				path="/order_action.jsp"
			/>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator markupView="lexicon" />
	</liferay-ui:search-container>
</div>

<aui:script>
	function <portlet:namespace />deleteCommerceOrders() {
		if (confirm('<%= UnicodeLanguageUtil.get(request, "are-you-sure-you-want-to-delete-the-selected-orders") %>')) {
			var form = AUI.$(document.<portlet:namespace />fm);

			form.attr('method', 'post');
			form.fm('<%= Constants.CMD %>').val('<%= Constants.DELETE %>');
			form.fm('deleteCommerceOrderIds').val(Liferay.Util.listCheckedExcept(form, '<portlet:namespace />allRowIds'));

			submitForm(form, '<portlet:actionURL name="editCommerceOrder" />');
		}
	}
</aui:script>