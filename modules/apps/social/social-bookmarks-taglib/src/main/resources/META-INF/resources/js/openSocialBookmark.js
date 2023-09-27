/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const SHARE_WINDOW_HEIGHT = 436;
const SHARE_WINDOW_WIDTH = 626;

export default function openSocialBookmark({
	className,
	classPK,
	postURL,
	type,
	url,
}) {
	const shareWindowFeatures = `
		height=${SHARE_WINDOW_HEIGHT},
		left=${window.innerWidth / 2 - SHARE_WINDOW_WIDTH / 2},
		status=0,
		toolbar=0,
		top=${window.innerHeight / 2 - SHARE_WINDOW_HEIGHT / 2},
		width=${SHARE_WINDOW_WIDTH}
	`;

	window.open(postURL, null, shareWindowFeatures).focus();

	Liferay.fire('socialBookmarks:share', {
		className,
		classPK,
		type,
		url,
	});

	return false;
}
