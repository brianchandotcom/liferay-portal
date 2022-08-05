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
import {API} from '@liferay/object-js-components-web';
import {createResourceURL, sub} from 'frontend-js-web';
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

	if (code !== 0) {
		onDelete(id);

		return null;
	}

	return (
		<DangerModal
			errorMessage={sub(
				Liferay.Language.get('input-does-not-match-x'),
				`${name}`
			)}
			observer={observer}
			onClose={onClose}
			onDelete={() => onDelete(id)}
			placeholder={Liferay.Language.get('confirm-object-definition-name')}
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
	onDelete: (value: string) => Promise<void>;
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

	const {observer, onClose} = useModal({
		onClose: () => setObjectDefinition(null),
	});

	const deleteObjectDefinition = async (id: string) => {
		API.deleteObjectDefinitions(Number(id)).then(() => {
			Liferay.Util.openToast({
				message: sub(
					Liferay.Language.get('x-was-deleted-successfully'),
					`<strong>${objectDefinition?.name}</strong>`
				),
			});

			onClose();
			setTimeout(window.location.reload, 1500);
		});
	};

	useEffect(() => {
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
			const url = createResourceURL(baseResourceURL, {
				objectDefinitionId: itemData.id,
				p_p_resource_id:
					'/object_definitions/get_object_definition_delete_info',
			}).href;
			const {
				hasObjectRelationship,
				objectEntriesCount,
			} = await API.fetchJSON<{
				hasObjectRelationship: boolean;
				objectEntriesCount: number;
			}>(url);

			setObjectDefinition({
				...itemData,
				hasObjectRelationship,
				objectEntriesCount,
			});
		};

		Liferay.on('deleteObjectDefinition', getDeleteObjectDefinition);

		return () => Liferay.detach('deleteObjectDefinition');
	}, [baseResourceURL]);

	return (
		<ClayModalProvider>
			{objectDefinition && (
				<ModalDeleteObjectDefinition
					objectDefinition={objectDefinition}
					observer={observer}
					onClose={onClose}
					onDelete={deleteObjectDefinition}
				/>
			)}
		</ClayModalProvider>
	);
}
