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

<clay:management-toolbar
	actionItems="<%= journalDisplayContext.getActionItemsDropdownItemList() %>"
	clearResultsURL="<%= journalDisplayContext.getClearResultsURL() %>"
	componentId="journalWebManagementToolbar"
	creationMenu="<%= journalDisplayContext.getCreationMenu() %>"
	disabled="<%= journalDisplayContext.isDisabled() %>"
	filterItems="<%= journalDisplayContext.getFilterItemsDropdownItemList() %>"
	infoPanelId="infoPanelId"
	namespace="<%= renderResponse.getNamespace() %>"
	searchActionURL="<%= journalDisplayContext.getSearchActionURL() %>"
	searchContainerId="<%= searchContainerId %>"
	searchFormName="fm1"
	showInfoButton="<%= journalDisplayContext.isShowInfoButton() %>"
	showSearch="<%= journalDisplayContext.isShowSearch() %>"
	sortingOrder="<%= journalDisplayContext.getOrderByType() %>"
	sortingURL="<%= journalDisplayContext.getSortingURL() %>"
	totalItems="<%= journalDisplayContext.getTotalItems() %>"
	viewTypes="<%= journalDisplayContext.getViewTypesItemList() %>"
/>

<aui:script>
	function <portlet:namespace />deleteEntries() {
		if (<%= trashHelper.isTrashEnabled(scopeGroupId) %> || confirm(' <%= UnicodeLanguageUtil.get(request, "are-you-sure-you-want-to-delete-the-selected-entries") %>')) {
			Liferay.fire(
				'<%= renderResponse.getNamespace() %>editEntry',
				{
					action: '<%= trashHelper.isTrashEnabled(scopeGroupId) ? "moveEntriesToTrash" : "deleteEntries" %>'
				}
			);
		}
	}

	<portlet:renderURL var="viewDDMStructureArticlesURL">
		<portlet:param name="navigation" value="structure" />
		<portlet:param name="folderId" value="<%= String.valueOf(JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID) %>" />
		<portlet:param name="showEditActions" value="<%= String.valueOf(journalDisplayContext.isShowEditActions()) %>" />
	</portlet:renderURL>

	function <portlet:namespace />openStructuresSelector() {
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

				refererWebDAVToken: '<%= HtmlUtil.escapeJS(WebDAVUtil.getStorageToken(portlet)) %>',

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

	function <portlet:namespace />openViewMoreStructuresSelector() {
		Liferay.Util.openWindow(
			{
				dialog: {
					destroyOnHide: true,
					modal: true
				},
				id: '<portlet:namespace />selectAddMenuItem',
				title: '<liferay-ui:message key="more" />',

				<portlet:renderURL var="viewMoreURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
					<portlet:param name="mvcPath" value="/view_more_menu_items.jsp" />
					<portlet:param name="folderId" value="<%= String.valueOf(journalDisplayContext.getFolderId()) %>" />
					<portlet:param name="eventName" value='<%= renderResponse.getNamespace() + "selectAddMenuItem" %>' />
				</portlet:renderURL>

				uri: '<%= viewMoreURL %>'
			}
		);
	}
</aui:script>