/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {openToast} from 'frontend-js-components-web';
import {fetch} from 'frontend-js-web';

import {
	IBulkActionFDSDataItemTransformed,
	IBulkActionTaskStarter,
	IBulkActionTaskStarterDTO,
	IBulkActionTaskType,
} from '../../../common/types/BulkActionTask';
import {displayErrorToast} from '../../../common/utils/toastUtil';
import {BulkActionTaskStarter} from '../../bulk_actions_monitor/services/BulkActionTaskStarter';

export async function triggerAssetDownloadBulkAction(
	dto: IBulkActionTaskStarterDTO<keyof IBulkActionTaskType>
): Promise<void> {
	const bulkAction: IBulkActionTaskStarter = new BulkActionTaskStarter({
		...dto,
	});

	if (!dto.selectedData.selectAll) {
		const files = bulkAction.payload.bulkActionItems.filter(
			(bulkActionItem: IBulkActionFDSDataItemTransformed) =>
				bulkActionItem.file
		);

		if (!files.length) {
			displayErrorToast(
				Liferay.Language.get(
					'unexpected-error-unable-to-process-the-bulk-download.-please-check-your-selection-and-try-again'
				)
			);

			return;
		}

		if (files.length !== bulkAction.payload.bulkActionItems.length) {
			openToast({
				message: Liferay.Language.get(
					'you-have-selected-both-content-and-file-assets.-only-file-assets-can-be-downloaded.-content-assets-will-be-skipped'
				),
				type: 'warning',
			});
		}
	}

	openToast({
		message: Liferay.Language.get(
			'preparing-your-files-for-download.-please-do-not-close-this-window-or-navigate-to-another-section'
		),
		type: 'warning',
	});

	return fetch(bulkAction.postURL, {
		body: JSON.stringify(bulkAction.payload),
		headers: new Headers({
			'Accept': 'application/zip',
			'Accept-Language': Liferay.ThemeDisplay.getBCP47LanguageId(),
			'Content-Type': 'application/json',
		}),
		method: 'POST',
	}).then(async (response): Promise<void> => {
		if (!response.ok) {
			displayErrorToast(
				Liferay.Language.get(
					'unexpected-error-unable-to-process-the-bulk-download.-please-check-your-selection-and-try-again'
				)
			);
		}
		else {
			openToast({
				message: Liferay.Language.get(
					'your-files-are-ready-the-download-will-begin-shortly'
				),
				title: Liferay.Language.get('success'),
				type: 'success',
			});

			const blob = response.blob();
			const blobURL = URL.createObjectURL(await blob);

			const link = document.createElement('a');
			link.href = blobURL;

			link.click();

			URL.revokeObjectURL(blobURL);
		}
	});
}
