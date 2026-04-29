<%--
/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
StyleBookEditorBreadcrumbDisplayContext styleBookEditorBreadcrumbDisplayContext = (StyleBookEditorBreadcrumbDisplayContext)request.getAttribute(StyleBookEditorBreadcrumbProductNavigationControlMenuEntry.STYLE_BOOK_EDITOR_BREADCRUMB_DISPLAY_CONTEXT);
%>

<li class="control-menu-nav-item">
	<react:component
		module="{StyleBookEditorBreadcrumb} from style-book-web"
		props='<%=
			HashMapBuilder.<String, Object>put(
				"items",
				ListUtil.fromArray(
					HashMapBuilder.<String, Object>put(
						"href", styleBookEditorBreadcrumbDisplayContext.getAssetLibraryURL()
					).put(
						"label", styleBookEditorBreadcrumbDisplayContext.getAssetLibraryName()
					).build(),
					HashMapBuilder.<String, Object>put(
						"active", true
					).put(
						"label", styleBookEditorBreadcrumbDisplayContext.getStyleBookName()
					).build())
			).build()
		%>'
	/>
</li>