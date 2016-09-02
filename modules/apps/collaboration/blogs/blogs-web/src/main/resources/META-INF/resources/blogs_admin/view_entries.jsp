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

long assetCategoryId = ParamUtil.getLong(request, "categoryId");
String assetTagName = ParamUtil.getString(request, "tag");

String displayStyle = ParamUtil.getString(request, "displayStyle");

if (Validator.isNull(displayStyle)) {
	displayStyle = portalPreferences.getValue(BlogsPortletKeys.BLOGS_ADMIN, "entries-display-style", "icon");
}
else {
	portalPreferences.setValue(BlogsPortletKeys.BLOGS_ADMIN, "entries-display-style", displayStyle);

	request.setAttribute(WebKeys.SINGLE_PAGE_APPLICATION_CLEAR_CACHE, Boolean.TRUE);
}

int cur = ParamUtil.getInteger(request, SearchContainer.DEFAULT_CUR_PARAM);
int delta = ParamUtil.getInteger(request, SearchContainer.DEFAULT_DELTA_PARAM);
String orderByCol = ParamUtil.getString(request, "orderByCol", "title");
String orderByType = ParamUtil.getString(request, "orderByType", "asc");

PortletURL navigationPortletURL = renderResponse.createRenderURL();

navigationPortletURL.setParameter("mvcRenderCommandName", "/blogs/view");

if (delta > 0) {
	navigationPortletURL.setParameter("delta", String.valueOf(delta));
}

PortletURL sortURL = PortletURLUtil.clone(navigationPortletURL, liferayPortletResponse);

sortURL.setParameter("entriesNavigation", entriesNavigation);

navigationPortletURL.setParameter("orderBycol", orderByCol);
navigationPortletURL.setParameter("orderByType", orderByType);

PortletURL portletURL = PortletURLUtil.clone(navigationPortletURL, liferayPortletResponse);

portletURL.setParameter("entriesNavigation", entriesNavigation);

PortletURL displayStyleURL = PortletURLUtil.clone(portletURL, liferayPortletResponse);

if (cur > 0) {
	displayStyleURL.setParameter("cur", String.valueOf(cur));
}

String keywords = ParamUtil.getString(request, "keywords");
%>

<%
int entriesTotal = 0;
List entriesResults = null;

SearchContainer entriesSearchContainer = new SearchContainer(renderRequest, PortletURLUtil.clone(portletURL, liferayPortletResponse), null, "no-entries-were-found");

if ((assetCategoryId != 0) || Validator.isNotNull(assetTagName)) {
	SearchContainerResults<AssetEntry> searchContainerResults = BlogsUtil.getSearchContainerResults(entriesSearchContainer);

	entriesSearchContainer.setTotal(searchContainerResults.getTotal());

	List<AssetEntry> assetEntries = searchContainerResults.getResults();

	for (AssetEntry assetEntry : assetEntries) {
		entriesResults.add(BlogsEntryLocalServiceUtil.getEntry(assetEntry.getClassPK()));
	}
}
else if (Validator.isNull(keywords)) {
	if (entriesNavigation.equals("mine")) {
		entriesTotal = BlogsEntryServiceUtil.getGroupUserEntriesCount(scopeGroupId, themeDisplay.getUserId(), WorkflowConstants.STATUS_ANY);
	}
	else {
		entriesTotal = BlogsEntryServiceUtil.getGroupEntriesCount(scopeGroupId, WorkflowConstants.STATUS_ANY);
	}

	entriesSearchContainer.setTotal(entriesTotal);

	if (entriesNavigation.equals("mine")) {
		entriesResults = BlogsEntryServiceUtil.getGroupUserEntries(scopeGroupId, themeDisplay.getUserId(), WorkflowConstants.STATUS_ANY, entriesSearchContainer.getStart(), entriesSearchContainer.getEnd(), entriesSearchContainer.getOrderByComparator());
	}
	else {
		entriesResults = BlogsEntryServiceUtil.getGroupEntries(scopeGroupId, WorkflowConstants.STATUS_ANY, entriesSearchContainer.getStart(), entriesSearchContainer.getEnd(), entriesSearchContainer.getOrderByComparator());
	}
}
else {
	Indexer indexer = IndexerRegistryUtil.getIndexer(BlogsEntry.class);

	SearchContext searchContext = SearchContextFactory.getInstance(request);

	searchContext.setEnd(entriesSearchContainer.getEnd());
	searchContext.setKeywords(keywords);
	searchContext.setStart(entriesSearchContainer.getStart());

	Hits hits = indexer.search(searchContext);

	entriesSearchContainer.setTotal(hits.getLength());

	for (int i = 0; i < hits.getDocs().length; i++) {
		Document doc = hits.doc(i);

		long entryId = GetterUtil.getLong(doc.get(Field.ENTRY_CLASS_PK));

		BlogsEntry entry = null;

		try {
			entry = BlogsEntryServiceUtil.getEntry(entryId);

			entry = entry.toEscapedModel();
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn("Blogs search index is stale and contains entry " + entryId);
			}

			continue;
		}

		entriesResults.add(entry);
	}
}

