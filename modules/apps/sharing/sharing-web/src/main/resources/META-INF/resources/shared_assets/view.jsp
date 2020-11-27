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
ViewSharedAssetsDisplayContext viewSharedAssetsDisplayContext = (ViewSharedAssetsDisplayContext)renderRequest.getAttribute(ViewSharedAssetsDisplayContext.class.getName());
%>

<clay:navigation-bar
	inverted="<%= layout.isTypeControlPanel() %>"
	navigationItems="<%= viewSharedAssetsDisplayContext.getNavigationItems() %>"
/>

<clay:management-toolbar-v2
	defaultEventHandler='<%= liferayPortletResponse.getNamespace() + "SharedAssets" %>'
	filterDropdownItems="<%= viewSharedAssetsDisplayContext.getFilterDropdownItems() %>"
	selectable="<%= false %>"
	showSearch="<%= false %>"
	sortingOrder="<%= viewSharedAssetsDisplayContext.getSortingOrder() %>"
	sortingURL="<%= String.valueOf(viewSharedAssetsDisplayContext.getSortingURL()) %>"
/>

<%
PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("mvcRenderCommandName", "/blogs/view");

SearchContainer<SharingEntry> sharingEntriesSearchContainer = new SearchContainer(renderRequest, PortletURLUtil.clone(portletURL, liferayPortletResponse), null, "no-entries-were-found");

viewSharedAssetsDisplayContext.populateResults(sharingEntriesSearchContainer);
%>

<clay:container-fluid
	cssClass="main-content-body"
>
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
				<portlet:param name="mvcRenderCommandName" value="/sharing/view_sharing_entry" />
				<portlet:param name="redirect" value="<%= currentURL %>" />
				<portlet:param name="sharingEntryId" value="<%= String.valueOf(sharingEntry.getSharingEntryId()) %>" />
			</liferay-portlet:renderURL>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-expand"
				href="<%= viewSharedAssetsDisplayContext.isVisible(sharingEntry) ? rowURL : null %>"
				name="title"
				orderable="<%= false %>"
				value="<%= viewSharedAssetsDisplayContext.getTitle(sharingEntry) %>"
			/>

			<liferay-ui:search-container-column-text
				name="asset-type"
				orderable="<%= false %>"
				value="<%= viewSharedAssetsDisplayContext.getAssetTypeTitle(sharingEntry) %>"
			/>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-expand-smallest"
				name="status"
				orderable="<%= false %>"
			>
				<c:if test="<%= !viewSharedAssetsDisplayContext.isVisible(sharingEntry) %>">
					<clay:label
						displayType="info"
						label="not-visible"
					/>
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
</clay:container-fluid>

<%
PortletURL viewAssetTypeURL = PortletURLUtil.clone(currentURLObj, liferayPortletResponse);

viewAssetTypeURL.setParameter("className", (String)null);

PortletURL selectAssetTypeURL = viewSharedAssetsDisplayContext.getSelectAssetTypeURL();
%>

<liferay-frontend:component
	componentId='<%= liferayPortletResponse.getNamespace() + "SharedAssets" %>'
	context='<%=
		HashMapBuilder.<String, Object>put(
			"selectAssetTypeURL", selectAssetTypeURL.toString()
		).put(
			"viewAssetTypeURL", viewAssetTypeURL.toString()
		).build()
	%>'
	module="SharedAssets.es"
/>