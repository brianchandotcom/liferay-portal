/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayModalProvider, useModal} from '@clayui/modal';
import {sub} from 'frontend-js-web';
import React from 'react';

import DangerModal from '../DangerModal';
import {deleteObjectFolder} from './objectDefinitionUtil';

interface ModalDeleteObjectFolderProps {
	handleOnClose: () => void;
	objectFolder: ObjectFolder;
}

export function ModalDeleteObjectFolder({
	handleOnClose,
	objectFolder,
}: ModalDeleteObjectFolderProps) {
	const {observer, onClose} = useModal({
		onClose: () => {
			handleOnClose();
		},
	});

	return (
		<ClayModalProvider>
			<DangerModal
				errorMessage={sub(
					Liferay.Language.get('input-does-not-match-x'),
					`${objectFolder.name}`
				)}
				observer={observer}
				onClose={onClose}
				onDelete={async () => {
					await deleteObjectFolder(
						objectFolder?.id,
						objectFolder?.name
					);

					setTimeout(() => window.location.reload(), 1500);
					onClose();
				}}
				placeholder={Liferay.Language.get('confirm-folder-name')}
				title={Liferay.Language.get('delete-object-folder')}
				token={objectFolder.name}
			>
				<p>
					{Liferay.Language.get(
						'deleting-an-object-folder-will-move-its-object-definitions'
					)}
				</p>

				<p
					dangerouslySetInnerHTML={{
						__html: sub(
							Liferay.Language.get('please-enter-x-to-confirm'),
							`<strong>${objectFolder.name}</strong>`
						),
					}}
				/>
			</DangerModal>
		</ClayModalProvider>
	);
}
