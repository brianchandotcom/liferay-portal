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

<%@ include file="/html/portal/layout/edit/init.jsp" %>

<%
LayoutTypePortlet selLayoutTypePortlet = null;

Theme selTheme = layout.getTheme();

if (selLayout != null) {
	selLayoutTypePortlet = (LayoutTypePortlet)selLayout.getLayoutType();

	selTheme = selLayout.getTheme();
}
%>

<liferay-ui:layout-templates-list
	layoutTemplateId="<%= (selLayoutTypePortlet != null) ? selLayoutTypePortlet.getLayoutTemplateId() : StringPool.BLANK %>"
	layoutTemplates="<%= LayoutTemplateLocalServiceUtil.getLayoutTemplates(selTheme.getThemeId()) %>"
/>