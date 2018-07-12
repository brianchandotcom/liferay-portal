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
  BlogsEntry blogsEntry = (BlogsEntry)request.getAttribute("view_entry_related.jsp-blogs_entry");
%>

<c:if test="<%= blogsEntry != null %>">
  <div class="col-lg-6">
    <div class="card">

      <%
      String imageURL = blogsEntry.getCoverImageURL(themeDisplay);

      if (Validator.isNull(imageURL)) {
        imageURL = blogsEntry.getSmallImageURL(themeDisplay);
      }
      %>

      <c:if test="<%= Validator.isNotNull(imageURL) %>">
        <div class="card-header">
          <div class="aspect-ratio aspect-ratio-8-to-3">
            <img alt="thumbnail" class="aspect-ratio-item-center-middle aspect-ratio-item-fluid" src="<%= HtmlUtil.escape(imageURL) %>" />
          </div>
        </div>
      </c:if>

      <div class="card-body widget-topbar">
        <div class="autofit-row card-title">
          <div class="autofit-col autofit-col-expand">
            <portlet:renderURL var="previousEntryURL">
              <portlet:param name="mvcRenderCommandName" value="/blogs/view_entry" />
              <portlet:param name="redirect" value="<%= redirect %>" />
              <portlet:param name="urlTitle" value="<%= blogsEntry.getUrlTitle() %>" />
            </portlet:renderURL>

            <liferay-util:html-top
              outputKey="blogs_previous_entry_link"
            >
              <link href="<%= previousEntryURL.toString() %>" rel="prev" />
            </liferay-util:html-top>

            <h3 class="title"><a class="title-link" href="<%= previousEntryURL %>">
              <%= HtmlUtil.escape(BlogsEntryUtil.getDisplayTitle(resourceBundle,
                  blogsEntry)) %></a>
            </h3>
          </div>
        </div>

        <div class="autofit-row widget-metadata">
          <div class="autofit-col inline-item-before">

            <%
            User previousEntryUser = UserLocalServiceUtil.fetchUser(blogsEntry.getUserId());

            String previousEntryUserURL = StringPool.BLANK;

            if ((previousEntryUser != null) && !previousEntryUser.isDefaultUser()) {
              previousEntryUserURL = previousEntryUser.getDisplayURL(themeDisplay);
            }
            %>

            <liferay-ui:user-portrait
              user="<%= previousEntryUser %>"
            />
          </div>

          <div class="autofit-col autofit-col-expand">
            <div class="autofit-row">
              <div class="autofit-col autofit-col-expand">
                <div class="text-truncate-inline">
                  <a class="text-truncate username" href="<%= previousEntryUserURL %>"><%= blogsEntry.getUserName() %></a>
                </div>

                <div class="text-secondary">
                  <%= DateUtil.getDate(blogsEntry.getStatusDate(), "dd MMM", locale) %>

                  <c:if test="<%= blogsPortletInstanceConfiguration.enableReadingTime() %>">
                    - <liferay-reading-time:reading-time displayStyle="descriptive" model="<%= blogsEntry %>" />
                  </c:if>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>

      <div class="card-footer">
        <div class="card-row">
          <div class="autofit-float autofit-row autofit-row-center widget-toolbar">
            <c:if test="<%= blogsPortletInstanceConfiguration.enableComments() %>">
              <div class="autofit-col">
                <portlet:renderURL var="previousEntryViewCommentsURL">
                  <portlet:param name="mvcRenderCommandName" value="/blogs/view_entry" />
                  <portlet:param name="scroll" value='<%= renderResponse.getNamespace() + "discussionContainer" %>' />

                  <c:choose>
                    <c:when test="<%= Validator.isNotNull(blogsEntry.getUrlTitle()) %>">
                      <portlet:param name="urlTitle" value="<%= blogsEntry.getUrlTitle() %>" />
                    </c:when>
                    <c:otherwise>
                      <portlet:param name="entryId" value="<%= String.valueOf(blogsEntry.getEntryId()) %>" />
                    </c:otherwise>
                  </c:choose>
                </portlet:renderURL>

                <a class="btn btn-outline-borderless btn-outline-secondary btn-sm" href="<%= previousEntryViewCommentsURL %>">
                  <span class="inline-item inline-item-before">
                    <clay:icon
                      symbol="comments"
                    />
                  </span>

                  <%= CommentManagerUtil.getCommentsCount(BlogsEntry.class.getName(), blogsEntry.getEntryId()) %>
                </a>
              </div>
            </c:if>

            <c:if test="<%= blogsPortletInstanceConfiguration.enableRatings() %>">
              <div class="autofit-col">

                <%
                RatingsEntry previousEntryRatingsEntry = null;
                RatingsStats previousEntryRatingsStats = RatingsStatsLocalServiceUtil.fetchStats(BlogsEntry.class.getName(), blogsEntry.getEntryId());

                if (previousEntryRatingsStats != null) {
                  previousEntryRatingsEntry = RatingsEntryLocalServiceUtil.fetchEntry(themeDisplay.getUserId(), BlogsEntry.class.getName(), blogsEntry.getEntryId());
                }
                %>

                <liferay-ui:ratings
                  className="<%= BlogsEntry.class.getName() %>"
                  classPK="<%= blogsEntry.getEntryId() %>"
                  inTrash="<%= blogsEntry.isInTrash() %>"
                  ratingsEntry="<%= previousEntryRatingsEntry %>"
                  ratingsStats="<%= previousEntryRatingsStats %>"
                />
              </div>
            </c:if>

            <div class="autofit-col autofit-col-end">
              <liferay-portlet:renderURL varImpl="previousEntryBookmarksURL">
                <portlet:param name="mvcRenderCommandName" value="/blogs/view_entry" />

                <c:choose>
                  <c:when test="<%= Validator.isNotNull(blogsEntry.getUrlTitle()) %>">
                    <portlet:param name="urlTitle" value="<%= blogsEntry.getUrlTitle() %>" />
                  </c:when>
                  <c:otherwise>
                    <portlet:param name="entryId" value="<%= String.valueOf(blogsEntry.getEntryId()) %>" />
                  </c:otherwise>
                </c:choose>
              </liferay-portlet:renderURL>

              <liferay-social-bookmarks:bookmarks
                className="<%= BlogsEntry.class.getName() %>"
                classPK="<%= blogsEntry.getEntryId() %>"
                displayStyle="menu"
                target="_blank"
                title="<%= BlogsEntryUtil.getDisplayTitle(resourceBundle, blogsEntry) %>"
                types="<%= SocialBookmarksUtil.getSocialBookmarksTypes(blogsPortletInstanceConfiguration) %>"
                urlImpl="<%= previousEntryBookmarksURL %>"
              />
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</c:if>