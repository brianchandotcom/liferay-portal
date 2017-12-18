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
CPDefinitionSpecificationOptionValueDisplayContext cpDefinitionSpecificationOptionValueDisplayContext = (CPDefinitionSpecificationOptionValueDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CPDefinition cpDefinition = cpDefinitionSpecificationOptionValueDisplayContext.getCPDefinition();

long cpDefinitionId = cpDefinitionSpecificationOptionValueDisplayContext.getCPDefinitionId();

SearchContainer<CPDefinitionSpecificationOptionValue> cpDefinitionSpecificationOptionValueSearchContainer = cpDefinitionSpecificationOptionValueDisplayContext.getSearchContainer();

PortletURL portletURL = cpDefinitionSpecificationOptionValueDisplayContext.getPortletURL();
%>

<liferay-frontend:management-bar
	includeCheckBox="<%= true %>"
	searchContainerId="cpDefinitionSpecificationOptionValues"
>
	<liferay-frontend:management-bar-buttons>
		<c:if test="<%= cpDefinitionSpecificationOptionValueDisplayContext.isShowInfoPanel() %>">
			<liferay-frontend:management-bar-sidenav-toggler-button
				icon="info-circle"
				label="info"
			/>
		</c:if>

		<liferay-frontend:management-bar-display-buttons
			displayViews='<%= new String[] {"list"} %>'
			portletURL="<%= portletURL %>"
			selectedDisplayStyle="<%= cpDefinitionSpecificationOptionValueDisplayContext.getDisplayStyle() %>"
		/>
	</liferay-frontend:management-bar-buttons>

	<liferay-frontend:management-bar-filters>
		<liferay-frontend:management-bar-navigation
			navigationKeys='<%= new String[] {"all"} %>'
			portletURL="<%= portletURL %>"
		/>

		<liferay-frontend:management-bar-sort
			orderByCol="<%= cpDefinitionSpecificationOptionValueDisplayContext.getOrderByCol() %>"
			orderByType="<%= cpDefinitionSpecificationOptionValueDisplayContext.getOrderByType() %>"
			orderColumns='<%= new String[] {"priority"} %>'
			portletURL="<%= portletURL %>"
		/>
	</liferay-frontend:management-bar-filters>

	<liferay-frontend:management-bar-action-buttons>
		<c:if test="<%= cpDefinitionSpecificationOptionValueDisplayContext.isShowInfoPanel() %>">
			<liferay-frontend:management-bar-sidenav-toggler-button
				icon="info-circle"
				label="info"
			/>
		</c:if>

		<liferay-frontend:management-bar-button href='<%= "javascript:" + renderResponse.getNamespace() + "deleteCPDefinitionSpecificationOptionValues();" %>' icon="times" label="delete" />
	</liferay-frontend:management-bar-action-buttons>
</liferay-frontend:management-bar>

<div id="<portlet:namespace />productDefinitionSpecificationOptionValuesContainer">
	<div class="closed container-fluid-1280 sidenav-container sidenav-right" id="<portlet:namespace />infoPanelId">
		<c:if test="<%= cpDefinitionSpecificationOptionValueDisplayContext.isShowInfoPanel() %>">
			<liferay-portlet:resourceURL
				copyCurrentRenderParameters="<%= false %>"
				id="cpDefinitionSpecificationOptionValueInfoPanel"
				var="sidebarPanelURL"
			/>

			<liferay-frontend:sidebar-panel
				resourceURL="<%= sidebarPanelURL %>"
				searchContainerId="cpDefinitionSpecificationOptionValues"
			>
				<liferay-util:include page="/definition_specification_option_value_info_panel.jsp" servletContext="<%= application %>" />
			</liferay-frontend:sidebar-panel>
		</c:if>

		<div class="sidenav-content">
			<aui:form action="<%= portletURL.toString() %>" method="post" name="fm">
				<aui:input name="<%= Constants.CMD %>" type="hidden" />
				<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
				<aui:input name="deleteCPDefinitionSpecificationOptionValueIds" type="hidden" />

				<div class="product-specification-option-values-container" id="<portlet:namespace />entriesContainer">
					<liferay-ui:search-container
						id="cpDefinitionSpecificationOptionValues"
						iteratorURL="<%= portletURL %>"
						searchContainer="<%= cpDefinitionSpecificationOptionValueSearchContainer %>"
					>
						<liferay-ui:search-container-row
							className="com.liferay.commerce.product.model.CPDefinitionSpecificationOptionValue"
							cssClass="entry-display-style"
							keyProperty="CPDefinitionSpecificationOptionValueId"
							modelVar="cpDefinitionSpecificationOptionValue"
						>

							<%
							CPSpecificationOption cpSpecificationOption = cpDefinitionSpecificationOptionValue.getCPSpecificationOption();

							PortletURL rowURL = renderResponse.createRenderURL();

							rowURL.setParameter("mvcRenderCommandName", "editProductDefinitionSpecificationOptionValue");
							rowURL.setParameter("cpDefinitionId", String.valueOf(cpDefinitionSpecificationOptionValue.getCPDefinitionId()));
							rowURL.setParameter("cpDefinitionSpecificationOptionValueId", String.valueOf(cpDefinitionSpecificationOptionValue.getCPDefinitionSpecificationOptionValueId()));
							%>

							<liferay-ui:search-container-column-text
								cssClass="table-cell-content"
								href="<%= rowURL %>"
								name="title"
								value="<%= HtmlUtil.escape(cpSpecificationOption.getTitle(languageId)) %>"
							/>

							<liferay-ui:search-container-column-text
								cssClass="table-cell-content"
								name="value"
								value="<%= HtmlUtil.escape(cpDefinitionSpecificationOptionValue.getValue(languageId)) %>"
							/>

							<liferay-ui:search-container-column-text
								cssClass="table-cell-content"
								name="category"
								value="<%= HtmlUtil.escape(cpDefinitionSpecificationOptionValueDisplayContext.getCPOptionCategoryTitle(cpDefinitionSpecificationOptionValue)) %>"
							/>

							<liferay-ui:search-container-column-text
								cssClass="table-cell-content"
								property="priority"
							/>

							<liferay-ui:search-container-column-jsp
								cssClass="entry-action-column"
								path="/definition_specification_option_value_action.jsp"
							/>
						</liferay-ui:search-container-row>

						<liferay-ui:search-iterator markupView="lexicon" searchContainer="<%= cpDefinitionSpecificationOptionValueSearchContainer %>" />
					</liferay-ui:search-container>
				</div>
			</aui:form>
		</div>
	</div>
</div>

<portlet:actionURL name="editProductDefinitionSpecificationOptionValue" var="addCPDefinitionSpecificationOptionValueURL" />

<aui:form action="<%= addCPDefinitionSpecificationOptionValueURL %>" cssClass="hide" name="addCPDefinitionSpecificationOptionValueFm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.ADD %>" />
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
	<aui:input name="cpDefinitionId" type="hidden" value="<%= cpDefinitionId %>" />
	<aui:input name="cpSpecificationOptionIds" type="hidden" value="" />
</aui:form>

<liferay-frontend:add-menu>
	<liferay-frontend:add-menu-item id="addCommerceProductDefinitionSpecificationOptionValue" title='<%= LanguageUtil.get(request, "add-specification-value") %>' url="javascript:;" />
</liferay-frontend:add-menu>

<aui:script>
	function <portlet:namespace />deleteCPDefinitionSpecificationOptionValues() {
		if (confirm('<%= UnicodeLanguageUtil.get(request, "are-you-sure-you-want-to-delete-the-selected-specification-values") %>')) {
			var form = AUI.$(document.<portlet:namespace />fm);

			form.attr('method', 'post');
			form.fm('<%= Constants.CMD %>').val('<%= Constants.DELETE %>');
			form.fm('deleteCPDefinitionSpecificationOptionValueIds').val(Liferay.Util.listCheckedExcept(form, '<portlet:namespace />allRowIds'));

			submitForm(form, '<portlet:actionURL name="editProductDefinitionSpecificationOptionValue" />');
		}
	}
</aui:script>

<aui:script use="liferay-item-selector-dialog">
	$('#<portlet:namespace />addCommerceProductDefinitionSpecificationOptionValue').on(
		'click',
		function(event) {
			event.preventDefault();

			var itemSelectorDialog = new A.LiferayItemSelectorDialog(
				{
					eventName: 'productSpecificationOptionsSelectItem',
					on: {
						selectedItemChange: function(event) {
							var selectedItems = event.newVal;

							if (selectedItems) {
								$('#<portlet:namespace />cpSpecificationOptionIds').val(selectedItems);

								var addCPDefinitionSpecificationOptionValueFm = $('#<portlet:namespace />addCPDefinitionSpecificationOptionValueFm');

								submitForm(addCPDefinitionSpecificationOptionValueFm);
							}
						}
					},
					title: '<liferay-ui:message arguments="<%= cpDefinition.getTitle(languageId) %>" key="add-new-specification-option-value-to-x" />',
					url: '<%= cpDefinitionSpecificationOptionValueDisplayContext.getItemSelectorUrl() %>'
				}
			);

			itemSelectorDialog.open();
		}
	);
</aui:script>