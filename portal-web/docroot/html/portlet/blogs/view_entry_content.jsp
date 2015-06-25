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

<%@ include file="/html/portlet/blogs/init.jsp" %>

<%
SearchContainer searchContainer = (SearchContainer)request.getAttribute("view_entry_content.jsp-searchContainer");

BlogsEntry entry = (BlogsEntry)request.getAttribute("view_entry_content.jsp-entry");

AssetEntry assetEntry = (AssetEntry)request.getAttribute("view_entry_content.jsp-assetEntry");
%>

<c:choose>
	<c:when test="<%= BlogsEntryPermission.contains(permissionChecker, entry, ActionKeys.VIEW) && (entry.isVisible() || (entry.getUserId() == user.getUserId()) || BlogsEntryPermission.contains(permissionChecker, entry, ActionKeys.UPDATE)) %>">
		<div class="entry <%= WorkflowConstants.getStatusLabel(entry.getStatus()) %>" id="<portlet:namespace /><%= entry.getEntryId() %>">
			<div class="entry-body">

				<%
				String coverImageURL = entry.getCoverImageURL(themeDisplay);
				%>

				<c:if test="<%= Validator.isNotNull(coverImageURL) %>">
					<div class="cover-image-container" style="background-image: url(<%= coverImageURL %>)"></div>

					<div class="cover-image-caption">
						<span><%= entry.getCoverImageCaption() %></span>
					</div>
				</c:if>

				<%
				String mvcRenderCommandName = ParamUtil.getString(request, "mvcRenderCommandName");

				long assetCategoryId = ParamUtil.getLong(request, "categoryId");
				String assetTagName = ParamUtil.getString(request, "tag");

				boolean viewSingleEntry = mvcRenderCommandName.equals("/blogs/view_entry") && (assetCategoryId == 0) && Validator.isNull(assetTagName);
				%>

				<c:if test="<%= !entry.isApproved() %>">
					<h3 class="icon-file-alt">
						<liferay-ui:message key='<%= entry.isPending() ? "pending-approval" : WorkflowConstants.getStatusLabel(entry.getStatus()) %>' />
					</h3>
				</c:if>

				<portlet:renderURL var="viewEntryURL">
					<portlet:param name="mvcRenderCommandName" value="/blogs/view_entry" />
					<portlet:param name="redirect" value="<%= currentURL %>" />
					<portlet:param name="urlTitle" value="<%= entry.getUrlTitle() %>" />
				</portlet:renderURL>

				<div class="entry-title">
					<h2>
						<c:choose>
							<c:when test="<%= !viewSingleEntry %>">
								<aui:a href="<%= viewEntryURL %>"><%= HtmlUtil.escape(entry.getTitle()) %></aui:a>
							</c:when>
							<c:otherwise>
								<%= HtmlUtil.escape(entry.getTitle()) %>
							</c:otherwise>
						</c:choose>
					</h2>
				</div>

				<%
				String subtitle = entry.getSubtitle();
				%>

				<c:if test="<%= Validator.isNotNull(subtitle) %>">
					<div class="entry-subtitle">
						<p><%= HtmlUtil.escape(subtitle) %></p>
					</div>
				</c:if>

				<div class="entry-date icon-calendar">
					<span class="hide-accessible"><liferay-ui:message key="published-date" /></span>

					<%= dateFormatDateTime.format(entry.getDisplayDate()) %>
				</div>
			</div>

			<portlet:renderURL var="bookmarkURL" windowState="<%= WindowState.NORMAL.toString() %>">
				<portlet:param name="mvcRenderCommandName" value="/blogs/view_entry" />
				<portlet:param name="urlTitle" value="<%= entry.getUrlTitle() %>" />
			</portlet:renderURL>

			<c:if test='<%= blogsPortletInstanceSettings.isEnableSocialBookmarks() && blogsPortletInstanceSettings.getSocialBookmarksDisplayPosition().equals("top") %>'>
				<liferay-ui:social-bookmarks
					displayStyle="<%= blogsPortletInstanceSettings.getSocialBookmarksDisplayStyle() %>"
					target="_blank"
					title="<%= entry.getTitle() %>"
					types="<%= blogsPortletInstanceSettings.getSocialBookmarksTypes() %>"
					url="<%= PortalUtil.getCanonicalURL(bookmarkURL.toString(), themeDisplay, layout) %>"
				/>
			</c:if>

			<c:if test="<%= BlogsEntryPermission.contains(permissionChecker, entry, ActionKeys.DELETE) || BlogsEntryPermission.contains(permissionChecker, entry, ActionKeys.PERMISSIONS) || BlogsEntryPermission.contains(permissionChecker, entry, ActionKeys.UPDATE) %>">
				<ul class="edit-actions entry icons-container lfr-meta-actions">
					<c:if test="<%= BlogsEntryPermission.contains(permissionChecker, entry, ActionKeys.UPDATE) %>">
						<li class="edit-entry">
							<portlet:renderURL var="editEntryURL">
								<portlet:param name="mvcRenderCommandName" value="/blogs/edit_entry" />
								<portlet:param name="redirect" value="<%= currentURL %>" />
								<portlet:param name="backURL" value="<%= currentURL %>" />
								<portlet:param name="entryId" value="<%= String.valueOf(entry.getEntryId()) %>" />
							</portlet:renderURL>

							<liferay-ui:icon
								iconCssClass="icon-edit"
								label="<%= true %>"
								message="edit"
								url="<%= editEntryURL %>"
							/>
						</li>
					</c:if>

					<c:if test="<%= showEditEntryPermissions && BlogsEntryPermission.contains(permissionChecker, entry, ActionKeys.PERMISSIONS) %>">
						<li class="edit-entry-permissions">
							<liferay-security:permissionsURL
								modelResource="<%= BlogsEntry.class.getName() %>"
								modelResourceDescription="<%= entry.getTitle() %>"
								resourceGroupId="<%= String.valueOf(entry.getGroupId()) %>"
								resourcePrimKey="<%= String.valueOf(entry.getEntryId()) %>"
								var="permissionsEntryURL"
								windowState="<%= LiferayWindowState.POP_UP.toString() %>"
							/>

							<liferay-ui:icon
								iconCssClass="icon-lock"
								label="<%= true %>"
								message="permissions"
								method="get"
								url="<%= permissionsEntryURL %>"
								useDialog="<%= true %>"
							/>
						</li>
					</c:if>

					<c:if test="<%= BlogsEntryPermission.contains(permissionChecker, entry, ActionKeys.DELETE) %>">
						<li class="delete-entry">
							<portlet:renderURL var="viewURL">
								<portlet:param name="mvcRenderCommandName" value="/blogs/view" />
							</portlet:renderURL>

							<portlet:actionURL name="/blogs/edit_entry" var="deleteEntryURL">
								<portlet:param name="<%= Constants.CMD %>" value="<%= TrashUtil.isTrashEnabled(scopeGroupId) ? Constants.MOVE_TO_TRASH : Constants.DELETE %>" />
								<portlet:param name="redirect" value="<%= viewURL %>" />
								<portlet:param name="entryId" value="<%= String.valueOf(entry.getEntryId()) %>" />
							</portlet:actionURL>

							<liferay-ui:icon-delete
								label="<%= true %>"
								trash="<%= TrashUtil.isTrashEnabled(scopeGroupId) %>"
								url="<%= deleteEntryURL %>"
							/>
						</li>
					</c:if>
				</ul>
			</c:if>

			<div class="entry-body">
				<c:choose>
					<c:when test="<%= blogsPortletInstanceSettings.getDisplayStyle().equals(BlogsUtil.DISPLAY_STYLE_ABSTRACT) && !viewSingleEntry %>">
						<c:if test="<%= entry.isSmallImage() %>">
							<div class="asset-small-image">
								<img alt="" class="asset-small-image" src="<%= HtmlUtil.escape(entry.getSmallImageURL(themeDisplay)) %>" width="150" />
							</div>
						</c:if>

						<%
						String summary = entry.getDescription();

						if (Validator.isNull(summary)) {
							summary = entry.getContent();
						}
						%>

						<p>
							<%= StringUtil.shorten(HtmlUtil.stripHtml(summary), pageAbstractLength) %>
						</p>

						<div class="read-more">
							<aui:a href="<%= viewEntryURL %>"><liferay-ui:message arguments='<%= new Object[] {"hide-accessible", HtmlUtil.escape(entry.getTitle())} %>' key="read-more-x-about-x" translateArguments="<%= false %>" /> &raquo;</aui:a>
						</div>
					</c:when>
					<c:when test="<%= blogsPortletInstanceSettings.getDisplayStyle().equals(BlogsUtil.DISPLAY_STYLE_FULL_CONTENT) || viewSingleEntry %>">
						<div>
							<%= entry.getContent() %>
						</div>

						<liferay-ui:custom-attributes-available className="<%= BlogsEntry.class.getName() %>">
							<liferay-ui:custom-attribute-list
								className="<%= BlogsEntry.class.getName() %>"
								classPK="<%= entry.getEntryId() %>"
								editable="<%= false %>"
								label="<%= true %>"
							/>
						</liferay-ui:custom-attributes-available>

					</c:when>
					<c:when test="<%= blogsPortletInstanceSettings.getDisplayStyle().equals(BlogsUtil.DISPLAY_STYLE_TITLE) && !viewSingleEntry %>">
						<div class="read-more">
							<aui:a href="<%= viewEntryURL %>"><liferay-ui:message arguments='<%= new Object[] {"hide-accessible", HtmlUtil.escape(entry.getTitle())} %>' key="read-more-x-about-x" translateArguments="<%= false %>" /> &raquo;</aui:a>
						</div>
					</c:when>
				</c:choose>
			</div>

			<div class="entry-footer">
				<div class="entry-author">
					<liferay-ui:user-display
						userId="<%= entry.getUserId() %>"
						userName="<%= entry.getUserName() %>"
					>
						<liferay-ui:message arguments="<%= LanguageUtil.getTimeDescription(request, System.currentTimeMillis() - entry.getCreateDate().getTime(), true) %>" key="x-ago" translateArguments="<%= false %>" />
					</liferay-ui:user-display>
				</div>

				<div class="entry-social">
					<c:if test="<%= blogsPortletInstanceSettings.isEnableRatings() %>">
						<div class="ratings">
							<liferay-ui:ratings
								className="<%= BlogsEntry.class.getName() %>"
								classPK="<%= entry.getEntryId() %>"
							/>
						</div>
					</c:if>

					<c:if test="<%= blogsPortletInstanceSettings.isEnableFlags() %>">
						<div class="flags">
							<liferay-ui:flags
								className="<%= BlogsEntry.class.getName() %>"
								classPK="<%= entry.getEntryId() %>"
								contentTitle="<%= entry.getTitle() %>"
								reportedUserId="<%= entry.getUserId() %>"
							/>
						</div>
					</c:if>

					<c:if test='<%= blogsPortletInstanceSettings.isEnableSocialBookmarks() && blogsPortletInstanceSettings.getSocialBookmarksDisplayPosition().equals("bottom") %>'>
						<div class="social-bookmarks">
							<liferay-ui:social-bookmarks
								contentId="<%= String.valueOf(entry.getEntryId()) %>"
								displayStyle="<%= blogsPortletInstanceSettings.getSocialBookmarksDisplayStyle() %>"
								target="_blank"
								title="<%= entry.getTitle() %>"
								types="<%= blogsPortletInstanceSettings.getSocialBookmarksTypes() %>"
								url="<%= PortalUtil.getCanonicalURL(bookmarkURL.toString(), themeDisplay, layout) %>"
							/>
						</div>
					</c:if>
				</div>
			</div>

			<div class="entry-metadata">
				<liferay-ui:asset-categories-available
					className="<%= BlogsEntry.class.getName() %>"
					classPK="<%= entry.getEntryId() %>"
				>
					<h2><liferay-ui:message key="categories" /></h2>

					<div class="entry-categories">
						<liferay-ui:asset-categories-summary
							className="<%= BlogsEntry.class.getName() %>"
							classPK="<%= entry.getEntryId() %>"
							portletURL="<%= renderResponse.createRenderURL() %>"
						/>
					</div>
				</liferay-ui:asset-categories-available>

				<liferay-ui:asset-tags-available
					className="<%= BlogsEntry.class.getName() %>"
					classPK="<%= entry.getEntryId() %>"
				>
					<div class="entry-tags">
						<h2><liferay-ui:message key="tags" /></h2>

						<liferay-ui:asset-tags-summary
							className="<%= BlogsEntry.class.getName() %>"
							classPK="<%= entry.getEntryId() %>"
							portletURL="<%= renderResponse.createRenderURL() %>"
						/>
					</div>
				</liferay-ui:asset-tags-available>

				<c:if test="<%= blogsPortletInstanceSettings.getDisplayStyle().equals(BlogsUtil.DISPLAY_STYLE_FULL_CONTENT) || viewSingleEntry %>">
					<c:if test="<%= blogsPortletInstanceSettings.isEnableRelatedAssets() %>">
						<div class="entry-links">
							<liferay-ui:asset-links
								assetEntryId="<%= (assetEntry != null) ? assetEntry.getEntryId() : 0 %>"
								className="<%= BlogsEntry.class.getName() %>"
								classPK="<%= entry.getEntryId() %>"
							/>
						</div>
					</c:if>
				</c:if>
			</div>
		</div>
	</c:when>
	<c:otherwise>

		<%
		if (searchContainer != null) {
			searchContainer.setTotal(searchContainer.getTotal() - 1);
		}
		%>

	</c:otherwise>
</c:choose>