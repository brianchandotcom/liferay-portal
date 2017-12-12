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
String toolbarItem = ParamUtil.getString(request, "toolbarItem", "view-all-orders");

CommerceOrderDisplayContext commerceOrderDisplayContext = (CommerceOrderDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

SearchContainer<CommerceOrder> commerceOrderSearchContainer = commerceOrderDisplayContext.getSearchContainer();

String displayStyle = commerceOrderDisplayContext.getDisplayStyle();

PortletURL portletURL = commerceOrderDisplayContext.getPortletURL();

portletURL.setParameter("toolbarItem", toolbarItem);
portletURL.setParameter("searchContainerId", "commerceOrders");

request.setAttribute("view.jsp-portletURL", portletURL);
%>

<liferay-portlet:renderURL varImpl="viewCommerceOrdersURL">
	<portlet:param name="toolbarItem" value="view-all-orders" />
	<portlet:param name="jspPage" value="/view.jsp" />
</liferay-portlet:renderURL>

<liferay-util:include page="/toolbar.jsp" servletContext="<%= application %>">
	<liferay-util:param name="searchContainerId" value="commerceOrders" />
</liferay-util:include>

<div id="<portlet:namespace />ordersContainer">
	<div class="closed container-fluid-1280 sidenav-container sidenav-right" id="<portlet:namespace />infoPanelId">
		<c:if test="<%= commerceOrderDisplayContext.isShowInfoPanel() %>">
			<liferay-portlet:resourceURL
				copyCurrentRenderParameters="<%= false %>"
				id="commerceOrderInfoPanel"
				var="sidebarPanelURL"
			/>

			<liferay-frontend:sidebar-panel
				resourceURL="<%= sidebarPanelURL %>"
				searchContainerId="commerceOrders"
			>
				<liferay-util:include page="/order_info_panel.jsp" servletContext="<%= application %>" />
			</liferay-frontend:sidebar-panel>
		</c:if>

		<div class="sidenav-content">
			<aui:form action="<%= portletURL.toString() %>" cssClass="container-fluid-1280" method="post" name="fm">
				<aui:input name="<%= Constants.CMD %>" type="hidden" />
				<aui:input name="redirect" type="hidden" value="<%= portletURL.toString() %>" />
				<aui:input name="deleteCommerceOrderIds" type="hidden" />

				<div class="orders-container" id="<portlet:namespace />entriesContainer">
					<liferay-ui:search-container
						id="commerceOrders"
						iteratorURL="<%= portletURL %>"
						searchContainer="<%= commerceOrderSearchContainer %>"
					>
						<liferay-ui:search-container-row
							className="com.liferay.commerce.model.CommerceOrder"
							cssClass="entry-display-style"
							keyProperty="commerceOrderId"
							modelVar="commerceOrder"
						>

							<%
							PortletURL rowURL = renderResponse.createRenderURL();

							rowURL.setParameter("mvcRenderCommandName", "viewCommerceOrderItems");
							rowURL.setParameter("redirect", currentURL);
							rowURL.setParameter("commerceOrderId", String.valueOf(commerceOrder.getCommerceOrderId()));
							%>

							<liferay-ui:search-container-column-text
								cssClass="table-cell-content"
								href="<%= rowURL %>"
								name="order-number"
								property="commerceOrderId"
							/>

							<liferay-ui:search-container-column-text
								cssClass="table-cell-content"
								name="user"
								property="userName"
							/>

							<liferay-ui:search-container-column-text
								cssClass="table-cell-content"
								name="total"
								value="<%= commerceOrderDisplayContext.getCommerceOrderTotal(commerceOrder.getCommerceOrderId()) %>"
							/>

							<liferay-ui:search-container-column-status
								cssClass="table-cell-content"
								name="status"
								status="<%= commerceOrder.getStatus() %>"
							/>

							<liferay-ui:search-container-column-jsp
								cssClass="entry-action-column"
								path="/order_action.jsp"
							/>
						</liferay-ui:search-container-row>

						<liferay-ui:search-iterator displayStyle="<%= displayStyle %>" markupView="lexicon" searchContainer="<%= commerceOrderSearchContainer %>" />
					</liferay-ui:search-container>
				</div>
			</aui:form>
		</div>
	</div>
</div>