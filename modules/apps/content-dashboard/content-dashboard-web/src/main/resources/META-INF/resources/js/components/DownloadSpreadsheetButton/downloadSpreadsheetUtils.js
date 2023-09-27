/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

	const fileLink = document.createElement('a');
	fileLink.href = file;

	const today = new Date();
	const filename = `ContentDashboardItemsData-${today.toLocaleDateString(
		'en-US'
	)}.xls`;

	fileLink.download = filename;

	fileLink.click();

	URL.revokeObjectURL(file);
}
