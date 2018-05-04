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
String entriesNavigation = ParamUtil.getString(request, "entriesNavigation");

int delta = ParamUtil.getInteger(request, SearchContainer.DEFAULT_DELTA_PARAM);
String orderByCol = ParamUtil.getString(request, "orderByCol", "title");
String orderByType = ParamUtil.getString(request, "orderByType", "asc");

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("mvcRenderCommandName", "/blogs/view");

if (delta > 0) {
	portletURL.setParameter("delta", String.valueOf(delta));
}

portletURL.setParameter("orderBycol", orderByCol);
portletURL.setParameter("orderByType", orderByType);

portletURL.setParameter("entriesNavigation", entriesNavigation);

SearchContainer entriesSearchContainer = new SearchContainer(renderRequest, PortletURLUtil.clone(portletURL, liferayPortletResponse), null, "no-entries-were-found");

entriesSearchContainer.setOrderByComparator(BlogsUtil.getOrderByComparator(orderByCol, orderByType));

BlogEntriesDisplayContext blogEntriesDisplayContext = new BlogEntriesDisplayContext(liferayPortletRequest);

blogEntriesDisplayContext.populateResults(entriesSearchContainer);

BlogEntriesManagementToolbarDisplayContext blogEntriesManagementToolbarDisplayContext = new BlogEntriesManagementToolbarDisplayContext(liferayPortletRequest, liferayPortletResponse, currentURLObj, trashHelper);

String displayStyle = blogEntriesManagementToolbarDisplayContext.getDisplayStyle();
%>

<clay:management-toolbar
	actionDropdownItems="<%= blogEntriesManagementToolbarDisplayContext.getActionDropdownItems() %>"
	clearResultsURL="<%= blogEntriesManagementToolbarDisplayContext.getSearchActionURL() %>"
	creationMenu="<%= blogEntriesManagementToolbarDisplayContext.getCreationMenu() %>"
	disabled="<%= entriesSearchContainer.getTotal() <= 0 %>"
	filterDropdownItems="<%= blogEntriesManagementToolbarDisplayContext.getFilterDropdownItems() %>"
	itemsTotal="<%= entriesSearchContainer.getTotal() %>"
	searchActionURL="<%= blogEntriesManagementToolbarDisplayContext.getSearchActionURL() %>"
	searchContainerId="blogEntries"
	searchFormName="searchFm"
	showInfoButton="<%= false %>"
	sortingOrder="<%= blogEntriesManagementToolbarDisplayContext.getOrderByType() %>"
	sortingURL="<%= String.valueOf(blogEntriesManagementToolbarDisplayContext.getSortingURL()) %>"
	viewTypeItems="<%= blogEntriesManagementToolbarDisplayContext.getViewTypes() %>"
/>

<portlet:actionURL name="/blogs/edit_entry" var="restoreTrashEntriesURL">
	<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.RESTORE %>" />
</portlet:actionURL>

<liferay-trash:undo
	portletURL="<%= restoreTrashEntriesURL %>"
/>

<div class="container-fluid-1280 main-content-body">
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
			rowChecker="<%= new EmptyOnClickRowChecker(renderResponse) %>"
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
	function <portlet:namespace />deleteEntries() {
		if (<%= trashHelper.isTrashEnabled(scopeGroupId) %> || confirm('<%= UnicodeLanguageUtil.get(request, "are-you-sure-you-want-to-delete-the-selected-entries") %>')) {
			var form = AUI.$(document.<portlet:namespace />fm);

			form.attr('method', 'post');
			form.fm('<%= Constants.CMD %>').val('<%= trashHelper.isTrashEnabled(scopeGroupId) ? Constants.MOVE_TO_TRASH : Constants.DELETE %>');
			form.fm('deleteEntryIds').val(Liferay.Util.listCheckedExcept(form, '<portlet:namespace />allRowIds'));

			submitForm(form, '<portlet:actionURL name="/blogs/edit_entry" />');
		}
	}
</aui:script>