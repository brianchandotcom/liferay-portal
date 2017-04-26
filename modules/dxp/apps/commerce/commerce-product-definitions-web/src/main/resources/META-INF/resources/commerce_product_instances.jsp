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
CommerceProductInstanceDisplayContext commerceProductInstanceDisplayContext = (CommerceProductInstanceDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CommerceProductDefinition commerceProductDefinition = commerceProductInstanceDisplayContext.getCommerceProductDefinition();

long commerceProductDefinitionId = commerceProductInstanceDisplayContext.getCommerceProductDefinitionId();

SearchContainer<CommerceProductInstance> commerceProductInstanceSearchContainer = commerceProductInstanceDisplayContext.getSearchContainer();

PortletURL portletURL = commerceProductInstanceDisplayContext.getPortletURL();

String orderByCol = ParamUtil.getString(request, "orderByCol", "sku");
String orderByType = ParamUtil.getString(request, "orderByType", "asc");

String toolbarItem = ParamUtil.getString(request, "toolbarItem", "view-product-instances");

portletURL.setParameter("toolbarItem", toolbarItem);
portletURL.setParameter("mvcRenderCommandName", "viewProductInstances");
portletURL.setParameter("redirect", redirect);
portletURL.setParameter("backURL", backURL);
portletURL.setParameter("commerceProductDefinitionId", String.valueOf(commerceProductDefinitionId));
portletURL.setParameter("orderByCol", orderByCol);
portletURL.setParameter("orderByType", orderByType);
portletURL.setParameter("searchContainerId", "commerceProductInstances");

request.setAttribute("view.jsp-portletURL", portletURL);

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(backURL);

renderResponse.setTitle((commerceProductDefinition == null) ? LanguageUtil.get(request, "add-product-definition") : commerceProductDefinition.getTitle(languageId));
%>

<%@ include file="/commerce_product_definition_navbar.jspf" %>

<liferay-frontend:management-bar
	includeCheckBox="<%= false %>"
	searchContainerId="commerceProductInstances"
>
	<liferay-frontend:management-bar-buttons>
		<c:if test="<%= commerceProductInstanceDisplayContext.isShowInfoPanel() %>">
			<liferay-frontend:management-bar-sidenav-toggler-button
				icon="info-circle"
				label="info"
			/>
		</c:if>

		<liferay-frontend:management-bar-display-buttons
			displayViews='<%= new String[] {"icon", "descriptive", "list"} %>'
			portletURL="<%= portletURL %>"
			selectedDisplayStyle="<%= commerceProductInstanceDisplayContext.getDisplayStyle() %>"
		/>
	</liferay-frontend:management-bar-buttons>

	<liferay-frontend:management-bar-filters>
		<liferay-frontend:management-bar-navigation
			navigationKeys='<%= new String[] {"all"} %>'
			portletURL="<%= portletURL %>"
		/>

		<liferay-frontend:management-bar-sort
			orderByCol="<%= orderByCol %>"
			orderByType="<%= orderByType %>"
			orderColumns='<%= new String[] {"sku", "create-date", "display-date"} %>'
			portletURL="<%= portletURL %>"
		/>
	</liferay-frontend:management-bar-filters>

	<liferay-frontend:management-bar-action-buttons>
		<c:if test="<%= commerceProductInstanceDisplayContext.isShowInfoPanel() %>">
			<liferay-frontend:management-bar-sidenav-toggler-button
				icon="info-circle"
				label="info"
			/>
		</c:if>
	</liferay-frontend:management-bar-action-buttons>
</liferay-frontend:management-bar>

<div id="<portlet:namespace />productInstancesContainer">
	<div class="closed container-fluid-1280 sidenav-container sidenav-right" id="<portlet:namespace />infoPanelId">
		<c:if test="<%= commerceProductInstanceDisplayContext.isShowInfoPanel() %>">
			<liferay-portlet:resourceURL
				copyCurrentRenderParameters="<%= false %>"
				id="commerceProductInstanceInfoPanel"
				var="sidebarPanelURL"
			/>

	<liferay-frontend:sidebar-panel
		resourceURL="<%= sidebarPanelURL %>"
		searchContainerId="commerceProductInstances"
	>
		<liferay-util:include page="/commerce_product_instance_info_panel.jsp" servletContext="<%= application %>" />
	</liferay-frontend:sidebar-panel>
</c:if>

<div class="sidenav-content">
<aui:form action="<%= portletURL.toString() %>" cssClass="container-fluid-1280" method="post" name="fm">
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />

	<div class="product-skus-container" id="<portlet:namespace />entriesContainer">
		<liferay-ui:search-container
			id="commerceProductInstances"
			iteratorURL="<%= portletURL %>"
			searchContainer="<%= commerceProductInstanceSearchContainer %>"
		>
			<liferay-ui:search-container-row
				className="com.liferay.commerce.product.model.CommerceProductInstance"
				cssClass="entry-display-style"
				keyProperty="commerceProductInstanceId"
				modelVar="commerceProductInstance"
			>

				<%
				PortletURL rowURL = renderResponse.createRenderURL();

				rowURL.setParameter(Constants.CMD, Constants.UPDATE);
				rowURL.setParameter("mvcRenderCommandName", "editProductInstance");
				rowURL.setParameter("redirect", currentURL);
				rowURL.setParameter("commerceProductDefinitionId", String.valueOf(commerceProductDefinitionId));
				rowURL.setParameter("commerceProductInstanceId", String.valueOf(commerceProductInstance.getCommerceProductInstanceId()));
				%>

				<liferay-ui:search-container-column-text
					cssClass="table-cell-content"
					href="<%= rowURL %>"
					name="sku"
					property="sku"
				/>

				<liferay-ui:search-container-column-jsp
					cssClass="entry-action-column"
					path="/commerce_product_instance_action.jsp"
				/>
			</liferay-ui:search-container-row>

			<liferay-ui:search-iterator markupView="lexicon" searchContainer="<%= commerceProductInstanceSearchContainer %>" />
		</liferay-ui:search-container>
	</div>
</aui:form>

<liferay-portlet:actionURL name="editProductInstance" var="addProductDefinitionURL">
	<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.ADD_MULTIPLE %>" />
	<portlet:param name="redirect" value="<%= currentURL %>" />
	<portlet:param name="backURL" value="<%= backURL %>" />
	<portlet:param name="commerceProductDefinitionId" value="<%= String.valueOf(commerceProductDefinitionId) %>" />
	<portlet:param name="toolbarItem" value="view-product-instances" />
</liferay-portlet:actionURL>

<liferay-frontend:add-menu>
	<liferay-frontend:add-menu-item id="addSkuButton" title='<%= LanguageUtil.get(request, "add-sku") %>' url="javascript:;" />
	<liferay-frontend:add-menu-item title='<%= LanguageUtil.get(request, "generate-all-skus") %>' url="<%= addProductDefinitionURL.toString() %>" />
</liferay-frontend:add-menu>