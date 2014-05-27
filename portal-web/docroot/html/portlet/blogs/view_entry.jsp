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

	<div class="entry-navigation">
		<c:choose>
			<c:when test="<%= previousEntry != null %>">
				<portlet:renderURL var="previousEntryURL">
					<portlet:param name="struts_action" value="/blogs/view_entry" />
					<portlet:param name="redirect" value="<%= redirect %>" />
					<portlet:param name="entryId" value="<%= String.valueOf(previousEntry.getEntryId()) %>" />
				</portlet:renderURL>

				<aui:a cssClass="icon-circle-arrow-left previous" href="<%= previousEntryURL %>" label="previous" />
			</c:when>
			<c:otherwise>
				<span class="icon-circle-arrow-left previous"><liferay-ui:message key="previous" /></span>
			</c:otherwise>
		</c:choose>

		<c:choose>
			<c:when test="<%= nextEntry != null %>">
				<portlet:renderURL var="nextEntryURL">
					<portlet:param name="struts_action" value="/blogs/view_entry" />
					<portlet:param name="redirect" value="<%= redirect %>" />
					<portlet:param name="entryId" value="<%= String.valueOf(nextEntry.getEntryId()) %>" />
				</portlet:renderURL>

				<aui:a cssClass="next" href="<%= nextEntryURL %>" label="next">
					<i class="icon-circle-arrow-right"></i>
				</aui:a>
			</c:when>
			<c:otherwise>
				<span class="next">
					<liferay-ui:message key="next" />
					<i class="icon-circle-arrow-right"></i>
				</span>
			</c:otherwise>
		</c:choose>
	</div>
</c:if>

<c:if test="<%= blogsPortletInstanceSettings.getEnableComments() %>">
	<liferay-ui:panel-container extended="<%= false %>" id="blogsCommentsPanelContainer" persistState="<%= true %>">
		<liferay-ui:panel collapsible="<%= true %>" extended="<%= true %>" id="blogsCommentsPanel" persistState="<%= true %>" title="comments">
			<c:if test="<%= PropsValues.BLOGS_TRACKBACK_ENABLED && entry.isAllowTrackbacks() && !portletId.equals(PortletKeys.BLOGS_ADMIN) %>">
				<aui:input inlineLabel="left" name="trackbackURL" type="resource" value='<%= PortalUtil.getLayoutFullURL(themeDisplay) + Portal.FRIENDLY_URL_SEPARATOR + "blogs/trackback/" + entry.getUrlTitle() %>' />
			</c:if>

			<portlet:actionURL var="discussionURL">
				<portlet:param name="struts_action" value="/blogs/edit_entry_discussion" />
			</portlet:actionURL>

			<liferay-ui:discussion
				className="<%= BlogsEntry.class.getName() %>"
				classPK="<%= entry.getEntryId() %>"
				formAction="<%= discussionURL %>"
				formName="fm2"
				ratingsEnabled="<%= blogsPortletInstanceSettings.getEnableCommentRatings() %>"
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