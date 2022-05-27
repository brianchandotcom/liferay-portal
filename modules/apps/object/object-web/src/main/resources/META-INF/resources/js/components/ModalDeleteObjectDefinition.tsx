/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import {ClayModalProvider, useModal} from '@clayui/modal';
import {Observer} from '@clayui/modal/lib/types';
import {createResourceURL, fetch, sub} from 'frontend-js-web';
import React, {useEffect, useState} from 'react';

import {HEADERS} from '../utils/constants';
import DangerModal from './DangerModal';
import WarningModal from './WarningModal';

function ModalDeleteObjectDefinition({
	objectDefinition,
	observer,
	onClose,
	onDelete,
}: IProps) {
	const {
		hasObjectRelationship,
		id,
		name,
		objectEntriesCount,
		status: {code},
	} = objectDefinition;

	if (hasObjectRelationship) {
		return (
			<WarningModal
				observer={observer}
				onClose={onClose}
				title={Liferay.Language.get('deletion-not-allowed')}
			>
				<div>
					{sub(
						Liferay.Language.get(
							'x-has-active-relationships-and-cannot-be-deleted'
						),
						`${name}`
					)}
				</div>

				<div>
					{sub(
						Liferay.Language.get(
							'to-delete-x,-you-must-first-delete-its-relationships'
						),
						`${name}`
					)}
				</div>

				<div>
					{Liferay.Language.get('go-to-object-details-relationships')}
				</div>
			</WarningModal>
		);
	}

	return code === 0 ? (
		<DangerModal
			errorMessage={sub(
				Liferay.Language.get('input-does-not-match-x'),
				`${name}`
			)}
			observer={observer}
			onClose={onClose}
			onDelete={() => onDelete(id)}
			title={Liferay.Language.get('delete-object-definition')}
			token={name}
		>
			<p>
				{Liferay.Language.get(
					'deleting-an-object-definition-also-removes-its-data-records'
				)}
			</p>

			<p
				dangerouslySetInnerHTML={{
					__html: sub(
						Liferay.Language.get('x-has-x-object-entries'),
						`<strong>${name}</strong>`,
						`${objectEntriesCount}`
					),
				}}
			/>

			<p>
				{Liferay.Language.get(
					'before-deleting-this-object-definition-you-may-want-to-back-up-its-entries-to-prevent-data-loss'
				)}
			</p>

			<p
				dangerouslySetInnerHTML={{
					__html: sub(
						Liferay.Language.get('please-enter-x-to-confirm'),
						`<strong>${name}</strong>`
					),
				}}
			/>
		</DangerModal>
	) : (
		onDelete(id)
	);
}

interface IProps {
	objectDefinition: {
		hasObjectRelationship: boolean;
		id: string;
		name: string;
		objectEntriesCount: number;
		status: {code: number};
	};
	observer: Observer;
	onClose: () => void;
	onDelete: any;
}

export default function ModalWithProvider({
	baseResourceURL,
}: {
	baseResourceURL: string;
}) {
	const [objectDefinition, setObjectDefinition] = useState<{
		hasObjectRelationship: boolean;
		id: string;
		name: string;
		objectEntriesCount: number;
		status: {code: number};
	} | null>(null);

	const getDeleteObjectDefinition = async ({
		itemData,
	}: {
		itemData: {
			id: string;
			name: string;
			objectEntriesCount: number;
			status: {code: number};
		};
	}) => {
		const response = await fetch(
			createResourceURL(baseResourceURL, {
				objectDefinitionId: itemData.id,
				p_p_resource_id:
					'/object_definitions/get_object_definition_delete_info',
			}).href
		);

		const {
			hasObjectRelationship,
			objectEntriesCount,
		} = (await response.json()) as {
			hasObjectRelationship: boolean;
			objectEntriesCount: number;
		};
		setObjectDefinition({
			...itemData,
			hasObjectRelationship,
			objectEntriesCount,
		});
	};

	const {observer, onClose} = useModal({
		onClose: () => setObjectDefinition(null),
	});

	const deleteRelationship = async (id: string) => {
		const response = await fetch(
			`/o/object-admin/v1.0/object-definitions/${id}`,
			{
				headers: HEADERS,
				method: 'DELETE',
			}
		);

		if (response.ok) {
			Liferay.Util.openToast({
				message: sub(
					Liferay.Language.get('x-was-deleted-successfully'),
					`<strong>${objectDefinition?.name}</strong>`
				),
				type: 'success',
			});

			window.location.reload();

			return;
		}
		onClose();
	};

	useEffect(() => {
		Liferay.on('deleteObjectDefinition', getDeleteObjectDefinition);

		return () => {
			Liferay.detach('deleteObjectDefinition');
		};
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, []);

	return (
		<ClayModalProvider>
			{objectDefinition && (
				<ModalDeleteObjectDefinition
					objectDefinition={objectDefinition}
					observer={observer}
					onClose={onClose}
					onDelete={deleteRelationship}
				/>
			)}
		</ClayModalProvider>
	);
}
