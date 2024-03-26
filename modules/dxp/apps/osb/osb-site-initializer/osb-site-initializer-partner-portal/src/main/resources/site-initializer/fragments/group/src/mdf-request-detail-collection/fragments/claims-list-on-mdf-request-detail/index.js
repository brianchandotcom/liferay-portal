/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const viewButtons = document.getElementsByClassName('view-btn');

if (viewButtons) {
	const urlParams = new URLSearchParams(window.location.href.split('?')[1]);
	for (let i = 0; i < viewButtons.length; i++) {
		viewButtons[
			i
		].href += `?${urlParams.toString()}&p_l_back_url=${encodeURIComponent(
			Liferay.ThemeDisplay.getLayoutRelativeURL()
		)}`;
	}
}
