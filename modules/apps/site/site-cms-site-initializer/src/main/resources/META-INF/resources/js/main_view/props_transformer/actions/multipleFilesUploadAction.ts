/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FileData} from 'frontend-js-components-web';

import {AssetLibrary} from '../../../common/types/AssetLibrary';
import {openCMSModal} from '../../../common/utils/openCMSModal';
import MultipleFilesUploadModalContent from '../../modal/MultipleFilesUploadModalContent';

export type MultipleFileUploaderData = {
	assetLibraries: AssetLibrary[];
	baseAssetLibraryViewURL: string;
	keywords?: string;
	parentObjectEntryFolderExternalReferenceCode: string;
};

export default function multipleFilesUploadAction(
	data: MultipleFileUploaderData & {
		filesToUpload?: FileData[];
	},
	loadData?: () => void
) {
	openCMSModal({
		contentComponent: ({closeModal}: {closeModal: () => void}) =>
			MultipleFilesUploadModalContent({
				...data,
				loadData,
				onModalClose: closeModal,
			}),
		size: 'md',
	});
}
