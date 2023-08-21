/* eslint-disable no-undef */
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const videoButtons = fragmentElement.querySelectorAll('.video-tour-button');

const randomTime =
	'?random=' + new Date().getTime() + Math.floor(Math.random() * 1000000);

if (layoutMode !== 'edit') {
	for (const videoButton of videoButtons) {
		videoButton.onclick = () =>
			Liferay.Util.openModal({
				bodyHTML: `<iframe width="100%" height="500" src=${
					configuration.videoButtonLink + randomTime
				} loading="lazy" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture" allowfullscreen></iframe>`,
				size: 'lg',
			});
	}
}
