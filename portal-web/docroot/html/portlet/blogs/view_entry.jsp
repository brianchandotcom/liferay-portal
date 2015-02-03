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
String strutsAction = ParamUtil.getString(request, "struts_action");

String redirect = ParamUtil.getString(request, "redirect");

if (Validator.isNull(redirect) || (strutsAction.equals("/blogs/view_entry") && !portletId.equals(PortletKeys.BLOGS))) {
	PortletURL portletURL = renderResponse.createRenderURL();

	if (portletId.equals(PortletKeys.BLOGS_ADMIN)) {
		portletURL.setParameter("struts_action", "/blogs_admin/view");
	}
	else if (portletId.equals(PortletKeys.BLOGS_AGGREGATOR)) {
		portletURL.setParameter("struts_action", "/blogs_aggregator/view");
	}
	else {
		portletURL.setParameter("struts_action", "/blogs/view");
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

<liferay-ui:header
	backURL="<%= redirect %>"
	localizeTitle="<%= false %>"
	title="<%= entry.getTitle() %>"
/>

<portlet:actionURL var="editEntryURL">
	<portlet:param name="struts_action" value="/blogs/edit_entry" />
</portlet:actionURL>

<aui:form action="<%= editEntryURL %>" method="post" name="fm1" onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "saveEntry();" %>'>
	<aui:input name="<%= Constants.CMD %>" type="hidden" />
	<aui:input name="entryId" type="hidden" value="<%= String.valueOf(entryId) %>" />

	<liferay-util:include page="/html/portlet/blogs/view_entry_content.jsp" />
</aui:form>

<c:if test="<%= PropsValues.BLOGS_ENTRY_PREVIOUS_AND_NEXT_NAVIGATION_ENABLED %>">

	<%
	BlogsEntry[] prevAndNext = BlogsEntryLocalServiceUtil.getEntriesPrevAndNext(entryId);

	BlogsEntry previousEntry = prevAndNext[0];
	BlogsEntry nextEntry = prevAndNext[2];
	%>

	<c:if test="<%= (previousEntry != null) || (nextEntry != null) %>">
		<div class="container-fluid entry-navigation">
			<div class="col-md-6 col-sm-6 previous-entry">
				<c:if test="<%= previousEntry != null %>">
					<h2><liferay-ui:message key="previous-entry" /></h2>

					<portlet:renderURL var="previousEntryURL">
						<portlet:param name="struts_action" value="/blogs/view_entry" />
						<portlet:param name="redirect" value="<%= redirect %>" />
						<portlet:param name="entryId" value="<%= String.valueOf(previousEntry.getEntryId()) %>" />
					</portlet:renderURL>

					<%
					String smallImageURL = previousEntry.getSmallImageURL(themeDisplay);
					%>

					<c:if test="<%= Validator.isNotNull(smallImageURL) %>">
						<div class="small-image-wrapper visible-lg-block visible-md-block" style="background-image: url(<%= HtmlUtil.escape(smallImageURL) %>)"></div>
					</c:if>

					<div class="entry-content-wrapper">
						<h3><a href="<%= previousEntryURL %>"><%= previousEntry.getTitle() %></a></h3>

						<c:choose>
							<c:when test="<%= Validator.isNotNull(previousEntry.getSubtitle()) %>">
								<p class="entry-subtitle visible-lg-block">
									<%= StringUtil.shorten(previousEntry.getSubtitle(), 100) %>
								</p>
							</c:when>
							<c:otherwise>
								<p class="entry-content visible-lg-block">
									<%= StringUtil.shorten(HtmlUtil.stripHtml(previousEntry.getContent()), 100) %>
								</p>
							</c:otherwise>
						</c:choose>

						<liferay-ui:user-display
							userId="<%= previousEntry.getUserId() %>"
							userName="<%= previousEntry.getUserName() %>"
						>
							<liferay-ui:message arguments="<%= LanguageUtil.getTimeDescription(request, System.currentTimeMillis() - previousEntry.getCreateDate().getTime(), true) %>" key="x-ago" translateArguments="<%= false %>" />
						</liferay-ui:user-display>
					</div>
				</c:if>
			</div>
			<div class="col-md-6 col-sm-6 next-entry">
				<c:if test="<%= nextEntry != null %>">
					<h2><liferay-ui:message key="next-entry" /></h2>

					<portlet:renderURL var="nextEntryURL">
						<portlet:param name="struts_action" value="/blogs/view_entry" />
						<portlet:param name="redirect" value="<%= redirect %>" />
						<portlet:param name="entryId" value="<%= String.valueOf(nextEntry.getEntryId()) %>" />
					</portlet:renderURL>

					<%
					String smallImageURL = nextEntry.getSmallImageURL(themeDisplay);
					%>

					<c:if test="<%= Validator.isNotNull(smallImageURL) %>">
						<div class="small-image-wrapper visible-lg-block visible-md-block" style="background-image: url(<%= HtmlUtil.escape(smallImageURL) %>)"></div>
					</c:if>

					<div class="entry-content-wrapper">
						<h3><a href="<%= nextEntryURL %>"><%= nextEntry.getTitle() %></a></h3>

						<c:choose>
							<c:when test="<%= Validator.isNotNull(nextEntry.getSubtitle()) %>">
								<p class="entry-subtitle visible-lg-block">
									<%= StringUtil.shorten(nextEntry.getSubtitle(), 100) %>
								</p>
							</c:when>
							<c:otherwise>
								<p class="entry-content visible-lg-block">
									<%= StringUtil.shorten(HtmlUtil.stripHtml(nextEntry.getContent()), 100) %>
								</p>
							</c:otherwise>
						</c:choose>

						<liferay-ui:user-display
							userId="<%= nextEntry.getUserId() %>"
							userName="<%= nextEntry.getUserName() %>"
						>
							<liferay-ui:message arguments="<%= LanguageUtil.getTimeDescription(request, System.currentTimeMillis() - nextEntry.getCreateDate().getTime(), true) %>" key="x-ago" translateArguments="<%= false %>" />
						</liferay-ui:user-display>
					</div>
				</c:if>
			</div>
		</div>
	</c:if>
</c:if>

<c:if test="<%= blogsPortletInstanceSettings.isEnableComments() %>">
	<liferay-ui:panel-container extended="<%= false %>" id="blogsCommentsPanelContainer" persistState="<%= true %>">
		<liferay-ui:panel collapsible="<%= true %>" id="blogsCommentsPanel" persistState="<%= true %>" title='<%= LanguageUtil.format(request, "x-comments", MBMessageLocalServiceUtil.getDiscussionMessagesCount(BlogsEntry.class.getName(), entry.getEntryId(), WorkflowConstants.STATUS_APPROVED)) %>'>
			<c:if test="<%= PropsValues.BLOGS_TRACKBACK_ENABLED && entry.isAllowTrackbacks() && !portletId.equals(PortletKeys.BLOGS_ADMIN) %>">
				<aui:input inlineLabel="left" name="trackbackURL" type="resource" value='<%= PortalUtil.getLayoutFullURL(themeDisplay) + Portal.FRIENDLY_URL_SEPARATOR + "blogs/trackback/" + entry.getUrlTitle() %>' />
			</c:if>

			<portlet:actionURL var="discussionURL">
				<portlet:param name="struts_action" value="/blogs/edit_entry_discussion" />
			</portlet:actionURL>

			<portlet:resourceURL var="discussionPaginationURL">
				<portlet:param name="struts_action" value="/blogs/edit_entry_discussion" />
			</portlet:resourceURL>

			<liferay-ui:discussion
				className="<%= BlogsEntry.class.getName() %>"
				classPK="<%= entry.getEntryId() %>"
				formAction="<%= discussionURL %>"
				formName="fm2"
				paginationURL="<%= discussionPaginationURL %>"
				ratingsEnabled="<%= blogsPortletInstanceSettings.isEnableCommentRatings() %>"
				redirect="<%= currentURL %>"
				userId="<%= entry.getUserId() %>"
			/>
		</liferay-ui:panel>
	</liferay-ui:panel-container>
</c:if>

<%
PortalUtil.setPageSubtitle(entry.getTitle(), request);
PortalUtil.setPageDescription(entry.getDescription(), request);

List<AssetTag> assetTags = AssetTagLocalServiceUtil.getTags(BlogsEntry.class.getName(), entry.getEntryId());

PortalUtil.setPageKeywords(ListUtil.toString(assetTags, AssetTag.NAME_ACCESSOR), request);

PortalUtil.addPortletBreadcrumbEntry(request, entry.getTitle(), currentURL);
%>