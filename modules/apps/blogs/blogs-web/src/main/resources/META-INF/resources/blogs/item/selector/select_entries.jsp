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

<%@ include file="/blogs/item/selector/init.jsp" %>

<%
BlogEntriesItemSelectorDisplayContext blogEntriesItemSelectorDisplayContext = (BlogEntriesItemSelectorDisplayContext)request.getAttribute(BlogsWebKeys.BLOGS_ITEM_SELECTOR_DISPLAY_CONTEXT);
%>

<clay:management-toolbar
	displayContext="<%= new BlogEntriesItemSelectorManagementToolbarDisplayContext(liferayPortletRequest, liferayPortletResponse, request, blogEntriesItemSelectorDisplayContext.getSearchContainer()) %>"
	searchContainerId="blogEntries"
/>

<div class="container-fluid container-fluid-max-xl main-content-body" id="<portlet:namespace />blogEntriesContainer">
	<liferay-ui:search-container
		id="blogEntries"
		searchContainer="<%= blogEntriesItemSelectorDisplayContext.getSearchContainer() %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.blogs.model.BlogsEntry"
			escapedModel="<%= true %>"
			keyProperty="entryId"
			modelVar="entry"
		>
			<c:choose>
				<c:when test='<%= Objects.equals(blogEntriesItemSelectorDisplayContext.getDisplayStyle(), "descriptive") %>'>
					<liferay-ui:search-container-column-user
						showDetails="<%= false %>"
						userId="<%= entry.getUserId() %>"
					/>

					<liferay-ui:search-container-column-text
						colspan="<%= 2 %>"
					>

						<%
						Date modifiedDate = entry.getModifiedDate();

						String modifiedDateDescription = LanguageUtil.getTimeDescription(request, System.currentTimeMillis() - modifiedDate.getTime(), true);
						%>

						<span class="text-default">
							<liferay-ui:message arguments="<%= new String[] {entry.getUserName(), modifiedDateDescription} %>" key="x-modified-x-ago" />
						</span>

						<p class="font-weight-bold h5">
							<%= BlogsEntryUtil.getDisplayTitle(resourceBundle, entry) %>
						</p>

						<span class="text-default">
							<aui:workflow-status markupView="lexicon" showIcon="<%= false %>" showLabel="<%= false %>" status="<%= entry.getStatus() %>" />
						</span>
					</liferay-ui:search-container-column-text>
				</c:when>
				<c:when test='<%= Objects.equals(blogEntriesItemSelectorDisplayContext.getDisplayStyle(), "icon") %>'>

					<%
					row.setCssClass("entry-card lfr-asset-item " + row.getCssClass());
					%>

					<liferay-ui:search-container-column-text>
						<clay:vertical-card
							verticalCard="<%= new BlogsEntryItemSelectorVerticalCard(entry, renderRequest, resourceBundle) %>"
						/>
					</liferay-ui:search-container-column-text>
				</c:when>
				<c:otherwise>
					<liferay-ui:search-container-column-text
						cssClass="table-cell-expand table-cell-minw-200 table-title"
						name="title"
						value="<%= BlogsEntryUtil.getDisplayTitle(resourceBundle, entry) %>"
					/>

					<liferay-ui:search-container-column-text
						cssClass="table-cell-expand-smallest table-cell-minw-150"
						name="author"
						property="userName"
					/>

					<liferay-ui:search-container-column-date
						cssClass="table-cell-expand-smallest table-cell-ws-nowrap"
						name="create-date"
						property="createDate"
					/>

					<liferay-ui:search-container-column-date
						cssClass="table-cell-expand-smallest table-cell-ws-nowrap"
						name="display-date"
						property="displayDate"
					/>

					<%
					AssetEntry assetEntry = AssetEntryLocalServiceUtil.getEntry(BlogsEntry.class.getName(), entry.getEntryId());
					%>

					<liferay-ui:search-container-column-text
						cssClass="table-column-text-end"
						name="views"
						value="<%= String.valueOf(assetEntry.getViewCount()) %>"
					/>

					<liferay-ui:search-container-column-status
						name="status"
					/>
				</c:otherwise>
			</c:choose>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			displayStyle="<%= blogEntriesItemSelectorDisplayContext.getDisplayStyle() %>"
			markupView="lexicon"
		/>
	</liferay-ui:search-container>
</div>