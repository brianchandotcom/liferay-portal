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
