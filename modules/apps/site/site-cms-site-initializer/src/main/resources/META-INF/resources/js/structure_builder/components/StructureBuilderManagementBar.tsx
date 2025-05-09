/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import ClayLink from '@clayui/link';
import {openConfirmModal} from '@liferay/layout-js-components-web';
import {ManagementToolbar, openToast} from 'frontend-js-components-web';
import React, {Dispatch} from 'react';

import {
	Action,
	State,
	useSelector,
	useStateDispatch,
} from '../contexts/StateContext';
import selectHistory from '../selectors/selectHistory';
import selectState from '../selectors/selectState';
import selectStructureERC from '../selectors/selectStructureERC';
import selectStructureFields from '../selectors/selectStructureFields';
import selectStructureId from '../selectors/selectStructureId';
import selectStructureLabel from '../selectors/selectStructureLabel';
import selectStructureLocalizedLabel from '../selectors/selectStructureLocalizedLabel';
import selectStructureName from '../selectors/selectStructureName';
import selectStructureSpaces from '../selectors/selectStructureSpaces';
import selectStructureStatus from '../selectors/selectStructureStatus';
import StructureService from '../services/StructureService';
import {useValidate} from '../utils/validation';
import AsyncButton from './AsyncButton';
import ManagementBar from './ManagementBar';

export default function StructureBuilderManagementBar() {
	const label = useSelector(selectStructureLocalizedLabel);
	const status = useSelector(selectStructureStatus);

	return (
		<ManagementBar
			backURL="structures"
			title={
				status === 'published'
					? label
					: Liferay.Language.get('new-structure')
			}
		>
			<ManagementToolbar.Item>
				<CustomizeExperienceButton />
			</ManagementToolbar.Item>

			<ManagementToolbar.Item>
				<div className="vertical-divider"></div>
			</ManagementToolbar.Item>

			<ManagementToolbar.Item>
				<ClayLink
					className="btn btn-outline-borderless btn-outline-secondary btn-sm"
					href="structures"
				>
					{Liferay.Language.get('cancel')}
				</ClayLink>
			</ManagementToolbar.Item>

			{status !== 'published' ? (
				<ManagementToolbar.Item>
					<SaveButton />
				</ManagementToolbar.Item>
			) : null}

			<ManagementToolbar.Item>
				<PublishButton />
			</ManagementToolbar.Item>
		</ManagementBar>
	);
}

function CustomizeExperienceButton() {
	return (
		<ClayButton
			className="font-weight-semi-bold"
			displayType="link"
			onClick={() => {}}
			size="sm"
		>
			{Liferay.Language.get('customize-experience')}

			<ClayIcon className="ml-2" symbol="shortcut" />
		</ClayButton>
	);
}

function SaveButton() {
	const dispatch = useStateDispatch();
	const validate = useValidate();

	const erc = useSelector(selectStructureERC);
	const fields = useSelector(selectStructureFields);
	const label = useSelector(selectStructureLabel);
	const localizedLabel = useSelector(selectStructureLocalizedLabel);
	const name = useSelector(selectStructureName);
	const spaces = useSelector(selectStructureSpaces);
	const status = useSelector(selectStructureStatus);
	const structureId = useSelector(selectStructureId);

	const onSave = async () => {
		const valid = validate();

		if (!valid) {
			return;
		}

		try {
			if (status === 'new') {
				const {id} = await StructureService.createStructure({
					erc,
					fields,
					label,
					name,
					spaces,
				});

				dispatch({id, type: 'create-structure'});
			}
			else {
				await StructureService.updateStructure({
					erc,
					fields,
					id: structureId,
					label,
					name,
					spaces,
				});

				dispatch({type: 'clear-error'});
			}

			openToast({
				message: Liferay.Util.sub(
					Liferay.Language.get('x-was-saved-successfully'),
					localizedLabel
				),
				type: 'success',
			});
		}
		catch (error) {
			const {message} = error as Error;

			dispatch({
				error:
					message ||
					Liferay.Language.get(
						'an-unexpected-error-occurred-while-saving-or-publishing-the-structure'
					),
				type: 'set-error',
			});
		}
	};

	return (
		<AsyncButton
			displayType="secondary"
			label={Liferay.Language.get('save')}
			onClick={onSave}
		/>
	);
}

function PublishButton() {
	const dispatch = useStateDispatch();
	const validate = useValidate();
	const state = useSelector(selectState);
	const localizedLabel = useSelector(selectStructureLocalizedLabel);

	const onPublish = async () => {
		await publishStructure({
			dispatch,
			onSuccess: () => {
				openToast({
					message: Liferay.Util.sub(
						Liferay.Language.get('x-was-published-successfully'),
						localizedLabel
					),
					type: 'success',
				});
			},
			state,
			validate,
		});
	};

	return (
		<AsyncButton
			displayType="primary"
			label={Liferay.Language.get('publish')}
			onClick={onPublish}
		/>
	);
}

async function publishStructure({
	checkDeletedFields = true,
	dispatch,
	state,
	validate,
}: {
	checkDeletedFields?: boolean;
	dispatch: Dispatch<Action>;
	onSuccess: () => void;
	state: State;
	validate: () => boolean;
}) {
	const valid = validate();

	if (!valid) {
		return;
	}

	const history = selectHistory(state);

	if (checkDeletedFields && history.deletedFields) {
		if (
			!(await openConfirmModal({
				buttonLabel: Liferay.Language.get('publish'),
				center: true,
				status: 'danger',
				text: Liferay.Language.get(
					'you-removed-one-or-more-fields-from-the-structure'
				),
				title: Liferay.Language.get('publish-structure-changes'),
			}))
		) {
			return;
		}
	}

	const erc = selectStructureERC(state);
	const fields = selectStructureFields(state);
	const label = selectStructureLabel(state);

	const name = selectStructureName(state);
	const spaces = selectStructureSpaces(state);
	const status = selectStructureStatus(state);
	const structureId = selectStructureId(state);

	try {
		if (status === 'new') {
			const {id} = await StructureService.createStructure({
				erc,
				fields,
				label,
				name,
				spaces,
			});

			await StructureService.publishStructure({id});

			dispatch({id, type: 'publish-structure'});
		}
		else if (status === 'draft') {
			await StructureService.updateStructure({
				erc,
				fields,
				id: structureId,
				label,
				name,
				spaces,
			});

			await StructureService.publishStructure({id: structureId});

			dispatch({type: 'publish-structure'});
		}
		else if (status === 'published') {
			await StructureService.updateStructure({
				erc,
				fields,
				id: structureId,
				label,
				name,
				spaces,
			});

			dispatch({type: 'publish-structure'});
		}

		onSuccess();
	}
	catch (error) {
		const {message} = error as Error;

		dispatch({
			error:
				message ||
				Liferay.Language.get(
					'an-unexpected-error-occurred-while-saving-or-publishing-the-structure'
				),
			type: 'set-error',
		});
	}
}
