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

String keywords = ParamUtil.getString(request, "keywords");

if (Validator.isNotNull(keywords)) {
	portletURL.setParameter("keywords", keywords);
}

String orderByCol = ParamUtil.getString(request, "orderByCol");
String orderByType = ParamUtil.getString(request, "orderByType");

if (Validator.isNotNull(orderByCol) && Validator.isNotNull(orderByType)) {
	portalPreferences.setValue(MBPortletKeys.MESSAGE_BOARDS_ADMIN, "order-by-col", orderByCol);
	portalPreferences.setValue(MBPortletKeys.MESSAGE_BOARDS_ADMIN, "order-by-type", orderByType);
}
else {
	orderByCol = portalPreferences.getValue(MBPortletKeys.MESSAGE_BOARDS_ADMIN, "order-by-col", "modified-date");
	orderByType = portalPreferences.getValue(MBPortletKeys.MESSAGE_BOARDS_ADMIN, "order-by-type", "desc");
}

boolean orderByAsc = false;

if (orderByType.equals("asc")) {
	orderByAsc = true;
}

OrderByComparator orderByComparator = null;

if (orderByCol.equals("modified-date")) {
	orderByComparator = new ThreadModifiedDateComparator(orderByAsc);

}
else if (orderByCol.equals("title")) {
	orderByComparator = new CategoryTitleComparator(orderByAsc);
}

request.setAttribute("view.jsp-categoryId", categoryId);
request.setAttribute("view.jsp-categorySubscriptionClassPKs", MBSubscriptionUtil.getCategorySubscriptionClassPKs(user.getUserId()));
request.setAttribute("view.jsp-portletURL", portletURL);
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

SearchContainer searchContainer = new SearchContainer(renderRequest, null, null, "cur1", 0, entriesDelta, portletURL, null, "there-are-no-threads-or-categories");

mbAdminListDisplayContext.setEntriesDelta(searchContainer);

searchContainer.setId("mbEntries");
searchContainer.setOrderByCol(orderByCol);
searchContainer.setOrderByComparator(orderByComparator);
searchContainer.setOrderByType(orderByType);

EntriesChecker entriesChecker = new EntriesChecker(liferayPortletRequest, liferayPortletResponse);

searchContainer.setRowChecker(entriesChecker);

if (categoryId == 0) {
	entriesChecker.setRememberCheckBoxStateURLRegex("mvcRenderCommandName=/message_boards/view(&.|$)");
}
else {
	entriesChecker.setRememberCheckBoxStateURLRegex("mbCategoryId=" + categoryId);
}

mbAdminListDisplayContext.populateResultsAndTotal(searchContainer);
%>

<liferay-frontend:management-bar
	disabled="<%= searchContainer.getTotal() == 0 %>"
	includeCheckBox="<%= true %>"
	searchContainerId="mbEntries"
>
	<liferay-frontend:management-bar-buttons>
		<liferay-frontend:management-bar-display-buttons
			displayViews='<%= new String[] {"descriptive"} %>'
			portletURL="<%= searchContainer.getIteratorURL() %>"
			selectedDisplayStyle="descriptive"
		/>

		<c:if test="<%= !mbAdminListDisplayContext.isShowSearch() %>">
			<liferay-util:include page="/message_boards_admin/add_button.jsp" servletContext="<%= application %>" />
		</c:if>
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

		<liferay-frontend:management-bar-sort
			orderByCol="<%= orderByCol %>"
			orderByType="<%= orderByType %>"
			orderColumns='<%= new String[] {"modified-date", "title"} %>'
			portletURL="<%= portletURL %>"
		/>

		<liferay-portlet:renderURL varImpl="searchURL">
			<portlet:param name="mvcRenderCommandName" value="/message_boards_admin/search" />
		</liferay-portlet:renderURL>

		<li>
			<aui:form action="<%= searchURL %>" name="searchFm">
				<liferay-portlet:renderURLParams varImpl="searchURL" />
				<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
				<aui:input name="breadcrumbsCategoryId" type="hidden" value="<%= categoryId %>" />
				<aui:input name="searchCategoryId" type="hidden" value="<%= categoryId %>" />

				<liferay-ui:input-search
					markupView="lexicon"
				/>
			</aui:form>
		</li>
	</liferay-frontend:management-bar-filters>

	<liferay-frontend:management-bar-action-buttons>
		<liferay-frontend:management-bar-button
			href='<%= "javascript:" + renderResponse.getNamespace() + "deleteEntries();" %>'
			icon='<%= trashHelper.isTrashEnabled(scopeGroupId) ? "trash" : "times" %>'
			label='<%= trashHelper.isTrashEnabled(scopeGroupId) ? "recycle-bin" : "delete" %>'
		/>

		<liferay-frontend:management-bar-button
			href='<%= "javascript:" + renderResponse.getNamespace() + "lockEntries();" %>'
			icon="lock"
			label="lock"
		/>

		<liferay-frontend:management-bar-button
			href='<%= "javascript:" + renderResponse.getNamespace() + "unlockEntries();" %>'
			icon="unlock"
			label="unlock"
		/>
	</liferay-frontend:management-bar-action-buttons>
</liferay-frontend:management-bar>

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