/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import {
	convertToFormData,
	makeFetch,
	useConfig,
} from 'data-engine-js-components-web';
import {FieldChangeEventHandler} from 'dynamic-data-mapping-form-field-type';
import {openSelectionModal} from 'frontend-js-web';
import React, {ChangeEventHandler, useRef, useState} from 'react';

import FileContainer from './FileContainer';
import {validateFileExtension, validateFileSize} from './util/attachment';

import './Attachment.scss';

export type Attachment = {
	contentURL: string;
	title: string;
};

type File = {
	contentURL: string;
	fileEntryId: string;
	readOnly: boolean;
	title: string;
};

export interface AttachmentBaseProps {
	acceptedFileExtensions: string;
	contentURL: string;
	error: {} | {displayErrors: boolean; errorMessage: string; valid: boolean};
	fileSource: string;
	maximumFileSize: number;
	onChange: FieldChangeEventHandler<string>;
	overallMaximumUploadRequestSize: number;
	readOnly: boolean;
	setError: React.Dispatch<React.SetStateAction<{}>>;
	tip: string;
	title: string;
	url: string;
}

export default function AttachmentBase({
	acceptedFileExtensions,
	contentURL,
	fileSource,
	maximumFileSize,
	onChange,
	overallMaximumUploadRequestSize,
	readOnly,
	setError,
	title,
	url,
}: AttachmentBaseProps) {
	const {portletNamespace} = useConfig();
	const inputRef = useRef<HTMLInputElement>(null);
	const [attachment, setAttachment] = useState<Attachment | null>(
		contentURL && title ? {contentURL, title} : null
	);
	const [isLoading, setLoading] = useState(false);

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
				selectedItemValue.size,
				maximumFileSize,
				Number(overallMaximumUploadRequestSize)
			);

		if (error) {
			setError(error);
		}
		else {
			setAttachment({
				contentURL: selectedItemValue.url,
				title: selectedItemValue.title,
			});

			onChange({target: {value: selectedItemValue.fileEntryId}});
		}
	};

	const handleDelete = () => {
		setAttachment(null);

		onChange({target: {value: ''}}); // TODO: fix backend to support null
	};

	const handleUpload: ChangeEventHandler<HTMLInputElement> = async ({
		target: {files},
	}) => {
		const selectedFile = files?.[0];
		if (selectedFile) {
			const fileSizeError = validateFileSize(
				selectedFile.size,
				maximumFileSize,
				Number(overallMaximumUploadRequestSize)
			);

			if (fileSizeError) {
				setError(fileSizeError);

				return;
			}
			setError({});
			setLoading(true);
			try {
				const {error, file} = (await makeFetch({
					body: convertToFormData({
						[`${portletNamespace}file`]: files[0],
					}),
					method: 'POST',
					url,
				})) as {error: {message: string}; file: File; success: boolean};

				if (error) {
					setError({
						displayErrors: true,
						errorMessage: error.message,
						valid: false,
					});
				}
				else {
					setAttachment({
						contentURL: file.contentURL,
						title: file.title,
					});

					onChange({target: {value: file.fileEntryId}});
				}
			}
			finally {
				setLoading(false);
			}
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

							if (fileSource === 'documentsAndMedia') {
								openSelectionModal({
									onSelect: handleSelectedItem,
									selectEventName: `${portletNamespace}selectAttachmentEntry`,
									title: Liferay.Language.get('select-file'),
									url,
								});
							}
							else if (fileSource === 'userComputer') {
								const filePicker = inputRef.current;
								if (filePicker) {
									filePicker.value = '';
									filePicker.click();
								}
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
