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
String redirect = ParamUtil.getString(request, "redirect");

BlogsEntry blogsEntry = (BlogsEntry)request.getAttribute("view_entry_related.jsp-blogs_entry");

BlogsPortletInstanceConfiguration blogsPortletInstanceConfiguration = BlogsPortletInstanceConfigurationUtil.getBlogsPortletInstanceConfiguration(themeDisplay);
%>

<c:if test="<%= blogsEntry != null %>">
	<div class="card">

		<%
		String imageURL = blogsEntry.getCoverImageURL(themeDisplay);

		if (Validator.isNull(imageURL)) {
			imageURL = blogsEntry.getSmallImageURL(themeDisplay);
		}

		if (Validator.isNull(imageURL)) {
			imageURL = PortalUtil.getPathContext(request) + "/images/cover_image_placeholder.jpg";
		}
		%>

		<div class="card-header">
			<div class="aspect-ratio aspect-ratio-8-to-3">
				<img alt="thumbnail" class="aspect-ratio-item-center-middle aspect-ratio-item-fluid" src="<%= HtmlUtil.escape(imageURL) %>" />
			</div>
		</div>

		<div class="card-body widget-topbar">
			<div class="card-title">
				<clay:content-col
					expand="<%= true %>"
				>
					<portlet:renderURL var="blogsEntryURL">
						<portlet:param name="mvcRenderCommandName" value="/blogs/view_entry" />
						<portlet:param name="redirect" value="<%= redirect %>" />
						<portlet:param name="urlTitle" value="<%= blogsEntry.getUrlTitle() %>" />
					</portlet:renderURL>

					<liferay-util:html-top
						outputKey="blogs_previous_entry_link"
					>
						<link href="<%= blogsEntryURL.toString() %>" rel="prev" />
					</liferay-util:html-top>

					<h3 class="title"><a class="title-link" href="<%= blogsEntryURL %>">
						<%= HtmlUtil.escape(BlogsEntryUtil.getDisplayTitle(resourceBundle, blogsEntry)) %></a>
					</h3>
				</clay:content-col>
			</div>

			<clay:content-row
				cssClass="autofit-padded-no-gutters widget-metadata"
			>

				<%
				User blogsEntryUser = UserLocalServiceUtil.fetchUser(blogsEntry.getUserId());

				String blogsEntryUserURL = StringPool.BLANK;

				if ((blogsEntryUser != null) && !blogsEntryUser.isDefaultUser() && !user.isDefaultUser()) {
					blogsEntryUserURL = blogsEntryUser.getDisplayURL(themeDisplay);
				}
				%>

				<clay:content-col>
					<liferay-ui:user-portrait
						user="<%= blogsEntryUser %>"
					/>
				</clay:content-col>

				<clay:content-col
					expand="<%= true %>"
				>
					<clay:content-section>
						<div class="text-truncate-inline">
							<a class="text-truncate username" href="<%= blogsEntryUserURL %>"><%= HtmlUtil.escape(blogsEntry.getUserName()) %></a>
						</div>

						<div class="text-secondary">
							<%= DateUtil.getDate(blogsEntry.getStatusDate(), "dd MMM", locale) %>

							<c:if test="<%= blogsPortletInstanceConfiguration.enableReadingTime() %>">
								- <liferay-reading-time:reading-time displayStyle="descriptive" model="<%= blogsEntry %>" />
							</c:if>
						</div>
					</clay:content-section>
				</clay:content-col>
			</clay:content-row>
		</div>

		<div class="card-footer">

			<%
			request.setAttribute("entry_toolbar.jsp-entry", blogsEntry);
			%>

			<liferay-util:include page="/blogs/entry_toolbar.jsp" servletContext="<%= application %>">
				<liferay-util:param name="showOnlyIcons" value="<%= Boolean.TRUE.toString() %>" />
			</liferay-util:include>
		</div>
	</div>
</c:if>