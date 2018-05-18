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
OrganizationCommerceUserSegmentCriterionTypeDisplayContext organizationCommerceUserSegmentCriterionTypeDisplayContext = (OrganizationCommerceUserSegmentCriterionTypeDisplayContext)request.getAttribute("organization.jsp-portletDisplayContext");

List<Organization> organizations = organizationCommerceUserSegmentCriterionTypeDisplayContext.getOrganizations();
%>

<liferay-util:buffer
	var="removeCommerceUserSegmentCriterionOrganizationIcon"
>
	<liferay-ui:icon
		icon="times"
		markupView="lexicon"
		message="remove"
	/>
</liferay-util:buffer>

<liferay-ui:search-container
	cssClass="lfr-search-container-user-segment-criterion-organizations"
	curParam="commerceUserSegmentCriterionTypeOrganizationCur"
	headerNames="null,null"
	id="commerceUserSegmentCriterionOrganizationSearchContainer"
	iteratorURL="<%= currentURLObj %>"
	total="<%= organizations.size() %>"
>
	<liferay-ui:search-container-results
		results="<%= organizations.subList(searchContainer.getStart(), searchContainer.getResultEnd()) %>"
	/>

	<liferay-ui:search-container-row
		className="com.liferay.portal.kernel.model.Organization"
		keyProperty="organizationId"
		modelVar="organization"
	>
		<liferay-ui:search-container-column-text
			cssClass="table-cell-content"
			value="<%= HtmlUtil.escape(organization.getName()) %>"
		/>

		<liferay-ui:search-container-column-text>
			<a class="float-right modify-link" data-rowId="<%= organization.getOrganizationId() %>" href="javascript:;"><%= removeCommerceUserSegmentCriterionOrganizationIcon %></a>
		</liferay-ui:search-container-column-text>
	</liferay-ui:search-container-row>

	<liferay-ui:search-iterator
		markupView="lexicon"
	/>
</liferay-ui:search-container>

<aui:button name="selectCommerceUserSegmentCriterionTypeOrganization" value="select" />

<aui:script use="liferay-item-selector-dialog">
	$('#<portlet:namespace />selectCommerceUserSegmentCriterionTypeOrganization').on(
		'click',
		function(event) {
			event.preventDefault();

			var itemSelectorDialog = new A.LiferayItemSelectorDialog(
				{
					eventName: 'organizationsSelectItem',
					on: {
						selectedItemChange: function(event) {
							var selectedItems = event.newVal;

							if (selectedItems) {
								var A = AUI();

								A.Array.each(
									selectedItems,
									function(item, index, selectedItems) {
										<portlet:namespace />addCommerceUserSegmentCriterionTypeOrganization(item);
									}
								);
							}
						}
					},
					title: '<liferay-ui:message arguments="organization" key="select-x" />',
					url: '<%= organizationCommerceUserSegmentCriterionTypeDisplayContext.getItemSelectorUrl() %>'
				}
			);

			itemSelectorDialog.open();
		}
	);
</aui:script>

<aui:script>
	var <portlet:namespace />addCommerceUserSegmentCriterionTypeOrganizationIds = [];
	var <portlet:namespace />deleteCommerceUserSegmentCriterionTypeOrganizationIds = [];

	function <portlet:namespace />addCommerceUserSegmentCriterionTypeOrganization(item) {
		var A = AUI();

		var searchContainer = Liferay.SearchContainer.get('<portlet:namespace />commerceUserSegmentCriterionOrganizationSearchContainer');

		var rowColumns = [];

		rowColumns.push(item.name);
		rowColumns.push('<a class="float-right modify-link" data-rowId="' + item.id + '" href="javascript:;"><%= UnicodeFormatter.toString(removeCommerceUserSegmentCriterionOrganizationIcon) %></a>');

		A.Array.removeItem(<portlet:namespace />deleteCommerceUserSegmentCriterionTypeOrganizationIds, item.id);

		<portlet:namespace />addCommerceUserSegmentCriterionTypeOrganizationIds.push(item.id);

		document.<portlet:namespace />fm.<portlet:namespace />addTypeSettings.value = <portlet:namespace />addCommerceUserSegmentCriterionTypeOrganizationIds.join(',');
		document.<portlet:namespace />fm.<portlet:namespace />deleteTypeSettings.value = <portlet:namespace />deleteCommerceUserSegmentCriterionTypeOrganizationIds.join(',');

		searchContainer.addRow(rowColumns, item.id);

		searchContainer.updateDataStore();
	}

	function <portlet:namespace />deleteCommerceUserSegmentCriterionTypeOrganization(organizationId) {
		var A = AUI();

		A.Array.removeItem(<portlet:namespace />addCommerceUserSegmentCriterionTypeOrganizationIds, organizationId);

		<portlet:namespace />deleteCommerceUserSegmentCriterionTypeOrganizationIds.push(organizationId);

		document.<portlet:namespace />fm.<portlet:namespace />addTypeSettings.value = <portlet:namespace />addCommerceUserSegmentCriterionTypeOrganizationIds.join(',');
		document.<portlet:namespace />fm.<portlet:namespace />deleteTypeSettings.value = <portlet:namespace />deleteCommerceUserSegmentCriterionTypeOrganizationIds.join(',');
	}
</aui:script>

<aui:script use="liferay-search-container">
	var Util = Liferay.Util;

	var searchContainer = Liferay.SearchContainer.get('<portlet:namespace />commerceUserSegmentCriterionOrganizationSearchContainer');

	var searchContainerContentBox = searchContainer.get('contentBox');

	searchContainerContentBox.delegate(
		'click',
		function(event) {
			var link = event.currentTarget;

			var rowId = link.attr('data-rowId');

			var tr = link.ancestor('tr');

			searchContainer.deleteRow(tr, link.getAttribute('data-rowId'));

			<portlet:namespace />deleteCommerceUserSegmentCriterionTypeOrganization(rowId);
		},
		'.modify-link'
	);
</aui:script>