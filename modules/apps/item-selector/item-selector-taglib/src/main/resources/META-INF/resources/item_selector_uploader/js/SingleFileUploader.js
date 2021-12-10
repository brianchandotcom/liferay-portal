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

import {ClayButtonWithIcon} from '@clayui/button';
import ClayForm from '@clayui/form';
import ClayLayout from '@clayui/layout';
import ClayProgressBar from '@clayui/progress-bar';
import {useIsMounted} from '@liferay/frontend-js-react-web';
import classNames from 'classnames';
import React, {useEffect, useState} from 'react';
import {ErrorCode, useDropzone} from 'react-dropzone';

import ItemSelectorPreview from '../../item_selector_preview/js/ItemSelectorPreview.es';
import getPreviewProps from './getPreviewProps';
import {sendFile} from './utils';

function SingleFileUploader({
	closeCaption,
	editImageURL,
	itemSelectedEventName,
	maxFileSize,
	uploadItemReturnType,
	uploadItemURL,
	validExtensions,
}) {
	const [abort, setAbort] = useState(null);
	const [file, setFile] = useState();
	const [itemServerData, setItemServerData] = useState(null);
	const [progress, setProgess] = useState(null);
	const [showPreview, setShowPreview] = useState(false);

	const isMounted = useIsMounted();

	const {
		fileRejections,
		getInputProps,
		getRootProps,
		isDragActive,
	} = useDropzone({
		accept: validExtensions,
		maxSize: maxFileSize,
		multiple: false,
		onDrop: (acceptedFiles) => {
			setFile(acceptedFiles[0]);
		},
	});

	const ERRORS = {
		[ErrorCode.FileInvalidType]: Liferay.Util.sub(
			Liferay.Language.get(
				'please-enter-a-file-with-a-valid-extension-x'
			),
			[validExtensions]
		),

		[ErrorCode.FileTooLarge]: Liferay.Util.sub(
			Liferay.Language.get(
				'please-enter-a-file-with-a-valid-file-size-no-larger-than-x'
			),
			[Liferay.Util.formatStorage(Number(maxFileSize))]
		),
		[ErrorCode.TooManyFiles]: Liferay.Language.get(
			'multiple-file-upload-is-not-supported-please-enter-a-single-file'
		),
	};

	function clear() {
		setFile(null);
		setAbort(null);
		setProgess(null);
	}

	useEffect(() => {
		if (file) {
			const client = sendFile({
				file,
				onProgress: setProgess,
				onSuccess: (itemData) => {
					if (isMounted()) {
						setItemServerData(itemData);
						setShowPreview(true);
					}
				},
				url: uploadItemURL,
			});

			setAbort(() => () => {
				clear();
				client.abort();
			});
		}
	}, [file, isMounted, uploadItemURL]);

	const errorCode = fileRejections?.[0]?.errors?.[0]?.code;
	const errorMessage = ERRORS[errorCode] || '';

	return (
		<>
			<div
				className={classNames({
					'has-error': errorMessage,
				})}
			>
				<div
					{...getRootProps({
						className: classNames('dropzone', {
							'dropzone-drag-active': isDragActive,
							'dropzone-uploading': progress,
						}),
					})}
				>
					<input disabled={progress} {...getInputProps()} />

					{progress ? (
						<ClayLayout.ContentRow
							className="align-items-center"
							padded
						>
							<ClayLayout.ContentCol>
								<strong>
									{Liferay.Language.get('uploading')}
								</strong>
							</ClayLayout.ContentCol>

							<ClayLayout.ContentCol expand>
								<ClayProgressBar value={progress} />
							</ClayLayout.ContentCol>

							<ClayLayout.ContentCol>
								<ClayButtonWithIcon
									borderless
									displayType="secondary"
									onClick={abort}
									symbol="times"
								/>
							</ClayLayout.ContentCol>
						</ClayLayout.ContentRow>
					) : (
						<span>
							{Liferay.Language.get(
								'drag-and-drop-or-click-to-upload'
							)}
						</span>
					)}
				</div>

				{errorMessage && (
					<ClayForm.FeedbackGroup>
						<ClayForm.FeedbackItem>
							<ClayForm.FeedbackIndicator symbol="exclamation-full" />

							{errorMessage}
						</ClayForm.FeedbackItem>
					</ClayForm.FeedbackGroup>
				)}
			</div>

			{showPreview && (
				<div className="item-selector-preview-container">
					<ItemSelectorPreview
						{...getPreviewProps({
							closeCaption,
							file,
							itemData: itemServerData,
							itemSelectedEventName,
							uploadItemReturnType,
						})}
						editImageURL={editImageURL}
						handleClose={() => {
							setShowPreview(false);
						}}
						reloadOnHide
					/>
				</div>
			)}
		</>
	);
}

export default SingleFileUploader;
