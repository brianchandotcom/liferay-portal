<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
CPPublisherConfigurationDisplayContext cpPublisherConfigurationDisplayContext = (CPPublisherConfigurationDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);
PortletURL configurationRenderURL = (PortletURL)request.getAttribute("configuration.jsp-configurationRenderURL");

List<CPCatalogEntry> catalogEntries = cpPublisherConfigurationDisplayContext.getCPCatalogEntries();
%>

<liferay-ui:search-container
	compactEmptyResultsMessage="<%= true %>"
	emptyResultsMessage="none"
	iteratorURL="<%= configurationRenderURL %>"
	total="<%= catalogEntries.size() %>"
>
	<liferay-ui:search-container-results
		calculateStartAndEnd="<%= true %>"
		results="<%= catalogEntries %>"
	/>

	<liferay-ui:search-container-row
		className="com.liferay.commerce.product.catalog.CPCatalogEntry"
		escapedModel="<%= true %>"
		keyProperty="cpDefinitionId"
		modelVar="cpCatalogEntry"
	>

		<%
		String defaultImageFileURL = cpPublisherConfigurationDisplayContext.getDefaultImageFileURL(cpCatalogEntry);
		%>

		<c:choose>
			<c:when test="<%= Validator.isNotNull(defaultImageFileURL) %>">
				<liferay-ui:search-container-column-image
					name="image"
					src="<%= defaultImageFileURL %>"
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
			cssClass="font-weight-bold important table-cell-expand"
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

<liferay-ui:icon
	id="addCommerceProductDefinition"
	label="<%= true %>"
	linkCssClass="btn btn-secondary"
	message="select"
	url="javascript:void(0);"
/>

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