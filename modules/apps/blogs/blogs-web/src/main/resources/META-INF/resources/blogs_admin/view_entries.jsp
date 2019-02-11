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

<%@ include file="/blogs_admin/init.jsp" %>

<%
BlogEntriesDisplayContext blogEntriesDisplayContext = new BlogEntriesDisplayContext(liferayPortletRequest, liferayPortletResponse);

String displayStyle = blogEntriesDisplayContext.getDisplayStyle();
SearchContainer entriesSearchContainer = blogEntriesDisplayContext.getSearchContainer();
PortletURL portletURL = blogEntriesDisplayContext.getPortletURL();

BlogEntriesManagementToolbarDisplayContext blogEntriesManagementToolbarDisplayContext = new BlogEntriesManagementToolbarDisplayContext(liferayPortletRequest, liferayPortletResponse, request, entriesSearchContainer, trashHelper, displayStyle);
%>

<clay:management-toolbar
	displayContext="<%= blogEntriesManagementToolbarDisplayContext %>"
/>

<portlet:actionURL name="/blogs/edit_entry" var="restoreTrashEntriesURL">
	<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.RESTORE %>" />
</portlet:actionURL>

<liferay-trash:undo
	portletURL="<%= restoreTrashEntriesURL %>"
/>

<div class="container-fluid container-fluid-max-xl main-content-body">
	<aui:form action="<%= portletURL.toString() %>" method="get" name="fm">
		<aui:input name="<%= Constants.CMD %>" type="hidden" />
		<aui:input name="redirect" type="hidden" value="<%= portletURL.toString() %>" />
		<aui:input name="deleteEntryIds" type="hidden" />

		<liferay-asset:categorization-filter
			assetType="entries"
			portletURL="<%= portletURL %>"
		/>

		<liferay-ui:search-container
			id="blogEntries"
			searchContainer="<%= entriesSearchContainer %>"
		>
			<liferay-ui:search-container-row
				className="com.liferay.blogs.model.BlogsEntry"
				escapedModel="<%= true %>"
				keyProperty="entryId"
				modelVar="entry"
			>
				<liferay-portlet:renderURL varImpl="rowURL">
					<portlet:param name="mvcRenderCommandName" value="/blogs/edit_entry" />
					<portlet:param name="redirect" value="<%= entriesSearchContainer.getIteratorURL().toString() %>" />
					<portlet:param name="entryId" value="<%= String.valueOf(entry.getEntryId()) %>" />
				</liferay-portlet:renderURL>

				<%
				Map<String, Object> rowData = new HashMap<>();

				rowData.put("actions", String.join(StringPool.COMMA, blogEntriesDisplayContext.getAvailableActionDropdownItems(entry)));

				row.setData(rowData);
				%>

				<%@ include file="/blogs_admin/entry_search_columns.jspf" %>
			</liferay-ui:search-container-row>

			<liferay-ui:search-iterator
				displayStyle="<%= displayStyle %>"
				markupView="lexicon"
			/>
		</liferay-ui:search-container>
	</aui:form>
</div>

<aui:script>
	var deleteEntries = function() {
		if (<%= trashHelper.isTrashEnabled(scopeGroupId) %> || confirm('<liferay-ui:message key="are-you-sure-you-want-to-delete-the-selected-entries" />')) {
			var form = document.getElementById('<portlet:namespace />fm');

			if (form) {
				form.setAttribute('method', 'post');

				var cmd = form.querySelector('#<portlet:namespace /><%= Constants.CMD %>');

				if (cmd) {
					cmd.setAttribute('value', '<%= trashHelper.isTrashEnabled(scopeGroupId) ? Constants.MOVE_TO_TRASH : Constants.DELETE %>');
				}

				var deleteEntryIds = form.querySelector('#<portlet:namespace />deleteEntryIds');

				if (deleteEntryIds) {
					deleteEntryIds.setAttribute('value', Liferay.Util.listCheckedExcept(form, '<portlet:namespace />allRowIds'));
				}

				submitForm(form, '<portlet:actionURL name="/blogs/edit_entry" />');
			}
		}
	};

	var ACTIONS = {
		'deleteEntries': deleteEntries
	};

	Liferay.componentReady('blogEntriesManagementToolbar').then(
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