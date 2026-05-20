<%--
/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/dynamic_include/init.jsp" %>

<liferay-theme:defineObjects />

<%
String homeURL = themeDisplay.getURLHome();
%>

<div class="alert alert-info align-items-center d-flex justify-content-between mb-3">
	<span>
		<liferay-ui:message key="cookies-banner-preview-description" />
	</span>

	<button class="btn btn-info" id="<portlet:namespace />cookiesBannerPreviewButton" type="button">
		<liferay-ui:message key="preview" />
	</button>
</div>

<aui:script>
	var cookiesBannerPreviewButton = document.getElementById(
		'<portlet:namespace />cookiesBannerPreviewButton'
	);

	if (cookiesBannerPreviewButton) {
		cookiesBannerPreviewButton.addEventListener('click', function (event) {
			event.preventDefault();

			Liferay.Util.openModal({
				id: 'cookiesBannerPreviewModal',
				size: 'full-screen',
				title: Liferay.Language.get('preview'),
				url: '<%= HtmlUtil.escapeJS(homeURL + (homeURL.contains("?") ? "&" : "?") + CookiesBannerWebKeys.COOKIES_BANNER_PREVIEW + "=1") %>',
			});
		});
	}
</aui:script>