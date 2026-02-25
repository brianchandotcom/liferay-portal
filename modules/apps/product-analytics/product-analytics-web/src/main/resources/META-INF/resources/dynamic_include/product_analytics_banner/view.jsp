<%--
/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/dynamic_include/init.jsp" %>

<liferay-portlet:runtime
	portletName="<%= ProductAnalyticsPortletKeys.PRODUCT_ANALYTICS_BANNER %>"
/>

<aui:script src="https://storage.googleapis.com/liferaycloud-cdn-product-experience-manager-assets-prd/self-hosted-script/product-analytics-script.umd.min.js" type="text/javascript"></aui:script>

<aui:script type="module">
	window.productAnalyticsScript = new window.productAnalyticsScript({
		consentRenewalPeriod: 12,
		lastModified: 0,
	});

	window.productAnalyticsScript.startTrackingJourney();

	Liferay.on('endNavigate', () => {
		window.productAnalyticsScript.dispose();

		window.productAnalyticsScript.startTrackingJourney();
	});
</aui:script>