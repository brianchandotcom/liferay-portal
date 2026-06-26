/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	IItemsActions,
	findAction,
	replaceTokens,
} from '@liferay/frontend-data-set-web';
import {Immutable} from '@liferay/frontend-js-state-web';

import {ITaskObjectEntry} from './types';

/**
 * Builds the URL for one of a task's actions.
 *
 * It finds the action named `actionId`, takes its `href`, and replaces the
 * tokens in it with matching values from `task`. A token such as
 * `{embedded.id}` is resolved as `task.embedded.id`.
 *
 * For example, an `href` of `/tasks/{embedded.id}/edit` with a `task` whose
 * `embedded.id` is 42 becomes `/tasks/42/edit`.
 */
export default function getActionURL({
	actionId,
	itemsActions,
	task,
}: {
	actionId: string;
	itemsActions: IItemsActions[];
	task: {embedded: Immutable<ITaskObjectEntry> | ITaskObjectEntry};
}) {
	return replaceTokens(findAction(itemsActions, actionId)?.href, task);
}
