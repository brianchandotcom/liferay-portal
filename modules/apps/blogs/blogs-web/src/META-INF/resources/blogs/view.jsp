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

<%@ include file="/blogs/init.jsp" %>

<%
long assetCategoryId = ParamUtil.getLong(request, "categoryId");
String assetTagName = ParamUtil.getString(request, "tag");

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("mvcRenderCommandName", "/blogs/view");
%>

<portlet:actionURL name="/blogs/edit_entry" var="restoreTrashEntriesURL">
	<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.RESTORE %>" />
</portlet:actionURL>

<liferay-ui:trash-undo
	portletURL="<%= restoreTrashEntriesURL %>"
/>

<liferay-portlet:renderURL varImpl="searchURL">
	<portlet:param name="mvcPath" value="/blogs/search.jsp" />
</liferay-portlet:renderURL>

<aui:form action="<%= searchURL %>" method="get" name="fm1">
	<liferay-portlet:renderURLParams varImpl="searchURL" />
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />

	<%
	SearchContainer searchContainer = new SearchContainer(renderRequest, null, null, SearchContainer.DEFAULT_CUR_PARAM, blogsPortletInstanceSettings.getPageDelta(), portletURL, null, null);

	searchContainer.setDelta(blogsPortletInstanceSettings.getPageDelta());
	searchContainer.setDeltaConfigurable(false);

	int total = 0;
	List results = null;

	if ((assetCategoryId != 0) || Validator.isNotNull(assetTagName)) {
		SearchContainerResults<AssetEntry> searchContainerResults = BlogsUtil.getSearchContainerResults(searchContainer);

		searchContainer.setTotal(searchContainerResults.getTotal());

		results = searchContainerResults.getResults();
	}
	else {
		int status = WorkflowConstants.STATUS_APPROVED;

		total = BlogsEntryServiceUtil.getGroupEntriesCount(scopeGroupId, status);

		searchContainer.setTotal(total);

		results = BlogsEntryServiceUtil.getGroupEntries(scopeGroupId, status, searchContainer.getStart(), searchContainer.getEnd());
	}

	searchContainer.setResults(results);
	%>

	<%@ include file="/blogs/view_entries.jspf" %>
</aui:form>