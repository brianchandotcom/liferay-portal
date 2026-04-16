/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import classNames from 'classnames';
import {FieldBase} from 'frontend-js-components-web';
import React from 'react';
import {useDropzone} from 'react-dropzone';

import DragZoneBackground from '../DragZoneBackground';

const DragZone = ({
	handleRejection,
	handleUpload,
	maxFiles,
	maxSize,
	name,
	validExtensions,
}: {
	handleRejection: (error: string) => void;
	handleUpload: (file: File) => Promise<void>;
	maxFiles: number;
	maxSize: number;
	name: string;
	validExtensions?: string;
}) => {
	const {getInputProps, getRootProps, isDragActive} = useDropzone({
		accept: validExtensions === '*' ? undefined : validExtensions,
		maxFiles,
		maxSize,
		noKeyboard: false,
		onDropAccepted: async (acceptedFiles) => {
			const file = acceptedFiles[0];
			await handleUpload(file);
		},
		onDropRejected: (rejectedFiles) => {
			if (rejectedFiles.length > 1 && maxFiles === 1) {
				handleRejection(
					Liferay.Language.get('multiple-files-not-allowed')
				);

				return;
			}

			const {errors} = rejectedFiles[0];

			handleRejection(errors[0].message);
		},
	});

	return (
		<FieldBase id={name} required>
			<div
				{...getRootProps({
					'aria-label': Liferay.Language.get(
						'drag-and-drop-to-upload'
					),
					'className': classNames(
						'dropzone p-4 rounded text-center text-secondary',
						{
							'dropzone-drag-active': isDragActive,
						}
					),
					'role': 'button',
				})}
			>
				<input aria-hidden="true" {...getInputProps()} />

				<DragZoneBackground maxSize={maxSize} />
			</div>
		</FieldBase>
	);
};

export default DragZone;
