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
int[] statuses = {WorkflowConstants.STATUS_DRAFT, WorkflowConstants.STATUS_SCHEDULED};

int myPendingEntriesCount = BlogsEntryServiceUtil.getGroupUserEntriesCount(scopeGroupId, themeDisplay.getUserId(), statuses);
%>

<c:if test="<%= myPendingEntriesCount > 0 %>">

	<%
	boolean extended = ParamUtil.getBoolean(request, "extended", false);

	PortletURL iteratorURL = renderResponse.createRenderURL();

	iteratorURL.setParameter("mvcRenderCommandName", "/blogs/view");
	iteratorURL.setParameter("extended", Boolean.FALSE.toString());
	%>

	<liferay-ui:panel collapsible="<%= true %>" extended="<%= extended %>" markupView="lexicon" persistState="<%= true %>" title="my-pending-entries">
		<liferay-ui:search-container
			curParam="myPendingEntriesCur"
			delta="<%= 5 %>"
			iteratorURL="<%= iteratorURL %>"
			total="<%= myPendingEntriesCount %>"
		>
			<liferay-ui:search-container-results
				results="<%= BlogsEntryServiceUtil.getGroupUserEntries(scopeGroupId, themeDisplay.getUserId(), statuses, searchContainer.getStart(), searchContainer.getEnd(), new EntryModifiedDateComparator()) %>"
			/>

			<liferay-ui:search-container-row
				className="com.liferay.portlet.blogs.model.BlogsEntry"
				keyProperty="entryId"
				modelVar="entry"
			>
				<portlet:renderURL var="viewEntryURL" windowState="<%= WindowState.MAXIMIZED.toString() %>">
					<portlet:param name="mvcRenderCommandName" value="/blogs/view_entry" />
					<portlet:param name="redirect" value="<%= currentURL %>" />
					<portlet:param name="urlTitle" value="<%= entry.getUrlTitle() %>" />
				</portlet:renderURL>

				<liferay-ui:search-container-column-text
					href="<%= viewEntryURL %>"
					name="title"
					value="<%= entry.getTitle() %>"
				/>

				<liferay-ui:search-container-column-status
					name="status"
					status="<%= entry.getStatus() %>"
				/>

				<liferay-ui:search-container-column-jsp
					align="right"
					cssClass="entry-action"
					path="/blogs/entry_action.jsp"
				/>
			</liferay-ui:search-container-row>

			<liferay-ui:search-iterator markupView="lexicon" />
		</liferay-ui:search-container>
	</liferay-ui:panel>
</c:if>