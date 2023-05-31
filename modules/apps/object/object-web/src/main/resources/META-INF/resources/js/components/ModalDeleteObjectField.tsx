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

import ClayButton from '@clayui/button';
import ClayModal, {ClayModalProvider, useModal} from '@clayui/modal';
import {Observer} from '@clayui/modal/lib/types';
import {API} from '@liferay/object-js-components-web';
import {createResourceURL, sub} from 'frontend-js-web';
import React, {useEffect, useState} from 'react';

import {defaultLanguageId} from '../utils/constants';
import WarningModal from './WarningModal';

function ModalDeleteObjectField({
	objectField,
	observer,
	onClose,
	onDelete,
}: IProps) {
	const {
		id,
		label,
		showDeletionModal,
		showDeletionNotAllowedModal,
	} = objectField;

	if (!showDeletionModal) {
		onDelete(id);

		return null;
	}

	if (showDeletionNotAllowedModal) {
		return (
			<WarningModal
				observer={observer}
				onClose={onClose}
				title={Liferay.Language.get('deletion-not-allowed')}
			>
				<div>
					{sub(
						Liferay.Language.get(
							'x-is-the-only-field-of-the-published-object-definition-and-cannot-be-deleted'
						),
						`${label[defaultLanguageId]}`
					)}
				</div>
			</WarningModal>
		);
	}

	return (
		<ClayModal observer={observer} status="danger">
			<ClayModal.Header>
				{Liferay.Language.get('delete-object-field')}
			</ClayModal.Header>

			<ClayModal.Body>
				<p>
					{Liferay.Language.get(
						"this-action-cannot-be-undone-and-will-permanently-delete-this-field's-data"
					)}
				</p>

				<p>{Liferay.Language.get('it-may-affect-many-records')}</p>

				<p>{Liferay.Language.get('do-you-want-to-proceed')}</p>
			</ClayModal.Body>

			<ClayModal.Footer
				last={
					<ClayButton.Group spaced>
						<ClayButton displayType="secondary" onClick={onClose}>
							{Liferay.Language.get('cancel')}
						</ClayButton>

						<ClayButton
							displayType="danger"
							onClick={() => onDelete(id)}
						>
							{Liferay.Language.get('delete')}
						</ClayButton>
					</ClayButton.Group>
				}
			></ClayModal.Footer>
		</ClayModal>
	);
}

interface IProps {
	objectField: {
		id: string;
		label: LocalizedValue<string>;
		showDeletionModal: boolean;
		showDeletionNotAllowedModal: boolean;
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
	const [objectField, setObjectField] = useState<{
		id: string;
		label: LocalizedValue<string>;
		showDeletionModal: boolean;
		showDeletionNotAllowedModal: boolean;
	} | null>(null);

	const {observer, onClose, open} = useModal({
		onClose: () => setObjectField(null),
	});

	const deleteObjectField = async (id: string) => {
		try {
			await API.deleteObjectField(Number(id));

			Liferay.Util.openToast({
				message: sub(
					Liferay.Language.get('x-was-deleted-successfully'),
					`<strong>${objectField?.label[defaultLanguageId]}</strong>`
				),
			});

			open ? onClose() : setObjectField(null);

			setTimeout(() => window.location.reload(), 1500);
		}
		catch (error) {
			Liferay.Util.openToast({
				message: (error as Error).message,
				type: 'danger',
			});
		}
	};

	useEffect(() => {
		const getDeleteObjectField = async ({
			itemData,
		}: {
			itemData: {
				id: string;
				label: LocalizedValue<string>;
			};
		}) => {
			const url = createResourceURL(baseResourceURL, {
				objectFieldId: itemData.id,
				p_p_resource_id:
					'/object_definitions/get_object_field_delete_info',
			}).href;
			const {
				showDeletionModal,
				showDeletionNotAllowedModal,
			} = await API.fetchJSON<{
				showDeletionModal: boolean;
				showDeletionNotAllowedModal: boolean;
			}>(url);

			setObjectField({
				...itemData,
				showDeletionModal,
				showDeletionNotAllowedModal,
			});
		};

		Liferay.on('deleteObjectField', getDeleteObjectField);

		return () => Liferay.detach('deleteObjectField');
	}, [baseResourceURL]);

	return (
		<ClayModalProvider>
			{objectField && (
				<ModalDeleteObjectField
					objectField={objectField}
					observer={observer}
					onClose={onClose}
					onDelete={deleteObjectField}
				/>
			)}
		</ClayModalProvider>
	);
}
