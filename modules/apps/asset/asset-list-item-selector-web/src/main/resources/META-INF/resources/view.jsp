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
AssetListEntryItemSelectorDisplayContext assetListEntryItemSelectorDisplayContext = (AssetListEntryItemSelectorDisplayContext)request.getAttribute(AssetListEntryItemSelectorDisplayContext.class.getName());

SearchContainer<AssetListEntry> searchContainer = assetListEntryItemSelectorDisplayContext.getSearchContainer();
%>

<clay:management-toolbar
	managementToolbarDisplayContext="<%= new AssetListEntryItemSelectorManagementToolbarDisplayContext(request, liferayPortletRequest, liferayPortletResponse, assetListEntryItemSelectorDisplayContext, searchContainer) %>"
/>

<clay:container-fluid
	cssClass="item-selector lfr-item-viewer"
	id='<%= liferayPortletResponse.getNamespace() + "entriesContainer" %>'
>
	<liferay-site-navigation:breadcrumb
		breadcrumbEntries="<%= assetListEntryItemSelectorDisplayContext.getBreadcrumbEntries(currentURLObj) %>"
	/>

	<liferay-ui:search-container
		id="entries"
		searchContainer="<%= searchContainer %>"
		var="entriesSearch"
	>
		<liferay-ui:search-container-row
			className="com.liferay.asset.list.model.AssetListEntry"
			modelVar="assetListEntry"
		>

			<%
			row.setCssClass("selector-button");
			row.setData(
				HashMapBuilder.<String, Object>put(
					"return-type", assetListEntryItemSelectorDisplayContext.getReturnType()
				).put(
					"value", assetListEntryItemSelectorDisplayContext.getPayload(assetListEntry)
				).build());
			%>

			<c:choose>
				<c:when test="<%= assetListEntryItemSelectorDisplayContext.isIconDisplayStyle() %>">

					<%
					row.setCssClass("card-page-item card-page-item-asset entry " + row.getCssClass());
					%>

					<liferay-ui:search-container-column-text>
						<clay:vertical-card
							verticalCard="<%= new AssetListEntryVerticalCard(assetListEntry, assetListEntryItemSelectorDisplayContext, renderRequest, searchContainer.getRowChecker()) %>"
						/>
					</liferay-ui:search-container-column-text>
				</c:when>
				<c:when test="<%= assetListEntryItemSelectorDisplayContext.isDescriptiveDisplayStyle() %>">

					<%
					row.setCssClass("item-selector-list-row " + row.getCssClass());
					%>

					<liferay-ui:search-container-column-user
						showDetails="<%= false %>"
						userId="<%= assetListEntry.getUserId() %>"
					/>

					<liferay-ui:search-container-column-text
						colspan="<%= 2 %>"
						cssClass="entry"
					>

						<%
						Date modifiedDate = assetListEntry.getModifiedDate();

						String modifiedDateDescription = LanguageUtil.getTimeDescription(request, System.currentTimeMillis() - modifiedDate.getTime(), true);
						%>

						<span class="text-default">
							<liferay-ui:message arguments="<%= new String[] {assetListEntry.getUserName(), modifiedDateDescription} %>" key="x-modified-x-ago" />
						</span>

						<p class="font-weight-bold h5">
							<%= assetListEntryItemSelectorDisplayContext.getTitle(assetListEntry, locale) %>
						</p>

						<p class="h6 text-default">
							<%= assetListEntryItemSelectorDisplayContext.getType(assetListEntry, locale) %>

							<%
							String subtype = assetListEntryItemSelectorDisplayContext.getSubtype(assetListEntry);
							%>

							<c:if test="<%= Validator.isNotNull(subtype) %>">
								- <%= subtype %>
							</c:if>
						</p>

						<p class="h6 text-default">
							<liferay-ui:message key="supports-filters" />
						</p>
					</liferay-ui:search-container-column-text>
				</c:when>
				<c:otherwise>
					<liferay-ui:search-container-column-text
						cssClass="table-cell-expand table-cell-minw-200"
						name="title"
					>
						<a class="entry">
							<%= assetListEntryItemSelectorDisplayContext.getTitle(assetListEntry, locale) %>
						</a>
					</liferay-ui:search-container-column-text>

					<liferay-ui:search-container-column-text
						cssClass="table-cell-expand table-cell-minw-150"
						name="type"
						value="<%= assetListEntryItemSelectorDisplayContext.getType(assetListEntry, locale) %>"
					/>

					<liferay-ui:search-container-column-text
						cssClass="table-cell-expand table-cell-minw-150"
						name="subtype"
						value="<%= assetListEntryItemSelectorDisplayContext.getSubtype(assetListEntry) %>"
					/>

					<liferay-ui:search-container-column-text
						cssClass="table-cell-expand-smaller table-cell-minw-150"
						name="user"
						value="<%= assetListEntry.getUserName() %>"
					/>

					<liferay-ui:search-container-column-text
						cssClass="table-cell-expand-smaller table-cell-minw-150"
						name="modified-date"
					>

						<%
						Date modifiedDate = assetListEntry.getModifiedDate();
						%>

						<span class="text-default">
							<liferay-ui:message arguments="<%= LanguageUtil.getTimeDescription(request, System.currentTimeMillis() - modifiedDate.getTime(), true) %>" key="modified-x-ago" />
						</span>
					</liferay-ui:search-container-column-text>

					<liferay-ui:search-container-column-text
						cssClass="table-cell-expand table-cell-smallest table-column-text-center"
						name="supports-filters"
					>
						<liferay-ui:message key="yes" />
					</liferay-ui:search-container-column-text>
				</c:otherwise>
			</c:choose>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			displayStyle="<%= assetListEntryItemSelectorDisplayContext.getDisplayStyle() %>"
			markupView="lexicon"
			searchContainer="<%= searchContainer %>"
		/>
	</liferay-ui:search-container>
</clay:container-fluid>