/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {openModal} from 'frontend-js-components-web';
import {sub} from 'frontend-js-web';

import {ISearchAssetObjectEntry} from '../../../common/types/AssetType';
import {IBulkActionFDSData} from '../../../common/types/BulkActionTask';
import {
	displayCreateTaskErrorToast,
	displayCreateTaskSuccessToast,
} from '../../../common/utils/toastUtil';
import {getBulkActionTaskMessage} from '../../bulk_actions_monitor/util/notifications';
import {triggerAssetBulkAction} from './triggerAssetBulkAction';

function getBulkDeleteMessage(
	objectEntryTitle: string,
	selectedData: IBulkActionFDSData
) {
	const {items = [], selectAll} = selectedData;

	if (selectAll) {
		return {
			confirmationMessage: sub(
				Liferay.Language.get('delete-all-versions-confirmation'),
				objectEntryTitle
			),
			title: Liferay.Language.get('delete-versions'),
		};
	}
	else if (items?.length > 1) {
		return {
			confirmationMessage: sub(
				Liferay.Language.get('delete-versions-confirmation'),
				items?.length,
				objectEntryTitle
			),
			title: Liferay.Language.get('delete-versions'),
		};
	}

	return {
		confirmationMessage: sub(
			Liferay.Language.get('delete-version-confirmation'),
			`<strong>${sub(Liferay.Language.get('version-x'), items?.[0]?.systemProperties?.version.number)}</strong>`,
			objectEntryTitle
		),
		title: Liferay.Language.get('delete-version'),
	};
}

export default function deleteAssetVersionBulkAction({
	apiURL,
	dataSetId,
	entryClassName,
	objectEntryCurrentVersion,
	objectEntryTitle,
	selectedData,
}: {
	apiURL?: string;
	dataSetId?: string;
	entryClassName?: string;
	objectEntryCurrentVersion: number;
	objectEntryTitle: string;
	selectedData: any;
}) {
	const {confirmationMessage, title} = getBulkDeleteMessage(
		objectEntryTitle,
		selectedData
	);

	openModal({
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
				onClick: ({processClose}) => {
					processClose();
				},
				type: 'cancel',
			},
			{
				displayType: 'danger',
				label: Liferay.Language.get('delete'),
				onClick: async ({processClose}) => {
					const versions = selectedData.keyValues.filter(
						(version: number) =>
							version !== objectEntryCurrentVersion
					);

					const type = 'DeleteAssetVersionBulkAction';

					if (!versions.length) {
						const message = getBulkActionTaskMessage(
							type,
							'danger',
							selectedData
						);

						displayCreateTaskErrorToast(message);

						processClose();

						return;
					}

					triggerAssetBulkAction({
						apiURL,
						dataSetId,
						keyValues: {versions},
						onCreateSuccess: () => {
							const message = getBulkActionTaskMessage(
								type,
								'info',
								selectedData
							);

							displayCreateTaskSuccessToast(
								selectedData.selectAll
									? message
									: sub(message, [
											selectedData?.items?.length || 0,
										])
							);
						},
						overrideDefaultSuccessToast: true,
						selectedData: {
							items: [
								{
									embedded: selectedData.items[0],
									entryClassName,
								} as ISearchAssetObjectEntry,
							],
							selectAll: selectedData.selectAll,
						},
						type,
					});

					processClose();
				},
			},
		],
		center: true,
		status: 'danger',
		title,
	});
}
