/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {openToast} from 'frontend-js-components-web';
import {Dispatch} from 'react';

import StructureService from '../../common/services/StructureService';
import {ObjectDefinitions} from '../../common/types/ObjectDefinition';
import {Action, State} from '../contexts/StateContext';
import selectHistory from '../selectors/selectHistory';
import selectStructureChildren from '../selectors/selectStructureChildren';
import selectStructureERC from '../selectors/selectStructureERC';
import selectStructureId from '../selectors/selectStructureId';
import selectStructureLabel from '../selectors/selectStructureLabel';
import selectStructureLocalizedLabel from '../selectors/selectStructureLocalizedLabel';
import selectStructureName from '../selectors/selectStructureName';
import selectStructureSpaces from '../selectors/selectStructureSpaces';
import selectStructureStatus from '../selectors/selectStructureStatus';
import selectStructureUuid from '../selectors/selectStructureUuid';
import selectStructureWorkflows from '../selectors/selectStructureWorkflows';

type Props = {
	dispatch: Dispatch<Action>;
	objectDefinitions: ObjectDefinitions;
	state: State;
	validate: () => boolean;
};

export async function saveStructure({
	dispatch,
	objectDefinitions,
	state,
	validate,
}: Props) {
	const valid = validate();

	if (!valid) {
		return;
	}

	const children = selectStructureChildren(state);
	const erc = selectStructureERC(state);
	const history = selectHistory(state);
	const id = selectStructureId(state);
	const label = selectStructureLabel(state);
	const localizedLabel = selectStructureLocalizedLabel(state);
	const name = selectStructureName(state);
	const spaces = selectStructureSpaces(state);
	const status = selectStructureStatus(state);
	const workflows = selectStructureWorkflows(state);
	const uuid = selectStructureUuid(state);

	const previousStatus = state.structure.status;

	const onError = () =>
		dispatch({
			error: 'unexpected',
			property: 'global',
			status: previousStatus,
			type: 'add-error',
			uuid,
		});

	dispatch({status: 'saving', type: 'set-structure-status'});

	if (status === 'new') {
		const {data, error} = await StructureService.createStructure({
			children,
			erc,
			label,
			name,
			spaces,
			status: 'draft',
			workflows,
		});

		if (error) {
			onError();

			return;
		}
		else if (data) {
			dispatch({id: data.id, type: 'create-structure'});
		}
	}
	else {
		const {error} = await StructureService.updateStructure({
			children,
			erc,
			history,
			id,
			label,
			name,
			objectDefinitions,
			spaces,
			status: 'draft',
			workflows,
		});

		if (error) {
			onError();

			return;
		}
		else {
			dispatch({type: 'clear-errors'});
		}
	}

	openToast({
		message: Liferay.Util.sub(
			Liferay.Language.get('x-was-saved-successfully'),
			localizedLabel
		),
		type: 'success',
	});
}
