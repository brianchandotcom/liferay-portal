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
List<Layout> rootLayouts = LayoutLocalServiceUtil.getLayouts(layout.getGroupId(), false, rootLayoutId);
%>

<liferay-ui:ddm-template-renderer className="<%= LayoutSet.class.getName() %>" displayStyle="<%= displayStyle %>" displayStyleGroupId="<%= displayStyleGroupId %>" entries="<%= rootLayouts %>">

	<%
	StringBundler sb = new StringBundler();

	_buildSiteMap(layout, rootLayouts, rootLayout, includeRootInTree, displayDepth, showCurrentPage, useHtmlTitle, showHiddenPages, 1, themeDisplay, sb);
	%>

	<%= sb.toString() %>

</liferay-ui:ddm-template-renderer>

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

	String layoutName = HtmlUtil.escape(layout.getName(themeDisplay.getLocale()));

	if (useHtmlTitle) {
		layoutName = HtmlUtil.escape(layout.getHTMLTitle(themeDisplay.getLocale()));
	}

	sb.append(layoutName);
	sb.append("</a>");
}

private void _buildSiteMap(Layout layout, List<Layout> layouts, Layout rootLayout, boolean includeRootInTree, int displayDepth, boolean showCurrentPage, boolean useHtmlTitle, boolean showHiddenPages, int curDepth, ThemeDisplay themeDisplay, StringBundler sb) throws Exception {
	if (layouts.isEmpty()) {
		return;
	}

	sb.append("<ul>");

	if (includeRootInTree && (rootLayout != null) && (curDepth == 1)) {
		sb.append("<li>");

		String cssClass = "root";

		if (rootLayout.getPlid() == layout.getPlid()) {
			cssClass += " current";
		}

		_buildLayoutView(rootLayout, cssClass, useHtmlTitle, themeDisplay, sb);

		_buildSiteMap(layout, layouts, rootLayout, includeRootInTree, displayDepth, showCurrentPage, useHtmlTitle, showHiddenPages, curDepth +1, themeDisplay, sb);

		sb.append("</li>");
	}
	else {
		for (Layout curLayout : layouts) {
			if ((showHiddenPages || !curLayout.isHidden()) && LayoutPermissionUtil.contains(themeDisplay.getPermissionChecker(), curLayout, ActionKeys.VIEW)) {
				sb.append("<li>");

				String cssClass = StringPool.BLANK;

				if (curLayout.getPlid() == layout.getPlid()) {
					cssClass = "current";
				}

				_buildLayoutView(curLayout, cssClass, useHtmlTitle, themeDisplay, sb);

				if ((displayDepth == 0) || (displayDepth > curDepth)) {
					if (showHiddenPages) {
						_buildSiteMap(layout, curLayout.getChildren(), rootLayout, includeRootInTree, displayDepth, showCurrentPage, useHtmlTitle, showHiddenPages, curDepth + 1, themeDisplay, sb);
					}
					else {
						_buildSiteMap(layout, curLayout.getChildren(themeDisplay.getPermissionChecker()), rootLayout, includeRootInTree, displayDepth, showCurrentPage, useHtmlTitle, showHiddenPages, curDepth + 1, themeDisplay, sb);
					}
				}

				sb.append("</li>");
			}
		}
	}

	sb.append("</ul>");
}
%>