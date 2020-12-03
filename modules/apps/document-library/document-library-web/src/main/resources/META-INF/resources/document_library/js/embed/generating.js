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

import {fetch} from 'frontend-js-web';

const INTERVAL = 3000;

function scheduleEmbedVideoStatusCheck(url) {
	setTimeout(() => {
		fetch(url)
			.then(({status}) => {
				if (status !== 202) {
					window.location.reload();
				}
				else {
					scheduleEmbedVideoStatusCheck(url);
				}
			})
			.catch(() => {
				window.location.reload();
			});
	}, INTERVAL);
}

export default function ({getEmbedVideoStatusURL}) {
	scheduleEmbedVideoStatusCheck(getEmbedVideoStatusURL);
}
