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
String toolbarItem = ParamUtil.getString(request, "toolbarItem", "view-all-product-options");

SearchContainer<CommerceProductOption> productOptionSearchContainer = commerceProductOptionsDisplayContext.getSearchContainer();

String displayStyle = commerceProductOptionsDisplayContext.getDisplayStyle();

PortletURL portletURL = commerceProductOptionsDisplayContext.getPortletURL();

portletURL.setParameter("toolbarItem", toolbarItem);
portletURL.setParameter("searchContainerId", "commerceProductOptions");

request.setAttribute("view.jsp-portletURL", portletURL);
%>

<liferay-portlet:renderURL varImpl="viewProductOptionsURL">
	<portlet:param name="toolbarItem" value="view-all-product-options" />
	<portlet:param name="jspPage" value="/view.jsp" />
</liferay-portlet:renderURL>

<aui:nav-bar cssClass="collapse-basic-search" markupView="lexicon">
	<aui:nav cssClass="navbar-nav">
		<aui:nav-item
			href="<%= viewProductOptionsURL.toString() %>"
			label="product-options"
			selected='<%= toolbarItem.equals("view-all-product-options") %>'
		/>
	</aui:nav>

	<aui:form action="<%= portletURL.toString() %>" name="searchFm">
		<aui:nav-bar-search>
			<liferay-ui:input-search markupView="lexicon" />
		</aui:nav-bar-search>
	</aui:form>
</aui:nav-bar>

<liferay-util:include page="/toolbar.jsp" servletContext="<%= application %>">
	<liferay-util:param name="searchContainerId" value="commerceProductOptions" />
</liferay-util:include>

<div id="<portlet:namespace />productOptionsContainer">
	<div class="closed container-fluid-1280 sidenav-container sidenav-right" id="<portlet:namespace />infoPanelId">
		<c:if test="<%= commerceProductOptionsDisplayContext.isShowInfoPanel() %>">
			<liferay-portlet:resourceURL
				copyCurrentRenderParameters="<%= false %>"
				id="/commerce_product_options/info_panel"
				var="sidebarPanelURL"
			/>

			<liferay-frontend:sidebar-panel
				resourceURL="<%= sidebarPanelURL %>"
				searchContainerId="commerceProductOptions"
			>
				<liferay-util:include page="/info_panel.jsp" servletContext="<%= application %>" />
			</liferay-frontend:sidebar-panel>
		</c:if>

		<div class="sidenav-content">
			<aui:form action="<%= portletURL.toString() %>" cssClass="container-fluid-1280" method="post" name="fm">
				<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />

				<div class="product-options-container" id="<portlet:namespace />entriesContainer">
					<liferay-ui:search-container
						emptyResultsMessage="no-product-options-were-found"
						id="commerceProductOptions"
						searchContainer="<%= productOptionSearchContainer %>"
					>
						<liferay-ui:search-container-row
							className="com.liferay.commerce.product.model.CommerceProductOption"
							cssClass="entry-display-style"
							keyProperty="commerceProductOptionId"
							modelVar="commerceProductOption"
						>
							<liferay-ui:search-container-column-text
								cssClass="table-cell-content"
								name="name"
							>
								<%= commerceProductOption.getName(locale) %>
							</liferay-ui:search-container-column-text>

							<liferay-ui:search-container-column-date
								cssClass="table-cell-content"
								name="modified-date"
								property="modifiedDate"
							/>

							<liferay-ui:search-container-column-jsp
								cssClass="entry-action-column"
								path="/commerce_product_option_action.jsp"
							/>
						</liferay-ui:search-container-row>

						<liferay-ui:search-iterator displayStyle="<%= displayStyle %>" markupView="lexicon" searchContainer="<%= productOptionSearchContainer %>" />
					</liferay-ui:search-container>
				</div>
			</aui:form>
		</div>
	</div>
</div>

<liferay-portlet:renderURL var="addProductOptionURL">
	<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.ADD %>" />
	<portlet:param name="mvcRenderCommandName" value="/commerce_product_options/edit_product_option" />
	<portlet:param name="redirect" value="<%= currentURL %>" />
</liferay-portlet:renderURL>

<liferay-frontend:add-menu>
	<liferay-frontend:add-menu-item title='<%= LanguageUtil.get(request, "add-product-option") %>' url="<%= addProductOptionURL.toString() %>" />
</liferay-frontend:add-menu>