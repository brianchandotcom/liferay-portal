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

<liferay-util:dynamic-include key="com.liferay.blogs.web#/blogs/view_entry.jsp#pre" />

<%
String redirect = ParamUtil.getString(request, "redirect");

if (Validator.isNull(redirect)) {
	PortletURL portletURL = renderResponse.createRenderURL();

	portletURL.setParameter("mvcRenderCommandName", "/blogs/view");

	redirect = portletURL.toString();
}

BlogsEntry entry = (BlogsEntry)request.getAttribute(WebKeys.BLOGS_ENTRY);

long entryId = ParamUtil.getLong(request, "entryId", entry.getEntryId());

AssetEntry assetEntry = AssetEntryLocalServiceUtil.getEntry(BlogsEntry.class.getName(), entry.getEntryId());

AssetEntryServiceUtil.incrementViewCounter(assetEntry);

assetHelper.addLayoutTags(request, AssetTagLocalServiceUtil.getTags(BlogsEntry.class.getName(), entry.getEntryId()));

RatingsEntry ratingsEntry = null;
RatingsStats ratingsStats = RatingsStatsLocalServiceUtil.fetchStats(BlogsEntry.class.getName(), entry.getEntryId());

if (ratingsStats != null) {
	ratingsEntry = RatingsEntryLocalServiceUtil.fetchEntry(themeDisplay.getUserId(), BlogsEntry.class.getName(), entry.getEntryId());
}

request.setAttribute(WebKeys.LAYOUT_ASSET_ENTRY, assetEntry);

request.setAttribute("view_entry_content.jsp-entry", entry);

request.setAttribute("view_entry_content.jsp-assetEntry", assetEntry);

request.setAttribute("view_entry_content.jsp-ratingsEntry", ratingsEntry);
request.setAttribute("view_entry_content.jsp-ratingsStats", ratingsStats);

boolean portletTitleBasedNavigation = GetterUtil.getBoolean(portletConfig.getInitParameter("portlet-title-based-navigation"));

if (portletTitleBasedNavigation) {
	portletDisplay.setShowBackIcon(true);
	portletDisplay.setURLBack(redirect);

	renderResponse.setTitle(BlogsEntryUtil.getDisplayTitle(resourceBundle, entry));
}
%>

<portlet:actionURL name="/blogs/edit_entry" var="editEntryURL" />

<aui:form action="<%= editEntryURL %>" method="post" name="fm1" onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "saveEntry();" %>'>
	<aui:input name="<%= Constants.CMD %>" type="hidden" />
	<aui:input name="entryId" type="hidden" value="<%= String.valueOf(entryId) %>" />

	<div class="widget-mode-detail">
		<liferay-util:include page="/blogs/view_entry_content_detail.jsp" servletContext="<%= application %>" />
	</div>
</aui:form>

