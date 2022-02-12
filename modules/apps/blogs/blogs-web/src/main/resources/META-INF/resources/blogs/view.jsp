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
String mvcRenderCommandName = ParamUtil.getString(request, "mvcRenderCommandName");

long assetCategoryId = ParamUtil.getLong(request, "categoryId");
String assetTagName = ParamUtil.getString(request, "tag");

boolean useAssetEntryQuery = (assetCategoryId > 0) || Validator.isNotNull(assetTagName);

PortletURL portletURL = PortletURLBuilder.createRenderURL(
	renderResponse
).setMVCRenderCommandName(
	"/blogs/view"
).buildPortletURL();
%>

<liferay-ui:success key='<%= portletDisplay.getId() + "requestProcessed" %>' message="your-request-completed-successfully" />
<liferay-ui:success key="blogsEntryPublished" message="the-blog-entry-was-published-successfully" />

<portlet:actionURL name="/blogs/edit_entry" var="restoreTrashEntriesURL">
	<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.RESTORE %>" />
</portlet:actionURL>

<liferay-trash:undo
	portletURL="<%= restoreTrashEntriesURL %>"
/>

<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />

<%
BlogsPortletInstanceConfiguration blogsPortletInstanceConfiguration = BlogsPortletInstanceConfigurationUtil.getBlogsPortletInstanceConfiguration(themeDisplay);

int pageDelta = GetterUtil.getInteger(blogsPortletInstanceConfiguration.pageDelta());

SearchContainer<BaseModel<?>> searchContainer = new SearchContainer(renderRequest, null, null, SearchContainer.DEFAULT_CUR_PARAM, pageDelta, currentURLObj, null, null);

searchContainer.setDelta(pageDelta);
searchContainer.setDeltaConfigurable(false);

int notPublishedEntriesCount = BlogsEntryServiceUtil.getGroupUserEntriesCount(scopeGroupId, themeDisplay.getUserId(), new int[] {WorkflowConstants.STATUS_DRAFT, WorkflowConstants.STATUS_PENDING, WorkflowConstants.STATUS_SCHEDULED});

if (useAssetEntryQuery) {
	SearchContainerResults<AssetEntry> searchContainerResults = BlogsUtil.getSearchContainerResults(searchContainer);

	searchContainer.setResultsAndTotal(() -> new ArrayList<>(searchContainerResults.getResults()), searchContainerResults.getTotal());
}
else if ((notPublishedEntriesCount > 0) && mvcRenderCommandName.equals("/blogs/view_not_published_entries")) {
	long blogsScopeGroupId = scopeGroupId;
	long blogsUserId = themeDisplay.getUserId();

	searchContainer.setResultsAndTotal(() -> new ArrayList<>(BlogsEntryServiceUtil.getGroupUserEntries(blogsScopeGroupId, blogsUserId, new int[] {WorkflowConstants.STATUS_DRAFT, WorkflowConstants.STATUS_PENDING, WorkflowConstants.STATUS_SCHEDULED}, searchContainer.getStart(), searchContainer.getEnd(), new EntryModifiedDateComparator())), notPublishedEntriesCount);
}
else {
	long blogsScopeGroupId = scopeGroupId;
	int status = WorkflowConstants.STATUS_APPROVED;

	searchContainer.setResultsAndTotal(() -> new ArrayList<>(BlogsEntryServiceUtil.getGroupEntries(blogsScopeGroupId, status, searchContainer.getStart(), searchContainer.getEnd())), BlogsEntryServiceUtil.getGroupEntriesCount(blogsScopeGroupId, status));
}
%>

<c:if test="<%= notPublishedEntriesCount > 0 %>">
	<clay:navigation-bar
		navigationItems='<%=
			new JSPNavigationItemList(pageContext) {
				{
					add(
						navigationItem -> {
							navigationItem.setActive(!mvcRenderCommandName.equals("/blogs/view_not_published_entries"));
							navigationItem.setHref(portletURL);
							navigationItem.setLabel(LanguageUtil.get(httpServletRequest, "published"));
						});

					add(
						navigationItem -> {
							navigationItem.setActive(mvcRenderCommandName.equals("/blogs/view_not_published_entries"));
							navigationItem.setHref(renderResponse.createRenderURL(), "mvcRenderCommandName", "/blogs/view_not_published_entries");
							navigationItem.setLabel(LanguageUtil.format(httpServletRequest, "not-published-x", notPublishedEntriesCount, false));
						});
				}
			}
		%>'
	/>
</c:if>

<%@ include file="/blogs/view_entries.jspf" %>