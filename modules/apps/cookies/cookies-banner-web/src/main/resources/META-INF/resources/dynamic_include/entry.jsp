<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */
--%>

<%@ include file="/dynamic_include/init.jsp" %>

<div class="cookies-banner cookies-banner-bottom">
	<liferay-portlet:runtime
		portletName="<%= CookiesBannerPortletKeys.COOKIES_BANNER %>"
	/>
</div>

<script>
	const buttonAccept = document.querySelector('.cookies-banner-button-accept');
	const buttonConfiguration = document.querySelector(
		'.cookies-banner-button-configuration'
	);
	const buttonDecline = document.querySelector('.cookies-banner-button-decline');
	const cookieBanner = document.querySelector('.cookies-banner');

	const editMode = document.body.classList.contains('has-edit-mode-menu');

	function handleButtonClickAccept() {
		hideBanner();

		localStorage.setItem('liferay.cookie.consent', 'accepted2');
	}

	function handleButtonClickDecline() {
		hideBanner();

		localStorage.setItem('liferay.cookie.consent', 'decline2');
	}

	function handleButtonClickConfiguration() {
		Liferay.Util.openModal({
			title: 'Cookie Configuration',
			url:
				'<%=
					PortletURLBuilder.createRenderURL(
						renderResponse
					).setMVCPath(
						"configuration.jsp"
					).setWindowState(
						LiferayWindowState.POP_UP
					).buildPortletURL()
				%>',
		});
	}

	function hideBanner() {
		cookieBanner.style.display = 'none';
	}

	export default function main() {
		if (!editMode) {
			if (
				localStorage.getItem('liferay.cookie.consent') === 'accepted' ||
				localStorage.getItem('liferay.cookie.consent') === 'decline'
			) {
				hideBanner();
			}
			else {
				buttonAccept.addEventListener('click', handleButtonClickAccept);
				buttonConfiguration.addEventListener(
					'click',
					handleButtonClickConfiguration
				);
				buttonDecline.addEventListener('click', handleButtonClickDecline);
			}
		}
	}

	main();
</script>