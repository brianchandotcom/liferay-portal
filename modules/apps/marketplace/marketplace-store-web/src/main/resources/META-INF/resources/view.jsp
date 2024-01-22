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

<aui:script use="liferay-marketplace-messenger">
	var frame = document.getElementById('<portlet:namespace />frame');

	Liferay.MarketplaceMessenger.init({
		targetFrame: frame,
	});

	Liferay.MarketplaceMessenger.receiveMessage(
		(event) => {
			var response = event.responseData;

			if (response.cmd) {
				if (response.cmd == 'resize' || response.cmd == 'init') {
					if (response.height) {
						frame.style.height = response.height + 50 + 'px';
					}

					if (response.width) {
						frame.style.width = response.width + 'px';
					}
				}
				else if (response.cmd == 'scrollTo' || response.cmd == 'init') {
					var scrollX = response.scrollX || 0;
					var scrollY = response.scrollY || 0;

					window.scrollTo(scrollX, scrollY);
				}
				else if (response.cmd == 'goto') {
					var url = '<%= themeDisplay.getURLControlPanel() %>';

					if (response.panel == 'purchased') {
						url =
							'<liferay-portlet:renderURL doAsGroupId="<%= themeDisplay.getScopeGroupId() %>" portletName="<%= MarketplaceStorePortletKeys.MARKETPLACE_PURCHASED %>" windowState="<%= LiferayWindowState.MAXIMIZED.toString() %>" />';
					}
					else if (response.panel == 'store') {
						url =
							'<liferay-portlet:renderURL doAsGroupId="<%= themeDisplay.getScopeGroupId() %>" portletName="<%= MarketplaceStorePortletKeys.MARKETPLACE_STORE %>" windowState="<%= LiferayWindowState.MAXIMIZED.toString() %>" />';

						if (response.appEntryId) {
							url = Liferay.Util.addParams(
								'<%= PortalUtil.getPortletNamespace(MarketplaceStorePortletKeys.MARKETPLACE_STORE) %>appEntryId=' +
									response.appEntryId,
								url
							);
						}
					}

					window.location = url;
				}
			}
		},
		() => {
			return true;
		}
	);
</aui:script>