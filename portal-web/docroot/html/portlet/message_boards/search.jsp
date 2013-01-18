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

<%@ include file="/html/portlet/message_boards/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

long breadcrumbsCategoryId = ParamUtil.getLong(request, "breadcrumbsCategoryId");
long breadcrumbsMessageId = ParamUtil.getLong(request, "breadcrumbsMessageId");

long searchCategoryId = ParamUtil.getLong(request, "searchCategoryId");

long[] categoryIdsArray = null;

List categoryIds = new ArrayList();

categoryIds.add(new Long(searchCategoryId));

MBCategoryServiceUtil.getSubcategoryIds(categoryIds, scopeGroupId, searchCategoryId);

categoryIdsArray = StringUtil.split(StringUtil.merge(categoryIds), 0L);

long threadId = ParamUtil.getLong(request, "threadId");
String keywords = ParamUtil.getString(request, "keywords");
%>

<liferay-portlet:renderURL varImpl="searchURL">
	<portlet:param name="struts_action" value="/message_boards/search" />
</liferay-portlet:renderURL>

<aui:form action="<%= searchURL %>" method="get" name="fm">
	<liferay-portlet:renderURLParams varImpl="searchURL" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="breadcrumbsCategoryId" type="hidden" value="<%= breadcrumbsCategoryId %>" />
	<aui:input name="breadcrumbsMessageId" type="hidden" value="<%= breadcrumbsMessageId %>" />
	<aui:input name="searchCategoryId" type="hidden" value="<%= searchCategoryId %>" />
	<aui:input name="threadId" type="hidden" value="<%= threadId %>" />

	<liferay-ui:header
		backURL="<%= redirect %>"
		title="search"
	/>

	<%
	PortletURL portletURL = renderResponse.createRenderURL();

	portletURL.setParameter("struts_action", "/message_boards/search");
	portletURL.setParameter("redirect", redirect);
	portletURL.setParameter("breadcrumbsCategoryId", String.valueOf(breadcrumbsCategoryId));
	portletURL.setParameter("breadcrumbsMessageId", String.valueOf(breadcrumbsMessageId));
	portletURL.setParameter("searchCategoryId", String.valueOf(searchCategoryId));
	portletURL.setParameter("threadId", String.valueOf(threadId));
	portletURL.setParameter("keywords", keywords);
	%>

	<liferay-ui:search-container
		emptyResultsMessage='<%= LanguageUtil.format(pageContext, "no-messages-were-found-that-matched-the-keywords-x", "<strong>" + HtmlUtil.escape(keywords) + "</strong>") %>'
		iteratorURL="<%= portletURL %>"
	>

		<%
		Indexer indexer = IndexerRegistryUtil.getIndexer(MBMessage.class);

		SearchContext searchContext = SearchContextFactory.getInstance(request);

		searchContext.setAttribute("paginationType", "more");
		searchContext.setCategoryIds(categoryIdsArray);
		searchContext.setEnd(searchContainer.getEnd());
		searchContext.setIncludeAttachments(true);
		searchContext.setKeywords(keywords);
		searchContext.setStart(searchContainer.getStart());

		Hits hits = indexer.search(searchContext);
		%>

		<liferay-ui:search-container-results
			results="<%= MBUtil.getEntries(hits) %>"
			total="<%= hits.getLength() %>"
		/>

		<liferay-ui:search-container-row
			className="Object"
			modelVar="obj"
		>

			<c:choose>
				<c:when test="<%= obj instanceof DLFileEntry %>">

					<%
					DLFileEntry dlFileEntry = (DLFileEntry)obj;

					MBMessage message = MBMessageAttachmentsUtil.getMessage(dlFileEntry.getFileEntryId());

					MBCategory category = message.getCategory();

					PortletURL categoryURL = renderResponse.createRenderURL();

					categoryURL.setParameter("struts_action", "/message_boards/view");
					categoryURL.setParameter("redirect", currentURL);
					categoryURL.setParameter("mbCategoryId", String.valueOf(category.getCategoryId()));
					%>

					<portlet:actionURL var="rowURL" windowState="<%= LiferayWindowState.EXCLUSIVE.toString() %>">
						<portlet:param name="struts_action" value="/message_boards/get_message_attachment" />
						<portlet:param name="messageId" value="<%= String.valueOf(message.getMessageId()) %>" />
						<portlet:param name="attachment" value="<%= dlFileEntry.getTitle() %>" />
					</portlet:actionURL>

					<portlet:renderURL var="messageURL">
						<portlet:param name="struts_action" value="/message_boards/view_message" />
						<portlet:param name="redirect" value="<%= currentURL %>" />
						<portlet:param name="messageId" value="<%= String.valueOf(message.getMessageId()) %>" />
					</portlet:renderURL>

					<liferay-ui:search-container-column-text
						name="title"
					>
						<liferay-ui:icon
							image='<%= "../file_system/small/" + DLUtil.getFileIcon(dlFileEntry.getExtension()) %>'
							label="<%= true %>"
							message="<%= dlFileEntry.getTitle() %>"
							url="<%= rowURL %>"
						/>

						<liferay-util:buffer var="rootEntryIcon">
							<liferay-ui:icon
								image="message"
								label="<%= true %>"
								message="<%= HtmlUtil.escape(message.getSubject()) %>"
								url="<%= messageURL %>"
							/>
						</liferay-util:buffer>

						<span class="search-root-entry">(<liferay-ui:message arguments="<%= rootEntryIcon %>" key="attachment-found-in-message-x" />)</span>
					</liferay-ui:search-container-column-text>

					<liferay-ui:search-container-column-text
						name="type"
						value='<%= LanguageUtil.get(locale, "attachment") %>'
					/>

					<liferay-ui:search-container-column-text
						href="<%= categoryURL %>"
						name="category"
						value="<%= HtmlUtil.escape(category.getName()) %>"
					/>

					<liferay-ui:search-container-column-text
						href="<%= rowURL %>"
						name="thread-posts"
						value="<%= StringPool.BLANK %>"
					/>

					<liferay-ui:search-container-column-text
						href="<%= rowURL %>"
						name="thread-views"
						value="<%= StringPool.BLANK %>"
					/>
				</c:when>
				<c:when test="<%= obj instanceof MBMessage %>">

					<%
					MBMessage message = (MBMessage)obj;

					MBCategory category = message.getCategory();

					PortletURL categoryURL = renderResponse.createRenderURL();

					categoryURL.setParameter("struts_action", "/message_boards/view");
					categoryURL.setParameter("redirect", currentURL);
					categoryURL.setParameter("mbCategoryId", String.valueOf(category.getCategoryId()));

					// Thread and message

					MBThread thread = message.getThread();
					%>

					<portlet:renderURL var="rowURL">
						<portlet:param name="struts_action" value="/message_boards/view_message" />
						<portlet:param name="redirect" value="<%= currentURL %>" />
						<portlet:param name="messageId" value="<%= String.valueOf(message.getMessageId()) %>" />
					</portlet:renderURL>

					<liferay-ui:search-container-column-text
						name="title"
					>
						<liferay-ui:icon
							image="message"
							label="<%= true %>"
							message="<%= HtmlUtil.escape(message.getSubject()) %>"
							url="<%= rowURL %>"
						/>
					</liferay-ui:search-container-column-text>

					<liferay-ui:search-container-column-text
						name="type"
						value='<%= LanguageUtil.get(locale, "message") %>'
					/>

					<liferay-ui:search-container-column-text
						href="<%= categoryURL %>"
						name="category"
						value="<%= HtmlUtil.escape(category.getName()) %>"
					/>

					<liferay-ui:search-container-column-text
						href="<%= rowURL %>"
						name="thread-posts"
						value="<%= String.valueOf(thread.getMessageCount()) %>"
					/>

					<liferay-ui:search-container-column-text
						href="<%= rowURL %>"
						name="thread-views"
						value="<%= String.valueOf(thread.getViewCount()) %>"
					/>
				</c:when>
			</c:choose>

		</liferay-ui:search-container-row>

		<span class="aui-search-bar">
			<aui:input inlineField="<%= true %>" label="" name="keywords" size="30" title="search-messages" type="text" value="<%= keywords %>" />

			<aui:button type="submit" value="search" />
		</span>

		<br /><br />

		<liferay-ui:search-iterator type="more" />
	</liferay-ui:search-container>

</aui:form>

<c:if test="<%= windowState.equals(WindowState.MAXIMIZED) && !themeDisplay.isFacebook() %>">
	<aui:script>
		Liferay.Util.focusFormField(document.<portlet:namespace />fm.<portlet:namespace />keywords);
	</aui:script>
</c:if>

<%
if (breadcrumbsCategoryId > 0) {
	MBUtil.addPortletBreadcrumbEntries(breadcrumbsCategoryId, request, renderResponse);
}

PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(pageContext, "search") + ": " + keywords, currentURL);
%>