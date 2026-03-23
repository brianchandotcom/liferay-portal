/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {openToast} from 'frontend-js-components-web';
import {fetch} from 'frontend-js-web';

import {
	IBulkActionTaskStarter,
	IBulkActionTaskStarterDTO,
	IBulkActionType,
} from '../../../common/types/BulkActionTask';
import {downloadBlob} from '../../../common/utils/downloadBlob';
import {displayErrorToast} from '../../../common/utils/toastUtil';
import {BulkActionTaskStarter} from '../../bulk_actions_monitor/services/BulkActionTaskStarter';

export async function exportTranslationBulkAction(
	dto: IBulkActionTaskStarterDTO<keyof IBulkActionType>
): Promise<void> {
	const bulkAction: IBulkActionTaskStarter = new BulkActionTaskStarter({
		...dto,
	});

	openToast({
		message: Liferay.Language.get(
			'the-export-of-all-selected-contents-is-being-prepared.-please-do-not-close-this-window-or-navigate-to-another-section'
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
			displayErrorToast();
		}
		else {
			openToast({
				message: Liferay.Language.get(
					'the-download-will-begin-shortly'
				),
				title: Liferay.Language.get('success'),
				type: 'success',
			});

			downloadBlob(response.blob());
		}
	});
}
