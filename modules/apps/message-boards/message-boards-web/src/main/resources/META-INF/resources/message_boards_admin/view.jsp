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

PortletURL portletURL = renderResponse.createRenderURL();

if (categoryId == MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID) {
	portletURL.setParameter("mvcRenderCommandName", "/message_boards/view");
}
else {
	portletURL.setParameter("mvcRenderCommandName", "/message_boards/view_category");
	portletURL.setParameter("mbCategoryId", String.valueOf(categoryId));
}

request.setAttribute("view.jsp-categoryId", categoryId);
request.setAttribute("view.jsp-viewCategory", Boolean.TRUE.toString());
request.setAttribute("view.jsp-portletURL", portletURL);
%>

<portlet:actionURL name="/message_boards/edit_category" var="restoreTrashEntriesURL">
	<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.RESTORE %>" />
</portlet:actionURL>

<liferay-trash:undo
	portletURL="<%= restoreTrashEntriesURL %>"
/>

<aui:nav-bar cssClass="collapse-basic-search" markupView="lexicon">
	<liferay-util:include page="/message_boards_admin/nav.jsp" servletContext="<%= application %>">
		<liferay-util:param name="navItemSelected" value="threads" />
	</liferay-util:include>

	<liferay-portlet:renderURL varImpl="searchURL">
		<portlet:param name="mvcRenderCommandName" value="/message_boards/search" />
	</liferay-portlet:renderURL>

	<aui:nav-bar-search>
		<aui:form action="<%= searchURL %>" name="searchFm">
			<liferay-portlet:renderURLParams varImpl="searchURL" />
			<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
			<aui:input name="breadcrumbsCategoryId" type="hidden" value="<%= categoryId %>" />
			<aui:input name="searchCategoryId" type="hidden" value="<%= categoryId %>" />

			<liferay-ui:input-search markupView="lexicon" />
		</aui:form>
	</aui:nav-bar-search>
</aui:nav-bar>

<%
String displayStyle = ParamUtil.getString(request, "displayStyle");

if (Validator.isNull(displayStyle)) {
	displayStyle = portalPreferences.getValue(MBPortletKeys.MESSAGE_BOARDS, "entries-display-style", "descriptive");
}
else {
	portalPreferences.setValue(MBPortletKeys.MESSAGE_BOARDS, "entries-display-style", displayStyle);

	request.setAttribute(WebKeys.SINGLE_PAGE_APPLICATION_CLEAR_CACHE, Boolean.TRUE);
}

String entriesNavigation = ParamUtil.getString(request, "entriesNavigation", "all");

int entriesTotal = 0;

long groupThreadsUserId = ParamUtil.getLong(request, "groupThreadsUserId");

Calendar calendar = Calendar.getInstance();

int offset = GetterUtil.getInteger(recentPostsDateOffset);

calendar.add(Calendar.DATE, -offset);

SearchContainer searchContainer = new SearchContainer(renderRequest, null, null, "cur1", 0, SearchContainer.DEFAULT_DELTA, portletURL, null, "there-are-no-threads-nor-categories");

searchContainer.setId("mbEntries");
searchContainer.setRowChecker(new EntriesChecker(liferayPortletRequest, liferayPortletResponse));

if (entriesNavigation.equals("all")) {
	entriesTotal = MBCategoryLocalServiceUtil.getCategoriesAndThreadsCount(scopeGroupId, categoryId);
}
else if (entriesNavigation.equals("recent")) {
	entriesTotal = MBThreadServiceUtil.getGroupThreadsCount(scopeGroupId, groupThreadsUserId, calendar.getTime(), WorkflowConstants.STATUS_APPROVED);
}

searchContainer.setTotal(entriesTotal);

int status = WorkflowConstants.STATUS_APPROVED;

if (permissionChecker.isContentReviewer(user.getCompanyId(), scopeGroupId)) {
	status = WorkflowConstants.STATUS_ANY;
}

List entriesResults = null;

