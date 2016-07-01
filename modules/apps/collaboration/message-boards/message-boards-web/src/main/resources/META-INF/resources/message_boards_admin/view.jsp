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

request.setAttribute("view.jsp-categoryId", categoryId);
request.setAttribute("view.jsp-portletURL", portletURL);
request.setAttribute("view.jsp-viewCategory", Boolean.TRUE.toString());
%>

<portlet:actionURL name="/message_boards/edit_category" var="restoreTrashEntriesURL">
	<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.RESTORE %>" />
</portlet:actionURL>

<liferay-trash:undo
	portletURL="<%= restoreTrashEntriesURL %>"
/>

<%
MBListDisplayContext mbListDisplayContext = mbDisplayContextProvider.getMbListDisplayContext(request, response, categoryId);
%>

<c:if test="<%= !mbListDisplayContext.isShowRecentPosts() %>">
	<aui:nav-bar cssClass="collapse-basic-search" markupView="lexicon">
		<liferay-util:include page="/message_boards_admin/nav.jsp" servletContext="<%= application %>">
			<liferay-util:param name="navItemSelected" value="threads" />
		</liferay-util:include>

		<liferay-portlet:renderURL varImpl="searchURL">
			<portlet:param name="mvcRenderCommandName" value="/message_boards_admin/search" />
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
</c:if>

<%
SearchContainer searchContainer = new SearchContainer(renderRequest, null, null, "cur1", 0, SearchContainer.DEFAULT_DELTA, portletURL, null, "there-are-no-threads-nor-categories");

searchContainer.setId("mbEntries");

EntriesChecker entriesChecker = new EntriesChecker(liferayPortletRequest, liferayPortletResponse);

searchContainer.setRowChecker(entriesChecker);

if (categoryId == 0) {
	entriesChecker.setRememberCheckBoxStateURLRegex("mvcRenderCommandName=/message_boards/view(&.|$)");
}
else {
	entriesChecker.setRememberCheckBoxStateURLRegex("mbCategoryId=" + categoryId);
}

mbListDisplayContext.populateResultsAndTotal(searchContainer);
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
		<liferay-frontend:management-bar-button href='<%= "javascript:" + renderResponse.getNamespace() + "deleteEntries();" %>' icon='<%= TrashUtil.isTrashEnabled(scopeGroupId) ? "trash" : "times" %>' label='<%= TrashUtil.isTrashEnabled(scopeGroupId) ? "recycle-bin" : "delete" %>' />

		<liferay-frontend:management-bar-button href='<%= "javascript:" + renderResponse.getNamespace() + "lockEntries();" %>' icon="lock" label="lock" />

		<liferay-frontend:management-bar-button href='<%= "javascript:" + renderResponse.getNamespace() + "unlockEntries();" %>' icon="unlock" label="unlock" />
	</liferay-frontend:management-bar-action-buttons>
</liferay-frontend:management-bar>

<%
request.setAttribute("view.jsp-displayStyle", "descriptive");
request.setAttribute("view.jsp-entriesSearchContainer", searchContainer);
%>

<c:choose>
	<c:when test="<%= mbListDisplayContext.isShowRecentPosts() %>">
		<div class="container-fluid-1280">

			<%
			long groupThreadsUserId = ParamUtil.getLong(request, "groupThreadsUserId");

			if (groupThreadsUserId > 0) {
				portletURL.setParameter("groupThreadsUserId", String.valueOf(groupThreadsUserId));
			}
			%>

			<c:if test="<%= groupThreadsUserId > 0 %>">
				<div class="alert alert-info">
					<liferay-ui:message key="filter-by-user" />: <%= HtmlUtil.escape(PortalUtil.getUserName(groupThreadsUserId, StringPool.BLANK)) %>
				</div>
			</c:if>

			<c:if test="<%= enableRSS %>">
				<liferay-ui:rss
					delta="<%= rssDelta %>"
					displayStyle="<%= rssDisplayStyle %>"
					feedType="<%= rssFeedType %>"
					message="subscribe-to-recent-posts"
					url="<%= MBUtil.getRSSURL(plid, 0, 0, groupThreadsUserId, themeDisplay) %>"
				/>
			</c:if>
		</div>

		<liferay-util:include page='<%= "/message_boards_admin/view_entries.jsp" %>' servletContext="<%= application %>">
			<liferay-util:param name="showBreadcrumb" value="<%= Boolean.FALSE.toString() %>" />
		</liferay-util:include>

		<%
		PortalUtil.setPageSubtitle(LanguageUtil.get(request, StringUtil.replace("recent-posts", CharPool.UNDERLINE, CharPool.DASH)), request);
		%>

	</c:when>
	<c:otherwise>
		<liferay-util:include page="/message_boards_admin/view_entries.jsp" servletContext="<%= application %>" />

		<%
		if (category != null) {
			PortalUtil.setPageSubtitle(category.getName(), request);
			PortalUtil.setPageDescription(category.getDescription(), request);
		}
		%>

	</c:otherwise>
</c:choose>

<c:if test="<%= !mbListDisplayContext.isShowSearch() && !mbListDisplayContext.isShowRecentPosts() %>">
	<liferay-util:include page="/message_boards_admin/add_button.jsp" servletContext="<%= application %>" />
</c:if>

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