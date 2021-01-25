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
long assetCategoryId = ParamUtil.getLong(request, "categoryId");
String assetTagName = ParamUtil.getString(request, "tag");

BlogEntriesDisplayContext blogEntriesDisplayContext = (BlogEntriesDisplayContext)request.getAttribute(BlogsWebKeys.BLOG_ENTRIES_DISPLAY_CONTEXT);

String displayStyle = blogEntriesDisplayContext.getDisplayStyle();

SearchContainer<BlogsEntry> entriesSearchContainer = blogEntriesDisplayContext.getSearchContainer();

PortletURL portletURL = entriesSearchContainer.getIteratorURL();

BlogEntriesManagementToolbarDisplayContext blogEntriesManagementToolbarDisplayContext = new BlogEntriesManagementToolbarDisplayContext(request, liferayPortletRequest, liferayPortletResponse, entriesSearchContainer, trashHelper, displayStyle);
%>

<clay:management-toolbar
	additionalProps="<%= blogEntriesManagementToolbarDisplayContext.getAdditionalProps() %>"
	managementToolbarDisplayContext="<%= blogEntriesManagementToolbarDisplayContext %>"
	propsTransformer="blogs_admin/js/BlogEntriesManagementToolbarPropsTransformer"
	searchContainerId="blogEntries"
/>

<portlet:actionURL name="/blogs/edit_entry" var="restoreTrashEntriesURL">
	<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.RESTORE %>" />
</portlet:actionURL>

<liferay-trash:undo
	portletURL="<%= restoreTrashEntriesURL %>"
/>

<clay:container-fluid>
	<aui:form action="<%= portletURL.toString() %>" method="get" name="fm">
		<aui:input name="<%= Constants.CMD %>" type="hidden" />
		<aui:input name="redirect" type="hidden" value="<%= portletURL.toString() %>" />
		<aui:input name="deleteEntryIds" type="hidden" />
		<aui:input name="selectAll" type="hidden" value="<%= false %>" />

		<c:if test="<%= (assetCategoryId != 0) || Validator.isNotNull(assetTagName) %>">
			<liferay-asset:categorization-filter
				assetType="entries"
				portletURL="<%= portletURL %>"
			/>
		</c:if>

		<liferay-ui:search-container
			id="blogEntries"
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
					<portlet:param name="redirect" value="<%= portletURL.toString() %>" />
					<portlet:param name="entryId" value="<%= String.valueOf(entry.getEntryId()) %>" />
				</liferay-portlet:renderURL>

				<%
				row.setData(
					HashMapBuilder.<String, Object>put(
						"actions", StringUtil.merge(blogEntriesDisplayContext.getAvailableActions(entry))
					).build());
				%>

				<%@ include file="/blogs_admin/entry_search_columns.jspf" %>
			</liferay-ui:search-container-row>

			<liferay-ui:search-iterator
				displayStyle="<%= displayStyle %>"
				markupView="lexicon"
			/>
		</liferay-ui:search-container>
	</aui:form>
</clay:container-fluid>

<liferay-frontend:component
	componentId="<%= BlogsWebConstants.BLOGS_ELEMENTS_DEFAULT_EVENT_HANDLER %>"
	context="<%= blogEntriesDisplayContext.getComponentContext() %>"
	module="blogs_admin/js/ElementsDefaultEventHandler.es"
/>