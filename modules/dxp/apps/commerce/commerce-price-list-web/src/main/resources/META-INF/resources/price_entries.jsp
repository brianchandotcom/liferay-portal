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
CommercePriceEntryDisplayContext commercePriceEntryDisplayContext = (CommercePriceEntryDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CommercePriceList commercePriceList = commercePriceEntryDisplayContext.getCommercePriceList();

long commercePriceListId = commercePriceEntryDisplayContext.getCommercePriceListId();

SearchContainer<CommercePriceEntry> commercePriceEntriesSearchContainer = commercePriceEntryDisplayContext.getSearchContainer();

PortletURL portletURL = commercePriceEntryDisplayContext.getPortletURL();
%>

<liferay-frontend:management-bar
	includeCheckBox="<%= true %>"
	searchContainerId="commercePriceEntries"
>
	<liferay-frontend:management-bar-buttons>
		<c:if test="<%= commercePriceEntryDisplayContext.isShowInfoPanel() %>">
			<liferay-frontend:management-bar-sidenav-toggler-button
				icon="info-circle"
				label="info"
			/>
		</c:if>

		<liferay-frontend:management-bar-display-buttons
			displayViews='<%= new String[] {"list"} %>'
			portletURL="<%= portletURL %>"
			selectedDisplayStyle="list"
		/>

		<portlet:actionURL name="editCommercePriceEntry" var="addCommercePriceEntryURL" />

		<aui:form action="<%= addCommercePriceEntryURL %>" cssClass="hide" name="addCommercePriceEntryFm">
			<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.ADD_MULTIPLE %>" />
			<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
			<aui:input name="commercePriceListId" type="hidden" value="<%= commercePriceListId %>" />
			<aui:input name="cpInstanceIds" type="hidden" value="" />
		</aui:form>

		<liferay-frontend:add-menu
			inline="<%= true %>"
		>
			<liferay-frontend:add-menu-item
				id="addCommercePriceEntry"
				title='<%= LanguageUtil.get(request, "add-entry") %>'
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
			orderByCol="<%= commercePriceEntriesSearchContainer.getOrderByCol() %>"
			orderByType="<%= commercePriceEntriesSearchContainer.getOrderByType() %>"
			orderColumns='<%= new String[] {"create-date"} %>'
			portletURL="<%= portletURL %>"
		/>
	</liferay-frontend:management-bar-filters>

	<liferay-frontend:management-bar-action-buttons>
		<c:if test="<%= commercePriceEntryDisplayContext.isShowInfoPanel() %>">
			<liferay-frontend:management-bar-sidenav-toggler-button
				icon="info-circle"
				label="info"
			/>
		</c:if>

		<liferay-frontend:management-bar-button
			href='<%= "javascript:" + renderResponse.getNamespace() + "deleteCommercePriceEntries();" %>'
			icon="times"
			label="delete"
		/>
	</liferay-frontend:management-bar-action-buttons>
</liferay-frontend:management-bar>

<div id="<portlet:namespace />priceEntriesContainer">
	<div class="closed container-fluid-1280 sidenav-container sidenav-right" id="<portlet:namespace />infoPanelId">
		<c:if test="<%= commercePriceEntryDisplayContext.isShowInfoPanel() %>">
			<liferay-portlet:resourceURL copyCurrentRenderParameters="<%= false %>" id="commercePriceEntryInfoPanel" var="sidebarPanelURL" />

			<liferay-frontend:sidebar-panel
				resourceURL="<%= sidebarPanelURL %>"
				searchContainerId="commercePriceEntries"
			>
				<liferay-util:include page="/price_entry_info_panel.jsp" servletContext="<%= application %>" />
			</liferay-frontend:sidebar-panel>
		</c:if>

		<div class="sidenav-content">
			<aui:form action="<%= portletURL.toString() %>" method="post" name="fm">
				<aui:input name="<%= Constants.CMD %>" type="hidden" />
				<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
				<aui:input name="deleteCommercePriceEntryIds" type="hidden" />

				<liferay-ui:error exception="<%= DuplicateCommercePriceEntryException.class %>" message="one-or-more-selected-entries-already-exist" />

				<div class="price-entries-container" id="<portlet:namespace />entriesContainer">
					<liferay-ui:search-container
						id="commercePriceEntries"
						iteratorURL="<%= portletURL %>"
						searchContainer="<%= commercePriceEntriesSearchContainer %>"
					>
						<liferay-ui:search-container-row
							className="com.liferay.commerce.price.list.model.CommercePriceEntry"
							cssClass="entry-display-style"
							keyProperty="commercePriceEntryId"
							modelVar="commercePriceEntry"
						>
							<liferay-ui:search-container-column-text
								cssClass="table-cell-content"
								name="id"
								value="<%= String.valueOf(commercePriceEntry.getCommercePriceEntryId()) %>"
							/>

							<%
							PortletURL rowURL = renderResponse.createRenderURL();

							rowURL.setParameter("mvcRenderCommandName", "editCommercePriceEntry");
							rowURL.setParameter("commercePriceEntryId", String.valueOf(commercePriceEntry.getCommercePriceEntryId()));
							rowURL.setParameter("commercePriceListId", String.valueOf(commercePriceEntry.getCommercePriceListId()));

							CPInstance cpInstance = commercePriceEntry.getCPInstance();

							CPDefinition cpDefinition = cpInstance.getCPDefinition();
							%>

							<liferay-ui:search-container-column-text
								cssClass="table-cell-content"
								href="<%= rowURL %>"
								name="product-title"
								value="<%= HtmlUtil.escape(cpDefinition.getTitle(languageId)) %>"
							/>

							<liferay-ui:search-container-column-text
								cssClass="table-cell-content"
								name="sku"
								value="<%= HtmlUtil.escape(cpInstance.getSku()) %>"
							/>

							<liferay-ui:search-container-column-text
								cssClass="table-cell-content"
								name="price"
								value="<%= HtmlUtil.escape(commercePriceEntryDisplayContext.getCommercePriceEntryPrice(commercePriceEntry)) %>"
							/>

							<liferay-ui:search-container-column-date
								cssClass="table-cell-content"
								name="create-date"
								property="createDate"
							/>

							<liferay-ui:search-container-column-jsp
								cssClass="entry-action-column"
								path="/price_entry_action.jsp"
							/>
						</liferay-ui:search-container-row>

						<liferay-ui:search-iterator
							markupView="lexicon"
							searchContainer="<%= commercePriceEntriesSearchContainer %>"
						/>
					</liferay-ui:search-container>
				</div>
			</aui:form>
		</div>
	</div>
</div>

<aui:script>
	function <portlet:namespace />deleteCommercePriceEntries() {
		if (confirm('<liferay-ui:message key="are-you-sure-you-want-to-delete-the-selected-entries" />')) {
			var form = AUI.$(document.<portlet:namespace />fm);

			form.attr('method', 'post');
			form.fm('<%= Constants.CMD %>').val('<%= Constants.DELETE %>');
			form.fm('deleteCommercePriceEntryIds').val(Liferay.Util.listCheckedExcept(form, '<portlet:namespace />allRowIds'));

			submitForm(form, '<portlet:actionURL name="editCommercePriceEntry" />');
		}
	}
</aui:script>

<aui:script use="liferay-item-selector-dialog">
	$('#<portlet:namespace />addCommercePriceEntry').on(
		'click',
		function(event) {
			event.preventDefault();

			var itemSelectorDialog = new A.LiferayItemSelectorDialog(
				{
					eventName: 'productInstancesSelectItem',
					on: {
						selectedItemChange: function(event) {
							var selectedItems = event.newVal;

							if (selectedItems) {
								$('#<portlet:namespace />cpInstanceIds').val(selectedItems);

								var addCommercePriceEntryFm = $('#<portlet:namespace />addCommercePriceEntryFm');

								submitForm(addCommercePriceEntryFm);
							}
						}
					},
					title: '<liferay-ui:message arguments="<%= commercePriceList.getName() %>" key="add-new-entry-to-x" />',
					url: '<%= commercePriceEntryDisplayContext.getItemSelectorUrl() %>'
				}
			);

			itemSelectorDialog.open();
		}
	);
</aui:script>