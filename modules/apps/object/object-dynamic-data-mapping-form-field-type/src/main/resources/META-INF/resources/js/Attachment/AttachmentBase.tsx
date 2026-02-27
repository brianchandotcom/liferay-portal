/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import {useModal} from '@clayui/modal';
import {
	convertToFormData,
	makeFetch,
	useConfig,
} from 'data-engine-js-components-web';
import {openSelectionModal} from 'frontend-js-components-web';
import {fetch} from 'frontend-js-web';
import React, {ChangeEventHandler, useEffect, useRef, useState} from 'react';

import FileContainer from './FileContainer';
import CMSFilesItemSelectorModal, {
	CMSFile,
} from './util/CMSFilesItemSelectorModal';
import {validateFileExtension, validateFileSize} from './util/attachment';

import './Attachment.scss';

import type {LocalizedValue} from 'dynamic-data-mapping-form-field-type';

export type AttachmentFile = {
	contentURL: string;
	fileEntryId: string;
	source?: 'dm' | 'cms';
	storageDepotGroup?: string;
	title: string;
};

type DMUploadedFile = {
	contentURL: string;
	fileEntryId: string;
	readOnly: boolean;
	title: string;
};

type CMSUploadResponse = {
	contentURL: string;
	file: {
		id: number;
		link: {
			href: string;
		};
	};
	id: number;
	title: string;
};

interface Space {
	externalReferenceCode: string;
}

export interface AttachmentBaseProps<TValue> {
	acceptedFileExtensions: string;
	attachment: AttachmentFile | null;
	error: {} | {displayErrors: boolean; errorMessage: string; valid: boolean};
	fileSource: string;
	handleDelete: () => void;
	maximumFileSize: number;
	onAttachmentChange: (attachment: AttachmentFile, id: string) => void;
	overallMaximumUploadRequestSize: number;
	readOnly: boolean;
	setError: React.Dispatch<React.SetStateAction<{}>>;
	storageDLFolderPath?: string;
	storageDepotGroup?: string;
	tip: string;
	url: string;
	value: TValue;
}

const getBase64 = (file: File): Promise<string> =>
	new Promise((resolve, reject) => {
		const reader = new FileReader();

		reader.onload = () => {
			if (typeof reader.result === 'string') {
				resolve(reader.result.split(',')[1]);
			}
			else {
				reject(new Error('Invalid FileReader result'));
			}
		};

		reader.onerror = reject;
		reader.readAsDataURL(file);
	});

