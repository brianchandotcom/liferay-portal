/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Dispatch} from 'react';

import {Action, State} from '../contexts/StateContext';
import {Uuid} from '../types/Uuid';
import confirmDeletionAction from './confirmDeletionAction';

export default async function handleAddRepeatableGroup({
	dispatch,
	publishedChildren,
	uuids,
}: {
	dispatch: Dispatch<Action>;
	publishedChildren: State['publishedChildren'];
	uuids: Uuid[];
}) {
	if (uuids.some((uuid) => publishedChildren.has(uuid))) {
		const confirm = await confirmDeletionAction('create-repeatable-group');

		if (!confirm) {
			return;
		}
	}

	dispatch({type: 'add-repeatable-group', uuids});
}