if (entriesNavigation.equals("all")) {
	entriesResults = MBCategoryServiceUtil.getCategoriesAndThreads(scopeGroupId, categoryId, status, searchContainer.getStart(), searchContainer.getEnd());
}
else if (entriesNavigation.equals("recent")) {
	entriesResults = MBThreadServiceUtil.getGroupThreads(scopeGroupId, groupThreadsUserId, calendar.getTime(), WorkflowConstants.STATUS_APPROVED, searchContainer.getStart(), searchContainer.getEnd());
}

searchContainer.setResults(entriesResults);
%>

<liferay-frontend:management-bar
	disabled="<%= entriesTotal == 0 %>"
	includeCheckBox="<%= true %>"
	searchContainerId="mbEntries"
>

	<%
	PortletURL displayStyleURL = renderResponse.createRenderURL();

	if (categoryId == MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID) {
		displayStyleURL.setParameter("mvcRenderCommandName", "/message_boards/view");
	}
	else {
		displayStyleURL.setParameter("mvcRenderCommandName", "/message_boards/view_category");
		displayStyleURL.setParameter("categoryId", String.valueOf(categoryId));
	}
	%>

	<liferay-frontend:management-bar-buttons>
		<liferay-frontend:management-bar-display-buttons
			displayViews='<%= new String[] {"descriptive"} %>'
			portletURL="<%= displayStyleURL %>"
			selectedDisplayStyle="<%= displayStyle %>"
		/>
	</liferay-frontend:management-bar-buttons>

	<portlet:renderURL var="viewEntriesHomeURL">
		<portlet:param name="categoryId" value="<%= String.valueOf(MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID) %>" />
	</portlet:renderURL>

	<liferay-frontend:management-bar-filters>

		<%
		PortletURL navigationPortletURL = renderResponse.createRenderURL();

		navigationPortletURL.setParameter("categoryId", String.valueOf(MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID));
		%>

		<liferay-frontend:management-bar-navigation
			navigationKeys='<%= new String[] {"all", "recent"} %>'
			navigationParam="entriesNavigation"
			portletURL="<%= navigationPortletURL %>"
		/>
	</liferay-frontend:management-bar-filters>

	<liferay-frontend:management-bar-action-buttons>

		<%
		String taglibURL = "javascript:" + renderResponse.getNamespace() + "deleteEntries();";
		%>

		<liferay-frontend:management-bar-button href="<%= taglibURL %>" icon='<%= TrashUtil.isTrashEnabled(scopeGroupId) ? "trash" : "times" %>' label='<%= TrashUtil.isTrashEnabled(scopeGroupId) ? "recycle-bin" : "delete" %>' />

		<%
		taglibURL = "javascript:" + renderResponse.getNamespace() + "lockEntries();";
		%>

		<liferay-frontend:management-bar-button href="<%= taglibURL %>" icon="lock" label="lock" />

		<%
		taglibURL = "javascript:" + renderResponse.getNamespace() + "unlockEntries();";
		%>

		<liferay-frontend:management-bar-button href="<%= taglibURL %>" icon="unlock" label="unlock" />
	</liferay-frontend:management-bar-action-buttons>
</liferay-frontend:management-bar>

<%
request.setAttribute("view.jsp-displayStyle", displayStyle);
request.setAttribute("view.jsp-entriesSearchContainer", searchContainer);
%>

<liferay-util:include page="/message_boards_admin/view_entries.jsp" servletContext="<%= application %>" />

<liferay-util:include page="/message_boards_admin/add_button.jsp" servletContext="<%= application %>" />

<aui:script>
	function <portlet:namespace />deleteEntries() {
		if (<%= TrashUtil.isTrashEnabled(scopeGroupId) %> || confirm('<%= UnicodeLanguageUtil.get(request, TrashUtil.isTrashEnabled(scopeGroupId) ? "are-you-sure-you-want-to-move-the-selected-entries-to-the-recycle-bin" : "are-you-sure-you-want-to-delete-the-selected-entries") %>')) {
			var form = AUI.$(document.<portlet:namespace />fm);

			form.attr('method', 'post');
			form.fm('<%= Constants.CMD %>').val('<%= TrashUtil.isTrashEnabled(scopeGroupId) ? Constants.MOVE_TO_TRASH : Constants.DELETE %>');

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