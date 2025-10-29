/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {fetch} from 'frontend-js-web';

export async function downloadFile(
	url: string,
	filename: string
): Promise<void> {
	const response = await fetch(url);

	const blob = await response.blob();
	const blobURL = URL.createObjectURL(blob);

	const link = document.createElement('a');
	link.href = blobURL;
	link.download = filename;

	link.click();

	URL.revokeObjectURL(blobURL);
}
