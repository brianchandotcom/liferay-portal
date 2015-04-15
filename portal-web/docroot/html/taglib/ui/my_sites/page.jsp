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
boolean includeControlPanel = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:my_sites:includeControlPanel"));
int max = GetterUtil.getInteger((String)request.getAttribute("liferay-ui:my_sites:max"));

if (max <= 0) {
	max = PropsValues.MY_SITES_MAX_ELEMENTS;
}

List<Group> mySiteGroups = user.getMySiteGroups(classNames, includeControlPanel, max);
%>

<c:if test="<%= !mySiteGroups.isEmpty() %>">
	<ul class="taglib-my-sites <%= cssClass %>">

		<%
		for (Group mySiteGroup : mySiteGroups) {
			boolean showSite = mySiteGroup.isShowSite(permissionChecker);

			Group siteGroup = mySiteGroup;
		%>

			<c:if test="<%= showSite %>">
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

						boolean showSiteStaging = false;

						if (mySiteGroup.hasStagingGroup()) {
							Group stagingGroup = mySiteGroup.getStagingGroup();

							stagingGroupId = stagingGroup.getGroupId();

							if (GroupPermissionUtil.contains(permissionChecker, mySiteGroup, ActionKeys.VIEW_STAGING)) {
								if (!mySiteGroup.hasLayouts() && stagingGroup.hasLayouts()) {
									showSiteStaging = true;
								}
							}
						}
						%>

						<c:if test="<%= showSite && (mySiteGroup.hasLayouts() || showSiteStaging) %>">

							<%
							if (showSiteStaging) {
								siteGroup = GroupLocalServiceUtil.fetchGroup(stagingGroupId);
							}
							%>

							<li class="<%= (selectedSite && layout.isPublicLayout()) ? "active" : "public-site" %> <%= itemCssClass %>">
								<a href="<%= HtmlUtil.escape(siteGroup.getDisplayURL(themeDisplay)) %>" onclick="Liferay.Util.forcePost(this); return false;">

									<%
									String siteName = StringPool.BLANK;

									if (mySiteGroup.isUser()) {
										siteName = LanguageUtil.get(request, "my-profile");
									}
									else {
										siteName = mySiteGroup.getDescriptiveName(locale);
									}

									if (showSiteStaging) {
										StringBundler sb = new StringBundler(5);

										sb.append(HtmlUtil.escape(siteName));
										sb.append(StringPool.SPACE);
										sb.append(StringPool.OPEN_PARENTHESIS);
										sb.append(LanguageUtil.get(request, "staging"));
										sb.append(StringPool.CLOSE_PARENTHESIS);

										siteName = sb.toString();
									}
									%>

									<%@ include file="/html/taglib/ui/my_sites/page_site_name.jspf" %>
								</a>
							</li>
						</c:if>
					</c:when>
					<c:when test='<%= PropsValues.MY_SITES_DISPLAY_STYLE.equals("classic") %>'>

						<%
						String addPageHREF = null;

						if (mySiteGroup.isSite() && GroupPermissionUtil.contains(permissionChecker, mySiteGroup, ActionKeys.ADD_LAYOUT)) {
							addPageHREF = mySiteGroup.getDisplayURL(themeDisplay);
						}
						else if (mySiteGroup.isUser()) {
							PortletURL addPageURL = new PortletURLImpl(request, PortletKeys.MY_ACCOUNT, plid, PortletRequest.RENDER_PHASE);

							addPageURL.setParameter("struts_action", "/my_account/edit_layouts");
							addPageURL.setParameter("redirect", currentURL);
							addPageURL.setParameter("groupId", String.valueOf(mySiteGroup.getGroupId()));
							addPageURL.setPortletMode(PortletMode.VIEW);
							addPageURL.setWindowState(WindowState.MAXIMIZED);

							addPageHREF = addPageURL.toString();
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
										<c:if test="<%= showSite %>">
											<li>
												<a href="<%= mySiteGroup.hasLayouts() ? HtmlUtil.escape(mySiteGroup.getDisplayURL(themeDisplay)) : "javascript:;" %>"

												<c:if test="<%= mySiteGroup.isUser() %>">
													id="my-site-pages"
												</c:if>

												<c:if test="<%= mySiteGroup.hasLayouts() %>">
													onclick="Liferay.Util.forcePost(this); return false;"
												</c:if>

												><liferay-ui:message key="pages" /> <span class="page-count">(<%= mySiteGroup.getLayoutsPageCount() %>)</span></a>

												<c:if test="<%= addPageHREF != null %>">
													<a class="add-page" href="<%= HtmlUtil.escape(addPageHREF) %>" onclick="Liferay.Util.forcePost(this); return false;"><liferay-ui:message key="manage-pages" /></a>
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