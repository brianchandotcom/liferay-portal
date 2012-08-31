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

<%@ include file="/html/portlet/site_map/init.jsp" %>

<%
List<Layout> rootLayouts = LayoutLocalServiceUtil.getLayouts(layout.getGroupId(), layout.isPrivateLayout(), rootLayoutId);

long portletDisplayDDMTemplateId = PortletDisplayTemplateUtil.getPortletDisplayTemplateDDMTemplateId(themeDisplay, displayStyle);
%>

<c:choose>
	<c:when test="<%= portletDisplayDDMTemplateId > 0 %>">
		<%= PortletDisplayTemplateUtil.renderDDMTemplate(pageContext, portletDisplayDDMTemplateId, rootLayouts) %>
	</c:when>
	<c:otherwise>

		<%
		StringBundler sb = new StringBundler();

		_buildSiteMap(layout, rootLayouts, rootLayout, includeRootInTree, displayDepth, showCurrentPage, useHtmlTitle, showHiddenPages, 1, themeDisplay, sb);
		%>

		<%= sb.toString() %>
	</c:otherwise>
</c:choose>

<%!
private void _buildLayoutView(Layout layout, String cssClass, boolean useHtmlTitle, ThemeDisplay themeDisplay, StringBundler sb) throws Exception {
	String layoutURL = PortalUtil.getLayoutURL(layout, themeDisplay);
	String target = PortalUtil.getLayoutTarget(layout);

	sb.append("<a href=\"");
	sb.append(layoutURL);
	sb.append("\" ");
	sb.append(target);

	if (Validator.isNotNull(cssClass)) {
		sb.append(" class=\"");
		sb.append(cssClass);
		sb.append("\" ");
	}

	sb.append("> ");

	String layoutName = layout.getName(themeDisplay.getLocale());

	if (useHtmlTitle) {
		layoutName = layout.getHTMLTitle(themeDisplay.getLocale());
	}

	sb.append(layoutName);
	sb.append("</a>");
}

private void _buildSiteMap(Layout layout, List<Layout> layouts, Layout rootLayout, boolean includeRootInTree, int displayDepth, boolean showCurrentPage, boolean useHtmlTitle, boolean showHiddenPages, int curDepth, ThemeDisplay themeDisplay, StringBundler sb) throws Exception {
	if (layouts.isEmpty()) {
		return;
	}

	PermissionChecker permissionChecker = themeDisplay.getPermissionChecker();
	boolean showRoot = (rootLayout != null) && (curDepth == 1) && includeRootInTree;

	sb.append("<ul>");

	if (showRoot) {
		String cssClass = "root";

		sb.append("<li>");

		if (rootLayout.getPlid() == layout.getPlid()) {
			cssClass += " current";
		}

		_buildLayoutView(rootLayout, cssClass, useHtmlTitle, themeDisplay, sb);

		sb.append("</li>");
	}

	for (int i = 0; i < layouts.size(); i++) {
		Layout curLayout = layouts.get(i);

		if ((showHiddenPages || !curLayout.isHidden()) && LayoutPermissionUtil.contains(permissionChecker, curLayout, ActionKeys.VIEW)) {
			String cssClass = StringPool.BLANK;

			if (curLayout.getPlid() == layout.getPlid()) {
				cssClass = "current";
			}

			sb.append("<li>");

			_buildLayoutView(curLayout, cssClass, useHtmlTitle, themeDisplay, sb);

			if ((displayDepth == 0) || (displayDepth > curDepth)) {
				if (showHiddenPages) {
					_buildSiteMap(layout, curLayout.getChildren(), rootLayout, includeRootInTree, displayDepth, showCurrentPage, useHtmlTitle, showHiddenPages, curDepth + 1, themeDisplay, sb);
				}
				else {
					_buildSiteMap(layout, curLayout.getChildren(permissionChecker), rootLayout, includeRootInTree, displayDepth, showCurrentPage, useHtmlTitle, showHiddenPages, curDepth + 1, themeDisplay, sb);
				}
			}

			sb.append("</li>");
		}
	}

	sb.append("</ul>");
}
%>