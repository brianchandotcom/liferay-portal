/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {openToast} from 'frontend-js-components-web';
import {fetch, sub} from 'frontend-js-web';

import {displayErrorToast} from '../../../common/utils/toastUtil';

export async function restoreItemAction(
	label: string,
	loadData: any,
	method: any,
	restoreURL: any
) {
	const displayRestoreItemSuccessToast = (
		folderName: string,
		folderURL: string
	) => {
		const formmatedFolderLink = `<strong><a href="${folderURL}" class="restore-link"><u>${Liferay.Util.escapeHTML(folderName)}</u></a></strong>`;
		openToast({
			message: sub(
				Liferay.Language.get('x-was-restored-to-x'),
				label,
				formmatedFolderLink
			),
			type: 'success',
		});
	};

	if (restoreURL && method) {
		const headers = {
			'Accept': 'application/json',
			'x-csrf-token': Liferay.authToken,
		};

		try {
			const response = await fetch(restoreURL, {
				headers: {
					...headers,
					'Content-Type': 'application/json',
				},
				method,
			});

			const entry = await response.json();

			const url =
				Liferay.ThemeDisplay.getPathContext() +
				'/o/cms/object-entry-folder-context?objectEntryFolderId=' +
				entry.objectEntryFolderId;

			const entryFolderResponse = await fetch(url, {
				headers,
				method: 'GET',
			});

			const entryFolder = await entryFolderResponse.json();

			displayRestoreItemSuccessToast(
				entryFolder.label,
				entryFolder.viewFolderURL
			);
			loadData();
		}
		catch {
			displayErrorToast();
		}
	}
	else {
		displayErrorToast();
	}
}
