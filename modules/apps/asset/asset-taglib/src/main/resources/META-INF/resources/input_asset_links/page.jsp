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

<%@ include file="/input_asset_links/init.jsp" %>

<clay:dropdown-menu
	aria-label='<%= LanguageUtil.get(request, "select-items") %>'
	cssClass="btn btn-secondary"
	dropdownItems="<%= inputAssetLinksDisplayContext.getActionDropdownItems() %>"
	label='<%= LanguageUtil.get(request, "select") %>'
	propsTransformer="js/InputAssetLinkDropdownDefaultPropsTransformer"
/>

<liferay-util:buffer
	var="removeLinkIcon"
>
	<liferay-ui:icon
		icon="times-circle"
		markupView="lexicon"
		message="remove"
	/>
</liferay-util:buffer>

<liferay-ui:search-container
	compactEmptyResultsMessage="<%= true %>"
	emptyResultsMessage="none"
	headerNames="title,null"
	total="<%= inputAssetLinksDisplayContext.getAssetLinksCount() %>"
>
	<liferay-ui:search-container-results
		results="<%= inputAssetLinksDisplayContext.getAssetLinks() %>"
	/>

	<liferay-ui:search-container-row
		className="com.liferay.asset.kernel.model.AssetLink"
		keyProperty="entryId2"
		modelVar="assetLink"
	>

		<%
		AssetEntry assetLinkEntry = inputAssetLinksDisplayContext.getAssetLinkEntry(assetLink);
		%>

		<liferay-ui:search-container-column-text
			name="title"
		>
			<h4 class="list-group-title">
				<%= HtmlUtil.escape(assetLinkEntry.getTitle(locale)) %>
			</h4>

			<p class="list-group-subtitle">
				<%= inputAssetLinksDisplayContext.getAssetType(assetLinkEntry) %>
			</p>

			<p class="list-group-subtitle">
				<liferay-ui:message key="scope" />: <%= HtmlUtil.escape(inputAssetLinksDisplayContext.getGroupDescriptiveName(assetLinkEntry)) %>
			</p>
		</liferay-ui:search-container-column-text>

		<liferay-ui:search-container-column-text
			cssClass="text-right"
		>
			<a class="modify-link" data-rowId="<%= assetLinkEntry.getEntryId() %>" href="javascript:void(0);"><%= removeLinkIcon %></a>
		</liferay-ui:search-container-column-text>
	</liferay-ui:search-container-row>

	<liferay-ui:search-iterator
		markupView="lexicon"
		paginate="<%= false %>"
		searchResultCssClass="table table-autofit table-heading-nowrap"
	/>
</liferay-ui:search-container>

<c:if test="<%= stagingGroupHelper.isLiveGroup(themeDisplay.getScopeGroupId()) %>">
	<span>
		<liferay-ui:message key="related-assets-for-staged-asset-types-can-be-managed-on-the-staging-site" />
	</span>
</c:if>

<aui:input name="assetLinkEntryIds" type="hidden" />

<aui:script use="liferay-search-container">
	var searchContainer = Liferay.SearchContainer.get(
		'<portlet:namespace />assetLinksSearchContainer'
	);

	searchContainer.get('contentBox').delegate(
		'click',
		(event) => {
			var link = event.currentTarget;

			var tr = link.ancestor('tr');

			searchContainer.deleteRow(tr, link.getAttribute('data-rowId'));
		},
		'.modify-link'
	);
</aui:script>