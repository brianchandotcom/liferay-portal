/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayModal from '@clayui/modal';
import {
	FileData,
	MultipleFileUploader,
	UploadBatchesCallback,
	UploadMessages,
	openToast,
} from 'frontend-js-components-web';
import {sub} from 'frontend-js-web';
import React from 'react';

import ApiHelper from '../../common/services/ApiHelper';

const VALID_EXTENSIONS = '.xliff,.xlf,.zip';

const sequentialUploadBatches: UploadBatchesCallback = (files) =>
	files.map((file) => [file]);

const IMPORT_MESSAGES: UploadMessages = {
	anotherFileButton: Liferay.Language.get('import-another-file'),
	filesToUpload: Liferay.Language.get('files-to-import'),
	loadingMessageDescription: Liferay.Language.get(
		'closing-the-window-will-cancel-the-import-process'
	),
	loadingMessageTitle: Liferay.Language.get('importing-files'),
	xFilesNotUploaded: Liferay.Language.get('x-files-could-not-be-imported'),
};

interface FailedImportMessage {
	container: string;
	errorMessage: string;
	fileName: string;
}

interface ImportTranslationResultData {
	failureMessagesJSON: string[];
	successMessages: string[];
}

export default function ImportTranslationModalContent({
	actionLink,
	itemName,
	loadData,
	onModalClose,
	translationsAPIURL,
}: {
	actionLink: string;
	itemName: string;
	loadData?: () => void;
	onModalClose: () => void;
	translationsAPIURL: string;
}) {
	const getItemLink = () => {
		return `<a href="${actionLink}" class="alert-link lead"><strong>${itemName}</strong></a>`;
	};

	const uploadRequest = async ({fileData}: {fileData: FileData}) => {
		const formData = new FormData();

		formData.append('file', fileData.file);

		const response =
			await ApiHelper.postFormData<ImportTranslationResultData>(
				formData,
				translationsAPIURL
			);

		const failureMessagesJSON = response.data?.failureMessagesJSON ?? [];
		const successFiles = response.data?.successMessages ?? [];

		if (!failureMessagesJSON.length) {
			return {
				successFiles,
			};
		}

		return {
			errors: failureMessagesJSON.map((item) => {
				const jsonItem = JSON.parse(item) as FailedImportMessage;

				return {
					errorMessage: jsonItem.errorMessage,
					name: jsonItem.fileName,
				};
			}),
			multipleErrors: true,
			successFiles,
		};
	};

	const onUploadComplete = ({
		failedFiles,
		successFiles,
	}: {
		failedFiles: string[];
		successFiles: string[];
	}) => {
		if (successFiles.length) {
			loadData?.();

			let toastMessage;

			if (successFiles.length === 1) {
				toastMessage = sub(
					Liferay.Language.get(
						'x-file-was-successfully-imported-x-is-now-published-with-new-translations'
					),
					['1', getItemLink()]
				);
			}
			else {
				toastMessage = sub(
					Liferay.Language.get('x-files-were-successfully-imported'),
					[String(successFiles.length)]
				);
			}

			openToast({
				message: toastMessage,
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
				buttonLabel={Liferay.Language.get('import')}
				description={Liferay.Language.get(
					'please-upload-your-translation-files'
				)}
				messages={IMPORT_MESSAGES}
				onModalClose={onModalClose}
				onUploadComplete={onUploadComplete}
				uploadBatches={sequentialUploadBatches}
				uploadRequest={uploadRequest}
				validExtensions={VALID_EXTENSIONS}
			/>
		</>
	);
}
