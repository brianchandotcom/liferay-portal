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
String toolbarItem = ParamUtil.getString(request, "toolbarItem", "view-all-product-definitions");

SearchContainer productDefinitionSearchContainer = commerceProductDefinitionsDisplayContext.getSearchContainer();

String displayStyle = commerceProductDefinitionsDisplayContext.getDisplayStyle();

PortletURL portletURL = commerceProductDefinitionsDisplayContext.getPortletURL();

portletURL.setParameter("toolbarItem", toolbarItem);
portletURL.setParameter("searchContainerId", "commerceProductDefinitions");

request.setAttribute("view.jsp-portletURL", portletURL);
%>

<liferay-portlet:renderURL varImpl="viewProductDefinitionsURL">
	<portlet:param name="toolbarItem" value="view-all-product-definitions" />
	<portlet:param name="jspPage" value="/view.jsp" />
</liferay-portlet:renderURL>

<aui:nav-bar cssClass="collapse-basic-search" markupView="lexicon">
	<aui:nav cssClass="navbar-nav">
		<aui:nav-item
			href="<%= viewProductDefinitionsURL.toString() %>"
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

<liferay-util:include page="/toolbar.jsp" servletContext="<%= application %>">
	<liferay-util:param name="searchContainerId" value="commerceProductDefinitions" />
</liferay-util:include>

<div id="<portlet:namespace />productsContainer">
	<div class="closed container-fluid-1280 sidenav-container sidenav-right" id="<portlet:namespace />infoPanelId">
		<c:if test="<%= commerceProductDefinitionsDisplayContext.isShowInfoPanel() %>">
			<liferay-portlet:resourceURL
				copyCurrentRenderParameters="<%= false %>"
				id="infoPanel"
				var="sidebarPanelURL"
			/>

			<liferay-frontend:sidebar-panel
				resourceURL="<%= sidebarPanelURL %>"
				searchContainerId="commerceProductDefinitions"
			>
				<liferay-util:include page="/info_panel.jsp" servletContext="<%= application %>" />
			</liferay-frontend:sidebar-panel>
		</c:if>

		<div class="sidenav-content">
			<aui:form action="<%= portletURL.toString() %>" cssClass="container-fluid-1280" method="post" name="fm">
				<div class="products-container" id="<portlet:namespace />entriesContainer">
					<liferay-ui:search-container
						emptyResultsMessage="no-products-were-found"
						id="commerceProductDefinitions"
						searchContainer="<%= productDefinitionSearchContainer %>"
					>
						<liferay-ui:search-container-row
							className="com.liferay.commerce.product.model.CommerceProductDefinition"
							cssClass="entry-display-style"
							keyProperty="commerceProductDefinitionId"
							modelVar="commerceProductDefinition"
						>
							<c:choose>
								<c:when test='<%= displayStyle.equals("descriptive") %>'>
									<%@ include file="/commerce_product_definition_descriptive.jspf" %>
								</c:when>
								<c:when test='<%= displayStyle.equals("icon") %>'>

									<%
									row.setCssClass("entry-card lfr-asset-folder " + row.getCssClass());
									%>

									<liferay-ui:search-container-column-text>
										<liferay-frontend:icon-vertical-card
											actionJsp="/commerce_product_definition_action.jsp"
											actionJspServletContext="<%= application %>"
											icon="web-content"
											resultRow="<%= row %>"
											rowChecker="<%= commerceProductDefinitionsDisplayContext.getRowChecker() %>"
											title="<%= HtmlUtil.escape(commerceProductDefinition.getTitle(locale)) %>"
										>
											<%@ include file="/commerce_product_definition_vertical_card.jspf" %>
										</liferay-frontend:icon-vertical-card>
									</liferay-ui:search-container-column-text>
								</c:when>
								<c:otherwise>
									<%@ include file="/commerce_product_definition_columns.jspf" %>
								</c:otherwise>
							</c:choose>
						</liferay-ui:search-container-row>

						<liferay-ui:search-iterator displayStyle="<%= displayStyle %>" markupView="lexicon" searchContainer="<%= productDefinitionSearchContainer %>" />
					</liferay-ui:search-container>
				</div>
			</aui:form>
		</div>
	</div>
</div>

<liferay-portlet:renderURL var="editProductDefinitionURL">
	<portlet:param name="mvcRenderCommandName" value="editProductDefinition" />
	<portlet:param name="backURL" value="<%= PortalUtil.getCurrentCompleteURL(request) %>" />
</liferay-portlet:renderURL>

<liferay-frontend:add-menu>
	<liferay-frontend:add-menu-item title="add-product" url="<%= editProductDefinitionURL.toString() %>" />
</liferay-frontend:add-menu>