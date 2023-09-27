<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
int delta = ParamUtil.getInteger(request, SearchContainer.DEFAULT_DELTA_PARAM, SearchContainer.DEFAULT_DELTA);
%>

<liferay-frontend:screen-navigation
	key="<%= ServerAdminNavigationEntryConstants.SCREEN_NAVIGATION_KEY_PROPERTIES %>"
	portletURL='<%=
		PortletURLBuilder.createRenderURL(
			renderResponse
		).setMVCRenderCommandName(
			"/server_admin/view"
		).setTabs1(
			tabs1
		).setParameter(
			"delta", delta
		).buildPortletURL()
	%>'
/>