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

List<Group> mySiteGroups = user.getMySiteGroups(new String[] {Group.class.getName(), Organization.class.getName()}, false, PropsValues.MY_SITES_MAX_ELEMENTS);
%>

<c:if test="<%= !mySiteGroups.isEmpty() %>">

	<%
	String panelPageCategoryId = "panel-manage-" + StringUtil.replace(panelCategory.getKey(), StringPool.PERIOD, StringPool.UNDERLINE);
	%>

	<a aria-expanded="false" class="collapse-icon collapsed list-group-heading" data-toggle="collapse" href="#<%= panelPageCategoryId %>">
		<liferay-ui:message key="my-sites" />
	</a>

	<div class="collapse" id="<%= panelPageCategoryId %>">
		<div class="list-group-item">
			<ul aria-labelledby="<%= panelCategory.getKey() %>" class="nav nav-equal-height" role="menu">

			<%
			for (Group mySiteGroup : mySiteGroups) {
				boolean showPublicSite = mySiteGroup.isShowSite(permissionChecker, false);
				boolean showPrivateSite = mySiteGroup.isShowSite(permissionChecker, true);

				Group siteGroup = mySiteGroup;
			%>

				<c:if test="<%= showPublicSite || showPrivateSite %>">

					<%
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

					long doAsGroupId = themeDisplay.getDoAsGroupId();

					try {
					%>

						<c:if test="<%= showPublicSite && ((mySiteGroup.getPublicLayoutsPageCount() > 0) || showPublicSiteStaging) %>">

							<%
							if (showPublicSiteStaging) {
								siteGroup = GroupLocalServiceUtil.fetchGroup(stagingGroupId);
							}

							themeDisplay.setDoAsGroupId(siteGroup.getGroupId());

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

							themeDisplay.setDoAsGroupId(siteGroup.getGroupId());

							request.setAttribute("my_sites.jsp-privateLayout", true);
							request.setAttribute("my_sites.jsp-selectedSite", selectedSite);
							request.setAttribute("my_sites.jsp-siteGroup", mySiteGroup);

							if (mySiteGroup.isUser()) {
								request.setAttribute("my_sites.jsp-siteName", LanguageUtil.get(request, "my-dashboard"));
							}

							request.setAttribute("my_sites.jsp-showPrivateLabel", (mySiteGroup.getPublicLayoutsPageCount() > 0) || showPublicSiteStaging);
							request.setAttribute("my_sites.jsp-showStagingLabel", showPrivateSiteStaging);
							%>

							<liferay-util:include page="/my_sites/site_link.jsp" servletContext="<%= application %>" />
						</c:if>

					<%
					}
					finally {
						themeDisplay.setDoAsGroupId(doAsGroupId);
					}
					%>

				</c:if>

			<%
			}
			%>

		</ul>
		</div>
	</div>
</c:if>