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
int max = GetterUtil.getInteger((String)request.getAttribute("liferay-ui:my_places:max"));

if (max <= 0) {
	max = PropsValues.MY_PLACES_MAX_ELEMENTS;
}

List<Group> myPlaces = user.getMyPlaces(max);
%>

<c:if test="<%= !myPlaces.isEmpty() %>">
	<ul class="taglib-my-places">
		<%
		PortletURL portletURL = new PortletURLImpl(request, PortletKeys.MY_PLACES, plid, PortletRequest.ACTION_PHASE);

		portletURL.setWindowState(WindowState.NORMAL);
		portletURL.setPortletMode(PortletMode.VIEW);

		portletURL.setParameter("struts_action", "/my_places/view");

		for (Group myPlace : myPlaces) {
			myPlace = myPlace.toEscapedModel();

			boolean regularSite = myPlace.isRegularSite();
			boolean userSite = myPlace.isUser();
			int publicLayoutsPageCount = myPlace.getPublicLayoutsPageCount();
			int privateLayoutsPageCount = myPlace.getPrivateLayoutsPageCount();

			Organization organization = null;

			String publicAddPageHREF = null;
			String privateAddPageHREF = null;

			if (regularSite) {
				if (PortalPermissionUtil.contains(permissionChecker, ActionKeys.VIEW_CONTROL_PANEL)) {
					privateAddPageHREF = themeDisplay.getURLControlPanel();
				}
				else if (GroupPermissionUtil.contains(permissionChecker, myPlace.getGroupId(), ActionKeys.MANAGE_LAYOUTS)) {
					PortletURL addPageURL = new PortletURLImpl(request, PortletKeys.MY_PLACES, plid, PortletRequest.ACTION_PHASE);

					addPageURL.setWindowState(WindowState.NORMAL);
					addPageURL.setPortletMode(PortletMode.VIEW);

					addPageURL.setParameter("struts_action", "/my_places/edit_layouts");
					addPageURL.setParameter("redirect", currentURL);
					addPageURL.setParameter("groupId", String.valueOf(myPlace.getGroupId()));
					addPageURL.setParameter("privateLayout", Boolean.FALSE.toString());

					publicAddPageHREF = addPageURL.toString();

					addPageURL.setParameter("privateLayout", Boolean.TRUE.toString());

					privateAddPageHREF = addPageURL.toString();
				}
			}
			else if (userSite) {
				PortletURL publicAddPageURL = new PortletURLImpl(request, PortletKeys.MY_ACCOUNT, plid, PortletRequest.RENDER_PHASE);

				publicAddPageURL.setWindowState(WindowState.MAXIMIZED);
				publicAddPageURL.setPortletMode(PortletMode.VIEW);

				publicAddPageURL.setParameter("struts_action", "/my_account/edit_layouts");
				publicAddPageURL.setParameter("tabs1", "public-pages");
				publicAddPageURL.setParameter("redirect", currentURL);
				publicAddPageURL.setParameter("groupId", String.valueOf(myPlace.getGroupId()));

				publicAddPageHREF = publicAddPageURL.toString();

				long privateAddPagePlid = myPlace.getDefaultPrivatePlid();

				PortletURL privateAddPageURL = new PortletURLImpl(request, PortletKeys.MY_ACCOUNT, plid, PortletRequest.RENDER_PHASE);

				privateAddPageURL.setWindowState(WindowState.MAXIMIZED);
				privateAddPageURL.setPortletMode(PortletMode.VIEW);

				privateAddPageURL.setParameter("struts_action", "/my_account/edit_layouts");
				privateAddPageURL.setParameter("tabs1", "private-pages");
				privateAddPageURL.setParameter("redirect", currentURL);
				privateAddPageURL.setParameter("groupId", String.valueOf(myPlace.getGroupId()));

				privateAddPageHREF = privateAddPageURL.toString();

				if (!PropsValues.LAYOUT_USER_PUBLIC_LAYOUTS_MODIFIABLE) {
					publicAddPageHREF = null;
				}

				if (!PropsValues.LAYOUT_USER_PRIVATE_LAYOUTS_MODIFIABLE) {
					privateAddPageHREF = null;
				}
			}

			boolean showPublicPlace = true;

			boolean hasPowerUserRole = RoleLocalServiceUtil.hasUserRole(user.getUserId(), user.getCompanyId(), RoleConstants.POWER_USER, true);

			if (publicLayoutsPageCount == 0) {
				if (regularSite) {
					showPublicPlace = PropsValues.MY_PLACES_SHOW_PUBLIC_SITES_WITH_NO_LAYOUTS;
				}
				else if (userSite) {
					showPublicPlace = PropsValues.MY_PLACES_SHOW_USER_PUBLIC_SITES_WITH_NO_LAYOUTS;

					if (!PropsValues.LAYOUT_USER_PUBLIC_LAYOUTS_MODIFIABLE || (PropsValues.LAYOUT_USER_PUBLIC_LAYOUTS_POWER_USER_REQUIRED && !hasPowerUserRole)) {
						showPublicPlace = false;
					}
				}
			}

			boolean showPrivatePlace = true;

			if (privateLayoutsPageCount == 0) {
				if (regularSite) {
					showPrivatePlace = PropsValues.MY_PLACES_SHOW_PRIVATE_SITES_WITH_NO_LAYOUTS;
				}
				else if (userSite) {
					showPrivatePlace = PropsValues.MY_PLACES_SHOW_USER_PRIVATE_SITES_WITH_NO_LAYOUTS;

					if (!PropsValues.LAYOUT_USER_PRIVATE_LAYOUTS_MODIFIABLE || (PropsValues.LAYOUT_USER_PRIVATE_LAYOUTS_POWER_USER_REQUIRED && !hasPowerUserRole)) {
						showPrivatePlace = false;
					}
				}
			}
		%>

			<c:if test="<%= showPublicPlace || showPrivatePlace %>">
				<c:choose>
					<c:when test='<%= PropsValues.MY_PLACES_DISPLAY_STYLE.equals("simple") %>'>

						<%
						portletURL.setParameter("groupId", String.valueOf(myPlace.getGroupId()));
						portletURL.setParameter("privateLayout", Boolean.FALSE.toString());

						boolean firstSite = false;

						if (myPlaces.indexOf(myPlace) == 0) {
							firstSite = true;
						}

						boolean lastSite = false;

						if (myPlaces.size()	== (myPlaces.indexOf(myPlace) + 1)) {
							lastSite = true;
						}

						boolean selectedSite = false;

						if (layout != null) {
							if (layout.getGroupId() == myPlace.getGroupId()) {
								selectedSite = true;
							}
						}

						boolean selectedPlace = false;

						if (layout != null) {
							if (!layout.isPrivateLayout() && (layout.getGroupId() == myPlace.getGroupId())) {
								selectedPlace = true;
							}
						}

						String cssClass = "public-site";

						if (firstSite) {
							cssClass += " first";
						}

						if (lastSite) {
							cssClass += " last";
						}

						if (selectedSite) {
							cssClass += " current-site";
						}

						if (selectedPlace) {
							cssClass += " current-site";
						}
						%>

						<c:if test="<%= showPublicPlace && publicLayoutsPageCount > 0 %>">
							<li class="<%= cssClass %>">
								<a href="<%= HtmlUtil.escape(portletURL.toString()) %>" onclick="Liferay.Util.forcePost(this); return false;">

									<%
									String siteName = StringPool.BLANK;

									if (userSite) {
										siteName = LanguageUtil.get(pageContext, "my-public-pages");
									}
									else if (myPlace.getName().equals(GroupConstants.GUEST)) {
										siteName = HtmlUtil.escape(themeDisplay.getAccount().getName());
									}
									else {
										siteName = myPlace.getName();
									}
									%>

									<%@ include file="/html/taglib/ui/my_places/page_site_name.jspf" %>

									<c:if test="<%= privateLayoutsPageCount > 0 %>">
										<span class="site-type"><liferay-ui:message key="public" /></span>
									</c:if>
								</a>
							</li>
						</c:if>

						<%
						portletURL.setParameter("privateLayout", Boolean.TRUE.toString());

						selectedPlace = false;

						if (layout != null) {
							selectedPlace = layout.isPrivateLayout() && (layout.getGroupId() == myPlace.getGroupId());
						}

						cssClass = "private-site";

						if (myPlace.isControlPanel()) {
							cssClass += " control-panel";
						}

						if (selectedSite) {
							cssClass += " current-site";
						}

						if (selectedPlace) {
							cssClass += " current-site";
						}
						%>

						<c:if test="<%= showPrivatePlace && privateLayoutsPageCount > 0 %>">
							<li class="<%= cssClass %>">
								<a href="<%= HtmlUtil.escape(portletURL.toString()) %>" onclick="Liferay.Util.forcePost(this); return false;">

									<%
									String siteName = StringPool.BLANK;

									if (userSite) {
										siteName = LanguageUtil.get(pageContext, "my-private-pages");
									}
									else if (myPlace.getName().equals(GroupConstants.GUEST)) {
										siteName = HtmlUtil.escape(themeDisplay.getAccount().getName());
									}
									else {
										siteName = myPlace.getName();
									}
									%>

									<%@ include file="/html/taglib/ui/my_places/page_site_name.jspf" %>

									<c:if test="<%= publicLayoutsPageCount > 0 %>">
										<span class="site-type"><liferay-ui:message key="private" /></span>
									</c:if>
								</a>
							</li>
						</c:if>
					</c:when>
					<c:when test='<%= PropsValues.MY_PLACES_DISPLAY_STYLE.equals("classic") %>'>

						<%
						boolean selectedSite = false;

						if (layout != null) {
							if (layout.getGroupId() == myPlace.getGroupId()) {
								selectedSite = true;
							}
						}
						%>

						<li class="<%= selectedSite ? "current-site" : "" %>">
							<h3>
								<a href="javascript:;">
									<c:choose>
										<c:when test="<%= userSite %>">
											<liferay-ui:message key="my-site" />
										</c:when>
										<c:otherwise>
											<%= myPlace.getName() %>
										</c:otherwise>
									</c:choose>
								</a>
							</h3>

							<ul>

								<%
								portletURL.setParameter("groupId", String.valueOf(myPlace.getGroupId()));
								portletURL.setParameter("privateLayout", Boolean.FALSE.toString());

								boolean selectedPlace = false;

								if (layout != null) {
									selectedPlace = !layout.isPrivateLayout() && (layout.getGroupId() == myPlace.getGroupId());
								}
								%>

								<c:if test="<%= showPublicPlace %>">
									<li class="public <%= selectedPlace ? "current" : "" %>">
										<a href="<%= publicLayoutsPageCount > 0 ? HtmlUtil.escape(portletURL.toString()) : "javascript:;" %>"

										<c:if test="<%= userSite %>">
											id="my-site-public-pages"
										</c:if>

										<c:if test="<%= publicLayoutsPageCount > 0 %>">
											onclick="Liferay.Util.forcePost(this); return false;"
										</c:if>

										><liferay-ui:message key="public-pages" /> <span class="page-count">(<%= publicLayoutsPageCount %>)</span></a>

										<c:if test="<%= publicAddPageHREF != null %>">
											<a class="add-page" href="<%= HtmlUtil.escape(publicAddPageHREF) %>" onclick="Liferay.Util.forcePost(this); return false;"><liferay-ui:message key="manage-pages" /></a>
										</c:if>
									</li>
								</c:if>

								<%
								portletURL.setParameter("groupId", String.valueOf(myPlace.getGroupId()));
								portletURL.setParameter("privateLayout", Boolean.TRUE.toString());

								selectedPlace = false;

								if (layout != null) {
									selectedPlace = layout.isPrivateLayout() && (layout.getGroupId() == myPlace.getGroupId());
								}
								%>

								<c:if test="<%= showPrivatePlace %>">
									<li class="private <%= selectedPlace ? "current" : "" %>">
										<a href="<%= privateLayoutsPageCount > 0 ? HtmlUtil.escape(portletURL.toString()) : "javascript:;" %>"

										<c:if test="<%= userSite %>">
											id="my-site-private-pages"
										</c:if>

										<c:if test="<%= privateLayoutsPageCount > 0 %>">
											onclick="Liferay.Util.forcePost(this); return false;"
										</c:if>

										><liferay-ui:message key="private-pages" /> <span class="page-count">(<%= privateLayoutsPageCount %>)</span></a>

										<c:if test="<%= privateAddPageHREF != null %>">
											<a class="add-page" href="<%= HtmlUtil.escape(privateAddPageHREF) %>" onclick="Liferay.Util.forcePost(this); return false;"><liferay-ui:message key="manage-pages" /></a>
										</c:if>
									</li>
								</c:if>
							</ul>
						</li>
					</c:when>
				</c:choose>
			</c:if>

		<%
		}
		%>

	</ul>
</c:if>