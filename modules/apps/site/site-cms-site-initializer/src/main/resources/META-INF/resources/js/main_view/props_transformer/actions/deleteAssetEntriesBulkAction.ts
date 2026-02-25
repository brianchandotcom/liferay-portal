/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {sub} from 'frontend-js-web';

import SpaceService from '../../../common/services/SpaceService';
import {IBulkActionFDSData} from '../../../common/types/BulkActionTask';
import {getScopeExternalReferenceCode} from '../../../common/utils/getScopeExternalReferenceCode';
import {openCMSModal} from '../../../common/utils/openCMSModal';
import {isFromRecycleBin} from '../utils/isFromRecycleBin';
import {triggerAssetBulkAction} from './triggerAssetBulkAction';

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
		keyValues: {
			className: selectedData.items
				? selectedData.items[0]?.entryClassName
				: '',
		},
		onCreateSuccess: () => {
			processClose?.();
		},
		selectedData,
		type: 'DeleteBulkAction',
	});
}

/**
 * Returns the confirmation message and title for bulk delete modal.
 */
function getBulkDeleteMessage(
	selectedData: any,
	allEntriesHaveTrashEnabled: boolean,
	someEntriesHaveTrashEnabled: boolean
): {
	confirmationMessage: string;
	title: string;
} {
	if (someEntriesHaveTrashEnabled && !allEntriesHaveTrashEnabled) {
		return {
			confirmationMessage: Liferay.Language.get(
				'bulk-delete-cms-entries-confirmation'
			),
			title: Liferay.Language.get('delete-entries'),
		};
	}
	else if (selectedData.selectAll) {
		return {
			confirmationMessage: Liferay.Language.get(
				'delete-all-entries-confirmation'
			),
			title: Liferay.Language.get('delete-all-entries'),
		};
	}
	else if (selectedData.items.length > 1) {
		return {
			confirmationMessage: sub(
				Liferay.Language.get('delete-entries-confirmation'),
				[selectedData.items.length]
			),
			title: Liferay.Language.get('delete-entries'),
		};
	}

	return {
		confirmationMessage: Liferay.Language.get('delete-entry-confirmation'),
		title: Liferay.Language.get('delete-entry'),
	};
}

/**
 * Fetches asset library spaces for the given items.
 */
async function getEntriesSpaces(
	items: IBulkActionFDSData['items'] = []
): Promise<any[]> {
	const promises = items.flatMap((item) => {
		const externalReferenceCode = getScopeExternalReferenceCode(item);

		return externalReferenceCode
			? [SpaceService.getSpace(externalReferenceCode)]
			: [];
	});

	return (await Promise.all(promises)).filter(Boolean);
}

/**
 * Handles bulk deletion logic and modal display based on trash status of spaces.
 */
async function handleBulkDeletion({
	apiURL,
	dataSetId,
	getCustomBulkDeleteMessage,
	selectedData,
}: {
	apiURL: string;
	dataSetId: string;
	getCustomBulkDeleteMessage?: typeof getBulkDeleteMessage;
	selectedData: IBulkActionFDSData;
}): Promise<void> {
	const spaces = await getEntriesSpaces(selectedData?.items || []);

	// Trash status checks

	const allEntriesHaveTrashEnabled = spaces.every(
		(space) => space.settings.trashEnabled
	);
	const someEntriesHaveTrashEnabled = spaces.some(
		(space) => space.settings.trashEnabled
	);

	const bulkDeleteMessage =
		getCustomBulkDeleteMessage ?? getBulkDeleteMessage;

	const {confirmationMessage, title} = bulkDeleteMessage(
		selectedData,
		allEntriesHaveTrashEnabled,
		someEntriesHaveTrashEnabled
	);

	if (allEntriesHaveTrashEnabled) {
		if (!isFromRecycleBin(selectedData)) {
			executeBulkDeleteAction(apiURL, dataSetId, selectedData);
		}
		else {
			showModal(
				apiURL,
				confirmationMessage,
				dataSetId,
				title,
				selectedData
			);
		}
	}
	else {
		showModal(apiURL, confirmationMessage, dataSetId, title, selectedData);
	}
}

/**
 * Shows the bulk delete confirmation modal.
 */
async function showModal(
	apiURL: string,
	confirmationMessage: string,
	dataSetId: string,
	title: string,
	selectedData: any
): Promise<void> {
	openCMSModal({
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

/**
 * Entry point for bulk delete action.
 */
export default async function deleteAssetEntriesBulkAction({
	apiURL = '',
	dataSetId = '',
	getCustomBulkDeleteMessage,
	selectedData,
}: {
	apiURL?: string;
	dataSetId?: string;
	getCustomBulkDeleteMessage?: typeof getBulkDeleteMessage;
	selectedData: IBulkActionFDSData;
}): Promise<void> {
	await handleBulkDeletion({
		apiURL,
		dataSetId,
		getCustomBulkDeleteMessage,
		selectedData,
	});
}
