/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {IAssetObjectEntry} from '../../../common/types/AssetType';
import {
	IBulkActionFDSData,
	IBulkActionFDSDataItemTransformed,
	IBulkActionTask,
	IBulkActionType,
	TBulkActionTaskDTO,
} from '../../../common/types/BulkActionTask';
import {OBJECT_ENTRY_FOLDER_CLASS_NAME} from '../../../common/utils/constants';
import {
	TASK_STATUS_PROPS,
	URL_BULK_ACTION_TASK,
	URL_DOWNLOAD_BULK_ACTION_TASK,
	URL_TASKS_REPORT_DETAIL,
} from './constants';

export function composeCreateTaskURL(
	apiURL: string,
	{filters = [], searchQuery = '', selectAll = false}: IBulkActionFDSData,
	useDownloadURL: boolean = false
): string {
	const url: string = useDownloadURL
		? URL_DOWNLOAD_BULK_ACTION_TASK
		: URL_BULK_ACTION_TASK;

	const postURL = new URL(`${Liferay.ThemeDisplay.getPortalURL()}${url}`);

	if (!selectAll) {
		return postURL.toString();
	}

	if (searchQuery) {
		postURL.searchParams.append('search', searchQuery);
	}
	else {
		postURL.searchParams.append('emptySearch', 'true');
	}

	const fullFilters = filters.map(({odataFilterString}) => odataFilterString);

	const scopeFilter =
		new URL(
			`${Liferay.ThemeDisplay.getPortalURL()}${apiURL}`
		).searchParams.get('filter') || '';

	if (scopeFilter) {
		fullFilters.unshift(scopeFilter);
	}

	postURL.searchParams.append('filter', fullFilters.join(' and '));

	return postURL.toString();
}

export function composeCreateTaskDTO(
	type: keyof IBulkActionType,
	keyValues: IBulkActionType[keyof IBulkActionType] = {},
	{items = [], selectAll = false}: IBulkActionFDSData
): TBulkActionTaskDTO {
	return {
		bulkActionItems: items.map(
			({
				className,
				id,
				embedded: {
					externalReferenceCode: embeddedExternalReferenceCode,
					file,
					id: classPK,
					title: name,
				} = {} as IAssetObjectEntry,
				entryClassName,
				externalReferenceCode,
			}: any) => {
				const itemsTransformed = {
					classExternalReferenceCode:
						externalReferenceCode || embeddedExternalReferenceCode,
					className:
						entryClassName ||
						className ||
						OBJECT_ENTRY_FOLDER_CLASS_NAME,
					classPK: classPK || id,
					name,
				} as IBulkActionFDSDataItemTransformed;

				if (type === 'DownloadBulkAction') {
					itemsTransformed.file = file;
				}

				return itemsTransformed;
			}
		),
		selectionScope: {selectAll},
		type,
		...keyValues,
	} as TBulkActionTaskDTO;
}

export function getTaskReportLink(
	classNameId: number,
	taskId?: number
): string {
	if (!taskId) {
		return '';
	}

	const href = `${URL_TASKS_REPORT_DETAIL}${classNameId}/${taskId}`;

	return `<a class="alert-link lead" href="${href}"><strong>${Liferay.Language.get('task-report')}</strong></a>`;
}

export function getTaskStatusProps(bulkActionTask: IBulkActionTask) {
	const {executionStatus, numberOfFailedItems, numberOfItems} =
		bulkActionTask as IBulkActionTask;

	if (numberOfFailedItems === Number(numberOfItems)) {
		return TASK_STATUS_PROPS.failed;
	}

	return TASK_STATUS_PROPS[executionStatus.key];
}
