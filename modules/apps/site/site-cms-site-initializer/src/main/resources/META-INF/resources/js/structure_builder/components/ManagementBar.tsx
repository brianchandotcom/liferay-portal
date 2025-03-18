/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import ClayLink from '@clayui/link';
import {ManagementToolbar, openToast} from 'frontend-js-components-web';
import React, {useCallback} from 'react';

import {useSelector, useStateDispatch} from '../contexts/StateContext';
import selectInvalids from '../selectors/selectInvalids';
import selectSelection from '../selectors/selectSelection';
import selectStructureERC from '../selectors/selectStructureERC';
import selectStructureFields from '../selectors/selectStructureFields';
import selectStructureId from '../selectors/selectStructureId';
import selectStructureLabel from '../selectors/selectStructureLabel';
import selectStructureLocalizedLabel from '../selectors/selectStructureLocalizedLabel';
import selectStructureName from '../selectors/selectStructureName';
import selectStructureStatus from '../selectors/selectStructureStatus';
import StructureService from '../services/StructureService';
import focusInvalidInput from '../utils/focusInvalidInput';
import AsyncButton from './AsyncButton';

export default function ManagementBar() {
	const label = useSelector(selectStructureLocalizedLabel);
	const status = useSelector(selectStructureStatus);

	return (
		<ManagementToolbar.Container className="border">
			<ManagementToolbar.ItemList className="c-gap-3" expand>
				<ManagementToolbar.Item>
					<ClayLink
						aria-label={Liferay.Language.get('back')}
						className="btn btn-monospaced btn-outline-borderless btn-outline-secondary btn-sm"
						href="structures"
					>
						<ClayIcon symbol="angle-left" />
					</ClayLink>
				</ManagementToolbar.Item>

				<ManagementToolbar.Item className="nav-item-expand">
					<h2 className="font-weight-semi-bold m-0 text-5">
						{status === 'published'
							? label
							: Liferay.Language.get('new-structure')}
					</h2>
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
			</ManagementToolbar.ItemList>
		</ManagementToolbar.Container>
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
				});
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

			dispatch({error: message, type: 'set-error'});
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

	const erc = useSelector(selectStructureERC);
	const fields = useSelector(selectStructureFields);
	const label = useSelector(selectStructureLabel);
	const localizedLabel = useSelector(selectStructureLocalizedLabel);
	const name = useSelector(selectStructureName);
	const status = useSelector(selectStructureStatus);
	const structureId = useSelector(selectStructureId);

	const onPublish = async () => {
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
				});

				dispatch({type: 'publish-structure'});
			}

			openToast({
				message: Liferay.Util.sub(
					Liferay.Language.get('x-was-published-successfully'),
					localizedLabel
				),
				type: 'success',
			});
		}
		catch (error) {
			const {message} = error as Error;

			dispatch({error: message, type: 'set-error'});
		}
	};

	return (
		<AsyncButton
			displayType="primary"
			label={Liferay.Language.get('publish')}
			onClick={onPublish}
		/>
	);
}

function useValidate() {
	const dispatch = useStateDispatch();
	const fields = useSelector(selectStructureFields);
	const invalids = useSelector(selectInvalids);
	const selection = useSelector(selectSelection);

	return useCallback(() => {
		if (!fields.length) {
			dispatch({
				error: Liferay.Language.get(
					'at-least-one-field-must-be-added-to-save-or-publish-the-structure'
				),
				type: 'set-error',
			});

			return false;
		}

		if (!invalids.size) {
			return true;
		}

		const [uuid] = [...invalids];

		const isSelected = selection.length === 1 && selection.includes(uuid);

		if (isSelected) {
			focusInvalidInput();
		}
		else {
			dispatch({
				selection: [uuid],
				type: 'set-selection',
			});
		}

		return false;
	}, [dispatch, fields, invalids, selection]);
}
