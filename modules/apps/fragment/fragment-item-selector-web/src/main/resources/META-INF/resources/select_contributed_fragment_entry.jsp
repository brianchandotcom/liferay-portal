<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
DefaultFragmentEntriesDisplayContext defaultFragmentEntriesDisplayContext = new DefaultFragmentEntriesDisplayContext(request, liferayPortletRequest, liferayPortletResponse);
%>

<clay:management-toolbar
	managementToolbarDisplayContext="<%= new ContributedFragmentEntriesItemSelectorViewManagementToolbarDisplayContext(request, liferayPortletRequest, liferayPortletResponse, defaultFragmentEntriesDisplayContext.getFragmentsSearchContainer()) %>"
/>

<clay:container-fluid>
	<liferay-site-navigation:breadcrumb
		breadcrumbEntries="<%= defaultFragmentEntriesDisplayContext.getBreadcrumbEntries() %>"
	/>

	<liferay-ui:search-container
		searchContainer="<%= defaultFragmentEntriesDisplayContext.getFragmentsSearchContainer() %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.fragment.model.FragmentEntry"
			modelVar="fragmentEntry"
		>
			<liferay-ui:search-container-column-text>
				<clay:vertical-card
					data-fragmententrykey="<%= fragmentEntry.getFragmentEntryKey() %>"
					data-fragmententryname="<%= fragmentEntry.getName() %>"
					verticalCard="<%= new FragmentEntryVerticalCard(fragmentEntry) %>"
				/>
			</liferay-ui:search-container-column-text>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			displayStyle="icon"
			markupView="lexicon"
		/>
	</liferay-ui:search-container>
</clay:container-fluid>