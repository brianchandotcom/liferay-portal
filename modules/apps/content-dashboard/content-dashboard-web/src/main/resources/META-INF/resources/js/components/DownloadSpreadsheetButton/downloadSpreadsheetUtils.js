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

export async function fetchFile({controller, url}) {
	const response = await fetch(url, {
		method: 'GET',
		signal: controller.signal,
	});

	const blob = await response.blob();

	return blob;
}

export function downloadFileFromBlob(blob) {
	const file = URL.createObjectURL(blob);

	var fileLink = document.createElement('a');
	fileLink.href = file;

	const today = new Date();
	const filename = `ContentDashboardItemsData-${today.toLocaleDateString(
		'en-US'
	)}.xls`;

	fileLink.download = filename;

	fileLink.click();

	URL.revokeObjectURL(file);
}
