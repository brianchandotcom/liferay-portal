/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {fetch} from 'frontend-js-web';

export async function exportObjectEntity({
	exportObjectEntityURL,
	objectEntityId,
}: {
	exportObjectEntityURL: string;
	objectEntityId: number;
}) {
	if (objectEntityId) {
		const response = await fetch(exportObjectEntityURL);
		const responseHeaders = response.headers.get('Content-Disposition');

		if (response.ok && responseHeaders?.includes('attachment')) {
			const responseBlob = await response.blob();
			const downloadElement = document.createElement('a');

			downloadElement.download =
				responseHeaders.match(/filename="([^"]+)"/)![1];

			downloadElement.href = URL.createObjectURL(responseBlob);

			document.body.appendChild(downloadElement);
			downloadElement.click();
		}
	}
}
