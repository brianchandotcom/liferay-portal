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
ChangeListsHistoryDetailsDisplayContext changeListsHistoryDetailsDisplayContext = new ChangeListsHistoryDetailsDisplayContext(request, renderRequest, renderResponse);

CTCollection ctCollection = (CTCollection)request.getAttribute(CTWebKeys.CT_COLLECTION);

SearchContainer<CTEntry> ctEntrySearchContainer = changeListsHistoryDetailsDisplayContext.getCTCollectionSearchContainer(ctCollection);

long ctCollectionId = 0;

String title = StringPool.BLANK;

if (ctCollection != null) {
	ctCollectionId = ctCollection.getCtCollectionId();

	title = HtmlUtil.escape(ctCollection.getName());

	portletDisplay.setTitle(title);

	renderResponse.setTitle(title);
}

String backURL = ParamUtil.getString(request, "backURL");

portletDisplay.setURLBack(backURL);
portletDisplay.setShowBackIcon(true);
%>

<clay:management-toolbar
	clearResultsURL="<%= changeListsHistoryDetailsDisplayContext.getSearchActionURL(ctCollectionId) %>"
	filterDropdownItems="<%= changeListsHistoryDetailsDisplayContext.getFilterDropdownItems() %>"
	itemsTotal="<%= ctEntrySearchContainer.getTotal() %>"
	searchActionURL="<%= changeListsHistoryDetailsDisplayContext.getSearchActionURL(ctCollectionId) %>"
	searchContainerId="changeListsHistory"
	selectable="<%= false %>"
	showSearch="<%= true %>"
	sortingOrder="<%= changeListsHistoryDetailsDisplayContext.getOrderByType() %>"
	sortingURL="<%= changeListsHistoryDetailsDisplayContext.getSortingURL() %>"
/>

<div class="closed container-fluid-1280">
	<liferay-site-navigation:breadcrumb
		breadcrumbEntries="<%= changeListsHistoryDetailsDisplayContext.getBreadcrumbEntries(title) %>"
	/>

	<liferay-ui:search-container
		id="changeListsHistory"
		searchContainer="<%= ctEntrySearchContainer %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.change.tracking.model.CTEntry"
			keyProperty="ctEntryId"
			modelVar="curCTEntry"
		>
			<liferay-ui:search-container-column-text
				name="name"
				orderable="<%= true %>"
				orderableProperty="title"
			>
				<%= HtmlUtil.escape(CTDefinitionRegistryUtil.getVersionEntityTitle(curCTEntry.getModelClassNameId(), curCTEntry.getModelClassPK())) %>
			</liferay-ui:search-container-column-text>

			<liferay-ui:search-container-column-text
				name="user"
			>
				<%= HtmlUtil.escape(curCTEntry.getUserName()) %>
			</liferay-ui:search-container-column-text>

			<liferay-ui:search-container-column-text
				name="site"
			>
				<%= HtmlUtil.escape(CTDefinitionRegistryUtil.getVersionEntitySiteName(curCTEntry.getModelClassNameId(), curCTEntry.getModelClassPK())) %>
			</liferay-ui:search-container-column-text>

			<liferay-ui:search-container-column-text
				name="content-type"
			>
				<liferay-ui:message key="<%= HtmlUtil.escape(CTDefinitionRegistryUtil.getVersionEntityContentTypeLanguageKey(curCTEntry.getModelClassNameId())) %>" />
			</liferay-ui:search-container-column-text>

			<liferay-ui:search-container-column-text
				name="change-type"
			>
				<liferay-ui:message key="<%= changeListsHistoryDetailsDisplayContext.getChangeType(curCTEntry.getChangeType()) %>" />
			</liferay-ui:search-container-column-text>

			<liferay-ui:search-container-column-text
				name="affects"
			>

				<%
				int affectsCount = changeListsHistoryDetailsDisplayContext.getAffectsCount(curCTEntry);

				if (affectsCount > 0) {
				%>

					<span class="blue strong"><%= affectsCount %></span>

				<%
				}
				%>

			</liferay-ui:search-container-column-text>

			<liferay-ui:search-container-column-text
				name="version"
			>
				<%= HtmlUtil.escape(String.valueOf(CTDefinitionRegistryUtil.getVersionEntityVersion(curCTEntry.getModelClassNameId(), curCTEntry.getModelClassPK()))) %>
			</liferay-ui:search-container-column-text>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			markupView="lexicon"
		/>
	</liferay-ui:search-container>
</div>