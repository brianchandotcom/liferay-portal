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
import {sub} from 'frontend-js-web';
import React, {useCallback, useEffect, useState} from 'react';

import DangerModal from '../DangerModal';
import WarningModal from '../WarningModal';

function ModalDeleteObjectRelationship({
	objectRelationship,
	observer,
	onClose,
	onDelete,
}: IProps) {
	return objectRelationship.reverse ? (
		<WarningModal
			observer={observer}
			onClose={onClose}
			title={Liferay.Language.get('deletion-not-allowed')}
		>
			<div>
				{Liferay.Language.get(
					'you-do-not-have-permission-to-delete-this-relationship'
				)}
			</div>

			<div>
				{Liferay.Language.get(
					'you-cannot-delete-a-relationship-from-here'
				)}
			</div>
		</WarningModal>
	) : (
		<DangerModal
			errorMessage={Liferay.Language.get(
				'input-and-relationship-name-do-not-match'
			)}
			observer={observer}
			onClose={onClose}
			onDelete={() => onDelete(objectRelationship.id)}
			placeholder={Liferay.Language.get('confirm-relationship-name')}
			title={Liferay.Language.get('delete-relationship')}
			token={objectRelationship.name}
		>
			<p>
				{Liferay.Language.get(
					'this-action-cannot-be-undone-and-will-permanently-permanently-all-related-fields-from-this-relationship'
				)}
			</p>

			<p>{Liferay.Language.get('it-may-affect-many-records')}</p>

			<p
				dangerouslySetInnerHTML={{
					__html: sub(
						Liferay.Language.get(
							'please-type-the-relationship-name-x-to-confirm'
						),
						`<strong>${objectRelationship.name}</strong>`
					),
				}}
			/>
		</DangerModal>
	);
}

interface IProps {
	objectRelationship: ObjectRelationship;
	observer: Observer;
	onClose: () => void;
	onDelete: (value: number) => void;
}

export default function ModalWithProvider({isApproved}: {isApproved: boolean}) {
	const [
		objectRelationship,
		setObjectRelationship,
	] = useState<ObjectRelationship | null>();

	const {observer, onClose} = useModal({
		onClose: () => setObjectRelationship(null),
	});

	const deleteRelationship = useCallback(
		(id: number) => {
			API.deleteObjectRelationships(id).then(() => {
				Liferay.Util.openToast({
					message: Liferay.Language.get(
						'relationship-was-deleted-successfully'
					),
				});

				onClose();
				setTimeout(() => window.location.reload(), 1500);
			});
		},
		[onClose]
	);

	useEffect(() => {
		const getObjectRelationship = ({itemData}: any) => {
			if (isApproved || itemData.reverse) {
				setObjectRelationship(itemData);
			}
			else {
				deleteRelationship(itemData.id);
			}
		};

		Liferay.on('deleteObjectRelationship', getObjectRelationship);

		return () => Liferay.detach('deleteObjectRelationship');
	}, [deleteRelationship, isApproved]);

	return (
		<ClayModalProvider>
			{objectRelationship && (
				<ModalDeleteObjectRelationship
					objectRelationship={objectRelationship}
					observer={observer}
					onClose={onClose}
					onDelete={deleteRelationship}
				/>
			)}
		</ClayModalProvider>
	);
}
