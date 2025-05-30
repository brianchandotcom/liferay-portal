/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton, {ClayButtonWithIcon} from '@clayui/button';
import ClayLayout from '@clayui/layout';
import ClayModal from '@clayui/modal';
import classNames from 'classnames';
import {sub} from 'frontend-js-web';
import React, {useState} from 'react';
import {useDropzone} from 'react-dropzone';

import DragZoneBackground from './DragZoneBackground';
import {LoadingMessage} from './LoadingMessage';
import {FieldPicker} from './forms';

import '../../../css/components/MultipleFileUploader.scss';
import {AssetLibrary} from '../../types/AssetLibrary';

interface FileData {
	file: File;
	name: string;
	size: number;
}

export default function MultipleFileUploader({
	assetLibraries,
	onModalClose,
}: {
	assetLibraries: AssetLibrary[];
	onModalClose: () => void;
}) {
	const [filesData, setFilesData] = useState<FileData[]>([]);
	const [groupId, setGroupId] = useState(
		assetLibraries.length === 1 ? assetLibraries[0].groupId : ''
	);
	const [isLoading, setIsLoading] = useState(false);


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

	const handleRemoveFile = (fileNameToRemove: string) => {
		setFilesData((prevFilesData) =>
			prevFilesData.filter((file) => file.name !== fileNameToRemove)
		);
	};

	const handleButtonClick = () => {
		setIsLoading(true);

		setTimeout(() => {
			setIsLoading(false);
		}, 5000);
	};

	return (
		<form>
			<ClayModal.Body scrollable>
				{isLoading && (
					<div className="loading-message">
						<LoadingMessage />
					</div>
				)}

				<div
					{...getRootProps({
						className: classNames('dropzone', {
							'dropzone-drag-active': isDragActive,
						}),
					})}
				>
					<input {...getInputProps()} />

					<DragZoneBackground />
				</div>

				{assetLibraries.length > 1 && (
					<div className="mt-4">
						<FieldPicker
							helpMessage={Liferay.Language.get(
								'select-the-space-to-which-the-file-will-be-uploaded'
							)}
							items={assetLibraries.map(({groupId, name}) => ({
								label: name,
								value: groupId,
							}))}
							label={Liferay.Language.get('space')}
							name="groupId"
							onSelectionChange={(value: string) => {
								setGroupId(value);
							}}
							placeholder={Liferay.Language.get('select-a-space')}
							required
							selectedKey={groupId}
						/>
					</div>
				)}

				{!!filesData.length && (
					<div className={classNames('mt-4', {invisible: isLoading})}>
						<p className="text-3 text-secondary text-uppercase">
							{Liferay.Language.get('files-to-upload')}
						</p>

						{filesData.map((fileData, index) => (
							<ClayLayout.ContentRow
								className={classNames('align-items-center', {
									'border-bottom':
										index < filesData.length - 1,
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

								<ClayLayout.ContentCol
									className="text-3"
									expand
								>
									<span className="text-weight-semi-bold">
										{fileData.name}
									</span>

									<span className="text-secondary">
										{Liferay.Util.formatStorage(
											fileData.size,
											{
												addSpaceBeforeSuffix: true,
											}
										)}
									</span>
								</ClayLayout.ContentCol>

								<ClayLayout.ContentCol>
									<ClayButtonWithIcon
										aria-label={Liferay.Language.get(
											'remove-file'
										)}
										borderless
										displayType="secondary"
										onClick={() =>
											handleRemoveFile(fileData.name)
										}
										size="sm"
										symbol="times-circle"
									/>
								</ClayLayout.ContentCol>
							</ClayLayout.ContentRow>
						))}
					</div>
				)}
			</ClayModal.Body>

			{!!filesData.length && (
				<ClayModal.Footer
					last={
						<ClayButton.Group spaced>
							<ClayButton
								displayType="secondary"
								onClick={onModalClose}
							>
								{Liferay.Language.get('cancel')}
							</ClayButton>

							<ClayButton
								disabled={isLoading}
								onClick={handleButtonClick}
							>
								{sub(
									Liferay.Language.get('upload-x'),
									`(${filesData.length})`
								)}
							</ClayButton>
						</ClayButton.Group>
					}
				></ClayModal.Footer>
			)}
		</form>
	);
}
