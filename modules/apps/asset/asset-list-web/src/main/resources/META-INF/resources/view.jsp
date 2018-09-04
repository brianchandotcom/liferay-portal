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

<clay:management-toolbar
	actionDropdownItems="<%= assetListDisplayContext.getAssetListEntryActionItemsDropdownItems() %>"
	clearResultsURL="<%= assetListDisplayContext.getAssetListEntryClearResultsURL() %>"
	componentId="assetListEntriesEntriesManagementToolbar"
	creationMenu="<%= assetListDisplayContext.isShowAddAssetListEntryAction() ? assetListDisplayContext.getCreationMenu() : null %>"
	disabled="<%= (assetListDisplayContext.getAssetListEntriesCount() > 0) ? false : true %>"
	filterDropdownItems="<%= assetListDisplayContext.getAssetListEntryFilterItemsDropdownItems() %>"
	itemsTotal="<%= assetListDisplayContext.getAssetListEntryTotalItems() %>"
	searchActionURL="<%= assetListDisplayContext.getAssetListEntrySearchActionURL() %>"
	searchContainerId="assetListEntries"
	searchFormName="searchFm"
	showSearch="<%= true %>"
	sortingOrder="<%= assetListDisplayContext.getOrderByType() %>"
	sortingURL="<%= assetListDisplayContext.getSortingURL() %>"
/>

<aui:form cssClass="container-fluid-1280" name="fm">
	<c:choose>
		<c:when test="<%= assetListDisplayContext.getAssetListEntriesCount() > 0 %>">
			<liferay-ui:search-container
				id="assetListEntries"
				searchContainer="<%= assetListDisplayContext.getAssetListEntriesSearchContainer() %>"
			>
				<liferay-ui:search-container-row
					className="com.liferay.asset.list.model.AssetListEntry"
					keyProperty="assetListEntryId"
					modelVar="assetListEntry"
				>

					<%
					PortletURL editArticleURL = liferayPortletResponse.createRenderURL();

					editArticleURL.setParameter("mvcPath", "/edit_asset_list_entry.jsp");
					editArticleURL.setParameter("redirect", currentURL);
					editArticleURL.setParameter("assetListEntryId", String.valueOf(assetListEntry.getAssetListEntryId()));
					%>

					<liferay-ui:search-container-column-icon
						icon="list"
						toggleRowChecker="<%= false %>"
					/>

					<liferay-ui:search-container-column-text
						colspan="<%= 2 %>"
					>
						<h5>
							<aui:a href="<%= editArticleURL.toString() %>">
								<%= HtmlUtil.escape(assetListEntry.getTitle()) %>
							</aui:a>
						</h5>

						<h6 class="text-default">
							<strong><liferay-ui:message key="<%= HtmlUtil.escape(assetListEntry.getTypeLabel()) %>" /></strong>
						</h6>

						<%
						Date statusDate = assetListEntry.getCreateDate();
						%>

						<liferay-ui:message arguments="<%= LanguageUtil.getTimeDescription(request, System.currentTimeMillis() - statusDate.getTime(), true) %>" key="x-ago" translateArguments="<%= false %>" />
					</liferay-ui:search-container-column-text>

					<liferay-ui:search-container-column-jsp
						path="/asset_list_entry_action.jsp"
					/>
				</liferay-ui:search-container-row>

				<liferay-ui:search-iterator
					displayStyle="descriptive"
					markupView="lexicon"
				/>
			</liferay-ui:search-container>
		</c:when>
		<c:otherwise>
			<liferay-frontend:empty-result-message
				actionDropdownItems="<%= assetListDisplayContext.isShowAddAssetListEntryAction() ? assetListDisplayContext.getAddAssetListEntryDropdownItems() : null %>"
				description="<%= assetListDisplayContext.getEmptyResultMessageDescription() %>"
				elementType='<%= LanguageUtil.get(request, "asset-lists") %>'
			/>
		</c:otherwise>
	</c:choose>
</aui:form>

<aui:script>
	var deleteSelectedAssetListEntries = function() {
		if (confirm('<liferay-ui:message key="are-you-sure-you-want-to-delete-this" />')) {
			submitForm(document.querySelector('#<portlet:namespace />fm'), '<portlet:actionURL name="/asset_list/delete_asset_list_entry"><portlet:param name="redirect" value="<%= currentURL %>" /></portlet:actionURL>');
		}
	}

	var ACTIONS = {
		'deleteSelectedAssetListEntries': deleteSelectedAssetListEntries
	};

	Liferay.componentReady('assetListEntriesEntriesManagementToolbar').then(
		function(managementToolbar) {
			managementToolbar.on(
				'actionItemClicked',
					function(event) {
						var itemData = event.data.item.data;

						if (itemData && itemData.action && ACTIONS[itemData.action]) {
							ACTIONS[itemData.action]();
						}
					}
				);
		}
	);
</aui:script>