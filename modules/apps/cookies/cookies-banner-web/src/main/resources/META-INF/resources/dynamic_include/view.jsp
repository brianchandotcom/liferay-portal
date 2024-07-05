<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/dynamic_include/init.jsp" %>

<div aria-label="banner cookies" class="cookies-banner cookies-banner-bottom" role="dialog" style="display: none;">
	<liferay-portlet:runtime
		portletName="<%= CookiesBannerPortletKeys.COOKIES_BANNER %>"
	/>
</div>