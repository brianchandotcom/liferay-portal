<%--
/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/blogs/init.jsp" %>

<%
long assetCategoryId = ParamUtil.getLong(request, "categoryId");
String assetTagName = ParamUtil.getString(request, "tag");

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("struts_action", "/blogs/view");

String trashedEntryIds = StringPool.BLANK;

if (SessionMessages.contains(request, portletDisplay.getId() + "_delete-success")) {
	trashedEntryIds = GetterUtil.getString(StringUtil.merge((long[])session.getAttribute("trashedEntryIds")));

	session.removeAttribute("trashedEntryIds");
}
%>

<liferay-portlet:renderURL varImpl="searchURL">
	<portlet:param name="struts_action" value="/blogs/search" />
</liferay-portlet:renderURL>

<% if (SessionMessages.contains(request, portletDisplay.getId() + "_delete-success")) { %>
	<div class="portlet-msg-notifier">
		<liferay-ui:message arguments='<%= new String[] {"javascript:" + renderResponse.getNamespace() + "undoEntries();"} %>' key="the-selected-item-has-been-moved-to-the-recycle-bin.-undo" translateArguments="false" />
	</div>

	<liferay-portlet:renderURL varImpl="undoURL">
		<portlet:param name="struts_action" value="/blogs/edit_entry" />
	</liferay-portlet:renderURL>

	<aui:form action="<%= undoURL.toString() %>" method="get" name="fm2">
		<aui:input name="<%= Constants.CMD %>" type="hidden" />
		<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
		<aui:input name="restoreEntryIds" type="hidden" />
	</aui:form>
<% } %>

<aui:form action="<%= searchURL %>" method="get" name="fm1">
	<liferay-portlet:renderURLParams varImpl="searchURL" />
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
	<aui:input name="groupId" type="hidden" value="<%= String.valueOf(scopeGroupId) %>" />

	<%
	SearchContainer searchContainer = new SearchContainer(renderRequest, null, null, SearchContainer.DEFAULT_CUR_PARAM, pageDelta, portletURL, null, null);

	searchContainer.setDelta(pageDelta);
	searchContainer.setDeltaConfigurable(false);

	int total = 0;
	List results = null;

	if ((assetCategoryId != 0) || Validator.isNotNull(assetTagName)) {
		AssetEntryQuery assetEntryQuery = new AssetEntryQuery(BlogsEntry.class.getName(), searchContainer);

		assetEntryQuery.setExcludeZeroViewCount(false);
		assetEntryQuery.setVisible(Boolean.TRUE);

		total = AssetEntryServiceUtil.getEntriesCount(assetEntryQuery);
		results = AssetEntryServiceUtil.getEntries(assetEntryQuery);
	}
	else {
		int status = WorkflowConstants.STATUS_APPROVED;

		if (BlogsPermission.contains(permissionChecker, scopeGroupId, ActionKeys.ADD_ENTRY)) {
			status = WorkflowConstants.STATUS_ANY;
		}

		total = BlogsEntryServiceUtil.getGroupEntriesCount(scopeGroupId, status);
		results = BlogsEntryServiceUtil.getGroupEntries(scopeGroupId, status, searchContainer.getStart(), searchContainer.getEnd());
	}

	searchContainer.setTotal(total);
	searchContainer.setResults(results);
	%>

	<%@ include file="/html/portlet/blogs/view_entries.jspf" %>
</aui:form>

<c:if test="<%= windowState.equals(WindowState.MAXIMIZED) %>">
	<aui:script>
		Liferay.Util.focusFormField(document.<portlet:namespace />fm1.<portlet:namespace />keywords);
	</aui:script>
</c:if>

<aui:script>
	Liferay.provide(
				window,
				'<portlet:namespace />undoEntries',
				function() {
					if (confirm('<%= UnicodeLanguageUtil.get(pageContext, "are-you-sure-you-want-to-undo-your-last-changes") %>')) {
						document.<portlet:namespace />fm2.method = "post";
						document.<portlet:namespace />fm2.<portlet:namespace /><%= Constants.CMD %>.value = "<%= Constants.UNDO %>";
						document.<portlet:namespace />fm2.<portlet:namespace />restoreEntryIds.value = "<%= trashedEntryIds %>";
						submitForm(document.<portlet:namespace />fm2, "<portlet:actionURL><portlet:param name="struts_action" value="/blogs/edit_entry" /></portlet:actionURL>");
					}
				},
				['liferay-util-list-fields']
			);
</aui:script>