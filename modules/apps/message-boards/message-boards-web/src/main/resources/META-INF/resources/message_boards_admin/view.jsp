<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/message_boards/init.jsp" %>

<%
String navigation = "threads";

MBCategory category = (MBCategory)request.getAttribute(WebKeys.MESSAGE_BOARDS_CATEGORY);

long categoryId = MBUtil.getCategoryId(request, category);

MBEntriesManagementToolbarDisplayContext mbEntriesManagementToolbarDisplayContext = new MBEntriesManagementToolbarDisplayContext(request, liferayPortletRequest, liferayPortletResponse, currentURLObj, trashHelper);

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

<%@ include file="/message_boards/nav.jspf" %>

<%
MBAdminListDisplayContext mbAdminListDisplayContext = MBDisplayContextUtil.getMBAdminListDisplayContext(request, response, categoryId);

PortletURL portletURL = mbEntriesManagementToolbarDisplayContext.getPortletURL();

SearchContainer entriesSearchContainer = new SearchContainer(renderRequest, null, null, "cur1", 0, mbAdminListDisplayContext.getEntriesDelta(), portletURL, null, "there-are-no-threads-or-categories");

mbAdminListDisplayContext.setEntriesDelta(entriesSearchContainer);

entriesSearchContainer.setId("mbEntries");

mbEntriesManagementToolbarDisplayContext.populateOrder(entriesSearchContainer);

EntriesChecker entriesChecker = new EntriesChecker(liferayPortletRequest, liferayPortletResponse);

entriesSearchContainer.setRowChecker(entriesChecker);

if (categoryId == 0) {
	entriesChecker.setRememberCheckBoxStateURLRegex("mvcRenderCommandName=/message_boards/view(&.|$)");
}
else {
	entriesChecker.setRememberCheckBoxStateURLRegex("mbCategoryId=" + categoryId);
}

mbAdminListDisplayContext.populateResultsAndTotal(entriesSearchContainer);

String entriesNavigation = ParamUtil.getString(request, "entriesNavigation", "all");
%>

<clay:management-toolbar
	actionDropdownItems="<%= mbEntriesManagementToolbarDisplayContext.getActionDropdownItems() %>"
	additionalProps="<%= mbEntriesManagementToolbarDisplayContext.getAdditionalProps() %>"
	clearResultsURL="<%= mbEntriesManagementToolbarDisplayContext.getSearchActionURL() %>"
	creationMenu="<%= mbEntriesManagementToolbarDisplayContext.getCreationMenu() %>"
	disabled='<%= (entriesSearchContainer.getTotal() == 0) && (categoryId == MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID) && entriesNavigation.equals("all") %>'
	filterDropdownItems="<%= mbEntriesManagementToolbarDisplayContext.getFilterDropdownItems() %>"
	filterLabelItems="<%= mbEntriesManagementToolbarDisplayContext.getFilterLabelItems() %>"
	itemsTotal="<%= entriesSearchContainer.getTotal() %>"
	orderDropdownItems="<%= mbEntriesManagementToolbarDisplayContext.getOrderByDropdownItems() %>"
	propsTransformer="message_boards_admin/js/MBEntriesManagementToolbarPropsTransformer"
	searchActionURL="<%= mbEntriesManagementToolbarDisplayContext.getSearchActionURL() %>"
	searchContainerId="mbEntries"
	searchFormName="searchFm"
	showInfoButton="<%= false %>"
	sortingOrder="<%= mbEntriesManagementToolbarDisplayContext.getOrderByType() %>"
	sortingURL="<%= String.valueOf(mbEntriesManagementToolbarDisplayContext.getSortingURL()) %>"
/>

<%@ include file="/message_boards_admin/view_entries.jspf" %>

<%
if (category != null) {
	PortalUtil.setPageSubtitle(category.getName(), request);
	PortalUtil.setPageDescription(category.getDescription(), request);
}
%>