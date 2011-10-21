<%--
/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

<%@ page import="com.liferay.portal.service.permission.PortalPermissionUtil" %>

<%
int max = GetterUtil.getInteger((String)request.getAttribute("liferay-ui:my_sites:max"));

if (max <= 0) {
	max = PropsValues.MY_SITES_MAX_ELEMENTS;
}

List<Group> mySites = user.getMySites(max);
%>

<c:if test="<%= !mySites.isEmpty() %>">
	<ul class="taglib-my-sites">

		<%
		PortletURL portletURL = new PortletURLImpl(request, PortletKeys.MY_SITES, plid, PortletRequest.ACTION_PHASE);

		portletURL.setWindowState(WindowState.NORMAL);
		portletURL.setPortletMode(PortletMode.VIEW);

		portletURL.setParameter("struts_action", "/my_sites/view");

		for (Group mySite : mySites) {
			String escapedSiteName = HtmlUtil.escape(mySite.getName());

			Organization organization = null;

			String publicAddPageHREF = null;
			String privateAddPageHREF = null;

			if (mySite.isRegularSite() && GroupPermissionUtil.contains(permissionChecker, mySite.getGroupId(), ActionKeys.ADD_LAYOUT)) {
				PortletURL addPageURL = new PortletURLImpl(request, PortletKeys.MY_SITES, plid, PortletRequest.ACTION_PHASE);

				addPageURL.setWindowState(WindowState.NORMAL);
				addPageURL.setPortletMode(PortletMode.VIEW);

				addPageURL.setParameter("struts_action", "/my_sites/edit_layouts");
				addPageURL.setParameter("redirect", currentURL);
				addPageURL.setParameter("groupId", String.valueOf(mySite.getGroupId()));
				addPageURL.setParameter("privateLayout", Boolean.FALSE.toString());

				publicAddPageHREF = addPageURL.toString();

				addPageURL.setParameter("privateLayout", Boolean.TRUE.toString());

				privateAddPageHREF = addPageURL.toString();
			}
			else if (mySite.isUser()) {
				PortletURL publicAddPageURL = new PortletURLImpl(request, PortletKeys.MY_ACCOUNT, plid, PortletRequest.RENDER_PHASE);

				publicAddPageURL.setWindowState(WindowState.MAXIMIZED);
				publicAddPageURL.setPortletMode(PortletMode.VIEW);

				publicAddPageURL.setParameter("struts_action", "/my_account/edit_layouts");
				publicAddPageURL.setParameter("tabs1", "public-pages");
				publicAddPageURL.setParameter("redirect", currentURL);
				publicAddPageURL.setParameter("groupId", String.valueOf(mySite.getGroupId()));

				publicAddPageHREF = publicAddPageURL.toString();

				long privateAddPagePlid = mySite.getDefaultPrivatePlid();

				PortletURL privateAddPageURL = new PortletURLImpl(request, PortletKeys.MY_ACCOUNT, plid, PortletRequest.RENDER_PHASE);

				privateAddPageURL.setWindowState(WindowState.MAXIMIZED);
				privateAddPageURL.setPortletMode(PortletMode.VIEW);

				privateAddPageURL.setParameter("struts_action", "/my_account/edit_layouts");
				privateAddPageURL.setParameter("tabs1", "private-pages");
				privateAddPageURL.setParameter("redirect", currentURL);
				privateAddPageURL.setParameter("groupId", String.valueOf(mySite.getGroupId()));

				privateAddPageHREF = privateAddPageURL.toString();

				if (!PropsValues.LAYOUT_USER_PUBLIC_LAYOUTS_MODIFIABLE) {
					publicAddPageHREF = null;
				}

				if (!PropsValues.LAYOUT_USER_PRIVATE_LAYOUTS_MODIFIABLE) {
					privateAddPageHREF = null;
				}
			}

			boolean showPublicSite = true;

			boolean hasPowerUserRole = RoleLocalServiceUtil.hasUserRole(user.getUserId(), user.getCompanyId(), RoleConstants.POWER_USER, true);

			if (mySite.getPublicLayoutsPageCount() == 0) {
				if (mySite.isRegularSite()) {
					showPublicSite = PropsValues.MY_SITES_SHOW_PUBLIC_SITES_WITH_NO_LAYOUTS;
				}
				else if (mySite.isUser()) {
					showPublicSite = PropsValues.MY_SITES_SHOW_USER_PUBLIC_SITES_WITH_NO_LAYOUTS;

					if (!PropsValues.LAYOUT_USER_PUBLIC_LAYOUTS_MODIFIABLE || (PropsValues.LAYOUT_USER_PUBLIC_LAYOUTS_POWER_USER_REQUIRED && !hasPowerUserRole)) {
						showPublicSite = false;
					}
				}
			}

			boolean showPrivateSite = true;

			if (mySite.getPrivateLayoutsPageCount() == 0) {
				if (mySite.isRegularSite()) {
					showPrivateSite = PropsValues.MY_SITES_SHOW_PRIVATE_SITES_WITH_NO_LAYOUTS;
				}
				else if (mySite.isUser()) {
					showPrivateSite = PropsValues.MY_SITES_SHOW_USER_PRIVATE_SITES_WITH_NO_LAYOUTS;

					if (!PropsValues.LAYOUT_USER_PRIVATE_LAYOUTS_MODIFIABLE || (PropsValues.LAYOUT_USER_PRIVATE_LAYOUTS_POWER_USER_REQUIRED && !hasPowerUserRole)) {
						showPrivateSite = false;
					}
				}
			}
		%>

			<c:if test="<%= showPublicSite || showPrivateSite %>">
				<c:choose>
					<c:when test='<%= PropsValues.MY_SITES_DISPLAY_STYLE.equals("simple") %>'>

						<%
						portletURL.setParameter("groupId", String.valueOf(mySite.getGroupId()));

						boolean firstSite = false;

						if (mySites.indexOf(mySite) == 0) {
							firstSite = true;
						}

						boolean lastSite = false;

						if (mySites.size() == (mySites.indexOf(mySite) + 1)) {
							lastSite = true;
						}

						boolean selectedSite = false;

						if (layout != null) {
							if (layout.getGroupId() == mySite.getGroupId()) {
								selectedSite = true;
							}
						}

						String cssClass = StringPool.BLANK;

						if (firstSite) {
							cssClass += " first";
						}

						if (lastSite) {
							cssClass += " last";
						}
						%>

						<c:choose>
							<c:when test="<%= mySite.isControlPanel() %>">
								<li class="control-panel<%= cssClass %>">
									<a href="<%= themeDisplay.getURLControlPanel() %>">

										<%
										String siteName = mySite.getDescriptiveName();
										%>

										<%@ include file="/html/taglib/ui/my_sites/page_site_name.jspf" %>

									</a>
								</li>
							</c:when>
							<c:otherwise>

								<%
								portletURL.setParameter("privateLayout", Boolean.FALSE.toString());
								%>

								<c:if test="<%= showPublicSite && (mySite.getPublicLayoutsPageCount() > 0) %>">
									<li class="<%= (selectedSite && layout.isPublicLayout()) ? "current-site" : "public-site" %> <%= cssClass %>">
										<a href="<%= HtmlUtil.escape(portletURL.toString()) %>" onclick="Liferay.Util.forcePost(this); return false;">

											<%
											String siteName = StringPool.BLANK;

											if (mySite.isUser()) {
												siteName = LanguageUtil.get(pageContext, "my-public-pages");
											}
											else if (escapedSiteName.equals(GroupConstants.GUEST)) {
												siteName = HtmlUtil.escape(themeDisplay.getAccount().getName());
											}
											else {
												siteName = mySite.getDescriptiveName();
											}
											%>

											<%@ include file="/html/taglib/ui/my_sites/page_site_name.jspf" %>

											<c:if test="<%= mySite.getPrivateLayoutsPageCount() > 0 %>">
												<span class="site-type"><liferay-ui:message key="public" /></span>
											</c:if>
										</a>
									</li>
								</c:if>

								<%
								portletURL.setParameter("privateLayout", Boolean.TRUE.toString());
								%>

								<c:if test="<%= showPrivateSite && (mySite.getPrivateLayoutsPageCount() > 0) %>">
									<li class="<%= (selectedSite && layout.isPrivateLayout()) ? "current-site" : "private-site" %> <%= cssClass %>">
										<a href="<%= HtmlUtil.escape(portletURL.toString()) %>" onclick="Liferay.Util.forcePost(this); return false;">

											<%
											String siteName = StringPool.BLANK;

											if (mySite.isUser()) {
												siteName = LanguageUtil.get(pageContext, "my-private-pages");
											}
											else if (escapedSiteName.equals(GroupConstants.GUEST)) {
												siteName = HtmlUtil.escape(themeDisplay.getAccount().getName());
											}
											else {
												siteName = mySite.getDescriptiveName();
											}
											%>

											<%@ include file="/html/taglib/ui/my_sites/page_site_name.jspf" %>

											<c:if test="<%= mySite.getPublicLayoutsPageCount() > 0 %>">
												<span class="site-type"><liferay-ui:message key="private" /></span>
											</c:if>
										</a>
									</li>
								</c:if>
							</c:otherwise>
						</c:choose>
					</c:when>
					<c:when test='<%= PropsValues.MY_SITES_DISPLAY_STYLE.equals("classic") %>'>

						<%
						boolean selectedSite = false;

						if (layout != null) {
							if (layout.getGroupId() == mySite.getGroupId()) {
								selectedSite = true;
							}
						}
						%>

						<li class="<%= selectedSite ? "current-site" : "" %>">
							<c:choose>
								<c:when test="<%= mySite.isControlPanel() %>">
									<h3>
										<a href="<%= themeDisplay.getURLControlPanel() %>">
											<%= escapedSiteName %>
										</a>
									</h3>
								</c:when>
								<c:otherwise>
									<h3>
										<a href="javascript:;">
											<c:choose>
												<c:when test="<%= mySite.isUser() %>">
													<liferay-ui:message key="my-site" />
												</c:when>
												<c:otherwise>
													<%= escapedSiteName %>
												</c:otherwise>
											</c:choose>
										</a>
									</h3>

									<ul>

										<%
										portletURL.setParameter("groupId", String.valueOf(mySite.getGroupId()));
										portletURL.setParameter("privateLayout", Boolean.FALSE.toString());
										%>

										<c:if test="<%= showPublicSite %>">
											<li>
												<a href="<%= (mySite.getPublicLayoutsPageCount() > 0) ? HtmlUtil.escape(portletURL.toString()) : "javascript:;" %>"

												<c:if test="<%= mySite.isUser() %>">
													id="my-site-public-pages"
												</c:if>

												<c:if test="<%= (mySite.getPublicLayoutsPageCount() > 0) %>">
													onclick="Liferay.Util.forcePost(this); return false;"
												</c:if>

												><liferay-ui:message key="public-pages" /> <span class="page-count">(<%= mySite.getPublicLayoutsPageCount() %>)</span></a>

												<c:if test="<%= publicAddPageHREF != null %>">
													<a class="add-page" href="<%= HtmlUtil.escape(publicAddPageHREF) %>" onclick="Liferay.Util.forcePost(this); return false;"><liferay-ui:message key="manage-pages" /></a>
												</c:if>
											</li>
										</c:if>

										<%
										portletURL.setParameter("groupId", String.valueOf(mySite.getGroupId()));
										portletURL.setParameter("privateLayout", Boolean.TRUE.toString());
										%>

										<c:if test="<%= showPrivateSite %>">
											<li>
												<a href="<%= (mySite.getPrivateLayoutsPageCount() > 0) ? HtmlUtil.escape(portletURL.toString()) : "javascript:;" %>"

												<c:if test="<%= mySite.isUser() %>">
													id="my-site-private-pages"
												</c:if>

												<c:if test="<%= mySite.getPrivateLayoutsPageCount() > 0 %>">
													onclick="Liferay.Util.forcePost(this); return false;"
												</c:if>

												><liferay-ui:message key="private-pages" /> <span class="page-count">(<%= mySite.getPrivateLayoutsPageCount() %>)</span></a>

												<c:if test="<%= privateAddPageHREF != null %>">
													<a class="add-page" href="<%= HtmlUtil.escape(privateAddPageHREF) %>" onclick="Liferay.Util.forcePost(this); return false;"><liferay-ui:message key="manage-pages" /></a>
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