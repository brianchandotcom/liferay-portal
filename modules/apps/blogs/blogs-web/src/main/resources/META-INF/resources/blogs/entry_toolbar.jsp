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

<%@ include file="/blogs/init.jsp" %>

<%
BlogsEntry entry = (BlogsEntry)request.getAttribute("entry_toolbar.jsp-entry");
RatingsEntry ratingsEntry = (RatingsEntry)request.getAttribute("view_entry_content.jsp-ratingsEntry");
RatingsStats ratingsStats = (RatingsStats)request.getAttribute("view_entry_content.jsp-ratingsStats");

boolean showFlags = ParamUtil.getBoolean(request, "showFlags");
boolean showOnlyIcons = ParamUtil.getBoolean(request, "showOnlyIcons");

BlogsPortletInstanceConfiguration blogsPortletInstanceConfiguration = BlogsPortletInstanceConfigurationUtil.getBlogsPortletInstanceConfiguration(themeDisplay);
%>

<div class="autofit-float autofit-row autofit-row-center widget-toolbar">
	<c:if test="<%= blogsPortletInstanceConfiguration.enableComments() %>">
		<div class="autofit-col">

			<%
			int messagesCount = CommentManagerUtil.getCommentsCount(BlogsEntry.class.getName(), entry.getEntryId());
			%>

			<portlet:renderURL var="viewEntryCommentsURL">
				<portlet:param name="mvcRenderCommandName" value="/blogs/view_entry" />
				<portlet:param name="scroll" value='<%= renderResponse.getNamespace() + "discussionContainer" %>' />

				<c:choose>
					<c:when test="<%= Validator.isNotNull(entry.getUrlTitle()) %>">
						<portlet:param name="urlTitle" value="<%= entry.getUrlTitle() %>" />
					</c:when>
					<c:otherwise>
						<portlet:param name="entryId" value="<%= String.valueOf(entry.getEntryId()) %>" />
					</c:otherwise>
				</c:choose>
			</portlet:renderURL>

			<liferay-util:whitespace-remover>
				<a class="btn btn-outline-borderless btn-outline-secondary btn-sm" href="<%= viewEntryCommentsURL.toString() %>" title="<liferay-ui:message key="comments" />">
					<span class="inline-item inline-item-before">
						<clay:icon
							symbol="comments"
						/>
					</span>

					<c:choose>
						<c:when test="<%= showOnlyIcons %>">
							<%= messagesCount %>
						</c:when>
						<c:otherwise>
							<liferay-ui:message arguments="<%= messagesCount %>" key="comment-x" />
						</c:otherwise>
					</c:choose>
				</a>
			</liferay-util:whitespace-remover>
		</div>
	</c:if>

	<c:if test="<%= blogsPortletInstanceConfiguration.enableRatings() %>">
		<div class="autofit-col">
			<div class="ratings">
				<liferay-ui:ratings
					className="<%= BlogsEntry.class.getName() %>"
					classPK="<%= entry.getEntryId() %>"
					inTrash="<%= entry.isInTrash() %>"
					ratingsEntry="<%= ratingsEntry %>"
					ratingsStats="<%= ratingsStats %>"
				/>
			</div>
		</div>
	</c:if>

	<c:if test="<%= blogsPortletInstanceConfiguration.enableFlags() && showFlags %>">
		<div class="autofit-col">
			<div class="flags">
				<liferay-flags:flags
					className="<%= BlogsEntry.class.getName() %>"
					classPK="<%= entry.getEntryId() %>"
					contentTitle="<%= BlogsEntryUtil.getDisplayTitle(resourceBundle, entry) %>"
					enabled="<%= !entry.isInTrash() %>"
					message='<%= entry.isInTrash() ? "flags-are-disabled-because-this-entry-is-in-the-recycle-bin" : null %>'
					reportedUserId="<%= entry.getUserId() %>"
				/>
			</div>
		</div>
	</c:if>

	<div class="autofit-col autofit-col-end">
		<liferay-portlet:renderURL varImpl="bookmarkURL" windowState="<%= WindowState.NORMAL.toString() %>">
			<portlet:param name="mvcRenderCommandName" value="/blogs/view_entry" />

			<c:choose>
				<c:when test="<%= Validator.isNotNull(entry.getUrlTitle()) %>">
					<portlet:param name="urlTitle" value="<%= entry.getUrlTitle() %>" />
				</c:when>
				<c:otherwise>
					<portlet:param name="entryId" value="<%= String.valueOf(entry.getEntryId()) %>" />
				</c:otherwise>
			</c:choose>
		</liferay-portlet:renderURL>

		<liferay-social-bookmarks:bookmarks
			className="<%= BlogsEntry.class.getName() %>"
			classPK="<%= entry.getEntryId() %>"
			displayStyle='<%= showOnlyIcons ? "inline" : blogsPortletInstanceConfiguration.socialBookmarksDisplayStyle() %>'
			maxInlineItems="<%= showOnlyIcons ? 0 : 3 %>"
			target="_blank"
			title="<%= BlogsEntryUtil.getDisplayTitle(resourceBundle, entry) %>"
			types="<%= SocialBookmarksUtil.getSocialBookmarksTypes(blogsPortletInstanceConfiguration) %>"
			urlImpl="<%= bookmarkURL %>"
		/>
	</div>
</div>