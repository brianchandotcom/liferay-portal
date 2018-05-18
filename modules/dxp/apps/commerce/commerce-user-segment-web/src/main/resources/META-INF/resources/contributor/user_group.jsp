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
UserGroupCommerceUserSegmentCriterionTypeDisplayContext userGroupCommerceUserSegmentCriterionTypeDisplayContext = (UserGroupCommerceUserSegmentCriterionTypeDisplayContext)request.getAttribute("user_group.jsp-portletDisplayContext");

List<UserGroup> userGroups = userGroupCommerceUserSegmentCriterionTypeDisplayContext.getUserGroups();
%>

<liferay-util:buffer
	var="removeCommerceUserSegmentCriterionUserGroupIcon"
>
	<liferay-ui:icon
		icon="times"
		markupView="lexicon"
		message="remove"
	/>
</liferay-util:buffer>

<liferay-ui:search-container
	cssClass="lfr-search-container-user-segment-criterion-user-groups"
	curParam="commerceUserSegmentCriterionTypeUserGroupCur"
	headerNames="null,null"
	id="commerceUserSegmentCriterionUserGroupSearchContainer"
	iteratorURL="<%= currentURLObj %>"
	total="<%= userGroups.size() %>"
>
	<liferay-ui:search-container-results
		results="<%= userGroups.subList(searchContainer.getStart(), searchContainer.getResultEnd()) %>"
	/>

	<liferay-ui:search-container-row
		className="com.liferay.portal.kernel.model.UserGroup"
		keyProperty="userGroupId"
		modelVar="userGroup"
	>
		<liferay-ui:search-container-column-text
			cssClass="table-cell-content"
			value="<%= HtmlUtil.escape(userGroup.getName()) %>"
		/>

		<liferay-ui:search-container-column-text>
			<a class="float-right modify-link" data-rowId="<%= userGroup.getUserGroupId() %>" href="javascript:;"><%= removeCommerceUserSegmentCriterionUserGroupIcon %></a>
		</liferay-ui:search-container-column-text>
	</liferay-ui:search-container-row>

	<liferay-ui:search-iterator
		markupView="lexicon"
	/>
</liferay-ui:search-container>

<aui:button name="selectCommerceUserSegmentCriterionTypeUserGroup" value="select" />

<aui:script use="liferay-item-selector-dialog">
	$('#<portlet:namespace />selectCommerceUserSegmentCriterionTypeUserGroup').on(
		'click',
		function(event) {
			event.preventDefault();

			var itemSelectorDialog = new A.LiferayItemSelectorDialog(
				{
					eventName: 'userGroupsSelectItem',
					on: {
						selectedItemChange: function(event) {
							var selectedItems = event.newVal;

							if (selectedItems) {
								var A = AUI();

								A.Array.each(
									selectedItems,
									function(item, index, selectedItems) {
										<portlet:namespace />addCommerceUserSegmentCriterionTypeUserGroup(item);
									}
								);
							}
						}
					},
					title: '<liferay-ui:message arguments="user-group" key="select-x" />',
					url: '<%= userGroupCommerceUserSegmentCriterionTypeDisplayContext.getItemSelectorUrl() %>'
				}
			);

			itemSelectorDialog.open();
		}
	);
</aui:script>

<aui:script>
	var <portlet:namespace />addCommerceUserSegmentCriterionTypeUserGroupIds = [];
	var <portlet:namespace />deleteCommerceUserSegmentCriterionTypeUserGroupIds = [];

	function <portlet:namespace />addCommerceUserSegmentCriterionTypeUserGroup(item) {
		var A = AUI();

		var searchContainer = Liferay.SearchContainer.get('<portlet:namespace />commerceUserSegmentCriterionUserGroupSearchContainer');

		var rowColumns = [];

		rowColumns.push(item.name);
		rowColumns.push('<a class="float-right modify-link" data-rowId="' + item.id + '" href="javascript:;"><%= UnicodeFormatter.toString(removeCommerceUserSegmentCriterionUserGroupIcon) %></a>');

		A.Array.removeItem(<portlet:namespace />deleteCommerceUserSegmentCriterionTypeUserGroupIds, item.id);

		<portlet:namespace />addCommerceUserSegmentCriterionTypeUserGroupIds.push(item.id);

		document.<portlet:namespace />fm.<portlet:namespace />addTypeSettings.value = <portlet:namespace />addCommerceUserSegmentCriterionTypeUserGroupIds.join(',');
		document.<portlet:namespace />fm.<portlet:namespace />deleteTypeSettings.value = <portlet:namespace />deleteCommerceUserSegmentCriterionTypeUserGroupIds.join(',');

		searchContainer.addRow(rowColumns, item.id);

		searchContainer.updateDataStore();
	}

	function <portlet:namespace />deleteCommerceUserSegmentCriterionTypeUserGroup(userGroupId) {
		var A = AUI();

		A.Array.removeItem(<portlet:namespace />addCommerceUserSegmentCriterionTypeUserGroupIds, userGroupId);

		<portlet:namespace />deleteCommerceUserSegmentCriterionTypeUserGroupIds.push(userGroupId);

		document.<portlet:namespace />fm.<portlet:namespace />addTypeSettings.value = <portlet:namespace />addCommerceUserSegmentCriterionTypeUserGroupIds.join(',');
		document.<portlet:namespace />fm.<portlet:namespace />deleteTypeSettings.value = <portlet:namespace />deleteCommerceUserSegmentCriterionTypeUserGroupIds.join(',');
	}
</aui:script>

<aui:script use="liferay-search-container">
	var Util = Liferay.Util;

	var searchContainer = Liferay.SearchContainer.get('<portlet:namespace />commerceUserSegmentCriterionUserGroupSearchContainer');

	var searchContainerContentBox = searchContainer.get('contentBox');

	searchContainerContentBox.delegate(
		'click',
		function(event) {
			var link = event.currentTarget;

			var rowId = link.attr('data-rowId');

			var tr = link.ancestor('tr');

			searchContainer.deleteRow(tr, link.getAttribute('data-rowId'));

			<portlet:namespace />deleteCommerceUserSegmentCriterionTypeUserGroup(rowId);
		},
		'.modify-link'
	);
</aui:script>