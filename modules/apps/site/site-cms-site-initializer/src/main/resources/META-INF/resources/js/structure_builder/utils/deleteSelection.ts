/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Dispatch} from 'react';

import {Action, State} from '../contexts/StateContext';
import confirmDeletionAction from './confirmDeletionAction';
import findChild from './findChild';
import isReferenced from './isReferenced';

export async function deleteSelection({
	dispatch,
	publishedChildren,
	selection,
	structure,
}: {
	dispatch: Dispatch<Action>;
	publishedChildren: State['publishedChildren'];
	selection: State['selection'];
	structure: State['structure'];
}) {
	for (const uuid of selection) {
		const item = findChild({root: structure, uuid})!;

		if (
			!isReferenced({item, root: structure}) &&
			publishedChildren.has(uuid)
		) {
			const confirm = await confirmDeletionAction('delete-children');

			if (confirm) {
				dispatch({type: 'delete-selection'});

				return;
			}

			return;
		}
	}

	dispatch({type: 'delete-selection'});
}
