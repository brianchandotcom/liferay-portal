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
			<aui:row>
				<c:if test="<%= previousEntry != null %>">
					<aui:col cssClass='<%= "previous-entry " + ((nextEntry != null) ? "has-next-entry" : StringPool.BLANK) %>' md="6" sm="6">
						<h2><liferay-ui:message key="previous-entry" /></h2>

						<div class="previous-entry-content">
							<portlet:renderURL var="previousEntryURL">
								<portlet:param name="mvcRenderCommandName" value="/blogs/view_entry" />
								<portlet:param name="redirect" value="<%= redirect %>" />
								<portlet:param name="entryId" value="<%= String.valueOf(previousEntry.getEntryId()) %>" />
							</portlet:renderURL>

							<a href="<%= previousEntryURL %>" title="<%= previousEntry.getTitle() %>">

								<%
								String smallImageURL = previousEntry.getSmallImageURL(themeDisplay);
								%>

								<c:if test="<%= Validator.isNotNull(smallImageURL) %>">
									<span class="small-image visible-lg-block visible-md-block" style="background-image: url(<%= HtmlUtil.escape(smallImageURL) %>)"></span>
								</c:if>

								<span class="entry-content">
									<h3><%= previousEntry.getTitle() %></h3>

									<span class="entry-content-body visible-lg-block">
										<c:choose>
											<c:when test="<%= Validator.isNotNull(previousEntry.getSubtitle()) %>">
												<%= StringUtil.shorten(previousEntry.getSubtitle(), 100) %>
											</c:when>
											<c:otherwise>
												<%= StringUtil.shorten(HtmlUtil.stripHtml(previousEntry.getContent()), 100) %>
											</c:otherwise>
										</c:choose>
									</span>
								</span>
							</a>

							<liferay-ui:user-display
								userId="<%= previousEntry.getUserId() %>"
								userName="<%= previousEntry.getUserName() %>"
							>

								<%
								Date createDate = previousEntry.getCreateDate();
								%>

								<liferay-ui:message arguments="<%= LanguageUtil.getTimeDescription(request, System.currentTimeMillis() - createDate.getTime(), true) %>" key="x-ago" translateArguments="<%= false %>" />
							</liferay-ui:user-display>
						</div>
					</aui:col>
				</c:if>

				<c:if test="<%= nextEntry != null %>">
					<aui:col cssClass='<%= "next-entry " + ((previousEntry != null) ? "has-previous-entry" : StringPool.BLANK) %>' md="6" sm="6">
						<h2><liferay-ui:message key="next-entry" /></h2>

						<div class="next-entry-content">
							<portlet:renderURL var="nextEntryURL">
								<portlet:param name="mvcRenderCommandName" value="/blogs/view_entry" />
								<portlet:param name="redirect" value="<%= redirect %>" />
								<portlet:param name="entryId" value="<%= String.valueOf(nextEntry.getEntryId()) %>" />
							</portlet:renderURL>

							<a href="<%= nextEntryURL %>" title="<%= nextEntry.getTitle() %>">

								<%
								String smallImageURL = nextEntry.getSmallImageURL(themeDisplay);
								%>

								<c:if test="<%= Validator.isNotNull(smallImageURL) %>">
									<span class="small-image visible-lg-block visible-md-block" style="background-image: url(<%= HtmlUtil.escape(smallImageURL) %>)"></span>
								</c:if>

								<span class="entry-content">
									<h3><%= nextEntry.getTitle() %></h3>

									<span class="entry-content-body visible-lg-block">
										<c:choose>
											<c:when test="<%= Validator.isNotNull(nextEntry.getSubtitle()) %>">
												<%= StringUtil.shorten(nextEntry.getSubtitle(), 100) %>
											</c:when>
											<c:otherwise>
												<%= StringUtil.shorten(HtmlUtil.stripHtml(nextEntry.getContent()), 100) %>
											</c:otherwise>
										</c:choose>
									</span>
								</span>
							</a>

							<liferay-ui:user-display
								userId="<%= nextEntry.getUserId() %>"
								userName="<%= nextEntry.getUserName() %>"
							>

								<%
								Date createDate = nextEntry.getCreateDate();
								%>

								<liferay-ui:message arguments="<%= LanguageUtil.getTimeDescription(request, System.currentTimeMillis() - createDate.getTime(), true) %>" key="x-ago" translateArguments="<%= false %>" />
							</liferay-ui:user-display>
						</div>
					</aui:col>
				</c:if>
			</aui:row>
		</aui:container>
	</c:if>
</c:if>

<c:if test="<%= blogsPortletInstanceSettings.isEnableComments() %>">
	<liferay-ui:panel-container extended="<%= false %>" id="blogsCommentsPanelContainer" persistState="<%= true %>">
		<liferay-ui:panel collapsible="<%= true %>" id="blogsCommentsPanel" persistState="<%= true %>" title='<%= LanguageUtil.format(request, "x-comments", MBMessageLocalServiceUtil.getDiscussionMessagesCount(BlogsEntry.class.getName(), entry.getEntryId(), WorkflowConstants.STATUS_APPROVED)) %>'>
			<c:if test="<%= PropsValues.BLOGS_TRACKBACK_ENABLED && entry.isAllowTrackbacks() && !portletId.equals(BlogsPortletKeys.BLOGS_ADMIN) %>">
				<aui:input inlineLabel="left" name="trackbackURL" type="resource" value='<%= PortalUtil.getLayoutFullURL(themeDisplay) + Portal.FRIENDLY_URL_SEPARATOR + "blogs/trackback/" + entry.getUrlTitle() %>' />
			</c:if>

			<portlet:actionURL name="invokeTaglibDiscussion" var="discussionURL" />

			<portlet:resourceURL id="invokeTaglibDiscussionPagination" var="discussionPaginationURL" />

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