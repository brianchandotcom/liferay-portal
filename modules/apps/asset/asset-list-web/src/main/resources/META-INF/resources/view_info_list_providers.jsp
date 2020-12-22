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
InfoListProviderDisplayContext infoListProviderDisplayContext = (InfoListProviderDisplayContext)request.getAttribute(AssetListWebKeys.INFO_LIST_PROVIDER_DISPLAY_CONTEXT);
%>

<clay:navigation-bar
	inverted="<%= true %>"
	navigationItems='<%= assetListDisplayContext.getNavigationItems("collection-providers") %>'
/>

<div class="container-fluid container-fluid-max-xl lfr-search-container-wrapper" id="<portlet:namespace />collectionProviders">
	<liferay-ui:breadcrumb
		showLayout="<%= false %>"
	/>

	<liferay-ui:search-container
		id="entries"
		searchContainer="<%= infoListProviderDisplayContext.getSearchContainer() %>"
		var="collectionsSearch"
	>
		<liferay-ui:search-container-row
			className="com.liferay.info.list.provider.InfoListProvider"
			cssClass="entry"
			modelVar="infoListProvider"
		>
			<liferay-ui:search-container-column-icon
				icon="list"
			/>

			<liferay-ui:search-container-column-text
				colspan="<%= 2 %>"
			>
				<div class="list-group-title">
					<%= HtmlUtil.escape(infoListProviderDisplayContext.getTitle(infoListProvider)) %>
				</div>

				<div class="list-group-subtext">
					<liferay-ui:message key="<%= HtmlUtil.escape(infoListProviderDisplayContext.getSubtitle(infoListProvider)) %>" />
				</div>
			</liferay-ui:search-container-column-text>

			<%
			InfoListProviderActionDropdownItems infoListProviderActionDropdownItems = new InfoListProviderActionDropdownItems(infoListProvider, liferayPortletRequest, liferayPortletResponse);
			%>

			<liferay-ui:search-container-column-text>
				<clay:dropdown-actions
					defaultEventHandler="infoListProviderDropdownDefaultEventHandler"
					dropdownItems="<%= infoListProviderActionDropdownItems.getActionDropdownItems() %>"
				/>
			</liferay-ui:search-container-column-text>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			displayStyle="descriptive"
			markupView="lexicon"
		/>
	</liferay-ui:search-container>
</div>

<liferay-frontend:component
	componentId="infoListProviderDropdownDefaultEventHandler"
	module="js/InfoListProviderDropdownDefaultEventHandler.es"
/>