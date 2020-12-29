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
import classNames from 'classnames';
import PropTypes from 'prop-types';
import React, {useState} from 'react';

import DropHereInfo from '../../drop_here_info/js/DropHereInfo';

const SELECT_FILE_BUTTON = `<button class='btn btn-secondary' type='button'>${Liferay.Language.get(
	'select-file'
)}</button>`;
const STR_IMAGE_DELETED = 'coverImageDeleted';
const STR_IMAGE_SELECTED = 'coverImageSelected';

const BrowseImageZone = ({
	handleClick,
	itemSelectorEventName,
	itemSelectorURL,
	maxFileSize,
	validExtensions,
}) => (
	<div className="browse-image-controls">
		<div className="drag-drop-label" onClick={handleClick}>
			{itemSelectorEventName && itemSelectorURL ? (
				Liferay.Browser.isMobile() ? (
					SELECT_FILE_BUTTON
				) : (
					<span
						className="pr-1"
						dangerouslySetInnerHTML={{
							__html: Liferay.Util.sub(
								Liferay.Language.get(
									'drag-and-drop-to-upload-or-x'
								),
								SELECT_FILE_BUTTON
							),
						}}
					></span>
				)
			) : (
				Liferay.Language.get('drag-and-drop-to-upload')
			)}
		</div>
		<div className="file-validation-info">
			{validExtensions && <strong>{validExtensions}</strong>}

			{maxFileSize !== 0 && (
				<span
					className="pl-1"
					dangerouslySetInnerHTML={{
						__html: Liferay.Util.sub(
							Liferay.Language.get('maximum-size-x'),
							Liferay.Util.formatStorage(
								parseInt(maxFileSize, 10)
							)
						),
					}}
				></span>
			)}
		</div>
	</div>
);

const ChangeImageControls = ({handleClickDelete, handleClickPicture}) => (
	<div className="change-image-controls">
		<ClayButtonWithIcon
			displayType="secondary"
			monospaced
			onClick={handleClickPicture}
			symbol="picture"
			title={Liferay.Language.get('change-image')}
		/>

		<ClayButtonWithIcon
			className="ml-1"
			displayType="secondary"
			monospaced
			onClick={handleClickDelete}
			symbol="trash"
			title={Liferay.Language.get('remove-image')}
		/>
	</div>
);

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
	validExtensions,
}) => {
	const [image, setImage] = useState({
		fileEntryId,
		src: imageURL,
	});

	const handleSelectFileClick = (event) => {
		if (event.target.tagName === 'BUTTON') {
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
		}
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

	return (
		<div
			className={classNames(
				'drop-zone',
				{'draggable-image': draggableImage !== 'none'},
				{'drop-enabled': image.fileEntryId == 0},
				'taglib-image-selector'
			)}
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
				<BrowseImageZone
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
