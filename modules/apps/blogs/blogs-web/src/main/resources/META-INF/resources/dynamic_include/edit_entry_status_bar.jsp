<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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