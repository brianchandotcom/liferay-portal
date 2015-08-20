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
String mvcRenderCommandName = ParamUtil.getString(request, "mvcRenderCommandName");

String redirect = ParamUtil.getString(request, "redirect");

if (Validator.isNull(redirect) || (mvcRenderCommandName.equals("/blogs/view_entry") && !portletId.equals(BlogsPortletKeys.BLOGS))) {
	PortletURL portletURL = renderResponse.createRenderURL();

	if (portletId.equals(BlogsPortletKeys.BLOGS_ADMIN)) {
		portletURL.setParameter("mvcRenderCommandName", "/blogs_admin/view");
	}
	else if (portletId.equals(BlogsPortletKeys.BLOGS_AGGREGATOR)) {
		portletURL.setParameter("mvcRenderCommandName", "/blogs_aggregator/view");
	}
	else {
		portletURL.setParameter("mvcRenderCommandName", "/blogs/view");
	}

	redirect = portletURL.toString();
}

BlogsEntry entry = (BlogsEntry)request.getAttribute(WebKeys.BLOGS_ENTRY);

//entry = entry.toEscapedModel();

long entryId = BeanParamUtil.getLong(entry, request, "entryId");

AssetEntry assetEntry = AssetEntryLocalServiceUtil.getEntry(BlogsEntry.class.getName(), entry.getEntryId());

AssetEntryServiceUtil.incrementViewCounter(BlogsEntry.class.getName(), entry.getEntryId());

AssetUtil.addLayoutTags(request, AssetTagLocalServiceUtil.getTags(BlogsEntry.class.getName(), entry.getEntryId()));

request.setAttribute(WebKeys.LAYOUT_ASSET_ENTRY, assetEntry);

request.setAttribute("view_entry_content.jsp-entry", entry);

request.setAttribute("view_entry_content.jsp-assetEntry", assetEntry);
%>

<c:if test="<%= portletId.equals(BlogsPortletKeys.BLOGS_ADMIN) %>">
	<liferay-ui:header
		backURL="<%= redirect %>"
		localizeTitle="<%= false %>"
		title="<%= entry.getTitle() %>"
	/>
</c:if>

<portlet:actionURL name="/blogs/edit_entry" var="editEntryURL" />

