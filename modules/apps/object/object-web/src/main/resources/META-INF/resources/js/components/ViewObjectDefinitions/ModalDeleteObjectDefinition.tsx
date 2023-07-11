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
import {sub} from 'frontend-js-web';
import React from 'react';

import DangerModal from '../DangerModal';
import WarningModal from '../WarningModal';
import {DeletedObjectDefinition} from './ViewObjectDefinitions';
import {deleteObjectDefinition} from './objectDefinitionUtil';

interface ModalDeleteObjectDefinitionProps {
	handleOnClose: () => void;
	objectDefinition: DeletedObjectDefinition;
	setDeletedObjectDefinition: (value: DeletedObjectDefinition | null) => void;
}

export function ModalDeleteObjectDefinition({
	handleOnClose,
	objectDefinition,
	setDeletedObjectDefinition,
}: ModalDeleteObjectDefinitionProps) {
	const {observer, onClose} = useModal({
		onClose: () => {
			setDeletedObjectDefinition(null);
			handleOnClose();
		},
	});

	return (
		<ClayModalProvider>
			{objectDefinition?.hasObjectRelationship ? (
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
							`${objectDefinition?.name}`
						)}
					</div>

					<div>
						{sub(
							Liferay.Language.get(
								'to-delete-x,-you-must-first-delete-its-relationships'
							),
							`${objectDefinition?.name}`
						)}
					</div>

					<div>
						{Liferay.Language.get(
							'go-to-object-details-relationships'
						)}
					</div>
				</WarningModal>
			) : (
				<DangerModal
					errorMessage={sub(
						Liferay.Language.get('input-does-not-match-x'),
						`${objectDefinition?.name}`
					)}
					observer={observer}
					onClose={onClose}
					onDelete={async () => {
						await deleteObjectDefinition(
							objectDefinition?.id,
							objectDefinition?.name
						);
						setTimeout(() => window.location.reload(), 1500);
						onClose();
					}}
					placeholder={Liferay.Language.get(
						'confirm-object-definition-name'
					)}
					title={Liferay.Language.get('delete-object-definition')}
					token={objectDefinition?.name}
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
								`<strong>${objectDefinition?.name}</strong>`,
								`${objectDefinition?.objectEntriesCount}`
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
								Liferay.Language.get(
									'please-enter-x-to-confirm'
								),
								`<strong>${objectDefinition?.name}</strong>`
							),
						}}
					/>
				</DangerModal>
			)}
		</ClayModalProvider>
	);
}
