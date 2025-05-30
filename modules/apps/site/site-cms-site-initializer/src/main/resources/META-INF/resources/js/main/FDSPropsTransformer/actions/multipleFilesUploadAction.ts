/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {openModal} from 'frontend-js-components-web';

import {AssetLibrary} from '../../../types/AssetLibrary';
import MultipleFilesUploadModalContent from '../../components/modal/MultipleFilesUploadModalContent';

export type MultipleFileUploaderData = {
	assetLibraries: AssetLibrary[];
	baseAssetLibraryViewURL: string;
};

export default function multipleFilesUploadAction(
	data: MultipleFileUploaderData
) {
	openModal({
		containerProps: {
			className: '',
		},
		contentComponent: ({closeModal}: {closeModal: () => void}) =>
			MultipleFilesUploadModalContent({
				assetLibraries: data.assetLibraries,
				baseAssetLibraryViewURL: data.baseAssetLibraryViewURL,
				onModalClose: closeModal,
			}),
		size: 'md',
	});
}
