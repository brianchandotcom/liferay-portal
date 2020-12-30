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

import classNames from 'classnames';
import PropTypes from 'prop-types';
import React, {useRef, useState} from 'react';

import DropHereInfo from '../../drop_here_info/js/DropHereInfo';
import BrowseImage from './BrowseImage';
import ChangeImageControls from './ChangeImageControls';
import ProgressWrapper from './ProgressWrapper';

const CSS_DROP_ACTIVE = 'drop-active';
const CSS_PROGRESS_ACTIVE = 'progress-active';
const STR_IMAGE_DELETED = 'coverImageDeleted';
const STR_IMAGE_SELECTED = 'coverImageSelected';
const STR_IMAGE_UPLOADED = 'coverImageUploaded';
const STR_SPACE = ' ';

const TPL_PROGRESS_DATA =
	'<strong>{0}</strong> {1} of <strong>{2}</strong> {3}';

const ImageSelector = ({
	draggableImage,
	cropRegion,
	fileEntryId = 0,
	imageURL,
	itemSelectorEventName,
	itemSelectorURL,
	maxFileSize = Liferay.PropsValues.UPLOAD_SERVLET_REQUEST_IMPL_MAX_SIZE,
	portletNamespace,
	paramName,
	uploadURL,
	validExtensions,
}) => {
	const [image, setImage] = useState({
		fileEntryId,
		src: imageURL,
	});

	const [fileName, setFileName] = useState('');

	const [progressValue, setProgressValue] = useState(0);
	const [progressData, setProgressData] = useState();

	const rootNodeRef = useRef(null);

	let uploader = null;

	let uploaderStatusStopped;

	const handleSelectFileClick = () => {
		Liferay.Util.openSelectionModal({
			onSelect: (selectedItem) => {
				if (selectedItem) {
					const itemValue = JSON.parse(selectedItem.value);

					setImage({
						fileEntryId: itemValue.fileEntryId || 0,
						src: itemValue.url || '',
					});

					Liferay.fire(STR_IMAGE_SELECTED);
				}
			},
			selectEventName: itemSelectorEventName,
			title: Liferay.Language.get('select-file'),
			url: itemSelectorURL,
		});
	};

	const handleDeleteImageClick = () => {
		setImage({
			fileEntryId: 0,
			src: '',
		});

		Liferay.fire(STR_IMAGE_DELETED, {
			imageData: null,
		});
	};

	const onFileSelect = (event) => {
		rootNodeRef.current.classList.remove(CSS_DROP_ACTIVE);

		const file = event.fileList[0];

		setFileName(file.get('name'));

		const queue = uploader.queue;

		if (queue && queue._currentState === uploaderStatusStopped) {
			queue.startUpload();
		}

		uploader.uploadThese(event.fileList);
	};

	const onUploadCancel = () => {
		console.log('onUploadCancel');
		stopProgress();
	};

	const onUploadComplete = (event) => {
		stopProgress();

		const data = JSON.parse(event.data);

		const image = data.file;
		const success = data.success;

		let fireEvent = STR_IMAGE_DELETED;

		if (success) {
			fireEvent = STR_IMAGE_UPLOADED;

			setImage({
				fileEntryId: image.fileEntryId,
				src: image.url,
			});
		}
		else {

			//TODO error

		}

		Liferay.fire(fireEvent, {
			imageData: success ? image : null,
		});
	};

	const onUploadProgress = (event) => {
		const percentLoaded = Math.round(event.percentLoaded);

		setProgressValue(Math.ceil(percentLoaded));

		const bytesLoaded = Liferay.Util.formatStorage(event.bytesLoaded);

		const bytesTotal = Liferay.Util.formatStorage(event.bytesTotal);

		const bytesLoadedSpaceIndex = bytesLoaded.indexOf(STR_SPACE);

		const bytesTotalSpaceIndex = bytesTotal.indexOf(STR_SPACE);

		setProgressData(
			Liferay.Util.sub(
				TPL_PROGRESS_DATA,
				bytesLoaded.substring(0, bytesLoadedSpaceIndex),
				bytesLoaded.substring(bytesLoadedSpaceIndex + 1),
				bytesTotal.substring(0, bytesTotalSpaceIndex),
				bytesTotal.substring(bytesTotalSpaceIndex + 1)
			)
		);
	};

	const stopProgress = () => {
		rootNodeRef.current.classList.remove(CSS_PROGRESS_ACTIVE);

		setProgressValue(0);
	};

	AUI().use('uploader', (A) => {
		const rootNode = rootNodeRef.current;

		uploader = new A.Uploader({
			boundingBox: rootNode,
			dragAndDropArea: rootNode,
			fileFieldName: 'imageSelectorFileName',
			on: {
				dragleave() {
					rootNode.classList.remove(CSS_DROP_ACTIVE);
				},
				dragover() {
					rootNode.classList.add(CSS_DROP_ACTIVE);
				},
				fileselect: onFileSelect,
				uploadcomplete: onUploadComplete,
				uploadprogress: onUploadProgress,
				uploadstart() {
					rootNode.classList.add(CSS_PROGRESS_ACTIVE);
				},
			},
			uploadURL,
		}).render();

		uploaderStatusStopped = A.Uploader.Queue.STOPPED;
	});

	return (
		<div
			className={classNames(
				'drop-zone',
				{'draggable-image': draggableImage !== 'none'},
				{'drop-enabled': image.fileEntryId == 0},
				'taglib-image-selector'
			)}
			ref={rootNodeRef}
		>
			<input
				name={`${portletNamespace}${paramName}Id`}
				type="hidden"
				value={image.fileEntryId}
			/>
			<input
				name={`${portletNamespace}${paramName}CropRegion`}
				type="hidden"
				value={cropRegion}
			/>

			{image.src && (
				<div className="image-wrapper">
					<img
						alt={Liferay.Language.get('current-image')}
						className="current-image"
						id={`${portletNamespace}image`}
						src={image.src}
					/>
				</div>
			)}

			{image.fileEntryId == 0 && (
				<BrowseImage
					handleClick={handleSelectFileClick}
					itemSelectorEventName={itemSelectorEventName}
					itemSelectorURL={itemSelectorURL}
					maxFileSize={maxFileSize}
					validExtensions={validExtensions}
				/>
			)}

			{image.fileEntryId != 0 && (
				<ChangeImageControls
					handleClickDelete={handleDeleteImageClick}
					handleClickPicture={handleSelectFileClick}
				/>
			)}

			<DropHereInfo />
			<ProgressWrapper
				fileName={fileName}
				onCancel={onUploadCancel}
				progressData={progressData}
				progressValue={progressValue}
			/>
		</div>
	);
};

ImageSelector.propTypes = {
	cropRegion: PropTypes.string,
	draggableImage: PropTypes.string,
	fileEntryId: PropTypes.string.isRequired,
	imageURL: PropTypes.string,
	itemSelectorEventName: PropTypes.string,
	itemSelectorURL: PropTypes.string,
	maxFileSize: PropTypes.number,
	paramName: PropTypes.string.isRequired,
	portletNamespace: PropTypes.string.isRequired,
	validExtensions: PropTypes.string,
};

export default ImageSelector;
