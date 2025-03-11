/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import ClayLink from '@clayui/link';
import {API} from '@liferay/object-js-components-web';
import {ManagementToolbar, openToast} from 'frontend-js-components-web';
import React from 'react';

import {useSelector, useStateDispatch} from '../contexts/StateContext';
import selectStructureERC from '../selectors/selectStructureERC';
import selectStructureFields from '../selectors/selectStructureFields';
import selectStructureId from '../selectors/selectStructureId';
import selectStructureLabel from '../selectors/selectStructureLabel';
import selectStructureLocalizedLabel from '../selectors/selectStructureLocalizedLabel';
import selectStructureName from '../selectors/selectStructureName';
import selectStructureStatus from '../selectors/selectStructureStatus';
import StructureService from '../services/StructureService';
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

				<ManagementToolbar.Item>
					<SaveButton />
				</ManagementToolbar.Item>

				<ManagementToolbar.Item>
					<PublishButton />
				</ManagementToolbar.Item>
			</ManagementToolbar.ItemList>
		</ManagementToolbar.Container>
	);
}

function SaveButton() {
	const dispatch = useStateDispatch();
	const fields = useSelector(selectStructureFields);
	const label = useSelector(selectStructureLabel);
	const localizedLabel = useSelector(selectStructureLocalizedLabel);
	const status = useSelector(selectStructureStatus);
	const structureId = useSelector(selectStructureId);
	const structureName = useSelector(selectStructureName);
	const structureERC = useSelector(selectStructureERC);

	const create = async () => {
		const {id, name} = await StructureService.createStructure({
			erc: structureERC,
			fields,
			label,
			name: structureName,
		});

		openToast({
			message: Liferay.Util.sub(
				Liferay.Language.get('x-was-created-successfully'),
				localizedLabel
			),
			type: 'success',
		});

		dispatch({id, name, type: 'create-structure'});
	};

	const update = async () => {
		await StructureService.updateStructure({
			erc: structureERC,
			fields,
			id: structureId,
			label,
			name: structureName,
		});

		openToast({
			message: Liferay.Util.sub(
				Liferay.Language.get('x-was-updated-successfully'),
				localizedLabel
			),
			type: 'success',
		});

		dispatch({type: 'save-structure'});
	};

	const onSave = async () => {
		try {
			if (status === 'new') {
				await create();
			}
			else {
				await update();
			}
		}
		catch (error) {
			const {message} = error as API.ErrorDetails;

			dispatch({error: message, type: 'set-error'});
		}
	};

	return (
		<AsyncButton
			displayType={status === 'published' ? 'primary' : 'secondary'}
			label={Liferay.Language.get('save')}
			onClick={onSave}
		/>
	);
}

function PublishButton() {
	const dispatch = useStateDispatch();
	const erc = useSelector(selectStructureERC);
	const fields = useSelector(selectStructureFields);
	const id = useSelector(selectStructureId);
	const label = useSelector(selectStructureLabel);
	const localizedLabel = useSelector(selectStructureLocalizedLabel);
	const name = useSelector(selectStructureName);
	const status = useSelector(selectStructureStatus);

	if (status === 'published') {
		return null;
	}

	const onPublish = async () => {
		try {
			await StructureService.updateStructure({
				erc,
				fields,
				id,
				label,
				name,
			});

			await StructureService.publishStructure({id});

			openToast({
				message: Liferay.Util.sub(
					Liferay.Language.get('x-was-published-successfully'),
					localizedLabel
				),
				type: 'success',
			});

			dispatch({type: 'publish-structure'});
		}
		catch (error) {
			const {message} = error as API.ErrorDetails;

			dispatch({error: message, type: 'set-error'});
		}
	};

	return (
		<AsyncButton
			disabled={status === 'new'}
			displayType="primary"
			label={Liferay.Language.get('publish')}
			onClick={onPublish}
		/>
	);
}