<div class="container-fluid">
	<c:if test="<%= PropsValues.BLOGS_ENTRY_PREVIOUS_AND_NEXT_NAVIGATION_ENABLED %>">

		<%
		BlogsEntry[] prevAndNext = BlogsEntryServiceUtil.getEntriesPrevAndNext(entryId);

		BlogsEntry previousEntry = prevAndNext[0];
		BlogsEntry nextEntry = prevAndNext[2];
		%>

		<c:if test="<%= (previousEntry != null) || (nextEntry != null) %>">
			<div class="row">
				<div class="col-md-10 col-md-offset-1 entry-navigation">
					<h2><strong><liferay-ui:message key="more-blog-entries" /></strong></h2>

					<div class="row widget-mode-card">
						<c:if test="<%= previousEntry != null %>">
							<div class="col-lg-6">
								<div class="card">

									<%
									String smallImageURL = previousEntry.getSmallImageURL(themeDisplay);
									%>

									<c:if test="<%= Validator.isNotNull(smallImageURL) %>">
										<div class="card-header">
											<div class="aspect-ratio aspect-ratio-8-to-3">
												<img alt="thumbnail" class="aspect-ratio-item-center-middle aspect-ratio-item-fluid" src="<%= HtmlUtil.escape(smallImageURL) %>" />
											</div>
										</div>
									</c:if>

									<div class="card-body widget-topbar">
										<div class="autofit-row card-title">
											<div class="autofit-col autofit-col-expand">
												<portlet:renderURL var="previousEntryURL">
													<portlet:param name="mvcRenderCommandName" value="/blogs/view_entry" />
													<portlet:param name="redirect" value="<%= redirect %>" />
													<portlet:param name="urlTitle" value="<%= previousEntry.getUrlTitle() %>" />
												</portlet:renderURL>

												<liferay-util:html-top
													outputKey="blogs_previous_entry_link"
												>
													<link href="<%= previousEntryURL.toString() %>" rel="prev" />
												</liferay-util:html-top>

												<a class="title-link" href="<%= previousEntryURL %>">
													<h3 class="title"><%= HtmlUtil.escape(BlogsEntryUtil.getDisplayTitle(resourceBundle, previousEntry)) %></h3>
												</a>
											</div>
										</div>

										<div class="autofit-row widget-metadata">
											<div class="autofit-col inline-item-before">

												<%
												User previousEntryUser = UserLocalServiceUtil.fetchUser(previousEntry.getUserId());

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
														<a class="username" href="<%= previousEntryUserURL %>"><%= previousEntry.getUserName() %></a>

														<div>
															<%= DateUtil.getDate(previousEntry.getStatusDate(), "dd MMM", locale) %>

															<c:if test="<%= blogsPortletInstanceConfiguration.enableReadingTime() %>">
																- <liferay-reading-time:reading-time displayStyle="descriptive" model="<%= previousEntry %>" />
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
																<c:when test="<%= Validator.isNotNull(previousEntry.getUrlTitle()) %>">
																	<portlet:param name="urlTitle" value="<%= previousEntry.getUrlTitle() %>" />
																</c:when>
																<c:otherwise>
																	<portlet:param name="entryId" value="<%= String.valueOf(previousEntry.getEntryId()) %>" />
																</c:otherwise>
															</c:choose>
														</portlet:renderURL>

														<a class="btn btn-outline-borderless btn-outline-secondary btn-sm" href="<%= previousEntryViewCommentsURL %>">
															<span class="inline-item inline-item-before">
																<clay:icon
																	symbol="comments"
																/>
															</span>

															<%= CommentManagerUtil.getCommentsCount(BlogsEntry.class.getName(), previousEntry.getEntryId()) %>
														</a>
													</div>
												</c:if>

												<c:if test="<%= blogsPortletInstanceConfiguration.enableRatings() %>">
													<div class="autofit-col">

														<%
														RatingsEntry previousEntryRatingsEntry = null;
														RatingsStats previousEntryRatingsStats = RatingsStatsLocalServiceUtil.fetchStats(BlogsEntry.class.getName(), previousEntry.getEntryId());

														if (previousEntryRatingsStats != null) {
															previousEntryRatingsEntry = RatingsEntryLocalServiceUtil.fetchEntry(themeDisplay.getUserId(), BlogsEntry.class.getName(), previousEntry.getEntryId());
														}
														%>

														<liferay-ui:ratings
															className="<%= BlogsEntry.class.getName() %>"
															classPK="<%= previousEntry.getEntryId() %>"
															inTrash="<%= previousEntry.isInTrash() %>"
															ratingsEntry="<%= previousEntryRatingsEntry %>"
															ratingsStats="<%= previousEntryRatingsStats %>"
														/>
													</div>
												</c:if>

												<div class="autofit-col autofit-col-end">
													<portlet:renderURL var="previousEntryBookmarksURL">
														<portlet:param name="mvcRenderCommandName" value="/blogs/view_entry" />

														<c:choose>
															<c:when test="<%= Validator.isNotNull(previousEntry.getUrlTitle()) %>">
																<portlet:param name="urlTitle" value="<%= previousEntry.getUrlTitle() %>" />
															</c:when>
															<c:otherwise>
																<portlet:param name="entryId" value="<%= String.valueOf(previousEntry.getEntryId()) %>" />
															</c:otherwise>
														</c:choose>
													</portlet:renderURL>

													<liferay-social-bookmarks:bookmarks
														className="<%= BlogsEntry.class.getName() %>"
														classPK="<%= previousEntry.getEntryId() %>"
														displayStyle="menu"
														target="_blank"
														title="<%= BlogsEntryUtil.getDisplayTitle(resourceBundle, previousEntry) %>"
														types="<%= SocialBookmarksUtil.getSocialBookmarksTypes(blogsPortletInstanceConfiguration) %>"
														url="<%= PortalUtil.getCanonicalURL(previousEntryBookmarksURL.toString(), themeDisplay, layout) %>"
													/>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
						</c:if>

						<c:if test="<%= nextEntry != null %>">
							<div class="col-lg-6">
								<div class="card">

									<%
									String smallImageURL = nextEntry.getSmallImageURL(themeDisplay);
									%>

									<c:if test="<%= Validator.isNotNull(smallImageURL) %>">
										<div class="card-header">
											<div class="aspect-ratio aspect-ratio-8-to-3">
												<img alt="thumbnail" class="aspect-ratio-item-center-middle aspect-ratio-item-fluid" src="<%= HtmlUtil.escape(smallImageURL) %>" />
											</div>
										</div>
									</c:if>

									<div class="card-body widget-topbar">
										<div class="autofit-row card-title">
											<div class="autofit-col autofit-col-expand">
												<portlet:renderURL var="nextEntryURL">
													<portlet:param name="mvcRenderCommandName" value="/blogs/view_entry" />
													<portlet:param name="redirect" value="<%= redirect %>" />
													<portlet:param name="urlTitle" value="<%= nextEntry.getUrlTitle() %>" />
												</portlet:renderURL>

												<liferay-util:html-top
													outputKey="blogs_next_entry_link"
												>
													<link href="<%= nextEntryURL.toString() %>" rel="next" />
												</liferay-util:html-top>

												<a class="title-link" href="<%= nextEntryURL %>">
													<h3 class="title"><%= HtmlUtil.escape(BlogsEntryUtil.getDisplayTitle(resourceBundle, nextEntry)) %></h3>
												</a>
											</div>
										</div>

										<div class="autofit-row widget-metadata">
											<div class="autofit-col inline-item-before">

												<%
												User nextEntryUser = UserLocalServiceUtil.fetchUser(nextEntry.getUserId());

												String nextEntryUserURL = StringPool.BLANK;

												if ((nextEntryUser != null) && !nextEntryUser.isDefaultUser()) {
													nextEntryUserURL = nextEntryUser.getDisplayURL(themeDisplay);
												}
												%>

												<liferay-ui:user-portrait
													user="<%= nextEntryUser %>"
												/>
											</div>

											<div class="autofit-col autofit-col-expand">
												<div class="autofit-row">
													<div class="autofit-col autofit-col-expand">
														<a class="username" href="<%= nextEntryUserURL %>"><%= nextEntry.getUserName() %></a>

														<div>
															<%= DateUtil.getDate(nextEntry.getStatusDate(), "dd MMM", locale) %>

															<c:if test="<%= blogsPortletInstanceConfiguration.enableReadingTime() %>">
																- <liferay-reading-time:reading-time displayStyle="descriptive" model="<%= nextEntry %>" />
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
														<portlet:renderURL var="nextEntryViewCommentsURL">
															<portlet:param name="mvcRenderCommandName" value="/blogs/view_entry" />
															<portlet:param name="scroll" value='<%= renderResponse.getNamespace() + "discussionContainer" %>' />

															<c:choose>
																<c:when test="<%= Validator.isNotNull(nextEntry.getUrlTitle()) %>">
																	<portlet:param name="urlTitle" value="<%= nextEntry.getUrlTitle() %>" />
																</c:when>
																<c:otherwise>
																	<portlet:param name="entryId" value="<%= String.valueOf(nextEntry.getEntryId()) %>" />
																</c:otherwise>
															</c:choose>
														</portlet:renderURL>

														<a class="btn btn-outline-borderless btn-outline-secondary btn-sm" href="<%= nextEntryViewCommentsURL %>">
															<span class="inline-item inline-item-before">
																<clay:icon
																	symbol="comments"
																/>
															</span>

															<%= CommentManagerUtil.getCommentsCount(BlogsEntry.class.getName(), nextEntry.getEntryId()) %>
														</a>
													</div>
												</c:if>

												<c:if test="<%= blogsPortletInstanceConfiguration.enableRatings() %>">
													<div class="autofit-col">

														<%
														RatingsEntry nextEntryRatingsEntry = null;
														RatingsStats nextEntryRatingsStats = RatingsStatsLocalServiceUtil.fetchStats(BlogsEntry.class.getName(), nextEntry.getEntryId());

														if (nextEntryRatingsStats != null) {
															nextEntryRatingsEntry = RatingsEntryLocalServiceUtil.fetchEntry(themeDisplay.getUserId(), BlogsEntry.class.getName(), nextEntry.getEntryId());
														}
														%>

														<liferay-ui:ratings
															className="<%= BlogsEntry.class.getName() %>"
															classPK="<%= nextEntry.getEntryId() %>"
															inTrash="<%= nextEntry.isInTrash() %>"
															ratingsEntry="<%= nextEntryRatingsEntry %>"
															ratingsStats="<%= nextEntryRatingsStats %>"
														/>
													</div>
												</c:if>

												<div class="autofit-col autofit-col-end">
													<portlet:renderURL var="nextEntryBookmarksURL">
														<portlet:param name="mvcRenderCommandName" value="/blogs/view_entry" />

														<c:choose>
															<c:when test="<%= Validator.isNotNull(nextEntry.getUrlTitle()) %>">
																<portlet:param name="urlTitle" value="<%= nextEntry.getUrlTitle() %>" />
															</c:when>
															<c:otherwise>
																<portlet:param name="entryId" value="<%= String.valueOf(nextEntry.getEntryId()) %>" />
															</c:otherwise>
														</c:choose>
													</portlet:renderURL>

													<liferay-social-bookmarks:bookmarks
														className="<%= BlogsEntry.class.getName() %>"
														classPK="<%= nextEntry.getEntryId() %>"
														displayStyle="menu"
														target="_blank"
														title="<%= BlogsEntryUtil.getDisplayTitle(resourceBundle, nextEntry) %>"
														types="<%= SocialBookmarksUtil.getSocialBookmarksTypes(blogsPortletInstanceConfiguration) %>"
														url="<%= PortalUtil.getCanonicalURL(nextEntryBookmarksURL.toString(), themeDisplay, layout) %>"
													/>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
						</c:if>
					</div>
				</div>
			</div>
		</c:if>
	</c:if>

	<div class="row">
		<div class="col-md-8 col-md-offset-2">
			<c:if test="<%= blogsPortletInstanceConfiguration.enableComments() %>">

				<%
				Discussion discussion = CommentManagerUtil.getDiscussion(user.getUserId(), scopeGroupId, BlogsEntry.class.getName(), entry.getEntryId(), new ServiceContextFunction(request));
				%>

				<c:if test="<%= discussion != null %>">
					<h2>
						<strong><liferay-ui:message arguments="<%= discussion.getDiscussionCommentsCount() %>" key='<%= discussion.getDiscussionCommentsCount() == 1 ? "x-comment" : "x-comments" %>' /></strong>
					</h2>

					<c:if test="<%= PropsValues.BLOGS_TRACKBACK_ENABLED && entry.isAllowTrackbacks() && Validator.isNotNull(entry.getUrlTitle()) %>">
						<aui:input inlineLabel="left" name="trackbackURL" type="resource" value='<%= PortalUtil.getLayoutFullURL(themeDisplay.getLayout(), themeDisplay, false) + Portal.FRIENDLY_URL_SEPARATOR + "blogs/trackback/" + entry.getUrlTitle() %>' />
					</c:if>

					<liferay-comment:discussion
						className="<%= BlogsEntry.class.getName() %>"
						classPK="<%= entry.getEntryId() %>"
						discussion="<%= discussion %>"
						formName="fm2"
						ratingsEnabled="<%= blogsPortletInstanceConfiguration.enableCommentRatings() %>"
						redirect="<%= currentURL %>"
						userId="<%= entry.getUserId() %>"
					/>
				</c:if>
			</c:if>
		</div>
	</div>
</div>

<%
PortalUtil.setPageTitle(BlogsEntryUtil.getDisplayTitle(resourceBundle, entry), request);
PortalUtil.setPageSubtitle(entry.getSubtitle(), request);

String description = entry.getDescription();

if (Validator.isNull(description)) {
	description = HtmlUtil.stripHtml(StringUtil.shorten(entry.getContent(), pageAbstractLength));
}

PortalUtil.setPageDescription(description, request);

List<AssetTag> assetTags = AssetTagLocalServiceUtil.getTags(BlogsEntry.class.getName(), entry.getEntryId());

PortalUtil.setPageKeywords(ListUtil.toString(assetTags, AssetTag.NAME_ACCESSOR), request);

PortalUtil.addPortletBreadcrumbEntry(request, BlogsEntryUtil.getDisplayTitle(resourceBundle, entry), currentURL);
%>

<liferay-util:dynamic-include key="com.liferay.blogs.web#/blogs/view_entry.jsp#post" />