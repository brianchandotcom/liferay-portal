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

renderResponse.setTitle(cpDefinition.getTitle(themeDisplay.getLanguageId()));
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

		<portlet:actionURL name="editCPDefinitionGroupedEntry" var="addDefinitionGroupedEntryURL">
			<portlet:param name="mvcRenderCommandName" value="viewCPDefinitionGroupedEntries" />
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
			href='<%= "javascript:" + renderResponse.getNamespace() + "deleteCPDefinitionGroupedEntries();" %>'
			icon="times"
			label="delete"
		/>
	</liferay-frontend:management-bar-action-buttons>
</liferay-frontend:management-bar>

<div id="<portlet:namespace />cpDefinitionGroupedEntriesContainer">
	<div class="closed container-fluid-1280 sidenav-container sidenav-right" id="<portlet:namespace />infoPanelId">
		<c:if test="<%= cpDefinitionGroupedEntriesDisplayContext.isShowInfoPanel() %>">
			<liferay-portlet:resourceURL copyCurrentRenderParameters="<%= false %>" id="cpDefinitionGroupedEntryInfoPanel" var="sidebarPanelURL" />

			<liferay-frontend:sidebar-panel
				resourceURL="<%= sidebarPanelURL %>"
				searchContainerId="cpDefinitionGroupedEntries"
			>
				<liferay-util:include page="/definition_grouped_entry_info_panel.jsp" servletContext="<%= application %>" />
			</liferay-frontend:sidebar-panel>
		</c:if>

		<div class="sidenav-content">
			<aui:form action="<%= portletURL.toString() %>" cssClass="container-fluid-1280" method="post" name="fm">
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
							cssClass="entry-display-style"
							keyProperty="CPDefinitionGroupedEntryId"
							modelVar="cpDefinitionGroupedEntry"
						>

							<%
							PortletURL rowURL = renderResponse.createRenderURL();

							rowURL.setParameter("mvcRenderCommandName", "editCPDefinitionGroupedEntry");
							rowURL.setParameter("cpDefinitionId", String.valueOf(cpDefinitionGroupedEntry.getCPDefinitionId()));
							rowURL.setParameter("cpDefinitionGroupedEntryId", String.valueOf(cpDefinitionGroupedEntry.getCPDefinitionGroupedEntryId()));

							CPDefinition entryCPDefinition = cpDefinitionGroupedEntry.getEntryCPDefinition();
							%>

							<liferay-ui:search-container-column-text
								cssClass="table-cell-content"
								href="<%= rowURL %>"
								name="title"
								value="<%= HtmlUtil.escape(entryCPDefinition.getTitle(themeDisplay.getLanguageId())) %>"
							/>

							<liferay-ui:search-container-column-text
								cssClass="table-cell-content"
								property="quantity"
							/>

							<liferay-ui:search-container-column-text
								cssClass="table-cell-content"
								property="priority"
							/>

							<liferay-ui:search-container-column-jsp
								cssClass="entry-action-column"
								path="/definition_grouped_entry_action.jsp"
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
		if (confirm('<liferay-ui:message key="are-you-sure-you-want-to-delete-the-selected-entries" />')) {
			var form = AUI.$(document.<portlet:namespace />fm);

			form.attr('method', 'post');
			form.fm('<%= Constants.CMD %>').val('<%= Constants.DELETE %>');
			form.fm('deleteCPDefinitionGroupedEntryIds').val(Liferay.Util.listCheckedExcept(form, '<portlet:namespace />allRowIds'));

			submitForm(form, '<portlet:actionURL name="editCPDefinitionGroupedEntry" />');
		}
	}
</aui:script>

<aui:script use="liferay-item-selector-dialog">
	$('#<portlet:namespace />addDefinitionGroupedEntry').on(
		'click',
		function(event) {
			event.preventDefault();

			var itemSelectorDialog = new A.LiferayItemSelectorDialog(
				{
					eventName: 'productDefinitionsSelectItem',
					on: {
						selectedItemChange: function(event) {
							var <portlet:namespace />addCPDefinitionIds = [];

							var selectedItems = event.newVal;

							if (selectedItems) {
								A.Array.each(
									selectedItems,
									function(item, index, selectedItems) {
										<portlet:namespace />addCPDefinitionIds.push(item.cpDefinitionId);
									}
								);

								$('#<portlet:namespace />entryCPDefinitionIds').val(<portlet:namespace />addCPDefinitionIds.join(','));

								var addCPDefinitionGroupedEntryFm = $('#<portlet:namespace />addCPDefinitionGroupedEntryFm');

								submitForm(addCPDefinitionGroupedEntryFm);
							}
						}
					},
					title: '<liferay-ui:message arguments="<%= cpDefinition.getTitle(themeDisplay.getLanguageId()) %>" key="add-new-grouped-entry-to-x" />',
					url: '<%= cpDefinitionGroupedEntriesDisplayContext.getItemSelectorUrl() %>'
				}
			);

			itemSelectorDialog.open();
		}
	);
</aui:script>