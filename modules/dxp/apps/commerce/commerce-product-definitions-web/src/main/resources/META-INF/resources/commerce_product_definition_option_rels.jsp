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
CPDefinitionOptionRelDisplayContext cpDefinitionOptionRelDisplayContext = (CPDefinitionOptionRelDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CPDefinition cpDefinition = cpDefinitionOptionRelDisplayContext.getCPDefinition();

long cpDefinitionId = cpDefinitionOptionRelDisplayContext.getCPDefinitionId();

SearchContainer<CPDefinitionOptionRel> cpDefinitionOptionRelSearchContainer = cpDefinitionOptionRelDisplayContext.getSearchContainer();

PortletURL portletURL = cpDefinitionOptionRelDisplayContext.getPortletURL();

String toolbarItem = ParamUtil.getString(request, "toolbarItem", "view-product-definition-options");

portletURL.setParameter("toolbarItem", toolbarItem);

request.setAttribute("view.jsp-portletURL", portletURL);

PortletURL backUrl = liferayPortletResponse.createRenderURL();

backUrl.setParameter("mvcPath", "/view.jsp");

String backURLString = backUrl.toString();

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(backURLString);

renderResponse.setTitle(cpDefinition.getTitle(languageId));
%>

<%@ include file="/commerce_product_definition_navbar.jspf" %>

<liferay-frontend:management-bar
	includeCheckBox="<%= false %>"
	searchContainerId="cpDefinitionOptionRels"
>
	<liferay-frontend:management-bar-buttons>
		<c:if test="<%= cpDefinitionOptionRelDisplayContext.isShowInfoPanel() %>">
			<liferay-frontend:management-bar-sidenav-toggler-button
				icon="info-circle"
				label="info"
			/>
		</c:if>

		<liferay-frontend:management-bar-display-buttons
			disabled=""
			displayViews='<%= new String[] {"list"} %>'
			portletURL="<%= portletURL %>"
			selectedDisplayStyle="<%= cpDefinitionOptionRelDisplayContext.getDisplayStyle() %>"
		/>
	</liferay-frontend:management-bar-buttons>

	<liferay-frontend:management-bar-filters>
		<liferay-frontend:management-bar-navigation
			navigationKeys='<%= new String[] {"all"} %>'
			portletURL="<%= portletURL %>"
		/>

		<liferay-frontend:management-bar-sort
			orderByCol="<%= cpDefinitionOptionRelDisplayContext.getOrderByCol() %>"
			orderByType="<%= cpDefinitionOptionRelDisplayContext.getOrderByType() %>"
			orderColumns='<%= new String[] {"priority", "create-date", "name"} %>'
			portletURL="<%= portletURL %>"
		/>
	</liferay-frontend:management-bar-filters>

	<liferay-frontend:management-bar-action-buttons>
		<c:if test="<%= cpDefinitionOptionRelDisplayContext.isShowInfoPanel() %>">
			<liferay-frontend:management-bar-sidenav-toggler-button
				icon="info-circle"
				label="info"
			/>
		</c:if>
	</liferay-frontend:management-bar-action-buttons>
</liferay-frontend:management-bar>

<div id="<portlet:namespace />productDefinitionOptionRelsContainer">
<div class="closed container-fluid-1280 sidenav-container sidenav-right" id="<portlet:namespace />infoPanelId">
<c:if test="<%= cpDefinitionOptionRelDisplayContext.isShowInfoPanel() %>">
	<liferay-portlet:resourceURL
		copyCurrentRenderParameters="<%= false %>"
		id="cpDefinitionOptionRelInfoPanel"
		var="sidebarPanelURL"
	/>

	<liferay-frontend:sidebar-panel
		resourceURL="<%= sidebarPanelURL %>"
		searchContainerId="cpDefinitionOptionRels"
	>
		<liferay-util:include page="/commerce_product_definition_option_rel_info_panel.jsp" servletContext="<%= application %>" />
	</liferay-frontend:sidebar-panel>
</c:if>

<div class="sidenav-content">

<aui:form action="<%= portletURL.toString() %>" cssClass="container-fluid-1280" method="post" name="fm">
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />

	<div class="product-definition-option-rels-container" id="<portlet:namespace />entriesContainer">
		<liferay-ui:search-container
			id="cpDefinitionOptionRels"
			iteratorURL="<%= portletURL %>"
			searchContainer="<%= cpDefinitionOptionRelSearchContainer %>"
		>
			<liferay-ui:search-container-row
				className="com.liferay.commerce.product.model.CPDefinitionOptionRel"
				cssClass="entry-display-style"
				keyProperty="cpDefinitionOptionRelId"
				modelVar="cpDefinitionOptionRel"
			>
				<liferay-ui:search-container-column-text
					cssClass="table-cell-content"
					name="name"
				>
					<%= cpDefinitionOptionRel.getName(locale) %>
				</liferay-ui:search-container-column-text>

				<liferay-ui:search-container-column-text
						cssClass="table-cell-content"
						name="facetable"
						property="facetable"
				/>

				<liferay-ui:search-container-column-text
						cssClass="table-cell-content"
						name="skuContributor"
						property="skuContributor"
				/>

				<liferay-ui:search-container-column-text
						cssClass="table-cell-content"
						name="priority"
						property="priority"
				/>

				<liferay-ui:search-container-column-date
					cssClass="table-cell-content"
					name="create-date"
					property="createDate"
				/>

				<liferay-ui:search-container-column-jsp
					cssClass="entry-action-column"
					path="/commerce_product_definition_option_rel_action.jsp"
				/>
			</liferay-ui:search-container-row>

			<liferay-ui:search-iterator markupView="lexicon" searchContainer="<%= cpDefinitionOptionRelSearchContainer %>" />
		</liferay-ui:search-container>
	</div>
</aui:form>

<portlet:actionURL name="editProductDefinitionOptionRel" var="addCPDefinitionOptionRelURL" />

<aui:form action="<%= addCPDefinitionOptionRelURL %>" cssClass="hide" name="addCPDefinitionOptionRelFm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.ADD %>" />
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
	<aui:input name="cpDefinitionId" type="hidden" value="<%= cpDefinitionId %>" />
	<aui:input name="cpOptionId" type="hidden" value="" />
</aui:form>

<liferay-frontend:add-menu>
	<liferay-frontend:add-menu-item id="addCommerceProducOption" title='<%= LanguageUtil.get(request, "add-product-option") %>' url="javascript:;" />
</liferay-frontend:add-menu>

<aui:script use="liferay-item-selector-dialog">

	$('#<portlet:namespace />addCommerceProducOption').on(
		'click',
		function(event) {
			event.preventDefault();

			var itemSelectorDialog = new A.LiferayItemSelectorDialog(
				{
					eventName: 'productOptionsSelectItem',
					on: {
						selectedItemChange: function(event) {
							var selectedItem = event.newVal;

							if (selectedItem != null) {

								if (selectedItem) {
									var cpOptionId = selectedItem.value.id;

									$('#<portlet:namespace />cpOptionId').val(cpOptionId);

									var addCPDefinitionOptionRelFm = $('#<portlet:namespace />addCPDefinitionOptionRelFm');

									submitForm(addCPDefinitionOptionRelFm);
								}
							}
						}
					},
					title: '<liferay-ui:message key="add-new-commerce-product-option-to-x" />',
					url: '<%= cpDefinitionOptionRelDisplayContext.getItemSelectorUrl() %>'
				}
			);

			itemSelectorDialog.open();
		}
	);

</aui:script>