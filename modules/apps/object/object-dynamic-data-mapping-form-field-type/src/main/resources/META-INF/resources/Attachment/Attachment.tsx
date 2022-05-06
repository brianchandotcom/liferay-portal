/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import ClayButton, {ClayButtonWithIcon} from '@clayui/button';
import ClayIcon from '@clayui/icon';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import {
	convertToFormData,
	makeFetch,
	useConfig,
} from 'data-engine-js-components-web';
import {
	FieldChangeEventHandler,
	ReactFieldBase as FieldBase,
} from 'dynamic-data-mapping-form-field-type';
import {openSelectionModal} from 'frontend-js-web';
import React, {ChangeEventHandler, useRef, useState} from 'react';

import './Attachment.scss';

function validateFileExtension(
	acceptedFileExtensions: string,
	fileExtension: string
) {
	const isValidExtension = acceptedFileExtensions
		.split(/\s*,\s*/)
		.some(
			(acceptedFileExtension) =>
				acceptedFileExtension.toLowerCase() ===
				fileExtension.toLowerCase()
		);

	if (!isValidExtension) {
		return {
			displayErrors: true,
			errorMessage: Liferay.Util.sub(
				Liferay.Language.get(
					'please-enter-a-file-with-a-valid-extension-x'
				),
				acceptedFileExtensions
			),
			valid: false,
		};
	}
}

function validateFileSize(
	fileSize: number,
	maxFileSize: number,
	overallMaximumUploadRequestSize: number
) {
	if (maxFileSize === 0 && fileSize > overallMaximumUploadRequestSize) {
		return {
			displayErrors: true,
			errorMessage: Liferay.Util.sub(
				Liferay.Language.get(
					'file-size-is-larger-than-the-allowed-overall-maximum-upload-request-size-x'
				),
				`${overallMaximumUploadRequestSize / 1048576} MB`
			),
			valid: false,
		};
	}

	if (
		maxFileSize > 0 &&
		fileSize > maxFileSize * overallMaximumUploadRequestSize
	) {
		return {
			displayErrors: true,
			errorMessage: Liferay.Util.sub(
				Liferay.Language.get(
					'please-enter-a-file-with-a-valid-file-size-no-larger-than-x'
				),
				`${maxFileSize} MB`
			),
			valid: false,
		};
	}
}

function File({attachment, loading, onDelete}: IFileProps) {
	if (loading) {
		return (
			<ClayLoadingIndicator className="lfr-objects__attachment-loading" />
		);
	}
	else if (attachment) {
		return (
			<>
				<div className="lfr-objects__attachment-title">
					<ClayButton
						displayType="unstyled"
						onClick={() => {
							window.open(attachment.contentURL, '_blank');
						}}
					>
						{attachment.title}
					</ClayButton>

					<a
						className="lfr-objects__attachment-download"
						download
						href={attachment.contentURL}
					>
						<ClayIcon symbol="download" />
					</a>
				</div>
				<ClayButtonWithIcon
					borderless
					displayType="secondary"
					monospaced
					onClick={() => onDelete()}
					symbol="times-circle-full"
				/>
			</>
		);
	}

	return null;
}

export default function Attachment({
	acceptedFileExtensions,
	contentURL,
	fileSource,
	maximumFileSize,
	onChange,
	overallMaximumUploadRequestSize,
	title,
	url,
	...otherProps
}: IProps) {
	const {portletNamespace} = useConfig();
	const inputRef = useRef<HTMLInputElement>(null);
	const [attachment, setAttachment] = useState<Attachment | null>(
		contentURL && title ? {contentURL, title} : null
	);
	const [error, setError] = useState({});
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
		<FieldBase {...otherProps} {...error}>
			<div className="inline-item lfr-objects__attachment">
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

				<File
					attachment={attachment}
					loading={isLoading}
					onDelete={handleDelete}
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
		</FieldBase>
	);
}

interface File {
	contentURL: string;
	fileEntryId: string;
	title: string;
}

interface Attachment {
	contentURL: string;
	title: string;
}

interface IFileProps {
	attachment: Attachment | null;
	loading?: boolean;
	onDelete: () => void;
}
interface IProps {
	acceptedFileExtensions: string;
	contentURL: string;
	fileSource: string;
	maximumFileSize: number;
	onChange: FieldChangeEventHandler;
	overallMaximumUploadRequestSize: number;
	title: string;
	url: string;
}
