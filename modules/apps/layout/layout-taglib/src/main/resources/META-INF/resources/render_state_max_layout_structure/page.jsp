<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/render_state_max_layout_structure/init.jsp" %>

<style type="text/css">
	.master-layout-fragment .portlet-header {
		display: none;
	}
</style>

<%
RenderMaxStateLayoutStructureDisplayContext renderMaxStateLayoutStructureDisplayContext = new RenderMaxStateLayoutStructureDisplayContext();
%>

<liferay-layout:render-layout-structure
	layoutStructure="<%= renderMaxStateLayoutStructureDisplayContext.getLayoutStructure(themeDisplay.getLayout()) %>"
/>