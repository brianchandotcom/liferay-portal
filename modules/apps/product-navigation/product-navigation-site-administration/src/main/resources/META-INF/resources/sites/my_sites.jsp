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
SiteAdministrationPanelCategoryDisplayContext siteAdministrationPanelCategoryDisplayContext = new SiteAdministrationPanelCategoryDisplayContext(liferayPortletRequest, liferayPortletResponse, null);

List<Group> mySiteGroups = siteAdministrationPanelCategoryDisplayContext.getMySites();
%>

<c:if test="<%= !mySiteGroups.isEmpty() %>">
	<ul class="no-borders site-selector tabular-list-group" role="menu">

	<%
	for (Group mySiteGroup : mySiteGroups) {
		boolean showPublicSite = mySiteGroup.isShowSite(permissionChecker, false) && (mySiteGroup.getPublicLayoutsPageCount() > 0);
		boolean showPrivateSite = mySiteGroup.isShowSite(permissionChecker, true) && (mySiteGroup.getPrivateLayoutsPageCount() > 0);

		Group siteGroup = mySiteGroup;

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

		SiteAdministrationPanelCategoryDisplayContext groupSiteAdministrationPanelCategoryDisplayContext = new SiteAdministrationPanelCategoryDisplayContext(liferayPortletRequest, liferayPortletResponse, mySiteGroup);
	%>

		<c:choose>
			<c:when test="<%= showPublicSite || showPublicSiteStaging || showPrivateSite || showPrivateSiteStaging %>">
				<c:if test="<%= showPublicSite || showPublicSiteStaging %>">

					<%
					if (showPublicSiteStaging) {
						siteGroup = GroupLocalServiceUtil.fetchGroup(stagingGroupId);
					}

					request.setAttribute("my_sites.jsp-privateLayout", false);
					request.setAttribute("my_sites.jsp-selectedSite", groupSiteAdministrationPanelCategoryDisplayContext.isSelectedSite() && !layout.isPrivateLayout());
					request.setAttribute("my_sites.jsp-siteGroup", showPublicSite ? mySiteGroup : siteGroup);

					if (mySiteGroup.isUser()) {
						request.setAttribute("my_sites.jsp-siteName", LanguageUtil.get(request, "my-profile"));
					}

					request.setAttribute("my_sites.jsp-showPrivateLabel", (mySiteGroup.getPrivateLayoutsPageCount() > 0) || showPrivateSiteStaging);
					request.setAttribute("my_sites.jsp-showStagingLabel", showPublicSiteStaging);
					%>

					<liferay-util:include page="/sites/site_link.jsp" servletContext="<%= application %>" />
				</c:if>

				<c:if test="<%= showPrivateSite || showPrivateSiteStaging %>">

					<%
					siteGroup = mySiteGroup;

					if (showPrivateSiteStaging) {
						siteGroup = GroupLocalServiceUtil.fetchGroup(stagingGroupId);
					}

					request.setAttribute("my_sites.jsp-privateLayout", true);
					request.setAttribute("my_sites.jsp-selectedSite", groupSiteAdministrationPanelCategoryDisplayContext.isSelectedSite() && layout.isPrivateLayout());
					request.setAttribute("my_sites.jsp-siteGroup", showPrivateSite ? mySiteGroup : siteGroup);

					if (mySiteGroup.isUser()) {
						request.setAttribute("my_sites.jsp-siteName", LanguageUtil.get(request, "my-dashboard"));
					}

					request.setAttribute("my_sites.jsp-showPrivateLabel", (mySiteGroup.getPublicLayoutsPageCount() > 0) || showPublicSiteStaging);
					request.setAttribute("my_sites.jsp-showStagingLabel", showPrivateSiteStaging);
					%>

					<liferay-util:include page="/sites/site_link.jsp" servletContext="<%= application %>" />
				</c:if>
			</c:when>
			<c:when test="<%= GroupPermissionUtil.contains(permissionChecker, mySiteGroup, ActionKeys.VIEW_SITE_ADMINISTRATION) %>">

				<%
				siteGroup = StagingUtil.getStagingGroup(mySiteGroup.getGroupId());

				request.setAttribute("my_sites.jsp-privateLayout", false);
				request.setAttribute("my_sites.jsp-selectedSite", groupSiteAdministrationPanelCategoryDisplayContext.isSelectedSite());
				request.setAttribute("my_sites.jsp-siteGroup", siteGroup);

				if (siteGroup.isUser()) {
					request.setAttribute("my_sites.jsp-siteName", LanguageUtil.get(request, "my-site"));
				}

				request.setAttribute("my_sites.jsp-showPrivateLabel", false);
				request.setAttribute("my_sites.jsp-showStagingLabel", false);
				%>

				<liferay-util:include page="/sites/site_link.jsp" servletContext="<%= application %>" />
			</c:when>
		</c:choose>

	<%
	}
	%>

	</ul>
</c:if>