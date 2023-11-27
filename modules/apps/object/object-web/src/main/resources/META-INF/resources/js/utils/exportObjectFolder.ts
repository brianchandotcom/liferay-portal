/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {createResourceURL, fetch} from 'frontend-js-web';

export async function exportObjectFolder({
	baseResourceURL,
	objectFolderId,
}: {
	baseResourceURL: string;
	objectFolderId: number;
}) {
	if (objectFolderId) {
		const exportObjectFolderURL = createResourceURL(baseResourceURL, {
			objectFolderId,
			p_p_resource_id: '/object_definitions/export_object_folder',
		}).href;

		const response = await fetch(exportObjectFolderURL);
		const responseHeaders = response.headers.get('Content-Disposition');

		if (response.ok && responseHeaders?.includes('attachment')) {
			const responseBlob = await response.blob();
			const downloadElement = document.createElement('a');

			downloadElement.download = responseHeaders.match(
				/filename="([^"]+)"/
			)![1];

			downloadElement.href = URL.createObjectURL(responseBlob);

			document.body.appendChild(downloadElement);
			downloadElement.click();
		}
	}
}
