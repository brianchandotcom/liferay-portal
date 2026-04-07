/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayModal from '@clayui/modal';
import {
	FieldBase,
	FileData,
	MultipleFileUploader,
	openToast,
} from 'frontend-js-components-web';
import {sub} from 'frontend-js-web';
import React, {useId, useState} from 'react';

import SpaceSelector from '../../common/components/SpaceSelector';
import ApiHelper from '../../common/services/ApiHelper';
import {AssetLibrary} from '../../common/types/AssetLibrary';
import {Space} from '../../common/types/Space';

const getBase64 = (file: File): Promise<string> => {
	return new Promise((resolve, reject) => {
		const reader = new FileReader();
		reader.onload = () => {
			if (typeof reader.result === 'string') {
				resolve(reader.result.split(',')[1]);
			}
			else {
				reject(new Error('FileReader did not return a string.'));
			}
		};
		reader.onerror = reject;
		reader.readAsDataURL(file);
	});
};

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

	const getAssetLibraryLink = (assetLibrary: AssetLibrary) => {
		return `<a href="${baseAssetLibraryViewURL}${assetLibrary.groupId}" class="alert-link lead"><strong>${assetLibrary.name}</strong></a>`;
	};

	const uploadRequest = async ({
		fileData,
		groupId,
	}: {
		fileData: FileData;
		groupId: string;
	}) => {
		const fileBase64 = await getBase64(fileData.file);

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
		assetLibrary,
		failedFiles,
		successFiles,
	}: {
		assetLibrary: AssetLibrary | null;
		failedFiles: string[];
		successFiles: string[];
	}) => {
		if (successFiles.length) {
			loadData?.();

			let toastMessage;

			if (assetLibrary) {
				if (successFiles.length === 1) {
					toastMessage = sub(
						Liferay.Language.get(
							'x-file-was-successfully-uploaded-to-x-space'
						),
						['1', getAssetLibraryLink(assetLibrary)]
					);
				}
				else {
					toastMessage = sub(
						Liferay.Language.get(
							'x-files-were-successfully-uploaded-to-x-space'
						),
						[
							String(successFiles.length),
							getAssetLibraryLink(assetLibrary),
						]
					);
				}
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
