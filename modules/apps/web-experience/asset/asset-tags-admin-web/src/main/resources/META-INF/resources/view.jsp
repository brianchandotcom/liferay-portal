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

<clay:navigation-bar
	inverted="<%= true %>"
	items="<%= assetTagsDisplayContext.getNavigationItems() %>"
/>

<clay:management-toolbar
	actionItems="<%= assetTagsDisplayContext.getActionItemsDropdownItemList() %>"
	clearResultsURL="<%= assetTagsDisplayContext.getClearResultsURL() %>"
	componentId="assetTagsManagementToolbar"
	creationMenu="<%= assetTagsDisplayContext.isShowAddButton() ? assetTagsDisplayContext.getCreationMenu() : null %>"
	disabled="<%= assetTagsDisplayContext.getTotalItems() == 0 %>"
	filterItems="<%= assetTagsDisplayContext.getFilterItemsDropdownItemList() %>"
	namespace="<%= renderResponse.getNamespace() %>"
	searchActionURL="<%= assetTagsDisplayContext.getSearchActionURL() %>"
	searchContainerId="assetTags"
	searchFormName="searchFm"
	showSearch="<%= assetTagsDisplayContext.isShowSearch() %>"
	sortingOrder="<%= assetTagsDisplayContext.getOrderByType() %>"
	sortingURL="<%= assetTagsDisplayContext.getSortingURL() %>"
	totalItems="<%= assetTagsDisplayContext.getTotalItems() %>"
	viewTypes="<%= assetTagsDisplayContext.getViewTypeItemList() %>"
/>

<portlet:actionURL name="deleteTag" var="deleteTagURL">
	<portlet:param name="redirect" value="<%= currentURL %>" />
</portlet:actionURL>

<aui:form action="<%= deleteTagURL %>" cssClass="container-fluid-1280" name="fm">
	<liferay-ui:search-container
		id="assetTags"
		searchContainer="<%= assetTagsDisplayContext.getTagsSearchContainer() %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.asset.kernel.model.AssetTag"
			keyProperty="tagId"
			modelVar="tag"
		>

			<%
			long fullTagsCount = assetTagsDisplayContext.getFullTagsCount(tag);
			%>

			<c:choose>
				<c:when test='<%= Objects.equals(assetTagsDisplayContext.getDisplayStyle(), "descriptive") %>'>
					<liferay-ui:search-container-column-icon
						icon="tag"
						toggleRowChecker="<%= true %>"
					/>

					<liferay-ui:search-container-column-text
						colspan="<%= 2 %>"
					>
						<h5>
							<%= tag.getName() %>
						</h5>

						<h6 class="text-default">
							<strong><liferay-ui:message key="usages" /></strong>: <span><%= String.valueOf(fullTagsCount) %></span>
						</h6>
					</liferay-ui:search-container-column-text>

					<liferay-ui:search-container-column-jsp
						path="/tag_action.jsp"
					/>
				</c:when>
				<c:when test='<%= Objects.equals(assetTagsDisplayContext.getDisplayStyle(), "icon") %>'>

					<%
					row.setCssClass("entry-card lfr-asset-item");
					%>

					<liferay-ui:search-container-column-text>
						<liferay-frontend:icon-vertical-card
							actionJsp="/tag_action.jsp"
							actionJspServletContext="<%= application %>"
							icon="tag"
							resultRow="<%= row %>"
							rowChecker="<%= searchContainer.getRowChecker() %>"
							title="<%= tag.getName() %>"
						>
							<liferay-frontend:vertical-card-footer>
								<strong><liferay-ui:message key="usages" /></strong>: <span><%= String.valueOf(fullTagsCount) %></span>
							</liferay-frontend:vertical-card-footer>
						</liferay-frontend:icon-vertical-card>
					</liferay-ui:search-container-column-text>
				</c:when>
				<c:when test='<%= Objects.equals(assetTagsDisplayContext.getDisplayStyle(), "list") %>'>
					<liferay-ui:search-container-column-text
						cssClass="table-cell-content"
						name="name"
						value="<%= tag.getName() %>"
					/>

					<liferay-ui:search-container-column-text
						name="usages"
						value="<%= String.valueOf(fullTagsCount) %>"
					/>

					<liferay-ui:search-container-column-jsp
						path="/tag_action.jsp"
					/>
				</c:when>
			</c:choose>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			displayStyle="<%= assetTagsDisplayContext.getDisplayStyle() %>"
			markupView="lexicon"
		/>
	</liferay-ui:search-container>
</aui:form>

<aui:script>
	function <portlet:namespace />mergeTags() {
		<portlet:renderURL var="mergeURL">
			<portlet:param name="mvcPath" value="/merge_tag.jsp" />
			<portlet:param name="mergeTagIds" value="[$MERGE_TAGS_IDS$]" />
		</portlet:renderURL>

		let mergeURL = '<%= mergeURL %>';

		location.href = mergeURL.replace(
			escape('[$MERGE_TAGS_IDS$]'),
			Liferay.Util.listCheckedExcept(document.querySelector('#<portlet:namespace />fm'), '<portlet:namespace />allRowIds')
		);
	}

	function <portlet:namespace/>deleteTags() {
		if (confirm('<liferay-ui:message key="are-you-sure-you-want-to-delete-this" />')) {
			submitForm(form);
		}
	}
</aui:script>