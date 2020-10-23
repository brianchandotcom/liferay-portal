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

<%@ include file="/dynamic_include/init.jsp" %>

<%
BlogsGroupServiceOverriddenConfiguration blogsGroupServiceOverriddenConfiguration = ConfigurationProviderUtil.getConfiguration(BlogsGroupServiceOverriddenConfiguration.class, new GroupServiceSettingsLocator(themeDisplay.getSiteGroupId(), BlogsConstants.SERVICE_NAME));
%>

<div class="btn-group">
	<c:if test="<%= blogsGroupServiceOverriddenConfiguration.enableRss() && ((themeDisplay.getScopeGroupId() == 0) || GroupPermissionUtil.contains(themeDisplay.getPermissionChecker(), themeDisplay.getScopeGroupId(), ActionKeys.VIEW)) %>">

		<%
		String rssURL = RSSUtil.getURL(StringBundler.concat(themeDisplay.getPathMain(), "/blogs/rss?plid=", String.valueOf(themeDisplay.getPlid()), "&groupId=", String.valueOf(themeDisplay.getScopeGroupId())), GetterUtil.getInteger(blogsGroupServiceOverriddenConfiguration.rssDelta()), blogsGroupServiceOverriddenConfiguration.rssDisplayStyle(), blogsGroupServiceOverriddenConfiguration.rssFeedType(), null);
		%>

		<div class="btn-group-item">
			<clay:link
				borderless="<%= true %>"
				displayType="secondary"
				href="<%= rssURL %>"
				icon="rss-full"
				label="rss"
				small="<%= true %>"
				type="button"
			/>
		</div>

		<liferay-util:html-top>
			<link href="<%= HtmlUtil.escapeAttribute(rssURL) %>" rel="alternate" title="RSS" type="application/rss+xml" />
		</liferay-util:html-top>
	</c:if>

	<%
	BlogsGroupServiceSettings blogsGroupServiceSettings = BlogsGroupServiceSettings.getInstance(scopeGroupId);
	%>

	<c:if test="<%= (blogsGroupServiceSettings.isEmailEntryAddedEnabled() || blogsGroupServiceSettings.isEmailEntryUpdatedEnabled()) && BlogsPermission.contains(permissionChecker, scopeGroupId, ActionKeys.SUBSCRIBE) %>">
		<div class="btn-group-item">
			<c:choose>
				<c:when test="<%= SubscriptionLocalServiceUtil.isSubscribed(company.getCompanyId(), user.getUserId(), BlogsEntry.class.getName(), scopeGroupId) %>">
					<portlet:actionURL name="/blogs/edit_entry" var="unsubscribeURL">
						<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.UNSUBSCRIBE %>" />
						<portlet:param name="redirect" value="<%= currentURL %>" />
					</portlet:actionURL>

					<clay:link
						displayType="secondary"
						href="<%= unsubscribeURL %>"
						label="unsubscribe"
						small="<%= true %>"
						type="button"
					/>
				</c:when>
				<c:otherwise>
					<portlet:actionURL name="/blogs/edit_entry" var="subscribeURL">
						<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.SUBSCRIBE %>" />
						<portlet:param name="redirect" value="<%= currentURL %>" />
					</portlet:actionURL>

					<clay:link
						displayType="secondary"
						href="<%= subscribeURL %>"
						label="subscribe"
						small="<%= true %>"
						type="button"
					/>
				</c:otherwise>
			</c:choose>
		</div>
	</c:if>

	<c:if test="<%= BlogsPermission.contains(permissionChecker, scopeGroupId, ActionKeys.ADD_ENTRY) %>">

		<%
		PortletURL editEntryURL = PortalUtil.getControlPanelPortletURL(request, themeDisplay.getScopeGroup(), BlogsPortletKeys.BLOGS_ADMIN, 0, themeDisplay.getPlid(), PortletRequest.RENDER_PHASE);

		editEntryURL.setParameter("mvcRenderCommandName", "/blogs/edit_entry");
		editEntryURL.setParameter("redirect", currentURL);
		editEntryURL.setParameter("portletResource", portletDisplay.getId());
		%>

		<div class="btn-group-item">
			<clay:link
				displayType="primary"
				href="<%= editEntryURL.toString() %>"
				label="new-entry"
				small="<%= true %>"
				type="button"
			/>
		</div>
	</c:if>
</div>