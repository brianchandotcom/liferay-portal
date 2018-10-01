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

<%@ include file="/shared_with_me/init.jsp" %>

<%
SharedWithMeViewDisplayContext sharedWithMeViewDisplayContext = (SharedWithMeViewDisplayContext)renderRequest.getAttribute(SharedWithMeViewDisplayContext.class.getName());
%>

<clay:management-toolbar
	actionHandler='<%= renderResponse.getNamespace() + "SharedWithMe" %>'
	filterDropdownItems="<%= sharedWithMeViewDisplayContext.getFilterDropdownItems() %>"
	selectable="<%= false %>"
	showSearch="<%= false %>"
	sortingOrder="<%= sharedWithMeViewDisplayContext.getSortingOrder() %>"
	sortingURL="<%= String.valueOf(sharedWithMeViewDisplayContext.getSortingURL()) %>"
/>

<%
PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("mvcRenderCommandName", "/blogs/view");

SearchContainer sharingEntriesSearchContainer = new SearchContainer(renderRequest, PortletURLUtil.clone(portletURL, liferayPortletResponse), null, "no-entries-were-found");

sharedWithMeViewDisplayContext.populateResults(sharingEntriesSearchContainer);
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
				<portlet:param name="mvcRenderCommandName" value="/shared_with_me/view_sharing_entry" />
				<portlet:param name="redirect" value="<%= sharingEntriesSearchContainer.getIteratorURL().toString() %>" />
				<portlet:param name="sharingEntryId" value="<%= String.valueOf(sharingEntry.getSharingEntryId()) %>" />
			</liferay-portlet:renderURL>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-content"
				href="<%= rowURL %>"
				name="title"
				orderable="<%= false %>"
				value="<%= sharedWithMeViewDisplayContext.getTitle(sharingEntry) %>"
			/>

			<liferay-ui:search-container-column-text
				name="asset-type"
				orderable="<%= false %>"
				value="<%= sharedWithMeViewDisplayContext.getAssetTypeTitle(sharingEntry) %>"
			/>

			<%
			Date modifiedDate = sharingEntry.getModifiedDate();

			String modifiedDateDescription = LanguageUtil.getTimeDescription(request, System.currentTimeMillis() - modifiedDate.getTime(), true);
			%>

			<liferay-ui:search-container-column-text
				name="shared-date"
				orderable="<%= false %>"
				value="<%= modifiedDateDescription %>"
			/>

			<liferay-ui:search-container-column-jsp
				path="/shared_with_me/sharing_entry_action.jsp"
			/>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			displayStyle="list"
			markupView="lexicon"
		/>
	</liferay-ui:search-container>
</div>

<aui:script require="sharing-web/SharedWithMe.es">

	<%
	PortletURL viewAssetTypeURL = PortletURLUtil.clone(currentURLObj, liferayPortletResponse);

	viewAssetTypeURL.setParameter("className", (String)null);
	%>

	var component = Liferay.component(
		'<portlet:namespace />SharedWithMe',
		new sharingWebSharedWithMeEs.default(
			{
				namespace: '<portlet:namespace />',
				selectAssetTypeURL: '<portlet:renderURL windowState="<%= LiferayWindowState.POP_UP.toString() %>"><portlet:param name="mvcRenderCommandName" value="/shared_with_me/select_asset_type" /><portlet:param name="className" value="<%= sharedWithMeViewDisplayContext.getClassName() %>" /></portlet:renderURL>',
				viewAssetTypeURL: '<%= viewAssetTypeURL %>'
			}
		)
	);
</aui:script>