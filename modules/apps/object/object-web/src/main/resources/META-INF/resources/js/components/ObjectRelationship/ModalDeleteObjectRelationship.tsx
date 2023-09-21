/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayModalProvider, useModal} from '@clayui/modal';
import {sub} from 'frontend-js-web';
import React from 'react';

import DangerModal from '../DangerModal';
import {deleteRelationship} from '../ViewObjectDefinitions/objectDefinitionUtil';
import WarningModal from '../WarningModal';

interface ModalDeleteObjectRelationshipProps {
	handleOnClose: () => void;
	objectRelationship: ObjectRelationship;
	onAfterSubmit?: () => void;
	reload?: boolean;
	setObjectRelationship?: (value: ObjectRelationship | null) => void;
}

export function ModalDeleteObjectRelationship({
	handleOnClose,
	objectRelationship,
	onAfterSubmit,
	reload = true,
	setObjectRelationship,
}: ModalDeleteObjectRelationshipProps) {
	const {observer, onClose} = useModal({
		onClose: () => {
			if (setObjectRelationship) {
				setObjectRelationship(null);
			}
			handleOnClose();
		},
	});

	return (
		<ClayModalProvider>
			{objectRelationship.reverse ? (
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
					onDelete={() => {
						deleteRelationship(objectRelationship.id);
						onClose();

						if (reload) {
							setTimeout(() => window.location.reload(), 1500);
						}

						if (onAfterSubmit) {
							setTimeout(() => onAfterSubmit(), 200);
						}
					}}
					placeholder={Liferay.Language.get(
						'confirm-relationship-name'
					)}
					title={Liferay.Language.get('delete-relationship')}
					token={objectRelationship.name}
				>
					<p>
						{Liferay.Language.get(
							'this-action-cannot-be-undone-and-will-permanently-delete-all-related-fields-from-this-relationship'
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
			)}
		</ClayModalProvider>
	);
}
