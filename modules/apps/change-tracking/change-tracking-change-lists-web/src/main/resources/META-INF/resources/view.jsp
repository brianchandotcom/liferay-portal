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
	creationMenu="<%= changeListsDisplayContext.getCreationMenu() %>"
	filterDropdownItems="<%= changeListsDisplayContext.getFilterDropdownItems() %>"
	searchContainerId="changeLists"
	selectable="<%= false %>"
	showCreationMenu="<%= true %>"
	showSearch="<%= true %>"
	sortingOrder="<%= changeListsDisplayContext.getOrderByType() %>"
	sortingURL="<%= changeListsDisplayContext.getSortingURL() %>"
	viewTypeItems="<%= changeListsDisplayContext.getViewTypeItems() %>"
/>

<div class="closed container-fluid-1280">
	<liferay-ui:search-container
		id="changeLists"
		searchContainer="<%= changeListsDisplayContext.getSearchContainer() %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.change.tracking.model.CTCollection"
			keyProperty="ctCollectionId"
			modelVar="curCTCollection"
		>
			<liferay-ui:search-container-column-text
				name="name"
			>
				<%= HtmlUtil.escape(curCTCollection.getName()) %>
			</liferay-ui:search-container-column-text>

			<liferay-ui:search-container-column-text
				name="modified-date"
			>
				<%= curCTCollection.getModifiedDate() %>
			</liferay-ui:search-container-column-text>

			<liferay-ui:search-container-column-text
				name="created-by"
			>
				<%= curCTCollection.getUserName() %>
			</liferay-ui:search-container-column-text>

			<liferay-ui:search-container-column-text
				name="description"
			>
				<%= HtmlUtil.escape(curCTCollection.getDescription()) %>
			</liferay-ui:search-container-column-text>

			<liferay-ui:search-container-column-text>
				<liferay-ui:icon-menu
					direction="left-side"
					icon="<%= StringPool.BLANK %>"
					markupView="lexicon"
					message="<%= StringPool.BLANK %>"
					showWhenSingleIcon="<%= true %>"
				>
					<liferay-portlet:renderURL var="editCollectionURL">
						<portlet:param name="mvcRenderCommandName" value="/change_lists/edit_ct_collection" />
						<portlet:param name="backURL" value="<%= themeDisplay.getURLCurrent() %>" />
						<portlet:param name="ctCollectionId" value="<%= String.valueOf(curCTCollection.getCtCollectionId()) %>" />
					</liferay-portlet:renderURL>

					<liferay-ui:icon
						message="edit"
						url="<%= editCollectionURL %>"
					/>

					<liferay-portlet:actionURL name="/change_lists/checkout_ct_collection" var="checkoutCollectionURL">
						<portlet:param name="ctCollectionId" value="<%= String.valueOf(curCTCollection.getCtCollectionId()) %>" />
					</liferay-portlet:actionURL>

					<liferay-ui:icon
						message="make-active"
						url="<%= checkoutCollectionURL %>"
					/>

					<liferay-portlet:actionURL name="/change_lists/publish_ct_collection" var="publishCollectionURL">
						<portlet:param name="ctCollectionId" value="<%= String.valueOf(curCTCollection.getCtCollectionId()) %>" />
					</liferay-portlet:actionURL>

					<liferay-ui:icon
						message="publish"
						url="<%= publishCollectionURL %>"
					/>

					<liferay-portlet:actionURL var="deleteCollectionURL">
						<portlet:param name="ctCollectionId" value="<%= String.valueOf(curCTCollection.getCtCollectionId()) %>" />
					</liferay-portlet:actionURL>

					<liferay-ui:icon
						message="delete"
						url="<%= deleteCollectionURL %>"
					/>
				</liferay-ui:icon-menu>
			</liferay-ui:search-container-column-text>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			markupView="lexicon"
		/>
	</liferay-ui:search-container>
</div>