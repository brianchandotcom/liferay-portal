/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const SUBTASK_LABEL_KEYS: {[actionId: string]: string} = {
	subscribe: 'watch-subtask',
	unsubscribe: 'stop-watching-subtask',
};

export function addSubtaskWatchActions(itemsActions: any[]): any[] {
	return itemsActions.flatMap((action) => {
		if (action?.items) {
			return [{...action, items: addSubtaskWatchActions(action.items)}];
		}

		const labelKey = SUBTASK_LABEL_KEYS[action?.data?.id];

		if (!labelKey) {
			return [action];
		}

		return [
			{
				...action,
				isVisible: (itemData: any) => !isSubtask(itemData?.embedded),
			},
			{
				...action,
				isVisible: (itemData: any) => isSubtask(itemData?.embedded),
				label: Liferay.Language.get(labelKey),
			},
		];
	});
}

export function isSubtask(task?: {r_cmpTaskToCMPTasks_c_cmpTaskId?: number}) {
	return Boolean(task?.r_cmpTaskToCMPTasks_c_cmpTaskId);
}
