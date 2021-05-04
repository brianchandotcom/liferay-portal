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
CPPublisherConfigurationDisplayContext cpPublisherConfigurationDisplayContext = (CPPublisherConfigurationDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);
PortletURL configurationRenderURL = (PortletURL)request.getAttribute("configuration.jsp-configurationRenderURL");

List<CPCatalogEntry> catalogEntries = cpPublisherConfigurationDisplayContext.getCPCatalogEntries();
%>

<liferay-frontend:management-bar
	includeCheckBox="<%= false %>"
	searchContainerId="catalogEntries"
>
	<liferay-frontend:management-bar-buttons>
		<liferay-frontend:add-menu
			inline="<%= true %>"
		>
			<liferay-frontend:add-menu-item
				id="addCommerceProductDefinition"
				title="add"
				url="javascript:;"
			/>
		</liferay-frontend:add-menu>
	</liferay-frontend:management-bar-buttons>
</liferay-frontend:management-bar>

<liferay-ui:search-container
	compactEmptyResultsMessage="<%= true %>"
	emptyResultsMessage="none"
	iteratorURL="<%= configurationRenderURL %>"
	total="<%= catalogEntries.size() %>"
>
	<liferay-ui:search-container-results
		results="<%= catalogEntries.subList(searchContainer.getStart(), searchContainer.getResultEnd()) %>"
	/>

	<liferay-ui:search-container-row
		className="com.liferay.commerce.product.catalog.CPCatalogEntry"
		escapedModel="<%= true %>"
		keyProperty="cpDefinitionId"
		modelVar="cpCatalogEntry"
	>

		<%
		String thumbnailSrc = cpCatalogEntry.getDefaultImageFileUrl();
		%>

		<c:choose>
			<c:when test="<%= Validator.isNotNull(thumbnailSrc) %>">
				<liferay-ui:search-container-column-image
					name="image"
					src="<%= thumbnailSrc %>"
				/>
			</c:when>
			<c:otherwise>
				<liferay-ui:search-container-column-icon
					icon="documents-and-media"
					name="image"
				/>
			</c:otherwise>
		</c:choose>

		<liferay-ui:search-container-column-text
			cssClass="important table-cell-expand"
			name="name"
			value="<%= HtmlUtil.escape(cpCatalogEntry.getName()) %>"
		/>

		<%
		CPSku cpSku = cpPublisherConfigurationDisplayContext.getDefaultCPSku(cpCatalogEntry);
		%>

		<liferay-ui:search-container-column-text
			cssClass="table-cell-expand"
			name="sku"
			value="<%= (cpSku == null) ? StringPool.BLANK : HtmlUtil.escape(cpSku.getSku()) %>"
		/>

		<liferay-ui:search-container-column-jsp
			path="/product_publisher/configuration/product_selection_action.jsp"
		/>

		<liferay-ui:search-container-column-jsp
			cssClass="entry-action-column"
			path="/product_publisher/configuration/product_selection_order_action.jsp"
		/>
	</liferay-ui:search-container-row>

	<liferay-ui:search-iterator
		markupView="lexicon"
		paginate="<%= total > SearchContainer.DEFAULT_DELTA %>"
	/>
</liferay-ui:search-container>

<div class="select-asset-selector">
	<div class="edit-controls lfr-meta-actions">

	</div>
</div>

<aui:script>
	function <portlet:namespace />moveSelectionDown(productEntryOrder) {
		var form = window.document['<portlet:namespace />fm'];

		form['<portlet:namespace /><%= Constants.CMD %>'].value =
			'move-selection-down';
		form['<portlet:namespace />redirect'].value =
			'<%= HtmlUtil.escapeJS(currentURL) %>';
		form['<portlet:namespace />productEntryOrder'].value = productEntryOrder;

		submitForm(form);
	}

	function <portlet:namespace />moveSelectionUp(productEntryOrder) {
		var form = window.document['<portlet:namespace />fm'];

		form['<portlet:namespace /><%= Constants.CMD %>'].value =
			'move-selection-up';
		form['<portlet:namespace />redirect'].value =
			'<%= HtmlUtil.escapeJS(currentURL) %>';
		form['<portlet:namespace />productEntryOrder'].value = productEntryOrder;

		submitForm(form);
	}
</aui:script>

<aui:script sandbox="<%= true %>">
	const addCommerceProductDefinitionButton = document.getElementById(
		'<portlet:namespace />addCommerceProductDefinition'
	);

	if (addCommerceProductDefinitionButton) {
		addCommerceProductDefinitionButton.addEventListener('click', (event) => {
			event.preventDefault();

			const openerWindow = Liferay.Util.getOpener();

			openerWindow.Liferay.Util.openSelectionModal({
				multiple: true,
				onSelect: (selectedItems) => {
					if (!selectedItems || !selectedItems.length) {
						return;
					}

					const form = document.getElementById('<portlet:namespace />fm');

					if (!form) {
						return;
					}

					const values = selectedItems.map((item) => item.value);

					form.<portlet:namespace />cpDefinitionIds.value = values;

					form.<portlet:namespace /><%= Constants.CMD %>.value =
						'add-selection';
					form.<portlet:namespace />redirect.value = '<%= currentURL %>';

					submitForm(form);
				},
				title: 'add-new-product-to-x',
				url:
					'<%= cpPublisherConfigurationDisplayContext.getItemSelectorUrl() %>',
			});
		});
	}
</aui:script>