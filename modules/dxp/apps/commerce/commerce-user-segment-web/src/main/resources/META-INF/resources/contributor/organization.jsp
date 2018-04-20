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

								selectedItems = selectedItems.split(",");

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

		rowColumns.push(item);
		rowColumns.push('<a class="float-right modify-link" data-rowId="' + item + '" href="javascript:;"><%= UnicodeFormatter.toString(removeCommerceUserSegmentCriterionOrganizationIcon) %></a>');

		A.Array.removeItem(<portlet:namespace />deleteCommerceUserSegmentCriterionTypeOrganizationIds, item);

		<portlet:namespace />addCommerceUserSegmentCriterionTypeOrganizationIds.push(item);

		document.<portlet:namespace />fm.<portlet:namespace />addTypeSettings.value = <portlet:namespace />addCommerceUserSegmentCriterionTypeOrganizationIds.join(',');
		document.<portlet:namespace />fm.<portlet:namespace />deleteTypeSettings.value = <portlet:namespace />deleteCommerceUserSegmentCriterionTypeOrganizationIds.join(',');

		searchContainer.addRow(rowColumns, item);

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