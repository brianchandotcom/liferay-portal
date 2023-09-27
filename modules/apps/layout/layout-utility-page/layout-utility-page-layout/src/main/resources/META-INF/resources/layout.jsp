<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<liferay-layout:render-layout-utility-page-entry
	type="<%= LayoutUtilityPageEntryConstants.TYPE_SC_NOT_FOUND %>"
>
	<div class="container pb-3 pt-3">
		<clay:alert
			displayType="danger"
			message="not-found"
		/>

		<liferay-ui:message key="the-requested-resource-could-not-be-found" />

		<br /><br />

		<%
		String url = ParamUtil.getString(request, "previousURL");

		if (Validator.isNull(url)) {
			url = PortalUtil.getCurrentURL(request);
		}

		url = HttpComponentsUtil.decodeURL(themeDisplay.getPortalURL() + url);
		%>

		<code class="lfr-url-error"><%= HtmlUtil.escape(url) %></code>
	</div>
</liferay-layout:render-layout-utility-page-entry>