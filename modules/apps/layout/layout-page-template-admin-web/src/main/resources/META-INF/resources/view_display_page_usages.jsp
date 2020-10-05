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
DisplayPageUsageDisplayContext displayPageUsageDisplayContext = new DisplayPageUsageDisplayContext(request, renderRequest, renderResponse);

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(ParamUtil.getString(request, "redirect"));

LayoutPageTemplateEntry layoutPageTemplateEntry = LayoutPageTemplateEntryServiceUtil.fetchLayoutPageTemplateEntry(displayPageUsageDisplayContext.getLayoutPageTemplateEntryId());

renderResponse.setTitle(LanguageUtil.format(request, "usages-x", layoutPageTemplateEntry.getName()));

DisplayPageUsageManagementToolbarDisplayContext displayPageUsageManagementToolbarDisplayContext = new DisplayPageUsageManagementToolbarDisplayContext(request, liferayPortletRequest, liferayPortletResponse, displayPageUsageDisplayContext.getSearchContainer());
%>

<clay:management-toolbar
	displayContext="<%= displayPageUsageManagementToolbarDisplayContext %>"
/>

<clay:container-fluid>
	<liferay-ui:search-container
		searchContainer="<%= displayPageUsageDisplayContext.getSearchContainer() %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.asset.display.page.model.AssetDisplayPageEntry"
			keyProperty="assetDisplayPageEntryId"
			modelVar="assetDisplayPageEntry"
		>
			<liferay-ui:search-container-column-text
				name="title"
				value="<%= HtmlUtil.escape(assetDisplayPageEntry.getTitle(themeDisplay.getLocale())) %>"
			/>

			<liferay-ui:search-container-column-date
				name="modified-date"
				value="<%= assetDisplayPageEntry.getModifiedDate() %>"
			/>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			displayStyle="list"
			markupView="lexicon"
			searchResultCssClass="show-quick-actions-on-hover table table-autofit"
		/>
	</liferay-ui:search-container>
</clay:container-fluid>

<liferay-frontend:component
	componentId="<%= displayPageUsageManagementToolbarDisplayContext.getDefaultEventHandler() %>"
	module="js/DisplayPageUsageManagementToolbarDefaultEventHandler.es"
/>