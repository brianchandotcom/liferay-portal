/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {type Space, SpaceSelector} from '@liferay/site-cms-site-initializer';
import {
	FieldBase,
	MultipleFileUploader,
	UploadRequestCallback,
} from 'frontend-js-components-web';
import {getFileAsBase64} from 'frontend-js-web';
import React, {useId, useState} from 'react';

import {FilesUploaderComponent} from '../item_selector/ItemSelectorModal';

const CMSFileUploaderComponent: FilesUploaderComponent = function ({
	files,
	groupId,
	onCloseUploadView,
}) {
	const [spaceId, setSpaceId] = useState<number | undefined>(groupId);
	const [groupIdError, setGroupIdError] = useState(false);

	const groupIdInputId = useId();

	const formValidation = async () => {
		const error = (spaceId || 0) <= 0;

		setGroupIdError(error);

		return error === false;
	};

	const uploadRequest: UploadRequestCallback = async ({fileData}) => {
		const fileBase64 = await getFileAsBase64(fileData.file);

		const response = await Liferay.Util.fetch(
			`/o/cms/basic-documents/scopes/${spaceId}`,
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

	if (groupId) {
		return (
			<MultipleFileUploader
				filesToUpload={files}
				onModalClose={onCloseUploadView}
				onUploadComplete={onUploadComplete}
				uploadRequest={uploadRequest}
			/>
		);
	}
	else {
		return (
			<MultipleFileUploader
				filesToUpload={files}
				formValidation={formValidation}
				onModalClose={onCloseUploadView}
				onUploadComplete={onUploadComplete}
				scopeSelectorElement={
					<div className="mt-4">
						<FieldBase
							errorMessage={
								groupIdError
									? Liferay.Language.get(
											'this-field-is-required'
										)
									: undefined
							}
							helpMessage={Liferay.Language.get(
								'select-the-space-to-upload-the-file'
							)}
							id={groupIdInputId}
							label={Liferay.Language.get('space')}
							required
						>
							<SpaceSelector
								id={groupIdInputId}
								onSpaceChange={(space: Space) => {
									setGroupIdError(false);
									setSpaceId(space.siteId);
								}}
								space={spaceId}
							/>
						</FieldBase>
					</div>
				}
				uploadRequest={uploadRequest}
			/>
		);
	}
};

export default CMSFileUploaderComponent;
