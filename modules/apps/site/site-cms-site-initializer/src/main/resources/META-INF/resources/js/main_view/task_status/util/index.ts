/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	IBulkActionFDSDataItemTransformed,
	IBulkActionTaskType,
	TBulkActionTaskDTO,
} from '../../../common/types/BulkActionTask';
import {URL_BULK_ACTION_TASK, URL_TASKS_REPORT_DETAIL} from './constants';

const OBJECT_ENTRY_FOLDER_CLASS_NAME =
	'com.liferay.object.model.ObjectEntryFolder';

export function composeCreateTaskURL(
	apiURL: string,
	{searchQuery = '', selectAll = false}
): string {
	const postURL = new URL(
		`${Liferay.ThemeDisplay.getPortalURL()}${URL_BULK_ACTION_TASK}`
	);

	if (!selectAll) {
		return postURL.toString();
	}

	if (searchQuery) {
		postURL.searchParams.append('search', searchQuery);
	}

	let scopeFilter = new URL(
		`${Liferay.ThemeDisplay.getPortalURL()}${apiURL}`
	).searchParams.get('filter');

	if (!scopeFilter) {
		return postURL.toString();
	}

	if (scopeFilter.includes('cmsKind')) {
		scopeFilter = `(cmsSection eq 'contents' or cmsSection eq 'files')`;
	}
	else if (scopeFilter.includes(`cmsSection eq 'contents'`)) {
		scopeFilter = `cmsSection eq 'contents'`;
	}
	else if (scopeFilter.includes('folderId')) {
		const match = scopeFilter.match(/folderId eq (\d+)/);

		if (match && match[1]) {
			scopeFilter = `folderId eq ${parseInt(match[1], 10)}`;
		}
	}
	else {
		scopeFilter = `cmsSection eq 'files'`;
	}

	postURL.searchParams.append('filter', scopeFilter);

	return postURL.toString();
}

export function composeCreateTaskDTO(
	actionKey: keyof IBulkActionTaskType,
	keyValues: IBulkActionTaskType[keyof IBulkActionTaskType] = {},
	{items = [], selectAll = false}: IBulkActionFDSData
): TBulkActionTaskDTO {
	return {
		bulkActionItems: items.map(
			({
				embedded: {
					externalReferenceCode: embeddedExternalReferenceCode,
					id: classPK,
					title: name,
				},
				entryClassName,
				externalReferenceCode,
			}: any) =>
				({
					classExternalReferenceCode:
						externalReferenceCode || embeddedExternalReferenceCode,
					className: entryClassName || OBJECT_ENTRY_FOLDER_CLASS_NAME,
					classPK,
					name,
				}) as IBulkActionFDSDataItemTransformed
		),
		selectAll,
		type: actionKey,
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
