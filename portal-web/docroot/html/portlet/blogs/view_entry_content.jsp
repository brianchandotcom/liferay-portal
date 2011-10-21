<%--
/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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
SearchContainer searchContainer = (SearchContainer)request.getAttribute("view_entry_content.jsp-searchContainer");

BlogsEntry entry = (BlogsEntry)request.getAttribute("view_entry_content.jsp-entry");

AssetEntry assetEntry = (AssetEntry)request.getAttribute("view_entry_content.jsp-assetEntry");
%>

<c:choose>
	<c:when test="<%= BlogsEntryPermission.contains(permissionChecker, entry, ActionKeys.VIEW) && (entry.isApproved() || (entry.getUserId() == user.getUserId()) || BlogsEntryPermission.contains(permissionChecker, entry, ActionKeys.UPDATE)) %>">
		<div class="entry <%= entry.isApproved() ? "" : "draft" %>">
			<div class="entry-content">

				<%
				String strutsAction = ParamUtil.getString(request, "struts_action");
				%>

				<c:if test="<%= !entry.isApproved() %>">
					<h3>
						<liferay-ui:message key='<%= entry.isPending() ? "pending-approval" : "draft" %>' />
					</h3>
				</c:if>

				<portlet:renderURL var="viewEntryURL">
					<portlet:param name="struts_action" value="/blogs/view_entry" />
					<portlet:param name="redirect" value="<%= currentURL %>" />
					<portlet:param name="urlTitle" value="<%= entry.getUrlTitle() %>" />
				</portlet:renderURL>

				<c:if test='<%= strutsAction.equals("/blogs/view_entry") %>'>
					<portlet:renderURL var="blogsURL">
						<portlet:param name="struts_action" value="/blogs/view" />
					</portlet:renderURL>
				</c:if>

				<div class="entry-title">
					<c:if test='<%= !strutsAction.equals("/blogs/view_entry") %>'>
						<h2><aui:a href="<%= viewEntryURL %>"><%= HtmlUtil.escape(entry.getTitle()) %></aui:a></h2>
					</c:if>
				</div>

				<div class="entry-date">
					<%= dateFormatDateTime.format(entry.getDisplayDate()) %>
				</div>
			</div>

			<portlet:renderURL windowState="<%= WindowState.NORMAL.toString() %>" var="bookmarkURL">
				<portlet:param name="struts_action" value="/blogs/view_entry" />
				<portlet:param name="urlTitle" value="<%= entry.getUrlTitle() %>" />
			</portlet:renderURL>

			<c:if test='<%= enableSocialBookmarks && socialBookmarksDisplayPosition.equals("top") %>'>
				<liferay-ui:social-bookmarks
					displayStyle="<%= socialBookmarksDisplayStyle %>"
					target="_blank"
					title="<%= HtmlUtil.escape(entry.getTitle()) %>"
					url="<%= bookmarkURL.toString() %>"
				/>
			</c:if>

			<c:if test="<%= BlogsEntryPermission.contains(permissionChecker, entry, ActionKeys.DELETE) || BlogsEntryPermission.contains(permissionChecker, entry, ActionKeys.PERMISSIONS) || BlogsEntryPermission.contains(permissionChecker, entry, ActionKeys.UPDATE) %>">
				<div class="lfr-meta-actions edit-actions entry">
					<table class="lfr-table">
					<tr>
						<c:if test="<%= BlogsEntryPermission.contains(permissionChecker, entry, ActionKeys.UPDATE) %>">
							<td>
								<portlet:renderURL var="editEntryURL">
									<portlet:param name="struts_action" value="/blogs/edit_entry" />
									<portlet:param name="redirect" value="<%= currentURL %>" />
									<portlet:param name="backURL" value="<%= currentURL %>" />
									<portlet:param name="entryId" value="<%= String.valueOf(entry.getEntryId()) %>" />
								</portlet:renderURL>

								<liferay-ui:icon
									image="edit"
									label="<%= true %>"
									url="<%= editEntryURL %>"
								/>
							</td>
						</c:if>

						<c:if test="<%= showEditEntryPermissions && BlogsEntryPermission.contains(permissionChecker, entry, ActionKeys.PERMISSIONS) %>">
							<td>
								<liferay-security:permissionsURL
									modelResource="<%= BlogsEntry.class.getName() %>"
									modelResourceDescription="<%= entry.getTitle() %>"
									resourcePrimKey="<%= String.valueOf(entry.getEntryId()) %>"
									var="permissionsEntryURL"
								/>

								<liferay-ui:icon
									image="permissions"
									label="<%= true %>"
									url="<%= permissionsEntryURL %>"
								/>
							</td>
						</c:if>

						<c:if test="<%= BlogsEntryPermission.contains(permissionChecker, entry, ActionKeys.DELETE) %>">
							<td>
								<portlet:renderURL var="viewURL">
									<portlet:param name="struts_action" value="/blogs/view" />
								</portlet:renderURL>

								<portlet:actionURL var="deleteEntryURL">
									<portlet:param name="struts_action" value="/blogs/edit_entry" />
									<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.DELETE %>" />
									<portlet:param name="redirect" value="<%= viewURL %>" />
									<portlet:param name="entryId" value="<%= String.valueOf(entry.getEntryId()) %>" />
								</portlet:actionURL>

								<liferay-ui:icon-delete
									label="<%= true %>"
									url="<%= deleteEntryURL %>"
								/>
							</td>
						</c:if>
					</tr>
					</table>
				</div>
			</c:if>

			<div class="entry-body">
				<c:choose>
					<c:when test='<%= pageDisplayStyle.equals(RSSUtil.DISPLAY_STYLE_ABSTRACT) && !strutsAction.equals("/blogs/view_entry") %>'>
						<%= StringUtil.shorten(HtmlUtil.stripHtml(entry.getContent()), pageAbstractLength) %>

						<br />

						 <aui:a href="<%= viewEntryURL %>"><liferay-ui:message arguments='<%= new Object[] {"aui-helper-hidden-accessible", entry.getTitle()} %>' key="read-more-x-about-x" /> &raquo;</aui:a>
					</c:when>
					<c:when test='<%= pageDisplayStyle.equals(RSSUtil.DISPLAY_STYLE_FULL_CONTENT) || strutsAction.equals("/blogs/view_entry") %>'>
						<%= entry.getContent() %>

						<liferay-ui:custom-attributes-available className="<%= BlogsEntry.class.getName() %>">
							<liferay-ui:custom-attribute-list
								className="<%= BlogsEntry.class.getName() %>"
								classPK="<%= entry.getEntryId() %>"
								editable="<%= false %>"
								label="<%= true %>"
							/>
						</liferay-ui:custom-attributes-available>
					</c:when>
					<c:when test='<%= pageDisplayStyle.equals(RSSUtil.DISPLAY_STYLE_TITLE) && !strutsAction.equals("/blogs/view_entry") %>'>
						<aui:a href="<%= viewEntryURL %>"><liferay-ui:message arguments='<%= new Object[] {"aui-helper-hidden-accessible", entry.getTitle()} %>' key="read-more-x-about-x" /> &raquo;</aui:a>
					</c:when>
				</c:choose>
			</div>

			<div class="entry-footer">
				<div class="entry-author">
					<liferay-ui:message key="written-by" /> <%= HtmlUtil.escape(PortalUtil.getUserName(entry.getUserId(), entry.getUserName())) %>
				</div>

				<div class="stats">
					<c:if test="<%= assetEntry != null %>">
						<span class="view-count">
							<c:choose>
								<c:when test="<%= assetEntry.getViewCount() == 1 %>">
									<%= assetEntry.getViewCount() %> <liferay-ui:message key="view" />,
								</c:when>
								<c:when test="<%= assetEntry.getViewCount() > 1 %>">
									<%= assetEntry.getViewCount() %> <liferay-ui:message key="views" />,
								</c:when>
							</c:choose>
						</span>
					</c:if>

					<c:if test="<%= enableComments %>">
						<span class="comments">

							<%
							long classNameId = PortalUtil.getClassNameId(BlogsEntry.class.getName());

							int messagesCount = MBMessageLocalServiceUtil.getDiscussionMessagesCount(classNameId, entry.getEntryId(), WorkflowConstants.STATUS_APPROVED);
							%>

							<c:choose>
								<c:when test='<%= strutsAction.equals("/blogs/view_entry") %>'>
									<%= messagesCount %> <liferay-ui:message key='<%= (messagesCount == 1) ? "comment" : "comments" %>' />
								</c:when>
								<c:otherwise>
									<aui:a href='<%= PropsValues.PORTLET_URL_ANCHOR_ENABLE ? viewEntryURL : viewEntryURL + StringPool.POUND + "blogsCommentsPanelContainer" %>'><%= messagesCount %> <liferay-ui:message key='<%= (messagesCount == 1) ? "comment" : "comments" %>' /></aui:a>
								</c:otherwise>
							</c:choose>
						</span>
					</c:if>
				</div>

				<c:if test="<%= enableFlags %>">
					<liferay-ui:flags
						className="<%= BlogsEntry.class.getName() %>"
						classPK="<%= entry.getEntryId() %>"
						contentTitle="<%= entry.getTitle() %>"
						reportedUserId="<%= entry.getUserId() %>"
					/>
				</c:if>

				<span class="entry-categories">
					<liferay-ui:asset-categories-summary
						className="<%= BlogsEntry.class.getName() %>"
						classPK="<%= entry.getEntryId() %>"
						portletURL="<%= renderResponse.createRenderURL() %>"
					/>
				</span>

				<span class="entry-tags">
					<liferay-ui:asset-tags-summary
						className="<%= BlogsEntry.class.getName() %>"
						classPK="<%= entry.getEntryId() %>"
						portletURL="<%= renderResponse.createRenderURL() %>"
					/>
				</span>

				<c:if test='<%= pageDisplayStyle.equals(RSSUtil.DISPLAY_STYLE_FULL_CONTENT) || strutsAction.equals("/blogs/view_entry") %>'>
					<c:if test="<%= enableRelatedAssets %>">
						<div class="entry-links">
							<liferay-ui:asset-links
								className="<%= BlogsEntry.class.getName() %>"
								classPK="<%= entry.getEntryId() %>"
							/>
						</div>
					</c:if>

					<c:if test='<%= enableSocialBookmarks && socialBookmarksDisplayPosition.equals("bottom") %>'>
						<liferay-ui:social-bookmarks
							displayStyle="<%= socialBookmarksDisplayStyle %>"
							target="_blank"
							title="<%= HtmlUtil.escape(entry.getTitle()) %>"
							url="<%= bookmarkURL.toString() %>"
						/>
					</c:if>

					<c:if test="<%= enableRatings %>">
						<liferay-ui:ratings
							className="<%= BlogsEntry.class.getName() %>"
							classPK="<%= entry.getEntryId() %>"
						/>
					</c:if>
				</c:if>
			</div>
		</div>

		<div class="separator"><!-- --></div>
	</c:when>
	<c:otherwise>

		<%
		if (searchContainer != null) {
			searchContainer.setTotal(searchContainer.getTotal() - 1);
		}
		%>

	</c:otherwise>
</c:choose>