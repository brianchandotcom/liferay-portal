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

<%@ include file="/dynamic_include/init.jsp" %>

<%
BlogsEntry entry = (BlogsEntry)request.getAttribute(WebKeys.BLOGS_ENTRY);

BlogsPortletInstanceConfiguration blogsPortletInstanceConfiguration = BlogsPortletInstanceConfigurationUtil.getBlogsPortletInstanceConfiguration(themeDisplay);
%>

<liferay-frontend:info-bar
	fixed="<%= true %>"
>
	<c:choose>
		<c:when test="<%= entry != null %>">
			<small class="text-capitalize text-muted" id="<portlet:namespace />saveStatus">
				<aui:workflow-status markupView="lexicon" showHelpMessage="<%= false %>" showIcon="<%= false %>" showLabel="<%= false %>" status="<%= entry.getStatus() %>" />

				<liferay-ui:message arguments="<%= LanguageUtil.getTimeDescription(request, System.currentTimeMillis() - entry.getStatusDate().getTime(), true) %>" key="x-ago" translateArguments="<%= false %>" />
			</small>
		</c:when>
		<c:otherwise>
			<small class="text-capitalize text-muted" id="<portlet:namespace />saveStatus"></small>
		</c:otherwise>
	</c:choose>

	<c:if test="<%= blogsPortletInstanceConfiguration.enableReadingTime() %>">
		<small class="reading-time-wrapper text-muted">
			<liferay-reading-time:reading-time
				displayStyle="descriptive"
				id="readingTime"
				model="<%= entry %>"
			/>
		</small>
	</c:if>
</liferay-frontend:info-bar>