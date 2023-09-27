<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<c:choose>
	<c:when test='<%= ParamUtil.getBoolean(request, "editable", true) %>'>
		<p class="c-mb-3 c-mt-4 h4"><liferay-ui:message key="current-theme" /></p>

		<div id="<portlet:namespace />currentThemeContainer">
			<liferay-util:include page="/look_and_feel_theme_details.jsp" servletContext="<%= application %>" />
		</div>
	</c:when>
	<c:otherwise>
		<liferay-util:include page="/look_and_feel_themes_info.jsp" servletContext="<%= application %>" />
	</c:otherwise>
</c:choose>