entriesSearchContainer.setResults(entriesResults);
%>

<liferay-frontend:management-bar
	disabled="<%= entriesSearchContainer.getTotal() <= 0 %>"
	includeCheckBox="<%= true %>"
	searchContainerId="blogEntries"
>
	<c:if test="<%= Validator.isNull(keywords) %>">
		<liferay-frontend:management-bar-buttons>
			<liferay-frontend:management-bar-display-buttons
				displayViews='<%= new String[] {"icon", "descriptive", "list"} %>'
				portletURL="<%= displayStyleURL %>"
				selectedDisplayStyle="<%= displayStyle %>"
			/>
		</liferay-frontend:management-bar-buttons>

		<liferay-frontend:management-bar-filters>
			<liferay-frontend:management-bar-navigation
				navigationKeys='<%= new String[] {"all", "mine"} %>'
				navigationParam="entriesNavigation"
				portletURL="<%= navigationPortletURL %>"
			/>

			<liferay-frontend:management-bar-sort
				orderByCol="<%= orderByCol %>"
				orderByType="<%= orderByType %>"
				orderColumns='<%= new String[] {"title", "display-date"} %>'
				portletURL="<%= sortURL %>"
			/>
		</liferay-frontend:management-bar-filters>
	</c:if>

	<liferay-frontend:management-bar-action-buttons>
		<liferay-frontend:management-bar-button href='<%= "javascript:" + renderResponse.getNamespace() + "deleteEntries();" %>' icon='<%= TrashUtil.isTrashEnabled(scopeGroupId) ? "trash" : "times" %>' label='<%= TrashUtil.isTrashEnabled(scopeGroupId) ? "recycle-bin" : "delete" %>' />
	</liferay-frontend:management-bar-action-buttons>
</liferay-frontend:management-bar>

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

		<liferay-ui:categorization-filter
			assetType="entries"
			portletURL="<%= portletURL %>"
		/>

		<liferay-ui:search-container
			id="blogEntries"
			orderByComparator="<%= BlogsUtil.getOrderByComparator(orderByCol, orderByType) %>"
			rowChecker="<%= new EmptyOnClickRowChecker(renderResponse) %>"
			searchContainer="<%= entriesSearchContainer %>"
		>
			<liferay-ui:search-container-row
				className="com.liferay.blogs.kernel.model.BlogsEntry"
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

			<liferay-ui:search-iterator displayStyle="<%= displayStyle %>" markupView="lexicon" />
		</liferay-ui:search-container>
	</aui:form>
</div>

<c:if test="<%= BlogsPermission.contains(permissionChecker, scopeGroupId, ActionKeys.ADD_ENTRY) %>">
	<portlet:renderURL var="addEntryURL">
		<portlet:param name="mvcRenderCommandName" value="/blogs/edit_entry" />
		<portlet:param name="redirect" value="<%= currentURL %>" />
	</portlet:renderURL>

	<liferay-frontend:add-menu>
		<liferay-frontend:add-menu-item title='<%= LanguageUtil.get(request, "add-blog-entry") %>' url="<%= addEntryURL %>" />
	</liferay-frontend:add-menu>
</c:if>

<aui:script>
	function <portlet:namespace />deleteEntries() {
		if (<%= TrashUtil.isTrashEnabled(scopeGroupId) %> || confirm('<%= UnicodeLanguageUtil.get(request, "are-you-sure-you-want-to-delete-the-selected-entries") %>')) {
			var form = AUI.$(document.<portlet:namespace />fm);

			form.attr('method', 'post');
			form.fm('<%= Constants.CMD %>').val('<%= TrashUtil.isTrashEnabled(scopeGroupId) ? Constants.MOVE_TO_TRASH : Constants.DELETE %>');
			form.fm('deleteEntryIds').val(Liferay.Util.listCheckedExcept(form, '<portlet:namespace />allRowIds'));

			submitForm(form, '<portlet:actionURL name="/blogs/edit_entry" />');
		}
	}
</aui:script>

<%!
private static Log _log = LogFactoryUtil.getLog("com_liferay_blogs_web.blogs_admin.view_entries_jsp");
%>