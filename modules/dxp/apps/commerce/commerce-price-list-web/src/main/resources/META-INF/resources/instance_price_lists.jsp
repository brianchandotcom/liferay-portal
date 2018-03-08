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
CPInstanceCommercePriceEntryDisplayContext cpInstanceCommercePriceEntryDisplayContext = (CPInstanceCommercePriceEntryDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CPInstance cpInstance = cpInstanceCommercePriceEntryDisplayContext.getCPInstance();

long cpDefinitionId = cpInstanceCommercePriceEntryDisplayContext.getCPDefinitionId();
long cpInstanceId = cpInstanceCommercePriceEntryDisplayContext.getCPInstanceId();

SearchContainer<CommercePriceEntry> commercePriceEntriesSearchContainer = cpInstanceCommercePriceEntryDisplayContext.getSearchContainer();

PortletURL portletURL = cpInstanceCommercePriceEntryDisplayContext.getPortletURL();

PortletURL productSkusURL = PortalUtil.getControlPanelPortletURL(request, CPPortletKeys.CP_DEFINITIONS, lifecycle);

productSkusURL.setParameter("mvcRenderCommandName", "editProductDefinition");
productSkusURL.setParameter("cpDefinitionId", String.valueOf(cpDefinitionId));
productSkusURL.setParameter("screenNavigationCategoryKey", "skus");
%>

<liferay-frontend:management-bar
	includeCheckBox="<%= true %>"
	searchContainerId="commercePriceEntries"
>
	<liferay-frontend:management-bar-buttons>
		<liferay-frontend:management-bar-display-buttons
			displayViews='<%= new String[] {"list"} %>'
			portletURL="<%= portletURL %>"
			selectedDisplayStyle="list"
		/>
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
		<liferay-frontend:management-bar-button href='<%= "javascript:" + renderResponse.getNamespace() + "deleteCommercePriceEntries();" %>' icon="times" label="delete" />
	</liferay-frontend:management-bar-action-buttons>
</liferay-frontend:management-bar>

<div class="container-fluid-1280">
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
					className="com.liferay.commerce.model.CommercePriceEntry"
					cssClass="entry-display-style"
					keyProperty="commercePriceEntryId"
					modelVar="commercePriceEntry"
				>

					<%
					CommercePriceList commercePriceList = commercePriceEntry.getCommercePriceList();

					PortletURL rowURL = renderResponse.createRenderURL();

					rowURL.setParameter("mvcRenderCommandName", "editCPInstanceCommercePriceEntry");
					rowURL.setParameter("commercePriceEntryId", String.valueOf(commercePriceEntry.getCommercePriceEntryId()));
					rowURL.setParameter("cpDefinitionId", String.valueOf(cpDefinitionId));
					rowURL.setParameter("cpInstanceId", String.valueOf(cpInstanceId));
					%>

					<liferay-ui:search-container-column-text
						cssClass="table-cell-content"
						href="<%= rowURL %>"
						name="name"
						value="<%= HtmlUtil.escape(commercePriceList.getName()) %>"
					/>

					<liferay-ui:search-container-column-text
						cssClass="table-cell-content"
						property="price"
					/>

					<liferay-ui:search-container-column-date
						cssClass="table-cell-content"
						name="create-date"
						property="createDate"
					/>

					<liferay-ui:search-container-column-jsp
						cssClass="entry-action-column"
						path="/instance_price_entry_action.jsp"
					/>
				</liferay-ui:search-container-row>

				<liferay-ui:search-iterator markupView="lexicon" searchContainer="<%= commercePriceEntriesSearchContainer %>" />
			</liferay-ui:search-container>
		</div>
	</aui:form>
</div>

<portlet:actionURL name="editCPInstanceCommercePriceEntry" var="addCommercePriceEntryURL" />

<aui:form action="<%= addCommercePriceEntryURL %>" cssClass="hide" name="addCommercePriceEntryFm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.ADD_MULTIPLE %>" />
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
	<aui:input name="cpInstanceId" type="hidden" value="<%= cpInstanceId %>" />
	<aui:input name="commercePriceListIds" type="hidden" value="" />
</aui:form>

<liferay-frontend:add-menu>
	<liferay-frontend:add-menu-item id="addCommercePriceEntry" title='<%= LanguageUtil.format(request, "add-x-to-price-list", cpInstance.getSku(), false) %>' url="javascript:;" />
</liferay-frontend:add-menu>

<aui:script>
	function <portlet:namespace />deleteCommercePriceEntries() {
		if (confirm('<liferay-ui:message key="are-you-sure-you-want-to-delete-the-selected-entries" />')) {
			var form = AUI.$(document.<portlet:namespace />fm);

			form.attr('method', 'post');
			form.fm('<%= Constants.CMD %>').val('<%= Constants.DELETE %>');
			form.fm('deleteCommercePriceEntryIds').val(Liferay.Util.listCheckedExcept(form, '<portlet:namespace />allRowIds'));

			submitForm(form, '<portlet:actionURL name="editCPInstanceCommercePriceEntry" />');
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
					eventName: 'priceListsSelectItem',
					on: {
						selectedItemChange: function(event) {
							var selectedItems = event.newVal;

							if (selectedItems) {
								$('#<portlet:namespace />commercePriceListIds').val(selectedItems);

								var addCommercePriceEntryFm = $('#<portlet:namespace />addCommercePriceEntryFm');

								submitForm(addCommercePriceEntryFm);
							}
						}
					},
					title: '<liferay-ui:message arguments="<%= cpInstance.getSku() %>" key="add-x-to-price-list" />',
					url: '<%= cpInstanceCommercePriceEntryDisplayContext.getItemSelectorUrl() %>'
				}
			);

			itemSelectorDialog.open();
		}
	);
</aui:script>