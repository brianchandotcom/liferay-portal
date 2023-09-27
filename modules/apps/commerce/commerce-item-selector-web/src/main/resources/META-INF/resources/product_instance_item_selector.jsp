<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
CommerceProductInstanceItemSelectorViewDisplayContext commerceProductInstanceItemSelectorViewDisplayContext = (CommerceProductInstanceItemSelectorViewDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);
%>

<clay:management-toolbar
	managementToolbarDisplayContext="<%= new CommerceProductInstanceItemSelectorViewManagementToolbarDisplayContext(request, liferayPortletRequest, liferayPortletResponse, commerceProductInstanceItemSelectorViewDisplayContext.getSearchContainer()) %>"
/>

<div class="container-fluid container-fluid-max-xl" id="<portlet:namespace />cpInstanceSelectorWrapper">
	<liferay-ui:search-container
		id="cpInstances"
		searchContainer="<%= commerceProductInstanceItemSelectorViewDisplayContext.getSearchContainer() %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.commerce.product.model.CPInstance"
			cssClass="commerce-product-instance-row"
			keyProperty="CPInstanceId"
			modelVar="cpInstance"
		>

			<%
			CPDefinition cpDefinition = cpInstance.getCPDefinition();
			%>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-expand"
				name="title"
			>
				<div class="commerce-product-definition-title" data-id="<%= cpDefinition.getCPDefinitionId() %>">
					<%= HtmlUtil.escape(cpDefinition.getName(themeDisplay.getLanguageId())) %>
				</div>
			</liferay-ui:search-container-column-text>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-expand"
				property="sku"
			/>

			<liferay-ui:search-container-column-status
				cssClass="table-cell-expand"
				name="status"
				status="<%= cpInstance.getStatus() %>"
			/>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			markupView="lexicon"
		/>
	</liferay-ui:search-container>
</div>

<aui:script use="liferay-search-container">
	var cpInstanceSelectorWrapper = A.one(
		'#<portlet:namespace />cpInstanceSelectorWrapper'
	);

	var searchContainer = Liferay.SearchContainer.get(
		'<portlet:namespace />cpInstances'
	);

	searchContainer.on('rowToggled', (event) => {
		Liferay.Util.getOpener().Liferay.fire(
			'<%= HtmlUtil.escapeJS(commerceProductInstanceItemSelectorViewDisplayContext.getItemSelectedEventName()) %>',
			{
				data: Liferay.Util.getCheckedCheckboxes(
					cpInstanceSelectorWrapper,
					'<portlet:namespace />allRowIds'
				),
			}
		);
	});
</aui:script>