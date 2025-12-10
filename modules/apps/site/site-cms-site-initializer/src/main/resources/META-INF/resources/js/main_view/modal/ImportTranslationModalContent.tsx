/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayModal from '@clayui/modal';
import {openToast} from 'frontend-js-components-web';
import React from 'react';

import {AssetLibrary} from '../../common/types/AssetLibrary';
import MultipleFileUploader, {
	UploadMessages,
} from '../multiple_file_uploader/MultipleFileUploader';

const VALID_EXTENSIONS = '.xliff,.xlf,.zip';

const IMPORT_MESSAGES: UploadMessages = {
	anotherFileButton: Liferay.Language.get('import-another-file'),
	filesToUpload: Liferay.Language.get('files-to-import'),
	loadingMessageDescription: Liferay.Language.get(
		'closing-the-window-will-cancel-the-import-process'
	),
	loadingMessageTitle: Liferay.Language.get('importing-files'),
	xFilesNotUploaded: Liferay.Language.get('x-files-could-not-be-imported'),
};

export default function ImportTranslationModalContent({
	groupId,
	loadData,
	onModalClose,
}: {
	groupId: number;
	loadData?: () => void;
	onModalClose: () => void;
}) {
	const uploadRequest = async () => {
		return true;
	};

	const onUploadComplete = ({
		failedFiles,
		successFiles,
	}: {
		assetLibrary: AssetLibrary | null;
		failedFiles: string[];
		successFiles: string[];
	}) => {
		if (successFiles.length) {
			loadData?.();

			openToast({
				message: 'suscess',
				type: 'success',
			});
		}

		if (!failedFiles.length) {
			onModalClose();
		}
	};

	return (
		<>
			<ClayModal.Header
				closeButtonAriaLabel={Liferay.Language.get('close')}
			>
				{Liferay.Language.get('import-translation')}
			</ClayModal.Header>

			<MultipleFileUploader
				assetLibraries={[]}
				buttonLabel={Liferay.Language.get('import')}
				description={Liferay.Language.get(
					'please-upload-your-translation-files'
				)}
				groupId={groupId}
				messages={IMPORT_MESSAGES}
				onModalClose={onModalClose}
				onUploadComplete={onUploadComplete}
				uploadRequest={uploadRequest}
				validExtensions={VALID_EXTENSIONS}
			/>
		</>
	);
}