export default function AttachmentBase({
	acceptedFileExtensions,
	attachment,
	fileSource,
	handleDelete,
	maximumFileSize,
	onAttachmentChange,
	overallMaximumUploadRequestSize,
	readOnly,
	setError,
	storageDLFolderPath,
	storageDepotGroup,
	url,
}: AttachmentBaseProps<string | LocalizedValue<string>>) {
	const {portletNamespace} = useConfig();

	const inputRef = useRef<HTMLInputElement>(null);

	const [cmsFiles, setCMSFiles] = useState<CMSFile[]>([]);
	const [isLoading, setLoading] = useState(false);
	const [spaces, setSpaces] = useState<Space[]>([]);

	const {
		observer: spaceItemSelectorObserver,
		onOpenChange: spaceItemSelectorOpenChange,
		open: spaceItemSelectorOpen,
	} = useModal();

	const DEFAULT_FOLDER_ERC = 'L_FILES';

	const isCMSBasicDocument = fileSource === 'CMSBasicDocument';

	const isDocumentsAndMedia = fileSource === 'documentsAndMedia';

	const isUserComputerToCMSBasicDocument =
		fileSource === 'userComputerToCMSBasicDocument';

	const isUserComputerToDocumentsAndMedia =
		fileSource === 'userComputerToDocumentsAndMedia';

	const hasLibraryStorage =
		isUserComputerToCMSBasicDocument || isUserComputerToDocumentsAndMedia;

	const handleCMSItemsChange = (items: CMSFile[]) => {
		setCMSFiles(items);

		if (!items.length) {
			return;
		}

		const selectedItem = items[0];

		onAttachmentChange(
			{
				contentURL: selectedItem.embedded.file.fileURL,
				fileEntryId: String(selectedItem.embedded.id),
				title: selectedItem.title,
			},
			String(selectedItem.embedded.file.id)
		);
	};

	const handleSelectedItem = (selectedItem: any) => {
		if (!selectedItem) {
			return;
		}

		const selectedItemValue = JSON.parse(selectedItem.value);

		const error =
			validateFileExtension(
				acceptedFileExtensions,
				selectedItemValue.extension
			) ??
			validateFileSize(
				Number(selectedItemValue.size),
				Number(maximumFileSize),
				Number(overallMaximumUploadRequestSize)
			);

		if (error) {
			setError(error);
		}
		else {
			onAttachmentChange(
				{
					contentURL: selectedItemValue.url,
					fileEntryId: selectedItemValue.fileEntryId,
					title: selectedItemValue.title,
				},
				selectedItemValue.fileEntryId
			);
		}
	};

	useEffect(() => {
		fetch(
			"/o/headless-asset-library/v1.0/asset-libraries?filter=type eq 'Space'"
		)
			.then((response) => response.json())
			.then((data) => {
				setSpaces(data.items ?? []);
			});
	}, []);

	const resolveFolderId = async (
		isVisible: boolean,
		spaceERC: string,
		folderName?: string
	): Promise<{
		objectEntryFolderExternalReferenceCode?: string;
		objectEntryFolderId?: number;
	}> => {
		if (!folderName) {
			return {objectEntryFolderExternalReferenceCode: DEFAULT_FOLDER_ERC};
		}

		try {
			const searchParams = new URLSearchParams({
				nestedFields: 'embedded,scope',
				pageSize: '30',
				search: folderName,
			});

			const searchResponse = await fetch(
				`/o/search/v1.0/search?${searchParams}`
			);

			if (searchResponse.ok) {
				const {items = []} = await searchResponse.json();

				const match = items.find((item: any) => {
					const data = item.embedded ?? item;

					return (
						data.title === folderName &&
						data.scope?.externalReferenceCode === spaceERC
					);
				});

				const id = match?.embedded?.id ?? match?.id;

				if (id) {
					return {objectEntryFolderId: id};
				}
			}

			const createBody: any = {
				title: folderName,
			};

			if (isVisible) {
				createBody.parentObjectEntryFolderExternalReferenceCode =
					'L_FILES';
			}

			const createResponse = await fetch(
				`/o/headless-object/v1.0/scopes/${spaceERC}/object-entry-folders`,
				{
					body: JSON.stringify(createBody),
					headers: {
						'Content-Type': 'application/json',
					},
					method: 'POST',
				}
			);

			if (createResponse.ok) {
				const {id} = await createResponse.json();

				if (id) {
					return {objectEntryFolderId: id};
				}
			}
		}
		catch (error) {
			console.warn('Folder resolution failed', error);
		}

		return {objectEntryFolderExternalReferenceCode: DEFAULT_FOLDER_ERC};
	};

	const uploadToCMS = async (
		file: File,
		storageDLFolderPath: string | undefined,
		storageDepotGroup: string | undefined
	): Promise<AttachmentFile & {id: string}> => {
		const spaceERC =
			storageDepotGroup || spaces[0]?.externalReferenceCode || '';

		const fileBase64 = await getBase64(file);

		const isVisible = !!storageDepotGroup;

		const folderName = storageDLFolderPath
			? storageDLFolderPath.replace(/^\//, '')
			: 'HIDDEN_FILES';

		const folder = await resolveFolderId(
			isVisible,
			String(spaceERC),
			folderName
		);

		const body = {
			...folder,
			file: {
				fileBase64,
				name: file.name,
			},
			title: file.name,
		};

		const response = (await makeFetch({
			body: JSON.stringify(body),
			headers: {
				'Accept': 'application/json',
				'Content-Type': 'application/json',
			} as {Accept: string} & Record<string, string>,
			method: 'POST',
			url: `/o/cms/basic-documents/scopes/${spaceERC}`,
		})) as CMSUploadResponse & {status?: string; title?: string};

		const fileUrlPath = response.contentURL || response.file?.link?.href;

		const previewUrl = new URL(fileUrlPath, window.location.origin);

		previewUrl.searchParams.delete('download');

		return {
			contentURL: previewUrl.toString(),
			fileEntryId: String(response.id),
			id: String(response.file?.id),
			source: 'cms',
			title: response.title,
		};
	};

	const uploadToDM = async (
		file: File,
		url: string,
		portletNamespace: string
	): Promise<AttachmentFile & {id: string}> => {
		const {error, file: uploadedFile} = (await makeFetch({
			body: convertToFormData({
				[`${portletNamespace}file`]: file,
			}),
			method: 'POST',
			url,
		})) as {
			error?: {message: string};
			file: DMUploadedFile;
		};

		if (error) {
			throw new Error(error.message);
		}

		return {
			...uploadedFile,
			id: uploadedFile.fileEntryId,
		};
	};

	const handleUpload: ChangeEventHandler<HTMLInputElement> = async ({
		target: {files},
	}) => {
		const selectedFile = files?.[0];

		if (!selectedFile) {
			return;
		}

		const fileSizeError = validateFileSize(
			Number(selectedFile.size),
			Number(maximumFileSize),
			Number(overallMaximumUploadRequestSize)
		);

		if (fileSizeError) {
			setError(fileSizeError);

			return;
		}

		setError({});
		setLoading(true);

		try {
			let result: AttachmentFile & {id: string};

			if (isUserComputerToCMSBasicDocument) {
				result = await uploadToCMS(
					selectedFile,
					storageDLFolderPath,
					storageDepotGroup
				);
			}
			else {
				result = await uploadToDM(selectedFile, url, portletNamespace);
			}

			const {id, ...attachmentData} = result;

			onAttachmentChange(attachmentData, id);
		}
		catch (error: any) {
			setError({
				displayErrors: true,
				errorMessage:
					error?.message ||
					Liferay.Language.get('unable-to-upload-the-selected-file'),
				valid: false,
			});
		}
		finally {
			setLoading(false);
		}
	};

	return (
		<>
			<div className="inline-item lfr-objects__attachment">
				{!readOnly && (
					<ClayButton
						className="lfr-objects__attachment-button"
						displayType="secondary"
						onClick={() => {
							setError({});

							if (isDocumentsAndMedia) {
								openSelectionModal({
									onSelect: handleSelectedItem,
									selectEventName: `${portletNamespace}selectAttachmentEntry`,
									title: Liferay.Language.get('select-file'),
									url,
								});
							}
							else if (hasLibraryStorage) {
								const filePicker = inputRef.current;

								if (filePicker) {
									filePicker.value = '';
									filePicker.click();
								}
							}
							else if (isCMSBasicDocument) {
								spaceItemSelectorOpenChange(true);
							}
						}}
					>
						{Liferay.Language.get('select-file')}
					</ClayButton>
				)}

				<FileContainer
					attachment={attachment}
					loading={isLoading}
					onDelete={handleDelete}
					readOnly={readOnly}
				/>
			</div>

			{isCMSBasicDocument && (
				<CMSFilesItemSelectorModal
					items={cmsFiles}
					observer={spaceItemSelectorObserver}
					onItemsChange={handleCMSItemsChange}
					onOpenChange={spaceItemSelectorOpenChange}
					open={spaceItemSelectorOpen}
				/>
			)}

			<input
				accept={acceptedFileExtensions
					.split(',')
					.map((extension) => `.${extension.trim()}`)
					.join(',')}
				onChange={handleUpload}
				ref={inputRef}
				style={{display: 'none'}}
				type="file"
			/>
		</>
	);
}
