/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClaySelect} from '@clayui/form';
import ClayModal from '@clayui/modal';
import {
	FieldBase,
	FileData,
	MultipleFileUploader,
	UploadRequestCallback,
	openToast,
} from 'frontend-js-components-web';
import {fetch, getFileAsBase64, sub} from 'frontend-js-web';
import React, {useMemo, useState} from 'react';

export type Space = {
	externalReferenceCode: string;
	id?: string | number;
	name?: string;
};

export default function CMSUploadModal({
	initialFiles,
	onModalClose,
	onUploadSuccess,
	spaces,
}: {
	initialFiles?: File[];
	onModalClose: () => void;
	onUploadSuccess: () => void;
	spaces: Space[];
}) {
	const [spaceERC, setSpaceERC] = useState<string>('');
	const [spaceError, setSpaceError] = useState(false);

	const filesToUpload = useMemo<FileData[] | undefined>(
		() =>
			initialFiles?.map((file) => ({
				file,
				name: file.name,
				size: file.size,
			})),
		[initialFiles]
	);

	const isValidSpace = (value: string) =>
		spaces.some((space) => space.externalReferenceCode === value);

	const formValidation = async () => {
		const isValid = isValidSpace(spaceERC);

		setSpaceError(!isValid);

		return isValid;
	};

	const uploadRequest: UploadRequestCallback = async ({fileData}) => {
		if (!isValidSpace(spaceERC)) {
			setSpaceError(true);

			throw new Error(Liferay.Language.get('space-is-required'));
		}

		const fileBase64 = await getFileAsBase64(fileData.file);

		const response = await fetch(
			`/o/cms/basic-documents/scopes/${spaceERC}`,
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
					'Content-Type': 'application/json',
				},
				method: 'POST',
			}
		);

		if (!response.ok) {
			throw new Error('Upload failed');
		}

		return response.json();
	};

	const selectedSpace = spaces.find(
		(space) => space.externalReferenceCode === spaceERC
	);

	const onUploadComplete = ({
		failedFiles,
		successFiles,
	}: {
		failedFiles: string[];
		successFiles: string[];
	}) => {
		if (successFiles.length && selectedSpace) {
			onUploadSuccess();

			const toastMessage =
				successFiles.length === 1
					? sub(
							Liferay.Language.get(
								'x-file-was-successfully-uploaded-to-x-space'
							),
							['1', selectedSpace.name]
						)
					: sub(
							Liferay.Language.get(
								'x-files-were-successfully-uploaded-to-x-space'
							),
							[String(successFiles.length), selectedSpace.name]
						);

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
				{Liferay.Language.get('upload-files')}
			</ClayModal.Header>

			<MultipleFileUploader
				filesToUpload={filesToUpload}
				formValidation={formValidation}
				onModalClose={onModalClose}
				onUploadComplete={onUploadComplete}
				scopeSelectorElement={
					spaces.length ? (
						<FieldBase
							errorMessage={
								spaceError
									? Liferay.Language.get(
											'this-field-is-required'
										)
									: undefined
							}
							helpMessage={Liferay.Language.get(
								'select-the-space-to-upload-the-file'
							)}
							id="spaceSelect"
							label={Liferay.Language.get('space')}
							required
						>
							<ClaySelect
								id="spaceSelect"
								onChange={(event) => {
									setSpaceError(false);
									setSpaceERC(event.target.value);
								}}
								value={spaceERC}
							>
								<ClaySelect.Option
									aria-label={Liferay.Language.get(
										'select-a-space'
									)}
									label={Liferay.Language.get(
										'select-a-space'
									)}
									value=""
								/>

								{spaces.map((space) => (
									<ClaySelect.Option
										key={space.externalReferenceCode}
										label={space.name}
										value={space.externalReferenceCode}
									/>
								))}
							</ClaySelect>
						</FieldBase>
					) : undefined
				}
				uploadRequest={uploadRequest}
			/>
		</>
	);
}
