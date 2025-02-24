/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import ClayLink from '@clayui/link';
import {API} from '@liferay/object-js-components-web';
import {ManagementToolbar, openToast} from 'frontend-js-components-web';
import React from 'react';

import {
	useStateDispatch,
	useStructureFields,
	useStructureId,
	useStructureLabel,
	useStructureName,
	useStructureStatus,
} from '../contexts/StateContext';
import StructureService from '../services/StructureService';

export default function ManagementBar() {
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
						{Liferay.Language.get('new-structure')}
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
	const fields = useStructureFields();
	const label = useStructureLabel();
	const status = useStructureStatus();
	const structureId = useStructureId();
	const structureName = useStructureName();

	const create = async () => {
		const {id, name, objectFields} = await StructureService.createStructure(
			{
				fields,
				label,
			}
		);

		openToast({
			message: Liferay.Util.sub(
				Liferay.Language.get('x-was-created-successfully'),
				label
			),
			type: 'success',
		});

		dispatch({id, name, objectFields, type: 'create-structure'});
	};

	const update = async () => {
		const {objectFields} = await StructureService.updateStructure({
			fields,
			id: structureId,
			label,
			name: structureName,
		});

		openToast({
			message: Liferay.Util.sub(
				Liferay.Language.get('x-was-updated-successfully'),
				label
			),
			type: 'success',
		});

		dispatch({objectFields, type: 'update-structure'});
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
		<ClayButton
			displayType={status === 'published' ? 'primary' : 'secondary'}
			onClick={onSave}
			size="sm"
		>
			{Liferay.Language.get('save')}
		</ClayButton>
	);
}

function PublishButton() {
	const dispatch = useStateDispatch();
	const id = useStructureId();
	const label = useStructureLabel();
	const status = useStructureStatus();

	if (status === 'published') {
		return null;
	}

	const onPublish = async () => {
		try {
			await StructureService.publishStructure({id});

			openToast({
				message: Liferay.Util.sub(
					Liferay.Language.get('x-was-published-successfully'),
					label
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
		<ClayButton
			disabled={status === 'new'}
			displayType="primary"
			onClick={onPublish}
			size="sm"
		>
			{Liferay.Language.get('publish')}
		</ClayButton>
	);
}
