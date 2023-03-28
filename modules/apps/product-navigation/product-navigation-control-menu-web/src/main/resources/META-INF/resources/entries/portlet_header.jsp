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

<%@ include file="/init.jsp" %>

<%
String portletTitle = (String)request.getAttribute(ProductNavigationControlMenuWebKeys.PORTLET_TITLE);

Group group = layout.getGroup();

Group liveGroup = group;

boolean inStaging = false;

if (group.isControlPanel()) {
	long doAsGroupId = ParamUtil.getLong(request, "doAsGroupId");

	if (doAsGroupId > 0) {
		try {
			liveGroup = GroupLocalServiceUtil.getGroup(doAsGroupId);

			if (liveGroup.isStagingGroup()) {
				liveGroup = liveGroup.getLiveGroup();

				inStaging = true;
			}
		}
		catch (Exception e) {
		}
	}
}
else if (group.isStagingGroup()) {
	liveGroup = group.getLiveGroup();

	inStaging = true;
}
%>

<div class="control-menu-nav-item control-menu-nav-item-content">
	<h1 class="control-menu-level-1-heading mb-0 text-truncate" data-qa-id="headerTitle"><%= HtmlUtil.escape(portletTitle) %></h1>

	<c:if test="<%= liveGroup.isStaged() && !liveGroup.isStagedPortlet(portletDisplay.getRootPortletId()) %>">
		<c:choose>
			<c:when test="<%= !liveGroup.isStagedRemotely() && inStaging %>">
				<span class="align-items-center lfr-portal-tooltip" title="<%= HtmlUtil.stripHtml(LanguageUtil.get(request, "this-portlet-is-not-staged-local-alert")) %>">
					<clay:icon
						aria-label='<%= HtmlUtil.stripHtml(LanguageUtil.get(request, "this-portlet-is-not-staged-local-alert")) %>'
						cssClass="ml-3 mt-0"
						symbol="warning-full"
					/>
				</span>
			</c:when>
			<c:when test="<%= liveGroup.isStagedRemotely() && themeDisplay.isSignedIn() %>">
				<span class="align-items-center lfr-portal-tooltip" title="<%= HtmlUtil.stripHtml(LanguageUtil.get(request, "this-portlet-is-not-staged-remote-alert")) %>">
					<clay:icon
						aria-label='<%= HtmlUtil.stripHtml(LanguageUtil.get(request, "this-portlet-is-not-staged-remote-alert")) %>'
						cssClass="ml-3 mt-0"
						symbol="warning-full"
					/>
				</span>
			</c:when>
		</c:choose>
	</c:if>
</div>