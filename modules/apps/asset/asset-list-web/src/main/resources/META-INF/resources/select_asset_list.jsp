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
SelectAssetListDisplayContext selectAssetListDisplayContext = new SelectAssetListDisplayContext(request, renderResponse);
%>

<clay:management-toolbar
	displayContext="<%= new SelectAssetListManagementToolbarDisplayContext(liferayPortletRequest, liferayPortletResponse, request, selectAssetListDisplayContext) %>"
/>

<div class="container-fluid-1280" id="<portlet:namespace />assetLists">
	<liferay-ui:search-container
		emptyResultsMessage="there-are-no-content-sets"
		id="assetListEntries"
		searchContainer="<%= selectAssetListDisplayContext.getSearchContainer() %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.asset.list.model.AssetListEntry"
			cssClass="asset-list-entry entry-display-style"
			keyProperty="assetListEntryId"
			modelVar="assetListEntry"
		>

			<%
			Map<String, Object> data = new HashMap<>();

			data.put("assetListEntryId", assetListEntry.getAssetListEntryId());
			data.put("assetListEntryTitle", assetListEntry.getTitle());
			%>

			<liferay-ui:search-container-column-icon
				icon="list"
			/>

			<liferay-ui:search-container-column-text
				colspan="<%= 2 %>"
			>
				<h5>
					<c:choose>
						<c:when test="<%= assetListEntry.getAssetListEntryId() != selectAssetListDisplayContext.getSelectedAssetListEntryId() %>">
							<aui:a cssClass="selector-button" data="<%= data %>" href="javascript:;">
								<%= HtmlUtil.escape(assetListEntry.getTitle()) %>
							</aui:a>
						</c:when>
						<c:otherwise>
							<%= HtmlUtil.escape(assetListEntry.getTitle()) %>
						</c:otherwise>
					</c:choose>
				</h5>

				<h6 class="text-default">
					<strong><liferay-ui:message key="<%= HtmlUtil.escape(assetListEntry.getTypeLabel()) %>" /></strong>
				</h6>
			</liferay-ui:search-container-column-text>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			displayStyle="descriptive"
			markupView="lexicon"
			searchContainer="<%= selectAssetListDisplayContext.getSearchContainer() %>"
		/>
	</liferay-ui:search-container>
</div>

<aui:script>
	Liferay.Util.selectEntityHandler(
		'#<portlet:namespace />assetLists',
		'<%= HtmlUtil.escapeJS(selectAssetListDisplayContext.getEventName()) %>'
	);
</aui:script>