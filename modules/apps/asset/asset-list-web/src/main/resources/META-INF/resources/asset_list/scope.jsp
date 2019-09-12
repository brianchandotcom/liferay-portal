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
List<Group> selectedGroups = editAssetListDisplayContext.getSelectedGroups();

PortletURL portletURL = editAssetListDisplayContext.getPortletURL();
%>

<aui:input name="TypeSettingsProperties--groupIds--" type="hidden" value="<%= StringUtil.merge(editAssetListDisplayContext.getSelectedGroupIds()) %>" />

<liferay-util:buffer
	var="removeLinkIcon"
>
	<liferay-ui:icon
		icon="times-circle"
		markupView="lexicon"
		message="remove"
	/>
</liferay-util:buffer>

<liferay-ui:search-container
	compactEmptyResultsMessage="<%= true %>"
	emptyResultsMessage="none"
	headerNames="name,type,null"
	iteratorURL="<%= portletURL %>"
	total="<%= selectedGroups.size() %>"
>
	<liferay-ui:search-container-results
		results="<%= selectedGroups %>"
	/>

	<liferay-ui:search-container-row
		className="com.liferay.portal.kernel.model.Group"
		keyProperty="groupId"
		modelVar="group"
	>
		<liferay-ui:search-container-column-text
			name="name"
			truncate="<%= true %>"
			value="<%= group.getScopeDescriptiveName(themeDisplay) %>"
		/>

		<liferay-ui:search-container-column-text
			name="type"
			value="<%= LanguageUtil.get(request, group.getScopeLabel(themeDisplay)) %>"
		/>

		<liferay-ui:search-container-column-text>
			<a class="modify-link" data-rowId="<%= group.getGroupId() %>" href="javascript:;"><%= removeLinkIcon %></a>
		</liferay-ui:search-container-column-text>
	</liferay-ui:search-container-row>

	<liferay-ui:search-iterator
		markupView="lexicon"
		paginate="<%= false %>"
	/>
</liferay-ui:search-container>

<liferay-ui:icon-menu
	cssClass="select-existing-selector"
	direction="right"
	message="select"
	showArrow="<%= false %>"
	showWhenSingleIcon="<%= true %>"
>

	<%
	for (Group group : editAssetListDisplayContext.getAvailableGroups()) {
		if (selectedGroups.contains(group)) {
			continue;
		}

		String onClick = "addRow('" + group.getGroupId() + "', '" + HtmlUtil.escapeJS(HtmlUtil.escape(group.getDescriptiveName(themeDisplay.getLocale()))) + "', '" + group.getScopeLabel(themeDisplay) + "');";
	%>

		<liferay-ui:icon
			message="<%= group.getScopeDescriptiveName(themeDisplay) %>"
			onClick="<%= onClick %>"
			url="javascript:;"
		/>

	<%
	}
	%>

	<liferay-ui:icon
		cssClass="highlited scope-selector"
		id="selectManageableGroup"
		message='<%= LanguageUtil.get(request, "other-site") + StringPool.TRIPLE_PERIOD %>'
		method="get"
		url="javascript:;"
	/>
</liferay-ui:icon-menu>

<aui:script use="liferay-search-container">
	var searchContainer = Liferay.SearchContainer.get(
		'<portlet:namespace />groupsSearchContainer'
	);

	searchContainer.get('contentBox').delegate(
		'click',
		function(event) {
			var link = event.currentTarget;

			var tr = link.ancestor('tr');

			searchContainer.deleteRow(tr, link.getAttribute('data-rowId'));

			searchContainer.updateDataStore();

			updateGroupIds();
		},
		'.modify-link'
	);

	var selectManageableGroupIcon = document.getElementById(
		'<portlet:namespace />selectManageableGroup'
	);

	if (selectManageableGroupIcon) {
		selectManageableGroupIcon.addEventListener('click', function(event) {
			event.preventDefault();

			Liferay.Util.selectEntity(
				{
					dialog: {
						destroyOnHide: true
					},
					eventName:
						'<%= editAssetListDisplayContext.getSelectGroupEventName() %>',
					id:
						'<%= editAssetListDisplayContext.getSelectGroupEventName() %>',
					title: '<liferay-ui:message key="scopes" />',
					uri:
						'<%= editAssetListDisplayContext.getGroupItemSelectorURL() %>'
				},
				function(event) {
					var entityId = event.groupid;

					var searchContainerData = searchContainer.getData();

					if (searchContainerData.indexOf(entityId) == -1) {
						addRow(
							entityId,
							event.groupdescriptivename,
							event.groupscopelabel
						);
					}
				}
			);
		});
	}

	Liferay.provide(window, 'addRow', function(groupId, name, scopeLabel) {
		var rowColumns = [];

		rowColumns.push('<span class="truncate-text">' + name + '</span>');
		rowColumns.push(scopeLabel);
		rowColumns.push(
			'<a class="modify-link" data-rowId="' +
				groupId +
				'" href="javascript:;"><%= UnicodeFormatter.toString(removeLinkIcon) %></a>'
		);

		searchContainer.addRow(rowColumns, groupId);

		searchContainer.updateDataStore();

		updateGroupIds();
	});

	Liferay.provide(window, 'updateGroupIds', function() {
		var groupIds = document.getElementById('<portlet:namespace />groupIds');

		if (groupIds) {
			var searchContainerData = searchContainer.getData();

			groupIds.setAttribute('value', searchContainerData.split(','));
		}
	});
</aui:script>