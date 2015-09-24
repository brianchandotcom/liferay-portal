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

<%@ include file="/my_sites/init.jsp" %>

<%
PanelCategory panelCategory = (PanelCategory)request.getAttribute(ApplicationListWebKeys.PANEL_CATEGORY);

List<Group> mySiteGroups = user.getMySiteGroups(new String[] {Company.class.getName(), Group.class.getName(), Organization.class.getName()}, PropsValues.MY_SITES_MAX_ELEMENTS);
%>

<c:if test="<%= !mySiteGroups.isEmpty() %>">
	<ul aria-labelledby="<%= AUIUtil.normalizeId(panelCategory.getKey()) %>" class="nav nav-equal-height" role="menu">

	<%
	for (Group mySiteGroup : mySiteGroups) {
		boolean showPublicSite = mySiteGroup.isShowSite(permissionChecker, false) && (mySiteGroup.getPublicLayoutsPageCount() > 0);
		boolean showPrivateSite = mySiteGroup.isShowSite(permissionChecker, true) && (mySiteGroup.getPrivateLayoutsPageCount() > 0);

		Group siteGroup = mySiteGroup;

		boolean selectedSite = false;

		if (layout != null) {
			if (layout.getGroupId() == mySiteGroup.getGroupId()) {
				selectedSite = true;
			}
			else if (mySiteGroup.hasStagingGroup()) {
				Group stagingGroup = mySiteGroup.getStagingGroup();

				if (layout.getGroupId() == stagingGroup.getGroupId()) {
					selectedSite = true;
				}
			}
		}
	%>

		<c:choose>
			<c:when test="<%= showPublicSite || showPrivateSite %>">

				<%
				long stagingGroupId = 0;

				boolean showPublicSiteStaging = false;
				boolean showPrivateSiteStaging = false;

				if (mySiteGroup.hasStagingGroup()) {
					Group stagingGroup = mySiteGroup.getStagingGroup();

					stagingGroupId = stagingGroup.getGroupId();

					if (GroupPermissionUtil.contains(permissionChecker, mySiteGroup, ActionKeys.VIEW_STAGING)) {
						if ((mySiteGroup.getPublicLayoutsPageCount() == 0) && (stagingGroup.getPublicLayoutsPageCount() > 0)) {
							showPublicSiteStaging = true;
						}

						if ((mySiteGroup.getPrivateLayoutsPageCount() == 0) && (stagingGroup.getPrivateLayoutsPageCount() > 0)) {
							showPrivateSiteStaging = true;
						}
					}
				}
				%>

				<c:if test="<%= showPublicSite && ((mySiteGroup.getPublicLayoutsPageCount() > 0) || showPublicSiteStaging) %>">

					<%
					if (showPublicSiteStaging) {
						siteGroup = GroupLocalServiceUtil.fetchGroup(stagingGroupId);
					}

					request.setAttribute("my_sites.jsp-privateLayout", false);
					request.setAttribute("my_sites.jsp-selectedSite", selectedSite);
					request.setAttribute("my_sites.jsp-siteGroup", mySiteGroup);

					if (mySiteGroup.isUser()) {
						request.setAttribute("my_sites.jsp-siteName", LanguageUtil.get(request, "my-profile"));
					}

					request.setAttribute("my_sites.jsp-showPrivateLabel", (mySiteGroup.getPrivateLayoutsPageCount() > 0) || showPrivateSiteStaging);
					request.setAttribute("my_sites.jsp-showStagingLabel", showPublicSiteStaging);
					%>

					<liferay-util:include page="/my_sites/site_link.jsp" servletContext="<%= application %>" />
				</c:if>

				<c:if test="<%= showPrivateSite && ((mySiteGroup.getPrivateLayoutsPageCount() > 0) || showPrivateSiteStaging) %>">

					<%
					siteGroup = mySiteGroup;

					if (showPrivateSiteStaging) {
						siteGroup = GroupLocalServiceUtil.fetchGroup(stagingGroupId);
					}

					request.setAttribute("my_sites.jsp-privateLayout", true);
					request.setAttribute("my_sites.jsp-selectedSite", selectedSite && !showPublicSite);
					request.setAttribute("my_sites.jsp-siteGroup", mySiteGroup);

					if (mySiteGroup.isUser()) {
						request.setAttribute("my_sites.jsp-siteName", LanguageUtil.get(request, "my-dashboard"));
					}

					request.setAttribute("my_sites.jsp-showPrivateLabel", (mySiteGroup.getPublicLayoutsPageCount() > 0) || showPublicSiteStaging);
					request.setAttribute("my_sites.jsp-showStagingLabel", showPrivateSiteStaging);
					%>

					<liferay-util:include page="/my_sites/site_link.jsp" servletContext="<%= application %>" />
				</c:if>
			</c:when>
			<c:when test="<%= GroupPermissionUtil.contains(permissionChecker, mySiteGroup, ActionKeys.VIEW_SITE_ADMINISTRATION) %>">

				<%
				siteGroup = StagingUtil.getStagingGroup(mySiteGroup.getGroupId());

				request.setAttribute("my_sites.jsp-privateLayout", false);
				request.setAttribute("my_sites.jsp-selectedSite", selectedSite);
				request.setAttribute("my_sites.jsp-siteGroup", siteGroup);

				if (siteGroup.isUser()) {
					request.setAttribute("my_sites.jsp-siteName", LanguageUtil.get(request, "my-site"));
				}

				request.setAttribute("my_sites.jsp-showPrivateLabel", false);
				request.setAttribute("my_sites.jsp-showStagingLabel", false);
				%>

				<liferay-util:include page="/my_sites/site_link.jsp" servletContext="<%= application %>" />
			</c:when>
		</c:choose>

	<%
	}
	%>

	</ul>
</c:if>