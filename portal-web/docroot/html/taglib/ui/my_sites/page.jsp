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

<%@ include file="/html/taglib/init.jsp" %>

<%
String[] classNames = (String[])request.getAttribute("liferay-ui:my_sites:classNames");
String cssClass = GetterUtil.getString((String)request.getAttribute("liferay-ui:my_sites:cssClass"));
int max = GetterUtil.getInteger((String)request.getAttribute("liferay-ui:my_sites:max"));

if (max <= 0) {
	max = PropsValues.MY_SITES_MAX_ELEMENTS;
}

List<Group> mySiteGroups = user.getMySiteGroups(classNames, max);
%>

<c:if test="<%= !mySiteGroups.isEmpty() %>">
	<ul class="taglib-my-sites <%= cssClass %>">

		<%
		for (Group mySiteGroup : mySiteGroups) {
			boolean showPublicSite = mySiteGroup.isShowSite(permissionChecker, false);
			boolean showPrivateSite = mySiteGroup.isShowSite(permissionChecker, true);

			Group siteGroup = mySiteGroup;
		%>

			<c:if test="<%= showPublicSite || showPrivateSite %>">
				<c:choose>
					<c:when test='<%= PropsValues.MY_SITES_DISPLAY_STYLE.equals("simple") %>'>

						<%
						boolean firstSite = false;

						if (mySiteGroups.indexOf(mySiteGroup) == 0) {
							firstSite = true;
						}

						boolean lastSite = false;

						if (mySiteGroups.size() == (mySiteGroups.indexOf(mySiteGroup) + 1)) {
							lastSite = true;
						}

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

						String itemCssClass = StringPool.BLANK;

						if (firstSite) {
							itemCssClass += " first";
						}

						if (lastSite) {
							itemCssClass += " last";
						}

						String iconCssClass = "icon-spacer";

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
							%>

							<li class="<%= (selectedSite && layout.isPublicLayout()) ? "active" : "public-site" %> <%= itemCssClass %>">
								<a href="<%= HtmlUtil.escape(siteGroup.getDisplayURL(themeDisplay, false)) %>" onclick="Liferay.Util.forcePost(this); return false;" role="menuitem">

									<%
									String siteName = StringPool.BLANK;

									if (mySiteGroup.isUser()) {
										siteName = LanguageUtil.get(resourceBundle, "my-profile");
									}
									else {
										siteName = mySiteGroup.getDescriptiveName(locale);
									}

									if (showPublicSiteStaging) {
										siteName = StringUtil.appendParentheticalSuffix(siteName, LanguageUtil.get(resourceBundle, "staging"));
									}

									if ((mySiteGroup.getPrivateLayoutsPageCount() > 0) || showPrivateSiteStaging) {
										iconCssClass = "icon-eye-open";
									}
									%>

									<%@ include file="/html/taglib/ui/my_sites/page_site_name.jspf" %>

									<c:if test="<%= (mySiteGroup.getPrivateLayoutsPageCount() > 0) || showPrivateSiteStaging %>">
										<span class="badge site-type"><liferay-ui:message key="public" /></span>
									</c:if>
								</a>
							</li>
						</c:if>

						<c:if test="<%= showPrivateSite && ((mySiteGroup.getPrivateLayoutsPageCount() > 0) || showPrivateSiteStaging) %>">

							<%
							siteGroup = mySiteGroup;

							if (showPrivateSiteStaging) {
								siteGroup = GroupLocalServiceUtil.fetchGroup(stagingGroupId);
							}
							%>

							<li class="<%= (selectedSite && layout.isPrivateLayout()) ? "active" : "private-site" %> <%= itemCssClass %>">
								<a href="<%= HtmlUtil.escape(siteGroup.getDisplayURL(themeDisplay, true)) %>" onclick="Liferay.Util.forcePost(this); return false;" role="menuitem">

									<%
									String siteName = StringPool.BLANK;

									if (mySiteGroup.isUser()) {
										siteName = LanguageUtil.get(resourceBundle, "my-dashboard");
									}
									else {
										siteName = mySiteGroup.getDescriptiveName(locale);
									}

									if (showPrivateSiteStaging) {
										siteName = StringUtil.appendParentheticalSuffix(siteName, LanguageUtil.get(resourceBundle, "staging"));
									}

									if ((mySiteGroup.getPublicLayoutsPageCount() > 0) || showPublicSiteStaging) {
										iconCssClass = "icon-eye-close";
									}
									%>

									<%@ include file="/html/taglib/ui/my_sites/page_site_name.jspf" %>

									<c:if test="<%= (mySiteGroup.getPublicLayoutsPageCount() > 0) || showPublicSiteStaging %>">
										<span class="badge site-type"><liferay-ui:message key="private" /></span>
									</c:if>
								</a>
							</li>
						</c:if>
					</c:when>
					<c:when test='<%= PropsValues.MY_SITES_DISPLAY_STYLE.equals("classic") %>'>

						<%
						String publicAddPageHREF = null;
						String privateAddPageHREF = null;

						if ((mySiteGroup.isSite() || mySiteGroup.isUser()) && GroupPermissionUtil.contains(permissionChecker, mySiteGroup, ActionKeys.ADD_LAYOUT)) {
							PortletURL publicAddPageURL = PortletProviderUtil.getPortletURL(request, Layout.class.getName(), PortletProvider.Action.VIEW);

							publicAddPageURL.setParameter("tabs1", "public-pages");
							publicAddPageURL.setParameter("redirect", currentURL);
							publicAddPageURL.setParameter("groupId", String.valueOf(mySiteGroup.getGroupId()));
							publicAddPageURL.setPortletMode(PortletMode.VIEW);
							publicAddPageURL.setWindowState(WindowState.MAXIMIZED);

							publicAddPageHREF = publicAddPageURL.toString();

							PortletURL privateAddPageURL = PortletProviderUtil.getPortletURL(request, Layout.class.getName(), PortletProvider.Action.EDIT);

							privateAddPageURL.setParameter("tabs1", "private-pages");
							privateAddPageURL.setParameter("redirect", currentURL);
							privateAddPageURL.setParameter("groupId", String.valueOf(mySiteGroup.getGroupId()));
							privateAddPageURL.setPortletMode(PortletMode.VIEW);
							privateAddPageURL.setWindowState(WindowState.MAXIMIZED);

							privateAddPageHREF = privateAddPageURL.toString();
						}

						boolean selectedSite = false;

						if (layout != null) {
							if (layout.getGroupId() == mySiteGroup.getGroupId()) {
								selectedSite = true;
							}
						}
						%>

						<li class="<%= selectedSite ? "active" : StringPool.BLANK %>">
							<c:choose>
								<c:when test="<%= mySiteGroup.isControlPanel() %>">
									<h3>
										<a href="<%= themeDisplay.getURLControlPanel() %>">
											<%= HtmlUtil.escape(mySiteGroup.getDescriptiveName(locale)) %>
										</a>
									</h3>
								</c:when>
								<c:otherwise>
									<h3>
										<a href="javascript:;">
											<c:choose>
												<c:when test="<%= mySiteGroup.isUser() %>">
													<liferay-ui:message key="my-site" />
												</c:when>
												<c:otherwise>
													<%= HtmlUtil.escape(mySiteGroup.getDescriptiveName(locale)) %>
												</c:otherwise>
											</c:choose>
										</a>
									</h3>

									<ul>
										<c:if test="<%= showPublicSite %>">
											<li>
												<a href="<%= (mySiteGroup.getPublicLayoutsPageCount() > 0) ? HtmlUtil.escape(mySiteGroup.getDisplayURL(themeDisplay, false)) : "javascript:;" %>"

												<c:if test="<%= mySiteGroup.isUser() %>">
													id="my-site-public-pages"
												</c:if>

												<c:if test="<%= mySiteGroup.getPublicLayoutsPageCount() > 0 %>">
													onclick="Liferay.Util.forcePost(this); return false;"
												</c:if>

												role="menuitem"

												><liferay-ui:message key="public-pages" /> <span class="page-count">(<%= mySiteGroup.getPublicLayoutsPageCount() %>)</span></a>

												<c:if test="<%= publicAddPageHREF != null %>">
													<a class="add-page" href="<%= HtmlUtil.escape(publicAddPageHREF) %>" onclick="Liferay.Util.forcePost(this); return false;" role="menuitem"><liferay-ui:message key="manage-pages" /></a>
												</c:if>
											</li>
										</c:if>

										<c:if test="<%= showPrivateSite %>">
											<li>
												<a href="<%= (mySiteGroup.getPrivateLayoutsPageCount() > 0) ? HtmlUtil.escape(mySiteGroup.getDisplayURL(themeDisplay, true)) : "javascript:;" %>"

												<c:if test="<%= mySiteGroup.isUser() %>">
													id="my-site-private-pages"
												</c:if>

												<c:if test="<%= mySiteGroup.getPrivateLayoutsPageCount() > 0 %>">
													onclick="Liferay.Util.forcePost(this); return false;"
												</c:if>

												role="menuitem"

												><liferay-ui:message key="private-pages" /> <span class="page-count">(<%= mySiteGroup.getPrivateLayoutsPageCount() %>)</span></a>

												<c:if test="<%= privateAddPageHREF != null %>">
													<a class="add-page" href="<%= HtmlUtil.escape(privateAddPageHREF) %>" onclick="Liferay.Util.forcePost(this); return false;" role="menuitem"><liferay-ui:message key="manage-pages" /></a>
												</c:if>
											</li>
										</c:if>
									</ul>
								</c:otherwise>
							</c:choose>
						</li>
					</c:when>
				</c:choose>
			</c:if>

		<%
		}
		%>

	</ul>
</c:if>