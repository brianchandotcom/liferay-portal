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

import ClayButton from '@clayui/button';
import classNames from 'classnames';
import PropTypes from 'prop-types';
import React, {useState} from 'react';

const BrowseImageZone = ({
	handleClick,
	itemSelectorEventName,
	itemSelectorURL,
	maxFileSize,
	validExtensions,
}) => (
	<div className="browse-image-controls">
		<div className="drag-drop-label">
			{itemSelectorEventName && itemSelectorURL ? (
				Liferay.Browser.isMobile() ? (
					<SelectFileButton handleClick={handleClick} />
				) : (
					<>
						<span
							className="pr-1"
							dangerouslySetInnerHTML={{
								__html: Liferay.Language.get(
									'drag-and-drop-to-upload-or'
								),
							}}
						></span>
						<SelectFileButton handleClick={handleClick} />
					</>
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

const SelectFileButton = ({handleClick}) => (
	<ClayButton displayType="secondary" onClick={handleClick}>
		{Liferay.Language.get('select-file')}
	</ClayButton>
);

const STR_IMAGE_SELECTED = 'coverImageSelected';

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
