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
BlogsEditEntryDisplayContext blogsEditEntryDisplayContext = (BlogsEditEntryDisplayContext)request.getAttribute(BlogsEditEntryDisplayContext.class.getName());

BlogsEntry entry = blogsEditEntryDisplayContext.getBlogsEntry();

BlogsPortletInstanceConfiguration blogsPortletInstanceConfiguration = BlogsPortletInstanceConfigurationUtil.getBlogsPortletInstanceConfiguration(themeDisplay);
%>

<div class="management-bar management-bar-light navbar navbar-expand-md">
	<clay:container-fluid>
		<ul class="m-auto navbar-nav"></ul>

		<ul class="middle navbar-nav">
			<li class="nav-item">
				<c:choose>
					<c:when test="<%= entry != null %>">
						<span class="text-capitalize text-muted" id="<portlet:namespace />saveStatus">
							<aui:workflow-status markupView="lexicon" showHelpMessage="<%= false %>" showIcon="<%= false %>" showLabel="<%= false %>" status="<%= entry.getStatus() %>" />

							<liferay-ui:message arguments="<%= LanguageUtil.getTimeDescription(request, System.currentTimeMillis() - entry.getStatusDate().getTime(), true) %>" key="x-ago" translateArguments="<%= false %>" />
						</span>
					</c:when>
					<c:otherwise>
						<span class="text-capitalize text-muted" id="<portlet:namespace />saveStatus"></span>
					</c:otherwise>
				</c:choose>

				<c:if test="<%= blogsPortletInstanceConfiguration.enableReadingTime() %>">
					<span class="reading-time-wrapper text-muted">
						<liferay-reading-time:reading-time
							displayStyle="descriptive"
							id="readingTime"
							model="<%= entry %>"
						/>
					</span>
				</c:if>
			</li>
		</ul>

		<ul class="end m-auto navbar-nav"></ul>
	</clay:container-fluid>
</div>