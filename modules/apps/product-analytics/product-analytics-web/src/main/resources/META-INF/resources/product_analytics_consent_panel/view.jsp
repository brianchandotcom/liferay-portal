<%--
/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
PortletURL viewURL = renderResponse.createRenderURL();

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(ParamUtil.getString(request, "backURL", viewURL.toString()));
portletDisplay.setURLBackTitle(portletDisplay.getPortletDisplayName());

ProductAnalyticsConsentPanelDisplayContext productAnalyticsConsentPanelDisplayContext = (ProductAnalyticsConsentPanelDisplayContext)request.getAttribute(ProductAnalyticsWebKeys.PRODUCT_ANALYTICS_CONSENT_PANEL_DISPLAY_CONTEXT);

User selUser = PortalUtil.getSelectedUser(request);

renderResponse.setTitle((selUser == null) ? LanguageUtil.get(request, "add-user") : LanguageUtil.format(request, "edit-user-x", selUser.getFullName(), false));
%>

<clay:container-fluid
	cssClass="container-view p-md-4"
	id='<%= liferayPortletResponse.getNamespace() + "productAnalyticsConsentPanelForm" %>'
>
	<c:choose>
		<c:when test="<%= productAnalyticsConsentPanelDisplayContext.isShowButtons() %>">
			<clay:sheet>
				<clay:sheet-header>
					<h2 class="sheet-title">
						<liferay-ui:message key="product-analytics-configuration-name" />
					</h2>
				</clay:sheet-header>

				<liferay-util:include page="/product_analytics_consent_panel/product_analytics_consent_panel.jsp" servletContext="<%= application %>" />
			</clay:sheet>
		</c:when>
		<c:otherwise>
			<liferay-util:include page="/product_analytics_consent_panel/product_analytics_consent_panel.jsp" servletContext="<%= application %>" />
		</c:otherwise>
	</c:choose>
</clay:container-fluid>

<liferay-frontend:component
	componentId="ProductAnalyticsConsentPanel"
	context="<%= productAnalyticsConsentPanelDisplayContext.getContext() %>"
	module="{ProductAnalyticsConsentPanel} from product-analytics-web"
/>