/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	MultipleFileUploader,
	UploadRequestCallback,
} from 'frontend-js-components-web';
import {getFileAsBase64} from 'frontend-js-web';
import React from 'react';

import {FilesUploaderComponent} from '../item_selector/ItemSelectorModal';

const CMSFileUploaderComponent: FilesUploaderComponent = function ({
	files,
	groupId,
	onCloseUploadView,
}) {
	const uploadRequest: UploadRequestCallback = async ({fileData}) => {
		if (!groupId) {
			throw new Error('spaceId is not defined');
		}

		const fileBase64 = await getFileAsBase64(fileData.file);

		const response = await Liferay.Util.fetch(
			`/o/cms/basic-documents/scopes/${groupId}`,
			{
				body: JSON.stringify({
					file: {
						fileBase64,
						name: fileData.name,
					},
					objectEntryFolderExternalReferenceCode: 'L_FILES',
					title: fileData.name,
				}),
				headers: {
					'Accept': 'application/json',
					'Accept-Language':
						Liferay.ThemeDisplay.getBCP47LanguageId(),
					'Content-Type': 'application/json',
				},
				method: 'POST',
			}
		);

		return await response.json();
	};

	const onUploadComplete = ({
		failedFiles,
	}: {
		failedFiles: string[];
		successFiles: string[];
	}) => {
		if (!failedFiles.length) {
			onCloseUploadView();
		}
	};

	return (
		<MultipleFileUploader
			filesToUpload={files}
			onModalClose={onCloseUploadView}
			onUploadComplete={onUploadComplete}
			uploadRequest={uploadRequest}
		/>
	);
};

export default CMSFileUploaderComponent;
