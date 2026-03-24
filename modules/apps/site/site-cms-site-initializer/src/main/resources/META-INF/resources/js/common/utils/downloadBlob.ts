/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/**
 * Gets the filename from the response headers.
 *
 * @param {Response} response
 * @param {string} defaultFilename
 */
function getFilenameFromResponse(
	response: Response,
	defaultFilename: string = 'export.zip'
): string {
	const contentDisposition = response.headers.get('Content-Disposition');

	if (contentDisposition) {
		const filenameMatch = contentDisposition.match(/filename="?([^"]+)"?/);

		if (filenameMatch && filenameMatch.length > 1) {
			return filenameMatch[1];
		}
	}

	return defaultFilename;
}

/**
 * Downloads a blob as a file.
 *
 * @param {Response | Promise<Blob> | Blob} data
 * @param {string} filename
 */
export async function downloadBlob(
	data: Response | Promise<Blob> | Blob,
	filename?: string
): Promise<void> {
	let blob: Blob;
	let finalFilename: string | undefined = filename;

	if (data instanceof Response) {
		finalFilename = finalFilename || getFilenameFromResponse(data);
		blob = await data.blob();
	}
	else {
		blob = await data;
	}

	if (!finalFilename) {
		throw new Error('Filename is required');
	}

	const blobURL = URL.createObjectURL(blob);

	const link = document.createElement('a');

	link.href = blobURL;
	link.download = finalFilename;

	document.body.appendChild(link);

	link.click();

	document.body.removeChild(link);

	URL.revokeObjectURL(blobURL);
}
