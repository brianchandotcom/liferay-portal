<%--
/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/taglib/ui/sites_directory/init.jsp" %>

<c:if test="<%= layout != null %>">

	<%
	PortletURL portletURL = new PortletURLImpl(request, portletDisplay.getId(), plid, PortletRequest.RENDER_PHASE);

	Group rootGroup = null;
	boolean hidden = false;

	List selBranch = new ArrayList();

	selBranch.add(group);
	selBranch.addAll(group.getAncestors());

	if (rootGroupType.equals("relative")) {
		if ((rootGroupLevel >= 0) && (rootGroupLevel < selBranch.size())) {
			rootGroup = (Group)selBranch.get(rootGroupLevel);
		}
		else {
			rootGroup = null;
		}
	}
	else if (rootGroupType.equals("absolute")) {
		int ancestorIndex = selBranch.size() - rootGroupLevel;

		if ((ancestorIndex >= 0) && (ancestorIndex < selBranch.size())) {
			rootGroup = (Group)selBranch.get(ancestorIndex);
		}
		else if (ancestorIndex == selBranch.size()) {
			rootGroup = null;
		}
		else {
			hidden = true;
		}
	}
	%>

	<div class="sites-directory-taglib nav-menu nav-menu-style-<%= bulletStyle %>">

		<c:choose>
			<c:when test='<%= (headerType.equals("root-group") && (rootGroup != null)) %>'>

				<%
				boolean privateLayoutSet = false;

				if (!rootGroup.hasPublicLayouts()) {
					privateLayoutSet = true;
				}

				String groupFriendlyURL = PortalUtil.getGroupFriendlyURL(rootGroup, privateLayoutSet, themeDisplay);
				String groupName = rootGroup.getDescriptiveName(themeDisplay.getLocale());
				%>

				<h2>
					<a href="<%= groupFriendlyURL %>"><%= groupName %></a>
				</h2>
			</c:when>
			<c:when test='<%= headerType.equals("portlet-title") %>'>
				<h2><%= themeDisplay.getPortletDisplay().getTitle() %></h2>
			</c:when>
			<c:when test='<%= headerType.equals("breadcrumb") %>'>
				<liferay-ui:breadcrumb />
			</c:when>
		</c:choose>

		<c:if test="<%= !hidden %>">
			<c:choose>
				<c:when test='<%= displayStyle.equals("icon") || displayStyle.equals("descriptive") %>'>
					<c:choose>
						<c:when test="<%= Validator.isNull(portletDisplay.getId()) %>">
							<div class="portlet-msg-info">
								<liferay-ui:message key="sites-directory-taglib-does-not-support-x-display-style-you-can-use-this-display-style-by-using-the-sites-directory-portlet" arguments="<%= displayStyle %>"/>
							</div>
						</c:when>
						<c:otherwise>
							<liferay-ui:search-container iteratorURL="<%= portletURL %>">

								<%
								List groupChildren = null;

								if (rootGroup != null) {
									groupChildren = rootGroup.getChildrenWithLayouts(true, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
								}
								else {
									groupChildren = GroupLocalServiceUtil.getLayoutsGroups(group.getCompanyId(), GroupConstants.DEFAULT_LIVE_GROUP_ID, true, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
								}

								List<Group> visibleGroups = new UniqueList<Group>();

								for (int i = 0; i < groupChildren.size(); i++) {
									Group groupChild = (Group)groupChildren.get(i);

									LayoutSet publicLayoutSet = groupChild.getPublicLayoutSet();

									if ((publicLayoutSet != null) && (publicLayoutSet.getPageCount() > 0)) {
										visibleGroups.add(groupChild);
									}
									else {
										List<Group> mySites = user.getMySites(true, QueryUtil.ALL_POS);

										if (mySites.contains(groupChild)) {
											visibleGroups.add(groupChild);
										}
									}
								}
								%>

								<liferay-ui:search-container-results
									results="<%= ListUtil.subList(visibleGroups, searchContainer.getStart(), searchContainer.getEnd()) %>"
									total="<%= visibleGroups.size() %>"
								/>

								<liferay-ui:search-container-row
									className="com.liferay.portal.model.Group"
									modelVar="groupChild"
								>

									<%
									LayoutSet layoutSet = null;

									LayoutSet publicLayoutSet = groupChild.getPublicLayoutSet();

									boolean hasPublicLayouts = false;

									if ((publicLayoutSet != null) && (publicLayoutSet.getPageCount() > 0)) {
										layoutSet = publicLayoutSet;

										hasPublicLayouts = true;
									}
									else {
										layoutSet = groupChild.getPrivateLayoutSet();
									}

									String groupFriendlyURL = PortalUtil.getGroupFriendlyURL(groupChild, !hasPublicLayouts, themeDisplay);
									%>

									<liferay-ui:app-view-entry
										description="<%= groupChild.getDescription() %>"
										displayStyle="<%= displayStyle%>"
										showCheckbox="<%= false %>"
										thumbnailSrc='<%= themeDisplay.getPathImage() + "/layout_set_logo?img_id=" + layoutSet.getLogoId() + "&t=" + WebServerServletTokenUtil.getToken(layoutSet.getLogoId()) %>'
										title="<%= HtmlUtil.escape(groupChild.getDescriptiveName(themeDisplay.getLocale())) %>"
										url="<%= groupFriendlyURL %>"
									/>
								</liferay-ui:search-container-row>

								<liferay-ui:search-iterator />
							</liferay-ui:search-container>
						</c:otherwise>
					</c:choose>
				</c:when>
				<c:otherwise>

					<%
					StringBundler sb = new StringBundler();

					_buildNavigation(user, rootGroup, group, selBranch, themeDisplay, 1, includedGroups, nestedChildren, sb);

					String content = sb.toString();
					%>

					<%= content %>

				</c:otherwise>
			</c:choose>
		</c:if>
	</div>
</c:if>

<%!
private void _buildNavigation(User user, Group rootGroup, Group selGroup, List selBranch, ThemeDisplay themeDisplay, int groupLevel, String includedGroups, boolean nestedChildren, StringBundler sb) throws Exception {
	List groupChildren = null;

	if (rootGroup != null) {
		groupChildren = rootGroup.getChildrenWithLayouts(true, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}
	else {
		groupChildren = GroupLocalServiceUtil.getLayoutsGroups(selGroup.getCompanyId(), GroupConstants.DEFAULT_LIVE_GROUP_ID, true, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	List<Group> visibleGroups = new UniqueList<Group>();

	for (int i = 0; i < groupChildren.size(); i++) {
		Group groupChild = (Group)groupChildren.get(i);

		LayoutSet publicLayoutSet = groupChild.getPublicLayoutSet();

		if ((publicLayoutSet != null) && (publicLayoutSet.getPageCount() > 0)) {
			visibleGroups.add(groupChild);
		}
		else {
			List<Group> mySites = user.getMySites(true, QueryUtil.ALL_POS);

			if (mySites.contains(groupChild)) {
				visibleGroups.add(groupChild);
			}
		}
	}

	if (!groupChildren.isEmpty()) {
		StringBundler tailSB = null;

		if (!nestedChildren) {
			tailSB = new StringBundler();
		}

		sb.append("<ul class=\"sites level-");
		sb.append(groupLevel);
		sb.append("\">");

		for (int i = 0; i < groupChildren.size(); i++) {
			Group groupChild = (Group)groupChildren.get(i);

			LayoutSet publicLayoutSet = groupChild.getPublicLayoutSet();

			boolean hasPublicLayouts = false;

			if ((publicLayoutSet != null) && (publicLayoutSet.getPageCount() > 0)) {
				hasPublicLayouts = true;
			}

			String groupFriendlyURL = PortalUtil.getGroupFriendlyURL(groupChild, !hasPublicLayouts, themeDisplay);

			boolean open = false;

			if (includedGroups.equals("auto") && selBranch.contains(groupChild) && !groupChild.getChildren(true).isEmpty()) {
				open = true;
			}

			if (includedGroups.equals("all")) {
				open = true;
			}

			StringBundler className = new StringBundler(2);

			if (open) {
				className.append("open ");
			}

			if (selGroup.getGroupId() == groupChild.getGroupId()) {
				className.append("selected ");
			}

			sb.append("<li ");

			if (Validator.isNotNull(className)) {
				sb.append("class=\"");
				sb.append(className);
				sb.append("\" ");
			}

			sb.append(">");

			sb.append("<a ");

			if (Validator.isNotNull(className)) {
				sb.append("class=\"");
				sb.append(className);
				sb.append("\" ");
			}

			sb.append("href=\"");
			sb.append(HtmlUtil.escapeHREF(groupFriendlyURL));
			sb.append("\"> ");

			sb.append(HtmlUtil.escape(groupChild.getDescriptiveName(themeDisplay.getLocale())));

			sb.append("</a>");

			if (open) {
				StringBundler groupChildSB = null;

				if (nestedChildren) {
					groupChildSB = sb;
				}
				else {
					groupChildSB = tailSB;
				}

				_buildNavigation(user, groupChild, selGroup, selBranch, themeDisplay, groupLevel + 1, includedGroups, nestedChildren, groupChildSB);
			}

			sb.append("</li>");
		}

		sb.append("</ul>");

		if (!nestedChildren) {
			sb.append(tailSB);
		}
	}
}
%>