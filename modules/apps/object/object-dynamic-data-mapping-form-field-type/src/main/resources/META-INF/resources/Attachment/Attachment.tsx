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
import {fetch} from 'frontend-js-web';
import React, {ChangeEventHandler, useEffect, useRef, useState} from 'react';

import './Attachment.scss';

const BYTES_PER_MB = 1048576;

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
							window.open(
								window.location.origin + attachment.contentUrl,
								'_blank'
							);
						}}
					>
						{attachment.title}
					</ClayButton>

					<a
						className="lfr-objects__attachment-download"
						download
						href={`${window.location.origin}${attachment.contentUrl}`}
					>
						<ClayIcon symbol="download" />
					</a>
				</div>
				<ClayButtonWithIcon
					borderless
					displayType="secondary"
					monospaced
					onClick={() => onDelete(attachment)}
					symbol="times-circle-full"
				/>
			</>
		);
	}

	return null;
}

export default function Attachment({
	acceptedFileExtensions,
	maximumFileSize,
	objectEntryId, // "0" means that there is no previews
	onChange,
	uploadURL,
	value,

	...otherProps
}: IProps) {
	const {portletNamespace} = useConfig();
	const inputRef = useRef<HTMLInputElement>(null);
	const [isLoading, setLoading] = useState(false);
	const [error, setError] = useState({});
	const [attachment, setAttachment] = useState<Attachment>();

	useEffect(() => {
		let isMounted = true;

		if (value) {
			fetch(`/o/headless-delivery/v1.0/documents/${value}`).then(
				async (response: any) => {
					if (isMounted && response.ok) {
						const file = await response.json();
						setAttachment(file as Attachment);
					}
				}
			);
		}
		else if (value === null) {
			setAttachment(undefined);
		}

		return () => {
			isMounted = false;
		};
	}, [value]);

	const handleDelete = async ({
		actions: {
			delete: {href, method},
		},
	}: Attachment) => {
		if (objectEntryId === '0') {
			await fetch(href, {method});
		}

		onChange({target: {value: null}});
	};

	const handleUpload: ChangeEventHandler<HTMLInputElement> = async ({
		target: {files},
	}) => {
		const selectedFile = files?.[0];
		if (selectedFile) {
			const maxFileSize = Number(maximumFileSize);

			if (selectedFile.size > maxFileSize * BYTES_PER_MB) {
				setError({
					displayErrors: true,
					errorMessage: Liferay.Util.sub(
						Liferay.Language.get(
							'please-enter-a-file-with-a-valid-file-size-no-larger-than-x'
						),
						`${maxFileSize} MB`
					),
					valid: false,
				});

				return;
			}
			setError({});
			setLoading(true);
			try {
				const {file} = (await makeFetch({
					body: convertToFormData({
						[`${portletNamespace}file`]: files[0],
					}),
					method: 'POST',
					url: uploadURL,
				})) as {file: File; success: boolean};

				onChange({target: {value: file.fileEntryId}});
			}
			finally {
				setLoading(false);
			}
		}
	};

	const tip = Liferay.Util.sub(
		Liferay.Language.get('upload-a-x-no-larger-than-x-mb'),
		acceptedFileExtensions,
		maximumFileSize
	);

	return (
		<FieldBase {...otherProps} {...error} tip={tip}>
			<div className="inline-item lfr-objects__attachment">
				<ClayButton
					className="lfr-objects__attachment-button"
					displayType="secondary"
					onClick={() => {
						setError({});
						const filePicker = inputRef.current;
						if (filePicker) {
							filePicker.value = '';
							filePicker.click();
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
	attributeDataImageId: 'data-image-id';
	fileEntryId: '40678';
	groupId: '20123';
	mimeType: 'image/jpeg';
	randomId: '';
	title: 'star-wars-the-rise-of-skywalker-new_1572371043 (1).jpg';
	type: 'document';
	url: string;
	uuid: '5e564762-705a-de9e-05a2-f427c1232b56';
}

interface Action {
	href: string;
	method: 'DELETE' | 'GET' | 'PATCH' | 'PUT';
}
interface Attachment {
	actions: {
		delete: Action;
	};
	contentUrl: string;
	id: number;
	title: string;
}

interface IFileProps {
	attachment?: Attachment;
	loading?: boolean;
	onDelete: (attachment: Attachment) => void;
}
interface IProps {
	acceptedFileExtensions: string;
	maximumFileSize: string; // TODO: Fix endpoint to fetch as a number
	objectEntryId: string; // TODO: Fix endpoint to fetch as a number
	onChange: FieldChangeEventHandler;
	uploadURL: string;
	value: string; // TODO: Fix endpoint to fetch as a number
}
