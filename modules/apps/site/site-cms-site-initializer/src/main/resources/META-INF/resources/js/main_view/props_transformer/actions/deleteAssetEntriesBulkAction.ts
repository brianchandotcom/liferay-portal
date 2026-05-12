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
		type: 'DeleteObjectBulkSelectionAction',
	});
}

/**
 * Returns the confirmation message and title for bulk delete modal.
 */
function getBulkDeleteMessage(
	selectedData: any,
	someEntriesHaveTrashEnabled: boolean
): {
	confirmationMessage: string;
	title: string;
} {
	if (someEntriesHaveTrashEnabled) {
		return {
			confirmationMessage: getBodyHTML([
				Liferay.Language.get(
					'you-are-about-to-delete-the-selected-items-from-multiple-spaces'
				),
				Liferay.Language.get(
					'bulk-delete-from-multiple-spaces-warning'
				),
				Liferay.Language.get('are-you-sure-you-want-to-continue'),
			]),
			title: selectedData.items.length
				? sub(Liferay.Language.get('delete-x-items'), [
						selectedData.items.length,
					])
				: Liferay.Language.get('delete-all-items'),
		};
	}

	if (selectedData.selectAll) {
		return {
			confirmationMessage: getBodyHTML([
				Liferay.Language.get('delete-all-items-confirmation'),
			]),
			title: Liferay.Language.get('delete-all-items'),
		};
	}

	if (selectedData.items.length > 1) {
		return {
			confirmationMessage: getBodyHTML([
				sub(Liferay.Language.get('delete-x-items-confirmation'), [
					selectedData.items.length,
				]),
			]),
			title: sub(Liferay.Language.get('delete-x-items'), [
				selectedData.items.length,
			]),
		};
	}

	return {
		confirmationMessage: getBodyHTML([
			Liferay.Language.get('delete-item-confirmation'),
		]),
		title: Liferay.Language.get('delete-item'),
	};
}

function getBodyHTML(lines: string[]): string {
	return `
    	<div>
    		${lines.map((line) => `<p>${line}</p>`).join('')}
		</div>
  	`;
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
	showConfirmationModal,
	trashEnabled,
}: {
	apiURL: string;
	dataSetId: string;
	getCustomBulkDeleteMessage?: typeof getBulkDeleteMessage;
	selectedData: IBulkActionFDSData;
	showConfirmationModal?: boolean;
	trashEnabled?: boolean;
}): Promise<void> {
	let allEntriesHaveTrashEnabled: boolean;
	let someEntriesHaveTrashEnabled: boolean;

	if (trashEnabled === null || trashEnabled === undefined) {
		const spaces = await getEntriesSpaces(selectedData?.items || []);

		allEntriesHaveTrashEnabled =
			!!spaces.length &&
			spaces.every((space) => space.settings.trashEnabled);

		someEntriesHaveTrashEnabled =
			!spaces.length ||
			spaces.some((space) => space.settings.trashEnabled);
	}
	else {
		allEntriesHaveTrashEnabled = trashEnabled;
		someEntriesHaveTrashEnabled = trashEnabled;
	}

	const bulkDeleteMessage =
		getCustomBulkDeleteMessage ?? getBulkDeleteMessage;

	const {confirmationMessage, title} = bulkDeleteMessage(
		selectedData,
		someEntriesHaveTrashEnabled
	);
	if (
		showConfirmationModal ||
		!allEntriesHaveTrashEnabled ||
		isFromRecycleBin(selectedData)
	) {
		showModal(apiURL, confirmationMessage, dataSetId, title, selectedData);
	}
	else {
		executeBulkDeleteAction(apiURL, dataSetId, selectedData);
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
		bodyHTML: confirmationMessage,
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
	showConfirmationModal,
	trashEnabled,
}: {
	apiURL?: string;
	dataSetId?: string;
	getCustomBulkDeleteMessage?: typeof getBulkDeleteMessage;
	selectedData: IBulkActionFDSData;
	showConfirmationModal?: boolean;
	trashEnabled?: boolean;
}): Promise<void> {
	await handleBulkDeletion({
		apiURL,
		dataSetId,
		getCustomBulkDeleteMessage,
		selectedData,
		showConfirmationModal,
		trashEnabled,
	});
}
