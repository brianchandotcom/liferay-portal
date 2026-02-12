/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {openToast} from 'frontend-js-components-web';
import {Dispatch} from 'react';

import {Action, State} from '../contexts/StateContext';
import {Structure} from '../types/Structure';
import {Uuid} from '../types/Uuid';
import confirmDeletionAction from './confirmDeletionAction';
import getUndeletableChildren from './getUndeletableChildren';
import isReferenced from './isReferenced';

export default async function handleDeleteChildren({
	dispatch,
	publishedChildren,
	structure,
	uuids,
}: {
	dispatch: Dispatch<Action>;
	publishedChildren: State['publishedChildren'];
	structure: Structure;
	uuids: Uuid[];
}) {
	const deletingPublished = uuids.some(
		(uuid) =>
			!isReferenced({root: structure, uuid}) &&
			publishedChildren.has(uuid)
	);

	if (deletingPublished) {
		const confirm = await confirmDeletionAction('delete-children');

		if (!confirm) {
			return;
		}
	}

	const undeletables = getUndeletableChildren(uuids, structure);

	const reasons = [...undeletables.values()];

	if (reasons.includes('causes-invalid-group')) {
		openToast({
			message: Liferay.Language.get(
				'some-fields-could-not-be-deleted-because-at-least-one-field-is-required-in-a-repeatable-group'
			),
			type: 'danger',
		});
	}
	else if (reasons.length) {
		openToast({
			message: Liferay.Language.get(
				'some-items-could-not-be-deleted-because-they-are-system-fields-or-they-belong-to-a-referenced-content-structure'
			),
			type: 'danger',
		});
	}

	const deleteableUuids = uuids.filter((uuid) => !undeletables.has(uuid));

	if (deleteableUuids.length) {
		dispatch({type: 'delete-children', uuids: deleteableUuids});
	}
}
