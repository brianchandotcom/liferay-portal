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

CPInstanceCommerceTierPriceEntryDisplayContext cpInstanceCommerceTierPriceEntryDisplayContext = (CPInstanceCommerceTierPriceEntryDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CPDefinition cpDefinition = cpInstanceCommerceTierPriceEntryDisplayContext.getCPDefinition();

CPInstance cpInstance = cpInstanceCommerceTierPriceEntryDisplayContext.getCPInstance();

CommercePriceEntry commercePriceEntry = cpInstanceCommerceTierPriceEntryDisplayContext.getCommercePriceEntry();

CommercePriceList commercePriceList = commercePriceEntry.getCommercePriceList();

long commercePriceEntryId = cpInstanceCommerceTierPriceEntryDisplayContext.getCommercePriceEntryId();
long cpDefinitionId = cpInstanceCommerceTierPriceEntryDisplayContext.getCPDefinitionId();
long cpInstanceId = cpInstanceCommerceTierPriceEntryDisplayContext.getCPInstanceId();

SearchContainer<CommerceTierPriceEntry> commerceTierPriceEntriesSearchContainer = cpInstanceCommerceTierPriceEntryDisplayContext.getSearchContainer();

PortletURL portletURL = cpInstanceCommerceTierPriceEntryDisplayContext.getPortletURL();

String instancePriceEntryToolbarItem = ParamUtil.getString(request, "instancePriceEntryToolbarItem", "view-tier-price-entries");

portletURL.setParameter("instancePriceEntryToolbarItem", instancePriceEntryToolbarItem);

request.setAttribute("view.jsp-portletURL", portletURL);

PortletURL productSkusURL = cpInstanceCommerceTierPriceEntryDisplayContext.getProductSkusURL();

PortletURL instancePriceListsURL = cpInstanceCommerceTierPriceEntryDisplayContext.getInstancePriceListURL();

String title = commercePriceList.getName();

Map<String, Object> data = new HashMap<>();

data.put("direction-right", StringPool.TRUE);

PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, "products"), catalogURL, data);
PortalUtil.addPortletBreadcrumbEntry(request, cpDefinition.getTitle(languageId), String.valueOf(cpInstanceCommerceTierPriceEntryDisplayContext.getEditProductDefinitionURL()), data);
PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, CPDefinitionScreenNavigationConstants.CATEGORY_KEY_SKUS), productSkusURL.toString(), data);
PortalUtil.addPortletBreadcrumbEntry(request, cpInstance.getSku(), instancePriceListsURL.toString(), data);
PortalUtil.addPortletBreadcrumbEntry(request, title, StringPool.BLANK, data);
%>

<%@ include file="/navbar.jspf" %>

<%@ include file="/breadcrumb.jspf" %>

<%@ include file="/instance_price_entry_navbar.jspf" %>

<liferay-frontend:management-bar
	includeCheckBox="<%= true %>"
	searchContainerId="commerceTierPriceEntries"
>
	<liferay-frontend:management-bar-buttons>
		<liferay-frontend:management-bar-display-buttons
			displayViews='<%= new String[] {"list"} %>'
			portletURL="<%= portletURL %>"
			selectedDisplayStyle="list"
		/>

		<liferay-portlet:renderURL var="addCommerceTierPriceEntryURL">
			<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.ADD %>" />
			<portlet:param name="mvcRenderCommandName" value="editCPInstanceCommerceTierPriceEntry" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="commercePriceEntryId" value="<%= String.valueOf(commercePriceEntryId) %>" />
			<portlet:param name="cpDefinitionId" value="<%= String.valueOf(cpDefinitionId) %>" />
			<portlet:param name="cpInstanceId" value="<%= String.valueOf(cpInstanceId) %>" />
		</liferay-portlet:renderURL>

		<liferay-frontend:add-menu
			inline="<%= true %>"
		>
			<liferay-frontend:add-menu-item
				title='<%= LanguageUtil.get(request, "add-tier-price-entry") %>'
				url="<%= addCommerceTierPriceEntryURL.toString() %>"
			/>
		</liferay-frontend:add-menu>
	</liferay-frontend:management-bar-buttons>

	<liferay-frontend:management-bar-filters>
		<liferay-frontend:management-bar-navigation
			navigationKeys='<%= new String[] {"all"} %>'
			portletURL="<%= portletURL %>"
		/>

		<liferay-frontend:management-bar-sort
			orderByCol="<%= commerceTierPriceEntriesSearchContainer.getOrderByCol() %>"
			orderByType="<%= commerceTierPriceEntriesSearchContainer.getOrderByType() %>"
			orderColumns='<%= new String[] {"minQuantity"} %>'
			portletURL="<%= portletURL %>"
		/>
	</liferay-frontend:management-bar-filters>

	<liferay-frontend:management-bar-action-buttons>
		<liferay-frontend:management-bar-button
			href='<%= "javascript:" + renderResponse.getNamespace() + "deleteCommerceTierPriceEntries();" %>'
			icon="times"
			label="delete"
		/>
	</liferay-frontend:management-bar-action-buttons>
</liferay-frontend:management-bar>

<div class="container-fluid-1280">
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

					rowURL.setParameter("mvcRenderCommandName", "editCPInstanceCommerceTierPriceEntry");
					rowURL.setParameter("commercePriceEntryId", String.valueOf(commercePriceEntryId));
					rowURL.setParameter("commerceTierPriceEntryId", String.valueOf(commerceTierPriceEntry.getCommerceTierPriceEntryId()));
					rowURL.setParameter("cpDefinitionId", String.valueOf(cpDefinitionId));
					rowURL.setParameter("cpInstanceId", String.valueOf(cpInstanceId));
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
						value="<%= HtmlUtil.escape(cpInstanceCommerceTierPriceEntryDisplayContext.getCommerceTierPriceEntryPrice(commerceTierPriceEntry)) %>"
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
						path="/instance_tier_price_entry_action.jsp"
					/>
				</liferay-ui:search-container-row>

				<liferay-ui:search-iterator
					markupView="lexicon"
					searchContainer="<%= commerceTierPriceEntriesSearchContainer %>"
				/>
			</liferay-ui:search-container>
		</div>
	</aui:form>
</div>

<aui:script>
	function <portlet:namespace />deleteCommerceTierPriceEntries() {
		if (confirm('<liferay-ui:message key="are-you-sure-you-want-to-delete-the-selected-tier-price-entries" />')) {
			var form = AUI.$(document.<portlet:namespace />fm);

			form.attr('method', 'post');
			form.fm('<%= Constants.CMD %>').val('<%= Constants.DELETE %>');
			form.fm('deleteCommerceTierPriceEntryIds').val(Liferay.Util.listCheckedExcept(form, '<portlet:namespace />allRowIds'));

			submitForm(form, '<portlet:actionURL name="editCPInstanceCommerceTierPriceEntry" />');
		}
	}
</aui:script>