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
String searchContainerId = ParamUtil.getString(request, "searchContainerId");
%>

<liferay-frontend:management-bar
	disabled="<%= journalDisplayContext.isDisabledManagementBar() %>"
	includeCheckBox="<%= !user.isDefaultUser() && journalDisplayContext.isShowEditActions() %>"
	searchContainerId="<%= searchContainerId %>"
>
	<liferay-frontend:management-bar-buttons>
		<c:if test="<%= journalDisplayContext.isShowInfoPanel() %>">
			<liferay-frontend:management-bar-sidenav-toggler-button
				icon="info-circle"
				label="info"
			/>
		</c:if>

		<liferay-frontend:management-bar-display-buttons
			displayViews="<%= journalDisplayContext.getDisplayViews() %>"
			portletURL="<%= journalDisplayContext.getPortletURL() %>"
			selectedDisplayStyle="<%= journalDisplayContext.getDisplayStyle() %>"
		/>
	</liferay-frontend:management-bar-buttons>

	<%
	String label = null;

	if (journalDisplayContext.isNavigationStructure()) {
		label = LanguageUtil.get(request, "structure") + StringPool.COLON + StringPool.SPACE + HtmlUtil.escape(journalDisplayContext.getDDMStructureName());
	}
	%>

	<liferay-frontend:management-bar-filters>
		<liferay-frontend:management-bar-navigation
			label="<%= label %>"
		>
			<portlet:renderURL var="viewArticlesHomeURL">
				<portlet:param name="folderId" value="<%= String.valueOf(JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID) %>" />
				<portlet:param name="showEditActions" value="<%= String.valueOf(journalDisplayContext.isShowEditActions()) %>" />
			</portlet:renderURL>

			<liferay-frontend:management-bar-filter-item active="<%= journalDisplayContext.isNavigationHome() %>" label="all" url="<%= viewArticlesHomeURL.toString() %>" />

			<portlet:renderURL var="viewRecentArticlesURL">
				<portlet:param name="navigation" value="recent" />
				<portlet:param name="folderId" value="<%= String.valueOf(JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID) %>" />
				<portlet:param name="showEditActions" value="<%= String.valueOf(journalDisplayContext.isShowEditActions()) %>" />
			</portlet:renderURL>

			<liferay-frontend:management-bar-filter-item active="<%= journalDisplayContext.isNavigationRecent() %>" label="recent" url="<%= viewRecentArticlesURL.toString() %>" />

			<portlet:renderURL var="viewMyArticlesURL">
				<portlet:param name="navigation" value="mine" />
				<portlet:param name="folderId" value="<%= String.valueOf(JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID) %>" />
				<portlet:param name="showEditActions" value="<%= String.valueOf(journalDisplayContext.isShowEditActions()) %>" />
			</portlet:renderURL>

			<liferay-frontend:management-bar-filter-item active="<%= journalDisplayContext.isNavigationMine() %>" label="mine" url="<%= viewMyArticlesURL.toString() %>" />

			<liferay-frontend:management-bar-filter-item active="<%= journalDisplayContext.isNavigationStructure() %>" id="structures" label="structures" url="javascript:;" />
		</liferay-frontend:management-bar-navigation>

		<liferay-frontend:management-bar-filter
			label="status"
			managementBarFilterItems="<%= journalDisplayContext.getManagementBarStatusFilterItems() %>"
			value="<%= journalDisplayContext.getManagementBarStatusFilterValue() %>"
		/>

		<liferay-frontend:management-bar-sort
			orderByCol="<%= journalDisplayContext.getOrderByCol() %>"
			orderByType="<%= journalDisplayContext.getOrderByType() %>"
			orderColumns='<%= new String[] {"display-date", "modified-date"} %>'
			portletURL="<%= journalDisplayContext.getPortletURL() %>"
		/>
	</liferay-frontend:management-bar-filters>

	<liferay-frontend:management-bar-action-buttons>
		<c:if test="<%= journalDisplayContext.isShowInfoPanel() %>">
			<liferay-frontend:management-bar-sidenav-toggler-button
				icon="info-circle"
				label="info"
			/>
		</c:if>

		<liferay-frontend:management-bar-button href='<%= "javascript:" + renderResponse.getNamespace() + "deleteEntries();" %>' icon='<%= TrashUtil.isTrashEnabled(scopeGroupId) ? "trash" : "times" %>' label='<%= TrashUtil.isTrashEnabled(scopeGroupId) ? "recycle-bin" : "delete" %>' />

		<%
		String taglibURL = "javascript:Liferay.fire('" + renderResponse.getNamespace() + "editEntry', {action: 'expireEntries'}); void(0);";
		%>

		<liferay-frontend:management-bar-button href="<%= taglibURL %>" icon="time" label="expire" />

		<%
		taglibURL = "javascript:Liferay.fire('" + renderResponse.getNamespace() + "editEntry', {action: 'moveEntries'}); void(0);";
		%>

		<liferay-frontend:management-bar-button href="<%= taglibURL %>" icon="change" label="move" />
	</liferay-frontend:management-bar-action-buttons>
</liferay-frontend:management-bar>

<aui:script>
	function <portlet:namespace />deleteEntries() {
		if (<%= TrashUtil.isTrashEnabled(scopeGroupId) %> || confirm(' <%= UnicodeLanguageUtil.get(request, "are-you-sure-you-want-to-delete-the-selected-entries") %>')) {
			Liferay.fire(
				'<%= renderResponse.getNamespace() %>editEntry',
				{
					action: '<%= TrashUtil.isTrashEnabled(scopeGroupId) ? "moveEntriesToTrash" : "deleteEntries" %>'
				}
			);
		}
	}
</aui:script>

<aui:script>
	<portlet:renderURL var="viewDDMStructureArticlesURL">
		<portlet:param name="navigation" value="structure" />
		<portlet:param name="folderId" value="<%= String.valueOf(JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID) %>" />
		<portlet:param name="showEditActions" value="<%= String.valueOf(journalDisplayContext.isShowEditActions()) %>" />
	</portlet:renderURL>

	$('#<portlet:namespace />structures').on(
		'click',
		function(event) {
			Liferay.Util.openDDMPortlet(
				{
					basePortletURL: '<%= PortletURLFactoryUtil.create(request, PortletProviderUtil.getPortletId(DDMStructure.class.getName(), PortletProvider.Action.VIEW), themeDisplay.getPlid(), PortletRequest.RENDER_PHASE) %>',
					classPK: <%= journalDisplayContext.getDDMStructurePrimaryKey() %>,
					dialog: {
						destroyOnHide: true
					},
					eventName: '<portlet:namespace />selectStructure',
					groupId: <%= themeDisplay.getScopeGroupId() %>,
					mvcPath: '/select_structure.jsp',
					navigationStartsOn: '<%= DDMNavigationHelper.SELECT_STRUCTURE %>',
					refererPortletName: '<%= JournalPortletKeys.JOURNAL + ".filterStructure" %>',

					<%
					Portlet portlet = PortletLocalServiceUtil.getPortletById(portletDisplay.getId());
					%>

					refererWebDAVToken: '<%= WebDAVUtil.getStorageToken(portlet) %>',

					showAncestorScopes: true,
					title: '<%= UnicodeLanguageUtil.get(request, "structures") %>'
				},
				function(event) {
					var uri = '<%= viewDDMStructureArticlesURL %>';

					uri = Liferay.Util.addParams('<portlet:namespace />ddmStructureKey=' + event.ddmstructurekey, uri);

					location.href = uri;
				}
			);
		}
	);
</aui:script>