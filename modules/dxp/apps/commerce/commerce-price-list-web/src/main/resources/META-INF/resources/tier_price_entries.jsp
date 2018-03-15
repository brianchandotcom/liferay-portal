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
CommerceTierPriceEntryDisplayContext commerceTierPriceEntryDisplayContext = (CommerceTierPriceEntryDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CommercePriceList commercePriceList = commerceTierPriceEntryDisplayContext.getCommercePriceList();

CommercePriceEntry commercePriceEntry = commerceTierPriceEntryDisplayContext.getCommercePriceEntry();

CPInstance cpInstance = commercePriceEntry.getCPInstance();
CPDefinition cpDefinition = cpInstance.getCPDefinition();

long commercePriceEntryId = commerceTierPriceEntryDisplayContext.getCommercePriceEntryId();
long commercePriceListId = commerceTierPriceEntryDisplayContext.getCommercePriceListId();

SearchContainer<CommerceTierPriceEntry> commerceTierPriceEntriesSearchContainer = commerceTierPriceEntryDisplayContext.getSearchContainer();

PortletURL portletURL = commerceTierPriceEntryDisplayContext.getPortletURL();

PortletURL priceEntriesURL = renderResponse.createRenderURL();

priceEntriesURL.setParameter("mvcRenderCommandName", "viewCommercePriceEntries");
priceEntriesURL.setParameter("commercePriceListId", String.valueOf(commercePriceListId));

String title = cpDefinition.getTitle(languageId);

Map<String, Object> data = new HashMap<>();

data.put("direction-right", StringPool.TRUE);

PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, "price-lists"), priceListsURL, data);
PortalUtil.addPortletBreadcrumbEntry(request, commercePriceList.getName(), priceEntriesURL.toString(), data);
PortalUtil.addPortletBreadcrumbEntry(request, title, portletURL.toString(), data);
PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, "tier-price-entries"), StringPool.BLANK, data);

String toolbarItem = ParamUtil.getString(request, "toolbarItem", "view-tier-price-entries");

portletURL.setParameter("toolbarItem", toolbarItem);

request.setAttribute("view.jsp-portletURL", portletURL);

renderResponse.setTitle(LanguageUtil.get(request, "price-lists"));
%>

<%@ include file="/price_list_navbar.jspf" %>

<%@ include file="/breadcrumb.jspf" %>
<%@ include file="/price_entry_navbar.jspf" %>

<liferay-frontend:management-bar
	includeCheckBox="<%= true %>"
	searchContainerId="commerceTierPriceEntries"
>
	<liferay-frontend:management-bar-buttons>
		<c:if test="<%= commerceTierPriceEntryDisplayContext.isShowInfoPanel() %>">
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
	</liferay-frontend:management-bar-buttons>

	<liferay-frontend:management-bar-filters>
		<liferay-frontend:management-bar-navigation
			navigationKeys='<%= new String[] {"all"} %>'
			portletURL="<%= portletURL %>"
		/>

		<liferay-frontend:management-bar-sort
			orderByCol="<%= commerceTierPriceEntriesSearchContainer.getOrderByCol() %>"
			orderByType="<%= commerceTierPriceEntriesSearchContainer.getOrderByType() %>"
			orderColumns='<%= new String[] {"create-date"} %>'
			portletURL="<%= portletURL %>"
		/>
	</liferay-frontend:management-bar-filters>

	<liferay-frontend:management-bar-action-buttons>
		<c:if test="<%= commerceTierPriceEntryDisplayContext.isShowInfoPanel() %>">
			<liferay-frontend:management-bar-sidenav-toggler-button
				icon="info-circle"
				label="info"
			/>
		</c:if>

		<liferay-frontend:management-bar-button href='<%= "javascript:" + renderResponse.getNamespace() + "deleteCommerceTierPriceEntries();" %>' icon="times" label="delete" />
	</liferay-frontend:management-bar-action-buttons>
</liferay-frontend:management-bar>

