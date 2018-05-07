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

<%@ include file="/message_boards/init.jsp" %>

<%
MBCategory category = (MBCategory)request.getAttribute(WebKeys.MESSAGE_BOARDS_CATEGORY);

long categoryId = MBUtil.getCategoryId(request, category);

MBEntriesManagementToolbarDisplayContext mbEntriesManagementToolbarDisplayContext = new MBEntriesManagementToolbarDisplayContext(liferayPortletRequest, liferayPortletResponse, currentURLObj, trashHelper);

request.setAttribute("view.jsp-categoryId", categoryId);
request.setAttribute("view.jsp-categorySubscriptionClassPKs", MBSubscriptionUtil.getCategorySubscriptionClassPKs(user.getUserId()));
request.setAttribute("view.jsp-threadSubscriptionClassPKs", MBSubscriptionUtil.getThreadSubscriptionClassPKs(user.getUserId()));
request.setAttribute("view.jsp-viewCategory", Boolean.TRUE.toString());
%>

<portlet:actionURL name="/message_boards/edit_category" var="restoreTrashEntriesURL">
	<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.RESTORE %>" />
</portlet:actionURL>

<liferay-trash:undo
	portletURL="<%= restoreTrashEntriesURL %>"
/>

<liferay-util:include page="/message_boards_admin/nav.jsp" servletContext="<%= application %>">
	<liferay-util:param name="navItemSelected" value="threads" />
</liferay-util:include>

<%
MBAdminListDisplayContext mbAdminListDisplayContext = mbDisplayContextProvider.getMbAdminListDisplayContext(request, response, categoryId);

int entriesDelta = mbAdminListDisplayContext.getEntriesDelta();

PortletURL portletURL = mbEntriesManagementToolbarDisplayContext.getPortletURL();

SearchContainer searchContainer = new SearchContainer(renderRequest, null, null, "cur1", 0, entriesDelta, portletURL, null, "there-are-no-threads-or-categories");

mbAdminListDisplayContext.setEntriesDelta(searchContainer);

searchContainer.setId("mbEntries");

mbEntriesManagementToolbarDisplayContext.populateOrder(searchContainer);

EntriesChecker entriesChecker = new EntriesChecker(liferayPortletRequest, liferayPortletResponse);

searchContainer.setRowChecker(entriesChecker);

if (categoryId == 0) {
	entriesChecker.setRememberCheckBoxStateURLRegex("mvcRenderCommandName=/message_boards/view(&.|$)");
}
else {
	entriesChecker.setRememberCheckBoxStateURLRegex("mbCategoryId=" + categoryId);
}

mbAdminListDisplayContext.populateResultsAndTotal(searchContainer);

String entriesNavigation = ParamUtil.getString(request, "entriesNavigation", "all");
%>

<clay:management-toolbar
	actionItems="<%= mbEntriesManagementToolbarDisplayContext.getActionDropdownItems() %>"
	clearResultsURL="<%= mbEntriesManagementToolbarDisplayContext.getSearchActionURL() %>"
	creationMenu="<%= mbEntriesManagementToolbarDisplayContext.getCreationMenu() %>"
	disabled='<%= (searchContainer.getTotal() == 0) && (categoryId == MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID) && entriesNavigation.equals("all") %>'
	filterItems="<%= mbEntriesManagementToolbarDisplayContext.getFilterDropdownItems() %>"
	searchActionURL="<%= mbEntriesManagementToolbarDisplayContext.getSearchActionURL() %>"
	searchContainerId="mbEntries"
	searchFormName="searchFm"
	showInfoButton="<%= false %>"
	sortingOrder="<%= mbEntriesManagementToolbarDisplayContext.getOrderByType() %>"
	sortingURL="<%= String.valueOf(mbEntriesManagementToolbarDisplayContext.getSortingURL()) %>"
	totalItems="<%= searchContainer.getTotal() %>"
	viewTypes="<%= mbEntriesManagementToolbarDisplayContext.getViewTypes() %>"
/>

<%
request.setAttribute("view.jsp-entriesSearchContainer", searchContainer);
%>

<liferay-util:include page="/message_boards_admin/view_entries.jsp" servletContext="<%= application %>" />

<%
if (category != null) {
	PortalUtil.setPageSubtitle(category.getName(), request);
	PortalUtil.setPageDescription(category.getDescription(), request);
}
%>

<aui:script>
	function <portlet:namespace />deleteEntries() {
		if (<%= trashHelper.isTrashEnabled(scopeGroupId) %> || confirm('<%= UnicodeLanguageUtil.get(request, trashHelper.isTrashEnabled(scopeGroupId) ? "are-you-sure-you-want-to-move-the-selected-entries-to-the-recycle-bin" : "are-you-sure-you-want-to-delete-the-selected-entries") %>')) {
			var form = AUI.$(document.<portlet:namespace />fm);

			form.attr('method', 'post');
			form.fm('<%= Constants.CMD %>').val('<%= trashHelper.isTrashEnabled(scopeGroupId) ? Constants.MOVE_TO_TRASH : Constants.DELETE %>');

			submitForm(form, '<portlet:actionURL name="/message_boards/edit_entry" />');
		}
	}

	function <portlet:namespace />lockEntries() {
		var form = AUI.$(document.<portlet:namespace />fm);

		form.attr('method', 'post');
		form.fm('<%= Constants.CMD %>').val('<%= Constants.LOCK %>');

		submitForm(form, '<portlet:actionURL name="/message_boards/edit_entry" />');
	}

	function <portlet:namespace />unlockEntries() {
		var form = AUI.$(document.<portlet:namespace />fm);

		form.attr('method', 'post');
		form.fm('<%= Constants.CMD %>').val('<%= Constants.UNLOCK %>');

		submitForm(form, '<portlet:actionURL name="/message_boards/edit_entry" />');
	}
</aui:script>