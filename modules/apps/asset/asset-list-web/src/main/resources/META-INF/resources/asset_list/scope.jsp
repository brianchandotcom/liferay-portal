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
	iteratorURL="<%= editAssetListDisplayContext.getPortletURL() %>"
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

		<c:if test="<%= !editAssetListDisplayContext.isLiveGroup() %>">
			<liferay-ui:search-container-column-text>
				<a class="modify-link" data-rowId="<%= group.getGroupId() %>" href="javascript:;"><%= removeLinkIcon %></a>
			</liferay-ui:search-container-column-text>
		</c:if>
	</liferay-ui:search-container-row>

	<liferay-ui:search-iterator
		markupView="lexicon"
		paginate="<%= false %>"
	/>
</liferay-ui:search-container>

<c:if test="<%= !editAssetListDisplayContext.isLiveGroup() %>">
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

			String taglibOnClick = liferayPortletResponse.getNamespace() + "addRow('" + group.getGroupId() + "', '" + HtmlUtil.escapeJS(HtmlUtil.escape(group.getDescriptiveName(themeDisplay.getLocale()))) + "', '" + LanguageUtil.get(request, group.getScopeLabel(themeDisplay)) + "');";
		%>

			<liferay-ui:icon
				message="<%= group.getScopeDescriptiveName(themeDisplay) %>"
				onClick="<%= taglibOnClick %>"
				url="javascript:;"
			/>

		<%
		}
		%>

		<liferay-ui:icon
			cssClass="highlited scope-selector"
			id="selectManageableGroup"
			message='<%= LanguageUtil.get(request, "other-site-or-asset-library") + StringPool.TRIPLE_PERIOD %>'
			method="get"
			url="javascript:;"
		/>
	</liferay-ui:icon-menu>
</c:if>

<aui:script use="liferay-search-container">
	var searchContainer = Liferay.SearchContainer.get(
		'<portlet:namespace />groupsSearchContainer'
	);

	searchContainer.get('contentBox').delegate(
		'click',
		(event) => {
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
		selectManageableGroupIcon.addEventListener('click', (event) => {
			event.preventDefault();

			Liferay.Util.openSelectionModal({
				id: '<%= editAssetListDisplayContext.getSelectGroupEventName() %>',
				onSelect: function (selectedItem) {
					var entityId = selectedItem.groupid;

					var searchContainerData = searchContainer.getData();

					if (searchContainerData.indexOf(entityId) == -1) {
						<portlet:namespace />addRow(
							entityId,
							selectedItem.groupdescriptivename,
							selectedItem.groupscopelabel
						);
					}
				},
				selectEventName:
					'<%= editAssetListDisplayContext.getSelectGroupEventName() %>',
				title: '<liferay-ui:message key="scopes" />',
				url: '<%= editAssetListDisplayContext.getGroupItemSelectorURL() %>',
			});
		});
	}

	window['<portlet:namespace />addRow'] = function (groupId, name, scopeLabel) {
		var data = searchContainer.getData(true);
		if (data.includes(groupId)) {
			return;
		}

		var rowColumns = [];

		rowColumns.push('<span class="text-truncate">' + name + '</span>');
		rowColumns.push(scopeLabel);
		rowColumns.push(
			'<a class="modify-link" data-rowId="' +
				groupId +
				'" href="javascript:;"><%= UnicodeFormatter.toString(removeLinkIcon) %></a>'
		);

		searchContainer.addRow(rowColumns, groupId);

		searchContainer.updateDataStore();

		updateGroupIds();
	};

	function updateGroupIds() {
		var groupIds = document.getElementById('<portlet:namespace />groupIds');

		if (groupIds) {
			var searchContainerData = searchContainer.getData();

			groupIds.setAttribute('value', searchContainerData.split(','));
		}
	}
</aui:script>