<div class="view-entry-container">
	<aui:form action="<%= editEntryURL %>" method="post" name="fm1" onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "saveEntry();" %>'>
		<aui:input name="<%= Constants.CMD %>" type="hidden" />
		<aui:input name="entryId" type="hidden" value="<%= String.valueOf(entryId) %>" />

		<liferay-util:include page="/blogs/view_entry_content.jsp" servletContext="<%= application %>" />
	</aui:form>

	<c:if test="<%= !portletId.equals(BlogsPortletKeys.BLOGS_ADMIN) && PropsValues.BLOGS_ENTRY_PREVIOUS_AND_NEXT_NAVIGATION_ENABLED %>">

		<%
		BlogsEntry[] prevAndNext = BlogsEntryLocalServiceUtil.getEntriesPrevAndNext(entryId);

		BlogsEntry previousEntry = prevAndNext[0];
		BlogsEntry nextEntry = prevAndNext[2];
		%>

		<c:if test="<%= (previousEntry != null) || (nextEntry != null) %>">
			<aui:container cssClass="entry-navigation">
				<h2><strong><liferay-ui:message key="more-blog-entries" /></strong></h2>

				<aui:row>
					<c:if test="<%= previousEntry != null %>">
						<aui:col cssClass="entry-navigation-item" md="6" sm="6">
							<portlet:renderURL var="previousEntryURL" windowState="<%= WindowState.MAXIMIZED.toString() %>">
								<portlet:param name="mvcRenderCommandName" value="/blogs/view_entry" />
								<portlet:param name="redirect" value="<%= redirect %>" />
								<portlet:param name="entryId" value="<%= String.valueOf(previousEntry.getEntryId()) %>" />
							</portlet:renderURL>

							<%
							String smallImageURL = previousEntry.getSmallImageURL(themeDisplay);
							%>

							<c:if test="<%= Validator.isNotNull(smallImageURL) %>">
								<aui:a href="<%= previousEntryURL %>" title="<%= previousEntry.getTitle() %>">
									<span class="small-image visible-lg-block visible-md-block" style="background-image: url(<%= HtmlUtil.escape(smallImageURL) %>)"></span>
								</aui:a>
							</c:if>

							<div class="entry-info">
								<liferay-ui:user-display
									showUserName="<%= false %>"
									userIconCssClass="user-icon-md"
									userId="<%= previousEntry.getUserId() %>"
									userName="<%= previousEntry.getUserName() %>"
									view="lexicon"
								>
									<small>
										<strong><%= previousEntry.getUserName() %></strong>
										<span> - </span>
										<span class="hide-accessible"><liferay-ui:message key="published-date" /></span>
										<%= dateFormatDate.format(previousEntry.getDisplayDate()) %>
									</small>
								</liferay-ui:user-display>
							</div>

							<div class="entry-content">
								<h4>
									<aui:a href="<%= previousEntryURL %>" title="<%= previousEntry.getTitle() %>"><%= previousEntry.getTitle() %></aui:a>
								</h4>

								<p class="entry-content-body visible-lg-block">
									<%= StringUtil.shorten(HtmlUtil.stripHtml(previousEntry.getContent()), 100) %>
								</p>
							</div>
						</aui:col>
					</c:if>

					<c:if test="<%= nextEntry != null %>">
						<aui:col cssClass="entry-navigation-item" md="6" sm="6">
							<portlet:renderURL var="nextEntryURL" windowState="<%= WindowState.MAXIMIZED.toString() %>">
								<portlet:param name="mvcRenderCommandName" value="/blogs/view_entry" />
								<portlet:param name="redirect" value="<%= redirect %>" />
								<portlet:param name="entryId" value="<%= String.valueOf(nextEntry.getEntryId()) %>" />
							</portlet:renderURL>

							<%
							String smallImageURL = nextEntry.getSmallImageURL(themeDisplay);
							%>

							<c:if test="<%= Validator.isNotNull(smallImageURL) %>">
								<aui:a href="<%= nextEntryURL %>" title="<%= nextEntry.getTitle() %>">
									<span class="small-image visible-lg-block visible-md-block" style="background-image: url(<%= HtmlUtil.escape(smallImageURL) %>)"></span>
								</aui:a>
							</c:if>

							<div class="entry-info">
								<liferay-ui:user-display
									showUserName="<%= false %>"
									userIconCssClass="user-icon-md"
									userId="<%= nextEntry.getUserId() %>"
									userName="<%= nextEntry.getUserName() %>"
									view="lexicon"
								>
									<small>
										<strong><%= nextEntry.getUserName() %></strong>
										<span> - </span>
										<span class="hide-accessible"><liferay-ui:message key="published-date" /></span>
										<%= dateFormatDate.format(nextEntry.getDisplayDate()) %>
									</small>
								</liferay-ui:user-display>
							</div>

							<div class="entry-content">
								<h4>
									<aui:a href="<%= nextEntryURL %>" title="<%= nextEntry.getTitle() %>"><%= nextEntry.getTitle() %></aui:a>
								</h4>

								<p class="visible-lg-block">
									<%= StringUtil.shorten(HtmlUtil.stripHtml(nextEntry.getContent()), 100) %>
								</p>
							</div>
						</aui:col>
					</c:if>
				</aui:row>
			</aui:container>
		</c:if>
	</c:if>

	<%
	Discussion discussion = CommentManagerUtil.getDiscussion(user.getUserId(), scopeGroupId, BlogsEntry.class.getName(), entry.getEntryId(), new ServiceContextFunction(request));
	%>

	<c:if test="<%= (discussion != null) && blogsPortletInstanceConfiguration.enableComments() %>">
		<h2><strong><%= LanguageUtil.format(request, "x-comments", CommentManagerUtil.getCommentsCount(BlogsEntry.class.getName(), entry.getEntryId())) %></strong></h2>

		<c:if test="<%= PropsValues.BLOGS_TRACKBACK_ENABLED && entry.isAllowTrackbacks() && !portletId.equals(BlogsPortletKeys.BLOGS_ADMIN) %>">
			<aui:input inlineLabel="left" name="trackbackURL" type="resource" value='<%= PortalUtil.getLayoutFullURL(themeDisplay) + Portal.FRIENDLY_URL_SEPARATOR + "blogs/trackback/" + entry.getUrlTitle() %>' />
		</c:if>

		<liferay-ui:discussion
			className="<%= BlogsEntry.class.getName() %>"
			classPK="<%= entry.getEntryId() %>"
			formName="fm2"
			ratingsEnabled="<%= blogsPortletInstanceConfiguration.enableCommentRatings() %>"
			redirect="<%= currentURL %>"
			userId="<%= entry.getUserId() %>"
		/>
	</c:if>
</div>

<%
PortalUtil.setPageSubtitle(entry.getTitle(), request);
PortalUtil.setPageDescription(entry.getDescription(), request);

List<AssetTag> assetTags = AssetTagLocalServiceUtil.getTags(BlogsEntry.class.getName(), entry.getEntryId());

PortalUtil.setPageKeywords(ListUtil.toString(assetTags, AssetTag.NAME_ACCESSOR), request);

PortalUtil.addPortletBreadcrumbEntry(request, entry.getTitle(), currentURL);
%>