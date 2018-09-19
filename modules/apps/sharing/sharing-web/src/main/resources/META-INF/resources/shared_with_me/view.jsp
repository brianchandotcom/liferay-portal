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
PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("mvcRenderCommandName", "/blogs/view");

SearchContainer sharingEntriesSearchContainer = new SearchContainer(renderRequest, PortletURLUtil.clone(portletURL, liferayPortletResponse), null, "no-entries-were-found");

SharedWithMeViewDisplayContext sharedWithMeViewDisplayContext = (SharedWithMeViewDisplayContext)renderRequest.getAttribute(SharedWithMeViewDisplayContext.class.getName());

sharedWithMeViewDisplayContext.populateResults(sharingEntriesSearchContainer);
%>

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

		<liferay-ui:search-container-column-jsp
			path="/shared_with_me/sharing_entry_action.jsp"
		/>
	</liferay-ui:search-container-row>

	<liferay-ui:search-iterator
		displayStyle="list"
		markupView="lexicon"
	/>
</liferay-ui:search-container>