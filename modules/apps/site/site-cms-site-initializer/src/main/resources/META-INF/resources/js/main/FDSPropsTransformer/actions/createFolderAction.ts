/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {openModal, openToast} from 'frontend-js-components-web';
import {sub} from 'frontend-js-web';

import {postScopeScopeKeyObjectEntryFolder} from '../../../api/api';
import CreationModalContent, {
	AssetLibray,
} from '../../components/modal/CreationModalContent';

export type FolderData = {
	action: 'createFolder';
	assetLibraries: AssetLibray[];
};

export default function createFolderAction(
	data: FolderData,
	additionalProps: {parentObjectEntryFolderExternalReferenceCode: string}
) {
	openModal({
		center: true,
		contentComponent: ({closeModal}: {closeModal: () => void}) =>
			CreationModalContent({
				...data,
				closeModal,
				onSubmit: async ({groupId, name}) => {
					const response = await postScopeScopeKeyObjectEntryFolder(
						groupId,
						name,
						additionalProps.parentObjectEntryFolderExternalReferenceCode
					);

					if (response.ok) {
						closeModal();

						openToast({
							message: sub(
								Liferay.Language.get(
									'x-was-created-successfully'
								),
								`<strong>${name}</strong>`
							),
							onClose: () => {
								window.location.reload();
							},
							type: 'success',
						});
					}
					else {
						const {
							message,
						}: {
							message?: string;
						} = await response.json();

						openToast({
							message:
								message ||
								Liferay.Language.get(
									'an-unexpected-error-occurred'
								),
							type: 'danger',
						});
					}
				},
				title: Liferay.Language.get('new-folder'),
			}),
		size: 'sm',
	});
}
