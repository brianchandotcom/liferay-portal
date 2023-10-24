<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
PortletURL viewURL = renderResponse.createRenderURL();

String portletId = portletDisplay.getId();

if (portletId.equals(MarketplaceStorePortletKeys.MARKETPLACE_STORE)) {
	long appEntryId = ParamUtil.getLong(request, "appEntryId");

	if (appEntryId <= 0) {
		viewURL.setParameter("remoteMVCPath", "/marketplace/view.jsp");
	}
	else {
		viewURL.setParameter("remoteMVCPath", "/marketplace/view_app_entry.jsp");
		viewURL.setParameter("appEntryId", String.valueOf(appEntryId));
	}
}
else {
	viewURL.setParameter("remoteMVCPath", "/marketplace_server/view_purchased.jsp");
}

viewURL.setWindowState(LiferayWindowState.EXCLUSIVE);
%>

<iframe frameborder="0" id="<portlet:namespace />frame" name="<portlet:namespace />frame" scrolling="no" src="<%= viewURL %>"></iframe>

<c:if test="<%= GetterUtil.getBoolean(request.getAttribute(MarketplaceStoreWebKeys.OAUTH_AUTHORIZED)) %>">
	<div class="sign-out">
		<liferay-portlet:actionURL name="deauthorize" var="deauthorizeURL" />

		<aui:button onClick="<%= deauthorizeURL %>" value="sign-out" />
	</div>
</c:if>

<liferay-frontend:component
	module="js/MarketplaceFrame"
/>