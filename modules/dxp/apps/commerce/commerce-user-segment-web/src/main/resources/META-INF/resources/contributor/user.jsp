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
CommerceUserSegmentCriterionTypeUserDisplayContext commerceUserSegmentCriterionTypeUserDisplayContext = (CommerceUserSegmentCriterionTypeUserDisplayContext)request.getAttribute("user.jsp-portletDisplayContext");

List<User> users = commerceUserSegmentCriterionTypeUserDisplayContext.getUsers();
%>

<liferay-util:buffer
	var="removeCommerceUserSegmentCriterionUserIcon"
>
	<liferay-ui:icon
		icon="times"
		markupView="lexicon"
		message="remove"
	/>
</liferay-util:buffer>

<liferay-ui:search-container
	cssClass="lfr-search-container-user-segment-criterion-users"
	curParam="commerceUserSegmentCriterionTypeUserCur"
	headerNames="null,null"
	id="commerceUserSegmentCriterionUserSearchContainer"
	iteratorURL="<%= currentURLObj %>"
	total="<%= users.size() %>"
>
	<liferay-ui:search-container-results
		results="<%= users.subList(searchContainer.getStart(), searchContainer.getResultEnd()) %>"
	/>

	<liferay-ui:search-container-row
		className="com.liferay.portal.kernel.model.User"
		keyProperty="userId"
		modelVar="user"
	>
		<liferay-ui:search-container-column-text
			cssClass="table-cell-content"
			value="<%= HtmlUtil.escape(user.getFullName()) %>"
		/>

		<liferay-ui:search-container-column-text>
			<a class="float-right modify-link" data-rowId="<%= user.getUserId() %>" href="javascript:;"><%= removeCommerceUserSegmentCriterionUserIcon %></a>
		</liferay-ui:search-container-column-text>
	</liferay-ui:search-container-row>

	<liferay-ui:search-iterator
		markupView="lexicon"
	/>
</liferay-ui:search-container>

<aui:button name="selectCommerceUserSegmentCriterionTypeUser" value="select" />

<aui:script use="liferay-item-selector-dialog">
	$('#<portlet:namespace />selectCommerceUserSegmentCriterionTypeUser').on(
		'click',
		function(event) {
			event.preventDefault();

			var itemSelectorDialog = new A.LiferayItemSelectorDialog(
				{
					eventName: 'usersSelectItem',
					on: {
						selectedItemChange: function(event) {
							var selectedItems = event.newVal;

							if (selectedItems) {
								var A = AUI();

								selectedItems = selectedItems.split(",");

								A.Array.each(
									selectedItems,
									function(item, index, selectedItems) {
										<portlet:namespace />addCommerceUserSegmentCriterionTypeUser(item);
									}
								);
							}
						}
					},
					title: '<liferay-ui:message arguments="user" key="select-x" />',
					url: '<%= commerceUserSegmentCriterionTypeUserDisplayContext.getItemSelectorUrl() %>'
				}
			);

			itemSelectorDialog.open();
		}
	);
</aui:script>

<aui:script>
	var <portlet:namespace />addCommerceUserSegmentCriterionTypeUserIds = [];
	var <portlet:namespace />deleteCommerceUserSegmentCriterionTypeUserIds = [];

	function <portlet:namespace />addCommerceUserSegmentCriterionTypeUser(item) {
		var A = AUI();

		var searchContainer = Liferay.SearchContainer.get('<portlet:namespace />commerceUserSegmentCriterionUserSearchContainer');

		var rowColumns = [];

		rowColumns.push(item);
		rowColumns.push('<a class="float-right modify-link" data-rowId="' + item + '" href="javascript:;"><%= UnicodeFormatter.toString(removeCommerceUserSegmentCriterionUserIcon) %></a>');

		A.Array.removeItem(<portlet:namespace />deleteCommerceUserSegmentCriterionTypeUserIds, item);

		<portlet:namespace />addCommerceUserSegmentCriterionTypeUserIds.push(item);

		document.<portlet:namespace />fm.<portlet:namespace />addTypeSettings.value = <portlet:namespace />addCommerceUserSegmentCriterionTypeUserIds.join(',');
		document.<portlet:namespace />fm.<portlet:namespace />deleteTypeSettings.value = <portlet:namespace />deleteCommerceUserSegmentCriterionTypeUserIds.join(',');

		searchContainer.addRow(rowColumns, item);

		searchContainer.updateDataStore();
	}

	function <portlet:namespace />deleteCommerceUserSegmentCriterionTypeUser(userId) {
		var A = AUI();

		A.Array.removeItem(<portlet:namespace />addCommerceUserSegmentCriterionTypeUserIds, userId);

		<portlet:namespace />deleteCommerceUserSegmentCriterionTypeUserIds.push(userId);

		document.<portlet:namespace />fm.<portlet:namespace />addTypeSettings.value = <portlet:namespace />addCommerceUserSegmentCriterionTypeUserIds.join(',');
		document.<portlet:namespace />fm.<portlet:namespace />deleteTypeSettings.value = <portlet:namespace />deleteCommerceUserSegmentCriterionTypeUserIds.join(',');
	}
</aui:script>

<aui:script use="liferay-search-container">
	var Util = Liferay.Util;

	var searchContainer = Liferay.SearchContainer.get('<portlet:namespace />commerceUserSegmentCriterionUserSearchContainer');

	var searchContainerContentBox = searchContainer.get('contentBox');

	searchContainerContentBox.delegate(
		'click',
		function(event) {
			var link = event.currentTarget;

			var rowId = link.attr('data-rowId');

			var tr = link.ancestor('tr');

			searchContainer.deleteRow(tr, link.getAttribute('data-rowId'));

			<portlet:namespace />deleteCommerceUserSegmentCriterionTypeUser(rowId);
		},
		'.modify-link'
	);
</aui:script>