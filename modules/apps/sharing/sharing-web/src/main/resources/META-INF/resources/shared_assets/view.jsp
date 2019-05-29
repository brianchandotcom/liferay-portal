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

<%@ include file="/shared_assets/init.jsp" %>

<%
SharedAssetsViewDisplayContext sharedAssetsViewDisplayContext = (SharedAssetsViewDisplayContext)renderRequest.getAttribute(SharedAssetsViewDisplayContext.class.getName());
%>

<clay:navigation-bar
	inverted="<%= layout.isTypeControlPanel() %>"
	navigationItems="<%= sharedAssetsViewDisplayContext.getNavigationItems() %>"
/>

<clay:management-toolbar
	defaultEventHandler='<%= renderResponse.getNamespace() + "SharedAssets" %>'
	filterDropdownItems="<%= sharedAssetsViewDisplayContext.getFilterDropdownItems() %>"
	selectable="<%= false %>"
	showSearch="<%= false %>"
	sortingOrder="<%= sharedAssetsViewDisplayContext.getSortingOrder() %>"
	sortingURL="<%= String.valueOf(sharedAssetsViewDisplayContext.getSortingURL()) %>"
/>

<%
PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("mvcRenderCommandName", "/blogs/view");

SearchContainer sharingEntriesSearchContainer = new SearchContainer(renderRequest, PortletURLUtil.clone(portletURL, liferayPortletResponse), null, "no-entries-were-found");

sharedAssetsViewDisplayContext.populateResults(sharingEntriesSearchContainer);
%>

<div class="container-fluid-1280 main-content-body">
	<liferay-ui:search-container
		id="sharingEntries"
		searchContainer="<%= sharingEntriesSearchContainer %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.sharing.model.SharingEntry"
			escapedModel="<%= true %>"
			keyProperty="sharingEntryId"
			modelVar="sharingEntry"
		>
			<liferay-portlet:renderURL varImpl="rowURL">
				<portlet:param name="mvcRenderCommandName" value="/shared_assets/view_sharing_entry" />
				<portlet:param name="redirect" value="<%= currentURL %>" />
				<portlet:param name="sharingEntryId" value="<%= String.valueOf(sharingEntry.getSharingEntryId()) %>" />
			</liferay-portlet:renderURL>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-content"
				href="<%= sharedAssetsViewDisplayContext.isVisible(sharingEntry) ? rowURL : null %>"
				name="title"
				orderable="<%= false %>"
				value="<%= sharedAssetsViewDisplayContext.getTitle(sharingEntry) %>"
			/>

			<liferay-ui:search-container-column-text
				name="asset-type"
				orderable="<%= false %>"
				value="<%= sharedAssetsViewDisplayContext.getAssetTypeTitle(sharingEntry) %>"
			/>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-expand-smallest"
				name="status"
				orderable="<%= false %>"
			>
				<c:if test="<%= !sharedAssetsViewDisplayContext.isVisible(sharingEntry) %>">
					<span class="label label-info">
						<span class="label-item label-item-expand">
							<liferay-ui:message key="not-visible" />
						</span>
					</span>
				</c:if>
			</liferay-ui:search-container-column-text>

			<liferay-ui:search-container-column-date
				name="shared-date"
				orderable="<%= false %>"
				value="<%= sharingEntry.getModifiedDate() %>"
			/>

			<liferay-ui:search-container-column-jsp
				path="/shared_assets/sharing_entry_action.jsp"
			/>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			displayStyle="list"
			markupView="lexicon"
		/>
	</liferay-ui:search-container>
</div>

<portlet:renderURL var="selectAssetTypeURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
	<portlet:param name="mvcRenderCommandName" value="/shared_assets/select_asset_type" />
	<portlet:param name="className" value="<%= sharedAssetsViewDisplayContext.getClassName() %>" />
</portlet:renderURL>

<%
PortletURL viewAssetTypeURL = PortletURLUtil.clone(currentURLObj, liferayPortletResponse);

viewAssetTypeURL.setParameter("className", (String)null);

Map<String, Object> context = new HashMap<>();

context.put("selectAssetTypeURL", selectAssetTypeURL.toString());
context.put("viewAssetTypeURL", viewAssetTypeURL.toString());
%>

<liferay-frontend:component
	componentId='<%= renderResponse.getNamespace() + "SharedAssets" %>'
	context="<%= context %>"
	module="SharedAssets.es"
/>