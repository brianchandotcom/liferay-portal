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
import React, {useEffect, useMemo, useState} from 'react';
import {ErrorCode, useDropzone} from 'react-dropzone';

import ItemSelectorPreview from '../../item_selector_preview/js/ItemSelectorPreview.es';
import DragFileIcon from './components/DragFileIcon';
import getPreviewProps from './utils/getPreviewProps';
import getUploadErrorMessage from './utils/getUploadErrorMessage';
import sendFile from './utils/sendFile';

function SingleFileUploader({
	closeCaption,
	editImageURL,
	itemSelectedEventName,
	maxFileSize: initialMaxFileSizeString,
	uploadItemReturnType,
	uploadItemURL,
	validExtensions,
}) {
	const [abort, setAbort] = useState(null);
	const [errorAnimation, setErrorAnimation] = useState(false);
	const [errorMessage, setErrorMessage] = useState('');
	const [file, setFile] = useState();
	const [itemServerData, setItemServerData] = useState(null);
	const [progress, setProgess] = useState(null);
	const [showPreview, setShowPreview] = useState(false);

	const isMounted = useIsMounted();
	const maxFileSize = Number(initialMaxFileSizeString);

	const CLIENT_ERRORS = useMemo(
		() => ({
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
				[Liferay.Util.formatStorage(maxFileSize)]
			),
			[ErrorCode.TooManyFiles]: Liferay.Language.get(
				'multiple-file-upload-is-not-supported-please-enter-a-single-file'
			),
		}),
		[maxFileSize, validExtensions]
	);

	const {getInputProps, getRootProps, isDragActive} = useDropzone({
		accept: validExtensions,
		maxSize: maxFileSize,
		multiple: false,
		onDropAccepted: (acceptedFiles) => {
			setErrorMessage('');
			setFile(acceptedFiles[0]);
		},
		onDropRejected: (fileRejections) => {
			setErrorMessage(
				CLIENT_ERRORS[fileRejections?.[0]?.errors?.[0]?.code] || ''
			);
		},
	});

	function clear() {
		setFile(null);
		setAbort(null);
		setProgess(null);
	}

	const handleAnimationErrorEnd = () => {
		if (isMounted()) {
			setErrorAnimation(false);
		}
	};

	useEffect(() => {
		if (file) {
			const client = sendFile({
				file,
				fileFieldName: 'imageSelectorFileName',
				onError: () => {
					if (!isMounted()) {
						return;
					}

					setErrorMessage(getUploadErrorMessage());
				},
				onProgress: setProgess,
				onSuccess: (itemData) => {
					if (!isMounted()) {
						return;
					}

					if (itemData.success) {
						setItemServerData(itemData);
						setShowPreview(true);
					}
					else {
						setErrorMessage(
							getUploadErrorMessage(itemData.error, maxFileSize)
						);
					}
				},
				url: uploadItemURL,
			});

			setAbort(() => () => {
				clear();
				client.abort();
			});
		}
	}, [file, isMounted, uploadItemURL, maxFileSize]);

	return (
		<>
			<>
				<div
					{...getRootProps({
						className: classNames('dropzone', {
							'dropzone-drag-active': isDragActive,
							'dropzone-error': errorAnimation,
							'dropzone-uploading': progress,
						}),
					})}
					onAnimationEnd={handleAnimationErrorEnd}
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
						<div>
							<div className="dropzone-drag-file-icon-wrapper">
								<DragFileIcon />
							</div>

							{Liferay.Language.get(
								'drag-and-drop-or-click-to-upload'
							)}
						</div>
					)}
				</div>

				{errorMessage && (
					<ClayForm.FeedbackGroup className="has-error">
						<ClayForm.FeedbackItem>
							<ClayForm.FeedbackIndicator symbol="exclamation-full" />

							{errorMessage}
						</ClayForm.FeedbackItem>
					</ClayForm.FeedbackGroup>
				)}
			</>

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
