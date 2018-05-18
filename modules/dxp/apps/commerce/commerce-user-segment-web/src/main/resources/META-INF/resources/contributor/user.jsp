<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */
--%>

<%@ include file="/init.jsp" %>

<%
UserCommerceUserSegmentCriterionTypeDisplayContext userCommerceUserSegmentCriterionTypeDisplayContext = (UserCommerceUserSegmentCriterionTypeDisplayContext)request.getAttribute("user.jsp-portletDisplayContext");

List<User> users = userCommerceUserSegmentCriterionTypeDisplayContext.getUsers();
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
					url: '<%= userCommerceUserSegmentCriterionTypeDisplayContext.getItemSelectorUrl() %>'
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

		rowColumns.push(item.name);
		rowColumns.push('<a class="float-right modify-link" data-rowId="' + item.id + '" href="javascript:;"><%= UnicodeFormatter.toString(removeCommerceUserSegmentCriterionUserIcon) %></a>');

		A.Array.removeItem(<portlet:namespace />deleteCommerceUserSegmentCriterionTypeUserIds, item.id);

		<portlet:namespace />addCommerceUserSegmentCriterionTypeUserIds.push(item.id);

		document.<portlet:namespace />fm.<portlet:namespace />addTypeSettings.value = <portlet:namespace />addCommerceUserSegmentCriterionTypeUserIds.join(',');
		document.<portlet:namespace />fm.<portlet:namespace />deleteTypeSettings.value = <portlet:namespace />deleteCommerceUserSegmentCriterionTypeUserIds.join(',');

		searchContainer.addRow(rowColumns, item.id);

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