<div id="<portlet:namespace />tierPriceEntriesContainer">
	<div class="closed container-fluid-1280 sidenav-container sidenav-right" id="<portlet:namespace />infoPanelId">
		<c:if test="<%= commerceTierPriceEntryDisplayContext.isShowInfoPanel() %>">
			<liferay-portlet:resourceURL
				copyCurrentRenderParameters="<%= false %>"
				id="commerceTierPriceEntryInfoPanel"
				var="sidebarPanelURL"
			/>

			<liferay-frontend:sidebar-panel
				resourceURL="<%= sidebarPanelURL %>"
				searchContainerId="commerceTierPriceEntries"
			>
				<liferay-util:include page="/tier_price_entry_info_panel.jsp" servletContext="<%= application %>" />
			</liferay-frontend:sidebar-panel>
		</c:if>

		<div class="sidenav-content">
			<aui:form action="<%= portletURL.toString() %>" method="post" name="fm">
				<aui:input name="<%= Constants.CMD %>" type="hidden" />
				<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
				<aui:input name="deleteCommerceTierPriceEntryIds" type="hidden" />

				<div class="tier-price-entries-container" id="<portlet:namespace />entriesContainer">
					<liferay-ui:search-container
						id="commerceTierPriceEntries"
						iteratorURL="<%= portletURL %>"
						searchContainer="<%= commerceTierPriceEntriesSearchContainer %>"
					>
						<liferay-ui:search-container-row
							className="com.liferay.commerce.model.CommerceTierPriceEntry"
							cssClass="entry-display-style"
							keyProperty="commerceTierPriceEntryId"
							modelVar="commerceTierPriceEntry"
						>
							<liferay-ui:search-container-column-text
								cssClass="table-cell-content"
								name="id"
								value="<%= String.valueOf(commerceTierPriceEntry.getCommerceTierPriceEntryId()) %>"
							/>

							<%
							PortletURL rowURL = renderResponse.createRenderURL();

							rowURL.setParameter("mvcRenderCommandName", "editCommerceTierPriceEntry");
							rowURL.setParameter("commercePriceEntryId", String.valueOf(commercePriceEntryId));
							rowURL.setParameter("commercePriceListId", String.valueOf(commercePriceListId));
							rowURL.setParameter("commerceTierPriceEntryId", String.valueOf(commerceTierPriceEntry.getCommerceTierPriceEntryId()));
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
								value="<%= HtmlUtil.escape(commerceTierPriceEntryDisplayContext.getCommerceTierPriceEntryPrice(commerceTierPriceEntry)) %>"
							/>

							<liferay-ui:search-container-column-text
								cssClass="table-cell-content"
								name="min-quantity"
								property="minQuantity"
							/>

							<liferay-ui:search-container-column-date
								cssClass="table-cell-content"
								name="create-date"
								property="createDate"
							/>

							<liferay-ui:search-container-column-jsp
								cssClass="entry-action-column"
								path="/tier_price_entry_action.jsp"
							/>
						</liferay-ui:search-container-row>

						<liferay-ui:search-iterator markupView="lexicon" searchContainer="<%= commerceTierPriceEntriesSearchContainer %>" />
					</liferay-ui:search-container>
				</div>
			</aui:form>
		</div>
	</div>
</div>

<liferay-portlet:renderURL var="addCommerceTierPriceEntryURL">
	<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.ADD %>" />
	<portlet:param name="mvcRenderCommandName" value="editCommerceTierPriceEntry" />
	<portlet:param name="redirect" value="<%= currentURL %>" />
	<portlet:param name="commercePriceEntryId" value="<%= String.valueOf(commercePriceEntryId) %>" />
	<portlet:param name="commercePriceListId" value="<%= String.valueOf(commercePriceListId) %>" />
</liferay-portlet:renderURL>

<liferay-frontend:add-menu>
	<liferay-frontend:add-menu-item title='<%= LanguageUtil.get(request, "add-tier-price-entry") %>' url="<%= addCommerceTierPriceEntryURL.toString() %>" />
</liferay-frontend:add-menu>

<aui:script>
	function <portlet:namespace />deleteCommerceTierPriceEntries() {
		if (confirm('<liferay-ui:message key="are-you-sure-you-want-to-delete-the-selected-tier-price-entries" />')) {
			var form = AUI.$(document.<portlet:namespace />fm);

			form.attr('method', 'post');
			form.fm('<%= Constants.CMD %>').val('<%= Constants.DELETE %>');
			form.fm('deleteCommerceTierPriceEntryIds').val(Liferay.Util.listCheckedExcept(form, '<portlet:namespace />allRowIds'));

			submitForm(form, '<portlet:actionURL name="editCommerceTierPriceEntry" />');
		}
	}
</aui:script>