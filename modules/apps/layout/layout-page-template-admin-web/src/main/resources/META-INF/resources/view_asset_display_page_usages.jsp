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
AssetDisplayPageUsagesDisplayContext assetDisplayPageUsagesDisplayContext = new AssetDisplayPageUsagesDisplayContext(request, renderRequest, renderResponse);

AssetDisplayPageUsagesManagementToolbarDisplayContext assetDisplayPageUsagesManagementToolbarDisplayContext = new AssetDisplayPageUsagesManagementToolbarDisplayContext(request, liferayPortletRequest, liferayPortletResponse, assetDisplayPageUsagesDisplayContext.getSearchContainer());

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(assetDisplayPageUsagesDisplayContext.getRedirect());

LayoutPageTemplateEntry layoutPageTemplateEntry = LayoutPageTemplateEntryServiceUtil.fetchLayoutPageTemplateEntry(assetDisplayPageUsagesDisplayContext.getLayoutPageTemplateEntryId());

renderResponse.setTitle(LanguageUtil.format(request, "usages-x", layoutPageTemplateEntry.getName()));
%>

<clay:management-toolbar-v2
	displayContext="<%= assetDisplayPageUsagesManagementToolbarDisplayContext %>"
/>

<aui:form cssClass="container-fluid container-fluid-max-xl" name="fm">

	<%
	LayoutPageTemplateEntry defaultLayoutPageTemplateEntry = assetDisplayPageUsagesManagementToolbarDisplayContext.getDefaultLayoutPageTemplateEntry();

	String displayPageAssignedMessage = LanguageUtil.get(resourceBundle, "successfully-assigned-to-default-display-page-template");

	if (defaultLayoutPageTemplateEntry != null) {
		displayPageAssignedMessage = LanguageUtil.format(resourceBundle, "successfully-assigned-to-default-display-page-template-x", "<strong>" + defaultLayoutPageTemplateEntry.getName() + "</strong>");
	}
	%>

	<liferay-ui:success key="displayPageAssigned" message="<%= displayPageAssignedMessage %>" />

	<liferay-ui:success key="displayPageUnassigned" message="successfully-unassigned-display-page-template" />

	<liferay-ui:search-container
		id="assetDisplayPageEntries"
		searchContainer="<%= assetDisplayPageUsagesDisplayContext.getSearchContainer() %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.asset.display.page.model.AssetDisplayPageEntry"
			keyProperty="assetDisplayPageEntryId"
			modelVar="assetDisplayPageEntry"
		>
			<liferay-ui:search-container-column-text
				cssClass="table-cell-expand table-cell-minw-200 table-title"
				name="title"
				value="<%= HtmlUtil.escape(assetDisplayPageUsagesDisplayContext.getTitle(assetDisplayPageEntry, themeDisplay.getLocale())) %>"
			/>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-expand table-cell-minw-200 table-title"
				name="author"
				value="<%= HtmlUtil.escape(assetDisplayPageEntry.getUserName()) %>"
			/>

			<liferay-ui:search-container-column-date
				cssClass="table-cell-expand-smallest table-cell-ws-nowrap"
				name="modified-date"
				value="<%= assetDisplayPageEntry.getModifiedDate() %>"
			/>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			displayStyle="list"
			markupView="lexicon"
		/>
	</liferay-ui:search-container>
</aui:form>

<liferay-frontend:component
	componentId="<%= assetDisplayPageUsagesManagementToolbarDisplayContext.getDefaultEventHandler() %>"
	module="js/AssetDisplayPageUsagesManagementToolbarDefaultEventHandler.es"
/>