/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	IBulkActionFDSData,
	triggerAssetBulkAction,
} from '@liferay/site-cms-site-initializer';
import {sub} from 'frontend-js-web';

import {openCMPModal} from '../../../utils/openCMPModal';

/**
 * Executes the bulk delete action.
 */
export function executeBulkDeleteAction(
	apiURL: string,
	dataSetId: string,
	selectedData: IBulkActionFDSData,
	processClose?: () => void
): void {
	triggerAssetBulkAction({
		apiURL,
		dataSetId,
		keyValues: {className: selectedData.items[0]?.entryClassName},
		onCreateSuccess: () => {
			processClose?.();
		},
		selectedData,
		type: 'DeleteBulkAction',
	});
}

function getBulkDeleteMessage(selectedData: any): {
	confirmationMessage: string;
	title: string;
} {
	if (selectedData.selectAll) {
		return {
			confirmationMessage: Liferay.Language.get(
				'delete-tasks-confirmation'
			),
			title: Liferay.Language.get('delete-all-tasks'),
		};
	}
	else if (selectedData.items.length > 1) {
		return {
			confirmationMessage: Liferay.Language.get(
				'delete-tasks-confirmation'
			),
			title: sub(Liferay.Language.get('delete-x-tasks'), [
				selectedData.items.length,
			]),
		};
	}

	return {
		confirmationMessage: Liferay.Language.get('delete-tasks-confirmation'),
		title: Liferay.Language.get('delete-task'),
	};
}

async function handleBulkDeletion({
	apiURL,
	dataSetId,
	selectedData,
}: {
	apiURL: string;
	dataSetId: string;
	selectedData: IBulkActionFDSData;
}): Promise<void> {
	const {confirmationMessage, title} = getBulkDeleteMessage(selectedData);

	showModal(apiURL, confirmationMessage, dataSetId, title, selectedData);
}

async function showModal(
	apiURL: string,
	confirmationMessage: string,
	dataSetId: string,
	title: string,
	selectedData: any
): Promise<void> {
	openCMPModal({
		bodyHTML: `
			<div>
				<p>
					${confirmationMessage}
				</p>
			</div>
		`,
		buttons: [
			{
				displayType: 'secondary',
				label: Liferay.Language.get('cancel'),
				onClick: ({processClose}: {processClose: () => void}) => {
					processClose();
				},
				type: 'cancel',
			},
			{
				displayType: 'danger',
				label: Liferay.Language.get('delete'),
				onClick: async ({processClose}: {processClose: () => void}) => {
					processClose();

					executeBulkDeleteAction(
						apiURL,
						dataSetId,
						selectedData,
						processClose
					);
				},
			},
		],
		center: true,
		status: 'danger',
		title,
	});
}

export default async function deleteBulkAction({
	apiURL = '',
	dataSetId = '',
	selectedData,
}: {
	apiURL?: string;
	dataSetId?: string;
	selectedData: IBulkActionFDSData;
}): Promise<void> {
	await handleBulkDeletion({apiURL, dataSetId, selectedData});
}
