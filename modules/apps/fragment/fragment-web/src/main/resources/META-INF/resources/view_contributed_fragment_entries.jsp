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
ContributedFragmentManagementToolbarDisplayContext contributedFragmentManagementToolbarDisplayContext = new ContributedFragmentManagementToolbarDisplayContext(liferayPortletRequest, liferayPortletResponse, request, fragmentDisplayContext);
%>

<clay:management-toolbar
	displayContext="<%= contributedFragmentManagementToolbarDisplayContext %>"
/>

<aui:form name="fm">
	<liferay-ui:search-container
		searchContainer="<%= fragmentDisplayContext.getContributedFragmentEntriesSearchContainer() %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.fragment.model.FragmentEntry"
			keyProperty="fragmentEntryKey"
			modelVar="fragmentEntry"
		>

			<%
			row.setCssClass("card-page-item-asset " + row.getCssClass());
			%>

			<liferay-ui:search-container-column-text>
				<clay:vertical-card
					verticalCard="<%= new ContributedFragmentEntryVerticalCard(fragmentEntry, renderRequest, renderResponse, searchContainer.getRowChecker()) %>"
				/>
			</liferay-ui:search-container-column-text>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			displayStyle="icon"
			markupView="lexicon"
			resultRowSplitter="<%= fragmentDisplayContext.isSearch() ? null : new FragmentEntryResultRowSplitter() %>"
			searchResultCssClass="card-page"
		/>
	</liferay-ui:search-container>
</aui:form>

<aui:form name="fragmentEntryFm">
	<aui:input name="fragmentEntryKeys" type="hidden" />
	<aui:input name="fragmentCollectionId" type="hidden" />
</aui:form>

<liferay-frontend:component
	componentId="<%= FragmentWebKeys.FRAGMENT_ENTRY_DROPDOWN_DEFAULT_EVENT_HANDLER %>"
	module="js/FragmentEntryDropdownDefaultEventHandler.es"
/>

<liferay-frontend:component
	componentId="<%= contributedFragmentManagementToolbarDisplayContext.getDefaultEventHandler() %>"
	context="<%= contributedFragmentManagementToolbarDisplayContext.getComponentContext() %>"
	module="js/ManagementToolbarDefaultEventHandler.es"
/>