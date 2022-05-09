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
import {createResourceURL, fetch} from 'frontend-js-web';
import React, {useEffect, useState} from 'react';

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

	return hasObjectRelationship ? (
		<WarningModal
			observer={observer}
			onClose={onClose}
			title={Liferay.Language.get('deletion-not-allowed')}
		>
			<div>
				{Liferay.Language.get(
					'this-object-cannot-be-deleted-because-it-is-related-to-others'
				)}
			</div>

			<div>
				{Liferay.Language.get(
					'to-delete-this-object-you-need-first-delete-its-relationships'
				)}
			</div>

			<div>
				{Liferay.Language.get('go-to-object-details-relationships')}
			</div>
		</WarningModal>
	) : code === 0 ? (
		<DangerModal
			errorMessage={Liferay.Language.get(
				'input-and-object-name-does-not-match'
			)}
			observer={observer}
			onClose={onClose}
			onDelete={() => onDelete(id)}
			title={Liferay.Language.get('delete-custom-object')}
			token={name}
		>
			<p>
				{Liferay.Language.get(
					'deleted-custom-objects-cannot-be-restored'
				)}
			</p>

			<ul>
				<li>{Liferay.Language.get('objects-definitions')}</li>

				<li>
					{Liferay.Language.get('all-data-records-in-the-object')}
				</li>
			</ul>

			<p>
				{Liferay.Util.sub(
					Liferay.Language.get('x-has-x-records'),
					`${name}`,
					`${objectEntriesCount}`
				)}
			</p>

			<p>
				{Liferay.Language.get(
					'to-export-all-records-go-to-global-menu-applications-import-export-center'
				)}
			</p>

			<p
				dangerouslySetInnerHTML={{
					__html: Liferay.Util.sub(
						Liferay.Language.get(
							'please-type-the-object-name-x-to-confirm'
						),
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
	objectDefinition: any;
	observer: Observer;
	onClose: () => void;
	onDelete: any;
}

export default function ModalWithProvider({baseResourceURL}: any) {
	const [objectDefinition, setObjectDefinition] = useState<any>(null);

	const getDeleteObjectDefinition = async ({itemData}: any) => {
		const response = await fetch(
			createResourceURL(baseResourceURL, {
				objectDefinitionId: itemData.id,
				p_p_resource_id:
					'/object_definitions/get_object_definition_delete_info',
			})
		);

		const data = (await response.json()) as {
			hasObjectRelationship: boolean;
			objectEntriesCount: number;
		};

		setObjectDefinition({
			...itemData,
			hasObjectRelationship: data?.hasObjectRelationship,
			objectEntriesCount: data?.objectEntriesCount,
		});
	};

	const {observer, onClose} = useModal({
		onClose: () => setObjectDefinition(null),
	});

	const deleteRelationship = async (id: string) => {
		const response = await fetch(
			`/o/object-admin/v1.0/object-definitions/${id}`,
			{
				headers: new Headers({
					'Accept': 'application/json',
					'Content-Type': 'application/json',
				}),
				method: 'DELETE',
			}
		);

		if (response.ok) {
			Liferay.Util.openToast({
				message: Liferay.Language.get(
					'custom-object-deleted-successfully'
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
