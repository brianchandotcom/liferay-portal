/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/**
 * Downloads a blob as a file.
 *
 * @param {Blob} blob
 */
export async function downloadBlob(blob: Promise<Blob> | Blob): Promise<void> {
	const blobURL = URL.createObjectURL(await blob);

	const link = document.createElement('a');

	link.href = blobURL;

	link.click();

	URL.revokeObjectURL(blobURL);
}
