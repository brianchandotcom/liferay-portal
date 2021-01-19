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
CPDefinitionGroupedEntriesDisplayContext cpDefinitionGroupedEntriesDisplayContext = (CPDefinitionGroupedEntriesDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CPDefinition cpDefinition = cpDefinitionGroupedEntriesDisplayContext.getCPDefinition();
SearchContainer<CPDefinitionGroupedEntry> cpDefinitionGroupedEntrySearchContainer = cpDefinitionGroupedEntriesDisplayContext.getSearchContainer();

PortletURL portletURL = cpDefinitionGroupedEntriesDisplayContext.getPortletURL();

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(catalogURL);

renderResponse.setTitle(cpDefinition.getName(themeDisplay.getLanguageId()));
%>

<liferay-ui:error exception="<%= NoSuchCPDefinitionException.class %>" message="please-select-a-valid-product" />

<liferay-frontend:management-bar
	includeCheckBox="<%= true %>"
	searchContainerId="cpDefinitionGroupedEntries"
>
	<liferay-frontend:management-bar-buttons>
		<c:if test="<%= cpDefinitionGroupedEntriesDisplayContext.isShowInfoPanel() %>">
			<liferay-frontend:management-bar-sidenav-toggler-button
				icon="info-circle"
				label="info"
			/>
		</c:if>

		<liferay-frontend:management-bar-display-buttons
			displayViews='<%= new String[] {"list"} %>'
			portletURL="<%= portletURL %>"
			selectedDisplayStyle="<%= cpDefinitionGroupedEntriesDisplayContext.getDisplayStyle() %>"
		/>

		<portlet:actionURL name="/cp_definitions/edit_cp_definition_grouped_entry" var="addDefinitionGroupedEntryURL">
			<portlet:param name="mvcRenderCommandName" value="/cp_definitions/view_cp_definition_grouped_entries" />
		</portlet:actionURL>

		<aui:form action="<%= addDefinitionGroupedEntryURL %>" cssClass="hide" name="addCPDefinitionGroupedEntryFm">
			<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.ADD %>" />
			<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
			<aui:input name="cpDefinitionId" type="hidden" value="<%= cpDefinition.getCPDefinitionId() %>" />
			<aui:input name="entryCPDefinitionIds" type="hidden" value="" />
		</aui:form>

		<liferay-frontend:add-menu
			inline="<%= true %>"
		>
			<liferay-frontend:add-menu-item
				id="addDefinitionGroupedEntry"
				title='<%= cpDefinitionGroupedEntriesDisplayContext.getLabel(locale, "add-grouped-entry") %>'
				url="javascript:;"
			/>
		</liferay-frontend:add-menu>
	</liferay-frontend:management-bar-buttons>

	<liferay-frontend:management-bar-filters>
		<liferay-frontend:management-bar-navigation
			navigationKeys='<%= new String[] {"all"} %>'
			portletURL="<%= portletURL %>"
		/>

		<liferay-frontend:management-bar-sort
			orderByCol="<%= cpDefinitionGroupedEntriesDisplayContext.getOrderByCol() %>"
			orderByType="<%= cpDefinitionGroupedEntriesDisplayContext.getOrderByType() %>"
			orderColumns='<%= new String[] {"priority", "quantity"} %>'
			portletURL="<%= portletURL %>"
		/>
	</liferay-frontend:management-bar-filters>

	<liferay-frontend:management-bar-action-buttons>
		<c:if test="<%= cpDefinitionGroupedEntriesDisplayContext.isShowInfoPanel() %>">
			<liferay-frontend:management-bar-sidenav-toggler-button
				icon="info-circle"
				label="info"
			/>
		</c:if>

		<liferay-frontend:management-bar-button
			href='<%= "javascript:" + liferayPortletResponse.getNamespace() + "deleteCPDefinitionGroupedEntries();" %>'
			icon="times"
			label="delete"
		/>
	</liferay-frontend:management-bar-action-buttons>
</liferay-frontend:management-bar>

<div id="<portlet:namespace />cpDefinitionGroupedEntriesContainer">
	<div class="closed container-fluid container-fluid-max-xl sidenav-container sidenav-right" id="<portlet:namespace />infoPanelId">
		<c:if test="<%= cpDefinitionGroupedEntriesDisplayContext.isShowInfoPanel() %>">
			<liferay-portlet:resourceURL copyCurrentRenderParameters="<%= false %>" id="/cp_definitions/cp_definition_grouped_entry_info_panel" var="sidebarPanelURL" />

			<liferay-frontend:sidebar-panel
				resourceURL="<%= sidebarPanelURL %>"
				searchContainerId="cpDefinitionGroupedEntries"
			>
				<liferay-util:include page="/cp_definition_grouped_entry_info_panel.jsp" servletContext="<%= application %>" />
			</liferay-frontend:sidebar-panel>
		</c:if>

		<div class="sidenav-content">
			<aui:form action="<%= portletURL.toString() %>" cssClass="container-fluid container-fluid-max-xl" method="post" name="fm">
				<aui:input name="<%= Constants.CMD %>" type="hidden" />
				<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
				<aui:input name="deleteCPDefinitionGroupedEntryIds" type="hidden" />

				<div id="<portlet:namespace />entriesContainer">
					<liferay-ui:search-container
						id="cpDefinitionGroupedEntries"
						iteratorURL="<%= portletURL %>"
						searchContainer="<%= cpDefinitionGroupedEntrySearchContainer %>"
					>
						<liferay-ui:search-container-row
							className="com.liferay.commerce.product.type.grouped.model.CPDefinitionGroupedEntry"
							keyProperty="CPDefinitionGroupedEntryId"
							modelVar="cpDefinitionGroupedEntry"
						>

							<%
							PortletURL rowURL = renderResponse.createRenderURL();

							rowURL.setParameter("mvcRenderCommandName", "/cp_definitions/edit_cp_definition_grouped_entry");
							rowURL.setParameter("cpDefinitionId", String.valueOf(cpDefinitionGroupedEntry.getCPDefinitionId()));
							rowURL.setParameter("cpDefinitionGroupedEntryId", String.valueOf(cpDefinitionGroupedEntry.getCPDefinitionGroupedEntryId()));

							CProduct cProduct = cpDefinitionGroupedEntry.getEntryCProduct();

							CPDefinition cProductCPDefinition = CPDefinitionLocalServiceUtil.getCPDefinition(cProduct.getPublishedCPDefinitionId());
							%>

							<liferay-ui:search-container-column-text
								cssClass="important table-cell-expand"
								href="<%= rowURL %>"
								name="name"
								value="<%= HtmlUtil.escape(cProductCPDefinition.getName(themeDisplay.getLanguageId())) %>"
							/>

							<liferay-ui:search-container-column-text
								cssClass="table-cell-expand"
								property="quantity"
							/>

							<liferay-ui:search-container-column-text
								cssClass="table-cell-expand"
								property="priority"
							/>

							<liferay-ui:search-container-column-jsp
								cssClass="entry-action-column"
								path="/cp_definition_grouped_entry_action.jsp"
							/>
						</liferay-ui:search-container-row>

						<liferay-ui:search-iterator
							markupView="lexicon"
							searchContainer="<%= cpDefinitionGroupedEntrySearchContainer %>"
						/>
					</liferay-ui:search-container>
				</div>
			</aui:form>
		</div>
	</div>
</div>

<aui:script>
	function <portlet:namespace />deleteCPDefinitionGroupedEntries() {
		if (
			confirm(
				'<liferay-ui:message key="are-you-sure-you-want-to-delete-the-selected-entries" />'
			)
		) {
			var form = window.document['<portlet:namespace />fm'];

			form.setAttribute('method', 'post');
			form['<portlet:namespace /><%= Constants.CMD %>'].value =
				'<%= Constants.DELETE %>';
			form[
				'<portlet:namespace />deleteCPDefinitionGroupedEntryIds'
			].value = Liferay.Util.listCheckedExcept(
				form,
				'<portlet:namespace />allRowIds'
			);

			submitForm(
				form,
				'<portlet:actionURL name="/cp_definitions/edit_cp_definition_grouped_entry" />'
			);
		}
	}
</aui:script>

<aui:script use="liferay-item-selector-dialog">
	window.document
		.querySelector('#<portlet:namespace />addDefinitionGroupedEntry')
		.addEventListener('click', function (event) {
			event.preventDefault();

			var itemSelectorDialog = new A.LiferayItemSelectorDialog({
				eventName: 'productDefinitionsSelectItem',
				on: {
					selectedItemChange: function (event) {
						var <portlet:namespace />addCPDefinitionIds = [];

						var selectedItems = event.newVal;

						if (selectedItems) {
							window.document.querySelector(
								'#<portlet:namespace />entryCPDefinitionIds'
							).value = selectedItems;

							var addCPDefinitionGroupedEntryFm = window.document.querySelector(
								'#<portlet:namespace />addCPDefinitionGroupedEntryFm'
							);

							submitForm(addCPDefinitionGroupedEntryFm);
						}
					},
				},
				title:
					'<liferay-ui:message arguments="<%= cpDefinition.getName(themeDisplay.getLanguageId()) %>" key="add-new-grouped-entry-to-x" />',
				url:
					'<%= cpDefinitionGroupedEntriesDisplayContext.getItemSelectorUrl() %>',
			});

			itemSelectorDialog.open();
		});
</aui:script>