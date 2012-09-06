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

<%@ include file="/html/taglib/ui/navigation/init.jsp" %>

<c:if test="<%= layout != null %>">

	<%
	Layout rootLayout = null;
	boolean hidden = false;

	List selBranch = new ArrayList();

	selBranch.add(layout);
	selBranch.addAll(layout.getAncestors());

	if (rootLayoutType.equals("relative")) {
		if ((rootLayoutLevel >= 0) && (rootLayoutLevel < selBranch.size())) {
			rootLayout = (Layout)selBranch.get(rootLayoutLevel);
		}
		else {
			rootLayout = null;
		}
	}
	else if (rootLayoutType.equals("absolute")) {
		int ancestorIndex = selBranch.size() - rootLayoutLevel;

		if ((ancestorIndex >= 0) && (ancestorIndex < selBranch.size())) {
			rootLayout = (Layout) selBranch.get(ancestorIndex);
		}
		else if (ancestorIndex == selBranch.size()) {
			rootLayout = null;
		}
		else {
			hidden = true;
		}
	}
	%>

	<div class="nav-menu nav-menu-style-<%= bulletStyle %>">

		<c:choose>
			<c:when test='<%= (headerType.equals("root-layout") && (rootLayout != null)) %>'>

				<%
				String layoutURL = PortalUtil.getLayoutURL(rootLayout, themeDisplay);
				String target = PortalUtil.getLayoutTarget(rootLayout);
				String layoutName = rootLayout.getName(themeDisplay.getLocale());
				%>

				<h2>
					<a href="<%= layoutURL %>" <%= target %>><%= layoutName %></a>
				</h2>
			</c:when>
			<c:when test='<%= headerType.equals("portlet-title") %>'>
				<h2><%= themeDisplay.getPortletDisplay().getTitle() %></h2>
			</c:when>
			<c:when test='<%= headerType.equals("breadcrumb") %>'>
				<liferay-ui:breadcrumb />
			</c:when>
		</c:choose>

		<%
		if (!hidden) {
			StringBundler sb = new StringBundler();

			_buildNavigation(rootLayout, layout, selBranch, themeDisplay, 1, includedLayouts, nestedChildren, sb);

			String content = sb.toString();

			/*if (!nestedChildren) {
				content = StringUtil.replace(content, "</a><ul class", "</a></li></ul><ul class");
				content = StringUtil.replace(content, "</ul></li>", "</ul><ul class=\"layouts\">");
			}*/
		%>

			<%= content %>

		<%
		}
		%>

	</div>
</c:if>

<%!
private void _buildNavigation(Layout rootLayout, Layout selLayout, List selBranch, ThemeDisplay themeDisplay, int layoutLevel, String includedLayouts, boolean nestedChildren, StringBundler sb) throws Exception {
	List layoutChildren = null;

	if (rootLayout != null) {
		layoutChildren = rootLayout.getChildren(themeDisplay.getPermissionChecker());
	}
	else {
		layoutChildren = LayoutLocalServiceUtil.getLayouts(selLayout.getGroupId(), selLayout.isPrivateLayout(), LayoutConstants.DEFAULT_PARENT_LAYOUT_ID);
	}

	if (!layoutChildren.isEmpty()) {
		StringBundler tailSB = null;

		if (!nestedChildren) {
			tailSB = new StringBundler();
		}

		sb.append("<ul class=\"layouts level-");
		sb.append(layoutLevel);
		sb.append("\">");

		for (int i = 0; i < layoutChildren.size(); i++) {
			Layout layoutChild = (Layout)layoutChildren.get(i);

			if (!layoutChild.isHidden() && LayoutPermissionUtil.contains(themeDisplay.getPermissionChecker(), layoutChild, ActionKeys.VIEW)) {
				String layoutURL = PortalUtil.getLayoutURL(layoutChild, themeDisplay);
				String target = PortalUtil.getLayoutTarget(layoutChild);

				boolean open = false;

				if (includedLayouts.equals("auto") && selBranch.contains(layoutChild) && !layoutChild.getChildren().isEmpty()) {
					open = true;
				}

				if (includedLayouts.equals("all")) {
					open = true;
				}

				StringBundler className = new StringBundler(2);

				if (open) {
					className.append("open ");
				}

				if (selLayout.getLayoutId() == layoutChild.getLayoutId()) {
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
				sb.append(HtmlUtil.escapeHREF(layoutURL));
				sb.append("\" ");
				sb.append(target);
				sb.append("> ");
				sb.append(HtmlUtil.escape(layoutChild.getName(themeDisplay.getLocale())));
				sb.append("</a>");

				if (open) {
					StringBundler layoutChildSB = null;

					if (nestedChildren) {
						layoutChildSB = sb;
					}
					else {
						layoutChildSB = tailSB;
					}

					_buildNavigation(layoutChild, selLayout, selBranch, themeDisplay, layoutLevel + 1, includedLayouts, nestedChildren, layoutChildSB);
				}

				sb.append("</li>");
			}
		}

		sb.append("</ul>");

		if (!nestedChildren) {
			sb.append(tailSB);
		}
	}
}
%>