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
String toolbarItem = ParamUtil.getString(request, "toolbarItem", "view-all-carts");

int type = ParamUtil.getInteger(request, "type", CommerceCartConstants.TYPE_CART);

CommerceCartDisplayContext commerceCartDisplayContext = (CommerceCartDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

SearchContainer<CommerceCart> commerceCartSearchContainer = commerceCartDisplayContext.getSearchContainer();

String displayStyle = commerceCartDisplayContext.getDisplayStyle();

PortletURL portletURL = commerceCartDisplayContext.getPortletURL();

portletURL.setParameter("toolbarItem", toolbarItem);
portletURL.setParameter("searchContainerId", "commerceCarts");

request.setAttribute("view.jsp-portletURL", portletURL);
%>

<%@ include file="/navbar.jspf" %>

<liferay-util:include page="/toolbar.jsp" servletContext="<%= application %>">
	<liferay-util:param name="searchContainerId" value="commerceCarts" />
	<liferay-util:param name="type" value="<%= String.valueOf(type) %>" />
</liferay-util:include>

<div id="<portlet:namespace />cartsContainer">
	<div class="closed container-fluid-1280 sidenav-container sidenav-right" id="<portlet:namespace />infoPanelId">
		<c:if test="<%= commerceCartDisplayContext.isShowInfoPanel() %>">
			<liferay-portlet:resourceURL
				copyCurrentRenderParameters="<%= false %>"
				id="commerceCartInfoPanel"
				var="sidebarPanelURL"
			/>

			<liferay-frontend:sidebar-panel
				resourceURL="<%= sidebarPanelURL %>"
				searchContainerId="commerceCarts"
			>
				<liferay-util:include page="/cart_info_panel.jsp" servletContext="<%= application %>" />
			</liferay-frontend:sidebar-panel>
		</c:if>

		<div class="sidenav-content">
			<aui:form action="<%= portletURL.toString() %>" cssClass="container-fluid-1280" method="post" name="fm">
				<aui:input name="<%= Constants.CMD %>" type="hidden" />
				<aui:input name="redirect" type="hidden" value="<%= portletURL.toString() %>" />
				<aui:input name="deleteCommerceCartIds" type="hidden" />

				<div class="carts-container" id="<portlet:namespace />entriesContainer">
					<liferay-ui:search-container
						id="commerceCarts"
						iteratorURL="<%= portletURL %>"
						searchContainer="<%= commerceCartSearchContainer %>"
					>
						<liferay-ui:search-container-row
							className="com.liferay.commerce.model.CommerceCart"
							cssClass="entry-display-style"
							keyProperty="commerceCartId"
							modelVar="commerceCart"
						>

							<%
							PortletURL rowURL = renderResponse.createRenderURL();

							rowURL.setParameter("mvcRenderCommandName", "viewCommerceCartItems");
							rowURL.setParameter("redirect", currentURL);
							rowURL.setParameter("commerceCartId", String.valueOf(commerceCart.getCommerceCartId()));
							rowURL.setParameter("cartToolbarItem", toolbarItem);
							%>

							<liferay-ui:search-container-column-text
								cssClass="table-cell-content"
								href="<%= rowURL %>"
								name="name"
								property="name"
							/>

							<liferay-ui:search-container-column-text
								cssClass="table-cell-content"
								name="user"
								property="userName"
							/>

							<liferay-ui:search-container-column-text
								cssClass="table-cell-content"
								name="total"
								value="<%= commerceCartDisplayContext.getCommerceCartTotal(commerceCart) %>"
							/>

							<liferay-ui:search-container-column-date
								cssClass="table-cell-content"
								name="create-date"
								property="createDate"
							/>

							<liferay-ui:search-container-column-date
								cssClass="table-cell-content"
								name="modified-date"
								property="modifiedDate"
							/>

							<liferay-ui:search-container-column-jsp
								cssClass="entry-action-column"
								path="/cart_action.jsp"
							/>
						</liferay-ui:search-container-row>

						<liferay-ui:search-iterator displayStyle="<%= displayStyle %>" markupView="lexicon" searchContainer="<%= commerceCartSearchContainer %>" />
					</liferay-ui:search-container>
				</div>
			</aui:form>
		</div>
	</div>
</div>