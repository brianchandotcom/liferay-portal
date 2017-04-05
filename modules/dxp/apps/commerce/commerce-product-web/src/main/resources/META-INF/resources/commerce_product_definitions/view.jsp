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

<%@ include file="/commerce_product_definitions/init.jsp" %>

<%
String toolbarItem = ParamUtil.getString(request, "toolbarItem", "view-all-product-definitions");

SearchContainer productDefinitionSearchContainer = commerceProductDisplayContext.getSearchContainer();

String displayStyle = commerceProductDisplayContext.getDisplayStyle();

PortletURL portletURL = commerceProductDisplayContext.getPortletURL();

portletURL.setParameter("toolbarItem", toolbarItem);
portletURL.setParameter("searchContainerId", "commerceProductDefinitions");

request.setAttribute("view.jsp-portletURL", portletURL);
%>

<liferay-portlet:renderURL varImpl="viewProductsURL">
	<portlet:param name="toolbarItem" value="view-all-product-definitions" />
	<portlet:param name="jspPage" value="/commerce_product_definitions/view.jsp" />
</liferay-portlet:renderURL>

<aui:nav-bar cssClass="collapse-basic-search" markupView="lexicon">
	<aui:nav cssClass="navbar-nav">
		<aui:nav-item
			href="<%= viewProductsURL.toString() %>"
			label="Catalog"
			selected='<%= toolbarItem.equals("view-all-product-definitions") %>'
		/>
	</aui:nav>

	<aui:form action="<%= portletURL.toString() %>" name="searchFm">
		<aui:nav-bar-search>
			<liferay-ui:input-search markupView="lexicon" />
		</aui:nav-bar-search>
	</aui:form>
</aui:nav-bar>

<liferay-util:include page="/commerce_product_definitions/toolbar.jsp" servletContext="<%= application %>">
	<liferay-util:param name="searchContainerId" value="commerceProductDefinitions" />
</liferay-util:include>

<div id="<portlet:namespace />journalContainer">
	<div class="closed container-fluid-1280 sidenav-container sidenav-right" id="<portlet:namespace />infoPanelId">
		<c:if test="<%= commerceProductDisplayContext.isShowInfoPanel() %>">
			<liferay-portlet:resourceURL
				copyCurrentRenderParameters="<%= false %>"
				id="/commerce_product_definitions/info_panel"
				var="sidebarPanelURL"
			/>

			<liferay-frontend:sidebar-panel
				resourceURL="<%= sidebarPanelURL %>"
				searchContainerId="commerceProductDefinitions"
			>
				<liferay-util:include page="/commerce_product_definitions/info_panel.jsp" servletContext="<%= application %>" />
			</liferay-frontend:sidebar-panel>
		</c:if>

		<div class="sidenav-content">
			<div class="journal-breadcrumb" id="<portlet:namespace />breadcrumbContainer">
				<liferay-util:include page="/commerce_product_definitions/breadcrumb.jsp" servletContext="<%= application %>" />
			</div>

			<aui:form action="<%= portletURL.toString() %>" cssClass="container-fluid-1280" method="post" name="fm">
				<div class="journal-container" id="<portlet:namespace />entriesContainer">
					<liferay-ui:section>
						<liferay-ui:search-container
							emptyResultsMessage="no-product-was-found"
							id="commerceProductDefinitions"
							searchContainer="<%= productDefinitionSearchContainer %>"
						>
							<liferay-ui:search-container-row
								className="com.liferay.commerce.product.model.CommerceProductDefinition"
								keyProperty="commerceProductDefinitionId"
								modelVar="commerceProductDefinition"
							>
								<%@ include file="/commerce_product_definitions/search_columns.jspf" %>
							</liferay-ui:search-container-row>

							<liferay-ui:search-iterator markupView="lexicon" />
						</liferay-ui:search-container>
					</liferay-ui:section>
				</div>
			</aui:form>
		</div>
	</div>
</div>

<liferay-portlet:renderURL var="editProductDefinitionURL">
	<portlet:param name="mvcRenderCommandName" value="/commerce_product_definitions/edit_product_definition" />
	<portlet:param name="backURL" value="<%= PortalUtil.getCurrentCompleteURL(request) %>" />
</liferay-portlet:renderURL>

<liferay-frontend:add-menu>
	<liferay-frontend:add-menu-item title="add-product" url="<%= editProductDefinitionURL.toString() %>" />
</liferay-frontend:add-menu>