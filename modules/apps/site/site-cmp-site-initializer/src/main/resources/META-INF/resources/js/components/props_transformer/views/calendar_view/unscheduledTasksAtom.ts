/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {State} from '@liferay/frontend-js-state-web';

import {ITaskObjectEntry} from '../../../../utils/types';

export const CMP_UNSCHEDULED_TASKS_ATOM_ID = 'cmpUnscheduledTasks';

/**
 * Shares the unscheduled tasks (those without a due date) that the calendar
 * view derives from the full FDS items with the info panel component. The FDS
 * core otherwise provides the info panel only the currently selected items.
 */
const unscheduledTasksAtom = State.atom<ITaskObjectEntry[]>(
	CMP_UNSCHEDULED_TASKS_ATOM_ID,
	[]
);

export {unscheduledTasksAtom};
