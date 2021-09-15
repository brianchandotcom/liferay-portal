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
SearchContainer<BaseModel<?>> searchContainer = (SearchContainer)request.getAttribute("view_entry_content.jsp-searchContainer");

BlogsEntry entry = (BlogsEntry)request.getAttribute("view_entry_content.jsp-entry");

BlogsPortletInstanceConfiguration blogsPortletInstanceConfiguration = BlogsPortletInstanceConfigurationUtil.getBlogsPortletInstanceConfiguration(themeDisplay);
%>

<c:choose>
	<c:when test="<%= entry.isVisible() || (entry.getUserId() == user.getUserId()) || BlogsEntryPermission.contains(permissionChecker, entry, ActionKeys.UPDATE) %>">
		<portlet:renderURL var="viewEntryURL">
			<portlet:param name="mvcRenderCommandName" value="/blogs/view_entry" />
			<portlet:param name="redirect" value="<%= currentURL %>" />

			<c:choose>
				<c:when test="<%= Validator.isNotNull(entry.getUrlTitle()) %>">
					<portlet:param name="urlTitle" value="<%= entry.getUrlTitle() %>" />
				</c:when>
				<c:otherwise>
					<portlet:param name="entryId" value="<%= String.valueOf(entry.getEntryId()) %>" />
				</c:otherwise>
			</c:choose>
		</portlet:renderURL>

		<div class="widget-mode-simple-entry">
			<clay:content-row
				cssClass="widget-topbar"
			>
				<clay:content-col
					expand="<%= true %>"
				>
					<h3 class="title">
						<aui:a cssClass="title-link" href="<%= viewEntryURL %>"><%= HtmlUtil.escape(BlogsEntryUtil.getDisplayTitle(resourceBundle, entry)) %></aui:a>
					</h3>

					<%
					String subtitle = entry.getSubtitle();
					%>

					<c:if test="<%= Objects.equals(blogsPortletInstanceConfiguration.displayStyle(), BlogsUtil.DISPLAY_STYLE_FULL_CONTENT) && Validator.isNotNull(subtitle) %>">
						<h4 class="sub-title"><%= HtmlUtil.escape(subtitle) %></h4>
					</c:if>
				</clay:content-col>

				<clay:content-col>

					<%
					BlogsEntryActionDropdownItemsProvider blogsEntryActionDropdownItemsProvider = new BlogsEntryActionDropdownItemsProvider(renderRequest, renderResponse, permissionChecker, resourceBundle, trashHelper);
					%>

					<clay:dropdown-actions
						additionalProps='<%=
							HashMapBuilder.<String, Object>put(
								"trashEnabled", trashHelper.isTrashEnabled(themeDisplay.getScopeGroupId())
							).build()
						%>'
						dropdownItems="<%= blogsEntryActionDropdownItemsProvider.getActionDropdownItems(entry) %>"
						propsTransformer="blogs_admin/js/ElementsPropsTransformer"
					/>
				</clay:content-col>
			</clay:content-row>

			<clay:content-row
				cssClass="widget-metadata"
			>

				<%
				User entryUser = UserLocalServiceUtil.fetchUser(entry.getUserId());

				String entryUserURL = StringPool.BLANK;

				if ((entryUser != null) && !entryUser.isDefaultUser() && !user.isDefaultUser()) {
					entryUserURL = entryUser.getDisplayURL(themeDisplay);
				}
				%>

				<clay:content-col
					cssClass="inline-item-before"
				>
					<liferay-ui:user-portrait
						user="<%= entryUser %>"
					/>
				</clay:content-col>

				<clay:content-col
					expand="<%= true %>"
				>
					<clay:content-row>
						<clay:content-col
							expand="<%= true %>"
						>
							<div class="text-truncate-inline">
								<a class="text-truncate username" href="<%= entryUserURL %>"><%= HtmlUtil.escape(entry.getUserName()) %></a>
							</div>

							<div class="text-secondary">
								<span class="sr-only"><liferay-ui:message key="published-date" /></span><liferay-ui:message arguments="<%= LanguageUtil.getTimeDescription(request, System.currentTimeMillis() - entry.getStatusDate().getTime(), true) %>" key="x-ago" translateArguments="<%= false %>" />

								<c:if test="<%= blogsPortletInstanceConfiguration.enableReadingTime() %>">
									- <liferay-reading-time:reading-time displayStyle="descriptive" model="<%= entry %>" />
								</c:if>

								<c:if test="<%= blogsPortletInstanceConfiguration.enableViewCount() %>">

									<%
									AssetEntry assetEntry = BlogsEntryAssetEntryUtil.getAssetEntry(request, entry);
									%>

									- <liferay-ui:message arguments="<%= assetEntry.getViewCount() %>" key='<%= (assetEntry.getViewCount() == 1) ? "x-view" : "x-views" %>' />
								</c:if>
							</div>
						</clay:content-col>
					</clay:content-row>
				</clay:content-col>
			</clay:content-row>

			<div class="widget-content" id="<portlet:namespace /><%= entry.getEntryId() %>">

				<%
				String coverImageURL = entry.getCoverImageURL(themeDisplay);
				%>

				<liferay-util:include page="/blogs/entry_cover_image_caption.jsp" servletContext="<%= application %>">
					<liferay-util:param name="coverImageCaption" value="<%= entry.getCoverImageCaption() %>" />
					<liferay-util:param name="coverImageURL" value="<%= entry.getCoverImageURL(themeDisplay) %>" />
					<liferay-util:param name="viewEntryURL" value="<%= viewEntryURL %>" />
				</liferay-util:include>

				<c:choose>
					<c:when test="<%= blogsPortletInstanceConfiguration.displayStyle().equals(BlogsUtil.DISPLAY_STYLE_ABSTRACT) %>">
						<c:if test="<%= entry.isSmallImage() && Validator.isNull(coverImageURL) %>">
							<div class="asset-small-image">
								<img alt="" class="asset-small-image img-thumbnail" src="<%= HtmlUtil.escape(entry.getSmallImageURL(themeDisplay)) %>" width="150" />
							</div>
						</c:if>

						<%
						String summary = entry.getDescription();

						if (Validator.isNull(summary)) {
							summary = entry.getContent();
						}
						%>

						<p>
							<%= StringUtil.shorten(HtmlUtil.stripHtml(summary), PropsValues.BLOGS_PAGE_ABSTRACT_LENGTH) %>
						</p>
					</c:when>
					<c:when test="<%= blogsPortletInstanceConfiguration.displayStyle().equals(BlogsUtil.DISPLAY_STYLE_FULL_CONTENT) %>">
						<%= entry.getContent() %>
					</c:when>
				</c:choose>

				<%
				request.setAttribute("entry_toolbar.jsp-entry", entry);
				%>

				<liferay-util:include page="/blogs/entry_toolbar.jsp" servletContext="<%= application %>" />
			</div>

			<c:if test="<%= Objects.equals(blogsPortletInstanceConfiguration.displayStyle(), BlogsUtil.DISPLAY_STYLE_FULL_CONTENT) %>">
				<liferay-asset:asset-tags-available
					className="<%= BlogsEntry.class.getName() %>"
					classPK="<%= entry.getEntryId() %>"
				>
					<div class="entry-tags">
						<liferay-asset:asset-tags-summary
							className="<%= BlogsEntry.class.getName() %>"
							classPK="<%= entry.getEntryId() %>"
							portletURL="<%= renderResponse.createRenderURL() %>"
						/>
					</div>
				</liferay-asset:asset-tags-available>

				<c:if test="<%= blogsPortletInstanceConfiguration.enableRelatedAssets() %>">
					<div class="entry-links">

						<%
						AssetEntry assetEntry = BlogsEntryAssetEntryUtil.getAssetEntry(request, entry);
						%>

						<liferay-asset:asset-links
							assetEntryId="<%= (assetEntry != null) ? assetEntry.getEntryId() : 0 %>"
							className="<%= BlogsEntry.class.getName() %>"
							classPK="<%= entry.getEntryId() %>"
						/>
					</div>
				</c:if>

				<liferay-asset:asset-categories-available
					className="<%= BlogsEntry.class.getName() %>"
					classPK="<%= entry.getEntryId() %>"
				>
					<div class="entry-categories">
						<liferay-asset:asset-categories-summary
							className="<%= BlogsEntry.class.getName() %>"
							classPK="<%= entry.getEntryId() %>"
							portletURL="<%= renderResponse.createRenderURL() %>"
						/>
					</div>
				</liferay-asset:asset-categories-available>
			</c:if>
		</div>
	</c:when>
	<c:otherwise>

		<%
		if (searchContainer != null) {
			searchContainer.setResultsAndTotal(searchContainer::getResults, searchContainer.getTotal() - 1);
		}
		%>

	</c:otherwise>
</c:choose>