/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayModal from '@clayui/modal';
import {
	FieldBase,
	FileData,
	MultipleFileUploader,
	UploadRequestCallback,
	openToast,
} from 'frontend-js-components-web';
import {getFileAsBase64, sub} from 'frontend-js-web';
import React, {useId, useState} from 'react';

import SpaceSelector from '../../common/components/SpaceSelector';
import ApiHelper from '../../common/services/ApiHelper';
import {AssetLibrary} from '../../common/types/AssetLibrary';
import {Space} from '../../common/types/Space';

export default function MultipleFilesUploadModalContent({
	assetLibraries,
	baseAssetLibraryViewURL,
	filesToUpload,
	keywords,
	loadData,
	onModalClose,
	parentObjectEntryFolderExternalReferenceCode,
}: {
	assetLibraries: AssetLibrary[];
	baseAssetLibraryViewURL: string;
	filesToUpload?: FileData[];
	keywords?: string;
	loadData?: () => void;
	onModalClose: () => void;
	parentObjectEntryFolderExternalReferenceCode: string;
}) {
	const [groupId, setGroupId] = useState<number>(
		assetLibraries?.length === 1 ? assetLibraries?.[0].groupId : 0
	);

	const [groupIdError, setGroupIdError] = useState(false);

	const groupIdInputId = useId();

	const [space, setSpace] = useState<Space>();

	const formValidation = async () => {
		const error = (groupId || 0) <= 0;

		setGroupIdError(error);

		return error === false;
	};

	const getAssetLibraryLink = () => {
		const assetLibrary = assetLibraries?.find(
			(assetLibrary) => Number(assetLibrary.groupId) === groupId
		);

		return `<a href="${baseAssetLibraryViewURL}${assetLibrary?.groupId}" class="alert-link lead"><strong>${assetLibrary?.name}</strong></a>`;
	};

	const uploadRequest: UploadRequestCallback = async ({
		fileData,
	}: {
		fileData: FileData;
	}) => {
		if (!groupId) {
			setGroupIdError(true);

			throw new Error(
				sub(
					Liferay.Language.get('no-x-selected'),
					Liferay.Language.get('space')
				)
			);
		}

		const fileBase64 = await getFileAsBase64(fileData.file);

		return await ApiHelper.post(
			`/o/cms/basic-documents/scopes/${groupId}`,
			{
				file: {
					fileBase64,
					name: fileData.name,
				},
				keywords: keywords?.split(','),
				objectEntryFolderExternalReferenceCode:
					parentObjectEntryFolderExternalReferenceCode || 'L_FILES',
				title: fileData.name,
			}
		);
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
						'x-file-was-successfully-uploaded-to-x-space'
					),
					['1', getAssetLibraryLink()]
				);
			}
			else {
				toastMessage = sub(
					Liferay.Language.get(
						'x-files-were-successfully-uploaded-to-x-space'
					),
					[String(successFiles.length), getAssetLibraryLink()]
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
				{sub(
					Liferay.Language.get('upload-x'),
					Liferay.Language.get('multiple-files')
				)}
			</ClayModal.Header>

			<MultipleFileUploader
				filesToUpload={filesToUpload}
				formValidation={formValidation}
				onModalClose={onModalClose}
				onUploadComplete={onUploadComplete}
				scopeSelectorElement={
					assetLibraries && assetLibraries.length > 1 ? (
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
									onSpaceChange={(space) => {
										setGroupIdError(false);
										setGroupId(space ? space.siteId : 0);
										setSpace(space);
									}}
									space={space}
								/>
							</FieldBase>
						</div>
					) : undefined
				}
				uploadRequest={uploadRequest}
			/>
		</>
	);
}
