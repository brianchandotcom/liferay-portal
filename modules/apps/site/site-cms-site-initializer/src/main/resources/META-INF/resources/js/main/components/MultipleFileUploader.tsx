/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayButtonWithIcon} from '@clayui/button';
import ClayLayout from '@clayui/layout';
import classNames from 'classnames';
import {formatStorage} from 'frontend-js-web';
import React, {useState} from 'react';
import {useDropzone} from 'react-dropzone';

interface FileData {
	file: File;
	name: string;
	size: number;
}

export default function MultipleFileUploader() {
	const [filesData, setFilesData] = useState<FileData[]>([]);

	const {getInputProps, getRootProps, isDragActive} = useDropzone({
		multiple: true,
		onDropAccepted: (acceptedFiles) => {
			const newFilesToUpload = acceptedFiles.map((file) => ({
				file,
				name: file.name,
				size: file.size,
			}));

			setFilesData((prevFilesData) => {
				const currentIds = new Set(
					prevFilesData.map((fileData) => fileData.name)
				);
				const uniqueNewFiles = newFilesToUpload.filter(
					(nf) => !currentIds.has(nf.name)
				);

				return [...prevFilesData, ...uniqueNewFiles];
			});
		},
	});

	return (
		<>
			<div
				style={{border: '1px solid black', height: '200px'}}
				{...getRootProps({
					className: classNames('dropzone', {
						'dropzone-drag-active': isDragActive,
					}),
				})}
			>
				<input {...getInputProps()} />
			</div>

			{!!filesData.length && (
				<div className="mt-4">
					<p className="text-3 text-secondary text-uppercase">
						{Liferay.Language.get('files-to-upload')}
					</p>

					{filesData.map((fileData, index) => (
						<ClayLayout.ContentRow
							className={classNames('align-items-center', {
								'border-bottom': index < filesData.length - 1,
							})}
							key={fileData.name}
							padded
						>
							<ClayLayout.ContentCol>
								<ClayButtonWithIcon
									displayType="secondary"
									size="sm"
									symbol="document"
								/>
							</ClayLayout.ContentCol>

							<ClayLayout.ContentCol className="text-3" expand>
								<span className="text-weight-semi-bold">
									{fileData.name}
								</span>

								<span className="text-secondary">
									{formatStorage(fileData.size, {
										addSpaceBeforeSuffix: true,
									})}
								</span>
							</ClayLayout.ContentCol>

							<ClayLayout.ContentCol>
								<ClayButtonWithIcon
									borderless
									displayType="secondary"
									size="sm"
									symbol="times-circle"
								/>
							</ClayLayout.ContentCol>
						</ClayLayout.ContentRow>
					))}
				</div>
			)}
		</>
	);
}
