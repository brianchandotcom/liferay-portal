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
SearchContainer accountEntryDisplaySearchContainer = AccountEntryDisplaySearchContainerFactory.create(liferayPortletRequest, liferayPortletResponse);

SelectAccountEntriesManagementToolbarDisplayContext selectAccountEntriesManagementToolbarDisplayContext = new SelectAccountEntriesManagementToolbarDisplayContext(request, liferayPortletRequest, liferayPortletResponse, accountEntryDisplaySearchContainer);
%>

<clay:management-toolbar
	displayContext="<%= selectAccountEntriesManagementToolbarDisplayContext %>"
/>

<aui:container cssClass="container-fluid container-fluid-max-xl">
	<liferay-ui:search-container
		searchContainer="<%= accountEntryDisplaySearchContainer %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.account.admin.web.internal.display.AccountEntryDisplay"
			keyProperty="accountEntryId"
			modelVar="accountEntryDisplay"
		>
			<liferay-ui:search-container-column-text
				cssClass="table-cell-expand table-title"
				name="name"
				property="name"
			/>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-expand"
				name="parent-account"
				property="parentAccountEntryName"
			/>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-expand"
				name="status"
			>
				<clay:label
					label="<%= StringUtil.toUpperCase(LanguageUtil.get(request, accountEntryDisplay.getStatusLabel()), locale) %>"
					style="<%= accountEntryDisplay.getStatusLabelStyle() %>"
				/>
			</liferay-ui:search-container-column-text>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			markupView="lexicon"
		/>
	</liferay-ui:search-container>
</aui:container>

<aui:script use="liferay-search-container">
	var searchContainer = Liferay.SearchContainer.get(
		'<portlet:namespace />accountEntries'
	);

	searchContainer.on('rowToggled', function(event) {
		var selectedItems = event.elements.allSelectedElements;

		var result = {};

		if (!selectedItems.isEmpty()) {
			result = {
				data: {
					value: selectedItems.get('value').join(',')
				}
			};
		}

		Liferay.Util.getOpener().Liferay.fire(
			'<%= HtmlUtil.escapeJS(renderResponse.getNamespace() + "selectAccountEntries") %>',
			result
		);
	});
</aui:script>